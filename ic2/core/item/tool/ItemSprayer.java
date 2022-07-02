/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.BlockFoam;
/*     */ import ic2.core.block.BlockIC2Fence;
/*     */ import ic2.core.block.BlockScaffold;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import ic2.core.item.ItemIC2FluidContainer;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemSprayer
/*     */   extends ItemIC2FluidContainer
/*     */   implements IBoxable
/*     */ {
/*     */   public ItemSprayer() {
/*  60 */     super(ItemName.foam_sprayer, 8000);
/*     */     
/*  62 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*  67 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/*  70 */     subItems.add(new ItemStack((Item)this));
/*  71 */     subItems.add(getItemStack(FluidName.construction_foam));
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  76 */     if (IC2.platform.isSimulating() && 
/*  77 */       IC2.keyboard.isModeSwitchKeyDown(player)) {
/*  78 */       ItemStack stack = StackUtil.get(player, hand);
/*  79 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */       
/*  81 */       int mode = nbtData.func_74762_e("mode");
/*  82 */       mode = (mode == 0) ? 1 : 0;
/*  83 */       nbtData.func_74768_a("mode", mode);
/*     */       
/*  85 */       String sMode = (mode == 0) ? "ic2.tooltip.mode.normal" : "ic2.tooltip.mode.single";
/*     */       
/*  87 */       IC2.platform.messagePlayer(player, "ic2.tooltip.mode", new Object[] { sMode });
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return super.func_77659_a(world, player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/*     */     Target target;
/*  99 */     if (IC2.keyboard.isModeSwitchKeyDown(player)) return EnumActionResult.PASS; 
/* 100 */     if (!IC2.platform.isSimulating()) return EnumActionResult.SUCCESS;
/*     */     
/* 102 */     RayTraceResult rtResult = func_77621_a(world, player, true);
/* 103 */     if (rtResult == null) return EnumActionResult.PASS;
/*     */     
/* 105 */     if (rtResult.field_72313_a == RayTraceResult.Type.BLOCK && !pos.equals(rtResult.func_178782_a())) {
/* 106 */       BlockPos fluidPos = rtResult.func_178782_a();
/*     */       
/* 108 */       if (LiquidUtil.drainBlockToContainer(world, fluidPos, player, hand)) {
/* 109 */         return EnumActionResult.SUCCESS;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     int maxFoamBlocks = 0;
/*     */     
/* 115 */     ItemStack stack = StackUtil.get(player, hand);
/* 116 */     FluidStack fluid = FluidUtil.getFluidContained(stack);
/*     */     
/* 118 */     if (fluid != null && fluid.amount > 0) {
/* 119 */       maxFoamBlocks += fluid.amount / getFluidPerFoam();
/*     */     }
/*     */     
/* 122 */     ItemStack pack = (ItemStack)player.field_71071_by.field_70460_b.get(2);
/*     */     
/* 124 */     if (pack != null && pack.func_77973_b() == ItemName.cf_pack.getInstance()) {
/* 125 */       fluid = FluidUtil.getFluidContained(pack);
/*     */       
/* 127 */       if (fluid != null && fluid.amount > 0) {
/* 128 */         maxFoamBlocks += fluid.amount / getFluidPerFoam();
/*     */       } else {
/* 130 */         pack = null;
/*     */       } 
/*     */     } else {
/* 133 */       pack = null;
/*     */     } 
/*     */     
/* 136 */     if (maxFoamBlocks == 0) return EnumActionResult.FAIL;
/*     */     
/* 138 */     maxFoamBlocks = Math.min(maxFoamBlocks, getMaxFoamBlocks(stack));
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (canPlaceFoam(world, pos, Target.Scaffold)) {
/* 143 */       target = Target.Scaffold;
/* 144 */     } else if (canPlaceFoam(world, pos, Target.Cable)) {
/* 145 */       target = Target.Cable;
/*     */     } else {
/*     */       
/* 148 */       pos = pos.func_177972_a(side);
/* 149 */       target = Target.Any;
/*     */     } 
/*     */     
/* 152 */     Vec3d viewVec = player.func_70040_Z();
/* 153 */     EnumFacing playerViewFacing = EnumFacing.func_176737_a((float)viewVec.field_72450_a, (float)viewVec.field_72448_b, (float)viewVec.field_72449_c);
/* 154 */     int amount = sprayFoam(world, pos, playerViewFacing.func_176734_d(), target, maxFoamBlocks);
/* 155 */     amount *= getFluidPerFoam();
/*     */     
/* 157 */     if (amount > 0) {
/* 158 */       if (pack != null) {
/* 159 */         IFluidHandlerItem packHandler = FluidUtil.getFluidHandler(pack);
/* 160 */         assert packHandler != null;
/* 161 */         fluid = packHandler.drain(amount, true);
/* 162 */         amount -= fluid.amount;
/* 163 */         player.field_71071_by.field_70460_b.set(2, packHandler.getContainer());
/*     */       } 
/*     */       
/* 166 */       if (amount > 0) {
/* 167 */         IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
/* 168 */         assert handler != null;
/* 169 */         handler.drain(amount, true);
/* 170 */         StackUtil.set(player, hand, handler.getContainer());
/*     */       } 
/*     */       
/* 173 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/* 176 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sprayFoam(World world, BlockPos pos, EnumFacing excludedDir, Target target, int maxFoamBlocks) {
/* 183 */     if (!canPlaceFoam(world, pos, target)) return 0;
/*     */     
/* 185 */     Queue<BlockPos> toCheck = new ArrayDeque<>();
/* 186 */     Set<BlockPos> positions = new HashSet<>();
/*     */     
/* 188 */     toCheck.add(pos);
/*     */     
/*     */     BlockPos cPos;
/*     */     
/* 192 */     while ((cPos = toCheck.poll()) != null && positions.size() < maxFoamBlocks) {
/* 193 */       if (!canPlaceFoam(world, cPos, target))
/*     */         continue; 
/* 195 */       if (positions.add(cPos)) {
/* 196 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 197 */           if (dir != excludedDir)
/*     */           {
/* 199 */             toCheck.add(cPos.func_177972_a(dir));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 204 */     toCheck.clear();
/*     */     
/* 206 */     int failedPlacements = 0;
/* 207 */     for (BlockPos targetPos : positions) {
/* 208 */       IBlockState state = world.func_180495_p(targetPos);
/* 209 */       Block targetBlock = state.func_177230_c();
/*     */       
/* 211 */       if (targetBlock == BlockName.scaffold.getInstance()) {
/* 212 */         BlockScaffold scaffold = (BlockScaffold)targetBlock;
/*     */         
/* 214 */         switch ((BlockScaffold.ScaffoldType)state.func_177229_b((IProperty)scaffold.getTypeProperty())) {
/*     */           case Any:
/*     */           case Scaffold:
/* 217 */             scaffold.func_176226_b(world, targetPos, state, 0);
/* 218 */             world.func_175656_a(targetPos, BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal));
/*     */             continue;
/*     */           case Cable:
/* 221 */             StackUtil.dropAsEntity(world, targetPos, BlockName.fence.getItemStack((Enum)BlockIC2Fence.IC2FenceType.iron));
/*     */ 
/*     */           
/*     */           case null:
/* 225 */             world.func_175656_a(targetPos, BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.reinforced)); continue;
/*     */         }  continue;
/*     */       } 
/* 228 */       if (targetBlock == BlockName.te.getInstance()) {
/* 229 */         TileEntity te = world.func_175625_s(targetPos);
/*     */         
/* 231 */         if (te instanceof TileEntityCable && 
/* 232 */           !((TileEntityCable)te).foam()) failedPlacements++; 
/*     */         continue;
/*     */       } 
/* 235 */       if (!world.func_175656_a(targetPos, BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal))) failedPlacements++;
/*     */     
/*     */     } 
/*     */     
/* 239 */     return positions.size() - failedPlacements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMaxFoamBlocks(ItemStack stack) {
/* 246 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 248 */     if (nbtData.func_74762_e("mode") == 0) {
/* 249 */       return 10;
/*     */     }
/* 251 */     return 1;
/*     */   }
/*     */   
/*     */   protected int getFluidPerFoam() {
/* 255 */     return 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 260 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canfill(Fluid fluid) {
/* 265 */     return (fluid == FluidName.construction_foam.getInstance());
/*     */   }
/*     */   private static boolean canPlaceFoam(World world, BlockPos pos, Target target) {
/*     */     TileEntity te;
/* 269 */     switch (target)
/*     */     { case Any:
/* 271 */         return BlockName.foam.getInstance().func_176198_a(world, pos, EnumFacing.DOWN);
/*     */       case Scaffold:
/* 273 */         return (world.func_180495_p(pos).func_177230_c() == BlockName.scaffold.getInstance());
/*     */       case Cable:
/* 275 */         if (world.func_180495_p(pos).func_177230_c() != BlockName.te.getInstance()) return false;
/*     */         
/* 277 */         te = world.func_175625_s(pos);
/*     */         
/* 279 */         if (te instanceof TileEntityCable) {
/* 280 */           return !((TileEntityCable)te).isFoamed();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 289 */         return false; }  assert false; return false;
/*     */   }
/*     */   
/*     */   private enum Target {
/* 293 */     Any, Scaffold, Cable;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemSprayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */