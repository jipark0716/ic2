/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.machine.container.ContainerFluidDistributor;
/*     */ import ic2.core.block.machine.gui.GuiFluidDistributor;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityFluidDistributor
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, INetworkClientTileEntityEventListener
/*     */ {
/*  43 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this)); @GuiSynced
/*  44 */   public final Fluids.InternalFluidTank fluidTank = this.fluids.addTank("fluidTank", 1000);
/*     */   
/*  46 */   public final InvSlotConsumableLiquidByTank inputSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "inputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.fluidTank);
/*  47 */   public final InvSlotOutput OutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "OutputSlot", 1);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  52 */     super.onLoaded();
/*  53 */     updateConnectivity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActive(boolean val) {
/*  58 */     super.setActive(val);
/*  59 */     updateConnectivity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacing(EnumFacing facing) {
/*  64 */     super.setFacing(facing);
/*  65 */     updateConnectivity();
/*     */   }
/*     */   
/*     */   protected void updateConnectivity() {
/*  69 */     EnumSet<EnumFacing> acceptingSides = EnumSet.of(getFacing());
/*  70 */     if (getActive()) {
/*  71 */       acceptingSides = EnumSet.complementOf(acceptingSides);
/*     */     }
/*  73 */     this.fluids.changeConnectivity(this.fluidTank, acceptingSides, Collections.emptySet());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  78 */     super.updateEntityServer();
/*     */     
/*  80 */     this.inputSlot.processFromTank((IFluidTank)this.fluidTank, this.OutputSlot);
/*     */     
/*  82 */     if (this.fluidTank.getFluidAmount() > 0) {
/*  83 */       moveFluid();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void moveFluid() {
/*  88 */     World world = func_145831_w();
/*     */     
/*  90 */     if (getActive()) {
/*  91 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(getFacing()));
/*  92 */       EnumFacing side = getFacing().func_176734_d();
/*     */       
/*  94 */       if (LiquidUtil.isFluidTile(target, side)) {
/*  95 */         int amount = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), false);
/*     */         
/*  97 */         if (amount > 0) {
/*  98 */           this.fluidTank.drainInternal(amount, true);
/*     */         }
/*     */       } 
/*     */     } else {
/* 102 */       Map<EnumFacing, TileEntity> acceptingNeighbors = new EnumMap<>(EnumFacing.class);
/* 103 */       int acceptedVolume = 0;
/*     */       
/* 105 */       for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 106 */         if (dir != getFacing()) {
/*     */           
/* 108 */           TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/* 109 */           EnumFacing side = dir.func_176734_d();
/*     */           
/* 111 */           if (LiquidUtil.isFluidTile(target, side)) {
/* 112 */             int amount = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), true);
/*     */             
/* 114 */             if (amount > 0) {
/* 115 */               acceptingNeighbors.put(dir, target);
/* 116 */               acceptedVolume += amount;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 121 */       while (!acceptingNeighbors.isEmpty()) {
/* 122 */         int amount = Math.min(acceptedVolume, this.fluidTank.getFluidAmount());
/* 123 */         if (amount <= 0)
/*     */           break; 
/* 125 */         amount /= acceptingNeighbors.size();
/*     */         
/* 127 */         if (amount > 0) {
/* 128 */           for (Iterator<Map.Entry<EnumFacing, TileEntity>> it = acceptingNeighbors.entrySet().iterator(); it.hasNext(); ) {
/* 129 */             Map.Entry<EnumFacing, TileEntity> entry = it.next();
/* 130 */             TileEntity target = entry.getValue();
/* 131 */             EnumFacing side = ((EnumFacing)entry.getKey()).func_176734_d();
/* 132 */             FluidStack fs = this.fluidTank.getFluid();
/* 133 */             if (fs == null) {
/*     */               break;
/*     */             }
/* 136 */             fs = fs.copy();
/*     */             
/* 138 */             if (fs.amount <= 0)
/*     */               break; 
/* 140 */             fs.amount = Math.min(amount, fs.amount);
/*     */             
/* 142 */             int cAmount = LiquidUtil.fillTile(target, side, fs, false);
/* 143 */             this.fluidTank.drainInternal(cAmount, true);
/* 144 */             acceptedVolume -= cAmount;
/*     */             
/* 146 */             if (cAmount < fs.amount) it.remove(); 
/*     */           }  continue;
/*     */         } 
/* 149 */         for (Map.Entry<EnumFacing, TileEntity> entry : acceptingNeighbors.entrySet()) {
/* 150 */           TileEntity target = entry.getValue();
/* 151 */           EnumFacing side = ((EnumFacing)entry.getKey()).func_176734_d();
/* 152 */           FluidStack fs = this.fluidTank.getFluid();
/* 153 */           if (fs == null) {
/*     */             break;
/*     */           }
/* 156 */           fs = fs.copy();
/*     */ 
/*     */           
/* 159 */           fs.amount = Math.min(acceptedVolume, fs.amount);
/* 160 */           if (fs.amount <= 0)
/*     */             break; 
/* 162 */           int cAmount = LiquidUtil.fillTile(target, side, fs, false);
/* 163 */           this.fluidTank.drainInternal(cAmount, true);
/* 164 */           acceptedVolume -= cAmount;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 177 */     setActive(!getActive());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 183 */     return (ContainerBase<?>)new ContainerFluidDistributor(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 189 */     return (GuiScreen)new GuiFluidDistributor(new ContainerFluidDistributor(player, this));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */