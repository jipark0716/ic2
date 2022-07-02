/*     */ package ic2.core.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class Ic2BlockPos extends BlockPos {
/*     */   private int x;
/*     */   
/*     */   public Ic2BlockPos() {
/*  18 */     super(0, 0, 0);
/*     */   }
/*     */   private int y; private int z;
/*     */   public Ic2BlockPos(int x, int y, int z) {
/*  22 */     super(0, 0, 0);
/*     */     
/*  24 */     set(x, y, z);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos(double x, double y, double z) {
/*  28 */     this(Util.roundToNegInf(x), Util.roundToNegInf(y), Util.roundToNegInf(z));
/*     */   }
/*     */   
/*     */   public Ic2BlockPos(Vec3i v) {
/*  32 */     this(v.func_177958_n(), v.func_177956_o(), v.func_177952_p());
/*     */   }
/*     */   
/*     */   public Ic2BlockPos(Vec3d v) {
/*  36 */     this(v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos copy() {
/*  40 */     return new Ic2BlockPos((Vec3i)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_177958_n() {
/*  45 */     return this.x;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos setX(int x) {
/*  49 */     this.x = x;
/*     */     
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_177956_o() {
/*  56 */     return this.y;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos setY(int y) {
/*  60 */     this.y = y;
/*     */     
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_177952_p() {
/*  67 */     return this.z;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos setZ(int z) {
/*  71 */     this.z = z;
/*     */     
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos set(int x, int y, int z) {
/*  77 */     this.x = x;
/*  78 */     this.y = y;
/*  79 */     this.z = z;
/*     */     
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos set(Vec3i v) {
/*  85 */     this.x = v.func_177958_n();
/*  86 */     this.y = v.func_177956_o();
/*  87 */     this.z = v.func_177952_p();
/*     */     
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos func_185334_h() {
/*  94 */     return new BlockPos((Vec3i)this);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos move(EnumFacing facing) {
/*  98 */     return move(facing, 1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos move(EnumFacing facing, int count) {
/* 102 */     if (count == 0) return this;
/*     */     
/* 104 */     if (facing.func_176740_k() == EnumFacing.Axis.X) {
/* 105 */       this.x += facing.func_176743_c().func_179524_a() * count;
/* 106 */     } else if (facing.func_176740_k() == EnumFacing.Axis.Y) {
/* 107 */       this.y += facing.func_176743_c().func_179524_a() * count;
/*     */     } else {
/* 109 */       this.z += facing.func_176743_c().func_179524_a() * count;
/*     */     } 
/*     */     
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveXN() {
/* 116 */     return moveX(-1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveXP() {
/* 120 */     return moveX(1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveX(int count) {
/* 124 */     this.x += count;
/*     */     
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveYN() {
/* 130 */     return moveY(-1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveYP() {
/* 134 */     return moveY(1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveY(int count) {
/* 138 */     this.y += count;
/*     */     
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveZN() {
/* 144 */     return moveZ(-1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveZP() {
/* 148 */     return moveZ(1);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveZ(int count) {
/* 152 */     this.z += count;
/*     */     
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveDown() {
/* 158 */     return moveYN();
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveDown(int count) {
/* 162 */     return moveY(-count);
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveUp() {
/* 166 */     return moveYP();
/*     */   }
/*     */   
/*     */   public Ic2BlockPos moveUp(int count) {
/* 170 */     return moveY(count);
/*     */   }
/*     */   
/*     */   public boolean isBelowMap() {
/* 174 */     return (this.y < 0);
/*     */   }
/*     */   
/*     */   public Vec3d getCenter() {
/* 178 */     return new Vec3d(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D);
/*     */   }
/*     */   
/*     */   public IBlockState getBlockState(IBlockAccess world) {
/* 182 */     return world.func_180495_p(this);
/*     */   }
/*     */   
/*     */   public TileEntity getTe(IBlockAccess world) {
/* 186 */     return world.func_175625_s(this);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitNeighbors() {
/* 190 */     return visitNeighbors(Util.allFacings);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitNeighbors(Ic2BlockPos result) {
/* 194 */     return visitNeighbors(Util.allFacings, result);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitHorizontalNeighbors() {
/* 198 */     return visitNeighbors(Util.horizontalFacings);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitHorizontalNeighbors(Ic2BlockPos result) {
/* 202 */     return visitNeighbors(Util.horizontalFacings, result);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitNeighbors(Set<EnumFacing> dirs) {
/* 206 */     return visitNeighbors(dirs, new Ic2BlockPos());
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitNeighbors(final Set<EnumFacing> dirs, final Ic2BlockPos result) {
/* 210 */     return new Iterable<Ic2BlockPos>()
/*     */       {
/*     */         public Iterator<Ic2BlockPos> iterator() {
/* 213 */           return new Iterator<Ic2BlockPos>()
/*     */             {
/*     */               public boolean hasNext() {
/* 216 */                 return this.dirIt.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public Ic2BlockPos next() {
/* 221 */                 EnumFacing dir = this.dirIt.next();
/*     */                 
/* 223 */                 return result.set((Vec3i)Ic2BlockPos.this).move(dir);
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 228 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */               
/* 231 */               private final Iterator<EnumFacing> dirIt = dirs.iterator();
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitBox(int dx, int dy, int dz) {
/* 238 */     return visitBox(dx, dy, dz, new Ic2BlockPos());
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitBox(int dx, int dy, int dz, Ic2BlockPos result) {
/* 242 */     return visitBox(this.x, this.y, this.z, this.x + dx + 1, this.y + dy + 1, this.z + dz + 1, result);
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitCenteredBox(int range) {
/* 246 */     return visitCenteredBox(range, new Ic2BlockPos());
/*     */   }
/*     */   
/*     */   public Iterable<Ic2BlockPos> visitCenteredBox(int range, Ic2BlockPos result) {
/* 250 */     if (range < 0) throw new IllegalArgumentException();
/*     */     
/* 252 */     return visitBox(this.x - range, this.y - range, this.z - range, this.x + range + 1, this.y + range + 1, this.z + range + 1, result);
/*     */   }
/*     */   
/*     */   public static Iterable<Ic2BlockPos> visitBox(int xS, int yS, int zS, int xE, int yE, int zE) {
/* 256 */     return visitBox(xS, yS, zS, xE, yE, zE, new Ic2BlockPos());
/*     */   }
/*     */   
/*     */   public static Iterable<Ic2BlockPos> visitBox(final int xS, int yS, final int zS, final int xE, final int yE, final int zE, final Ic2BlockPos result) {
/* 260 */     result.set(xS, yS, zS);
/*     */     
/* 262 */     return new Iterable<Ic2BlockPos>()
/*     */       {
/*     */         public Iterator<Ic2BlockPos> iterator() {
/* 265 */           return new Iterator<Ic2BlockPos>()
/*     */             {
/*     */               public boolean hasNext() {
/* 268 */                 return (result.y < yE || result.z < zE || result.x < xE);
/*     */               }
/*     */ 
/*     */               
/*     */               public Ic2BlockPos next() {
/* 273 */                 if (result.x < xE) {
/* 274 */                   result.x++;
/* 275 */                 } else if (result.z < zE) {
/* 276 */                   result.x = xS;
/* 277 */                   result.z++;
/* 278 */                 } else if (result.y < yE) {
/* 279 */                   result.x = xS;
/* 280 */                   result.z = zS;
/* 281 */                   result.y++;
/*     */                 } else {
/* 283 */                   throw new NoSuchElementException();
/*     */                 } 
/*     */                 
/* 286 */                 return result;
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 291 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Ic2BlockPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */