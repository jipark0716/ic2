/*     */ package ic2.core.util;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public abstract class InventorySlotCrafting extends InventoryCrafting {
/*     */   protected final int width;
/*     */   
/*     */   public InventorySlotCrafting(int width, int height) {
/*   8 */     super(null, 0, 0);
/*     */     
/*  10 */     this.width = width;
/*  11 */     this.height = height;
/*  12 */     this.size = width * height;
/*     */   }
/*     */   protected final int height; protected final int size;
/*     */   protected boolean validIndex(int index) {
/*  16 */     return (index >= 0 && index < this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  21 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70463_b(int row, int column) {
/*  26 */     return (row >= 0 && row < this.height && column >= 0 && column < this.width) ? func_70301_a(row + column * this.height) : StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ItemStack get(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void put(int paramInt, ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clear(int index) {
/*  47 */     put(index, StackUtil.emptyStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int index) {
/*  52 */     return !validIndex(index) ? StackUtil.emptyStack : get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/*  57 */     if (validIndex(index)) {
/*  58 */       ItemStack stack = get(index);
/*     */       
/*  60 */       clear(index);
/*     */       
/*  62 */       return stack;
/*     */     } 
/*  64 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int count) {
/*     */     ItemStack stack;
/*  72 */     if (validIndex(index) && !StackUtil.isEmpty(stack = get(index))) {
/*     */       ItemStack ret;
/*     */       
/*  75 */       if (count >= StackUtil.getSize(stack)) {
/*  76 */         ret = stack;
/*  77 */         clear(index);
/*     */       } else {
/*  79 */         ret = StackUtil.copyWithSize(stack, count);
/*  80 */         put(index, StackUtil.decSize(stack, count));
/*     */       } 
/*     */       
/*  83 */       return ret;
/*     */     } 
/*  85 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int index, ItemStack stack) {
/*  91 */     if (validIndex(index)) {
/*  92 */       put(index, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void func_174888_l();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean func_191420_l();
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174922_i() {
/* 108 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174923_h() {
/* 114 */     return this.height;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\InventorySlotCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */