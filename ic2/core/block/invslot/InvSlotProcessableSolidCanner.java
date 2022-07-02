/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotProcessableSolidCanner
/*    */   extends InvSlotProcessable<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> {
/*    */   public InvSlotProcessableSolidCanner(TileEntitySolidCanner base1, String name1, int count) {
/* 13 */     super((IInventorySlotHolder<?>)base1, name1, count, (IMachineRecipeManager<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput>)Recipes.cannerBottle);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ICannerBottleRecipeManager.RawInput getInput(ItemStack stack) {
/* 18 */     return new ICannerBottleRecipeManager.RawInput(((TileEntitySolidCanner)this.base).canInputSlot.get(), stack);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setInput(ICannerBottleRecipeManager.RawInput input) {
/* 23 */     ((TileEntitySolidCanner)this.base).canInputSlot.put(input.container);
/* 24 */     put(input.fill);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotProcessableSolidCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */