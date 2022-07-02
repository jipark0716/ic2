/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.core.block.EntityIC2Explosive;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class Explosive extends TileEntityInventory implements Redstone.IRedstoneChangeHandler {
/*     */   protected final Redstone redstone;
/*     */   
/*     */   protected Explosive() {
/*  25 */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*  26 */     this.redstone.subscribe(this);
/*     */   }
/*     */   private boolean exploded;
/*     */   
/*     */   protected SoundType getBlockSound(Entity entity) {
/*  31 */     return SoundType.field_185850_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRedstoneChange(int newLevel) {
/*  36 */     if (newLevel > 0) explode((EntityLivingBase)null, false);
/*     */   
/*     */   }
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  41 */     if (StackUtil.consume(player, hand, StackUtil.sameItem(Items.field_151059_bz), 1) || 
/*  42 */       StackUtil.damage(player, hand, StackUtil.sameItem(Items.field_151033_d), 1)) {
/*  43 */       explode((EntityLivingBase)player, false);
/*  44 */       return true;
/*     */     } 
/*     */     
/*  47 */     return super.onActivated(player, hand, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onExploded(Explosion explosion) {
/*  52 */     super.onExploded(explosion);
/*     */     
/*  54 */     explode(explosion.func_94613_c(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onRemovedByPlayer(EntityPlayer player, boolean willHarvest) {
/*  59 */     if (explodeOnRemoval()) {
/*  60 */       explode((EntityLivingBase)player, false);
/*     */       
/*  62 */       return true;
/*     */     } 
/*     */     
/*  65 */     return super.onRemovedByPlayer(player, willHarvest);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityCollision(Entity entity) {
/*  70 */     if (!(func_145831_w()).field_72995_K && entity instanceof EntityArrow && entity.func_70027_ad()) {
/*  71 */       EntityArrow arrow = (EntityArrow)entity;
/*     */       
/*  73 */       explode((arrow.field_70250_c instanceof EntityLivingBase) ? (EntityLivingBase)arrow.field_70250_c : null, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/*  79 */     if (this.exploded) return null;
/*     */     
/*  81 */     return super.adjustDrop(drop, wrench);
/*     */   }
/*     */   
/*     */   protected boolean explode(EntityLivingBase igniter, boolean shortFuse) {
/*  85 */     EntityIC2Explosive entity = getEntity(igniter);
/*  86 */     if (entity == null) return false;
/*     */     
/*  88 */     World world = func_145831_w();
/*  89 */     if (world.field_72995_K) return true;
/*     */     
/*  91 */     entity.setIgniter(igniter);
/*  92 */     onIgnite(igniter);
/*  93 */     world.func_175698_g(this.field_174879_c);
/*     */     
/*  95 */     if (shortFuse) {
/*  96 */       entity.fuse = world.field_73012_v.nextInt(Math.max(1, entity.fuse / 4)) + entity.fuse / 8;
/*     */     }
/*     */     
/*  99 */     world.func_72838_d((Entity)entity);
/* 100 */     world.func_184148_a((EntityPlayer)null, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */     
/* 102 */     this.exploded = true;
/*     */     
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean explodeOnRemoval() {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract EntityIC2Explosive getEntity(EntityLivingBase paramEntityLivingBase);
/*     */   
/*     */   protected void onIgnite(EntityLivingBase igniter) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\Explosive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */