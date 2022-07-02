package ic2.api.transport;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IPipe {
  TileEntity getTile();
  
  boolean isConnected(EnumFacing paramEnumFacing);
  
  void flipConnection(EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\transport\IPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */