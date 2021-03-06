package ic2.api.energy;

import ic2.api.energy.tile.IEnergyTile;
import java.io.PrintStream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyNet {
  IEnergyTile getTile(World paramWorld, BlockPos paramBlockPos);
  
  IEnergyTile getSubTile(World paramWorld, BlockPos paramBlockPos);
  
  <T extends net.minecraft.tileentity.TileEntity & IEnergyTile> void addTile(T paramT);
  
  <T extends ic2.api.info.ILocatable & IEnergyTile> void addTile(T paramT);
  
  void removeTile(IEnergyTile paramIEnergyTile);
  
  World getWorld(IEnergyTile paramIEnergyTile);
  
  BlockPos getPos(IEnergyTile paramIEnergyTile);
  
  NodeStats getNodeStats(IEnergyTile paramIEnergyTile);
  
  boolean dumpDebugInfo(World paramWorld, BlockPos paramBlockPos, PrintStream paramPrintStream1, PrintStream paramPrintStream2);
  
  double getPowerFromTier(int paramInt);
  
  int getTierFromPower(double paramDouble);
  
  void registerEventReceiver(IEnergyNetEventReceiver paramIEnergyNetEventReceiver);
  
  void unregisterEventReceiver(IEnergyNetEventReceiver paramIEnergyNetEventReceiver);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\IEnergyNet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */