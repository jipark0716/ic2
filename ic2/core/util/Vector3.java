/*     */ package ic2.core.util;
/*     */ 
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public final class Vector3 {
/*   6 */   public static final Vector3 UP = new Vector3(0.0D, 1.0D, 0.0D);
/*     */   
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public Vector3(double x1, double y1, double z1) {
/*  12 */     this.x = x1;
/*  13 */     this.y = y1;
/*  14 */     this.z = z1;
/*     */   } public double z;
/*     */   public Vector3() {}
/*     */   public Vector3(Vector3 v) {
/*  18 */     this(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public Vector3(Vec3d v) {
/*  22 */     this(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public Vector3 copy() {
/*  26 */     return new Vector3(this);
/*     */   }
/*     */   
/*     */   public Vector3 copy(Vector3 dst) {
/*  30 */     return dst.set(this);
/*     */   }
/*     */   
/*     */   public Vector3 set(double vx, double vy, double vz) {
/*  34 */     this.x = vx;
/*  35 */     this.y = vy;
/*  36 */     this.z = vz;
/*     */     
/*  38 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3 set(Vector3 v) {
/*  42 */     return set(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public Vector3 set(Vec3d v) {
/*  46 */     return set(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public Vector3 add(double vx, double vy, double vz) {
/*  50 */     this.x += vx;
/*  51 */     this.y += vy;
/*  52 */     this.z += vz;
/*     */     
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3 add(Vector3 v) {
/*  58 */     return add(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public Vector3 addScaled(Vector3 v, double scale) {
/*  62 */     return add(v.x * scale, v.y * scale, v.z * scale);
/*     */   }
/*     */   
/*     */   public Vector3 sub(double vx, double vy, double vz) {
/*  66 */     this.x -= vx;
/*  67 */     this.y -= vy;
/*  68 */     this.z -= vz;
/*     */     
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3 sub(Vector3 v) {
/*  74 */     return sub(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public Vector3 cross(double vx, double vy, double vz) {
/*  78 */     return set(this.y * vz - this.z * vy, this.z * vx - this.x * vz, this.x * vy - this.y * vx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 cross(Vector3 v) {
/*  84 */     return cross(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public double dot(double vx, double vy, double vz) {
/*  88 */     return this.x * vx + this.y * vy + this.z * vz;
/*     */   }
/*     */   
/*     */   public double dot(Vector3 v) {
/*  92 */     return dot(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public Vector3 normalize() {
/*  96 */     double len = length();
/*     */     
/*  98 */     this.x /= len;
/*  99 */     this.y /= len;
/* 100 */     this.z /= len;
/*     */     
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public double lengthSquared() {
/* 106 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */   
/*     */   public double length() {
/* 110 */     return Math.sqrt(lengthSquared());
/*     */   }
/*     */   
/*     */   public Vector3 negate() {
/* 114 */     this.x = -this.x;
/* 115 */     this.y = -this.y;
/* 116 */     this.z = -this.z;
/*     */     
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public double distanceSquared(double vx, double vy, double vz) {
/* 122 */     double dx = vx - this.x;
/* 123 */     double dy = vy - this.y;
/* 124 */     double dz = vz - this.z;
/*     */     
/* 126 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */   
/*     */   public double distanceSquared(Vector3 v) {
/* 130 */     return distanceSquared(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public double distanceSquared(Vec3d v) {
/* 134 */     return distanceSquared(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public double distance(double vx, double vy, double vz) {
/* 138 */     return Math.sqrt(distanceSquared(vx, vy, vz));
/*     */   }
/*     */   
/*     */   public double distance(Vector3 v) {
/* 142 */     return distance(v.x, v.y, v.z);
/*     */   }
/*     */   
/*     */   public double distance(Vec3d v) {
/* 146 */     return distance(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public Vector3 scale(double factor) {
/* 150 */     this.x *= factor;
/* 151 */     this.y *= factor;
/* 152 */     this.z *= factor;
/*     */     
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3 scaleTo(double len) {
/* 158 */     double factor = len / length();
/*     */     
/* 160 */     return scale(factor);
/*     */   }
/*     */   
/*     */   public Vec3d toVec3() {
/* 164 */     return new Vec3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 169 */     return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Vector3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */