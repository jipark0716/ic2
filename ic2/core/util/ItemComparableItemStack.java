/*    */ package ic2.core.util;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class ItemComparableItemStack
/*    */ {
/*    */   private final Item item;
/*    */   private final int meta;
/*    */   private final NBTTagCompound nbt;
/*    */   private final int hashCode;
/*    */   
/*    */   public ItemComparableItemStack(ItemStack stack, boolean copyNbt) {
/* 15 */     this.item = stack.func_77973_b();
/* 16 */     this.meta = stack.func_77981_g() ? stack.func_77960_j() : 0;
/*    */     
/* 18 */     NBTTagCompound nbt = stack.func_77978_p();
/*    */     
/* 20 */     if (nbt != null) {
/* 21 */       if (nbt.func_82582_d()) {
/* 22 */         nbt = null;
/*    */       } else {
/* 24 */         if (copyNbt) nbt = nbt.func_74737_b();
/*    */         
/* 26 */         boolean copied = copyNbt;
/*    */         
/* 28 */         for (String key : StackUtil.ignoredNbtKeys) {
/* 29 */           if (!copied && nbt.func_74764_b(key)) {
/* 30 */             nbt = nbt.func_74737_b();
/* 31 */             copied = true;
/*    */           } 
/*    */           
/* 34 */           nbt.func_82580_o(key);
/*    */         } 
/*    */         
/* 37 */         if (nbt.func_82582_d()) nbt = null;
/*    */       
/*    */       } 
/*    */     }
/* 41 */     this.nbt = nbt;
/* 42 */     this.hashCode = calculateHashCode();
/*    */   }
/*    */   
/*    */   private ItemComparableItemStack(ItemComparableItemStack src) {
/* 46 */     this.item = src.item;
/* 47 */     this.meta = src.meta;
/* 48 */     this.nbt = (src.nbt != null) ? src.nbt.func_74737_b() : null;
/* 49 */     this.hashCode = src.hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (!(obj instanceof ItemComparableItemStack)) return false;
/*    */     
/* 56 */     ItemComparableItemStack cmp = (ItemComparableItemStack)obj;
/* 57 */     if (cmp.hashCode != this.hashCode) return false; 
/* 58 */     if (cmp == this) return true;
/*    */     
/* 60 */     return (cmp.item == this.item && cmp.meta == this.meta && ((cmp.nbt == null && this.nbt == null) || (cmp.nbt != null && this.nbt != null && cmp.nbt
/*    */       
/* 62 */       .equals(this.nbt))));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 67 */     return this.hashCode;
/*    */   }
/*    */   
/*    */   private int calculateHashCode() {
/* 71 */     int ret = 0;
/* 72 */     if (this.item != null) ret = System.identityHashCode(this.item);
/*    */     
/* 74 */     ret = ret * 31 + this.meta;
/*    */     
/* 76 */     if (this.nbt != null) {
/* 77 */       ret = ret * 61 + this.nbt.hashCode();
/*    */     }
/*    */     
/* 80 */     return ret;
/*    */   }
/*    */   
/*    */   public ItemComparableItemStack copy() {
/* 84 */     if (this.nbt == null) return this;
/*    */     
/* 86 */     return new ItemComparableItemStack(this);
/*    */   }
/*    */   
/*    */   public ItemStack toStack() {
/* 90 */     return toStack(1);
/*    */   }
/*    */   
/*    */   public ItemStack toStack(int size) {
/* 94 */     if (this.item == null) return null;
/*    */     
/* 96 */     ItemStack ret = new ItemStack(this.item, size, this.meta);
/* 97 */     ret.func_77982_d(this.nbt);
/*    */     
/* 99 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\ItemComparableItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */