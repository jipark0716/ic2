/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.item.tool.ItemToolWrench;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityNuke extends EntityIC2Explosive {
/*    */   public EntityNuke(World world, double x, double y, double z, float power, int radiationRange) {
/* 16 */     super(world, x, y, z, 300, power, 0.05F, 1.5F, BlockName.te.getBlockState((IIdProvider)TeBlock.nuke), radiationRange);
/*    */   }
/*    */   
/*    */   public EntityNuke(World world) {
/* 20 */     this(world, 0.0D, 0.0D, 0.0D, 0.0F, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 26 */     ItemStack stack = StackUtil.get(player, hand);
/* 27 */     if (IC2.platform.isSimulating() && !StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof ItemToolWrench) {
/* 28 */       ItemToolWrench wrench = (ItemToolWrench)stack.func_77973_b();
/*    */       
/* 30 */       if (wrench.canTakeDamage(stack, 1)) {
/* 31 */         wrench.damage(stack, 1, player);
/* 32 */         func_70106_y();
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\EntityNuke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */