package ic2.api.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemAPI {
  IBlockState getBlockState(String paramString1, String paramString2);
  
  ItemStack getItemStack(String paramString1, String paramString2);
  
  Block getBlock(String paramString);
  
  Item getItem(String paramString);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IItemAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */