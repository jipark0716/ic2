/*   */ package ic2.core.gui;
/*   */ 
/*   */ public enum ScrollDirection {
/* 4 */   stopped(0), up(-1), down(1); public final byte multiplier;
/*   */   
/*   */   ScrollDirection(int multiplier) {
/* 7 */     this.multiplier = (byte)multiplier;
/*   */   }
/*   */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\ScrollDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */