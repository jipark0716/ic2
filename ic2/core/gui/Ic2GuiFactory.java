/*     */ package ic2.core.gui;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterators;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.Config;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.client.IModGuiFactory;
/*     */ import net.minecraftforge.fml.client.config.ConfigGuiType;
/*     */ import net.minecraftforge.fml.client.config.DummyConfigElement;
/*     */ import net.minecraftforge.fml.client.config.GuiConfig;
/*     */ import net.minecraftforge.fml.client.config.GuiConfigEntries;
/*     */ import net.minecraftforge.fml.client.config.GuiEditArray;
/*     */ import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
/*     */ import net.minecraftforge.fml.client.config.IConfigElement;
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
/*     */ public class Ic2GuiFactory
/*     */   implements IModGuiFactory
/*     */ {
/*     */   public static class IC2ConfigGuiScreen
/*     */     extends GuiConfig
/*     */   {
/*     */     public IC2ConfigGuiScreen(GuiScreen parent) {
/*  58 */       super(parent, sinkCategoryLevel(MainConfig.get(), "."), "ic2", false, false, "IC2 Configuration");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static List<IConfigElement> sinkCategoryLevel(Config config, String parentName) {
/*  69 */       List<IConfigElement> list = new ArrayList<>(config.getNumberOfSections() + config.getNumberOfConfigs());
/*  70 */       if (config.hasChildSection()) {
/*  71 */         for (Iterator<Config> configCategories = config.sectionIterator(); configCategories.hasNext(); ) {
/*  72 */           Config category = configCategories.next();
/*     */           
/*  74 */           if ("predefined".equals(category.name) && ".balance.uu-values.".equals(parentName)) {
/*  75 */             list.add(new UUListElement()); continue;
/*     */           } 
/*  77 */           list.add(new DummyConfigElement.DummyCategoryElement(category.name, "ic2.config.sub." + category.name, sinkCategoryLevel(category, parentName + category.name + '.')));
/*     */         } 
/*     */         
/*  80 */         if (!config.isEmptySection()) {
/*  81 */           getConfigs(list, config.valueIterator(), parentName);
/*     */         }
/*     */       } else {
/*  84 */         getConfigs(list, config.valueIterator(), parentName);
/*     */       } 
/*  86 */       return list;
/*     */     }
/*     */     
/*     */     private static void getConfigs(List<IConfigElement> list, Iterator<Config.Value> configs, String parentName) {
/*  90 */       while (configs.hasNext()) {
/*  91 */         ConfigGuiType type; Config.Value conf = configs.next();
/*  92 */         Config.Value defaultConf = MainConfig.getDefault(parentName.substring(1).replace('.', '/') + conf.name);
/*  93 */         if (defaultConf == null)
/*     */           continue; 
/*  95 */         if (defaultConf.value.isEmpty() || defaultConf.value.contains(",") || defaultConf.comment.contains("comma")) {
/*     */           
/*  97 */           list.add((new ListElement(conf.name, conf.value, defaultConf.value, "ic2.config" + parentName + conf.name, ITEM_PATTERN)).setArrayEntryClass(ItemEntry.class));
/*     */           continue;
/*     */         } 
/* 100 */         if (IS_DOUBLE.matcher(conf.value).matches()) {
/* 101 */           type = ConfigGuiType.DOUBLE;
/* 102 */         } else if (IS_INT.matcher(conf.value).matches()) {
/* 103 */           type = ConfigGuiType.INTEGER;
/* 104 */         } else if (IS_BOOLEAN.matcher(conf.value).matches()) {
/* 105 */           type = ConfigGuiType.BOOLEAN;
/*     */         } else {
/* 107 */           type = ConfigGuiType.STRING;
/*     */         } 
/* 109 */         list.add(new ConfigElement(conf.name, conf.value, defaultConf.value, type, "ic2.config" + parentName + conf.name));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_146281_b() {
/* 119 */       for (IConfigElement config : this.configElements) {
/* 120 */         saveConfig(config);
/*     */       }
/* 122 */       MainConfig.save();
/*     */       
/* 124 */       super.func_146281_b();
/*     */     }
/*     */     
/*     */     private void saveConfig(IConfigElement config) {
/* 128 */       if (config.getChildElements() != null) {
/* 129 */         for (IConfigElement subConfig : config.getChildElements()) {
/* 130 */           saveConfig(subConfig);
/*     */         }
/*     */       }
/* 133 */       if (config.isProperty()) {
/* 134 */         if (config.isList()) {
/* 135 */           assert config instanceof ListElement : "Unexpected class type: " + config.getClass();
/*     */           
/* 137 */           ((ListElement)config).save();
/*     */         } else {
/* 139 */           assert config.getClass() == ConfigElement.class : "Unexpected class type: " + config.getClass();
/*     */           
/* 141 */           if (!Objects.equals(config.get(), ((ConfigElement)config).previous))
/*     */           {
/* 143 */             MainConfig.get().set(config.getLanguageKey().substring(LANG_KEY_LENGTH).replace('.', '/'), config.get());
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private static class ConfigElement
/*     */       extends DummyConfigElement
/*     */     {
/*     */       Object previous;
/*     */       
/*     */       ConfigElement(String name, Object value, Object defaultValue, ConfigGuiType type, String langKey) {
/* 156 */         super(name, defaultValue, type, langKey);
/*     */         
/* 158 */         this.previous = this.value = value;
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(Object value) {
/* 163 */         this.previous = this.value;
/* 164 */         this.value = value;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setToDefault() {
/* 169 */         this.previous = this.value;
/* 170 */         super.setToDefault();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 175 */         return "ConfigElement<" + this.name + '>';
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static class ListElement
/*     */       extends DummyConfigElement.DummyListElement
/*     */     {
/*     */       protected Object[] previous;
/*     */ 
/*     */       
/*     */       protected ListElement(String name, CharSequence value, CharSequence defaultValues, String langKey, Pattern pattern) {
/* 188 */         super(name, COMMA_SPLITTER.splitToList(defaultValues).toArray(), ConfigGuiType.STRING, langKey, pattern);
/*     */         
/* 190 */         this.previous = this.values = COMMA_SPLITTER.splitToList(value).toArray();
/*     */       }
/*     */       
/*     */       protected void save() {
/* 194 */         if (!this.previous.equals(getList())) {
/* 195 */           MainConfig.get().set(getLanguageKey().substring(Ic2GuiFactory.IC2ConfigGuiScreen.LANG_KEY_LENGTH).replace('.', '/'), Ic2GuiFactory.IC2ConfigGuiScreen.COMMA_JOINER.join(getList()));
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(Object[] values) {
/* 201 */         this.previous = this.values;
/* 202 */         this.values = values;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setToDefault() {
/* 207 */         this.previous = this.values;
/* 208 */         super.setToDefault();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/* 213 */         return "Config" + getClass().getSimpleName() + '<' + this.name + '>';
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 218 */       private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static class ItemEntry
/*     */       extends GuiEditArrayEntries.StringEntry
/*     */     {
/* 227 */       private static final Field ENABLED = ReflectionUtil.getField(GuiEditArray.class, boolean.class);
/* 228 */       private static final Method TOOLTIP = ReflectionUtil.getMethod(GuiScreen.class, new String[] { "func_146285_a", "renderToolTip", "a" }, new Class[] { ItemStack.class, int.class, int.class }); protected ItemStack stack;
/*     */       protected int stackX;
/*     */       protected int stackY;
/*     */       
/*     */       public ItemEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
/* 233 */         super(owningScreen, owningEntryList, configElement, value);
/*     */         
/* 235 */         assert this.isValidated;
/* 236 */         if (this.isValidValue) updateStack();
/*     */       
/*     */       }
/*     */       
/*     */       public String getValue() {
/* 241 */         return (String)super.getValue();
/*     */       }
/*     */       
/*     */       protected String getStack() {
/* 245 */         return getValue();
/*     */       }
/*     */       
/*     */       protected void updateStack() {
/*     */         try {
/* 250 */           this.stack = ConfigUtil.asStack(getStack());
/*     */           
/* 252 */           this.isValidValue = !StackUtil.isEmpty(this.stack);
/* 253 */         } catch (ParseException e) {
/* 254 */           this.isValidValue = false;
/*     */         } 
/*     */       }
/*     */       
/*     */       protected boolean isEnabled() {
/*     */         try {
/* 260 */           return ENABLED.getBoolean(this.owningScreen);
/* 261 */         } catch (Exception e) {
/* 262 */           throw new RuntimeException("Error checking owningScreen enabled!", e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void keyTyped(char eventChar, int eventKey) {
/* 268 */         super.keyTyped(eventChar, eventKey);
/*     */         
/* 270 */         if (this.isValidValue && isEnabled()) updateStack();
/*     */       
/*     */       }
/*     */       
/*     */       public void func_192634_a(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partial) {
/* 275 */         if (this.isValidValue) this.isValidated = false; 
/* 276 */         super.func_192634_a(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
/* 277 */         this.isValidated = true;
/*     */         
/* 279 */         assert getValue() != null;
/* 280 */         if (this.isValidValue) {
/* 281 */           RenderHelper.func_74520_c();
/* 282 */           this.owningEntryList.getMC().func_175599_af().func_175042_a(this.stack, this.stackX = listWidth / 4 - 16 - 1, this.stackY = y + slotHeight / 2 - 8);
/*     */ 
/*     */           
/* 285 */           RenderHelper.func_74518_a();
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void drawToolTip(int mouseX, int mouseY) {
/* 292 */         super.drawToolTip(mouseX, mouseY);
/*     */         
/* 294 */         if (!StackUtil.isEmpty(this.stack) && this.stackX <= mouseX && this.stackX + 16 >= mouseX && this.stackY <= mouseY && this.stackY + 16 >= mouseY) {
/* 295 */           assert getClass() != ItemEntry.class || this.isValidValue;
/*     */           
/*     */           try {
/* 298 */             TOOLTIP.invoke(this.owningScreen, new Object[] { this.stack, Integer.valueOf(mouseX), Integer.valueOf(mouseY) });
/* 299 */           } catch (Exception e) {
/* 300 */             throw new RuntimeException("Error drawing tooltip!", e);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static class UUListElement
/*     */       extends ListElement
/*     */     {
/*     */       public static class ArrayCategory
/*     */         extends GuiConfigEntries.CategoryEntry
/*     */       {
/*     */         private int index;
/*     */         
/*     */         protected Object[] currentValues;
/*     */         
/*     */         protected final Object[] beforeValues;
/*     */ 
/*     */         
/*     */         public ArrayCategory(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
/* 322 */           super(owningScreen, owningEntryList, configElement);
/*     */           
/* 324 */           this.beforeValues = configElement.getList();
/* 325 */           this.currentValues = configElement.getList();
/*     */           
/* 327 */           this.childScreen = buildChildScreen();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 332 */           this.index = index;
/* 333 */           return super.func_148278_a(index, x, y, mouseEvent, relativeX, relativeY);
/*     */         }
/*     */ 
/*     */         
/*     */         protected GuiScreen buildChildScreen() {
/* 338 */           return (GuiScreen)new GuiEditArray((GuiScreen)this.owningScreen, this.configElement, this.index, this.currentValues, enabled());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isDefault() {
/* 343 */           return Arrays.deepEquals(this.configElement.getDefaults(), this.currentValues);
/*     */         }
/*     */ 
/*     */         
/*     */         public void setToDefault() {
/* 348 */           this.currentValues = this.configElement.getDefaults();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isChanged() {
/* 353 */           return !Arrays.deepEquals(this.beforeValues, this.currentValues);
/*     */         }
/*     */ 
/*     */         
/*     */         public void undoChanges() {
/* 358 */           this.currentValues = this.beforeValues;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean saveConfigElement() {
/* 363 */           if (isChanged()) {
/* 364 */             this.configElement.set(this.currentValues);
/* 365 */             return this.configElement.requiresMcRestart();
/*     */           } 
/* 367 */           return false;
/*     */         }
/*     */       }
/*     */       
/*     */       public static class UUEntry
/*     */         extends Ic2GuiFactory.IC2ConfigGuiScreen.ItemEntry
/*     */       {
/*     */         protected boolean hasValidStack;
/*     */         protected final GuiTextField uuValue;
/*     */         
/*     */         public UUEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
/* 378 */           super(owningScreen, owningEntryList, configElement, value);
/*     */           
/* 380 */           int totalSpace = this.textFieldValue.field_146218_h;
/* 381 */           int textSpace = Math.round((totalSpace * 3) / 4.0F);
/* 382 */           int numSpace = totalSpace - textSpace;
/*     */           
/* 384 */           this.textFieldValue.field_146218_h = textSpace - 1;
/* 385 */           this.uuValue = new GuiTextField(1, (owningEntryList.getMC()).field_71466_p, this.textFieldValue.field_146209_f + textSpace, 0, numSpace, 16);
/* 386 */           this.uuValue.func_146203_f(25);
/* 387 */           this.uuValue.func_146180_a(value.toString());
/* 388 */           this.uuValue.func_175205_a(new Predicate<String>()
/*     */               {
/*     */                 public boolean apply(String input) {
/*     */                   try {
/* 392 */                     return (Double.parseDouble(input) >= 0.0D);
/* 393 */                   } catch (NumberFormatException e) {
/* 394 */                     return input.isEmpty();
/*     */                   } 
/*     */                 }
/*     */               });
/*     */           
/* 399 */           String val = value.toString();
/* 400 */           int split = val.lastIndexOf(':');
/* 401 */           if (split > -1) {
/* 402 */             this.textFieldValue.func_146180_a(val.substring(0, split));
/* 403 */             this.uuValue.func_146180_a(val.substring(split + 1));
/*     */           } else {
/* 405 */             assert this.textFieldValue.func_146179_b().isEmpty() : "Expected empty textFieldValue but found: " + this.textFieldValue.func_146179_b();
/* 406 */             assert this.uuValue.func_146179_b().isEmpty() : "Expected empty uuValue but found: " + this.uuValue.func_146179_b();
/*     */           } 
/*     */           
/* 409 */           assert configElement.getValidationPattern() != null;
/* 410 */           updateState();
/*     */         }
/*     */         
/*     */         protected void updateState() {
/* 414 */           if (this.configElement.getValidationPattern().matcher(getStack()).matches()) {
/* 415 */             updateStack();
/* 416 */             this.hasValidStack = this.isValidValue;
/*     */           } else {
/* 418 */             this.hasValidStack = false;
/*     */           } 
/* 420 */           this.isValidValue &= !this.uuValue.func_146179_b().trim().isEmpty() ? 1 : 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public void func_192634_a(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partial) {
/* 425 */           boolean previous = this.isValidValue;
/* 426 */           this.isValidValue = this.hasValidStack;
/*     */           
/* 428 */           super.func_192634_a(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
/*     */           
/* 430 */           this.isValidValue = previous;
/*     */           
/* 432 */           this.uuValue.func_146189_e((slotIndex != this.owningEntryList.listEntries.size() - 1));
/* 433 */           this.uuValue.field_146210_g = y + 1;
/* 434 */           this.uuValue.func_146194_f();
/*     */         }
/*     */ 
/*     */         
/*     */         public void keyTyped(char eventChar, int eventKey) {
/* 439 */           boolean enabled = isEnabled();
/* 440 */           if (enabled || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
/* 441 */             this.textFieldValue.func_146201_a(enabled ? eventChar : Character.MIN_VALUE, eventKey);
/* 442 */             this.uuValue.func_146201_a(enabled ? eventChar : Character.MIN_VALUE, eventKey);
/*     */             
/* 444 */             if (enabled) updateState();
/*     */           
/*     */           } 
/*     */         }
/*     */         
/*     */         public void updateCursorCounter() {
/* 450 */           super.updateCursorCounter();
/*     */           
/* 452 */           this.uuValue.func_146178_a();
/*     */         }
/*     */ 
/*     */         
/*     */         public void mouseClicked(int x, int y, int mouseEvent) {
/* 457 */           super.mouseClicked(x, y, mouseEvent);
/*     */           
/* 459 */           this.uuValue.func_146192_a(x, y, mouseEvent);
/*     */         }
/*     */ 
/*     */         
/*     */         public String getValue() {
/* 464 */           return getStack() + ':' + this.uuValue.func_146179_b().trim();
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getStack() {
/* 469 */           return super.getValue();
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private static CharSequence getValues(Iterator<Config.Value> sub) {
/* 477 */         return Ic2GuiFactory.IC2ConfigGuiScreen.COMMA_JOINER.join(Iterators.transform(sub, new Function<Config.Value, String>()
/*     */               {
/*     */                 public String apply(Config.Value input) {
/* 480 */                   return input.name + ':' + input.value;
/*     */                 }
/*     */               }));
/*     */       }
/*     */       
/*     */       UUListElement() {
/* 486 */         super("predefined", getValues(MainConfig.get().getSub("balance/uu-values/predefined").valueIterator()), 
/* 487 */             getValues(MainConfig.getDefaults("balance/uu-values/predefined")), "ic2.config.sub.predefined", Ic2GuiFactory.IC2ConfigGuiScreen.ITEM_PATTERN);
/*     */         
/* 489 */         setConfigEntryClass(ArrayCategory.class);
/* 490 */         setArrayEntryClass(UUEntry.class);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(Object[] values) {
/* 495 */         super.set(values);
/*     */         
/* 497 */         save();
/* 498 */         MainConfig.save();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected void save() {
/* 504 */         Config config = MainConfig.get().getSub("balance/uu-values/predefined");
/*     */         
/* 506 */         for (Object line : getList()) {
/* 507 */           String part = (String)line;
/* 508 */           System.out.println("Trying to save part: " + part);
/*     */           
/* 510 */           int split = part.lastIndexOf(':');
/* 511 */           config.set(part.substring(0, split), part.substring(split + 1));
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 516 */     static final int LANG_KEY_LENGTH = "ic2.config.".length();
/* 517 */     static final Joiner COMMA_JOINER = Joiner.on(", ");
/*     */     
/* 519 */     static final Pattern ITEM_PATTERN = Pattern.compile("^[A-Za-z0-9_]+:[A-Za-z0-9_]+(#[A-Za-z0-9_]+|(@(\\d+|\\*)))?$");
/* 520 */     private static final Pattern IS_BOOLEAN = Pattern.compile("true|false", 2);
/* 521 */     private static final Pattern IS_INT = Pattern.compile("\\d");
/* 522 */     private static final Pattern IS_DOUBLE = Pattern.compile("\\d\\.\\d");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(Minecraft mc) {}
/*     */ 
/*     */   
/*     */   public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
/* 531 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasConfigGui() {
/* 536 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiScreen createConfigGui(GuiScreen parentScreen) {
/* 541 */     return (GuiScreen)new IC2ConfigGuiScreen(parentScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\Ic2GuiFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */