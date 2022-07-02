/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.ref.FluidName;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorCFPack
/*    */   extends ItemArmorFluidTank
/*    */ {
/*    */   public ItemArmorCFPack() {
/* 16 */     super(ItemName.cf_pack, "batpack", FluidName.construction_foam.getInstance(), 80000);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 21 */     if (!func_194125_a(tab)) {
/*    */       return;
/*    */     }
/* 24 */     ItemStack stack = new ItemStack((Item)this, 1);
/* 25 */     filltank(stack);
/* 26 */     stack.func_77964_b(1);
/* 27 */     subItems.add(stack);
/*    */     
/* 29 */     stack = new ItemStack((Item)this, 1);
/* 30 */     stack.func_77964_b(getMaxDamage(stack));
/* 31 */     subItems.add(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorCFPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */