package ic2.shades.org.ejml.interfaces.linsol;

public interface LinearSolver<T extends ic2.shades.org.ejml.data.Matrix64F> {
  boolean setA(T paramT);
  
  double quality();
  
  void solve(T paramT1, T paramT2);
  
  void invert(T paramT);
  
  boolean modifiesA();
  
  boolean modifiesB();
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\linsol\LinearSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */