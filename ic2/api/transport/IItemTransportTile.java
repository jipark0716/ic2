package ic2.api.transport;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IItemTransportTile extends IPipe {
  int putItems(ItemStack paramItemStack, EnumFacing paramEnumFacing, boolean paramBoolean);
  
  ItemStack getContents();
  
  void setContents(ItemStack paramItemStack);
  
  int getMaxStackSizeAllowed();
  
  int getTransferRate();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\transport\IItemTransportTile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */