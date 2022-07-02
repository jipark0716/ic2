/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.core.util.Ic2Color;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ 
/*     */ 
/*     */ public class TileEntityClassicCable
/*     */   extends TileEntityCable
/*     */ {
/*     */   public TileEntityClassicCable(CableType cableType, int insulation) {
/*  13 */     super(cableType, insulation);
/*     */   }
/*     */   
/*     */   public TileEntityClassicCable(CableType cableType, int insulation, Ic2Color color) {
/*  17 */     super(cableType, insulation, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityClassicCable() {}
/*     */ 
/*     */   
/*     */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/*  25 */     boolean cheat = false;
/*  26 */     if (forCollision && this.cableType == CableType.tin) {
/*  27 */       cheat = true;
/*  28 */       this.insulation = -1;
/*     */     } 
/*     */     
/*  31 */     List<AxisAlignedBB> ret = super.getAabbs(forCollision);
/*  32 */     if (cheat) this.insulation = 0;
/*     */     
/*  34 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryAddInsulation() {
/*  40 */     return (this.cableType != CableType.tin && super.tryAddInsulation());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getConductionLoss() {
/*  45 */     return getConductionLoss(this.cableType, this.insulation);
/*     */   }
/*     */   
/*     */   public static double getConductionLoss(CableType type, int insulation) {
/*  49 */     switch (type) {
/*     */       case tin:
/*     */       case glass:
/*  52 */         return 0.025D;
/*     */       
/*     */       case copper:
/*  55 */         return 0.3D - 0.1D * insulation;
/*     */       
/*     */       case gold:
/*  58 */         return 0.5D - 0.05D * insulation;
/*     */       
/*     */       case iron:
/*  61 */         return (insulation <= 0) ? 1.0D : (1.0D - 0.05D * (1 << insulation - 1));
/*     */       
/*     */       case detector:
/*     */       case splitter:
/*  65 */         return 0.5D;
/*     */     } 
/*  67 */     throw new IllegalStateException("Type was " + type + ", " + insulation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationEnergyAbsorption() {
/*  73 */     switch (this.cableType) {
/*     */       case tin:
/*  75 */         assert this.insulation == 0;
/*  76 */         return 3.0D;
/*     */       
/*     */       case copper:
/*     */       case gold:
/*  80 */         return EnergyNet.instance.getPowerFromTier(this.insulation);
/*     */       
/*     */       case iron:
/*  83 */         if (this.insulation == 0) return 0.0D; 
/*  84 */         return EnergyNet.instance.getPowerFromTier(this.insulation + 1);
/*     */       
/*     */       case glass:
/*     */       case detector:
/*     */       case splitter:
/*  89 */         return 9001.0D;
/*     */     } 
/*     */     
/*  92 */     return super.getInsulationEnergyAbsorption();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductorBreakdownEnergy() {
/*  98 */     return (getCableCapacity(this.cableType) + 1);
/*     */   }
/*     */   
/*     */   public static int getCableCapacity(CableType type) {
/* 102 */     switch (type) {
/*     */       case tin:
/* 104 */         return 5;
/*     */       
/*     */       case copper:
/* 107 */         return 32;
/*     */       
/*     */       case gold:
/* 110 */         return 128;
/*     */       
/*     */       case glass:
/* 113 */         return 512;
/*     */       
/*     */       case iron:
/*     */       case detector:
/*     */       case splitter:
/* 118 */         return 2048;
/*     */     } 
/* 120 */     throw new IllegalStateException("Type was " + type);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityClassicCable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */