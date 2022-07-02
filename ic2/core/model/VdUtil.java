/*     */ package ic2.core.model;
/*     */ 
/*     */ import ic2.core.util.Util;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VdUtil
/*     */ {
/*     */   public static final int quadVertexCount = 4;
/*     */   
/*     */   public static IntBuffer getQuadBuffer() {
/*  22 */     return IntBuffer.allocate(4 * dataStride);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addCuboid(float xS, float yS, float zS, float xE, float yE, float zE, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/*  27 */     float spriteU = sprite.func_94209_e();
/*  28 */     float spriteV = sprite.func_94206_g();
/*     */     
/*  30 */     addCuboid(xS, yS, zS, xE, yE, zE, spriteU, spriteV, sprite.func_94212_f() - spriteU, sprite.func_94210_h() - spriteV, faces, sprite, faceQuads, generalQuads);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addCuboid(float xS, float yS, float zS, float xE, float yE, float zE, int color, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/*  35 */     float spriteU = sprite.func_94209_e();
/*  36 */     float spriteV = sprite.func_94206_g();
/*     */     
/*  38 */     addCuboid(xS, yS, zS, xE, yE, zE, color, spriteU, spriteV, sprite.func_94212_f() - spriteU, sprite.func_94210_h() - spriteV, faces, sprite, faceQuads, generalQuads);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addFlippedCuboid(float xS, float yS, float zS, float xE, float yE, float zE, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/*  43 */     float spriteU = sprite.func_94212_f();
/*  44 */     float spriteV = sprite.func_94210_h();
/*     */     
/*  46 */     addCuboid(xS, yS, zS, xE, yE, zE, spriteU, spriteV, sprite.func_94209_e() - spriteU, sprite.func_94206_g() - spriteV, faces, sprite, faceQuads, generalQuads);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addFlippedCuboid(float xS, float yS, float zS, float xE, float yE, float zE, int colour, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/*  51 */     float spriteU = sprite.func_94212_f();
/*  52 */     float spriteV = sprite.func_94210_h();
/*     */     
/*  54 */     addCuboid(xS, yS, zS, xE, yE, zE, colour, spriteU, spriteV, sprite.func_94209_e() - spriteU, sprite.func_94206_g() - spriteV, faces, sprite, faceQuads, generalQuads);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addFlippedCuboidWithYOffset(float xS, float yS, float zS, float xE, float yE, float zE, int colour, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads, float offset) {
/*  59 */     float spriteU = sprite.func_94212_f();
/*  60 */     float spriteV = sprite.func_94210_h();
/*     */     
/*  62 */     addCuboidWithYOffset(xS, yS, zS, xE, yE, zE, colour, spriteU, spriteV, sprite.func_94209_e() - spriteU, sprite.func_94206_g() - spriteV, faces, sprite, faceQuads, generalQuads, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addCuboid(float xS, float yS, float zS, float xE, float yE, float zE, float spriteU, float spriteV, float spriteWidth, float spriteHeight, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/*  68 */     IntBuffer quadBuffer = getQuadBuffer();
/*     */     
/*  70 */     for (EnumFacing facing : faces) {
/*     */       boolean isFace;
/*     */       
/*  73 */       switch (facing) {
/*     */         case DOWN:
/*  75 */           if (xS == xE || zS == zE)
/*  76 */             continue;  generateBlockVertex(xS, yS, zS, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/*  77 */           generateBlockVertex(xE, yS, zS, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/*  78 */           generateBlockVertex(xE, yS, zE, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/*  79 */           generateBlockVertex(xS, yS, zE, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/*  80 */           isFace = (yS == 0.0F);
/*     */           break;
/*     */         case UP:
/*  83 */           if (xS == xE || zS == zE)
/*  84 */             continue;  generateBlockVertex(xS, yE, zS, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/*  85 */           generateBlockVertex(xS, yE, zE, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/*  86 */           generateBlockVertex(xE, yE, zE, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/*  87 */           generateBlockVertex(xE, yE, zS, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/*  88 */           isFace = (yE == 1.0F);
/*     */           break;
/*     */         case NORTH:
/*  91 */           if (xS == xE || yS == yE)
/*  92 */             continue;  generateBlockVertex(xS, yS, zS, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/*  93 */           generateBlockVertex(xS, yE, zS, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/*  94 */           generateBlockVertex(xE, yE, zS, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/*  95 */           generateBlockVertex(xE, yS, zS, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/*  96 */           isFace = (zS == 0.0F);
/*     */           break;
/*     */         case SOUTH:
/*  99 */           if (xS == xE || yS == yE)
/* 100 */             continue;  generateBlockVertex(xS, yS, zE, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 101 */           generateBlockVertex(xE, yS, zE, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 102 */           generateBlockVertex(xE, yE, zE, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 103 */           generateBlockVertex(xS, yE, zE, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 104 */           isFace = (zE == 1.0F);
/*     */           break;
/*     */         case WEST:
/* 107 */           if (yS == yE || zS == zE)
/* 108 */             continue;  generateBlockVertex(xS, yS, zS, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 109 */           generateBlockVertex(xS, yS, zE, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 110 */           generateBlockVertex(xS, yE, zE, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 111 */           generateBlockVertex(xS, yE, zS, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 112 */           isFace = (xS == 0.0F);
/*     */           break;
/*     */         case EAST:
/* 115 */           if (yS == yE || zS == zE)
/* 116 */             continue;  generateBlockVertex(xE, yS, zS, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 117 */           generateBlockVertex(xE, yE, zS, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 118 */           generateBlockVertex(xE, yE, zE, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 119 */           generateBlockVertex(xE, yS, zE, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 120 */           isFace = (xE == 1.0F);
/*     */           break;
/*     */         default:
/* 123 */           throw new IllegalArgumentException("Unexpected facing: " + facing);
/*     */       } 
/*     */       
/* 126 */       if (quadBuffer.position() > 0) {
/* 127 */         BakedQuad quad = BasicBakedBlockModel.createQuad(Arrays.copyOf(quadBuffer.array(), quadBuffer.position()), facing, sprite);
/*     */         
/* 129 */         if (isFace) {
/* 130 */           faceQuads[facing.ordinal()].add(quad);
/*     */         } else {
/* 132 */           generalQuads.add(quad);
/*     */         } 
/*     */         
/* 135 */         quadBuffer.rewind();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addCuboid(float xS, float yS, float zS, float xE, float yE, float zE, int color, float spriteU, float spriteV, float spriteWidth, float spriteHeight, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads) {
/* 143 */     IntBuffer quadBuffer = getQuadBuffer();
/*     */     
/* 145 */     for (EnumFacing facing : faces) {
/*     */       boolean isFace;
/*     */       
/* 148 */       switch (facing) {
/*     */         case DOWN:
/* 150 */           if (xS == xE || zS == zE)
/* 151 */             continue;  generateBlockVertex(xS, yS, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 152 */           generateBlockVertex(xE, yS, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 153 */           generateBlockVertex(xE, yS, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 154 */           generateBlockVertex(xS, yS, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 155 */           isFace = (yS == 0.0F);
/*     */           break;
/*     */         case UP:
/* 158 */           if (xS == xE || zS == zE)
/* 159 */             continue;  generateBlockVertex(xS, yE, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 160 */           generateBlockVertex(xS, yE, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 161 */           generateBlockVertex(xE, yE, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 162 */           generateBlockVertex(xE, yE, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 163 */           isFace = (yE == 1.0F);
/*     */           break;
/*     */         case NORTH:
/* 166 */           if (xS == xE || yS == yE)
/* 167 */             continue;  generateBlockVertex(xS, yS, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 168 */           generateBlockVertex(xS, yE, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 169 */           generateBlockVertex(xE, yE, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 170 */           generateBlockVertex(xE, yS, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 171 */           isFace = (zS == 0.0F);
/*     */           break;
/*     */         case SOUTH:
/* 174 */           if (xS == xE || yS == yE)
/* 175 */             continue;  generateBlockVertex(xS, yS, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 176 */           generateBlockVertex(xE, yS, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 177 */           generateBlockVertex(xE, yE, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 178 */           generateBlockVertex(xS, yE, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 179 */           isFace = (zE == 1.0F);
/*     */           break;
/*     */         case WEST:
/* 182 */           if (yS == yE || zS == zE)
/* 183 */             continue;  generateBlockVertex(xS, yS, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 184 */           generateBlockVertex(xS, yS, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 185 */           generateBlockVertex(xS, yE, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 186 */           generateBlockVertex(xS, yE, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 187 */           isFace = (xS == 0.0F);
/*     */           break;
/*     */         case EAST:
/* 190 */           if (yS == yE || zS == zE)
/* 191 */             continue;  generateBlockVertex(xE, yS, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 192 */           generateBlockVertex(xE, yE, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 193 */           generateBlockVertex(xE, yE, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 194 */           generateBlockVertex(xE, yS, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 195 */           isFace = (xE == 1.0F);
/*     */           break;
/*     */         default:
/* 198 */           throw new IllegalArgumentException("Unexpected facing: " + facing);
/*     */       } 
/*     */       
/* 201 */       if (quadBuffer.position() > 0) {
/* 202 */         BakedQuad quad = BasicBakedBlockModel.createQuad(Arrays.copyOf(quadBuffer.array(), quadBuffer.position()), facing, sprite);
/*     */         
/* 204 */         if (isFace) {
/* 205 */           faceQuads[facing.ordinal()].add(quad);
/*     */         } else {
/* 207 */           generalQuads.add(quad);
/*     */         } 
/*     */         
/* 210 */         quadBuffer.rewind();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addCuboidWithYOffset(float xS, float yS, float zS, float xE, float yE, float zE, int color, float spriteU, float spriteV, float spriteWidth, float spriteHeight, Set<EnumFacing> faces, TextureAtlasSprite sprite, List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads, float offset) {
/* 218 */     IntBuffer quadBuffer = getQuadBuffer();
/*     */     
/* 220 */     for (EnumFacing facing : faces) {
/*     */       boolean isFace;
/*     */       
/* 223 */       switch (facing) {
/*     */         case DOWN:
/* 225 */           if (xS == xE || zS == zE)
/* 226 */             continue;  generateBlockVertex(xS, yS + offset, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 227 */           generateBlockVertex(xE, yS + offset, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 228 */           generateBlockVertex(xE, yS + offset, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 229 */           generateBlockVertex(xS, yS + offset, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 230 */           isFace = (yS == 0.0F);
/*     */           break;
/*     */         case UP:
/* 233 */           if (xS == xE || zS == zE)
/* 234 */             continue;  generateBlockVertex(xS, yE + offset, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 235 */           generateBlockVertex(xS, yE + offset, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 236 */           generateBlockVertex(xE, yE + offset, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zE, facing, quadBuffer);
/* 237 */           generateBlockVertex(xE, yE + offset, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * zS, facing, quadBuffer);
/* 238 */           isFace = (yE == 1.0F);
/*     */           break;
/*     */         case NORTH:
/* 241 */           if (xS == xE || yS == yE)
/* 242 */             continue;  generateBlockVertex(xS, yS + offset, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 243 */           generateBlockVertex(xS, yE + offset, zS, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 244 */           generateBlockVertex(xE, yE + offset, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 245 */           generateBlockVertex(xE, yS + offset, zS, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 246 */           isFace = (zS == 0.0F);
/*     */           break;
/*     */         case SOUTH:
/* 249 */           if (xS == xE || yS == yE)
/* 250 */             continue;  generateBlockVertex(xS, yS + offset, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 251 */           generateBlockVertex(xE, yS + offset, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 252 */           generateBlockVertex(xE, yE + offset, zE, color, spriteU + spriteWidth * xE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 253 */           generateBlockVertex(xS, yE + offset, zE, color, spriteU + spriteWidth * xS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 254 */           isFace = (zE == 1.0F);
/*     */           break;
/*     */         case WEST:
/* 257 */           if (yS == yE || zS == zE)
/* 258 */             continue;  generateBlockVertex(xS, yS + offset, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 259 */           generateBlockVertex(xS, yS + offset, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 260 */           generateBlockVertex(xS, yE + offset, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 261 */           generateBlockVertex(xS, yE + offset, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 262 */           isFace = (xS == 0.0F);
/*     */           break;
/*     */         case EAST:
/* 265 */           if (yS == yE || zS == zE)
/* 266 */             continue;  generateBlockVertex(xE, yS + offset, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 267 */           generateBlockVertex(xE, yE + offset, zS, color, spriteU + spriteWidth * zS, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 268 */           generateBlockVertex(xE, yE + offset, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yE, facing, quadBuffer);
/* 269 */           generateBlockVertex(xE, yS + offset, zE, color, spriteU + spriteWidth * zE, spriteV + spriteHeight * yS, facing, quadBuffer);
/* 270 */           isFace = (xE == 1.0F);
/*     */           break;
/*     */         default:
/* 273 */           throw new IllegalArgumentException("Unexpected facing: " + facing);
/*     */       } 
/*     */       
/* 276 */       if (quadBuffer.position() > 0) {
/* 277 */         BakedQuad quad = BasicBakedBlockModel.createQuad(Arrays.copyOf(quadBuffer.array(), quadBuffer.position()), facing, sprite);
/*     */         
/* 279 */         if (isFace) {
/* 280 */           faceQuads[facing.ordinal()].add(quad);
/*     */         } else {
/* 282 */           generalQuads.add(quad);
/*     */         } 
/*     */         
/* 285 */         quadBuffer.rewind();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void generateVertex(float x, float y, float z, int color, float u, float v, EnumFacing facing, IntBuffer out) {
/* 291 */     generateVertex(x, y, z, color, u, v, facing.func_82601_c(), facing.func_96559_d(), facing.func_82599_e(), out);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void generateVertex(float x, float y, float z, int color, float u, float v, float nx, float ny, float nz, IntBuffer out) {
/* 296 */     out.put(Float.floatToRawIntBits(x));
/* 297 */     out.put(Float.floatToRawIntBits(y));
/* 298 */     out.put(Float.floatToRawIntBits(z));
/* 299 */     out.put(color);
/* 300 */     out.put(Float.floatToRawIntBits(u));
/* 301 */     out.put(Float.floatToRawIntBits(v));
/* 302 */     out.put(packNormals(nx, ny, nz));
/*     */   }
/*     */   
/*     */   public static void generateBlockVertex(float x, float y, float z, int color, float u, float v, EnumFacing facing, IntBuffer out) {
/* 306 */     generateVertex(x, y, z, color, u, v, facing.func_82601_c(), facing.func_96559_d(), facing.func_82599_e(), out);
/*     */   }
/*     */   
/*     */   public static void generateBlockVertex(float x, float y, float z, float u, float v, EnumFacing facing, IntBuffer out) {
/* 310 */     generateVertex(x, y, z, faceShades[facing.ordinal()], u, v, facing.func_82601_c(), facing.func_96559_d(), facing.func_82599_e(), out);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int packNormals(float nx, float ny, float nz) {
/* 315 */     return mapFloatToByte(nx) | 
/* 316 */       mapFloatToByte(ny) << 8 | 
/* 317 */       mapFloatToByte(nz) << 16;
/*     */   }
/*     */   
/*     */   private static int mapFloatToByte(float f) {
/* 321 */     assert f >= -1.0F && f <= 1.0F;
/*     */     
/* 323 */     return Math.round(f * 127.0F) & 0xFF;
/*     */   }
/*     */   
/*     */   private static int[] getFaceShades() {
/* 327 */     int[] ret = new int[EnumFacing.field_82609_l.length];
/* 328 */     double[] faceBrightness = { 0.5D, 1.0D, 0.8D, 0.8D, 0.6D, 0.6D };
/*     */     
/* 330 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 331 */       int brightness = Util.limit((int)(faceBrightness[facing.ordinal()] * 255.0D), 0, 255);
/*     */       
/* 333 */       ret[facing.ordinal()] = 0xFF000000 | brightness << 16 | brightness << 8 | brightness;
/*     */     } 
/*     */     
/* 336 */     return ret;
/*     */   }
/*     */ 
/*     */   
/* 340 */   public static final VertexFormat vertexFormat = DefaultVertexFormats.field_176599_b;
/* 341 */   public static final int dataStride = vertexFormat.func_177338_f() / 4;
/* 342 */   private static final int[] faceShades = getFaceShades();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\VdUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */