/*     */ package ic2.core.recipe.dynamic;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class DynamicRecipeManager
/*     */   implements IDynamicRecipeManager
/*     */ {
/*     */   public DynamicRecipe createRecipe() {
/*  37 */     return new DynamicRecipe(this);
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
/*     */   public boolean addRecipe(DynamicRecipe recipe, boolean replace) {
/*  57 */     if (recipe.getInputIngredients() == null) {
/*  58 */       throw new NullPointerException("The recipe input is null");
/*     */     }
/*     */     
/*  61 */     if (recipe.getInputIngredients().size() <= 0) {
/*  62 */       throw new IllegalArgumentException("No inputs");
/*     */     }
/*     */     
/*  65 */     if (recipe.getOutputIngredients() == null) {
/*  66 */       throw new NullPointerException("The recipe output is null");
/*     */     }
/*     */     
/*  69 */     if (recipe.getOutputIngredients().size() <= 0) {
/*  70 */       throw new IllegalArgumentException("No outputs");
/*     */     }
/*     */     
/*  73 */     List<RecipeInputIngredient> listOfInputs = new ArrayList<>(recipe.getInputIngredients().size());
/*  74 */     for (RecipeInputIngredient entry : recipe.getInputIngredients()) {
/*  75 */       if (entry.isEmpty()) {
/*  76 */         displayError("The RecipeInputIngredient " + entry.toStringSafe() + " is invalid.");
/*  77 */         return false;
/*     */       } 
/*     */       
/*  80 */       listOfInputs.add(entry);
/*     */     } 
/*     */     
/*  83 */     List<RecipeOutputIngredient> listOfOutputs = new ArrayList<>(recipe.getOutputIngredients().size());
/*  84 */     for (RecipeOutputIngredient entry : recipe.getOutputIngredients()) {
/*  85 */       if (entry.isEmpty()) {
/*  86 */         displayError("The RecipeOutputIngredient " + entry.toStringSafe() + " is invalid.");
/*  87 */         return false;
/*     */       } 
/*     */       
/*  90 */       listOfOutputs.add(entry);
/*     */     } 
/*     */     
/*  93 */     DynamicRecipe temp = getRecipe(recipe.getInputIngredients());
/*     */     
/*  95 */     if (temp != null) {
/*  96 */       if (replace) {
/*     */         do {
/*  98 */           this.recipes.remove(recipe.getInputIngredients());
/*  99 */           removeCachedRecipes(recipe.getInputIngredients());
/* 100 */           recipe = getRecipe(recipe.getInputIngredients());
/* 101 */         } while (recipe != null);
/*     */       } else {
/* 103 */         IC2.log.error(LogCategory.Recipe, "Skipping %s => %s due to duplicate recipe for %s (%s => %s)", new Object[] { recipe.getInputIngredients(), recipe.getOutputIngredients(), recipe.getInputIngredients(), recipe.getInputIngredients(), recipe.getOutputIngredients() });
/* 104 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 108 */     DynamicRecipe newRecipe = createRecipe().withInput(listOfInputs).withOutput(listOfOutputs).withOperationEnergyCost(recipe.getOperationEnergyCost()).withOperationDurationTicks(recipe.getOperationDuration()).withMetadata(recipe.getMetadata());
/*     */     
/* 110 */     this.recipes.put(recipe.getInputIngredients(), newRecipe);
/* 111 */     addToCache(newRecipe);
/*     */     
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DynamicRecipe getRecipe(Collection<RecipeInputIngredient> input) {
/* 122 */     if (input.isEmpty()) {
/* 123 */       return null;
/*     */     }
/*     */     
/* 126 */     List<DynamicRecipe> recipes = new ArrayList<>();
/*     */     
/* 128 */     for (RecipeInputIngredient entry : input) {
/* 129 */       Object unspecific = entry.getUnspecific();
/* 130 */       if (unspecific instanceof Item) {
/* 131 */         if (this.recipeCacheItem.get(unspecific) != null)
/* 132 */           recipes.addAll(this.recipeCacheItem.get(unspecific));  continue;
/*     */       } 
/* 134 */       if (unspecific instanceof Fluid && 
/* 135 */         this.recipeCacheFluid.containsKey(((Fluid)unspecific).getName())) {
/* 136 */         recipes.addAll(this.recipeCacheFluid.get(((Fluid)unspecific).getName()));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (!recipes.isEmpty()) {
/* 143 */       label61: for (DynamicRecipe recipe : recipes) {
/* 144 */         if (input.size() != recipe.getInputIngredients().size()) {
/*     */           continue;
/*     */         }
/*     */         
/* 148 */         ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 149 */         for (RecipeInputIngredient entry : input) {
/* 150 */           while (itB.hasNext()) {
/* 151 */             RecipeInputIngredient temp = itB.next();
/* 152 */             if (temp.matches(entry.ingredient) && 
/* 153 */               entry.getCount() >= temp.getCount()) {
/* 154 */               itB.remove();
/*     */ 
/*     */               
/* 157 */               while (itB.hasPrevious()) {
/* 158 */                 itB.previous();
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           continue label61;
/*     */         } 
/*     */ 
/*     */         
/* 169 */         return recipe;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 174 */     label62: for (DynamicRecipe recipe : this.uncacheableRecipes) {
/* 175 */       if (input.size() != recipe.getInputIngredients().size()) {
/*     */         continue;
/*     */       }
/*     */       
/* 179 */       ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 180 */       for (RecipeInputIngredient entry : input) {
/* 181 */         while (itB.hasNext()) {
/* 182 */           RecipeInputIngredient temp = itB.next();
/* 183 */           if (temp.matches(entry.ingredient) && 
/* 184 */             entry.getCount() >= temp.getCount()) {
/* 185 */             itB.remove();
/*     */ 
/*     */             
/* 188 */             while (itB.hasPrevious()) {
/* 189 */               itB.previous();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         continue label62;
/*     */       } 
/*     */ 
/*     */       
/* 200 */       return recipe;
/*     */     } 
/*     */     
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicRecipe findRecipe(ItemStack[] items, FluidStack[] fluids) {
/* 214 */     List<RecipeInputIngredient> inputs = new ArrayList<>();
/* 215 */     for (ItemStack stack : items) {
/* 216 */       if (StackUtil.isEmpty(stack)) {
/* 217 */         return null;
/*     */       }
/*     */       
/* 220 */       inputs.add(RecipeInputItemStack.of(stack));
/*     */     } 
/*     */     
/* 223 */     for (FluidStack stack : fluids) {
/* 224 */       if (stack.amount <= 0) {
/* 225 */         return null;
/*     */       }
/*     */       
/* 228 */       inputs.add(RecipeInputFluidStack.of(stack));
/*     */     } 
/*     */     
/* 231 */     if (inputs.isEmpty()) return null;
/*     */     
/* 233 */     List<DynamicRecipe> recipes = new ArrayList<>();
/*     */     
/* 235 */     for (RecipeInputIngredient entry : inputs) {
/* 236 */       Object unspecific = entry.getUnspecific();
/* 237 */       if (unspecific instanceof Item) {
/* 238 */         if (this.recipeCacheItem.get(unspecific) != null)
/* 239 */           recipes.addAll(this.recipeCacheItem.get(unspecific));  continue;
/*     */       } 
/* 241 */       if (unspecific instanceof Fluid && 
/* 242 */         this.recipeCacheFluid.containsKey(((Fluid)unspecific).getName())) {
/* 243 */         recipes.addAll(this.recipeCacheFluid.get(((Fluid)unspecific).getName()));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (!recipes.isEmpty()) {
/* 250 */       label79: for (DynamicRecipe recipe : recipes) {
/* 251 */         if (inputs.size() != recipe.getInputIngredients().size()) {
/*     */           continue;
/*     */         }
/*     */         
/* 255 */         ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 256 */         for (RecipeInputIngredient entry : inputs) {
/* 257 */           while (itB.hasNext()) {
/* 258 */             RecipeInputIngredient temp = itB.next();
/* 259 */             if (temp.matches(entry.ingredient) && 
/* 260 */               entry.getCount() >= temp.getCount()) {
/* 261 */               itB.remove();
/*     */ 
/*     */               
/* 264 */               while (itB.hasPrevious()) {
/* 265 */                 itB.previous();
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           continue label79;
/*     */         } 
/*     */ 
/*     */         
/* 276 */         return recipe;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 281 */     label80: for (DynamicRecipe recipe : this.uncacheableRecipes) {
/* 282 */       if (inputs.size() != recipe.getInputIngredients().size()) {
/*     */         continue;
/*     */       }
/*     */       
/* 286 */       ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 287 */       for (RecipeInputIngredient entry : inputs) {
/* 288 */         while (itB.hasNext()) {
/* 289 */           RecipeInputIngredient temp = itB.next();
/* 290 */           if (temp.matches(entry.ingredient) && 
/* 291 */             entry.getCount() >= temp.getCount()) {
/* 292 */             itB.remove();
/*     */ 
/*     */             
/* 295 */             while (itB.hasPrevious()) {
/* 296 */               itB.previous();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         continue label80;
/*     */       } 
/*     */ 
/*     */       
/* 307 */       return recipe;
/*     */     } 
/*     */     
/* 310 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isPartOfRecipe(ItemStack stack) {
/* 314 */     if (StackUtil.isEmpty(stack)) return false;
/*     */     
/* 316 */     RecipeInputItemStack subject = RecipeInputItemStack.of(stack);
/*     */     
/* 318 */     List<DynamicRecipe> recipes = new ArrayList<>();
/*     */     
/* 320 */     Object unspecific = subject.getUnspecific();
/* 321 */     if (unspecific instanceof Item) {
/* 322 */       if (this.recipeCacheItem.get(unspecific) != null) {
/* 323 */         recipes.addAll(this.recipeCacheItem.get(unspecific));
/*     */       }
/* 325 */     } else if (unspecific instanceof Fluid && 
/* 326 */       this.recipeCacheFluid.containsKey(((Fluid)unspecific).getName())) {
/* 327 */       recipes.addAll(this.recipeCacheFluid.get(((Fluid)unspecific).getName()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 332 */     if (!recipes.isEmpty()) {
/* 333 */       for (DynamicRecipe recipe : recipes) {
/* 334 */         ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 335 */         while (itB.hasNext()) {
/* 336 */           RecipeInputIngredient temp = itB.next();
/* 337 */           if (temp.matches(subject.ingredient)) return true;
/*     */         
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 343 */     if (!this.uncacheableRecipes.isEmpty()) {
/* 344 */       for (DynamicRecipe recipe : this.uncacheableRecipes) {
/* 345 */         ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 346 */         RecipeInputIngredient temp = itB.next();
/* 347 */         if (temp.matches(subject.ingredient)) return true;
/*     */       
/*     */       } 
/*     */     }
/* 351 */     return false;
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
/*     */   public DynamicRecipe apply(ItemStack[] items, FluidStack[] fluids, boolean simulate) {
/* 365 */     List<RecipeInputIngredient> inputs = new ArrayList<>();
/* 366 */     for (ItemStack stack : items) {
/* 367 */       if (StackUtil.isEmpty(stack)) {
/* 368 */         return null;
/*     */       }
/*     */       
/* 371 */       inputs.add(RecipeInputItemStack.of(stack));
/*     */     } 
/*     */     
/* 374 */     for (FluidStack stack : fluids) {
/* 375 */       if (stack.amount <= 0) {
/* 376 */         return null;
/*     */       }
/*     */       
/* 379 */       inputs.add(RecipeInputFluidStack.of(stack));
/*     */     } 
/*     */     
/* 382 */     DynamicRecipe recipe = getRecipe(inputs);
/*     */     
/* 384 */     if (recipe == null) {
/* 385 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 389 */     if (items.length + fluids.length != recipe.getInputIngredients().size()) {
/* 390 */       return null;
/*     */     }
/*     */     
/* 393 */     ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 394 */     for (RecipeInputIngredient entry : inputs) {
/* 395 */       while (itB.hasNext()) {
/* 396 */         RecipeInputIngredient temp = itB.next();
/* 397 */         if (temp.matches(entry.ingredient) && 
/* 398 */           entry.getCount() >= temp.getCount()) {
/* 399 */           itB.remove();
/*     */ 
/*     */           
/* 402 */           while (itB.hasPrevious()) {
/* 403 */             itB.previous();
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 411 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 415 */     if (!simulate) {
/* 416 */       itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 417 */       for (RecipeInputIngredient entry : inputs) {
/* 418 */         while (itB.hasNext()) {
/* 419 */           RecipeInputIngredient temp = itB.next();
/* 420 */           if (temp.matches(entry.ingredient)) {
/* 421 */             entry.shrink(temp.getCount());
/*     */             
/* 423 */             itB.remove();
/*     */ 
/*     */             
/* 426 */             while (itB.hasPrevious()) {
/* 427 */               itB.previous();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 434 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 438 */     return recipe;
/*     */   }
/*     */   
/*     */   public boolean apply(DynamicRecipe recipe, ItemStack[] items, FluidStack[] fluids, boolean simulate) {
/* 442 */     if (recipe == null) {
/* 443 */       return false;
/*     */     }
/*     */     
/* 446 */     List<RecipeInputIngredient> inputs = new ArrayList<>();
/* 447 */     for (ItemStack stack : items) {
/* 448 */       if (StackUtil.isEmpty(stack)) {
/* 449 */         return false;
/*     */       }
/*     */       
/* 452 */       inputs.add(RecipeInputItemStack.of(stack));
/*     */     } 
/*     */     
/* 455 */     for (FluidStack stack : fluids) {
/* 456 */       if (stack.amount <= 0) {
/* 457 */         return false;
/*     */       }
/*     */       
/* 460 */       inputs.add(RecipeInputFluidStack.of(stack));
/*     */     } 
/*     */ 
/*     */     
/* 464 */     if (items.length + fluids.length != recipe.getInputIngredients().size()) {
/* 465 */       return false;
/*     */     }
/*     */     
/* 468 */     ListIterator<RecipeInputIngredient> itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 469 */     for (RecipeInputIngredient entry : inputs) {
/* 470 */       while (itB.hasNext()) {
/* 471 */         RecipeInputIngredient temp = itB.next();
/* 472 */         if (temp.matches(entry.ingredient) && 
/* 473 */           entry.getCount() >= temp.getCount()) {
/* 474 */           itB.remove();
/*     */ 
/*     */           
/* 477 */           while (itB.hasPrevious()) {
/* 478 */             itB.previous();
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 490 */     if (!simulate) {
/* 491 */       itB = (new ArrayList<>(recipe.getInputIngredients())).listIterator();
/* 492 */       for (RecipeInputIngredient entry : inputs) {
/* 493 */         while (itB.hasNext()) {
/* 494 */           RecipeInputIngredient temp = itB.next();
/* 495 */           if (temp.matches(entry.ingredient)) {
/* 496 */             entry.shrink(temp.getCount());
/*     */             
/* 498 */             itB.remove();
/*     */ 
/*     */             
/* 501 */             while (itB.hasPrevious()) {
/* 502 */               itB.previous();
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 509 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 513 */     return true;
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
/*     */   public Iterable<? extends DynamicRecipe> getRecipes() {
/* 527 */     return () -> new Iterator<DynamicRecipe>()
/*     */       {
/*     */         public boolean hasNext() {
/* 530 */           return this.recipeIt.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public DynamicRecipe next() {
/* 535 */           DynamicRecipe next = this.recipeIt.next();
/* 536 */           this.lastInput = next.getInputIngredients();
/*     */           
/* 538 */           return next;
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 543 */           this.recipeIt.remove();
/* 544 */           DynamicRecipeManager.this.removeCachedRecipes(this.lastInput);
/*     */         }
/*     */         
/* 547 */         private final Iterator recipeIt = DynamicRecipeManager.this.recipes.values().iterator();
/*     */ 
/*     */ 
/*     */         
/*     */         private Collection lastInput;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIterable() {
/* 559 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addToCache(DynamicRecipe recipe) {
/* 569 */     if (recipe.getInputIngredients().stream().anyMatch(entry -> entry instanceof RecipeInputOreDictionary)) {
/* 570 */       if (!oreRegisterEventSubscribed) {
/* 571 */         MinecraftForge.EVENT_BUS.register(DynamicRecipeManager.class);
/*     */         
/* 573 */         oreRegisterEventSubscribed = true;
/*     */       } 
/*     */       
/* 576 */       watchingManagers.add(this);
/*     */     } 
/*     */     
/* 579 */     Collection<Item> items = getItemsFromRecipe(recipe.getInputIngredients());
/* 580 */     Collection<Fluid> fluids = getFluidsFromRecipe(recipe.getInputIngredients());
/*     */     
/* 582 */     if (items != null) {
/* 583 */       for (Item item : items) {
/* 584 */         addToCache(item, recipe);
/*     */       }
/*     */     }
/*     */     
/* 588 */     if (fluids != null) {
/* 589 */       for (Fluid fluid : fluids) {
/* 590 */         addToCache(fluid, recipe);
/*     */       }
/*     */     }
/*     */     
/* 594 */     if (items == null && fluids == null) {
/* 595 */       this.uncacheableRecipes.add(recipe);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToCache(Item item, DynamicRecipe recipe) {
/* 605 */     List<DynamicRecipe> recipes = this.recipeCacheItem.computeIfAbsent(item, newValue -> new ArrayList());
/*     */     
/* 607 */     if (!recipes.contains(recipe)) {
/* 608 */       recipes.add(recipe);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToCache(Fluid fluid, DynamicRecipe recipe) {
/* 618 */     List<DynamicRecipe> recipes = this.recipeCacheFluid.computeIfAbsent(fluid.getName(), newValue -> new ArrayList());
/*     */     
/* 620 */     if (!recipes.contains(recipe)) {
/* 621 */       recipes.add(recipe);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeCachedRecipes(Collection<RecipeInputIngredient> input) {
/* 631 */     Collection<Item> items = getItemsFromRecipe(input);
/* 632 */     Collection<Fluid> fluids = getFluidsFromRecipe(input);
/*     */     
/* 634 */     if (items != null) {
/* 635 */       for (Item item : items) {
/* 636 */         List<DynamicRecipe> recipes = this.recipeCacheItem.get(item);
/*     */         
/* 638 */         if (recipes == null) {
/* 639 */           IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + " is missing.");
/*     */           
/*     */           continue;
/*     */         } 
/* 643 */         removeInputFromRecipes(recipes.iterator(), input);
/*     */         
/* 645 */         if (recipes.isEmpty()) {
/* 646 */           this.recipeCacheItem.remove(item);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 651 */     if (fluids != null) {
/* 652 */       for (Fluid fluid : fluids) {
/* 653 */         List<DynamicRecipe> recipes = this.recipeCacheFluid.get(fluid.getName());
/*     */         
/* 655 */         if (recipes == null) {
/* 656 */           IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the fluid " + fluid + " is missing.");
/*     */           
/*     */           continue;
/*     */         } 
/* 660 */         removeInputFromRecipes(recipes.iterator(), input);
/*     */         
/* 662 */         if (recipes.isEmpty()) {
/* 663 */           this.recipeCacheFluid.remove(fluid.getName());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 668 */     if (items == null && fluids == null) {
/* 669 */       removeInputFromRecipes(this.uncacheableRecipes.iterator(), input);
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
/*     */   private void removeInputFromRecipes(Iterator<DynamicRecipe> it, Collection<RecipeInputIngredient> target) {
/* 681 */     assert target != null;
/*     */     
/* 683 */     while (it.hasNext()) {
/* 684 */       if (target.equals(((DynamicRecipe)it.next()).getInputIngredients())) {
/* 685 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<Item> getItemsFromRecipe(Collection<RecipeInputIngredient> recipe) {
/* 697 */     List<ItemStack> inputs = new ArrayList<>();
/*     */     
/* 699 */     for (RecipeInputIngredient entry : recipe) {
/* 700 */       if (entry instanceof RecipeInputItemStack) {
/* 701 */         inputs.add(((RecipeInputItemStack)entry).ingredient);
/*     */       }
/*     */     } 
/*     */     
/* 705 */     if (inputs.isEmpty()) {
/* 706 */       return null;
/*     */     }
/*     */     
/* 709 */     Set<Item> ret = Collections.newSetFromMap(new IdentityHashMap<>(inputs.size()));
/*     */     
/* 711 */     for (ItemStack stack : inputs) {
/* 712 */       ret.add(stack.func_77973_b());
/*     */     }
/*     */     
/* 715 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<Fluid> getFluidsFromRecipe(Collection<RecipeInputIngredient> recipe) {
/* 725 */     List<FluidStack> inputs = new ArrayList<>();
/*     */     
/* 727 */     for (RecipeInputIngredient entry : recipe) {
/* 728 */       if (entry instanceof RecipeInputFluidStack) {
/* 729 */         inputs.add(((RecipeInputFluidStack)entry).ingredient);
/*     */       }
/*     */     } 
/*     */     
/* 733 */     if (inputs.isEmpty()) {
/* 734 */       return null;
/*     */     }
/*     */     
/* 737 */     Set<Fluid> ret = Collections.newSetFromMap(new IdentityHashMap<>(inputs.size()));
/*     */     
/* 739 */     for (FluidStack stack : inputs) {
/* 740 */       ret.add(stack.getFluid());
/*     */     }
/*     */     
/* 743 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeRecipe(Collection<RecipeInputIngredient> input, Collection<RecipeOutputIngredient> output) {
/* 753 */     DynamicRecipe recipe = getRecipe(input);
/*     */     
/* 755 */     if (recipe == null) {
/* 756 */       return false;
/*     */     }
/*     */     
/* 759 */     if (checkListEqualityIngredient((Collection)output, (Collection)recipe.getOutputIngredients(), true)) {
/* 760 */       this.recipes.remove(recipe.getInputIngredients());
/* 761 */       removeCachedRecipes(recipe.getInputIngredients());
/*     */     } 
/*     */     
/* 764 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkListEqualityIngredient(Collection<? extends RecipeIngredient> first, Collection<? extends RecipeIngredient> second, boolean strict) {
/* 775 */     if (first.size() != second.size()) {
/* 776 */       return false;
/*     */     }
/*     */     
/* 779 */     ListIterator<RecipeIngredient> itB = (new ArrayList<>(second)).listIterator();
/* 780 */     for (RecipeIngredient ingredient : first) {
/* 781 */       while (itB.hasNext()) {
/* 782 */         if (strict ? ingredient.matchesStrict(itB.next()) : ingredient.matches(itB.next())) {
/* 783 */           itB.remove();
/*     */ 
/*     */           
/* 786 */           while (itB.hasPrevious()) {
/* 787 */             itB.previous();
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 794 */       return false;
/*     */     } 
/*     */     
/* 797 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayError(String message) {
/* 806 */     if (MainConfig.ignoreInvalidRecipes) {
/* 807 */       IC2.log.warn(LogCategory.Recipe, message);
/*     */     } else {
/* 809 */       throw new RuntimeException(message);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 814 */   protected final Map<Collection<RecipeInputIngredient>, DynamicRecipe> recipes = new HashMap<>();
/* 815 */   private final Map<Item, List<DynamicRecipe>> recipeCacheItem = new IdentityHashMap<>();
/* 816 */   private final Map<String, List<DynamicRecipe>> recipeCacheFluid = new IdentityHashMap<>();
/* 817 */   private final List<DynamicRecipe> uncacheableRecipes = new ArrayList<>();
/*     */   
/*     */   private static boolean oreRegisterEventSubscribed;
/*     */   
/*     */   @SubscribeEvent
/*     */   public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
/* 823 */     Item item = event.getOre().func_77973_b();
/*     */     
/* 825 */     if (item == null) {
/* 826 */       IC2.log.warn(LogCategory.Recipe, "Found null item ore dict registration.", new Object[] { new Throwable() });
/*     */       
/*     */       return;
/*     */     } 
/* 830 */     for (DynamicRecipeManager manager : watchingManagers) {
/* 831 */       manager.onOreRegister(item, event.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   private void onOreRegister(Item item, String name) {
/* 836 */     for (DynamicRecipe rawRecipe : this.recipes.values()) {
/* 837 */       for (RecipeInputIngredient entry : rawRecipe.getInputIngredients()) {
/* 838 */         if (!(entry instanceof RecipeInputOreDictionary)) {
/*     */           continue;
/*     */         }
/*     */         
/* 842 */         RecipeInputOreDictionary input = (RecipeInputOreDictionary)entry;
/*     */         
/* 844 */         if (input.matchesStrict(name)) {
/* 845 */           addToCache(item, rawRecipe);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 852 */   private static final Set<DynamicRecipeManager> watchingManagers = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\DynamicRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */