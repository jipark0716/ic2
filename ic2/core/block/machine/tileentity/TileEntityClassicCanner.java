/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ @Delegated(current = TileEntityCanner.class, old = TileEntityClassicCanner.class) public class TileEntityClassicCanner extends TileEntityElectricMachine implements IHasGui, IGuiValueProvider, INetworkTileEntityEventListener { public short progress; public int energyConsume; public int operationLength; private int fuelQuality; protected Mode mode; protected AudioSource audioSource; public final InvSlot resInputSlot; public final InvSlotConsumable inputSlot; public final InvSlotOutput outputSlot; public void func_145839_a(NBTTagCompound nbt) { super.func_145839_a(nbt); this.fuelQuality = nbt.func_74762_e("fuelQuality"); } public NBTTagCompound func_189515_b(NBTTagCompound nbt) { super.func_189515_b(nbt); nbt.func_74768_a("fuelQuality", this.fuelQuality); return nbt; } protected void onUnloaded() { super.onUnloaded(); if (IC2.platform.isRendering() && this.audioSource != null) { IC2.audioManager.removeSources(this); this.audioSource = null; }  } public double getGuiValue(String name) { if ("progress".equals(name)) { int l = this.operationLength; if (this.mode == Mode.FOOD && !this.resInputSlot.isEmpty()) { int food = getFoodValue(this.resInputSlot.get()); if (food > 0)
/*     */           l = 50 * food;  }  if (this.mode == Mode.CF)
/*     */         l = 50;  return this.progress / l; }  throw new IllegalArgumentException("Unexpected name: " + name); } protected void updateEntityServer() { super.updateEntityServer(); boolean needsInvUpdate = false; boolean canOperate = canOperate(); if (canOperate && this.energy.useEnergy(this.energyConsume)) { setActive(true); if (this.progress == 0)
/*     */         ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true);  this.progress = (short)(this.progress + 1); if ((this.mode == Mode.FOOD && this.progress >= getFoodValue(this.resInputSlot.get()) * 50) || (this.mode == Mode.FUEL && this.progress > 0 && this.progress % 100 == 0) || (this.mode == Mode.CF && this.progress >= 50)) { if (this.mode != Mode.FUEL || this.progress >= 600) { operate(false); this.fuelQuality = 0; this.progress = 0; } else { operate(true); }  needsInvUpdate = true; ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 2, true); } else if (this.progress % 50 == 0) { ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 2, true); ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true); }  }
/*     */     else { if (getActive() && this.progress > 0)
/*     */         ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 1, true);  if (!canOperate && this.mode != Mode.FUEL) { this.fuelQuality = 0; this.progress = 0; }
/*     */        setActive(false); }
/*     */      if (needsInvUpdate)
/*     */       func_70296_d();  } public void operate(boolean incremental) { MachineRecipeResult<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> result; int fuel; ItemStack cfPack; ICannerBottleRecipeManager.RawInput newInput; switch (this.mode) { case FOOD:
/*     */         result = Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false); this.outputSlot.add((ItemStack)result.getOutput()); newInput = (ICannerBottleRecipeManager.RawInput)result.getAdjustedInput(); this.inputSlot.put(newInput.container); this.resInputSlot.put(newInput.fill); return;
/*     */       case FUEL:
/*     */         fuel = getFuelValue(this.resInputSlot.get()); this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get())); this.fuelQuality += fuel; if (!incremental)
/*     */           if (StackUtil.checkItemEquality(this.inputSlot.get(), ItemName.crafting.getItemStack((Enum)CraftingItemType.empty_fuel_can))) { this.inputSlot.consume(1); ItemStack itemStack = ItemName.filled_fuel_can.getItemStack(); NBTTagCompound data = StackUtil.getOrCreateNbtData(itemStack); data.func_74768_a("value", this.fuelQuality); this.outputSlot.add(itemStack); }
/*     */           else { int damage = this.inputSlot.get().func_77952_i(); damage -= this.fuelQuality; if (damage < 1)
/*     */               damage = 1;  this.inputSlot.clear(); this.outputSlot.add(new ItemStack(ItemName.jetpack.getInstance(), 1, damage)); }
/*     */             return;
/*     */       case CF:
/*     */         this.resInputSlot.put(StackUtil.decSize(this.resInputSlot.get())); cfPack = this.inputSlot.get(); cfPack.func_77964_b(cfPack.func_77952_i() + 2); if (this.resInputSlot.isEmpty() || cfPack.func_77952_i() > cfPack.func_77958_k() - 2) { this.outputSlot.add(cfPack); this.inputSlot.clear(); }
/*     */         else { this.inputSlot.put(cfPack); }
/*     */          return; }
/*     */      assert false; } public boolean canOperate() { int fuel; ItemStack cfPack; if (this.inputSlot.isEmpty() || this.resInputSlot.isEmpty())
/*     */       return false;  switch (this.mode) { case FOOD:
/*     */         return (Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(this.inputSlot.get(), this.resInputSlot.get()), false) != null);
/*     */       case FUEL:
/*     */         fuel = getFuelValue(this.resInputSlot.get()); return (fuel > 0 && this.outputSlot.canAdd(ItemName.jetpack.getItemStack()));
/*     */       case CF:
/*     */         cfPack = this.inputSlot.get(); return (cfPack.func_77952_i() <= cfPack.func_77958_k() - 2 && getPelletValue(this.resInputSlot.get()) > 0 && this.outputSlot.canAdd(cfPack)); }
/*     */      assert false; return false; } public Mode getMode() { if (!this.inputSlot.isEmpty()) { ItemStack input = this.inputSlot.get(); if (StackUtil.checkItemEquality(input, ItemName.crafting.getItemStack((Enum)CraftingItemType.tin_can)))
/*     */         return Mode.FOOD;  if (StackUtil.checkItemEquality(input, ItemName.crafting.getItemStack((Enum)CraftingItemType.empty_fuel_can)) || input.func_77973_b() == ItemName.jetpack.getInstance())
/*     */         return Mode.FUEL;  if (input.func_77973_b() == ItemName.cf_pack.getInstance())
/*     */         return Mode.CF;  }
/*  41 */      return Mode.NONE; } public static int getFoodValue(ItemStack stack) { MachineRecipeResult<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> result = Recipes.cannerBottle.apply(new ICannerBottleRecipeManager.RawInput(StackUtil.copyWithSize(ItemName.crafting.getItemStack((Enum)CraftingItemType.tin_can), 2147483647), stack), false); return (result == null) ? 0 : StackUtil.getSize((ItemStack)result.getOutput()); } public TileEntityClassicCanner() { super(600, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     this.progress = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     this.fuelQuality = 0; this.energyConsume = 1; this.operationLength = 600; this.resInputSlot = new InvSlot((IInventorySlotHolder)this, "input", InvSlot.Access.I, 1); this.inputSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "canInput", 1, new ItemStack[] { ItemName.crafting.getItemStack((Enum)CraftingItemType.tin_can), ItemName.crafting.getItemStack((Enum)CraftingItemType.empty_fuel_can) }) {
/*     */         public boolean accepts(ItemStack stack) { if (StackUtil.isEmpty(stack)) return false;  Item item = stack.func_77973_b(); if (item == ItemName.jetpack.getInstance() || item == ItemName.cf_pack.getInstance()) return true;  return super.accepts(stack); }
/*     */         public void onChanged() { super.onChanged(); TileEntityClassicCanner.this.mode = TileEntityClassicCanner.this.getMode(); }
/*     */       }; this.outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1); }
/*     */   public int getFuelValue(ItemStack stack) { if (StackUtil.isEmpty(stack)) return 0;  if (StackUtil.checkItemEquality(stack, ItemName.cell.getItemStack((Enum)CellType.coalfuel))) return 2548;  if (StackUtil.checkItemEquality(stack, ItemName.cell.getItemStack((Enum)CellType.biofuel))) return 868;  if (stack.func_77973_b() == Items.field_151137_ax && this.fuelQuality > 0) return (int)(this.fuelQuality * 0.2D);  if (stack.func_77973_b() == Items.field_151114_aO && this.fuelQuality > 0) return (int)(this.fuelQuality * 0.3D);  if (stack.func_77973_b() == Items.field_151016_H && this.fuelQuality > 0) return (int)(this.fuelQuality * 0.4D);  return 0; }
/*     */   public static int getPelletValue(ItemStack item) { if (StackUtil.isEmpty(item)) return 0;  return StackUtil.checkItemEquality(item, ItemName.crafting.getItemStack((Enum)CraftingItemType.pellet)) ? StackUtil.getSize(item) : 0; }
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) { return (ContainerBase<?>)new ContainerClassicCanner(player, this); }
/*     */   @SideOnly(Side.CLIENT) public GuiScreen getGui(EntityPlayer player, boolean isAdmin) { return (GuiScreen)new GuiClassicCanner(new ContainerClassicCanner(player, this)); } public void onGuiClosed(EntityPlayer entityPlayer) {} public String getStartSoundFile() { return "Machines/CannerOp.ogg"; } public String getInterruptSoundFile() { return "Machines/InterruptOne.ogg"; } public void onNetworkEvent(int event) { if (this.audioSource == null) this.audioSource = IC2.audioManager.createSource(this, getStartSoundFile());  switch (event) { case 0: if (this.audioSource != null) this.audioSource.play();  break;case 2: if (this.audioSource != null)
/*     */           this.audioSource.stop();  break;case 1: if (this.audioSource != null) { this.audioSource.stop(); IC2.audioManager.playOnce(this, PositionSpec.Center, getInterruptSoundFile(), false, IC2.audioManager.getDefaultVolume()); }  break; }  } private enum Mode
/*     */   {
/* 394 */     NONE, FOOD, FUEL, CF;
/*     */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityClassicCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */