/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.BlockFoam;
/*     */ import ic2.core.block.BlockScaffold;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import ic2.core.item.ItemGradualInt;
/*     */ import ic2.core.item.armor.ItemArmorClassicCFPack;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemClassicSprayer
/*     */   extends ItemGradualInt
/*     */ {
/*     */   public ItemClassicSprayer() {
/*  43 */     super(ItemName.foam_sprayer, 1602);
/*     */     
/*  45 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
/*  53 */     if (!IC2.platform.isSimulating()) return EnumActionResult.SUCCESS; 
/*  54 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/*  56 */     ItemStack pack = (ItemStack)player.field_71071_by.field_70460_b.get(2);
/*     */     
/*  58 */     boolean pulledFromCFPack = (StackUtil.check(pack) && pack.func_77973_b() == ItemName.cf_pack.getInstance() && ((ItemArmorClassicCFPack)pack.func_77973_b()).getCFPellet(player, pack));
/*     */     
/*  60 */     if (!pulledFromCFPack && getCustomDamage(stack) < 100) {
/*  61 */       return EnumActionResult.FAIL;
/*     */     }
/*     */     
/*  64 */     if (world.func_180495_p(pos).func_177230_c() == BlockName.scaffold.getInstance()) {
/*  65 */       sprayFoam(world, pos, calculateDirectionsFromPlayer(player), true);
/*  66 */       if (!pulledFromCFPack) applyCustomDamage(stack, 100, (EntityLivingBase)player);
/*     */       
/*  68 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  71 */     if (sprayFoam(world, pos.func_177972_a(facing), calculateDirectionsFromPlayer(player), false)) {
/*  72 */       if (!pulledFromCFPack) applyCustomDamage(stack, 100, (EntityLivingBase)player);
/*     */       
/*  74 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  77 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean[] calculateDirectionsFromPlayer(EntityPlayer player) {
/*  88 */     float yaw = player.field_70177_z % 360.0F;
/*  89 */     float pitch = player.field_70125_A;
/*  90 */     boolean[] r = { true, true, true, true, true, true };
/*     */     
/*  92 */     if (pitch >= -65.0F && pitch <= 65.0F) {
/*  93 */       if ((yaw >= 300.0F && yaw <= 360.0F) || (yaw >= 0.0F && yaw <= 60.0F)) r[2] = false; 
/*  94 */       if (yaw >= 30.0F && yaw <= 150.0F) r[5] = false; 
/*  95 */       if (yaw >= 120.0F && yaw <= 240.0F) r[3] = false; 
/*  96 */       if (yaw >= 210.0F && yaw <= 330.0F) r[4] = false;
/*     */     
/*     */     } 
/*     */     
/* 100 */     if (pitch <= -40.0F) r[0] = false; 
/* 101 */     if (pitch >= 40.0F) r[1] = false;
/*     */     
/* 103 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sprayFoam(World world, BlockPos start, boolean[] directions, boolean scaffold) {
/* 114 */     if (!canFoam(world, start, scaffold)) return false;
/*     */ 
/*     */     
/* 117 */     Queue<BlockPos> check = new ArrayDeque<>();
/* 118 */     Set<BlockPos> place = new LinkedHashSet<>();
/*     */     
/* 120 */     int foamcount = getSprayMass();
/* 121 */     check.add(start);
/*     */     
/*     */     BlockPos set;
/* 124 */     while ((set = check.poll()) != null && foamcount > 0) {
/* 125 */       if (canFoam(world, set, scaffold) && place.add(set)) {
/* 126 */         for (int i : generateRngSpread(IC2.random)) {
/* 127 */           if (scaffold || directions[i]) {
/* 128 */             check.add(set.func_177972_a(EnumFacing.func_82600_a(i)));
/*     */           }
/*     */         } 
/*     */         
/* 132 */         foamcount--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 137 */     for (BlockPos pos : place) {
/* 138 */       IBlockState state = world.func_180495_p(pos);
/* 139 */       Block targetBlock = state.func_177230_c();
/*     */       
/* 141 */       if (targetBlock == BlockName.scaffold.getInstance()) {
/* 142 */         BlockScaffold block = (BlockScaffold)targetBlock;
/*     */         
/* 144 */         switch ((BlockScaffold.ScaffoldType)state.func_177229_b((IProperty)block.getTypeProperty())) {
/*     */           case wood:
/*     */           case reinforced_wood:
/* 147 */             block.func_176226_b(world, pos, state, 0);
/* 148 */             world.func_175656_a(pos, BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal));
/*     */             continue;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 154 */       if (targetBlock == BlockName.te.getInstance()) {
/* 155 */         TileEntity te = world.func_175625_s(pos);
/*     */         
/* 157 */         if (te instanceof TileEntityCable)
/* 158 */           ((TileEntityCable)te).foam(); 
/*     */         continue;
/*     */       } 
/* 161 */       world.func_175656_a(pos, BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal));
/*     */     } 
/*     */ 
/*     */     
/* 165 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean canFoam(World world, BlockPos pos, boolean scaffold) {
/* 169 */     if (!scaffold) {
/* 170 */       if (BlockName.foam.getInstance().func_176198_a(world, pos, EnumFacing.DOWN)) return true; 
/* 171 */       if (world.func_180495_p(pos).func_177230_c() != BlockName.te.getInstance()) return false;
/*     */       
/* 173 */       TileEntity te = world.func_175625_s(pos);
/* 174 */       return (te instanceof TileEntityCable && !((TileEntityCable)te).isFoamed());
/*     */     } 
/* 176 */     return (world.func_180495_p(pos).func_177230_c() == BlockName.scaffold.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] generateRngSpread(Random random) {
/* 187 */     int[] re = { 0, 1, 2, 3, 4, 5 };
/*     */     
/* 189 */     for (int i = 0; i < 16; i++) {
/* 190 */       int first = random.nextInt(6);
/* 191 */       int second = random.nextInt(6);
/*     */       
/* 193 */       int temp = re[first];
/* 194 */       re[first] = re[second];
/* 195 */       re[second] = temp;
/*     */     } 
/*     */     
/* 198 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSprayMass() {
/* 205 */     return 13;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/* 210 */     return 1.0D - super.getDurabilityForDisplay(stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemClassicSprayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */