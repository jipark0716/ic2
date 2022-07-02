/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import ic2.api.energy.tile.IKineticSource;
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
/*    */ @NotClassic
/*    */ public class TileEntityKineticGenerator
/*    */   extends TileEntityConversionGenerator
/*    */ {
/* 21 */   private final double euPerKu = 0.25D * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic");
/*    */   
/*    */   protected IKineticSource source;
/*    */   
/*    */   protected void onLoaded() {
/* 26 */     super.onLoaded();
/*    */     
/* 28 */     updateSource();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setFacing(EnumFacing facing) {
/* 33 */     super.setFacing(facing);
/*    */     
/* 35 */     updateSource();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/* 40 */     super.onNeighborChange(neighbor, neighborPos);
/*    */     
/* 42 */     if (func_174877_v().func_177972_a(getFacing()).equals(neighborPos)) {
/* 43 */       updateSource();
/*    */     }
/*    */   }
/*    */   
/*    */   protected void updateSource() {
/* 48 */     if (this.source == null || ((TileEntity)this.source).func_145837_r()) {
/* 49 */       TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(getFacing()));
/*    */       
/* 51 */       if (te instanceof IKineticSource) {
/* 52 */         this.source = (IKineticSource)te;
/*    */       } else {
/* 54 */         this.source = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getEnergyAvailable() {
/* 61 */     if (this.source != null) {
/* 62 */       assert !((TileEntity)this.source).func_145837_r();
/* 63 */       return this.source.drawKineticEnergy(getFacing().func_176734_d(), this.source.getConnectionBandwidth(getFacing().func_176734_d()), true);
/*    */     } 
/* 65 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawEnergyAvailable(int amount) {
/* 71 */     if (this.source != null) {
/* 72 */       assert !((TileEntity)this.source).func_145837_r();
/* 73 */       this.source.drawKineticEnergy(getFacing().func_176734_d(), amount, false);
/*    */     } else {
/*    */       assert false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getMultiplier() {
/* 81 */     return this.euPerKu;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */