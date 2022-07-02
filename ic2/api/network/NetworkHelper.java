/*     */ package ic2.api.network;
/*     */ 
/*     */ import ic2.api.info.Info;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
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
/*     */ public final class NetworkHelper
/*     */ {
/*     */   private static INetworkManager serverInstance;
/*     */   private static INetworkManager clientInstance;
/*     */   
/*     */   public static void updateTileEntityField(TileEntity te, String field) {
/*  66 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).updateTileEntityField(te, field);
/*     */   }
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
/*     */   public static void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange) {
/*  81 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).initiateTileEntityEvent(te, event, limitRange);
/*     */   }
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
/*     */   public static void initiateItemEvent(EntityPlayer player, ItemStack stack, int event, boolean limitRange) {
/*  99 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).initiateItemEvent(player, stack, event, limitRange);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendInitialData(TileEntity te) {
/* 108 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).sendInitialData(te);
/*     */   }
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
/*     */   public static void initiateClientTileEntityEvent(TileEntity te, int event) {
/* 123 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).initiateClientTileEntityEvent(te, event);
/*     */   }
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
/*     */   public static void initiateClientItemEvent(ItemStack stack, int event) {
/* 137 */     getNetworkManager(FMLCommonHandler.instance().getEffectiveSide()).initiateClientItemEvent(stack, event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static INetworkManager getNetworkManager(Side side) {
/* 146 */     if (side.isClient()) {
/* 147 */       return clientInstance;
/*     */     }
/*     */     
/* 150 */     return serverInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setInstance(INetworkManager server, INetworkManager client) {
/* 162 */     ModContainer mc = Loader.instance().activeModContainer();
/* 163 */     if (mc == null || !Info.MOD_ID.equals(mc.getModId())) {
/* 164 */       throw new IllegalAccessError();
/*     */     }
/* 166 */     serverInstance = server;
/* 167 */     clientInstance = client;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\network\NetworkHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */