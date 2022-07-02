package ic2.api.energy.tile;

public interface IEnergySource extends IEnergyEmitter {
  double getOfferedEnergy();
  
  void drawEnergy(double paramDouble);
  
  int getSourceTier();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IEnergySource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */