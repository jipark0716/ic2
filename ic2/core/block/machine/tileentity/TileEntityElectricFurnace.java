/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.FutureSound;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotProcessableSmelting;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class TileEntityElectricFurnace
/*     */   extends TileEntityStandardMachine<ItemStack, ItemStack, ItemStack>
/*     */   implements INetworkClientTileEntityEventListener {
/*     */   protected double xp;
/*     */   protected FutureSound startingSound;
/*     */   protected String finishingSound;
/*     */   
/*     */   public TileEntityElectricFurnace() {
/*  28 */     super(3, 100, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.xp = 0.0D;
/*     */     this.inputSlot = (InvSlotProcessable<ItemStack, ItemStack, ItemStack>)new InvSlotProcessableSmelting((IInventorySlotHolder)this, "input", 1);
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*     */     super.func_145839_a(nbt);
/*     */     this.xp = nbt.func_74769_h("xp");
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*     */     super.func_189515_b(nbt);
/*     */     nbt.func_74780_a("xp", this.xp);
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
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Collection<ItemStack> getOutput(ItemStack output) {
/*     */     return Collections.singletonList(output);
/*     */   }
/*     */   
/*     */   public void operateOnce(MachineRecipeResult<ItemStack, ItemStack, ItemStack> result, Collection<ItemStack> processResult) {
/*     */     super.operateOnce(result, processResult);
/*     */     this.xp += result.getRecipe().getMetaData().func_74760_g("experience");
/*     */   }
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/*     */     if (event == 0) {
/*     */       assert !(func_145831_w()).field_72995_K;
/*     */       this.xp = TileEntityIronFurnace.spawnXP(player, this.xp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStartingSoundFile() {
/*     */     return "Machines/Electro Furnace/ElectroFurnaceStart.ogg";
/*     */   }
/*     */   
/*     */   public String getStartSoundFile() {
/*     */     return "Machines/Electro Furnace/ElectroFurnaceLoop.ogg";
/*     */   }
/*     */   
/*     */   public String getInterruptSoundFile() {
/*     */     return "Machines/Electro Furnace/ElectroFurnaceStop.ogg";
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
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/*     */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityElectricFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */