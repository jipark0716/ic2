package ic2.core.item.armor.jetpack;

import net.minecraft.item.ItemStack;

public interface IJetpack {
  public static final int EU_ENERGY_INCREASE = 6;
  
  boolean drainEnergy(ItemStack paramItemStack, int paramInt);
  
  float getPower(ItemStack paramItemStack);
  
  float getDropPercentage(ItemStack paramItemStack);
  
  double getChargeLevel(ItemStack paramItemStack);
  
  boolean isJetpackActive(ItemStack paramItemStack);
  
  float getHoverMultiplier(ItemStack paramItemStack, boolean paramBoolean);
  
  float getWorldHeightDivisor(ItemStack paramItemStack);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\IJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */