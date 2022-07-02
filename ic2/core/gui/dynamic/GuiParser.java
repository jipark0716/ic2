/*     */ package ic2.core.gui.dynamic;
/*     */ 
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.SlotGrid;
/*     */ import ic2.core.gui.Text;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import ic2.core.util.XmlUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class GuiParser
/*     */ {
/*     */   public static GuiNode parse(ITeBlock teBlock) {
/*  53 */     ResourceLocation loc = new ResourceLocation(teBlock.getIdentifier().func_110624_b(), "guidef/" + teBlock.getName() + ".xml");
/*     */     
/*     */     try {
/*  56 */       return parse(loc, teBlock.getTeClass());
/*  57 */     } catch (Exception e) {
/*  58 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static GuiNode parse(ResourceLocation location, Class<?> baseClass) throws IOException, SAXException {
/*  64 */     String fileLoc = "/assets/" + location.func_110624_b() + '/' + location.func_110623_a();
/*  65 */     InputStream is = GuiParser.class.getResourceAsStream(fileLoc);
/*     */     
/*  67 */     if (is == null) throw new FileNotFoundException("Could not load " + fileLoc + " from the classpath.");
/*     */     
/*     */     try {
/*  70 */       is = new BufferedInputStream(is);
/*  71 */       return parse(is, baseClass);
/*     */     } finally {
/*  73 */       if (is != null) is.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static GuiNode parse(InputStream is, Class<?> baseClass) throws SAXException, IOException {
/*  78 */     is = new BufferedInputStream(is);
/*  79 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */     
/*     */     try {
/*  82 */       SAXParser parser = factory.newSAXParser();
/*  83 */       XMLReader reader = parser.getXMLReader();
/*  84 */       SaxHandler handler = new SaxHandler(baseClass);
/*  85 */       reader.setContentHandler(handler);
/*  86 */       reader.parse(new InputSource(is));
/*     */       
/*  88 */       return handler.getResult();
/*  89 */     } catch (ParserConfigurationException e) {
/*  90 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   private static class SaxHandler extends DefaultHandler { private final Class<?> baseClass; private GuiParser.ParentNode parentNode; private GuiParser.Node currentNode;
/*     */     
/*     */     public SaxHandler(Class<?> baseClass) {
/*  96 */       this.baseClass = baseClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 101 */       GuiParser.NodeType type = GuiParser.NodeType.get(qName);
/* 102 */       if (type == null) type = GuiParser.NodeType.get(qName.toLowerCase(Locale.ENGLISH)); 
/* 103 */       if (type == null) throw new SAXException("invalid element: " + qName);
/*     */       
/* 105 */       if (type == GuiParser.NodeType.gui) {
/* 106 */         if (this.parentNode != null) throw new SAXException("invalid gui element location"); 
/* 107 */       } else if (this.parentNode == null) {
/* 108 */         throw new SAXException("invalid " + qName + " element location");
/*     */       } 
/*     */       
/* 111 */       switch (type) {
/*     */         case gui:
/* 113 */           this.currentNode = this.parentNode = new GuiParser.GuiNode(attributes, this.baseClass);
/*     */           break;
/*     */         case environment:
/* 116 */           this.currentNode = new GuiParser.EnvironmentNode(this.parentNode, attributes);
/* 117 */           this.parentNode.addNode(this.currentNode);
/* 118 */           this.parentNode = (GuiParser.ParentNode)this.currentNode;
/*     */           break;
/*     */         case key:
/* 121 */           this.currentNode = GuiParser.KeyOnlyNode.getForKey(this.parentNode, attributes);
/* 122 */           this.parentNode.addNode(this.currentNode);
/* 123 */           this.parentNode = (GuiParser.ParentNode)this.currentNode;
/*     */           break;
/*     */         case only:
/* 126 */           this.currentNode = GuiParser.LogicalNode.getForValue(this.parentNode, attributes);
/* 127 */           this.parentNode.addNode(this.currentNode);
/* 128 */           this.parentNode = (GuiParser.ParentNode)this.currentNode;
/*     */           break;
/*     */         case tooltip:
/* 131 */           this.currentNode = new GuiParser.TooltipNode(this.parentNode, attributes.getValue("text"));
/* 132 */           this.parentNode.addNode(this.currentNode);
/* 133 */           this.parentNode = (GuiParser.ParentNode)this.currentNode;
/*     */           break;
/*     */         case button:
/* 136 */           this.currentNode = new GuiParser.ButtonNode(this.parentNode, attributes);
/* 137 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case energygauge:
/* 145 */           this.currentNode = new GuiParser.EnergyGaugeNode(this.parentNode, attributes);
/* 146 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case gauge:
/* 149 */           this.currentNode = new GuiParser.GaugeNode(this.parentNode, attributes);
/* 150 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case image:
/* 153 */           this.currentNode = new GuiParser.ImageNode(this.parentNode, attributes);
/* 154 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case playerinventory:
/* 157 */           this.currentNode = new GuiParser.PlayerInventoryNode(this.parentNode, attributes);
/* 158 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case slot:
/* 161 */           this.currentNode = new GuiParser.SlotNode(this.parentNode, attributes);
/* 162 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case slotgrid:
/* 165 */           this.currentNode = new GuiParser.SlotGridNode(this.parentNode, attributes);
/* 166 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case slothologram:
/* 169 */           this.currentNode = new GuiParser.SlotHologramNode(this.parentNode, attributes);
/* 170 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case text:
/* 173 */           this.currentNode = new GuiParser.TextNode(this.parentNode, attributes);
/* 174 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case fluidtank:
/* 177 */           this.currentNode = new GuiParser.FluidTankNode(this.parentNode, attributes);
/* 178 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */         case fluidslot:
/* 181 */           this.currentNode = new GuiParser.FluidSlotNode(this.parentNode, attributes);
/* 182 */           this.parentNode.addNode(this.currentNode);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void characters(char[] ch, int start, int length) throws SAXException {
/* 190 */       while (length > 0 && Character.isWhitespace(ch[start])) {
/* 191 */         start++;
/* 192 */         length--;
/*     */       } 
/*     */ 
/*     */       
/* 196 */       while (length > 0 && Character.isWhitespace(ch[start + length - 1])) {
/* 197 */         length--;
/*     */       }
/*     */       
/* 200 */       if (length != 0) {
/* 201 */         if (this.currentNode == null) throw new SAXException("unexpected characters");
/*     */         
/* 203 */         this.currentNode.setContent(new String(ch, start, length));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void endElement(String uri, String localName, String qName) throws SAXException {
/* 209 */       if (this.currentNode == this.parentNode) {
/* 210 */         if (this.currentNode.getType() == GuiParser.NodeType.gui) {
/*     */           
/* 212 */           this.currentNode = null;
/*     */         } else {
/* 214 */           this.currentNode = this.parentNode = this.parentNode.parent;
/*     */         } 
/*     */       } else {
/* 217 */         this.currentNode = this.parentNode;
/*     */       } 
/*     */     }
/*     */     
/*     */     public GuiParser.GuiNode getResult() {
/* 222 */       return (GuiParser.GuiNode)this.parentNode;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NodeType
/*     */   {
/* 231 */     gui,
/* 232 */     environment,
/* 233 */     key,
/* 234 */     only,
/* 235 */     tooltip,
/* 236 */     button,
/*     */     
/* 238 */     energygauge,
/* 239 */     gauge,
/* 240 */     image,
/* 241 */     playerinventory,
/* 242 */     slot,
/* 243 */     slotgrid,
/* 244 */     slothologram,
/* 245 */     text,
/* 246 */     fluidtank,
/* 247 */     fluidslot;
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
/* 264 */     private static Map<String, NodeType> map = getMap(); public static NodeType get(String name) { return map.get(name); } private static Map<String, NodeType> getMap() { NodeType[] values = values(); Map<String, NodeType> ret = new HashMap<>(values.length); for (NodeType type : values)
/*     */         ret.put(type.name(), type); 
/*     */       return ret; }
/*     */     static {  } }
/*     */   public static abstract class Node { final GuiParser.ParentNode parent;
/* 269 */     Node(GuiParser.ParentNode parent) { this.parent = parent; }
/*     */ 
/*     */     
/*     */     public abstract GuiParser.NodeType getType();
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 275 */       throw new SAXException("unexpected characters");
/*     */     } }
/*     */ 
/*     */   
/*     */   public static abstract class ParentNode extends Node
/*     */   {
/*     */     final List<GuiParser.Node> children;
/*     */     
/* 283 */     ParentNode(ParentNode parent) { super(parent);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 309 */       this.children = new ArrayList<>(); }
/*     */     public void addNode(GuiParser.Node node) { this.children.add(node); }
/*     */     public Iterable<GuiParser.Node> getNodes() { return this.children; } public Class<?> getBaseClass() { return this.parent.getBaseClass(); } void addElement(DynamicGui<?> gui, GuiElement<?> element) { Collection<IEnableHandler> handlers = addHandlers(gui, element, new ArrayList<>()); if (!handlers.isEmpty()) element.withEnableHandler(IEnableHandler.EnableHandlers.and(handlers.<IEnableHandler>toArray(new IEnableHandler[handlers.size()])));  gui.addElement(element); } protected Collection<IEnableHandler> addHandlers(DynamicGui<?> gui, GuiElement<?> element, Collection<IEnableHandler> handlers) { return handlers; }
/*     */   } public static class GuiNode extends ParentNode
/*     */   {
/* 314 */     private final Class<?> baseClass; GuiNode(Attributes attributes, Class<?> baseClass) throws SAXException { super(null);
/*     */       
/* 316 */       this.baseClass = baseClass;
/*     */       
/* 318 */       this.width = XmlUtil.getIntAttr(attributes, "width");
/* 319 */       this.height = XmlUtil.getIntAttr(attributes, "height"); }
/*     */     
/*     */     final int width; final int height;
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 324 */       return GuiParser.NodeType.gui;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getBaseClass() {
/* 329 */       return this.baseClass;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EnvironmentNode
/*     */     extends ParentNode {
/*     */     public final GuiEnvironment environment;
/*     */     
/*     */     EnvironmentNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 338 */       super(parent);
/*     */       
/* 340 */       String name = XmlUtil.getAttr(attributes, "name");
/* 341 */       this.environment = GuiEnvironment.get(name);
/* 342 */       if (this.environment == null) throw new SAXException("invalid environment name: " + name);
/*     */     
/*     */     }
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 347 */       return GuiParser.NodeType.environment;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class KeyOnlyNode extends ParentNode {
/*     */     protected final boolean inverted;
/*     */     
/*     */     static KeyOnlyNode getForKey(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 355 */       String key = XmlUtil.getAttr(attributes, "key");
/* 356 */       boolean inverted = (attributes.getValue("inverted") != null);
/*     */       
/* 358 */       if ("shift".equalsIgnoreCase(key))
/* 359 */         return new GuiParser.ShiftOnlyNode(parent, inverted); 
/* 360 */       if ("control".equalsIgnoreCase(key))
/* 361 */         return new GuiParser.ControlOnlyNode(parent, inverted); 
/* 362 */       if ("alt".equalsIgnoreCase(key)) {
/* 363 */         return new GuiParser.AltOnlyNode(parent, inverted);
/*     */       }
/* 365 */       throw new SAXException("Invalid/Unsupported key name: " + key);
/*     */     }
/*     */ 
/*     */     
/*     */     protected KeyOnlyNode(GuiParser.ParentNode parent, boolean inverted) {
/* 370 */       super(parent);
/*     */       
/* 372 */       this.inverted = inverted;
/*     */     }
/*     */ 
/*     */     
/*     */     public final GuiParser.NodeType getType() {
/* 377 */       return GuiParser.NodeType.key;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Collection<IEnableHandler> addHandlers(DynamicGui<?> gui, GuiElement<?> element, Collection<IEnableHandler> handlers) {
/* 382 */       handlers.add(this.inverted ? IEnableHandler.EnableHandlers.not(this::isKeyDown) : this::isKeyDown);
/*     */       
/* 384 */       return super.addHandlers(gui, element, handlers);
/*     */     }
/*     */     
/*     */     protected abstract boolean isKeyDown();
/*     */   }
/*     */   
/*     */   static class ShiftOnlyNode
/*     */     extends KeyOnlyNode
/*     */   {
/*     */     ShiftOnlyNode(GuiParser.ParentNode parent, boolean inverted) {
/* 394 */       super(parent, inverted);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isKeyDown() {
/* 399 */       return GuiScreen.func_146272_n();
/*     */     }
/*     */   }
/*     */   
/*     */   static class ControlOnlyNode extends KeyOnlyNode {
/*     */     ControlOnlyNode(GuiParser.ParentNode parent, boolean inverted) {
/* 405 */       super(parent, inverted);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isKeyDown() {
/* 410 */       return GuiScreen.func_146271_m();
/*     */     }
/*     */   }
/*     */   
/*     */   static class AltOnlyNode extends KeyOnlyNode {
/*     */     AltOnlyNode(GuiParser.ParentNode parent, boolean inverted) {
/* 416 */       super(parent, inverted);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isKeyDown() {
/* 421 */       return GuiScreen.func_175283_s();
/*     */     } }
/*     */   public static class LogicalNode extends ParentNode { final String condition;
/*     */     protected final boolean inverted;
/*     */     
/*     */     static LogicalNode getForValue(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 427 */       return new LogicalNode(parent, XmlUtil.getAttr(attributes, "if"), (attributes.getValue("inverted") != null));
/*     */     }
/*     */     
/*     */     protected LogicalNode(GuiParser.ParentNode parent, String condition, boolean inverted) {
/* 431 */       super(parent);
/*     */       
/* 433 */       this.condition = condition;
/* 434 */       this.inverted = inverted;
/*     */     }
/*     */ 
/*     */     
/*     */     public final GuiParser.NodeType getType() {
/* 439 */       return GuiParser.NodeType.only;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Collection<IEnableHandler> addHandlers(DynamicGui<?> gui, GuiElement<?> element, Collection<IEnableHandler> handlers) {
/* 444 */       if (!((gui.getContainer()).base instanceof IGuiConditionProvider)) {
/* 445 */         throw new RuntimeException("Invalid base " + (gui.getContainer()).base + " for conditional elements");
/*     */       }
/*     */       
/* 448 */       IEnableHandler handler = () -> ((IGuiConditionProvider)(gui.getContainer()).base).getGuiState(this.condition);
/* 449 */       handlers.add(this.inverted ? IEnableHandler.EnableHandlers.not(handler) : handler);
/*     */       
/* 451 */       return super.addHandlers(gui, element, handlers);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class TooltipNode
/*     */     extends ParentNode {
/*     */     final String tooltip;
/*     */     
/*     */     public TooltipNode(GuiParser.ParentNode parent, String tooltip) {
/* 460 */       super(parent);
/*     */       
/* 462 */       this.tooltip = tooltip;
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 467 */       return GuiParser.NodeType.tooltip;
/*     */     }
/*     */ 
/*     */     
/*     */     void addElement(DynamicGui<?> gui, GuiElement<?> element) {
/* 472 */       this.parent.addElement(gui, element.withTooltip(this.tooltip));
/*     */     } }
/*     */   public static class ButtonNode extends Node { final int x; final int y; final int width; final int height; final int eventID;
/*     */     final String eventName;
/*     */     final ItemStack icon;
/*     */     TextProvider.ITextProvider text;
/*     */     final ButtonType type;
/*     */     
/*     */     ButtonNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 481 */       super(parent);
/*     */       
/* 483 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 484 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/*     */       
/* 486 */       this.width = XmlUtil.getIntAttr(attributes, "width", 16);
/* 487 */       this.height = XmlUtil.getIntAttr(attributes, "height", 16);
/*     */       
/* 489 */       Tuple.T2<Integer, String> event = getEventID(attributes);
/* 490 */       if (event.a == null) {
/* 491 */         this.eventID = 0;
/* 492 */         this.eventName = (String)event.b;
/*     */       } else {
/* 494 */         this.eventID = ((Integer)event.a).intValue();
/* 495 */         this.eventName = null;
/*     */       } 
/*     */       
/* 498 */       String typeName = XmlUtil.getAttr(attributes, "type", "vanilla");
/* 499 */       this.type = ButtonType.get(typeName);
/* 500 */       if (this.type == null) throw new SAXException("Invalid button type: " + typeName);
/*     */       
/* 502 */       String icon = XmlUtil.getAttr(attributes, "icon", "none");
/* 503 */       if ("none".equals(icon)) {
/* 504 */         this.icon = null;
/*     */       } else {
/*     */         try {
/* 507 */           this.icon = ConfigUtil.asStack(icon);
/* 508 */         } catch (ParseException e) {
/* 509 */           throw new SAXException("Invalid/Unknown icon requested: " + icon, e);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected Tuple.T2<Integer, String> getEventID(Attributes attributes) throws SAXException {
/*     */       Integer ID;
/*     */       String name;
/*     */       try {
/* 519 */         ID = Integer.valueOf(XmlUtil.getIntAttr(attributes, "event"));
/* 520 */         name = null;
/* 521 */       } catch (NumberFormatException e) {
/* 522 */         ID = null;
/* 523 */         name = XmlUtil.getAttr(attributes, "event");
/*     */       } 
/*     */       
/* 526 */       return new Tuple.T2(ID, name);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 531 */       return GuiParser.NodeType.button;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 536 */       if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) Mouse.setGrabbed(false); 
/* 537 */       this.text = TextProvider.parse(content, this.parent.getBaseClass());
/*     */     }
/*     */     
/*     */     public enum ButtonType {
/* 541 */       VANILLA, CUSTOM, TRANSPARENT, RECIPE;
/*     */       
/*     */       public static ButtonType get(String name) {
/* 544 */         for (ButtonType button : values()) {
/* 545 */           if (button.name().equalsIgnoreCase(name)) {
/* 546 */             return button;
/*     */           }
/*     */         } 
/* 549 */         return null;
/*     */       }
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EnergyGaugeNode
/*     */     extends Node
/*     */   {
/*     */     public final int x;
/*     */ 
/*     */ 
/*     */     
/*     */     public final int y;
/*     */ 
/*     */ 
/*     */     
/*     */     public final EnergyGauge.EnergyGaugeStyle style;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EnergyGaugeNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 575 */       super(parent);
/*     */       
/* 577 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 578 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/*     */       
/* 580 */       String styleName = XmlUtil.getAttr(attributes, "style", "bolt");
/* 581 */       this.style = EnergyGauge.EnergyGaugeStyle.get(styleName);
/* 582 */       if (this.style == null) throw new SAXException("invalid gauge style: " + styleName);
/*     */     
/*     */     }
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 587 */       return GuiParser.NodeType.energygauge;
/*     */     } }
/*     */   public static class GaugeNode extends Node { public final int x;
/*     */     public final int y;
/*     */     public final String name;
/*     */     public final Gauge.IGaugeStyle style;
/*     */     public final boolean activeLinked;
/*     */     
/*     */     GaugeNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 596 */       super(parent);
/*     */       
/* 598 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 599 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 600 */       this.name = XmlUtil.getAttr(attributes, "name");
/*     */       
/* 602 */       String styleName = XmlUtil.getAttr(attributes, "style", "normal");
/* 603 */       this.style = Gauge.GaugeStyle.get(styleName);
/* 604 */       if (this.style == null) throw new SAXException("invalid gauge style: " + styleName);
/*     */       
/* 606 */       this.activeLinked = XmlUtil.getBoolAttr(attributes, "active", false);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 611 */       return GuiParser.NodeType.gauge;
/*     */     } }
/*     */   public static class ImageNode extends Node { public final int x; public final int y; public final int width; public final int height; public final int baseWidth;
/*     */     public final int baseHeight;
/*     */     public final int u1;
/*     */     public final int v1;
/*     */     public final int u2;
/*     */     public final int v2;
/*     */     public final ResourceLocation src;
/*     */     
/*     */     ImageNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 622 */       super(parent);
/*     */       String domain, file;
/* 624 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 625 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 626 */       this.width = XmlUtil.getIntAttr(attributes, "width", -1);
/* 627 */       this.height = XmlUtil.getIntAttr(attributes, "height", -1);
/*     */       
/* 629 */       this.baseWidth = XmlUtil.getIntAttr(attributes, "basewidth", "basesize", 0);
/* 630 */       this.baseHeight = XmlUtil.getIntAttr(attributes, "baseheight", "basesize", 0);
/*     */       
/* 632 */       this.u1 = XmlUtil.getIntAttr(attributes, "u", "u1", 0);
/* 633 */       this.v1 = XmlUtil.getIntAttr(attributes, "v", "v1", 0);
/* 634 */       this.u2 = XmlUtil.getIntAttr(attributes, "u2", -1);
/* 635 */       this.v2 = XmlUtil.getIntAttr(attributes, "v2", -1);
/*     */       
/* 637 */       String resLoc = XmlUtil.getAttr(attributes, "src");
/* 638 */       if (resLoc.isEmpty()) throw new SAXException("empty src");
/*     */       
/* 640 */       int pos = resLoc.indexOf(':');
/*     */ 
/*     */ 
/*     */       
/* 644 */       if (pos == -1) {
/* 645 */         domain = "ic2";
/* 646 */         file = resLoc;
/*     */       } else {
/* 648 */         domain = resLoc.substring(0, pos);
/* 649 */         file = resLoc.substring(pos + 1);
/*     */       } 
/*     */       
/* 652 */       if (!file.endsWith(".png")) file = file + ".png";
/*     */       
/* 654 */       this.src = new ResourceLocation(domain, file);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 659 */       return GuiParser.NodeType.image;
/*     */     } }
/*     */   
/*     */   protected static class PlayerInventoryNode extends Node { final int x;
/*     */     final int y;
/*     */     final int spacing;
/*     */     final int hotbarOffset;
/*     */     final boolean showTitle;
/*     */     final SlotGrid.SlotStyle style;
/*     */     
/*     */     PlayerInventoryNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 670 */       super(parent);
/*     */       
/* 672 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 673 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 674 */       this.spacing = XmlUtil.getIntAttr(attributes, "spacing", 0);
/* 675 */       this.hotbarOffset = XmlUtil.getIntAttr(attributes, "hotbaroffset", 58);
/*     */       
/* 677 */       String styleName = XmlUtil.getAttr(attributes, "style", "normal");
/* 678 */       this.style = SlotGrid.SlotStyle.get(styleName);
/* 679 */       if (this.style == null) throw new SAXException("Invalid inventory slot style: " + styleName);
/*     */       
/* 681 */       this.showTitle = XmlUtil.getBoolAttr(attributes, "title", true);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 686 */       return GuiParser.NodeType.playerinventory;
/*     */     } }
/*     */   
/*     */   public static class SlotNode extends Node { public final int x;
/*     */     public final int y;
/*     */     public final String name;
/*     */     public final int index;
/*     */     public final SlotGrid.SlotStyle style;
/*     */     
/*     */     SlotNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 696 */       super(parent);
/*     */       
/* 698 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 699 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 700 */       this.name = XmlUtil.getAttr(attributes, "name");
/* 701 */       this.index = XmlUtil.getIntAttr(attributes, "index", 0);
/*     */       
/* 703 */       String styleName = XmlUtil.getAttr(attributes, "style", "normal");
/* 704 */       this.style = SlotGrid.SlotStyle.get(styleName);
/* 705 */       if (this.style == null) throw new SAXException("invalid slot style: " + styleName);
/*     */     
/*     */     }
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 710 */       return GuiParser.NodeType.slot;
/*     */     } }
/*     */   public static class SlotGridNode extends Node { public final int x; public final int y; public final String name;
/*     */     public final int spacing;
/*     */     public final int offset;
/*     */     public final int rows;
/*     */     public final int cols;
/*     */     public final boolean vertical;
/*     */     public final SlotGrid.SlotStyle style;
/*     */     
/*     */     SlotGridNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 721 */       super(parent);
/*     */       
/* 723 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 724 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 725 */       this.name = XmlUtil.getAttr(attributes, "name");
/*     */       
/* 727 */       this.spacing = XmlUtil.getIntAttr(attributes, "spacing", 0);
/* 728 */       this.offset = XmlUtil.getIntAttr(attributes, "offset", 0);
/*     */       
/* 730 */       this.rows = XmlUtil.getIntAttr(attributes, "rows", -1);
/* 731 */       this.cols = XmlUtil.getIntAttr(attributes, "cols", -1);
/* 732 */       this.vertical = XmlUtil.getBoolAttr(attributes, "vertical", false);
/*     */       
/* 734 */       String styleName = XmlUtil.getAttr(attributes, "style", "normal");
/* 735 */       this.style = SlotGrid.SlotStyle.get(styleName);
/* 736 */       if (this.style == null) throw new SAXException("invalid slot style: " + styleName);
/*     */     
/*     */     }
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 741 */       return GuiParser.NodeType.slotgrid;
/*     */     }
/*     */     
/*     */     public SlotGridDimension getDimension(int totalSize) {
/* 745 */       totalSize -= this.offset;
/*     */       
/* 747 */       if (!this.vertical) {
/* 748 */         if (this.cols > 0)
/* 749 */           return new SlotGridDimension(Math.max(this.rows, (totalSize + this.cols - 1) / this.cols), this.cols); 
/* 750 */         if (this.rows > 0) {
/* 751 */           return new SlotGridDimension(this.rows, (totalSize + this.rows - 1) / this.rows);
/*     */         }
/* 753 */         int cols = (int)Math.floor(Math.sqrt(totalSize));
/*     */         
/* 755 */         return new SlotGridDimension((totalSize + cols - 1) / cols, cols);
/*     */       } 
/*     */       
/* 758 */       if (this.rows > 0)
/* 759 */         return new SlotGridDimension(this.rows, Math.max(this.cols, (totalSize + this.rows - 1) / this.rows)); 
/* 760 */       if (this.cols > 0) {
/* 761 */         return new SlotGridDimension((totalSize + this.cols - 1) / this.cols, this.cols);
/*     */       }
/* 763 */       int rows = (int)Math.floor(Math.sqrt(totalSize));
/*     */       
/* 765 */       return new SlotGridDimension(rows, (totalSize + rows - 1) / rows);
/*     */     }
/*     */     
/*     */     public static class SlotGridDimension { public final int rows;
/*     */       public final int cols;
/*     */       
/*     */       public SlotGridDimension(int rows, int cols) {
/* 772 */         this.rows = rows;
/* 773 */         this.cols = cols;
/*     */       } }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SlotHologramNode
/*     */     extends SlotNode
/*     */   {
/*     */     public final int index;
/*     */ 
/*     */     
/*     */     public final int stackSizeLimit;
/*     */ 
/*     */     
/*     */     SlotHologramNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 790 */       super(parent, attributes);
/*     */       
/* 792 */       this.index = XmlUtil.getIntAttr(attributes, "index", 0);
/* 793 */       this.stackSizeLimit = XmlUtil.getIntAttr(attributes, "stack", 64);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 798 */       return GuiParser.NodeType.slothologram;
/*     */     } }
/*     */   public static class TextNode extends Node { private static final int defaultColor = 4210752; public final int x; public final int y; public final int width; public final int height; public final int xOffset; public final int yOffset; public final boolean centerX; public final boolean centerY; public final boolean rightAligned; public final Text.TextAlignment align;
/*     */     public final int color;
/*     */     public final boolean shadow;
/*     */     public TextProvider.ITextProvider text;
/*     */     
/*     */     TextNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 806 */       super(parent);
/*     */       
/* 808 */       this.x = XmlUtil.getIntAttr(attributes, "x", 0);
/* 809 */       this.y = XmlUtil.getIntAttr(attributes, "y", 0);
/* 810 */       this.width = XmlUtil.getIntAttr(attributes, "width", -1);
/* 811 */       this.height = XmlUtil.getIntAttr(attributes, "height", -1);
/* 812 */       this.xOffset = XmlUtil.getIntAttr(attributes, "xoffset", 0);
/* 813 */       this.yOffset = XmlUtil.getIntAttr(attributes, "yoffset", 0);
/*     */       
/* 815 */       String alignName = XmlUtil.getAttr(attributes, "align", "start");
/* 816 */       this.align = Text.TextAlignment.get(alignName);
/* 817 */       if (this.align == null) throw new SAXException("invalid alignment: " + alignName);
/*     */       
/* 819 */       String center = XmlUtil.getAttr(attributes, "center", (this.align == Text.TextAlignment.Center) ? "x" : "");
/* 820 */       this.centerX = (center.indexOf('x') != -1);
/* 821 */       this.centerY = (center.indexOf('y') != -1);
/* 822 */       this.rightAligned = XmlUtil.getBoolAttr(attributes, "right", false);
/*     */       
/* 824 */       this.color = XmlUtil.getIntAttr(attributes, "color", 4210752);
/* 825 */       this.shadow = (attributes.getIndex("shadow") != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 830 */       return GuiParser.NodeType.text;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContent(String content) throws SAXException {
/* 835 */       if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) Mouse.setGrabbed(false); 
/* 836 */       this.text = TextProvider.parse(content, this.parent.getBaseClass());
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FluidTankNode
/*     */     extends Node
/*     */   {
/*     */     final int x;
/*     */     final int y;
/*     */     final String name;
/*     */     final int width;
/*     */     final int height;
/*     */     final boolean mirrored;
/*     */     final TankType type;
/*     */     
/*     */     FluidTankNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 853 */       super(parent);
/*     */       
/* 855 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 856 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 857 */       this.name = XmlUtil.getAttr(attributes, "name");
/*     */       
/* 859 */       String typeName = XmlUtil.getAttr(attributes, "type", "normal");
/* 860 */       this.type = TankType.get(typeName);
/* 861 */       if (this.type == null) throw new SAXException("Invalid type: " + typeName);
/*     */       
/* 863 */       if (this.type == TankType.PLAIN) {
/* 864 */         this.width = XmlUtil.getIntAttr(attributes, "width");
/* 865 */         this.height = XmlUtil.getIntAttr(attributes, "height");
/*     */       } else {
/* 867 */         this.width = this.height = -1;
/*     */       } 
/*     */       
/* 870 */       if (this.type == TankType.BORDERLESS) {
/* 871 */         this.mirrored = XmlUtil.getBoolAttr(attributes, "mirrored");
/*     */       } else {
/* 873 */         this.mirrored = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 879 */       return GuiParser.NodeType.fluidtank;
/*     */     }
/*     */     
/*     */     public enum TankType {
/* 883 */       NORMAL, PLAIN, BORDERLESS;
/*     */       
/*     */       public static TankType get(String name) {
/* 886 */         for (TankType type : values()) {
/* 887 */           if (type.name().equalsIgnoreCase(name)) {
/* 888 */             return type;
/*     */           }
/*     */         } 
/* 891 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FluidSlotNode
/*     */     extends Node
/*     */   {
/*     */     final int x;
/*     */     final int y;
/*     */     final String name;
/*     */     
/*     */     FluidSlotNode(GuiParser.ParentNode parent, Attributes attributes) throws SAXException {
/* 904 */       super(parent);
/* 905 */       this.x = XmlUtil.getIntAttr(attributes, "x");
/* 906 */       this.y = XmlUtil.getIntAttr(attributes, "y");
/* 907 */       this.name = XmlUtil.getAttr(attributes, "name");
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiParser.NodeType getType() {
/* 912 */       return GuiParser.NodeType.fluidslot;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\GuiParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */