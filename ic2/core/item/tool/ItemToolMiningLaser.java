/*     */ package ic2.core.item.tool;
/*     */ import ic2.api.event.LaserEvent;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.util.Vector3;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemToolMiningLaser extends ItemElectricTool implements INetworkItemEventListener {
/*     */   private static final int EventShotMining = 0;
/*     */   private static final int EventShotLowFocus = 1;
/*     */   private static final int EventShotLongRange = 2;
/*     */   private static final int EventShotHorizontal = 3;
/*     */   private static final int EventShotSuperHeat = 4;
/*     */   private static final int EventShotScatter = 5;
/*     */   private static final int EventShotExplosive = 6;
/*     */   private static final int EventShot3x3 = 7;
/*     */   
/*     */   public ItemToolMiningLaser() {
/*  43 */     super(ItemName.mining_laser, 100);
/*     */     
/*  45 */     this.maxCharge = 300000;
/*  46 */     this.transferLimit = 512;
/*  47 */     this.tier = 3;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
/*     */     String mode;
/*  53 */     super.func_77624_a(stack, world, list, par4);
/*     */     
/*  55 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */ 
/*     */     
/*  58 */     switch (nbtData.func_74762_e("laserSetting")) { case 0:
/*  59 */         mode = Localization.translate("ic2.tooltip.mode.mining"); break;
/*  60 */       case 1: mode = Localization.translate("ic2.tooltip.mode.lowFocus"); break;
/*  61 */       case 2: mode = Localization.translate("ic2.tooltip.mode.longRange"); break;
/*  62 */       case 3: mode = Localization.translate("ic2.tooltip.mode.horizontal"); break;
/*  63 */       case 4: mode = Localization.translate("ic2.tooltip.mode.superHeat"); break;
/*  64 */       case 5: mode = Localization.translate("ic2.tooltip.mode.scatter"); break;
/*  65 */       case 6: mode = Localization.translate("ic2.tooltip.mode.explosive"); break;
/*  66 */       case 7: mode = Localization.translate("ic2.tooltip.mode.3x3"); break;
/*     */       default:
/*     */         return; }
/*     */     
/*  70 */     list.add(Localization.translate("ic2.tooltip.mode", new Object[] { mode }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/*  76 */     List<String> info = new LinkedList<>();
/*     */     
/*  78 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*  79 */     String mode = Localization.translate(getModeString(nbtData.func_74762_e("laserSetting")));
/*     */     
/*  81 */     info.addAll(super.getHudInfo(stack, advanced));
/*  82 */     info.add(Localization.translate("ic2.tooltip.mode", new Object[] { mode }));
/*     */     
/*  84 */     return info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  93 */     ItemStack stack = StackUtil.get(player, hand);
/*  94 */     if (!IC2.platform.isSimulating()) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/*  96 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */     
/*  98 */     int laserSetting = nbtData.func_74762_e("laserSetting");
/*     */     
/* 100 */     if (IC2.keyboard.isModeSwitchKeyDown(player)) {
/* 101 */       laserSetting = (laserSetting + 1) % 8;
/* 102 */       nbtData.func_74768_a("laserSetting", laserSetting);
/*     */       
/* 104 */       IC2.platform.messagePlayer(player, "ic2.tooltip.mode", new Object[] { getModeString(laserSetting) });
/*     */     } else {
/*     */       Vector3 look, right, up; int sideShots; double unitDistance; int r;
/* 107 */       (new int[8])[0] = 1250; (new int[8])[1] = 100; (new int[8])[2] = 5000; (new int[8])[3] = 0; (new int[8])[4] = 2500; (new int[8])[5] = 10000; (new int[8])[6] = 5000; (new int[8])[7] = 7500; int consume = (new int[8])[laserSetting];
/*     */       
/* 109 */       if (!ElectricItem.manager.use(stack, consume, (EntityLivingBase)player)) {
/* 110 */         return new ActionResult(EnumActionResult.FAIL, stack);
/*     */       }
/*     */       
/* 113 */       switch (laserSetting) {
/*     */         case 0:
/* 115 */           if (shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false)) {
/* 116 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 0, true);
/*     */           }
/*     */           break;
/*     */         case 1:
/* 120 */           if (shootLaser(stack, world, (EntityLivingBase)player, 4.0F, 5.0F, 1, false, false)) {
/* 121 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 1, true);
/*     */           }
/*     */           break;
/*     */         case 2:
/* 125 */           if (shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 20.0F, 2147483647, false, false)) {
/* 126 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 2, true);
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/* 134 */           if (shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 8.0F, 2147483647, false, true)) {
/* 135 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 4, true);
/*     */           }
/*     */           break;
/*     */         case 5:
/* 139 */           look = Util.getLook((Entity)player);
/* 140 */           right = look.copy().cross(Vector3.UP);
/*     */           
/* 142 */           if (right.lengthSquared() < 1.0E-4D) {
/* 143 */             double angle = Math.toRadians(player.field_70177_z) - 1.5707963267948966D;
/* 144 */             right.set(Math.sin(angle), 0.0D, -Math.cos(angle));
/*     */           } else {
/* 146 */             right.normalize();
/*     */           } 
/*     */           
/* 149 */           up = right.copy().cross(look);
/*     */           
/* 151 */           sideShots = 2;
/* 152 */           unitDistance = 8.0D;
/*     */           
/* 154 */           look.scale(8.0D);
/*     */           
/* 156 */           for (r = -2; r <= 2; r++) {
/* 157 */             for (int u = -2; u <= 2; u++) {
/* 158 */               Vector3 dir = look.copy().addScaled(right, r).addScaled(up, u).normalize();
/*     */               
/* 160 */               shootLaser(stack, world, dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 12.0F, 2147483647, false, false);
/*     */             } 
/*     */           } 
/*     */           
/* 164 */           ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 5, true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/* 169 */           if (shootLaser(stack, world, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 12.0F, 2147483647, true, false)) {
/* 170 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 6, true);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 180 */     return super.func_77659_a(world, player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 185 */     if (world.field_72995_K) return EnumActionResult.PASS;
/*     */     
/* 187 */     ItemStack stack = StackUtil.get(player, hand);
/* 188 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 190 */     if (!IC2.keyboard.isModeSwitchKeyDown(player) && (nbtData
/* 191 */       .func_74762_e("laserSetting") == 3 || nbtData.func_74762_e("laserSetting") == 7)) {
/* 192 */       Vector3 dir = Util.getLook((Entity)player);
/* 193 */       double angle = dir.dot(Vector3.UP);
/*     */       
/* 195 */       if (Math.abs(angle) < 1.0D / Math.sqrt(2.0D)) {
/* 196 */         if (ElectricItem.manager.use(stack, 3000.0D, (EntityLivingBase)player)) {
/*     */           
/* 198 */           dir.y = 0.0D;
/* 199 */           dir.normalize();
/*     */           
/* 201 */           Vector3 start = Util.getEyePosition((Entity)player);
/* 202 */           start.y = pos.func_177956_o() + 0.5D;
/* 203 */           start = adjustStartPos(start, dir);
/*     */           
/* 205 */           if (nbtData.func_74762_e("laserSetting") == 3) {
/* 206 */             if (shootLaser(stack, world, start, dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false)) {
/* 207 */               ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 3, true);
/*     */             }
/* 209 */           } else if (nbtData.func_74762_e("laserSetting") == 7 && 
/* 210 */             shootLaser(stack, world, start, dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false)) {
/* 211 */             shootLaser(stack, world, new Vector3(start.x, start.y - 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 212 */             shootLaser(stack, world, new Vector3(start.x, start.y + 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 213 */             if (player.func_174811_aO().equals(EnumFacing.SOUTH) || player.func_174811_aO().equals(EnumFacing.NORTH)) {
/* 214 */               shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 215 */               shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 216 */               shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y - 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 217 */               shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y - 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 218 */               shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y + 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 219 */               shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y + 1.0D, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/*     */             } 
/* 221 */             if (player.func_174811_aO().equals(EnumFacing.EAST) || player.func_174811_aO().equals(EnumFacing.WEST)) {
/* 222 */               shootLaser(stack, world, new Vector3(start.x, start.y, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 223 */               shootLaser(stack, world, new Vector3(start.x, start.y, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 224 */               shootLaser(stack, world, new Vector3(start.x, start.y - 1.0D, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 225 */               shootLaser(stack, world, new Vector3(start.x, start.y - 1.0D, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 226 */               shootLaser(stack, world, new Vector3(start.x, start.y + 1.0D, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 227 */               shootLaser(stack, world, new Vector3(start.x, start.y + 1.0D, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/*     */             } 
/* 229 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 7, true);
/*     */           }
/*     */         
/*     */         } 
/* 233 */       } else if (nbtData.func_74762_e("laserSetting") == 7) {
/* 234 */         if (ElectricItem.manager.use(stack, 3000.0D, (EntityLivingBase)player)) {
/*     */           
/* 236 */           dir.x = 0.0D;
/* 237 */           dir.z = 0.0D;
/* 238 */           dir.normalize();
/*     */           
/* 240 */           Vector3 start = Util.getEyePosition((Entity)player);
/* 241 */           start.x = pos.func_177958_n() + 0.5D;
/* 242 */           start.z = pos.func_177952_p() + 0.5D;
/* 243 */           start = adjustStartPos(start, dir);
/*     */           
/* 245 */           if (shootLaser(stack, world, start, dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false)) {
/* 246 */             shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 247 */             shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y, start.z), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 248 */             shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 249 */             shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 250 */             shootLaser(stack, world, new Vector3(start.x + 1.0D, start.y, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 251 */             shootLaser(stack, world, new Vector3(start.x - 1.0D, start.y, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 252 */             shootLaser(stack, world, new Vector3(start.x, start.y, start.z + 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/* 253 */             shootLaser(stack, world, new Vector3(start.x, start.y, start.z - 1.0D), dir, (EntityLivingBase)player, Float.POSITIVE_INFINITY, 5.0F, 2147483647, false, false);
/*     */             
/* 255 */             ((NetworkManager)IC2.network.get(true)).initiateItemEvent(player, stack, 7, true);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 259 */         IC2.platform.messagePlayer(player, "Mining laser aiming angle too steep", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     return EnumActionResult.FAIL;
/*     */   }
/*     */   
/*     */   private static Vector3 adjustStartPos(Vector3 pos, Vector3 dir) {
/* 267 */     return pos.addScaled(dir, 0.2D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shootLaser(ItemStack stack, World world, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt) {
/* 272 */     Vector3 dir = Util.getLook((Entity)owner);
/*     */     
/* 274 */     return shootLaser(stack, world, dir, owner, range, power, blockBreaks, explosive, smelt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shootLaser(ItemStack stack, World world, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt) {
/* 279 */     Vector3 start = adjustStartPos(Util.getEyePosition((Entity)owner), dir);
/*     */     
/* 281 */     return shootLaser(stack, world, start, dir, owner, range, power, blockBreaks, explosive, smelt);
/*     */   }
/*     */   
/*     */   public boolean shootLaser(ItemStack stack, World world, Vector3 start, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt) {
/* 285 */     EntityMiningLaser entity = new EntityMiningLaser(world, start, dir, owner, range, power, blockBreaks, explosive);
/* 286 */     LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent(world, entity, owner, range, power, blockBreaks, explosive, smelt, stack);
/* 287 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/* 289 */     if (event.isCanceled()) return false;
/*     */     
/* 291 */     entity.copyDataFromEvent((LaserEvent)event);
/* 292 */     world.func_72838_d(entity);
/*     */     
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 300 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(ItemStack stack, EntityPlayer player, int event) {
/* 305 */     switch (event) {
/*     */       case 0:
/* 307 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 1:
/* 310 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLowFocus.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 2:
/* 313 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLongRange.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 3:
/* 316 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 4:
/* 319 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 5:
/* 322 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 6:
/* 325 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserExplosive.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */       case 7:
/* 328 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getModeString(int mode) {
/* 334 */     switch (mode) { case 0:
/* 335 */         return "ic2.tooltip.mode.mining";
/* 336 */       case 1: return "ic2.tooltip.mode.lowFocus";
/* 337 */       case 2: return "ic2.tooltip.mode.longRange";
/* 338 */       case 3: return "ic2.tooltip.mode.horizontal";
/* 339 */       case 4: return "ic2.tooltip.mode.superHeat";
/* 340 */       case 5: return "ic2.tooltip.mode.scatter";
/* 341 */       case 6: return "ic2.tooltip.mode.explosive";
/* 342 */       case 7: return "ic2.tooltip.mode.3x3"; }
/* 343 */      assert false; return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolMiningLaser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */