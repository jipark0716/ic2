package ic2.api.info;

import net.minecraft.item.ItemStack;

public interface IInfoProvider {
  double getEnergyValue(ItemStack paramItemStack);
  
  int getFuelValue(ItemStack paramItemStack, boolean paramBoolean);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\info\IInfoProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */