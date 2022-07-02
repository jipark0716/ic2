/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ 
/*     */ public class ItemGradualInt
/*     */   extends ItemIC2
/*     */   implements ICustomDamageItem
/*     */ {
/*     */   private static final boolean alwaysShowDurability = true;
/*     */   private static final String nbtKey = "advDmg";
/*     */   private final int maxDamage;
/*     */   
/*     */   public ItemGradualInt(ItemName name, int maxDamage) {
/*  24 */     super(name);
/*     */     
/*  26 */     setNoRepair();
/*     */     
/*  28 */     this.maxDamage = maxDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/*  34 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/*  42 */     return getCustomDamage(stack) / getMaxCustomDamage(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77645_m() {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/*  52 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemStack stack) {
/*  57 */     return getCustomDamage(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCustomDamage(ItemStack stack) {
/*  62 */     if (!stack.func_77942_o()) return 0;
/*     */     
/*  64 */     return stack.func_77978_p().func_74762_e("advDmg");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxDamage(ItemStack stack) {
/*  69 */     return getMaxCustomDamage(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxCustomDamage(ItemStack stack) {
/*  74 */     return this.maxDamage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/*  79 */     int prev = getCustomDamage(stack);
/*     */     
/*  81 */     if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
/*  82 */       IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid gradual item damage application (%d):", new Object[] { Integer.valueOf(damage - prev) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomDamage(ItemStack stack, int damage) {
/*  88 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*  89 */     nbt.func_74768_a("advDmg", damage);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/*  94 */     setCustomDamage(stack, getCustomDamage(stack) + damage);
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 100 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/* 103 */     ItemStack stack = new ItemStack(this);
/* 104 */     setCustomDamage(stack, 0);
/*     */     
/* 106 */     subItems.add(stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemGradualInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */