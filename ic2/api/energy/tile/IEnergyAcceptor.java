package ic2.api.energy.tile;

import net.minecraft.util.EnumFacing;

public interface IEnergyAcceptor extends IEnergyTile {
  boolean acceptsEnergyFrom(IEnergyEmitter paramIEnergyEmitter, EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IEnergyAcceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */