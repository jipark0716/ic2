/*     */ package ic2.core.util;
/*     */ 
/*     */ 
/*     */ public class Vector2
/*     */ {
/*     */   public double x;
/*     */   
/*     */   public Vector2(double x, double y) {
/*   9 */     this.x = x;
/*  10 */     this.y = y;
/*     */   } public double y;
/*     */   public Vector2() {}
/*     */   public Vector2(Vector2 v) {
/*  14 */     this(v.x, v.y);
/*     */   }
/*     */   
/*     */   public Vector2 copy() {
/*  18 */     return new Vector2(this);
/*     */   }
/*     */   
/*     */   public Vector2 copy(Vector2 dst) {
/*  22 */     return dst.set(this);
/*     */   }
/*     */   
/*     */   public Vector2 set(double vx, double vy) {
/*  26 */     this.x = vx;
/*  27 */     this.y = vy;
/*     */     
/*  29 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2 set(Vector2 v) {
/*  33 */     return set(v.x, v.y);
/*     */   }
/*     */   
/*     */   public Vector2 add(double vx, double vy) {
/*  37 */     this.x += vx;
/*  38 */     this.y += vy;
/*     */     
/*  40 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2 add(Vector2 v) {
/*  44 */     return add(v.x, v.y);
/*     */   }
/*     */   
/*     */   public Vector2 sub(double vx, double vy) {
/*  48 */     this.x -= vx;
/*  49 */     this.y -= vy;
/*     */     
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2 sub(Vector2 v) {
/*  55 */     return sub(v.x, v.y);
/*     */   }
/*     */   
/*     */   public double dot(double vx, double vy) {
/*  59 */     return this.x * vx + this.y * vy;
/*     */   }
/*     */   
/*     */   public double dot(Vector2 v) {
/*  63 */     return dot(v.x, v.y);
/*     */   }
/*     */   
/*     */   public Vector2 normalize() {
/*  67 */     double len = length();
/*     */     
/*  69 */     this.x /= len;
/*  70 */     this.y /= len;
/*     */     
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public double lengthSquared() {
/*  76 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */   
/*     */   public double length() {
/*  80 */     return Math.sqrt(lengthSquared());
/*     */   }
/*     */   
/*     */   public Vector2 negate() {
/*  84 */     this.x = -this.x;
/*  85 */     this.y = -this.y;
/*     */     
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public double distanceSquared(double vx, double vy) {
/*  91 */     double dx = vx - this.x;
/*  92 */     double dy = vy - this.y;
/*     */     
/*  94 */     return dx * dx + dy * dy;
/*     */   }
/*     */   
/*     */   public double distanceSquared(Vector2 v) {
/*  98 */     return distanceSquared(v.x, v.y);
/*     */   }
/*     */   
/*     */   public double distance(double vx, double vy) {
/* 102 */     return Math.sqrt(distanceSquared(vx, vy));
/*     */   }
/*     */   
/*     */   public double distance(Vector2 v) {
/* 106 */     return distance(v.x, v.y);
/*     */   }
/*     */   
/*     */   public Vector2 scale(double factor) {
/* 110 */     this.x *= factor;
/* 111 */     this.y *= factor;
/*     */     
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public Vector2 scaleTo(double len) {
/* 117 */     double factor = len / length();
/*     */     
/* 119 */     return scale(factor);
/*     */   }
/*     */   
/*     */   public Vector2 rotate(double angle) {
/* 123 */     double cos = Math.cos(angle);
/* 124 */     double sin = Math.sin(angle);
/*     */     
/* 126 */     return set(cos * this.x - sin * this.y, sin * this.x + cos * this.y);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Vector2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */