/*    */ package ic2.core.slot;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ 
/*    */ public class ArmorSlot
/*    */ {
/*    */   public static EntityEquipmentSlot get(int index) {
/* 12 */     return armorSlots[index];
/*    */   }
/*    */   
/*    */   public static int getCount() {
/* 16 */     return armorSlots.length;
/*    */   }
/*    */   
/*    */   public static Iterable<EntityEquipmentSlot> getAll() {
/* 20 */     return armorSlotList;
/*    */   }
/*    */   
/*    */   private static EntityEquipmentSlot[] getArmorSlots() {
/* 24 */     EntityEquipmentSlot[] values = EntityEquipmentSlot.values();
/* 25 */     int count = 0;
/*    */     
/* 27 */     for (EntityEquipmentSlot slot : values) {
/* 28 */       if (slot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) count++;
/*    */     
/*    */     } 
/* 31 */     EntityEquipmentSlot[] ret = new EntityEquipmentSlot[count];
/*    */     int i;
/* 33 */     for (i = 0; i < ret.length; i++) {
/* 34 */       for (EntityEquipmentSlot slot : values) {
/* 35 */         if (slot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR && slot.func_188454_b() == i) {
/* 36 */           ret[i] = slot;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 42 */     for (i = 0; i < ret.length; i++) {
/* 43 */       if (ret[i] == null) throw new RuntimeException("Can't find an armor mapping for idx " + i);
/*    */     
/*    */     } 
/* 46 */     return ret;
/*    */   }
/*    */   
/* 49 */   private static final EntityEquipmentSlot[] armorSlots = getArmorSlots();
/* 50 */   private static final List<EntityEquipmentSlot> armorSlotList = Collections.unmodifiableList(Arrays.asList(armorSlots));
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\ArmorSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */