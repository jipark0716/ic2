/*     */ package ic2.core.item.armor.jetpack;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JetpackAttachmentRecipe
/*     */   implements IRecipe
/*     */ {
/*  35 */   private final IRecipeInput attachmentPlate = Recipes.inputFactory.forStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.jetpack_attachment_plate));
/*     */ 
/*     */ 
/*     */   
/*  39 */   public static final Set<Item> blacklistedItems = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */   static {
/*  41 */     blacklistedItems.add(ItemName.jetpack.getInstance());
/*  42 */     blacklistedItems.add(ItemName.jetpack_electric.getInstance());
/*  43 */     blacklistedItems.add(ItemName.quantum_chestplate.getInstance());
/*     */   }
/*     */   private ResourceLocation name;
/*     */   public static void init() {
/*  47 */     for (ItemStack stack : ConfigUtil.asStackList(MainConfig.get(), "recipes/jetpackAttachmentBlacklist")) {
/*  48 */       blacklistedItems.add(stack.func_77973_b());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting inv, World worldIn) {
/*  58 */     return (func_77572_b(inv) != StackUtil.emptyStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting inv) {
/*  63 */     ItemStack jetpack = null;
/*  64 */     ItemStack armor = null;
/*  65 */     boolean attachmentPlate = false;
/*  66 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/*  67 */       ItemStack currentStack = inv.func_70301_a(i);
/*  68 */       if (!StackUtil.isEmpty(currentStack)) {
/*  69 */         Item item = currentStack.func_77973_b();
/*  70 */         if (item == ItemName.jetpack_electric.getInstance()) {
/*  71 */           if (jetpack != null) return StackUtil.emptyStack; 
/*  72 */           jetpack = currentStack;
/*  73 */         } else if (EntityLiving.func_184640_d(currentStack) == EntityEquipmentSlot.CHEST && 
/*  74 */           !blacklistedItems.contains(item)) {
/*  75 */           if (armor != null) return StackUtil.emptyStack; 
/*  76 */           armor = currentStack;
/*  77 */         } else if (this.attachmentPlate.matches(currentStack)) {
/*  78 */           if (attachmentPlate) return StackUtil.emptyStack; 
/*  79 */           attachmentPlate = true;
/*     */         } else {
/*     */           
/*  82 */           return StackUtil.emptyStack;
/*     */         } 
/*     */       } 
/*  85 */     }  if (jetpack == null || armor == null || !attachmentPlate || JetpackHandler.hasJetpackAttached(armor)) return StackUtil.emptyStack; 
/*  86 */     ItemStack ret = armor.func_77946_l();
/*  87 */     JetpackHandler.setJetpackAttached(ret, true);
/*  88 */     ElectricItem.manager.charge(ret, ElectricItem.manager.getCharge(jetpack), 2147483647, true, false);
/*  89 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/*  95 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> func_179532_b(InventoryCrafting inv) {
/* 100 */     return ForgeHooks.defaultRecipeGetRemainingItems(inv);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe setRegistryName(ResourceLocation name) {
/* 105 */     this.name = name;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getRegistryName() {
/* 111 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<IRecipe> getRegistryType() {
/* 116 */     return IRecipe.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int x, int y) {
/* 121 */     return (x * y >= 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\JetpackAttachmentRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */