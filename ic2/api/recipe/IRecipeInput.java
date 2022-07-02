/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IRecipeInput
/*    */ {
/*    */   boolean matches(ItemStack paramItemStack);
/*    */   
/*    */   int getAmount();
/*    */   
/*    */   List<ItemStack> getInputs();
/*    */   
/*    */   default Ingredient getIngredient() {
/* 46 */     return Recipes.inputFactory.getIngredient(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IRecipeInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */