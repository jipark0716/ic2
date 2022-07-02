/*     */ package ic2.core.block.transport;
/*     */ 
/*     */ import ic2.api.transport.IPipe;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.block.state.UnlistedProperty;
/*     */ import ic2.core.block.transport.cover.CoverProperty;
/*     */ import ic2.core.block.transport.cover.Covers;
/*     */ import ic2.core.block.transport.cover.ICoverHolder;
/*     */ import ic2.core.block.transport.cover.ICoverItem;
/*     */ import ic2.core.block.transport.items.PipeSize;
/*     */ import ic2.core.block.transport.items.PipeType;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ 
/*     */ public abstract class TileEntityPipe
/*     */   extends TileEntityBlock
/*     */   implements IPipe, ICoverHolder {
/*  37 */   protected final Covers coversComponent = (Covers)addComponent((TileEntityComponent)new Covers(this));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity getTile() {
/*  43 */     return (TileEntity)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConnected(EnumFacing facing) {
/*  48 */     return ((this.connectivity & 1 << facing.ordinal()) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void flipConnection(EnumFacing paramEnumFacing);
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<CoverProperty> getCoverProperties() {
/*  58 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceCover(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  63 */     Item rawItem = stack.func_77973_b();
/*  64 */     if (!(rawItem instanceof ICoverItem)) return false;
/*     */     
/*  66 */     ICoverItem item = (ICoverItem)rawItem;
/*     */     
/*  68 */     return (item.isSuitableFor(stack, super.getCoverProperties()) && (this.covers & 1 << side
/*  69 */       .ordinal()) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeCover(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  74 */     this.coversComponent.addCover(side, stack);
/*  75 */     this.covers = (byte)(this.covers ^ 1 << side.ordinal());
/*  76 */     ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "covers");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRemoveCover(World world, BlockPos pos, EnumFacing side) {
/*  81 */     return ((this.covers & 1 << side.ordinal()) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeCover(World world, BlockPos pos, EnumFacing side) {
/*  86 */     ItemStack ret = this.coversComponent.removeCover(side);
/*  87 */     this.covers = (byte)(this.covers ^ 1 << side.ordinal());
/*  88 */     ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "covers");
/*  89 */     StackUtil.dropAsEntity(func_145831_w(), func_174877_v(), StackUtil.copyWithSize(ret, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  95 */     super.onLoaded();
/*     */     
/*  97 */     if ((func_145831_w()).field_72995_K) updateRenderState();
/*     */   
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 102 */     super.func_145839_a(nbt);
/*     */     
/* 104 */     this.connectivity = nbt.func_74771_c("connectivity");
/* 105 */     this.covers = nbt.func_74771_c("covers");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 110 */     super.func_189515_b(nbt);
/*     */     
/* 112 */     nbt.func_74774_a("connectivity", this.connectivity);
/* 113 */     nbt.func_74774_a("covers", this.covers);
/*     */     
/* 115 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/* 120 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/* 125 */     super.onNeighborChange(neighbor, neighborPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/* 132 */     if (this.field_145850_b.field_72995_K) updateRenderState();
/*     */     
/* 134 */     super.onPlaced(stack, placer, facing);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void updateConnectivity();
/*     */   
/*     */   public Ic2BlockState.Ic2BlockStateInstance getExtendedState(Ic2BlockState.Ic2BlockStateInstance state) {
/* 141 */     state = super.getExtendedState(state);
/*     */     
/* 143 */     PipeRenderState pipeRenderState = this.renderState;
/* 144 */     if (pipeRenderState != null) state = state.withProperties(new Object[] { renderStateProperty, pipeRenderState });
/*     */     
/* 146 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundType getBlockSound(Entity entity) {
/* 151 */     return SoundType.field_185852_e;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isNormalCube() {
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSideSolid(EnumFacing side) {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doesSideBlockRendering(EnumFacing side) {
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLightOpacity() {
/* 171 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean clientNeedsExtraModelInfo() {
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 181 */     updateRenderState();
/*     */     
/* 183 */     rerender();
/*     */     
/* 185 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 190 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 192 */     ret.add("connectivity");
/* 193 */     ret.add("covers");
/*     */     
/* 195 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ItemStack> getAuxDrops(int fortune) {
/* 200 */     List<ItemStack> ret = new ArrayList<>();
/*     */     
/* 202 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 203 */       if (this.coversComponent.hasCover(facing)) {
/* 204 */         ret.add(this.coversComponent.removeCover(facing));
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return ret;
/*     */   }
/*     */   
/*     */   public static class PipeRenderState { public final PipeType type;
/*     */     public final PipeSize size;
/*     */     
/*     */     public PipeRenderState(PipeType type, PipeSize size, int connectivity, int covers, int facing) {
/* 215 */       this.type = type;
/* 216 */       this.size = size;
/* 217 */       this.connectivity = connectivity;
/* 218 */       this.covers = covers;
/* 219 */       this.facing = facing;
/*     */     }
/*     */     public final int connectivity; public final int covers; public final int facing;
/*     */     
/*     */     public int hashCode() {
/* 224 */       int ret = this.type.hashCode();
/* 225 */       ret = ret * 31 + this.size.hashCode();
/* 226 */       ret = ret * 31 + this.connectivity;
/* 227 */       ret = ret * 31 + this.covers;
/* 228 */       ret = ret * 31 + this.facing;
/* 229 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 234 */       if (obj == this) return true;
/*     */       
/* 236 */       if (!(obj instanceof PipeRenderState)) return false;
/*     */       
/* 238 */       PipeRenderState o = (PipeRenderState)obj;
/*     */       
/* 240 */       return (o.type == this.type && o.size == this.size && o.connectivity == this.connectivity && o.covers == this.covers && o.facing == this.facing);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 245 */       return "PipeState<" + this.type + ", " + this.size + ", " + this.connectivity + ", " + this.covers + ", " + this.facing + '>';
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public static final IUnlistedProperty<PipeRenderState> renderStateProperty = (IUnlistedProperty<PipeRenderState>)new UnlistedProperty("renderstate", PipeRenderState.class);
/*     */ 
/*     */ 
/*     */   
/* 260 */   protected byte connectivity = 0;
/* 261 */   protected byte covers = 0;
/*     */   protected volatile PipeRenderState renderState;
/*     */   
/*     */   protected abstract void updateRenderState();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\TileEntityPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */