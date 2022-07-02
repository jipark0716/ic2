/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.profile.NotClassic;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityChargepadBatBox
/*    */   extends TileEntityChargepadBlock
/*    */ {
/*    */   public TileEntityChargepadBatBox() {
/* 12 */     super(1, 32, 40000);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void getItems(EntityPlayer player) {
/* 17 */     if (player != null) {
/* 18 */       for (ItemStack current : player.field_71071_by.field_70460_b) {
/* 19 */         if (current == null)
/* 20 */           continue;  chargeItem(current, 32);
/*    */       } 
/*    */       
/* 23 */       for (ItemStack current : player.field_71071_by.field_70462_a) {
/* 24 */         if (current == null)
/* 25 */           continue;  chargeItem(current, 32);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityChargepadBatBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */