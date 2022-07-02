/*    */ package ic2.core.block.machine.gui;
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerSortingMachine;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.FixedSizeOverlaySupplier;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.IOverlaySupplier;
/*    */ import ic2.core.gui.Image;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiSortingMachine extends GuiIC2<ContainerSortingMachine> {
/*    */   public GuiSortingMachine(final ContainerSortingMachine container) {
/* 19 */     super((ContainerBase)container, 212, 243);
/*    */     
/* 21 */     addElement((GuiElement)EnergyGauge.asBolt(this, 174, 220, (TileEntityBlock)container.base));
/*    */     
/* 23 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 24 */       final EnumFacing cDir = dir;
/*    */       
/* 26 */       addElement((GuiElement)Image.create(this, 60, 18 + dir.ordinal() * 20, 18, 18, texture, 256, 256, (IOverlaySupplier)new FixedSizeOverlaySupplier(18)
/*    */             {
/*    */               public int getUS() {
/* 29 */                 return 212;
/*    */               }
/*    */ 
/*    */               
/*    */               public int getVS() {
/* 34 */                 if (StackUtil.getAdjacentInventory((TileEntity)container.base, cDir) != null) {
/* 35 */                   return 15;
/*    */                 }
/* 37 */                 return 33;
/*    */               }
/*    */             }));
/*    */       
/* 41 */       addElement((new CustomButton(this, 42, 18 + dir.ordinal() * 20, 18, 18, (IOverlaySupplier)new FixedSizeOverlaySupplier(18)
/*    */             {
/*    */               public int getUS() {
/* 44 */                 return 230;
/*    */               }
/*    */ 
/*    */               
/*    */               public int getVS() {
/* 49 */                 if (((TileEntitySortingMachine)container.base).defaultRoute != cDir) {
/* 50 */                   return 15;
/*    */                 }
/* 52 */                 return 33;
/*    */               }
/* 55 */             }texture, createEventSender(dir.ordinal())))
/* 56 */           .withTooltip(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 59 */                 if (((TileEntitySortingMachine)container.base).defaultRoute != cDir) {
/* 60 */                   return "ic2.SortingMachine.whitelist";
/*    */                 }
/* 62 */                 return "ic2.SortingMachine.default";
/*    */               }
/*    */             }));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 71 */     return texture;
/*    */   }
/*    */   
/* 74 */   private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/gui/GUISortingMachine.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiSortingMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */