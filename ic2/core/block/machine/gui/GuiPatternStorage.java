/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.machine.container.ContainerPatternStorage;
/*    */ import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
/*    */ import ic2.core.gui.CustomButton;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ import ic2.core.gui.ItemImage;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiPatternStorage
/*    */   extends GuiIC2<ContainerPatternStorage>
/*    */ {
/*    */   public GuiPatternStorage(final ContainerPatternStorage container) {
/* 27 */     super((ContainerBase)container);
/*    */     
/* 29 */     addElement((new CustomButton(this, 7, 19, 9, 18, createEventSender(0)))
/* 30 */         .withTooltip("ic2.PatternStorage.gui.info.last"));
/* 31 */     addElement((new CustomButton(this, 36, 19, 9, 18, createEventSender(1)))
/* 32 */         .withTooltip("ic2.PatternStorage.gui.info.next"));
/* 33 */     addElement((new CustomButton(this, 10, 37, 16, 8, createEventSender(2)))
/* 34 */         .withTooltip("ic2.PatternStorage.gui.info.export"));
/* 35 */     addElement((new CustomButton(this, 26, 37, 16, 8, createEventSender(3)))
/* 36 */         .withTooltip("ic2.PatternStorage.gui.info.import"));
/* 37 */     addElement((GuiElement)Text.create(this, this.field_146999_f / 2, 30, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 40 */                 TileEntityPatternStorage te = (TileEntityPatternStorage)container.base;
/*    */                 
/* 42 */                 return Math.min(te.index + 1, te.maxIndex) + " / " + te.maxIndex;
/*    */               }
/*    */             }), 4210752, false, true, false));
/* 45 */     addElement((GuiElement)Text.create(this, 10, 48, TextProvider.ofTranslated("ic2.generic.text.Name"), 16777215, false));
/* 46 */     addElement((GuiElement)Text.create(this, 10, 59, TextProvider.ofTranslated("ic2.generic.text.UUMatte"), 16777215, false));
/* 47 */     addElement((GuiElement)Text.create(this, 10, 70, TextProvider.ofTranslated("ic2.generic.text.Energy"), 16777215, false));
/*    */     
/* 49 */     IEnableHandler patternInfoEnabler = new IEnableHandler()
/*    */       {
/*    */         public boolean isEnabled() {
/* 52 */           return (((TileEntityPatternStorage)container.base).pattern != null);
/*    */         }
/*    */       };
/*    */     
/* 56 */     addElement(Text.create(this, 80, 48, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 59 */                 ItemStack pattern = ((TileEntityPatternStorage)container.base).pattern;
/*    */                 
/* 61 */                 return (pattern != null) ? pattern.func_82833_r() : null;
/*    */               }
/* 63 */             },  ), 16777215, false).withEnableHandler(patternInfoEnabler));
/* 64 */     addElement(Text.create(this, 80, 59, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 67 */                 return Util.toSiString(((TileEntityPatternStorage)container.base).patternUu, 4) + Localization.translate("ic2.generic.text.bucketUnit");
/*    */               }
/* 69 */             }), 16777215, false).withEnableHandler(patternInfoEnabler));
/* 70 */     addElement(Text.create(this, 80, 70, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 73 */                 return Util.toSiString(((TileEntityPatternStorage)container.base).patternEu, 4) + Localization.translate("ic2.generic.text.EU");
/*    */               }
/* 75 */             }), 16777215, false).withEnableHandler(patternInfoEnabler));
/* 76 */     addElement((GuiElement)new ItemImage(this, 152, 29, new Supplier<ItemStack>()
/*    */           {
/*    */             public ItemStack get() {
/* 79 */               return ((TileEntityPatternStorage)container.base).pattern;
/*    */             }
/*    */           }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 86 */     return background;
/*    */   }
/*    */   
/* 89 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIPatternStorage.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiPatternStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */