/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.recipe.AdvRecipe;
/*    */ import ic2.core.util.LogCategory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*    */ 
/*    */ public class VanillaSmeltingResolver
/*    */   implements IRecipeResolver {
/*    */   private static final double transformCost = 14.0D;
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 17 */     List<RecipeTransformation> ret = new ArrayList<>();
/*    */     
/* 19 */     for (Map.Entry<ItemStack, ItemStack> entry : (Iterable<Map.Entry<ItemStack, ItemStack>>)FurnaceRecipes.func_77602_a().func_77599_b().entrySet()) {
/*    */       try {
/* 21 */         List<List<LeanItemStack>> inputs = RecipeUtil.convertIngredients(AdvRecipe.expand(entry.getKey()));
/* 22 */         LeanItemStack output = new LeanItemStack(entry.getValue());
/*    */         
/* 24 */         ret.add(new RecipeTransformation(14.0D, inputs, new LeanItemStack[] { output }));
/* 25 */       } catch (IllegalArgumentException e) {
/* 26 */         IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\VanillaSmeltingResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */