/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.slot.SlotHologramSlot;
/*     */ import ic2.core.slot.SlotInvSlot;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IContainerListener;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public abstract class ContainerBase<T extends IInventory> extends Container {
/*     */   protected static final int windowBorder = 8;
/*     */   protected static final int slotSize = 16;
/*     */   protected static final int slotDistance = 2;
/*     */   
/*     */   public ContainerBase(T base1) {
/*  28 */     this.base = base1;
/*     */   }
/*     */   protected static final int slotSeparator = 4; protected static final int hotbarYOffset = -24; protected static final int inventoryYOffset = -82; public final T base;
/*     */   protected void addPlayerInventorySlots(EntityPlayer player, int height) {
/*  32 */     addPlayerInventorySlots(player, 178, height);
/*     */   }
/*     */   
/*     */   protected void addPlayerInventorySlots(EntityPlayer player, int width, int height) {
/*  36 */     int xStart = (width - 162) / 2;
/*     */     
/*  38 */     for (int row = 0; row < 3; row++) {
/*  39 */       for (int i = 0; i < 9; i++) {
/*  40 */         func_75146_a(new Slot((IInventory)player.field_71071_by, i + row * 9 + 9, xStart + i * 18, height + -82 + row * 18));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     for (int col = 0; col < 9; col++) {
/*  48 */       func_75146_a(new Slot((IInventory)player.field_71071_by, col, xStart + col * 18, height + -24));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_184996_a(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
/*     */     Slot slot;
/*  59 */     if (slotId >= 0 && slotId < this.field_75151_b.size() && 
/*  60 */       slot = this.field_75151_b.get(slotId) instanceof SlotHologramSlot) {
/*  61 */       return ((SlotHologramSlot)slot).slotClick(dragType, clickType, player);
/*     */     }
/*  63 */     return super.func_184996_a(slotId, dragType, clickType, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ItemStack func_82846_b(EntityPlayer player, int sourceSlotIndex) {
/*  69 */     Slot sourceSlot = this.field_75151_b.get(sourceSlotIndex);
/*     */     
/*  71 */     if (sourceSlot != null && sourceSlot.func_75216_d()) {
/*  72 */       ItemStack resultStack, sourceItemStack = sourceSlot.func_75211_c();
/*     */       
/*  74 */       int oldSourceItemStackSize = StackUtil.getSize(sourceItemStack);
/*     */       
/*  76 */       if (sourceSlot.field_75224_c == player.field_71071_by) {
/*  77 */         resultStack = handlePlayerSlotShiftClick(player, sourceItemStack);
/*     */       } else {
/*  79 */         resultStack = handleGUISlotShiftClick(player, sourceItemStack);
/*     */       } 
/*     */       
/*  82 */       if (StackUtil.isEmpty(resultStack) || StackUtil.getSize(resultStack) != oldSourceItemStackSize) {
/*  83 */         sourceSlot.func_75215_d(resultStack);
/*  84 */         sourceSlot.func_190901_a(player, sourceItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  89 */         if (!(player.func_130014_f_()).field_72995_K) {
/*  90 */           func_75142_b();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack handlePlayerSlotShiftClick(EntityPlayer player, ItemStack sourceItemStack) {
/* 100 */     for (int run = 0; run < 4 && !StackUtil.isEmpty(sourceItemStack); run++) {
/* 101 */       for (Slot targetSlot : this.field_75151_b) {
/* 102 */         if (targetSlot.field_75224_c != player.field_71071_by)
/* 103 */           if (isValidTargetSlot(targetSlot, sourceItemStack, (run % 2 == 1), (run < 2))) {
/* 104 */             sourceItemStack = transfer(sourceItemStack, targetSlot);
/*     */             
/* 106 */             if (StackUtil.isEmpty(sourceItemStack))
/*     */               break; 
/*     */           }  
/*     */       } 
/*     */     } 
/* 111 */     return sourceItemStack;
/*     */   }
/*     */   
/*     */   protected ItemStack handleGUISlotShiftClick(EntityPlayer player, ItemStack sourceItemStack) {
/* 115 */     for (int run = 0; run < 2 && !StackUtil.isEmpty(sourceItemStack); run++) {
/* 116 */       for (ListIterator<Slot> it = this.field_75151_b.listIterator(this.field_75151_b.size()); it.hasPrevious(); ) {
/* 117 */         Slot targetSlot = it.previous();
/*     */         
/* 119 */         if (targetSlot.field_75224_c == player.field_71071_by && 
/* 120 */           isValidTargetSlot(targetSlot, sourceItemStack, (run == 1), false)) {
/* 121 */           sourceItemStack = transfer(sourceItemStack, targetSlot);
/*     */           
/* 123 */           if (StackUtil.isEmpty(sourceItemStack))
/*     */             break; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 128 */     return sourceItemStack;
/*     */   }
/*     */   
/*     */   protected static final boolean isValidTargetSlot(Slot slot, ItemStack stack, boolean allowEmpty, boolean requireInputOnly) {
/* 132 */     if (slot instanceof ic2.core.slot.SlotInvSlotReadOnly || slot instanceof SlotHologramSlot)
/*     */     {
/* 134 */       return false;
/*     */     }
/*     */     
/* 137 */     if (!slot.func_75214_a(stack)) return false; 
/* 138 */     if (!allowEmpty && !slot.func_75216_d()) return false;
/*     */     
/* 140 */     if (requireInputOnly) {
/* 141 */       return (slot instanceof SlotInvSlot && ((SlotInvSlot)slot).invSlot
/* 142 */         .canInput());
/*     */     }
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 151 */     return this.base.func_70300_a(entityplayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/* 156 */     super.func_75142_b();
/*     */     
/* 158 */     if (this.base instanceof TileEntity) {
/* 159 */       for (String name : getNetworkedFields()) {
/* 160 */         for (IContainerListener crafter : this.field_75149_d) {
/* 161 */           if (crafter instanceof EntityPlayerMP) {
/* 162 */             ((NetworkManager)IC2.network.get(true)).updateTileEntityFieldTo((TileEntity)this.base, name, (EntityPlayerMP)crafter);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 167 */       if (this.base instanceof TileEntityBlock) {
/* 168 */         for (TileEntityComponent component : ((TileEntityBlock)this.base).getComponents()) {
/* 169 */           for (IContainerListener crafter : this.field_75149_d) {
/* 170 */             if (crafter instanceof EntityPlayerMP) {
/* 171 */               component.onContainerUpdate((EntityPlayerMP)crafter);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 180 */     return new ArrayList<>();
/*     */   }
/*     */   
/*     */   public List<IContainerListener> getListeners() {
/* 184 */     return this.field_75149_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onContainerEvent(String event) {}
/*     */ 
/*     */   
/*     */   protected final ItemStack transfer(ItemStack stack, Slot dst) {
/* 192 */     int amount = getTransferAmount(stack, dst);
/* 193 */     if (amount <= 0) return stack;
/*     */     
/* 195 */     ItemStack dstStack = dst.func_75211_c();
/*     */     
/* 197 */     if (StackUtil.isEmpty(dstStack)) {
/* 198 */       dst.func_75215_d(StackUtil.copyWithSize(stack, amount));
/*     */     } else {
/* 200 */       dst.func_75215_d(StackUtil.incSize(dstStack, amount));
/*     */     } 
/*     */     
/* 203 */     stack = StackUtil.decSize(stack, amount);
/*     */     
/* 205 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getTransferAmount(ItemStack stack, Slot dst) {
/* 210 */     int amount = Math.min(dst.field_75224_c.func_70297_j_(), dst.func_75219_a());
/* 211 */     amount = Math.min(amount, stack.func_77985_e() ? stack.func_77976_d() : 1);
/*     */     
/* 213 */     ItemStack dstStack = dst.func_75211_c();
/*     */     
/* 215 */     if (!StackUtil.isEmpty(dstStack)) {
/* 216 */       if (!StackUtil.checkItemEqualityStrict(stack, dstStack)) return 0;
/*     */       
/* 218 */       amount -= StackUtil.getSize(dstStack);
/*     */     } 
/*     */ 
/*     */     
/* 222 */     amount = Math.min(amount, StackUtil.getSize(stack));
/*     */     
/* 224 */     return amount;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ContainerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */