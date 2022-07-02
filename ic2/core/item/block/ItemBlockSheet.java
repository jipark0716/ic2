/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.core.block.BlockSheet;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBlockSheet
/*    */   extends ItemBlockMulti {
/*    */   public ItemBlockSheet(Block block) {
/* 15 */     super(block);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
/* 21 */     if (!((BlockSheet)this.field_150939_a).canReplace(world, pos, side, stack)) return false;
/*    */     
/* 23 */     return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemBlockSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */