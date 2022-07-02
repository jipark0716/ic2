/*    */ package ic2.core.slot;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class SlotArmor extends Slot {
/*    */   public SlotArmor(InventoryPlayer inventory, EntityEquipmentSlot armorType, int x, int y) {
/* 15 */     super((IInventory)inventory, 36 + armorType.func_188454_b(), x, y);
/*    */     
/* 17 */     this.armorType = armorType;
/*    */   }
/*    */   private final EntityEquipmentSlot armorType;
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 22 */     Item item = stack.func_77973_b();
/* 23 */     if (item == null) return false;
/*    */     
/* 25 */     return item.isValidArmor(stack, this.armorType, (Entity)((InventoryPlayer)this.field_75224_c).field_70458_d);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public String func_178171_c() {
/* 31 */     return ItemArmor.field_94603_a[this.armorType.func_188454_b()];
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */