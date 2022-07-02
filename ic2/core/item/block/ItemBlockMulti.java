/*    */ package ic2.core.item.block;
/*    */ import ic2.core.block.BlockMultiID;
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemBlockMulti extends ItemBlockIC2 {
/*    */   public ItemBlockMulti(Block block) {
/* 10 */     super(block);
/*    */     
/* 12 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int damage) {
/* 17 */     return damage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 23 */     String name = ((IIdProvider)this.field_150939_a.func_176203_a(stack.func_77960_j()).func_177229_b((IProperty)((BlockMultiID)this.field_150939_a).getTypeProperty())).getName();
/*    */     
/* 25 */     return super.func_77667_c(stack) + "." + name;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemBlockMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */