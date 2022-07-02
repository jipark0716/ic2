package ic2.api.item;

import net.minecraft.item.ItemStack;

public interface IItemHudProvider {
  boolean doesProvideHUD(ItemStack paramItemStack);
  
  HudMode getHudMode(ItemStack paramItemStack);
  
  public static interface IItemHudBarProvider {
    int getBarPercent(ItemStack param1ItemStack);
  }
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IItemHudProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */