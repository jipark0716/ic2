/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CreativeTabIC2
/*    */   extends CreativeTabs {
/*    */   private static ItemStack a;
/*    */   private static ItemStack b;
/*    */   private static ItemStack z;
/*    */   private int ticker;
/*    */   
/*    */   public CreativeTabIC2() {
/* 16 */     super("IC2");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_151244_d() {
/* 21 */     if (IC2.seasonal) {
/* 22 */       if (a == null) a = new ItemStack(Items.field_151144_bL, 1, 2); 
/* 23 */       if (b == null) b = new ItemStack(Items.field_151144_bL, 1, 0); 
/* 24 */       if (z == null) z = ItemName.nano_chestplate.getItemStack();
/*    */       
/* 26 */       if (++this.ticker >= 5000) this.ticker = 0; 
/* 27 */       if (this.ticker >= 2500) return (this.ticker < 3000) ? a : ((this.ticker < 4500) ? b : z);
/*    */     
/*    */     } 
/* 30 */     return ItemName.mining_laser.getItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_78016_d() {
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\CreativeTabIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */