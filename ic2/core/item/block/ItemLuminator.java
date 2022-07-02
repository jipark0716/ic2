/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLuminator extends ItemBlockIC2 {
/*    */   public ItemLuminator(Block block) {
/* 15 */     super(block);
/*    */     
/* 17 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState state) {
/* 22 */     if (!world.func_180501_a(pos, state, 3)) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemLuminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */