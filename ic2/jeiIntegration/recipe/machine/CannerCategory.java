/*     */ package ic2.jeiIntegration.recipe.machine;
/*     */ 
/*     */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*     */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.machine.gui.GuiCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.Tuple;
/*     */ import ic2.jeiIntegration.SlotPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import mezz.jei.api.IGuiHelper;
/*     */ import mezz.jei.api.gui.IDrawable;
/*     */ import mezz.jei.api.gui.IDrawableAnimated;
/*     */ import mezz.jei.api.gui.IGuiFluidStackGroup;
/*     */ import mezz.jei.api.gui.IRecipeLayout;
/*     */ import mezz.jei.api.ingredients.IIngredients;
/*     */ import mezz.jei.api.recipe.IRecipeWrapper;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ public class CannerCategory<T>
/*     */   extends IORecipeCategory<T>
/*     */   implements IDrawable {
/*     */   protected static final byte offsetX = -40;
/*     */   protected static final byte offsetY = -16;
/*     */   protected static final byte slotX = -41;
/*     */   protected static final byte slotY = -17;
/*     */   
/*     */   public enum CanningActivity {
/*  39 */     ENRICHING((String)Recipes.cannerEnrich, TileEntityCanner.Mode.EnrichLiquid, 60, (TileEntityCanner.Mode)new Slot[] { Slot.ADDITIVE }, Tank.values()),
/*  40 */     CANNING((String)Recipes.cannerBottle, TileEntityCanner.Mode.BottleSolid, 18, (TileEntityCanner.Mode)Slot.values(), new Tank[0])
/*     */     {
/*     */       void createBackground(List<Tuple.T2<IDrawable, SlotPosition>> elements, IGuiHelper guiHelper) {
/*  43 */         super.createBackground(elements, guiHelper);
/*     */         
/*  45 */         elements.add(new Tuple.T2(guiHelper.createDrawable(GuiCanner.texture, 3, 4, 9, 18), new SlotPosition(19, 37)));
/*     */         
/*  47 */         elements.add(new Tuple.T2(guiHelper.createDrawable(GuiCanner.texture, 3, 4, 18, 23), new SlotPosition(59, 37)));
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     final IMachineRecipeManager<?, ?, ?> manager;
/*     */     final TileEntityCanner.Mode mode;
/*     */     final int overlayV;
/*     */     final Slot[] slots;
/*     */     final Tank[] tanks;
/*     */     
/*     */     CanningActivity(IMachineRecipeManager<?, ?, ?> manager, TileEntityCanner.Mode mode, int overlayV, Slot[] slots, Tank... tanks) {
/*  59 */       this.manager = manager;
/*  60 */       this.mode = mode;
/*  61 */       this.overlayV = overlayV;
/*  62 */       this.slots = slots;
/*  63 */       this.tanks = tanks;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void createBackground(List<Tuple.T2<IDrawable, SlotPosition>> elements, IGuiHelper guiHelper) {
/*  73 */       elements.add(new Tuple.T2(guiHelper.createDrawable(GuiCanner.texture, 40, 16, 96, 81), new SlotPosition(0, 0)));
/*     */     }
/*     */     
/*     */     enum Slot
/*     */     {
/*  78 */       ADDITIVE, CAN, OUTPUT;
/*     */     }
/*     */     
/*     */     enum Tank {
/*  82 */       INPUT(39, 42), OUTPUT(117, 42);
/*     */       final int y;
/*     */       final int x;
/*     */       
/*     */       Tank(int x, int y) {
/*  87 */         this.x = -40 + x;
/*  88 */         this.y = -16 + y; } } } enum Slot { ADDITIVE, CAN, OUTPUT; } enum Tank { Tank(int x, int y) { this.x = -40 + x; this.y = -16 + y; }
/*     */ 
/*     */     
/*     */     INPUT(39, 42),
/*     */     OUTPUT(117, 42);
/*     */     final int y;
/*     */     final int x; }
/*     */   
/*  96 */   protected final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<>();
/*  97 */   protected final List<Tuple.T2<IDrawable, SlotPosition>> progress = new ArrayList<>(); private final List<SlotPosition> inputs; private final List<SlotPosition> outputs;
/*     */   private final CanningActivity.Tank[] tanks;
/*     */   private final Set<CanningActivity.Tank> notTanks;
/*     */   private final String name;
/*     */   private final IDrawable emptyTank;
/*     */   private final IDrawable tankBackground;
/*     */   private final IDrawable tankOverlay;
/*     */   
/*     */   public static CannerCategory<ICannerEnrichRecipeManager> enriching(IGuiHelper guiHelper) {
/* 106 */     return new CannerCategory<>(CanningActivity.ENRICHING, guiHelper);
/*     */   }
/*     */   
/*     */   public static CannerCategory<ICannerBottleRecipeManager> bottling(IGuiHelper guiHelper) {
/* 110 */     return new CannerCategory<>(CanningActivity.CANNING, guiHelper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected CannerCategory(CanningActivity activity, IGuiHelper guiHelper) {
/* 115 */     super((ITeBlock)TeBlock.canner, (T)activity.manager);
/*     */     
/* 117 */     activity.createBackground(this.elements, guiHelper);
/* 118 */     this.elements.add(new Tuple.T2(guiHelper.createDrawable(GuiCanner.texture, 176, activity.overlayV, 50, 14), new SlotPosition(23, 65)));
/*     */ 
/*     */     
/* 121 */     this.emptyTank = (IDrawable)guiHelper.createDrawable(GuiElement.commonTexture, 70, 100, 20, 55);
/* 122 */     this.tankBackground = (IDrawable)guiHelper.createDrawable(GuiElement.commonTexture, 6, 100, 20, 55);
/* 123 */     int borderX = -4, borderY = -4;
/* 124 */     this.tankOverlay = (IDrawable)guiHelper.createDrawable(GuiElement.commonTexture, 38, 100, 20, 55, -4, -4, -4, -4);
/* 125 */     this.name = activity.mode.name();
/*     */     
/* 127 */     this.progress.add(new Tuple.T2(guiHelper.createAnimatedDrawable(guiHelper.createDrawable(GuiCanner.texture, 233, 0, 23, 14), 66, IDrawableAnimated.StartDirection.LEFT, false), new SlotPosition(34, 6)));
/*     */ 
/*     */     
/* 130 */     List<SlotPosition> inputs = new ArrayList<>(2);
/* 131 */     List<SlotPosition> outputs = Collections.emptyList();
/* 132 */     for (CanningActivity.Slot slot : activity.slots) {
/* 133 */       switch (slot) {
/*     */         case INPUT:
/* 135 */           inputs.add(new SlotPosition(39, 27));
/*     */           break;
/*     */         
/*     */         case OUTPUT:
/* 139 */           inputs.add(new SlotPosition(0, 0));
/*     */           break;
/*     */         
/*     */         case null:
/* 143 */           outputs = Collections.singletonList(new SlotPosition(78, 0));
/*     */           break;
/*     */       } 
/*     */     } 
/* 147 */     this.inputs = inputs;
/* 148 */     this.outputs = outputs;
/*     */     
/* 150 */     this.tanks = activity.tanks;
/* 151 */     this.notTanks = (this.tanks.length == 0) ? EnumSet.<CanningActivity.Tank>allOf(CanningActivity.Tank.class) : EnumSet.<CanningActivity.Tank>complementOf(EnumSet.copyOf(Arrays.asList(this.tanks)));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUid() {
/* 156 */     return super.getUid() + '_' + this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public IDrawable getBackground() {
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getInputSlotPos() {
/* 167 */     return this.inputs;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getOutputSlotPos() {
/* 172 */     return this.outputs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
/* 177 */     super.setRecipe(recipeLayout, recipeWrapper, ingredients);
/*     */     
/* 179 */     IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
/* 180 */     int tankWidth = 12, tankHeight = 47;
/* 181 */     int fluidX = 4, fluidY = 4;
/* 182 */     int id = 0;
/*     */     
/* 184 */     for (CanningActivity.Tank tank : this.tanks) {
/* 185 */       List<List<FluidStack>> inputs, outputs; switch (tank) {
/*     */         case INPUT:
/* 187 */           fluidStacks.init(id, true, tank.x + 4, tank.y + 4, 12, 47, 8000, false, this.tankOverlay);
/* 188 */           inputs = ingredients.getInputs(FluidStack.class);
/* 189 */           if (!inputs.isEmpty()) {
/* 190 */             fluidStacks.set(id, inputs.get(0));
/*     */           }
/*     */           break;
/*     */         
/*     */         case OUTPUT:
/* 195 */           fluidStacks.init(id, false, tank.x + 4, tank.y + 4, 12, 47, 8000, false, this.tankOverlay);
/* 196 */           outputs = ingredients.getOutputs(FluidStack.class);
/* 197 */           if (!outputs.isEmpty()) {
/* 198 */             fluidStacks.set(id, outputs.get(0));
/*     */           }
/*     */           break;
/*     */       } 
/* 202 */       id++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft) {
/* 209 */     for (Tuple.T2<IDrawable, SlotPosition> element : this.elements) {
/* 210 */       ((IDrawable)element.a).draw(minecraft, ((SlotPosition)element.b).getX(), ((SlotPosition)element.b).getY());
/*     */     }
/*     */     
/* 213 */     for (CanningActivity.Tank tank : this.tanks) {
/* 214 */       this.tankBackground.draw(minecraft, tank.x, tank.y);
/*     */     }
/* 216 */     for (CanningActivity.Tank tank : this.notTanks) {
/* 217 */       this.emptyTank.draw(minecraft, tank.x, tank.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawExtras(Minecraft minecraft) {
/* 223 */     for (Tuple.T2<IDrawable, SlotPosition> bar : this.progress) {
/* 224 */       ((IDrawable)bar.a).draw(minecraft, ((SlotPosition)bar.b).getX(), ((SlotPosition)bar.b).getY());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft, int xOffset, int yOffset) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 235 */     return 96;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 240 */     return 81;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\CannerCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */