/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.BlockRubWood;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class ItemTreetap
/*     */   extends ItemIC2
/*     */   implements IBoxable
/*     */ {
/*     */   public ItemTreetap() {
/*  38 */     super(ItemName.treetap);
/*     */     
/*  40 */     func_77625_d(1);
/*  41 */     func_77656_e(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/*  51 */     IBlockState state = world.func_180495_p(pos);
/*  52 */     Block block = state.func_177230_c();
/*     */     
/*  54 */     if (block == BlockName.rubber_wood.getInstance()) {
/*  55 */       if (attemptExtract(player, world, pos, side, state, (List<ItemStack>)null)) {
/*  56 */         if (!world.field_72995_K) StackUtil.damage(player, hand, StackUtil.anyStack, 1);
/*     */         
/*  58 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*  60 */       return EnumActionResult.FAIL;
/*     */     } 
/*     */ 
/*     */     
/*  64 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean attemptExtract(EntityPlayer player, World world, BlockPos pos, EnumFacing side, IBlockState state, List<ItemStack> stacks) {
/*  73 */     assert state.func_177230_c() == BlockName.rubber_wood.getInstance();
/*     */     
/*  75 */     BlockRubWood.RubberWoodState rwState = (BlockRubWood.RubberWoodState)state.func_177229_b((IProperty)BlockRubWood.stateProperty);
/*     */     
/*  77 */     if (rwState.isPlain() || rwState.facing != side) return false;
/*     */     
/*  79 */     if (rwState.wet) {
/*  80 */       if (!world.field_72995_K) {
/*  81 */         world.func_175656_a(pos, state.func_177226_a((IProperty)BlockRubWood.stateProperty, (Comparable)rwState.getDry()));
/*     */         
/*  83 */         if (stacks != null) {
/*  84 */           stacks.add(StackUtil.copyWithSize(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin), world.field_73012_v.nextInt(3) + 1));
/*     */         } else {
/*  86 */           ejectResin(world, pos, side, world.field_73012_v.nextInt(3) + 1);
/*     */         } 
/*     */         
/*  89 */         if (player != null) IC2.achievements.issueAchievement(player, "acquireResin");
/*     */       
/*     */       } 
/*  92 */       if (world.field_72995_K && player != null) {
/*  93 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */       }
/*     */       
/*  96 */       return true;
/*     */     } 
/*  98 */     if (!world.field_72995_K && world.field_73012_v.nextInt(5) == 0) {
/*  99 */       world.func_175656_a(pos, state.func_177226_a((IProperty)BlockRubWood.stateProperty, (Comparable)BlockRubWood.RubberWoodState.plain_y));
/*     */     }
/*     */     
/* 102 */     if (world.field_73012_v.nextInt(5) == 0) {
/* 103 */       if (!world.field_72995_K) {
/* 104 */         ejectResin(world, pos, side, 1);
/*     */         
/* 106 */         if (stacks != null) {
/* 107 */           stacks.add(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/*     */         } else {
/* 109 */           ejectResin(world, pos, side, 1);
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       if (world.field_72995_K && player != null) {
/* 114 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */       }
/*     */       
/* 117 */       return true;
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ejectResin(World world, BlockPos pos, EnumFacing side, int quantity) {
/* 128 */     double ejectBias = 0.3D;
/* 129 */     double ejectX = pos.func_177958_n() + 0.5D + side.func_82601_c() * 0.3D;
/* 130 */     double ejectY = pos.func_177956_o() + 0.5D + side.func_96559_d() * 0.3D;
/* 131 */     double ejectZ = pos.func_177952_p() + 0.5D + side.func_82599_e() * 0.3D;
/*     */     
/* 133 */     for (int i = 0; i < quantity; i++) {
/* 134 */       EntityItem entityitem = new EntityItem(world, ejectX, ejectY, ejectZ, ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/* 135 */       entityitem.func_174869_p();
/* 136 */       world.func_72838_d((Entity)entityitem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 142 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemTreetap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */