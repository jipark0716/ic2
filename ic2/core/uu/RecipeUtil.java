/*    */ package ic2.core.uu;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ class RecipeUtil
/*    */ {
/*    */   public static List<List<LeanItemStack>> fixIngredientSize(List<ItemStack>[] x) {
/* 12 */     List<List<LeanItemStack>> ret = new ArrayList<>(x.length);
/*    */     
/* 14 */     for (int i = 0; i < x.length; i++) {
/* 15 */       List<ItemStack> listIn = x[i];
/* 16 */       if (listIn != null) {
/*    */         
/* 18 */         List<LeanItemStack> listOut = new ArrayList<>(listIn.size());
/*    */         
/* 20 */         for (ItemStack stack : x[i]) {
/* 21 */           listOut.add(new LeanItemStack(stack, 1));
/*    */         }
/*    */         
/* 24 */         ret.add(listOut);
/*    */       } 
/*    */     } 
/* 27 */     return ret;
/*    */   }
/*    */   
/*    */   public static List<List<LeanItemStack>> convertIngredients(List<ItemStack> x) {
/* 31 */     return Collections.singletonList(convertOutputs(x));
/*    */   }
/*    */   
/*    */   public static List<LeanItemStack> convertOutputs(Collection<ItemStack> x) {
/* 35 */     List<LeanItemStack> ret = new ArrayList<>(x.size());
/*    */     
/* 37 */     for (ItemStack stack : x) {
/* 38 */       ret.add(new LeanItemStack(stack));
/*    */     }
/*    */     
/* 41 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\RecipeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */