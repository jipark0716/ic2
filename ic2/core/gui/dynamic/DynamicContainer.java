/*     */ package ic2.core.gui.dynamic;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.slot.SlotHologramSlot;
/*     */ import ic2.core.slot.SlotInvSlot;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class DynamicContainer<T extends IInventory>
/*     */   extends ContainerBase<T>
/*     */ {
/*     */   public static <T extends IInventory> DynamicContainer<T> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/*  35 */     return new DynamicContainer<>(base, player, guiNode);
/*     */   }
/*     */   
/*     */   protected DynamicContainer(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/*  39 */     super((IInventory)base);
/*     */     
/*  41 */     initialize(player, guiNode, guiNode);
/*     */   }
/*     */   
/*     */   private void initialize(EntityPlayer player, GuiParser.GuiNode guiNode, GuiParser.ParentNode parentNode) {
/*  45 */     for (GuiParser.Node rawNode : parentNode.getNodes()) {
/*  46 */       GuiParser.PlayerInventoryNode playerInventoryNode; GuiParser.SlotNode slotNode; GuiParser.SlotGridNode slotGridNode; GuiParser.SlotHologramNode node; int xOffset; InvSlot slot; int x; int yOffset; int i; int size; int y; int width; int j; int height; int row; int col; switch (rawNode.getType()) {
/*     */         case environment:
/*  48 */           if (((GuiParser.EnvironmentNode)rawNode).environment != GuiEnvironment.GAME) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         
/*     */         case playerinventory:
/*  54 */           playerInventoryNode = (GuiParser.PlayerInventoryNode)rawNode;
/*  55 */           xOffset = (playerInventoryNode.style.width - 16) / 2;
/*  56 */           yOffset = (playerInventoryNode.style.height - 16) / 2;
/*  57 */           width = playerInventoryNode.style.width + playerInventoryNode.spacing;
/*  58 */           height = playerInventoryNode.style.height + playerInventoryNode.spacing;
/*     */           
/*  60 */           for (row = 0; row < 3; row++) {
/*  61 */             for (int k = 0; k < 9; k++) {
/*  62 */               func_75146_a(new Slot((IInventory)player.field_71071_by, k + row * 9 + 9, playerInventoryNode.x + k * width + xOffset, playerInventoryNode.y + row * height + yOffset));
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  69 */           for (col = 0; col < 9; col++) {
/*  70 */             func_75146_a(new Slot((IInventory)player.field_71071_by, col, playerInventoryNode.x + col * width + xOffset, playerInventoryNode.y + playerInventoryNode.hotbarOffset + yOffset));
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case slot:
/*  79 */           if (!(this.base instanceof IInventorySlotHolder)) {
/*  80 */             throw new RuntimeException("Invalid base " + this.base + " for slot elements");
/*     */           }
/*  82 */           slotNode = (GuiParser.SlotNode)rawNode;
/*     */           
/*  84 */           slot = ((IInventorySlotHolder)this.base).getInventorySlot(slotNode.name);
/*  85 */           if (slot == null) {
/*  86 */             throw new RuntimeException("Invalid InvSlot name " + slotNode.name + " for base " + this.base);
/*     */           }
/*  88 */           i = slotNode.x + (slotNode.style.width - 16) / 2;
/*  89 */           j = slotNode.y + (slotNode.style.height - 16) / 2;
/*     */           
/*  91 */           func_75146_a((Slot)new SlotInvSlot(slot, slotNode.index, i, j));
/*     */           break;
/*     */ 
/*     */         
/*     */         case slotgrid:
/*  96 */           if (!(this.base instanceof IInventorySlotHolder)) {
/*  97 */             throw new RuntimeException("Invalid base " + this.base + " for slot elements");
/*     */           }
/*  99 */           slotGridNode = (GuiParser.SlotGridNode)rawNode;
/*     */           
/* 101 */           slot = ((IInventorySlotHolder)this.base).getInventorySlot(slotGridNode.name);
/* 102 */           if (slot == null) {
/* 103 */             throw new RuntimeException("Invalid InvSlot name " + slotGridNode.name + " for base " + this.base);
/*     */           }
/* 105 */           size = slot.size();
/*     */           
/* 107 */           if (size > slotGridNode.offset) {
/* 108 */             int x0 = slotGridNode.x + (slotGridNode.style.width - 16) / 2;
/* 109 */             int y0 = slotGridNode.y + (slotGridNode.style.height - 16) / 2;
/*     */             
/* 111 */             GuiParser.SlotGridNode.SlotGridDimension dim = slotGridNode.getDimension(size);
/* 112 */             int rows = dim.rows;
/* 113 */             int cols = dim.cols;
/*     */             
/* 115 */             int k = slotGridNode.style.width + slotGridNode.spacing;
/* 116 */             int m = slotGridNode.style.height + slotGridNode.spacing;
/*     */             
/* 118 */             int idx = slotGridNode.offset;
/*     */             
/* 120 */             if (!slotGridNode.vertical) {
/* 121 */               int i2 = y0;
/*     */               
/* 123 */               for (int i3 = 0; i3 < rows && idx < size; i3++) {
/* 124 */                 int i4 = x0;
/*     */                 
/* 126 */                 for (int i5 = 0; i5 < cols && idx < size; i5++) {
/* 127 */                   func_75146_a((Slot)new SlotInvSlot(slot, idx, i4, i2));
/* 128 */                   idx++;
/* 129 */                   i4 += k;
/*     */                 } 
/*     */                 
/* 132 */                 i2 += m;
/*     */               }  break;
/*     */             } 
/* 135 */             int n = x0;
/*     */             
/* 137 */             for (int i1 = 0; i1 < cols && idx < size; i1++) {
/* 138 */               int i2 = y0;
/*     */               
/* 140 */               for (int i3 = 0; i3 < rows && idx < size; i3++) {
/* 141 */                 func_75146_a((Slot)new SlotInvSlot(slot, idx, n, i2));
/* 142 */                 idx++;
/* 143 */                 i2 += m;
/*     */               } 
/*     */               
/* 146 */               n += k;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case slothologram:
/* 154 */           if (!(this.base instanceof IHolographicSlotProvider)) throw new RuntimeException("Invalid base " + this.base + " for holographic slot elements");
/*     */           
/* 156 */           node = (GuiParser.SlotHologramNode)rawNode;
/*     */           
/* 158 */           x = node.x + (node.style.width - 16) / 2;
/* 159 */           y = node.y + (node.style.height - 16) / 2;
/*     */           
/* 161 */           func_75146_a((Slot)new SlotHologramSlot(((IHolographicSlotProvider)this.base).getStacksForName(node.name), node.index, x, y, node.stackSizeLimit, getCallback()));
/*     */           break;
/*     */       } 
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
/* 180 */       if (rawNode instanceof GuiParser.ParentNode) {
/* 181 */         initialize(player, guiNode, (GuiParser.ParentNode)rawNode);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SlotHologramSlot.ChangeCallback getCallback() {
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 192 */     List<String> ret = networkedFieldCache.get(this.base.getClass());
/* 193 */     if (ret != null) return ret;
/*     */     
/* 195 */     ret = new ArrayList<>();
/* 196 */     Class<?> cls = this.base.getClass();
/*     */     
/*     */     do {
/* 199 */       for (Field field : cls.getDeclaredFields()) {
/* 200 */         if (field.getAnnotation(GuiSynced.class) != null) {
/* 201 */           ret.add(field.getName());
/*     */         }
/*     */       } 
/*     */       
/* 205 */       cls = cls.getSuperclass();
/* 206 */     } while (cls != TileEntity.class && cls != Object.class);
/*     */     
/* 208 */     if (ret.isEmpty()) {
/* 209 */       ret = Collections.emptyList();
/*     */     } else {
/* 211 */       ret = new ArrayList<>(ret);
/*     */     } 
/*     */     
/* 214 */     networkedFieldCache.put(this.base.getClass(), ret);
/*     */     
/* 216 */     return ret;
/*     */   }
/*     */   
/* 219 */   private static Map<Class<?>, List<String>> networkedFieldCache = new IdentityHashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\DynamicContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */