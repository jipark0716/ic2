package ic2.core.ref;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelProvider {
  @SideOnly(Side.CLIENT)
  void registerModels(ItemName paramItemName);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\IItemModelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */