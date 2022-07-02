/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerMagnetizer;
/*     */ import ic2.core.block.machine.gui.GuiMagnetizer;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class TileEntityMagnetizer
/*     */   extends TileEntityElectricMachine
/*     */   implements IHasGui, IUpgradableBlock
/*     */ {
/*     */   public InvSlotUpgrade upgradeSlot;
/*     */   public static final int defaultMaxEnergy = 100;
/*     */   public static final int defaultTier = 1;
/*     */   private static final double boostEnergy = 2.0D;
/*     */   protected final Redstone redstone;
/*     */   
/*     */   public TileEntityMagnetizer() {
/*  32 */     super(100, 1);
/*     */     
/*  34 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*     */     
/*  36 */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
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
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  51 */     super.func_70296_d();
/*     */     
/*  53 */     if (!(func_145831_w()).field_72995_K) {
/*  54 */       setOverclockRates();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverclockRates() {
/*  62 */     this.upgradeSlot.onChanged();
/*     */     
/*  64 */     int tier = this.upgradeSlot.getTier(1);
/*  65 */     this.energy.setSinkTier(tier);
/*  66 */     this.dischargeSlot.setTier(tier);
/*  67 */     this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(100, 0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int distance() {
/*  74 */     return 20 + this.upgradeSlot.augmentation;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/*  79 */     return (ContainerBase<?>)new ContainerMagnetizer(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*  85 */     return (GuiScreen)new GuiMagnetizer(new ContainerMagnetizer(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/*  95 */     return this.energy.getEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 100 */     return this.energy.useEnergy(amount);
/*     */   }
/*     */   
/*     */   public boolean canBoost() {
/* 104 */     return (this.energy.getEnergy() >= 2.0D);
/*     */   }
/*     */   
/*     */   public void boost(double multiplier) {
/* 108 */     this.energy.useEnergy(2.0D * multiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 113 */     return EnumSet.of(UpgradableProperty.Augmentable, UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityMagnetizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */