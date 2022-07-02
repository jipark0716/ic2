/*     */ package ic2.core.block.invslot;
/*     */ 
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class InvSlotOutput
/*     */   extends InvSlot
/*     */ {
/*     */   public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count) {
/*  13 */     this(base1, name1, count, InvSlot.InvSide.BOTTOM);
/*     */   }
/*     */   
/*     */   public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count, InvSlot.InvSide side) {
/*  17 */     super(base1, name1, InvSlot.Access.O, count, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean accepts(ItemStack stack) {
/*  22 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(Collection<ItemStack> stacks) {
/*  32 */     return add(stacks, false);
/*     */   }
/*     */   
/*     */   public int add(ItemStack stack) {
/*  36 */     if (stack == null) throw new NullPointerException("null ItemStack");
/*     */     
/*  38 */     return add(Collections.singletonList(stack), false);
/*     */   }
/*     */   
/*     */   public boolean canAdd(Collection<ItemStack> stacks) {
/*  42 */     return (add(stacks, true) == 0);
/*     */   }
/*     */   
/*     */   public boolean canAdd(ItemStack stack) {
/*  46 */     if (stack == null) throw new NullPointerException("null ItemStack");
/*     */     
/*  48 */     return (add(Collections.singletonList(stack), true) == 0);
/*     */   }
/*     */   
/*     */   private int add(Collection<ItemStack> stacks, boolean simulate) {
/*  52 */     if (stacks == null || stacks.isEmpty()) return 0;
/*     */     
/*  54 */     ItemStack[] backup = simulate ? backup() : null;
/*     */     
/*  56 */     int totalAmount = 0;
/*     */     
/*  58 */     for (ItemStack stack : stacks) {
/*  59 */       int amount = StackUtil.getSize(stack);
/*  60 */       if (amount <= 0)
/*     */         continue;  int pass;
/*  62 */       label47: for (pass = 0; pass < 2; pass++) {
/*  63 */         for (int i = 0; i < size(); i++) {
/*  64 */           ItemStack existingStack = get(i);
/*  65 */           int space = getStackSizeLimit();
/*     */           
/*  67 */           if (!StackUtil.isEmpty(existingStack)) {
/*  68 */             space = Math.min(space, existingStack.func_77976_d()) - StackUtil.getSize(existingStack);
/*     */           }
/*     */           
/*  71 */           if (space > 0)
/*     */           {
/*  73 */             if (pass == 0 && !StackUtil.isEmpty(existingStack) && StackUtil.checkItemEqualityStrict(stack, existingStack)) {
/*  74 */               if (space >= amount) {
/*  75 */                 put(i, StackUtil.incSize(existingStack, amount));
/*  76 */                 amount = 0;
/*     */                 break label47;
/*     */               } 
/*  79 */               put(i, StackUtil.incSize(existingStack, space));
/*  80 */               amount -= space;
/*     */             }
/*  82 */             else if (pass == 1 && StackUtil.isEmpty(existingStack)) {
/*  83 */               if (space >= amount) {
/*  84 */                 put(i, StackUtil.copyWithSize(stack, amount));
/*  85 */                 amount = 0;
/*     */                 break label47;
/*     */               } 
/*  88 */               put(i, StackUtil.copyWithSize(stack, space));
/*  89 */               amount -= space;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  95 */       totalAmount += amount;
/*     */     } 
/*     */     
/*  98 */     if (simulate) restore(backup);
/*     */     
/* 100 */     return totalAmount;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */