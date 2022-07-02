/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemCoke extends ItemIC2 {
/*    */   public ItemCoke() {
/*  8 */     super(ItemName.coke);
/*    */     
/* 10 */     func_77625_d(64);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemBurnTime(ItemStack itemStack) {
/* 15 */     return 3200;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemCoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */