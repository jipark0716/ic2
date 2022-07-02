package ic2.core.block.steam;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiBlockController {
  World getWorld();
  
  BlockPos getPos();
  
  boolean isInvalid();
  
  boolean hasValidStructure();
  
  boolean isFormed();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\steam\IMultiBlockController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */