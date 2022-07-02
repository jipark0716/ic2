package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.DenseMatrix64F;

public interface QRPDecomposition<T extends ic2.shades.org.ejml.data.Matrix64F> extends QRDecomposition<T> {
  void setSingularThreshold(double paramDouble);
  
  int getRank();
  
  int[] getPivots();
  
  DenseMatrix64F getPivotMatrix(DenseMatrix64F paramDenseMatrix64F);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\QRPDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */