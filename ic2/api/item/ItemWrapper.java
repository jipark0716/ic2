/*    */ package ic2.api.item;
/*    */ 
/*    */ import com.google.common.collect.ArrayListMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemWrapper
/*    */ {
/* 16 */   private static final Multimap<Item, IBoxable> boxableItems = (Multimap<Item, IBoxable>)ArrayListMultimap.create();
/* 17 */   private static final Multimap<Item, IMetalArmor> metalArmorItems = (Multimap<Item, IMetalArmor>)ArrayListMultimap.create();
/*    */   
/*    */   public static void registerBoxable(Item item, IBoxable boxable) {
/* 20 */     boxableItems.put(item, boxable);
/*    */   }
/*    */   
/*    */   public static boolean canBeStoredInToolbox(ItemStack stack) {
/* 24 */     Item item = stack.func_77973_b();
/*    */     
/* 26 */     for (IBoxable boxable : boxableItems.get(item)) {
/* 27 */       if (boxable.canBeStoredInToolbox(stack)) return true;
/*    */     
/*    */     } 
/* 30 */     if (item instanceof IBoxable && ((IBoxable)item).canBeStoredInToolbox(stack)) return true;
/*    */     
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public static void registerMetalArmor(Item item, IMetalArmor armor) {
/* 36 */     metalArmorItems.put(item, armor);
/*    */   }
/*    */   
/*    */   public static boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
/* 40 */     Item item = stack.func_77973_b();
/*    */     
/* 42 */     for (IMetalArmor metalArmor : metalArmorItems.get(item)) {
/* 43 */       if (metalArmor.isMetalArmor(stack, player)) return true;
/*    */     
/*    */     } 
/* 46 */     if (item instanceof IMetalArmor && ((IMetalArmor)item).isMetalArmor(stack, player)) return true;
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\ItemWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */