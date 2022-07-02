/*    */ package ic2.api.crops;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CropSoilType
/*    */ {
/* 14 */   FARMLAND(Blocks.field_150458_ak),
/* 15 */   MYCELIUM((Block)Blocks.field_150391_bh),
/* 16 */   SAND((Block)Blocks.field_150354_m),
/* 17 */   SOULSAND(Blocks.field_150425_aM);
/*    */   
/*    */   CropSoilType(Block block) {
/* 20 */     this.block = block;
/*    */   }
/*    */   private final Block block;
/*    */   public Block getBlock() {
/* 24 */     return this.block;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean contais(Block block) {
/* 33 */     for (CropSoilType aux : values()) {
/* 34 */       if (aux.getBlock() == block) {
/* 35 */         return true;
/*    */       }
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\CropSoilType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */