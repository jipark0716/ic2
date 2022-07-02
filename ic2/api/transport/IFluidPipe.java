package ic2.api.transport;

import net.minecraftforge.fluids.FluidTank;

public interface IFluidPipe extends IPipe {
  int getTransferRate();
  
  FluidTank getTank();
  
  int getCurrentInnerCapacity();
  
  int getMaxInnerCapacity();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\transport\IFluidPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */