/*    */ package ic2.core;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraftforge.event.entity.player.FillBucketEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IC2BucketHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void onBucketFill(FillBucketEvent event) {
/* 18 */     if (event.getTarget() != null) {
/* 19 */       Block block = event.getWorld().func_180495_p(event.getTarget().func_178782_a()).func_177230_c();
/*    */       
/* 21 */       if (block instanceof ic2.core.block.BlockIC2Fluid && event.isCancelable())
/* 22 */         event.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\IC2BucketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */