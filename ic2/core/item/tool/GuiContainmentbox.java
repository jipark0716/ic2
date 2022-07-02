/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiContainmentbox
/*    */   extends GuiIC2<ContainerContainmentbox>
/*    */ {
/*    */   public GuiContainmentbox(ContainerContainmentbox container) {
/* 15 */     super((ContainerBase)container);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 20 */     return new ResourceLocation("ic2", "textures/gui/GUIContainmentbox.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\GuiContainmentbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */