/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.FutureSound;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotProcessableSmelting;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class TileEntityInduction
/*     */   extends TileEntityElectricMachine
/*     */   implements IHasGui, IUpgradableBlock, IGuiValueProvider, INetworkTileEntityEventListener {
/*     */   private static final short maxHeat = 10000;
/*     */   public final InvSlotProcessableSmelting inputSlotA;
/*     */   public final InvSlotProcessableSmelting inputSlotB;
/*     */   
/*     */   public TileEntityInduction() {
/*  45 */     super(10000, 2);
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
/* 322 */     this.heat = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     this.progress = 0;
/*     */     this.inputSlotA = new InvSlotProcessableSmelting((IInventorySlotHolder)this, "inputA", 1);
/*     */     this.inputSlotB = new InvSlotProcessableSmelting((IInventorySlotHolder)this, "inputB", 1);
/*     */     this.outputSlotA = new InvSlotOutput((IInventorySlotHolder)this, "outputA", 1);
/*     */     this.outputSlotB = new InvSlotOutput((IInventorySlotHolder)this, "outputB", 1);
/*     */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 2);
/*     */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*     */     this.comparator.setUpdate(() -> this.heat * 15 / 10000);
/*     */   }
/*     */   
/*     */   public final InvSlotUpgrade upgradeSlot;
/*     */   public final InvSlotOutput outputSlotA;
/*     */   public final InvSlotOutput outputSlotB;
/*     */   protected final Redstone redstone;
/*     */   protected AudioSource audioSource;
/*     */   protected FutureSound startingSound;
/*     */   protected String finishingSound;
/*     */   @GuiSynced
/*     */   public short heat;
/*     */   @GuiSynced
/*     */   public short progress;
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*     */     super.func_145839_a(nbt);
/*     */     this.heat = nbt.func_74765_d("heat");
/*     */     this.progress = nbt.func_74765_d("progress");
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*     */     super.func_189515_b(nbt);
/*     */     nbt.func_74777_a("heat", this.heat);
/*     */     nbt.func_74777_a("progress", this.progress);
/*     */     return nbt;
/*     */   }
/*     */   
/*     */   protected void onUnloaded() {
/*     */     super.onUnloaded();
/*     */     if (IC2.platform.isRendering()) {
/*     */       if (this.startingSound != null) {
/*     */         if (!this.startingSound.isComplete())
/*     */           this.startingSound.cancel(); 
/*     */         this.startingSound = null;
/*     */       } 
/*     */       if (this.finishingSound != null) {
/*     */         IC2.audioManager.removeSource(this.finishingSound);
/*     */         this.finishingSound = null;
/*     */       } 
/*     */       if (this.audioSource != null) {
/*     */         IC2.audioManager.removeSources(this);
/*     */         this.audioSource = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateEntityServer() {
/*     */     super.updateEntityServer();
/*     */     boolean needsInvUpdate = false;
/*     */     boolean newActive = getActive();
/*     */     if (this.heat == 0)
/*     */       newActive = false; 
/*     */     if (this.progress >= 4000) {
/*     */       operate();
/*     */       needsInvUpdate = true;
/*     */       this.progress = 0;
/*     */       newActive = false;
/*     */     } 
/*     */     boolean canOperate = canOperate();
/*     */     if ((canOperate || this.redstone.hasRedstoneInput()) && this.energy.useEnergy(1.0D)) {
/*     */       if (this.heat < 10000)
/*     */         this.heat = (short)(this.heat + 1); 
/*     */       newActive = true;
/*     */     } else {
/*     */       this.heat = (short)(this.heat - Math.min(this.heat, 4));
/*     */     } 
/*     */     if (!newActive || this.progress == 0) {
/*     */       if (canOperate) {
/*     */         if (this.energy.getEnergy() >= 15.0D) {
/*     */           newActive = true;
/*     */           ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true);
/*     */         } 
/*     */       } else {
/*     */         if (needsInvUpdate)
/*     */           ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 3, true); 
/*     */         this.progress = 0;
/*     */       } 
/*     */     } else if (!canOperate || this.energy.getEnergy() < 15.0D) {
/*     */       if (!canOperate)
/*     */         this.progress = 0; 
/*     */       newActive = false;
/*     */       ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 1, true);
/*     */     } 
/*     */     if (newActive && canOperate) {
/*     */       this.progress = (short)(this.progress + this.heat / 30);
/*     */       this.energy.useEnergy(15.0D);
/*     */     } 
/*     */     needsInvUpdate |= this.upgradeSlot.tickNoMark();
/*     */     if (needsInvUpdate)
/*     */       func_70296_d(); 
/*     */     if (newActive != getActive())
/*     */       setActive(newActive); 
/*     */   }
/*     */   
/*     */   public String getHeat() {
/*     */     return "" + (this.heat * 100 / 10000) + "%";
/*     */   }
/*     */   
/*     */   public int gaugeProgressScaled(int i) {
/*     */     return i * this.progress / 4000;
/*     */   }
/*     */   
/*     */   public void operate() {
/*     */     operate(this.inputSlotA, this.outputSlotA);
/*     */     operate(this.inputSlotB, this.outputSlotB);
/*     */   }
/*     */   
/*     */   public void operate(InvSlotProcessableSmelting inputSlot, InvSlotOutput outputSlot) {
/*     */     if (!canOperate(inputSlot, outputSlot))
/*     */       return; 
/*     */     MachineRecipeResult<ItemStack, ItemStack, ItemStack> result = inputSlot.process();
/*     */     outputSlot.add((ItemStack)result.getOutput());
/*     */     inputSlot.consume(result);
/*     */   }
/*     */   
/*     */   public boolean canOperate() {
/*     */     return (canOperate(this.inputSlotA, this.outputSlotA) || canOperate(this.inputSlotB, this.outputSlotB));
/*     */   }
/*     */   
/*     */   public boolean canOperate(InvSlotProcessableSmelting inputSlot, InvSlotOutput outputSlot) {
/*     */     if (inputSlot.isEmpty())
/*     */       return false; 
/*     */     MachineRecipeResult<? extends ItemStack, ? extends ItemStack, ? extends ItemStack> result = inputSlot.process();
/*     */     if (result == null)
/*     */       return false; 
/*     */     return outputSlot.canAdd((ItemStack)result.getOutput());
/*     */   }
/*     */   
/*     */   public ContainerBase<TileEntityInduction> getGuiContainer(EntityPlayer player) {
/*     */     return (ContainerBase<TileEntityInduction>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*     */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */   
/*     */   public double getEnergy() {
/*     */     return this.energy.getEnergy();
/*     */   }
/*     */   
/*     */   public boolean useEnergy(double amount) {
/*     */     return this.energy.useEnergy(amount);
/*     */   }
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/*     */     return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */   
/*     */   public double getGuiValue(String name) {
/*     */     if ("progress".equals(name))
/*     */       return gaugeProgressScaled(1000) / 1000.0D; 
/*     */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getStartingSoundFile() {
/*     */     return "Machines/Induction Furnace/InductionStart.ogg";
/*     */   }
/*     */   
/*     */   public String getStartSoundFile() {
/*     */     return "Machines/Induction Furnace/InductionLoop.ogg";
/*     */   }
/*     */   
/*     */   public String getInterruptSoundFile() {
/*     */     return "Machines/Induction Furnace/InductionStop.ogg";
/*     */   }
/*     */   
/*     */   public void onNetworkEvent(int event) {
/*     */     if (this.audioSource == null && getStartSoundFile() != null)
/*     */       this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, getStartSoundFile(), true, false, IC2.audioManager.getDefaultVolume()); 
/*     */     switch (event) {
/*     */       case 0:
/*     */         if (this.startingSound == null) {
/*     */           if (this.finishingSound != null) {
/*     */             IC2.audioManager.removeSource(this.finishingSound);
/*     */             this.finishingSound = null;
/*     */           } 
/*     */           String source = IC2.audioManager.playOnce(this, PositionSpec.Center, getStartingSoundFile(), false, IC2.audioManager.getDefaultVolume());
/*     */           if (this.audioSource != null)
/*     */             IC2.audioManager.chainSource(source, this.startingSound = new FutureSound(this.audioSource::play)); 
/*     */         } 
/*     */         break;
/*     */       case 1:
/*     */       case 3:
/*     */         if (this.audioSource != null) {
/*     */           this.audioSource.stop();
/*     */           if (this.startingSound != null) {
/*     */             if (!this.startingSound.isComplete())
/*     */               this.startingSound.cancel(); 
/*     */             this.startingSound = null;
/*     */           } 
/*     */           this.finishingSound = IC2.audioManager.playOnce(this, PositionSpec.Center, getInterruptSoundFile(), false, IC2.audioManager.getDefaultVolume());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityInduction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */