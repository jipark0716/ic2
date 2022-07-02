/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemDoor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemIC2Door
/*    */   extends ItemBlockIC2 {
/*    */   public ItemIC2Door(Block block) {
/* 15 */     super(block);
/*    */     
/* 17 */     func_77625_d(8);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
/* 22 */     ItemDoor.func_179235_a(world, pos, EnumFacing.func_176733_a(player.field_70177_z), this.field_150939_a, false);
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemIC2Door.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */