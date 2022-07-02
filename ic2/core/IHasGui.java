package ic2.core;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasGui extends IInventory {
  ContainerBase<?> getGuiContainer(EntityPlayer paramEntityPlayer);
  
  @SideOnly(Side.CLIENT)
  GuiScreen getGui(EntityPlayer paramEntityPlayer, boolean paramBoolean);
  
  void onGuiClosed(EntityPlayer paramEntityPlayer);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\IHasGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */