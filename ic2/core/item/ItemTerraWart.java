/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2Potion;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class ItemTerraWart
/*    */   extends ItemFoodIc2
/*    */ {
/*    */   public ItemTerraWart() {
/* 20 */     super(ItemName.terra_wart, 0, 1.0F, false);
/*    */     
/* 22 */     func_77848_i();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_77654_b(ItemStack stack, World world, EntityLivingBase player) {
/* 30 */     IC2.platform.removePotion(player, MobEffects.field_76431_k);
/* 31 */     IC2.platform.removePotion(player, MobEffects.field_76419_f);
/* 32 */     IC2.platform.removePotion(player, MobEffects.field_76438_s);
/* 33 */     IC2.platform.removePotion(player, MobEffects.field_76421_d);
/* 34 */     IC2.platform.removePotion(player, MobEffects.field_76437_t);
/* 35 */     IC2.platform.removePotion(player, MobEffects.field_76440_q);
/* 36 */     IC2.platform.removePotion(player, MobEffects.field_76436_u);
/* 37 */     IC2.platform.removePotion(player, MobEffects.field_82731_v);
/*    */     
/* 39 */     PotionEffect effect = player.func_70660_b((Potion)IC2Potion.radiation);
/*    */     
/* 41 */     if (effect != null) {
/* 42 */       if (effect.func_76459_b() <= 600) {
/* 43 */         IC2.platform.removePotion(player, (Potion)IC2Potion.radiation);
/*    */       } else {
/* 45 */         IC2.platform.removePotion(player, (Potion)IC2Potion.radiation);
/* 46 */         IC2Potion.radiation.applyTo(player, effect.func_76459_b() - 600, effect.func_76458_c());
/*    */       } 
/*    */     }
/*    */     
/* 50 */     return super.func_77654_b(stack, world, player);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 56 */     return EnumRarity.RARE;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemTerraWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */