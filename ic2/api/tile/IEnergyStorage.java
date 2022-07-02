package ic2.api.tile;

import net.minecraft.util.EnumFacing;

public interface IEnergyStorage {
  int getStored();
  
  void setStored(int paramInt);
  
  int addEnergy(int paramInt);
  
  int getCapacity();
  
  int getOutput();
  
  double getOutputEnergyUnitsPerTick();
  
  boolean isTeleporterCompatible(EnumFacing paramEnumFacing);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\tile\IEnergyStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */