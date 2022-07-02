/*    */ package ic2.core.block.kineticgenerator.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.kineticgenerator.container.ContainerSteamKineticGenerator;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ import ic2.core.gui.Image;
/*    */ import ic2.core.gui.SlotGrid;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiSteamKineticGenerator
/*    */   extends GuiIC2<ContainerSteamKineticGenerator> {
/*    */   public GuiSteamKineticGenerator(final ContainerSteamKineticGenerator container) {
/* 22 */     super((ContainerBase)container);
/*    */     
/* 24 */     addElement((GuiElement)TankGauge.createPlain(this, 75, 21, 26, 26, (IFluidTank)((TileEntitySteamKineticGenerator)container.base).getDistilledWaterTank()));
/* 25 */     addElement((new SlotGrid(this, 80, 26, SlotGrid.SlotStyle.Plain))
/* 26 */         .withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 29 */               if (!((TileEntitySteamKineticGenerator)container.base).hasTurbine()) {
/* 30 */                 return "ic2.SteamKineticGenerator.gui.turbineslot";
/*    */               }
/* 32 */               return null;
/*    */             }
/*    */           }));
/*    */     
/* 36 */     addElement(((Image)Image.create(this, 36, 20, 30, 26, TEXTURE, 256, 256, 176, 0, 206, 26)
/* 37 */         .withEnableHandler(new IEnableHandler()
/*    */           {
/*    */             public boolean isEnabled() {
/* 40 */               return (((TileEntitySteamKineticGenerator)container.base).hasTurbine() && ((TileEntitySteamKineticGenerator)container.base).isVentingSteam());
/*    */             }
/* 43 */           })).withTooltip("ic2.SteamKineticGenerator.gui.ventingWarning"));
/* 44 */     addElement(((Image)Image.create(this, 110, 20, 30, 26, TEXTURE, 256, 256, 176, 0, 206, 26)
/* 45 */         .withEnableHandler(new IEnableHandler()
/*    */           {
/*    */             public boolean isEnabled() {
/* 48 */               return (((TileEntitySteamKineticGenerator)container.base).hasTurbine() && ((TileEntitySteamKineticGenerator)container.base).isThrottled());
/*    */             }
/* 51 */           })).withTooltip("ic2.SteamKineticGenerator.gui.condensationwarrning"));
/* 52 */     addElement((GuiElement)Text.create(this, 8, 51, 160, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 55 */                 return Localization.translate(getRaw());
/*    */               }
/*    */               
/*    */               private String getRaw() {
/* 59 */                 if (!((TileEntitySteamKineticGenerator)container.base).hasTurbine())
/* 60 */                   return "ic2.SteamKineticGenerator.gui.error.noturbine"; 
/* 61 */                 if (((TileEntitySteamKineticGenerator)container.base).isTurbineBlockedByWater())
/* 62 */                   return "ic2.SteamKineticGenerator.gui.error.filledupwithwater"; 
/* 63 */                 if (((TileEntitySteamKineticGenerator)container.base).getActive()) {
/* 64 */                   return "ic2.SteamKineticGenerator.gui.aktive";
/*    */                 }
/* 66 */                 return "ic2.SteamKineticGenerator.gui.waiting";
/*    */               }
/*    */             },  ), new Supplier<Integer>()
/*    */           {
/*    */             public Integer get()
/*    */             {
/* 72 */               if (!((TileEntitySteamKineticGenerator)container.base).hasTurbine() || ((TileEntitySteamKineticGenerator)container.base).isTurbineBlockedByWater()) {
/* 73 */                 return Integer.valueOf(14946604);
/*    */               }
/* 75 */               return Integer.valueOf(2157374);
/*    */             }
/*    */           },  false, 4, 0, false, true));
/*    */     
/* 79 */     addElement(Text.create(this, 8, 68, 160, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 82 */                 return Localization.translate("ic2.SteamKineticGenerator.gui.turbine.ouput", new Object[] { Integer.valueOf(((TileEntitySteamKineticGenerator)this.val$container.base).getKUoutput())
/*    */                     });
/*    */               }
/* 85 */             }), 2157374, false, 4, 0, false, true).withEnableHandler(new IEnableHandler()
/*    */           {
/*    */             public boolean isEnabled() {
/* 88 */               return (((TileEntitySteamKineticGenerator)container.base).hasTurbine() && !((TileEntitySteamKineticGenerator)container.base).isTurbineBlockedByWater());
/*    */             }
/*    */           }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 95 */     return TEXTURE;
/*    */   }
/*    */   
/* 98 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUISteamKineticGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\gui\GuiSteamKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */