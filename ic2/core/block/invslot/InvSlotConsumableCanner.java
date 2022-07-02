/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableCanner
/*    */   extends InvSlotConsumableLiquid {
/*    */   public InvSlotConsumableCanner(TileEntityCanner base1, String name1, int count) {
/* 12 */     super((IInventorySlotHolder<?>)base1, name1, count);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 17 */     switch (((TileEntityCanner)this.base).getMode()) {
/*    */       case BottleSolid:
/* 19 */         return (Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(stack, ((TileEntityCanner)this.base).inputSlot.get()), true) != null);
/*    */       case BottleLiquid:
/*    */       case EmptyLiquid:
/*    */       case EnrichLiquid:
/* 23 */         return super.accepts(stack);
/*    */     } 
/*    */     assert false;
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */