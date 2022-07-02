package ic2.api.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;

public interface IScrapboxManager extends IBasicMachineRecipeManager {
  void addDrop(ItemStack paramItemStack, float paramFloat);
  
  ItemStack getDrop(ItemStack paramItemStack, boolean paramBoolean);
  
  Map<ItemStack, Float> getDrops();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IScrapboxManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */