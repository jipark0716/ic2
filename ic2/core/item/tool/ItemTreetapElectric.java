/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTreetapElectric
/*    */   extends ItemElectricTool {
/*    */   public ItemTreetapElectric() {
/* 21 */     super(ItemName.electric_treetap, 50);
/*    */     
/* 23 */     this.maxCharge = 10000;
/* 24 */     this.transferLimit = 100;
/* 25 */     this.tier = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 30 */     IBlockState state = world.func_180495_p(pos);
/* 31 */     Block block = state.func_177230_c();
/* 32 */     ItemStack stack = StackUtil.get(player, hand);
/*    */     
/* 34 */     if (block != BlockName.rubber_wood.getInstance() || 
/* 35 */       !ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
/* 36 */       return EnumActionResult.PASS;
/*    */     }
/*    */     
/* 39 */     if (ItemTreetap.attemptExtract(player, world, pos, side, state, null)) {
/* 40 */       ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/* 41 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */     
/* 44 */     return super.func_180614_a(player, world, pos, hand, side, hitX, hitY, hitZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemTreetapElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */