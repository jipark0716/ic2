/*     */ package ic2.api.event;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Cancelable
/*     */ public class LaserEvent
/*     */   extends WorldEvent
/*     */ {
/*     */   public final Entity lasershot;
/*     */   public EntityLivingBase owner;
/*     */   public float range;
/*     */   public float power;
/*     */   public int blockBreaks;
/*     */   public boolean explosive;
/*     */   public boolean smelt;
/*     */   
/*     */   public LaserEvent(World world, Entity lasershot, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt) {
/*  32 */     super(world);
/*     */     
/*  34 */     this.lasershot = lasershot;
/*  35 */     this.owner = owner;
/*  36 */     this.range = range;
/*  37 */     this.power = power;
/*  38 */     this.blockBreaks = blockBreaks;
/*  39 */     this.explosive = explosive;
/*  40 */     this.smelt = smelt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LaserShootEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public final ItemStack laserItem;
/*     */ 
/*     */     
/*     */     public LaserShootEvent(World world, Entity lasershot, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, ItemStack laseritem) {
/*  52 */       super(world, lasershot, owner, range, power, blockBreaks, explosive, smelt);
/*     */       
/*  54 */       this.laserItem = laseritem;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LaserExplodesEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public float explosionPower;
/*     */     
/*     */     public float explosionDropRate;
/*     */     public float explosionEntityDamage;
/*     */     
/*     */     public LaserExplodesEvent(World world, Entity lasershot, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, float explosionpower1, float explosiondroprate1, float explosionentitydamage1) {
/*  68 */       super(world, lasershot, owner, range, power, blockBreaks, explosive, smelt);
/*     */       
/*  70 */       this.explosionPower = explosionpower1;
/*  71 */       this.explosionDropRate = explosiondroprate1;
/*  72 */       this.explosionEntityDamage = explosionentitydamage1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LaserHitsBlockEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public BlockPos pos;
/*     */     
/*     */     public final EnumFacing side;
/*     */     
/*     */     public boolean removeBlock;
/*     */     
/*     */     public boolean dropBlock;
/*     */     
/*     */     public float dropChance;
/*     */ 
/*     */     
/*     */     public LaserHitsBlockEvent(World world, Entity lasershot, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive1, boolean smelt1, BlockPos pos, EnumFacing side, float dropChance, boolean removeBlock, boolean dropBlock) {
/*  92 */       super(world, lasershot, owner, range, power, blockBreaks, explosive1, smelt1);
/*     */       
/*  94 */       this.pos = pos;
/*  95 */       this.side = side;
/*  96 */       this.removeBlock = removeBlock;
/*  97 */       this.dropBlock = dropBlock;
/*  98 */       this.dropChance = dropChance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LaserHitsEntityEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public Entity hitEntity;
/*     */ 
/*     */     
/*     */     public boolean passThrough;
/*     */ 
/*     */ 
/*     */     
/*     */     public LaserHitsEntityEvent(World world, Entity lasershot, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive, boolean smelt, Entity hitentity) {
/* 115 */       super(world, lasershot, owner, range, power, blockBreaks, explosive, smelt);
/*     */       
/* 117 */       this.hitEntity = hitentity;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\event\LaserEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */