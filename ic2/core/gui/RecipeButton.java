/*    */ package ic2.core.gui;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.init.Localization;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RecipeButton extends Button<RecipeButton> {
/*    */   public static Function<String[], IClickHandler> jeiRecipeListOpener;
/*    */   
/*    */   public static boolean canUse() {
/* 12 */     return (jeiRecipeListOpener != null);
/*    */   }
/*    */   
/*    */   public RecipeButton(GuiElement<?> wrapping, String[] categories) {
/* 16 */     this(wrapping.gui, wrapping.x, wrapping.y, wrapping.width, wrapping.height, categories);
/*    */   }
/*    */   
/*    */   public RecipeButton(GuiIC2<?> gui, int x, int y, int width, int height, String[] categories) {
/* 20 */     super(gui, x, y, width, height, (IClickHandler)jeiRecipeListOpener.apply(categories));
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getToolTip() {
/* 25 */     List<String> ret = super.getToolTip();
/*    */     
/* 27 */     ret.add(Localization.translate("ic2.jei.recipes"));
/*    */     
/* 29 */     return ret;
/*    */   }
/*    */   
/*    */   @SkippedMethod
/*    */   public void drawBackground(int mouseX, int mouseY) {}
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\RecipeButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */