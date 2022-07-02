/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.recipe.AdvRecipe;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdvRecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */   implements IShapedCraftingRecipeWrapper
/*    */ {
/*    */   private final AdvRecipe recipe;
/*    */   
/*    */   public AdvRecipeWrapper(AdvRecipe recipe) {
/* 24 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public List<List<ItemStack>> getInputs() {
/* 28 */     int mask = this.recipe.masks[0];
/* 29 */     int itemIndex = 0;
/* 30 */     List<IRecipeInput> ret = new ArrayList<>();
/*    */     
/* 32 */     for (int i = 0; i < 9; i++) {
/* 33 */       if (i % 3 < this.recipe.inputWidth && i / 3 < this.recipe.inputHeight)
/* 34 */         if ((mask >>> 8 - i & 0x1) != 0) {
/* 35 */           ret.add(this.recipe.input[itemIndex++]);
/*    */         } else {
/* 37 */           ret.add(null);
/*    */         }  
/*    */     } 
/* 40 */     return replaceRecipeInputs(ret);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 45 */     return this.recipe.inputWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 50 */     return this.recipe.inputHeight;
/*    */   }
/*    */   
/*    */   public static List<List<ItemStack>> replaceRecipeInputs(List<IRecipeInput> list) {
/* 54 */     List<List<ItemStack>> out = new ArrayList<>(list.size());
/*    */     
/* 56 */     for (IRecipeInput recipe : list) {
/* 57 */       if (recipe == null) {
/* 58 */         out.add(Collections.emptyList()); continue;
/*    */       } 
/* 60 */       List<ItemStack> replace = new ArrayList<>(recipe.getInputs());
/*    */       
/* 62 */       for (ListIterator<ItemStack> it = replace.listIterator(); it.hasNext(); ) {
/* 63 */         ItemStack stack = it.next();
/*    */         
/* 65 */         if (stack != null && stack.func_77973_b() instanceof ic2.api.item.IElectricItem) {
/* 66 */           it.set(StackUtil.copyWithWildCard(stack));
/*    */         }
/*    */       } 
/*    */       
/* 70 */       out.add(replace);
/*    */     } 
/*    */ 
/*    */     
/* 74 */     return out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 79 */     ingredients.setInputLists(ItemStack.class, getInputs());
/* 80 */     ingredients.setOutput(ItemStack.class, this.recipe.func_77571_b());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\AdvRecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */