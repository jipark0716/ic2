/*     */ package ic2.core.item.type;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.crop.TileEntityCrop;
/*     */ import ic2.core.profile.NotExperimental;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.capability.FluidTankProperties;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fluids.capability.IFluidTankProperties;
/*     */ import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
/*     */ 
/*     */ @NotExperimental
/*     */ public enum CellType
/*     */   implements IIdProvider
/*     */ {
/*  32 */   empty(0),
/*  33 */   water(1, FluidRegistry.WATER),
/*  34 */   lava(2, FluidRegistry.LAVA),
/*  35 */   air(3, FluidName.air.getInstance()),
/*  36 */   electrolyzed_water(4),
/*  37 */   biofuel(5),
/*  38 */   coalfuel(6),
/*  39 */   bio(7),
/*  40 */   hydrated_coal(8),
/*  41 */   weed_ex(9),
/*  42 */   hydration(10);
/*     */   
/*     */   private final int id;
/*     */   
/*     */   final Fluid fluid;
/*     */   
/*     */   CellType(int id, Fluid fluid) {
/*  49 */     this.id = id;
/*  50 */     this.fluid = fluid;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  55 */     return name();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/*  60 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getStackSize() {
/*  64 */     return (this == weed_ex || this == hydration) ? 1 : 64;
/*     */   }
/*     */   
/*     */   public boolean isFluidContainer() {
/*  68 */     return (this.fluid != null || this == empty);
/*     */   }
/*     */   
/*     */   public boolean hasCropAction() {
/*  72 */     return (this == water || this == weed_ex || this == hydration);
/*     */   }
/*     */   
/*     */   public int getUsage(ItemStack stack) {
/*  76 */     switch (this) {
/*     */       case weed_ex:
/*  78 */         return stack.func_77942_o() ? stack.func_77978_p().func_74762_e("weedEX") : 0;
/*     */       
/*     */       case hydration:
/*  81 */         return stack.func_77942_o() ? stack.func_77978_p().func_74762_e("hydration") : 0;
/*     */     } 
/*     */     
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximum(ItemStack stack) {
/*  89 */     switch (this) {
/*     */       case weed_ex:
/*  91 */         return 64;
/*     */       
/*     */       case hydration:
/*  94 */         return 10000;
/*     */     } 
/*     */     
/*  97 */     return 0;
/*     */   }
/*     */   
/*     */   public EnumActionResult doCropAction(ItemStack stack, Consumer<ItemStack> result, TileEntityCrop crop, boolean manual) {
/*     */     IFluidHandlerItem handler;
/* 102 */     assert hasCropAction();
/*     */     
/* 104 */     switch (this) {
/*     */       case water:
/* 106 */         if (crop.getStorageWater() < 10) {
/* 107 */           crop.setStorageWater(10);
/*     */           
/* 109 */           return EnumActionResult.SUCCESS;
/*     */         } 
/* 111 */         return EnumActionResult.FAIL;
/*     */ 
/*     */       
/*     */       case weed_ex:
/* 115 */         handler = new WeedExHandler(stack);
/* 116 */         if (crop.applyWeedEx((IFluidHandler)handler, manual)) {
/* 117 */           result.accept(handler.getContainer());
/*     */           
/* 119 */           return EnumActionResult.SUCCESS;
/*     */         } 
/* 121 */         return EnumActionResult.FAIL;
/*     */ 
/*     */ 
/*     */       
/*     */       case hydration:
/* 126 */         handler = new HydrationHandler(stack, manual);
/* 127 */         if (crop.applyHydration((IFluidHandler)handler)) {
/* 128 */           result.accept(handler.getContainer());
/*     */           
/* 130 */           return EnumActionResult.SUCCESS;
/*     */         } 
/* 132 */         return EnumActionResult.FAIL;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     throw new IllegalStateException("Type was " + this);
/*     */   }
/*     */   
/*     */   public static class CellFluidHandler extends FluidBucketWrapper {
/*     */     private static final Map<Fluid, CellType> VALID_FLUIDS;
/*     */     protected final Supplier<CellType> typeGetter;
/*     */     
/*     */     static {
/* 145 */       VALID_FLUIDS = new IdentityHashMap<>((Map<? extends Fluid, ? extends CellType>)Arrays.<CellType>stream(CellType.values()).filter(type -> (type.fluid != null)).collect(Collectors.toMap(type -> type.fluid, Function.identity())));
/*     */     }
/*     */ 
/*     */     
/*     */     public CellFluidHandler(ItemStack container, Function<ItemStack, CellType> typeGetter) {
/* 150 */       super(container);
/*     */       
/* 152 */       this.typeGetter = (() -> (CellType)typeGetter.apply(this.container));
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack getFluid() {
/* 157 */       CellType type = this.typeGetter.get();
/* 158 */       assert type.isFluidContainer();
/*     */       
/* 160 */       return (type != null && type.fluid != null) ? new FluidStack(type.fluid, 1000) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canFillFluidType(FluidStack fluid) {
/* 165 */       assert fluid != null;
/* 166 */       assert fluid.getFluid() != null;
/*     */       
/* 168 */       return (this.typeGetter.get() == CellType.empty && VALID_FLUIDS.containsKey(fluid.getFluid()));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setFluid(FluidStack stack) {
/* 173 */       if (stack == null) {
/* 174 */         assert this.typeGetter.get() != CellType.empty;
/*     */         
/* 176 */         this.container = ItemName.cell.getItemStack(CellType.empty);
/*     */       } else {
/* 178 */         assert this.typeGetter.get() == CellType.empty;
/* 179 */         assert VALID_FLUIDS.containsKey(stack.getFluid());
/*     */         
/* 181 */         this.container = ItemName.cell.getItemStack(VALID_FLUIDS.get(stack.getFluid()));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class WeedExHandler
/*     */     implements IFluidHandlerItem
/*     */   {
/*     */     public static final String NBT = "weedEX";
/*     */     public static final int CHARGES = 64;
/*     */     private static final int DRAIN = 50;
/*     */     protected ItemStack container;
/*     */     
/*     */     public WeedExHandler(ItemStack stack) {
/* 195 */       this.container = stack;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getContainer() {
/* 200 */       return this.container;
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(FluidStack resource, boolean doFill) {
/* 205 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(FluidStack resource, boolean doDrain) {
/* 210 */       if (resource == null || resource.getFluid() != FluidName.weed_ex.getInstance()) return null;
/*     */       
/* 212 */       return drain(resource.amount, doDrain);
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(int maxDrain, boolean doDrain) {
/* 217 */       if (maxDrain < 50) return null;
/*     */       
/* 219 */       if (doDrain) {
/* 220 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(this.container);
/* 221 */         int amount = nbt.func_74762_e("weedEX") + 1;
/*     */         
/* 223 */         if (amount >= 64) {
/* 224 */           this.container = StackUtil.decSize(this.container);
/*     */         } else {
/* 226 */           nbt.func_74768_a("weedEX", amount);
/*     */         } 
/*     */       } 
/*     */       
/* 230 */       return new FluidStack(FluidName.weed_ex.getInstance(), 50);
/*     */     }
/*     */ 
/*     */     
/*     */     public IFluidTankProperties[] getTankProperties() {
/* 235 */       return new IFluidTankProperties[] { (IFluidTankProperties)new FluidTankProperties(new FluidStack(FluidName.weed_ex.getInstance(), (64 - this.container.func_77952_i()) * 50), 3200, false, true) };
/*     */     }
/*     */   }
/*     */   
/*     */   private static class HydrationHandler
/*     */     implements IFluidHandlerItem
/*     */   {
/*     */     public static final String NBT = "hydration";
/*     */     public static final int CHARGES = 10000;
/*     */     protected ItemStack container;
/*     */     protected final boolean manual;
/*     */     
/*     */     public HydrationHandler(ItemStack stack, boolean manual) {
/* 248 */       this.container = stack;
/* 249 */       this.manual = manual;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getContainer() {
/* 254 */       return this.container;
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(FluidStack resource, boolean doFill) {
/* 259 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(FluidStack resource, boolean doDrain) {
/* 264 */       if (resource == null || resource.getFluid() != FluidRegistry.WATER) return null;
/*     */       
/* 266 */       return drain(resource.amount, doDrain);
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(int maxDrain, boolean doDrain) {
/*     */       int remaining;
/* 272 */       if (this.container.func_77942_o()) {
/* 273 */         remaining = 10000 - this.container.func_77978_p().func_74762_e("hydration");
/*     */       } else {
/* 275 */         remaining = 10000;
/*     */       } 
/* 277 */       int target = Math.min(maxDrain, remaining);
/*     */ 
/*     */       
/* 280 */       if (!this.manual && target > 180) target = 180;
/*     */       
/* 282 */       if (doDrain) {
/* 283 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(this.container);
/* 284 */         int amount = nbt.func_74762_e("hydration") + target;
/*     */         
/* 286 */         if (amount >= 10000) {
/* 287 */           this.container = StackUtil.decSize(this.container);
/*     */         } else {
/* 289 */           nbt.func_74768_a("hydration", amount);
/*     */         } 
/*     */       } 
/*     */       
/* 293 */       return new FluidStack(FluidRegistry.WATER, target);
/*     */     }
/*     */ 
/*     */     
/*     */     public IFluidTankProperties[] getTankProperties() {
/* 298 */       return new IFluidTankProperties[] { (IFluidTankProperties)new FluidTankProperties(new FluidStack(FluidRegistry.WATER, (10000 - this.container.func_77952_i()) / 200), 50, false, true) };
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\CellType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */