/*    */ package ic2.jeiIntegration.recipe.misc;
/*    */ 
/*    */ import ic2.core.init.Localization;
/*    */ import mezz.jei.api.IGuiHelper;
/*    */ import mezz.jei.api.gui.IDrawable;
/*    */ import mezz.jei.api.gui.IGuiItemStackGroup;
/*    */ import mezz.jei.api.gui.IRecipeLayout;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeCategory;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class ScrapboxRecipeCategory
/*    */   extends BlankRecipeCategory<IRecipeWrapper>
/*    */ {
/*    */   public static final String UID = "ic2.scrapbox";
/*    */   private final IDrawable background;
/*    */   
/*    */   public ScrapboxRecipeCategory(IGuiHelper guiHelper) {
/* 22 */     this.background = (IDrawable)guiHelper.createDrawable(new ResourceLocation("ic2:textures/gui/ScrapboxRecipes.png"), 55, 30, 82, 26);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUid() {
/* 27 */     return "ic2.scrapbox";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 32 */     return Localization.translate("ic2.crafting.scrap_box");
/*    */   }
/*    */ 
/*    */   
/*    */   public IDrawable getBackground() {
/* 37 */     return this.background;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
/* 42 */     IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
/* 43 */     itemStacks.init(0, true, 0, 4);
/* 44 */     itemStacks.init(1, true, 60, 4);
/* 45 */     itemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
/* 46 */     itemStacks.set(1, ingredients.getOutputs(ItemStack.class).get(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModName() {
/* 53 */     return "ic2";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\misc\ScrapboxRecipeCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */