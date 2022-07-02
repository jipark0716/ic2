package ic2.core.block;

import ic2.core.block.invslot.InvSlot;

public interface IInventorySlotHolder<P extends TileEntityBlock & net.minecraft.inventory.IInventory> {
  P getParent();
  
  InvSlot getInventorySlot(String paramString);
  
  void addInventorySlot(InvSlot paramInvSlot);
  
  int getBaseIndex(InvSlot paramInvSlot);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\IInventorySlotHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */