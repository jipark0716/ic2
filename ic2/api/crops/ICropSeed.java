package ic2.api.crops;

import net.minecraft.item.ItemStack;

public interface ICropSeed {
  CropCard getCropFromStack(ItemStack paramItemStack);
  
  void setCropFromStack(ItemStack paramItemStack, CropCard paramCropCard);
  
  int getGrowthFromStack(ItemStack paramItemStack);
  
  void setGrowthFromStack(ItemStack paramItemStack, int paramInt);
  
  int getGainFromStack(ItemStack paramItemStack);
  
  void setGainFromStack(ItemStack paramItemStack, int paramInt);
  
  int getResistanceFromStack(ItemStack paramItemStack);
  
  void setResistanceFromStack(ItemStack paramItemStack, int paramInt);
  
  int getScannedFromStack(ItemStack paramItemStack);
  
  void setScannedFromStack(ItemStack paramItemStack, int paramInt);
  
  void incrementScannedFromStack(ItemStack paramItemStack);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\ICropSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */