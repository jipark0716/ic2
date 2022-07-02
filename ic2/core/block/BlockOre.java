/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.block.type.ResourceBlock;
/*    */ import ic2.core.ref.BlockName;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class BlockOre
/*    */   extends BlockMultiID<ResourceBlock>
/*    */ {
/*    */   public static BlockOre create() {
/* 12 */     return BlockMultiID.<ResourceBlock, BlockOre>create(BlockOre.class, ResourceBlock.class, new Object[0]);
/*    */   }
/*    */   
/*    */   public BlockOre() {
/* 16 */     super(BlockName.resource, Material.field_151576_e);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_180651_a(IBlockState state) {
/* 21 */     return func_176201_c(state);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */