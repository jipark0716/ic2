package ic2.api.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public interface ITeBlockSpecialItem {
  boolean doesOverrideDefault(ItemStack paramItemStack);
  
  ModelResourceLocation getModelLocation(ItemStack paramItemStack);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\ITeBlockSpecialItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */