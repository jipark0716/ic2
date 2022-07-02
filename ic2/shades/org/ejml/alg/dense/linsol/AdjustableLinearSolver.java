package ic2.shades.org.ejml.alg.dense.linsol;

import ic2.shades.org.ejml.data.DenseMatrix64F;
import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;

public interface AdjustableLinearSolver extends LinearSolver<DenseMatrix64F> {
  boolean addRowToA(double[] paramArrayOfdouble, int paramInt);
  
  boolean removeRowFromA(int paramInt);
}


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\AdjustableLinearSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */