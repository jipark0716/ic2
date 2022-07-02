/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiWeightedItemDistributor
/*    */   extends GuiWeightedDistributor<ContainerWeightedItemDistributor>
/*    */ {
/*    */   public GuiWeightedItemDistributor(ContainerWeightedItemDistributor container) {
/* 10 */     super(container, 211);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 15 */     return TEXTURE;
/*    */   }
/*    */   
/* 18 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIWeightedItemDistributor.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiWeightedItemDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */