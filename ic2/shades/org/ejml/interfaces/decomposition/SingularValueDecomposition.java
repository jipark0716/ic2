package ic2.shades.org.ejml.interfaces.decomposition;

public interface SingularValueDecomposition<T extends ic2.shades.org.ejml.data.Matrix64F> extends DecompositionInterface<T> {
  double[] getSingularValues();
  
  int numberOfSingularValues();
  
  boolean isCompact();
  
  T getU(T paramT, boolean paramBoolean);
  
  T getV(T paramT, boolean paramBoolean);
  
  T getW(T paramT);
  
  int numRows();
  
  int numCols();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\SingularValueDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */