/*     */ package ic2.core.model;
/*     */ 
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.SimpleBakedModel;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.property.IExtendedBlockState;
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
/*     */ public class ModelComparator
/*     */ {
/*     */   public static boolean isEqual(IBlockState stateA, IBlockState stateB, World world, BlockPos pos) {
/*     */     CacheKey cacheKey;
/*     */     Byte cacheResult;
/*  38 */     assert stateA != stateB;
/*     */ 
/*     */ 
/*     */     
/*  42 */     byte renderMask = 0;
/*     */     
/*  44 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/*  45 */       boolean renderA = stateA.func_185894_c((IBlockAccess)world, pos, facing);
/*  46 */       boolean renderB = stateB.func_185894_c((IBlockAccess)world, pos, facing);
/*  47 */       if (renderA != renderB) return false;
/*     */       
/*  49 */       if (renderA) renderMask = (byte)(renderMask | 1 << facing.ordinal());
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (stateA.getClass() == stateB.getClass() && (stateA
/*  58 */       .getClass() == BlockStateContainer.StateImplementation.class || (stateA instanceof Ic2BlockState.Ic2BlockStateInstance && 
/*  59 */       !((Ic2BlockState.Ic2BlockStateInstance)stateA).hasExtraProperties() && !((Ic2BlockState.Ic2BlockStateInstance)stateB).hasExtraProperties()) || (stateA instanceof IExtendedBlockState && ((IExtendedBlockState)stateA)
/*  60 */       .getClean() == stateA && ((IExtendedBlockState)stateB).getClean() == stateB))) {
/*  61 */       cacheKey = new CacheKey(stateA, stateB);
/*  62 */       cacheResult = cache.get(cacheKey);
/*     */       
/*  64 */       if (cacheResult != null && cacheResult != UNCACHEABLE) {
/*  65 */         return ((cacheResult.byteValue() | renderMask ^ 0xFFFFFFFF) == -1);
/*     */       }
/*     */     } else {
/*  68 */       cacheKey = null;
/*  69 */       cacheResult = UNCACHEABLE;
/*     */     } 
/*     */     
/*  72 */     assert cacheResult == null || cacheResult == UNCACHEABLE;
/*     */ 
/*     */ 
/*     */     
/*  76 */     BlockRendererDispatcher renderer = Minecraft.func_71410_x().func_175602_ab();
/*  77 */     IBakedModel modelA = renderer.func_184389_a(stateA);
/*  78 */     IBakedModel modelB = renderer.func_184389_a(stateB);
/*  79 */     Class<?> modelCls = modelA.getClass();
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (modelB.getClass() != modelCls) {
/*  84 */       if (cacheResult == null) cache.putIfAbsent(cacheKey, Byte.valueOf((byte)0));
/*     */       
/*  86 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (cacheResult == null && modelCls != SimpleBakedModel.class && modelCls != BasicBakedBlockModel.class && 
/*     */ 
/*     */       
/*  97 */       !modelCls.getName().equals("net.minecraftforge.client.model.ModelLoader$VanillaModelWrapper$1")) {
/*  98 */       if (Util.inDev() && !$assertionsDisabled) throw new AssertionError(); 
/*  99 */       cacheResult = UNCACHEABLE;
/* 100 */       cache.putIfAbsent(cacheKey, UNCACHEABLE);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 105 */     long rand = MathHelper.func_180186_a((Vec3i)pos);
/* 106 */     byte equal = 63;
/*     */     
/* 108 */     label88: for (EnumFacing facing : facings) {
/* 109 */       if (cacheResult == null || facing == null || (renderMask & 1 << facing.ordinal()) != 0) {
/*     */         
/* 111 */         List<BakedQuad> quadsA = modelA.func_188616_a(stateA, facing, rand);
/* 112 */         List<BakedQuad> quadsB = modelB.func_188616_a(stateB, facing, rand);
/*     */         
/* 114 */         if (quadsA.size() != quadsB.size()) {
/* 115 */           if (cacheResult != null)
/* 116 */             return false; 
/* 117 */           if (facing == null) {
/* 118 */             equal = 0;
/*     */             break;
/*     */           } 
/* 121 */           equal = (byte)(equal & (1 << facing.ordinal() ^ 0xFFFFFFFF));
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 126 */         else if (!quadsA.isEmpty()) {
/*     */           
/* 128 */           for (int i = 0; i < quadsA.size(); i++) {
/* 129 */             if (!Arrays.equals(((BakedQuad)quadsA.get(i)).func_178209_a(), ((BakedQuad)quadsB.get(i)).func_178209_a())) {
/* 130 */               if (cacheResult != null)
/* 131 */                 return false; 
/* 132 */               if (facing == null) {
/* 133 */                 equal = 0;
/*     */                 break label88;
/*     */               } 
/* 136 */               equal = (byte)(equal & (1 << facing.ordinal() ^ 0xFFFFFFFF));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (cacheResult != null) {
/* 146 */       return true;
/*     */     }
/* 148 */     cache.putIfAbsent(cacheKey, Byte.valueOf(equal));
/*     */     
/* 150 */     return ((equal | renderMask ^ 0xFFFFFFFF) == -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onReload() {
/* 155 */     cache.clear();
/*     */   }
/*     */   private static class CacheKey { private final IBlockState stateA;
/*     */     
/*     */     CacheKey(IBlockState stateA, IBlockState stateB) {
/* 160 */       this.stateA = stateA;
/* 161 */       this.stateB = stateB;
/*     */     }
/*     */     private final IBlockState stateB;
/*     */     
/*     */     public boolean equals(Object obj) {
/* 166 */       if (obj == null || obj.getClass() != CacheKey.class) return false;
/*     */       
/* 168 */       CacheKey o = (CacheKey)obj;
/*     */       
/* 170 */       return ((this.stateA == o.stateA && this.stateB == o.stateB) || (this.stateA == o.stateB && this.stateB == o.stateA));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 175 */       return System.identityHashCode(this.stateA) ^ System.identityHashCode(this.stateB);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   private static final EnumFacing[] facings = new EnumFacing[] { null, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
/* 183 */   private static final Byte UNCACHEABLE = Byte.valueOf((byte)-1);
/* 184 */   private static final ConcurrentMap<CacheKey, Byte> cache = new ConcurrentHashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\ModelComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */