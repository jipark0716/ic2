/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IExplosionPowerOverride;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @Delegated(current = TileEntityMassFabricator.class, old = TileEntityClassicMassFabricator.class)
/*     */ public class TileEntityMassFabricator extends TileEntityElectricMachine implements IHasGui, IUpgradableBlock, IExplosionPowerOverride {
/*     */   @GuiSynced
/*     */   public int scrap;
/*     */   
/*     */   public static Class<? extends TileEntityElectricMachine> delegate() {
/*  49 */     return IC2.version.isClassic() ? (Class)TileEntityClassicMassFabricator.class : (Class)TileEntityMassFabricator.class;
/*     */   } @GuiSynced
/*     */   public int consumedScrap; protected double maxScrapConsumption;
/*     */   public TileEntityMassFabricator() {
/*  53 */     super(Math.round(1000000.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), DEFAULT_TIER, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     this.scrap = 0;
/*     */     
/* 283 */     this.consumedScrap = 0;
/*     */     
/* 285 */     this.maxScrapConsumption = EnergyNet.instance.getPowerFromTier(DEFAULT_TIER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     this.scrapCounter = 0;
/*     */     
/* 296 */     this.amplifierSlot = new InvSlotProcessable<IRecipeInput, Integer, ItemStack>((IInventorySlotHolder)this, "scrap", 1, Recipes.matterAmplifier)
/*     */       {
/*     */         protected ItemStack getInput(ItemStack stack) {
/* 299 */           return stack;
/*     */         }
/*     */ 
/*     */         
/*     */         protected void setInput(ItemStack input) {
/* 304 */           put(input);
/*     */         }
/*     */       };
/* 307 */     this.outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/* 308 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*     */     
/* 310 */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*     */     this.redstone.subscribe(newLevel -> this.energy.setEnabled((newLevel == 0)));
/*     */     this.comparator.setUpdate(() -> {
/*     */           int count = calcRedstoneFromInvSlots(new InvSlot[] { (InvSlot)this.amplifierSlot });
/*     */           return (count > 0) ? count : ((this.scrap > 0) ? 1 : 0);
/*     */         });
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*     */     super.func_145839_a(nbt);
/*     */     this.scrap = nbt.func_74762_e("scrap");
/*     */     this.consumedScrap = nbt.func_74762_e("consumedScrap");
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*     */     super.func_189515_b(nbt);
/*     */     nbt.func_74768_a("scrap", this.scrap);
/*     */     nbt.func_74768_a("consumedScrap", this.consumedScrap);
/*     */     return nbt;
/*     */   }
/*     */   
/*     */   protected void onLoaded() {
/*     */     super.onLoaded();
/*     */     if (!(func_145831_w()).field_72995_K)
/*     */       updateUpgrades(); 
/*     */   }
/*     */   
/*     */   public void func_70296_d() {
/*     */     super.func_70296_d();
/*     */     if (!(func_145831_w()).field_72995_K)
/*     */       updateUpgrades(); 
/*     */   }
/*     */   
/*     */   public void updateUpgrades() {
/*     */     this.upgradeSlot.onChanged();
/*     */     int tier = this.upgradeSlot.getTier(DEFAULT_TIER);
/*     */     this.energy.setSinkTier(tier);
/*     */     this.dischargeSlot.setTier(tier);
/*     */     this.maxScrapConsumption = EnergyNet.instance.getPowerFromTier(tier);
/*     */   }
/*     */   
/*     */   protected void onUnloaded() {
/*     */     if (this.field_145850_b.field_72995_K && (this.audioSource != null || this.audioSourceScrap != null)) {
/*     */       IC2.audioManager.removeSources(this);
/*     */       this.audioSource = null;
/*     */       this.audioSourceScrap = null;
/*     */     } 
/*     */     super.onUnloaded();
/*     */   }
/*     */   
/*     */   protected void updateEntityServer() {
/*     */     super.updateEntityServer();
/*     */     boolean needsInvUpdate = this.upgradeSlot.tickNoMark();
/*     */     if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0D) {
/*     */       setActive(false);
/*     */     } else {
/*     */       if (this.scrap < 100000) {
/*     */         MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe = this.amplifierSlot.process();
/*     */         if (recipe != null) {
/*     */           this.amplifierSlot.consume(recipe);
/*     */           this.scrap += ((Integer)recipe.getOutput()).intValue() * 10;
/*     */         } 
/*     */       } 
/*     */       assert this.scrap >= 0;
/*     */       double scrapConversion = Math.min(Math.min(this.scrap, this.energy.getEnergy() - this.consumedScrap), this.maxScrapConsumption);
/*     */       assert scrapConversion >= 0.0D;
/*     */       boolean newActivity = false;
/*     */       if (scrapConversion > 0.0D) {
/*     */         this.consumedScrap = (int)(this.consumedScrap + scrapConversion);
/*     */         this.scrap = (int)(this.scrap - scrapConversion);
/*     */         newActivity = true;
/*     */         if (this.energy.getEnergy() >= this.energy.getCapacity() && this.consumedScrap >= REQUIRED_SCRAP)
/*     */           if (this.outputSlot.canAdd(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter))) {
/*     */             this.outputSlot.add(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter));
/*     */             this.energy.useEnergy(this.energy.getCapacity());
/*     */             this.consumedScrap = 0;
/*     */             needsInvUpdate = true;
/*     */           } else {
/*     */             newActivity = false;
/*     */           }  
/*     */       } 
/*     */       setActive(newActivity);
/*     */     } 
/*     */     if (needsInvUpdate)
/*     */       func_70296_d(); 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/*     */     super.updateEntityClient();
/*     */     if (getActive() && (this.scrapCounter = (byte)(this.scrapCounter + 1)) > 40) {
/*     */       this.scrapCounter = 0;
/*     */       if (this.audioSourceScrap == null) {
/*     */         this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", false, false, IC2.audioManager.getDefaultVolume());
/*     */       } else {
/*     */         this.audioSourceScrap.stop();
/*     */       } 
/*     */       if (this.audioSourceScrap != null)
/*     */         this.audioSourceScrap.play(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/*     */     if ("active".equals(field))
/*     */       if (getActive()) {
/*     */         if (this.audioSource == null)
/*     */           this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume()); 
/*     */         if (this.audioSource != null)
/*     */           this.audioSource.play(); 
/*     */       } else {
/*     */         this.scrapCounter = 0;
/*     */         if (this.audioSource != null)
/*     */           this.audioSource.stop(); 
/*     */         if (this.audioSourceScrap != null)
/*     */           this.audioSourceScrap.stop(); 
/*     */       }  
/*     */     super.onNetworkUpdate(field);
/*     */   }
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/*     */     return (ContainerBase<?>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*     */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   public int getScrap() {
/*     */     return this.scrap / 10;
/*     */   }
/*     */   
/*     */   public int getScrapProgress() {
/*     */     return (int)Math.min(100.0F * this.consumedScrap / REQUIRED_SCRAP, 100.0F);
/*     */   }
/*     */   
/*     */   public int getEnergyProgress() {
/*     */     return (int)Math.min(100.0D * this.energy.getFillRatio(), 100.0D);
/*     */   }
/*     */   
/*     */   public boolean getGuiState(String name) {
/*     */     if ("scrap".equals(name))
/*     */       return (this.scrap > 0); 
/*     */     if ("dev".equals(name))
/*     */       return Util.inDev(); 
/*     */     return super.getGuiState(name);
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
/*     */     tooltip.add("You probably want the " + Localization.translate(getBlockType().func_149739_a() + '.' + TeBlock.matter_generator.getName()));
/*     */   }
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
/*     */     return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */   
/*     */   public boolean shouldExplode() {
/*     */     return true;
/*     */   }
/*     */   
/*     */   public float getExplosionPower(int tier, float defaultPower) {
/*     */     return 15.0F;
/*     */   }
/*     */   
/*     */   public static final int DEFAULT_TIER = ConfigUtil.getInt(MainConfig.get(), "balance/massFabricatorTier");
/*     */   private static final int REQUIRED_SCRAP = Util.roundToNegInf(1000000.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor"));
/*     */   private static final int SCRAP_FACTOR = 10;
/*     */   private AudioSource audioSource;
/*     */   private AudioSource audioSourceScrap;
/*     */   private byte scrapCounter;
/*     */   public final InvSlotProcessable<IRecipeInput, Integer, ItemStack> amplifierSlot;
/*     */   public final InvSlotOutput outputSlot;
/*     */   public final InvSlotUpgrade upgradeSlot;
/*     */   protected final Redstone redstone;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityMassFabricator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */