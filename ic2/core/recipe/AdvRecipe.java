/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.recipe.ICraftingRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.init.Rezepte;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.common.crafting.IShapedRecipe;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdvRecipe
/*     */   implements IShapedRecipe
/*     */ {
/*     */   public static void addAndRegister(ItemStack result, Object... args) {
/*     */     try {
/*  45 */       Rezepte.registerRecipe((IRecipe)new AdvRecipe(result, args));
/*  46 */     } catch (RuntimeException e) {
/*  47 */       if (!MainConfig.ignoreInvalidRecipes) throw e;
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
/*     */   public AdvRecipe(ItemStack result, Object... args) {
/* 154 */     if (StackUtil.isEmpty(result)) displayError("null result", null, result, false);
/*     */     
/* 156 */     Map<Character, IRecipeInput> charMapping = new HashMap<>();
/* 157 */     List<String> inputArrangement = new ArrayList<>();
/* 158 */     Character lastChar = null;
/* 159 */     boolean isHidden = false;
/* 160 */     boolean isConsuming = false;
/* 161 */     boolean isFixedSize = false;
/*     */     
/* 163 */     for (Object arg : args) {
/* 164 */       if (arg instanceof String) {
/* 165 */         if (lastChar == null) {
/* 166 */           if (!charMapping.isEmpty()) displayError("oredict name without preceding char", "Name: " + arg, result, false);
/*     */           
/* 168 */           String str = (String)arg;
/*     */           
/* 170 */           if (str.isEmpty() || str.length() > 3) {
/* 171 */             displayError("none or too many crafting columns", "Input: " + str + "\nSize: " + str.length(), result, false);
/*     */           }
/*     */           
/* 174 */           inputArrangement.add(str);
/*     */         } else {
/* 176 */           charMapping.put(lastChar, getRecipeObject(arg));
/*     */           
/* 178 */           lastChar = null;
/*     */         } 
/* 180 */       } else if (arg instanceof Character) {
/* 181 */         if (lastChar != null) displayError("two consecutive char definitions", "Input: " + arg + "\nprev. Input: " + lastChar, result, false);
/*     */         
/* 183 */         lastChar = (Character)arg;
/* 184 */       } else if (arg instanceof Boolean) {
/* 185 */         isHidden = ((Boolean)arg).booleanValue();
/* 186 */       } else if (arg instanceof ICraftingRecipeManager.AttributeContainer) {
/* 187 */         isHidden = ((ICraftingRecipeManager.AttributeContainer)arg).hidden;
/* 188 */         isConsuming = ((ICraftingRecipeManager.AttributeContainer)arg).consuming;
/* 189 */         isFixedSize = ((ICraftingRecipeManager.AttributeContainer)arg).fixedSize;
/*     */       } else {
/* 191 */         if (lastChar == null) displayError("two consecutive char definitions", "Input: " + arg + "\nprev. Input: " + lastChar, result, false); 
/*     */         try {
/* 193 */           IRecipeInput last = charMapping.put(lastChar, getRecipeObject(arg));
/* 194 */           if (last != null) displayError("duplicate char mapping", "Char: " + lastChar + "\nInput: " + arg + "\nType: " + arg.getClass().getName(), result, false); 
/* 195 */           lastChar = null;
/* 196 */         } catch (Exception e) {
/* 197 */           e.printStackTrace();
/* 198 */           displayError("unknown type", "Input: " + arg + "\nType: " + arg.getClass().getName(), result, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     this.hidden = isHidden;
/* 204 */     this.consuming = isConsuming;
/* 205 */     this.inputHeight = inputArrangement.size();
/*     */     
/* 207 */     if (lastChar != null) displayError("one or more unused mapping chars", "Letter: " + lastChar, result, false); 
/* 208 */     if (this.inputHeight == 0 || this.inputHeight > 3) displayError("none or too many crafting rows", "Size: " + inputArrangement.size(), result, false); 
/* 209 */     if (charMapping.size() == 0) displayError("no mapping chars", null, result, false);
/*     */     
/* 211 */     this.inputWidth = ((String)inputArrangement.get(0)).length();
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (debug && !isFixedSize) {
/* 216 */       if (StringUtils.containsOnly(inputArrangement.get(0), new char[] { ' ' })) {
/* 217 */         IC2.log.warn(LogCategory.Recipe, "Leading empty row in shaped recipe for %s (%s), from %s.", new Object[] { result, result.func_82833_r(), getCaller() });
/*     */       }
/*     */       
/* 220 */       if (StringUtils.containsOnly(inputArrangement.get(this.inputHeight - 1), new char[] { ' ' })) {
/* 221 */         IC2.log.warn(LogCategory.Recipe, "Trailing empty row in shaped recipe for %s (%s), from %s.", new Object[] { result, result.func_82833_r(), getCaller() });
/*     */       }
/*     */       
/* 224 */       for (int pass = 0; pass < 2; pass++) {
/* 225 */         boolean found = true;
/*     */         
/* 227 */         for (int j = 0; j < this.inputHeight; j++) {
/* 228 */           String str = inputArrangement.get(j);
/*     */           
/* 230 */           if ((pass == 0 && str.charAt(0) != ' ') || (pass == 1 && str
/* 231 */             .charAt(this.inputWidth - 1) != ' ')) {
/* 232 */             found = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 237 */         if (found) {
/* 238 */           if (pass == 0) {
/* 239 */             IC2.log.warn(LogCategory.Recipe, "Leading empty column in shaped recipe for %s (%s), from %s.", new Object[] { result, result.func_82833_r(), getCaller() });
/*     */           } else {
/* 241 */             IC2.log.warn(LogCategory.Recipe, "Trailing empty column in shaped recipe for %s (%s), from %s.", new Object[] { result, result.func_82833_r(), getCaller() });
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 249 */     int xMasks = -this.inputWidth + 4;
/* 250 */     int yMasks = -this.inputHeight + 4;
/* 251 */     int mask = 0;
/* 252 */     List<Object> inputs = new ArrayList();
/*     */     
/* 254 */     for (int y = 0; y < 3; y++) {
/* 255 */       String str = null;
/*     */       
/* 257 */       if (y < this.inputHeight) {
/* 258 */         str = inputArrangement.get(y);
/*     */         
/* 260 */         if (str.length() != this.inputWidth) {
/* 261 */           displayError("no fixed width", "Expected: " + this.inputWidth + "\nGot: " + str.length(), result, false);
/*     */         }
/*     */       } 
/*     */       
/* 265 */       for (int x = 0; x < 3; x++) {
/* 266 */         mask <<= 1;
/*     */         
/* 268 */         if (x < this.inputWidth && str != null) {
/* 269 */           char c = str.charAt(x);
/*     */           
/* 271 */           if (c != ' ') {
/* 272 */             if (!charMapping.containsKey(Character.valueOf(c))) {
/* 273 */               displayError("missing char mapping", "Letter: " + c, result, false);
/*     */             }
/*     */             
/* 276 */             inputs.add(charMapping.get(Character.valueOf(c)));
/* 277 */             mask |= 0x1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     this.input = inputs.<IRecipeInput>toArray(new IRecipeInput[0]);
/*     */ 
/*     */ 
/*     */     
/* 287 */     boolean mirror = false;
/*     */     
/* 289 */     if (this.inputWidth != 1) {
/* 290 */       for (String s : inputArrangement) {
/* 291 */         if (s.charAt(0) != s.charAt(this.inputWidth - 1)) {
/* 292 */           mirror = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 300 */     if (!mirror) {
/* 301 */       this.inputMirrored = null;
/*     */     } else {
/* 303 */       IRecipeInput[] tmp = new IRecipeInput[9];
/*     */       
/* 305 */       for (int k = 0, j = 0; k < 9; k++) {
/* 306 */         if ((mask & 1 << 8 - k) != 0) {
/* 307 */           tmp[k] = this.input[j];
/*     */           
/* 309 */           j++;
/*     */         } 
/*     */       } 
/*     */       
/* 313 */       IRecipeInput old = tmp[0]; tmp[0] = tmp[2]; tmp[2] = old;
/* 314 */       old = tmp[3]; tmp[3] = tmp[5]; tmp[5] = old;
/* 315 */       old = tmp[6]; tmp[6] = tmp[8]; tmp[8] = old;
/*     */       
/* 317 */       this.inputMirrored = new IRecipeInput[this.input.length];
/*     */       
/* 319 */       for (int m = 0, n = 0; m < 9; m++) {
/* 320 */         if (tmp[m] != null) {
/* 321 */           this.inputMirrored[n] = tmp[m];
/*     */           
/* 323 */           n++;
/*     */         } 
/*     */       } 
/*     */     } 
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
/* 344 */     this.masks = new int[xMasks * yMasks];
/*     */     
/* 346 */     if (!mirror) {
/* 347 */       this.masksMirrored = null;
/*     */     } else {
/* 349 */       this.masksMirrored = new int[this.masks.length];
/*     */     } 
/*     */     
/* 352 */     for (int i = 0; i < yMasks; i++) {
/* 353 */       int yMask = mask >>> i * 3;
/*     */       
/* 355 */       for (int x = 0; x < xMasks; x++) {
/* 356 */         int xyMask = yMask >>> x;
/* 357 */         this.masks[x + i * xMasks] = xyMask;
/*     */         
/* 359 */         if (mirror)
/*     */         {
/* 361 */           this.masksMirrored[x + i * xMasks] = xyMask << 2 & 0x124 | xyMask & 0x92 | xyMask >>> 2 & 0x49;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     this.output = result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting inventorycrafting, World world) {
/* 371 */     return (func_77572_b(inventorycrafting) != StackUtil.emptyStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting inventorycrafting) {
/* 378 */     int size = inventorycrafting.func_70302_i_();
/* 379 */     int mask = 0;
/*     */     
/* 381 */     for (int i = 0; i < size; i++) {
/* 382 */       mask <<= 1;
/*     */       
/* 384 */       if (!StackUtil.isEmpty(inventorycrafting.func_70301_a(i))) {
/* 385 */         mask |= 0x1;
/*     */       }
/*     */     } 
/*     */     
/* 389 */     if (size == 4) {
/* 390 */       mask = (mask & 0xC) << 5 | (mask & 0x3) << 4;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 395 */     if (checkMask(mask, this.masks)) {
/* 396 */       ItemStack ret = checkItems((IInventory)inventorycrafting, this.input);
/*     */       
/* 398 */       if (!StackUtil.isEmpty(ret)) return ret;
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 403 */     if (this.masksMirrored != null && checkMask(mask, this.masksMirrored)) {
/* 404 */       ItemStack ret = checkItems((IInventory)inventorycrafting, this.inputMirrored);
/*     */       
/* 406 */       if (!StackUtil.isEmpty(ret)) return ret;
/*     */     
/*     */     } 
/* 409 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 414 */     return this.output;
/*     */   }
/*     */   
/*     */   public static boolean canShow(Object[] input, ItemStack output, boolean hidden) {
/* 418 */     return (!hidden || !ConfigUtil.getBool(MainConfig.get(), "misc/hideSecretRecipes"));
/*     */   }
/*     */   
/*     */   public boolean canShow() {
/* 422 */     return canShow((Object[])this.input, this.output, this.hidden);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ItemStack> expand(Object o) {
/* 427 */     List<ItemStack> ret = new ArrayList<>();
/*     */     
/* 429 */     if (o instanceof IRecipeInput) {
/* 430 */       ret.addAll(((IRecipeInput)o).getInputs());
/* 431 */     } else if (o instanceof String) {
/* 432 */       String s = (String)o;
/*     */       
/* 434 */       if (s.startsWith("liquid$")) {
/* 435 */         String name = s.substring(7);
/* 436 */         Fluid fluid = FluidRegistry.getFluid(name);
/* 437 */         ret.addAll(RecipeInputFluidContainer.getFluidContainer(fluid));
/*     */       } else {
/* 439 */         for (ItemStack stack : OreDictionary.getOres((String)o)) {
/* 440 */           if (!StackUtil.isEmpty(stack)) ret.add(stack); 
/*     */         } 
/*     */       } 
/* 443 */     } else if (o instanceof ItemStack) {
/* 444 */       if (!StackUtil.isEmpty((ItemStack)o)) ret.add((ItemStack)o); 
/* 445 */     } else if (o.getClass().isArray()) {
/* 446 */       assert Array.getLength(o) != 0 : "empty array";
/*     */       
/* 448 */       for (int i = 0; i < Array.getLength(o); i++) {
/* 449 */         ret.addAll(expand(Array.get(o, i)));
/*     */       }
/* 451 */     } else if (o instanceof Iterable) {
/* 452 */       assert ((Iterable)o).iterator().hasNext() : "emtpy iterable";
/*     */       
/* 454 */       for (Object o2 : o) {
/* 455 */         ret.addAll(expand(o2));
/*     */       }
/*     */     } else {
/* 458 */       displayError("unknown type", "Input: " + o + "\nType: " + o.getClass().getName(), null, false);
/* 459 */       return null;
/*     */     } 
/*     */     
/* 462 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<ItemStack>[] expandArray(Object[] array) {
/* 468 */     List[] arrayOfList = new List[array.length];
/*     */     
/* 470 */     for (int i = 0; i < array.length; i++) {
/* 471 */       if (array[i] == null) {
/* 472 */         arrayOfList[i] = null;
/*     */       } else {
/* 474 */         arrayOfList[i] = expand(array[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 478 */     return (List<ItemStack>[])arrayOfList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void displayError(String cause, String tech, ItemStack result, boolean shapeless) {
/* 487 */     String msg = "An invalid crafting recipe was attempted to be added. This could happen due to a bug in IndustrialCraft 2 or an addon.\n\n(Technical information: Adv" + (shapeless ? "Shapeless" : "") + "Recipe, " + cause + ")\n" + ((result != null) ? ("Output: " + result + "\n") : "") + ((tech != null) ? (tech + "\n") : "") + "Source: " + getCaller();
/*     */     
/* 489 */     if (MainConfig.ignoreInvalidRecipes) {
/* 490 */       IC2.log.warn(LogCategory.Recipe, msg);
/* 491 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 494 */     IC2.platform.displayError(msg, new Object[0]);
/*     */   }
/*     */   
/*     */   private static String getCaller() {
/* 498 */     String ret = "unknown";
/*     */     
/* 500 */     for (StackTraceElement st : Thread.currentThread().getStackTrace()) {
/* 501 */       String className = st.getClassName();
/* 502 */       int pkgSeparator = className.lastIndexOf('.');
/*     */       
/* 504 */       String pkg = (pkgSeparator == -1) ? "" : className.substring(0, pkgSeparator);
/*     */       
/* 506 */       if (!className.equals("ic2.core.recipe.AdvRecipe") && !className.equals("ic2.core.recipe.AdvShapelessRecipe") && 
/* 507 */         !className.equals("ic2.core.recipe.AdvCraftingRecipeManager") && 
/* 508 */         !pkg.startsWith("ic2.api") && !pkg.startsWith("java.")) {
/* 509 */         ret = className + "." + st.getMethodName() + "(" + st.getFileName() + ":" + st.getLineNumber() + ")";
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 514 */     return ret;
/*     */   }
/*     */   
/*     */   private static boolean checkMask(int mask, int[] request) {
/* 518 */     for (int cmpMask : request) {
/* 519 */       if (mask == cmpMask) {
/* 520 */         return true;
/*     */       }
/*     */     } 
/* 523 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static IRecipeInput getRecipeObject(Object o) {
/* 531 */     if (o == null) throw new NullPointerException("Null recipe input object."); 
/* 532 */     if (o instanceof IRecipeInput)
/* 533 */       return (IRecipeInput)o; 
/* 534 */     if (o instanceof ItemStack)
/* 535 */       return Recipes.inputFactory.forStack((ItemStack)o); 
/* 536 */     if (o instanceof Block)
/* 537 */       return Recipes.inputFactory.forStack(new ItemStack((Block)o)); 
/* 538 */     if (o instanceof Item)
/* 539 */       return Recipes.inputFactory.forStack(new ItemStack((Item)o)); 
/* 540 */     if (o instanceof String)
/* 541 */       return Recipes.inputFactory.forOreDict((String)o); 
/* 542 */     if (o instanceof Fluid)
/* 543 */       return Recipes.inputFactory.forFluidContainer((Fluid)o); 
/* 544 */     if (o instanceof FluidStack)
/* 545 */       return Recipes.inputFactory.forFluidContainer(((FluidStack)o).getFluid(), ((FluidStack)o).amount); 
/* 546 */     if (o instanceof Iterable) {
/* 547 */       List<IRecipeInput> list = new ArrayList<>();
/* 548 */       for (Object o1 : o) {
/* 549 */         list.add(getRecipeObject(o1));
/*     */       }
/* 551 */       return Recipes.inputFactory.forAny(list);
/* 552 */     }  if (o.getClass().isArray()) {
/* 553 */       IRecipeInput[] inputs = new IRecipeInput[Array.getLength(o)];
/* 554 */       for (int i = 0; i < inputs.length; i++) {
/* 555 */         inputs[i] = getRecipeObject(Array.get(o, i));
/*     */       }
/* 557 */       return Recipes.inputFactory.forAny(inputs);
/*     */     } 
/* 559 */     throw new IllegalArgumentException("Invalid object found as RecipeInput: " + o);
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
/*     */   private ItemStack checkItems(IInventory inventory, IRecipeInput[] request) {
/* 571 */     int size = inventory.func_70302_i_();
/* 572 */     double outputCharge = 0.0D;
/*     */     
/* 574 */     for (int i = 0, j = 0; i < size; i++) {
/* 575 */       ItemStack offer = inventory.func_70301_a(i);
/* 576 */       if (!StackUtil.isEmpty(offer))
/*     */       {
/* 578 */         if (request[j++].matches(offer)) {
/* 579 */           outputCharge += ElectricItem.manager.getCharge(StackUtil.copyWithSize(offer, 1));
/*     */         } else {
/* 581 */           return StackUtil.emptyStack;
/*     */         } 
/*     */       }
/*     */     } 
/* 585 */     ItemStack ret = this.output.func_77946_l();
/*     */     
/* 587 */     ElectricItem.manager.charge(ret, outputCharge, 2147483647, true, false);
/*     */     
/* 589 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> func_179532_b(InventoryCrafting inv) {
/* 595 */     return this.consuming ? NonNullList.func_191197_a(inv.func_70302_i_(), StackUtil.emptyStack) : ForgeHooks.defaultRecipeGetRemainingItems(inv);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe setRegistryName(ResourceLocation name) {
/* 600 */     this.name = name;
/* 601 */     return (IRecipe)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getRegistryName() {
/* 606 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<IRecipe> getRegistryType() {
/* 611 */     return IRecipe.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int x, int y) {
/* 616 */     return (this.inputWidth <= x && this.inputHeight <= y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecipeWidth() {
/* 621 */     return this.inputWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRecipeHeight() {
/* 626 */     return this.inputHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> func_192400_c() {
/* 631 */     NonNullList<Ingredient> list = NonNullList.func_191196_a();
/* 632 */     if (!this.hidden) {
/* 633 */       int mask = this.masks[0];
/* 634 */       int actualIngredient = 0;
/* 635 */       for (int x = 0; x < 9; x++) {
/* 636 */         if ((mask >>> 8 - x & 0x1) != 0) {
/* 637 */           list.add(this.input[actualIngredient++].getIngredient());
/*     */         } else {
/* 639 */           list.add(Ingredient.field_193370_a);
/*     */         } 
/*     */       } 
/*     */     } 
/* 643 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 648 */     return this.hidden;
/*     */   }
/*     */ 
/*     */   
/* 652 */   private static final boolean debug = Util.hasAssertions();
/*     */   public final ItemStack output;
/*     */   public final IRecipeInput[] input;
/*     */   public final IRecipeInput[] inputMirrored;
/*     */   public final int[] masks;
/*     */   public final int[] masksMirrored;
/*     */   public final int inputWidth;
/*     */   public final int inputHeight;
/*     */   public final boolean hidden;
/*     */   public final boolean consuming;
/*     */   private ResourceLocation name;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\AdvRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */