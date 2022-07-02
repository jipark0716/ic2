/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*    */ import ic2.core.block.ITeBlock;
/*    */ import ic2.core.block.wiring.CableType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import mezz.jei.api.IGuiHelper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public final class MetalFormerCategory
/*    */   extends DynamicCategory<IBasicMachineRecipeManager>
/*    */ {
/*    */   private final int mode;
/* 17 */   private static final ItemStack[] icon = new ItemStack[] { ItemName.cable
/* 18 */       .getItemStack((Enum)CableType.copper), ItemName.forge_hammer
/* 19 */       .getItemStack(), ItemName.cutter
/* 20 */       .getItemStack() };
/*    */ 
/*    */   
/*    */   public MetalFormerCategory(IBasicMachineRecipeManager recipeManager, int mode, IGuiHelper guiHelper) {
/* 24 */     super((ITeBlock)TeBlock.metal_former, recipeManager, guiHelper);
/* 25 */     this.mode = mode;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUid() {
/* 30 */     return super.getUid() + this.mode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Minecraft minecraft) {
/* 35 */     super.draw(minecraft);
/* 36 */     minecraft.func_175599_af().func_180450_b(icon[this.mode], 70, 35);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\MetalFormerCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */