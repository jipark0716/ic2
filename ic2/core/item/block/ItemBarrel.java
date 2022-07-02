/*    */ package ic2.core.item.block;
/*    */ import ic2.core.block.BlockScaffold;
/*    */ import ic2.core.block.TileEntityBarrel;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*    */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.model.ModelLoader;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ItemBarrel extends ItemIC2 {
/*    */   public ItemBarrel() {
/* 29 */     super(ItemName.barrel);
/*    */     
/* 31 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(final ItemName name) {
/* 37 */     ModelLoader.setCustomMeshDefinition((Item)this, new ItemMeshDefinition()
/*    */         {
/*    */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/* 40 */             return ItemIC2.getModelLocation(name, null);
/*    */           }
/*    */         });
/* 43 */     ModelBakery.registerItemVariants((Item)this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, null) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77653_i(ItemStack itemstack) {
/* 51 */     int v = ItemBooze.getAmountOfValue(itemstack.func_77952_i());
/* 52 */     if (v > 0) return "" + v + Localization.translate("ic2.item.LBoozeBarrel"); 
/* 53 */     return Localization.translate("ic2.item.EmptyBoozeBarrel");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float a, float b, float c) {
/* 62 */     ItemStack stack = StackUtil.get(player, hand);
/*    */     
/* 64 */     if (world.func_180495_p(pos) == BlockName.scaffold.getBlockState((IIdProvider)BlockScaffold.ScaffoldType.wood) && 
/* 65 */       ItemBlockTileEntity.placeTeBlock(stack, (EntityLivingBase)player, world, pos, side, (TileEntityBlock)new TileEntityBarrel(stack.func_77952_i()))) {
/* 66 */       StackUtil.consumeOrError(player, hand, 1);
/*    */       
/* 68 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */     
/* 71 */     return EnumActionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemBarrel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */