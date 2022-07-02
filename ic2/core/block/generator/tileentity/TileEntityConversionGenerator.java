/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public abstract class TileEntityConversionGenerator
/*     */   extends TileEntityInventory implements IHasGui, IEnergySource {
/*  26 */   private static final NumberFormat FORMAT = new DecimalFormat("#.#");
/*     */   @GuiSynced
/*     */   private double lastProduction;
/*     */   @GuiSynced
/*     */   private double maxProduction;
/*     */   private double production;
/*     */   private boolean registeredToEnet;
/*     */   
/*     */   protected void updateEntityServer() {
/*  35 */     super.updateEntityServer();
/*     */     
/*  37 */     this.lastProduction = this.production;
/*  38 */     this.production = 0.0D;
/*     */     
/*  40 */     setActive((this.maxProduction > 0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/*  45 */     super.onUnloaded();
/*  46 */     if (this.registeredToEnet && !this.field_145850_b.field_72995_K) {
/*  47 */       EnergyNet.instance.removeTile((IEnergyTile)this);
/*  48 */       this.registeredToEnet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  54 */     super.onLoaded();
/*  55 */     if (!this.registeredToEnet && !this.field_145850_b.field_72995_K) {
/*  56 */       EnergyNet.instance.addTile((TileEntity)this);
/*  57 */       this.registeredToEnet = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getProduction() {
/*  62 */     return FORMAT.format(this.lastProduction);
/*     */   }
/*     */   
/*     */   public String getMaxProduction() {
/*  66 */     return FORMAT.format(this.maxProduction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityConversionGenerator> getGuiContainer(EntityPlayer player) {
/*  72 */     return (ContainerBase<TileEntityConversionGenerator>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*  78 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   protected abstract int getEnergyAvailable();
/*     */ 
/*     */   
/*     */   protected abstract void drawEnergyAvailable(int paramInt);
/*     */ 
/*     */   
/*     */   protected abstract double getMultiplier();
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/*  95 */     return this.maxProduction = getEnergyAvailable() * getMultiplier();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 100 */     this.production += amount;
/* 101 */     drawEnergyAvailable((int)Math.ceil(amount / getMultiplier()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 106 */     return Math.max(EnergyNet.instance.getTierFromPower(this.maxProduction), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
/* 111 */     return (side != getFacing());
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityConversionGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */