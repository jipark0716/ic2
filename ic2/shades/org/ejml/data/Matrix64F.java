package ic2.shades.org.ejml.data;

import java.io.Serializable;

public interface Matrix64F extends Serializable {
  double get(int paramInt1, int paramInt2);
  
  double unsafe_get(int paramInt1, int paramInt2);
  
  void set(int paramInt1, int paramInt2, double paramDouble);
  
  void unsafe_set(int paramInt1, int paramInt2, double paramDouble);
  
  int getNumRows();
  
  int getNumCols();
  
  int getNumElements();
  
  <T extends Matrix64F> T copy();
  
  void print();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\Matrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */