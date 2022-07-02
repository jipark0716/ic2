/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class AdvancedIORecipeWrapper
/*    */   extends IORecipeWrapper
/*    */ {
/*    */   private final IRecipeInput secondary;
/*    */   
/*    */   AdvancedIORecipeWrapper(MachineRecipe<IRecipeInput, Collection<ItemStack>> container, IRecipeInput input, IORecipeCategory<?> category) {
/* 17 */     super(container, category);
/* 18 */     this.secondary = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<List<ItemStack>> getInputs() {
/* 23 */     List<List<ItemStack>> list = new ArrayList<>(2);
/*    */     
/* 25 */     list.addAll(super.getInputs());
/* 26 */     list.add(this.secondary.getInputs());
/*    */     
/* 28 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\AdvancedIORecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */