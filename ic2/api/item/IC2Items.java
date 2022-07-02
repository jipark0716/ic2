/*    */ package ic2.api.item;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.ModContainer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IC2Items
/*    */ {
/*    */   private static IItemAPI instance;
/*    */   
/*    */   public static ItemStack getItem(String name, String variant) {
/* 45 */     if (instance == null) {
/* 46 */       return null;
/*    */     }
/* 48 */     return instance.getItemStack(name, variant);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack getItem(String name) {
/* 59 */     return getItem(name, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IItemAPI getItemAPI() {
/* 67 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setInstance(IItemAPI api) {
/* 78 */     ModContainer mc = Loader.instance().activeModContainer();
/*    */     
/* 80 */     if (mc == null || !Info.MOD_ID.equals(mc.getModId())) {
/* 81 */       throw new IllegalAccessError("invoked from " + mc);
/*    */     }
/*    */     
/* 84 */     instance = api;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IC2Items.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */