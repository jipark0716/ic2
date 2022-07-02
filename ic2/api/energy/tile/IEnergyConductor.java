package ic2.api.energy.tile;

public interface IEnergyConductor extends IEnergyAcceptor, IEnergyEmitter {
  double getConductionLoss();
  
  double getInsulationEnergyAbsorption();
  
  double getInsulationBreakdownEnergy();
  
  double getConductorBreakdownEnergy();
  
  void removeInsulation();
  
  void removeConductor();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IEnergyConductor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */