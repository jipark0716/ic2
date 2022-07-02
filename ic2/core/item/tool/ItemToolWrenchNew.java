/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.IEnhancedOverlayProvider;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.api.transport.IPipe;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHitSoundOverride;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.item.ItemToolIC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.RotationUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.util.ITooltipFlag;
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
/*     */ 
/*     */ public class ItemToolWrenchNew
/*     */   extends ItemToolIC2
/*     */   implements IEnhancedOverlayProvider, IHitSoundOverride
/*     */ {
/*     */   public ItemToolWrenchNew() {
/*  41 */     super(ItemName.wrench_new, HarvestLevel.Iron, EnumSet.of(ToolClass.Wrench));
/*     */     
/*  43 */     func_77656_e(120);
/*     */   }
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  52 */     ItemStack stack = StackUtil.get(player, hand);
/*  53 */     if (!canTakeDamage(stack, 1)) return EnumActionResult.FAIL;
/*     */     
/*  55 */     IBlockState state = Util.getBlockState((IBlockAccess)world, pos);
/*  56 */     Block block = state.func_177230_c();
/*     */     
/*  58 */     if (block.isAir(state, (IBlockAccess)world, pos)) return EnumActionResult.FAIL;
/*     */     
/*  60 */     if (world.func_175625_s(pos) instanceof IPipe) {
/*  61 */       IPipe target = (IPipe)world.func_175625_s(pos);
/*     */       
/*  63 */       EnumFacing newFacing = RotationUtil.rotateByHit(side, hitX, hitY, hitZ);
/*     */       
/*  65 */       assert target != null;
/*  66 */       target.flipConnection(newFacing);
/*     */ 
/*     */       
/*  69 */       if (world.func_175625_s(pos.func_177972_a(newFacing)) instanceof IPipe) {
/*  70 */         IPipe other = (IPipe)world.func_175625_s(pos.func_177972_a(newFacing));
/*  71 */         assert other != null;
/*  72 */         if (target.isConnected(newFacing) != other.isConnected(newFacing.func_176734_d())) {
/*  73 */           other.flipConnection(newFacing.func_176734_d());
/*     */         }
/*     */       } 
/*  76 */       if (world.field_72995_K) {
/*  77 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager
/*  78 */             .getDefaultVolume());
/*     */       }
/*  80 */       return EnumActionResult.SUCCESS;
/*  81 */     }  if (block instanceof IWrenchable) {
/*  82 */       IWrenchable wrenchable = (IWrenchable)block;
/*     */       
/*  84 */       EnumFacing newFacing = RotationUtil.rotateByHit(side, hitX, hitY, hitZ);
/*     */       
/*  86 */       wrenchable.setFacing(world, pos, newFacing, player);
/*     */       
/*  88 */       if (world.field_72995_K) {
/*  89 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager
/*  90 */             .getDefaultVolume());
/*     */       }
/*  92 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  95 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
/* 100 */     return Util.matchesOD(repair, "ingotBronze");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack stack) {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> info, ITooltipFlag flagIn) {
/* 111 */     info.add((Minecraft.func_71410_x()).field_71474_y.field_74312_F.getDisplayName() + ":");
/* 112 */     info.add(" Safely mine IC2 machines (Yes you will get the machine and not the machine block)");
/* 113 */     info.add("");
/* 114 */     info.add((Minecraft.func_71410_x()).field_71474_y.field_74313_G.getDisplayName() + ":");
/* 115 */     info.add(" Set the machine facing (rotate)");
/* 116 */     info.add(" Connect pipes together and to covers");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 122 */     Block block = world.func_180495_p(pos).func_177230_c();
/* 123 */     if (block instanceof IWrenchable) {
/* 124 */       TileEntity tileEntity = world.func_175625_s(pos);
/* 125 */       return (tileEntity instanceof IPipe || 
/* 126 */         Arrays.<EnumFacing>stream(EnumFacing.field_82609_l).anyMatch(face -> ((IWrenchable)block).canSetFacing(world, pos, face, player)));
/*     */     } 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getHitSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/* 137 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getBreakSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/* 143 */     if (player.field_71075_bZ.field_75098_d) return null;
/*     */     
/* 145 */     IBlockState state = world.func_180495_p(pos);
/*     */     
/* 147 */     return (state.func_177230_c() instanceof IWrenchable) ? "Tools/wrench.ogg" : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolWrenchNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */