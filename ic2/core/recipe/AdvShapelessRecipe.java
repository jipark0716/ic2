/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.recipe.ICraftingRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.init.Rezepte;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class AdvShapelessRecipe
/*     */   implements IRecipe {
/*     */   public ItemStack output;
/*     */   public IRecipeInput[] input;
/*     */   
/*     */   public static void addAndRegister(ItemStack result, Object... args) {
/*     */     try {
/*  28 */       Rezepte.registerRecipe(new AdvShapelessRecipe(result, args));
/*  29 */     } catch (RuntimeException e) {
/*  30 */       if (!MainConfig.ignoreInvalidRecipes) throw e;
/*     */     
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hidden;
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
/*     */ 
/*     */   
/*     */   public boolean consuming;
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
/*     */   
/*     */   private ResourceLocation name;
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
/*     */ 
/*     */   
/*     */   public AdvShapelessRecipe(ItemStack result, Object... args) {
/* 126 */     if (result == null) { AdvRecipe.displayError("null result", null, null, true); } else { result = result.func_77946_l(); }
/*     */     
/* 128 */     this.input = new IRecipeInput[args.length - Util.countInArray(args, new Class[] { Boolean.class, ICraftingRecipeManager.AttributeContainer.class })];
/* 129 */     int inputIndex = 0;
/*     */     
/* 131 */     for (Object o : args) {
/* 132 */       if (o instanceof Boolean) {
/* 133 */         this.hidden = ((Boolean)o).booleanValue();
/* 134 */       } else if (o instanceof ICraftingRecipeManager.AttributeContainer) {
/* 135 */         this.hidden = ((ICraftingRecipeManager.AttributeContainer)o).hidden;
/* 136 */         this.consuming = ((ICraftingRecipeManager.AttributeContainer)o).consuming;
/*     */       } else {
/*     */         try {
/* 139 */           this.input[inputIndex++] = AdvRecipe.getRecipeObject(o);
/* 140 */         } catch (Exception e) {
/* 141 */           e.printStackTrace();
/* 142 */           AdvRecipe.displayError("unknown type", "O: " + o + "\nT: " + o.getClass().getName(), result, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     if (inputIndex != this.input.length) AdvRecipe.displayError("length calculation error", "I: " + inputIndex + "\nL: " + this.input.length, result, true);
/*     */     
/* 149 */     this.output = result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting inventorycrafting, World world) {
/* 154 */     return (func_77572_b(inventorycrafting) != StackUtil.emptyStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting inventorycrafting) {
/* 159 */     int offerSize = inventorycrafting.func_70302_i_();
/* 160 */     if (offerSize < this.input.length) return StackUtil.emptyStack;
/*     */     
/* 162 */     List<IRecipeInput> unmatched = new Vector<>();
/* 163 */     for (IRecipeInput o : this.input) unmatched.add(o);
/*     */     
/* 165 */     double outputCharge = 0.0D;
/*     */     
/* 167 */     for (int i = 0; i < offerSize; i++) {
/* 168 */       ItemStack offer = inventorycrafting.func_70301_a(i);
/* 169 */       if (!StackUtil.isEmpty(offer)) {
/*     */         
/* 171 */         int j = 0; while (true) { if (j < unmatched.size()) {
/* 172 */             if (((IRecipeInput)unmatched.get(j)).matches(offer)) {
/* 173 */               outputCharge += ElectricItem.manager.getCharge(StackUtil.copyWithSize(offer, 1));
/* 174 */               unmatched.remove(j); break;
/*     */             } 
/*     */             j++;
/*     */             continue;
/*     */           } 
/* 179 */           return StackUtil.emptyStack; }
/*     */       
/*     */       } 
/* 182 */     }  if (!unmatched.isEmpty()) return StackUtil.emptyStack;
/*     */     
/* 184 */     ItemStack ret = this.output.func_77946_l();
/*     */     
/* 186 */     ElectricItem.manager.charge(ret, outputCharge, 2147483647, true, false);
/*     */     
/* 188 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 193 */     return this.output;
/*     */   }
/*     */   
/*     */   public boolean canShow() {
/* 197 */     return AdvRecipe.canShow((Object[])this.input, this.output, this.hidden);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> func_179532_b(InventoryCrafting inv) {
/* 203 */     return this.consuming ? NonNullList.func_191197_a(inv.func_70302_i_(), StackUtil.emptyStack) : ForgeHooks.defaultRecipeGetRemainingItems(inv);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe setRegistryName(ResourceLocation name) {
/* 208 */     this.name = name;
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getRegistryName() {
/* 214 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<IRecipe> getRegistryType() {
/* 219 */     return IRecipe.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int x, int y) {
/* 224 */     return (x * y >= this.input.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> func_192400_c() {
/* 230 */     NonNullList<Ingredient> list = NonNullList.func_191196_a();
/* 231 */     if (!this.hidden) {
/* 232 */       for (IRecipeInput input : this.input) {
/* 233 */         list.add(input.getIngredient());
/*     */       }
/*     */     }
/* 236 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 241 */     return this.hidden;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\AdvShapelessRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */