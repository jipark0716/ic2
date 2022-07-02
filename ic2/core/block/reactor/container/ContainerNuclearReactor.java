/*    */ package ic2.core.block.reactor.container;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerNuclearReactor
/*    */   extends ContainerBase<TileEntityNuclearReactorElectric>
/*    */ {
/*    */   private final int size;
/*    */   
/*    */   public ContainerNuclearReactor(EntityPlayer player, TileEntityNuclearReactorElectric te) {
/* 18 */     super((IInventory)te);
/*    */     
/* 20 */     this.size = te.getReactorSize();
/*    */     
/* 22 */     int startX = 26;
/* 23 */     int startY = 25;
/*    */     
/* 25 */     int slotCount = te.reactorSlot.size();
/*    */     
/* 27 */     for (int i = 0; i < slotCount; i++) {
/* 28 */       int x = i % this.size;
/* 29 */       int y = i / this.size;
/*    */       
/* 31 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)te.reactorSlot, i, startX + 18 * x, startY + 18 * y));
/*    */     } 
/*    */     
/* 34 */     addPlayerInventorySlots(player, 214, 243);
/*    */     
/* 36 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.coolantinputSlot, 0, 8, 25));
/* 37 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.hotcoolinputSlot, 0, 188, 25));
/* 38 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.coolantoutputSlot, 0, 8, 115));
/* 39 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.hotcoolantoutputSlot, 0, 188, 115));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 44 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 46 */     ret.add("heat");
/* 47 */     ret.add("maxHeat");
/* 48 */     ret.add("EmitHeat");
/* 49 */     ret.add("inputTank");
/* 50 */     ret.add("outputTank");
/* 51 */     ret.add("fluidCooled");
/*    */     
/* 53 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\container\ContainerNuclearReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */