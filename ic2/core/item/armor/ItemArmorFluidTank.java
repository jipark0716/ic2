/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.api.item.IItemHudProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.capability.CapabilityFluidHandlerItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public abstract class ItemArmorFluidTank
/*     */   extends ItemArmorUtility
/*     */   implements IItemHudInfo, IItemHudProvider.IItemHudBarProvider
/*     */ {
/*     */   protected final int capacity;
/*     */   protected final Fluid allowfluid;
/*     */   
/*     */   public ItemArmorFluidTank(ItemName name, String armorName, Fluid allowfluid, int capacity) {
/*  36 */     super(name, armorName, EntityEquipmentSlot.CHEST);
/*     */     
/*  38 */     func_77656_e(27);
/*  39 */     func_77625_d(1);
/*     */     
/*  41 */     this.capacity = capacity;
/*  42 */     this.allowfluid = allowfluid;
/*     */     
/*  44 */     addCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, new Function<ItemStack, IFluidHandlerItem>()
/*     */         {
/*     */           public IFluidHandlerItem apply(@Nullable ItemStack stack) {
/*  47 */             return (IFluidHandlerItem)new CapabilityFluidHandlerItem(stack, ItemArmorFluidTank.this.capacity)
/*     */               {
/*     */                 public boolean canFillFluidType(FluidStack fluid)
/*     */                 {
/*  51 */                   return (fluid != null && fluid.getFluid() == ItemArmorFluidTank.this.allowfluid);
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public boolean canDrainFluidType(FluidStack fluid) {
/*  57 */                   return (fluid != null && fluid.getFluid() == ItemArmorFluidTank.this.allowfluid);
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public ItemStack getContainer() {
/*  63 */                   ItemStack ret = super.getContainer();
/*  64 */                   ItemArmorFluidTank.this.Updatedamage(ret);
/*  65 */                   return ret;
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void filltank(ItemStack stack) {
/*  74 */     NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(stack);
/*  75 */     NBTTagCompound fluidTag = nbtTagCompound.func_74775_l("Fluid");
/*  76 */     FluidStack fs = new FluidStack(this.allowfluid, this.capacity);
/*  77 */     fs.writeToNBT(fluidTag);
/*  78 */     nbtTagCompound.func_74782_a("Fluid", (NBTBase)fluidTag);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack stack) {
/*  83 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/*  84 */     if (fs == null) return 0.0D; 
/*  85 */     double ret = fs.amount;
/*  86 */     return (ret > 0.0D) ? ret : 0.0D;
/*     */   }
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  90 */     return this.capacity;
/*     */   }
/*     */   
/*     */   protected void Updatedamage(ItemStack stack) {
/*  94 */     stack.func_77964_b(stack.func_77958_k() - 1 - (int)Util.map(getCharge(stack), getMaxCharge(stack), (stack.func_77958_k() - 2)));
/*     */   }
/*     */   
/*     */   public boolean isEmpty(ItemStack stack) {
/*  98 */     return (FluidUtil.getFluidContained(stack) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 105 */     super.func_77624_a(stack, world, tooltip, advanced);
/* 106 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/*     */     
/* 108 */     if (fs != null) {
/* 109 */       tooltip.add("< " + fs.getLocalizedName() + ", " + fs.amount + " mB >");
/*     */     } else {
/* 111 */       tooltip.add(Localization.translate("ic2.item.FluidContainer.Empty"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBarPercent(ItemStack stack) {
/* 118 */     return getMaxDamage(stack) - getDamage(stack) * 100 / getMaxDamage(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 123 */     List<String> info = new LinkedList<>();
/*     */     
/* 125 */     FluidStack fs = FluidUtil.getFluidContained(stack);
/*     */     
/* 127 */     if (fs != null) {
/* 128 */       info.add("< " + fs.getLocalizedName() + ", " + fs.amount + " mB >");
/*     */     } else {
/* 130 */       info.add(Localization.translate("ic2.item.FluidContainer.Empty"));
/*     */     } 
/*     */     
/* 133 */     return info;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorFluidTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */