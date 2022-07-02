package ic2.core.block.transport.cover;

import java.util.Collection;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

public interface ICoverItem {
  boolean isSuitableFor(ItemStack paramItemStack, Set<CoverProperty> paramSet);
  
  boolean onTick(ItemStack paramItemStack, ICoverHolder paramICoverHolder);
  
  boolean allowsInput(ItemStack paramItemStack);
  
  boolean allowsInput(FluidStack paramFluidStack);
  
  boolean allowsOutput(ItemStack paramItemStack);
  
  boolean allowsOutput(FluidStack paramFluidStack);
  
  Collection<? extends Capability<?>> getProvidedCapabilities();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\cover\ICoverItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */