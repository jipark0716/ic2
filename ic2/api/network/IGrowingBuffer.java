package ic2.api.network;

import java.io.DataInput;
import java.io.DataOutput;

public interface IGrowingBuffer extends DataInput, DataOutput {
  void writeVarInt(int paramInt);
  
  void writeString(String paramString);
  
  int readVarInt();
  
  String readString();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\network\IGrowingBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */