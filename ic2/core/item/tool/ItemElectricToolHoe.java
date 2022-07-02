/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.entity.player.UseHoeEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemElectricToolHoe
/*    */   extends ItemElectricTool
/*    */ {
/*    */   public ItemElectricToolHoe() {
/* 34 */     super(ItemName.electric_hoe, 50, HarvestLevel.Iron, EnumSet.of(ToolClass.Hoe));
/*    */     
/* 36 */     this.maxCharge = 10000;
/* 37 */     this.transferLimit = 100;
/* 38 */     this.tier = 1;
/*    */     
/* 40 */     this.field_77864_a = 16.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
/* 49 */     ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 59 */     ItemStack stack = StackUtil.get(player, hand);
/* 60 */     if (!player.func_175151_a(pos, side, stack)) return EnumActionResult.PASS; 
/* 61 */     if (!ElectricItem.manager.canUse(stack, this.operationEnergyCost)) return EnumActionResult.PASS; 
/* 62 */     if (MinecraftForge.EVENT_BUS.post((Event)new UseHoeEvent(player, stack, world, pos))) return EnumActionResult.PASS;
/*    */     
/* 64 */     IBlockState state = world.func_180495_p(pos);
/* 65 */     Block block = state.func_177230_c();
/*    */     
/* 67 */     if (side != EnumFacing.DOWN && world
/* 68 */       .func_175623_d(pos.func_177984_a()) && (block == Blocks.field_150391_bh || block == Blocks.field_150349_c || block == Blocks.field_150346_d)) {
/*    */       
/* 70 */       block = Blocks.field_150458_ak;
/*    */       
/* 72 */       SoundType stepSound = block.getSoundType(state, world, pos, (Entity)player);
/* 73 */       world.func_184148_a(null, pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D, stepSound.func_185844_d(), SoundCategory.BLOCKS, (stepSound.func_185843_a() + 1.0F) / 2.0F, stepSound.func_185847_b() * 0.8F);
/*    */       
/* 75 */       if (IC2.platform.isSimulating()) {
/* 76 */         world.func_175656_a(pos, block.func_176223_P());
/* 77 */         ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/*    */       } 
/*    */       
/* 80 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */     
/* 83 */     return super.func_180614_a(player, world, pos, hand, side, hitX, hitY, hitZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemElectricToolHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */