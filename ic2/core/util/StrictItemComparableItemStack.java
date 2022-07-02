/*    */ package ic2.core.util;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class StrictItemComparableItemStack
/*    */ {
/*    */   private final Item item;
/*    */   private final int meta;
/*    */   private final NBTTagCompound nbt;
/*    */   private final int hashCode;
/*    */   
/*    */   public StrictItemComparableItemStack(ItemStack stack, boolean copyNbt) {
/* 15 */     this.item = stack.func_77973_b();
/* 16 */     this.meta = StackUtil.getRawMeta(stack);
/*    */     
/* 18 */     NBTTagCompound nbt = stack.func_77978_p();
/*    */     
/* 20 */     if (nbt != null) {
/* 21 */       if (nbt.func_82582_d()) {
/* 22 */         nbt = null;
/* 23 */       } else if (copyNbt) {
/* 24 */         nbt = nbt.func_74737_b();
/*    */       } 
/*    */     }
/*    */     
/* 28 */     this.nbt = nbt;
/* 29 */     this.hashCode = calculateHashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 34 */     if (!(obj instanceof StrictItemComparableItemStack)) return false;
/*    */     
/* 36 */     StrictItemComparableItemStack cmp = (StrictItemComparableItemStack)obj;
/* 37 */     if (cmp.hashCode != this.hashCode) return false; 
/* 38 */     if (cmp == this) return true;
/*    */     
/* 40 */     return (cmp.item == this.item && cmp.meta == this.meta && ((cmp.nbt == null && this.nbt == null) || (cmp.nbt != null && this.nbt != null && cmp.nbt
/*    */       
/* 42 */       .equals(this.nbt))));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     return this.hashCode;
/*    */   }
/*    */   
/*    */   private int calculateHashCode() {
/* 51 */     int ret = 0;
/* 52 */     if (this.item != null) ret = System.identityHashCode(this.item);
/*    */     
/* 54 */     ret = ret * 31 + this.meta;
/*    */     
/* 56 */     if (this.nbt != null) {
/* 57 */       ret = ret * 61 + this.nbt.hashCode();
/*    */     }
/*    */     
/* 60 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\StrictItemComparableItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */