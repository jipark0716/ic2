/*    */ package ic2.core.block.generator.tileentity;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Fluids;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*    */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*    */ import ic2.core.block.invslot.InvSlotOutput;
/*    */ import ic2.core.network.GuiSynced;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraftforge.fluids.FluidEvent;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class TileEntityGeoGenerator extends TileEntityBaseGenerator {
/*    */   private static final int fluidPerTick = 2;
/*    */   public final InvSlotConsumableLiquid fluidSlot;
/*    */   
/*    */   public TileEntityGeoGenerator() {
/* 23 */     super(20.0D, 1, 2400);
/*    */     
/* 25 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*    */     
/* 27 */     this.fluidTank = (FluidTank)this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(new Fluid[] { FluidRegistry.LAVA }));
/*    */     
/* 29 */     this.production = Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/geothermal"));
/*    */     
/* 31 */     this.fluidSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "fluidSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
/* 32 */     this.outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*    */   }
/*    */   public final InvSlotOutput outputSlot; @GuiSynced
/*    */   protected final FluidTank fluidTank; protected final Fluids fluids;
/*    */   protected void updateEntityServer() {
/* 37 */     super.updateEntityServer();
/*    */     
/* 39 */     if (this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.outputSlot)) {
/* 40 */       func_70296_d();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean gainFuel() {
/* 46 */     boolean dirty = false;
/*    */     
/* 48 */     FluidStack ret = this.fluidTank.drainInternal(2, false);
/* 49 */     if (ret != null && ret.amount >= 2) {
/* 50 */       this.fluidTank.drainInternal(2, true);
/* 51 */       this.fuel++;
/*    */       
/* 53 */       dirty = true;
/*    */     } 
/*    */     
/* 56 */     return dirty;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOperationSoundFile() {
/* 61 */     return "Generators/GeothermalLoop.ogg";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onBlockBreak() {
/* 66 */     super.onBlockBreak();
/*    */     
/* 68 */     FluidEvent.fireEvent((FluidEvent)new FluidEvent.FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, 1000), func_145831_w(), this.field_174879_c));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityGeoGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */