/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class InvSlotProcessableGeneric
/*    */   extends InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>
/*    */ {
/*    */   public InvSlotProcessableGeneric(IInventorySlotHolder<?> base, String name, int count, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeManager) {
/* 14 */     super(base, name, count, recipeManager);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getInput(ItemStack stack) {
/* 19 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setInput(ItemStack input) {
/* 24 */     put(input);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotProcessableGeneric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */