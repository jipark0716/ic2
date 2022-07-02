/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*     */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBattery
/*     */   extends BaseElectricItem
/*     */ {
/*     */   public ItemBattery(ItemName name, double maxCharge, double transferLimit, int tier) {
/*  34 */     super(name, maxCharge, transferLimit, tier);
/*     */     
/*  36 */     func_77625_d(16);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(final ItemName name) {
/*  42 */     ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition()
/*     */         {
/*     */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/*  45 */             int level, damage = stack.func_77952_i();
/*  46 */             int maxDamage = stack.func_77958_k() - 1;
/*     */ 
/*     */             
/*  49 */             if (maxDamage > 0) {
/*  50 */               level = Util.limit((damage * ItemBattery.maxLevel + maxDamage / 2) / maxDamage, 0, ItemBattery.maxLevel);
/*     */             } else {
/*  52 */               level = 0;
/*     */             } 
/*     */             
/*  55 */             return ItemIC2.getModelLocation(name, Integer.toString(ItemBattery.maxLevel - level));
/*     */           }
/*     */         });
/*     */     
/*  59 */     for (int level = 0; level <= maxLevel; level++) {
/*  60 */       ModelBakery.registerItemVariants(this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, Integer.toString(level)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  75 */     ItemStack stack = StackUtil.get(player, hand);
/*  76 */     if (world.field_72995_K || StackUtil.getSize(stack) != 1) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/*  78 */     if (ElectricItem.manager.getCharge(stack) > 0.0D) {
/*  79 */       boolean transferred = false;
/*     */       
/*  81 */       for (int i = 0; i < 9; i++) {
/*  82 */         ItemStack target = (ItemStack)player.field_71071_by.field_70462_a.get(i);
/*  83 */         if (target != null && target != stack)
/*     */         {
/*     */           
/*  86 */           if (ElectricItem.manager.discharge(target, Double.POSITIVE_INFINITY, 2147483647, true, true, true) <= 0.0D) {
/*     */ 
/*     */             
/*  89 */             double transfer = ElectricItem.manager.discharge(stack, 2.0D * this.transferLimit, 2147483647, true, true, true);
/*  90 */             if (transfer > 0.0D) {
/*     */ 
/*     */               
/*  93 */               transfer = ElectricItem.manager.charge(target, transfer, this.tier, true, false);
/*  94 */               if (transfer > 0.0D)
/*     */               
/*     */               { 
/*  97 */                 ElectricItem.manager.discharge(stack, transfer, 2147483647, true, true, false);
/*  98 */                 transferred = true; } 
/*     */             } 
/*     */           }  } 
/* 101 */       }  if (transferred && !world.field_72995_K) {
/* 102 */         player.field_71070_bA.func_75142_b();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */   
/* 111 */   private static int maxLevel = 4;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemBattery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */