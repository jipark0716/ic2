package ic2.api.upgrade;

import java.util.Collection;
import java.util.Set;
import net.minecraft.item.ItemStack;

public interface IUpgradeItem {
  boolean isSuitableFor(ItemStack paramItemStack, Set<UpgradableProperty> paramSet);
  
  boolean onTick(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock);
  
  Collection<ItemStack> onProcessEnd(ItemStack paramItemStack, IUpgradableBlock paramIUpgradableBlock, Collection<ItemStack> paramCollection);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\ap\\upgrade\IUpgradeItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */