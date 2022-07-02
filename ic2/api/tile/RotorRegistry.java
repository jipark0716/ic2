/*    */ package ic2.api.tile;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.ModContainer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RotorRegistry
/*    */ {
/*    */   private static IRotorRegistry INSTANCE;
/*    */   
/*    */   public static <T extends net.minecraft.tileentity.TileEntity & IRotorProvider> void registerRotorProvider(Class<T> clazz) {
/* 24 */     if (INSTANCE != null) INSTANCE.registerRotorProvider(clazz);
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setInstance(IRotorRegistry i) {
/* 32 */     ModContainer mc = Loader.instance().activeModContainer();
/* 33 */     if (mc == null || !Info.MOD_ID.equals(mc.getModId())) {
/* 34 */       throw new IllegalAccessError("Only IC2 can set the instance");
/*    */     }
/* 36 */     INSTANCE = i;
/*    */   }
/*    */   
/*    */   public static interface IRotorRegistry {
/*    */     <T extends net.minecraft.tileentity.TileEntity & IRotorProvider> void registerRotorProvider(Class<T> param1Class);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\tile\RotorRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */