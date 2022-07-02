/*    */ package ic2.core.block.storage.box;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.gui.dynamic.DynamicContainer;
/*    */ import ic2.core.gui.dynamic.DynamicGui;
/*    */ import ic2.core.gui.dynamic.GuiParser;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public abstract class TileEntityStorageBox
/*    */   extends TileEntityInventory implements IHasGui {
/*    */   public TileEntityStorageBox(int inventorySize) {
/* 30 */     this.inventory = new InvSlot((IInventorySlotHolder)this, "inventory", InvSlot.Access.IO, inventorySize, InvSlot.InvSide.ANY);
/*    */   }
/*    */   protected final InvSlot inventory;
/*    */   
/*    */   protected List<ItemStack> getAuxDrops(int fortune) {
/* 35 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/* 40 */     super.onPlaced(stack, placer, facing);
/*    */     
/* 42 */     if (!(func_145831_w()).field_72995_K) {
/* 43 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 44 */       this.inventory.readFromNbt(nbt);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/* 50 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(drop);
/*    */     
/* 52 */     if (!this.inventory.isEmpty()) {
/* 53 */       this.inventory.writeToNbt(nbt);
/*    */     }
/*    */     
/* 56 */     return drop;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void addInformation(ItemStack stack, List<String> info, ITooltipFlag advanced) {
/* 62 */     info.add("Stores items even when broken");
/* 63 */     info.add("Inventory size: " + this.inventory.size());
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundType getBlockSound(Entity entity) {
/* 68 */     return SoundType.field_185852_e;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerBase<? extends TileEntityStorageBox> getGuiContainer(EntityPlayer player) {
/* 74 */     return (ContainerBase<? extends TileEntityStorageBox>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 80 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\storage\box\TileEntityStorageBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */