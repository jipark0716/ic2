/*    */ package ic2.core.crop;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.item.block.ItemBlockTileEntity;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCrop
/*    */   extends ItemIC2
/*    */   implements IBoxable {
/*    */   public ItemCrop() {
/* 29 */     super(ItemName.crop_stick);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 35 */     if (!world.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)world, pos)) pos = pos.func_177972_a(side);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     ItemStack cropStickStack = StackUtil.get(player, hand);
/* 45 */     if (StackUtil.isEmpty(cropStickStack)) return EnumActionResult.PASS;
/*    */     
/* 47 */     if (world.func_180495_p(pos.func_177977_b()).func_177230_c() != Blocks.field_150458_ak) return EnumActionResult.PASS;
/*    */     
/* 49 */     if (!player.func_175151_a(pos, side, cropStickStack)) return EnumActionResult.PASS;
/*    */ 
/*    */     
/* 52 */     if (!world.func_190527_a(BlockName.te.getInstance(), pos, true, side, (Entity)player)) return EnumActionResult.PASS;
/*    */     
/* 54 */     TileEntityBlock tile = TileEntityBlock.instantiate(TeBlock.crop.getTeClass());
/*    */     
/* 56 */     if (ItemBlockTileEntity.placeTeBlock(cropStickStack, (EntityLivingBase)player, world, pos, side, tile)) {
/* 57 */       SoundType stepSound = SoundType.field_185850_c;
/* 58 */       world.func_184148_a(null, pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D, stepSound
/* 59 */           .func_185841_e(), SoundCategory.BLOCKS, (stepSound
/*    */           
/* 61 */           .func_185843_a() + 1.0F) / 2.0F, stepSound
/* 62 */           .func_185847_b() * 0.8F);
/* 63 */       StackUtil.consumeOrError(player, hand, 1);
/*    */       
/* 65 */       return EnumActionResult.SUCCESS;
/*    */     } 
/* 67 */     return EnumActionResult.PASS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemStack) {
/* 73 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\ItemCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */