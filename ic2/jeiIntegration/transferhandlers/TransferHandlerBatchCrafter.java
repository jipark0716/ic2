/*    */ package ic2.jeiIntegration.transferhandlers;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.machine.container.ContainerBatchCrafter;
/*    */ import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Map;
/*    */ import mezz.jei.api.gui.IGuiIngredient;
/*    */ import mezz.jei.api.gui.IGuiItemStackGroup;
/*    */ import mezz.jei.api.gui.IRecipeLayout;
/*    */ import mezz.jei.api.recipe.transfer.IRecipeTransferError;
/*    */ import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransferHandlerBatchCrafter
/*    */   implements IRecipeTransferHandler<ContainerBatchCrafter>
/*    */ {
/*    */   public Class<ContainerBatchCrafter> getContainerClass() {
/* 27 */     return ContainerBatchCrafter.class;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IRecipeTransferError transferRecipe(ContainerBatchCrafter container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
/* 33 */     if (!doTransfer) return null;
/*    */     
/* 35 */     IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
/* 36 */     Map<Integer, ? extends IGuiIngredient<ItemStack>> slotToStackMap = stacks.getGuiIngredients();
/*    */     
/* 38 */     for (int i = 0; i < 9; i++) {
/*    */       ItemStack set;
/*    */       
/* 41 */       IGuiIngredient<ItemStack> currentIngredient = slotToStackMap.get(Integer.valueOf(i + 1));
/* 42 */       if (currentIngredient != null) {
/* 43 */         set = (ItemStack)currentIngredient.getDisplayedIngredient();
/*    */       } else {
/* 45 */         set = StackUtil.emptyStack;
/*    */       } 
/* 47 */       ((TileEntityBatchCrafter)container.base).craftingGrid[i] = set;
/*    */     } 
/*    */     
/* 50 */     ((NetworkManager)IC2.network.get(false)).updateTileEntityField((TileEntity)container.base, "craftingGrid");
/* 51 */     ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)container.base, 0);
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\transferhandlers\TransferHandlerBatchCrafter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */