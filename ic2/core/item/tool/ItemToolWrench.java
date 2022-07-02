/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemToolWrench
/*     */   extends ItemIC2
/*     */   implements IBoxable
/*     */ {
/*     */   public ItemToolWrench() {
/*  41 */     this(ItemName.wrench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemToolWrench(ItemName name) {
/*  48 */     super(name);
/*     */     
/*  50 */     func_77656_e(120);
/*  51 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  69 */     ItemStack stack = StackUtil.get(player, hand);
/*  70 */     if (!canTakeDamage(stack, 1)) return EnumActionResult.FAIL;
/*     */     
/*  72 */     WrenchResult result = wrenchBlock(world, pos, side, player, canTakeDamage(stack, 10));
/*     */     
/*  74 */     if (result != WrenchResult.Nothing) {
/*  75 */       if (!world.field_72995_K) {
/*  76 */         damage(stack, (result == WrenchResult.Rotated) ? 1 : 10, player);
/*     */       } else {
/*  78 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */       } 
/*     */       
/*  81 */       return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */     } 
/*  83 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */   
/*     */   public static WrenchResult wrenchBlock(World world, BlockPos pos, EnumFacing side, EntityPlayer player, boolean remove) {
/*  88 */     IBlockState state = Util.getBlockState((IBlockAccess)world, pos);
/*  89 */     Block block = state.func_177230_c();
/*     */     
/*  91 */     if (block.isAir(state, (IBlockAccess)world, pos)) return WrenchResult.Nothing;
/*     */     
/*  93 */     if (block instanceof IWrenchable) {
/*  94 */       IWrenchable wrenchable = (IWrenchable)block;
/*     */       
/*  96 */       EnumFacing currentFacing = wrenchable.getFacing(world, pos);
/*  97 */       EnumFacing newFacing = currentFacing;
/*     */       
/*  99 */       if (IC2.keyboard.isAltKeyDown(player)) {
/* 100 */         EnumFacing.Axis axis = side.func_176740_k();
/*     */         
/* 102 */         if ((side.func_176743_c() == EnumFacing.AxisDirection.POSITIVE && !player.func_70093_af()) || (side
/* 103 */           .func_176743_c() == EnumFacing.AxisDirection.NEGATIVE && player.func_70093_af())) {
/* 104 */           newFacing = newFacing.func_176732_a(axis);
/*     */         } else {
/* 106 */           for (int i = 0; i < 3; i++) {
/* 107 */             newFacing = newFacing.func_176732_a(axis);
/*     */           }
/*     */         } 
/* 110 */       } else if (player.func_70093_af()) {
/* 111 */         newFacing = side.func_176734_d();
/*     */       } else {
/* 113 */         newFacing = side;
/*     */       } 
/*     */       
/* 116 */       if (newFacing != currentFacing && wrenchable.setFacing(world, pos, newFacing, player)) {
/* 117 */         return WrenchResult.Rotated;
/*     */       }
/*     */       
/* 120 */       if (remove && wrenchable.wrenchCanRemove(world, pos, player)) {
/* 121 */         if (!world.field_72995_K) {
/* 122 */           int experience; TileEntity te = world.func_175625_s(pos);
/*     */           
/* 124 */           if (ConfigUtil.getBool(MainConfig.get(), "protection/wrenchLogging")) {
/* 125 */             String playerName = player.func_146103_bH().getName() + "/" + player.func_146103_bH().getId();
/*     */             
/* 127 */             IC2.log.info(LogCategory.PlayerActivity, "Player %s used a wrench to remove the block %s (te %s) at %s.", new Object[] { playerName, state, 
/* 128 */                   getTeName(te), Util.formatPosition((IBlockAccess)world, pos) });
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 133 */           if (player instanceof EntityPlayerMP) {
/* 134 */             experience = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).field_71134_c.func_73081_b(), (EntityPlayerMP)player, pos);
/* 135 */             if (experience < 0) return WrenchResult.Nothing; 
/*     */           } else {
/* 137 */             experience = 0;
/*     */           } 
/*     */           
/* 140 */           block.func_176208_a(world, pos, state, player);
/*     */           
/* 142 */           if (block.removedByPlayer(state, world, pos, player, true)) {
/* 143 */             block.func_176206_d(world, pos, state);
/*     */           } else {
/* 145 */             return WrenchResult.Nothing;
/*     */           } 
/*     */           
/* 148 */           List<ItemStack> drops = wrenchable.getWrenchDrops(world, pos, state, te, player, 0);
/*     */           
/* 150 */           if (drops == null || drops.isEmpty()) {
/* 151 */             if (logEmptyWrenchDrops) {
/* 152 */               IC2.log.warn(LogCategory.General, "The block %s (te %s) at %s didn't yield any wrench drops.", new Object[] { state, getTeName(te), Util.formatPosition((IBlockAccess)world, pos) });
/*     */             }
/*     */           } else {
/* 155 */             for (ItemStack stack : drops) {
/* 156 */               StackUtil.dropAsEntity(world, pos, stack);
/*     */             }
/*     */           } 
/*     */           
/* 160 */           if (!player.field_71075_bZ.field_75098_d && experience > 0) {
/* 161 */             block.func_180637_b(world, pos, experience);
/*     */           }
/*     */         } 
/*     */         
/* 165 */         return WrenchResult.Removed;
/*     */       }
/*     */     
/* 168 */     } else if (block.rotateBlock(world, pos, side)) {
/* 169 */       return WrenchResult.Rotated;
/*     */     } 
/*     */     
/* 172 */     return WrenchResult.Nothing;
/*     */   }
/*     */   
/*     */   private static String getTeName(Object te) {
/* 176 */     return (te != null) ? te.getClass().getSimpleName().replace("TileEntity", "") : "none";
/*     */   }
/*     */   
/*     */   private enum WrenchResult {
/* 180 */     Rotated,
/* 181 */     Removed,
/* 182 */     Nothing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damage(ItemStack is, int damage, EntityPlayer player) {
/* 189 */     is.func_77972_a(damage, (EntityLivingBase)player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
/* 199 */     return (repair != null && Util.matchesOD(repair, "ingotBronze"));
/*     */   }
/*     */   
/* 202 */   private static final boolean logEmptyWrenchDrops = ConfigUtil.getBool(MainConfig.get(), "debug/logEmptyWrenchDrops");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */