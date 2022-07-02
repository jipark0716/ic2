/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ 
/*    */ public class RecipeResolver
/*    */   implements IRecipeResolver
/*    */ {
/*    */   private static final double transformCost = 1.0D;
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 18 */     List<RecipeTransformation> ret = new ArrayList<>();
/*    */     
/* 20 */     for (IRecipe irecipe : ForgeRegistries.RECIPES) {
/* 21 */       NonNullList<Ingredient> inputs = irecipe.func_192400_c();
/* 22 */       ItemStack output = irecipe.func_77571_b();
/* 23 */       if (StackUtil.isEmpty(output) || inputs.isEmpty())
/*    */         continue; 
/* 25 */       ret.add(new RecipeTransformation(1.0D, toDoubleStackList((List<Ingredient>)inputs), new LeanItemStack[] { new LeanItemStack(output) }));
/*    */     } 
/*    */ 
/*    */     
/* 29 */     return ret;
/*    */   }
/*    */   
/*    */   private static List<List<LeanItemStack>> toDoubleStackList(List<Ingredient> list) {
/* 33 */     List<List<LeanItemStack>> ret = new ArrayList<>(list.size());
/* 34 */     for (Ingredient ingredient : list) {
/* 35 */       ItemStack[] arr = ingredient.func_193365_a();
/* 36 */       List<LeanItemStack> toAdd = new ArrayList<>(arr.length);
/* 37 */       for (ItemStack stack : arr) {
/* 38 */         toAdd.add(new LeanItemStack(stack));
/*    */       }
/* 40 */       ret.add(toAdd);
/*    */     } 
/* 42 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\RecipeResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */