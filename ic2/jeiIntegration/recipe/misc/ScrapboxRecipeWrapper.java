/*    */ package ic2.jeiIntegration.recipe.misc;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScrapboxRecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final Map.Entry<ItemStack, Float> entry;
/*    */   
/*    */   public ScrapboxRecipeWrapper(Map.Entry<ItemStack, Float> entry) {
/* 24 */     this.entry = entry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
/*    */     String text;
/* 31 */     float value = ((Float)this.entry.getValue()).floatValue();
/*    */     
/* 33 */     if (value < 0.001D) {
/* 34 */       text = "< 0.01";
/*    */     } else {
/*    */       
/* 37 */       text = "  " + String.format("%.2f", new Object[] { Float.valueOf(value * 100.0F) });
/*    */     } 
/* 39 */     minecraft.field_71466_p.func_78276_b(text + "%", 86, 9, 4210752);
/*    */   }
/*    */   
/*    */   public static List<ScrapboxRecipeWrapper> createRecipes() {
/* 43 */     List<ScrapboxRecipeWrapper> recipes = new ArrayList<>();
/* 44 */     for (Map.Entry<ItemStack, Float> e : (Iterable<Map.Entry<ItemStack, Float>>)Recipes.scrapboxDrops.getDrops().entrySet()) {
/* 45 */       recipes.add(new ScrapboxRecipeWrapper(e));
/*    */     }
/* 47 */     return recipes;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 52 */     ingredients.setInput(ItemStack.class, ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box));
/* 53 */     ingredients.setOutput(ItemStack.class, this.entry.getKey());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\misc\ScrapboxRecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */