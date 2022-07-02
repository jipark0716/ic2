/*     */ package ic2.core.block.steam;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlotConsumableFuel;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.ref.ItemName;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityBurningBox
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IGuiValueProvider
/*     */ {
/*  34 */   public int ticksSinceLastActiveUpdate = IC2.random.nextInt(128);
/*     */   
/*  36 */   public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel((IInventorySlotHolder)this, "fuel", 1, false);
/*  37 */   public final InvSlotOutput ashesSlot = new InvSlotOutput((IInventorySlotHolder)this, "ashes", 1);
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  42 */     super.func_145839_a(nbt);
/*     */     
/*  44 */     this.delta = nbt.func_74762_e("delta");
/*  45 */     this.fuel = nbt.func_74762_e("fuel");
/*  46 */     this.remainingFuel = nbt.func_74762_e("remainingFuel");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  51 */     super.func_189515_b(nbt);
/*     */     
/*  53 */     nbt.func_74768_a("delta", this.delta);
/*  54 */     nbt.func_74768_a("fuel", this.fuel);
/*  55 */     nbt.func_74768_a("remainingFuel", this.remainingFuel);
/*     */     
/*  57 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  62 */     super.updateEntityServer();
/*     */     
/*  64 */     boolean needsInventoryUpdate = false;
/*     */     
/*  66 */     if (needsFuel()) {
/*  67 */       needsInventoryUpdate = gainFuel();
/*     */     }
/*     */     
/*  70 */     boolean newActive = work();
/*     */     
/*  72 */     if (needsInventoryUpdate) {
/*  73 */       func_70296_d();
/*     */     }
/*     */     
/*  76 */     if (!delayActiveUpdate()) {
/*  77 */       setActive(newActive);
/*     */     } else {
/*  79 */       if (this.ticksSinceLastActiveUpdate % 128 == 0) {
/*  80 */         setActive((this.activityMeter > 0));
/*  81 */         this.activityMeter = 0;
/*     */       } 
/*     */       
/*  84 */       if (newActive) {
/*  85 */         this.activityMeter++;
/*     */       } else {
/*  87 */         this.activityMeter--;
/*     */       } 
/*     */       
/*  90 */       this.ticksSinceLastActiveUpdate++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getProvidedHeat(EnumFacing side) {
/*  95 */     return (side == EnumFacing.UP) ? this.heat : 0;
/*     */   }
/*     */   
/*     */   public boolean needsFuel() {
/*  99 */     return (this.fuel <= 0);
/*     */   }
/*     */   
/*     */   public boolean gainFuel() {
/* 103 */     if (this.ashesSlot.canAdd(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.ashes))) {
/* 104 */       int fuelValue = this.fuelSlot.consumeFuel() / 4;
/*     */       
/* 106 */       if (fuelValue == 0) {
/* 107 */         return false;
/*     */       }
/*     */       
/* 110 */       this.fuel += fuelValue;
/* 111 */       this.remainingFuel = fuelValue;
/* 112 */       return true;
/*     */     } 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   public boolean work() {
/* 119 */     if (this.fuel > 0) {
/* 120 */       this.fuel--;
/*     */       
/* 122 */       if (this.fuel == 0 && 
/* 123 */         (int)(Math.random() * 2.0D) == 1) {
/* 124 */         this.ashesSlot.add(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.ashes));
/*     */       }
/*     */ 
/*     */       
/* 128 */       this.delta = Math.min(++this.delta, 1100);
/* 129 */       int i = 55 - this.delta / 20;
/* 130 */       this.heat = 1400 + (int)(0.008D * -(i * i * i));
/*     */       
/* 132 */       return true;
/*     */     } 
/* 134 */     this.delta = Math.max(--this.delta, 0);
/* 135 */     int temp = this.delta / 20;
/* 136 */     this.heat = (int)(0.008D * (temp * temp * temp));
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityBurningBox> getGuiContainer(EntityPlayer player) {
/* 149 */     return (ContainerBase<TileEntityBurningBox>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 155 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 167 */     if ("fuel".equals(name)) {
/* 168 */       return (this.fuel == 0) ? 0.0D : (this.fuel / this.remainingFuel);
/*     */     }
/* 170 */     throw new IllegalArgumentException("Unexpected value requested: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
/* 178 */     tooltip.add("");
/* 179 */     tooltip.add("Maximum temperature:");
/* 180 */     tooltip.add(" 1400K");
/* 181 */     tooltip.add("");
/* 182 */     tooltip.add("Time to reach maximum temperature:");
/* 183 */     tooltip.add(" 55 seconds");
/* 184 */     tooltip.add("");
/*     */   }
/*     */ 
/*     */   
/* 188 */   protected int heat = 0;
/* 189 */   protected int delta = 0;
/* 190 */   public int activityMeter = 0;
/*     */   @GuiSynced
/* 192 */   public int fuel = 0;
/*     */   @GuiSynced
/* 194 */   private int remainingFuel = 0;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\steam\TileEntityBurningBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */