package ic2.shades.org.ejml.interfaces.decomposition;

public interface TridiagonalSimilarDecomposition<MatrixType extends ic2.shades.org.ejml.data.ReshapeMatrix64F> extends DecompositionInterface<MatrixType> {
  MatrixType getT(MatrixType paramMatrixType);
  
  MatrixType getQ(MatrixType paramMatrixType, boolean paramBoolean);
  
  void getDiagonal(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\TridiagonalSimilarDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */