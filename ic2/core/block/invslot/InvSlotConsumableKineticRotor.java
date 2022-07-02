/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.item.IKineticRotor;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ public class InvSlotConsumableKineticRotor
/*    */   extends InvSlotConsumableClass
/*    */ {
/*    */   private final String updateName;
/*    */   private final IKineticRotor.GearboxType type;
/*    */   
/*    */   public InvSlotConsumableKineticRotor(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, IKineticRotor.GearboxType type) {
/* 18 */     this(base1, name1, access1, count, preferredSide1, type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvSlotConsumableKineticRotor(IInventorySlotHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide preferredSide, IKineticRotor.GearboxType type, String field) {
/* 24 */     super(base, name, access, count, preferredSide, IKineticRotor.class);
/*    */     
/* 26 */     this.type = type;
/* 27 */     this.updateName = field;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 32 */     if (super.accepts(stack)) {
/* 33 */       return ((IKineticRotor)stack.func_77973_b()).isAcceptedType(stack, this.type);
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onChanged() {
/* 40 */     if (this.updateName != null && this.base.getParent().func_145830_o() && !(this.base.getParent().func_145831_w()).field_72995_K)
/*    */     {
/* 42 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this.base.getParent(), this.updateName);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableKineticRotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */