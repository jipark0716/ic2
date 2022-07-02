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
/*    */ public class IngredientRecipeInput
/*    */   extends Ingredient
/*    */ {
/*    */   private IRecipeInput part;
/*    */   private ItemStack[] items;
/*    */   private IntList list;
/*    */   
/*    */   IngredientRecipeInput(IRecipeInput part) {
/* 25 */     super(0);
/* 26 */     this.part = part;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack[] func_193365_a() {
/* 32 */     if (this.items == null) {
/* 33 */       this.items = (ItemStack[])this.part.getInputs().toArray((Object[])new ItemStack[0]);
/*    */     }
/* 35 */     return this.items;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply(@Nullable ItemStack item) {
/* 41 */     return this.part.matches(item);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IntList func_194139_b() {
/* 48 */     if (this.list == null) {
/*    */       
/* 50 */       ItemStack[] items = func_193365_a();
/* 51 */       this.list = (IntList)new IntArrayList(items.length);
/*    */       
/* 53 */       for (ItemStack itemstack : items)
/*    */       {
/* 55 */         this.list.add(RecipeItemHelper.func_194113_b(itemstack));
/*    */       }
/*    */       
/* 58 */       this.list.sort((Comparator)IntComparators.NATURAL_COMPARATOR);
/*    */     } 
/*    */     
/* 61 */     return this.list;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\IngredientRecipeInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */