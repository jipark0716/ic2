package ic2.api.upgrade;

import net.minecraft.item.ItemStack;

public interface IRedstoneSensitiveUpgrade extends IUpgradeItem {
  boolean modifiesRedstoneInput(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
  
  int getRedstoneInput(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock, int paramInt);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\ap\\upgrade\IRedstoneSensitiveUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */