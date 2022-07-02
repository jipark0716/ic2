package ic2.shades.org.ejml.interfaces.decomposition;

public interface DecompositionInterface<T extends ic2.shades.org.ejml.data.Matrix64F> {
  boolean decompose(T paramT);
  
  boolean inputModified();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\decomposition\DecompositionInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */