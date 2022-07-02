/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ 
/*     */ 
/*     */ public class IInventoryInvSlot
/*     */   implements IInventory
/*     */ {
/*     */   public final InvSlot slot;
/*     */   
/*     */   public IInventoryInvSlot(InvSlot slot) {
/*  17 */     this.slot = slot;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  22 */     return this.slot.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  27 */     return this.slot.getStackSizeLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  32 */     return this.slot.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int index, ItemStack stack) {
/*  37 */     return this.slot.accepts(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int index) {
/*  42 */     return this.slot.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int count) {
/*  47 */     ItemStack stack = func_70301_a(index);
/*     */     
/*  49 */     if (!StackUtil.isEmpty(stack)) {
/*  50 */       int amount = Math.min(StackUtil.getSize(stack), count);
/*     */       
/*  52 */       ItemStack out = StackUtil.copyWithSize(stack, amount);
/*  53 */       func_70299_a(index, StackUtil.decSize(stack, amount));
/*     */       
/*  55 */       return out;
/*     */     } 
/*  57 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int index, ItemStack stack) {
/*  63 */     this.slot.put(index, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/*  68 */     ItemStack stack = func_70301_a(index);
/*  69 */     func_70299_a(index, StackUtil.emptyStack);
/*     */     
/*  71 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/*  76 */     this.slot.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  81 */     this.slot.onChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 100 */     assert this.slot.base != null;
/* 101 */     return ((IWorldNameable)this.slot.base.getParent()).func_145818_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/* 106 */     assert this.slot.base != null;
/* 107 */     return ((IWorldNameable)this.slot.base.getParent()).func_70005_c_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/* 112 */     assert this.slot.base != null;
/* 113 */     return this.slot.base.getParent().func_145748_c_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174890_g() {
/* 119 */     assert this.slot.base != null;
/* 120 */     return ((IInventory)this.slot.base.getParent()).func_174890_g();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 125 */     assert this.slot.base != null;
/* 126 */     return ((IInventory)this.slot.base.getParent()).func_174887_a_(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {
/* 131 */     assert this.slot.base != null;
/* 132 */     ((IInventory)this.slot.base.getParent()).func_174885_b(id, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\IInventoryInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */