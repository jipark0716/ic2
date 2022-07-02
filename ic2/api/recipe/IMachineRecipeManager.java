package ic2.api.recipe;

import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeManager<RI, RO, I> {
  boolean addRecipe(RI paramRI, RO paramRO, NBTTagCompound paramNBTTagCompound, boolean paramBoolean);
  
  MachineRecipeResult<RI, RO, I> apply(I paramI, boolean paramBoolean);
  
  Iterable<? extends MachineRecipe<RI, RO>> getRecipes();
  
  boolean isIterable();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IMachineRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */