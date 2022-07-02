/*     */ package ic2.core.gui.dynamic;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.gui.Button;
/*     */ import ic2.core.gui.CustomButton;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.gui.FluidSlot;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiDefaultBackground;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.Image;
/*     */ import ic2.core.gui.LinkedGauge;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.gui.RecipeButton;
/*     */ import ic2.core.gui.SlotGrid;
/*     */ import ic2.core.gui.TankGauge;
/*     */ import ic2.core.gui.Text;
/*     */ import ic2.core.gui.VanillaButton;
/*     */ import ic2.core.item.tool.HandHeldInventory;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicGui<T extends ContainerBase<? extends IInventory>>
/*     */   extends GuiDefaultBackground<T>
/*     */ {
/*     */   public static <T extends IInventory> DynamicGui<ContainerBase<T>> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/*  56 */     DynamicContainer<T> container = DynamicContainer.create(base, player, guiNode);
/*     */     
/*  58 */     return new DynamicGui<>(player, container, guiNode);
/*     */   }
/*     */   
/*     */   public static <T extends HandHeldInventory> DynamicGui<ContainerBase<T>> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/*  62 */     DynamicHandHeldContainer<T> container = DynamicHandHeldContainer.create(base, player, guiNode);
/*     */     
/*  64 */     return new DynamicGui<>(player, container, guiNode);
/*     */   }
/*     */   
/*     */   protected DynamicGui(EntityPlayer player, T container, GuiParser.GuiNode guiNode) {
/*  68 */     super((ContainerBase)container, guiNode.width, guiNode.height);
/*     */     
/*  70 */     initializeWidgets(player, guiNode);
/*     */   }
/*     */   
/*     */   private void initializeWidgets(EntityPlayer player, GuiParser.ParentNode parentNode) {
/*  74 */     for (GuiParser.Node rawNode : parentNode.getNodes()) {
/*  75 */       GuiParser.ButtonNode buttonNode; GuiParser.EnergyGaugeNode energyGaugeNode; GuiParser.GaugeNode gaugeNode; GuiParser.ImageNode imageNode; GuiParser.PlayerInventoryNode playerInventoryNode; GuiParser.SlotNode slotNode; GuiParser.SlotGridNode slotGridNode; GuiParser.TextNode textNode; GuiParser.FluidTankNode fluidTankNode; GuiParser.FluidSlotNode node; Button<?> button; VanillaButton vanillaButton; CustomButton customButton; RecipeButton recipeButton; final boolean isActiveLinked; InvSlot slot; int x; Fluids fluids; int size; Text text; TankGauge tankGauge; switch (rawNode.getType()) {
/*     */         case environment:
/*  77 */           if (((GuiParser.EnvironmentNode)rawNode).environment != GuiEnvironment.GAME) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case button:
/*  91 */           buttonNode = (GuiParser.ButtonNode)rawNode;
/*     */           
/*  93 */           if (buttonNode.type != GuiParser.ButtonNode.ButtonType.RECIPE && !(this.container.base instanceof ic2.api.network.INetworkClientTileEntityEventListener) && !isHandHeldGUI()) {
/*  94 */             throw new RuntimeException("Invalid base " + this.container.base + " for button elements");
/*     */           }
/*     */           
/*  97 */           button = null;
/*     */           
/*  99 */           switch (buttonNode.type) {
/*     */             case environment:
/* 101 */               vanillaButton = new VanillaButton((GuiIC2)this, buttonNode.x, buttonNode.y, buttonNode.width, buttonNode.height, createEventSender(buttonNode.eventID, buttonNode.eventName));
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case key:
/* 108 */               customButton = new CustomButton((GuiIC2)this, buttonNode.x, buttonNode.y, buttonNode.width, buttonNode.height, createEventSender(buttonNode.eventID, buttonNode.eventName));
/*     */               break;
/*     */             
/*     */             case only:
/* 112 */               if (RecipeButton.canUse() && buttonNode.eventName != null) {
/* 113 */                 recipeButton = new RecipeButton((GuiIC2)this, buttonNode.x, buttonNode.y, buttonNode.width, buttonNode.height, buttonNode.eventName.split(",[ ]*"));
/* 114 */                 buttonNode.text = TextProvider.of("");
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           
/* 119 */           if (recipeButton != null) {
/* 120 */             Button button1; String str = buttonNode.text.get(this.container.base, Collections.singletonMap("name", TextProvider.ofTranslated(this.container.base.func_70005_c_())));
/* 121 */             if (buttonNode.icon == null) {
/* 122 */               button1 = recipeButton.withText(str);
/*     */             } else {
/* 124 */               button1.withIcon(Suppliers.ofInstance(buttonNode.icon));
/* 125 */               button1.withTooltip(str);
/*     */             } 
/* 127 */             parentNode.addElement(this, (GuiElement<?>)button1);
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case energygauge:
/* 133 */           if (!(this.container.base instanceof TileEntityBlock) || 
/* 134 */             !((TileEntityBlock)this.container.base).hasComponent(Energy.class)) {
/* 135 */             throw new RuntimeException("invalid base " + this.container.base + " for energygauge elements");
/*     */           }
/*     */           
/* 138 */           energyGaugeNode = (GuiParser.EnergyGaugeNode)rawNode;
/*     */           
/* 140 */           parentNode.addElement(this, (GuiElement<?>)new EnergyGauge((GuiIC2)this, energyGaugeNode.x, energyGaugeNode.y, (TileEntityBlock)this.container.base, energyGaugeNode.style));
/*     */           break;
/*     */ 
/*     */         
/*     */         case gauge:
/* 145 */           if (!(this.container.base instanceof IGuiValueProvider)) throw new RuntimeException("invalid base " + this.container.base + " for gauge elements");
/*     */           
/* 147 */           gaugeNode = (GuiParser.GaugeNode)rawNode;
/*     */           
/* 149 */           isActiveLinked = gaugeNode.activeLinked;
/* 150 */           if (isActiveLinked && !(this.container.base instanceof IGuiValueProvider.IActiveGuiValueProvider)) {
/* 151 */             throw new RuntimeException("Invalid base " + this.container.base + " for active linked gauge elements");
/*     */           }
/*     */           
/* 154 */           parentNode.addElement(this, (GuiElement<?>)new LinkedGauge((GuiIC2)this, gaugeNode.x, gaugeNode.y, (IGuiValueProvider)this.container.base, gaugeNode.name, gaugeNode.style)
/*     */               {
/*     */                 protected boolean isActive(double ratio) {
/* 157 */                   return isActiveLinked ? ((IGuiValueProvider.IActiveGuiValueProvider)DynamicGui.this.container.base).isGuiValueActive(this.name) : super.isActive(ratio);
/*     */                 }
/*     */               });
/*     */           break;
/*     */ 
/*     */         
/*     */         case image:
/* 164 */           imageNode = (GuiParser.ImageNode)rawNode;
/*     */           
/* 166 */           parentNode.addElement(this, (GuiElement<?>)Image.create((GuiIC2)this, imageNode.x, imageNode.y, imageNode.width, imageNode.height, imageNode.src, imageNode.baseWidth, imageNode.baseHeight, imageNode.u1, imageNode.v1, imageNode.u2, imageNode.v2));
/*     */           break;
/*     */ 
/*     */         
/*     */         case playerinventory:
/* 171 */           playerInventoryNode = (GuiParser.PlayerInventoryNode)rawNode;
/*     */           
/* 173 */           parentNode.addElement(this, (GuiElement<?>)new SlotGrid((GuiIC2)this, playerInventoryNode.x, playerInventoryNode.y, 9, 3, playerInventoryNode.style, 0, playerInventoryNode.spacing));
/* 174 */           parentNode.addElement(this, (GuiElement<?>)new SlotGrid((GuiIC2)this, playerInventoryNode.x, playerInventoryNode.y + playerInventoryNode.hotbarOffset, 9, 1, playerInventoryNode.style, 0, playerInventoryNode.spacing));
/* 175 */           if (playerInventoryNode.showTitle) {
/* 176 */             parentNode.addElement(this, (GuiElement<?>)Text.create((GuiIC2)this, playerInventoryNode.x + 1, playerInventoryNode.y - 10, 
/* 177 */                   TextProvider.ofTranslated(player.field_71071_by.func_70005_c_()), 4210752, false));
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case slot:
/*     */         case slothologram:
/* 184 */           slotNode = (GuiParser.SlotNode)rawNode;
/*     */           
/* 186 */           parentNode.addElement(this, (GuiElement<?>)new SlotGrid((GuiIC2)this, slotNode.x, slotNode.y, 1, 1, slotNode.style));
/*     */           break;
/*     */ 
/*     */         
/*     */         case slotgrid:
/* 191 */           if (!(this.container.base instanceof IInventorySlotHolder)) {
/* 192 */             throw new RuntimeException("Invalid base " + this.container.base + " for slot elements");
/*     */           }
/* 194 */           slotGridNode = (GuiParser.SlotGridNode)rawNode;
/*     */           
/* 196 */           slot = ((IInventorySlotHolder)this.container.base).getInventorySlot(slotGridNode.name);
/* 197 */           if (slot == null) {
/* 198 */             throw new RuntimeException("Invalid InvSlot name " + slotGridNode.name + " for base " + this.container.base);
/*     */           }
/* 200 */           size = slot.size();
/*     */           
/* 202 */           if (size > slotGridNode.offset) {
/* 203 */             GuiParser.SlotGridNode.SlotGridDimension dim = slotGridNode.getDimension(size);
/*     */             
/* 205 */             parentNode.addElement(this, (GuiElement<?>)new SlotGrid((GuiIC2)this, slotGridNode.x, slotGridNode.y, dim.cols, dim.rows, slotGridNode.style, 0, slotGridNode.spacing));
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case text:
/* 211 */           textNode = (GuiParser.TextNode)rawNode;
/*     */ 
/*     */           
/* 214 */           switch (textNode.align) { case environment:
/* 215 */               x = textNode.x; break;
/* 216 */             case gui: x = textNode.x + this.field_146999_f / 2; break;
/* 217 */             case key: x = textNode.x + this.field_146999_f; break;
/* 218 */             default: throw new IllegalArgumentException("invalid alignment: " + textNode.align); }
/*     */ 
/*     */ 
/*     */           
/* 222 */           if (textNode.rightAligned) {
/* 223 */             text = Text.createRightAligned((GuiIC2)this, x, textNode.y, textNode.width, textNode.height, textNode.text, textNode.color, textNode.shadow, textNode.xOffset, textNode.yOffset, textNode.centerX, textNode.centerY);
/*     */           } else {
/*     */             
/* 226 */             text = Text.create((GuiIC2)this, x, textNode.y, textNode.width, textNode.height, textNode.text, textNode.color, textNode.shadow, textNode.xOffset, textNode.yOffset, textNode.centerX, textNode.centerY);
/*     */           } 
/*     */           
/* 229 */           parentNode.addElement(this, (GuiElement<?>)text);
/*     */           break;
/*     */         
/*     */         case fluidtank:
/* 233 */           if (!(this.container.base instanceof TileEntityBlock) || 
/* 234 */             !((TileEntityBlock)this.container.base).hasComponent(Fluids.class)) {
/* 235 */             throw new RuntimeException("invalid base " + this.container.base + " for tank elements");
/*     */           }
/*     */           
/* 238 */           fluidTankNode = (GuiParser.FluidTankNode)rawNode;
/* 239 */           fluids = (Fluids)((TileEntityBlock)this.container.base).getComponent(Fluids.class);
/*     */           
/* 241 */           switch (fluidTankNode.type) {
/*     */             case environment:
/* 243 */               tankGauge = TankGauge.createNormal((GuiIC2)this, fluidTankNode.x, fluidTankNode.y, (IFluidTank)fluids.getFluidTank(fluidTankNode.name));
/*     */               break;
/*     */             
/*     */             case gui:
/* 247 */               tankGauge = TankGauge.createPlain((GuiIC2)this, fluidTankNode.x, fluidTankNode.y, fluidTankNode.width, fluidTankNode.height, (IFluidTank)fluids.getFluidTank(fluidTankNode.name));
/*     */               break;
/*     */             
/*     */             case key:
/* 251 */               tankGauge = TankGauge.createBorderless((GuiIC2)this, fluidTankNode.x, fluidTankNode.y, (IFluidTank)fluids.getFluidTank(fluidTankNode.name), fluidTankNode.mirrored);
/*     */               break;
/*     */             
/*     */             default:
/* 255 */               throw new IllegalStateException("Unexpected type " + fluidTankNode.type);
/*     */           } 
/* 257 */           parentNode.addElement(this, (GuiElement<?>)tankGauge);
/*     */           break;
/*     */         
/*     */         case fluidslot:
/* 261 */           if (!(this.container.base instanceof TileEntityBlock) || 
/* 262 */             !((TileEntityBlock)this.container.base).hasComponent(Fluids.class)) {
/* 263 */             throw new RuntimeException("invalid base " + this.container.base + " for tank elements");
/*     */           }
/*     */           
/* 266 */           node = (GuiParser.FluidSlotNode)rawNode;
/* 267 */           parentNode.addElement(this, (GuiElement<?>)FluidSlot.createFluidSlot((GuiIC2)this, node.x, node.y, (IFluidTank)((Fluids)((TileEntityBlock)this.container.base).getComponent(Fluids.class)).getFluidTank(node.name)));
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 273 */       if (rawNode instanceof GuiParser.ParentNode) {
/* 274 */         initializeWidgets(player, (GuiParser.ParentNode)rawNode);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected IClickHandler createEventSender(int event, String eventString) {
/* 280 */     if (isHandHeldGUI()) {
/*     */       final String eventName;
/* 282 */       if (eventString == null) {
/* 283 */         IC2.log.warn(LogCategory.General, "HandHand inventory given numeric event rather than string");
/*     */         
/* 285 */         eventName = Integer.toString(event);
/*     */       } else {
/* 287 */         eventName = eventString;
/*     */       } 
/*     */       
/* 290 */       return new IClickHandler()
/*     */         {
/*     */           public void onClick(MouseButton button) {
/* 293 */             ((NetworkManager)IC2.network.get(false)).sendContainerEvent(DynamicGui.this.container, eventName);
/*     */ 
/*     */             
/* 296 */             ((HandHeldInventory)DynamicGui.this.container.base).onEvent(eventName);
/*     */           }
/*     */         };
/*     */     } 
/* 300 */     assert eventString == null;
/*     */     
/* 302 */     return createEventSender(event);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isHandHeldGUI() {
/* 307 */     return this.container.base instanceof HandHeldInventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addElement(GuiElement<?> element) {
/* 312 */     super.addElement(element);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\DynamicGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */