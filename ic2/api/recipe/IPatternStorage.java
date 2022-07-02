package ic2.api.recipe;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IPatternStorage {
  boolean addPattern(ItemStack paramItemStack);
  
  List<ItemStack> getPatterns();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IPatternStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */