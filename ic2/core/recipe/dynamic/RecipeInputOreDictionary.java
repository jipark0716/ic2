/*     */ package ic2.core.recipe.dynamic;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ public class RecipeInputOreDictionary extends RecipeInputIngredient<String> {
/*     */   public final int amount;
/*     */   
/*     */   public static RecipeInputOreDictionary of(String ingredient) {
/*  11 */     return of(ingredient, 1);
/*     */   }
/*     */   public final Integer meta; private List<ItemStack> equivalents;
/*     */   public static RecipeInputOreDictionary of(String ingredient, int amount) {
/*  15 */     return of(ingredient, amount, null);
/*     */   }
/*     */   
/*     */   public static RecipeInputOreDictionary of(String ingredient, int amount, Integer meta) {
/*  19 */     return new RecipeInputOreDictionary(ingredient, amount, meta);
/*     */   }
/*     */   
/*     */   protected RecipeInputOreDictionary(String ingredient) {
/*  23 */     this(ingredient, 1);
/*     */   }
/*     */   
/*     */   protected RecipeInputOreDictionary(String ingredient, int amount) {
/*  27 */     this(ingredient, amount, null);
/*     */   }
/*     */   
/*     */   protected RecipeInputOreDictionary(String ingredient, int amount, Integer meta) {
/*  31 */     super(ingredient);
/*     */     
/*  33 */     this.amount = amount;
/*  34 */     this.meta = meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getUnspecific() {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeInputIngredient<String> copy() {
/*  44 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  49 */     return (this.amount <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  54 */     return this.amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shrink(int amount) {
/*  59 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(Object other) {
/*  64 */     if (!(other instanceof ItemStack)) {
/*  65 */       return false;
/*     */     }
/*     */     
/*  68 */     List<ItemStack> inputs = getEquivalents();
/*  69 */     boolean useOreStackMeta = (this.meta == null);
/*  70 */     Item subjectItem = ((ItemStack)other).func_77973_b();
/*  71 */     int subjectMeta = ((ItemStack)other).func_77952_i();
/*     */     
/*  73 */     for (ItemStack oreStack : inputs) {
/*  74 */       Item oreItem = oreStack.func_77973_b();
/*  75 */       if (oreItem == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  79 */       int metaRequired = useOreStackMeta ? oreStack.func_77952_i() : this.meta.intValue();
/*     */       
/*  81 */       if (subjectItem == oreItem && (subjectMeta == metaRequired || metaRequired == 32767)) {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesStrict(Object other) {
/*  91 */     if (!(other instanceof String)) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     return this.ingredient.equals(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toStringSafe() {
/* 100 */     return this.ingredient;
/*     */   }
/*     */   
/*     */   private List<ItemStack> getEquivalents() {
/* 104 */     if (this.equivalents != null) {
/* 105 */       return this.equivalents;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 110 */     NonNullList nonNullList = OreDictionary.getOres(this.ingredient);
/*     */     
/* 112 */     if (nonNullList != OreDictionary.EMPTY_LIST) {
/* 113 */       this.equivalents = (List<ItemStack>)nonNullList;
/*     */     }
/*     */     
/* 116 */     return (List<ItemStack>)nonNullList;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeInputOreDictionary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */