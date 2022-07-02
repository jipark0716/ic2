/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.item.ContainerHandHeldInventory;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCropnalyzer extends ContainerHandHeldInventory<HandHeldCropnalyzer> {
/*    */   public ContainerCropnalyzer(EntityPlayer player, HandHeldCropnalyzer cropnalyzer1) {
/* 13 */     super(cropnalyzer1);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotCustom((IInventory)cropnalyzer1, ItemName.crop_seed_bag.getInstance(), 0, 8, 7));
/* 16 */     func_75146_a((Slot)new SlotCustom((IInventory)cropnalyzer1, null, 1, 41, 7));
/* 17 */     func_75146_a((Slot)new SlotDischarge((IInventory)cropnalyzer1, 2, 152, 7));
/*    */     
/* 19 */     addPlayerInventorySlots(player, 223);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ContainerCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */