/*    */ package ic2.api.crops;
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
/*    */ public class CropProperties
/*    */ {
/*    */   private final int tier;
/*    */   private final int chemistry;
/*    */   private final int consumable;
/*    */   private final int defensive;
/*    */   private final int colorful;
/*    */   private final int weed;
/*    */   
/*    */   public CropProperties(int tier, int chemistry, int consumable, int defensive, int colorful, int weed) {
/* 32 */     this.tier = tier;
/* 33 */     this.chemistry = chemistry;
/* 34 */     this.consumable = consumable;
/* 35 */     this.defensive = defensive;
/* 36 */     this.colorful = colorful;
/* 37 */     this.weed = weed;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 41 */     return this.tier;
/*    */   }
/*    */   
/*    */   public int getChemistry() {
/* 45 */     return this.chemistry;
/*    */   }
/*    */   
/*    */   public int getConsumable() {
/* 49 */     return this.consumable;
/*    */   }
/*    */   
/*    */   public int getDefensive() {
/* 53 */     return this.defensive;
/*    */   }
/*    */   
/*    */   public int getColorful() {
/* 57 */     return this.colorful;
/*    */   }
/*    */   
/*    */   public int getWeed() {
/* 61 */     return this.weed;
/*    */   }
/*    */   
/*    */   public int[] getAllProperties() {
/* 65 */     return new int[] { getChemistry(), getConsumable(), getDefensive(), 
/* 66 */         getColorful(), getWeed() };
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\CropProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */