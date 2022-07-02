/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2DamageSource;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Energy;
/*    */ import ic2.core.block.comp.Redstone;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.item.armor.ItemArmorHazmat;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityTesla
/*    */   extends TileEntityBlock
/*    */ {
/* 25 */   protected final Redstone redstone = (Redstone)addComponent((TileEntityComponent)new Redstone(this));
/* 26 */   protected final Energy energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSink(this, 10000.0D, 2));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 34 */     super.updateEntityServer();
/*    */     
/* 36 */     if (!this.redstone.hasRedstoneInput())
/*    */       return; 
/* 38 */     if (this.energy.useEnergy(1.0D) && ++this.ticker % 32 == 0) {
/*    */       
/* 40 */       int damage = (int)this.energy.getEnergy() / 400;
/*    */       
/* 42 */       if (damage > 0 && shock(damage)) {
/* 43 */         this.energy.useEnergy((damage * 400));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean shock(int damage) {
/* 53 */     int r = 4;
/*    */     
/* 55 */     World world = func_145831_w();
/* 56 */     List<EntityLivingBase> entities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((this.field_174879_c
/* 57 */           .func_177958_n() - 4), (this.field_174879_c.func_177956_o() - 4), (this.field_174879_c.func_177952_p() - 4), (this.field_174879_c
/* 58 */           .func_177958_n() + 4 + 1), (this.field_174879_c.func_177956_o() + 4 + 1), (this.field_174879_c.func_177952_p() + 4 + 1)));
/*    */     
/* 60 */     for (EntityLivingBase entity : entities) {
/* 61 */       if (ItemArmorHazmat.hasCompleteHazmat(entity))
/*    */         continue; 
/* 63 */       if (entity.func_70097_a((DamageSource)IC2DamageSource.electricity, damage)) {
/* 64 */         if (world instanceof WorldServer) {
/* 65 */           WorldServer worldServer = (WorldServer)world;
/* 66 */           Random rnd = world.field_73012_v;
/*    */           
/* 68 */           for (int i = 0; i < damage; i++) {
/* 69 */             worldServer.func_180505_a(EnumParticleTypes.REDSTONE, true, entity.field_70165_t + rnd
/*    */                 
/* 71 */                 .nextFloat() - 0.5D, entity.field_70163_u + (rnd
/* 72 */                 .nextFloat() * 2.0F) - 1.0D, entity.field_70161_v + rnd
/* 73 */                 .nextFloat() - 0.5D, 0, 0.1D, 0.1D, 1.0D, 1.0D, new int[0]);
/*    */           }
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 80 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 90 */   private int ticker = IC2.random.nextInt(32);
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityTesla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */