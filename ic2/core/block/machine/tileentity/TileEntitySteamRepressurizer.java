/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntitySteamRepressurizer
/*     */   extends TileEntityInventory
/*     */   implements IHasGui
/*     */ {
/*  42 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this)); @GuiSynced
/*  43 */   protected final FluidTank input = (FluidTank)this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate(new Fluid[] { FluidName.steam.getInstance(), FluidName.superheated_steam.getInstance() })); @GuiSynced
/*  44 */   protected final FluidTank output = (FluidTank)this.fluids.addTankExtract("output", 10000);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  50 */     super.func_145839_a(nbt);
/*     */     
/*  52 */     this.currentHeat = nbt.func_74762_e("heat");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  57 */     super.func_189515_b(nbt);
/*     */     
/*  59 */     nbt.func_74768_a("heat", this.currentHeat);
/*     */     
/*  61 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasSteam() {
/*  66 */     return (STEAM != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  71 */     super.updateEntityServer();
/*  72 */     if (!hasSteam())
/*     */       return; 
/*  74 */     if (this.input.getFluidAmount() >= 10) {
/*  75 */       if (this.currentHeat < this.input.getFluidAmount() / 10) {
/*  76 */         getHeat();
/*     */       }
/*     */       
/*  79 */       int amount = getOutput();
/*  80 */       while (this.currentHeat > 0 && this.input.getFluidAmount() >= 10 && canOutput(amount)) {
/*  81 */         this.currentHeat--;
/*     */         
/*  83 */         this.input.drainInternal(10, true);
/*  84 */         this.output.fillInternal(new FluidStack(STEAM, amount), true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getHeat() {
/*  90 */     int aim = this.input.getFluidAmount() / 10;
/*     */     
/*  92 */     if (aim > 0) {
/*  93 */       World world = func_145831_w();
/*  94 */       int targetHeat = aim;
/*     */       
/*  96 */       for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  97 */         TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */         
/*  99 */         if (target instanceof IHeatSource) {
/* 100 */           IHeatSource hs = (IHeatSource)target;
/*     */           
/* 102 */           int request = hs.drawHeat(dir.func_176734_d(), targetHeat, true);
/* 103 */           if (request > 0) {
/* 104 */             targetHeat -= hs.drawHeat(dir.func_176734_d(), request, false);
/*     */             
/* 106 */             if (targetHeat <= 0)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 111 */       this.currentHeat += aim - targetHeat;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getOutput() {
/* 116 */     assert this.input.getFluid() != null;
/* 117 */     Fluid fluid = this.input.getFluid().getFluid();
/*     */     
/* 119 */     if (fluid == FluidName.steam.getInstance())
/* 120 */       return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSteam"); 
/* 121 */     if (fluid == FluidName.superheated_steam.getInstance()) {
/* 122 */       return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSuperSteam");
/*     */     }
/* 124 */     throw new IllegalStateException("Unknown tank contents: " + fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canOutput(int amount) {
/* 129 */     return (this.output.fillInternal(new FluidStack(STEAM, amount), false) == amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntitySteamRepressurizer> getGuiContainer(EntityPlayer player) {
/* 134 */     return (ContainerBase<TileEntitySteamRepressurizer>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 140 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public boolean getGuiState(String name) {
/* 149 */     if ("valid".equals(name)) {
/* 150 */       return hasSteam();
/*     */     }
/*     */     
/* 153 */     return super.getGuiState(name);
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
/* 165 */   public static final Fluid STEAM = FluidRegistry.getFluid("steam");
/*     */   protected int currentHeat;
/*     */   protected static final int CONSUMPTION = 10;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntitySteamRepressurizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */