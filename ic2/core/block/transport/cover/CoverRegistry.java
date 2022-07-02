/*    */ package ic2.core.block.transport.cover;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class CoverRegistry
/*    */ {
/*    */   public static ItemStack register(ItemStack stack) {
/* 23 */     if (!(stack.func_77973_b() instanceof ICoverItem)) throw new IllegalArgumentException("The stack must represent an ICoverItem.");
/*    */     
/* 25 */     covers.add(stack);
/*    */     
/* 27 */     return stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Iterable<ItemStack> getCovers() {
/* 34 */     return Collections.unmodifiableCollection(covers);
/*    */   }
/*    */   
/* 37 */   private static final List<ItemStack> covers = new ArrayList<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\cover\CoverRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */