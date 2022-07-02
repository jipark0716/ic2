/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.crop.IC2Crops;
/*    */ import ic2.core.crop.TileEntityCrop;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.item.type.CropResItemType;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemWeedingTrowel
/*    */   extends ItemIC2 {
/*    */   public ItemWeedingTrowel() {
/* 23 */     super(ItemName.weeding_trowel);
/*    */     
/* 25 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 30 */     if (!IC2.platform.isSimulating()) {
/* 31 */       return EnumActionResult.PASS;
/*    */     }
/*    */     
/* 34 */     TileEntity tileEntity = world.func_175625_s(pos);
/* 35 */     if (tileEntity instanceof TileEntityCrop) {
/* 36 */       TileEntityCrop tileEntityCrop = (TileEntityCrop)tileEntity;
/*    */       
/* 38 */       if (tileEntityCrop.getCrop() == IC2Crops.weed) {
/* 39 */         StackUtil.dropAsEntity(world, pos, StackUtil.copyWithSize(ItemName.crop_res.getItemStack((Enum)CropResItemType.weed), tileEntityCrop.getCurrentSize()));
/* 40 */         tileEntityCrop.reset();
/* 41 */         return EnumActionResult.SUCCESS;
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return EnumActionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemWeedingTrowel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */