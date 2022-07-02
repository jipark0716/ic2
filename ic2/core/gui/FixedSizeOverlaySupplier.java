/*    */ package ic2.core.gui;public abstract class FixedSizeOverlaySupplier implements IOverlaySupplier {
/*    */   private final int width;
/*    */   
/*    */   public FixedSizeOverlaySupplier(int size) {
/*  5 */     this(size, size);
/*    */   }
/*    */   private final int height;
/*    */   public FixedSizeOverlaySupplier(int width, int height) {
/*  9 */     this.width = width;
/* 10 */     this.height = height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUE() {
/* 15 */     return getUS() + this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVE() {
/* 20 */     return getVS() + this.height;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\FixedSizeOverlaySupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */