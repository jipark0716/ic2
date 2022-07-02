/*    */ package ic2.api.tile;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IWrenchable
/*    */ {
/*    */   EnumFacing getFacing(World paramWorld, BlockPos paramBlockPos);
/*    */   
/*    */   default boolean canSetFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   boolean setFacing(World paramWorld, BlockPos paramBlockPos, EnumFacing paramEnumFacing, EntityPlayer paramEntityPlayer);
/*    */   
/*    */   boolean wrenchCanRemove(World paramWorld, BlockPos paramBlockPos, EntityPlayer paramEntityPlayer);
/*    */   
/*    */   List<ItemStack> getWrenchDrops(World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState, TileEntity paramTileEntity, EntityPlayer paramEntityPlayer, int paramInt);
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\tile\IWrenchable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */