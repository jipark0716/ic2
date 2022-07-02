/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntComparators;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import java.util.Comparator;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.util.RecipeItemHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RecipeInputBase
/*    */   extends Ingredient
/*    */   implements IRecipeInput
/*    */ {
/*    */   private ItemStack[] items;
/*    */   private IntList list;
/*    */   
/*    */   protected RecipeInputBase() {
/* 30 */     super(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 35 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ingredient getIngredient() {
/* 40 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack[] func_193365_a() {
/* 46 */     if (this.items == null) {
/* 47 */       this.items = (ItemStack[])getInputs().toArray((Object[])new ItemStack[0]);
/*    */     }
/* 49 */     return this.items;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply(@Nullable ItemStack item) {
/* 55 */     return matches(item);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IntList func_194139_b() {
/* 62 */     if (this.list == null) {
/*    */       
/* 64 */       ItemStack[] items = func_193365_a();
/* 65 */       this.list = (IntList)new IntArrayList(items.length);
/*    */       
/* 67 */       for (ItemStack itemstack : items)
/*    */       {
/* 69 */         this.list.add(RecipeItemHelper.func_194113_b(itemstack));
/*    */       }
/*    */       
/* 72 */       this.list.sort((Comparator)IntComparators.NATURAL_COMPARATOR);
/*    */     } 
/*    */     
/* 75 */     return this.list;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void invalidate() {
/* 81 */     this.items = null;
/* 82 */     this.list = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */