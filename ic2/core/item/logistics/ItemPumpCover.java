/*     */ package ic2.core.item.logistics;
/*     */ 
/*     */ import ic2.api.item.IEnhancedOverlayProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.transport.cover.CoverProperty;
/*     */ import ic2.core.block.transport.cover.CoverRegistry;
/*     */ import ic2.core.block.transport.cover.ICoverHolder;
/*     */ import ic2.core.block.transport.cover.IFluidConsumingCover;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ItemMulti;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.RotationUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemPumpCover extends ItemMulti<PumpCoverType> implements IFluidConsumingCover, IEnhancedOverlayProvider {
/*     */   public ItemPumpCover() {
/*  34 */     super(ItemName.cover, PumpCoverType.class);
/*     */     
/*  36 */     func_77627_a(true);
/*     */     
/*  38 */     for (PumpCoverType type : PumpCoverType.values()) {
/*  39 */       CoverRegistry.register(new ItemStack((Item)this, 1, type.getId()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/*  45 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/*  47 */     PumpCoverType type = (PumpCoverType)getType(stack);
/*  48 */     if (type == null) {
/*  49 */       return EnumActionResult.PASS;
/*     */     }
/*     */     
/*  52 */     TileEntity tileEntity = world.func_175625_s(pos);
/*  53 */     if (!(tileEntity instanceof ICoverHolder)) return EnumActionResult.PASS;
/*     */     
/*  55 */     EnumFacing selectedFacing = RotationUtil.rotateByHit(side, xOffset, yOffset, zOffset);
/*     */     
/*  57 */     if (((ICoverHolder)tileEntity).canPlaceCover(world, pos, selectedFacing, stack)) {
/*  58 */       if (!world.field_72995_K) {
/*  59 */         ((ICoverHolder)tileEntity).placeCover(world, pos, selectedFacing, StackUtil.copyWithSize(stack, 1));
/*  60 */         stack.func_190918_g(1);
/*     */       } else {
/*  62 */         IC2.platform.messagePlayer(player, Localization.translate("Attachment placed"), new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/*  66 */     return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/*  72 */     super.func_77624_a(stack, world, tooltip, advanced);
/*     */     
/*  74 */     PumpCoverType type = (PumpCoverType)getType(stack);
/*  75 */     if (type == null)
/*     */       return; 
/*  77 */     tooltip.add("Transfer rate: " + type.transferRate + " mb/sec (as attachment)");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSuitableFor(ItemStack stack, Set<CoverProperty> types) {
/*  83 */     PumpCoverType type = (PumpCoverType)getType(stack);
/*  84 */     if (type == null) return false;
/*     */     
/*  86 */     return types.contains(CoverProperty.FluidConsuming);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTick(ItemStack stack, ICoverHolder parent) {
/*  91 */     PumpCoverType type = (PumpCoverType)getType(stack);
/*  92 */     if (type == null) {
/*  93 */       return false;
/*     */     }
/*     */     
/*  96 */     NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(stack);
/*  97 */     EnumFacing side = EnumFacing.field_82609_l[nbtTagCompound.func_74771_c("side") & 0xFF];
/*     */     
/*  99 */     boolean ret = false;
/* 100 */     TileEntity holder = (TileEntity)parent;
/*     */     
/* 102 */     LiquidUtil.AdjacentFluidHandler target = LiquidUtil.getAdjacentHandler(holder, side);
/* 103 */     if (target != null) {
/* 104 */       int amount = type.transferRate / 20;
/* 105 */       LiquidUtil.transfer(target.handler, target.dir.func_176734_d(), holder, amount);
/*     */     } 
/*     */     
/* 108 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowsInput(ItemStack stack) {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowsInput(FluidStack stack) {
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowsOutput(ItemStack stack) {
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowsOutput(FluidStack stack) {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 135 */     TileEntity tileEntity = world.func_175625_s(pos);
/* 136 */     if (tileEntity instanceof ICoverHolder) {
/* 137 */       return true;
/*     */     }
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getSideName(EnumFacing dir) {
/* 145 */     switch (dir) { case WEST:
/* 146 */         return "ic2.dir.west";
/* 147 */       case EAST: return "ic2.dir.east";
/* 148 */       case DOWN: return "ic2.dir.bottom";
/* 149 */       case UP: return "ic2.dir.top";
/* 150 */       case NORTH: return "ic2.dir.north";
/* 151 */       case SOUTH: return "ic2.dir.south"; }
/* 152 */      throw new RuntimeException("Invalid direction: " + dir);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\logistics\ItemPumpCover.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */