/*     */ package ic2.core.util;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ public class AabbUtil
/*     */ {
/*     */   enum Edge
/*     */   {
/*  12 */     AD, AB, AE, DC, DH, BC, BF, EH, EF, CG, FG, HG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing getIntersection(Vec3d origin, Vec3d direction, AxisAlignedBB bbox, MutableObject<Vec3d> intersection) {
/*     */     Vec3d planeOrigin;
/*  23 */     double length = Util.square(direction.field_72450_a) + Util.square(direction.field_72448_b) + Util.square(direction.field_72449_c);
/*     */     
/*  25 */     if (Math.abs(length - 1.0D) > 1.0E-5D) {
/*  26 */       length = Math.sqrt(length);
/*  27 */       direction = new Vec3d(direction.field_72450_a / length, direction.field_72448_b / length, direction.field_72449_c / length);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  32 */     EnumFacing intersectingDirection = intersects(origin, direction, bbox);
/*  33 */     if (intersectingDirection == null) return null;
/*     */ 
/*     */ 
/*     */     
/*  37 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/*  38 */       planeOrigin = new Vec3d(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72334_f);
/*  39 */     } else if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/*  40 */       planeOrigin = new Vec3d(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72339_c);
/*  41 */     } else if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/*  42 */       planeOrigin = new Vec3d(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72334_f);
/*  43 */     } else if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c >= 0.0D) {
/*  44 */       planeOrigin = new Vec3d(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72339_c);
/*  45 */     } else if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/*  46 */       planeOrigin = new Vec3d(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72334_f);
/*  47 */     } else if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/*  48 */       planeOrigin = new Vec3d(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72339_c);
/*  49 */     } else if (direction.field_72450_a >= 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/*  50 */       planeOrigin = new Vec3d(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72334_f);
/*     */     } else {
/*  52 */       planeOrigin = new Vec3d(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72339_c);
/*     */     } 
/*     */     
/*  55 */     Vec3d planeNormalVector = null;
/*     */     
/*  57 */     switch (intersectingDirection) {
/*     */       case AD:
/*     */       case AB:
/*  60 */         planeNormalVector = new Vec3d(1.0D, 0.0D, 0.0D);
/*     */         break;
/*     */       case AE:
/*     */       case DC:
/*  64 */         planeNormalVector = new Vec3d(0.0D, 1.0D, 0.0D);
/*     */         break;
/*     */       case DH:
/*     */       case BC:
/*  68 */         planeNormalVector = new Vec3d(0.0D, 0.0D, 1.0D);
/*     */         break;
/*     */     } 
/*     */     
/*  72 */     if (intersection != null) {
/*  73 */       intersection.setValue(getIntersectionWithPlane(origin, direction, planeOrigin, planeNormalVector));
/*     */     }
/*     */     
/*  76 */     return intersectingDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing intersects(Vec3d origin, Vec3d direction, AxisAlignedBB bbox) {
/*  85 */     double[] ray = getRay(origin, direction);
/*     */     
/*  87 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/*  88 */       if (origin.field_72450_a < bbox.field_72340_a) return null; 
/*  89 */       if (origin.field_72448_b < bbox.field_72338_b) return null; 
/*  90 */       if (origin.field_72449_c < bbox.field_72339_c) return null; 
/*  91 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D) return null; 
/*  92 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) return null; 
/*  93 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D) return null; 
/*  94 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) return null; 
/*  95 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D) return null; 
/*  96 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) return null;
/*     */       
/*  98 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
/*  99 */         return EnumFacing.SOUTH; 
/* 100 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) {
/* 101 */         return EnumFacing.UP;
/*     */       }
/* 103 */       return EnumFacing.EAST;
/*     */     } 
/* 105 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/* 106 */       if (origin.field_72450_a < bbox.field_72340_a) return null; 
/* 107 */       if (origin.field_72448_b < bbox.field_72338_b) return null; 
/* 108 */       if (origin.field_72449_c > bbox.field_72334_f) return null; 
/* 109 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D) return null; 
/* 110 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D) return null; 
/* 111 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D) return null; 
/* 112 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D) return null; 
/* 113 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) return null; 
/* 114 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) return null;
/*     */       
/* 116 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
/* 117 */         return EnumFacing.EAST; 
/* 118 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) {
/* 119 */         return EnumFacing.UP;
/*     */       }
/* 121 */       return EnumFacing.NORTH;
/*     */     } 
/* 123 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/* 124 */       if (origin.field_72450_a < bbox.field_72340_a) return null; 
/* 125 */       if (origin.field_72448_b > bbox.field_72337_e) return null; 
/* 126 */       if (origin.field_72449_c < bbox.field_72339_c) return null; 
/* 127 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D) return null; 
/* 128 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D) return null; 
/* 129 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D) return null; 
/* 130 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) return null; 
/* 131 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) return null; 
/* 132 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) return null;
/*     */       
/* 134 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
/* 135 */         return EnumFacing.SOUTH; 
/* 136 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) {
/* 137 */         return EnumFacing.EAST;
/*     */       }
/* 139 */       return EnumFacing.DOWN;
/*     */     } 
/* 141 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c >= 0.0D) {
/* 142 */       if (origin.field_72450_a < bbox.field_72340_a) return null; 
/* 143 */       if (origin.field_72448_b > bbox.field_72337_e) return null; 
/* 144 */       if (origin.field_72449_c > bbox.field_72334_f) return null; 
/* 145 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D) return null; 
/* 146 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D) return null; 
/* 147 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D) return null; 
/* 148 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) return null; 
/* 149 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D) return null; 
/* 150 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D) return null;
/*     */       
/* 152 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
/* 153 */         return EnumFacing.DOWN; 
/* 154 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D) {
/* 155 */         return EnumFacing.NORTH;
/*     */       }
/* 157 */       return EnumFacing.EAST;
/*     */     } 
/* 159 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/* 160 */       if (origin.field_72450_a > bbox.field_72336_d) return null; 
/* 161 */       if (origin.field_72448_b < bbox.field_72338_b) return null; 
/* 162 */       if (origin.field_72449_c < bbox.field_72339_c) return null; 
/* 163 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D) return null; 
/* 164 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D) return null; 
/* 165 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) return null; 
/* 166 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D) return null; 
/* 167 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D) return null; 
/* 168 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D) return null;
/*     */       
/* 170 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
/* 171 */         return EnumFacing.WEST; 
/* 172 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) {
/* 173 */         return EnumFacing.SOUTH;
/*     */       }
/* 175 */       return EnumFacing.UP;
/*     */     } 
/* 177 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/* 178 */       if (origin.field_72450_a > bbox.field_72336_d) return null; 
/* 179 */       if (origin.field_72448_b < bbox.field_72338_b) return null; 
/* 180 */       if (origin.field_72449_c > bbox.field_72334_f) return null; 
/* 181 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D) return null; 
/* 182 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D) return null; 
/* 183 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D) return null; 
/* 184 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D) return null; 
/* 185 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D) return null; 
/* 186 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D) return null;
/*     */       
/* 188 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
/* 189 */         return EnumFacing.NORTH; 
/* 190 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D) {
/* 191 */         return EnumFacing.WEST;
/*     */       }
/* 193 */       return EnumFacing.UP;
/*     */     } 
/* 195 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/* 196 */       if (origin.field_72450_a > bbox.field_72336_d) return null; 
/* 197 */       if (origin.field_72448_b > bbox.field_72337_e) return null; 
/* 198 */       if (origin.field_72449_c < bbox.field_72339_c) return null; 
/* 199 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D) return null; 
/* 200 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D) return null; 
/* 201 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) return null; 
/* 202 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) return null; 
/* 203 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D) return null; 
/* 204 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D) return null;
/*     */       
/* 206 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
/* 207 */         return EnumFacing.WEST; 
/* 208 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D) {
/* 209 */         return EnumFacing.DOWN;
/*     */       }
/* 211 */       return EnumFacing.SOUTH;
/*     */     } 
/*     */     
/* 214 */     if (origin.field_72450_a > bbox.field_72336_d) return null; 
/* 215 */     if (origin.field_72448_b > bbox.field_72337_e) return null; 
/* 216 */     if (origin.field_72449_c > bbox.field_72334_f) return null; 
/* 217 */     if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D) return null; 
/* 218 */     if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D) return null; 
/* 219 */     if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D) return null; 
/* 220 */     if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D) return null; 
/* 221 */     if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D) return null; 
/* 222 */     if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D) return null;
/*     */     
/* 224 */     if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D && side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
/* 225 */       return EnumFacing.WEST; 
/* 226 */     if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D) {
/* 227 */       return EnumFacing.NORTH;
/*     */     }
/* 229 */     return EnumFacing.DOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] getRay(Vec3d origin, Vec3d direction) {
/* 238 */     double[] ret = new double[6];
/*     */     
/* 240 */     ret[0] = origin.field_72450_a * direction.field_72448_b - direction.field_72450_a * origin.field_72448_b;
/* 241 */     ret[1] = origin.field_72450_a * direction.field_72449_c - direction.field_72450_a * origin.field_72449_c;
/* 242 */     ret[2] = -direction.field_72450_a;
/* 243 */     ret[3] = origin.field_72448_b * direction.field_72449_c - direction.field_72448_b * origin.field_72449_c;
/* 244 */     ret[4] = -direction.field_72449_c;
/* 245 */     ret[5] = direction.field_72448_b;
/*     */     
/* 247 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] getEdgeRay(Edge edge, AxisAlignedBB bbox) {
/* 254 */     switch (edge) {
/*     */       case AD:
/* 256 */         return new double[] { -bbox.field_72338_b, -bbox.field_72339_c, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */       case AB:
/* 258 */         return new double[] { bbox.field_72340_a, 0.0D, 0.0D, -bbox.field_72339_c, 0.0D, 1.0D };
/*     */       case AE:
/* 260 */         return new double[] { 0.0D, bbox.field_72340_a, 0.0D, bbox.field_72338_b, -1.0D, 0.0D };
/*     */       case DC:
/* 262 */         return new double[] { bbox.field_72336_d, 0.0D, 0.0D, -bbox.field_72339_c, 0.0D, 1.0D };
/*     */       case DH:
/* 264 */         return new double[] { 0.0D, bbox.field_72336_d, 0.0D, bbox.field_72338_b, -1.0D, 0.0D };
/*     */       case BC:
/* 266 */         return new double[] { -bbox.field_72337_e, -bbox.field_72339_c, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */       case BF:
/* 268 */         return new double[] { 0.0D, bbox.field_72340_a, 0.0D, bbox.field_72337_e, -1.0D, 0.0D };
/*     */       case EH:
/* 270 */         return new double[] { -bbox.field_72338_b, -bbox.field_72334_f, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */       case EF:
/* 272 */         return new double[] { bbox.field_72340_a, 0.0D, 0.0D, -bbox.field_72334_f, 0.0D, 1.0D };
/*     */       case CG:
/* 274 */         return new double[] { 0.0D, bbox.field_72336_d, 0.0D, bbox.field_72337_e, -1.0D, 0.0D };
/*     */       case FG:
/* 276 */         return new double[] { -bbox.field_72337_e, -bbox.field_72334_f, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */       case HG:
/* 278 */         return new double[] { bbox.field_72336_d, 0.0D, 0.0D, -bbox.field_72334_f, 0.0D, 1.0D };
/*     */     } 
/* 280 */     return new double[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double side(double[] ray1, double[] ray2) {
/* 291 */     return ray1[2] * ray2[3] + ray1[5] * ray2[1] + ray1[4] * ray2[0] + ray1[1] * ray2[5] + ray1[0] * ray2[4] + ray1[3] * ray2[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vec3d getIntersectionWithPlane(Vec3d origin, Vec3d direction, Vec3d planeOrigin, Vec3d planeNormalVector) {
/* 300 */     double distance = getDistanceToPlane(origin, direction, planeOrigin, planeNormalVector);
/*     */     
/* 302 */     return new Vec3d(origin.field_72450_a + direction.field_72450_a * distance, origin.field_72448_b + direction.field_72448_b * distance, origin.field_72449_c + direction.field_72449_c * distance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double getDistanceToPlane(Vec3d origin, Vec3d direction, Vec3d planeOrigin, Vec3d planeNormalVector) {
/* 313 */     Vec3d base = new Vec3d(planeOrigin.field_72450_a - origin.field_72450_a, planeOrigin.field_72448_b - origin.field_72448_b, planeOrigin.field_72449_c - origin.field_72449_c);
/*     */ 
/*     */ 
/*     */     
/* 317 */     return dotProduct(base, planeNormalVector) / dotProduct(direction, planeNormalVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double dotProduct(Vec3d a, Vec3d b) {
/* 324 */     return a.field_72450_a * b.field_72450_a + a.field_72448_b * b.field_72448_b + a.field_72449_c * b.field_72449_c;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\AabbUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */