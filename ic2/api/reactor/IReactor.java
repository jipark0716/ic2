package ic2.api.reactor;

import ic2.api.info.ILocatable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IReactor extends ILocatable {
  TileEntity getCoreTe();
  
  int getHeat();
  
  void setHeat(int paramInt);
  
  int addHeat(int paramInt);
  
  int getMaxHeat();
  
  void setMaxHeat(int paramInt);
  
  void addEmitHeat(int paramInt);
  
  float getHeatEffectModifier();
  
  void setHeatEffectModifier(float paramFloat);
  
  float getReactorEnergyOutput();
  
  double getReactorEUEnergyOutput();
  
  float addOutput(float paramFloat);
  
  ItemStack getItemAt(int paramInt1, int paramInt2);
  
  void setItemAt(int paramInt1, int paramInt2, ItemStack paramItemStack);
  
  void explode();
  
  int getTickRate();
  
  boolean produceEnergy();
  
  boolean isFluidCooled();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\reactor\IReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */