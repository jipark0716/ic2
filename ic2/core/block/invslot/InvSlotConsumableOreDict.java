/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ public class InvSlotConsumableOreDict extends InvSlotConsumable {
/*    */   protected final String oreDict;
/*    */   
/*    */   public InvSlotConsumableOreDict(IInventorySlotHolder<?> base, String name, int count, String oreDict) {
/* 12 */     super(base, name, count);
/*    */     
/* 14 */     this.oreDict = oreDict;
/*    */   }
/*    */   
/*    */   public InvSlotConsumableOreDict(IInventorySlotHolder<?> base, String name, InvSlot.Access access, int count, InvSlot.InvSide side, String oreDict) {
/* 18 */     super(base, name, access, count, side);
/*    */     
/* 20 */     this.oreDict = oreDict;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 25 */     if (StackUtil.isEmpty(stack)) return false;
/*    */     
/* 27 */     for (int ID : OreDictionary.getOreIDs(stack)) {
/* 28 */       if (this.oreDict.equals(OreDictionary.getOreName(ID))) {
/* 29 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableOreDict.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */