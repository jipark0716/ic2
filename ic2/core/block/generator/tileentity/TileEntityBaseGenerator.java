/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotCharge;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntityBaseGenerator
/*     */   extends TileEntityInventory
/*     */   implements IHasGui
/*     */ {
/*     */   public final InvSlotCharge chargeSlot;
/*     */   protected final Energy energy;
/*     */   @GuiSynced
/*     */   public int fuel;
/*     */   protected double production;
/*     */   private int ticksSinceLastActiveUpdate;
/*     */   private int activityMeter;
/*     */   public AudioSource audioSource;
/*     */   
/*     */   public TileEntityBaseGenerator(double production, int tier, int maxStorage) {
/* 198 */     this.fuel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     this.activityMeter = 0;
/*     */     this.production = production;
/*     */     this.ticksSinceLastActiveUpdate = IC2.random.nextInt(256);
/*     */     this.chargeSlot = new InvSlotCharge((IInventorySlotHolder)this, 1);
/*     */     this.energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSource((TileEntityBlock)this, maxStorage, tier).addManagedSlot((InvSlot)this.chargeSlot));
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*     */     super.func_145839_a(nbttagcompound);
/*     */     this.fuel = nbttagcompound.func_74762_e("fuel");
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*     */     super.func_189515_b(nbt);
/*     */     nbt.func_74768_a("fuel", this.fuel);
/*     */     return nbt;
/*     */   }
/*     */   
/*     */   protected void onUnloaded() {
/*     */     if (IC2.platform.isRendering() && this.audioSource != null) {
/*     */       IC2.audioManager.removeSources(this);
/*     */       this.audioSource = null;
/*     */     } 
/*     */     super.onUnloaded();
/*     */   }
/*     */   
/*     */   protected void updateEntityServer() {
/*     */     super.updateEntityServer();
/*     */     boolean needsInvUpdate = false;
/*     */     if (needsFuel())
/*     */       needsInvUpdate = gainFuel(); 
/*     */     boolean newActive = gainEnergy();
/*     */     if (needsInvUpdate)
/*     */       func_70296_d(); 
/*     */     if (!delayActiveUpdate()) {
/*     */       setActive(newActive);
/*     */     } else {
/*     */       if (this.ticksSinceLastActiveUpdate % 256 == 0) {
/*     */         setActive((this.activityMeter > 0));
/*     */         this.activityMeter = 0;
/*     */       } 
/*     */       if (newActive) {
/*     */         this.activityMeter++;
/*     */       } else {
/*     */         this.activityMeter--;
/*     */       } 
/*     */       this.ticksSinceLastActiveUpdate++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean gainEnergy() {
/*     */     if (isConverting()) {
/*     */       this.energy.addEnergy(this.production);
/*     */       this.fuel--;
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/*     */     return (!needsFuel() && this.energy.getFreeEnergy() >= this.production);
/*     */   }
/*     */   
/*     */   public boolean needsFuel() {
/*     */     return (this.fuel <= 0 && this.energy.getFreeEnergy() >= this.production);
/*     */   }
/*     */   
/*     */   public abstract boolean gainFuel();
/*     */   
/*     */   public String getOperationSoundFile() {
/*     */     return null;
/*     */   }
/*     */   
/*     */   protected boolean delayActiveUpdate() {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */   
/*     */   public ContainerBase<? extends TileEntityBaseGenerator> getGuiContainer(EntityPlayer player) {
/*     */     return (ContainerBase<? extends TileEntityBaseGenerator>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*     */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/*     */     if (field.equals("active")) {
/*     */       if (this.audioSource == null && getOperationSoundFile() != null)
/*     */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getOperationSoundFile(), true, false, IC2.audioManager.getDefaultVolume()); 
/*     */       if (getActive()) {
/*     */         if (this.audioSource != null)
/*     */           this.audioSource.play(); 
/*     */       } else if (this.audioSource != null) {
/*     */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/*     */     super.onNetworkUpdate(field);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityBaseGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */