/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableItemStack;
/*     */ import ic2.core.block.wiring.TileEntityElectricBlock;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.item.type.CellType;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock.Delegated;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Delegated(current = TileEntityElectrolyzer.class, old = TileEntityClassicElectrolyzer.class)
/*     */ public class TileEntityClassicElectrolyzer
/*     */   extends TileEntityInventory
/*     */   implements IHasGui
/*     */ {
/*     */   public TileEntityElectricBlock mfe;
/*     */   public int ticker;
/*     */   public final InvSlotConsumableItemStack waterSlot;
/*     */   public final InvSlotConsumableItemStack hydrogenSlot;
/*     */   protected AudioSource audio;
/*     */   @GuiSynced
/*     */   protected final Energy energy;
/*     */   
/*     */   public TileEntityClassicElectrolyzer() {
/*  54 */     this.mfe = null;
/*     */     this.ticker = IC2.random.nextInt(16);
/*     */     this.waterSlot = new InvSlotConsumableItemStack((IInventorySlotHolder)this, "water", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP, new ItemStack[] { ItemName.cell.getItemStack((Enum)CellType.water) });
/*     */     this.hydrogenSlot = new InvSlotConsumableItemStack((IInventorySlotHolder)this, "hydrogen", InvSlot.Access.IO, 1, InvSlot.InvSide.BOTTOM, new ItemStack[] { ItemName.cell.getItemStack((Enum)CellType.electrolyzed_water) });
/*     */     this.energy = (Energy)addComponent((TileEntityComponent)new Energy((TileEntityBlock)this, 20000.0D, Util.noFacings, Util.noFacings, 1));
/*     */     this.comparator.setUpdate(this.energy::getComparatorValue); } protected void onLoaded() {
/*  60 */     super.onLoaded();
/*     */     
/*  62 */     if ((func_145831_w()).field_72995_K) this.audio = IC2.audioManager.createSource(this, "Machines/ElectrolyzerLoop.ogg");
/*     */   
/*     */   }
/*     */   
/*     */   protected void onUnloaded() {
/*  67 */     super.onUnloaded();
/*     */     
/*  69 */     if (this.audio != null) {
/*  70 */       IC2.audioManager.removeSources(this);
/*  71 */       this.audio = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  77 */     super.updateEntityServer();
/*     */     
/*  79 */     boolean needsInvUpdate = false;
/*  80 */     boolean turnActive = false;
/*     */     
/*  82 */     if (++this.ticker % 16 == 0) {
/*  83 */       this.mfe = lookForMFE();
/*     */     }
/*  85 */     if (this.mfe == null) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     if (shouldDrain() && canDrain()) {
/*  90 */       needsInvUpdate |= drain();
/*  91 */       turnActive = true;
/*     */     } 
/*     */     
/*  94 */     if (shouldPower() && (canPower() || this.energy.getEnergy() > 0.0D)) {
/*  95 */       needsInvUpdate |= power();
/*  96 */       turnActive = true;
/*     */     } 
/*     */     
/*  99 */     setActive(turnActive);
/*     */     
/* 101 */     if (needsInvUpdate) {
/* 102 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityClient() {
/* 108 */     super.updateEntityClient();
/*     */     
/* 110 */     if (this.ticker++ % 32 == 0 && this.audio != null) {
/* 111 */       this.audio.stop();
/* 112 */       if (getActive()) this.audio.play();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldDrain() {
/* 120 */     return (this.mfe != null && this.mfe.energy.getFillRatio() >= 0.7D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldPower() {
/* 126 */     return (this.mfe != null && this.mfe.energy.getFillRatio() <= 0.3D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDrain() {
/* 133 */     return (this.waterSlot.consume(1, true, false) != null && (this.hydrogenSlot
/* 134 */       .isEmpty() || StackUtil.getSize(this.hydrogenSlot.get()) < Math.min(this.hydrogenSlot.getStackSizeLimit(), this.hydrogenSlot.get().func_77976_d())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPower() {
/* 141 */     return (this.hydrogenSlot.consume(1, true, false) != null && (this.waterSlot
/* 142 */       .isEmpty() || StackUtil.getSize(this.waterSlot.get()) < Math.min(this.waterSlot.getStackSizeLimit(), this.waterSlot.get().func_77976_d())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drain() {
/* 150 */     double amount = processRate();
/*     */     
/* 152 */     if (!this.mfe.energy.useEnergy(amount)) return false;
/*     */     
/* 154 */     this.energy.addEnergy(amount);
/*     */     
/* 156 */     if (this.energy.useEnergy(20000.0D)) {
/* 157 */       this.waterSlot.consume(1);
/*     */       
/* 159 */       if (this.hydrogenSlot.isEmpty()) {
/* 160 */         this.hydrogenSlot.put(ItemName.cell.getItemStack((Enum)CellType.electrolyzed_water));
/*     */       } else {
/* 162 */         this.hydrogenSlot.put(StackUtil.incSize(this.hydrogenSlot.get()));
/*     */       } 
/*     */       
/* 165 */       return true;
/*     */     } 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean power() {
/* 176 */     if (this.energy.getEnergy() > 0.0D) {
/* 177 */       double out = Math.min(this.energy.getEnergy(), processRate());
/*     */       
/* 179 */       this.energy.useEnergy(out);
/* 180 */       this.mfe.energy.addEnergy(out);
/*     */       
/* 182 */       return false;
/*     */     } 
/*     */     
/* 185 */     this.energy.forceAddEnergy((12000 + 2000 * this.mfe.energy.getSinkTier()));
/*     */     
/* 187 */     this.hydrogenSlot.consume(1);
/*     */     
/* 189 */     if (this.waterSlot.isEmpty()) {
/* 190 */       this.waterSlot.put(ItemName.cell.getItemStack((Enum)CellType.water));
/*     */     } else {
/* 192 */       this.waterSlot.put(StackUtil.incSize(this.waterSlot.get()));
/*     */     } 
/*     */     
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int processRate() {
/* 203 */     switch (this.mfe.energy.getSinkTier()) { default:
/* 204 */         return 2;
/* 205 */       case 2: return 8;
/* 206 */       case 3: return 32;
/* 207 */       case 4: break; }  return 128;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityElectricBlock lookForMFE() {
/* 216 */     World world = func_145831_w();
/*     */     
/* 218 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 219 */       TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */       
/* 221 */       if (te instanceof TileEntityElectricBlock) return (TileEntityElectricBlock)te;
/*     */     
/*     */     } 
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 229 */     return (ContainerBase<?>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 235 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityClassicElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */