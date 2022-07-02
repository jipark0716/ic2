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
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.ItemSword;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ic2Sword
/*    */   extends ItemSword
/*    */   implements IItemModelProvider
/*    */ {
/*    */   public int weaponDamage;
/*    */   private final Object repairMaterial;
/*    */   
/*    */   public Ic2Sword(Item.ToolMaterial material) {
/* 31 */     super(material);
/*    */     
/* 33 */     this.weaponDamage = 7;
/* 34 */     this.repairMaterial = "ingotBronze";
/*    */     
/* 36 */     func_77655_b(ItemName.bronze_sword.name());
/* 37 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */     
/* 39 */     BlocksItems.registerItem((Item)this, IC2.getIdentifier(ItemName.bronze_sword.name()));
/* 40 */     ItemName.bronze_sword.setInstance((Item)this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(ItemName name) {
/* 47 */     ItemIC2.registerModel((Item)this, 0, name, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77658_a() {
/* 52 */     return "ic2." + super.func_77658_a().substring(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 57 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77657_g(ItemStack stack) {
/* 62 */     return func_77667_c(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77653_i(ItemStack stack) {
/* 67 */     return Localization.translate(func_77667_c(stack));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
/* 75 */     return (stack2 != null && Util.matchesOD(stack2, this.repairMaterial));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\Ic2Sword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */