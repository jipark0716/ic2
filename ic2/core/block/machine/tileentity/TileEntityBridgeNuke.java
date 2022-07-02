/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.EntityIC2Explosive;
/*    */ import ic2.core.block.EntityNuke;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.ref.TeBlock.Delegated;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import ic2.core.util.LogCategory;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ @Delegated(current = TileEntityNuke.class, old = TileEntityBridgeNuke.TileEntityClassicNuke.class)
/*    */ public abstract class TileEntityBridgeNuke
/*    */   extends Explosive
/*    */ {
/*    */   public static class TileEntityClassicNuke
/*    */     extends TileEntityBridgeNuke {
/*    */     private static final float POWER = 35.0F;
/*    */     
/*    */     public float getNukeExplosivePower() {
/* 27 */       return Math.min(35.0F, ConfigUtil.getFloat(MainConfig.get(), "protection/nukeExplosionPowerLimit"));
/*    */     }
/*    */ 
/*    */     
/*    */     public int getRadiationRange() {
/* 32 */       return 1;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/* 39 */     super.onPlaced(stack, placer, facing);
/*    */     
/* 41 */     if (placer instanceof EntityPlayer) {
/* 42 */       EntityPlayer player = (EntityPlayer)placer;
/* 43 */       String playerName = player.func_146103_bH().getName() + "/" + player.func_146103_bH().getId();
/*    */       
/* 45 */       IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Player %s placed a nuke at %s.", new Object[] { playerName, Util.formatPosition((TileEntity)this) });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract float getNukeExplosivePower();
/*    */   
/*    */   public abstract int getRadiationRange();
/*    */   
/*    */   protected EntityIC2Explosive getEntity(EntityLivingBase igniter) {
/* 55 */     if (!ConfigUtil.getBool(MainConfig.get(), "protection/enableNuke")) return null;
/*    */     
/* 57 */     float power = getNukeExplosivePower();
/* 58 */     if (power < 0.0F) return null;
/*    */     
/* 60 */     int radiationRange = getRadiationRange();
/*    */     
/* 62 */     return (EntityIC2Explosive)new EntityNuke(func_145831_w(), this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o() + 0.5D, this.field_174879_c.func_177952_p() + 0.5D, power, radiationRange);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onIgnite(EntityLivingBase igniter) {
/* 67 */     String cause = (igniter == null) ? "indirectly" : ("by " + igniter.getClass().getSimpleName() + " " + igniter.func_70005_c_());
/* 68 */     IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuke at %s was ignited %s.", new Object[] { Util.formatPosition((TileEntity)this), cause });
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityBridgeNuke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */