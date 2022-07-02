/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.ClientModifiable;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
/*     */ import ic2.core.block.machine.gui.GuiWeightedItemDistributor;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityWeightedItemDistributor
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IWeightedDistributor
/*     */ {
/*  33 */   public final InvSlot buffer = new InvSlot((IInventorySlotHolder)this, "buffer", InvSlot.Access.I, 9);
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  38 */     super.func_189515_b(nbt);
/*     */     
/*  40 */     if (!this.priority.isEmpty()) {
/*  41 */       int[] indexes = new int[this.priority.size()];
/*     */       
/*  43 */       for (int i = 0; i < indexes.length; i++) {
/*  44 */         indexes[i] = ((EnumFacing)this.priority.get(i)).func_176745_a();
/*     */       }
/*     */       
/*  47 */       nbt.func_74783_a("priority", indexes);
/*     */     } 
/*     */     
/*  50 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  55 */     super.func_145839_a(nbt);
/*     */     
/*  57 */     int[] indexes = nbt.func_74759_k("priority");
/*     */     
/*  59 */     if (indexes.length > 0) {
/*  60 */       for (int index : indexes) {
/*  61 */         this.priority.add(EnumFacing.func_82600_a(index));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/*  68 */     List<String> ret = super.getNetworkedFields();
/*     */     
/*  70 */     ret.add("priority");
/*     */     
/*  72 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  78 */     super.onLoaded();
/*     */     
/*  80 */     updateConnectivity();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setFacing(EnumFacing facing) {
/*  85 */     super.setFacing(facing);
/*     */     
/*  87 */     updateConnectivity();
/*     */   }
/*     */   
/*     */   protected void updateConnectivity() {
/*  91 */     if (!(func_145831_w()).field_72995_K && !this.priority.isEmpty() && this.priority.remove(getFacing())) {
/*  92 */       updatePriority(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  98 */     super.updateEntityServer();
/*     */     
/* 100 */     if (!this.priority.isEmpty() && !this.buffer.isEmpty()) {
/* 101 */       World world = func_145831_w();
/* 102 */       boolean hasChanged = false;
/*     */       
/* 104 */       for (EnumFacing facing : this.priority) {
/* 105 */         TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(facing));
/* 106 */         EnumFacing side = facing.func_176734_d();
/*     */         
/* 108 */         if (StackUtil.isInventoryTile(te, side)) {
/* 109 */           boolean empty = true;
/*     */           
/* 111 */           for (int index = 0; index < this.buffer.size(); index++) {
/* 112 */             if (!this.buffer.isEmpty(index)) {
/* 113 */               ItemStack stack = this.buffer.get(index);
/*     */               
/* 115 */               ItemStack transferStack = StackUtil.copy(stack);
/* 116 */               int amount = StackUtil.putInInventory(te, side, transferStack, true);
/*     */               
/* 118 */               if (amount > 0) {
/* 119 */                 amount = StackUtil.putInInventory(te, side, transferStack, false);
/* 120 */                 stack = StackUtil.decSize(stack, amount);
/*     */                 
/* 122 */                 this.buffer.put(index, stack);
/*     */                 
/* 124 */                 hasChanged = true;
/* 125 */                 empty &= StackUtil.isEmpty(stack);
/*     */               } 
/*     */             } 
/*     */           } 
/* 129 */           if (hasChanged && empty)
/*     */             break; 
/*     */         } 
/*     */       } 
/* 133 */       if (hasChanged) func_70296_d();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 140 */     return (ContainerBase<?>)new ContainerWeightedItemDistributor(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 146 */     return (GuiScreen)new GuiWeightedItemDistributor(new ContainerWeightedItemDistributor(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public List<EnumFacing> getPriority() {
/* 152 */     return this.priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePriority(boolean server) {
/* 157 */     ((NetworkManager)IC2.network.get(server)).updateTileEntityField((TileEntity)this, "priority");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   @ClientModifiable
/* 165 */   protected List<EnumFacing> priority = new ArrayList<>(5);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityWeightedItemDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */