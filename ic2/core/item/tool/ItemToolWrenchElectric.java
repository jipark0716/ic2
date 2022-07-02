/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.item.ElectricItemManager;
/*     */ import ic2.core.item.IPseudoDamageItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemToolWrenchElectric
/*     */   extends ItemToolWrench
/*     */   implements IPseudoDamageItem, IElectricItem, IItemHudInfo
/*     */ {
/*     */   public ItemToolWrenchElectric() {
/*  31 */     super(ItemName.electric_wrench);
/*     */     
/*  33 */     func_77656_e(27);
/*  34 */     func_77625_d(1);
/*  35 */     setNoRepair();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/*  40 */     List<String> info = new LinkedList<>();
/*  41 */     info.add(ElectricItem.manager.getToolTip(stack));
/*  42 */     return info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  50 */     amount *= 100;
/*     */     
/*  52 */     return (ElectricItem.manager.getCharge(stack) >= amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damage(ItemStack stack, int amount, EntityPlayer player) {
/*  60 */     ElectricItem.manager.use(stack, (100 * amount), (EntityLivingBase)player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  70 */     return 12000.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/*  75 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  80 */     return 250.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*  88 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/*  91 */     ElectricItemManager.addChargeVariants((Item)this, (List)subItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/* 101 */     int prev = getDamage(stack);
/*     */     
/* 103 */     if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
/* 104 */       IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", new Object[] { Integer.valueOf(damage - prev) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStackDamage(ItemStack stack, int damage) {
/* 110 */     super.setDamage(stack, damage);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolWrenchElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */