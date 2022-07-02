package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import net.minecraft.inventory.IInventory;

public interface IPersonalBlock {
  boolean permitsAccess(GameProfile paramGameProfile);
  
  IInventory getPrivilegedInventory(GameProfile paramGameProfile);
  
  GameProfile getOwner();
  
  void setOwner(GameProfile paramGameProfile);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\IPersonalBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */