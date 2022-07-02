package ic2.shades.org.ejml.interfaces.decomposition;

import ic2.shades.org.ejml.data.Complex64F;

public interface EigenDecomposition<MatrixType extends ic2.shades.org.ejml.data.Matrix64F> extends DecompositionInterface<MatrixType> {
  int getNumberOfEigenvalues();
  
  Complex64F getEigenvalue(int paramInt);
  
  MatrixType getEigenVector(int paramInt);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\EigenDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */