/*     */ package ic2.core.uu;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ class LeanItemStack {
/*     */   private final Item item;
/*     */   private final int meta;
/*     */   
/*     */   public LeanItemStack(ItemStack stack) {
/*  11 */     this(stack.func_77973_b(), 
/*  12 */         StackUtil.getRawMeta(stack), stack
/*  13 */         .func_77978_p(), 
/*  14 */         StackUtil.getSize(stack));
/*     */   }
/*     */   private final NBTTagCompound nbt; private final int size; private int hashCode;
/*     */   public LeanItemStack(ItemStack stack, int size) {
/*  18 */     this(stack.func_77973_b(), 
/*  19 */         StackUtil.getRawMeta(stack), stack
/*  20 */         .func_77978_p(), size);
/*     */   }
/*     */ 
/*     */   
/*     */   public LeanItemStack(Item item, int meta, NBTTagCompound nbt, int size) {
/*  25 */     if (item == null) throw new NullPointerException("null item");
/*     */     
/*  27 */     this.item = item;
/*  28 */     this.meta = meta;
/*  29 */     this.nbt = nbt;
/*  30 */     this.size = size;
/*     */   }
/*     */   
/*     */   public Item getItem() {
/*  34 */     return this.item;
/*     */   }
/*     */   
/*     */   public int getMeta() {
/*  38 */     return this.meta;
/*     */   }
/*     */   
/*     */   public NBTTagCompound getNbt() {
/*  42 */     return this.nbt;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  46 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  51 */     return String.format("%dx%s@%d", new Object[] { Integer.valueOf(this.size), this.item.getRegistryName(), Integer.valueOf(this.meta) });
/*     */   }
/*     */   
/*     */   public boolean hasSameItem(LeanItemStack o) {
/*  55 */     return (this.item == o.item && (this.meta == o.meta || 
/*  56 */       !this.item.func_77614_k()) && 
/*  57 */       StackUtil.checkNbtEquality(this.nbt, o.nbt));
/*     */   }
/*     */   
/*     */   public LeanItemStack copy() {
/*  61 */     return copyWithSize(this.size);
/*     */   }
/*     */   
/*     */   public LeanItemStack copyWithSize(int newSize) {
/*  65 */     LeanItemStack ret = new LeanItemStack(this.item, this.meta, this.nbt, newSize);
/*  66 */     ret.hashCode = this.hashCode;
/*     */     
/*  68 */     return ret;
/*     */   }
/*     */   
/*     */   public ItemStack toMcStack() {
/*  72 */     if (this.size <= 0) return StackUtil.emptyStack;
/*     */     
/*  74 */     ItemStack ret = new ItemStack(this.item, this.size, this.meta);
/*  75 */     ret.func_77982_d(this.nbt);
/*     */     
/*  77 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  82 */     if (!(obj instanceof LeanItemStack)) return false;
/*     */     
/*  84 */     LeanItemStack o = (LeanItemStack)obj;
/*     */     
/*  86 */     return (this.item == o.item && this.meta == o.meta && ((this.nbt == null && o.nbt == null) || (this.nbt != null && o.nbt != null && this.nbt
/*  87 */       .equals(o.nbt))));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     if (this.hashCode == 0) this.hashCode = calculateHashCode();
/*     */     
/*  94 */     return this.hashCode;
/*     */   }
/*     */   
/*     */   private int calculateHashCode() {
/*  98 */     int ret = System.identityHashCode(this.item);
/*     */     
/* 100 */     ret = ret * 31 + this.meta;
/*     */     
/* 102 */     if (this.nbt != null) {
/* 103 */       ret = ret * 61 + this.nbt.hashCode();
/*     */     }
/*     */     
/* 106 */     if (ret == 0) ret = -1;
/*     */     
/* 108 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\LeanItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */