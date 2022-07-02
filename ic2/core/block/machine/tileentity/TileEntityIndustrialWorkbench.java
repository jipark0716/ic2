/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import gnu.trove.TIntCollection;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumable;
/*     */ import ic2.core.block.invslot.InvSlotConsumableOreDict;
/*     */ import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
/*     */ import ic2.core.block.machine.gui.GuiIndustrialWorkbench;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.IInventoryInvSlot;
/*     */ import ic2.core.util.InventorySlotCrafting;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityIndustrialWorkbench
/*     */   extends TileEntityInventory
/*     */   implements IHasGui
/*     */ {
/*     */   public static class InvSlotCraftingCombo
/*     */   {
/*     */     protected IRecipe recipe;
/*     */     public final InvSlotConsumable input;
/*     */     public final InvSlotConsumableOreDict tool;
/*     */     public final InventoryCrafting crafting;
/*     */     public final InventoryCraftResult resultInv;
/*     */     
/*     */     protected boolean canProcess() {
/*     */       if (!this.crafting.func_191420_l()) {
/*     */         if (this.recipe == null || !this.recipe.func_77569_a(this.crafting, this.tool.base.getParent().func_145831_w())) {
/*     */           this.recipe = CraftingManager.func_192413_b(this.crafting, this.tool.base.getParent().func_145831_w());
/*     */           return (this.recipe != null);
/*     */         } 
/*     */         return true;
/*     */       } 
/*     */       return false;
/*     */     }
/*     */     
/*     */     public ItemStack getOutputStack() {
/*     */       return !canProcess() ? StackUtil.emptyStack : this.recipe.func_77572_b(this.crafting);
/*     */     }
/*     */     
/*     */     public InvSlotCraftingCombo(TileEntityInventory base, String name, String tool) {
/*  95 */       this.crafting = (InventoryCrafting)new InventorySlotCrafting(2, 1) {
/*     */           private InvSlot getSlot(int index) {
/*  97 */             switch (index) {
/*     */               case 0:
/*  99 */                 return (InvSlot)TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.tool;
/*     */               
/*     */               case 1:
/* 102 */                 return (InvSlot)TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.input;
/*     */             } 
/*     */             
/* 105 */             throw new IllegalArgumentException("Invalid index: " + index);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           protected ItemStack get(int index) {
/* 111 */             return getSlot(index).get();
/*     */           }
/*     */ 
/*     */           
/*     */           protected void put(int index, ItemStack stack) {
/* 116 */             getSlot(index).put(stack);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean func_191420_l() {
/* 121 */             return (TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.input.isEmpty() && TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.tool.isEmpty());
/*     */           }
/*     */ 
/*     */           
/*     */           public void func_174888_l() {
/* 126 */             TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.input.clear();
/* 127 */             TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.tool.clear();
/*     */           }
/*     */         };
/* 130 */       this.resultInv = new InventoryCraftResult(); this.input = new InvSlotConsumable((IInventorySlotHolder)base, name + "Input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY) {
/*     */           public boolean accepts(ItemStack stack) { ItemStack prev = get(); try { put(stack); return TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.canProcess(); }
/*     */             finally
/*     */             { put(prev); }
/*     */              } public void onChanged() { TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.resultInv.func_70299_a(0, TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.getOutputStack()); }
/*     */         }; this.tool = new InvSlotConsumableOreDict((IInventorySlotHolder)base, name + "Tool", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, tool) { public void onChanged() { TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.resultInv.func_70299_a(0, TileEntityIndustrialWorkbench.InvSlotCraftingCombo.this.getOutputStack()); } };
/* 136 */     } } public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) { super.onPlaced(stack, placer, facing);
/*     */     
/* 138 */     if (!stack.func_77942_o() || !stack.func_77978_p().func_74764_b("PLACED")) {
/* 139 */       this.leftCrafting.tool.put(ItemName.forge_hammer.getItemStack());
/* 140 */       this.rightCrafting.tool.put(ItemName.cutter.getItemStack());
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/* 146 */     drop = super.adjustDrop(drop, wrench);
/* 147 */     StackUtil.getOrCreateNbtData(drop).func_74757_a("PLACED", true);
/*     */     
/* 149 */     return drop;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebalance() {
/* 154 */     if (!this.craftingGrid.isEmpty()) {
/* 155 */       boolean changed = false;
/* 156 */       IInventoryInvSlot iInventoryInvSlot = new IInventoryInvSlot(this.craftingGrid);
/*     */       
/* 158 */       for (int index = 0, size = this.craftingStorage.size(); index < size; index++) {
/* 159 */         if (!this.craftingStorage.isEmpty(index)) {
/* 160 */           Tuple.T2<List<ItemStack>, ? extends TIntCollection> changes = StackUtil.balanceStacks((IInventory)iInventoryInvSlot, this.craftingStorage.get(index));
/* 161 */           if (!((TIntCollection)changes.b).isEmpty()) {
/* 162 */             changed = true;
/* 163 */             ItemStack toPut = ((List)changes.a).isEmpty() ? StackUtil.emptyStack : ((List<ItemStack>)changes.a).get(0);
/* 164 */             this.craftingStorage.put(index, toPut);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       if (changed) {
/* 170 */         func_70296_d();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getPossible(int max, ItemStack existing, ItemStack in) {
/* 176 */     int amount = Math.min(max, in.func_77985_e() ? in.func_77976_d() : 1);
/*     */     
/* 178 */     if (!StackUtil.isEmpty(existing)) {
/* 179 */       if (!StackUtil.checkItemEqualityStrict(existing, in)) return 0;
/*     */       
/* 181 */       amount -= StackUtil.getSize(existing);
/*     */     } 
/*     */     
/* 184 */     return Math.min(amount, StackUtil.getSize(in));
/*     */   }
/*     */   
/*     */   private static ItemStack transfer(InvSlot slot, ItemStack gridItem, boolean allowEmpty) {
/* 188 */     for (int index = 0; index < slot.size(); index++) {
/* 189 */       ItemStack stack = slot.get(index);
/*     */       
/* 191 */       int amount = getPossible(slot.getStackSizeLimit(), stack, gridItem);
/* 192 */       if (amount < 1)
/*     */         continue; 
/* 194 */       if (StackUtil.isEmpty(stack)) {
/* 195 */         if (!allowEmpty)
/*     */           continue; 
/* 197 */         slot.put(index, StackUtil.copyWithSize(gridItem, amount));
/*     */       } else {
/* 199 */         slot.put(index, StackUtil.incSize(stack, amount));
/*     */       } 
/*     */       
/* 202 */       gridItem = StackUtil.decSize(gridItem, amount);
/* 203 */       if (StackUtil.isEmpty(gridItem))
/*     */         break;  continue;
/*     */     } 
/* 206 */     return gridItem;
/*     */   }
/*     */   
/*     */   public void clear(EntityPlayer player) {
/* 210 */     if (!this.craftingGrid.isEmpty()) {
/* 211 */       int index; label26: for (index = 0; index < this.craftingGrid.size(); index++) {
/* 212 */         if (!this.craftingGrid.isEmpty(index)) {
/* 213 */           ItemStack stack = this.craftingGrid.get(index);
/*     */           
/* 215 */           for (int pass = 0; pass < 2; pass++) {
/* 216 */             stack = transfer(this.craftingStorage, stack, (pass == 1));
/* 217 */             if (StackUtil.isEmpty(stack)) {
/* 218 */               this.craftingGrid.clear(index);
/*     */               
/*     */               continue label26;
/*     */             } 
/*     */           } 
/*     */           
/* 224 */           if (StackUtil.storeInventoryItem(stack, player, false)) {
/* 225 */             this.craftingGrid.clear(index);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 230 */             this.craftingGrid.put(stack);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContainerBase<TileEntityIndustrialWorkbench> getGuiContainer(EntityPlayer player) {
/* 238 */     return (ContainerBase<TileEntityIndustrialWorkbench>)new ContainerIndustrialWorkbench(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 244 */     return (GuiScreen)new GuiIndustrialWorkbench(new ContainerIndustrialWorkbench(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/* 252 */   public final InvSlot craftingGrid = new InvSlot((IInventorySlotHolder)this, "crafting", InvSlot.Access.NONE, 9);
/* 253 */   public final InvSlot craftingStorage = new InvSlot((IInventorySlotHolder)this, "craftingStorage", InvSlot.Access.I, 18);
/*     */   
/* 255 */   public final InvSlotCraftingCombo leftCrafting = new InvSlotCraftingCombo(this, "left", "craftingToolForgeHammer");
/* 256 */   public final InvSlotCraftingCombo rightCrafting = new InvSlotCraftingCombo(this, "right", "craftingToolWireCutter");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityIndustrialWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */