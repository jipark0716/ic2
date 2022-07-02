package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITerraformingBP {
  double getConsume(ItemStack paramItemStack);
  
  int getRange(ItemStack paramItemStack);
  
  boolean canInsert(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, BlockPos paramBlockPos);
  
  boolean terraform(ItemStack paramItemStack, World paramWorld, BlockPos paramBlockPos);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\ITerraformingBP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */