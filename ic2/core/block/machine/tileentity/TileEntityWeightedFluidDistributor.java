/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.ClientModifiable;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
/*     */ import ic2.core.block.machine.gui.GuiWeightedFluidDistributor;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityWeightedFluidDistributor
/*     */   extends TileEntityFluidDistributor
/*     */   implements IWeightedDistributor
/*     */ {
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  29 */     super.func_189515_b(nbt);
/*     */     
/*  31 */     if (!this.priority.isEmpty()) {
/*  32 */       int[] indexes = new int[this.priority.size()];
/*     */       
/*  34 */       for (int i = 0; i < indexes.length; i++) {
/*  35 */         indexes[i] = ((EnumFacing)this.priority.get(i)).func_176745_a();
/*     */       }
/*     */       
/*  38 */       nbt.func_74783_a("priority", indexes);
/*     */     } 
/*     */     
/*  41 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  46 */     super.func_145839_a(nbt);
/*     */     
/*  48 */     int[] indexes = nbt.func_74759_k("priority");
/*     */     
/*  50 */     if (indexes.length > 0) {
/*  51 */       for (int index : indexes) {
/*  52 */         this.priority.add(EnumFacing.func_82600_a(index));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/*  59 */     List<String> ret = super.getNetworkedFields();
/*     */     
/*  61 */     ret.add("priority");
/*     */     
/*  63 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateConnectivity() {
/*  69 */     if (!(func_145831_w()).field_72995_K && !this.priority.isEmpty() && this.priority.remove(getFacing())) {
/*  70 */       updatePriority(true);
/*     */     }
/*     */     
/*  73 */     this.fluids.changeConnectivity(this.fluidTank, Collections.singleton(getFacing()), Collections.emptySet());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void moveFluid() {
/*  78 */     if (!this.priority.isEmpty()) {
/*  79 */       int tankAmount = this.fluidTank.getFluidAmount();
/*     */       
/*  81 */       for (EnumFacing dir : this.priority) {
/*  82 */         assert dir != getFacing();
/*     */         
/*  84 */         TileEntity target = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*  85 */         EnumFacing side = dir.func_176734_d();
/*     */         
/*  87 */         if (LiquidUtil.isFluidTile(target, side)) {
/*  88 */           int amount = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), false);
/*     */           
/*  90 */           if (amount > 0) {
/*  91 */             tankAmount -= amount;
/*  92 */             this.fluidTank.drainInternal(amount, true);
/*     */             
/*  94 */             if (tankAmount <= 0) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 104 */     return (ContainerBase<?>)new ContainerWeightedFluidDistributor(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 110 */     return (GuiScreen)new GuiWeightedFluidDistributor(new ContainerWeightedFluidDistributor(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public List<EnumFacing> getPriority() {
/* 116 */     return this.priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePriority(boolean server) {
/* 121 */     ((NetworkManager)IC2.network.get(server)).updateTileEntityField((TileEntity)this, "priority");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 126 */     int position = event / 10;
/* 127 */     EnumFacing facing = EnumFacing.func_82600_a(event % 10 & 0x6);
/*     */     
/* 129 */     assert position >= 0 && position <= this.priority.size() : "Position was " + position;
/* 130 */     assert facing != getFacing();
/*     */     
/* 132 */     if (position == this.priority.size()) {
/* 133 */       this.priority.add(facing);
/*     */     } else {
/* 135 */       this.priority.set(position, facing);
/*     */     } 
/*     */   }
/*     */   
/*     */   @ClientModifiable
/* 140 */   protected List<EnumFacing> priority = new ArrayList<>(5);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityWeightedFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */