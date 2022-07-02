/*     */ package ic2.core.item.tool;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ public class ContainerMeter extends ContainerFullInv<HandHeldMeter> { private IEnergyTile uut; private double resultAvg; private double resultMin; private double resultMax; private int resultCount; @ClientModifiable private Mode mode; public void func_75142_b() { super.func_75142_b(); if (this.uut == null)
/*     */       return;  NodeStats stats = EnergyNet.instance.getNodeStats(this.uut); if (stats == null) { ((HandHeldMeter)this.base).closeGUI(); return; }  double result = 0.0D; switch (this.mode) { case EnergyIn: result = stats.getEnergyIn(); break;
/*     */       case EnergyOut:
/*     */         result = stats.getEnergyOut(); break;
/*     */       case EnergyGain:
/*     */         result = stats.getEnergyIn() - stats.getEnergyOut(); break;
/*     */       case Voltage:
/*     */         result = stats.getVoltage(); break; }  this.resultAvg = this.resultMin = this.resultMax = result; if (result < this.resultMin)
/*     */       this.resultMin = result;  if (result > this.resultMax)
/*  15 */       this.resultMax = result;  this.resultAvg = (this.resultAvg * this.resultCount + result) / (this.resultCount + 1); this.resultCount++; ((NetworkManager)IC2.network.get(true)).sendContainerFields((ContainerBase)this, new String[] { "resultAvg", "resultMin", "resultMax", "resultCount" }); } public double getResultAvg() { return this.resultAvg; } public double getResultMin() { return this.resultMin; } public double getResultMax() { return this.resultMax; } public int getResultCount() { return this.resultCount; } public ContainerMeter(EntityPlayer player, HandHeldMeter meter) { super(player, (IInventory)meter, 218);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.resultCount = 0;
/* 114 */     this.mode = Mode.EnergyIn; }
/*     */   public Mode getMode() { return this.mode; }
/*     */   public void setMode(Mode mode) { this.mode = mode; ((NetworkManager)IC2.network.get(false)).sendContainerField((ContainerBase)this, "mode"); reset(); } public void reset() { if (IC2.platform.isSimulating()) { this.resultCount = 0; } else { ((NetworkManager)IC2.network.get(false)).sendContainerEvent((ContainerBase)this, "reset"); }  } public void setUut(IEnergyTile uut) { assert this.uut == null; this.uut = uut; } public void onContainerEvent(String event) { super.onContainerEvent(event); if ("reset".equals(event)) reset();  } public enum Mode
/*     */   {
/* 118 */     EnergyIn,
/* 119 */     EnergyOut,
/* 120 */     EnergyGain,
/* 121 */     Voltage;
/*     */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ContainerMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */