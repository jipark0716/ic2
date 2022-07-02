/*     */ package ic2.core.item.tool;
/*     */ import ic2.api.item.IEnhancedOverlayProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.transport.cover.ICoverHolder;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ItemToolIC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.RotationUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemToolCrowbar extends ItemToolIC2 implements IEnhancedOverlayProvider {
/*     */   public ItemToolCrowbar() {
/*  34 */     super(ItemName.crowbar, HarvestLevel.Iron, EnumSet.of(ToolClass.Crowbar));
/*     */     
/*  36 */     func_77656_e(250);
/*     */   }
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  40 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  45 */     ItemStack stack = StackUtil.get(player, hand);
/*  46 */     if (!canTakeDamage(stack, 1)) {
/*  47 */       return EnumActionResult.FAIL;
/*     */     }
/*     */     
/*  50 */     IBlockState state = Util.getBlockState((IBlockAccess)world, pos);
/*  51 */     Block block = state.func_177230_c();
/*     */     
/*  53 */     if (block.isAir(state, (IBlockAccess)world, pos)) {
/*  54 */       return EnumActionResult.FAIL;
/*     */     }
/*     */     
/*  57 */     if (world.func_175625_s(pos) instanceof ICoverHolder) {
/*  58 */       ICoverHolder target = (ICoverHolder)world.func_175625_s(pos);
/*     */       
/*  60 */       EnumFacing selectedFacing = RotationUtil.rotateByHit(side, hitX, hitY, hitZ);
/*     */       
/*  62 */       if (target.canRemoveCover(world, pos, selectedFacing)) {
/*  63 */         if (!world.field_72995_K) {
/*  64 */           target.removeCover(world, pos, selectedFacing);
/*  65 */           stack.func_77972_a(1, (EntityLivingBase)player);
/*     */         } else {
/*  67 */           IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Crowbar.ogg", true, IC2.audioManager.getDefaultVolume());
/*  68 */           IC2.platform.messagePlayer(player, Localization.translate("Attachment removed"), new Object[0]);
/*     */         } 
/*     */       }
/*     */       
/*  72 */       return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  75 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
/*  80 */     return (repair != null && Util.matchesOD(repair, "ingotBronze"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack stack) {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> info, ITooltipFlag flagIn) {
/*  94 */     info.add((Minecraft.func_71410_x()).field_71474_y.field_74313_G.getDisplayName() + ":");
/*  95 */     info.add(" Remove attachments from blocks");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 101 */     TileEntity tileEntity = world.func_175625_s(pos);
/* 102 */     if (tileEntity instanceof ICoverHolder) {
/* 103 */       return true;
/*     */     }
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolCrowbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */