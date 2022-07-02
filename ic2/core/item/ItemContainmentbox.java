/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.item.tool.ContainerContainmentbox;
/*    */ import ic2.core.item.tool.HandHeldContainmentbox;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemContainmentbox
/*    */   extends ItemIC2
/*    */   implements IHandHeldInventory {
/*    */   public ItemContainmentbox() {
/* 25 */     super(ItemName.containment_box);
/*    */     
/* 27 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 32 */     ItemStack stack = StackUtil.get(player, hand);
/* 33 */     if (!world.field_72995_K) {
/* 34 */       IC2.platform.launchGui(player, getInventory(player, stack));
/*    */     }
/*    */     
/* 37 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/* 42 */     if (!(player.func_130014_f_()).field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerContainmentbox) {
/* 43 */       HandHeldContainmentbox containmentBox = (HandHeldContainmentbox)((ContainerContainmentbox)player.field_71070_bA).base;
/*    */       
/* 45 */       if (containmentBox.isThisContainer(stack)) {
/* 46 */         containmentBox.saveAsThrown(stack);
/* 47 */         player.func_71053_j();
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 57 */     return EnumRarity.UNCOMMON;
/*    */   }
/*    */ 
/*    */   
/*    */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/* 62 */     return (IHasGui)new HandHeldContainmentbox(player, stack, 12);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemContainmentbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */