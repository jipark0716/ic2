package ic2.shades.org.ejml.interfaces.decomposition;

public interface CholeskyDecomposition<MatrixType extends ic2.shades.org.ejml.data.Matrix64F> extends DecompositionInterface<MatrixType> {
  boolean isLower();
  
  MatrixType getT(MatrixType paramMatrixType);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\CholeskyDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */