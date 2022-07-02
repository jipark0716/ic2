/*     */ package ic2.core.command;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IWorldTickCallback;
/*     */ import ic2.core.energy.grid.EnergyNetGlobal;
/*     */ import ic2.core.energy.grid.EnergyNetLocal;
/*     */ import ic2.core.energy.grid.EnergyNetSettings;
/*     */ import ic2.core.energy.grid.GridInfo;
/*     */ import ic2.core.item.ItemCropSeed;
/*     */ import ic2.core.ref.IMultiBlock;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.uu.DropScan;
/*     */ import ic2.core.uu.UuGraph;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandIc2
/*     */   extends CommandBase
/*     */ {
/*     */   public String func_71517_b() {
/*  80 */     return "ic2";
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_71518_a(ICommandSender icommandsender) {
/*  85 */     return "/ic2 uu-world-scan <tiny|small|medium|large> | debug (dumpUuValues | resolveIngredient <name> | dumpTextures <name> <size> | dumpLargeGrids | enet (logIssues | logUpdates) (true|false)) | currentItem | itemNameWithVariant | giveCrop <owner> <name> <growth (1-31)> <gain (1-31)> <resistance (1-31)>";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
/*  95 */     if (args.length == 1)
/*  96 */       return func_71530_a(args, new String[] { "uu-world-scan", "debug", "currentItem", "itemNameWithVariant", "giveCrop" }); 
/*  97 */     if (args.length == 2 && args[0].equals("uu-world-scan"))
/*  98 */       return func_71530_a(args, new String[] { "tiny", "small", "medium", "large" }); 
/*  99 */     if (args.length >= 2 && args[0].equals("debug"))
/* 100 */       return getDebugTabCompletionOptions(server, sender, args, pos); 
/* 101 */     if (args.length == 6 && args[0].equals("giveCrop")) {
/* 102 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 105 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private List<String> getDebugTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
/* 109 */     if (args.length == 2)
/* 110 */       return func_71530_a(args, new String[] { "dumpUuValues", "resolveIngredient", "dumpTextures", "dumpLargeGrids", "enet" }); 
/* 111 */     if (args.length == 3 && args[1].equals("resolveIngredient")) {
/* 112 */       List<String> possibilities = new ArrayList<>(1024);
/*     */       
/* 114 */       for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
/* 115 */         possibilities.add(loc.toString());
/*     */       }
/*     */       
/* 118 */       for (String name : OreDictionary.getOreNames()) {
/* 119 */         possibilities.add("OreDict:" + name);
/*     */       }
/*     */       
/* 122 */       for (String name : FluidRegistry.getRegisteredFluids().keySet()) {
/* 123 */         possibilities.add("Fluid:" + name);
/*     */       }
/*     */       
/* 126 */       return func_175762_a(args, possibilities);
/* 127 */     }  if (args.length >= 3 && "dumpTextures".equals(args[1])) {
/* 128 */       if (args.length == 3) {
/* 129 */         List<String> possibilities = new ArrayList<>(1024);
/*     */         
/* 131 */         for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
/* 132 */           possibilities.add(loc.toString());
/*     */         }
/*     */         
/* 135 */         return func_175762_a(args, possibilities);
/* 136 */       }  if (args.length == 4) {
/* 137 */         List<String> possibilities = new ArrayList<>();
/*     */         
/*     */         short num;
/* 140 */         for (num = 512; num > 8; num = (short)(num >> 1)) {
/* 141 */           possibilities.add(Integer.toString(num));
/*     */         }
/*     */         
/* 144 */         return func_175762_a(args, possibilities);
/*     */       } 
/* 146 */     } else if (args.length >= 3 && "enet".equals(args[1])) {
/* 147 */       if (args.length == 3) {
/* 148 */         List<String> possibilities = new ArrayList<>(1024);
/*     */         
/* 150 */         for (ResourceLocation loc : Item.field_150901_e.func_148742_b()) {
/* 151 */           possibilities.add(loc.toString());
/*     */         }
/*     */         
/* 154 */         return func_71530_a(args, new String[] { "logIssues", "logUpdates" });
/* 155 */       }  if (args.length == 4) {
/* 156 */         return func_71530_a(args, new String[] { "true", "false" });
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 165 */     if (args.length == 0) {
/* 166 */       throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */     }
/*     */     
/* 169 */     if (args.length == 2 && args[0].equals("uu-world-scan")) {
/* 170 */       cmdUuWorldScan(sender, args[1]);
/* 171 */     } else if (args[0].equals("debug")) {
/* 172 */       if (args.length == 2 && args[1].equals("dumpUuValues")) {
/* 173 */         cmdDumpUuValues(sender);
/* 174 */       } else if (args.length == 3 && args[1].equals("resolveIngredient")) {
/* 175 */         cmdDebugResolveIngredient(sender, args[2]);
/* 176 */       } else if (args.length == 4 && args[1].equals("dumpTextures")) {
/* 177 */         cmdDebugDumpTextures(sender, args[2], args[3]);
/* 178 */       } else if (args.length == 2 && args[1].equals("dumpLargeGrids")) {
/* 179 */         dumpLargeGrids(sender);
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
/*     */       }
/* 201 */       else if (args.length == 4 && args[1].equals("enet")) {
/* 202 */         cmdDebugEnet(sender, args[2], func_180527_d(args[3]));
/*     */       } else {
/* 204 */         throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */       } 
/* 206 */     } else if (args.length == 1 && args[0].equals("currentItem")) {
/* 207 */       cmdCurrentItem(sender);
/* 208 */     } else if (args.length == 1 && args[0].equals("itemNameWithVariant") && sender instanceof EntityPlayer) {
/* 209 */       EntityPlayer player = (EntityPlayer)sender;
/* 210 */       ItemStack stack = player.field_71071_by.func_70448_g();
/*     */       
/* 212 */       if (StackUtil.isEmpty(stack)) {
/* 213 */         msg(sender, "empty: " + StackUtil.toStringSafe(stack));
/* 214 */       } else if (!stack.func_77973_b().getClass().getCanonicalName().startsWith("ic2.core")) {
/* 215 */         msg(sender, "Not an IC2 Item.");
/*     */       } else {
/* 217 */         String name = Util.getName(stack.func_77973_b()).func_110623_a();
/* 218 */         String variant = null;
/*     */         
/* 220 */         if (stack.func_77973_b() instanceof IMultiItem) {
/* 221 */           variant = ((IMultiItem)stack.func_77973_b()).getVariant(stack);
/* 222 */         } else if (stack.func_77973_b() instanceof ItemBlock && ((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof IMultiBlock) {
/* 223 */           variant = ((IMultiBlock)((ItemBlock)stack.func_77973_b()).func_179223_d()).getVariant(stack);
/*     */         } 
/*     */         
/* 226 */         msg(sender, "Name: " + name + ((variant == null) ? "" : (" Variant: " + variant)));
/*     */       }
/*     */     
/* 229 */     } else if (args.length == 6 && args[0].equals("giveCrop") && sender instanceof EntityPlayer) {
/* 230 */       cmdGiveCrop(sender, args);
/*     */     } else {
/* 232 */       msg(sender, "Unknown Command.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cmdUuWorldScan(ICommandSender sender, String arg) throws CommandException {
/*     */     int areaCount;
/* 239 */     if (arg.equals("tiny")) {
/* 240 */       areaCount = 128;
/* 241 */     } else if (arg.equals("small")) {
/* 242 */       areaCount = 1024;
/* 243 */     } else if (arg.equals("medium")) {
/* 244 */       areaCount = 2048;
/* 245 */     } else if (arg.equals("large")) {
/* 246 */       areaCount = 4096;
/*     */     } else {
/* 248 */       throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */     } 
/*     */     
/* 251 */     float time = areaCount * 0.0032F;
/*     */     
/* 253 */     msg(sender, String.format("Starting world scan, this will take about %.1f minutes with a powerful cpu.", new Object[] { Float.valueOf(time) }));
/* 254 */     msg(sender, "The server will not respond while the calculations are running.");
/*     */     
/* 256 */     WorldServer world = null;
/*     */     
/* 258 */     if (sender instanceof EntityPlayerMP) {
/* 259 */       world = ((EntityPlayerMP)sender).func_71121_q();
/*     */     } else {
/* 261 */       world = DimensionManager.getWorld(0);
/*     */     } 
/*     */     
/* 264 */     if (world == null) {
/* 265 */       msg(sender, "Can't determine the world to scan.");
/*     */       
/*     */       return;
/*     */     } 
/* 269 */     int area = 50000;
/* 270 */     int range = 5;
/*     */     
/* 272 */     DropScan scan = new DropScan(world, range);
/* 273 */     scan.start(area, areaCount);
/* 274 */     scan.cleanup();
/*     */   }
/*     */   
/*     */   private void cmdDumpUuValues(ICommandSender sender) {
/* 278 */     List<Map.Entry<ItemStack, Double>> list = new ArrayList<>();
/*     */     
/* 280 */     Iterator<Map.Entry<ItemStack, Double>> it = UuGraph.iterator();
/* 281 */     while (it.hasNext()) {
/* 282 */       list.add(it.next());
/*     */     }
/*     */     
/* 285 */     Collections.sort(list, new Comparator<Map.Entry<ItemStack, Double>>()
/*     */         {
/*     */           public int compare(Map.Entry<ItemStack, Double> a, Map.Entry<ItemStack, Double> b)
/*     */           {
/* 289 */             return ((ItemStack)a.getKey())
/* 290 */               .func_77973_b()
/* 291 */               .func_77653_i(a.getKey())
/* 292 */               .compareTo(((ItemStack)b
/* 293 */                 .getKey()).func_77973_b()
/* 294 */                 .func_77653_i(b.getKey()));
/*     */           }
/*     */         });
/*     */     
/* 298 */     msg(sender, "UU Values:");
/*     */     
/* 300 */     for (it = list.iterator(); it.hasNext(); ) { Map.Entry<ItemStack, Double> entry = it.next();
/* 301 */       msg(sender, String.format("  %s: %s", new Object[] { ((ItemStack)entry
/* 302 */               .getKey()).func_77973_b()
/* 303 */               .func_77653_i(entry.getKey()), entry
/* 304 */               .getValue() })); }
/*     */ 
/*     */     
/* 307 */     msg(sender, "(check console for full list)");
/*     */   }
/*     */   
/*     */   private void cmdDebugResolveIngredient(ICommandSender sender, String arg) {
/*     */     try {
/* 312 */       IRecipeInput input = ConfigUtil.asRecipeInput(arg);
/*     */       
/* 314 */       if (input == null) {
/* 315 */         msg(sender, "No match");
/*     */       } else {
/* 317 */         List<ItemStack> inputs = input.getInputs();
/*     */         
/* 319 */         msg(sender, inputs.size() + " matches:");
/*     */         
/* 321 */         for (ItemStack stack : inputs) {
/* 322 */           if (stack == null) {
/* 323 */             msg(sender, " null"); continue;
/*     */           } 
/* 325 */           msg(sender, 
/* 326 */               String.format(" %s (%s, od: %s, name: %s / %s)", new Object[] {
/* 327 */                   StackUtil.toStringSafe(stack), 
/* 328 */                   Util.getName(stack.func_77973_b()), 
/* 329 */                   getOreDictNames(stack), stack
/* 330 */                   .func_77977_a(), stack
/* 331 */                   .func_82833_r()
/*     */                 }));
/*     */         } 
/*     */       } 
/* 335 */     } catch (Exception e) {
/* 336 */       msg(sender, "Error: " + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getOreDictNames(ItemStack stack) {
/* 344 */     String ret = "";
/*     */     
/* 346 */     for (int oreId : OreDictionary.getOreIDs(stack)) {
/* 347 */       if (!ret.isEmpty()) {
/* 348 */         ret = ret + ", ";
/*     */       }
/* 350 */       ret = ret + OreDictionary.getOreName(oreId);
/*     */     } 
/*     */     
/* 353 */     return ret.isEmpty() ? "<none>" : ret;
/*     */   }
/*     */   
/*     */   private void cmdDebugDumpTextures(ICommandSender sender, String name, String size) {
/* 357 */     if (FMLCommonHandler.instance().getSide().isServer()) {
/* 358 */       msg(sender, "Can't dump textures on the dedicated server.");
/*     */       return;
/*     */     } 
/* 361 */     msg(sender, "Dumping requested textures to sprites texture...");
/*     */ 
/*     */     
/* 364 */     Integer meta = null;
/* 365 */     int pos = name.indexOf('@');
/*     */     
/* 367 */     if (pos != -1) {
/* 368 */       meta = Integer.valueOf(name.substring(pos + 1));
/* 369 */       name = name.substring(0, pos);
/*     */     } 
/*     */     
/* 372 */     String regex = '^' + Pattern.quote(name).replace("*", "\\E.*\\Q") + '$';
/* 373 */     Pattern pattern = Pattern.compile(regex);
/*     */     
/* 375 */     IC2.tickHandler.requestSingleWorldTick(IC2.platform.getPlayerWorld(), new TextureDumper(pattern, 
/* 376 */           Integer.valueOf(size).intValue(), meta));
/*     */   }
/*     */   
/*     */   private void dumpLargeGrids(ICommandSender sender) {
/* 380 */     List<GridInfo> allGrids = new ArrayList<>();
/*     */     
/* 382 */     for (WorldServer worldServer : DimensionManager.getWorlds()) {
/* 383 */       EnergyNetLocal energyNet = EnergyNetGlobal.getLocal((World)worldServer);
/*     */       
/* 385 */       allGrids.addAll(energyNet.getGridInfos());
/*     */     } 
/*     */     
/* 388 */     Collections.sort(allGrids, new Comparator<GridInfo>()
/*     */         {
/*     */           public int compare(GridInfo a, GridInfo b) {
/* 391 */             return b.complexNodeCount - a.complexNodeCount;
/*     */           }
/*     */         });
/*     */     
/* 395 */     msg(sender, "found " + allGrids.size() + " grids overall");
/*     */     
/* 397 */     for (int i = 0; i < 8 && i < allGrids.size(); i++) {
/* 398 */       GridInfo grid = allGrids.get(i);
/*     */       
/* 400 */       if (grid.nodeCount == 0) {
/* 401 */         msg(sender, "grid " + grid.id + " is empty");
/*     */       } else {
/* 403 */         msg(sender, String.format("%d complex / %d total nodes in grid %d (%d/%d/%d - %d/%d/%d)", new Object[] {
/*     */                 
/* 405 */                 Integer.valueOf(grid.complexNodeCount), Integer.valueOf(grid.nodeCount), Integer.valueOf(grid.id), 
/* 406 */                 Integer.valueOf(grid.minX), Integer.valueOf(grid.minY), Integer.valueOf(grid.minZ), Integer.valueOf(grid.maxX), 
/* 407 */                 Integer.valueOf(grid.maxY), Integer.valueOf(grid.maxZ) }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cmdDebugEnet(ICommandSender sender, String option, boolean value) throws CommandException {
/* 413 */     if ("logIssues".equals(option)) {
/* 414 */       msg(sender, "setting logGridUpdateIssues to " + value);
/*     */       
/* 416 */       EnergyNetSettings.logGridUpdateIssues = value;
/* 417 */     } else if ("logUpdates".equals(option)) {
/* 418 */       msg(sender, "setting logGridUpdatesVerbose to " + value);
/*     */       
/* 420 */       EnergyNetSettings.logGridUpdatesVerbose = value;
/*     */     } else {
/* 422 */       throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void msg(ICommandSender sender, String text) {
/* 428 */     sender.func_145747_a((ITextComponent)new TextComponentString(text));
/*     */   }
/*     */   
/*     */   static void cmdCurrentItem(ICommandSender sender) {
/* 432 */     if (!(sender.func_174793_f() instanceof EntityPlayer)) {
/* 433 */       msg(sender, "Not applicable for non-player");
/*     */     }
/*     */     
/* 436 */     EntityPlayer player = (EntityPlayer)sender.func_174793_f();
/* 437 */     ItemStack stack = player.field_71071_by.func_70448_g();
/*     */     
/* 439 */     if (StackUtil.isEmpty(stack)) {
/* 440 */       msg(sender, "empty: " + StackUtil.toStringSafe(stack));
/*     */     } else {
/* 442 */       msg(sender, String.format("ID: %s, Raw Meta: %d, Meta: %d, Damage: %d, NBT: %s", new Object[] { stack
/* 443 */               .func_77973_b().getRegistryName(), 
/* 444 */               Integer.valueOf(StackUtil.getRawMeta(stack)), 
/* 445 */               Integer.valueOf(stack.func_77960_j()), 
/* 446 */               Integer.valueOf(stack.func_77952_i()), stack
/* 447 */               .func_77978_p() }));
/*     */       
/* 449 */       msg(sender, "Current Item excluding amount: " + 
/* 450 */           ConfigUtil.fromStack(stack));
/* 451 */       msg(sender, "Current Item including amount: " + 
/* 452 */           ConfigUtil.fromStackWithAmount(stack));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cmdGiveCrop(ICommandSender sender, String[] args) throws CommandException {
/* 462 */     EntityPlayer player = (EntityPlayer)sender;
/*     */     
/* 464 */     if (!StackUtil.isEmpty(player.field_71071_by.func_70448_g())) {
/* 465 */       msg(sender, "The currently selected slot needs to be empty.");
/*     */     } else {
/* 467 */       CropCard crop = Crops.instance.getCropCard(args[1], args[2]);
/*     */ 
/*     */       
/* 470 */       if (crop == null) {
/* 471 */         msg(sender, "The crop you specified does not exist.");
/*     */       } else {
/*     */         int growth, gain, resistance; try {
/* 474 */           growth = Integer.parseInt(args[3]);
/* 475 */           gain = Integer.parseInt(args[4]);
/* 476 */           resistance = Integer.parseInt(args[5]);
/* 477 */         } catch (NumberFormatException exception) {
/* 478 */           throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */         } 
/*     */         
/* 481 */         if (growth < 1 || growth > 31 || gain < 1 || gain > 31 || resistance < 1 || resistance > 31) {
/* 482 */           throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*     */         }
/*     */         
/* 485 */         player.field_71071_by.func_70441_a(
/* 486 */             ItemCropSeed.generateItemStackFromValues(crop, growth, gain, resistance, 4));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static class TextureDumper implements IWorldTickCallback { private final Pattern pattern;
/*     */     
/*     */     TextureDumper(Pattern pattern, int size, Integer meta) {
/* 493 */       this.pattern = pattern;
/* 494 */       this.size = size;
/* 495 */       this.meta = meta;
/*     */     }
/*     */     private final int size; private final Integer meta;
/*     */     
/*     */     public void onTick(World world) {
/* 500 */       if (this.size > 0) {
/* 501 */         MinecraftForge.EVENT_BUS.register(this);
/*     */       }
/*     */     }
/*     */     
/*     */     @SubscribeEvent
/*     */     @SideOnly(Side.CLIENT)
/*     */     public void onRenderWorldLast(RenderWorldLastEvent event) {
/* 508 */       IC2.log.info(LogCategory.General, "Starting texture dump.");
/*     */       
/* 510 */       int count = 0;
/*     */       
/* 512 */       GlStateManager.func_179094_E();
/* 513 */       GlStateManager.func_179123_a();
/* 514 */       for (Item item : ForgeRegistries.ITEMS) {
/* 515 */         String regName = Util.getName(item).toString();
/*     */         
/* 517 */         if (this.pattern.matcher(regName).matches()) {
/* 518 */           if (this.meta == null) {
/* 519 */             if (item instanceof IMultiItem) {
/* 520 */               for (ItemStack stack : ((IMultiItem)item).getAllStacks()) {
/* 521 */                 assert stack != null : item + " produced a null stack in getAllStacks()";
/*     */                 
/* 523 */                 dump(stack, regName);
/* 524 */                 count++;
/*     */               } 
/*     */             } else {
/* 527 */               Set<String> processedNames = new HashSet<>();
/*     */               
/* 529 */               for (int i = 0; i < 32767; i++) {
/* 530 */                 ItemStack stack = new ItemStack(item, 1, i);
/*     */                 
/*     */                 try {
/* 533 */                   String name = stack.func_77977_a();
/* 534 */                   if (name == null || !processedNames.add(name))
/*     */                     break; 
/* 536 */                 } catch (Exception e) {
/* 537 */                   IC2.log.info(LogCategory.General, e, "Exception for %s.", new Object[] { stack });
/*     */                   
/*     */                   break;
/*     */                 } 
/* 541 */                 dump(stack, regName);
/* 542 */                 count++;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 546 */             dump(new ItemStack(item, 1, this.meta.intValue()), regName);
/* 547 */             count++;
/*     */           } 
/*     */         }
/*     */         
/* 551 */         if (Keyboard.isKeyDown(1))
/*     */           break; 
/* 553 */       }  GlStateManager.func_179099_b();
/* 554 */       GlStateManager.func_179121_F();
/*     */       
/* 556 */       IC2.log.info(LogCategory.General, "Dumped %d sprites.", new Object[] { Integer.valueOf(count) });
/*     */       
/* 558 */       MinecraftForge.EVENT_BUS.unregister(this);
/*     */     }
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     private void dump(ItemStack stack, String name) {
/* 563 */       Minecraft mc = Minecraft.func_71410_x();
/*     */       
/* 565 */       GL11.glClear(16640);
/*     */       
/* 567 */       GL11.glMatrixMode(5889);
/* 568 */       GL11.glPushMatrix();
/* 569 */       GL11.glLoadIdentity();
/* 570 */       GL11.glOrtho(0.0D, mc.field_71443_c * 16.0D / this.size, mc.field_71440_d * 16.0D / this.size, 0.0D, 1000.0D, 3000.0D);
/*     */       
/* 572 */       GL11.glMatrixMode(5888);
/* 573 */       GL11.glPushMatrix();
/* 574 */       GL11.glLoadIdentity();
/* 575 */       GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/*     */       
/* 577 */       RenderHelper.func_74520_c();
/* 578 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 579 */       GL11.glEnable(32826);
/*     */       
/* 581 */       mc.func_175599_af().func_175042_a(stack, 0, 0);
/*     */       
/* 583 */       BufferedImage img = new BufferedImage(this.size, this.size, 2);
/*     */       
/* 585 */       if (OpenGlHelper.func_148822_b()) {
/* 586 */         Framebuffer fb = mc.func_147110_a();
/*     */         
/* 588 */         int width = fb.field_147622_a;
/* 589 */         int height = fb.field_147620_b;
/*     */         
/* 591 */         IntBuffer buffer = BufferUtils.createIntBuffer(width * height);
/* 592 */         int[] data = new int[width * height];
/*     */         
/* 594 */         GlStateManager.func_187425_g(3333, 1);
/* 595 */         GlStateManager.func_187425_g(3317, 1);
/*     */         
/* 597 */         GlStateManager.func_179144_i(fb.field_147617_g);
/* 598 */         GlStateManager.func_187433_a(3553, 0, 32993, 33639, buffer);
/*     */         
/* 600 */         buffer.get(data);
/*     */         
/* 602 */         int[] mirroredData = new int[data.length];
/* 603 */         for (int y = 0; y < height; y++) {
/* 604 */           System.arraycopy(data, y * width, mirroredData, (height - y - 1) * width, width);
/*     */         }
/*     */         
/* 607 */         img.setRGB(0, 0, this.size, this.size, mirroredData, 0, width);
/*     */       } else {
/* 609 */         IntBuffer buffer = BufferUtils.createIntBuffer(this.size * this.size);
/* 610 */         int[] data = new int[this.size * this.size];
/*     */         
/* 612 */         GlStateManager.func_187425_g(3333, 1);
/* 613 */         GlStateManager.func_187425_g(3317, 1);
/*     */         
/* 615 */         GlStateManager.func_187413_a(0, 0, this.size, this.size, 32993, 33639, buffer);
/*     */         
/* 617 */         buffer.get(data);
/* 618 */         TextureUtil.func_147953_a(data, this.size, this.size);
/*     */         
/* 620 */         img.setRGB(0, 0, this.size, this.size, data, 0, this.size);
/*     */       } 
/*     */       
/*     */       try {
/* 624 */         File dir = new File(IC2.platform.getMinecraftDir(), "sprites");
/* 625 */         dir.mkdir();
/*     */         
/* 627 */         String modId = (name.indexOf(':') >= 0) ? name.substring(0, name.indexOf(':')) : name;
/* 628 */         String fileName = "Sprite_" + modId + '_' + stack.func_82833_r() + '_' + this.size;
/* 629 */         fileName = fileName.replaceAll("[^\\w\\- ]+", "");
/*     */         
/* 631 */         File file = new File(dir, fileName + ".png");
/* 632 */         int extra = 0;
/* 633 */         while (file.exists()) {
/* 634 */           file = new File(dir, fileName + '_' + extra++ + ".png");
/*     */         }
/*     */         
/* 637 */         ImageIO.write(img, "png", file);
/* 638 */       } catch (IOException e) {
/* 639 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 642 */       GL11.glPopMatrix();
/* 643 */       GL11.glMatrixMode(5889);
/* 644 */       GL11.glPopMatrix();
/* 645 */       GL11.glMatrixMode(5888);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\command\CommandIc2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */