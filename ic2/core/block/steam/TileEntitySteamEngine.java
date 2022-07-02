/*     */ package ic2.core.block.steam;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.ref.FluidName;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ 
/*     */ 
/*     */ public class TileEntitySteamEngine
/*     */   extends TileEntityInventory
/*     */   implements IKineticProvider
/*     */ {
/*  19 */   protected int ticksSinceLastActiveUpdate = IC2.random.nextInt(128);
/*     */   
/*  21 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  22 */   protected final Fluids.InternalFluidTank fluidTank = this.fluids.addTankInsert("steam", 1000, InvSlot.InvSide.ANY, Fluids.fluidPredicate(new Fluid[] { FluidName.biomass.getInstance() }));
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  27 */     super.func_145839_a(nbt);
/*     */     
/*  29 */     this.delta = nbt.func_74762_e("delta");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  34 */     super.func_189515_b(nbt);
/*     */     
/*  36 */     nbt.func_74768_a("delta", this.delta);
/*     */     
/*  38 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  44 */     super.updateEntityServer();
/*     */     
/*  46 */     boolean needsInventoryUpdate = false;
/*     */     
/*  48 */     boolean newActive = work();
/*     */     
/*  50 */     if (needsInventoryUpdate) {
/*  51 */       func_70296_d();
/*     */     }
/*     */     
/*  54 */     if (!delayActiveUpdate()) {
/*  55 */       setActive(newActive);
/*     */     } else {
/*  57 */       if (this.ticksSinceLastActiveUpdate % 128 == 0) {
/*  58 */         setActive((this.activityMeter > 0));
/*  59 */         this.activityMeter = 0;
/*     */       } 
/*     */       
/*  62 */       if (newActive) {
/*  63 */         this.activityMeter++;
/*     */       } else {
/*  65 */         this.activityMeter--;
/*     */       } 
/*     */       
/*  68 */       this.ticksSinceLastActiveUpdate++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean work() {
/*  73 */     if (this.fluidTank.getFluidAmount() > 1) {
/*  74 */       this.fluidTank.drainInternal(1, true);
/*     */       
/*  76 */       this.delta = Math.min(++this.delta, 200);
/*  77 */       this.power = (int)(getMaxPower() / 10.0D * (this.delta / 20));
/*     */       
/*  79 */       return true;
/*     */     } 
/*  81 */     this.delta = Math.max(--this.delta, 0);
/*  82 */     this.power = (int)(getMaxPower() / 10.0D * (this.delta / 20));
/*     */     
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProvidedPower(EnumFacing side) {
/*  95 */     return (side == getFacing()) ? this.power : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPower() {
/* 100 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected int power = 0;
/* 106 */   protected int delta = 0;
/* 107 */   protected int activityMeter = 0;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\steam\TileEntitySteamEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */