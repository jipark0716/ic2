package ic2.shades.org.ejml.alg.dense.decomposition.eig;

import ic2.shades.org.ejml.data.Complex64F;
import ic2.shades.org.ejml.data.DenseMatrix64F;

public interface EigenvalueExtractor {
  boolean process(DenseMatrix64F paramDenseMatrix64F);
  
  int getNumberOfEigenvalues();
  
  Complex64F[] getEigenvalues();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\EigenvalueExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */