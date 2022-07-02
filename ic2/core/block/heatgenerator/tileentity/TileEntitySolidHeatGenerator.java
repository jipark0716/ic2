/*     */ package ic2.core.block.heatgenerator.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityHeatSourceInventory;
/*     */ import ic2.core.block.invslot.InvSlotConsumableFuel;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntitySolidHeatGenerator
/*     */   extends TileEntityHeatSourceInventory
/*     */   implements IHasGui, IGuiValueProvider
/*     */ {
/*  32 */   public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel((IInventorySlotHolder)this, "fuel", 1, false);
/*  33 */   public final InvSlotOutput outputslot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*     */   
/*  35 */   public int ticksSinceLastActiveUpdate = IC2.random.nextInt(256);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  41 */     super.updateEntityServer();
/*     */     
/*  43 */     boolean needsInvUpdate = false;
/*     */     
/*  45 */     if (needsFuel()) {
/*  46 */       needsInvUpdate = gainFuel();
/*     */     }
/*     */     
/*  49 */     boolean newActive = gainheat();
/*     */     
/*  51 */     if (needsInvUpdate) {
/*  52 */       func_70296_d();
/*     */     }
/*     */     
/*  55 */     if (!delayActiveUpdate()) {
/*  56 */       setActive(newActive);
/*     */     } else {
/*  58 */       if (this.ticksSinceLastActiveUpdate % 256 == 0) {
/*  59 */         setActive((this.activityMeter > 0));
/*  60 */         this.activityMeter = 0;
/*     */       } 
/*     */       
/*  63 */       if (newActive) {
/*  64 */         this.activityMeter++;
/*     */       } else {
/*  66 */         this.activityMeter--;
/*     */       } 
/*     */       
/*  69 */       this.ticksSinceLastActiveUpdate++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gainheat() {
/*  75 */     if (isConverting()) {
/*  76 */       this.heatbuffer += getMaxHeatEmittedPerTick();
/*  77 */       this.fuel--;
/*  78 */       if (this.fuel == 0 && 
/*  79 */         (int)(Math.random() * 2.0D) == 1) {
/*  80 */         this.outputslot.add(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.ashes));
/*     */       }
/*     */       
/*  83 */       return true;
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/*  90 */     return (this.fuel <= 0 && getHeatBuffer() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  95 */     super.func_145839_a(nbt);
/*     */     
/*  97 */     this.fuel = nbt.func_74762_e("fuel");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 102 */     super.func_189515_b(nbt);
/*     */     
/* 104 */     nbt.func_74768_a("fuel", this.fuel);
/*     */     
/* 106 */     return nbt;
/*     */   }
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public boolean gainFuel() {
/* 114 */     if (this.outputslot.canAdd(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.ashes))) {
/* 115 */       int fuelValue = this.fuelSlot.consumeFuel() / 4;
/*     */       
/* 117 */       if (fuelValue == 0) return false;
/*     */       
/* 119 */       this.fuel += fuelValue;
/* 120 */       this.itemFuelTime = fuelValue;
/* 121 */       return true;
/*     */     } 
/*     */     
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/* 128 */     return (this.fuel > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fillHeatBuffer(int maxAmount) {
/* 134 */     if (this.heatbuffer - maxAmount >= 0) {
/* 135 */       this.heatbuffer -= maxAmount;
/* 136 */       return maxAmount;
/*     */     } 
/*     */     
/* 139 */     maxAmount = this.heatbuffer;
/* 140 */     this.heatbuffer = 0;
/*     */     
/* 142 */     return maxAmount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeatEmittedPerTick() {
/* 148 */     return emittedHU;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntitySolidHeatGenerator> getGuiContainer(EntityPlayer player) {
/* 153 */     return (ContainerBase<TileEntitySolidHeatGenerator>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 159 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 170 */     if ("fuel".equals(name)) {
/* 171 */       return (this.fuel == 0) ? 0.0D : (this.fuel / this.itemFuelTime);
/*     */     }
/* 173 */     throw new IllegalArgumentException("Unexpected value requested: " + name);
/*     */   }
/*     */ 
/*     */   
/* 177 */   private int heatbuffer = 0;
/* 178 */   public int activityMeter = 0;
/*     */   @GuiSynced
/* 180 */   public int fuel = 0;
/*     */   @GuiSynced
/* 182 */   public int itemFuelTime = 0;
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static final int emittedHU = Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/solid"));
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\tileentity\TileEntitySolidHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */