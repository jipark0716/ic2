/*     */ package ic2.core.block.comp;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import ic2.api.recipe.ILiquidAcceptManager;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidTankProperties;
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
/*     */ public class Fluids
/*     */   extends TileEntityComponent
/*     */ {
/*  40 */   protected final List<InternalFluidTank> managedTanks = new ArrayList<>();
/*  41 */   protected final List<Supplier<? extends Collection<InternalFluidTank>>> unmanagedTanks = new ArrayList<>();
/*     */   
/*     */   public Fluids(TileEntityBlock parent) {
/*  44 */     super(parent);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankInsert(String name, int capacity) {
/*  48 */     return addTankInsert(name, capacity, Predicates.alwaysTrue());
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankInsert(String name, int capacity, Predicate<Fluid> acceptedFluids) {
/*  52 */     return addTankInsert(name, capacity, InvSlot.InvSide.ANY, acceptedFluids);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankInsert(String name, int capacity, InvSlot.InvSide side) {
/*  56 */     return addTankInsert(name, capacity, side, Predicates.alwaysTrue());
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankInsert(String name, int capacity, InvSlot.InvSide side, Predicate<Fluid> acceptedFluids) {
/*  60 */     return addTank(name, capacity, InvSlot.Access.I, side, acceptedFluids);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankExtract(String name, int capacity) {
/*  64 */     return addTankExtract(name, capacity, InvSlot.InvSide.ANY);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTankExtract(String name, int capacity, InvSlot.InvSide side) {
/*  68 */     return addTank(name, capacity, InvSlot.Access.O, side);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity) {
/*  72 */     return addTank(name, capacity, InvSlot.Access.IO);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access) {
/*  76 */     return addTank(name, capacity, access, InvSlot.InvSide.ANY);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity, Predicate<Fluid> acceptedFluids) {
/*  80 */     return addTank(name, capacity, InvSlot.Access.IO, InvSlot.InvSide.ANY, acceptedFluids);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access, InvSlot.InvSide side) {
/*  84 */     return addTank(name, capacity, access, side, Predicates.alwaysTrue());
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity, InvSlot.Access access, InvSlot.InvSide side, Predicate<Fluid> acceptedFluids) {
/*  88 */     return addTank(name, capacity, access.isInput() ? side.getAcceptedSides() : Collections.<EnumFacing>emptySet(), access.isOutput() ? side.getAcceptedSides() : Collections.<EnumFacing>emptySet(), acceptedFluids);
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(String name, int capacity, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids) {
/*  92 */     return addTank(new InternalFluidTank(name, inputSides, outputSides, acceptedFluids, capacity));
/*     */   }
/*     */   
/*     */   public InternalFluidTank addTank(InternalFluidTank tank) {
/*  96 */     this.managedTanks.add(tank);
/*  97 */     return tank;
/*     */   }
/*     */   
/*     */   public void addUnmanagedTanks(InternalFluidTank tank) {
/* 101 */     this.unmanagedTanks.add(Suppliers.ofInstance(Collections.singleton(tank)));
/*     */   }
/*     */   
/*     */   public void addUnmanagedTanks(Collection<InternalFluidTank> tanks) {
/* 105 */     addUnmanagedTankHook(Suppliers.ofInstance(tanks));
/*     */   }
/*     */   
/*     */   public void addUnmanagedTankHook(Supplier<? extends Collection<InternalFluidTank>> suppl) {
/* 109 */     this.unmanagedTanks.add(suppl);
/*     */   }
/*     */   
/*     */   public void changeConnectivity(InternalFluidTank tank, InvSlot.Access access, InvSlot.InvSide side) {
/* 113 */     changeConnectivity(tank, access.isInput() ? side.getAcceptedSides() : Collections.<EnumFacing>emptySet(), access.isOutput() ? side.getAcceptedSides() : Collections.<EnumFacing>emptySet());
/*     */   }
/*     */   
/*     */   public void changeConnectivity(InternalFluidTank tank, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides) {
/* 117 */     assert this.managedTanks.contains(tank);
/* 118 */     tank.inputSides = inputSides;
/* 119 */     tank.outputSides = outputSides;
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
/*     */   public FluidTank getFluidTank(String name) {
/* 131 */     for (InternalFluidTank tank : getAllTanks()) {
/* 132 */       if (tank.identifier.equals(name)) {
/* 133 */         return tank;
/*     */       }
/*     */     } 
/* 136 */     throw new IllegalArgumentException("Unable to find tank: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNbt(NBTTagCompound nbt) {
/* 141 */     for (InternalFluidTank tank : this.managedTanks) {
/* 142 */       if (nbt.func_150297_b(tank.identifier, 10)) {
/* 143 */         tank.readFromNBT(nbt.func_74775_l(tank.identifier));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNbt() {
/* 150 */     NBTTagCompound nbt = new NBTTagCompound();
/* 151 */     for (InternalFluidTank tank : this.managedTanks) {
/* 152 */       NBTTagCompound subTag = new NBTTagCompound();
/* 153 */       subTag = tank.writeToNBT(subTag);
/* 154 */       nbt.func_74782_a(tank.identifier, (NBTBase)subTag);
/*     */     } 
/* 156 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<? extends Capability<?>> getProvidedCapabilities(EnumFacing side) {
/* 161 */     return Collections.singleton(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getCapability(Capability<T> cap, EnumFacing side) {
/* 167 */     if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
/*     */     {
/* 169 */       return (T)new FluidHandler(side);
/*     */     }
/* 171 */     return super.getCapability(cap, side);
/*     */   }
/*     */   
/*     */   public static Predicate<Fluid> fluidPredicate(Fluid... fluids) {
/*     */     final Collection<Fluid> acceptedFluids;
/* 176 */     if (fluids.length > 10) {
/* 177 */       acceptedFluids = new HashSet<>(Arrays.asList(fluids));
/*     */     } else {
/* 179 */       acceptedFluids = Arrays.asList(fluids);
/*     */     } 
/* 181 */     return new Predicate<Fluid>()
/*     */       {
/*     */         public boolean apply(Fluid fluid)
/*     */         {
/* 185 */           return acceptedFluids.contains(fluid);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Predicate<Fluid> fluidPredicate(final ILiquidAcceptManager manager) {
/* 192 */     return new Predicate<Fluid>()
/*     */       {
/*     */         public boolean apply(Fluid fluid)
/*     */         {
/* 196 */           return manager.acceptsFluid(fluid);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Iterable<InternalFluidTank> getAllTanks() {
/* 202 */     if (this.unmanagedTanks.isEmpty()) {
/* 203 */       return this.managedTanks;
/*     */     }
/* 205 */     List<InternalFluidTank> tanks = new ArrayList<>();
/* 206 */     tanks.addAll(this.managedTanks);
/* 207 */     for (Supplier<? extends Collection<InternalFluidTank>> suppl : this.unmanagedTanks) {
/* 208 */       tanks.addAll((Collection<? extends InternalFluidTank>)suppl.get());
/*     */     }
/* 210 */     return tanks;
/*     */   }
/*     */   
/*     */   public static class InternalFluidTank
/*     */     extends FluidTank {
/*     */     protected final String identifier;
/*     */     private final Predicate<Fluid> acceptedFluids;
/*     */     private Collection<EnumFacing> inputSides;
/*     */     private Collection<EnumFacing> outputSides;
/*     */     
/*     */     protected InternalFluidTank(String identifier, Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids, int capacity) {
/* 221 */       super(capacity);
/* 222 */       this.identifier = identifier;
/* 223 */       this.acceptedFluids = acceptedFluids;
/* 224 */       this.inputSides = inputSides;
/* 225 */       this.outputSides = outputSides;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canFillFluidType(FluidStack fluid) {
/* 231 */       return (fluid != null && acceptsFluid(fluid.getFluid()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canDrainFluidType(FluidStack fluid) {
/* 237 */       return (fluid != null && acceptsFluid(fluid.getFluid()));
/*     */     }
/*     */     
/*     */     public boolean acceptsFluid(Fluid fluid) {
/* 241 */       return this.acceptedFluids.apply(fluid);
/*     */     }
/*     */     
/*     */     IFluidTankProperties getTankProperties(final EnumFacing side) {
/* 245 */       assert side == null || this.inputSides.contains(side) || this.outputSides.contains(side);
/* 246 */       return new IFluidTankProperties()
/*     */         {
/*     */           public FluidStack getContents()
/*     */           {
/* 250 */             return Fluids.InternalFluidTank.this.getFluid();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getCapacity() {
/* 255 */             return Fluids.InternalFluidTank.this.capacity;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canFillFluidType(FluidStack fluidStack) {
/* 260 */             if (fluidStack == null || fluidStack.amount <= 0) {
/* 261 */               return false;
/*     */             }
/* 263 */             return (Fluids.InternalFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || Fluids.InternalFluidTank.this.canFill(side)));
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canFill() {
/* 268 */             return Fluids.InternalFluidTank.this.canFill(side);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canDrainFluidType(FluidStack fluidStack) {
/* 273 */             if (fluidStack == null || fluidStack.amount <= 0) {
/* 274 */               return false;
/*     */             }
/* 276 */             return (Fluids.InternalFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || Fluids.InternalFluidTank.this.canDrain(side)));
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean canDrain() {
/* 281 */             return Fluids.InternalFluidTank.this.canDrain(side);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean canFill(EnumFacing side) {
/* 287 */       return this.inputSides.contains(side);
/*     */     }
/*     */     
/*     */     public boolean canDrain(EnumFacing side) {
/* 291 */       return this.outputSides.contains(side);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FluidHandler
/*     */     implements IFluidHandler
/*     */   {
/*     */     private final EnumFacing side;
/*     */     
/*     */     FluidHandler(EnumFacing side) {
/* 301 */       this.side = side;
/*     */     }
/*     */ 
/*     */     
/*     */     public IFluidTankProperties[] getTankProperties() {
/* 306 */       List<IFluidTankProperties> props = new ArrayList<>(Fluids.this.managedTanks.size());
/* 307 */       for (Fluids.InternalFluidTank tank : Fluids.this.getAllTanks()) {
/* 308 */         if (tank.canFill(this.side) || tank.canDrain(this.side)) {
/* 309 */           props.add(tank.getTankProperties(this.side));
/*     */         }
/*     */       } 
/* 312 */       return props.<IFluidTankProperties>toArray(new IFluidTankProperties[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(FluidStack resource, boolean doFill) {
/* 317 */       if (resource == null || resource.amount <= 0) return 0; 
/* 318 */       int total = 0;
/* 319 */       FluidStack missing = resource.copy();
/* 320 */       for (Fluids.InternalFluidTank tank : Fluids.this.getAllTanks()) {
/* 321 */         if (!tank.canFill(this.side)) {
/*     */           continue;
/*     */         }
/*     */         
/* 325 */         total += tank.fill(missing, doFill);
/* 326 */         resource.amount -= total;
/*     */         
/* 328 */         if (missing.amount <= 0) {
/*     */           break;
/*     */         }
/*     */       } 
/* 332 */       return total;
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(FluidStack resource, boolean doDrain) {
/* 337 */       if (resource == null || resource.amount <= 0) {
/* 338 */         return null;
/*     */       }
/* 340 */       FluidStack ret = new FluidStack(resource.getFluid(), 0);
/* 341 */       for (Fluids.InternalFluidTank tank : Fluids.this.getAllTanks()) {
/* 342 */         if (!tank.canDrain(this.side)) {
/*     */           continue;
/*     */         }
/* 345 */         FluidStack inTank = tank.getFluid();
/* 346 */         if (inTank == null || inTank.getFluid() != resource.getFluid()) {
/*     */           continue;
/*     */         }
/*     */         
/* 350 */         FluidStack add = tank.drain(resource.amount - ret.amount, doDrain);
/* 351 */         if (add == null) {
/*     */           continue;
/*     */         }
/* 354 */         assert add.getFluid() == resource.getFluid();
/* 355 */         ret.amount += add.amount;
/* 356 */         if (ret.amount >= resource.amount) {
/*     */           break;
/*     */         }
/*     */       } 
/* 360 */       if (ret.amount == 0) {
/* 361 */         return null;
/*     */       }
/* 363 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(int maxDrain, boolean doDrain) {
/* 368 */       for (Fluids.InternalFluidTank tank : Fluids.this.getAllTanks()) {
/* 369 */         if (!tank.canDrain(this.side)) {
/*     */           continue;
/*     */         }
/* 372 */         FluidStack stack = tank.drain(maxDrain, false);
/* 373 */         if (stack != null) {
/*     */           
/* 375 */           stack.amount = maxDrain;
/* 376 */           return drain(stack, doDrain);
/*     */         } 
/*     */       } 
/* 379 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Fluids.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */