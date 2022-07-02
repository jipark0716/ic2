/*    */ package ic2.core.block.generator.tileentity;
/*    */ import ic2.api.recipe.ILiquidAcceptManager;
/*    */ import ic2.api.recipe.ISemiFluidFuelManager;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Fluids;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*    */ import ic2.core.block.invslot.InvSlotOutput;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.network.GuiSynced;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntitySemifluidGenerator extends TileEntityBaseGenerator {
/*    */   public final InvSlotConsumableLiquid fluidSlot;
/*    */   public final InvSlotOutput outputSlot;
/*    */   
/*    */   public TileEntitySemifluidGenerator() {
/* 24 */     super(32.0D, 1, 32000);
/*    */     
/* 26 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*    */     
/* 28 */     this.fluidTank = (FluidTank)this.fluids.addTankInsert("fluid", 10000, Fluids.fluidPredicate((ILiquidAcceptManager)Recipes.semiFluidGenerator));
/*    */     
/* 30 */     this.fluidSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "fluidSlot", 1, (ILiquidAcceptManager)Recipes.semiFluidGenerator);
/* 31 */     this.outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*    */   } @GuiSynced
/*    */   protected final FluidTank fluidTank; protected final Fluids fluids;
/*    */   public static void init() {
/* 35 */     Recipes.semiFluidGenerator = (ISemiFluidFuelManager)new SemiFluidFuelManager();
/*    */     
/* 37 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidOil") > 0.0F) {
/* 38 */       addFuel("oil", 10, Math.round(8.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidOil")));
/*    */     }
/* 40 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidFuel") > 0.0F) {
/* 41 */       addFuel("fuel", 5, Math.round(32.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidFuel")));
/*    */     }
/* 43 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiomass") > 0.0F) {
/* 44 */       addFuel("biomass", 20, Math.round(8.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiomass")));
/*    */     }
/* 46 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBioethanol") > 0.0F) {
/* 47 */       addFuel("bio.ethanol", 10, Math.round(16.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBioethanol")));
/*    */     }
/* 49 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas") > 0.0F)
/* 50 */       addFuel("ic2biogas", 10, Math.round(16.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas"))); 
/*    */   }
/*    */   
/*    */   public static void addFuel(String fluidName, int amount, int eu) {
/* 54 */     Recipes.semiFluidGenerator.addFluid(fluidName, amount, eu);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateEntityServer() {
/* 59 */     super.updateEntityServer();
/*    */     
/* 61 */     if (this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.outputSlot)) {
/* 62 */       func_70296_d();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean gainFuel() {
/* 68 */     boolean dirty = false;
/*    */     
/* 70 */     FluidStack ret = this.fluidTank.drain(2147483647, false);
/* 71 */     if (ret != null) {
/* 72 */       ISemiFluidFuelManager.BurnProperty property = Recipes.semiFluidGenerator.getBurnProperty(ret.getFluid());
/*    */       
/* 74 */       if (property != null && ret.amount >= property.amount) {
/* 75 */         this.fluidTank.drainInternal(property.amount, true);
/* 76 */         this.production = property.power;
/* 77 */         this.fuel += property.amount;
/*    */         
/* 79 */         dirty = true;
/*    */       } 
/*    */     } 
/* 82 */     return dirty;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getOperationSoundFile() {
/* 87 */     return "Generators/GeothermalLoop.ogg";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntitySemifluidGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */