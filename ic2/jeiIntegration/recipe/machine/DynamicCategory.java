/*     */ package ic2.jeiIntegration.recipe.machine;
/*     */ 
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.SlotGrid;
/*     */ import ic2.core.gui.dynamic.GuiEnvironment;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.util.Tuple;
/*     */ import ic2.jeiIntegration.SlotPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import mezz.jei.api.IGuiHelper;
/*     */ import mezz.jei.api.gui.IDrawable;
/*     */ import mezz.jei.api.gui.IDrawableAnimated;
/*     */ import mezz.jei.api.gui.IDrawableStatic;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicCategory<T>
/*     */   extends IORecipeCategory<T>
/*     */   implements IDrawable
/*     */ {
/*     */   protected static final int xOffset = 0;
/*     */   protected static final int yOffset = -16;
/*  40 */   protected final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<>();
/*     */   
/*  42 */   private final List<SlotPosition> inputSlots = new ArrayList<>();
/*  43 */   private final List<SlotPosition> outputSlots = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public DynamicCategory(ITeBlock block, T recipeManager, IGuiHelper guiHelper) {
/*  47 */     super(block, recipeManager);
/*     */     
/*  49 */     initializeWidgets(guiHelper, (GuiParser.ParentNode)GuiParser.parse(block));
/*     */   }
/*     */   
/*     */   private void initializeWidgets(IGuiHelper guiHelper, GuiParser.ParentNode parentNode) {
/*  53 */     label75: for (GuiParser.Node rawNode : parentNode.getNodes()) {
/*  54 */       GuiParser.EnergyGaugeNode energyGaugeNode; GuiParser.GaugeNode gaugeNode; GuiParser.ImageNode imageNode; GuiParser.SlotNode slotNode; GuiParser.SlotGridNode slotGridNode; GuiParser.EnvironmentNode node; SlotPosition slotPosition1; Gauge.GaugeProperties properties; SlotPosition pos; TileEntityInventory dummyTe; IDrawableStatic energyBackground; SlotPosition slotPosition2; IDrawableStatic iDrawableStatic2; IDrawableStatic iDrawableStatic1; InvSlot slot; IDrawableAnimated energyAnimated; IDrawableStatic guageBackground; int extraX; int size; IDrawableAnimated iDrawableAnimated1; int extraY; String slotName; switch (rawNode.getType()) {
/*     */         case energygauge:
/*  56 */           energyGaugeNode = (GuiParser.EnergyGaugeNode)rawNode;
/*     */           
/*  58 */           slotPosition1 = new SlotPosition(energyGaugeNode.x + energyGaugeNode.style.properties.bgXOffset + 0, energyGaugeNode.y + energyGaugeNode.style.properties.bgYOffset + -16);
/*  59 */           energyBackground = guiHelper.createDrawable(energyGaugeNode.style.properties.texture, energyGaugeNode.style.properties.uBgInactive, energyGaugeNode.style.properties.vBgInactive, energyGaugeNode.style.properties.bgWidth, energyGaugeNode.style.properties.bgHeight);
/*  60 */           this.elements.add(new Tuple.T2(energyBackground, slotPosition1));
/*  61 */           energyBackground = guiHelper.createDrawable(energyGaugeNode.style.properties.texture, energyGaugeNode.style.properties.uInner, energyGaugeNode.style.properties.vInner, energyGaugeNode.style.properties.innerWidth, energyGaugeNode.style.properties.innerHeight);
/*  62 */           energyAnimated = guiHelper.createAnimatedDrawable(energyBackground, 300, energyGaugeNode.style.properties.reverse ? (energyGaugeNode.style.properties.vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.RIGHT) : (energyGaugeNode.style.properties.vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.LEFT), true);
/*  63 */           this.elements.add(new Tuple.T2(energyAnimated, new SlotPosition(energyGaugeNode.x + 0, energyGaugeNode.y + -16)));
/*     */ 
/*     */         
/*     */         case gauge:
/*  67 */           gaugeNode = (GuiParser.GaugeNode)rawNode;
/*  68 */           properties = gaugeNode.style.getProperties();
/*     */           
/*  70 */           slotPosition2 = new SlotPosition(gaugeNode.x + properties.bgXOffset + 0, gaugeNode.y + properties.bgYOffset + -16);
/*  71 */           guageBackground = guiHelper.createDrawable(properties.texture, properties.uBgActive, properties.vBgActive, properties.bgWidth, properties.bgHeight);
/*  72 */           this.elements.add(new Tuple.T2(guageBackground, slotPosition2));
/*  73 */           guageBackground = guiHelper.createDrawable(properties.texture, properties.uInner, properties.vInner, properties.innerWidth, properties.innerHeight);
/*     */           
/*  75 */           if (gaugeNode.style == Gauge.GaugeStyle.HeatCentrifuge) {
/*  76 */             IDrawableStatic iDrawableStatic = guageBackground;
/*     */           } else {
/*  78 */             iDrawableAnimated1 = guiHelper.createAnimatedDrawable(guageBackground, getProcessSpeed(gaugeNode.name), properties.reverse ? (properties.vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.RIGHT) : (properties.vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.LEFT), false);
/*     */           } 
/*  80 */           this.elements.add(new Tuple.T2(iDrawableAnimated1, new SlotPosition(gaugeNode.x + 0, gaugeNode.y + -16)));
/*     */ 
/*     */         
/*     */         case image:
/*  84 */           imageNode = (GuiParser.ImageNode)rawNode;
/*     */           
/*  86 */           pos = new SlotPosition(imageNode.x + 0, imageNode.y + -16);
/*  87 */           iDrawableStatic2 = guiHelper.createDrawable(imageNode.src, imageNode.u1, imageNode.v1, imageNode.width, imageNode.height, imageNode.baseWidth, imageNode.baseHeight);
/*  88 */           this.elements.add(new Tuple.T2(iDrawableStatic2, pos));
/*     */ 
/*     */ 
/*     */         
/*     */         case slot:
/*  93 */           slotNode = (GuiParser.SlotNode)rawNode;
/*     */           
/*  95 */           pos = new SlotPosition(slotNode.x + 0, slotNode.y + -16, slotNode.style);
/*  96 */           iDrawableStatic1 = guiHelper.createDrawable(GuiElement.commonTexture, (pos.getStyle()).u, (pos.getStyle()).v, (pos.getStyle()).width, (pos.getStyle()).height);
/*  97 */           this.elements.add(new Tuple.T2(iDrawableStatic1, pos));
/*     */           
/*  99 */           extraY = extraX = 0;
/* 100 */           if (slotNode.style == SlotGrid.SlotStyle.Large) extraX = extraY = 4; 
/* 101 */           slotName = slotNode.name.toLowerCase(Locale.ENGLISH);
/* 102 */           if (slotName.contains("input") || slotName.equals("cutterInputSlot")) {
/* 103 */             this.inputSlots.add(new SlotPosition(pos, extraX, extraY)); continue;
/* 104 */           }  if (slotName.contains("output")) {
/* 105 */             this.outputSlots.add(new SlotPosition(pos, extraX, extraY));
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         case slotgrid:
/* 111 */           slotGridNode = (GuiParser.SlotGridNode)rawNode;
/*     */ 
/*     */           
/* 114 */           dummyTe = (TileEntityInventory)this.block.getDummyTe();
/* 115 */           if (dummyTe == null) throw new NullPointerException("Received null dummy for " + this.block + " in the JeiPlugin."); 
/* 116 */           slot = dummyTe.getInventorySlot(slotGridNode.name);
/* 117 */           if (slot == null) throw new RuntimeException("invalid invslot name " + slotGridNode.name + " for base " + dummyTe);
/*     */           
/* 119 */           size = slot.size();
/*     */           
/* 121 */           if (size > slotGridNode.offset) {
/* 122 */             GuiParser.SlotGridNode.SlotGridDimension dim = slotGridNode.getDimension(size);
/* 123 */             IDrawableStatic iDrawableStatic = guiHelper.createDrawable(GuiElement.commonTexture, slotGridNode.style.u, slotGridNode.style.v, slotGridNode.style.width, slotGridNode.style.height);
/*     */             
/* 125 */             boolean isInput = slotGridNode.name.toLowerCase().contains("input");
/* 126 */             boolean isOutput = slotGridNode.name.toLowerCase().contains("output");
/* 127 */             for (int i = 0; i < dim.cols; i++) {
/* 128 */               for (int j = 0; j < dim.rows; j++) {
/* 129 */                 if (i * dim.rows + j > size)
/* 130 */                   continue label75;  SlotPosition slotPosition = new SlotPosition(slotGridNode.x + 0 + i * slotGridNode.style.width, slotGridNode.y + -16 + j * slotGridNode.style.height, slotGridNode.style);
/* 131 */                 this.elements.add(new Tuple.T2(iDrawableStatic, slotPosition));
/* 132 */                 if (isInput) {
/* 133 */                   this.inputSlots.add(slotPosition);
/* 134 */                 } else if (isOutput) {
/* 135 */                   this.outputSlots.add(slotPosition);
/*     */                 } 
/*     */               } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case environment:
/* 166 */           node = (GuiParser.EnvironmentNode)rawNode;
/* 167 */           if (node.environment == GuiEnvironment.JEI) {
/* 168 */             initializeWidgets(guiHelper, (GuiParser.ParentNode)node);
/*     */           }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDrawable getBackground() {
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawExtras(Minecraft minecraft) {
/* 183 */     for (Tuple.T2<IDrawable, SlotPosition> element : this.elements) {
/* 184 */       ((IDrawable)element.a).draw(minecraft, ((SlotPosition)element.b).getX(), ((SlotPosition)element.b).getY());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getInputSlotPos() {
/* 190 */     return this.inputSlots;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<SlotPosition> getOutputSlotPos() {
/* 195 */     return this.outputSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Minecraft minecraft, int xOffset, int yOffset) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 213 */     return 60;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 221 */     return 160;
/*     */   }
/*     */   
/*     */   protected int getProcessSpeed(String name) {
/* 225 */     if ("progress".equals(name)) {
/*     */       
/* 227 */       TileEntityBlock te = this.block.getDummyTe();
/* 228 */       if (te != null && te instanceof TileEntityStandardMachine) {
/* 229 */         return ((TileEntityStandardMachine)te).defaultOperationLength / 3;
/*     */       }
/*     */     } 
/* 232 */     return 200;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\DynamicCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */