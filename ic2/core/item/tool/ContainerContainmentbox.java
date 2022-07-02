/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.item.ContainerHandHeldInventory;
/*    */ import ic2.core.slot.SlotRadioactive;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerContainmentbox extends ContainerHandHeldInventory<HandHeldContainmentbox> {
/*    */   public ContainerContainmentbox(EntityPlayer player, HandHeldContainmentbox box) {
/* 11 */     super(box);
/*    */     int i;
/* 13 */     for (i = 0; i < 4; i++) {
/* 14 */       func_75146_a((Slot)new SlotRadioactive((IInventory)box, i, 53 + i * 18, 19));
/*    */     }
/*    */     
/* 17 */     for (i = 4; i < 8; i++) {
/* 18 */       func_75146_a((Slot)new SlotRadioactive((IInventory)box, i, 53 + (i - 4) * 18, 37));
/*    */     }
/*    */     
/* 21 */     for (i = 8; i < 12; i++) {
/* 22 */       func_75146_a((Slot)new SlotRadioactive((IInventory)box, i, 53 + (i - 8) * 18, 55));
/*    */     }
/*    */     
/* 25 */     addPlayerInventorySlots(player, 166);
/*    */   }
/*    */   
/*    */   protected static final int height = 166;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ContainerContainmentbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */