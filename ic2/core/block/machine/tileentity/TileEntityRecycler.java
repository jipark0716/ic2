/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IListRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.recipe.BasicListRecipeManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityRecycler
/*     */   extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>
/*     */ {
/*     */   public TileEntityRecycler() {
/*  37 */     super(1, 45, 1);
/*     */     
/*  39 */     this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.recycler);
/*     */   }
/*     */   
/*     */   public static void init() {
/*  43 */     Recipes.recycler = new RecyclerRecipeManager();
/*     */     
/*  45 */     Recipes.recyclerWhitelist = (IListRecipeManager)new BasicListRecipeManager();
/*  46 */     Recipes.recyclerBlacklist = (IListRecipeManager)new BasicListRecipeManager();
/*     */   }
/*     */   
/*     */   public static void initLate() {
/*  50 */     for (IRecipeInput input : ConfigUtil.asRecipeInputList(MainConfig.get(), "balance/recyclerBlacklist")) {
/*  51 */       Recipes.recyclerBlacklist.add(input);
/*     */     }
/*     */     
/*  54 */     for (IRecipeInput input : ConfigUtil.asRecipeInputList(MainConfig.get(), "balance/recyclerWhitelist")) {
/*  55 */       Recipes.recyclerWhitelist.add(input);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int recycleChance() {
/*  65 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/*  70 */     return "Machines/RecyclerOp.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/*  75 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getIsItemBlacklisted(ItemStack aStack) {
/*  84 */     if (Recipes.recyclerWhitelist.isEmpty()) return Recipes.recyclerBlacklist.contains(aStack); 
/*  85 */     return !Recipes.recyclerWhitelist.contains(aStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result, Collection<ItemStack> processResult) {
/*  91 */     this.inputSlot.consume(result);
/*     */     
/*  93 */     if (IC2.random.nextInt(recycleChance()) == 0)
/*     */     {
/*  95 */       this.outputSlot.add(processResult);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RecyclerRecipeManager
/*     */     implements IBasicMachineRecipeManager
/*     */   {
/*     */     public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean replace, ItemStack... outputs) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/* 115 */       if (StackUtil.isEmpty(input)) return null;
/*     */       
/* 117 */       RecipeOutput ret = new RecipeOutput(null, new ArrayList<>(getOutput(input)));
/* 118 */       if (adjustInput) input.func_190918_g(1);
/*     */       
/* 120 */       return ret;
/*     */     }
/*     */     
/*     */     private static Collection<ItemStack> getOutput(ItemStack input) {
/* 124 */       return TileEntityRecycler.getIsItemBlacklisted(input) ? Collections.<ItemStack>emptyList() : Collections.<ItemStack>singletonList(ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap));
/*     */     }
/*     */ 
/*     */     
/*     */     public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack input, boolean acceptTest) {
/* 129 */       if (StackUtil.isEmpty(input)) return null;
/*     */       
/* 131 */       return (new MachineRecipe(Recipes.inputFactory
/* 132 */           .forStack(input, 1), 
/* 133 */           getOutput(input)))
/* 134 */         .getResult(StackUtil.copyWithSize(input, StackUtil.getSize(input) - 1));
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIterable() {
/* 144 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 150 */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityRecycler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */