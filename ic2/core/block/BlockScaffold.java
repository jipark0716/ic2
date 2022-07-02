/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.IBlockSound;
/*     */ import ic2.core.block.type.IExtBlockType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ public class BlockScaffold
/*     */   extends BlockMultiID<BlockScaffold.ScaffoldType>
/*     */ {
/*     */   public static BlockScaffold create() {
/*  58 */     return BlockMultiID.<ScaffoldType, BlockScaffold>create(BlockScaffold.class, ScaffoldType.class, new Object[0]);
/*     */   }
/*     */   
/*     */   private BlockScaffold() {
/*  62 */     super(BlockName.scaffold, Material.field_151575_d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     func_149675_a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public BlockRenderLayer func_180664_k() {
/*  76 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Material func_149688_o(IBlockState state) {
/*  82 */     ScaffoldType type = getType(state);
/*  83 */     if (type == null) return super.func_149688_o(state);
/*     */     
/*  85 */     switch (type) {
/*     */       case wood:
/*     */       case reinforced_wood:
/*  88 */         return Material.field_151575_d;
/*     */       
/*     */       case iron:
/*     */       case reinforced_iron:
/*  92 */         return Material.field_151573_f;
/*     */     } 
/*  94 */     throw new IllegalStateException("Invalid scaffold type: " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149721_r(IBlockState state) {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity rawEntity) {
/* 124 */     if (rawEntity instanceof EntityLivingBase) {
/* 125 */       EntityLivingBase entity = (EntityLivingBase)rawEntity;
/*     */ 
/*     */       
/* 128 */       entity.field_70143_R = 0.0F;
/*     */       
/* 130 */       double limit = 0.15D;
/*     */       
/* 132 */       entity.field_70159_w = Util.limit(entity.field_70159_w, -limit, limit);
/* 133 */       entity.field_70179_y = Util.limit(entity.field_70179_y, -limit, limit);
/*     */       
/* 135 */       if (entity.func_70093_af() && entity instanceof EntityPlayer) {
/*     */         
/* 137 */         if (entity.func_70090_H()) {
/* 138 */           entity.field_70181_x = 0.02D;
/*     */         } else {
/* 140 */           entity.field_70181_x = 0.08D;
/*     */         } 
/* 142 */       } else if (entity.field_70123_F) {
/* 143 */         entity.field_70181_x = 0.2D;
/*     */       } else {
/* 145 */         entity.field_70181_x = Math.max(entity.field_70181_x, -0.07D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 156 */     return aabb;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_180640_a(IBlockState state, World worldIn, BlockPos pos) {
/* 161 */     return field_185505_j.func_186670_a(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/* 169 */     return (side.func_176740_k() == EnumFacing.Axis.Y);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/* 174 */     if (state.func_177230_c() != this) return Collections.emptyList();
/*     */     
/* 176 */     List<ItemStack> ret = new ArrayList<>();
/* 177 */     ScaffoldType type = (ScaffoldType)state.func_177229_b((IProperty)this.typeProperty);
/*     */     
/* 179 */     switch (type) {
/*     */       case wood:
/*     */       case iron:
/* 182 */         ret.add(getItemStack(type));
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
/* 195 */         return ret;case reinforced_wood: ret.add(getItemStack(ScaffoldType.wood)); ret.add(new ItemStack(Items.field_151055_y, 2)); return ret;case reinforced_iron: ret.add(getItemStack(ScaffoldType.iron)); ret.add(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)); return ret;
/*     */     } 
/*     */     throw new IllegalStateException();
/* 198 */   } private static final IRecipeInput stickInput = Recipes.inputFactory.forOreDict("stickWood");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 206 */     if (player.func_70093_af()) return false;
/*     */     
/* 208 */     ItemStack stack = player.func_184586_b(hand);
/* 209 */     if (StackUtil.isEmpty(stack)) return false;
/*     */     
/* 211 */     ScaffoldType type = getType(state);
/* 212 */     if (type == null) return false;
/*     */     
/* 214 */     int stickCount = 2;
/* 215 */     int fenceCount = 1;
/*     */     
/* 217 */     switch (type) {
/*     */       case wood:
/* 219 */         if (!stickInput.matches(stack) || StackUtil.getSize(stack) < 2) return false; 
/*     */         break;
/*     */       case iron:
/* 222 */         if (!StackUtil.checkItemEquality(stack, BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)) || StackUtil.getSize(stack) < 1) return false; 
/*     */         break;
/*     */       case reinforced_wood:
/*     */       case reinforced_iron:
/* 226 */         return false;
/* 227 */       default: throw new IllegalStateException();
/*     */     } 
/*     */     
/* 230 */     if (!isPillar(world, pos)) {
/* 231 */       return false;
/*     */     }
/*     */     
/* 234 */     switch (type) {
/*     */       case wood:
/* 236 */         StackUtil.consumeOrError(player, hand, StackUtil.recipeInput(stickInput), 2);
/* 237 */         type = ScaffoldType.reinforced_wood;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 246 */         world.func_175656_a(pos, state.func_177226_a((IProperty)this.typeProperty, type));
/*     */         
/* 248 */         return true;case iron: StackUtil.consumeOrError(player, hand, StackUtil.sameStack(BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron)), 1); type = ScaffoldType.reinforced_iron; world.func_175656_a(pos, state.func_177226_a((IProperty)this.typeProperty, type)); return true;
/*     */     } 
/*     */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180649_a(World world, BlockPos pos, EntityPlayer player) {
/* 256 */     EnumHand hand = EnumHand.MAIN_HAND;
/* 257 */     ItemStack stack = player.func_184586_b(hand);
/* 258 */     if (StackUtil.isEmpty(stack))
/*     */       return; 
/* 260 */     if (StackUtil.checkItemEquality(stack, Item.func_150898_a(this))) {
/* 261 */       while (world.func_180495_p(pos).func_177230_c() == this) {
/* 262 */         pos = pos.func_177984_a();
/*     */       }
/*     */       
/* 265 */       if (func_176196_c(world, pos) && pos.func_177956_o() < IC2.getWorldHeight(world)) {
/* 266 */         boolean isCreative = player.field_71075_bZ.field_75098_d;
/* 267 */         ItemStack prev = isCreative ? StackUtil.copy(stack) : null;
/* 268 */         stack.func_179546_a(player, world, pos.func_177977_b(), hand, EnumFacing.UP, 0.5F, 1.0F, 0.5F);
/*     */         
/* 270 */         if (!isCreative) {
/* 271 */           StackUtil.clearEmpty(player, hand);
/*     */         } else {
/* 273 */           StackUtil.set(player, hand, prev);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_176196_c(World world, BlockPos pos) {
/* 285 */     return (super.func_176196_c(world, pos) && hasSupport((IBlockAccess)world, pos, ScaffoldType.wood));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
/* 293 */     checkSupport(world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
/* 298 */     if (random.nextInt(8) == 0) {
/* 299 */       checkSupport(world, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPillar(World world, BlockPos pos) {
/* 308 */     for (; world.func_180495_p(pos).func_177230_c() == this; pos = pos.func_177977_b());
/*     */     
/* 310 */     return world.func_175677_d(pos, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 315 */     ScaffoldType type = getType(world, pos);
/* 316 */     if (type == null) return 0;
/*     */     
/* 318 */     switch (type) {
/*     */       case wood:
/*     */       case reinforced_wood:
/* 321 */         return 8;
/*     */       case iron:
/*     */       case reinforced_iron:
/* 324 */         return 0;
/* 325 */     }  throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 331 */     ScaffoldType type = getType(world, pos);
/* 332 */     if (type == null) return 0;
/*     */     
/* 334 */     switch (type) {
/*     */       case wood:
/*     */       case reinforced_wood:
/* 337 */         return 20;
/*     */       case iron:
/*     */       case reinforced_iron:
/* 340 */         return 0;
/* 341 */     }  throw new IllegalStateException();
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
/*     */   private boolean hasSupport(IBlockAccess world, BlockPos start, ScaffoldType type) {
/* 354 */     return (((Support)calculateSupport(world, start, type).get(start)).strength >= 0);
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
/*     */   private void checkSupport(World world, BlockPos start) {
/* 366 */     IBlockState state = world.func_180495_p(start);
/* 367 */     if (state.func_177230_c() != this)
/*     */       return; 
/* 369 */     Map<BlockPos, Support> results = calculateSupport((IBlockAccess)world, start, (ScaffoldType)state.func_177229_b((IProperty)this.typeProperty));
/* 370 */     boolean droppedAny = false;
/*     */     
/* 372 */     for (Support support : results.values()) {
/* 373 */       if (support.strength >= 0) {
/*     */         continue;
/*     */       }
/* 376 */       world.func_180501_a(support.pos, Blocks.field_150350_a.func_176223_P(), 2);
/* 377 */       func_176226_b(world, support.pos, func_176223_P().func_177226_a((IProperty)this.typeProperty, support.type), 0);
/* 378 */       droppedAny = true;
/*     */     } 
/*     */ 
/*     */     
/* 382 */     if (droppedAny) {
/* 383 */       for (Support support : results.values()) {
/* 384 */         if (support.strength < 0) world.func_175722_b(support.pos, this, true);
/*     */       
/*     */       } 
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
/*     */   private Map<BlockPos, Support> calculateSupport(IBlockAccess world, BlockPos start, ScaffoldType type) {
/* 400 */     Map<BlockPos, Support> results = new HashMap<>();
/* 401 */     Queue<Support> queue = new ArrayDeque<>();
/* 402 */     Set<BlockPos> groundSupports = new HashSet<>();
/*     */     
/* 404 */     Support support = new Support(start, type, -1);
/* 405 */     results.put(start, support);
/* 406 */     queue.add(support);
/*     */     
/* 408 */     while ((support = queue.poll()) != null) {
/* 409 */       for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 410 */         BlockPos pos = support.pos.func_177972_a(dir);
/* 411 */         if (!results.containsKey(pos)) {
/*     */           
/* 413 */           IBlockState state = world.func_180495_p(pos);
/* 414 */           Block block = state.func_177230_c();
/*     */           
/* 416 */           if (block == this) {
/* 417 */             type = (ScaffoldType)state.func_177229_b((IProperty)this.typeProperty);
/* 418 */             Support cSupport = new Support(pos, type, -1);
/* 419 */             results.put(pos, cSupport);
/* 420 */             queue.add(cSupport);
/* 421 */           } else if (block.isNormalCube(state, world, pos)) {
/* 422 */             groundSupports.add(pos);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 427 */     label63: for (BlockPos groundPos : groundSupports) {
/* 428 */       BlockPos pos = groundPos.func_177984_a();
/* 429 */       int propagatedStrength = 0;
/*     */       while (true) {
/*     */         int strength;
/* 432 */         support = results.get(pos);
/* 433 */         if (support == null) {
/*     */           continue label63;
/*     */         }
/*     */         
/* 437 */         if (support.type.strength >= propagatedStrength) {
/* 438 */           strength = support.type.strength;
/* 439 */           propagatedStrength = strength - 1;
/*     */         } else {
/* 441 */           strength = propagatedStrength;
/* 442 */           propagatedStrength--;
/*     */         } 
/*     */         
/* 445 */         if (support.strength < strength) {
/* 446 */           support.strength = strength;
/*     */           
/* 448 */           for (EnumFacing dir : EnumFacing.field_176754_o) {
/* 449 */             BlockPos nPos = pos.func_177972_a(dir);
/* 450 */             Support nSupport = results.get(nPos);
/* 451 */             if (nSupport != null && nSupport.strength < strength) {
/*     */               
/* 453 */               nSupport.strength = strength - 1;
/* 454 */               queue.add(nSupport);
/*     */             } 
/*     */           } 
/*     */         } 
/* 458 */         pos = pos.func_177984_a();
/*     */       } 
/*     */     } 
/*     */     
/* 462 */     while ((support = queue.poll()) != null) {
/* 463 */       for (EnumFacing dir : supportedFacings) {
/* 464 */         BlockPos pos = support.pos.func_177972_a(dir);
/* 465 */         Support nSupport = results.get(pos);
/* 466 */         if (nSupport != null && nSupport.strength < support.strength) {
/*     */           
/* 468 */           support.strength--;
/*     */           
/* 470 */           if (nSupport.strength > 0) queue.add(nSupport); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 474 */     return results;
/*     */   }
/*     */   
/* 477 */   private static final EnumFacing[] supportedFacings = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST }; private static final double border = 0.03125D;
/*     */   private static class Support { final BlockPos pos; final BlockScaffold.ScaffoldType type; int strength;
/*     */     
/*     */     Support(BlockPos pos, BlockScaffold.ScaffoldType type, int strength) {
/* 481 */       this.pos = pos;
/* 482 */       this.type = type;
/* 483 */       this.strength = strength;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ScaffoldType
/*     */     implements IIdProvider, IExtBlockType, IBlockSound
/*     */   {
/* 492 */     wood(2, 0.5F, 0.12F, SoundType.field_185848_a),
/* 493 */     reinforced_wood(5, 0.6F, 0.24F, SoundType.field_185848_a),
/* 494 */     iron(5, 0.8F, 6.0F, SoundType.field_185852_e),
/* 495 */     reinforced_iron(12, 1.0F, 8.0F, SoundType.field_185852_e); public final int strength; private final float hardness; private final float explosionResistance; private final SoundType sound;
/*     */     
/*     */     ScaffoldType(int strength, float hardness, float explosionResistance, SoundType sound) {
/* 498 */       if (strength < 1) throw new IllegalArgumentException();
/*     */       
/* 500 */       this.strength = strength;
/* 501 */       this.hardness = hardness;
/* 502 */       this.explosionResistance = explosionResistance;
/* 503 */       this.sound = sound;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 508 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 513 */       return ordinal();
/*     */     }
/*     */ 
/*     */     
/*     */     public float getHardness() {
/* 518 */       return this.hardness;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getExplosionResistance() {
/* 523 */       return this.explosionResistance;
/*     */     }
/*     */ 
/*     */     
/*     */     public SoundType getSound() {
/* 528 */       return this.sound;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 538 */   private static final AxisAlignedBB aabb = new AxisAlignedBB(0.03125D, 0.0D, 0.03125D, 0.96875D, 1.0D, 0.96875D);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */