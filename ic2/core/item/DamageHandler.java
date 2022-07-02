/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ICustomDamageItem;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class DamageHandler {
/*    */   public static int getDamage(ItemStack stack) {
/* 12 */     Item item = stack.func_77973_b();
/* 13 */     if (item == null) return 0;
/*    */     
/* 15 */     if (item instanceof ICustomDamageItem) {
/* 16 */       return ((ICustomDamageItem)item).getCustomDamage(stack);
/*    */     }
/* 18 */     return stack.func_77952_i();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setDamage(ItemStack stack, int damage, boolean displayOnly) {
/* 23 */     Item item = stack.func_77973_b();
/* 24 */     if (item == null)
/*    */       return; 
/* 26 */     if (item instanceof ICustomDamageItem) {
/* 27 */       ((ICustomDamageItem)item).setCustomDamage(stack, damage);
/* 28 */     } else if (item instanceof IPseudoDamageItem) {
/* 29 */       if (!displayOnly) throw new IllegalStateException("can't damage " + stack + " physically");
/*    */       
/* 31 */       ((IPseudoDamageItem)item).setStackDamage(stack, damage);
/*    */     } else {
/* 33 */       stack.func_77964_b(damage);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getMaxDamage(ItemStack stack) {
/* 38 */     Item item = stack.func_77973_b();
/* 39 */     if (item == null) return 0;
/*    */     
/* 41 */     if (item instanceof ICustomDamageItem) {
/* 42 */       return ((ICustomDamageItem)item).getMaxCustomDamage(stack);
/*    */     }
/* 44 */     return stack.func_77958_k();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean damage(ItemStack stack, int damage, EntityLivingBase src) {
/* 49 */     Item item = stack.func_77973_b();
/* 50 */     if (item == null) return false;
/*    */     
/* 52 */     if (item instanceof ICustomDamageItem)
/* 53 */       return ((ICustomDamageItem)item).applyCustomDamage(stack, damage, src); 
/* 54 */     if (src != null) {
/* 55 */       stack.func_77972_a(damage, src);
/* 56 */       return true;
/*    */     } 
/* 58 */     return stack.func_96631_a(damage, IC2.random, (src instanceof EntityPlayerMP) ? (EntityPlayerMP)src : null);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\DamageHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */