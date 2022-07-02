/*     */ package ic2.core.block.machine.container;
/*     */ 
/*     */ import gnu.trove.TIntCollection;
/*     */ import gnu.trove.iterator.TIntIterator;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.ContainerFullInv;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.slot.SlotInvSlot;
/*     */ import ic2.core.util.InventorySlotCrafting;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.inventory.SlotCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class ContainerIndustrialWorkbench
/*     */   extends ContainerFullInv<TileEntityIndustrialWorkbench> {
/*  29 */   protected final InventoryCrafting craftMatrix = (InventoryCrafting)new InventorySlotCrafting(3, 3)
/*     */     {
/*     */       protected ItemStack get(int index) {
/*  32 */         return ((TileEntityIndustrialWorkbench)ContainerIndustrialWorkbench.this.base).craftingGrid.get(index);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void put(int index, ItemStack stack) {
/*  37 */         ((TileEntityIndustrialWorkbench)ContainerIndustrialWorkbench.this.base).craftingGrid.put(index, stack);
/*     */         
/*  39 */         ContainerIndustrialWorkbench.this.func_75130_a((IInventory)this);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean func_191420_l() {
/*  44 */         return ((TileEntityIndustrialWorkbench)ContainerIndustrialWorkbench.this.base).craftingGrid.isEmpty();
/*     */       }
/*     */ 
/*     */       
/*     */       public void func_174888_l() {
/*  49 */         ((TileEntityIndustrialWorkbench)ContainerIndustrialWorkbench.this.base).craftingGrid.clear();
/*     */       }
/*     */     };
/*  52 */   protected final IInventory craftResult = (IInventory)new InventoryCraftResult();
/*  53 */   protected final Slot[] outputs = new Slot[3]; public final EntityPlayer player;
/*     */   public final int indexOutput;
/*     */   public final int indexGridStart;
/*     */   public final int indexGridEnd;
/*     */   public final int indexBufferStart;
/*     */   
/*     */   public ContainerIndustrialWorkbench(EntityPlayer player, TileEntityIndustrialWorkbench tileEntity) {
/*  60 */     super(player, (IInventory)tileEntity, 228);
/*     */     
/*  62 */     this.player = player;
/*  63 */     this.indexOutput = this.field_75151_b.size();
/*  64 */     this.outputs[0] = func_75146_a((Slot)new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 124, 61)
/*     */         {
/*     */           protected void func_75208_c(ItemStack stack) {
/*  67 */             if (IC2.platform.isRendering()) {
/*  68 */               ((NetworkManager)IC2.network.get(false)).sendContainerEvent((ContainerBase)ContainerIndustrialWorkbench.this, "craft");
/*     */             } else {
/*  70 */               ContainerIndustrialWorkbench.this.onContainerEvent("craft");
/*     */             } 
/*     */             
/*  73 */             super.func_75208_c(stack);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public ItemStack func_190901_a(EntityPlayer thePlayer, ItemStack stack) {
/*  82 */             ForgeHooks.setCraftingPlayer(thePlayer);
/*  83 */             if (CraftingManager.func_192413_b(ContainerIndustrialWorkbench.this.craftMatrix, thePlayer.field_70170_p) != null) {
/*  84 */               stack = super.func_190901_a(thePlayer, stack);
/*     */             }
/*  86 */             ForgeHooks.setCraftingPlayer(null);
/*  87 */             return stack;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  92 */     this.indexGridStart = this.field_75151_b.size(); int y;
/*  93 */     for (y = 0; y < 3; y++) {
/*  94 */       for (int x = 0; x < 3; x++) {
/*  95 */         func_75146_a((Slot)new SlotInvSlot(tileEntity.craftingGrid, x + y * 3, 30 + x * 18, 43 + y * 18)
/*     */             {
/*     */               public void func_75218_e() {
/*  98 */                 super.func_75218_e();
/*     */                 
/* 100 */                 ContainerIndustrialWorkbench.this.func_75130_a((IInventory)ContainerIndustrialWorkbench.this.craftMatrix);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/* 105 */     this.indexGridEnd = this.field_75151_b.size();
/*     */     
/* 107 */     this.indexBufferStart = this.field_75151_b.size();
/* 108 */     for (y = 0; y < 2; y++) {
/* 109 */       for (int x = 0; x < 9; x++) {
/* 110 */         func_75146_a((Slot)new SlotInvSlot(tileEntity.craftingStorage, x + y * 9, 8 + x * 18, 106 + y * 18));
/*     */       }
/*     */     } 
/* 113 */     this.indexBufferEnd = this.field_75151_b.size();
/*     */     
/* 115 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.leftCrafting.tool, 0, 7, 17));
/* 116 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.leftCrafting.input, 0, 25, 17));
/* 117 */     this.indexOutputHammer = this.field_75151_b.size();
/* 118 */     this.outputs[1] = func_75146_a((Slot)new SlotCrafting(player, tileEntity.leftCrafting.crafting, (IInventory)tileEntity.leftCrafting.resultInv, 0, 69, 17));
/*     */     
/* 120 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.rightCrafting.tool, 0, 91, 17));
/* 121 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.rightCrafting.input, 0, 109, 17));
/* 122 */     this.indexOutputCutter = this.field_75151_b.size();
/* 123 */     this.outputs[2] = func_75146_a((Slot)new SlotCrafting(player, tileEntity.rightCrafting.crafting, (IInventory)tileEntity.rightCrafting.resultInv, 0, 153, 17));
/*     */     
/* 125 */     func_75130_a((IInventory)this.craftMatrix);
/*     */   }
/*     */   public final int indexBufferEnd; public final int indexOutputHammer; public final int indexOutputCutter; public static final int WIDTH = 194; public static final int HEIGHT = 228;
/*     */   
/*     */   public void onContainerEvent(String event) {
/* 130 */     if ("craft".equals(event)) {
/* 131 */       func_75142_b();
/* 132 */       ((TileEntityIndustrialWorkbench)this.base).rebalance();
/* 133 */       func_75142_b();
/* 134 */     } else if ("clear".equals(event)) {
/* 135 */       func_75142_b();
/* 136 */       ((TileEntityIndustrialWorkbench)this.base).clear(this.player);
/* 137 */       func_75142_b();
/*     */     } 
/* 139 */     super.onContainerEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75130_a(IInventory inventory) {
/* 144 */     this.craftResult.func_70299_a(0, CraftingManager.func_82787_a(this.craftMatrix, ((TileEntityIndustrialWorkbench)this.base).func_145831_w()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94530_a(ItemStack stack, Slot slot) {
/* 149 */     for (Slot output : this.outputs) {
/* 150 */       if (slot.field_75224_c == output.field_75224_c) return false;
/*     */     
/*     */     } 
/* 153 */     return super.func_94530_a(stack, slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack handlePlayerSlotShiftClick(EntityPlayer player, ItemStack sourceItemStack) {
/* 158 */     Tuple.T2<List<ItemStack>, ? extends TIntCollection> changes = StackUtil.balanceStacks((IInventory)this.craftMatrix, sourceItemStack);
/* 159 */     for (TIntIterator iter = ((TIntCollection)changes.b).iterator(); iter.hasNext(); ) {
/* 160 */       int currentSlot = iter.next();
/* 161 */       ((Slot)this.field_75151_b.get(currentSlot + 37)).func_75218_e();
/*     */     } 
/* 163 */     if (!((List)changes.a).isEmpty())
/*     */     {
/* 165 */       return super.handlePlayerSlotShiftClick(player, ((List<ItemStack>)changes.a).get(0));
/*     */     }
/* 167 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemStack handleGUISlotShiftClick(EntityPlayer player, ItemStack sourceItemStack) {
/* 173 */     ItemStack start = sourceItemStack.func_77946_l();
/*     */     
/* 175 */     Slot craftingSlot = null;
/* 176 */     for (Slot slot : this.outputs) {
/* 177 */       if (slot.func_75211_c() == sourceItemStack) {
/* 178 */         craftingSlot = slot;
/*     */         break;
/*     */       } 
/*     */     } 
/* 182 */     boolean isOutput = (craftingSlot != null);
/*     */     
/* 184 */     boolean isBuffer = false;
/* 185 */     for (int i = this.indexBufferStart; i < this.indexBufferEnd; i++) {
/* 186 */       Slot slot = this.field_75151_b.get(i);
/* 187 */       if (slot.func_75211_c() == sourceItemStack) {
/* 188 */         isBuffer = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     for (int run = 0; run < 2 && !StackUtil.isEmpty(sourceItemStack); run++) {
/* 195 */       for (ListIterator<Slot> it = this.field_75151_b.listIterator(this.field_75151_b.size()); it.hasPrevious(); ) {
/* 196 */         Slot targetSlot = it.previous();
/*     */         
/* 198 */         if ((targetSlot.field_75224_c == player.field_71071_by || (!isBuffer && targetSlot.field_75222_d >= this.indexBufferStart && targetSlot.field_75222_d < this.indexBufferEnd)) && 
/* 199 */           isValidTargetSlot(targetSlot, sourceItemStack, (run == 1), false)) {
/* 200 */           sourceItemStack = transfer(sourceItemStack, targetSlot);
/*     */ 
/*     */           
/* 203 */           if (StackUtil.isEmpty(sourceItemStack)) {
/* 204 */             if (isOutput) {
/* 205 */               craftingSlot.func_75220_a(sourceItemStack, start);
/* 206 */               craftingSlot.func_190901_a(player, start);
/*     */ 
/*     */               
/* 209 */               if (craftingSlot.func_75216_d() && StackUtil.checkItemEquality(craftingSlot.func_75211_c(), start)) {
/* 210 */                 sourceItemStack = craftingSlot.func_75211_c();
/* 211 */                 start = sourceItemStack.func_77946_l();
/*     */                 
/* 213 */                 assert it.hasNext();
/* 214 */                 it.next();
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 224 */     return sourceItemStack;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerIndustrialWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */