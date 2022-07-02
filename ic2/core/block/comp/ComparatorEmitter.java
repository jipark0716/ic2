/*    */ package ic2.core.block.comp;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ComparatorEmitter extends BasicRedstoneComponent {
/*    */   public ComparatorEmitter(TileEntityBlock parent) {
/*  7 */     super(parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onChange() {
/* 12 */     this.parent.func_145831_w().func_175666_e(this.parent.func_174877_v(), (Block)this.parent.getBlockType());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\ComparatorEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */