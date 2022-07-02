/*     */ package ic2.core.profile;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.util.XmlUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.ZipEntry;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOCase;
/*     */ import org.apache.commons.io.filefilter.WildcardFileFilter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileParser
/*     */ {
/*     */   public static Profile parse(ProfileTarget root) throws IOException {
/*  56 */     return parse(root, root.offset("profile.xml").asStream());
/*     */   }
/*     */   
/*     */   public static Profile parse(ProfileTarget root, InputStream is) throws IOException {
/*     */     ProfileNode parent;
/*     */     try {
/*  62 */       parent = create(is);
/*  63 */     } catch (SAXException e) {
/*  64 */       IC2.log.error(LogCategory.Resource, e, "Error reading profile XML");
/*  65 */       return null;
/*     */     } finally {
/*  67 */       is.close();
/*     */     } 
/*     */     
/*  70 */     String name = null;
/*  71 */     Version style = null;
/*  72 */     Set<TextureStyle> textures = new HashSet<>();
/*  73 */     List<RecipeChange> recipeChanges = new ArrayList<>();
/*     */     
/*  75 */     for (Node rawNode : parent.getNodes()) {
/*  76 */       switch (rawNode.getType()) {
/*     */         case name:
/*  78 */           if (name != null) throw new RuntimeException("Duplicate profile names: " + name + " and " + ((NameNode)rawNode).name); 
/*  79 */           name = ((NameNode)rawNode).name;
/*     */           continue;
/*     */         
/*     */         case style:
/*  83 */           if (style != null) throw new RuntimeException("Duplicate profile styles: " + style + " and " + ((StyleNode)rawNode).style); 
/*  84 */           style = ((StyleNode)rawNode).style;
/*     */           continue;
/*     */         
/*     */         case textures:
/*  88 */           textures.add(((TextureNode)rawNode).style.apply(root));
/*     */           continue;
/*     */ 
/*     */         
/*     */         case blocks:
/*     */         case items:
/*     */           continue;
/*     */         
/*     */         case crafting:
/*  97 */           for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
/*  98 */             switch (cookedNode.getType()) {
/*     */               case shaped:
/*     */               case shapeless:
/* 101 */                 recipeChanges.addAll(parseChanges(root, cookedNode.getType().name() + "_recipes", (ParentNode)cookedNode));
/*     */                 continue;
/*     */             } 
/*     */             
/* 105 */             assert NodeType.crafting.validChildren.contains(cookedNode.getType());
/* 106 */             throw new IllegalStateException("Unexpected child element in crafting node: " + cookedNode);
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case furnace:
/*     */         case macerator:
/*     */         case compressor:
/*     */         case extractor:
/*     */         case ore_washer:
/*     */         case thermal_centrifuge:
/*     */         case blast_furnace:
/*     */         case block_cutter:
/* 119 */           recipeChanges.addAll(parseChanges(root, (ParentNode)rawNode));
/*     */           continue;
/*     */         
/*     */         case metal_former:
/* 123 */           for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
/* 124 */             switch (cookedNode.getType()) {
/*     */               case cutting:
/*     */               case extruding:
/*     */               case rolling:
/* 128 */                 recipeChanges.addAll(parseChanges(root, "metal_former_" + cookedNode.getType().name(), (ParentNode)cookedNode));
/*     */                 continue;
/*     */             } 
/*     */             
/* 132 */             assert NodeType.metal_former.validChildren.contains(cookedNode.getType());
/* 133 */             throw new IllegalStateException("Unexpected child element in metal former node: " + cookedNode);
/*     */           } 
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 139 */       assert (parent.getType()).validChildren.contains(rawNode.getType());
/* 140 */       throw new IllegalStateException("Unexpected child element in " + parent.getType() + ": " + rawNode);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (name == null) throw new RuntimeException("Missing name for profile at " + root + "/profile.xml!"); 
/* 145 */     if (style == null) style = Version.NEW; 
/* 146 */     if (textures.isEmpty()) textures.add(TextureStyle.EXPERIMENTAL);
/*     */     
/* 148 */     return new Profile(name, textures, style, recipeChanges.<RecipeChange>toArray(new RecipeChange[0]));
/*     */   }
/*     */   
/*     */   private static List<RecipeChange> parseChanges(ProfileTarget root, ParentNode parent) {
/* 152 */     return parseChanges(root, parent.getType().name(), parent);
/*     */   }
/*     */   
/*     */   private static List<RecipeChange> parseChanges(ProfileTarget root, String name, ParentNode parent) {
/* 156 */     List<RecipeChange> ret = new ArrayList<>();
/* 157 */     boolean madeReplacement = false;
/*     */     
/* 159 */     for (Node rawNode : parent.getNodes()) {
/* 160 */       List<ProfileTarget> targets; switch (rawNode.getType()) {
/*     */         case extend:
/* 162 */           if (madeReplacement) throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode); 
/* 163 */           if (ret.stream().anyMatch(change -> (change.type == RecipeChange.ChangeType.EXTENSION))) throw new RuntimeException("Duplicate profile extension!"); 
/* 164 */           ret.add(new RecipeChange.RecipeExtension(name, ((ExtensionNode)rawNode).profile));
/*     */           continue;
/*     */ 
/*     */         
/*     */         case replacements:
/* 169 */           if (!madeReplacement && !ret.isEmpty()) throw new RuntimeException("Non-replacement changes made alongside replacement: " + ret); 
/* 170 */           assert ret.stream().allMatch(change -> (change.type == RecipeChange.ChangeType.REPLACEMENT));
/* 171 */           madeReplacement = true;
/*     */           
/* 173 */           targets = new ArrayList<>();
/* 174 */           for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
/* 175 */             switch (cookedNode.getType()) {
/*     */               case file:
/* 177 */                 targets.add(root.offset(((FileNode)cookedNode).path));
/*     */                 continue;
/*     */               
/*     */               case folder:
/* 181 */                 targets.addAll(((FolderNode)cookedNode).getFiles(root));
/*     */                 continue;
/*     */             } 
/*     */             
/* 185 */             assert NodeType.replacements.validChildren.contains(cookedNode.getType());
/* 186 */             throw new IllegalStateException("Unexpected child element in replacements node: " + cookedNode);
/*     */           } 
/*     */ 
/*     */           
/* 190 */           ret.add(new RecipeChange.RecipeReplacement(name, targets.<ProfileTarget>toArray(new ProfileTarget[0])));
/*     */           continue;
/*     */ 
/*     */         
/*     */         case additions:
/* 195 */           if (madeReplacement) throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode);
/*     */           
/* 197 */           targets = new ArrayList<>();
/* 198 */           for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
/* 199 */             switch (cookedNode.getType()) {
/*     */               case file:
/* 201 */                 targets.add(root.offset(((FileNode)cookedNode).path));
/*     */                 continue;
/*     */               
/*     */               case folder:
/* 205 */                 targets.addAll(((FolderNode)cookedNode).getFiles(root));
/*     */                 continue;
/*     */             } 
/*     */             
/* 209 */             assert NodeType.additions.validChildren.contains(cookedNode.getType());
/* 210 */             throw new IllegalStateException("Unexpected child element in additions node: " + cookedNode);
/*     */           } 
/*     */ 
/*     */           
/* 214 */           ret.add(new RecipeChange.RecipeAddition(name, targets.<ProfileTarget>toArray(new ProfileTarget[0])));
/*     */           continue;
/*     */ 
/*     */         
/*     */         case removals:
/* 219 */           if (madeReplacement) throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode); 
/* 220 */           ret.add((RecipeChange)null);
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 225 */       assert (parent.getType()).validChildren.contains(rawNode.getType());
/* 226 */       throw new IllegalStateException("Unexpected child element in " + parent.getType() + ": " + rawNode);
/*     */     } 
/*     */ 
/*     */     
/* 230 */     return ret;
/*     */   }
/*     */   
/*     */   private static ProfileNode create(InputStream is) throws SAXException, IOException {
/* 234 */     is = new BufferedInputStream(is);
/* 235 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */     
/*     */     try {
/* 238 */       SAXParser parser = factory.newSAXParser();
/* 239 */       XMLReader reader = parser.getXMLReader();
/* 240 */       SaxHandler handler = new SaxHandler();
/* 241 */       reader.setContentHandler(handler);
/* 242 */       reader.parse(new InputSource(is));
/*     */       
/* 244 */       return handler.getResult();
/* 245 */     } catch (ParserConfigurationException e) {
/* 246 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class SaxHandler
/*     */     extends DefaultHandler {
/*     */     private ProfileParser.ParentNode parentNode;
/*     */     private ProfileParser.Node currentNode;
/*     */     
/*     */     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 256 */       ProfileParser.NodeType type = ProfileParser.NodeType.get(qName);
/* 257 */       if (type == null) type = ProfileParser.NodeType.get(qName.toLowerCase(Locale.ENGLISH)); 
/* 258 */       if (type == null) throw new SAXException("Invalid element: " + qName);
/*     */       
/* 260 */       if (type == ProfileParser.NodeType.profile) {
/* 261 */         if (this.parentNode != null) throw new SAXException("Invalid profile element location"); 
/* 262 */       } else if (this.parentNode == null) {
/* 263 */         throw new SAXException("invalid " + qName + " element location");
/*     */       } 
/*     */       
/* 266 */       switch (type) {
/*     */         case profile:
/* 268 */           this.currentNode = this.parentNode = new ProfileParser.ProfileNode();
/*     */           break;
/*     */         case shaped:
/*     */         case shapeless:
/*     */         case cutting:
/*     */         case extruding:
/*     */         case rolling:
/*     */         case blocks:
/*     */         case items:
/*     */         case crafting:
/*     */         case furnace:
/*     */         case macerator:
/*     */         case compressor:
/*     */         case extractor:
/*     */         case ore_washer:
/*     */         case thermal_centrifuge:
/*     */         case blast_furnace:
/*     */         case block_cutter:
/*     */         case metal_former:
/*     */         case replacements:
/*     */         case additions:
/*     */         case removals:
/*     */         case whitelist:
/*     */         case blacklist:
/* 292 */           this.currentNode = new ProfileParser.GenericParentNode(this.parentNode, type);
/* 293 */           this.parentNode.addNode(this.currentNode);
/* 294 */           this.parentNode = (ProfileParser.ParentNode)this.currentNode;
/*     */           break;
/*     */         case name:
/* 297 */           this.currentNode = new ProfileParser.NameNode(this.parentNode);
/* 298 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case textures:
/* 301 */           this.currentNode = new ProfileParser.TextureNode(this.parentNode, attributes);
/* 302 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case style:
/* 305 */           this.currentNode = new ProfileParser.StyleNode(this.parentNode, attributes);
/* 306 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case stack:
/* 309 */           this.currentNode = new ProfileParser.StackNode(this.parentNode, attributes);
/* 310 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case ore_dict:
/* 313 */           this.currentNode = new ProfileParser.OreDictionaryNode(this.parentNode, attributes);
/* 314 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case file:
/* 317 */           this.currentNode = new ProfileParser.FileNode(this.parentNode, attributes);
/* 318 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case folder:
/* 321 */           this.currentNode = new ProfileParser.FolderNode(this.parentNode, attributes);
/* 322 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case extend:
/* 325 */           this.currentNode = new ProfileParser.ExtensionNode(this.parentNode, attributes);
/* 326 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void characters(char[] ch, int start, int length) throws SAXException {
/* 334 */       while (length > 0 && Character.isWhitespace(ch[start])) {
/* 335 */         start++;
/* 336 */         length--;
/*     */       } 
/*     */ 
/*     */       
/* 340 */       while (length > 0 && Character.isWhitespace(ch[start + length - 1])) {
/* 341 */         length--;
/*     */       }
/*     */       
/* 344 */       if (length != 0) {
/* 345 */         if (this.currentNode == null) throw new SAXException("unexpected characters");
/*     */         
/* 347 */         this.currentNode.setContent(new String(ch, start, length));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void endElement(String uri, String localName, String qName) throws SAXException {
/* 353 */       if (this.currentNode == this.parentNode) {
/* 354 */         if (this.currentNode.getType() == ProfileParser.NodeType.profile) {
/*     */           
/* 356 */           this.currentNode = null;
/*     */         } else {
/* 358 */           this.currentNode = this.parentNode = this.parentNode.parent;
/*     */         } 
/*     */       } else {
/* 361 */         this.currentNode = this.parentNode;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ProfileParser.ProfileNode getResult() {
/* 366 */       return (ProfileParser.ProfileNode)this.parentNode;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NodeType
/*     */   {
/* 375 */     name((String)new NodeType[0]),
/* 376 */     textures((String)new NodeType[0]),
/* 377 */     style((String)new NodeType[0]),
/* 378 */     stack((String)new NodeType[0]),
/* 379 */     ore_dict((String)new NodeType[0]),
/* 380 */     file((String)new NodeType[0]),
/* 381 */     folder((String)new NodeType[0]),
/* 382 */     extend((String)new NodeType[0]),
/*     */     
/* 384 */     whitelist((String)new NodeType[] { stack }),
/* 385 */     blacklist((String)new NodeType[] { stack }),
/* 386 */     blocks((String)new NodeType[] { whitelist, blacklist }),
/* 387 */     items((String)new NodeType[] { whitelist, blacklist }),
/* 388 */     replacements((String)new NodeType[] { file, folder }),
/* 389 */     additions((String)new NodeType[] { file, folder }),
/* 390 */     removals((String)new NodeType[] { stack, ore_dict, file, folder
/*     */       }),
/* 392 */     shaped((String)new NodeType[] { extend, replacements, additions, removals }),
/* 393 */     shapeless((String)new NodeType[] { extend, replacements, additions, removals }),
/* 394 */     crafting((String)new NodeType[] { shaped, shapeless
/*     */       }),
/* 396 */     furnace((String)new NodeType[] { extend, replacements, additions, removals }),
/* 397 */     macerator((String)new NodeType[] { extend, replacements, additions, removals }),
/* 398 */     compressor((String)new NodeType[] { extend, replacements, additions, removals }),
/* 399 */     extractor((String)new NodeType[] { extend, replacements, additions, removals }),
/* 400 */     ore_washer((String)new NodeType[] { extend, replacements, additions, removals }),
/* 401 */     thermal_centrifuge((String)new NodeType[] { extend, replacements, additions, removals }),
/* 402 */     blast_furnace((String)new NodeType[] { extend, replacements, additions, removals }),
/* 403 */     block_cutter((String)new NodeType[] { extend, replacements, additions, removals
/*     */       }),
/* 405 */     cutting((String)new NodeType[] { extend, replacements, additions, removals }),
/* 406 */     extruding((String)new NodeType[] { extend, replacements, additions, removals }),
/* 407 */     rolling((String)new NodeType[] { extend, replacements, additions, removals }),
/* 408 */     metal_former((String)new NodeType[] { cutting, extruding, rolling
/*     */       }),
/* 410 */     profile((String)new NodeType[] { name, textures, style, blocks, items, crafting, furnace, macerator, compressor, extractor, ore_washer, thermal_centrifuge, blast_furnace, block_cutter, metal_former });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private NodeType[] types;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Set<NodeType> validChildren;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 430 */     private static final Map<String, NodeType> MAP = (Map<String, NodeType>)Arrays.<NodeType>stream(values()).collect(Collectors.toMap(Enum::name, Function.identity())); NodeType(NodeType... types) { this.types = types; } public static NodeType get(String name) { return MAP.get(name); } static {
/*     */     
/*     */     }
/*     */   } public static abstract class Node {
/*     */     Node(ProfileParser.ParentNode parent) {
/* 435 */       this.parent = parent;
/*     */     }
/*     */     final ProfileParser.ParentNode parent;
/*     */     public abstract ProfileParser.NodeType getType();
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 441 */       throw new SAXException("Unexpected characters: " + content);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 446 */       return "Node<" + getType() + '>';
/*     */     } } public static abstract class ParentNode extends Node { final List<ProfileParser.Node> children; public void addNode(ProfileParser.Node node) throws SAXException {
/*     */       if (!(getType()).validChildren.contains(node.getType()))
/*     */         throw new SAXException("Invalid child: " + node); 
/*     */       this.children.add(node);
/*     */     } public Iterable<ProfileParser.Node> getNodes() {
/*     */       return this.children;
/*     */     } ParentNode(ParentNode parent) {
/* 454 */       super(parent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       this.children = new ArrayList<>();
/*     */     } public String toString() {
/*     */       return "ParentNode<" + getType() + ": " + this.children + '>';
/*     */     } }
/*     */   public static class ProfileNode extends ParentNode { ProfileNode() {
/* 476 */       super((ProfileParser.ParentNode)null);
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 481 */       return ProfileParser.NodeType.profile;
/*     */     } }
/*     */   
/*     */   public static class GenericParentNode extends ParentNode { private final ProfileParser.NodeType type;
/*     */     
/*     */     GenericParentNode(ProfileParser.ParentNode parent, ProfileParser.NodeType type) {
/* 487 */       super(parent);
/*     */       
/* 489 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 494 */       return this.type;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class NameNode
/*     */     extends Node {
/*     */     public String name;
/*     */     
/*     */     NameNode(ProfileParser.ParentNode parent) {
/* 503 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 508 */       return ProfileParser.NodeType.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 513 */       this.name = content;
/*     */     } }
/*     */   
/*     */   public static class TextureNode extends Node {
/*     */     private final String mod;
/*     */     public Function<ProfileTarget, TextureStyle> style;
/*     */     
/*     */     TextureNode(ProfileParser.ParentNode parent, Attributes attributes) {
/* 521 */       super(parent);
/*     */       
/* 523 */       this.mod = XmlUtil.getAttr(attributes, "mod", "ic2");
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 528 */       return ProfileParser.NodeType.textures;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 533 */       switch (content) {
/*     */         case "NEW":
/* 535 */           this.style = (root -> TextureStyle.EXPERIMENTAL);
/*     */           return;
/*     */         
/*     */         case "OLD":
/* 539 */           this.style = (root -> TextureStyle.CLASSIC);
/*     */           return;
/*     */       } 
/*     */       
/* 543 */       this.style = (root -> new TextureStyle(this.mod, root.offset(content)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StyleNode
/*     */     extends Node
/*     */   {
/*     */     public final Version style;
/*     */     
/*     */     StyleNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 553 */       super(parent);
/*     */       
/* 555 */       this.style = Version.valueOf(XmlUtil.getAttr(attributes, "type"));
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 560 */       return ProfileParser.NodeType.style;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StackNode extends Node {
/*     */     public final ItemStack stack;
/*     */     
/*     */     StackNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 568 */       super(parent);
/*     */       
/* 570 */       String combined = attributes.getValue("combined");
/* 571 */       if (combined != null) {
/*     */         try {
/* 573 */           this.stack = ConfigUtil.asStack(combined);
/* 574 */         } catch (ParseException e) {
/* 575 */           throw new SAXException("Invalid/Unknown stack requested: " + combined, e);
/*     */         } 
/*     */       } else {
/* 578 */         Item id = Util.getItem(XmlUtil.getAttr(attributes, "id"));
/* 579 */         if (id == null) throw new SAXException("Invalid/Unknown item requested: " + id);
/*     */         
/* 581 */         int meta = XmlUtil.getIntAttr(attributes, "meta", 32767);
/* 582 */         this.stack = new ItemStack(id, 1, meta);
/*     */         
/* 584 */         String nbt = attributes.getValue("nbt");
/* 585 */         if (nbt != null) {
/*     */           try {
/* 587 */             this.stack.func_77982_d(JsonToNBT.func_180713_a(nbt));
/* 588 */           } catch (NBTException e) {
/* 589 */             throw new SAXException("Invalid stack NBT: " + nbt, e);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 597 */       return ProfileParser.NodeType.stack;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OreDictionaryNode extends Node {
/*     */     public final String tag;
/*     */     
/*     */     OreDictionaryNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 605 */       super(parent);
/*     */       
/* 607 */       this.tag = XmlUtil.getAttr(attributes, "tag");
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 612 */       return ProfileParser.NodeType.ore_dict;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FileNode extends Node {
/*     */     public final String path;
/*     */     
/*     */     FileNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 620 */       super(parent);
/*     */       
/* 622 */       this.path = XmlUtil.getAttr(attributes, "path");
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 627 */       return ProfileParser.NodeType.file;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FolderNode extends Node {
/*     */     public final String path;
/*     */     
/*     */     FolderNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 635 */       super(parent);
/*     */       
/* 637 */       this.path = XmlUtil.getAttr(attributes, "path");
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 642 */       return ProfileParser.NodeType.folder;
/*     */     }
/*     */     
/*     */     Set<ProfileTarget> getFiles(ProfileTarget root) {
/* 646 */       ProfileTarget folder = root.offset(this.path);
/* 647 */       Set<ProfileTarget> files = new HashSet<>();
/*     */       
/* 649 */       if (!folder.isFile()) {
/* 650 */         for (File file : folder.asFile().listFiles((FilenameFilter)new WildcardFileFilter("*.INI", IOCase.INSENSITIVE))) {
/* 651 */           if (file.isFile()) {
/* 652 */             files.add(folder.offset(file.getName()));
/*     */           }
/*     */         } 
/*     */       } else {
/* 656 */         for (ZipEntry entry : Collections.<ZipEntry>list(folder.asZip().entries())) {
/* 657 */           if (!entry.isDirectory()) {
/* 658 */             String name = entry.getName();
/*     */             
/* 660 */             if (FilenameUtils.equals(this.path, FilenameUtils.getPathNoEndSeparator(name)) && FilenameUtils.isExtension(name, "ini")) {
/* 661 */               files.add(folder.offset(FilenameUtils.getName(name)));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 667 */       return files;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ExtensionNode extends Node {
/*     */     public final String profile;
/*     */     
/*     */     ExtensionNode(ProfileParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 675 */       super(parent);
/*     */       
/* 677 */       this.profile = XmlUtil.getAttr(attributes, "profile");
/*     */     }
/*     */ 
/*     */     
/*     */     public ProfileParser.NodeType getType() {
/* 682 */       return ProfileParser.NodeType.extend;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\ProfileParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */