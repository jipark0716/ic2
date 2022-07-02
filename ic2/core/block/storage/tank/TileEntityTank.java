/*     */ package ic2.core.block.storage.tank;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public abstract class TileEntityTank extends TileEntityInventory implements IHasGui {
/*     */   protected final Fluids fluidsComponent;
/*     */   
/*     */   public TileEntityTank(int bucketMultiplier) {
/*  40 */     this.fluidsComponent = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  41 */     this.contents = (FluidTank)this.fluidsComponent.addTank("contents", 1000 * bucketMultiplier);
/*     */   }
/*     */   @GuiSynced
/*     */   protected final FluidTank contents;
/*     */   protected List<ItemStack> getAuxDrops(int fortune) {
/*  46 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/*  51 */     super.onPlaced(stack, placer, facing);
/*     */     
/*  53 */     if (!this.field_145850_b.field_72995_K) {
/*  54 */       NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
/*  55 */       this.contents.readFromNBT(tag);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/*  61 */     NBTTagCompound tag = StackUtil.getOrCreateNbtData(drop);
/*     */     
/*  63 */     if (this.contents.getFluidAmount() > 0) this.contents.writeToNBT(tag);
/*     */     
/*  65 */     return drop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  70 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/*  72 */     if (!this.field_145850_b.field_72995_K && 
/*  73 */       LiquidUtil.isFluidContainer(stack)) {
/*  74 */       boolean changed = FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)this.fluidsComponent.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side));
/*     */       
/*  76 */       if (changed) func_70296_d();
/*     */       
/*  78 */       return changed;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return super.onActivated(player, hand, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack stack, List<String> info, ITooltipFlag advanced) {
/*  88 */     info.add("Capacity: " + this.contents.getCapacity() + " mB");
/*     */     
/*  90 */     NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
/*  91 */     if (!tag.func_74764_b("Empty")) {
/*  92 */       FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tag);
/*  93 */       if (fluidStack == null) {
/*  94 */         info.add("Empty");
/*     */       } else {
/*  96 */         info.add(fluidStack.getLocalizedName());
/*  97 */         info.add("Amount: " + fluidStack.amount + " mB");
/*  98 */         info.add("Type: " + (fluidStack.getFluid().isGaseous() ? "Gas" : "Liquid"));
/*     */       } 
/*     */     } else {
/* 101 */       info.add("Empty");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundType getBlockSound(Entity entity) {
/* 107 */     return SoundType.field_185852_e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<? extends TileEntityTank> getGuiContainer(EntityPlayer player) {
/* 113 */     return (ContainerBase<? extends TileEntityTank>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 119 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\storage\tank\TileEntityTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */