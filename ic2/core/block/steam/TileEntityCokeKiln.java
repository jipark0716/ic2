/*     */ package ic2.core.block.steam;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.recipe.dynamic.DynamicRecipe;
/*     */ import ic2.core.recipe.dynamic.DynamicRecipeManager;
/*     */ import ic2.core.recipe.dynamic.RecipeOutputIngredient;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ParticleUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityCokeKiln
/*     */   extends TileEntityInventory
/*     */   implements IMultiBlockController, IHasGui, IGuiValueProvider
/*     */ {
/*  51 */   protected int updateTicker = IC2.random.nextInt(this.tickRate);
/*     */   
/*  53 */   protected final InvSlotOutput outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1, InvSlot.InvSide.ANY);
/*     */ 
/*     */   
/*     */   public static void init() {
/*  57 */     recipeManager = new DynamicRecipeManager();
/*     */     
/*  59 */     recipeManager.createRecipe()
/*  60 */       .withInput("logWood")
/*  61 */       .withOutput(new ItemStack(Items.field_151044_h, 1, 1))
/*  62 */       .withOutput(new FluidStack(FluidName.creosote.getInstance(), 250))
/*  63 */       .withOperationDurationTicks(1800)
/*  64 */       .register();
/*     */     
/*  66 */     recipeManager.createRecipe()
/*  67 */       .withInput(new ItemStack(Items.field_151044_h, 1, 0))
/*  68 */       .withOutput(new ItemStack(ItemName.coke.getInstance(), 1))
/*  69 */       .withOutput(new FluidStack(FluidName.creosote.getInstance(), 500))
/*  70 */       .withOperationDurationTicks(1800)
/*  71 */       .register();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  76 */     super.func_145839_a(nbt);
/*     */     
/*  78 */     this.progress = nbt.func_74762_e("progress");
/*  79 */     this.operationLength = nbt.func_74762_e("operationLength");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  84 */     super.func_189515_b(nbt);
/*     */     
/*  86 */     nbt.func_74768_a("progress", this.progress);
/*  87 */     nbt.func_74768_a("operationLength", this.operationLength);
/*     */     
/*  89 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  94 */     super.updateEntityServer();
/*     */     
/*  96 */     if (this.updateTicker++ % this.tickRate != 0)
/*     */       return; 
/*  98 */     this.isFormed = hasValidStructure();
/*     */     
/* 100 */     if (!this.isFormed) {
/* 101 */       this.progress = 0;
/* 102 */       this.guiProgress = 0.0F;
/* 103 */       setActive(false);
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     boolean needsInventoryUpdate = false;
/*     */ 
/*     */     
/* 110 */     if (canWork()) {
/* 111 */       setActive(true);
/*     */ 
/*     */       
/* 114 */       if (this.progress == 0) needsInventoryUpdate = true;
/*     */       
/* 116 */       int progressNeeded = this.recipe.getOperationDuration();
/* 117 */       if (this.progress < progressNeeded) {
/* 118 */         this.progress += 20;
/*     */       }
/*     */       
/* 121 */       if (this.progress >= progressNeeded) {
/* 122 */         finishWork();
/*     */         
/* 124 */         needsInventoryUpdate = true;
/*     */       } 
/*     */     } else {
/* 127 */       setActive(false);
/*     */     } 
/*     */     
/* 130 */     if (this.progress == 0 || this.operationLength == 0) {
/* 131 */       this.guiProgress = 0.0F;
/*     */     } else {
/* 133 */       this.guiProgress = this.progress / this.operationLength;
/*     */     } 
/*     */     
/* 136 */     if (needsInventoryUpdate) func_70296_d(); 
/*     */   }
/*     */   
/*     */   protected boolean canWork() {
/* 140 */     BlockPos hatchPos = new BlockPos(this.field_174879_c.func_177958_n() + -getFacing().func_82601_c(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + -getFacing().func_82599_e());
/*     */     
/* 142 */     TileEntity hatch = this.field_145850_b.func_175625_s(hatchPos);
/*     */     
/* 144 */     if (!(hatch instanceof TileEntityCokeKilnHatch)) return false;
/*     */     
/* 146 */     ItemStack input = ((TileEntityCokeKilnHatch)hatch).inventory.get();
/*     */ 
/*     */     
/* 149 */     if (input.func_190926_b()) return false;
/*     */     
/* 151 */     if (this.recipe != null) {
/* 152 */       boolean canUse = recipeManager.apply(this.recipe, new ItemStack[] { input }, new FluidStack[0], true);
/* 153 */       if (!canUse) reset();
/*     */     
/*     */     } 
/* 156 */     DynamicRecipe maybeRecipe = this.recipe;
/*     */ 
/*     */     
/* 159 */     if (maybeRecipe != null) {
/*     */       
/* 161 */       for (RecipeOutputIngredient<?> entry : (Iterable<RecipeOutputIngredient<?>>)this.recipe.getOutputIngredients()) {
/* 162 */         if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputItemStack) {
/* 163 */           if (!this.outputSlot.canAdd((ItemStack)entry.ingredient)) return false;  continue;
/* 164 */         }  if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputFluidStack) {
/* 165 */           BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -getFacing().func_82599_e());
/*     */           
/* 167 */           TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
/*     */           
/* 169 */           if (!(grate instanceof TileEntityCokeKilnGrate)) return false;
/*     */           
/* 171 */           if (((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal((FluidStack)entry.ingredient, false) < ((FluidStack)entry.ingredient).amount) {
/* 172 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/* 176 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 180 */     maybeRecipe = recipeManager.findRecipe(new ItemStack[] { input }, new FluidStack[0]);
/*     */     
/* 182 */     if (maybeRecipe == null) return false;
/*     */ 
/*     */     
/* 185 */     updateRecipe(maybeRecipe);
/*     */ 
/*     */     
/* 188 */     for (RecipeOutputIngredient<?> entry : (Iterable<RecipeOutputIngredient<?>>)this.recipe.getOutputIngredients()) {
/* 189 */       if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputItemStack) {
/* 190 */         if (!this.outputSlot.canAdd((ItemStack)entry.ingredient)) return false;  continue;
/* 191 */       }  if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputFluidStack) {
/* 192 */         BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -getFacing().func_82599_e());
/*     */         
/* 194 */         TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
/*     */         
/* 196 */         if (!(grate instanceof TileEntityCokeKilnGrate)) return false;
/*     */         
/* 198 */         if (((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal((FluidStack)entry.ingredient, false) < ((FluidStack)entry.ingredient).amount) {
/* 199 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 203 */     return true;
/*     */   }
/*     */   
/*     */   protected void finishWork() {
/* 207 */     BlockPos hatchPos = new BlockPos(this.field_174879_c.func_177958_n() + -getFacing().func_82601_c(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + -getFacing().func_82599_e());
/*     */     
/* 209 */     TileEntity hatch = this.field_145850_b.func_175625_s(hatchPos);
/*     */     
/* 211 */     if (!(hatch instanceof TileEntityCokeKilnHatch))
/*     */       return; 
/* 213 */     InvSlot inventory = ((TileEntityCokeKilnHatch)hatch).inventory;
/*     */ 
/*     */     
/* 216 */     if (inventory.get().func_190926_b()) {
/*     */       return;
/*     */     }
/* 219 */     recipeManager.apply(this.recipe, new ItemStack[] { inventory.get() }, new FluidStack[0], false);
/*     */ 
/*     */     
/* 222 */     List<ItemStack> itemOutputs = new ArrayList<>();
/* 223 */     List<FluidStack> fluidOutputs = new ArrayList<>();
/* 224 */     for (RecipeOutputIngredient<?> entry : (Iterable<RecipeOutputIngredient<?>>)this.recipe.getOutputIngredients()) {
/* 225 */       if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputItemStack) {
/* 226 */         itemOutputs.add(StackUtil.copy((ItemStack)entry.ingredient)); continue;
/* 227 */       }  if (entry instanceof ic2.core.recipe.dynamic.RecipeOutputFluidStack) {
/* 228 */         fluidOutputs.add(((FluidStack)entry.ingredient).copy());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 234 */     for (ItemStack stack : itemOutputs) {
/* 235 */       int amount = this.outputSlot.add(StackUtil.copy(stack));
/* 236 */       stack.func_190918_g(amount);
/*     */     } 
/*     */     
/* 239 */     itemOutputs.clear();
/*     */ 
/*     */     
/* 242 */     BlockPos gratePos = new BlockPos(this.field_174879_c.func_177958_n() + -getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + -getFacing().func_82599_e());
/*     */     
/* 244 */     TileEntity grate = this.field_145850_b.func_175625_s(gratePos);
/*     */     
/* 246 */     if (grate instanceof TileEntityCokeKilnGrate) {
/* 247 */       for (FluidStack stack : fluidOutputs) {
/* 248 */         int amount = ((TileEntityCokeKilnGrate)grate).fluidTank.fillInternal(stack, true);
/* 249 */         stack.amount -= amount;
/*     */       } 
/*     */     }
/*     */     
/* 253 */     fluidOutputs.clear();
/*     */     
/* 255 */     this.progress = 0;
/*     */   }
/*     */   
/*     */   protected void updateRecipe(DynamicRecipe recipe) {
/* 259 */     this.operationLength = recipe.getOperationDuration();
/* 260 */     this.recipe = recipe;
/*     */   }
/*     */   
/*     */   protected void reset() {
/* 264 */     this.progress = 0;
/* 265 */     this.operationLength = 0;
/* 266 */     this.recipe = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/* 272 */     super.updateEntityClient();
/*     */     
/* 274 */     if (getActive()) {
/* 275 */       World world = func_145831_w();
/* 276 */       ParticleUtil.showFlames(world, this.field_174879_c, getFacing());
/*     */       
/* 278 */       if (world.field_73012_v.nextDouble() < 0.1D) {
/* 279 */         world.func_184134_a(this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o() + 0.5D, this.field_174879_c.func_177952_p() + 0.5D, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasValidStructure() {
/* 286 */     int range = 2;
/* 287 */     ChunkCache cache = new ChunkCache(func_145831_w(), this.field_174879_c.func_177982_a(-2, -2, -2), this.field_174879_c.func_177982_a(2, 2, 2), 0);
/* 288 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*     */     
/*     */     int x;
/* 291 */     for (x = -1; x <= 1; x++) {
/* 292 */       for (int z = -1; z <= 1; z++) {
/* 293 */         cPos.func_181079_c(this.field_174879_c.func_177958_n() + x - getFacing().func_82601_c(), this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() + z - getFacing().func_82599_e());
/*     */         
/* 295 */         if (x == 0 && z == 0) {
/* 296 */           TileEntity tileEntity = cache.func_175625_s((BlockPos)cPos);
/* 297 */           if (tileEntity == null) {
/* 298 */             return false;
/*     */           }
/*     */           
/* 301 */           if (!(tileEntity instanceof TileEntityCokeKilnGrate)) {
/* 302 */             return false;
/*     */           }
/*     */         } else {
/* 305 */           IBlockState state = cache.func_180495_p((BlockPos)cPos);
/* 306 */           if (state.func_177230_c() != BlockName.refractory_bricks.getInstance()) {
/* 307 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 314 */     for (x = -1; x <= 1; x++) {
/* 315 */       for (int z = -1; z <= 1; z++) {
/* 316 */         cPos.func_181079_c(this.field_174879_c.func_177958_n() + x - getFacing().func_82601_c(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p() + z - getFacing().func_82599_e());
/*     */         
/* 318 */         if (x == 0 && z == 0) {
/* 319 */           IBlockState state = cache.func_180495_p((BlockPos)cPos);
/* 320 */           if (state.func_177230_c() != Blocks.field_150350_a) {
/* 321 */             return false;
/*     */           }
/* 323 */         } else if (this.field_174879_c.func_177958_n() == cPos.func_177958_n() && this.field_174879_c.func_177952_p() == cPos.func_177952_p()) {
/* 324 */           TileEntity tileEntity = cache.func_175625_s((BlockPos)cPos);
/* 325 */           if (tileEntity == null) {
/* 326 */             return false;
/*     */           }
/*     */           
/* 329 */           if (tileEntity != this) {
/* 330 */             return false;
/*     */           }
/*     */         } else {
/* 333 */           IBlockState state = cache.func_180495_p((BlockPos)cPos);
/* 334 */           if (state.func_177230_c() != BlockName.refractory_bricks.getInstance()) {
/* 335 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 342 */     for (x = -1; x <= 1; x++) {
/* 343 */       for (int z = -1; z <= 1; z++) {
/* 344 */         cPos.func_181079_c(this.field_174879_c.func_177958_n() + x - getFacing().func_82601_c(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p() + z - getFacing().func_82599_e());
/*     */         
/* 346 */         if (x == 0 && z == 0) {
/* 347 */           TileEntity tileEntity = cache.func_175625_s((BlockPos)cPos);
/* 348 */           if (tileEntity == null) {
/* 349 */             return false;
/*     */           }
/*     */           
/* 352 */           if (!(tileEntity instanceof TileEntityCokeKilnHatch)) {
/* 353 */             return false;
/*     */           }
/*     */         } else {
/* 356 */           IBlockState state = cache.func_180495_p((BlockPos)cPos);
/* 357 */           if (state.func_177230_c() != BlockName.refractory_bricks.getInstance()) {
/* 358 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 364 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isFormed() {
/* 368 */     return this.isFormed;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityCokeKiln> getGuiContainer(EntityPlayer player) {
/* 373 */     return (ContainerBase<TileEntityCokeKiln>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 379 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 389 */     if (name.equals("progress")) {
/* 390 */       return this.guiProgress;
/*     */     }
/* 392 */     throw new IllegalArgumentException(getClass().getSimpleName() + " Cannot get value for " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack stack, List<String> info, ITooltipFlag advanced) {
/* 399 */     info.add("");
/* 400 */     info.add("MultiBlock Structure:");
/* 401 */     info.add("");
/* 402 */     info.add(" Bottom Layer - 3x3 of Refractory Blocks with a Coke Kiln Grate in the centre");
/* 403 */     info.add("");
/* 404 */     info.add(" Middle Layer - 3x3 of Refractory Blocks with a hollow centre and this block in the middle of one of the sides");
/* 405 */     info.add("");
/* 406 */     info.add(" Top Layer - 3x3 of Refractory Blocks with a Coke Kiln Hatch in the centre");
/* 407 */     info.add("");
/*     */   }
/*     */ 
/*     */   
/* 411 */   protected int tickRate = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isFormed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 422 */   protected int progress = 0;
/* 423 */   protected int operationLength = 0;
/* 424 */   protected DynamicRecipe recipe = null;
/*     */   public static DynamicRecipeManager recipeManager;
/*     */   @GuiSynced
/*     */   protected float guiProgress;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\steam\TileEntityCokeKiln.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */