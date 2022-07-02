/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import ic2.api.energy.tile.IHeatSource;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityStirlingGenerator
/*    */   extends TileEntityConversionGenerator
/*    */ {
/* 22 */   private final double productionpeerheat = (0.5F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Stirling"));
/*    */   
/*    */   protected IHeatSource source;
/*    */   
/*    */   protected void onLoaded() {
/* 27 */     super.onLoaded();
/*    */     
/* 29 */     updateSource();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setFacing(EnumFacing facing) {
/* 34 */     super.setFacing(facing);
/*    */     
/* 36 */     updateSource();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/* 41 */     super.onNeighborChange(neighbor, neighborPos);
/*    */     
/* 43 */     if (func_174877_v().func_177972_a(getFacing()).equals(neighborPos)) {
/* 44 */       updateSource();
/*    */     }
/*    */   }
/*    */   
/*    */   protected void updateSource() {
/* 49 */     if (this.source == null || ((TileEntity)this.source).func_145837_r()) {
/* 50 */       TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(getFacing()));
/*    */       
/* 52 */       if (te instanceof IHeatSource) {
/* 53 */         this.source = (IHeatSource)te;
/*    */       } else {
/* 55 */         this.source = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getEnergyAvailable() {
/* 62 */     if (this.source == null) {
/* 63 */       return 0;
/*    */     }
/* 65 */     assert !((TileEntity)this.source).func_145837_r();
/* 66 */     return this.source.drawHeat(getFacing().func_176734_d(), this.source.getConnectionBandwidth(getFacing().func_176734_d()), true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawEnergyAvailable(int amount) {
/* 72 */     if (this.source != null) {
/* 73 */       assert !((TileEntity)this.source).func_145837_r();
/* 74 */       this.source.drawHeat(getFacing().func_176734_d(), amount, false);
/*    */     } else {
/*    */       assert false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getMultiplier() {
/* 82 */     return this.productionpeerheat;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityStirlingGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */