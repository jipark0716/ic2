/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.item.ElectricItemManager;
/*     */ import ic2.core.item.IPseudoDamageItem;
/*     */ import ic2.core.item.ItemToolIC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class ItemElectricTool extends ItemToolIC2 implements IPseudoDamageItem, IElectricItem, IItemHudInfo {
/*     */   public double operationEnergyCost;
/*     */   public int maxCharge;
/*     */   public int transferLimit;
/*     */   public int tier;
/*     */   protected AudioSource audioSource;
/*     */   protected boolean wasEquipped;
/*     */   
/*     */   protected ItemElectricTool(ItemName name, int operationEnergyCost) {
/*  48 */     this(name, operationEnergyCost, HarvestLevel.Iron, Collections.emptySet());
/*     */   }
/*     */   
/*     */   protected ItemElectricTool(ItemName name, int operationEnergyCost, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses) {
/*  52 */     this(name, 2.0F, -3.0F, operationEnergyCost, harvestLevel, toolClasses, new HashSet<>());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemElectricTool(ItemName name, float damage, float speed, int operationEnergyCost, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
/*  58 */     super(name, damage, speed, harvestLevel, toolClasses, mineableBlocks);
/*     */     
/*  60 */     this.operationEnergyCost = operationEnergyCost;
/*     */     
/*  62 */     func_77656_e(27);
/*  63 */     setNoRepair();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/*  69 */     ElectricItem.manager.use(StackUtil.get(player, hand), 0.0D, (EntityLivingBase)player);
/*     */     
/*  71 */     return super.func_180614_a(player, world, pos, hand, side, xOffset, yOffset, zOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  77 */     ElectricItem.manager.use(StackUtil.get(player, hand), 0.0D, (EntityLivingBase)player);
/*     */     
/*  79 */     return super.func_77659_a(world, player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack stack, IBlockState state) {
/*  85 */     if (!ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
/*  86 */       return 1.0F;
/*     */     }
/*  88 */     return super.func_150893_a(stack, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77619_b() {
/* 108 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 123 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/* 128 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 133 */     return this.transferLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_179218_a(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
/* 138 */     if (state.func_185887_b(world, pos) != 0.0F) {
/* 139 */       if (user != null) {
/* 140 */         ElectricItem.manager.use(stack, this.operationEnergyCost, user);
/*     */       } else {
/* 142 */         ElectricItem.manager.discharge(stack, this.operationEnergyCost, this.tier, true, false, false);
/*     */       } 
/*     */     }
/*     */     
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 167 */     if (!func_194125_a(tab))
/*     */       return; 
/* 169 */     ElectricItemManager.addChargeVariants((Item)this, (List)subItems);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 175 */     List<String> info = new LinkedList<>();
/* 176 */     info.add(ElectricItem.manager.getToolTip(stack));
/* 177 */     info.add(Localization.translate("ic2.item.tooltip.PowerTier", new Object[] { Integer.valueOf(this.tier) }));
/* 178 */     return info;
/*     */   }
/*     */   
/*     */   protected ItemStack getItemStack(double charge) {
/* 182 */     ItemStack ret = new ItemStack((Item)this);
/* 183 */     ElectricItem.manager.charge(ret, charge, 2147483647, true, false);
/*     */     
/* 185 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
/* 190 */     boolean isEquipped = (flag && entity instanceof EntityLivingBase);
/*     */     
/* 192 */     if (IC2.platform.isRendering()) {
/* 193 */       if (isEquipped && !this.wasEquipped) {
/* 194 */         if (this.audioSource == null) {
/* 195 */           String sound = getIdleSound((EntityLivingBase)entity, itemstack);
/* 196 */           if (sound != null) {
/* 197 */             this.audioSource = IC2.audioManager.createSource(entity, PositionSpec.Hand, sound, true, false, IC2.audioManager.getDefaultVolume());
/*     */           }
/*     */         } 
/* 200 */         if (this.audioSource != null) this.audioSource.play(); 
/* 201 */         String initSound = getStartSound((EntityLivingBase)entity, itemstack);
/* 202 */         if (initSound != null) {
/* 203 */           IC2.audioManager.playOnce(entity, PositionSpec.Hand, initSound, true, IC2.audioManager.getDefaultVolume());
/*     */         }
/* 205 */       } else if (!isEquipped && this.audioSource != null) {
/* 206 */         if (entity instanceof EntityLivingBase) {
/* 207 */           EntityLivingBase theEntity = (EntityLivingBase)entity;
/* 208 */           ItemStack stack = theEntity.func_184582_a(EntityEquipmentSlot.MAINHAND);
/* 209 */           if (stack == null || stack.func_77973_b() != this || stack == itemstack) {
/* 210 */             removeAudioSource();
/* 211 */             String sound = getStopSound(theEntity, itemstack);
/* 212 */             if (sound != null) {
/* 213 */               IC2.audioManager.playOnce(entity, PositionSpec.Hand, sound, true, IC2.audioManager.getDefaultVolume());
/*     */             }
/*     */           } 
/*     */         } 
/* 217 */       } else if (this.audioSource != null) {
/* 218 */         this.audioSource.updatePosition();
/*     */       } 
/*     */       
/* 221 */       this.wasEquipped = isEquipped;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeAudioSource() {
/* 227 */     if (this.audioSource != null) {
/* 228 */       this.audioSource.stop();
/* 229 */       this.audioSource.remove();
/* 230 */       this.audioSource = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
/* 236 */     removeAudioSource();
/* 237 */     return true;
/*     */   }
/*     */   
/*     */   protected String getIdleSound(EntityLivingBase player, ItemStack stack) {
/* 241 */     return null;
/*     */   }
/*     */   
/*     */   protected String getStopSound(EntityLivingBase player, ItemStack stack) {
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   protected String getStartSound(EntityLivingBase player, ItemStack stack) {
/* 249 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/* 254 */     int prev = getDamage(stack);
/*     */     
/* 256 */     if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
/* 257 */       IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", new Object[] { Integer.valueOf(damage - prev) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStackDamage(ItemStack stack, int damage) {
/* 263 */     super.setDamage(stack, damage);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemElectricTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */