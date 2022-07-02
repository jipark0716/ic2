package ic2.shades.org.ejml.interfaces.decomposition;

public interface QRDecomposition<T extends ic2.shades.org.ejml.data.Matrix64F> extends DecompositionInterface<T> {
  T getQ(T paramT, boolean paramBoolean);
  
  T getR(T paramT, boolean paramBoolean);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\QRDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */