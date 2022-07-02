/*     */ package ic2.core.item.upgrade;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.api.upgrade.IFullUpgrade;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.api.upgrade.UpgradeRegistry;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.gui.dynamic.DynamicHandHeldContainer;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.IHandHeldSubInventory;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.item.ItemMulti;
/*     */ import ic2.core.item.tool.HandHeldInventory;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*     */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
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
/*     */ public class ItemUpgradeModule
/*     */   extends ItemMulti<ItemUpgradeModule.UpgradeType>
/*     */   implements IFullUpgrade, IHandHeldSubInventory, IItemHudInfo
/*     */ {
/*     */   public ItemUpgradeModule() {
/*  67 */     super(ItemName.upgrade, UpgradeType.class);
/*     */     
/*  69 */     func_77627_a(true);
/*     */     
/*  71 */     for (UpgradeType type : UpgradeType.values()) {
/*  72 */       UpgradeRegistry.register(new ItemStack((Item)this, 1, type.getId()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(final ItemName name) {
/*  79 */     ModelLoader.setCustomMeshDefinition((Item)this, new ItemMeshDefinition()
/*     */         {
/*     */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/*  82 */             ItemUpgradeModule.UpgradeType type = (ItemUpgradeModule.UpgradeType)ItemUpgradeModule.this.getType(stack);
/*  83 */             if (type == null) return new ModelResourceLocation("builtin/missing", "missing");
/*     */ 
/*     */             
/*     */             EnumFacing dir;
/*  87 */             if (type.directional && (dir = ItemUpgradeModule.getDirection(stack)) != null) {
/*  88 */               return ItemIC2.getModelLocation(name, type.getName() + '_' + dir.func_176610_l());
/*     */             }
/*  90 */             return ItemIC2.getModelLocation(name, type.getName());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  95 */     for (UpgradeType type : this.typeProperty.getAllowedValues()) {
/*  96 */       ModelBakery.registerItemVariants((Item)this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, type.getName()) });
/*     */       
/*  98 */       if (type.directional) {
/*  99 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 100 */           ModelBakery.registerItemVariants((Item)this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, type.getName() + '_' + dir.func_176610_l()) });
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 108 */     List<String> info = new LinkedList<>();
/* 109 */     info.add("Machine Upgrade");
/* 110 */     return info;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/*     */     String side;
/* 116 */     super.func_77624_a(stack, world, tooltip, advanced);
/*     */     
/* 118 */     UpgradeType type = (UpgradeType)getType(stack);
/* 119 */     if (type == null)
/*     */       return; 
/* 121 */     switch (type) {
/*     */       case IGNORED:
/* 123 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.overclocker.time", new Object[] { decimalformat
/* 124 */                 .format(100.0D * Math.pow(getProcessTimeMultiplier(stack, (IUpgradableBlock)null), StackUtil.getSize(stack))) }));
/* 125 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.overclocker.power", new Object[] { decimalformat
/* 126 */                 .format(100.0D * Math.pow(getEnergyDemandMultiplier(stack, (IUpgradableBlock)null), StackUtil.getSize(stack))) }));
/*     */         break;
/*     */       
/*     */       case FUZZY:
/* 130 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.transformer", new Object[] {
/* 131 */                 Integer.valueOf(getExtraTier(stack, (IUpgradableBlock)null) * StackUtil.getSize(stack))
/*     */               }));
/*     */         break;
/*     */       case EXACT:
/* 135 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.storage", new Object[] {
/* 136 */                 Integer.valueOf(getExtraEnergyStorage(stack, (IUpgradableBlock)null) * StackUtil.getSize(stack))
/*     */               }));
/*     */         break;
/*     */       case null:
/*     */       case null:
/* 141 */         side = getSideName(stack);
/* 142 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.ejector", new Object[] {
/* 143 */                 Localization.translate(side)
/*     */               }));
/*     */         break;
/*     */       case null:
/*     */       case null:
/* 148 */         side = getSideName(stack);
/* 149 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.pulling", new Object[] {
/* 150 */                 Localization.translate(side)
/*     */               }));
/*     */         break;
/*     */       case null:
/* 154 */         side = getSideName(stack);
/* 155 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.ejector", new Object[] {
/* 156 */                 Localization.translate(side)
/*     */               }));
/*     */         break;
/*     */       case null:
/* 160 */         side = getSideName(stack);
/* 161 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.pulling", new Object[] {
/* 162 */                 Localization.translate(side)
/*     */               }));
/*     */         break;
/*     */       case null:
/* 166 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.redstone"));
/*     */         break;
/*     */       
/*     */       case null:
/* 170 */         tooltip.add(Localization.translate("ic2.tooltip.upgrade.remote_interface", new Object[] { Integer.valueOf(StackUtil.getSize(stack)) }));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getSideName(ItemStack stack) {
/* 177 */     EnumFacing dir = getDirection(stack);
/*     */     
/* 179 */     if (dir == null) {
/* 180 */       return "ic2.tooltip.upgrade.ejector.anyside";
/*     */     }
/* 182 */     switch (dir) { case IGNORED:
/* 183 */         return "ic2.dir.west";
/* 184 */       case FUZZY: return "ic2.dir.east";
/* 185 */       case EXACT: return "ic2.dir.bottom";
/* 186 */       case null: return "ic2.dir.top";
/* 187 */       case null: return "ic2.dir.north";
/* 188 */       case null: return "ic2.dir.south"; }
/* 189 */      throw new RuntimeException("invalid dir: " + dir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/* 196 */     ItemStack stack = StackUtil.get(player, hand);
/* 197 */     UpgradeType type = (UpgradeType)getType(stack);
/* 198 */     if (type == null) return EnumActionResult.PASS;
/*     */     
/* 200 */     if (type.directional) {
/* 201 */       int dir = 1 + side.ordinal();
/*     */       
/* 203 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */       
/* 205 */       if (nbtData.func_74771_c("dir") == dir) {
/* 206 */         nbtData.func_74774_a("dir", (byte)0);
/*     */       } else {
/* 208 */         nbtData.func_74774_a("dir", (byte)dir);
/*     */       } 
/*     */       
/* 211 */       if (IC2.platform.isRendering()) {
/* 212 */         switch (type) {
/*     */           case null:
/*     */           case null:
/* 215 */             IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.ejector", new Object[] {
/* 216 */                     Localization.translate(getSideName(stack))
/*     */                   }), new Object[0]);
/*     */             break;
/*     */           case null:
/*     */           case null:
/* 221 */             IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.pulling", new Object[] {
/* 222 */                     Localization.translate(getSideName(stack))
/*     */                   }), new Object[0]);
/*     */             break;
/*     */           case null:
/* 226 */             IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.ejector", new Object[] {
/* 227 */                     Localization.translate(getSideName(stack))
/*     */                   }), new Object[0]);
/*     */             break;
/*     */           case null:
/* 231 */             IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.upgrade.pulling", new Object[] {
/* 232 */                     Localization.translate(getSideName(stack))
/*     */                   }), new Object[0]);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       }
/* 239 */       return EnumActionResult.SUCCESS;
/*     */     } 
/* 241 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/* 247 */     UpgradeType type = (UpgradeType)getType(stack);
/*     */     
/* 249 */     if (type != null) {
/* 250 */       switch (type) {
/*     */         case null:
/*     */         case null:
/* 253 */           if (!(player.func_130014_f_()).field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof DynamicHandHeldContainer) {
/* 254 */             HandHeldInventory base = (HandHeldInventory)((DynamicHandHeldContainer)player.field_71070_bA).base;
/*     */             
/* 256 */             if (base instanceof HandHeldAdvancedUpgrade && base.isThisContainer(stack)) {
/* 257 */               base.saveAsThrown(stack);
/* 258 */               player.func_71053_j();
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuitableFor(ItemStack stack, Set<UpgradableProperty> types) {
/* 272 */     UpgradeType type = (UpgradeType)getType(stack);
/* 273 */     if (type == null) return false;
/*     */     
/* 275 */     switch (type) {
/*     */       case null:
/*     */       case null:
/* 278 */         return types.contains(UpgradableProperty.ItemProducing);
/*     */       case null:
/*     */       case null:
/* 281 */         return types.contains(UpgradableProperty.ItemConsuming);
/*     */       case null:
/* 283 */         return types.contains(UpgradableProperty.FluidProducing);
/*     */       case null:
/* 285 */         return types.contains(UpgradableProperty.FluidConsuming);
/*     */       case EXACT:
/* 287 */         return types.contains(UpgradableProperty.EnergyStorage);
/*     */       case IGNORED:
/* 289 */         return (types.contains(UpgradableProperty.Processing) || types.contains(UpgradableProperty.Augmentable));
/*     */       
/*     */       case null:
/* 292 */         return types.contains(UpgradableProperty.RedstoneSensitive);
/*     */       case FUZZY:
/* 294 */         return types.contains(UpgradableProperty.Transformer);
/*     */       case null:
/* 296 */         return types.contains(UpgradableProperty.RemotelyAccessible);
/*     */     } 
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAugmentation(ItemStack stack, IUpgradableBlock parent) {
/* 304 */     UpgradeType type = (UpgradeType)getType(stack);
/* 305 */     if (type == null) return 0;
/*     */     
/* 307 */     switch (type) {
/*     */       case IGNORED:
/* 309 */         return 1;
/*     */     } 
/* 311 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraProcessTime(ItemStack stack, IUpgradableBlock parent) {
/* 317 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getProcessTimeMultiplier(ItemStack stack, IUpgradableBlock parent) {
/* 322 */     UpgradeType type = (UpgradeType)getType(stack);
/* 323 */     if (type == null) return 1.0D;
/*     */     
/* 325 */     switch (type) { case IGNORED:
/* 326 */         return 0.7D; }
/* 327 */      return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyDemand(ItemStack stack, IUpgradableBlock parent) {
/* 333 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getEnergyDemandMultiplier(ItemStack stack, IUpgradableBlock parent) {
/* 338 */     UpgradeType type = (UpgradeType)getType(stack);
/* 339 */     if (type == null) return 1.0D;
/*     */     
/* 341 */     switch (type) { case IGNORED:
/* 342 */         return 1.6D; }
/* 343 */      return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyStorage(ItemStack stack, IUpgradableBlock parent) {
/* 349 */     UpgradeType type = (UpgradeType)getType(stack);
/* 350 */     if (type == null) return 0;
/*     */     
/* 352 */     switch (type) { case EXACT:
/* 353 */         return 10000; }
/* 354 */      return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStorageMultiplier(ItemStack stack, IUpgradableBlock parent) {
/* 360 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExtraTier(ItemStack stack, IUpgradableBlock parent) {
/* 365 */     UpgradeType type = (UpgradeType)getType(stack);
/* 366 */     if (type == null) return 0;
/*     */     
/* 368 */     switch (type) { case FUZZY:
/* 369 */         return 1; }
/* 370 */      return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean modifiesRedstoneInput(ItemStack stack, IUpgradableBlock parent) {
/* 376 */     UpgradeType type = (UpgradeType)getType(stack);
/* 377 */     if (type == null) return false;
/*     */     
/* 379 */     switch (type) {
/*     */       case null:
/* 381 */         return true;
/*     */     } 
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRedstoneInput(ItemStack stack, IUpgradableBlock parent, int externalInput) {
/* 389 */     UpgradeType type = (UpgradeType)getType(stack);
/* 390 */     if (type == null) return externalInput;
/*     */     
/* 392 */     switch (type) {
/*     */       case null:
/* 394 */         return 15 - externalInput;
/*     */     } 
/* 396 */     return externalInput;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRangeAmplification(ItemStack stack, IUpgradableBlock parent, int existingRange) {
/* 402 */     UpgradeType type = (UpgradeType)getType(stack);
/* 403 */     if (type == null) return existingRange;
/*     */     
/* 405 */     switch (type) { case null:
/* 406 */         return existingRange << 1; }
/* 407 */      return existingRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTick(ItemStack stack, IUpgradableBlock parent) {
/*     */     int amount;
/* 413 */     UpgradeType type = (UpgradeType)getType(stack);
/* 414 */     if (type == null) return false;
/*     */     
/* 416 */     int size = StackUtil.getSize(stack);
/* 417 */     TileEntity te = (TileEntity)parent;
/* 418 */     boolean ret = false;
/*     */     
/* 420 */     switch (type) {
/*     */       case null:
/* 422 */         amount = (int)Math.pow(4.0D, Math.min(4, size - 1));
/*     */         
/* 424 */         for (StackUtil.AdjacentInv inv : getTargetInventories(stack, te)) {
/* 425 */           StackUtil.transfer(te, inv.te, inv.dir, amount);
/*     */         }
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
/* 483 */         return ret;case null: amount = (int)Math.pow(4.0D, Math.min(4, size - 1)); for (StackUtil.AdjacentInv inv : getTargetInventories(stack, te)) StackUtil.transfer(te, inv.te, inv.dir, amount, stackChecker(stack));  return ret;case null: amount = (int)Math.pow(4.0D, Math.min(4, size - 1)); for (StackUtil.AdjacentInv inv : getTargetInventories(stack, te)) StackUtil.transfer(inv.te, te, inv.dir.func_176734_d(), amount);  return ret;case null: amount = (int)Math.pow(4.0D, Math.min(4, size - 1)); for (StackUtil.AdjacentInv inv : getTargetInventories(stack, te)) StackUtil.transfer(inv.te, te, inv.dir.func_176734_d(), amount, stackChecker(stack));  return ret;case null: if (!LiquidUtil.isFluidTile(te, null)) return false;  amount = (int)(50.0D * Math.pow(4.0D, Math.min(4, size - 1))); for (LiquidUtil.AdjacentFluidHandler fh : getTargetFluidHandlers(stack, te)) LiquidUtil.transfer(te, fh.dir, fh.handler, amount);  return ret;case null: if (!LiquidUtil.isFluidTile(te, null)) return false;  amount = (int)(50.0D * Math.pow(4.0D, Math.min(4, size - 1))); for (LiquidUtil.AdjacentFluidHandler fh : getTargetFluidHandlers(stack, te)) LiquidUtil.transfer(fh.handler, fh.dir.func_176734_d(), te, amount);  return ret;
/*     */     } 
/*     */     return false;
/*     */   } private static Predicate<ItemStack> stackChecker(final ItemStack stack) {
/* 487 */     return new Predicate<ItemStack>() { private boolean hasInitialised = false;
/*     */         private Set<ItemStack> filters;
/*     */         private Settings meta;
/*     */         private Settings damage;
/*     */         private Settings energy;
/*     */         private NbtSettings nbt;
/*     */         
/*     */         private void initalise() {
/* 495 */           assert !this.hasInitialised;
/*     */           
/* 497 */           NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
/* 498 */           this.filters = getFilterStacks(tag);
/* 499 */           this.meta = new Settings(HandHeldAdvancedUpgrade.getTag(tag, "meta"));
/* 500 */           this.damage = null;
/* 501 */           this.nbt = NbtSettings.getFromNBT(HandHeldAdvancedUpgrade.getTag(tag, "nbt").func_74771_c("type"));
/* 502 */           this.energy = new Settings(HandHeldAdvancedUpgrade.getTag(tag, "energy"));
/*     */           
/* 504 */           this.hasInitialised = true;
/*     */         }
/*     */         
/*     */         private Set<ItemStack> getFilterStacks(NBTTagCompound nbt) {
/* 508 */           Set<ItemStack> ret = new HashSet<>();
/* 509 */           NBTTagList contentList = nbt.func_150295_c("Items", 10);
/*     */           
/* 511 */           for (int tag = 0; tag < contentList.func_74745_c(); tag++) {
/* 512 */             NBTTagCompound slotNbt = contentList.func_150305_b(tag);
/* 513 */             int slot = slotNbt.func_74771_c("Slot");
/*     */             
/* 515 */             if (slot >= 0 && slot < 9) {
/* 516 */               ItemStack filter = new ItemStack(slotNbt);
/* 517 */               if (!StackUtil.isEmpty(filter)) ret.add(filter);
/*     */             
/*     */             } 
/*     */           } 
/* 521 */           return ret;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private boolean checkMeta(ItemStack stack, ItemStack filter) {
/* 527 */           assert this.meta.active;
/* 528 */           assert this.meta.comparison == ComparisonType.DIRECT;
/*     */           
/* 530 */           return (stack.func_77960_j() == filter.func_77960_j());
/*     */         }
/*     */         
/*     */         private boolean checkDamage(ItemStack stack, ItemStack filter, boolean customStack) {
/* 534 */           assert this.damage.active;
/* 535 */           assert this.damage.comparison == ComparisonType.DIRECT;
/*     */           
/* 537 */           return (customStack && filter.func_77973_b() instanceof ICustomDamageItem) ? (
/*     */ 
/*     */             
/* 540 */             (((ICustomDamageItem)stack.func_77973_b()).getCustomDamage(stack) == ((ICustomDamageItem)filter.func_77973_b()).getCustomDamage(filter))) : (
/*     */             
/* 542 */             (filter.func_77952_i() == stack.func_77952_i()));
/*     */         }
/*     */         
/*     */         private boolean checkNBT(ItemStack stack, ItemStack filter) {
/* 546 */           switch (this.nbt) {
/*     */             case IGNORED:
/* 548 */               return true;
/*     */             
/*     */             case FUZZY:
/* 551 */               return StackUtil.checkNbtEquality(stack.func_77978_p(), filter.func_77978_p());
/*     */             
/*     */             case EXACT:
/* 554 */               return StackUtil.checkNbtEqualityStrict(stack, filter);
/*     */           } 
/*     */           
/* 557 */           throw new IllegalStateException("Unexpected NBT state: " + this.nbt);
/*     */         }
/*     */ 
/*     */         
/*     */         private boolean checkEnergy(ItemStack stack, ItemStack filter) {
/* 562 */           assert this.energy.active;
/* 563 */           assert this.energy.comparison == ComparisonType.DIRECT;
/*     */           
/* 565 */           return (filter.func_77973_b() instanceof ic2.api.item.IElectricItem && 
/* 566 */             Util.isSimilar(ElectricItem.manager.getCharge(stack), ElectricItem.manager.getCharge(filter)));
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean apply(ItemStack stack) {
/*     */           boolean checkMeta, checkEnergy;
/* 572 */           if (!this.hasInitialised) initalise();
/*     */ 
/*     */           
/* 575 */           if (!this.meta.comparison.ignoreFilters()) {
/* 576 */             if (this.meta.doComparison(stack.func_77960_j())) {
/* 577 */               checkMeta = false;
/*     */             } else {
/* 579 */               return false;
/*     */             } 
/*     */           } else {
/* 582 */             checkMeta = this.meta.active;
/*     */           } 
/*     */           
/* 585 */           boolean customStack = stack.func_77973_b() instanceof ICustomDamageItem;
/* 586 */           boolean checkDamage = false;
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
/* 598 */           if (!this.energy.comparison.ignoreFilters()) {
/* 599 */             if (stack.func_77973_b() instanceof ic2.api.item.IElectricItem && this.energy.doComparison((int)ElectricItem.manager.getCharge(stack))) {
/* 600 */               checkEnergy = false;
/*     */             } else {
/* 602 */               return false;
/*     */             } 
/*     */           } else {
/* 605 */             checkEnergy = this.energy.active;
/*     */             
/* 607 */             if (checkEnergy && !(stack.func_77973_b() instanceof ic2.api.item.IElectricItem)) return false;
/*     */           
/*     */           } 
/* 610 */           for (ItemStack filter : this.filters) {
/* 611 */             if (filter.func_77973_b() == stack.func_77973_b() && (!checkMeta || 
/* 612 */               checkMeta(stack, filter)) && (!checkDamage || 
/* 613 */               checkDamage(stack, filter, customStack)) && 
/* 614 */               checkNBT(stack, filter) && (!checkEnergy || 
/* 615 */               checkEnergy(stack, filter))) return true;
/*     */           
/*     */           } 
/* 618 */           return (this.filters.isEmpty() && this.meta.active && !checkMeta && this.energy.active && !checkEnergy);
/*     */         } }
/*     */       ;
/*     */   }
/*     */   
/*     */   private static List<StackUtil.AdjacentInv> getTargetInventories(ItemStack stack, TileEntity parent) {
/* 624 */     EnumFacing dir = getDirection(stack);
/*     */     
/* 626 */     if (dir == null) {
/* 627 */       return StackUtil.getAdjacentInventories(parent);
/*     */     }
/* 629 */     StackUtil.AdjacentInv inv = StackUtil.getAdjacentInventory(parent, dir);
/* 630 */     if (inv == null) return emptyInvList;
/*     */     
/* 632 */     return Collections.singletonList(inv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<LiquidUtil.AdjacentFluidHandler> getTargetFluidHandlers(ItemStack stack, TileEntity parent) {
/* 637 */     EnumFacing dir = getDirection(stack);
/*     */     
/* 639 */     if (dir == null) {
/* 640 */       return LiquidUtil.getAdjacentHandlers(parent);
/*     */     }
/* 642 */     LiquidUtil.AdjacentFluidHandler fh = LiquidUtil.getAdjacentHandler(parent, dir);
/* 643 */     if (fh == null) return emptyFhList;
/*     */     
/* 645 */     return Collections.singletonList(fh);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ItemStack> onProcessEnd(ItemStack stack, IUpgradableBlock parent, Collection<ItemStack> output) {
/* 651 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/* 656 */     UpgradeType type = (UpgradeType)getType(stack);
/* 657 */     if (type == null) return null;
/*     */     
/* 659 */     switch (type) {
/*     */       case null:
/*     */       case null:
/* 662 */         return (IHasGui)new HandHeldAdvancedUpgrade(player, stack);
/*     */     } 
/* 664 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IHasGui getSubInventory(EntityPlayer player, ItemStack stack, int ID) {
/* 670 */     UpgradeType type = (UpgradeType)getType(stack);
/* 671 */     if (type == null) return null;
/*     */     
/* 673 */     switch (type) {
/*     */       case null:
/*     */       case null:
/* 676 */         return HandHeldAdvancedUpgrade.delegate(player, stack, ID);
/*     */     } 
/* 678 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumFacing getDirection(ItemStack stack) {
/* 683 */     int rawDir = StackUtil.getOrCreateNbtData(stack).func_74771_c("dir");
/*     */     
/* 685 */     if (rawDir < 1 || rawDir > 6) return null;
/*     */     
/* 687 */     return EnumFacing.field_82609_l[rawDir - 1];
/*     */   }
/*     */   
/*     */   public enum UpgradeType implements IIdProvider {
/* 691 */     overclocker(false),
/* 692 */     transformer(false),
/* 693 */     energy_storage(false),
/* 694 */     redstone_inverter(false),
/* 695 */     ejector(true),
/* 696 */     advanced_ejector(true),
/*     */     
/* 698 */     pulling(true),
/* 699 */     advanced_pulling(true),
/*     */     
/* 701 */     fluid_ejector(true),
/* 702 */     fluid_pulling(true),
/* 703 */     remote_interface(false);
/*     */     public final boolean directional;
/*     */     
/*     */     UpgradeType(boolean directional) {
/* 707 */       this.directional = directional;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 712 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 717 */       return ordinal();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 723 */   private static final DecimalFormat decimalformat = new DecimalFormat("0.##");
/* 724 */   private static final List<StackUtil.AdjacentInv> emptyInvList = Collections.emptyList();
/* 725 */   private static final List<LiquidUtil.AdjacentFluidHandler> emptyFhList = Collections.emptyList();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\ItemUpgradeModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */