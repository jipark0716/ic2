/*    */ package ic2.core.block.kineticgenerator.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ import ic2.core.gui.Image;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiWindKineticGenerator
/*    */   extends GuiIC2<ContainerWindKineticGenerator> {
/*    */   public GuiWindKineticGenerator(final ContainerWindKineticGenerator container) {
/* 19 */     super((ContainerBase)container);
/*    */     
/* 21 */     addElement((GuiElement)Text.create(this, 17, 48, 143, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 24 */                 if (!((TileEntityWindKineticGenerator)container.base).hasRotor())
/* 25 */                   return Localization.translate("ic2.WindKineticGenerator.gui.rotormiss"); 
/* 26 */                 if (!((TileEntityWindKineticGenerator)container.base).rotorHasSpace())
/* 27 */                   return Localization.translate("ic2.WindKineticGenerator.gui.rotorspace"); 
/* 28 */                 if (!((TileEntityWindKineticGenerator)container.base).isWindStrongEnough()) {
/* 29 */                   return Localization.translate("ic2.WindKineticGenerator.gui.windweak1");
/*    */                 }
/* 31 */                 return Localization.translate("ic2.WindKineticGenerator.gui.output", new Object[] { Integer.valueOf(((TileEntityWindKineticGenerator)this.val$container.base).getKuOutput()) });
/*    */               }
/*    */             }), 2157374, false, 4, 0, false, true));
/*    */     
/* 35 */     addElement((GuiElement)Text.create(this, 17, 66, 143, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 38 */                 if (!((TileEntityWindKineticGenerator)container.base).hasRotor() || !((TileEntityWindKineticGenerator)container.base).rotorHasSpace())
/* 39 */                   return null; 
/* 40 */                 if (!((TileEntityWindKineticGenerator)container.base).isWindStrongEnough()) {
/* 41 */                   return Localization.translate("ic2.WindKineticGenerator.gui.windweak2");
/*    */                 }
/* 43 */                 return ((TileEntityWindKineticGenerator)container.base).getRotorHealth() + " %";
/*    */               }
/*    */             },  ), 2157374, false, 4, 0, false, true));
/*    */     
/* 47 */     IEnableHandler warningEnabler = new IEnableHandler()
/*    */       {
/*    */         public boolean isEnabled() {
/* 50 */           return ((TileEntityWindKineticGenerator)container.base).isRotorOverloaded();
/*    */         }
/*    */       };
/* 53 */     addElement(((Image)Image.create(this, 44, 20, 30, 26, background, 256, 256, 176, 0, 206, 26)
/* 54 */         .withEnableHandler(warningEnabler))
/* 55 */         .withTooltip("ic2.WindKineticGenerator.error.overload"));
/* 56 */     addElement(((Image)Image.create(this, 102, 20, 30, 26, background, 256, 256, 176, 0, 206, 26)
/* 57 */         .withEnableHandler(warningEnabler))
/* 58 */         .withTooltip("ic2.WindKineticGenerator.error.overload"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 63 */     return background;
/*    */   }
/*    */   
/* 66 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIWindKineticGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\gui\GuiWindKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */