/*     */ package ic2.core;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnhancedOverlay
/*     */ {
/*     */   public enum Segment
/*     */   {
/*  52 */     TOP_LEFT,
/*  53 */     TOP,
/*  54 */     TOP_RIGHT,
/*  55 */     LEFT,
/*  56 */     CENTRE,
/*  57 */     RIGHT,
/*  58 */     BOTTOM_LEFT,
/*  59 */     BOTTOM,
/*  60 */     BOTTOM_RIGHT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Segment forRayTrace(RayTraceResult ray) {
/*  70 */       assert ray.field_72313_a == RayTraceResult.Type.BLOCK;
/*  71 */       BlockPos blockPos = ray.func_178782_a();
/*  72 */       Vec3d hit = ray.field_72307_f;
/*  73 */       return forHit(ray.field_178784_b, hit.field_72450_a - blockPos.func_177958_n(), hit.field_72448_b - blockPos.func_177956_o(), hit.field_72449_c - blockPos.func_177952_p());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Segment forHit(EnumFacing face, double x, double y, double z) {
/*  88 */       switch (face) {
/*     */         case DOWN:
/*     */         case UP:
/*  91 */           return forHit(x, 1.0D - z);
/*     */         
/*     */         case NORTH:
/*  94 */           return forHit(1.0D - x, y);
/*     */         
/*     */         case SOUTH:
/*  97 */           return forHit(x, y);
/*     */         
/*     */         case WEST:
/* 100 */           return forHit(z, y);
/*     */         
/*     */         case EAST:
/* 103 */           return forHit(1.0D - z, y);
/*     */       } 
/*     */       
/* 106 */       throw new IllegalArgumentException("Unexpected face: " + face);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Segment forHit(double hitX, double hitY) {
/* 119 */       if (hitX <= 0.25D) {
/* 120 */         if (hitY <= 0.25D)
/* 121 */           return BOTTOM_LEFT; 
/* 122 */         if (hitY >= 0.75D) {
/* 123 */           return TOP_LEFT;
/*     */         }
/* 125 */         return LEFT;
/*     */       } 
/* 127 */       if (hitX > 0.25D && hitX < 0.75D) {
/* 128 */         if (hitY <= 0.25D)
/* 129 */           return BOTTOM; 
/* 130 */         if (hitY >= 0.75D) {
/* 131 */           return TOP;
/*     */         }
/* 133 */         return CENTRE;
/*     */       } 
/* 135 */       if (hitX >= 0.75D) {
/* 136 */         if (hitY <= 0.25D)
/* 137 */           return BOTTOM_RIGHT; 
/* 138 */         if (hitY >= 0.75D) {
/* 139 */           return TOP_RIGHT;
/*     */         }
/* 141 */         return RIGHT;
/*     */       } 
/*     */       
/* 144 */       throw new IllegalArgumentException("Unexpected hit values: [" + hitX + ", " + hitY + ']');
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum RawSegment
/*     */   {
/* 196 */     A_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 199 */         buffer.accept(0.5D, 0.0D, -0.5D);
/* 200 */         buffer.accept(0.25D, 0.0D, -0.5D);
/* 201 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 202 */         buffer.accept(0.5D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 205 */     B_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 208 */         buffer.accept(-0.25D, 0.0D, -0.5D);
/* 209 */         buffer.accept(0.25D, 0.0D, -0.5D);
/* 210 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 211 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 214 */     C_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 217 */         buffer.accept(-0.5D, 0.0D, -0.5D);
/* 218 */         buffer.accept(-0.25D, 0.0D, -0.5D);
/* 219 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 220 */         buffer.accept(-0.5D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 223 */     D_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 226 */         buffer.accept(0.5D, 0.0D, -0.25D);
/* 227 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 228 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 229 */         buffer.accept(0.5D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 232 */     E_CENTRE
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 235 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 236 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 237 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 238 */         buffer.accept(0.25D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 241 */     F_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 244 */         buffer.accept(-0.5D, 0.0D, -0.25D);
/* 245 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 246 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 247 */         buffer.accept(-0.5D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 250 */     G_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 253 */         buffer.accept(0.5D, 0.0D, 0.5D);
/* 254 */         buffer.accept(0.25D, 0.0D, 0.5D);
/* 255 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 256 */         buffer.accept(0.5D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 259 */     H_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 262 */         buffer.accept(-0.25D, 0.0D, 0.5D);
/* 263 */         buffer.accept(0.25D, 0.0D, 0.5D);
/* 264 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 265 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/*     */     
/* 269 */     I_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 272 */         buffer.accept(-0.5D, 0.0D, 0.5D);
/* 273 */         buffer.accept(-0.25D, 0.0D, 0.5D);
/* 274 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 275 */         buffer.accept(-0.5D, 0.0D, 0.25D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void draw(BufferBuilder buffer) {
/* 292 */       drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181675_d());
/*     */     }
/*     */     
/*     */     abstract void drawRaw(EnhancedOverlay.TripleDoubleConsumer param1TripleDoubleConsumer);
/*     */   }
/*     */   
/*     */   private enum XSegment
/*     */   {
/* 300 */     A_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 303 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 304 */         buffer.accept(0.5D, 0.0D, -0.5D);
/* 305 */         buffer.accept(0.25D, 0.0D, -0.5D);
/* 306 */         buffer.accept(0.5D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 309 */     B_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 312 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 313 */         buffer.accept(0.25D, 0.0D, -0.5D);
/* 314 */         buffer.accept(-0.25D, 0.0D, -0.5D);
/* 315 */         buffer.accept(0.25D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 318 */     C_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 321 */         buffer.accept(-0.5D, 0.0D, -0.25D);
/* 322 */         buffer.accept(-0.25D, 0.0D, -0.5D);
/* 323 */         buffer.accept(-0.5D, 0.0D, -0.5D);
/* 324 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/*     */       }
/*     */     },
/* 327 */     D_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 330 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 331 */         buffer.accept(0.5D, 0.0D, -0.25D);
/* 332 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 333 */         buffer.accept(0.5D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 336 */     E_CENTRE
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 339 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 340 */         buffer.accept(0.25D, 0.0D, -0.25D);
/* 341 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 342 */         buffer.accept(0.25D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 345 */     F_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 348 */         buffer.accept(-0.5D, 0.0D, 0.25D);
/* 349 */         buffer.accept(-0.25D, 0.0D, -0.25D);
/* 350 */         buffer.accept(-0.5D, 0.0D, -0.25D);
/* 351 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/*     */       }
/*     */     },
/* 354 */     G_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 357 */         buffer.accept(0.25D, 0.0D, 0.5D);
/* 358 */         buffer.accept(0.5D, 0.0D, 0.25D);
/* 359 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 360 */         buffer.accept(0.5D, 0.0D, 0.5D);
/*     */       }
/*     */     },
/* 363 */     H_BAR
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 366 */         buffer.accept(-0.25D, 0.0D, 0.5D);
/* 367 */         buffer.accept(0.25D, 0.0D, 0.25D);
/* 368 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 369 */         buffer.accept(0.25D, 0.0D, 0.5D);
/*     */       }
/*     */     },
/*     */     
/* 373 */     I_BOX
/*     */     {
/*     */       void drawRaw(EnhancedOverlay.TripleDoubleConsumer buffer) {
/* 376 */         buffer.accept(-0.5D, 0.0D, 0.5D);
/* 377 */         buffer.accept(-0.25D, 0.0D, 0.25D);
/* 378 */         buffer.accept(-0.5D, 0.0D, 0.25D);
/* 379 */         buffer.accept(-0.25D, 0.0D, 0.5D);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void draw(BufferBuilder buffer) {
/* 396 */       drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181675_d());
/*     */     }
/*     */     
/*     */     abstract void drawRaw(EnhancedOverlay.TripleDoubleConsumer param1TripleDoubleConsumer); }
/* 400 */   private static final Map<EnumFacing, EnhancedOverlay> SIDE_TO_OVERLAY = new EnumMap<>(EnumFacing.class);
/*     */   static {
/* 402 */     Map<Segment, RawSegment> segmentMap = new EnumMap<>(Segment.class);
/* 403 */     SIDE_TO_OVERLAY.put(EnumFacing.DOWN, new EnhancedOverlay(segmentMap));
/* 404 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.C_BOX);
/* 405 */     segmentMap.put(Segment.TOP, RawSegment.B_BAR);
/* 406 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.A_BOX);
/* 407 */     segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
/* 408 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 409 */     segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
/* 410 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.I_BOX);
/* 411 */     segmentMap.put(Segment.BOTTOM, RawSegment.H_BAR);
/* 412 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.G_BOX);
/*     */     
/* 414 */     segmentMap = new EnumMap<>(Segment.class);
/* 415 */     SIDE_TO_OVERLAY.put(EnumFacing.UP, new EnhancedOverlay(segmentMap));
/* 416 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.I_BOX);
/* 417 */     segmentMap.put(Segment.TOP, RawSegment.H_BAR);
/* 418 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.G_BOX);
/* 419 */     segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
/* 420 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 421 */     segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
/* 422 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.C_BOX);
/* 423 */     segmentMap.put(Segment.BOTTOM, RawSegment.B_BAR);
/* 424 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.A_BOX);
/*     */     
/* 426 */     segmentMap = new EnumMap<>(Segment.class);
/* 427 */     SIDE_TO_OVERLAY.put(EnumFacing.NORTH, new EnhancedOverlay(segmentMap));
/* 428 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.A_BOX);
/* 429 */     segmentMap.put(Segment.TOP, RawSegment.B_BAR);
/* 430 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.C_BOX);
/* 431 */     segmentMap.put(Segment.LEFT, RawSegment.D_BAR);
/* 432 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 433 */     segmentMap.put(Segment.RIGHT, RawSegment.F_BAR);
/* 434 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.G_BOX);
/* 435 */     segmentMap.put(Segment.BOTTOM, RawSegment.H_BAR);
/* 436 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.I_BOX);
/*     */     
/* 438 */     segmentMap = new EnumMap<>(Segment.class);
/* 439 */     SIDE_TO_OVERLAY.put(EnumFacing.SOUTH, new EnhancedOverlay(segmentMap));
/* 440 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.I_BOX);
/* 441 */     segmentMap.put(Segment.TOP, RawSegment.H_BAR);
/* 442 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.G_BOX);
/* 443 */     segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
/* 444 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 445 */     segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
/* 446 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.C_BOX);
/* 447 */     segmentMap.put(Segment.BOTTOM, RawSegment.B_BAR);
/* 448 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.A_BOX);
/*     */     
/* 450 */     segmentMap = new EnumMap<>(Segment.class);
/* 451 */     SIDE_TO_OVERLAY.put(EnumFacing.WEST, new EnhancedOverlay(segmentMap));
/* 452 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.C_BOX);
/* 453 */     segmentMap.put(Segment.TOP, RawSegment.F_BAR);
/* 454 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.I_BOX);
/* 455 */     segmentMap.put(Segment.LEFT, RawSegment.B_BAR);
/* 456 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 457 */     segmentMap.put(Segment.RIGHT, RawSegment.H_BAR);
/* 458 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.A_BOX);
/* 459 */     segmentMap.put(Segment.BOTTOM, RawSegment.D_BAR);
/* 460 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.G_BOX);
/*     */     
/* 462 */     segmentMap = new EnumMap<>(Segment.class);
/* 463 */     SIDE_TO_OVERLAY.put(EnumFacing.EAST, new EnhancedOverlay(segmentMap));
/* 464 */     segmentMap.put(Segment.TOP_LEFT, RawSegment.G_BOX);
/* 465 */     segmentMap.put(Segment.TOP, RawSegment.D_BAR);
/* 466 */     segmentMap.put(Segment.TOP_RIGHT, RawSegment.A_BOX);
/* 467 */     segmentMap.put(Segment.LEFT, RawSegment.H_BAR);
/* 468 */     segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
/* 469 */     segmentMap.put(Segment.RIGHT, RawSegment.B_BAR);
/* 470 */     segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.I_BOX);
/* 471 */     segmentMap.put(Segment.BOTTOM, RawSegment.F_BAR);
/* 472 */     segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.C_BOX);
/*     */   }
/*     */   
/*     */   private final Map<Segment, RawSegment> segmentMap;
/*     */   
/*     */   private EnhancedOverlay(Map<Segment, RawSegment> segmentMap) {
/* 478 */     this.segmentMap = segmentMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnhancedOverlay forFace(EnumFacing face) {
/* 489 */     return SIDE_TO_OVERLAY.get(face);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transformToFace(Entity entity, BlockPos pos, EnumFacing face, float partialTicks) {
/* 502 */     GlStateManager.func_179137_b(-(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * partialTicks), -(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * partialTicks), -(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * partialTicks));
/*     */ 
/*     */     
/* 505 */     GlStateManager.func_179109_b(pos.func_177958_n() + 0.5F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 0.5F);
/* 506 */     switch (face) {
/*     */       case UP:
/* 508 */         GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case NORTH:
/* 513 */         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case SOUTH:
/* 516 */         GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       case EAST:
/* 519 */         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       case WEST:
/* 522 */         GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */     } 
/* 525 */     GlStateManager.func_179137_b(0.0D, -0.501D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawArea(EnumFacing face, Segment... segments) {
/* 535 */     EnhancedOverlay overlay = forFace(face);
/* 536 */     BufferBuilder buffer = Tessellator.func_178181_a().func_178180_c();
/*     */     
/* 538 */     for (Segment segment : segments) {
/* 539 */       overlay.drawArea(segment, buffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawDebug(EnumFacing face) {
/* 549 */     EnhancedOverlay overlay = forFace(face);
/* 550 */     BufferBuilder buffer = Tessellator.func_178181_a().func_178180_c();
/*     */     
/* 552 */     GlStateManager.func_179090_x();
/*     */     
/* 554 */     overlay.drawArea(Segment.TOP_LEFT, buffer, 255, 0, 0);
/* 555 */     overlay.drawArea(Segment.TOP, buffer, 255, 127, 0);
/* 556 */     overlay.drawArea(Segment.TOP_RIGHT, buffer, 255, 255, 0);
/*     */     
/* 558 */     overlay.drawArea(Segment.LEFT, buffer, 0, 255, 0);
/* 559 */     overlay.drawArea(Segment.CENTRE, buffer, 0, 255, 127);
/* 560 */     overlay.drawArea(Segment.RIGHT, buffer, 0, 255, 255);
/*     */     
/* 562 */     overlay.drawArea(Segment.BOTTOM_LEFT, buffer, 0, 0, 255);
/* 563 */     overlay.drawArea(Segment.BOTTOM, buffer, 127, 0, 255);
/* 564 */     overlay.drawArea(Segment.BOTTOM_RIGHT, buffer, 255, 0, 255);
/*     */     
/* 566 */     GlStateManager.func_179098_w();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLines(Segment segment, BufferBuilder buffer) {
/* 576 */     buffer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 577 */     ((RawSegment)this.segmentMap.get(segment)).draw(buffer);
/* 578 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLines(Segment segment, BufferBuilder buffer, int red, int green, int blue) {
/* 591 */     drawLines(segment, buffer, red, green, blue, 255);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLines(Segment segment, BufferBuilder buffer, int red, int green, int blue, int alpha) {
/* 605 */     buffer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 606 */     ((RawSegment)this.segmentMap.get(segment)).drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181669_b(red, green, blue, alpha).func_181675_d());
/* 607 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArea(Segment segment, BufferBuilder buffer) {
/* 617 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 618 */     ((RawSegment)this.segmentMap.get(segment)).draw(buffer);
/* 619 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArea(Segment segment, BufferBuilder buffer, int red, int green, int blue) {
/* 632 */     drawArea(segment, buffer, red, green, blue, 127);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArea(Segment segment, BufferBuilder buffer, int red, int green, int blue, int alpha) {
/* 646 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 647 */     ((RawSegment)this.segmentMap.get(segment)).drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181669_b(red, green, blue, alpha).func_181675_d());
/* 648 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawSide(BufferBuilder buffer, int red, int green, int blue) {
/* 660 */     drawSide(buffer, red, green, blue, 127);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawSide(BufferBuilder buffer, int red, int green, int blue, int alpha) {
/* 673 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 674 */     buffer.func_181662_b(0.5D, 0.0D, -0.5D).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 675 */     buffer.func_181662_b(-0.5D, 0.0D, -0.5D).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 676 */     buffer.func_181662_b(-0.5D, 0.0D, 0.5D).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 677 */     buffer.func_181662_b(0.5D, 0.0D, 0.5D).func_181669_b(red, green, blue, alpha).func_181675_d();
/* 678 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface TripleDoubleConsumer {
/*     */     void accept(double param1Double1, double param1Double2, double param1Double3);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\EnhancedOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */