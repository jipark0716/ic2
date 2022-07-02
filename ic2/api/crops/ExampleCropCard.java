/*    */ package ic2.api.crops;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExampleCropCard
/*    */   extends CropCard
/*    */ {
/*    */   public String getId() {
/* 17 */     return "example";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOwner() {
/* 22 */     return "myaddon";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 30 */     return new CropProperties(1, 0, 4, 0, 0, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 35 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 40 */     return new ItemStack(Items.field_151045_i, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ResourceLocation> getTexturesLocation() {
/* 45 */     List<ResourceLocation> ret = new ArrayList<>(getMaxSize());
/*    */     
/* 47 */     for (int size = 1; size <= getMaxSize(); size++) {
/* 48 */       ret.add(new ResourceLocation("myaddon", "blocks/crop/" + getId() + "_" + size));
/*    */     }
/*    */     
/* 51 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\ExampleCropCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */