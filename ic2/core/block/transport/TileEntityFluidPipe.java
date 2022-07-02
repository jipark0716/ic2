/*     */ package ic2.core.block.transport;
/*     */ 
/*     */ import ic2.api.transport.IFluidPipe;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.transport.cover.CoverProperty;
/*     */ import ic2.core.block.transport.cover.ICoverItem;
/*     */ import ic2.core.block.transport.items.PipeSize;
/*     */ import ic2.core.block.transport.items.PipeType;
/*     */ import ic2.core.item.block.ItemPipe;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidTankProperties;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "ic2", value = {Side.CLIENT})
/*     */ public class TileEntityFluidPipe
/*     */   extends TileEntityPipe
/*     */   implements IFluidPipe
/*     */ {
/*     */   public TileEntityFluidPipe(PipeType type, PipeSize size) {
/*  59 */     this();
/*     */     
/*  61 */     this.type = type;
/*  62 */     this.size = size;
/*     */     
/*  64 */     this.tank = new PipeFluidTank(Util.allFacings, Util.allFacings, fluid -> true, (int)(type.transferRate * size.multiplier));
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<CoverProperty> getCoverProperties() {
/*  69 */     return EnumSet.of(CoverProperty.FluidConsuming);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTransferRate() {
/*  74 */     return this.type.transferRate;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTank getTank() {
/*  79 */     return this.tank;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentInnerCapacity() {
/*  84 */     return this.tank.getFluidAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxInnerCapacity() {
/*  89 */     return this.tank.getCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flipConnection(EnumFacing facing) {
/*  94 */     World world = func_145831_w();
/*  95 */     BlockPos pos = func_174877_v();
/*     */     
/*  97 */     if (!world.field_72995_K) {
/*  98 */       this.connectivity = (byte)(this.connectivity ^ 1 << facing.ordinal());
/*  99 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "connectivity");
/* 100 */       world.func_175685_c(pos, (Block)getBlockType(), true);
/* 101 */       func_70296_d();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 107 */     super.updateEntityServer();
/*     */     
/* 109 */     if (this.tank.getFluid() != null && this.tank.getFluidAmount() > 0) {
/* 110 */       int availableFluidAmount = this.tank.getFluidAmount();
/* 111 */       int cPipes = 1;
/*     */       
/* 113 */       List<LiquidUtil.AdjacentFluidHandler> adjacentFluidHandlers = new ArrayList<>();
/* 114 */       for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 115 */         if (LiquidUtil.drainTile((TileEntity)this, facing, 2147483647, true) != null) {
/*     */           
/* 117 */           LiquidUtil.AdjacentFluidHandler target = LiquidUtil.getAdjacentHandler((TileEntity)this, facing);
/*     */           
/* 119 */           if (target != null) {
/* 120 */             if (target.handler instanceof IFluidPipe)
/*     */             
/* 122 */             { int targetAmount = ((IFluidPipe)target.handler).getTank().getFluidAmount();
/* 123 */               if (this.tank.getFluidAmount() >= targetAmount) {
/* 124 */                 availableFluidAmount += targetAmount;
/* 125 */                 cPipes++;
/* 126 */                 adjacentFluidHandlers.add(target);
/*     */               }
/*     */                }
/*     */             
/* 130 */             else if (LiquidUtil.fillTile(target.handler, facing.func_176734_d(), this.tank.getFluid(), true) > 0) { adjacentFluidHandlers.add(target); }
/*     */           
/*     */           }
/*     */         } 
/*     */       } 
/* 135 */       if (this.debug) IC2.log.warn(LogCategory.Transport, "Number of valid adjacentFluidHandlers: %s", new Object[] { Integer.valueOf(adjacentFluidHandlers.size()) });
/*     */       
/* 137 */       int extraFluid = availableFluidAmount % cPipes;
/*     */       
/* 139 */       availableFluidAmount /= cPipes;
/*     */ 
/*     */       
/* 142 */       List<LiquidUtil.AdjacentFluidHandler> adjacentPipes = new ArrayList<>();
/* 143 */       if (!adjacentFluidHandlers.isEmpty()) {
/* 144 */         Iterator<LiquidUtil.AdjacentFluidHandler> it = adjacentFluidHandlers.iterator();
/* 145 */         while (it.hasNext()) {
/* 146 */           LiquidUtil.AdjacentFluidHandler target = it.next();
/* 147 */           if (target.handler instanceof IFluidPipe) {
/*     */             
/* 149 */             FluidStack ret = LiquidUtil.transfer((TileEntity)this, target.dir, target.handler, availableFluidAmount - ((IFluidPipe)target.handler).getTank().getFluidAmount());
/* 150 */             if (this.debug) IC2.log.warn(LogCategory.Transport, "Split with pipe: %s to facing %s", new Object[] { Integer.valueOf((ret != null) ? ret.amount : 0), target.dir }); 
/* 151 */             adjacentPipes.add(target);
/* 152 */             it.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       if (adjacentFluidHandlers.isEmpty()) {
/* 158 */         while (extraFluid > 0) {
/* 159 */           int bullet = IC2.random.nextInt(adjacentPipes.size());
/* 160 */           LiquidUtil.AdjacentFluidHandler target = adjacentPipes.get(bullet);
/* 161 */           LiquidUtil.transfer((TileEntity)this, target.dir, target.handler, 1);
/* 162 */           if (this.debug) IC2.log.warn(LogCategory.Transport, "Split with pipe: 1 to facing %s", new Object[] { target.dir }); 
/* 163 */           extraFluid--;
/* 164 */           adjacentPipes.remove(bullet);
/*     */         } 
/*     */       } else {
/* 167 */         availableFluidAmount += extraFluid;
/*     */       } 
/*     */ 
/*     */       
/* 171 */       if (!adjacentFluidHandlers.isEmpty()) {
/* 172 */         int maxTransfer = Math.min(availableFluidAmount, this.type.transferRate / 20);
/*     */         
/* 174 */         int maxAmountPerOutput = (int)Math.floor((maxTransfer / adjacentFluidHandlers.size()));
/*     */         
/* 176 */         if (maxAmountPerOutput <= 0) {
/*     */           
/* 178 */           while (maxTransfer > 0) {
/* 179 */             int bullet = IC2.random.nextInt(adjacentFluidHandlers.size());
/* 180 */             LiquidUtil.AdjacentFluidHandler target = adjacentFluidHandlers.get(bullet);
/* 181 */             LiquidUtil.transfer((TileEntity)this, target.dir, target.handler, 1);
/* 182 */             if (this.debug) IC2.log.warn(LogCategory.Transport, "Transferred: 1 to facing %s", new Object[] { target.dir }); 
/* 183 */             maxTransfer--;
/* 184 */             adjacentFluidHandlers.remove(bullet);
/*     */           } 
/*     */         } else {
/* 187 */           for (LiquidUtil.AdjacentFluidHandler target : adjacentFluidHandlers) {
/*     */             
/* 189 */             FluidStack ret = LiquidUtil.transfer((TileEntity)this, target.dir, target.handler, maxAmountPerOutput);
/* 190 */             if (this.debug) IC2.log.warn(LogCategory.Transport, "Transferred: %s to facing %s", new Object[] { Integer.valueOf((ret != null) ? ret.amount : 0), target.dir });
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 199 */     super.func_145839_a(nbt);
/*     */     
/* 201 */     this.type = PipeType.values[nbt.func_74771_c("type") & 0xFF];
/* 202 */     this.size = PipeSize.values()[nbt.func_74771_c("size") & 0xFF];
/*     */     
/* 204 */     this.tank = new PipeFluidTank(Util.allFacings, Util.allFacings, fluid -> true, (int)(this.type.transferRate * this.size.multiplier));
/*     */ 
/*     */     
/* 207 */     this.tank.readFromNBT(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 212 */     super.func_189515_b(nbt);
/*     */     
/* 214 */     nbt.func_74774_a("type", (byte)this.type.ordinal());
/* 215 */     nbt.func_74774_a("size", (byte)this.size.ordinal());
/*     */ 
/*     */     
/* 218 */     this.tank.writeToNBT(nbt);
/*     */     
/* 220 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
/* 225 */     return ItemPipe.getPipe(this.type, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 230 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 232 */     ret.add("type");
/* 233 */     ret.add("size");
/*     */     
/* 235 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateRenderState() {
/* 241 */     this.renderState = new TileEntityPipe.PipeRenderState(this.type, this.size, this.connectivity, this.covers, getFacing().ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateConnectivity() {
/* 246 */     byte newConnectivity = this.connectivity;
/*     */     
/* 248 */     for (EnumFacing direction : EnumFacing.field_82609_l) {
/* 249 */       TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(direction));
/*     */       
/* 251 */       if (tile != null) {
/* 252 */         if (tile instanceof IFluidPipe) {
/* 253 */           if (((IFluidPipe)tile).isConnected(direction.func_176734_d()))
/* 254 */             newConnectivity = (byte)(newConnectivity | 1 << direction.ordinal()); 
/* 255 */         } else if (LiquidUtil.isFluidTile(tile, direction.func_176734_d())) {
/* 256 */           newConnectivity = (byte)(newConnectivity | this.connectivity & 1 << direction.ordinal());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 261 */     if (this.connectivity != newConnectivity) {
/* 262 */       this.connectivity = newConnectivity;
/* 263 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "connectivity");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/* 269 */     super.onPlaced(stack, placer, facing);
/*     */     
/* 271 */     if (!this.field_145850_b.field_72995_K) {
/* 272 */       TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(facing.func_176734_d()));
/*     */       
/* 274 */       if (tile != null && (
/* 275 */         tile instanceof IFluidPipe || LiquidUtil.isFluidTile(tile, facing))) {
/*     */         
/* 277 */         flipConnection(facing.func_176734_d());
/*     */ 
/*     */         
/* 280 */         if (tile instanceof IFluidPipe) {
/* 281 */           IFluidPipe other = (IFluidPipe)tile;
/*     */           
/* 283 */           if (!other.isConnected(facing)) {
/* 284 */             other.flipConnection(facing);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onBlockBreak() {
/* 293 */     super.onBlockBreak();
/*     */ 
/*     */     
/* 296 */     if (this.tank.getFluidAmount() > 1000 && 
/* 297 */       LiquidUtil.fillBlock(this.tank.getFluid(), this.field_145850_b, this.field_174879_c, true)) {
/* 298 */       LiquidUtil.fillBlock(this.tank.getFluid(), this.field_145850_b, this.field_174879_c, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
/* 304 */     return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) ? ((facing != null && 
/* 305 */       isConnected(facing))) : super.hasCapability(capability, facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
/* 312 */     if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
/*     */     {
/* 314 */       return (facing != null && isConnected(facing)) ? (T)new PipeFluidHandler(facing) : null;
/*     */     }
/*     */     
/* 317 */     return (T)super.getCapability(capability, facing);
/*     */   }
/*     */   
/*     */   private class PipeFluidHandler implements IFluidHandler {
/*     */     public PipeFluidHandler(EnumFacing side) {
/* 322 */       this.side = side;
/*     */     }
/*     */     private final EnumFacing side;
/*     */     
/*     */     public IFluidTankProperties[] getTankProperties() {
/* 327 */       return TileEntityFluidPipe.this.tank.getTankProperties();
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(FluidStack resource, boolean doFill) {
/* 332 */       if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side)) {
/* 333 */         ICoverItem cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side);
/* 334 */         if (!cover.allowsInput(resource)) return 0;
/*     */       
/*     */       } 
/* 337 */       return TileEntityFluidPipe.this.tank.fill(resource, doFill);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public FluidStack drain(FluidStack resource, boolean doDrain) {
/* 343 */       if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side)) {
/* 344 */         ICoverItem cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side);
/* 345 */         if (!cover.allowsOutput(resource)) return null;
/*     */       
/*     */       } 
/* 348 */       return TileEntityFluidPipe.this.tank.drain(resource, doDrain);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public FluidStack drain(int maxDrain, boolean doDrain) {
/* 355 */       ICoverItem cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side);
/* 356 */       if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side) && !cover.allowsOutput(new FluidStack(Objects.<FluidStack>requireNonNull(TileEntityFluidPipe.this.tank.getFluid()), maxDrain))) return null;
/*     */ 
/*     */       
/* 359 */       return TileEntityFluidPipe.this.tank.drain(maxDrain, doDrain);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   protected PipeType type = PipeType.bronze;
/* 369 */   protected PipeSize size = PipeSize.small;
/*     */   
/*     */   protected PipeFluidTank tank;
/*     */   
/*     */   private boolean debug = false;
/*     */   
/*     */   private class PipeFluidTank
/*     */     extends FluidTank
/*     */   {
/*     */     private final Predicate<Fluid> acceptedFluids;
/*     */     private Collection<EnumFacing> inputSides;
/*     */     private Collection<EnumFacing> outputSides;
/*     */     
/*     */     protected PipeFluidTank(Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids, int capacity) {
/* 383 */       super(capacity);
/* 384 */       this.acceptedFluids = acceptedFluids;
/* 385 */       this.inputSides = inputSides;
/* 386 */       this.outputSides = outputSides;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canFillFluidType(FluidStack fluid) {
/* 391 */       return (fluid != null && acceptsFluid(fluid.getFluid()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canDrainFluidType(FluidStack fluid) {
/* 396 */       return (fluid != null && acceptsFluid(fluid.getFluid()));
/*     */     }
/*     */     
/*     */     public boolean acceptsFluid(Fluid fluid) {
/* 400 */       return this.acceptedFluids.test(fluid);
/*     */     }
/*     */     
/*     */     IFluidTankProperties getTankProperties(final EnumFacing side) {
/* 404 */       assert side == null || this.inputSides.contains(side) || this.outputSides.contains(side);
/* 405 */       return new IFluidTankProperties()
/*     */         {
/*     */           public FluidStack getContents()
/*     */           {
/* 409 */             return TileEntityFluidPipe.PipeFluidTank.this.getFluid();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getCapacity() {
/* 414 */             return TileEntityFluidPipe.PipeFluidTank.this.capacity;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canFillFluidType(FluidStack fluidStack) {
/* 419 */             if (fluidStack == null || fluidStack.amount <= 0) {
/* 420 */               return false;
/*     */             }
/* 422 */             return (TileEntityFluidPipe.PipeFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || TileEntityFluidPipe.PipeFluidTank.this.canFill(side)));
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canFill() {
/* 427 */             return TileEntityFluidPipe.PipeFluidTank.this.canFill(side);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canDrainFluidType(FluidStack fluidStack) {
/* 432 */             if (fluidStack == null || fluidStack.amount <= 0) {
/* 433 */               return false;
/*     */             }
/* 435 */             return (TileEntityFluidPipe.PipeFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || TileEntityFluidPipe.PipeFluidTank.this.canDrain(side)));
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canDrain() {
/* 440 */             return TileEntityFluidPipe.PipeFluidTank.this.canDrain(side);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean canFill(EnumFacing side) {
/* 446 */       return this.inputSides.contains(side);
/*     */     }
/*     */     
/*     */     public boolean canDrain(EnumFacing side) {
/* 450 */       return this.outputSides.contains(side);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/* 457 */     if (!forCollision) return super.getAabbs(false);
/*     */     
/* 459 */     float th = this.size.thickness;
/* 460 */     float sp = (1.0F - th) / 2.0F;
/* 461 */     List<AxisAlignedBB> ret = new ArrayList<>(7);
/*     */     
/* 463 */     ret.add(new AxisAlignedBB(sp, sp, sp, (sp + th), (sp + th), (sp + th)));
/*     */     
/* 465 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 466 */       boolean hasConnection = ((this.connectivity & 1 << facing.ordinal()) != 0);
/*     */       
/* 468 */       if (hasConnection) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         float zS = sp, yS = zS, xS = yS;
/* 475 */         float zE = sp + th, yE = zE, xE = yE;
/*     */         
/* 477 */         switch (facing) { case DOWN:
/* 478 */             yS = 0.0F; yE = sp; break;
/* 479 */           case UP: yS = sp + th; yE = 1.0F; break;
/* 480 */           case NORTH: zS = 0.0F; zE = sp; break;
/* 481 */           case SOUTH: zS = sp + th; zE = 1.0F; break;
/* 482 */           case WEST: xS = 0.0F; xE = sp; break;
/* 483 */           case EAST: xS = sp + th; xE = 1.0F; break;
/* 484 */           default: throw new RuntimeException(); }
/*     */ 
/*     */         
/* 487 */         ret.add(new AxisAlignedBB(xS, yS, zS, xE, yE, zE));
/*     */ 
/*     */         
/* 490 */         float cs = 1.0F;
/* 491 */         float ch = 0.1F;
/* 492 */         boolean hasCover = ((this.covers & 1 << facing.ordinal()) != 0);
/*     */         
/* 494 */         xS = yS = zS = 0.0F;
/* 495 */         xE = yE = zE = 1.0F;
/*     */         
/* 497 */         if (hasCover) {
/* 498 */           switch (facing) { case DOWN:
/* 499 */               yS = 0.0F; yE = ch; break;
/* 500 */             case UP: yS = cs - ch; yE = 1.0F; break;
/* 501 */             case NORTH: zS = 0.0F; zE = ch; break;
/* 502 */             case SOUTH: zS = cs - ch; zE = 1.0F; break;
/* 503 */             case WEST: xS = 0.0F; xE = ch; break;
/* 504 */             case EAST: xS = cs - ch; xE = 1.0F; break;
/* 505 */             default: throw new RuntimeException(); }
/*     */ 
/*     */           
/* 508 */           ret.add(new AxisAlignedBB(xS, yS, zS, xE, yE, zE));
/*     */         } 
/*     */       } 
/*     */     } 
/* 512 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getVisualBoundingBox() {
/* 518 */     return getPhysicsBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AxisAlignedBB getOutlineBoundingBox() {
/* 524 */     return super.getVisualBoundingBox();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent(priority = EventPriority.LOWEST)
/*     */   public static void drawBetter(DrawBlockHighlightEvent event) {
/* 530 */     if (event.getSubID() != 0)
/*     */       return; 
/* 532 */     RayTraceResult rayTrace = event.getTarget();
/* 533 */     if (rayTrace.field_72313_a != RayTraceResult.Type.BLOCK)
/*     */       return; 
/* 535 */     EntityPlayer player = event.getPlayer();
/* 536 */     World world = player.func_130014_f_();
/* 537 */     BlockPos pos = rayTrace.func_178782_a();
/* 538 */     if (!world.func_175723_af().func_177746_a(pos))
/*     */       return; 
/* 540 */     TileEntity te = world.func_175625_s(pos);
/* 541 */     if (!(te instanceof TileEntityFluidPipe)) {
/*     */       return;
/*     */     }
/* 544 */     GlStateManager.func_179147_l();
/* 545 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 546 */     GlStateManager.func_187441_d(2.0F);
/* 547 */     GlStateManager.func_179090_x();
/* 548 */     GlStateManager.func_179132_a(false);
/*     */     
/* 550 */     double xOffset = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * event.getPartialTicks();
/* 551 */     double yOffset = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * event.getPartialTicks();
/* 552 */     double zOffset = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * event.getPartialTicks();
/* 553 */     RenderGlobal.func_189697_a(((TileEntityFluidPipe)te).getVisualBoundingBox().func_186670_a(pos).func_186662_g(0.002D).func_72317_d(-xOffset, -yOffset, -zOffset), 0.0F, 0.0F, 0.0F, 0.4F);
/*     */     
/* 555 */     GlStateManager.func_179132_a(true);
/* 556 */     GlStateManager.func_179098_w();
/* 557 */     GlStateManager.func_179084_k();
/*     */     
/* 559 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   public TileEntityFluidPipe() {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\TileEntityFluidPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */