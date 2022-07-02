/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.machine.container.ContainerItemBuffer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiItemBuffer
/*    */   extends GuiIC2<ContainerItemBuffer> {
/*    */   public GuiItemBuffer(ContainerItemBuffer container) {
/* 11 */     super((ContainerBase)container);
/*    */     
/* 13 */     this.field_147000_g = 232;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 18 */     return new ResourceLocation("ic2", "textures/gui/GUIItemBuffer.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiItemBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */