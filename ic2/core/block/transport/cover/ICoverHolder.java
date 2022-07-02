package ic2.core.block.transport.cover;

import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICoverHolder {
  Set<CoverProperty> getCoverProperties();
  
  boolean canPlaceCover(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, ItemStack paramItemStack);
  
  void placeCover(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, ItemStack paramItemStack);
  
  boolean canRemoveCover(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  void removeCover(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\cover\ICoverHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */