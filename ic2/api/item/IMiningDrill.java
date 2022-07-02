/*    */ package ic2.api.item;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ 
/*    */ public interface IMiningDrill
/*    */ {
/*    */   int energyUse(ItemStack paramItemStack, World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
/*    */   
/*    */   int breakTime(ItemStack paramItemStack, World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
/*    */   
/*    */   boolean breakBlock(ItemStack paramItemStack, World paramWorld, BlockPos paramBlockPos, IBlockState paramIBlockState);
/*    */   
/*    */   default boolean tryUsePower(ItemStack drill, double amount) {
/* 69 */     return ElectricItem.manager.use(drill, amount, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IMiningDrill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */