/*    */ package ic2.core.item.crafting;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.wiring.TileEntityChargepadMFSU;
/*    */ import ic2.core.block.wiring.TileEntityElectricBlock;
/*    */ import ic2.core.block.wiring.TileEntityElectricMFSU;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemMulti;
/*    */ import ic2.core.item.type.UpdateKitType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpgradeKit
/*    */   extends ItemMulti<UpdateKitType>
/*    */ {
/*    */   public UpgradeKit() {
/* 35 */     super(ItemName.upgrade_kit, UpdateKitType.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 40 */     if (!IC2.platform.isSimulating()) return EnumActionResult.PASS;
/*    */     
/* 42 */     UpdateKitType type = (UpdateKitType)getType(StackUtil.get(player, hand));
/* 43 */     if (type == null) return EnumActionResult.PASS;
/*    */     
/* 45 */     boolean ret = false;
/*    */     
/* 47 */     switch (type) {
/*    */       case mfsu:
/* 49 */         ret = upgradeToMfsu(world, pos);
/*    */         break;
/*    */     } 
/*    */     
/* 53 */     if (!ret) return EnumActionResult.PASS;
/*    */     
/* 55 */     StackUtil.consumeOrError(player, hand, 1);
/*    */     
/* 57 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */   private static boolean upgradeToMfsu(World world, BlockPos pos) {
/*    */     TileEntityChargepadMFSU tileEntityChargepadMFSU;
/* 61 */     TileEntity te = world.func_175625_s(pos);
/* 62 */     if (!(te instanceof ic2.core.block.TileEntityBlock)) return false;
/*    */     
/* 64 */     TileEntityElectricBlock replacement = null;
/*    */     
/* 66 */     if (te instanceof ic2.core.block.wiring.TileEntityElectricMFE) {
/* 67 */       TileEntityElectricMFSU tileEntityElectricMFSU = new TileEntityElectricMFSU();
/* 68 */     } else if (te instanceof ic2.core.block.wiring.TileEntityChargepadMFE) {
/* 69 */       tileEntityChargepadMFSU = new TileEntityChargepadMFSU();
/*    */     } 
/*    */     
/* 72 */     if (tileEntityChargepadMFSU != null) {
/* 73 */       NBTTagCompound nbt = new NBTTagCompound();
/*    */       
/* 75 */       te.func_189515_b(nbt);
/* 76 */       tileEntityChargepadMFSU.func_145839_a(nbt);
/*    */       
/* 78 */       world.func_175690_a(pos, (TileEntity)tileEntityChargepadMFSU);
/* 79 */       tileEntityChargepadMFSU.onUpgraded();
/* 80 */       tileEntityChargepadMFSU.func_70296_d();
/*    */       
/* 82 */       return true;
/*    */     } 
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 91 */     UpdateKitType type = (UpdateKitType)getType(stack);
/* 92 */     if (type == null)
/*    */       return; 
/* 94 */     switch (type) {
/*    */       case mfsu:
/* 96 */         tooltip.add(Localization.translate("ic2.upgrade_kit.mfsu.info"));
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\crafting\UpgradeKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */