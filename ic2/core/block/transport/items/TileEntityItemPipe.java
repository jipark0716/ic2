/*     */ package ic2.core.block.transport.items;
/*     */ 
/*     */ import ic2.api.transport.IItemTransportTile;
/*     */ import ic2.core.block.transport.TileEntityPipe;
/*     */ import ic2.core.item.block.ItemPipe;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityItemPipe
/*     */   extends TileEntityPipe
/*     */   implements IItemTransportTile
/*     */ {
/*     */   public TileEntityItemPipe() {}
/*     */   
/*     */   public void flipConnection(EnumFacing facing) {}
/*     */   
/*     */   public TileEntityItemPipe(PipeType type, PipeSize size) {
/*  32 */     this();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     this.type = type;
/*  38 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int putItems(ItemStack stack, EnumFacing facing, boolean simulate) {
/*  44 */     if (StackUtil.isEmpty(stack)) {
/*  45 */       return 0;
/*     */     }
/*     */     
/*  48 */     if (!StackUtil.isEmpty(this.contents)) {
/*  49 */       return 0;
/*     */     }
/*     */     
/*  52 */     if (stack.func_190916_E() > getMaxStackSizeAllowed()) {
/*  53 */       return 0;
/*     */     }
/*     */     
/*  56 */     if (!simulate) {
/*  57 */       this.contents = StackUtil.copy(stack);
/*     */     }
/*     */     
/*  60 */     return stack.func_190916_E();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSizeAllowed() {
/*  65 */     return this.size.maxStackSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTransferRate() {
/*  70 */     return this.type.transferRate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getContents() {
/*  75 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContents(ItemStack stack) {
/*  80 */     this.contents = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  86 */     super.updateEntityServer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     boolean needsInventoryUpdate = false;
/*     */     
/*  94 */     if (!StackUtil.isEmpty(this.contents)) {
/*  95 */       EnumFacing facing = getFacing();
/*  96 */       TileEntity target = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(facing));
/*     */       
/*  98 */       if (target instanceof IItemTransportTile && (
/*  99 */         (IItemTransportTile)target).putItems(this.contents, facing.func_176734_d(), true) > 0) {
/* 100 */         int amount = ((IItemTransportTile)target).putItems(this.contents, facing.func_176734_d(), false);
/* 101 */         ItemStack newStack = StackUtil.copyShrunk(this.contents, amount);
/*     */         
/* 103 */         assert newStack.func_190926_b();
/*     */         
/* 105 */         this.contents = null;
/*     */         
/* 107 */         needsInventoryUpdate = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (needsInventoryUpdate) {
/* 113 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 119 */     super.func_145839_a(nbt);
/*     */     
/* 121 */     this.type = PipeType.values[nbt.func_74771_c("type") & 0xFF];
/* 122 */     this.size = PipeSize.values()[nbt.func_74771_c("size") & 0xFF];
/*     */ 
/*     */     
/* 125 */     NBTTagList contentsTag = nbt.func_150295_c("contents", 10);
/*     */     
/* 127 */     for (int i = 0; i < contentsTag.func_74745_c(); i++) {
/* 128 */       NBTTagCompound contentTag = contentsTag.func_150305_b(i);
/*     */       
/* 130 */       ItemStack stack = new ItemStack(contentTag);
/*     */       
/* 132 */       if (!StackUtil.isEmpty(stack))
/*     */       {
/*     */ 
/*     */         
/* 136 */         this.contents = stack;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 145 */     super.func_189515_b(nbt);
/*     */     
/* 147 */     nbt.func_74774_a("type", (byte)this.type.ordinal());
/* 148 */     nbt.func_74774_a("size", (byte)this.size.ordinal());
/*     */     
/* 150 */     NBTTagList contentsTag = new NBTTagList();
/* 151 */     if (!StackUtil.isEmpty(this.contents)) {
/* 152 */       NBTTagCompound contentTag = new NBTTagCompound();
/* 153 */       this.contents.func_77955_b(contentTag);
/* 154 */       contentsTag.func_74742_a((NBTBase)contentTag);
/* 155 */       nbt.func_74782_a("contents", (NBTBase)contentsTag);
/*     */     } 
/*     */     
/* 158 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateConnectivity() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
/* 168 */     return ItemPipe.getPipe(this.type, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ItemStack> getAuxDrops(int fortune) {
/* 173 */     List<ItemStack> ret = new ArrayList<>(super.getAuxDrops(fortune));
/*     */     
/* 175 */     if (!StackUtil.isEmpty(this.contents)) {
/* 176 */       ret.add(this.contents);
/*     */     }
/*     */     
/* 179 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/* 184 */     float th = this.size.thickness;
/* 185 */     float sp = (1.0F - th) / 2.0F;
/* 186 */     List<AxisAlignedBB> ret = new ArrayList<>(7);
/*     */     
/* 188 */     ret.add(new AxisAlignedBB(sp, sp, sp, (sp + th), (sp + th), (sp + th)));
/*     */     
/* 190 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 191 */       boolean hasConnection = ((this.connectivity & 1 << facing.ordinal()) != 0);
/*     */       
/* 193 */       if (hasConnection) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         float zS = sp, yS = zS, xS = yS;
/* 200 */         float zE = sp + th, yE = zE, xE = yE;
/*     */         
/* 202 */         switch (facing) { case DOWN:
/* 203 */             yS = 0.0F; yE = sp; break;
/* 204 */           case UP: yS = sp + th; yE = 1.0F; break;
/* 205 */           case NORTH: zS = 0.0F; zE = sp; break;
/* 206 */           case SOUTH: zS = sp + th; zE = 1.0F; break;
/* 207 */           case WEST: xS = 0.0F; xE = sp; break;
/* 208 */           case EAST: xS = sp + th; xE = 1.0F; break;
/* 209 */           default: throw new RuntimeException(); }
/*     */ 
/*     */         
/* 212 */         ret.add(new AxisAlignedBB(xS, yS, zS, xE, yE, zE));
/*     */       } 
/*     */     } 
/* 215 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 220 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 222 */     ret.add("type");
/* 223 */     ret.add("size");
/*     */     
/* 225 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateRenderState() {
/* 231 */     this.renderState = new TileEntityPipe.PipeRenderState(this.type, this.size, this.connectivity, this.covers, getFacing().ordinal());
/*     */   }
/*     */ 
/*     */   
/* 235 */   protected PipeType type = PipeType.bronze;
/* 236 */   protected PipeSize size = PipeSize.small;
/*     */   protected ItemStack contents;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\items\TileEntityItemPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */