package ic2.core.item.tfbp;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

abstract class TerraformerBase {
  abstract boolean terraform(World paramWorld, BlockPos paramBlockPos);
  
  void init() {}
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\TerraformerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */