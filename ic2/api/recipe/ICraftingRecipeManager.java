/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ICraftingRecipeManager
/*    */ {
/*    */   void addRecipe(ItemStack paramItemStack, Object... paramVarArgs);
/*    */   
/*    */   void addShapelessRecipe(ItemStack paramItemStack, Object... paramVarArgs);
/*    */   
/*    */   public static class AttributeContainer
/*    */   {
/*    */     public final boolean hidden;
/*    */     public final boolean consuming;
/*    */     public final boolean fixedSize;
/*    */     
/*    */     public AttributeContainer(boolean hidden, boolean consuming) {
/* 42 */       this(hidden, consuming, false);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public AttributeContainer(boolean hidden, boolean consuming, boolean fixedSize) {
/* 51 */       this.hidden = hidden;
/* 52 */       this.consuming = consuming;
/* 53 */       this.fixedSize = fixedSize;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\ICraftingRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */