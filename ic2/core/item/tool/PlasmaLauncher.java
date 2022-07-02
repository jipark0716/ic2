/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PlasmaLauncher extends ItemElectricTool {
/*    */   public PlasmaLauncher() {
/* 16 */     super(ItemName.plasma_launcher, 100);
/*    */     
/* 18 */     this.maxCharge = 40000;
/* 19 */     this.transferLimit = 128;
/* 20 */     this.tier = 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 25 */     if (!IC2.platform.isSimulating()) return new ActionResult(EnumActionResult.PASS, StackUtil.get(player, hand));
/*    */     
/* 27 */     EntityParticle particle = new EntityParticle(world, (EntityLivingBase)player, 8.0F, 1.0D, 2.0D);
/* 28 */     world.func_72838_d(particle);
/*    */     
/* 30 */     return super.func_77659_a(world, player, hand);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\PlasmaLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */