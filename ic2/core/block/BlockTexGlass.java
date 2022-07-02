/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.ref.BlockName;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTexGlass
/*    */   extends BlockMultiID<BlockTexGlass.GlassType>
/*    */ {
/*    */   public static BlockTexGlass create() {
/* 23 */     return BlockMultiID.<GlassType, BlockTexGlass>create(BlockTexGlass.class, GlassType.class, new Object[0]);
/*    */   }
/*    */   
/*    */   private BlockTexGlass() {
/* 27 */     super(BlockName.glass, Material.field_151592_s);
/*    */     
/* 29 */     func_149711_c(5.0F);
/* 30 */     func_149752_b(180.0F);
/* 31 */     func_149672_a(SoundType.field_185853_f);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149745_a(Random random) {
/* 36 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c(IBlockState state) {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149730_j(IBlockState state) {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d(IBlockState state) {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_185481_k(IBlockState state) {
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public BlockRenderLayer func_180664_k() {
/* 62 */     return BlockRenderLayer.CUTOUT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_176225_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/* 73 */     if (world.func_180495_p(pos.func_177972_a(side)).func_177230_c() == this) {
/* 74 */       return false;
/*    */     }
/*    */     
/* 77 */     return super.func_176225_a(state, world, pos, side);
/*    */   }
/*    */   
/*    */   public enum GlassType implements IIdProvider {
/* 81 */     reinforced;
/*    */ 
/*    */     
/*    */     public String getName() {
/* 85 */       return name();
/*    */     }
/*    */ 
/*    */     
/*    */     public int getId() {
/* 90 */       return ordinal();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockTexGlass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */