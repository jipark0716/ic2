package ic2.core.item.armor.jetpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBoostingJetpack extends IJetpack {
  float getBaseThrust(ItemStack paramItemStack, boolean paramBoolean);
  
  float getBoostThrust(EntityPlayer paramEntityPlayer, ItemStack paramItemStack, boolean paramBoolean);
  
  boolean useBoostPower(ItemStack paramItemStack, float paramFloat);
  
  float getHoverBoost(EntityPlayer paramEntityPlayer, ItemStack paramItemStack, boolean paramBoolean);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\IBoostingJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */