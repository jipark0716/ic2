/*     */ package ic2.core.block.invslot;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class InvSlot implements Iterable<ItemStack> {
/*     */   public final IInventorySlotHolder<?> base;
/*     */   public final String name;
/*     */   private final ItemStack[] contents;
/*     */   
/*     */   public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count) {
/*  24 */     this(base, name, access, count, InvSide.ANY);
/*     */   }
/*     */   protected final Access access; public final InvSide preferredSide; private int stackSizeLimit;
/*     */   public InvSlot(IInventorySlotHolder<?> base, String name, Access access, int count, InvSide preferredSide) {
/*  28 */     if (count <= 0) throw new IllegalArgumentException("invalid slot count: " + count);
/*     */     
/*  30 */     this.contents = new ItemStack[count];
/*  31 */     clear();
/*     */     
/*  33 */     this.base = base;
/*  34 */     this.name = name;
/*  35 */     this.access = access;
/*  36 */     this.preferredSide = preferredSide;
/*  37 */     this.stackSizeLimit = 64;
/*     */     
/*  39 */     base.addInventorySlot(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvSlot(int count) {
/*  46 */     this.contents = new ItemStack[count];
/*  47 */     clear();
/*     */     
/*  49 */     this.base = null;
/*  50 */     this.name = null;
/*  51 */     this.access = Access.NONE;
/*  52 */     this.preferredSide = InvSide.ANY;
/*     */   }
/*     */   
/*     */   public void readFromNbt(NBTTagCompound nbt) {
/*  56 */     clear();
/*  57 */     NBTTagList contentsTag = nbt.func_150295_c("Contents", 10);
/*     */     
/*  59 */     for (int i = 0; i < contentsTag.func_74745_c(); i++) {
/*  60 */       NBTTagCompound contentTag = contentsTag.func_150305_b(i);
/*     */       
/*  62 */       int index = contentTag.func_74771_c("Index") & 0xFF;
/*     */       
/*  64 */       if (index >= size()) {
/*  65 */         IC2.log.error(LogCategory.Block, "Can't load item stack for %s, slot %s, index %d is out of bounds.", new Object[] {
/*  66 */               Util.toString((TileEntity)this.base.getParent()), this.name, Integer.valueOf(index)
/*     */             });
/*     */       } else {
/*     */         
/*  70 */         ItemStack stack = new ItemStack(contentTag);
/*     */         
/*  72 */         if (StackUtil.isEmpty(stack)) {
/*  73 */           IC2.log.warn(LogCategory.Block, "Can't load item stack %s for %s, slot %s, index %d, no matching item for %d:%d.", new Object[] {
/*  74 */                 StackUtil.toStringSafe(stack), Util.toString((TileEntity)this.base.getParent()), this.name, Integer.valueOf(index), Short.valueOf(contentTag.func_74765_d("id")), Short.valueOf(contentTag.func_74765_d("Damage"))
/*     */               });
/*     */         } else {
/*     */           
/*  78 */           if (!isEmpty(index)) {
/*  79 */             IC2.log.error(LogCategory.Block, "Loading content to non-empty slot for %s, slot %s, index %d, replacing %s with %s.", new Object[] {
/*  80 */                   Util.toString((TileEntity)this.base.getParent()), this.name, Integer.valueOf(index), get(index), stack
/*     */                 });
/*     */           }
/*  83 */           putFromNBT(index, stack);
/*     */         } 
/*     */       } 
/*  86 */     }  onChanged();
/*     */   }
/*     */   
/*     */   public void writeToNbt(NBTTagCompound nbt) {
/*  90 */     NBTTagList contentsTag = new NBTTagList();
/*     */     
/*  92 */     for (int i = 0; i < this.contents.length; i++) {
/*  93 */       ItemStack content = this.contents[i];
/*  94 */       if (!StackUtil.isEmpty(content)) {
/*     */         
/*  96 */         NBTTagCompound contentTag = new NBTTagCompound();
/*     */         
/*  98 */         contentTag.func_74774_a("Index", (byte)i);
/*  99 */         content.func_77955_b(contentTag);
/*     */         
/* 101 */         contentsTag.func_74742_a((NBTBase)contentTag);
/*     */       } 
/*     */     } 
/* 104 */     nbt.func_74782_a("Contents", (NBTBase)contentsTag);
/*     */   }
/*     */   
/*     */   public int size() {
/* 108 */     return this.contents.length;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 112 */     for (ItemStack stack : this.contents) {
/* 113 */       if (!StackUtil.isEmpty(stack)) {
/* 114 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isEmpty(int index) {
/* 122 */     return StackUtil.isEmpty(this.contents[index]);
/*     */   }
/*     */   
/*     */   public ItemStack get() {
/* 126 */     return get(0);
/*     */   }
/*     */   
/*     */   public ItemStack get(int index) {
/* 130 */     return this.contents[index];
/*     */   }
/*     */   
/*     */   public void put(ItemStack content) {
/* 134 */     put(0, content);
/*     */   }
/*     */   
/*     */   protected void putFromNBT(int index, ItemStack content) {
/* 138 */     this.contents[index] = content;
/*     */   }
/*     */   
/*     */   public void put(int index, ItemStack content) {
/* 142 */     if (StackUtil.isEmpty(content)) content = StackUtil.emptyStack;
/*     */     
/* 144 */     this.contents[index] = content;
/*     */     
/* 146 */     onChanged();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 150 */     Arrays.fill((Object[])this.contents, StackUtil.emptyStack);
/*     */   }
/*     */   
/*     */   public void clear(int index) {
/* 154 */     put(index, StackUtil.emptyStack);
/*     */   }
/*     */   
/*     */   public void onChanged() {}
/*     */   
/*     */   public boolean accepts(ItemStack stack) {
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canInput() {
/* 164 */     return (this.access == Access.I || this.access == Access.IO);
/*     */   }
/*     */   
/*     */   public boolean canOutput() {
/* 168 */     return (this.access == Access.O || this.access == Access.IO);
/*     */   }
/*     */   
/*     */   public void organize() {
/* 172 */     for (int dstIndex = 0; dstIndex < this.contents.length - 1; dstIndex++) {
/* 173 */       ItemStack dst = this.contents[dstIndex];
/*     */       
/* 175 */       if (StackUtil.isEmpty(dst) || StackUtil.getSize(dst) < dst.func_77976_d())
/*     */       {
/*     */ 
/*     */         
/* 179 */         for (int srcIndex = dstIndex + 1; srcIndex < this.contents.length; srcIndex++) {
/* 180 */           ItemStack src = this.contents[srcIndex];
/* 181 */           if (!StackUtil.isEmpty(src))
/*     */           {
/*     */ 
/*     */             
/* 185 */             if (StackUtil.isEmpty(dst)) {
/* 186 */               this.contents[srcIndex] = StackUtil.emptyStack;
/* 187 */               this.contents[dstIndex] = dst = src;
/* 188 */             } else if (StackUtil.checkItemEqualityStrict(dst, src)) {
/* 189 */               int space = Math.min(getStackSizeLimit(), dst.func_77976_d() - StackUtil.getSize(dst));
/* 190 */               int srcSize = StackUtil.getSize(src);
/*     */               
/* 192 */               if (srcSize <= space)
/* 193 */               { this.contents[srcIndex] = StackUtil.emptyStack;
/* 194 */                 this.contents[dstIndex] = dst = StackUtil.incSize(dst, srcSize);
/* 195 */                 if (srcSize == space)
/*     */                   break;  }
/* 197 */               else { this.contents[srcIndex] = StackUtil.decSize(src, space);
/* 198 */                 this.contents[dstIndex] = StackUtil.incSize(dst, space);
/*     */                 break; }
/*     */             
/*     */             }  } 
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getStackSizeLimit() {
/* 207 */     return this.stackSizeLimit;
/*     */   }
/*     */   
/*     */   public void setStackSizeLimit(int stackSizeLimit) {
/* 211 */     this.stackSizeLimit = stackSizeLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<ItemStack> iterator() {
/* 216 */     return new Iterator<ItemStack>()
/*     */       {
/*     */         public boolean hasNext() {
/* 219 */           return (this.idx < InvSlot.this.contents.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public ItemStack next() {
/* 224 */           if (this.idx >= InvSlot.this.contents.length) throw new NoSuchElementException();
/*     */           
/* 226 */           return InvSlot.this.contents[this.idx++];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 231 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/* 234 */         private int idx = 0;
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 240 */     String ret = this.name + "[" + this.contents.length + "]: ";
/*     */     
/* 242 */     for (int i = 0; i < this.contents.length; i++) {
/* 243 */       ret = ret + this.contents[i];
/*     */       
/* 245 */       if (i < this.contents.length - 1) {
/* 246 */         ret = ret + ", ";
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return ret;
/*     */   }
/*     */   
/*     */   protected ItemStack[] backup() {
/* 254 */     ItemStack[] ret = new ItemStack[this.contents.length];
/*     */     
/* 256 */     for (int i = 0; i < this.contents.length; i++) {
/* 257 */       ItemStack content = this.contents[i];
/*     */       
/* 259 */       ret[i] = StackUtil.isEmpty(content) ? StackUtil.emptyStack : content.func_77946_l();
/*     */     } 
/*     */     
/* 262 */     return ret;
/*     */   }
/*     */   
/*     */   protected void restore(ItemStack[] backup) {
/* 266 */     if (backup.length != this.contents.length) throw new IllegalArgumentException("invalid array size");
/*     */     
/* 268 */     for (int i = 0; i < this.contents.length; i++) {
/* 269 */       this.contents[i] = backup[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Access
/*     */   {
/* 291 */     NONE,
/* 292 */     I,
/* 293 */     O,
/* 294 */     IO;
/*     */     
/*     */     public boolean isInput() {
/* 297 */       return ((ordinal() & 0x1) != 0);
/*     */     }
/*     */     
/*     */     public boolean isOutput() {
/* 301 */       return ((ordinal() & 0x2) != 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum InvSide {
/* 306 */     ANY((String)new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST }),
/* 307 */     TOP((String)new EnumFacing[] { EnumFacing.UP }),
/* 308 */     BOTTOM((String)new EnumFacing[] { EnumFacing.DOWN }),
/* 309 */     SIDE((String)new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST }),
/* 310 */     NOTSIDE((String)new EnumFacing[0]);
/*     */     
/*     */     private Set<EnumFacing> acceptedSides;
/*     */     
/*     */     InvSide(EnumFacing... sides) {
/* 315 */       if (sides.length == 0) {
/* 316 */         this.acceptedSides = Collections.emptySet();
/*     */       } else {
/* 318 */         Set<EnumFacing> acceptedSides = EnumSet.noneOf(EnumFacing.class);
/* 319 */         acceptedSides.addAll(Arrays.asList(sides));
/* 320 */         this.acceptedSides = Collections.unmodifiableSet(acceptedSides);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean matches(EnumFacing side) {
/* 325 */       return this.acceptedSides.contains(side);
/*     */     }
/*     */     
/*     */     public Set<EnumFacing> getAcceptedSides() {
/* 329 */       return this.acceptedSides;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */