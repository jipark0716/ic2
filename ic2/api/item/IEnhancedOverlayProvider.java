package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnhancedOverlayProvider {
  boolean providesEnhancedOverlay(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IEnhancedOverlayProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */