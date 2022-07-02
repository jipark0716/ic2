package ic2.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface IRecipeInputFactory {
  IRecipeInput forStack(ItemStack paramItemStack);
  
  IRecipeInput forStack(ItemStack paramItemStack, int paramInt);
  
  IRecipeInput forExactStack(ItemStack paramItemStack);
  
  IRecipeInput forExactStack(ItemStack paramItemStack, int paramInt);
  
  IRecipeInput forOreDict(String paramString);
  
  IRecipeInput forOreDict(String paramString, int paramInt);
  
  IRecipeInput forOreDict(String paramString, int paramInt1, int paramInt2);
  
  IRecipeInput forFluidContainer(Fluid paramFluid);
  
  IRecipeInput forFluidContainer(Fluid paramFluid, int paramInt);
  
  IRecipeInput forAny(IRecipeInput... paramVarArgs);
  
  IRecipeInput forAny(Iterable<IRecipeInput> paramIterable);
  
  IRecipeInput forIngredient(Ingredient paramIngredient);
  
  Ingredient getIngredient(IRecipeInput paramIRecipeInput);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IRecipeInputFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */