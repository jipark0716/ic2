package ic2.api.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public interface IRotorProvider {
  int getRotorDiameter();
  
  EnumFacing getFacing();
  
  float getAngle();
  
  ResourceLocation getRotorRenderTexture();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\tile\IRotorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */