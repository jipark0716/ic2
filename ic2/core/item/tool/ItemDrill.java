/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.IMiningDrill;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHitSoundOverride;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemDrill
/*     */   extends ItemElectricTool
/*     */   implements IMiningDrill, IHitSoundOverride
/*     */ {
/*     */   public ItemDrill(ItemName name, int operationEnergyCost, HarvestLevel harvestLevel, int maxCharge, int transferLimit, int tier, float efficiency) {
/*  35 */     super(name, operationEnergyCost, harvestLevel, EnumSet.of(ToolClass.Pickaxe, ToolClass.Shovel));
/*     */     
/*  37 */     this.maxCharge = maxCharge;
/*  38 */     this.transferLimit = transferLimit;
/*  39 */     this.tier = tier;
/*     */     
/*  41 */     this.field_77864_a = efficiency;
/*     */   }
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
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getHitSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getBreakSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/*  61 */     if (player.field_71075_bZ.field_75098_d) return null;
/*     */     
/*  63 */     IBlockState state = world.func_180495_p(pos);
/*  64 */     float hardness = state.func_185887_b(world, pos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     return (hardness > 1.0F || hardness < 0.0F) ? "Tools/Drill/DrillHard.ogg" : "Tools/Drill/DrillSoft.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack stack, IBlockState state) {
/*  78 */     float speed = super.func_150893_a(stack, state);
/*  79 */     EntityPlayer player = getPlayerHoldingItem(stack);
/*  80 */     if (player != null) {
/*     */       
/*  82 */       if (player.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i((EntityLivingBase)player))
/*     */       {
/*  84 */         speed *= 5.0F;
/*     */       }
/*     */       
/*  87 */       if (!player.field_70122_E)
/*     */       {
/*  89 */         speed *= 5.0F;
/*     */       }
/*     */     } 
/*  92 */     return speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EntityPlayer getPlayerHoldingItem(ItemStack stack) {
/* 103 */     if (IC2.platform.isRendering()) {
/* 104 */       EntityPlayer player = IC2.platform.getPlayerInstance();
/* 105 */       if (player != null && player.field_71071_by.func_70448_g() == stack) {
/* 106 */         return player;
/*     */       }
/*     */     } else {
/* 109 */       for (EntityPlayer player : FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_181057_v()) {
/* 110 */         if (player.field_71071_by.func_70448_g() == stack) {
/* 111 */           return player;
/*     */         }
/*     */       } 
/*     */     } 
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int energyUse(ItemStack stack, World world, BlockPos pos, IBlockState state) {
/* 120 */     if (stack.func_77973_b() == ItemName.drill.getInstance())
/* 121 */       return 6; 
/* 122 */     if (stack.func_77973_b() == ItemName.diamond_drill.getInstance())
/* 123 */       return 20; 
/* 124 */     if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
/* 125 */       return 200;
/*     */     }
/* 127 */     throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int breakTime(ItemStack stack, World world, BlockPos pos, IBlockState state) {
/* 133 */     if (stack.func_77973_b() == ItemName.drill.getInstance())
/* 134 */       return 200; 
/* 135 */     if (stack.func_77973_b() == ItemName.diamond_drill.getInstance())
/* 136 */       return 50; 
/* 137 */     if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
/* 138 */       return 20;
/*     */     }
/* 140 */     throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean breakBlock(ItemStack stack, World world, BlockPos pos, IBlockState state) {
/* 146 */     if (stack.func_77973_b() == ItemName.drill.getInstance())
/* 147 */       return tryUsePower(stack, 50.0D); 
/* 148 */     if (stack.func_77973_b() == ItemName.diamond_drill.getInstance())
/* 149 */       return tryUsePower(stack, 80.0D); 
/* 150 */     if (stack.func_77973_b() == ItemName.iridium_drill.getInstance()) {
/* 151 */       return tryUsePower(stack, 800.0D);
/*     */     }
/* 153 */     throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(stack));
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemDrill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */