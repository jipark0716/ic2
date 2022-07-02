/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.File;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IContainerListener;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ 
/*     */ public class Platform
/*     */ {
/*     */   public boolean isSimulating() {
/*  29 */     return !isRendering();
/*     */   }
/*     */   
/*     */   public boolean isRendering() {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public void displayError(String error, Object... args) {
/*  37 */     if (args.length > 0) error = String.format(error, args);
/*     */     
/*  39 */     error = "IndustrialCraft 2 Error\n\n == = IndustrialCraft 2 Error = == \n\n" + error + "\n\n == == == == == == == == == == ==\n";
/*  40 */     error = error.replace("\n", System.getProperty("line.separator"));
/*     */     
/*  42 */     throw new RuntimeException(error);
/*     */   }
/*     */   
/*     */   public void displayError(Exception e, String error, Object... args) {
/*  46 */     if (args.length > 0) error = String.format(error, args);
/*     */     
/*  48 */     displayError("An unexpected Exception occured.\n\n" + getStackTrace(e) + "\n" + error, new Object[0]);
/*     */   }
/*     */   
/*     */   public String getStackTrace(Exception e) {
/*  52 */     StringWriter writer = new StringWriter();
/*  53 */     PrintWriter printWriter = new PrintWriter(writer);
/*     */     
/*  55 */     e.printStackTrace(printWriter);
/*     */     
/*  57 */     return writer.toString();
/*     */   }
/*     */   
/*     */   public EntityPlayer getPlayerInstance() {
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public World getWorld(int dimId) {
/*  65 */     return (World)DimensionManager.getWorld(dimId);
/*     */   }
/*     */   
/*     */   public World getPlayerWorld() {
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preInit() {}
/*     */ 
/*     */   
/*     */   public void messagePlayer(EntityPlayer player, String message, Object... args) {
/*  77 */     if (player instanceof EntityPlayerMP) {
/*     */       TextComponentTranslation textComponentTranslation;
/*     */       
/*  80 */       if (args.length > 0) {
/*  81 */         textComponentTranslation = new TextComponentTranslation(message, (Object[])getMessageComponents(args));
/*     */       } else {
/*  83 */         textComponentTranslation = new TextComponentTranslation(message, new Object[0]);
/*     */       } 
/*     */       
/*  86 */       ((EntityPlayerMP)player).func_145747_a((ITextComponent)textComponentTranslation);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean launchGui(EntityPlayer player, IHasGui inventory) {
/*  94 */     if (!Util.isFakePlayer(player, true)) {
/*  95 */       EntityPlayerMP playerMp = (EntityPlayerMP)player;
/*     */       
/*  97 */       playerMp.func_71117_bO();
/*  98 */       playerMp.func_71128_l();
/*     */       
/* 100 */       int windowId = playerMp.field_71139_cq;
/*     */       
/* 102 */       ((NetworkManager)IC2.network.get(true)).initiateGuiDisplay(playerMp, inventory, windowId);
/*     */       
/* 104 */       player.field_71070_bA = inventory.getGuiContainer(player);
/* 105 */       player.field_71070_bA.field_75152_c = windowId;
/* 106 */       player.field_71070_bA.func_75132_a((IContainerListener)playerMp);
/*     */       
/* 108 */       return true;
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean launchSubGui(EntityPlayer player, IHasGui inventory, int ID) {
/* 115 */     if (!Util.isFakePlayer(player, true)) {
/* 116 */       EntityPlayerMP playerMp = (EntityPlayerMP)player;
/*     */       
/* 118 */       playerMp.func_71117_bO();
/* 119 */       playerMp.func_71128_l();
/*     */       
/* 121 */       int windowId = playerMp.field_71139_cq;
/*     */       
/* 123 */       ((NetworkManager)IC2.network.get(true)).initiateGuiDisplay(playerMp, inventory, windowId, Integer.valueOf(ID));
/*     */       
/* 125 */       player.field_71070_bA = inventory.getGuiContainer(player);
/* 126 */       player.field_71070_bA.field_75152_c = windowId;
/* 127 */       player.field_71070_bA.func_75132_a((IContainerListener)playerMp);
/*     */       
/* 129 */       return true;
/*     */     } 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public boolean launchGuiClient(EntityPlayer player, IHasGui inventory, boolean isAdmin) {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerStartSection(String section) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerEndSection() {}
/*     */ 
/*     */   
/*     */   public void profilerEndStartSection(String section) {}
/*     */ 
/*     */   
/*     */   public File getMinecraftDir() {
/* 152 */     return new File(".");
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSoundSp(String sound, float f, float g) {}
/*     */ 
/*     */   
/*     */   public void resetPlayerInAirTime(EntityPlayer player) {
/* 160 */     if (!(player instanceof EntityPlayerMP)) {
/*     */       return;
/*     */     }
/*     */     
/* 164 */     ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP)player).field_71135_a, 
/*     */         
/* 166 */         Integer.valueOf(0), new String[] { "field_147365_f", "floatingTickCount" });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockTexture(Block block, IBlockAccess world, int x, int y, int z, int side) {
/* 171 */     return 0;
/*     */   }
/*     */   
/*     */   public void removePotion(EntityLivingBase entity, Potion potion) {
/* 175 */     entity.func_184589_d(potion);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostInit() {}
/*     */ 
/*     */   
/*     */   protected ITextComponent[] getMessageComponents(Object... args) {
/* 183 */     ITextComponent[] encodedArgs = new ITextComponent[args.length];
/*     */     
/* 185 */     for (int i = 0; i < args.length; i++) {
/* 186 */       if (args[i] instanceof String && ((String)args[i]).startsWith("ic2.")) {
/* 187 */         encodedArgs[i] = (ITextComponent)new TextComponentTranslation((String)args[i], new Object[0]);
/*     */       } else {
/* 189 */         encodedArgs[i] = (ITextComponent)new TextComponentString(args[i].toString());
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return encodedArgs;
/*     */   }
/*     */   
/*     */   public void requestTick(boolean simulating, Runnable runnable) {
/* 197 */     if (!simulating) throw new IllegalStateException();
/*     */     
/* 199 */     FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(runnable);
/*     */   }
/*     */   
/*     */   public int getColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tint) {
/* 203 */     throw new UnsupportedOperationException("client only");
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */