package ic2.api.upgrade;

import net.minecraft.item.ItemStack;

public interface IProcessingUpgrade extends IUpgradeItem {
  int getExtraProcessTime(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
  
  double getProcessTimeMultiplier(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
  
  int getExtraEnergyDemand(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
  
  double getEnergyDemandMultiplier(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\ap\\upgrade\IProcessingUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */