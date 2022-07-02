/*     */ package ic2.core.item.block;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.BehaviorDynamiteDispense;
/*     */ import ic2.core.block.EntityDynamite;
/*     */ import ic2.core.block.EntityStickyDynamite;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemDynamite
/*     */   extends ItemIC2
/*     */   implements IBoxable
/*     */ {
/*     */   public boolean sticky;
/*     */   
/*     */   public ItemDynamite(ItemName name) {
/*  41 */     super(name);
/*     */     
/*  43 */     this.sticky = (name == ItemName.dynamite_sticky);
/*  44 */     func_77625_d(16);
/*     */     
/*  46 */     BlockDispenser.field_149943_a.func_82595_a(this, new BehaviorDynamiteDispense(this.sticky));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77647_b(int i) {
/*  51 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float a, float b, float c) {
/*  61 */     if (this.sticky) {
/*  62 */       return EnumActionResult.PASS;
/*     */     }
/*     */     
/*  65 */     pos = pos.func_177972_a(side);
/*     */     
/*  67 */     IBlockState state = world.func_180495_p(pos);
/*  68 */     Block dynamite = BlockName.dynamite.getInstance();
/*     */     
/*  70 */     if (state.func_177230_c().isAir(state, (IBlockAccess)world, pos) && dynamite.func_176198_a(world, pos, side) && dynamite.func_176196_c(world, pos)) {
/*  71 */       world.func_180501_a(pos, dynamite.getStateForPlacement(world, pos, side, a, b, c, 0, (EntityLivingBase)player, hand), 3);
/*  72 */       StackUtil.consumeOrError(player, hand, 1);
/*     */       
/*  74 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  77 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  85 */     ItemStack stack = StackUtil.get(player, hand);
/*  86 */     if (!player.field_71075_bZ.field_75098_d) stack = StackUtil.decSize(stack); 
/*  87 */     world.func_184133_a(player, player.func_180425_c(), SoundEvents.field_187737_v, SoundCategory.PLAYERS, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
/*     */     
/*  89 */     if (IC2.platform.isSimulating()) {
/*  90 */       if (this.sticky) {
/*  91 */         world.func_72838_d((Entity)new EntityStickyDynamite(world, (EntityLivingBase)player));
/*     */       } else {
/*  93 */         world.func_72838_d((Entity)new EntityDynamite(world, (EntityLivingBase)player));
/*     */       } 
/*     */     }
/*     */     
/*  97 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */