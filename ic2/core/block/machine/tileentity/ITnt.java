/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.core.block.EntityIC2Explosive;
/*    */ import ic2.core.block.EntityItnt;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class ITnt
/*    */   extends Explosive
/*    */ {
/*    */   protected boolean explodeOnRemoval() {
/* 11 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityIC2Explosive getEntity(EntityLivingBase igniter) {
/* 16 */     return (EntityIC2Explosive)new EntityItnt(func_145831_w(), this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o() + 0.5D, this.field_174879_c.func_177952_p() + 0.5D);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\ITnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */