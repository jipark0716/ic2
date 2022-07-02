/*     */ package ic2.core.block.invslot;
/*     */ 
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.item.DamageHandler;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public abstract class InvSlotConsumable
/*     */   extends InvSlot {
/*     */   public InvSlotConsumable(IInventorySlotHolder<?> base, String name, int count) {
/*  13 */     super(base, name, InvSlot.Access.I, count, InvSlot.InvSide.TOP);
/*     */   }
/*     */   
/*     */   public InvSlotConsumable(IInventorySlotHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide preferredSide) {
/*  17 */     super(base, name, access, count, preferredSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean accepts(ItemStack paramItemStack);
/*     */ 
/*     */   
/*     */   public boolean canOutput() {
/*  25 */     return (super.canOutput() || (this.access != InvSlot.Access.NONE && 
/*  26 */       !isEmpty() && !accepts(get())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack consume(int amount) {
/*  39 */     return consume(amount, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack consume(int amount, boolean simulate, boolean consumeContainers) {
/*  54 */     ItemStack ret = null;
/*     */     
/*  56 */     for (int i = 0; i < size(); i++) {
/*  57 */       ItemStack stack = get(i);
/*     */       
/*  59 */       if (StackUtil.getSize(stack) >= 1 && 
/*  60 */         accepts(stack) && (ret == null || 
/*  61 */         StackUtil.checkItemEqualityStrict(stack, ret)) && (
/*  62 */         StackUtil.getSize(stack) == 1 || consumeContainers || !stack.func_77973_b().hasContainerItem(stack))) {
/*  63 */         int currentAmount = Math.min(amount, StackUtil.getSize(stack));
/*     */         
/*  65 */         amount -= currentAmount;
/*     */         
/*  67 */         if (!simulate) {
/*  68 */           if (StackUtil.getSize(stack) == currentAmount) {
/*  69 */             if (!consumeContainers && stack.func_77973_b().hasContainerItem(stack)) {
/*  70 */               ItemStack container = stack.func_77973_b().getContainerItem(stack);
/*     */               
/*  72 */               if (container != null && container.func_77984_f() && DamageHandler.getDamage(container) > DamageHandler.getMaxDamage(container)) {
/*  73 */                 container = null;
/*     */               }
/*     */               
/*  76 */               put(i, container);
/*     */             } else {
/*  78 */               clear(i);
/*     */             } 
/*     */           } else {
/*  81 */             put(i, StackUtil.decSize(stack, currentAmount));
/*     */           } 
/*     */         }
/*     */         
/*  85 */         if (ret == null) {
/*  86 */           ret = StackUtil.copyWithSize(stack, currentAmount);
/*     */         } else {
/*  88 */           ret = StackUtil.incSize(ret, currentAmount);
/*     */         } 
/*     */         
/*  91 */         if (amount == 0)
/*     */           break; 
/*     */       } 
/*     */     } 
/*  95 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damage(int amount, boolean simulate) {
/* 108 */     return damage(amount, simulate, (EntityLivingBase)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damage(int amount, boolean simulate, EntityLivingBase src) {
/* 122 */     int damageApplied = 0;
/* 123 */     ItemStack target = null;
/*     */     
/* 125 */     for (int i = 0; i < size() && amount > 0; i++) {
/* 126 */       ItemStack stack = get(i);
/* 127 */       if (!StackUtil.isEmpty(stack)) {
/*     */         
/* 129 */         Item item = stack.func_77973_b();
/*     */         
/* 131 */         if (accepts(stack) && item.func_77645_m() && (target == null || (item == target
/* 132 */           .func_77973_b() && ItemStack.func_77970_a(stack, target)))) {
/* 133 */           if (target == null) target = stack.func_77946_l(); 
/* 134 */           if (simulate) stack = stack.func_77946_l();
/*     */           
/* 136 */           int maxDamage = DamageHandler.getMaxDamage(stack);
/*     */           
/*     */           do {
/* 139 */             int currentAmount = Math.min(amount, maxDamage - DamageHandler.getDamage(stack));
/*     */             
/* 141 */             DamageHandler.damage(stack, currentAmount, src);
/*     */             
/* 143 */             damageApplied += currentAmount;
/* 144 */             amount -= currentAmount;
/*     */ 
/*     */             
/* 147 */             if (DamageHandler.getDamage(stack) < maxDamage)
/* 148 */               continue;  stack = StackUtil.decSize(stack);
/* 149 */             if (!StackUtil.isEmpty(stack)) {
/* 150 */               DamageHandler.setDamage(stack, 0, false);
/*     */             } else {
/*     */               
/*     */               break;
/*     */             } 
/* 155 */           } while (amount > 0 && !StackUtil.isEmpty(stack));
/*     */           
/* 157 */           if (!simulate) {
/* 158 */             put(i, stack);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 163 */     return damageApplied;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */