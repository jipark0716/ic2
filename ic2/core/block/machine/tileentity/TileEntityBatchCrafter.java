/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import gnu.trove.TIntCollection;
/*     */ import ic2.api.network.ClientModifiable;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.IUpgradeItem;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerBatchCrafter;
/*     */ import ic2.core.block.machine.gui.GuiBatchCrafter;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.InventorySlotCrafting;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityBatchCrafter
/*     */   extends TileEntityElectricMachine
/*     */   implements IHasGui, IUpgradableBlock, IGuiValueProvider, INetworkClientTileEntityEventListener
/*     */ {
/*  53 */   private static final Set<UpgradableProperty> UPGRADES = EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   
/*     */   public static final int defaultTier = 1;
/*     */   
/*     */   public static final int defaultEnergyConsume = 2;
/*     */   
/*     */   public static final int defaultOperationLength = 40;
/*     */   
/*     */   public static final int defaultEnergyStorage = 20000;
/*     */   
/*     */   @ClientModifiable
/*  64 */   public final ItemStack[] craftingGrid = new ItemStack[9];
/*     */   
/*  66 */   public final InvSlot[] ingredientsRow = new InvSlot[this.craftingGrid.length];
/*  67 */   public final InvSlotOutput craftingOutput = new InvSlotOutput((IInventorySlotHolder)this, "output", 1, InvSlot.InvSide.SIDE);
/*  68 */   public final InvSlotOutput containerOutput = new InvSlotOutput((IInventorySlotHolder)this, "containersOut", this.craftingGrid.length, InvSlot.InvSide.NOTSIDE);
/*  69 */   public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*     */   
/*  71 */   protected final InventoryCrafting crafting = (InventoryCrafting)new InventorySlotCrafting(3, 3)
/*     */     {
/*     */       protected ItemStack get(int index) {
/*  74 */         return StackUtil.wrapEmpty(TileEntityBatchCrafter.this.craftingGrid[index]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void put(int index, ItemStack stack) {
/*  79 */         TileEntityBatchCrafter.this.craftingGrid[index] = stack;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean func_191420_l() {
/*  84 */         for (ItemStack stack : TileEntityBatchCrafter.this.craftingGrid) {
/*  85 */           if (!StackUtil.isEmpty(stack)) {
/*  86 */             return false;
/*     */           }
/*     */         } 
/*     */         
/*  90 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void func_174888_l() {
/*  95 */         Arrays.fill((Object[])TileEntityBatchCrafter.this.craftingGrid, StackUtil.emptyStack);
/*     */       }
/*     */     };
/*  98 */   public final InventoryCrafting ingredients = (InventoryCrafting)new InventorySlotCrafting(3, 3)
/*     */     {
/*     */       protected ItemStack get(int index) {
/* 101 */         return TileEntityBatchCrafter.this.ingredientsRow[index].get();
/*     */       }
/*     */ 
/*     */       
/*     */       protected void put(int index, ItemStack stack) {
/* 106 */         TileEntityBatchCrafter.this.ingredientsRow[index].put(stack);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean func_191420_l() {
/* 111 */         for (InvSlot slot : TileEntityBatchCrafter.this.ingredientsRow) {
/* 112 */           if (!slot.isEmpty()) {
/* 113 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 117 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void func_174888_l() {
/* 122 */         for (InvSlot slot : TileEntityBatchCrafter.this.ingredientsRow) {
/* 123 */           slot.clear();
/*     */         }
/*     */       }
/*     */     };
/*     */   
/* 128 */   public final Predicate<Tuple.T2<ItemStack, Integer>> acceptPredicate = new Predicate<Tuple.T2<ItemStack, Integer>>()
/*     */     {
/*     */       public boolean apply(Tuple.T2<ItemStack, Integer> input)
/*     */       {
/* 132 */         return TileEntityBatchCrafter.this.ingredientsRow[((Integer)input.b).intValue()].accepts((ItemStack)input.a);
/*     */       }
/*     */     };
/*     */   
/* 136 */   protected IRecipe recipe = null;
/*     */   protected boolean canCraft = false;
/*     */   protected boolean newChange = true;
/*     */   protected boolean attemptToBalance = false;
/* 140 */   public ItemStack recipeOutput = StackUtil.emptyStack;
/*     */   
/*     */   public int energyConsume;
/*     */   
/*     */   public int operationLength;
/*     */   public int operationsPerTick;
/* 146 */   protected short progress = 0;
/* 147 */   protected float guiProgress = 0.0F;
/*     */ 
/*     */   
/*     */   public TileEntityBatchCrafter() {
/* 151 */     super(20000, 1);
/*     */     
/* 153 */     for (int i = 0; i < this.ingredientsRow.length; i++) {
/* 154 */       final int slot = i;
/* 155 */       this.ingredientsRow[slot] = new InvSlot((IInventorySlotHolder)this, "ingredient[" + slot + ']', InvSlot.Access.I, 1)
/*     */         {
/*     */           public boolean accepts(ItemStack ingredient) {
/* 158 */             IRecipe recipe = TileEntityBatchCrafter.this.field_145850_b.field_72995_K ? TileEntityBatchCrafter.this.findRecipe() : TileEntityBatchCrafter.this.recipe;
/* 159 */             if (recipe == null) {
/* 160 */               return false;
/*     */             }
/* 162 */             assert recipe.func_77569_a(TileEntityBatchCrafter.this.crafting, TileEntityBatchCrafter.this.field_145850_b);
/*     */             
/* 164 */             ItemStack recipeStack = TileEntityBatchCrafter.this.craftingGrid[slot];
/*     */             try {
/* 166 */               TileEntityBatchCrafter.this.craftingGrid[slot] = ingredient;
/* 167 */               return recipe.func_77569_a(TileEntityBatchCrafter.this.crafting, TileEntityBatchCrafter.this.field_145850_b);
/*     */             } finally {
/*     */               
/* 170 */               TileEntityBatchCrafter.this.craftingGrid[slot] = recipeStack;
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChanged() {
/* 183 */             super.onChanged();
/*     */             
/* 185 */             TileEntityBatchCrafter.this.ingredientChange(slot);
/*     */           }
/*     */         };
/*     */     } 
/*     */     
/* 190 */     this.energyConsume = 2;
/* 191 */     this.operationLength = 40;
/* 192 */     this.operationsPerTick = 1;
/* 193 */     this.comparator.setUpdate(() -> this.progress * 15 / this.operationLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 200 */     super.func_145839_a(nbt);
/*     */     
/* 202 */     this.progress = nbt.func_74765_d("progress");
/*     */     
/* 204 */     NBTTagList grid = nbt.func_150295_c("grid", 10);
/* 205 */     for (int i = 0; i < grid.func_74745_c(); i++) {
/* 206 */       NBTTagCompound contentTag = grid.func_150305_b(i);
/*     */       
/* 208 */       this.craftingGrid[contentTag.func_74771_c("index")] = new ItemStack(contentTag);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 214 */     super.func_189515_b(nbt);
/*     */     
/* 216 */     nbt.func_74777_a("progress", this.progress);
/*     */     
/* 218 */     NBTTagList grid = new NBTTagList();
/* 219 */     for (byte i = 0; i < this.craftingGrid.length; i = (byte)(i + 1)) {
/* 220 */       ItemStack content = this.craftingGrid[i];
/* 221 */       if (!StackUtil.isEmpty(content)) {
/*     */         
/* 223 */         NBTTagCompound contentTag = new NBTTagCompound();
/*     */         
/* 225 */         contentTag.func_74774_a("index", i);
/* 226 */         content.func_77955_b(contentTag);
/*     */         
/* 228 */         grid.func_74742_a((NBTBase)contentTag);
/*     */       } 
/* 230 */     }  nbt.func_74782_a("grid", (NBTBase)grid);
/*     */     
/* 232 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IRecipe findRecipe() {
/* 238 */     World world = func_145831_w();
/*     */     
/* 240 */     return CraftingManager.func_192413_b(this.crafting, world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void matrixChange(int slot) {
/* 249 */     if (this.recipe == null || !this.recipe.func_77569_a(this.crafting, func_145831_w())) {
/* 250 */       this.recipe = findRecipe();
/*     */     }
/*     */     
/* 253 */     this.recipeOutput = (this.recipe != null) ? this.recipe.func_77572_b(this.crafting) : StackUtil.emptyStack;
/* 254 */     this.newChange = true;
/*     */   }
/*     */   
/*     */   public void ingredientChange(int slot) {
/* 258 */     this.newChange = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/* 265 */     super.onLoaded();
/*     */     
/* 267 */     if (!(func_145831_w()).field_72995_K) {
/* 268 */       setOverclockRates();
/*     */       
/* 270 */       matrixChange(-1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 276 */     super.func_70296_d();
/*     */     
/* 278 */     if (!(func_145831_w()).field_72995_K) {
/* 279 */       setOverclockRates();
/*     */       
/* 281 */       this.attemptToBalance = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 287 */     super.updateEntityServer();
/*     */     
/* 289 */     boolean needsInvUpdate = false;
/*     */     
/* 291 */     if (this.attemptToBalance) {
/* 292 */       if (!this.ingredients.func_191420_l()) {
/* 293 */         int i = needsInvUpdate | (!((TIntCollection)(StackUtil.balanceStacks((IInventory)this.ingredients, this.acceptPredicate)).b).isEmpty() ? 1 : 0);
/*     */       }
/*     */       
/* 296 */       this.attemptToBalance = false;
/*     */     } 
/*     */     
/* 299 */     if (this.newChange) {
/* 300 */       this.canCraft = canCraft();
/* 301 */       this.newChange = false;
/*     */     } 
/*     */     
/* 304 */     if (this.canCraft && this.craftingOutput.canAdd(this.recipeOutput) && this.energy.useEnergy(this.energyConsume)) {
/* 305 */       setActive(true);
/*     */       
/* 307 */       if ((this.progress = (short)(this.progress + 1)) >= this.operationLength) {
/* 308 */         doCrafting();
/* 309 */         this.newChange = needsInvUpdate = true;
/* 310 */         this.progress = 0;
/*     */       } 
/*     */     } else {
/* 313 */       if (!hasRecipe()) {
/* 314 */         this.progress = 0;
/*     */       }
/*     */       
/* 317 */       setActive(false);
/*     */     } 
/*     */     
/* 320 */     needsInvUpdate |= this.upgradeSlot.tickNoMark();
/* 321 */     this.guiProgress = this.progress / this.operationLength;
/*     */     
/* 323 */     if (needsInvUpdate) super.func_70296_d();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRecipe() {
/* 330 */     return (this.recipe != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 337 */     if (!hasRecipe()) return false;
/*     */     
/* 339 */     for (int slot = 0; slot < this.craftingGrid.length; slot++) {
/* 340 */       if (!StackUtil.isEmpty(this.craftingGrid[slot]) && !this.ingredientsRow[slot].accepts(this.ingredientsRow[slot].get())) {
/* 341 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 345 */     return true;
/*     */   }
/*     */   
/*     */   protected void doCrafting() {
/* 349 */     for (int operation = 0; operation < this.operationsPerTick; operation++) {
/* 350 */       List<ItemStack> outputs = Collections.singletonList(this.recipeOutput);
/*     */       
/* 352 */       for (ItemStack stack : this.upgradeSlot) {
/* 353 */         if (stack != null && stack.func_77973_b() instanceof IUpgradeItem) {
/* 354 */           ((IUpgradeItem)stack.func_77973_b()).onProcessEnd(stack, this, outputs);
/*     */         }
/*     */       } 
/*     */       
/* 358 */       craft();
/*     */       
/* 360 */       if (!hasRecipe() || !this.craftingOutput.canAdd(this.recipeOutput))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   protected void craft() {
/* 365 */     assert hasRecipe();
/* 366 */     assert this.craftingOutput.canAdd(this.recipeOutput);
/* 367 */     this.craftingOutput.add(this.recipeOutput);
/*     */     
/* 369 */     NonNullList<ItemStack> nonNullList = this.recipe.func_179532_b(this.ingredients);
/*     */ 
/*     */     
/* 372 */     World world = func_145831_w();
/*     */     
/* 374 */     for (int slot = 0; slot < this.ingredientsRow.length; slot++) {
/* 375 */       ItemStack oldStack = this.ingredientsRow[slot].get();
/*     */       
/* 377 */       if (!StackUtil.isEmpty(oldStack) && !StackUtil.isEmpty(this.craftingGrid[slot])) {
/* 378 */         oldStack = StackUtil.decSize(oldStack);
/* 379 */         this.ingredientsRow[slot].put(oldStack);
/*     */       } 
/*     */       
/* 382 */       if (nonNullList.size() > slot && !StackUtil.isEmpty(nonNullList.get(slot))) {
/* 383 */         ItemStack newStack = nonNullList.get(slot);
/*     */         
/* 385 */         if (StackUtil.isEmpty(oldStack) && this.ingredientsRow[slot].accepts(newStack)) {
/* 386 */           this.ingredientsRow[slot].put(newStack);
/* 387 */         } else if (StackUtil.checkItemEqualityStrict(oldStack, newStack)) {
/* 388 */           this.ingredientsRow[slot].put(StackUtil.incSize(newStack, StackUtil.getSize(oldStack)));
/*     */         }
/* 390 */         else if (this.containerOutput.canAdd(newStack)) {
/* 391 */           this.containerOutput.add(newStack);
/*     */         } else {
/* 393 */           StackUtil.dropAsEntity(world, this.field_174879_c, newStack);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 398 */     for (int i = this.ingredientsRow.length; i < nonNullList.size(); i++) {
/* 399 */       ItemStack newStack = nonNullList.get(i);
/* 400 */       if (this.containerOutput.canAdd(newStack)) {
/* 401 */         this.containerOutput.add(newStack);
/*     */       } else {
/* 403 */         StackUtil.dropAsEntity(world, this.field_174879_c, newStack);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setOverclockRates() {
/* 409 */     this.upgradeSlot.onChanged();
/* 410 */     double previousProgress = this.progress / this.operationLength;
/*     */     
/* 412 */     this.operationsPerTick = this.upgradeSlot.getOperationsPerTick(40);
/* 413 */     this.operationLength = this.upgradeSlot.getOperationLength(40);
/* 414 */     this.energyConsume = this.upgradeSlot.getEnergyDemand(2);
/* 415 */     int tier = this.upgradeSlot.getTier(1);
/* 416 */     this.energy.setSinkTier(tier);
/* 417 */     this.dischargeSlot.setTier(tier);
/* 418 */     this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(20000, 40, 2));
/*     */     
/* 420 */     this.progress = (short)(int)Math.floor(previousProgress * this.operationLength + 0.1D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 425 */     switch (event) {
/*     */       case 0:
/* 427 */         matrixChange(-1);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 436 */     return UPGRADES;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 441 */     return this.energy.getEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 446 */     return this.energy.useEnergy(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 453 */     return (ContainerBase<?>)new ContainerBatchCrafter(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 459 */     return (GuiScreen)new GuiBatchCrafter(new ContainerBatchCrafter(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 470 */     if ("progress".equals(name)) {
/* 471 */       return this.guiProgress;
/*     */     }
/* 473 */     throw new IllegalArgumentException("Unexpected value requested: " + name);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityBatchCrafter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */