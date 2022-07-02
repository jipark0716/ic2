package ic2.api.energy;

import ic2.api.energy.tile.IEnergyTile;

public interface IEnergyNetEventReceiver {
  void onAdd(IEnergyTile paramIEnergyTile);
  
  void onRemove(IEnergyTile paramIEnergyTile);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\IEnergyNetEventReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */