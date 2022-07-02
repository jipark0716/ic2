/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.init.Rezepte;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class GradualRecipe
/*     */   implements IRecipe
/*     */ {
/*     */   public ICustomDamageItem item;
/*     */   public ItemStack chargeMaterial;
/*     */   public int amount;
/*     */   public boolean hidden;
/*     */   private ResourceLocation name;
/*     */   
/*     */   public static void addAndRegister(ItemStack itemToFill, int amount, Object... args) {
/*     */     try {
/*  27 */       if (itemToFill == null) {
/*  28 */         AdvRecipe.displayError("Null item to fill", null, null, true);
/*     */       } else {
/*  30 */         if (!(itemToFill.func_77973_b() instanceof ICustomDamageItem)) {
/*  31 */           AdvRecipe.displayError("Filling item must extends ItemGradualInt", null, itemToFill, true);
/*     */         }
/*  33 */         ICustomDamageItem fillingItem = (ICustomDamageItem)itemToFill.func_77973_b();
/*     */         
/*  35 */         Boolean hidden = Boolean.valueOf(false);
/*  36 */         ItemStack filler = null;
/*  37 */         for (Object o : args) {
/*  38 */           if (o instanceof Boolean) {
/*  39 */             hidden = (Boolean)o;
/*     */           } else {
/*     */             try {
/*  42 */               filler = AdvRecipe.getRecipeObject(o).getInputs().get(0);
/*     */               break;
/*  44 */             } catch (IndexOutOfBoundsException e) {
/*  45 */               AdvRecipe.displayError("Invalid filler item: " + o, null, itemToFill, true);
/*  46 */             } catch (Exception e) {
/*  47 */               e.printStackTrace();
/*  48 */               AdvRecipe.displayError("unknown type", "O: " + o + "\nT: " + o.getClass().getName(), itemToFill, true);
/*     */             } 
/*     */           } 
/*     */         } 
/*  52 */         Rezepte.registerRecipe(new GradualRecipe(fillingItem, filler, amount, hidden.booleanValue()));
/*     */       } 
/*  54 */     } catch (RuntimeException e) {
/*  55 */       if (!MainConfig.ignoreInvalidRecipes) throw e; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public GradualRecipe(ICustomDamageItem item, ItemStack chargeMaterial, int amount) {
/*  60 */     this(item, chargeMaterial, amount, false);
/*     */   }
/*     */   
/*     */   public GradualRecipe(ICustomDamageItem item, ItemStack chargeMaterial, int amount, boolean hidden) {
/*  64 */     this.item = item;
/*  65 */     this.chargeMaterial = chargeMaterial;
/*  66 */     this.amount = amount;
/*  67 */     this.hidden = hidden;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting ic, World world) {
/*  72 */     return (func_77572_b(ic) != StackUtil.emptyStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting ic) {
/*  77 */     ItemStack gridItem = null;
/*  78 */     int chargeMats = 0;
/*     */     
/*  80 */     for (int slot = 0; slot < ic.func_70302_i_(); slot++) {
/*  81 */       ItemStack stack = ic.func_70301_a(slot);
/*  82 */       if (!StackUtil.isEmpty(stack))
/*     */       {
/*  84 */         if (gridItem == null && stack.func_77973_b() == this.item) {
/*  85 */           gridItem = stack;
/*  86 */         } else if (StackUtil.checkItemEquality(stack, this.chargeMaterial)) {
/*     */           
/*  88 */           chargeMats++;
/*     */         } else {
/*  90 */           return StackUtil.emptyStack;
/*     */         } 
/*     */       }
/*     */     } 
/*  94 */     if (gridItem != null && chargeMats > 0) {
/*  95 */       ItemStack stack = gridItem.func_77946_l();
/*  96 */       int damage = this.item.getCustomDamage(stack) - this.amount * chargeMats;
/*  97 */       if (damage > this.item.getMaxCustomDamage(stack)) {
/*  98 */         damage = this.item.getMaxCustomDamage(stack);
/*  99 */       } else if (damage < 0) {
/* 100 */         damage = 0;
/*     */       } 
/* 102 */       this.item.setCustomDamage(stack, damage);
/*     */       
/* 104 */       return stack;
/*     */     } 
/* 106 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 112 */     return new ItemStack((Item)this.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> func_179532_b(InventoryCrafting inv) {
/* 117 */     return ForgeHooks.defaultRecipeGetRemainingItems(inv);
/*     */   }
/*     */   
/*     */   public boolean canShow() {
/* 121 */     return AdvRecipe.canShow(new Object[] { this.chargeMaterial }, func_77571_b(), this.hidden);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe setRegistryName(ResourceLocation name) {
/* 126 */     this.name = name;
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getRegistryName() {
/* 132 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<IRecipe> getRegistryType() {
/* 137 */     return IRecipe.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int x, int y) {
/* 142 */     return (x * y >= 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\GradualRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */