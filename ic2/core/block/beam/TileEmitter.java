/*    */ package ic2.core.block.beam;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*    */ 
/*    */ public class TileEmitter extends TileEntityElectricMachine {
/*    */   public TileEmitter() {
/*  7 */     super(5000, 1);
/*    */   }
/*    */   
/*    */   private int progress;
/*    */   
/*    */   protected void updateEntityServer() {
/* 13 */     super.updateEntityServer();
/*    */     
/* 15 */     if (this.progress < 100) this.progress++;
/*    */     
/* 17 */     if (this.progress == 100 && func_145831_w().func_175640_z(this.field_174879_c)) {
/* 18 */       this.progress = 0;
/*    */       
/* 20 */       func_145831_w().func_72838_d(new EntityParticle(this));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\beam\TileEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */