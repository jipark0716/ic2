/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotationUtil
/*     */ {
/*     */   public static EnumFacing rotateByRay(RayTraceResult ray) {
/*  14 */     assert ray.field_72313_a == RayTraceResult.Type.BLOCK;
/*  15 */     Vec3d hit = ray.field_72307_f;
/*  16 */     BlockPos pos = ray.func_178782_a();
/*  17 */     return rotateByHit(ray.field_178784_b, (float)hit.field_72450_a - pos.func_177958_n(), (float)hit.field_72448_b - pos.func_177956_o(), (float)hit.field_72449_c - pos.func_177952_p());
/*     */   }
/*     */   
/*     */   public static EnumFacing rotateByHit(EnumFacing facingHit, float hitX, float hitY, float hitZ) {
/*  21 */     switch (facingHit) {
/*     */       case DOWN:
/*  23 */         if (hitX <= 0.25F) {
/*  24 */           if (hitZ > 0.25F && hitZ < 0.75F) {
/*  25 */             return EnumFacing.WEST;
/*     */           }
/*  27 */           return EnumFacing.UP;
/*     */         } 
/*  29 */         if (hitX > 0.25F && hitX < 0.75F) {
/*  30 */           if (hitZ <= 0.25F)
/*  31 */             return EnumFacing.NORTH; 
/*  32 */           if (hitZ >= 0.75F) {
/*  33 */             return EnumFacing.SOUTH;
/*     */           }
/*  35 */           return EnumFacing.DOWN;
/*     */         } 
/*  37 */         if (hitX >= 0.75F) {
/*  38 */           if (hitZ > 0.25F && hitZ < 0.75F) {
/*  39 */             return EnumFacing.EAST;
/*     */           }
/*  41 */           return EnumFacing.UP;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case UP:
/*  46 */         if (hitX <= 0.25F) {
/*  47 */           if (hitZ > 0.25F && hitZ < 0.75F) {
/*  48 */             return EnumFacing.WEST;
/*     */           }
/*  50 */           return EnumFacing.DOWN;
/*     */         } 
/*  52 */         if (hitX > 0.25F && hitX < 0.75F) {
/*  53 */           if (hitZ <= 0.25F)
/*  54 */             return EnumFacing.NORTH; 
/*  55 */           if (hitZ >= 0.75F) {
/*  56 */             return EnumFacing.SOUTH;
/*     */           }
/*  58 */           return EnumFacing.UP;
/*     */         } 
/*  60 */         if (hitX >= 0.75F) {
/*  61 */           if (hitZ > 0.25F && hitZ < 0.75F) {
/*  62 */             return EnumFacing.EAST;
/*     */           }
/*  64 */           return EnumFacing.DOWN;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  69 */         if (hitX <= 0.25F) {
/*  70 */           if (hitY > 0.25F && hitY < 0.75F) {
/*  71 */             return EnumFacing.WEST;
/*     */           }
/*  73 */           return EnumFacing.SOUTH;
/*     */         } 
/*  75 */         if (hitX > 0.25F && hitX < 0.75F) {
/*  76 */           if (hitY <= 0.25F)
/*  77 */             return EnumFacing.DOWN; 
/*  78 */           if (hitY >= 0.75F) {
/*  79 */             return EnumFacing.UP;
/*     */           }
/*  81 */           return EnumFacing.NORTH;
/*     */         } 
/*  83 */         if (hitX >= 0.75F) {
/*  84 */           if (hitY > 0.25F && hitY < 0.75F) {
/*  85 */             return EnumFacing.EAST;
/*     */           }
/*  87 */           return EnumFacing.SOUTH;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  92 */         if (hitX <= 0.25F) {
/*  93 */           if (hitY > 0.25F && hitY < 0.75F) {
/*  94 */             return EnumFacing.WEST;
/*     */           }
/*  96 */           return EnumFacing.NORTH;
/*     */         } 
/*  98 */         if (hitX > 0.25F && hitX < 0.75F) {
/*  99 */           if (hitY <= 0.25F)
/* 100 */             return EnumFacing.DOWN; 
/* 101 */           if (hitY >= 0.75F) {
/* 102 */             return EnumFacing.UP;
/*     */           }
/* 104 */           return EnumFacing.SOUTH;
/*     */         } 
/* 106 */         if (hitX >= 0.75F) {
/* 107 */           if (hitY > 0.25F && hitY < 0.75F) {
/* 108 */             return EnumFacing.EAST;
/*     */           }
/* 110 */           return EnumFacing.NORTH;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case WEST:
/* 115 */         if (hitZ <= 0.25F) {
/* 116 */           if (hitY > 0.25F && hitY < 0.75F) {
/* 117 */             return EnumFacing.NORTH;
/*     */           }
/* 119 */           return EnumFacing.EAST;
/*     */         } 
/* 121 */         if (hitZ > 0.25F && hitZ < 0.75F) {
/* 122 */           if (hitY <= 0.25F)
/* 123 */             return EnumFacing.DOWN; 
/* 124 */           if (hitY >= 0.75F) {
/* 125 */             return EnumFacing.UP;
/*     */           }
/* 127 */           return EnumFacing.WEST;
/*     */         } 
/* 129 */         if (hitZ >= 0.75F) {
/* 130 */           if (hitY > 0.25F && hitY < 0.75F) {
/* 131 */             return EnumFacing.SOUTH;
/*     */           }
/* 133 */           return EnumFacing.EAST;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case EAST:
/* 138 */         if (hitZ <= 0.25F) {
/* 139 */           if (hitY > 0.25F && hitY < 0.75F) {
/* 140 */             return EnumFacing.NORTH;
/*     */           }
/* 142 */           return EnumFacing.WEST;
/*     */         } 
/* 144 */         if (hitZ > 0.25F && hitZ < 0.75F) {
/* 145 */           if (hitY <= 0.25F)
/* 146 */             return EnumFacing.DOWN; 
/* 147 */           if (hitY >= 0.75F) {
/* 148 */             return EnumFacing.UP;
/*     */           }
/* 150 */           return EnumFacing.EAST;
/*     */         } 
/* 152 */         if (hitZ >= 0.75F) {
/* 153 */           if (hitY > 0.25F && hitY < 0.75F) {
/* 154 */             return EnumFacing.SOUTH;
/*     */           }
/* 156 */           return EnumFacing.WEST;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return facingHit;
/*     */   }
/*     */   
/*     */   public static int[] shuffledFacings() {
/* 166 */     int[] ordinals = { 0, 1, 2, 3, 4, 5 };
/*     */     
/* 168 */     for (int i = ordinals.length - 1; i > 0; i--) {
/* 169 */       int index = IC2.random.nextInt(i + 1);
/* 170 */       if (index != i) {
/* 171 */         ordinals[index] = ordinals[index] ^ ordinals[i];
/* 172 */         ordinals[i] = ordinals[i] ^ ordinals[index];
/* 173 */         ordinals[index] = ordinals[index] ^ ordinals[i];
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return ordinals;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */