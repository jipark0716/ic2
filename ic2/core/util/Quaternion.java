/*    */ package ic2.core.util;
/*    */ 
/*    */ 
/*    */ public final class Quaternion
/*    */ {
/*    */   public Vector3 v;
/*    */   public double w;
/*    */   
/*    */   public Quaternion() {}
/*    */   
/*    */   public Quaternion(Vector3 v1, double w1) {
/* 12 */     this(v1, w1, true);
/*    */   }
/*    */   
/*    */   private Quaternion(Vector3 v1, double w1, boolean copyV) {
/* 16 */     this.v = copyV ? v1.copy() : v1;
/* 17 */     this.w = w1;
/*    */   }
/*    */   
/*    */   public Quaternion(double x, double y, double z, double w1) {
/* 21 */     this(new Vector3(x, y, z), w1, false);
/*    */   }
/*    */   
/*    */   public Quaternion(Quaternion q) {
/* 25 */     this(q.v, q.w, true);
/*    */   }
/*    */   
/*    */   public Quaternion set(Vector3 v1, double w1, boolean copyV) {
/* 29 */     this.v = copyV ? v1.copy() : v1;
/* 30 */     this.w = w1;
/*    */     
/* 32 */     return this;
/*    */   }
/*    */   
/*    */   public Quaternion set(double x, double y, double z, double w1) {
/* 36 */     this.v.x = x;
/* 37 */     this.v.y = y;
/* 38 */     this.v.z = z;
/* 39 */     this.w = w1;
/*    */     
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   public Quaternion setFromAxisAngle(Vector3 axis, double angle) {
/* 45 */     return set(axis.copy().scale(Math.sin(angle / 2.0D)), Math.cos(angle / 2.0D), false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Quaternion mul(Quaternion q) {
/* 51 */     return set(this.v.copy().scale(q.w).add(q.v.copy().scale(this.w)).add(this.v.copy().cross(q.v)), this.w * q.w - this.v
/* 52 */         .dot(q.v), false);
/*    */   }
/*    */   
/*    */   public Quaternion inverse() {
/* 56 */     return set(this.v.negate(), this.w, false);
/*    */   }
/*    */   
/*    */   public Vector3 rotate(Vector3 p) {
/* 60 */     Vector3 vxp = this.v.copy().cross(p);
/*    */ 
/*    */     
/* 63 */     p.set(p.add(this.v.copy().cross(vxp).scale(2.0D)).add(vxp.scale(2.0D * this.w)));
/*    */     
/* 65 */     return p;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Quaternion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */