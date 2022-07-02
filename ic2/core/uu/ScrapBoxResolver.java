/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.LogCategory;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ScrapBoxResolver
/*    */   implements IRecipeResolver
/*    */ {
/*    */   private static final double transformCost = 1.0D;
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 21 */     List<RecipeTransformation> ret = new ArrayList<>();
/* 22 */     Map<ItemStack, Float> dropMap = Recipes.scrapboxDrops.getDrops();
/*    */     
/* 24 */     for (Map.Entry<ItemStack, Float> drop : dropMap.entrySet()) {
/* 25 */       if (StackUtil.isEmpty(drop.getKey())) {
/* 26 */         IC2.log.warn(LogCategory.Uu, "Invalid itemstack in scrapbox drops detected.");
/*    */         
/*    */         continue;
/*    */       } 
/* 30 */       int amount = Math.max(1, Math.round(1.0F / ((Float)drop.getValue()).floatValue()));
/* 31 */       List<LeanItemStack> input = Collections.singletonList(new LeanItemStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box), amount));
/*    */       
/* 33 */       ret.add(new RecipeTransformation(1.0D, Collections.singletonList(input), new LeanItemStack[] { new LeanItemStack(drop.getKey()) }));
/*    */     } 
/*    */     
/* 36 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\ScrapBoxResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */