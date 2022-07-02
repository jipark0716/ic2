/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ElectricItemTooltipHandler
/*    */ {
/*    */   public ElectricItemTooltipHandler() {
/* 21 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void drawTooltips(ItemTooltipEvent event) {
/* 26 */     ItemStack stack = event.getItemStack();
/*    */     
/* 28 */     if (stack != null && ElectricItem.manager.getMaxCharge(stack) > 0.0D) {
/* 29 */       String tooltip = ElectricItem.manager.getToolTip(stack);
/*    */       
/* 31 */       if (tooltip != null && !tooltip.trim().isEmpty()) {
/* 32 */         event.getToolTip().add(tooltip);
/*    */         
/* 34 */         if (Keyboard.isKeyDown(42))
/* 35 */           event.getToolTip().add(Localization.translate("ic2.item.tooltip.PowerTier", new Object[] { Integer.valueOf(ElectricItem.manager.getTier(stack)) })); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ElectricItemTooltipHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */