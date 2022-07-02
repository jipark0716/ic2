/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.core.crop.CropVanilla;
/*    */ import net.minecraft.block.BlockCrops;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CropBeetroot
/*    */   extends CropVanilla
/*    */ {
/*    */   public CropBeetroot() {
/* 14 */     super((BlockCrops)Blocks.field_185773_cZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 19 */     return "beetroots";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 24 */     return new CropProperties(1, 0, 4, 0, 1, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 29 */     return new String[] { "Red", "Food", "Beetroot" };
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getProduct() {
/* 34 */     return new ItemStack(Items.field_185164_cV, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getSeeds() {
/* 39 */     return new ItemStack(Items.field_185163_cU);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropBeetroot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */