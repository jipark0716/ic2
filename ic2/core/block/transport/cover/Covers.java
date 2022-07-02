/*     */ package ic2.core.block.transport.cover;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class Covers extends TileEntityComponent {
/*     */   public Covers(TileEntityBlock parent) {
/*  16 */     super(parent);
/*     */     
/*  18 */     this.covers = new ItemStack[6];
/*     */   }
/*     */   protected ItemStack[] covers;
/*     */   public void addCover(EnumFacing side, ItemStack cover) {
/*  22 */     if (StackUtil.isEmpty(this.covers[side.ordinal()])) {
/*  23 */       ItemStack ret = cover.func_77946_l();
/*  24 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(ret);
/*  25 */       nbtTagCompound.func_74774_a("side", (byte)side.ordinal());
/*  26 */       this.covers[side.ordinal()] = ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack removeCover(EnumFacing side) {
/*  31 */     ItemStack ret = this.covers[side.ordinal()];
/*  32 */     ret.func_77982_d(null);
/*  33 */     this.covers[side.ordinal()] = null;
/*  34 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean hasCover(EnumFacing side) {
/*  38 */     return !StackUtil.isEmpty(this.covers[side.ordinal()]);
/*     */   }
/*     */   
/*     */   public ICoverItem getCoverItem(EnumFacing side) {
/*  42 */     ItemStack stack = this.covers[side.ordinal()];
/*  43 */     if (StackUtil.isEmpty(stack)) {
/*  44 */       return null;
/*     */     }
/*     */     
/*  47 */     return (ICoverItem)stack.func_77973_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNbt(NBTTagCompound nbt) {
/*  52 */     NBTTagList coversTag = nbt.func_150295_c("covers", 10);
/*     */     
/*  54 */     for (int i = 0; i < coversTag.func_74745_c(); i++) {
/*  55 */       NBTTagCompound coverTag = coversTag.func_150305_b(i);
/*     */       
/*  57 */       int index = coverTag.func_74771_c("facing") & 0xFF;
/*     */       
/*  59 */       if (index >= this.covers.length) {
/*  60 */         IC2.log.error(LogCategory.Block, "Can't load cover for %s, index %d is out of bounds.", new Object[] { Util.toString((TileEntity)this.parent), Integer.valueOf(index) });
/*     */       }
/*     */       else {
/*     */         
/*  64 */         ItemStack cover = new ItemStack(coverTag);
/*     */         
/*  66 */         if (StackUtil.isEmpty(cover)) {
/*  67 */           IC2.log.warn(LogCategory.Block, "Can't load cover %s for %s, index %d, no matching item for %d:%d.", new Object[] {
/*  68 */                 StackUtil.toStringSafe(cover), Util.toString((TileEntity)this.parent), Integer.valueOf(index), Short.valueOf(coverTag.func_74765_d("id")), Short.valueOf(coverTag.func_74765_d("Damage"))
/*     */               });
/*     */         } else {
/*     */           
/*  72 */           if (!StackUtil.isEmpty(this.covers[index])) {
/*  73 */             IC2.log.error(LogCategory.Block, "Loading cover to non-empty cover for %s, index %d, replacing %s with %s.", new Object[] {
/*  74 */                   Util.toString((TileEntity)this.parent), Integer.valueOf(index), this.covers[index], cover
/*     */                 });
/*     */           }
/*  77 */           this.covers[index] = cover;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeToNbt() {
/*  84 */     NBTTagCompound ret = new NBTTagCompound();
/*     */     
/*  86 */     NBTTagList coversTag = new NBTTagList();
/*     */     
/*  88 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/*  89 */       ItemStack cover = this.covers[facing.ordinal()];
/*  90 */       if (!StackUtil.isEmpty(cover)) {
/*     */ 
/*     */ 
/*     */         
/*  94 */         NBTTagCompound coverTag = new NBTTagCompound();
/*     */         
/*  96 */         coverTag.func_74774_a("facing", (byte)facing.ordinal());
/*  97 */         cover.func_77955_b(coverTag);
/*     */         
/*  99 */         coversTag.func_74742_a((NBTBase)coverTag);
/*     */       } 
/*     */     } 
/* 102 */     ret.func_74782_a("covers", (NBTBase)coversTag);
/*     */     
/* 104 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enableWorldTick() {
/* 109 */     return !(this.parent.func_145831_w()).field_72995_K;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWorldTick() {
/* 114 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 115 */       if (!StackUtil.isEmpty(this.covers[facing.ordinal()]))
/* 116 */         ((ICoverItem)this.covers[facing.ordinal()].func_77973_b()).onTick(this.covers[facing.ordinal()], (ICoverHolder)this.parent); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\cover\Covers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */