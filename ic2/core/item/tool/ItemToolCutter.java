/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import ic2.api.item.IEnhancedOverlayProvider;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.audio.AudioPosition;
/*    */ import ic2.core.block.wiring.TileEntityCable;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemToolCutter
/*    */   extends ItemToolCrafting
/*    */   implements IEnhancedOverlayProvider
/*    */ {
/*    */   public ItemToolCutter() {
/* 28 */     super(ItemName.cutter, 60);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 36 */     TileEntity te = world.func_175625_s(pos);
/*    */     
/* 38 */     if (te instanceof TileEntityCable) {
/* 39 */       TileEntityCable cable = (TileEntityCable)te;
/*    */       
/* 41 */       Predicate<ItemStack> request = StackUtil.sameStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.rubber));
/*    */       
/* 43 */       if (StackUtil.consumeFromPlayerInventory(player, request, 1, true) && cable.tryAddInsulation()) {
/* 44 */         StackUtil.consumeFromPlayerInventory(player, request, 1, false);
/* 45 */         StackUtil.damageOrError(player, hand, 1);
/*    */         
/* 47 */         return EnumActionResult.SUCCESS;
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return EnumActionResult.PASS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeInsulation(EntityPlayer player, EnumHand hand, TileEntityCable cable) {
/* 59 */     if (cable.tryRemoveInsulation(true) && StackUtil.damage(player, hand, StackUtil.sameItem((Item)this), 3)) {
/* 60 */       cable.tryRemoveInsulation(false);
/*    */       
/* 62 */       if ((cable.func_145831_w()).field_72995_K) {
/* 63 */         IC2.audioManager.playOnce(new AudioPosition(cable.func_145831_w(), cable.func_174877_v()), "Tools/InsulationCutters.ogg");
/*    */       } else {
/* 65 */         StackUtil.dropAsEntity(cable.func_145831_w(), cable.func_174877_v(), ItemName.crafting.getItemStack((Enum)CraftingItemType.rubber));
/*    */       } 
/*    */       
/* 68 */       return true;
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 77 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolCutter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */