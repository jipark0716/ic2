/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotProcessableSmelting
/*    */   extends InvSlotProcessable<ItemStack, ItemStack, ItemStack>
/*    */ {
/*    */   public InvSlotProcessableSmelting(IInventorySlotHolder<?> base, String name, int count) {
/* 11 */     super(base, name, count, Recipes.furnace);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getInput(ItemStack stack) {
/* 16 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setInput(ItemStack input) {
/* 21 */     put(input);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotProcessableSmelting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */