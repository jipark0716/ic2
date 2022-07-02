package ic2.core.ref;

import net.minecraft.block.state.IBlockState;

public interface IMultiBlock<T extends ic2.core.block.state.IIdProvider> extends IMultiItem<T> {
  IBlockState getState(T paramT);
  
  IBlockState getState(String paramString);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\IMultiBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */