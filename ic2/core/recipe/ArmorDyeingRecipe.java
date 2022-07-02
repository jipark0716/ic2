/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.util.Ic2Color;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.RecipesArmorDyes;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArmorDyeingRecipe
/*     */   extends RecipesArmorDyes
/*     */ {
/*  29 */   private static final Map<IRecipeInput, int[]> stackToRGB = buildDyeMap(); protected final IRecipeInput armour;
/*     */   
/*     */   private static Map<IRecipeInput, int[]> buildDyeMap() {
/*  32 */     Map<IRecipeInput, int[]> ret = (Map)new HashMap<>();
/*     */     
/*  34 */     for (Ic2Color colour : Ic2Color.values) {
/*  35 */       float[] dyeMap = colour.mcColor.func_193349_f();
/*  36 */       assert dyeMap != null;
/*     */       
/*  38 */       ret.put(Recipes.inputFactory.forOreDict(colour.oreDictDyeName), new int[] { (int)(dyeMap[0] * 255.0F), (int)(dyeMap[1] * 255.0F), (int)(dyeMap[2] * 255.0F) });
/*     */     } 
/*     */     
/*  41 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorDyeingRecipe(ItemStack armour) {
/*  48 */     this(Recipes.inputFactory.forStack(armour));
/*     */     
/*  50 */     if (StackUtil.isEmpty(armour) || !(armour.func_77973_b() instanceof ItemArmor)) {
/*  51 */       throw new IllegalArgumentException("Invalid input stack: " + StackUtil.toStringSafe(armour));
/*     */     }
/*     */   }
/*     */   
/*     */   public ArmorDyeingRecipe(Class<? extends ItemArmor> type) {
/*  56 */     this(new RecipeInputClass(type));
/*     */     
/*  58 */     if (type == null || !ItemArmor.class.isAssignableFrom(type)) {
/*  59 */       throw new IllegalArgumentException("Invalid input class: " + type);
/*     */     }
/*     */   }
/*     */   
/*     */   public ArmorDyeingRecipe(IRecipeInput input) {
/*  64 */     this.armour = input;
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
/*     */   public static boolean isDye(ItemStack stack) {
/*  76 */     for (IRecipeInput input : stackToRGB.keySet()) {
/*  77 */       if (input.matches(stack)) {
/*  78 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getColourForStack(ItemStack stack) {
/*  93 */     for (Map.Entry<IRecipeInput, int[]> entry : stackToRGB.entrySet()) {
/*  94 */       if (((IRecipeInput)entry.getKey()).matches(stack)) {
/*  95 */         return entry.getValue();
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77569_a(InventoryCrafting craftingInv, World world) {
/* 104 */     ItemStack Qsuit = null;
/*     */     
/* 106 */     for (int slot = 0; slot < craftingInv.func_70302_i_(); slot++) {
/* 107 */       ItemStack stack = craftingInv.func_70301_a(slot);
/*     */       
/* 109 */       if (!StackUtil.isEmpty(stack)) {
/* 110 */         if (this.armour.matches(stack)) {
/* 111 */           if (Qsuit != null) {
/* 112 */             return false;
/*     */           }
/*     */           
/* 115 */           Qsuit = stack;
/* 116 */         } else if (getColourForStack(stack) == null) {
/* 117 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return (Qsuit != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting craftingInv) {
/* 127 */     ItemStack armourStack = null;
/* 128 */     ItemArmor Qsuit = null;
/* 129 */     int[] newRBG = new int[3];
/* 130 */     int totalColour = 0;
/* 131 */     int numberOfDyes = 0;
/*     */     
/* 133 */     for (int slot = 0; slot < craftingInv.func_70302_i_(); slot++) {
/* 134 */       ItemStack stack = craftingInv.func_70301_a(slot);
/*     */       
/* 136 */       if (!StackUtil.isEmpty(stack)) {
/* 137 */         if (this.armour.matches(stack)) {
/* 138 */           Qsuit = (ItemArmor)stack.func_77973_b();
/*     */           
/* 140 */           if (!StackUtil.isEmpty(armourStack)) {
/* 141 */             return StackUtil.emptyStack;
/*     */           }
/*     */           
/* 144 */           armourStack = StackUtil.copyWithSize(stack, 1);
/*     */           
/* 146 */           if (Qsuit.func_82816_b_(stack)) {
/* 147 */             int oldColour = Qsuit.func_82814_b(armourStack);
/*     */             
/* 149 */             int r = oldColour >> 16 & 0xFF;
/* 150 */             int g = oldColour >> 8 & 0xFF;
/* 151 */             int b = oldColour & 0xFF;
/*     */             
/* 153 */             totalColour += Math.max(r, Math.max(g, b));
/*     */             
/* 155 */             newRBG[0] = newRBG[0] + r;
/* 156 */             newRBG[1] = newRBG[1] + g;
/* 157 */             newRBG[2] = newRBG[2] + b;
/* 158 */             numberOfDyes++;
/*     */           } 
/*     */         } else {
/* 161 */           int[] dyeRGB = getColourForStack(stack);
/* 162 */           if (dyeRGB == null) {
/* 163 */             return StackUtil.emptyStack;
/*     */           }
/*     */           
/* 166 */           int r = dyeRGB[0];
/* 167 */           int g = dyeRGB[1];
/* 168 */           int b = dyeRGB[2];
/*     */           
/* 170 */           totalColour += Math.max(r, Math.max(g, b));
/*     */           
/* 172 */           newRBG[0] = newRBG[0] + r;
/* 173 */           newRBG[1] = newRBG[1] + g;
/* 174 */           newRBG[2] = newRBG[2] + b;
/* 175 */           numberOfDyes++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 180 */     if (Qsuit == null || numberOfDyes == 0) {
/* 181 */       return StackUtil.emptyStack;
/*     */     }
/* 183 */     if (Qsuit.func_82816_b_(armourStack) && numberOfDyes == 1) {
/* 184 */       Qsuit.func_82815_c(armourStack);
/*     */     } else {
/* 186 */       int averageRed = newRBG[0] / numberOfDyes;
/* 187 */       int averageGreen = newRBG[1] / numberOfDyes;
/* 188 */       int averageBlue = newRBG[2] / numberOfDyes;
/*     */       
/* 190 */       float gain = totalColour / numberOfDyes;
/* 191 */       float averageMax = Math.max(averageRed, Math.max(averageGreen, averageBlue));
/*     */       
/* 193 */       averageRed = (int)(averageRed * gain / averageMax);
/* 194 */       averageGreen = (int)(averageGreen * gain / averageMax);
/* 195 */       averageBlue = (int)(averageBlue * gain / averageMax);
/*     */       
/* 197 */       int finalColour = (averageRed << 8) + averageGreen;
/* 198 */       finalColour = (finalColour << 8) + averageBlue;
/*     */       
/* 200 */       Qsuit.func_82813_b(armourStack, finalColour);
/*     */     } 
/* 202 */     return armourStack;
/*     */   }
/*     */   
/*     */   public static class RecipeInputClass extends RecipeInputBase implements IRecipeInput { protected final Class<?> type;
/*     */     
/*     */     public RecipeInputClass(Class<?> type) {
/* 208 */       this(type, 1);
/*     */     }
/*     */     protected final int amount;
/*     */     public RecipeInputClass(Class<?> type, int amount) {
/* 212 */       this.type = type;
/* 213 */       this.amount = amount;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(ItemStack subject) {
/* 218 */       return matches(subject.func_77973_b());
/*     */     }
/*     */     
/*     */     protected boolean matches(Item item) {
/* 222 */       return this.type.isInstance(item);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getAmount() {
/* 227 */       return this.amount;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ItemStack> getInputs() {
/* 232 */       List<ItemStack> ret = new ArrayList<>();
/*     */       
/* 234 */       for (Item item : ForgeRegistries.ITEMS) {
/* 235 */         if (matches(item)) {
/* 236 */           ret.add(new ItemStack(item, 1, 32767));
/*     */         }
/*     */       } 
/*     */       
/* 240 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 245 */       return "RInputClass<" + this.type + ", " + this.amount + '>';
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*     */       RecipeInputClass other;
/* 251 */       return (obj != null && getClass() == obj.getClass() && (other = (RecipeInputClass)obj).type == this.type && other.amount == this.amount);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\ArmorDyeingRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */