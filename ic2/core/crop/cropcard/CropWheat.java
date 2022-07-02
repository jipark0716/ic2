/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.CropVanilla;
/*    */ import net.minecraft.block.BlockCrops;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropWheat
/*    */   extends CropVanilla
/*    */ {
/*    */   public CropWheat() {
/* 20 */     super((BlockCrops)Blocks.field_150464_aj);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 24 */     return "wheat";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 29 */     return new CropProperties(1, 0, 4, 0, 0, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 34 */     return new String[] { "Yellow", "Food", "Wheat" };
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getProduct() {
/* 39 */     return new ItemStack(Items.field_151015_O, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getSeeds() {
/* 44 */     return new ItemStack(Items.field_151014_N);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 49 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropWheat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */