/*    */ package ic2.core.crop;
/*    */ 
/*    */ import ic2.api.crops.CropCard;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IC2CropCard
/*    */   extends CropCard
/*    */ {
/*    */   public String getOwner() {
/* 14 */     return "ic2";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName() {
/* 19 */     return "ic2.crop." + getId();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 24 */     return "IC2 Team";
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ResourceLocation> getTexturesLocation() {
/* 29 */     List<ResourceLocation> ret = new ArrayList<>(getMaxSize());
/*    */     
/* 31 */     for (int size = 1; size <= getMaxSize(); size++) {
/* 32 */       ret.add(new ResourceLocation("ic2", "blocks/crop/" + getId() + "_" + size));
/*    */     }
/*    */     
/* 35 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\IC2CropCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */