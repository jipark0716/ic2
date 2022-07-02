/*     */ package ic2.core.item;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.capability.CapabilityFluidHandlerItem;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemIC2FluidContainer
/*     */   extends ItemIC2
/*     */   implements IMultiItem<FluidName>, IItemHudInfo
/*     */ {
/*     */   protected final int capacity;
/*     */   
/*     */   public ItemIC2FluidContainer(ItemName name, int capacity) {
/*  40 */     super(name);
/*     */     
/*  42 */     this.capacity = capacity;
/*     */     
/*  44 */     func_77627_a(true);
/*     */     
/*  46 */     addCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, new Function<ItemStack, IFluidHandlerItem>()
/*     */         {
/*     */           public IFluidHandlerItem apply(@Nullable ItemStack stack) {
/*  49 */             return (IFluidHandlerItem)new CapabilityFluidHandlerItem(stack, ItemIC2FluidContainer.this.capacity)
/*     */               {
/*     */                 public boolean canFillFluidType(FluidStack fluid)
/*     */                 {
/*  53 */                   return (fluid != null && ItemIC2FluidContainer.this.canfill(fluid.getFluid()));
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public boolean canDrainFluidType(FluidStack fluid) {
/*  59 */                   return (fluid != null && ItemIC2FluidContainer.this.canfill(fluid.getFluid()));
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(FluidName type) {
/*  68 */     return getItemStack(type.getInstance());
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(Fluid fluid) {
/*  72 */     ItemStack ret = new ItemStack(this);
/*     */     
/*  74 */     if (fluid == null) {
/*  75 */       return ret;
/*     */     }
/*  77 */     IFluidHandlerItem handler = FluidUtil.getFluidHandler(ret);
/*  78 */     if (handler == null) return null; 
/*  79 */     if (handler.fill(new FluidStack(fluid, 2147483647), true) > 0) {
/*  80 */       return handler.getContainer();
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(String variant) {
/*  88 */     if (variant == null || variant.isEmpty()) return new ItemStack(this);
/*     */     
/*  90 */     Fluid fluid = FluidRegistry.getFluid(variant);
/*  91 */     if (fluid == null) return null;
/*     */     
/*  93 */     return getItemStack(fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/*  98 */     if (stack == null) throw new NullPointerException("null stack"); 
/*  99 */     if (stack.func_77973_b() != this) throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this);
/*     */     
/* 101 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/* 102 */     if (fs == null || fs.getFluid() == null) return null;
/*     */     
/* 104 */     return fs.getFluid().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<FluidName> getAllTypes() {
/* 109 */     return EnumSet.allOf(FluidName.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ItemStack> getAllStacks() {
/* 114 */     Set<ItemStack> ret = new HashSet<>();
/*     */     
/* 116 */     ret.add(new ItemStack(this));
/* 117 */     for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
/* 118 */       ItemStack add = getItemStack(fluid);
/* 119 */       if (add != null) {
/* 120 */         ret.add(add);
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasContainerItem(ItemStack stack) {
/* 129 */     return (FluidUtil.getFluidContained(stack) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getContainerItem(ItemStack stack) {
/* 134 */     if (!hasContainerItem(stack)) return super.getContainerItem(stack);
/*     */     
/* 136 */     ItemStack ret = StackUtil.copyWithSize(stack, 1);
/* 137 */     IFluidHandlerItem handler = FluidUtil.getFluidHandler(ret);
/* 138 */     handler.drain(2147483647, true);
/*     */     
/* 140 */     return handler.getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 146 */     super.func_77624_a(stack, world, tooltip, advanced);
/*     */     
/* 148 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/*     */     
/* 150 */     if (fs != null) {
/* 151 */       tooltip.add("< " + fs.getLocalizedName() + ", " + fs.amount + " mB >");
/*     */     } else {
/* 153 */       tooltip.add(Localization.translate("ic2.item.FluidContainer.Empty"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 159 */     List<String> info = new LinkedList<>();
/*     */     
/* 161 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/*     */     
/* 163 */     if (fs != null) {
/* 164 */       info.add("< " + FluidRegistry.getFluidName(fs) + ", " + fs.amount + " mB >");
/*     */     } else {
/* 166 */       info.add(Localization.translate("ic2.item.FluidContainer.Empty"));
/*     */     } 
/*     */     
/* 169 */     return info;
/*     */   }
/*     */   
/*     */   public abstract boolean canfill(Fluid paramFluid);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemIC2FluidContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */