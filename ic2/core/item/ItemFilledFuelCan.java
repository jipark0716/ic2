/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemFilledFuelCan
/*    */   extends ItemIC2 {
/*    */   public ItemFilledFuelCan() {
/* 11 */     super(ItemName.filled_fuel_can);
/*    */     
/* 13 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasContainerItem(ItemStack stack) {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getContainerItem(ItemStack stack) {
/* 23 */     return ItemName.crafting.getItemStack((Enum)CraftingItemType.empty_fuel_can);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemBurnTime(ItemStack stack) {
/* 28 */     return StackUtil.getOrCreateNbtData(stack).func_74762_e("value") * 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemFilledFuelCan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */