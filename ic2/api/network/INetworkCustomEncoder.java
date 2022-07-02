package ic2.api.network;

import java.io.IOException;

public interface INetworkCustomEncoder {
  void encode(IGrowingBuffer paramIGrowingBuffer, Object paramObject) throws IOException;
  
  Object decode(IGrowingBuffer paramIGrowingBuffer) throws IOException;
  
  boolean isThreadSafe();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\network\INetworkCustomEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */