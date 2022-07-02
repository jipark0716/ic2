/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ public class RecipeInputOreDict extends RecipeInputBase implements IRecipeInput {
/*     */   public final String input;
/*     */   public final int amount;
/*     */   
/*     */   RecipeInputOreDict(String input) {
/*  18 */     this(input, 1);
/*     */   }
/*     */   public final Integer meta; private List<ItemStack> ores;
/*     */   RecipeInputOreDict(String input, int amount) {
/*  22 */     this(input, amount, null);
/*     */   }
/*     */   
/*     */   RecipeInputOreDict(String input, int amount, Integer meta) {
/*  26 */     this.input = input;
/*  27 */     this.amount = amount;
/*  28 */     this.meta = meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(ItemStack subject) {
/*  33 */     List<ItemStack> inputs = getOres();
/*  34 */     boolean useOreStackMeta = (this.meta == null);
/*  35 */     Item subjectItem = subject.func_77973_b();
/*  36 */     int subjectMeta = subject.func_77952_i();
/*     */     
/*  38 */     for (ItemStack oreStack : inputs) {
/*  39 */       Item oreItem = oreStack.func_77973_b();
/*  40 */       if (oreItem == null)
/*     */         continue; 
/*  42 */       int metaRequired = useOreStackMeta ? oreStack.func_77952_i() : this.meta.intValue();
/*     */       
/*  44 */       if (subjectItem == oreItem && (subjectMeta == metaRequired || metaRequired == 32767))
/*     */       {
/*  46 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmount() {
/*  55 */     return this.amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getInputs() {
/*  60 */     List<ItemStack> ores = getOres();
/*     */ 
/*     */     
/*  63 */     boolean hasUnsuitableEntries = false;
/*     */     
/*  65 */     for (ItemStack stack : ores) {
/*  66 */       if (StackUtil.getSize(stack) != getAmount()) {
/*  67 */         hasUnsuitableEntries = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  72 */     if (!hasUnsuitableEntries) return ores;
/*     */     
/*  74 */     List<ItemStack> ret = new ArrayList<>(ores.size());
/*     */     
/*  76 */     for (ItemStack stack : ores) {
/*  77 */       if (stack.func_77973_b() != null) {
/*  78 */         if (StackUtil.getSize(stack) != getAmount()) {
/*  79 */           stack = StackUtil.copyWithSize(stack, getAmount());
/*     */         }
/*  81 */         ret.add(stack);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return Collections.unmodifiableList(ret);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     if (this.meta == null) {
/*  91 */       return "RInputOreDict<" + this.amount + "x" + this.input + ">";
/*     */     }
/*  93 */     return "RInputOreDict<" + this.amount + "x" + this.input + "@" + this.meta + ">";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RecipeInputOreDict other;
/* 100 */     if (obj != null && getClass() == obj.getClass() && this.input
/* 101 */       .equals((other = (RecipeInputOreDict)obj).input) && other.amount == this.amount) {
/* 102 */       return (this.meta == null) ? ((other.meta == null)) : ((this.meta == other.meta));
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ItemStack> getOres() {
/* 109 */     if (this.ores != null) return this.ores;
/*     */ 
/*     */ 
/*     */     
/* 113 */     NonNullList nonNullList = OreDictionary.getOres(this.input);
/*     */     
/* 115 */     if (nonNullList != OreDictionary.EMPTY_LIST) this.ores = (List<ItemStack>)nonNullList;
/*     */     
/* 117 */     return (List<ItemStack>)nonNullList;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputOreDict.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */