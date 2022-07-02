/*    */ package ic2.core.block.comp;
/*    */ 
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import java.util.function.IntSupplier;
/*    */ 
/*    */ public abstract class BasicRedstoneComponent
/*    */   extends TileEntityComponent
/*    */ {
/*    */   private int level;
/*    */   private IntSupplier update;
/*    */   
/*    */   public BasicRedstoneComponent(TileEntityBlock parent) {
/* 13 */     super(parent);
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 17 */     return this.level;
/*    */   }
/*    */   
/*    */   public void setLevel(int newLevel) {
/* 21 */     if (newLevel == this.level)
/* 22 */       return;  this.level = newLevel;
/* 23 */     onChange();
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void onChange();
/*    */   
/*    */   public boolean enableWorldTick() {
/* 30 */     return (this.update != null);
/*    */   }
/*    */   
/*    */   public void onWorldTick() {
/* 34 */     assert this.update != null;
/*    */     
/* 36 */     setLevel(this.update.getAsInt());
/*    */   }
/*    */   
/*    */   public void setUpdate(IntSupplier update) {
/* 40 */     this.update = update;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\BasicRedstoneComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */