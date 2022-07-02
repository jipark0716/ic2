/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlotConsumableFuel;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotProcessableSmelting;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
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
/*     */ public class TileEntityIronFurnace
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IGuiValueProvider, INetworkClientTileEntityEventListener
/*     */ {
/*  46 */   public final InvSlotProcessableSmelting inputSlot = new InvSlotProcessableSmelting((IInventorySlotHolder)this, "input", 1);
/*  47 */   public final InvSlotOutput outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*  48 */   public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel((IInventorySlotHolder)this, "fuel", 1, true);
/*     */   
/*     */   protected AudioSource audioSource;
/*     */   
/*     */   protected void onUnloaded() {
/*  53 */     if (IC2.platform.isRendering() && this.audioSource != null) {
/*  54 */       IC2.audioManager.removeSources(this);
/*  55 */       this.audioSource = null;
/*     */     } 
/*     */     
/*  58 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  63 */     super.func_145839_a(nbt);
/*     */     
/*  65 */     this.fuel = nbt.func_74762_e("fuel");
/*  66 */     this.totalFuel = nbt.func_74762_e("totalFuel");
/*  67 */     this.progress = nbt.func_74765_d("progress");
/*  68 */     this.xp = nbt.func_74769_h("xp");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  73 */     super.func_189515_b(nbt);
/*     */     
/*  75 */     nbt.func_74768_a("fuel", this.fuel);
/*  76 */     nbt.func_74768_a("totalFuel", this.totalFuel);
/*  77 */     nbt.func_74777_a("progress", this.progress);
/*  78 */     nbt.func_74780_a("xp", this.xp);
/*     */     
/*  80 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  89 */     super.updateEntityServer();
/*     */     
/*  91 */     boolean needsInvUpdate = false;
/*     */     
/*  93 */     if (this.fuel <= 0 && canOperate()) {
/*  94 */       this.fuel = this.totalFuel = this.fuelSlot.consumeFuel();
/*     */       
/*  96 */       if (this.fuel > 0) {
/*  97 */         needsInvUpdate = true;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     if (this.fuel > 0 && canOperate()) {
/* 102 */       this.progress = (short)(this.progress + 1);
/*     */       
/* 104 */       if (this.progress >= 160) {
/* 105 */         this.progress = 0;
/* 106 */         operate();
/* 107 */         needsInvUpdate = true;
/*     */       } 
/*     */     } else {
/* 110 */       this.progress = 0;
/*     */     } 
/*     */     
/* 113 */     if (this.fuel > 0) {
/* 114 */       this.fuel--;
/* 115 */       setActive(true);
/*     */     } else {
/* 117 */       setActive(false);
/*     */     } 
/*     */     
/* 120 */     if (needsInvUpdate) {
/* 121 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/* 128 */     super.updateEntityClient();
/*     */     
/* 130 */     if (getActive()) {
/* 131 */       World world = func_145831_w();
/* 132 */       showFlames(world, this.field_174879_c, getFacing());
/*     */       
/* 134 */       if (world.field_73012_v.nextDouble() < 0.1D) {
/* 135 */         world.func_184134_a(this.field_174879_c.func_177958_n() + 0.5D, this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p() + 0.5D, SoundEvents.field_187652_bv, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void showFlames(World world, BlockPos pos, EnumFacing facing) {
/* 141 */     if (world.field_73012_v.nextInt(8) != 0)
/*     */       return; 
/* 143 */     double width = 0.625D;
/* 144 */     double height = 0.375D;
/* 145 */     double depthOffset = 0.02D;
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
/* 157 */     double x = pos.func_177958_n() + (facing.func_82601_c() * 1.04D + 1.0D) / 2.0D;
/* 158 */     double y = pos.func_177956_o() + world.field_73012_v.nextFloat() * 0.375D;
/* 159 */     double z = pos.func_177952_p() + (facing.func_82599_e() * 1.04D + 1.0D) / 2.0D;
/*     */     
/* 161 */     if (facing.func_176740_k() == EnumFacing.Axis.X) {
/* 162 */       z += world.field_73012_v.nextFloat() * 0.625D - 0.3125D;
/*     */     } else {
/* 164 */       x += world.field_73012_v.nextFloat() * 0.625D - 0.3125D;
/*     */     } 
/*     */     
/* 167 */     world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
/* 168 */     world.func_175688_a(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double spawnXP(EntityPlayer player, double xp) {
/* 180 */     World world = player.func_130014_f_();
/* 181 */     long balls = (long)Math.floor(xp);
/*     */     
/* 183 */     while (balls > 0L) {
/*     */       int amount;
/* 185 */       if (balls < 2477L) {
/* 186 */         amount = EntityXPOrb.func_70527_a((int)balls);
/*     */       } else {
/* 188 */         amount = 2477;
/*     */       } 
/* 190 */       balls -= amount;
/* 191 */       world.func_72838_d((Entity)new EntityXPOrb(world, player.field_70165_t, player.field_70163_u + 0.5D, player.field_70161_v + 0.5D, amount));
/*     */     } 
/*     */     
/* 194 */     return xp - Math.floor(xp);
/*     */   }
/*     */   
/*     */   private void operate() {
/* 198 */     MachineRecipeResult<ItemStack, ItemStack, ItemStack> result = this.inputSlot.process();
/* 199 */     ItemStack output = (ItemStack)result.getOutput();
/*     */     
/* 201 */     this.outputSlot.add(output);
/* 202 */     this.inputSlot.consume(result);
/*     */     
/* 204 */     this.xp += result.getRecipe().getMetaData().func_74760_g("experience");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canOperate() {
/* 212 */     MachineRecipeResult<ItemStack, ItemStack, ItemStack> result = this.inputSlot.process();
/* 213 */     if (result == null) return false;
/*     */     
/* 215 */     return this.outputSlot.canAdd((ItemStack)result.getOutput());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProgress() {
/* 224 */     return this.progress / 160.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFuelRatio() {
/* 233 */     if (this.fuel <= 0) return 0.0D;
/*     */     
/* 235 */     return this.fuel / this.totalFuel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityIronFurnace> getGuiContainer(EntityPlayer player) {
/* 240 */     return (ContainerBase<TileEntityIronFurnace>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 246 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 256 */     if (name.equals("fuel"))
/* 257 */       return (this.fuel == 0) ? 0.0D : (this.fuel / this.totalFuel); 
/* 258 */     if (name.equals("progress")) {
/* 259 */       return (this.progress == 0) ? 0.0D : (this.progress / 160.0D);
/*     */     }
/* 261 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 267 */     if (event == 0) {
/* 268 */       assert !(func_145831_w()).field_72995_K;
/*     */       
/* 270 */       this.xp = spawnXP(player, this.xp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 276 */     if (field.equals("active")) {
/* 277 */       if (this.audioSource == null) this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/IronFurnaceOp.ogg", true, false, IC2.audioManager.getDefaultVolume());
/*     */       
/* 279 */       if (getActive())
/* 280 */       { if (this.audioSource != null) this.audioSource.play();
/*     */          }
/* 282 */       else if (this.audioSource != null) { this.audioSource.stop(); }
/*     */     
/*     */     } 
/*     */     
/* 286 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuiSynced
/* 297 */   public int fuel = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @GuiSynced
/* 302 */   public int totalFuel = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @GuiSynced
/* 307 */   public short progress = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   protected double xp = 0.0D;
/*     */   public static final short operationLength = 160;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityIronFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */