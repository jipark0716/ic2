/*     */ package ic2.core.slot;
/*     */ 
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class SlotHologramSlot
/*     */   extends Slot {
/*     */   public SlotHologramSlot(ItemStack[] stacks, int index, int x, int y, int stackSizeLimit, ChangeCallback changeCallback) {
/*  14 */     super(null, 0, x, y);
/*     */     
/*  16 */     if (index >= stacks.length) throw new ArrayIndexOutOfBoundsException(index);
/*     */     
/*  18 */     this.stacks = stacks;
/*  19 */     this.index = index;
/*  20 */     this.stackSizeLimit = stackSizeLimit;
/*  21 */     this.changeCallback = changeCallback;
/*     */   }
/*     */   
/*     */   protected final ItemStack[] stacks;
/*     */   protected final int index;
/*     */   protected final int stackSizeLimit;
/*     */   protected final ChangeCallback changeCallback;
/*     */   
/*     */   public boolean func_82869_a(EntityPlayer player) {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_75219_a() {
/*  35 */     return this.stackSizeLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75214_a(ItemStack stack) {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_75211_c() {
/*  49 */     return StackUtil.wrapEmpty(this.stacks[this.index]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75215_d(ItemStack stack) {
/*  54 */     this.stacks[this.index] = stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75218_e() {
/*  59 */     if (Util.inDev()) System.out.println(StackUtil.toStringSafe(this.stacks)); 
/*  60 */     if (this.changeCallback != null) this.changeCallback.onChanged(this.index);
/*     */   
/*     */   }
/*     */   
/*     */   public ItemStack func_75209_a(int amount) {
/*  65 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75217_a(IInventory inventory, int index) {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack slotClick(int dragType, ClickType clickType, EntityPlayer player) {
/*  78 */     if (Util.inDev() && (player.func_130014_f_()).field_72995_K) System.out.printf("dragType=%d clickType=%s stack=%s%n", new Object[] { Integer.valueOf(dragType), clickType, player.field_71071_by.func_70445_o() });
/*     */     
/*  80 */     if (clickType == ClickType.PICKUP && (dragType == 0 || dragType == 1)) {
/*  81 */       ItemStack playerStack = player.field_71071_by.func_70445_o();
/*  82 */       ItemStack slotStack = this.stacks[this.index];
/*     */ 
/*     */ 
/*     */       
/*  86 */       if (!StackUtil.isEmpty(playerStack)) {
/*  87 */         int curSize = StackUtil.getSize(slotStack);
/*  88 */         int extraSize = (dragType == 0) ? StackUtil.getSize(playerStack) : 1;
/*  89 */         int limit = Math.min(playerStack.func_77976_d(), this.stackSizeLimit);
/*     */         
/*  91 */         if (curSize + extraSize > limit) extraSize = Math.max(0, limit - curSize);
/*     */         
/*  93 */         if (curSize == 0) {
/*  94 */           this.stacks[this.index] = StackUtil.copyWithSize(playerStack, extraSize);
/*  95 */         } else if (StackUtil.checkItemEquality(playerStack, slotStack)) {
/*  96 */           if (Util.inDev()) System.out.println("add " + extraSize + " to " + slotStack + " -> " + (curSize + extraSize)); 
/*  97 */           this.stacks[this.index] = StackUtil.incSize(slotStack, extraSize);
/*     */         } else {
/*  99 */           this.stacks[this.index] = StackUtil.copyWithSize(playerStack, Math.min(StackUtil.getSize(playerStack), limit));
/*     */         } 
/* 101 */       } else if (!StackUtil.isEmpty(slotStack)) {
/* 102 */         if (dragType == 0) {
/* 103 */           this.stacks[this.index] = StackUtil.emptyStack;
/*     */         } else {
/* 105 */           int newSize = StackUtil.getSize(slotStack) / 2;
/*     */           
/* 107 */           if (newSize <= 0) {
/* 108 */             this.stacks[this.index] = StackUtil.emptyStack;
/*     */           } else {
/* 110 */             this.stacks[this.index] = StackUtil.setSize(slotStack, newSize);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 115 */       func_75218_e();
/*     */     } 
/*     */     
/* 118 */     return StackUtil.emptyStack;
/*     */   }
/*     */   
/*     */   public static interface ChangeCallback {
/*     */     void onChanged(int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotHologramSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */