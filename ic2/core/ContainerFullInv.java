/*    */ package ic2.core;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContainerFullInv<T extends IInventory>
/*    */   extends ContainerBase<T>
/*    */ {
/*    */   public ContainerFullInv(EntityPlayer player, T base, int height) {
/* 13 */     super(base);
/*    */     
/* 15 */     addPlayerInventorySlots(player, height);
/*    */   }
/*    */   
/*    */   public ContainerFullInv(EntityPlayer player, T base, int width, int height) {
/* 19 */     super(base);
/*    */     
/* 21 */     addPlayerInventorySlots(player, width, height);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ContainerFullInv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */