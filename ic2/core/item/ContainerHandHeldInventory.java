/*    */ package ic2.core.item;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.item.tool.HandHeldInventory;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ 
/*    */ public class ContainerHandHeldInventory<T extends HandHeldInventory> extends ContainerBase<T> {
/*    */   public ContainerHandHeldInventory(T inventory) {
/* 15 */     super((IInventory)inventory);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_184996_a(int slot, int button, ClickType type, EntityPlayer player) {
/* 20 */     boolean swapOut, swapTo, closeGUI = false;
/*    */     
/* 22 */     switch (type) {
/*    */       case CLONE:
/*    */         break;
/*    */       
/*    */       case PICKUP:
/* 27 */         if (slot >= 0 && slot < this.field_75151_b.size()) {
/* 28 */           closeGUI = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
/*    */         }
/*    */         break;
/*    */ 
/*    */       
/*    */       case PICKUP_ALL:
/*    */       case QUICK_CRAFT:
/*    */         break;
/*    */ 
/*    */       
/*    */       case QUICK_MOVE:
/* 39 */         if (slot >= 0 && slot < this.field_75151_b.size() && ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c())) {
/* 40 */           return StackUtil.emptyStack;
/*    */         }
/*    */         break;
/*    */       
/*    */       case SWAP:
/* 45 */         assert slot >= 0 && slot < this.field_75151_b.size();
/* 46 */         assert func_75147_a((IInventory)player.field_71071_by, button) != null;
/*    */         
/* 48 */         swapOut = ((HandHeldInventory)this.base).isThisContainer(func_75147_a((IInventory)player.field_71071_by, button).func_75211_c());
/* 49 */         swapTo = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
/*    */         
/* 51 */         if (swapOut || swapTo) {
/* 52 */           for (int i = 0; i < 9; i++) {
/* 53 */             if ((swapOut && slot == (func_75147_a((IInventory)player.field_71071_by, i)).field_75222_d) || (swapTo && button == i)) {
/* 54 */               if (player instanceof EntityPlayerMP) ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)new SPacketHeldItemChange(i));
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         }
/*    */         break;
/*    */       case THROW:
/* 62 */         if (slot >= 0 && slot < this.field_75151_b.size()) {
/* 63 */           closeGUI = ((HandHeldInventory)this.base).isThisContainer(((Slot)this.field_75151_b.get(slot)).func_75211_c());
/*    */         }
/*    */         break;
/*    */       
/*    */       default:
/* 68 */         throw new RuntimeException("Unexpected ClickType: " + type);
/*    */     } 
/*    */     
/* 71 */     ItemStack stack = super.func_184996_a(slot, button, type, player);
/*    */     
/* 73 */     if (closeGUI && !(player.func_130014_f_()).field_72995_K) {
/* 74 */       ((HandHeldInventory)this.base).saveAsThrown(stack);
/* 75 */       player.func_71053_j();
/* 76 */     } else if (type == ClickType.CLONE) {
/* 77 */       ItemStack held = player.field_71071_by.func_70445_o();
/*    */       
/* 79 */       if (((HandHeldInventory)this.base).isThisContainer(held)) {
/* 80 */         held.func_77978_p().func_82580_o("uid");
/*    */       }
/*    */     } 
/*    */     
/* 84 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer player) {
/* 89 */     ((HandHeldInventory)this.base).onGuiClosed(player);
/*    */     
/* 91 */     super.func_75134_a(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ContainerHandHeldInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */