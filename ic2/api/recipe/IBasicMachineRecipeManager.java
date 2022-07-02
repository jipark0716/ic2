package ic2.api.recipe;

import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IBasicMachineRecipeManager extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> {
  boolean addRecipe(IRecipeInput paramIRecipeInput, NBTTagCompound paramNBTTagCompound, boolean paramBoolean, ItemStack... paramVarArgs);
  
  @Deprecated
  RecipeOutput getOutputFor(ItemStack paramItemStack, boolean paramBoolean);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IBasicMachineRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */