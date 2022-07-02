/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.init.Rezepte;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ 
/*    */ 
/*    */ public class ColourCarryingRecipe
/*    */   extends AdvRecipe
/*    */ {
/*    */   public static void addAndRegister(ItemStack result, Object... args) {
/*    */     try {
/* 17 */       Rezepte.registerRecipe((IRecipe)new ColourCarryingRecipe(result, args));
/* 18 */     } catch (RuntimeException e) {
/* 19 */       if (!MainConfig.ignoreInvalidRecipes) throw e; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public ColourCarryingRecipe(ItemStack result, Object... args) {
/* 24 */     super(result, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77572_b(InventoryCrafting craftingInv) {
/* 29 */     ItemStack initialResult = super.func_77572_b(craftingInv);
/*    */     
/* 31 */     if (!StackUtil.isEmpty(initialResult) && initialResult.func_77973_b() instanceof ItemArmor) {
/* 32 */       int colour = -1;
/*    */       
/* 34 */       for (int slot = 0; slot < craftingInv.func_70302_i_(); slot++) {
/* 35 */         ItemStack offer = craftingInv.func_70301_a(slot);
/*    */         
/* 37 */         if (!StackUtil.isEmpty(initialResult) && offer.func_77973_b() instanceof ItemArmor) {
/* 38 */           colour = ((ItemArmor)offer.func_77973_b()).func_82814_b(offer);
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 43 */       if (colour != -1) {
/* 44 */         ((ItemArmor)initialResult.func_77973_b()).func_82813_b(initialResult, colour);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     return initialResult;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\ColourCarryingRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */