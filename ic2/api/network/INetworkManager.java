package ic2.api.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface INetworkManager {
  void updateTileEntityField(TileEntity paramTileEntity, String paramString);
  
  void initiateTileEntityEvent(TileEntity paramTileEntity, int paramInt, boolean paramBoolean);
  
  void initiateItemEvent(EntityPlayer paramEntityPlayer, ItemStack paramItemStack, int paramInt, boolean paramBoolean);
  
  void initiateClientTileEntityEvent(TileEntity paramTileEntity, int paramInt);
  
  void initiateClientItemEvent(ItemStack paramItemStack, int paramInt);
  
  void sendInitialData(TileEntity paramTileEntity);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\network\INetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */