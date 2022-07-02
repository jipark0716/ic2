/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDynamicLiquid;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidBlock;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fluids.capability.IFluidTankProperties;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ public class LiquidUtil
/*     */ {
/*     */   public static List<Fluid> getAllFluids() {
/*  45 */     Set<Fluid> fluids = new HashSet<>(FluidRegistry.getRegisteredFluids().values());
/*  46 */     fluids.remove(null);
/*  47 */     List<Fluid> ret = new ArrayList<>(fluids);
/*     */     
/*  49 */     Collections.sort(ret, new Comparator<Fluid>()
/*     */         {
/*     */           public int compare(Fluid a, Fluid b) {
/*  52 */             String nameA = a.getName();
/*  53 */             String nameB = b.getName();
/*     */             
/*  55 */             if (nameA == null) {
/*  56 */               if (nameB == null) {
/*  57 */                 return 0;
/*     */               }
/*  59 */               return 1;
/*     */             } 
/*  61 */             if (nameB == null) {
/*  62 */               return -1;
/*     */             }
/*  64 */             return nameA.toLowerCase(Locale.ENGLISH).compareTo(nameB.toLowerCase(Locale.ENGLISH));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  69 */     return ret;
/*     */   }
/*     */   
/*     */   public static LiquidData getLiquid(World world, BlockPos pos) {
/*  73 */     IBlockState state = world.func_180495_p(pos);
/*  74 */     Block block = state.func_177230_c();
/*  75 */     Fluid liquid = null;
/*  76 */     boolean isSource = false;
/*     */     
/*  78 */     if (block instanceof IFluidBlock) {
/*  79 */       IFluidBlock fblock = (IFluidBlock)block;
/*     */       
/*  81 */       liquid = fblock.getFluid();
/*  82 */       isSource = fblock.canDrain(world, pos);
/*  83 */     } else if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
/*  84 */       liquid = FluidRegistry.WATER;
/*  85 */       isSource = (((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() == 0);
/*  86 */     } else if (block == Blocks.field_150353_l || block == Blocks.field_150356_k) {
/*  87 */       liquid = FluidRegistry.LAVA;
/*  88 */       isSource = (((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() == 0);
/*     */     } 
/*     */     
/*  91 */     if (liquid != null) {
/*  92 */       return new LiquidData(liquid, isSource);
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */   public static class LiquidData { public final Fluid liquid;
/*     */     
/*     */     LiquidData(Fluid liquid1, boolean isSource1) {
/*  99 */       this.liquid = liquid1;
/* 100 */       this.isSource = isSource1;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isSource; }
/*     */ 
/*     */   
/*     */   public static boolean isFluidContainer(ItemStack stack) {
/* 108 */     return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/*     */   }
/*     */   
/*     */   public static boolean isDrainableFluidContainer(ItemStack stack) {
/* 112 */     if (!isFluidContainer(stack)) return false;
/*     */     
/* 114 */     ItemStack singleStack = StackUtil.copyWithSize(stack, 1);
/* 115 */     IFluidHandlerItem handler = (IFluidHandlerItem)singleStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/* 116 */     if (handler == null) return false;
/*     */     
/* 118 */     FluidStack fs = handler.drain(2147483647, false);
/*     */     
/* 120 */     return (fs != null && fs.amount > 0);
/*     */   }
/*     */   
/*     */   public static boolean isFillableFluidContainer(ItemStack stack) {
/* 124 */     return isFillableFluidContainer(stack, null);
/*     */   }
/*     */   
/*     */   public static boolean isFillableFluidContainer(ItemStack stack, Iterable<Fluid> testFluids) {
/* 128 */     if (!isFluidContainer(stack)) return false; 
/* 129 */     if (testFluids == null) testFluids = registeredFluids;
/*     */     
/* 131 */     ItemStack singleStack = StackUtil.copyWithSize(stack, 1);
/* 132 */     IFluidHandlerItem handler = (IFluidHandlerItem)singleStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/* 133 */     if (handler == null) return false;
/*     */     
/* 135 */     FluidStack fs = handler.drain(2147483647, false);
/*     */     
/* 137 */     if (fs != null && 
/* 138 */       testFillFluid(handler, fs.getFluid(), fs.tag)) {
/* 139 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 143 */     for (IFluidTankProperties properties : handler.getTankProperties()) {
/* 144 */       fs = properties.getContents();
/*     */       
/* 146 */       if (fs != null && testFillFluid(handler, fs.getFluid(), fs.tag)) {
/* 147 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 153 */     for (Fluid fluid : registeredFluids) {
/* 154 */       if (testFillFluid(handler, fluid, null)) {
/* 155 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean testFillFluid(IFluidHandlerItem handler, Fluid fluid, NBTTagCompound nbt) {
/* 163 */     FluidStack fs = new FluidStack(fluid, 2147483647);
/* 164 */     fs.tag = nbt;
/*     */     
/* 166 */     return (handler.fill(fs, false) > 0);
/*     */   }
/*     */   
/* 169 */   private static final Collection<Fluid> registeredFluids = FluidRegistry.getRegisteredFluids().values();
/*     */   
/*     */   public static FluidStack drainContainer(EntityPlayer player, EnumHand hand, Fluid fluid, int maxAmount, FluidContainerOutputMode outputMode, boolean simulate) {
/* 172 */     ItemStack stack = StackUtil.get(player, hand);
/* 173 */     FluidOperationResult result = drainContainer(stack, fluid, maxAmount, outputMode);
/* 174 */     if (result == null) return null;
/*     */     
/* 176 */     if (result.extraOutput != null && 
/* 177 */       !StackUtil.storeInventoryItem(result.extraOutput, player, simulate)) return null;
/*     */ 
/*     */     
/* 180 */     if (!simulate) {
/* 181 */       StackUtil.set(player, hand, result.inPlaceOutput);
/*     */     }
/*     */     
/* 184 */     return result.fluidChange;
/*     */   }
/*     */   
/*     */   public static int fillContainer(EntityPlayer player, EnumHand hand, FluidStack fs, FluidContainerOutputMode outputMode, boolean simulate) {
/* 188 */     ItemStack stack = StackUtil.get(player, hand);
/* 189 */     FluidOperationResult result = fillContainer(stack, fs, outputMode);
/* 190 */     if (result == null) return 0;
/*     */     
/* 192 */     if (result.extraOutput != null && 
/* 193 */       !StackUtil.storeInventoryItem(result.extraOutput, player, simulate)) return 0;
/*     */ 
/*     */     
/* 196 */     if (!simulate) {
/* 197 */       StackUtil.set(player, hand, result.inPlaceOutput);
/*     */     }
/*     */     
/* 200 */     return result.fluidChange.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluidOperationResult drainContainer(ItemStack stack, Fluid fluid, int maxAmount, FluidContainerOutputMode outputMode)
/*     */   {
/*     */     FluidStack fs;
/* 213 */     if (StackUtil.isEmpty(stack) || maxAmount <= 0) return null;
/*     */ 
/*     */     
/* 216 */     ItemStack inPlace = StackUtil.copy(stack);
/* 217 */     ItemStack extra = null;
/*     */     
/* 219 */     if (inPlace.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
/* 220 */       ItemStack singleStack = StackUtil.copyWithSize(inPlace, 1);
/* 221 */       IFluidHandlerItem handler = (IFluidHandlerItem)singleStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/* 222 */       if (handler == null) return null;
/*     */       
/* 224 */       if (fluid == null) {
/* 225 */         fs = handler.drain(maxAmount, true);
/*     */       } else {
/* 227 */         fs = handler.drain(new FluidStack(fluid, maxAmount), true);
/*     */       } 
/*     */       
/* 230 */       if (fs == null || fs.amount <= 0) return null;
/*     */       
/* 232 */       if (StackUtil.isEmpty(singleStack)) {
/* 233 */         inPlace = StackUtil.decSize(inPlace);
/*     */       } else {
/* 235 */         FluidStack leftOver = handler.drain(2147483647, false);
/* 236 */         boolean isEmpty = (leftOver == null || leftOver.amount <= 0);
/*     */         
/* 238 */         if ((isEmpty && outputMode.isOutputEmptyFull()) || outputMode == FluidContainerOutputMode.AnyToOutput || (outputMode == FluidContainerOutputMode.InPlacePreferred && 
/*     */           
/* 240 */           StackUtil.getSize(inPlace) > 1))
/* 241 */         { extra = handler.getContainer();
/* 242 */           inPlace = StackUtil.decSize(inPlace); }
/* 243 */         else { if (StackUtil.getSize(inPlace) > 1) {
/* 244 */             return null;
/*     */           }
/* 246 */           inPlace = handler.getContainer(); }
/*     */       
/*     */       } 
/*     */     } else {
/* 250 */       return null;
/*     */     } 
/*     */     
/* 253 */     assert fs.amount > 0;
/*     */     
/* 255 */     return new FluidOperationResult(fs, inPlace, extra); } public static FluidOperationResult fillContainer(ItemStack stack, FluidStack fsIn, FluidContainerOutputMode outputMode) {
/*     */     FluidStack fsChange;
/*     */     ItemStack singleStack;
/*     */     boolean isFull;
/* 259 */     if (StackUtil.isEmpty(stack) || fsIn == null || fsIn.amount <= 0) return null;
/*     */ 
/*     */     
/* 262 */     ItemStack inPlace = StackUtil.copy(stack);
/* 263 */     ItemStack extra = null;
/*     */ 
/*     */ 
/*     */     
/* 267 */     if (inPlace.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
/* 268 */       singleStack = StackUtil.copyWithSize(inPlace, 1);
/* 269 */       IFluidHandlerItem handler = (IFluidHandlerItem)singleStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/* 270 */       if (handler == null) return null;
/*     */       
/* 272 */       fsChange = fsIn.copy();
/* 273 */       int amount = handler.fill(fsChange, true);
/* 274 */       if (amount <= 0) return null;
/*     */       
/* 276 */       fsChange.amount = amount;
/*     */       
/* 278 */       FluidStack fillTestFs = fsIn.copy();
/* 279 */       fillTestFs.amount = Integer.MAX_VALUE;
/* 280 */       isFull = (handler.fill(fillTestFs, false) <= 0);
/* 281 */       singleStack = handler.getContainer();
/*     */     } else {
/* 283 */       return null;
/*     */     } 
/*     */     
/* 286 */     assert fsChange.getFluid() == fsIn.getFluid();
/* 287 */     assert fsChange.amount > 0;
/* 288 */     assert StackUtil.getSize(singleStack) == 1;
/*     */     
/* 290 */     if ((isFull && outputMode.isOutputEmptyFull()) || outputMode == FluidContainerOutputMode.AnyToOutput || (outputMode == FluidContainerOutputMode.InPlacePreferred && 
/*     */       
/* 292 */       StackUtil.getSize(inPlace) > 1))
/* 293 */     { extra = singleStack;
/* 294 */       inPlace = StackUtil.decSize(inPlace); }
/* 295 */     else { if (StackUtil.getSize(inPlace) > 1) {
/* 296 */         return null;
/*     */       }
/* 298 */       inPlace = singleStack; }
/*     */ 
/*     */     
/* 301 */     return new FluidOperationResult(fsChange, inPlace, extra);
/*     */   }
/*     */   public static class FluidOperationResult { public final FluidStack fluidChange;
/*     */     
/*     */     FluidOperationResult(FluidStack fluidChange, ItemStack inPlaceOutput, ItemStack extraOutput) {
/* 306 */       if (fluidChange == null) throw new NullPointerException("null fluid change");
/*     */       
/* 308 */       this.fluidChange = fluidChange;
/* 309 */       this.inPlaceOutput = inPlaceOutput;
/* 310 */       this.extraOutput = extraOutput;
/*     */     }
/*     */ 
/*     */     
/*     */     public final ItemStack inPlaceOutput;
/*     */     public final ItemStack extraOutput; }
/*     */ 
/*     */   
/*     */   public static boolean isFluidTile(TileEntity te, EnumFacing side) {
/* 319 */     return (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side));
/*     */   }
/*     */   
/*     */   public static FluidStack drainTile(TileEntity te, EnumFacing side, int maxAmount, boolean simulate) {
/* 323 */     return drainTile(te, side, null, maxAmount, simulate);
/*     */   }
/*     */   
/*     */   public static FluidStack drainTile(TileEntity te, EnumFacing side, Fluid fluid, int maxAmount, boolean simulate) {
/* 327 */     if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side)) {
/* 328 */       IFluidHandler handler = (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
/* 329 */       if (handler == null) return null;
/*     */       
/* 331 */       if (fluid == null) {
/* 332 */         return handler.drain(maxAmount, !simulate);
/*     */       }
/* 334 */       return handler.drain(new FluidStack(fluid, maxAmount), !simulate);
/*     */     } 
/*     */     
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int fillTile(TileEntity te, EnumFacing side, FluidStack fs, boolean simulate) {
/* 342 */     if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side)) {
/* 343 */       IFluidHandler handler = (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
/* 344 */       if (handler == null) return 0;
/*     */       
/* 346 */       return handler.fill(fs, !simulate);
/*     */     } 
/* 348 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<AdjacentFluidHandler> getAdjacentHandlers(TileEntity source) {
/* 353 */     List<AdjacentFluidHandler> ret = new ArrayList<>();
/*     */     
/* 355 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 356 */       TileEntity te = source.func_145831_w().func_175625_s(source.func_174877_v().func_177972_a(dir));
/* 357 */       if (isFluidTile(te, dir.func_176734_d()))
/*     */       {
/* 359 */         ret.add(new AdjacentFluidHandler(te, dir));
/*     */       }
/*     */     } 
/* 362 */     return ret;
/*     */   }
/*     */   
/*     */   public static AdjacentFluidHandler getAdjacentHandler(TileEntity source, EnumFacing dir) {
/* 366 */     TileEntity te = source.func_145831_w().func_175625_s(source.func_174877_v().func_177972_a(dir));
/* 367 */     if (!isFluidTile(te, dir.func_176734_d())) return null;
/*     */     
/* 369 */     return new AdjacentFluidHandler(te, dir);
/*     */   }
/*     */   public static class AdjacentFluidHandler { public final TileEntity handler; public final EnumFacing dir;
/*     */     
/*     */     AdjacentFluidHandler(TileEntity handler, EnumFacing dir) {
/* 374 */       this.handler = handler;
/* 375 */       this.dir = dir;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int distribute(TileEntity source, FluidStack stack, boolean simulate) {
/* 383 */     int transferred = 0;
/*     */     
/* 385 */     for (AdjacentFluidHandler handler : getAdjacentHandlers(source)) {
/* 386 */       int amount = fillTile(handler.handler, handler.dir.func_176734_d(), stack, simulate);
/*     */       
/* 388 */       transferred += amount;
/* 389 */       stack.amount -= amount;
/*     */       
/* 391 */       if (stack.amount <= 0)
/*     */         break; 
/*     */     } 
/* 394 */     stack.amount += transferred;
/*     */     
/* 396 */     return transferred;
/*     */   }
/*     */   
/*     */   public static int distributeAll(TileEntity source, int amount) {
/* 400 */     if (source == null) throw new IllegalArgumentException("source has to be a tile entity"); 
/* 401 */     TileEntity srcTe = source;
/*     */     
/* 403 */     int transferred = 0;
/*     */     
/* 405 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 406 */       TileEntity te = srcTe.func_145831_w().func_175625_s(srcTe.func_174877_v().func_177972_a(dir));
/* 407 */       if (isFluidTile(te, dir.func_176734_d())) {
/*     */         
/* 409 */         FluidStack stack = transfer(source, dir, te, amount);
/* 410 */         if (stack != null)
/*     */         
/* 412 */         { amount -= stack.amount;
/* 413 */           transferred += stack.amount;
/*     */           
/* 415 */           if (amount <= 0)
/*     */             break;  } 
/*     */       } 
/* 418 */     }  return transferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluidStack transfer(TileEntity source, EnumFacing dir, TileEntity target, int amount) {
/*     */     do {
/* 427 */       ret = drainTile(source, dir, amount, true);
/* 428 */       if (ret == null || ret.amount <= 0) return null; 
/* 429 */       if (ret.amount > amount) throw new IllegalStateException("The fluid handler " + source + " drained more than the requested amount.");
/*     */       
/* 431 */       int cAmount = fillTile(target, getOppositeDir(dir), ret.copy(), true);
/* 432 */       if (cAmount > amount) throw new IllegalStateException("The fluid handler " + target + " filled more than the requested amount.");
/*     */       
/* 434 */       amount = cAmount;
/* 435 */     } while (amount != ret.amount && amount > 0);
/*     */     
/* 437 */     if (amount <= 0) return null;
/*     */ 
/*     */ 
/*     */     
/* 441 */     FluidStack ret = drainTile(source, dir, amount, false);
/* 442 */     if (ret == null) throw new IllegalStateException("The fluid handler " + source + " drained inconsistently. Expected " + amount + ", couldn't find previous IFluidHandler facing " + dir + '.'); 
/* 443 */     if (ret.amount != amount) throw new IllegalStateException("The fluid handler " + source + " drained inconsistently. Expected " + amount + ", got " + ret.amount + '.');
/*     */     
/* 445 */     amount = fillTile(target, getOppositeDir(dir), ret.copy(), false);
/* 446 */     if (amount != ret.amount) throw new IllegalStateException("The fluid handler " + target + " filled inconsistently. Expected " + ret.amount + ", got " + amount + '.');
/*     */     
/* 448 */     return ret;
/*     */   }
/*     */   
/*     */   private static EnumFacing getOppositeDir(EnumFacing dir) {
/* 452 */     if (dir == null) {
/* 453 */       return null;
/*     */     }
/* 455 */     return dir.func_176734_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean check(FluidStack fs) {
/* 460 */     return (fs.getFluid() != null);
/*     */   }
/*     */   
/*     */   public static FluidStack drainBlock(World world, BlockPos pos, boolean simulate) {
/* 464 */     IBlockState state = world.func_180495_p(pos);
/* 465 */     Block block = state.func_177230_c();
/*     */     
/* 467 */     if (block instanceof IFluidBlock) {
/* 468 */       IFluidBlock liquid = (IFluidBlock)block;
/*     */       
/* 470 */       if (liquid.canDrain(world, pos)) {
/* 471 */         return liquid.drain(world, pos, !simulate);
/*     */       }
/* 473 */     } else if (block instanceof BlockLiquid && ((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() == 0) {
/* 474 */       FluidStack fluid = null;
/*     */       
/* 476 */       if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
/* 477 */         fluid = new FluidStack(FluidRegistry.WATER, 1000);
/* 478 */       } else if (block == Blocks.field_150353_l || block == Blocks.field_150356_k) {
/* 479 */         fluid = new FluidStack(FluidRegistry.LAVA, 1000);
/*     */       } 
/*     */       
/* 482 */       if (fluid != null && !simulate) {
/* 483 */         world.func_175698_g(pos);
/*     */       }
/*     */       
/* 486 */       return fluid;
/*     */     } 
/*     */     
/* 489 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean drainBlockToContainer(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
/* 493 */     FluidStack fs = drainBlock(world, pos, true);
/* 494 */     if (fs == null || fs.amount <= 0) return false;
/*     */     
/* 496 */     int amount = fillContainer(player, hand, fs, FluidContainerOutputMode.InPlacePreferred, true);
/* 497 */     if (amount != fs.amount) return false;
/*     */     
/* 499 */     fs = drainBlock(world, pos, false);
/* 500 */     fillContainer(player, hand, fs, FluidContainerOutputMode.InPlacePreferred, false);
/*     */     
/* 502 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean fillBlock(FluidStack fs, World world, BlockPos pos, boolean simulate) {
/* 506 */     if (fs == null || fs.amount < 1000) {
/* 507 */       return false;
/*     */     }
/*     */     
/* 510 */     Fluid fluid = fs.getFluid();
/* 511 */     if (fluid == null || !fluid.canBePlacedInWorld() || fluid.getBlock() == null) return false;
/*     */     
/* 513 */     IBlockState state = world.func_180495_p(pos);
/* 514 */     Block block = state.func_177230_c();
/*     */     
/* 516 */     if (!block.isAir(state, (IBlockAccess)world, pos) && state.func_185904_a().func_76220_a()) return false; 
/* 517 */     if (block == fluid.getBlock() && isFullFluidBlock(world, pos, block, state)) return false;
/*     */     
/* 519 */     if (simulate) return true;
/*     */ 
/*     */     
/*     */     Block fluidBlock;
/* 523 */     if (world.field_73011_w.func_177500_n() && (
/* 524 */       fluidBlock = fluid.getBlock()) != null && fluidBlock
/* 525 */       .func_176223_P().func_185904_a() == Material.field_151586_h) {
/* 526 */       world.func_184133_a(null, pos, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.field_73012_v
/* 527 */           .nextFloat() - world.field_73012_v.nextFloat()) * 0.8F);
/*     */       
/* 529 */       for (int i = 0; i < 8; i++) {
/* 530 */         world.func_175688_a(EnumParticleTypes.SMOKE_LARGE, pos.func_177958_n() + Math.random(), pos.func_177956_o() + Math.random(), pos.func_177952_p() + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 533 */       if (!world.field_72995_K && !state.func_185904_a().func_76220_a() && !state.func_185904_a().func_76224_d())
/*     */       {
/* 535 */         world.func_175655_b(pos, true);
/*     */       }
/*     */       
/* 538 */       if (fluid == FluidRegistry.WATER) {
/* 539 */         BlockDynamicLiquid blockDynamicLiquid = Blocks.field_150358_i;
/* 540 */       } else if (fluid == FluidRegistry.LAVA) {
/* 541 */         BlockDynamicLiquid blockDynamicLiquid = Blocks.field_150356_k;
/*     */       } else {
/* 543 */         block = fluid.getBlock();
/*     */       } 
/*     */       
/* 546 */       if (!world.func_175656_a(pos, block.func_176223_P())) return false;
/*     */     
/*     */     } 
/* 549 */     fs.amount -= 1000;
/*     */     
/* 551 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isFullFluidBlock(World world, BlockPos pos, Block block, IBlockState state) {
/* 555 */     if (block instanceof IFluidBlock) {
/* 556 */       IFluidBlock fBlock = (IFluidBlock)block;
/* 557 */       FluidStack drained = fBlock.drain(world, pos, false);
/*     */       
/* 559 */       return (drained != null && drained.amount >= 1000);
/* 560 */     }  if (state.func_177228_b().containsKey(BlockLiquid.field_176367_b)) {
/* 561 */       return (((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() == 0);
/*     */     }
/* 563 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean fillBlockFromContainer(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
/* 568 */     FluidStack fs = drainContainer(player, hand, null, 1000, FluidContainerOutputMode.InPlacePreferred, true);
/* 569 */     if (fs == null || fs.amount < 1000) return false;
/*     */     
/* 571 */     if (!fillBlock(fs, world, pos, false)) return false;
/*     */     
/* 573 */     drainContainer(player, hand, null, 1000, FluidContainerOutputMode.InPlacePreferred, false);
/*     */     
/* 575 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean storeOutputContainer(MutableObject<ItemStack> output, EntityPlayer player) {
/* 579 */     if (output.getValue() == null) return true;
/*     */     
/* 581 */     return StackUtil.storeInventoryItem((ItemStack)output.getValue(), player, false);
/*     */   }
/*     */   
/*     */   public static String toStringSafe(FluidStack fluidStack) {
/* 585 */     if (fluidStack.getFluid() == null) {
/* 586 */       return fluidStack.amount + "(mb)x(null)@(unknown)";
/*     */     }
/* 588 */     return fluidStack.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\LiquidUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */