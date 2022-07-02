package ic2.api.energy.tile;

import net.minecraft.util.EnumFacing;

public interface IEnergySink extends IEnergyAcceptor {
  double getDemandedEnergy();
  
  int getSinkTier();
  
  double injectEnergy(EnumFacing paramEnumFacing, double paramDouble1, double paramDouble2);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IEnergySink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */