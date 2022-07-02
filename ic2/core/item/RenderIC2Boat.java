/*    */ package ic2.core.item;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RenderBoat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderIC2Boat
/*    */   extends RenderBoat
/*    */ {
/*    */   public RenderIC2Boat(RenderManager manager) {
/* 16 */     super(manager);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(EntityBoat entity) {
/* 21 */     return new ResourceLocation("ic2", ((EntityIC2Boat)entity).getTexture());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\RenderIC2Boat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */