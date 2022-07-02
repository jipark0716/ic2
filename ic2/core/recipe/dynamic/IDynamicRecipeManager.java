package ic2.core.recipe.dynamic;

import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IDynamicRecipeManager {
  boolean addRecipe(DynamicRecipe paramDynamicRecipe, boolean paramBoolean);
  
  boolean removeRecipe(Collection<RecipeInputIngredient> paramCollection, Collection<RecipeOutputIngredient> paramCollection1);
  
  DynamicRecipe apply(ItemStack[] paramArrayOfItemStack, FluidStack[] paramArrayOfFluidStack, boolean paramBoolean);
  
  Iterable<? extends DynamicRecipe> getRecipes();
  
  boolean isIterable();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\IDynamicRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */