/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.init.BlocksItems;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.ref.IItemModelProvider;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ItemFoodIc2 extends ItemFood implements IItemModelProvider {
/*    */   protected ItemFoodIc2(ItemName name, int amount, float saturation, boolean isWolfFood) {
/* 17 */     super(amount, saturation, isWolfFood);
/*    */     
/* 19 */     func_77655_b(name.name());
/* 20 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */     
/* 22 */     BlocksItems.registerItem((Item)this, IC2.getIdentifier(name.name()));
/* 23 */     name.setInstance((Item)this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(ItemName name) {
/* 29 */     ItemIC2.registerModel((Item)this, 0, name, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77658_a() {
/* 34 */     return "ic2." + super.func_77658_a().substring(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 39 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77657_g(ItemStack stack) {
/* 44 */     return func_77667_c(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77653_i(ItemStack stack) {
/* 49 */     return Localization.translate(func_77667_c(stack));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemFoodIc2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */