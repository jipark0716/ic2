/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.recipe.GradualRecipe;
/*    */ import ic2.core.util.Util;
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class GradualRecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final GradualRecipe recipe;
/*    */   
/*    */   public GradualRecipeWrapper(GradualRecipe recipe) {
/* 21 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 25 */     List<ItemStack> ret = new ArrayList<>(2);
/*    */     
/* 27 */     ret.add(this.recipe.chargeMaterial);
/* 28 */     ItemStack repairItem = this.recipe.func_77571_b();
/* 29 */     this.recipe.item.setCustomDamage(repairItem, this.recipe.amount);
/* 30 */     ret.add(repairItem);
/*    */     
/* 32 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
/* 37 */     assert this.recipe.item.getMaxCustomDamage(this.recipe.func_77571_b()) > 0;
/* 38 */     String effectiveness = Localization.translate("ic2.jei.condenser", new Object[] { Float.valueOf(Util.limit(this.recipe.amount / this.recipe.item.getMaxCustomDamage(this.recipe.func_77571_b()) * 100.0F, 0.0F, 100.0F)) });
/* 39 */     int width = minecraft.field_71466_p.func_78256_a(effectiveness);
/*    */     
/* 41 */     if (143 - width < 55) {
/* 42 */       minecraft.field_71466_p.func_78279_b(effectiveness, 55, 88, 90, Color.darkGray.getRGB());
/*    */     } else {
/* 44 */       minecraft.field_71466_p.func_78276_b(effectiveness, (55 + 143 - width) / 2, 42, Color.darkGray.getRGB());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 50 */     ingredients.setInputs(ItemStack.class, getInputs());
/* 51 */     ingredients.setOutput(ItemStack.class, this.recipe.func_77571_b());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\GradualRecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */