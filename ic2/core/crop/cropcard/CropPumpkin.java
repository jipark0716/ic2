/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.crop.CropVanillaStem;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropPumpkin
/*    */   extends CropVanillaStem
/*    */ {
/*    */   public CropPumpkin() {
/* 18 */     super(4);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 23 */     return "pumpkin";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 28 */     return new CropProperties(1, 0, 1, 0, 3, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 33 */     return new String[] { "Orange", "Decoration", "Stem" };
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getProduct() {
/* 38 */     return new ItemStack(Blocks.field_150423_aK);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getSeeds() {
/* 43 */     return new ItemStack(Items.field_151080_bb, IC2.random.nextInt(3) + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 48 */     if (crop.getCurrentSize() == 3) {
/* 49 */       return 600;
/*    */     }
/* 51 */     return 200;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropPumpkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */