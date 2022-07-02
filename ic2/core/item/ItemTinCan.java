/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTinCan
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemTinCan() {
/* 18 */     super(ItemName.filled_tin_can);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 23 */     ItemStack stack = StackUtil.get(player, hand);
/* 24 */     if (!world.field_72995_K && player.func_71024_bL().func_75121_c()) {
/* 25 */       return onEaten(player, stack);
/*    */     }
/*    */     
/* 28 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */   
/*    */   public ActionResult<ItemStack> onEaten(EntityPlayer player, ItemStack stack) {
/* 32 */     int amount = Math.min(StackUtil.getSize(stack), 20 - player.func_71024_bL().func_75116_a());
/* 33 */     if (amount <= 0) return new ActionResult(EnumActionResult.PASS, stack);
/*    */     
/* 35 */     ItemStack emptyStack = StackUtil.copyWithSize(ItemName.crafting.getItemStack((Enum)CraftingItemType.tin_can), amount);
/*    */     
/* 37 */     if (StackUtil.storeInventoryItem(emptyStack, player, true)) {
/* 38 */       player.func_71024_bL().func_75122_a(amount, amount);
/* 39 */       stack = StackUtil.decSize(stack, amount);
/* 40 */       StackUtil.storeInventoryItem(emptyStack, player, false);
/* 41 */       IC2.platform.playSoundSp("Tools/eat.ogg", 1.0F, 1.0F);
/* 42 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */     } 
/*    */     
/* 45 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemTinCan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */