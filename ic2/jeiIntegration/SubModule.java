/*     */ package ic2.jeiIntegration;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Predicate;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.machine.gui.GuiIndustrialWorkbench;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.gui.RecipeButton;
/*     */ import ic2.core.item.ItemCropSeed;
/*     */ import ic2.core.item.block.ItemCable;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.jeiIntegration.recipe.crafting.AdvRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.crafting.AdvShapelessRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.crafting.GradualRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.crafting.JetpackRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.crafting.JetpackRecipeWrapper;
/*     */ import ic2.jeiIntegration.recipe.machine.CannerCanningHandler;
/*     */ import ic2.jeiIntegration.recipe.machine.CannerCategory;
/*     */ import ic2.jeiIntegration.recipe.machine.CannerEnrichmentHandler;
/*     */ import ic2.jeiIntegration.recipe.machine.DynamicCategory;
/*     */ import ic2.jeiIntegration.recipe.machine.ElectrolyzerCategory;
/*     */ import ic2.jeiIntegration.recipe.machine.ElectrolyzerRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
/*     */ import ic2.jeiIntegration.recipe.machine.IORecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.machine.IRecipeWrapperGenerator;
/*     */ import ic2.jeiIntegration.recipe.machine.MetalFormerCategory;
/*     */ import ic2.jeiIntegration.recipe.machine.RecyclerCategory;
/*     */ import ic2.jeiIntegration.recipe.misc.ScrapboxRecipeCategory;
/*     */ import ic2.jeiIntegration.recipe.misc.ScrapboxRecipeHandler;
/*     */ import ic2.jeiIntegration.recipe.misc.ScrapboxRecipeWrapper;
/*     */ import ic2.jeiIntegration.transferhandlers.TransferHandlerBatchCrafter;
/*     */ import ic2.jeiIntegration.transferhandlers.TransferHandlerIndustrialWorkbench;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import mezz.jei.api.IGuiHelper;
/*     */ import mezz.jei.api.IJeiRuntime;
/*     */ import mezz.jei.api.IModPlugin;
/*     */ import mezz.jei.api.IModRegistry;
/*     */ import mezz.jei.api.IRecipesGui;
/*     */ import mezz.jei.api.ISubtypeRegistry;
/*     */ import mezz.jei.api.JEIPlugin;
/*     */ import mezz.jei.api.ingredients.IIngredientRegistry;
/*     */ import mezz.jei.api.ingredients.IModIngredientRegistration;
/*     */ import mezz.jei.api.recipe.IRecipeCategory;
/*     */ import mezz.jei.api.recipe.IRecipeHandler;
/*     */ import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
/*     */ import mezz.jei.startup.StackHelper;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JEIPlugin
/*     */ public class SubModule
/*     */   implements IModPlugin
/*     */ {
/*     */   private IIngredientRegistry ingredientRegistry;
/*     */   
/*     */   public void register(IModRegistry registry) {
/*  72 */     IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
/*     */ 
/*     */     
/*  75 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new AdvRecipeHandler() });
/*  76 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new AdvShapelessRecipeHandler() });
/*  77 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new GradualRecipeHandler() });
/*     */     
/*  79 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new JetpackRecipeHandler() });
/*  80 */     registry.addRecipes(JetpackRecipeWrapper.generateJetpackRecipes());
/*     */ 
/*     */     
/*  83 */     registry.addRecipeCategories(new IRecipeCategory[] { (IRecipeCategory)new ScrapboxRecipeCategory(guiHelper) });
/*  84 */     registry.addRecipeCategoryCraftingItem(ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box), new String[] { "ic2.scrapbox" });
/*  85 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new ScrapboxRecipeHandler() });
/*  86 */     registry.addRecipes(ScrapboxRecipeWrapper.createRecipes());
/*     */ 
/*     */     
/*  89 */     registry.addRecipeHandlers(new IRecipeHandler[] { (IRecipeHandler)new IORecipeHandler(), (IRecipeHandler)new ElectrolyzerRecipeHandler(), (IRecipeHandler)new CannerEnrichmentHandler(), (IRecipeHandler)new CannerCanningHandler() });
/*  90 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.macerator, Recipes.macerator, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  91 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.extractor, Recipes.extractor, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  92 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.compressor, Recipes.compressor, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  93 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.centrifuge, Recipes.centrifuge, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  94 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.blast_furnace, Recipes.blastfurnace, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  95 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.ore_washing_plant, Recipes.oreWashing, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  96 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.block_cutter, Recipes.blockcutter, guiHelper), IRecipeWrapperGenerator.blockCutter);
/*  97 */     addMachineRecipes(registry, (IORecipeCategory<?>)new MetalFormerCategory(Recipes.metalformerExtruding, 0, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  98 */     addMachineRecipes(registry, (IORecipeCategory<?>)new MetalFormerCategory(Recipes.metalformerRolling, 1, guiHelper), IRecipeWrapperGenerator.basicMachine);
/*  99 */     addMachineRecipes(registry, (IORecipeCategory<?>)new MetalFormerCategory(Recipes.metalformerCutting, 2, guiHelper), IRecipeWrapperGenerator.basicMachine);
/* 100 */     addMachineRecipes(registry, (IORecipeCategory<?>)new ElectrolyzerCategory(guiHelper), IRecipeWrapperGenerator.electrolyzer);
/* 101 */     addMachineRecipes(registry, (IORecipeCategory<?>)CannerCategory.enriching(guiHelper), IRecipeWrapperGenerator.cannerEnrichment);
/* 102 */     addMachineRecipes(registry, (IORecipeCategory<?>)CannerCategory.bottling(guiHelper), IRecipeWrapperGenerator.cannerBottling);
/* 103 */     addMachineRecipes(registry, (IORecipeCategory<?>)new DynamicCategory((ITeBlock)TeBlock.solid_canner, Recipes.cannerBottle, guiHelper), IRecipeWrapperGenerator.cannerBottling);
/*     */     
/* 105 */     addMachineRecipes(registry, (IORecipeCategory<?>)new RecyclerCategory(guiHelper), IRecipeWrapperGenerator.recycler);
/*     */ 
/*     */     
/* 108 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.iron_furnace), new String[] { "minecraft.smelting" });
/* 109 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.electric_furnace), new String[] { "minecraft.smelting" });
/* 110 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.induction_furnace), new String[] { "minecraft.smelting" });
/* 111 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.iron_furnace), new String[] { "minecraft.fuel" });
/* 112 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.generator), new String[] { "minecraft.fuel" });
/* 113 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.solid_heat_generator), new String[] { "minecraft.fuel" });
/*     */ 
/*     */     
/* 116 */     registry.getRecipeTransferRegistry().addRecipeTransferHandler((IRecipeTransferHandler)new TransferHandlerIndustrialWorkbench((StackHelper)registry.getJeiHelpers().getStackHelper(), registry.getJeiHelpers().recipeTransferHandlerHelper()), "minecraft.crafting");
/* 117 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.industrial_workbench), new String[] { "minecraft.crafting" });
/* 118 */     registry.getRecipeTransferRegistry().addRecipeTransferHandler((IRecipeTransferHandler)new TransferHandlerBatchCrafter(), "minecraft.crafting");
/* 119 */     registry.addRecipeCategoryCraftingItem(BlockName.te.getItemStack((Enum)TeBlock.batch_crafter), new String[] { "minecraft.crafting" });
/*     */     
/* 121 */     if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/* 122 */       (new Runnable()
/*     */         {
/*     */           public void run() {
/* 125 */             GuiIndustrialWorkbench.jeiScreenRecipesGuiCheck = new Predicate<GuiScreen>()
/*     */               {
/*     */                 public boolean apply(GuiScreen input) {
/* 128 */                   return input instanceof IRecipesGui;
/*     */                 }
/*     */               };
/*     */           }
/* 132 */         }).run();
/*     */     }
/*     */   }
/*     */   
/*     */   private <T> void addMachineRecipes(IModRegistry registry, IORecipeCategory<T> category, IRecipeWrapperGenerator<T> wrappergen) {
/* 137 */     registry.addRecipeCategories(new IRecipeCategory[] { (IRecipeCategory)category });
/* 138 */     registry.addRecipes(wrappergen.getRecipeList(category));
/* 139 */     registry.addRecipeCategoryCraftingItem(category.getBlockStack(), new String[] { category.getUid() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
/* 144 */     if (this.ingredientRegistry != null) {
/* 145 */       List<ItemStack> items = new ArrayList<>();
/*     */       
/* 147 */       for (CropCard crop : Crops.instance.getCrops()) {
/* 148 */         items.add(ItemCropSeed.generateItemStackFromValues(crop, 1, 1, 1, 4));
/*     */       }
/*     */       
/* 151 */       this.ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, items);
/*     */     } 
/*     */     
/* 154 */     if (FMLCommonHandler.instance().getSide().isClient()) {
/* 155 */       final IRecipesGui recipesGUI = jeiRuntime.getRecipesGui();
/*     */       
/* 157 */       (new Runnable()
/*     */         {
/*     */           public void run() {
/* 160 */             RecipeButton.jeiRecipeListOpener = new Function<String[], IClickHandler>()
/*     */               {
/*     */                 public IClickHandler apply(final String[] input) {
/* 163 */                   assert input != null;
/*     */                   
/* 165 */                   return new IClickHandler()
/*     */                     {
/*     */                       public void onClick(MouseButton button) {
/* 168 */                         if (input.length > 0) {
/* 169 */                           recipesGUI.showCategories(Arrays.asList(input));
/*     */                         }
/*     */                       }
/*     */                     };
/*     */                 }
/*     */               };
/*     */           }
/* 176 */         }).run();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
/* 182 */     subtypeRegistry.registerSubtypeInterpreter(ItemName.cable.getInstance(), new ISubtypeRegistry.ISubtypeInterpreter()
/*     */         {
/*     */           public String apply(ItemStack stack) {
/* 185 */             return ((ItemCable)ItemName.cable.getInstance()).getVariant(stack);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerIngredients(IModIngredientRegistration registry) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\SubModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */