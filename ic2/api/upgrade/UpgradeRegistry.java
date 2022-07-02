/*    */ package ic2.api.upgrade;
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
/*    */ public class UpgradeRegistry
/*    */ {
/*    */   public static ItemStack register(ItemStack stack) {
/* 23 */     if (!(stack.func_77973_b() instanceof IUpgradeItem)) throw new IllegalArgumentException("The stack must represent an IUpgradeItem.");
/*    */     
/* 25 */     upgrades.add(stack);
/*    */     
/* 27 */     return stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Iterable<ItemStack> getUpgrades() {
/* 34 */     return Collections.unmodifiableCollection(upgrades);
/*    */   }
/*    */   
/* 37 */   private static final List<ItemStack> upgrades = new ArrayList<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\ap\\upgrade\UpgradeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */