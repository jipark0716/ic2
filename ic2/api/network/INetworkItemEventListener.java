package ic2.api.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface INetworkItemEventListener {
  void onNetworkEvent(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\network\INetworkItemEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */