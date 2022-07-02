/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class MachineRecipeResolver implements IRecipeResolver {
/*    */   private static final double transformCost = 14.0D;
/*    */   private final IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> manager;
/*    */   
/*    */   public MachineRecipeResolver(IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> manager) {
/* 19 */     this.manager = manager;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 24 */     if (!this.manager.isIterable()) return Collections.emptyList();
/*    */     
/* 26 */     List<RecipeTransformation> ret = new ArrayList<>();
/*    */     
/* 28 */     for (MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe : (Iterable<MachineRecipe<IRecipeInput, Collection<ItemStack>>>)this.manager.getRecipes()) {
/*    */       try {
/* 30 */         List<List<LeanItemStack>> inputs = RecipeUtil.convertIngredients(((IRecipeInput)recipe.getInput()).getInputs());
/* 31 */         List<LeanItemStack> outputs = RecipeUtil.convertOutputs((Collection<ItemStack>)recipe.getOutput());
/*    */         
/* 33 */         ret.add(new RecipeTransformation(14.0D, inputs, outputs));
/* 34 */       } catch (IllegalArgumentException e) {
/* 35 */         IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
/*    */       } 
/*    */     } 
/*    */     
/* 39 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\MachineRecipeResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */