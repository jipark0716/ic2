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
/*    */ public class CropMelon
/*    */   extends CropVanillaStem
/*    */ {
/*    */   public CropMelon() {
/* 18 */     super(4);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 23 */     return "melon";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 28 */     return "Chao";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 33 */     return new CropProperties(2, 0, 4, 0, 2, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 38 */     return new String[] { "Green", "Food", "Stem" };
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getProduct() {
/* 43 */     if (IC2.random.nextInt(3) == 0) {
/* 44 */       return new ItemStack(Blocks.field_150440_ba);
/*    */     }
/* 46 */     return new ItemStack(Items.field_151127_ba, IC2.random.nextInt(4) + 2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getSeeds() {
/* 51 */     return new ItemStack(Items.field_151081_bc, IC2.random.nextInt(2) + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 56 */     if (crop.getCurrentSize() == 3) {
/* 57 */       return 700;
/*    */     }
/* 59 */     return 250;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropMelon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */