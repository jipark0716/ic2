/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.oredict.OreDictionary;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MachineRecipeHelper<RI, RO>
/*     */   implements IMachineRecipeManager<RI, RO, ItemStack>
/*     */ {
/*     */   protected IRecipeInput getForRecipe(MachineRecipe<RI, RO> recipe) {
/*  55 */     return getForInput((RI)recipe.getInput());
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
/*     */   protected boolean consumeContainer(ItemStack input, ItemStack container, MachineRecipe<RI, RO> recipe) {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MachineRecipeResult<RI, RO, ItemStack> apply(ItemStack input, boolean acceptTest) {
/*  73 */     if (StackUtil.isEmpty(input)) return null;
/*     */     
/*  75 */     MachineRecipe<RI, RO> recipe = getRecipe(input);
/*  76 */     if (recipe == null) return null;
/*     */     
/*  78 */     IRecipeInput recipeInput = getForRecipe(recipe);
/*  79 */     if (StackUtil.getSize(input) < recipeInput.getAmount()) return null;
/*     */ 
/*     */     
/*     */     ItemStack adjustedInput;
/*  83 */     if (input.func_77973_b().hasContainerItem(input) && 
/*  84 */       !StackUtil.isEmpty(adjustedInput = input.func_77973_b().getContainerItem(input)) && !acceptTest && 
/*  85 */       !consumeContainer(input, adjustedInput, recipe)) {
/*  86 */       if (!acceptTest && StackUtil.getSize(input) != recipeInput.getAmount()) return null;
/*     */       
/*  88 */       adjustedInput = StackUtil.copy(adjustedInput);
/*     */     } else {
/*  90 */       adjustedInput = StackUtil.copyWithSize(input, StackUtil.getSize(input) - recipeInput.getAmount());
/*     */     } 
/*     */     
/*  93 */     return recipe.getResult(adjustedInput);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<? extends MachineRecipe<RI, RO>> getRecipes() {
/*  98 */     return new Iterable<MachineRecipe<RI, RO>>()
/*     */       {
/*     */         public Iterator<MachineRecipe<RI, RO>> iterator() {
/* 101 */           return new Iterator<MachineRecipe<RI, RO>>()
/*     */             {
/*     */               public boolean hasNext() {
/* 104 */                 return this.recipeIt.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public MachineRecipe<RI, RO> next() {
/* 109 */                 MachineRecipe<RI, RO> next = this.recipeIt.next();
/* 110 */                 this.lastInput = (RI)next.getInput();
/*     */                 
/* 112 */                 return next;
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 117 */                 this.recipeIt.remove();
/* 118 */                 MachineRecipeHelper.this.removeCachedRecipes(this.lastInput);
/*     */               }
/*     */               
/* 121 */               private final Iterator<MachineRecipe<RI, RO>> recipeIt = MachineRecipeHelper.this.recipes.values().iterator();
/*     */               
/*     */               private RI lastInput;
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean isIterable() {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MachineRecipe<RI, RO> getRecipe(ItemStack input) {
/* 141 */     if (StackUtil.isEmpty(input)) return null;
/*     */     
/* 143 */     List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(input.func_77973_b());
/*     */     
/* 145 */     if (recipes != null) {
/* 146 */       for (MachineRecipe<RI, RO> recipe : recipes) {
/* 147 */         if (getForRecipe(recipe).matches(input)) return recipe;
/*     */       
/*     */       } 
/*     */     }
/* 151 */     for (MachineRecipe<RI, RO> recipe : this.uncacheableRecipes) {
/* 152 */       if (getForRecipe(recipe).matches(input)) {
/* 153 */         return recipe;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addToCache(MachineRecipe<RI, RO> recipe) {
/* 166 */     Collection<Item> items = getItemsFromRecipe((RI)recipe.getInput());
/*     */     
/* 168 */     if (items != null) {
/* 169 */       for (Item item : items) addToCache(item, recipe);
/*     */       
/* 171 */       if (recipe.getInput().getClass() == RecipeInputOreDict.class) {
/* 172 */         if (!oreRegisterEventSubscribed) {
/* 173 */           MinecraftForge.EVENT_BUS.register(MachineRecipeHelper.class);
/*     */           
/* 175 */           oreRegisterEventSubscribed = true;
/*     */         } 
/*     */         
/* 178 */         watchingManagers.add(this);
/*     */       } 
/*     */     } else {
/* 181 */       this.uncacheableRecipes.add(recipe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addToCache(Item item, MachineRecipe<RI, RO> recipe) {
/* 186 */     List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(item);
/*     */     
/* 188 */     if (recipes == null) {
/* 189 */       recipes = new ArrayList<>();
/* 190 */       this.recipeCache.put(item, recipes);
/*     */     } 
/*     */     
/* 193 */     if (!recipes.contains(recipe)) recipes.add(recipe);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeCachedRecipes(RI input) {
/* 202 */     Collection<Item> items = getItemsFromRecipe(input);
/*     */     
/* 204 */     if (items != null) {
/* 205 */       for (Item item : items) {
/* 206 */         List<MachineRecipe<RI, RO>> recipes = this.recipeCache.get(item);
/*     */         
/* 208 */         if (recipes == null) {
/* 209 */           IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + " is missing.");
/*     */           
/*     */           continue;
/*     */         } 
/* 213 */         removeInputFromRecipes(recipes.iterator(), input);
/*     */         
/* 215 */         if (recipes.isEmpty()) this.recipeCache.remove(item); 
/*     */       } 
/*     */     } else {
/* 218 */       removeInputFromRecipes(this.uncacheableRecipes.iterator(), input);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeInputFromRecipes(Iterator<MachineRecipe<RI, RO>> it, RI target) {
/* 223 */     assert target != null;
/*     */     
/* 225 */     while (it.hasNext()) {
/* 226 */       if (target.equals(((MachineRecipe)it.next()).getInput())) {
/* 227 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection<Item> getItemsFromRecipe(RI input) {
/* 233 */     return getItemsFromRecipe(getForInput(input));
/*     */   }
/*     */   
/*     */   private Collection<Item> getItemsFromRecipe(IRecipeInput recipe) {
/* 237 */     Class<?> recipeClass = recipe.getClass();
/*     */     
/* 239 */     if (recipeClass == RecipeInputItemStack.class || recipeClass == RecipeInputOreDict.class) {
/*     */       
/* 241 */       List<ItemStack> inputs = recipe.getInputs();
/* 242 */       Set<Item> ret = Collections.newSetFromMap(new IdentityHashMap<>(inputs.size()));
/*     */       
/* 244 */       for (ItemStack stack : inputs) {
/* 245 */         ret.add(stack.func_77973_b());
/*     */       }
/*     */       
/* 248 */       return ret;
/*     */     } 
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onOreRegister(Item item, String name) {
/* 255 */     for (MachineRecipe<RI, RO> rawRecipe : this.recipes.values()) {
/* 256 */       if (rawRecipe.getInput().getClass() != RecipeInputOreDict.class)
/*     */         continue; 
/* 258 */       RecipeInputOreDict recipe = (RecipeInputOreDict)rawRecipe.getInput();
/*     */       
/* 260 */       if (recipe.input.equals(name)) {
/* 261 */         addToCache(item, rawRecipe);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 266 */   protected final Map<RI, MachineRecipe<RI, RO>> recipes = new HashMap<>();
/* 267 */   private final Map<Item, List<MachineRecipe<RI, RO>>> recipeCache = new IdentityHashMap<>();
/* 268 */   private final List<MachineRecipe<RI, RO>> uncacheableRecipes = new ArrayList<>();
/*     */   private static boolean oreRegisterEventSubscribed;
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
/* 273 */     Item item = event.getOre().func_77973_b();
/*     */     
/* 275 */     if (item == null) {
/* 276 */       IC2.log.warn(LogCategory.Recipe, "Found null item ore dict registration.", new Object[] { new Throwable() });
/*     */       
/*     */       return;
/*     */     } 
/* 280 */     for (MachineRecipeHelper<?, ?> manager : watchingManagers) {
/* 281 */       manager.onOreRegister(item, event.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 286 */   private static final Set<MachineRecipeHelper<?, ?>> watchingManagers = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */   
/*     */   protected abstract IRecipeInput getForInput(RI paramRI);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\MachineRecipeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */