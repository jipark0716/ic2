/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.item.tool.ContainerToolbox;
/*    */ import ic2.core.item.tool.HandHeldToolbox;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*    */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.model.ModelLoader;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ItemToolbox
/*    */   extends ItemIC2 implements IHandHeldInventory {
/*    */   public ItemToolbox() {
/* 27 */     super(ItemName.tool_box);
/*    */     
/* 29 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(final ItemName name) {
/* 35 */     ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition()
/*    */         {
/*    */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/* 38 */             EntityPlayer player = IC2.platform.getPlayerInstance();
/*    */ 
/*    */             
/*    */             ItemStack mainHandItem;
/*    */             
/* 43 */             boolean open = (player != null && player.field_71070_bA instanceof ContainerToolbox && (StackUtil.checkItemEquality(mainHandItem = player.func_184614_ca(), stack) || (StackUtil.checkItemEquality(player.func_184592_cb(), stack) && (mainHandItem == null || !(mainHandItem.func_77973_b() instanceof IHandHeldInventory)))));
/*    */             
/* 45 */             return ItemIC2.getModelLocation(name, open ? "open" : null);
/*    */           }
/*    */         });
/*    */     
/* 49 */     ModelBakery.registerItemVariants(this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, (String)null) });
/* 50 */     ModelBakery.registerItemVariants(this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, "open") });
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 55 */     ItemStack stack = StackUtil.get(player, hand);
/* 56 */     if (IC2.platform.isSimulating()) {
/* 57 */       IC2.platform.launchGui(player, getInventory(player, stack));
/*    */     }
/*    */     
/* 60 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/* 65 */     if (!(player.func_130014_f_()).field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerToolbox) {
/* 66 */       HandHeldToolbox toolbox = (HandHeldToolbox)((ContainerToolbox)player.field_71070_bA).base;
/*    */       
/* 68 */       if (toolbox.isThisContainer(stack)) {
/* 69 */         toolbox.saveAsThrown(stack);
/* 70 */         player.func_71053_j();
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 80 */     return EnumRarity.UNCOMMON;
/*    */   }
/*    */ 
/*    */   
/*    */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/* 85 */     return (IHasGui)new HandHeldToolbox(player, stack, 9);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemToolbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */