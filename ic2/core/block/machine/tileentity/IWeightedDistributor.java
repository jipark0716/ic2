package ic2.core.block.machine.tileentity;

import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWeightedDistributor extends IInventory {
  EnumFacing getFacing();
  
  @SideOnly(Side.CLIENT)
  List<EnumFacing> getPriority();
  
  void updatePriority(boolean paramBoolean);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\IWeightedDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */