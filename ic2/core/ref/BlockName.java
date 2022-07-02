/*     */ package ic2.core.ref;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public enum BlockName
/*     */ {
/*  12 */   te,
/*  13 */   resource,
/*  14 */   leaves,
/*  15 */   rubber_wood,
/*  16 */   sapling,
/*  17 */   scaffold,
/*  18 */   foam,
/*  19 */   fence,
/*  20 */   sheet,
/*  21 */   glass,
/*  22 */   wall,
/*  23 */   mining_pipe,
/*  24 */   reinforced_door,
/*  25 */   dynamite,
/*  26 */   refractory_bricks;
/*     */   private Block instance;
/*     */   
/*     */   public boolean hasInstance() {
/*  30 */     return (this.instance != null);
/*     */   }
/*     */   public static final BlockName[] values;
/*     */   
/*     */   public <T extends Block & IBlockModelProvider> T getInstance() {
/*  35 */     if (this.instance == null) throw new IllegalStateException("the requested block instance for " + name() + " isn't set (yet)");
/*     */     
/*  37 */     return (T)this.instance;
/*     */   }
/*     */   
/*     */   public <T extends Block & IBlockModelProvider> void setInstance(T instance) {
/*  41 */     if (this.instance != null) throw new IllegalStateException("conflicting instance");
/*     */     
/*  43 */     this.instance = (Block)instance;
/*     */   }
/*     */   
/*     */   public <T extends ic2.core.block.state.IIdProvider> IBlockState getBlockState(T variant) {
/*  47 */     if (this.instance == null) return null;
/*     */     
/*  49 */     if (this.instance instanceof IMultiBlock) {
/*     */       
/*  51 */       IMultiBlock<T> block = (IMultiBlock<T>)this.instance;
/*     */       
/*  53 */       return block.getState(variant);
/*  54 */     }  if (variant == null) {
/*  55 */       return this.instance.func_176223_P();
/*     */     }
/*  57 */     throw new IllegalArgumentException("not applicable");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemStack() {
/*  67 */     if (this.instance == null) return false;
/*     */     
/*  69 */     if (this.instance instanceof IMultiItem) {
/*  70 */       return true;
/*     */     }
/*  72 */     Item item = Item.func_150898_a(this.instance);
/*  73 */     return (item != null && item != Items.field_190931_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Enum<T> & ic2.core.block.state.IIdProvider> ItemStack getItemStack() {
/*  78 */     return getItemStack((String)null);
/*     */   }
/*     */   
/*     */   public <T extends Enum<T> & ic2.core.block.state.IIdProvider> ItemStack getItemStack(T variant) {
/*  82 */     if (this.instance == null) return null;
/*     */     
/*  84 */     if (this.instance instanceof IMultiItem) {
/*     */       
/*  86 */       IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
/*     */       
/*  88 */       return multiItem.getItemStack(variant);
/*  89 */     }  if (variant == null) {
/*  90 */       return getItemStack((String)null);
/*     */     }
/*  92 */     throw new IllegalArgumentException("not applicable");
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Enum<T> & ic2.core.block.state.IIdProvider> ItemStack getItemStack(String variant) {
/*  97 */     if (this.instance == null) return null;
/*     */     
/*  99 */     if (this.instance instanceof IMultiItem) {
/*     */       
/* 101 */       IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
/*     */       
/* 103 */       return multiItem.getItemStack(variant);
/* 104 */     }  if (variant == null) {
/* 105 */       Item item = Item.func_150898_a(this.instance);
/* 106 */       if (item == null || item == Items.field_190931_a) throw new IllegalArgumentException("No item found for " + this.instance);
/*     */       
/* 108 */       return new ItemStack(item);
/*     */     } 
/* 110 */     throw new IllegalArgumentException("not applicable");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/* 115 */     if (this.instance == null) return null;
/*     */     
/* 117 */     if (this.instance instanceof IMultiItem) {
/* 118 */       return ((IMultiItem)this.instance).getVariant(stack);
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 126 */     values = values();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\BlockName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */