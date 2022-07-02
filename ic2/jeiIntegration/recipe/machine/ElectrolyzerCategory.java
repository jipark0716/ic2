/*     */ package ic2.jeiIntegration.recipe.machine;
/*     */ 
/*     */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.machine.gui.GuiElectrolyzer;
/*     */ import ic2.core.gui.ElectrolyzerTankController;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.Tuple;
/*     */ import ic2.jeiIntegration.SlotPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import mezz.jei.api.IGuiHelper;
/*     */ import mezz.jei.api.gui.IDrawable;
/*     */ import mezz.jei.api.gui.IDrawableAnimated;
/*     */ import mezz.jei.api.gui.IDrawableStatic;
/*     */ import mezz.jei.api.gui.IGuiFluidStackGroup;
/*     */ import mezz.jei.api.gui.IRecipeLayout;
/*     */ import mezz.jei.api.ingredients.IIngredients;
/*     */ import mezz.jei.api.recipe.IRecipeWrapper;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ElectrolyzerCategory
/*     */   extends IORecipeCategory<IElectrolyzerRecipeManager>
/*     */   implements IDrawable
/*     */ {
/*     */   private static final int xOffset = 0;
/*     */   private static final int yOffset = 0;
/*     */   private final List<SlotPosition> inputSlots;
/*     */   private final List<SlotPosition> outputSlots;
/*  40 */   private final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<>();
/*  41 */   private final List<Tuple.T2<IDrawable, SlotPosition>> progress = new ArrayList<>();
/*     */   
/*     */   public ElectrolyzerCategory(IGuiHelper guiHelper) {
/*  44 */     super((ITeBlock)TeBlock.electrolyzer, Recipes.electrolyzer);
/*     */     
/*  46 */     SlotPosition pos = new SlotPosition(78, 0);
/*  47 */     this.elements.add(new Tuple.T2(getFluidSlot(guiHelper), pos));
/*  48 */     this.inputSlots = Collections.singletonList(pos);
/*     */     
/*  50 */     List<SlotPosition> tempOutput = new ArrayList<>(5);
/*  51 */     for (int i = 0; i < 5; i++) {
/*  52 */       this.elements.add(new Tuple.T2(getFluidSlot(guiHelper), pos = new SlotPosition(36 + 21 * i + 0, 45)));
/*  53 */       tempOutput.add(pos);
/*     */     } 
/*  55 */     this.outputSlots = Collections.unmodifiableList(tempOutput);
/*     */     
/*  57 */     Gauge.GaugeProperties energyStyle = (EnergyGauge.EnergyGaugeStyle.get(EnergyGauge.EnergyGaugeStyle.Bolt.name)).properties;
/*  58 */     pos = new SlotPosition(12 + energyStyle.bgXOffset + 0, 44 + energyStyle.bgYOffset + 0 - 16);
/*  59 */     this.elements.add(new Tuple.T2(guiHelper.createDrawable(energyStyle.texture, energyStyle.uBgInactive, energyStyle.vBgInactive, energyStyle.bgWidth, energyStyle.bgHeight), pos));
/*     */     
/*  61 */     this.progress.add(new Tuple.T2(guiHelper.createAnimatedDrawable(drawableProperties(guiHelper, energyStyle), 300, getDirection(energyStyle.reverse, energyStyle.vertical), true), new SlotPosition(12, 28)));
/*     */ 
/*     */     
/*  64 */     this.progress.add(new Tuple.T2(guiHelper.createAnimatedDrawable(drawableProperties(guiHelper, GuiElectrolyzer.ElectrolyzerGauges.THREE_TANK.properties), 100, IDrawableAnimated.StartDirection.TOP, false), new SlotPosition(60, 20)));
/*     */   }
/*     */ 
/*     */   
/*     */   private IDrawableStatic drawableProperties(IGuiHelper guiHelper, Gauge.GaugeProperties properties) {
/*  69 */     return guiHelper.createDrawable(properties.texture, properties.uInner, properties.vInner, properties.innerWidth, properties.innerHeight);
/*     */   }
/*     */   
/*     */   private IDrawableAnimated.StartDirection getDirection(boolean reverse, boolean vertical) {
/*  73 */     return reverse ? (vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.RIGHT) : (vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.LEFT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IDrawable getFluidSlot(IGuiHelper guiHelper) {
/*  79 */     return (IDrawable)guiHelper.createDrawable(GuiElement.commonTexture, 8, 160, 18, 18);
/*     */   }
/*     */ 
/*     */   
/*     */   public IDrawable getBackground() {
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawExtras(Minecraft minecraft) {
/*  89 */     for (Tuple.T2<IDrawable, SlotPosition> bar : this.progress) {
/*  90 */       ((IDrawable)bar.a).draw(minecraft, ((SlotPosition)bar.b).getX(), ((SlotPosition)bar.b).getY());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getInputSlotPos() {
/*  96 */     return this.inputSlots;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getOutputSlotPos() {
/* 101 */     return this.outputSlots;
/*     */   }
/*     */   
/*     */   private static List<FluidStack> unpack(List<List<FluidStack>> in) {
/* 105 */     List<FluidStack> out = new ArrayList<>();
/*     */     
/* 107 */     for (List<FluidStack> stack : in) {
/* 108 */       if (!stack.isEmpty()) {
/* 109 */         out.add(stack.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
/* 118 */     IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
/*     */     
/* 120 */     List<SlotPosition> inputSlots = getInputSlotPos();
/* 121 */     List<List<FluidStack>> inputStacks = ingredients.getInputs(FluidStack.class);
/* 122 */     int ID = 0;
/*     */     
/* 124 */     SlotPosition pos = inputSlots.get(0);
/* 125 */     FluidStack fluid = ((List<FluidStack>)inputStacks.get(0)).get(0);
/* 126 */     fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 127 */     fluidStacks.set(ID++, fluid);
/*     */     
/* 129 */     List<SlotPosition> outputSlots = getOutputSlotPos();
/* 130 */     List<FluidStack> outputStacks = unpack(ingredients.getOutputs(FluidStack.class));
/* 131 */     int length = outputStacks.size();
/*     */     
/* 133 */     if (ElectrolyzerTankController.ONE_THREE_FIVE.contains(length)) {
/* 134 */       pos = outputSlots.get(3);
/* 135 */       fluid = outputStacks.get(length / 2);
/* 136 */       fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 137 */       fluidStacks.set(ID++, fluid);
/*     */     } 
/*     */     
/* 140 */     if (ElectrolyzerTankController.TWO_TO_FIVE.contains(length)) {
/* 141 */       pos = outputSlots.get(1);
/* 142 */       fluid = outputStacks.get((length < 4) ? 0 : 1);
/* 143 */       fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 144 */       fluidStacks.set(ID++, fluid);
/*     */       
/* 146 */       pos = outputSlots.get(3);
/* 147 */       fluid = outputStacks.get(length - ((length < 4) ? 1 : 2));
/* 148 */       fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 149 */       fluidStacks.set(ID++, fluid);
/*     */     } 
/*     */     
/* 152 */     if (ElectrolyzerTankController.FOUR_FIVE.contains(length)) {
/* 153 */       pos = outputSlots.get(0);
/* 154 */       fluid = outputStacks.get(0);
/* 155 */       fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 156 */       fluidStacks.set(ID++, fluid);
/*     */       
/* 158 */       pos = outputSlots.get(4);
/* 159 */       fluid = outputStacks.get(length - 1);
/* 160 */       fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
/* 161 */       fluidStacks.set(ID++, fluid);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft) {
/* 168 */     for (Tuple.T2<IDrawable, SlotPosition> element : this.elements) {
/* 169 */       ((IDrawable)element.a).draw(minecraft, ((SlotPosition)element.b).getX(), ((SlotPosition)element.b).getY());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft, int xOffset, int yOffset) {}
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 179 */     return 160;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 184 */     return 60;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\ElectrolyzerCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */