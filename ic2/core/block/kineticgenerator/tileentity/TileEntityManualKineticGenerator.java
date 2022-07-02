/*    */ package ic2.core.block.kineticgenerator.tileentity;
/*    */ 
/*    */ import ic2.api.energy.tile.IKineticSource;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityManualKineticGenerator
/*    */   extends TileEntityBlock implements IKineticSource {
/*    */   public int clicks;
/*    */   public static final int maxClicksPerTick = 10;
/*    */   
/*    */   protected void updateEntityServer() {
/* 20 */     super.updateEntityServer();
/*    */     
/* 22 */     this.clicks = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 27 */     playerClicked(player);
/*    */     
/* 29 */     return true;
/*    */   }
/*    */   
/*    */   private void playerClicked(EntityPlayer player) {
/* 33 */     if (player.func_71024_bL().func_75116_a() <= 6)
/* 34 */       return;  if (!(player instanceof net.minecraft.entity.player.EntityPlayerMP))
/* 35 */       return;  if (this.clicks >= 10) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     if (!Util.isFakePlayer(player, false)) {
/*    */       
/* 41 */       ku = 400;
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 46 */       ku = 20;
/*    */     } 
/*    */     
/* 49 */     int ku = (int)(ku * outputModifier);
/*    */     
/* 51 */     this.currentKU = Math.min(this.currentKU + ku, 1000);
/*    */     
/* 53 */     player.func_71020_j(0.25F);
/* 54 */     this.clicks++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public final int maxKU = 1000;
/*    */   
/*    */   public int currentKU;
/*    */   
/* 67 */   private static final float outputModifier = Math.round(ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/manual"));
/*    */ 
/*    */   
/*    */   public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
/* 71 */     return drawKineticEnergy(directionFrom, 2147483647, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getConnectionBandwidth(EnumFacing side) {
/* 76 */     return 1000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
/* 82 */     return drawKineticEnergy(directionFrom, requestkineticenergy, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
/* 87 */     int max = Math.min(this.currentKU, request);
/* 88 */     if (!simulate) this.currentKU -= max; 
/* 89 */     return max;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\tileentity\TileEntityManualKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */