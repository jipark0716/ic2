/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.api.item.IItemHudInfo;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import ic2.core.util.Util;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBatterySU
/*    */   extends ItemIC2
/*    */   implements IBoxable, IItemHudInfo
/*    */ {
/*    */   public int capacity;
/*    */   public int tier;
/*    */   
/*    */   public ItemBatterySU(ItemName internalName, int capacity1, int tier1) {
/* 26 */     super(internalName);
/*    */     
/* 28 */     this.capacity = capacity1;
/* 29 */     this.tier = tier1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 37 */     ItemStack stack = StackUtil.get(player, hand);
/* 38 */     double energy = this.capacity;
/*    */     
/* 40 */     for (int i = 0; i < 9 && energy > 0.0D; i++) {
/* 41 */       ItemStack target = (ItemStack)player.field_71071_by.field_70462_a.get(i);
/*    */       
/* 43 */       if (target != null && target != stack) {
/* 44 */         energy -= ElectricItem.manager.charge(target, energy, this.tier, true, false);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     if (!Util.isSimilar(energy, this.capacity)) {
/* 49 */       stack = StackUtil.decSize(stack);
/*    */       
/* 51 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */     } 
/*    */     
/* 54 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 64 */     List<String> info = new LinkedList<>();
/* 65 */     info.add(this.capacity + " EU");
/* 66 */     return info;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemBatterySU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */