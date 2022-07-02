/*     */ package ic2.core.util;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ public class DelegatingInventory implements IInventory {
/*     */   public DelegatingInventory(IInventory parent) {
/*  10 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/*  15 */     return this.parent.func_70005_c_();
/*     */   }
/*     */   private final IInventory parent;
/*     */   
/*     */   public boolean func_145818_k_() {
/*  20 */     return this.parent.func_145818_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/*  25 */     return this.parent.func_145748_c_();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  30 */     return this.parent.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  35 */     return this.parent.func_191420_l();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int index) {
/*  40 */     return this.parent.func_70301_a(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int count) {
/*  45 */     return this.parent.func_70298_a(index, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/*  50 */     return this.parent.func_70304_b(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int index, ItemStack stack) {
/*  55 */     this.parent.func_70299_a(index, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  60 */     return this.parent.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  65 */     this.parent.func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/*  70 */     return this.parent.func_70300_a(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {
/*  75 */     this.parent.func_174889_b(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {
/*  80 */     this.parent.func_174886_c(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int index, ItemStack stack) {
/*  85 */     return this.parent.func_94041_b(index, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/*  90 */     return this.parent.func_174887_a_(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {
/*  95 */     this.parent.func_174885_b(id, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_174890_g() {
/* 100 */     return this.parent.func_174890_g();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/* 105 */     this.parent.func_174888_l();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\DelegatingInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */