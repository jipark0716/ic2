/*    */ package ic2.core.item.upgrade;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.gui.GuiDefaultBackground;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.MouseButton;
/*    */ import ic2.core.gui.ScrollableList;
/*    */ import ic2.core.gui.SlotGrid;
/*    */ import ic2.core.item.ContainerHandHeldInventory;
/*    */ import ic2.core.slot.SlotHologramSlot;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class HandHeldOre
/*    */   extends HandHeldUpgradeOption {
/*    */   public class ContainerEditOre extends ContainerHandHeldInventory<HandHeldOre> {
/*    */     public ContainerEditOre() {
/* 25 */       super(HandHeldOre.this);
/*    */       
/* 27 */       addPlayerInventorySlots(HandHeldOre.this.player, 200);
/* 28 */       for (byte slot = 0; slot < 9; slot = (byte)(slot + 1)) {
/* 29 */         func_75146_a((Slot)new SlotHologramSlot(HandHeldOre.this.inventory, slot, 8 + 18 * slot, 8, 1, HandHeldOre.this.makeSaveCallback()));
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     static final int HEIGHT = 200;
/*    */     
/*    */     public void func_75134_a(EntityPlayer player) {
/* 37 */       super.func_75134_a(player);
/*    */     }
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public class GuiEditOre
/*    */     extends GuiDefaultBackground<ContainerEditOre> {
/*    */     public class ListItem
/*    */       implements ScrollableList.IListItem {
/*    */       private final String number;
/*    */       
/*    */       public ListItem(String number) {
/* 49 */         this.number = number;
/*    */       }
/*    */ 
/*    */       
/*    */       public void onClick(MouseButton button) {
/* 54 */         System.out.println(this.number + " clicked with " + button);
/*    */       }
/*    */ 
/*    */       
/*    */       public String func_176610_l() {
/* 59 */         return "Thing " + this.number;
/*    */       }
/*    */     }
/*    */     
/*    */     public GuiEditOre() {
/* 64 */       super((ContainerBase)new HandHeldOre.ContainerEditOre(HandHeldOre.this), 200);
/*    */       
/* 66 */       addElement((GuiElement)HandHeldOre.this.getBackButton((GuiIC2<?>)this, 10, 96));
/*    */       
/* 68 */       List<ScrollableList.IListItem> items = new ArrayList<>();
/* 69 */       for (String name : new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" }) {
/* 70 */         items.add(new ListItem(name));
/*    */       }
/* 72 */       addElement((GuiElement)new ScrollableList((GuiIC2)this, 10, 30, 120, 60, items));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 77 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
/* 78 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 117, 9, 3, SlotGrid.SlotStyle.Normal));
/* 79 */       addElement((GuiElement)new SlotGrid((GuiIC2)this, 7, 175, 9, 1, SlotGrid.SlotStyle.Normal));
/*    */     }
/*    */   }
/*    */   
/*    */   public HandHeldOre(HandHeldAdvancedUpgrade upgradeGUI) {
/* 84 */     super(upgradeGUI, "ore");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 90 */     return (ContainerBase<?>)new ContainerEditOre();
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 96 */     return (GuiScreen)new GuiEditOre();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\HandHeldOre.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */