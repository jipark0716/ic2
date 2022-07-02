/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.init.BlocksItems;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.ref.IItemModelProvider;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ic2Hoe
/*    */   extends ItemHoe
/*    */   implements IItemModelProvider
/*    */ {
/*    */   private final Object repairMaterial;
/*    */   
/*    */   public Ic2Hoe(Item.ToolMaterial material) {
/* 30 */     super(material);
/*    */     
/* 32 */     this.repairMaterial = "ingotBronze";
/*    */     
/* 34 */     func_77655_b(ItemName.bronze_hoe.name());
/* 35 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */     
/* 37 */     BlocksItems.registerItem((Item)this, IC2.getIdentifier(ItemName.bronze_hoe.name()));
/* 38 */     ItemName.bronze_hoe.setInstance((Item)this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(ItemName name) {
/* 44 */     ItemIC2.registerModel((Item)this, 0, name, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77658_a() {
/* 49 */     return "ic2." + super.func_77658_a().substring(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 54 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77657_g(ItemStack stack) {
/* 59 */     return func_77667_c(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77653_i(ItemStack stack) {
/* 64 */     return Localization.translate(func_77667_c(stack));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
/* 72 */     return (stack2 != null && Util.matchesOD(stack2, this.repairMaterial));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\Ic2Hoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */