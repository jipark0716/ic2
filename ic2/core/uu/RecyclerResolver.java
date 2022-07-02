/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.machine.tileentity.TileEntityRecycler;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class RecyclerResolver
/*    */   implements ILateRecipeResolver
/*    */ {
/*    */   public List<RecipeTransformation> getTransformations(Iterable<LeanItemStack> obtainableStacks) {
/* 20 */     List<LeanItemStack> input = new ArrayList<>();
/*    */     
/* 22 */     for (LeanItemStack obtainableStack : obtainableStacks) {
/* 23 */       ItemStack stack = obtainableStack.toMcStack();
/* 24 */       if (StackUtil.isEmpty(stack))
/*    */         continue; 
/* 26 */       if (!((Collection)Recipes.recycler.apply(stack, false).getOutput()).isEmpty()) {
/* 27 */         input.add(new LeanItemStack(stack, TileEntityRecycler.recycleChance()));
/*    */       }
/*    */     } 
/*    */     
/* 31 */     return Arrays.asList(new RecipeTransformation[] { new RecipeTransformation(transformCost, Collections.singletonList(input), new LeanItemStack[] { new LeanItemStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap)) }) });
/*    */   }
/*    */ 
/*    */   
/* 35 */   private static final double transformCost = 55.0D * TileEntityRecycler.recycleChance() / 4000.0D * 107.0D;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\RecyclerResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */