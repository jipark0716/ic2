/*     */ package ic2.core.block.comp;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.recipe.SmeltingRecipeManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class Process extends TileEntityComponent {
/*     */   protected int progress;
/*     */   public int defaultEnergyConsume;
/*     */   public int operationDuration;
/*     */   public int defaultTier;
/*     */   public int defaultEnergyStorage;
/*     */   
/*     */   public static Process asFurnace(TileEntityInventory parent) {
/*  28 */     return asFurnace(parent, 3, 100, 1, 4);
/*     */   }
/*     */   public int energyConsume; public int operationLength; public int operationsPerTick; private final InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> inputSlot; private final InvSlotOutput outputSlot; private InvSlotUpgrade upgradeSlot;
/*     */   
/*     */   public static Process asFurnace(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  33 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)SmeltingRecipeManager.SmeltingBridge.INSTANCE, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asMacerator(TileEntityInventory parent) {
/*  40 */     return asMacerator(parent, 2, 300, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asMacerator(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  45 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.macerator, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asExtractor(TileEntityInventory parent) {
/*  52 */     return asExtractor(parent, 2, 300, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asExtractor(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  57 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.extractor, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asCompressor(TileEntityInventory parent) {
/*  64 */     return asCompressor(parent, 2, 300, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asCompressor(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  69 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.compressor, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asCentrifuge(TileEntityInventory parent) {
/*  76 */     return asCentrifuge(parent, 48, 500, 3, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asCentrifuge(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  81 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.centrifuge, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asRecycler(TileEntityInventory parent) {
/*  88 */     return asRecycler(parent, 1, 45, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asRecycler(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/*  93 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.recycler, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asOreWasher(TileEntityInventory parent) {
/* 100 */     return asOreWasher(parent, 16, 500, 3, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asOreWasher(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 105 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.oreWashing, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asBlockCutter(TileEntityInventory parent) {
/* 112 */     return asBlockCutter(parent, 48, 900, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asBlockCutter(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 117 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.blockcutter, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asBlastFurnace(TileEntityInventory parent) {
/* 124 */     return asBlastFurnace(parent, 2, 300, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asBlastFurnace(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 129 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.blastfurnace, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asExtruder(TileEntityInventory parent) {
/* 136 */     return asExtruder(parent, 10, 200, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asExtruder(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 141 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.metalformerExtruding, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asCutter(TileEntityInventory parent) {
/* 148 */     return asCutter(parent, 10, 200, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asCutter(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 153 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.metalformerCutting, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Process asRollingMachine(TileEntityInventory parent) {
/* 160 */     return asRollingMachine(parent, 10, 200, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Process asRollingMachine(TileEntityInventory parent, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 165 */     return new Process(parent, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>)Recipes.metalformerRolling, operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Process(TileEntityInventory parent, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeManager) {
/* 171 */     this(parent, recipeManager, 2, 100, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Process(TileEntityInventory parent, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeManager, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 176 */     this(parent, (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)parent, "input", 1, recipeManager), operationCost, operationDuration, outputSlots, upgradeSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Process(TileEntityInventory parent, InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> inputSlot, int operationCost, int operationDuration, int outputSlots, int upgradeSlots) {
/* 181 */     super((TileEntityBlock)parent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     this.progress = 0;
/*     */     this.operationDuration = operationDuration;
/*     */     assert inputSlot != null;
/*     */     this.inputSlot = inputSlot;
/*     */     this.outputSlot = new InvSlotOutput((IInventorySlotHolder)parent, "output", outputSlots);
/*     */     if (parent instanceof IUpgradableBlock && upgradeSlots > 0)
/*     */       this.upgradeSlot = InvSlotUpgrade.createUnchecked((IInventorySlotHolder)parent, "upgrade", upgradeSlots); 
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*     */     this.progress = nbt.func_74762_e("progress");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*     */     nbt.func_74768_a("progress", this.progress);
/*     */   }
/*     */   
/*     */   public static int applyModifier(int base, int extra, double multiplier) {
/*     */     double ret = Math.round((base + extra) * multiplier);
/*     */     return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int)ret;
/*     */   }
/*     */   
/*     */   public void setOverclockRates() {
/*     */     this.upgradeSlot.onChanged();
/*     */     double previousProgress = this.progress / this.operationDuration;
/*     */     double stackOpLen = (this.operationDuration + this.upgradeSlot.extraProcessTime) * 64.0D * this.upgradeSlot.processTimeMultiplier;
/*     */     this.operationsPerTick = (int)Math.min(Math.ceil(64.0D / stackOpLen), 2.147483647E9D);
/*     */     this.operationDuration = (int)Math.round(stackOpLen * this.operationsPerTick / 64.0D);
/*     */     this.energyConsume = applyModifier(this.defaultEnergyConsume, this.upgradeSlot.extraEnergyDemand, this.upgradeSlot.energyDemandMultiplier);
/*     */     if (this.operationDuration < 1)
/*     */       this.operationDuration = 1; 
/*     */     this.progress = (short)(int)Math.floor(previousProgress * this.operationDuration + 0.1D);
/*     */   }
/*     */   
/*     */   public void operate(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result) {
/*     */     for (int i = 0; i < this.operationsPerTick; i++) {
/*     */       Collection<ItemStack> processResult = StackUtil.copy((Collection)result.getOutput());
/*     */       if (this.parent instanceof IUpgradableBlock)
/*     */         for (int j = 0; j < this.upgradeSlot.size(); j++) {
/*     */           ItemStack stack = this.upgradeSlot.get(j);
/*     */           if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IUpgradeItem)
/*     */             ((IUpgradeItem)stack.func_77973_b()).onProcessEnd(stack, (IUpgradableBlock)this.parent, processResult); 
/*     */         }  
/*     */       operateOnce(result, processResult);
/*     */       result = getOutput();
/*     */       if (result == null)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result, Collection<ItemStack> processResult) {
/*     */     this.inputSlot.consume(result);
/*     */     this.outputSlot.add(processResult);
/*     */   }
/*     */   
/*     */   public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
/*     */     if (this.inputSlot.isEmpty())
/*     */       return null; 
/*     */     MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = this.inputSlot.process();
/*     */     if (result == null)
/*     */       return null; 
/*     */     if (this.outputSlot.canAdd((Collection)result.getOutput()))
/*     */       return result; 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public int getProgress() {
/*     */     return this.progress;
/*     */   }
/*     */   
/*     */   public double getProgressRatio() {
/*     */     return (this.progress / this.operationDuration);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Process.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */