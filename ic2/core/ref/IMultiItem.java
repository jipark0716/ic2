/*    */ package ic2.core.ref;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public interface IMultiItem<T extends IIdProvider>
/*    */ {
/*    */   ItemStack getItemStack(T paramT);
/*    */   
/*    */   ItemStack getItemStack(String paramString);
/*    */   
/*    */   default Set<ItemStack> getAllStacks() {
/* 17 */     Set<ItemStack> ret = new HashSet<>();
/*    */     
/* 19 */     for (IIdProvider iIdProvider : getAllTypes()) {
/* 20 */       ret.add(getItemStack((T)iIdProvider));
/*    */     }
/*    */     
/* 23 */     ret.remove(null);
/* 24 */     ret.remove(StackUtil.emptyStack);
/*    */     
/* 26 */     return ret;
/*    */   }
/*    */   
/*    */   String getVariant(ItemStack paramItemStack);
/*    */   
/*    */   Set<T> getAllTypes();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\IMultiItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */