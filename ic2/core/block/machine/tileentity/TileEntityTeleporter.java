/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.ComparatorEmitter;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityTeleporter
/*     */   extends TileEntityBlock
/*     */   implements INetworkTileEntityEventListener
/*     */ {
/*  55 */   protected final ComparatorEmitter comparator = (ComparatorEmitter)addComponent((TileEntityComponent)new ComparatorEmitter(this));
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  60 */     super.func_145839_a(nbt);
/*     */     
/*  62 */     if (nbt.func_74764_b("targetX")) {
/*  63 */       this.target = new BlockPos(nbt.func_74762_e("targetX"), nbt.func_74762_e("targetY"), nbt.func_74762_e("targetZ"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  69 */     super.func_189515_b(nbt);
/*     */     
/*  71 */     if (this.target != null) {
/*  72 */       nbt.func_74768_a("targetX", this.target.func_177958_n());
/*  73 */       nbt.func_74768_a("targetY", this.target.func_177956_o());
/*  74 */       nbt.func_74768_a("targetZ", this.target.func_177952_p());
/*     */     } 
/*     */     
/*  77 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/*  82 */     if (IC2.platform.isRendering() && this.audioSource != null) {
/*  83 */       IC2.audioManager.removeSources(this);
/*  84 */       this.audioSource = null;
/*     */     } 
/*     */     
/*  87 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  92 */     super.onLoaded();
/*  93 */     updateComparatorLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 101 */     super.updateEntityServer();
/*     */     
/* 103 */     boolean coolingDown = (this.cooldown > 0);
/* 104 */     if (coolingDown) {
/* 105 */       this.cooldown--;
/*     */       
/* 107 */       ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "cooldown");
/*     */     } 
/*     */     
/* 110 */     World world = func_145831_w();
/* 111 */     if (world.func_175640_z(this.field_174879_c) && this.target != null) {
/* 112 */       List<Entity> entitiesNearby; setActive(true);
/*     */ 
/*     */       
/* 115 */       if (coolingDown) {
/* 116 */         entitiesNearby = Collections.emptyList();
/*     */       } else {
/* 118 */         entitiesNearby = world.func_72872_a(Entity.class, new AxisAlignedBB((this.field_174879_c
/* 119 */               .func_177958_n() - 1), this.field_174879_c.func_177956_o(), (this.field_174879_c.func_177952_p() - 1), (this.field_174879_c.func_177958_n() + 2), (this.field_174879_c.func_177956_o() + 3), (this.field_174879_c.func_177952_p() + 2)));
/*     */       } 
/*     */ 
/*     */       
/* 123 */       if (!entitiesNearby.isEmpty() && verifyTarget()) {
/* 124 */         double minDistanceSquared = Double.MAX_VALUE;
/* 125 */         Entity closestEntity = null;
/*     */         
/* 127 */         for (Entity entity : entitiesNearby) {
/* 128 */           if (entity.func_184187_bx() != null) {
/*     */             continue;
/*     */           }
/* 131 */           double distSquared = this.field_174879_c.func_177957_d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */           
/* 133 */           if (distSquared < minDistanceSquared) {
/* 134 */             minDistanceSquared = distSquared;
/* 135 */             closestEntity = entity;
/*     */           } 
/*     */         } 
/*     */         
/* 139 */         assert closestEntity != null;
/*     */         
/* 141 */         teleport(closestEntity, Math.sqrt(this.field_174879_c.func_177951_i((Vec3i)this.target)));
/* 142 */       } else if (++this.targetCheckTicker % 1024 == 0) {
/* 143 */         verifyTarget();
/*     */       } 
/*     */     } else {
/* 146 */       setActive(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean verifyTarget() {
/* 151 */     if (func_145831_w().func_175625_s(this.target) instanceof TileEntityTeleporter) {
/* 152 */       return true;
/*     */     }
/* 154 */     this.target = null;
/* 155 */     updateComparatorLevel();
/* 156 */     setActive(false);
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/* 165 */     super.updateEntityClient();
/*     */     
/* 167 */     if (getActive()) {
/* 168 */       if (this.cooldown > 0) {
/* 169 */         spawnGreenParticles(2, this.field_174879_c);
/*     */       } else {
/* 171 */         spawnBlueParticles(2, this.field_174879_c);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateComparatorLevel() {
/* 177 */     int targetLevel = (this.target != null) ? 15 : 0;
/* 178 */     this.comparator.setLevel(targetLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void teleport(Entity user, double distance) {
/* 189 */     int weight = getWeightOf(user);
/* 190 */     if (weight == 0)
/*     */       return; 
/* 192 */     int energyCost = (int)(weight * Math.pow(distance + 10.0D, 0.7D) * 5.0D);
/*     */     
/* 194 */     if (energyCost > getAvailableEnergy()) {
/*     */       return;
/*     */     }
/* 197 */     consumeEnergy(energyCost);
/*     */     
/* 199 */     if (user instanceof EntityPlayerMP) {
/* 200 */       ((EntityPlayerMP)user).func_70634_a(this.target.func_177958_n() + 0.5D, this.target.func_177956_o() + 1.5D + user.func_70033_W(), this.target.func_177952_p() + 0.5D);
/*     */     } else {
/* 202 */       user.func_70080_a(this.target.func_177958_n() + 0.5D, this.target.func_177956_o() + 1.5D + user.func_70033_W(), this.target.func_177952_p() + 0.5D, user.field_70177_z, user.field_70125_A);
/*     */     } 
/*     */     
/* 205 */     TileEntity te = func_145831_w().func_175625_s(this.target);
/* 206 */     assert te instanceof TileEntityTeleporter;
/* 207 */     ((TileEntityTeleporter)te).onTeleportTo(this, user);
/*     */     
/* 209 */     ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true);
/*     */     
/* 211 */     if (user instanceof EntityPlayer && distance >= 1000.0D) {
/* 212 */       IC2.achievements.issueAchievement((EntityPlayer)user, "teleportFarAway");
/*     */     }
/*     */   }
/*     */   
/*     */   public void spawnBlueParticles(int n, BlockPos pos) {
/* 217 */     spawnParticles(n, pos, -1, 0, 1);
/*     */   }
/*     */   
/*     */   public void spawnGreenParticles(int n, BlockPos pos) {
/* 221 */     spawnParticles(n, pos, -1, 1, 0);
/*     */   }
/*     */   
/*     */   private void spawnParticles(int n, BlockPos pos, int red, int green, int blue) {
/* 225 */     World world = func_145831_w();
/* 226 */     Random rnd = world.field_73012_v;
/*     */     
/* 228 */     for (int i = 0; i < n; i++) {
/* 229 */       world.func_175688_a(EnumParticleTypes.REDSTONE, (pos
/* 230 */           .func_177958_n() + rnd.nextFloat()), ((pos
/* 231 */           .func_177956_o() + 1) + rnd.nextFloat()), (pos
/* 232 */           .func_177952_p() + rnd.nextFloat()), red, green, blue, new int[0]);
/* 233 */       world.func_175688_a(EnumParticleTypes.REDSTONE, (pos
/* 234 */           .func_177958_n() + rnd.nextFloat()), ((pos
/* 235 */           .func_177956_o() + 2) + rnd.nextFloat()), (pos
/* 236 */           .func_177952_p() + rnd.nextFloat()), red, green, blue, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void consumeEnergy(int energy) {
/* 244 */     World world = func_145831_w();
/* 245 */     List<IEnergyStorage> energySources = new LinkedList<>();
/*     */     
/* 247 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 248 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */       
/* 250 */       if (target instanceof IEnergyStorage) {
/* 251 */         IEnergyStorage energySource = (IEnergyStorage)target;
/*     */         
/* 253 */         if (energySource.isTeleporterCompatible(dir.func_176734_d()) && energySource.getStored() > 0) {
/* 254 */           energySources.add(energySource);
/*     */         }
/*     */       } 
/*     */     } 
/* 258 */     while (energy > 0) {
/* 259 */       int drain = (energy + energySources.size() - 1) / energySources.size();
/*     */       
/* 261 */       for (Iterator<IEnergyStorage> it = energySources.iterator(); it.hasNext(); ) {
/* 262 */         IEnergyStorage energySource = it.next();
/*     */         
/* 264 */         if (drain > energy) {
/* 265 */           drain = energy;
/*     */         }
/* 267 */         if (energySource.getStored() <= drain) {
/* 268 */           energy -= energySource.getStored();
/* 269 */           energySource.setStored(0);
/*     */           
/* 271 */           it.remove(); continue;
/*     */         } 
/* 273 */         energy -= drain;
/* 274 */         energySource.addEnergy(-drain);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAvailableEnergy() {
/* 284 */     World world = func_145831_w();
/* 285 */     int energy = 0;
/*     */     
/* 287 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 288 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */       
/* 290 */       if (target instanceof IEnergyStorage) {
/* 291 */         IEnergyStorage storage = (IEnergyStorage)target;
/* 292 */         if (storage.isTeleporterCompatible(dir.func_176734_d()))
/*     */         {
/*     */           
/* 295 */           energy += storage.getStored();
/*     */         }
/*     */       } 
/*     */     } 
/* 299 */     return energy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeightOf(Entity user) {
/* 306 */     boolean teleporterUseInventoryWeight = ConfigUtil.getBool(MainConfig.get(), "balance/teleporterUseInventoryWeight");
/* 307 */     int weight = 0;
/*     */     
/* 309 */     if (user instanceof EntityItem) {
/* 310 */       ItemStack is = ((EntityItem)user).func_92059_d();
/* 311 */       weight += 100 * StackUtil.getSize(is) / is.func_77976_d();
/* 312 */     } else if (user instanceof net.minecraft.entity.passive.EntityAnimal || user instanceof net.minecraft.entity.item.EntityMinecart || user instanceof net.minecraft.entity.item.EntityBoat) {
/* 313 */       weight += 100;
/* 314 */     } else if (user instanceof EntityPlayer) {
/* 315 */       weight += 1000;
/*     */       
/* 317 */       if (teleporterUseInventoryWeight) {
/* 318 */         for (ItemStack stack : ((EntityPlayer)user).field_71071_by.field_70462_a) {
/* 319 */           weight += getStackCost(stack);
/*     */         }
/*     */       }
/* 322 */     } else if (user instanceof net.minecraft.entity.monster.EntityGhast) {
/* 323 */       weight += 2500;
/* 324 */     } else if (user instanceof net.minecraft.entity.boss.EntityWither) {
/* 325 */       weight += 5000;
/* 326 */     } else if (user instanceof net.minecraft.entity.boss.EntityDragon) {
/* 327 */       weight += 10000;
/* 328 */     } else if (user instanceof net.minecraft.entity.EntityCreature) {
/* 329 */       weight += 500;
/*     */     } 
/*     */     
/* 332 */     if (teleporterUseInventoryWeight && user instanceof EntityLivingBase) {
/* 333 */       EntityLivingBase living = (EntityLivingBase)user;
/*     */       
/* 335 */       for (ItemStack stack : living.func_184209_aF()) {
/* 336 */         weight += getStackCost(stack);
/*     */       }
/*     */       
/* 339 */       if (user instanceof EntityPlayer) {
/*     */         
/* 341 */         ItemStack stack = living.func_184614_ca();
/* 342 */         weight -= getStackCost(stack);
/*     */       } 
/*     */     } 
/* 345 */     for (Entity passenger : user.func_184188_bt())
/*     */     {
/* 347 */       weight += getWeightOf(passenger);
/*     */     }
/* 349 */     return weight;
/*     */   }
/*     */   
/*     */   private static int getStackCost(ItemStack stack) {
/* 353 */     if (StackUtil.isEmpty(stack)) return 0;
/*     */     
/* 355 */     return 100 * StackUtil.getSize(stack) / stack.func_77976_d();
/*     */   }
/*     */   
/*     */   private void onTeleportTo(TileEntityTeleporter from, Entity entity) {
/* 359 */     this.cooldown = 20;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canEntityDestroy(Entity entity) {
/* 364 */     return (!(entity instanceof net.minecraft.entity.boss.EntityDragon) && !(entity instanceof net.minecraft.entity.boss.EntityWither));
/*     */   }
/*     */   
/*     */   public boolean hasTarget() {
/* 368 */     return (this.target != null);
/*     */   }
/*     */   
/*     */   public BlockPos getTarget() {
/* 372 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(BlockPos pos) {
/* 376 */     this.target = pos;
/* 377 */     updateComparatorLevel();
/*     */     
/* 379 */     ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "target");
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 384 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 386 */     ret.add("target");
/*     */     
/* 388 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 393 */     if (field.equals("active")) {
/* 394 */       if (this.audioSource == null) {
/* 395 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/Teleporter/TeleChargedLoop.ogg", true, false, IC2.audioManager
/*     */ 
/*     */             
/* 398 */             .getDefaultVolume());
/*     */       }
/* 400 */       if (getActive()) {
/* 401 */         if (this.audioSource != null) {
/* 402 */           this.audioSource.play();
/*     */         }
/* 404 */       } else if (this.audioSource != null) {
/* 405 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/*     */     
/* 409 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 414 */     switch (event) {
/*     */       case 0:
/* 416 */         IC2.audioManager.playOnce(this, "Machines/Teleporter/TeleUse.ogg");
/* 417 */         IC2.audioManager.playOnce(new AudioPosition(func_145831_w(), this.field_174879_c), "Machines/Teleporter/TeleUse.ogg");
/*     */         
/* 419 */         spawnBlueParticles(20, this.field_174879_c);
/* 420 */         spawnBlueParticles(20, this.target);
/*     */         return;
/*     */     } 
/*     */     
/* 424 */     IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_174879_c + ")", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 433 */   private AudioSource audioSource = null;
/* 434 */   private int targetCheckTicker = IC2.random.nextInt(1024);
/* 435 */   private int cooldown = 0;
/*     */   private BlockPos target;
/*     */   private static final int EventTeleport = 0;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityTeleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */