/*     */ package ic2.jeiIntegration.transferhandlers;
/*     */ 
/*     */ import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import mezz.jei.JustEnoughItems;
/*     */ import mezz.jei.api.gui.IGuiIngredient;
/*     */ import mezz.jei.api.gui.IRecipeLayout;
/*     */ import mezz.jei.api.recipe.transfer.IRecipeTransferError;
/*     */ import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
/*     */ import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
/*     */ import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
/*     */ import mezz.jei.network.packets.PacketJei;
/*     */ import mezz.jei.network.packets.PacketRecipeTransfer;
/*     */ import mezz.jei.startup.StackHelper;
/*     */ import mezz.jei.transfer.BasicRecipeTransferHandler;
/*     */ import mezz.jei.util.Log;
/*     */ import mezz.jei.util.Translator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransferHandlerIndustrialWorkbench
/*     */   implements IRecipeTransferHandler<ContainerIndustrialWorkbench>
/*     */ {
/*     */   private final IRecipeTransferHandler<ContainerIndustrialWorkbench> crafting;
/*     */   private final IRecipeTransferHandler<ContainerIndustrialWorkbench> others;
/*     */   
/*     */   public TransferHandlerIndustrialWorkbench(StackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper) {
/*  41 */     TransferInfo info = new TransferInfo();
/*  42 */     this.crafting = (IRecipeTransferHandler<ContainerIndustrialWorkbench>)new BasicRecipeTransferHandler(stackHelper, handlerHelper, info);
/*  43 */     this.others = new AdjustedRecipeTransferHandler(stackHelper, handlerHelper, new TransferInfo());
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<ContainerIndustrialWorkbench> getContainerClass() {
/*  48 */     return ContainerIndustrialWorkbench.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IRecipeTransferError transferRecipe(ContainerIndustrialWorkbench container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
/*  55 */     IRecipeTransferError error = this.others.transferRecipe((Container)container, recipeLayout, player, maxTransfer, doTransfer);
/*  56 */     if (error == null) {
/*  57 */       return error;
/*     */     }
/*  59 */     return this.crafting.transferRecipe((Container)container, recipeLayout, player, maxTransfer, doTransfer);
/*     */   }
/*     */   
/*     */   private static class TransferInfo implements IRecipeTransferInfo<ContainerIndustrialWorkbench> {
/*     */     private TransferInfo() {}
/*     */     
/*     */     public Class<ContainerIndustrialWorkbench> getContainerClass() {
/*  66 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getRecipeCategoryUid() {
/*  71 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canHandle(ContainerIndustrialWorkbench container) {
/*  76 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Slot> getRecipeSlots(ContainerIndustrialWorkbench container) {
/*  81 */       List<Slot> recipeSlots = new ArrayList<>();
/*  82 */       for (int i = container.indexGridStart; i < container.indexGridEnd; i++) {
/*  83 */         recipeSlots.add(container.func_75139_a(i));
/*     */       }
/*  85 */       return recipeSlots;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Slot> getInventorySlots(ContainerIndustrialWorkbench container) {
/*  90 */       List<Slot> inventorySlots = new ArrayList<>(); int i;
/*  91 */       for (i = container.indexBufferStart; i < container.indexBufferEnd; i++) {
/*  92 */         inventorySlots.add(container.func_75139_a(i));
/*     */       }
/*  94 */       for (i = 0; i < 36; i++) {
/*  95 */         inventorySlots.add(container.func_75139_a(i));
/*     */       }
/*  97 */       return inventorySlots;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AdjustedRecipeTransferHandler
/*     */     implements IRecipeTransferHandler<ContainerIndustrialWorkbench>
/*     */   {
/*     */     private final StackHelper stackHelper;
/*     */     private final IRecipeTransferHandlerHelper handlerHelper;
/*     */     private final IRecipeTransferInfo<ContainerIndustrialWorkbench> transferHelper;
/*     */     
/*     */     public AdjustedRecipeTransferHandler(StackHelper stackHelper, IRecipeTransferHandlerHelper handlerHelper, IRecipeTransferInfo<ContainerIndustrialWorkbench> transferHelper) {
/* 110 */       this.stackHelper = stackHelper;
/* 111 */       this.handlerHelper = handlerHelper;
/* 112 */       this.transferHelper = transferHelper;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<ContainerIndustrialWorkbench> getContainerClass() {
/* 117 */       return this.transferHelper.getContainerClass();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IRecipeTransferError transferRecipe(ContainerIndustrialWorkbench container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
/* 123 */       List<IGuiIngredient<ItemStack>> ingredients = new ArrayList<>();
/* 124 */       recipeLayout.getItemStacks().getGuiIngredients().values().stream().filter(IGuiIngredient::isInput).filter(i -> !i.getAllIngredients().isEmpty())
/* 125 */         .forEach(ingredients::add);
/* 126 */       if (ingredients.size() != 2) {
/* 127 */         return this.handlerHelper.createInternalError();
/*     */       }
/*     */       
/* 130 */       Slot toolLeft = container.func_75139_a(container.indexOutputHammer - 2);
/* 131 */       Slot toolRight = container.func_75139_a(container.indexOutputCutter - 2);
/* 132 */       Slot itemLeft = container.func_75139_a(container.indexOutputHammer - 1);
/* 133 */       Slot itemRight = container.func_75139_a(container.indexOutputCutter - 1);
/*     */       
/* 135 */       Slot[][] craftingSlots = { { toolLeft, itemLeft }, { toolRight, itemRight } };
/*     */       
/* 137 */       int toolIdx = -1;
/* 138 */       int craftingIdx = -1;
/* 139 */       for (int i = 0; i < ingredients.size(); i++) {
/* 140 */         ItemStack stack = (ItemStack)((IGuiIngredient)ingredients.get(i)).getDisplayedIngredient();
/* 141 */         if (toolLeft.func_75214_a(stack)) {
/* 142 */           toolIdx = i;
/* 143 */           craftingIdx = 0; break;
/*     */         } 
/* 145 */         if (toolRight.func_75214_a(stack)) {
/* 146 */           toolIdx = i;
/* 147 */           craftingIdx = 1;
/*     */           break;
/*     */         } 
/*     */       } 
/* 151 */       if (toolIdx == -1) {
/* 152 */         return this.handlerHelper.createInternalError();
/*     */       }
/*     */       
/* 155 */       Map<Integer, IGuiIngredient<ItemStack>> adjustedIngredients = new HashMap<>();
/* 156 */       adjustedIngredients.put(Integer.valueOf(0), ingredients.get(toolIdx));
/* 157 */       adjustedIngredients.put(Integer.valueOf(1), ingredients.get(1 - toolIdx));
/*     */       
/* 159 */       Map<Integer, ItemStack> availableItemStacks = new HashMap<>();
/* 160 */       int filledCraftSlotCount = 0;
/* 161 */       int emptySlotCount = 0;
/*     */       
/* 163 */       for (Slot slot : craftingSlots[craftingIdx]) {
/* 164 */         ItemStack stack = slot.func_75211_c();
/* 165 */         if (!stack.func_190926_b()) {
/* 166 */           if (!slot.func_82869_a(player)) {
/* 167 */             Log.get().error("Recipe Transfer helper {} does not work for container {}. Player can't move item out of Crafting Slot number {}", this.transferHelper.getClass(), container.getClass(), Integer.valueOf(slot.field_75222_d));
/* 168 */             return this.handlerHelper.createInternalError();
/*     */           } 
/* 170 */           filledCraftSlotCount++;
/* 171 */           availableItemStacks.put(Integer.valueOf(slot.field_75222_d), stack.func_77946_l());
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       List<Slot> inventorySlots = this.transferHelper.getInventorySlots((Container)container);
/*     */       
/* 177 */       for (Slot slot : inventorySlots) {
/* 178 */         ItemStack stack = slot.func_75211_c();
/* 179 */         if (!stack.func_190926_b()) {
/* 180 */           availableItemStacks.put(Integer.valueOf(slot.field_75222_d), stack.func_77946_l()); continue;
/*     */         } 
/* 182 */         emptySlotCount++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 187 */       if (filledCraftSlotCount - ingredients.size() > emptySlotCount) {
/* 188 */         String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.inventory.full");
/* 189 */         return this.handlerHelper.createUserErrorWithTooltip(message);
/*     */       } 
/*     */       
/* 192 */       StackHelper.MatchingItemsResult matchingItemsResult = this.stackHelper.getMatchingItems(availableItemStacks, adjustedIngredients);
/*     */       
/* 194 */       if (matchingItemsResult.missingItems.size() > 0) {
/* 195 */         String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.missing");
/* 196 */         return this.handlerHelper.createUserErrorForSlots(message, matchingItemsResult.missingItems);
/*     */       } 
/*     */       
/* 199 */       List<Integer> inventorySlotIndexes = new ArrayList<>();
/* 200 */       inventorySlots.stream().map(s -> Integer.valueOf(s.field_75222_d)).forEach(inventorySlotIndexes::add);
/*     */       
/* 202 */       if (doTransfer) {
/* 203 */         List<Integer> craftingSlotIndexes = Arrays.asList(new Integer[] {
/* 204 */               Integer.valueOf((craftingSlots[craftingIdx][0]).field_75222_d), 
/* 205 */               Integer.valueOf((craftingSlots[craftingIdx][1]).field_75222_d) });
/* 206 */         PacketRecipeTransfer packet = new PacketRecipeTransfer(matchingItemsResult.matchingItems, craftingSlotIndexes, inventorySlotIndexes, maxTransfer, false);
/* 207 */         JustEnoughItems.getProxy().sendPacketToServer((PacketJei)packet);
/*     */       } 
/*     */       
/* 210 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\transferhandlers\TransferHandlerIndustrialWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */