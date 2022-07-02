/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ref.IMultiBlock;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigUtil
/*     */ {
/*     */   public static List<String> asList(String str) {
/*  27 */     str = str.trim();
/*  28 */     if (str.isEmpty()) return Collections.emptyList();
/*     */     
/*  30 */     return Arrays.asList(str.split("\\s*,\\s*"));
/*     */   }
/*     */   
/*     */   public static List<IRecipeInput> asRecipeInputList(Config config, String key) {
/*  34 */     Config.Value value = config.get(key);
/*     */ 
/*     */     
/*     */     try {
/*  38 */       return asRecipeInputList(value.getString());
/*  39 */     } catch (ParseException e) {
/*  40 */       throw new Config.ParseException("Invalid value", value, e);
/*     */     }
/*  42 */     catch (ParseException e) {
/*  43 */       displayError(e, key);
/*  44 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<ItemStack> asStackList(Config config, String key) {
/*  49 */     Config.Value value = config.get(key);
/*     */ 
/*     */     
/*     */     try {
/*  53 */       return asStackList(value.getString());
/*  54 */     } catch (ParseException e) {
/*  55 */       throw new Config.ParseException("Invalid value", value, e);
/*     */     }
/*  57 */     catch (ParseException e) {
/*  58 */       displayError(e, key);
/*  59 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack asStack(Config config, String key) {
/*  64 */     Config.Value value = config.get(key);
/*     */ 
/*     */     
/*     */     try {
/*  68 */       return asStack(value.getString());
/*  69 */     } catch (ParseException e) {
/*  70 */       throw new Config.ParseException("Invalid value", value, e);
/*     */     }
/*  72 */     catch (ParseException e) {
/*  73 */       displayError(e, key);
/*  74 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getString(Config config, String key) {
/*  79 */     return config.get(key).getString();
/*     */   }
/*     */   
/*     */   public static boolean getBool(Config config, String key) {
/*  83 */     Config.Value value = config.get(key);
/*     */     
/*     */     try {
/*  86 */       return value.getBool();
/*  87 */     } catch (ParseException e) {
/*  88 */       displayError(e, key);
/*  89 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getInt(Config config, String key) {
/*  94 */     Config.Value value = config.get(key);
/*     */     
/*     */     try {
/*  97 */       return value.getInt();
/*  98 */     } catch (ParseException e) {
/*  99 */       displayError(e, key);
/* 100 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float getFloat(Config config, String key) {
/* 105 */     Config.Value value = config.get(key);
/*     */     
/*     */     try {
/* 108 */       return value.getFloat();
/* 109 */     } catch (ParseException e) {
/* 110 */       displayError(e, key);
/* 111 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getDouble(Config config, String key) {
/* 116 */     Config.Value value = config.get(key);
/*     */     
/*     */     try {
/* 119 */       return value.getDouble();
/* 120 */     } catch (ParseException e) {
/* 121 */       displayError(e, key);
/* 122 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int[] asIntArray(Config config, String key) {
/* 127 */     Config.Value value = config.get(key);
/*     */     
/*     */     try {
/* 130 */       return asList(value.getString()).stream().mapToInt(Integer::parseInt).toArray();
/* 131 */     } catch (NumberFormatException e) {
/* 132 */       displayError(new Config.ParseException("Invalid value", value, e), key);
/* 133 */       return new int[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<ItemStack> asStackList(String str) throws ParseException {
/* 138 */     List<String> parts = asList(str);
/* 139 */     List<ItemStack> ret = new ArrayList<>(parts.size());
/*     */     
/* 141 */     for (String part : parts) {
/* 142 */       ret.add(asStack(part));
/*     */     }
/*     */     
/* 145 */     return ret;
/*     */   }
/*     */   
/*     */   public static List<IRecipeInput> asRecipeInputList(String str) throws ParseException {
/* 149 */     return asRecipeInputList(str, false);
/*     */   }
/*     */   
/*     */   public static List<IRecipeInput> asRecipeInputList(String str, boolean allowNull) throws ParseException {
/* 153 */     List<String> parts = asList(str);
/* 154 */     List<IRecipeInput> ret = new ArrayList<>(parts.size());
/*     */     
/* 156 */     for (String part : parts) {
/* 157 */       IRecipeInput input = asRecipeInput(part);
/*     */       
/* 159 */       if (input == null && !allowNull) {
/* 160 */         throw new ParseException("There is no item matching " + part + ".", -1);
/*     */       }
/* 162 */       ret.add(input);
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return ret;
/*     */   }
/*     */   
/*     */   private static ItemStack asStack(String str, boolean checkAmount) throws ParseException {
/* 170 */     String[] parts = str.split("(?=(@|#|\\*))");
/* 171 */     String itemName = parts[0];
/* 172 */     Item item = Util.getItem(itemName);
/* 173 */     if (item == null) return null;
/*     */     
/* 175 */     ItemStack stack = new ItemStack(item);
/* 176 */     int amount = 1;
/*     */     
/* 178 */     for (int i = 1; i < parts.length; i++) {
/* 179 */       String tmp = parts[i];
/*     */       
/* 181 */       if (tmp.startsWith("@")) {
/* 182 */         if (i + 1 < parts.length && parts[i + 1].equals("*")) {
/* 183 */           stack = new ItemStack(item, 1, 32767);
/* 184 */           i++;
/*     */         } else {
/* 186 */           stack = new ItemStack(item, 1, Integer.parseInt(tmp.substring(1)));
/*     */         } 
/* 188 */       } else if (tmp.startsWith("#")) {
/* 189 */         if (item instanceof IMultiItem) {
/* 190 */           stack = ((IMultiItem)item).getItemStack(tmp.substring(1));
/* 191 */         } else if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d() instanceof IMultiBlock) {
/* 192 */           stack = ((IMultiBlock)((ItemBlock)item).func_179223_d()).getItemStack(tmp.substring(1));
/*     */         } else {
/* 194 */           throw new ParseException("# is not supported on non-IC2-Items: " + str, 0);
/*     */         } 
/* 196 */       } else if (tmp.startsWith("*")) {
/* 197 */         if (!checkAmount) throw new ParseException("We do not support amount here.", -1);
/*     */         
/* 199 */         amount = Integer.parseInt(tmp.substring(1));
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     if (checkAmount) {
/* 204 */       stack = StackUtil.setSize(stack, amount);
/*     */     }
/*     */     
/* 207 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack asStack(String str) throws ParseException {
/* 212 */     return asStack(str, false);
/*     */   }
/*     */   
/*     */   public static ItemStack asStackWithAmount(String str) throws ParseException {
/* 216 */     return asStack(str, true);
/*     */   }
/*     */   
/*     */   public static String fromStack(ItemStack stack) {
/* 220 */     return fromStack(stack, false);
/*     */   }
/*     */   
/*     */   private static String fromStack(ItemStack stack, boolean amount) {
/* 224 */     String ret = Util.getName(stack.func_77973_b()).toString();
/*     */     
/* 226 */     if (amount) ret = ret + "*" + StackUtil.getSize(stack);
/*     */     
/* 228 */     if (stack.func_77973_b() instanceof IMultiItem) {
/* 229 */       String variant = ((IMultiItem)stack.func_77973_b()).getVariant(stack);
/*     */       
/* 231 */       if (variant != null) ret = ret + "#" + variant; 
/* 232 */     } else if (stack.func_77973_b() instanceof ItemBlock && ((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof IMultiBlock) {
/* 233 */       String variant = ((IMultiBlock)((ItemBlock)stack.func_77973_b()).func_179223_d()).getVariant(stack);
/*     */       
/* 235 */       if (variant != null) ret = ret + "#" + variant; 
/* 236 */     } else if (stack.func_77952_i() == 32767) {
/* 237 */       ret = ret + "@*";
/* 238 */     } else if (stack.func_77952_i() != 0) {
/* 239 */       ret = ret + "@" + stack.func_77952_i();
/*     */     } 
/*     */     
/* 242 */     return ret;
/*     */   }
/*     */   
/*     */   public static String fromStackWithAmount(ItemStack stack) {
/* 246 */     return fromStack(stack, true);
/*     */   }
/*     */   
/*     */   public static IRecipeInput asRecipeInput(Config.Value value) {
/*     */     try {
/* 251 */       return asRecipeInput(value.getString());
/* 252 */     } catch (ParseException e) {
/* 253 */       throw new Config.ParseException("Invalid value", value, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IRecipeInput asRecipeInput(String str, boolean checkAmount) throws ParseException {
/* 258 */     String[] parts = str.split("(?=(@|#|\\*))");
/* 259 */     String itemName = parts[0];
/*     */     
/* 261 */     if (!itemName.startsWith("OreDict:") && !itemName.startsWith("Fluid:")) {
/* 262 */       ItemStack stack = asStack(str, checkAmount);
/* 263 */       if (stack == null) return null;
/*     */       
/* 265 */       return Recipes.inputFactory.forStack(stack);
/*     */     } 
/*     */     
/* 268 */     Integer amount = null;
/* 269 */     Integer meta = null;
/*     */     
/* 271 */     for (int i = 1; i < parts.length; i++) {
/* 272 */       String tmp = parts[i];
/*     */       
/* 274 */       if (tmp.startsWith("@")) {
/* 275 */         if (i + 1 < parts.length && parts[i + 1].equals("*")) {
/* 276 */           meta = Integer.valueOf(32767);
/* 277 */           i++;
/*     */         } else {
/* 279 */           meta = Integer.valueOf(Integer.parseInt(tmp.substring(1)));
/*     */         } 
/* 281 */       } else if (tmp.startsWith("*")) {
/* 282 */         if (!checkAmount) throw new ParseException("We do not support amount here.", -1);
/*     */         
/* 284 */         amount = Integer.valueOf(Integer.parseInt(tmp.substring(1)));
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     if (itemName.startsWith("OreDict:")) {
/* 289 */       if (amount == null) amount = Integer.valueOf(1);
/*     */       
/* 291 */       if (meta == null) {
/* 292 */         return Recipes.inputFactory.forOreDict(itemName.substring("OreDict:".length()), amount.intValue());
/*     */       }
/* 294 */       return Recipes.inputFactory.forOreDict(itemName.substring("OreDict:".length()), amount.intValue(), meta.intValue());
/*     */     } 
/* 296 */     if (itemName.startsWith("Fluid:")) {
/* 297 */       if (amount == null) amount = Integer.valueOf(1000);
/*     */       
/* 299 */       return Recipes.inputFactory.forFluidContainer(FluidRegistry.getFluid(itemName.substring("Fluid:".length())), amount.intValue());
/*     */     } 
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IRecipeInput asRecipeInput(String str) throws ParseException {
/* 306 */     return asRecipeInput(str, false);
/*     */   }
/*     */   
/*     */   public static IRecipeInput asRecipeInputWithAmount(String str) throws ParseException {
/* 310 */     return asRecipeInput(str, true);
/*     */   }
/*     */   
/*     */   private static void displayError(Config.ParseException e, String key) {
/* 314 */     IC2.platform.displayError("The IC2 config file contains an invalid entry for %s.\n\n%s%s", new Object[] { key, e
/*     */           
/* 316 */           .getMessage(), 
/* 317 */           (e.getCause() != null) ? ("\n\n" + e.getCause().getMessage()) : "" });
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\ConfigUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */