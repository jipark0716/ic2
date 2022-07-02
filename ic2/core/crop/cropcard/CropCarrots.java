/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.core.crop.CropVanilla;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropCarrots
/*    */   extends CropVanilla
/*    */ {
/*    */   public CropCarrots() {
/* 16 */     super(3);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 20 */     return "carrots";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 25 */     return new CropProperties(2, 0, 4, 0, 0, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 30 */     return new String[] { "Orange", "Food", "Carrots" };
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getProduct() {
/* 35 */     return new ItemStack(Items.field_151172_bF);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getSeeds() {
/* 40 */     return new ItemStack(Items.field_151172_bF);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropCarrots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */