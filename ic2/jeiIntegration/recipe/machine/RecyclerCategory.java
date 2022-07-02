/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.ITeBlock;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.IGuiHelper;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecyclerCategory
/*    */   extends DynamicCategory<IBasicMachineRecipeManager>
/*    */ {
/*    */   private final List<List<ItemStack>> trueInputs;
/*    */   
/*    */   public RecyclerCategory(IGuiHelper guiHelper) {
/* 26 */     super((ITeBlock)TeBlock.recycler, Recipes.recycler, guiHelper);
/*    */     
/* 28 */     List<ItemStack> items = new ArrayList<>();
/* 29 */     if (Recipes.recyclerWhitelist.isEmpty()) {
/* 30 */       for (Item i : ForgeRegistries.ITEMS) {
/* 31 */         ItemStack stack = new ItemStack(i, 1, 32767);
/* 32 */         if (!Recipes.recyclerBlacklist.contains(stack))
/* 33 */           items.add(stack); 
/*    */       } 
/*    */     } else {
/* 36 */       for (IRecipeInput stack : Recipes.recyclerWhitelist) {
/* 37 */         items.addAll(stack.getInputs());
/*    */       }
/*    */     } 
/* 40 */     this.trueInputs = Collections.singletonList(items);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<List<ItemStack>> getInputStacks(IIngredients wrapper) {
/* 45 */     return this.trueInputs;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\RecyclerCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */