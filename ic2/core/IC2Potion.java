/*    */ package ic2.core;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ 
/*    */ public class IC2Potion extends Potion {
/*    */   public static IC2Potion radiation;
/*    */   
/*    */   public static void init() {
/* 15 */     radiation.func_76390_b("ic2.potion.radiation");
/* 16 */     radiation.func_76399_b(6, 0);
/* 17 */     radiation.func_76404_a(0.25D);
/*    */   }
/*    */ 
/*    */   
/*    */   private final List<ItemStack> curativeItems;
/*    */   
/*    */   public IC2Potion(String name, boolean badEffect, int liquidColor, ItemStack... curativeItems) {
/* 24 */     super(badEffect, liquidColor);
/*    */     
/* 26 */     this.curativeItems = Arrays.asList(curativeItems);
/*    */     
/* 28 */     ForgeRegistries.POTIONS.register(setRegistryName(name));
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_76394_a(EntityLivingBase entity, int amplifier) {
/* 33 */     if (this == radiation) {
/* 34 */       entity.func_70097_a(IC2DamageSource.radiation, (amplifier / 100) + 0.5F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_76397_a(int duration, int amplifier) {
/* 40 */     if (this == radiation) {
/* 41 */       int rate = 25 >> amplifier;
/* 42 */       return (rate > 0) ? ((duration % rate == 0)) : true;
/*    */     } 
/*    */     
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public void applyTo(EntityLivingBase entity, int duration, int amplifier) {
/* 49 */     PotionEffect effect = new PotionEffect(radiation, duration, amplifier);
/* 50 */     effect.setCurativeItems(this.curativeItems);
/*    */     
/* 52 */     entity.func_70690_d(effect);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\IC2Potion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */