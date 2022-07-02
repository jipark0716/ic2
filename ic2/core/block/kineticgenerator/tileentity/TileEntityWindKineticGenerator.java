/*     */ package ic2.core.block.kineticgenerator.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IKineticSource;
/*     */ import ic2.api.item.IKineticRotor;
/*     */ import ic2.api.tile.IRotorProvider;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableClass;
/*     */ import ic2.core.block.invslot.InvSlotConsumableKineticRotor;
/*     */ import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityWindKineticGenerator
/*     */   extends TileEntityInventory
/*     */   implements IKineticSource, IRotorProvider, IHasGui
/*     */ {
/*  44 */   private int updateTicker = IC2.random.nextInt(getTickRate());
/*  45 */   public final InvSlotConsumableClass rotorSlot = (InvSlotConsumableClass)new InvSlotConsumableKineticRotor((IInventorySlotHolder)this, "rotorslot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, IKineticRotor.GearboxType.WIND, "rotorSlot");
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  50 */     super.updateEntityServer();
/*     */     
/*  52 */     if (this.updateTicker++ % getTickRate() != 0) {
/*     */       return;
/*     */     }
/*     */     
/*  56 */     boolean needsInvUpdate = false;
/*  57 */     boolean isActive = getActive();
/*     */     
/*  59 */     if (((hasRotor() && rotorHasSpace())) != isActive) {
/*  60 */       setActive(isActive = !isActive);
/*  61 */       needsInvUpdate = true;
/*     */     } 
/*     */     
/*  64 */     if (isActive) {
/*     */ 
/*     */ 
/*     */       
/*  68 */       this.crossSection = Util.square(getRotorDiameter() / 2 * 2 * 2 + 1);
/*  69 */       this.obstructedCrossSection = checkSpace(getRotorDiameter() * 3, false);
/*     */       
/*  71 */       if (this.obstructedCrossSection > 0 && this.obstructedCrossSection <= (getRotorDiameter() + 1) / 2) {
/*  72 */         this.obstructedCrossSection = 0;
/*     */       }
/*     */       
/*  75 */       if (this.obstructedCrossSection < 0) {
/*  76 */         this.windStrength = 0.0D;
/*  77 */         setRotationSpeed(0.0F);
/*     */       } else {
/*  79 */         this.windStrength = calcWindStrength();
/*     */         
/*  81 */         float speed = (float)Util.limit((this.windStrength - getMinWindStrength()) / getMaxWindStrength(), 0.0D, 2.0D);
/*  82 */         setRotationSpeed(speed);
/*     */         
/*  84 */         if (this.windStrength >= getMinWindStrength()) {
/*  85 */           if (this.windStrength <= getMaxWindStrength()) {
/*  86 */             this.rotorSlot.damage(1, false);
/*     */           } else {
/*  88 */             this.rotorSlot.damage(4, false);
/*     */           } 
/*     */           
/*  91 */           needsInvUpdate = true;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  96 */       setRotationSpeed(0.0F);
/*     */     } 
/*     */     
/*  99 */     if (needsInvUpdate) {
/* 100 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 106 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 108 */     ret.add("rotationSpeed");
/* 109 */     ret.add("rotorSlot");
/*     */     
/* 111 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityWindKineticGenerator> getGuiContainer(EntityPlayer player) {
/* 116 */     return (ContainerBase<TileEntityWindKineticGenerator>)new ContainerWindKineticGenerator(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 122 */     return (GuiScreen)new GuiWindKineticGenerator(new ContainerWindKineticGenerator(player, this));
/*     */   }
/*     */   
/*     */   public boolean facingMatchesDirection(EnumFacing direction) {
/* 126 */     return (direction == getFacing());
/*     */   }
/*     */   
/*     */   public String getRotorHealth() {
/* 130 */     if (!this.rotorSlot.isEmpty()) {
/* 131 */       return Localization.translate("ic2.WindKineticGenerator.gui.rotorhealth", new Object[] { Integer.valueOf((int)(100.0F - this.rotorSlot.get().func_77952_i() / this.rotorSlot.get().func_77958_k() * 100.0F)) });
/*     */     }
/* 133 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
/* 138 */     return getConnectionBandwidth(directionFrom);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectionBandwidth(EnumFacing side) {
/* 143 */     return facingMatchesDirection(side.func_176734_d()) ? getKuOutput() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
/* 148 */     return drawKineticEnergy(directionFrom, requestkineticenergy, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
/* 153 */     if (facingMatchesDirection(side.func_176734_d())) {
/* 154 */       return Math.min(request, getKuOutput());
/*     */     }
/* 156 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int checkSpace(int length, boolean onlyrotor) {
/* 166 */     int box = getRotorDiameter() / 2;
/* 167 */     int lentemp = 0;
/*     */     
/* 169 */     if (onlyrotor) {
/* 170 */       length = 1;
/* 171 */       lentemp = length + 1;
/*     */     } 
/*     */     
/* 174 */     if (!onlyrotor) {
/* 175 */       box *= 2;
/*     */     }
/*     */     
/* 178 */     EnumFacing fwdDir = getFacing();
/* 179 */     EnumFacing rightDir = fwdDir.func_176732_a(EnumFacing.DOWN.func_176740_k());
/*     */     
/* 181 */     int xMaxDist = Math.abs(length * fwdDir.func_82601_c() + box * rightDir.func_82601_c());
/* 182 */     int zMaxDist = Math.abs(length * fwdDir.func_82599_e() + box * rightDir.func_82599_e());
/*     */     
/* 184 */     ChunkCache chunkCache = new ChunkCache(func_145831_w(), this.field_174879_c.func_177982_a(-xMaxDist, -box, -zMaxDist), this.field_174879_c.func_177982_a(xMaxDist, box, zMaxDist), 0);
/*     */     
/* 186 */     int ret = 0;
/* 187 */     int xCoord = this.field_174879_c.func_177958_n();
/* 188 */     int yCoord = this.field_174879_c.func_177956_o();
/* 189 */     int zCoord = this.field_174879_c.func_177952_p();
/*     */     
/* 191 */     BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/* 192 */     for (int up = -box; up <= box; up++) {
/* 193 */       int y = yCoord + up;
/*     */       
/* 195 */       for (int right = -box; right <= box; right++) {
/* 196 */         boolean occupied = false;
/*     */         
/* 198 */         for (int fwd = lentemp - length; fwd <= length; fwd++) {
/* 199 */           int x = xCoord + fwd * fwdDir.func_82601_c() + right * rightDir.func_82601_c();
/* 200 */           int z = zCoord + fwd * fwdDir.func_82599_e() + right * rightDir.func_82599_e();
/* 201 */           pos.func_181079_c(x, y, z);
/*     */           
/* 203 */           assert Math.abs(x - xCoord) <= xMaxDist;
/* 204 */           assert Math.abs(z - zCoord) <= zMaxDist;
/*     */           
/* 206 */           IBlockState state = chunkCache.func_180495_p((BlockPos)pos);
/* 207 */           Block block = state.func_177230_c();
/*     */           
/* 209 */           if (!block.isAir(state, (IBlockAccess)chunkCache, (BlockPos)pos)) {
/* 210 */             occupied = true;
/*     */             
/* 212 */             if ((up != 0 || right != 0 || fwd != 0) && chunkCache.func_175625_s((BlockPos)pos) instanceof TileEntityWindKineticGenerator && !onlyrotor) {
/* 213 */               return -1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 218 */         if (occupied) {
/* 219 */           ret++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean hasRotor() {
/* 228 */     return !this.rotorSlot.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean rotorHasSpace() {
/* 232 */     return (checkSpace(1, true) == 0);
/*     */   }
/*     */   
/*     */   private void setRotationSpeed(float speed) {
/* 236 */     if (this.rotationSpeed != speed) {
/* 237 */       this.rotationSpeed = speed;
/* 238 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "rotationSpeed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTickRate() {
/* 243 */     return 32;
/*     */   }
/*     */   
/*     */   public double calcWindStrength() {
/* 247 */     double windStr = (WorldData.get(func_145831_w())).windSim.getWindAt(this.field_174879_c.func_177956_o());
/*     */     
/* 249 */     windStr *= 1.0D - Math.pow(this.obstructedCrossSection / this.crossSection, 2.0D);
/*     */     
/* 251 */     return Math.max(0.0D, windStr);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAngle() {
/* 256 */     if (this.rotationSpeed != 0.0F) {
/*     */       
/* 258 */       this.angle += (float)(System.currentTimeMillis() - this.lastcheck) * this.rotationSpeed;
/* 259 */       this.angle %= 360.0F;
/*     */     } 
/* 261 */     this.lastcheck = System.currentTimeMillis();
/* 262 */     return this.angle;
/*     */   }
/*     */   
/*     */   public float getEfficiency() {
/* 266 */     ItemStack stack = this.rotorSlot.get();
/*     */     
/* 268 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
/* 269 */       return ((IKineticRotor)stack.func_77973_b()).getEfficiency(stack);
/*     */     }
/*     */     
/* 272 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public int getMinWindStrength() {
/* 276 */     ItemStack stack = this.rotorSlot.get();
/*     */     
/* 278 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
/* 279 */       return ((IKineticRotor)stack.func_77973_b()).getMinWindStrength(stack);
/*     */     }
/*     */     
/* 282 */     return 0;
/*     */   }
/*     */   
/*     */   public int getMaxWindStrength() {
/* 286 */     ItemStack stack = this.rotorSlot.get();
/*     */     
/* 288 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
/* 289 */       return ((IKineticRotor)stack.func_77973_b()).getMaxWindStrength(stack);
/*     */     }
/*     */     
/* 292 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRotorDiameter() {
/* 297 */     ItemStack stack = this.rotorSlot.get();
/*     */     
/* 299 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
/* 300 */       return ((IKineticRotor)stack.func_77973_b()).getDiameter(stack);
/*     */     }
/*     */     
/* 303 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getRotorRenderTexture() {
/* 308 */     ItemStack stack = this.rotorSlot.get();
/*     */     
/* 310 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
/* 311 */       return ((IKineticRotor)stack.func_77973_b()).getRotorRenderTexture(stack);
/*     */     }
/*     */     
/* 314 */     return woodenRotorTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRotorOverloaded() {
/* 322 */     return (hasRotor() && rotorHasSpace() && isWindStrongEnough() && this.windStrength > getMaxWindStrength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWindStrongEnough() {
/* 330 */     return (this.windStrength >= getMinWindStrength());
/*     */   }
/*     */   
/*     */   public int getKuOutput() {
/* 334 */     if (this.windStrength >= getMinWindStrength() && getActive()) {
/* 335 */       return (int)(this.windStrength * outputModifier * getEfficiency());
/*     */     }
/*     */     
/* 338 */     return 0;
/*     */   }
/*     */   
/*     */   public int getWindStrength() {
/* 342 */     return (int)this.windStrength;
/*     */   }
/*     */   
/*     */   public int getObstructions() {
/* 346 */     return this.obstructedCrossSection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 351 */     if (active != getActive())
/*     */     {
/*     */ 
/*     */       
/* 355 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "rotorSlot");
/*     */     }
/* 357 */     super.setActive(active);
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
/*     */ 
/*     */   
/* 371 */   private float angle = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   public static final float outputModifier = 10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/wind");
/* 380 */   private static final ResourceLocation woodenRotorTexture = new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png");
/*     */   private double windStrength;
/*     */   private int obstructedCrossSection;
/*     */   private int crossSection;
/*     */   private float rotationSpeed;
/*     */   private long lastcheck;
/*     */   private static final double efficiencyRollOffExponent = 2.0D;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\tileentity\TileEntityWindKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */