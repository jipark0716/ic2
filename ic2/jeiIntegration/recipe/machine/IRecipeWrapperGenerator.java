/*     */ package ic2.jeiIntegration.recipe.machine;
/*     */ 
/*     */ import ic2.api.item.IBlockCuttingBlade;
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*     */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*     */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.core.item.type.BlockCuttingBladeType;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import mezz.jei.api.recipe.IRecipeWrapper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IRecipeWrapperGenerator<T>
/*     */ {
/*  45 */   public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> basicMachine = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>()
/*     */     {
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
/*  48 */         List<IRecipeWrapper> recipes = new ArrayList<>();
/*  49 */         for (MachineRecipe<IRecipeInput, Collection<ItemStack>> container : (Iterable<MachineRecipe<IRecipeInput, Collection<ItemStack>>>)((IBasicMachineRecipeManager)category.recipeManager).getRecipes()) {
/*  50 */           recipes.add(new IORecipeWrapper(container, category));
/*     */         }
/*  52 */         return recipes;
/*     */       }
/*     */     };
/*     */   
/*  56 */   public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> recycler = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>()
/*     */     {
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
/*  59 */         IRecipeInput input = new IRecipeInput()
/*     */           {
/*     */             public boolean matches(ItemStack subject) {
/*  62 */               return StackUtil.checkItemEquality(subject, BlockName.te.getItemStack((Enum)TeBlock.recycler));
/*     */             }
/*     */ 
/*     */             
/*     */             public List<ItemStack> getInputs() {
/*  67 */               return Collections.singletonList(BlockName.te.getItemStack((Enum)TeBlock.recycler));
/*     */             }
/*     */ 
/*     */             
/*     */             public int getAmount() {
/*  72 */               return 1;
/*     */             }
/*     */           };
/*  75 */         return (List)Collections.singletonList(new IORecipeWrapper(new MachineRecipe(input, 
/*     */                 
/*  77 */                 Collections.singletonList(ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap))), category));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  82 */   public static final IRecipeWrapperGenerator<IBasicMachineRecipeManager> blockCutter = new IRecipeWrapperGenerator<IBasicMachineRecipeManager>()
/*     */     {
/*  84 */       private final List<ItemStack> candidates = Arrays.asList(new ItemStack[] { ItemName.block_cutting_blade
/*  85 */             .getItemStack((Enum)BlockCuttingBladeType.iron), ItemName.block_cutting_blade
/*  86 */             .getItemStack((Enum)BlockCuttingBladeType.steel), ItemName.block_cutting_blade
/*  87 */             .getItemStack((Enum)BlockCuttingBladeType.diamond) });
/*     */ 
/*     */       
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IBasicMachineRecipeManager> category) {
/*  91 */         List<IRecipeWrapper> list = new ArrayList<>();
/*  92 */         for (MachineRecipe<IRecipeInput, Collection<ItemStack>> container : (Iterable<MachineRecipe<IRecipeInput, Collection<ItemStack>>>)((IBasicMachineRecipeManager)category.recipeManager).getRecipes()) {
/*  93 */           list.add(new AdvancedIORecipeWrapper(container, getInput(getHardness(container.getMetaData())), category));
/*     */         }
/*  95 */         return list;
/*     */       }
/*     */       
/*     */       private int getHardness(NBTTagCompound metadata) {
/*  99 */         if (metadata == null) return Integer.MAX_VALUE; 
/* 100 */         return metadata.func_74762_e("hardness");
/*     */       }
/*     */       
/*     */       private IRecipeInput getInput(final int hardness) {
/* 104 */         return new IRecipeInput()
/*     */           {
/*     */             public boolean matches(ItemStack subject)
/*     */             {
/* 108 */               return (subject != null && subject.func_77973_b() instanceof IBlockCuttingBlade && ((IBlockCuttingBlade)subject.func_77973_b()).getHardness(subject) > hardness);
/*     */             }
/*     */ 
/*     */             
/*     */             public List<ItemStack> getInputs() {
/* 113 */               List<ItemStack> list = new ArrayList<>(IRecipeWrapperGenerator.null.this.candidates.size());
/* 114 */               for (ItemStack stack : IRecipeWrapperGenerator.null.this.candidates) {
/* 115 */                 if (((IBlockCuttingBlade)stack.func_77973_b()).getHardness(stack) >= hardness) {
/* 116 */                   list.add(stack);
/*     */                 }
/*     */               } 
/* 119 */               return list;
/*     */             }
/*     */ 
/*     */             
/*     */             public int getAmount() {
/* 124 */               return 1;
/*     */             }
/*     */           };
/*     */       }
/*     */     };
/*     */   
/* 130 */   public static final IRecipeWrapperGenerator<IElectrolyzerRecipeManager> electrolyzer = new IRecipeWrapperGenerator<IElectrolyzerRecipeManager>()
/*     */     {
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<IElectrolyzerRecipeManager> category) {
/* 133 */         List<IRecipeWrapper> recipes = new ArrayList<>();
/*     */         
/* 135 */         for (Map.Entry<String, IElectrolyzerRecipeManager.ElectrolyzerRecipe> recipe : (Iterable<Map.Entry<String, IElectrolyzerRecipeManager.ElectrolyzerRecipe>>)((IElectrolyzerRecipeManager)category.recipeManager).getRecipeMap().entrySet()) {
/* 136 */           Fluid input = FluidRegistry.getFluid(recipe.getKey());
/* 137 */           if (input != null) {
/* 138 */             recipes.add(new ElectrolyzerWrapper(new FluidStack(input, ((IElectrolyzerRecipeManager.ElectrolyzerRecipe)recipe.getValue()).inputAmount), ((IElectrolyzerRecipeManager.ElectrolyzerRecipe)recipe.getValue()).outputs, category));
/*     */           }
/*     */         } 
/*     */         
/* 142 */         return recipes;
/*     */       }
/*     */     };
/*     */   
/* 146 */   public static final IRecipeWrapperGenerator<ICannerEnrichRecipeManager> cannerEnrichment = new IRecipeWrapperGenerator<ICannerEnrichRecipeManager>()
/*     */     {
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<ICannerEnrichRecipeManager> category) {
/* 149 */         List<IRecipeWrapper> recipes = new ArrayList<>();
/*     */         
/* 151 */         for (MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack> recipe : (Iterable<MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack>>)((ICannerEnrichRecipeManager)category.recipeManager).getRecipes()) {
/* 152 */           recipes.add(new CannerEnrichmentWrapper((ICannerEnrichRecipeManager.Input)recipe.getInput(), (FluidStack)recipe.getOutput(), category));
/*     */         }
/*     */         
/* 155 */         return recipes;
/*     */       }
/*     */     };
/*     */   
/* 159 */   public static final IRecipeWrapperGenerator<ICannerBottleRecipeManager> cannerBottling = new IRecipeWrapperGenerator<ICannerBottleRecipeManager>()
/*     */     {
/*     */       public List<IRecipeWrapper> getRecipeList(IORecipeCategory<ICannerBottleRecipeManager> category) {
/* 162 */         List<IRecipeWrapper> recipes = new ArrayList<>();
/*     */         
/* 164 */         for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : (Iterable<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>>)((ICannerBottleRecipeManager)category.recipeManager).getRecipes()) {
/* 165 */           recipes.add(new CannerCanningWrapper((ICannerBottleRecipeManager.Input)recipe.getInput(), (ItemStack)recipe.getOutput(), category));
/*     */         }
/*     */         
/* 168 */         return recipes;
/*     */       }
/*     */     };
/*     */   
/*     */   List<IRecipeWrapper> getRecipeList(IORecipeCategory<T> paramIORecipeCategory);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\IRecipeWrapperGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */