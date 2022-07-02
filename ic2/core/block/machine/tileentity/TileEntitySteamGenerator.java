/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.machine.container.ContainerSteamGenerator;
/*     */ import ic2.core.block.machine.gui.GuiSteamGenerator;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.util.BiomeUtil;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntitySteamGenerator
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IGuiValueProvider, INetworkClientTileEntityEventListener
/*     */ {
/*  38 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  39 */   public final FluidTank waterTank = (FluidTank)this.fluids.addTankInsert("waterTank", 10000, Fluids.fluidPredicate(new Fluid[] { FluidRegistry.WATER, FluidName.distilled_water.getInstance() }));
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  44 */     super.func_145839_a(nbttagcompound);
/*  45 */     this.inputMB = nbttagcompound.func_74762_e("inputmb");
/*  46 */     this.pressure = nbttagcompound.func_74762_e("pressurevalve");
/*  47 */     this.systemHeat = nbttagcompound.func_74760_g("systemheat");
/*  48 */     this.calcification = nbttagcompound.func_74762_e("calcification");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  53 */     super.func_189515_b(nbt);
/*  54 */     nbt.func_74768_a("inputmb", this.inputMB);
/*  55 */     nbt.func_74768_a("pressurevalve", this.pressure);
/*  56 */     nbt.func_74776_a("systemheat", this.systemHeat);
/*  57 */     nbt.func_74768_a("calcification", this.calcification);
/*     */     
/*  59 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  64 */     super.updateEntityServer();
/*     */     
/*  66 */     this.systemHeat = Math.max(this.systemHeat, BiomeUtil.getBiomeTemperature(func_145831_w(), this.field_174879_c));
/*     */     
/*  68 */     if (isCalcified()) {
/*  69 */       if (getActive()) setActive(false); 
/*     */     } else {
/*  71 */       this.newActive = work();
/*  72 */       if (getActive() != this.newActive) setActive(this.newActive);
/*     */     
/*     */     } 
/*  75 */     if (!getActive()) {
/*  76 */       cooldown(0.01F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean work() {
/*     */     Fluid output;
/*  83 */     this.heatInput = requestHeat(1200);
/*  84 */     if (this.heatInput <= 0) return false;
/*     */     
/*  86 */     assert this.heatInput <= 1200;
/*     */     
/*  88 */     this.outputMB = 0;
/*  89 */     this.outputFluid = outputType.NONE;
/*     */     
/*  91 */     if (this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() <= 0 || this.inputMB <= 0) {
/*  92 */       heatup(this.heatInput);
/*  93 */       return true;
/*     */     } 
/*     */     
/*  96 */     Fluid inputFluid = this.waterTank.getFluid().getFluid();
/*  97 */     boolean hasDistilledWater = (inputFluid == FluidName.distilled_water.getInstance());
/*     */     
/*  99 */     int maxAmount = Math.min(this.inputMB, this.waterTank.getFluidAmount());
/*     */     
/* 101 */     float hUneeded = 100.0F + this.pressure / 220.0F * 100.0F;
/* 102 */     float targetTemp = 100.0F + this.pressure / 220.0F * 100.0F * 2.74F;
/* 103 */     float reqHeat = targetTemp - this.systemHeat;
/* 104 */     float remainingHuInput = this.heatInput;
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (reqHeat > 1.0E-4F) {
/* 109 */       int heatReq = (int)Math.ceil((reqHeat / 5.0E-4F));
/*     */       
/* 111 */       if (this.heatInput <= heatReq) {
/* 112 */         heatup(this.heatInput);
/*     */         
/* 114 */         if (this.pressure == 0 && this.systemHeat < 99.9999F) {
/* 115 */           this.outputMB = maxAmount;
/* 116 */           this.outputFluid = hasDistilledWater ? outputType.DISTILLEDWATER : outputType.WATER;
/* 117 */           int i = LiquidUtil.distribute((TileEntity)this, new FluidStack(inputFluid, maxAmount), false);
/* 118 */           if (i > 0) this.waterTank.drainInternal(i, true);
/*     */         
/*     */         } 
/* 121 */         return true;
/*     */       } 
/* 123 */       heatup(heatReq);
/* 124 */       remainingHuInput -= heatReq;
/* 125 */       reqHeat = targetTemp - this.systemHeat;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     assert this.systemHeat >= targetTemp - 1.0E-4F;
/* 132 */     assert this.systemHeat >= 99.9999F;
/*     */ 
/*     */ 
/*     */     
/* 136 */     float availableSystemHu = Math.min(-reqHeat / 5.0E-4F, (1200 - this.heatInput));
/*     */     
/* 138 */     int activeAmount = Math.min(maxAmount, (int)((remainingHuInput + availableSystemHu) / hUneeded));
/* 139 */     int totalAmount = activeAmount;
/*     */ 
/*     */ 
/*     */     
/* 143 */     remainingHuInput -= activeAmount * hUneeded;
/*     */     
/* 145 */     if (remainingHuInput < 0.0F) {
/* 146 */       cooldown(-remainingHuInput * 5.0E-4F);
/* 147 */       reqHeat = targetTemp - this.systemHeat;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (reqHeat <= -0.1001F) {
/* 153 */       int coolingAmount = Math.min(maxAmount, (int)(-reqHeat / 0.1F));
/* 154 */       coolingAmount = Math.min(coolingAmount, (int)Math.ceil(20.0D));
/* 155 */       assert coolingAmount >= 0;
/*     */       
/* 157 */       cooldown(coolingAmount * 0.1F);
/* 158 */       totalAmount = Math.max(activeAmount, coolingAmount);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (remainingHuInput > 0.0F) heatup(remainingHuInput);
/*     */ 
/*     */ 
/*     */     
/* 168 */     if (totalAmount <= 0) return true;
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (!hasDistilledWater) {
/* 173 */       this.calcification += totalAmount;
/*     */     }
/*     */     
/* 176 */     this.waterTank.drainInternal(totalAmount, true);
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (activeAmount <= 0) return true;
/*     */     
/* 182 */     this.outputMB = activeAmount * 100;
/*     */ 
/*     */     
/* 185 */     if (this.systemHeat >= 373.9999F) {
/* 186 */       output = FluidName.superheated_steam.getInstance();
/* 187 */       this.outputFluid = outputType.SUPERHEATEDSTEAM;
/*     */     } else {
/* 189 */       output = FluidName.steam.getInstance();
/* 190 */       this.outputFluid = outputType.STEAM;
/*     */     } 
/*     */     
/* 193 */     int transferred = LiquidUtil.distribute((TileEntity)this, new FluidStack(output, this.outputMB), false);
/* 194 */     int remaining = this.outputMB - transferred;
/*     */     
/* 196 */     if (remaining > 0) {
/* 197 */       World world = func_145831_w();
/*     */       
/* 199 */       if (world.field_73012_v.nextInt(10) == 0) {
/* 200 */         (new ExplosionIC2(world, null, this.field_174879_c, 1, 1.0F, ExplosionIC2.Type.Heat)).doExplosion();
/* 201 */       } else if (remaining >= 100) {
/* 202 */         this.waterTank.fillInternal(new FluidStack(inputFluid, remaining / 100), true);
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   private void heatup(float heatinput) {
/* 210 */     assert heatinput >= -1.0E-4F;
/*     */     
/* 212 */     this.systemHeat += heatinput * 5.0E-4F;
/*     */     
/* 214 */     if (this.systemHeat > 500.0F) {
/* 215 */       World world = func_145831_w();
/* 216 */       world.func_175698_g(this.field_174879_c);
/* 217 */       (new ExplosionIC2(world, null, this.field_174879_c, 10, 0.01F, ExplosionIC2.Type.Heat)).doExplosion();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cooldown(float cool) {
/* 222 */     assert cool >= -1.0E-4F;
/*     */     
/* 224 */     this.systemHeat = Math.max(this.systemHeat - cool, BiomeUtil.getBiomeTemperature(func_145831_w(), this.field_174879_c));
/*     */   }
/*     */   
/*     */   private int requestHeat(int requestHeat) {
/* 228 */     World world = func_145831_w();
/* 229 */     int targetHeat = requestHeat;
/*     */     
/* 231 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 232 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */       
/* 234 */       if (target instanceof IHeatSource) {
/* 235 */         IHeatSource hs = (IHeatSource)target;
/*     */         
/* 237 */         int request = hs.drawHeat(dir.func_176734_d(), targetHeat, true);
/* 238 */         if (request > 0) {
/* 239 */           targetHeat -= hs.drawHeat(dir.func_176734_d(), request, false);
/*     */           
/* 241 */           if (targetHeat == 0) {
/* 242 */             return requestHeat;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 247 */     return requestHeat - targetHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 253 */     if (event > 2000 || event < -2000) {
/* 254 */       if (event > 2000) {
/* 255 */         this.pressure = Math.min(this.pressure + event - 2000, 300);
/*     */       }
/* 257 */       if (event < -2000) {
/* 258 */         this.pressure = Math.max(this.pressure + event + 2000, 0);
/*     */       }
/*     */     } else {
/* 261 */       this.inputMB = Math.max(Math.min(this.inputMB + event, 1000), 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeLiquidScaled(int i, int tank) {
/* 267 */     if (tank == 0) {
/* 268 */       if (this.waterTank.getFluidAmount() <= 0) {
/* 269 */         return 0;
/*     */       }
/* 271 */       return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
/*     */     } 
/*     */     
/* 274 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntitySteamGenerator> getGuiContainer(EntityPlayer player) {
/* 280 */     return (ContainerBase<TileEntitySteamGenerator>)new ContainerSteamGenerator(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 286 */     return (GuiScreen)new GuiSteamGenerator(new ContainerSteamGenerator(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 294 */     if ("heat".equals(name))
/* 295 */       return (this.systemHeat == 0.0F) ? 0.0D : (this.systemHeat / 500.0D); 
/* 296 */     if ("calcification".equals(name)) {
/* 297 */       return (this.calcification == 0) ? 0.0D : (this.calcification / 100000.0D);
/*     */     }
/* 299 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOutputMB() {
/* 304 */     return this.outputMB;
/*     */   }
/*     */   
/*     */   public int getInputMB() {
/* 308 */     return this.inputMB;
/*     */   }
/*     */   
/*     */   public int getHeatInput() {
/* 312 */     return this.heatInput;
/*     */   }
/*     */   
/*     */   public int getPressure() {
/* 316 */     return this.pressure;
/*     */   }
/*     */   
/*     */   public float getSystemHeat() {
/* 320 */     return Math.round(this.systemHeat * 10.0F) / 10.0F;
/*     */   }
/*     */   
/*     */   public float getCalcification() {
/* 324 */     return Math.round(this.calcification / 100000.0F * 100.0F * 100.0F) / 100.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCalcified() {
/* 332 */     return (this.calcification >= 100000);
/*     */   }
/*     */   
/*     */   public String getOutputFluidName() {
/* 336 */     return this.outputFluid.getName();
/*     */   }
/*     */   
/*     */   private enum outputType
/*     */   {
/* 341 */     NONE(""),
/* 342 */     WATER("ic2.SteamGenerator.output.water"),
/* 343 */     DISTILLEDWATER("ic2.SteamGenerator.output.destiwater"),
/* 344 */     STEAM("ic2.SteamGenerator.output.steam"),
/* 345 */     SUPERHEATEDSTEAM("ic2.SteamGenerator.output.hotsteam");
/*     */     private final String name;
/*     */     
/*     */     outputType(String name) {
/* 349 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 353 */       return this.name;
/*     */     }
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
/*     */   
/* 366 */   private int heatInput = 0;
/* 367 */   private int inputMB = 0;
/*     */   
/* 369 */   private int calcification = 0;
/* 370 */   private int outputMB = 0;
/* 371 */   private outputType outputFluid = outputType.NONE;
/*     */ 
/*     */   
/* 374 */   private int pressure = 0;
/*     */   private boolean newActive = false;
/*     */   private static final float maxHeat = 500.0F;
/*     */   private static final float heatPerHu = 5.0E-4F;
/*     */   private static final float coolingPerMb = 0.1F;
/*     */   private static final float maxCooling = 2.0F;
/*     */   private static final int maxHuInput = 1200;
/*     */   private static final int maxCalcification = 100000;
/*     */   private static final int steamExpansion = 100;
/*     */   private static final float epsilon = 1.0E-4F;
/*     */   private float systemHeat;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntitySteamGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */