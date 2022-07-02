/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2Potion;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemIodineTablet extends ItemIC2 {
/*    */   public ItemIodineTablet() {
/* 20 */     super(ItemName.iodine_tablet);
/*    */   }
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 25 */     ItemStack stack = StackUtil.get(player, hand);
/* 26 */     if (!world.field_72995_K) {
/* 27 */       return onEaten(player, stack);
/*    */     }
/*    */     
/* 30 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */   
/*    */   public ActionResult<ItemStack> onEaten(EntityPlayer player, ItemStack stack) {
/* 34 */     PotionEffect radiation = player.func_70660_b((Potion)IC2Potion.radiation);
/* 35 */     if (radiation == null) return new ActionResult(EnumActionResult.PASS, stack);
/*    */     
/* 37 */     int duration = radiation.func_76459_b() / 20;
/*    */     
/* 39 */     int amount = Math.min(StackUtil.getSize(stack), duration);
/* 40 */     if (amount <= 0) return new ActionResult(EnumActionResult.PASS, stack);
/*    */     
/* 42 */     player.func_184589_d((Potion)IC2Potion.radiation);
/*    */     
/* 44 */     if (amount < duration) {
/* 45 */       player.func_70690_d(new PotionEffect((Potion)IC2Potion.radiation, (duration - amount) * 20));
/*    */     }
/*    */     
/* 48 */     stack = StackUtil.decSize(stack, amount);
/* 49 */     IC2.platform.playSoundSp("Tools/eat.ogg", 1.0F, 1.0F);
/*    */     
/* 51 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemIodineTablet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */