/*     */ package ic2.core.block.reactor.tileentity;
/*     */ 
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorChamber;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class TileEntityReactorChamberElectric extends TileEntityBlock implements IInventory, IReactorChamber, IEnergyEmitter {
/*     */   public final Redstone redstone;
/*     */   protected final Fluids fluids;
/*     */   
/*     */   public TileEntityReactorChamberElectric() {
/*  36 */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone(this));
/*  37 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids(this));
/*  38 */     this.fluids.addUnmanagedTankHook(new Supplier<Collection<Fluids.InternalFluidTank>>()
/*     */         {
/*     */           public Collection<Fluids.InternalFluidTank> get()
/*     */           {
/*  42 */             TileEntityNuclearReactorElectric reactor = TileEntityReactorChamberElectric.this.getReactor();
/*  43 */             if (reactor == null) {
/*  44 */               return Collections.emptySet();
/*     */             }
/*  46 */             return Arrays.asList(new Fluids.InternalFluidTank[] { reactor.inputTank, reactor.outputTank });
/*     */           }
/*     */         });
/*     */   }
/*     */   private TileEntityNuclearReactorElectric reactor; private long lastReactorUpdate;
/*     */   
/*     */   protected void onLoaded() {
/*  53 */     super.onLoaded();
/*     */     
/*  55 */     updateRedstoneLink();
/*     */   }
/*     */   
/*     */   private void updateRedstoneLink() {
/*  59 */     if ((func_145831_w()).field_72995_K)
/*     */       return; 
/*  61 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/*  63 */     if (reactor != null) {
/*  64 */       this.redstone.linkTo(reactor.redstone);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/*  71 */     super.updateEntityClient();
/*     */     
/*  73 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/*  75 */     if (reactor != null) {
/*  76 */       TileEntityNuclearReactorElectric.showHeatEffects(func_145831_w(), this.field_174879_c, reactor.getHeat());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  82 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/*  84 */     if (reactor != null) {
/*  85 */       World world = func_145831_w();
/*     */       
/*  87 */       return reactor.getBlockType().func_180639_a(world, reactor.func_174877_v(), world.func_180495_p(reactor.func_174877_v()), player, hand, side, hitX, hitY, hitZ);
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/*  96 */     super.onNeighborChange(neighbor, neighborPos);
/*     */     
/*  98 */     this.lastReactorUpdate = 0L;
/*     */     
/* 100 */     if (getReactor() == null) {
/* 101 */       destoryChamber(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void destoryChamber(boolean wrench) {
/* 106 */     World world = func_145831_w();
/* 107 */     world.func_175698_g(this.field_174879_c);
/*     */     
/* 109 */     for (ItemStack drop : getSelfDrops(0, wrench)) {
/* 110 */       StackUtil.dropAsEntity(world, this.field_174879_c, drop);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/* 118 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 120 */     return (reactor != null) ? reactor.func_70005_c_() : "<null>";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 125 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 127 */     return (reactor != null) ? reactor.func_145818_k_() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/* 132 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 134 */     return (reactor != null) ? reactor.func_145748_c_() : (ITextComponent)new TextComponentString("<null>");
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 139 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 141 */     return (reactor != null) ? reactor.func_70302_i_() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 146 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 148 */     return (reactor != null) ? reactor.func_191420_l() : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int index) {
/* 153 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 155 */     return (reactor != null) ? reactor.func_70301_a(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int count) {
/* 160 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 162 */     return (reactor != null) ? reactor.func_70298_a(index, count) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/* 167 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 169 */     return (reactor != null) ? reactor.func_70304_b(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int index, ItemStack stack) {
/* 174 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 176 */     if (reactor != null) reactor.func_70299_a(index, stack);
/*     */   
/*     */   }
/*     */   
/*     */   public int func_70297_j_() {
/* 181 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 183 */     return (reactor != null) ? reactor.func_70297_j_() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 188 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 190 */     return (reactor != null) ? reactor.func_70300_a(player) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {
/* 195 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 197 */     if (reactor != null) reactor.func_174889_b(player);
/*     */   
/*     */   }
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {
/* 202 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 204 */     if (reactor != null) reactor.func_174886_c(player);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_94041_b(int index, ItemStack stack) {
/* 209 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 211 */     return (reactor != null) ? reactor.func_94041_b(index, stack) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 216 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 218 */     return (reactor != null) ? reactor.func_174887_a_(id) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {
/* 223 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 225 */     if (reactor != null) reactor.func_174885_b(id, value);
/*     */   
/*     */   }
/*     */   
/*     */   public int func_174890_g() {
/* 230 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 232 */     return (reactor != null) ? reactor.func_174890_g() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/* 237 */     TileEntityNuclearReactorElectric reactor = getReactor();
/*     */     
/* 239 */     if (reactor != null) reactor.func_174888_l();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityNuclearReactorElectric getReactorInstance() {
/* 256 */     return this.reactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWall() {
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
/* 266 */     if (super.hasCapability(capability, facing))
/* 267 */       return (T)super.getCapability(capability, facing); 
/* 268 */     if (this.reactor != null) {
/* 269 */       return (T)this.reactor.getCapability(capability, facing);
/*     */     }
/* 271 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
/* 277 */     return (super.hasCapability(capability, facing) || (this.reactor != null && this.reactor.hasCapability(capability, facing)));
/*     */   }
/*     */   
/*     */   private TileEntityNuclearReactorElectric getReactor() {
/* 281 */     long time = func_145831_w().func_82737_E();
/*     */     
/* 283 */     if (time != this.lastReactorUpdate) {
/* 284 */       updateReactor();
/* 285 */       this.lastReactorUpdate = time;
/* 286 */     } else if (this.reactor != null && this.reactor.func_145837_r()) {
/* 287 */       this.reactor = null;
/*     */     } 
/*     */     
/* 290 */     return this.reactor;
/*     */   }
/*     */   
/*     */   private void updateReactor() {
/* 294 */     World world = func_145831_w();
/* 295 */     this.reactor = null;
/*     */     
/* 297 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 298 */       TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(facing));
/*     */       
/* 300 */       if (te instanceof TileEntityNuclearReactorElectric) {
/* 301 */         this.reactor = (TileEntityNuclearReactorElectric)te;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\tileentity\TileEntityReactorChamberElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */