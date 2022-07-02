package ic2.core.energy.grid;

import ic2.api.energy.NodeStats;
import java.io.PrintStream;

public interface IEnergyCalculator {
  void handleGridChange(Grid paramGrid);
  
  boolean runSyncStep(EnergyNetLocal paramEnergyNetLocal);
  
  boolean runSyncStep(Grid paramGrid);
  
  void runAsyncStep(Grid paramGrid);
  
  NodeStats getNodeStats(Tile paramTile);
  
  void dumpNodeInfo(Node paramNode, String paramString, PrintStream paramPrintStream1, PrintStream paramPrintStream2);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\IEnergyCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */