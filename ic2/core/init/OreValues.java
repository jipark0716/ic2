/*    */ package ic2.core.init;
/*    */ 
/*    */ import ic2.core.util.ItemComparableItemStack;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class OreValues
/*    */ {
/*    */   public static void add(ItemStack stack, int value) {
/* 13 */     if (value <= 0) throw new IllegalArgumentException("value has to be > 0");
/*    */     
/* 15 */     ItemComparableItemStack key = new ItemComparableItemStack(stack, true);
/* 16 */     Integer prev = stackValues.put(key, Integer.valueOf(value));
/*    */     
/* 18 */     if (prev != null && prev.intValue() > value) {
/* 19 */       stackValues.put(key, prev);
/*    */     }
/*    */   }
/*    */   
/*    */   public static int get(ItemStack stack) {
/* 24 */     if (StackUtil.isEmpty(stack)) return 0;
/*    */     
/* 26 */     Integer ret = stackValues.get(new ItemComparableItemStack(stack, false));
/*    */     
/* 28 */     return (ret != null) ? (ret.intValue() * StackUtil.getSize(stack)) : 0;
/*    */   }
/*    */   
/*    */   public static int get(List<ItemStack> stacks) {
/* 32 */     int ret = 0;
/*    */     
/* 34 */     for (ItemStack stack : stacks) {
/* 35 */       ret += get(stack);
/*    */     }
/*    */     
/* 38 */     return ret;
/*    */   }
/*    */   
/* 41 */   private static final Map<ItemComparableItemStack, Integer> stackValues = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\OreValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */