package ic2.shades.org.ejml.interfaces.linsol;

public interface ReducedRowEchelonForm<T extends ic2.shades.org.ejml.data.Matrix64F> {
  void reduce(T paramT, int paramInt);
  
  void setTolerance(double paramDouble);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\interfaces\linsol\ReducedRowEchelonForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */