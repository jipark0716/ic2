/*     */ package ic2.core.item.upgrade;
/*     */ public class HandHeldValueConfig extends HandHeldUpgradeOption { protected final ComparisonType initialComparisonType; protected final String initialNormalBox; protected final String initialExtraBox; protected final ComparisonSettings initialNormalSetting; protected final ComparisonSettings initialExtraSetting; public class ContainerValueConfig extends ContainerHandHeldInventory<HandHeldValueConfig> { @ClientModifiable protected ComparisonType comparisonType; @ClientModifiable protected String normalBox; @ClientModifiable protected String extraBox; @ClientModifiable protected ComparisonSettings normalSetting; @ClientModifiable
/*     */     protected ComparisonSettings extraSetting; public void func_75134_a(EntityPlayer player) { NBTTagCompound nbt = HandHeldValueConfig.this.getNBT();
/*     */       nbt.func_74757_a("active", this.comparisonType.enabled());
/*     */       ComparisonType saveType = this.comparisonType;
/*     */       switch (this.comparisonType) {
/*     */         case LESS:
/*     */           if (this.normalBox.isEmpty()) {
/*     */             saveType = ComparisonType.DIRECT;
/*     */           } else {
/*     */             nbt.func_74778_a("normal", this.normalBox);
/*     */             nbt.func_74774_a("normalComp", this.normalSetting.getForNBT());
/*     */           } 
/*     */         case LESS_OR_EQUAL:
/*     */           if (this.normalBox.isEmpty()) {
/*     */             if (this.extraBox.isEmpty()) {
/*     */               saveType = ComparisonType.DIRECT;
/*     */               break;
/*     */             } 
/*     */             saveType = ComparisonType.COMPARISON;
/*     */             nbt.func_74778_a("normal", this.extraBox);
/*     */             nbt.func_74774_a("normalComp", this.extraSetting.getForNBT());
/*     */             break;
/*     */           } 
/*     */           nbt.func_74778_a("normal", this.normalBox);
/*     */           nbt.func_74774_a("normalComp", this.normalSetting.getForNBT());
/*     */           if (this.extraBox.isEmpty()) {
/*     */             saveType = ComparisonType.COMPARISON;
/*     */             break;
/*     */           } 
/*     */           nbt.func_74778_a("extra", this.extraBox);
/*     */           nbt.func_74774_a("extraComp", this.extraSetting.getForNBT());
/*     */           break;
/*     */       } 
/*     */       nbt.func_74774_a("type", saveType.getForNBT());
/*  36 */       super.func_75134_a(player); } public ContainerValueConfig() { super(HandHeldValueConfig.this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       this.comparisonType = HandHeldValueConfig.this.initialComparisonType;
/*     */       
/*  90 */       this.normalBox = HandHeldValueConfig.this.initialNormalBox; this.extraBox = HandHeldValueConfig.this.initialExtraBox;
/*     */       
/*  92 */       this.normalSetting = HandHeldValueConfig.this.initialNormalSetting; this.extraSetting = HandHeldValueConfig.this.initialExtraSetting;
/*     */       addPlayerInventorySlots(HandHeldValueConfig.this.player, 166);
/*     */       for (byte slot = 0; slot < 9; slot = (byte)(slot + 1))
/*     */         func_75146_a((Slot)new SlotHologramSlot(HandHeldValueConfig.this.inventory, slot, 8 + 18 * slot, 8, 1, HandHeldValueConfig.this.makeSaveCallback()));  }
/*     */      }
/*     */   @SideOnly(Side.CLIENT)
/*     */   public class GuiValueConfig extends GuiDefaultBackground<ContainerValueConfig> { public GuiValueConfig() {
/*  99 */       super((ContainerBase)new HandHeldValueConfig.ContainerValueConfig(HandHeldValueConfig.this));
/*     */       
/* 101 */       addElement((GuiElement)HandHeldValueConfig.this.getBackButton((GuiIC2<?>)this, 10, 62));
/*     */       
/* 103 */       addElement(((VanillaButton)(new VanillaButton((GuiIC2)this, 10, 25, 75, 15, (IClickHandler)new EnumCycleHandler<ComparisonType>(ComparisonType.VALUES, ((HandHeldValueConfig.ContainerValueConfig)this.container).comparisonType)
/*     */             {
/*     */               public void onClick(MouseButton button) {
/* 106 */                 super.onClick(button);
/*     */                 
/* 108 */                 ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).comparisonType = (ComparisonType)getCurrentValue();
/* 109 */                 ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "comparisonType");
/*     */               }
/* 111 */             })).withText(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/* 114 */                 return Localization.translate(((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).comparisonType.name);
/*     */               }
/* 116 */             })).withTooltip(new Supplier<String>() {
/* 117 */               private final String name = Localization.translate("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_());
/*     */ 
/*     */               
/*     */               public String get() {
/* 121 */                 return Localization.translate(((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).comparisonType.name + ".desc", new Object[] { this.name });
/*     */               }
/*     */             }));
/*     */       
/* 125 */       IEnableHandler rangeEnabled = new IEnableHandler()
/*     */         {
/*     */           public boolean isEnabled() {
/* 128 */             return (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).comparisonType == ComparisonType.RANGE);
/*     */           }
/*     */         };
/* 131 */       IEnableHandler filtersEnabled = new IEnableHandler()
/*     */         {
/*     */           public boolean isEnabled() {
/* 134 */             return !((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).comparisonType.ignoreFilters();
/*     */           }
/*     */         };
/* 137 */       addElement(((VanillaButton)((VanillaButton)(new MoveableButton((GuiIC2<?>)this, 75, 43, 60, 43, 17, 15, (IClickHandler)new EnumCycleHandler<ComparisonSettings>(ComparisonSettings.VALUES, ((HandHeldValueConfig.ContainerValueConfig)this.container).normalSetting)
/*     */             {
/*     */               public void onClick(MouseButton button)
/*     */               {
/* 141 */                 super.onClick(button);
/*     */                 
/* 143 */                 ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalSetting = (ComparisonSettings)getCurrentValue();
/* 144 */                 ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "normalSetting");
/*     */                 
/* 146 */                 switch ((ComparisonSettings)getCurrentValue()) {
/*     */                   case LESS:
/*     */                   case LESS_OR_EQUAL:
/* 149 */                     if (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting != ComparisonSettings.LESS && ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting != ComparisonSettings.LESS_OR_EQUAL) {
/* 150 */                       ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.LESS;
/* 151 */                       ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "extraSetting");
/*     */                     } 
/*     */                     return;
/*     */                   
/*     */                   case GREATER:
/*     */                   case GREATER_OR_EQUAL:
/* 157 */                     if (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting != ComparisonSettings.GREATER && ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting != ComparisonSettings.GREATER_OR_EQUAL) {
/* 158 */                       ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.GREATER;
/* 159 */                       ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "extraSetting");
/*     */                     } 
/*     */                     return;
/*     */                 } 
/*     */                 
/* 164 */                 throw new IllegalStateException("Unexpected other setting: " + getCurrentValue());
/*     */               }
/* 167 */             })).withMoveHandler(rangeEnabled).withEnableHandler(filtersEnabled)).withText(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/* 170 */                 return ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalSetting.symbol;
/*     */               }
/* 172 */             })).withTooltip(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/* 175 */                 return Localization.translate(((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalSetting.name);
/*     */               }
/*     */             }));
/*     */       
/* 179 */       addElement(((VanillaButton)((VanillaButton)(new VanillaButton((GuiIC2)this, 105, 43, 17, 15, new IClickHandler()
/*     */             {
/*     */               public void onClick(MouseButton button) {
/* 182 */                 if (button == MouseButton.left || button == MouseButton.right) {
/* 183 */                   switch (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalSetting) {
/*     */                     case LESS:
/*     */                     case LESS_OR_EQUAL:
/* 186 */                       if (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting == ComparisonSettings.LESS) {
/* 187 */                         ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.LESS_OR_EQUAL; break;
/*     */                       } 
/* 189 */                       ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.LESS;
/*     */                       break;
/*     */ 
/*     */                     
/*     */                     case GREATER:
/*     */                     case GREATER_OR_EQUAL:
/* 195 */                       if (((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting == ComparisonSettings.GREATER) {
/* 196 */                         ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.GREATER_OR_EQUAL; break;
/*     */                       } 
/* 198 */                       ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting = ComparisonSettings.GREATER;
/*     */                       break;
/*     */ 
/*     */                     
/*     */                     default:
/* 203 */                       throw new IllegalStateException("Unexpected other setting: " + ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalSetting);
/*     */                   } 
/*     */                   
/* 206 */                   ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "extraSetting");
/*     */                 } 
/*     */               }
/* 209 */             })).withEnableHandler(rangeEnabled)).withText(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/* 212 */                 return ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting.symbol;
/*     */               }
/* 214 */             })).withTooltip(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/* 217 */                 return Localization.translate(((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraSetting.name);
/*     */               }
/*     */             }));
/*     */       
/* 221 */       Predicate<String> numberOnly = new Predicate<String>()
/*     */         {
/*     */           public boolean apply(String input) {
/*     */             try {
/* 225 */               return (Integer.parseInt(input) >= 0);
/* 226 */             } catch (NumberFormatException e) {
/* 227 */               return input.isEmpty();
/*     */             } 
/*     */           }
/*     */         };
/* 231 */       final MoveableTextBox textBox = new MoveableTextBox((GuiIC2<?>)this, 40, 43, 25, 43, 30, 15, ((HandHeldValueConfig.ContainerValueConfig)this.container).normalBox);
/* 232 */       addElement(textBox.withMoveHandler(rangeEnabled).withTextWatcher(new TextBox.ITextBoxWatcher()
/*     */             {
/*     */               public void onChanged(String oldValue, String newValue) {
/* 235 */                 ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).normalBox = newValue;
/* 236 */                 ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "normalBox");
/*     */               }
/* 238 */             }).withTextValidator(numberOnly).withEnableHandler(filtersEnabled));
/* 239 */       addElement((new TextBox((GuiIC2)this, 125, 43, 30, 15, ((HandHeldValueConfig.ContainerValueConfig)this.container).extraBox)).withTextWatcher(new TextBox.ITextBoxWatcher()
/*     */             {
/*     */               public void onChanged(String oldValue, String newValue) {
/* 242 */                 ((HandHeldValueConfig.ContainerValueConfig)HandHeldValueConfig.GuiValueConfig.this.container).extraBox = newValue;
/* 243 */                 ((NetworkManager)IC2.network.get(false)).sendContainerField(HandHeldValueConfig.GuiValueConfig.this.container, "extraBox");
/*     */               }
/* 245 */             }).withTextValidator(numberOnly).withEnableHandler(rangeEnabled));
/*     */ 
/*     */       
/* 248 */       addElement(Text.create((GuiIC2)this, 100, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_()), 4210752, false).withEnableHandler(new IEnableHandler()
/*     */             {
/*     */               public boolean isEnabled() {
/* 251 */                 return (textBox.isEnabled() && !textBox.isMoved());
/*     */               }
/*     */             }));
/* 254 */       addElement(Text.create((GuiIC2)this, 80, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_()), 4210752, false).withEnableHandler(new IEnableHandler()
/*     */             {
/*     */               public boolean isEnabled() {
/* 257 */                 return (textBox.isEnabled() && textBox.isMoved());
/*     */               }
/*     */             }));
/*     */ 
/*     */ 
/*     */       
/* 263 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
/* 264 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 83, 9, 3, SlotGrid.SlotStyle.Normal));
/* 265 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 141, 9, 1, SlotGrid.SlotStyle.Normal));
/*     */     } }
/*     */ 
/*     */   
/*     */   public HandHeldValueConfig(HandHeldAdvancedUpgrade upgradeGUI, String type) {
/* 270 */     super(upgradeGUI, type);
/*     */     
/* 272 */     Settings settings = new Settings(getNBT());
/* 273 */     this.initialComparisonType = settings.comparison;
/* 274 */     this.initialNormalBox = settings.mainBox;
/* 275 */     this.initialExtraBox = settings.extraBox;
/* 276 */     this.initialNormalSetting = settings.main;
/* 277 */     this.initialExtraSetting = settings.extra;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 282 */     return (ContainerBase<?>)new ContainerValueConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 288 */     return (GuiScreen)new GuiValueConfig();
/*     */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\HandHeldValueConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */