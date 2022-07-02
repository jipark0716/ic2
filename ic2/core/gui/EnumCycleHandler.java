/*    */ package ic2.core.gui;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ 
/*    */ public class EnumCycleHandler<E extends Enum<E>> extends CycleHandler {
/*    */   public EnumCycleHandler(E[] set) {
/*  8 */     this(set, set[0]);
/*    */   }
/*    */   protected final E[] set;
/*    */   public EnumCycleHandler(E[] set, E start) {
/* 12 */     this(0, 0, 0, 0, 0, false, set, start);
/*    */   }
/*    */   
/*    */   public EnumCycleHandler(int uS, int vS, int uE, int vE, int overlayStep, boolean vertical, E[] set, E start) {
/* 16 */     super(uS, vS, uE, vE, overlayStep, vertical, set.length, new INumericValueHandler((Enum[])set, (Enum)start) {
/*    */           private int[] makeIndexMap() {
/* 18 */             int[] ret = new int[((Enum)Collections.max(Arrays.asList((T[])set))).ordinal() + 1];
/*    */             
/* 20 */             for (int index = 0; index < set.length; index++) {
/* 21 */               ret[set[index].ordinal()] = index;
/*    */             }
/*    */             
/* 24 */             return ret;
/*    */           }
/*    */ 
/*    */           
/*    */           public void onChange(int value) {
/* 29 */             assert value >= 0 && value < set.length;
/* 30 */             this.currentValue = (E)set[value];
/*    */           }
/*    */ 
/*    */           
/*    */           public int getValue() {
/* 35 */             return this.index[this.currentValue.ordinal()];
/*    */           }
/*    */           
/* 38 */           private E currentValue = (E)start;
/* 39 */           private final int[] index = makeIndexMap();
/*    */         });
/*    */     
/* 42 */     this.set = set;
/*    */   }
/*    */   
/*    */   public E getCurrentValue() {
/* 46 */     return this.set[getValue()];
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\EnumCycleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */