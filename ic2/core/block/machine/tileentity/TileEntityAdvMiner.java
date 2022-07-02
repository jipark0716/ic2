/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableId;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerAdvMiner;
/*     */ import ic2.core.block.machine.gui.GuiAdvMiner;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityAdvMiner extends TileEntityElectricMachine implements IHasGui, INetworkClientTileEntityEventListener, IUpgradableBlock {
/*     */   private int maxBlockScanCount;
/*     */   public final int defaultTier;
/*     */   public final int workTick;
/*     */   public boolean blacklist;
/*     */   public boolean silkTouch;
/*     */   public boolean redstonePowered;
/*     */   private final int scanEnergy = 64;
/*     */   
/*     */   public TileEntityAdvMiner() {
/*  54 */     this(Math.min(2 + ConfigUtil.getInt(MainConfig.get(), "balance/minerDischargeTier"), 5));
/*     */   }
/*     */   private final int mineEnergy = 512; private BlockPos mineTarget; private short ticker; public final InvSlotConsumableId scannerSlot; public final InvSlotUpgrade upgradeSlot; public final InvSlot filterSlot; protected final Redstone redstone;
/*     */   public TileEntityAdvMiner(int tier) {
/*  58 */     super(4000000, tier);
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
/* 330 */     this.blacklist = true;
/* 331 */     this.silkTouch = false;
/*     */     
/* 333 */     this.redstonePowered = false;
/*     */     
/* 335 */     this.scanEnergy = 64;
/* 336 */     this.mineEnergy = 512;
/*     */ 
/*     */ 
/*     */     
/* 340 */     this.ticker = 0;
/*     */     this.scannerSlot = new InvSlotConsumableId((IInventorySlotHolder)this, "scanner", InvSlot.Access.IO, 1, InvSlot.InvSide.BOTTOM, new Item[] { ItemName.scanner.getInstance(), ItemName.advanced_scanner.getInstance() });
/*     */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*     */     this.filterSlot = new InvSlot((IInventorySlotHolder)this, "list", null, 15);
/*     */     this.defaultTier = tier;
/*     */     this.workTick = 20;
/*     */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*     */   }
/*     */   
/*     */   protected void onLoaded() {
/*     */     super.onLoaded();
/*     */     if (!(func_145831_w()).field_72995_K)
/*     */       setUpgradestat(); 
/*     */   }
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*     */     super.func_145839_a(nbt);
/*     */     if (nbt.func_74764_b("mineTargetX"))
/*     */       this.mineTarget = new BlockPos(nbt.func_74762_e("mineTargetX"), nbt.func_74762_e("mineTargetY"), nbt.func_74762_e("mineTargetZ")); 
/*     */     this.blacklist = nbt.func_74767_n("blacklist");
/*     */     this.silkTouch = nbt.func_74767_n("silkTouch");
/*     */   }
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*     */     super.func_189515_b(nbt);
/*     */     if (this.mineTarget != null) {
/*     */       nbt.func_74768_a("mineTargetX", this.mineTarget.func_177958_n());
/*     */       nbt.func_74768_a("mineTargetY", this.mineTarget.func_177956_o());
/*     */       nbt.func_74768_a("mineTargetZ", this.mineTarget.func_177952_p());
/*     */     } 
/*     */     nbt.func_74757_a("blacklist", this.blacklist);
/*     */     nbt.func_74757_a("silkTouch", this.silkTouch);
/*     */     return nbt;
/*     */   }
/*     */   
/*     */   public void func_70296_d() {
/*     */     super.func_70296_d();
/*     */     if (!(func_145831_w()).field_72995_K)
/*     */       setUpgradestat(); 
/*     */   }
/*     */   
/*     */   protected void updateEntityServer() {
/*     */     super.updateEntityServer();
/*     */     chargeTool();
/*     */     if (work()) {
/*     */       super.func_70296_d();
/*     */       setActive(true);
/*     */     } else {
/*     */       setActive(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean work() {
/*     */     int range;
/*     */     if (!this.energy.canUseEnergy(512.0D))
/*     */       return false; 
/*     */     if (this.redstone.hasRedstoneInput())
/*     */       return false; 
/*     */     if (this.mineTarget != null && this.mineTarget.func_177956_o() < 0)
/*     */       return false; 
/*     */     ItemStack scanner = this.scannerSlot.get();
/*     */     if (StackUtil.isEmpty(scanner) || !ElectricItem.manager.canUse(scanner, 64.0D))
/*     */       return false; 
/*     */     if ((this.ticker = (short)(this.ticker + 1)) != this.workTick)
/*     */       return true; 
/*     */     this.ticker = 0;
/*     */     if (scanner.func_77973_b() instanceof ic2.core.item.tool.ItemScannerAdv) {
/*     */       range = 32;
/*     */     } else if (scanner.func_77973_b() instanceof ic2.core.item.tool.ItemScanner) {
/*     */       range = 16;
/*     */     } else {
/*     */       range = 0;
/*     */     } 
/*     */     if (this.mineTarget == null) {
/*     */       this.mineTarget = new BlockPos(this.field_174879_c.func_177958_n() - range - 1, this.field_174879_c.func_177956_o() - 1, this.field_174879_c.func_177952_p() - range);
/*     */       if (this.mineTarget.func_177956_o() < 0)
/*     */         return false; 
/*     */     } 
/*     */     int blockScanCount = this.maxBlockScanCount;
/*     */     World world = func_145831_w();
/*     */     BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos(this.mineTarget.func_177958_n(), this.mineTarget.func_177956_o(), this.mineTarget.func_177952_p());
/*     */     do {
/*     */       if (scanPos.func_177958_n() < this.field_174879_c.func_177958_n() + range) {
/*     */         scanPos = new BlockPos.MutableBlockPos(scanPos.func_177958_n() + 1, scanPos.func_177956_o(), scanPos.func_177952_p());
/*     */       } else if (scanPos.func_177952_p() < this.field_174879_c.func_177952_p() + range) {
/*     */         scanPos = new BlockPos.MutableBlockPos(this.field_174879_c.func_177958_n() - range, scanPos.func_177956_o(), scanPos.func_177952_p() + 1);
/*     */       } else {
/*     */         scanPos = new BlockPos.MutableBlockPos(this.field_174879_c.func_177958_n() - range, scanPos.func_177956_o() - 1, this.field_174879_c.func_177952_p() - range);
/*     */         if (scanPos.func_177956_o() < 0) {
/*     */           this.mineTarget = new BlockPos((Vec3i)scanPos);
/*     */           return true;
/*     */         } 
/*     */       } 
/*     */       ElectricItem.manager.discharge(scanner, 64.0D, 2147483647, true, false, false);
/*     */       IBlockState state = world.func_180495_p((BlockPos)scanPos);
/*     */       Block block = state.func_177230_c();
/*     */       if (!block.isAir(state, (IBlockAccess)world, (BlockPos)scanPos) && canMine((BlockPos)scanPos, block, state)) {
/*     */         this.mineTarget = new BlockPos((Vec3i)scanPos);
/*     */         doMine(this.mineTarget, block, state);
/*     */         break;
/*     */       } 
/*     */       this.mineTarget = new BlockPos((Vec3i)scanPos);
/*     */     } while (--blockScanCount > 0 && ElectricItem.manager.canUse(scanner, 64.0D));
/*     */     return true;
/*     */   }
/*     */   
/*     */   private void chargeTool() {
/*     */     if (!this.scannerSlot.isEmpty())
/*     */       this.energy.useEnergy(ElectricItem.manager.charge(this.scannerSlot.get(), this.energy.getEnergy(), this.energy.getSinkTier(), false, false)); 
/*     */   }
/*     */   
/*     */   public void doMine(BlockPos pos, Block block, IBlockState state) {
/*     */     World world = func_145831_w();
/*     */     StackUtil.distributeDrops((TileEntity)this, new ArrayList(StackUtil.getDrops((IBlockAccess)world, pos, state, null, 0, this.silkTouch)));
/*     */     world.func_175698_g(pos);
/*     */     this.energy.useEnergy(512.0D);
/*     */   }
/*     */   
/*     */   public boolean canMine(BlockPos pos, Block block, IBlockState state) {
/*     */     if (block instanceof net.minecraftforge.fluids.IFluidBlock || block instanceof net.minecraft.block.BlockStaticLiquid || block instanceof net.minecraft.block.BlockDynamicLiquid)
/*     */       return false; 
/*     */     World world = func_145831_w();
/*     */     if (state.func_185887_b(world, pos) < 0.0F)
/*     */       return false; 
/*     */     List<ItemStack> drops = StackUtil.getDrops((IBlockAccess)world, pos, state, null, 0, this.silkTouch);
/*     */     if (drops.isEmpty())
/*     */       return false; 
/*     */     if (block.hasTileEntity(state) && OreValues.get(drops) <= 0)
/*     */       return false; 
/*     */     if (this.blacklist) {
/*     */       for (ItemStack drop : drops) {
/*     */         for (ItemStack filter : this.filterSlot) {
/*     */           if (StackUtil.checkItemEquality(drop, filter))
/*     */             return false; 
/*     */         } 
/*     */       } 
/*     */       return true;
/*     */     } 
/*     */     for (ItemStack drop : drops) {
/*     */       for (ItemStack filter : this.filterSlot) {
/*     */         if (StackUtil.checkItemEquality(drop, filter))
/*     */           return true; 
/*     */       } 
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/*     */     switch (event) {
/*     */       case 0:
/*     */         this.mineTarget = null;
/*     */         break;
/*     */       case 1:
/*     */         if (!getActive())
/*     */           this.blacklist = !this.blacklist; 
/*     */         break;
/*     */       case 2:
/*     */         if (!getActive())
/*     */           this.silkTouch = !this.silkTouch; 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUpgradestat() {
/*     */     this.upgradeSlot.onChanged();
/*     */     int tier = this.upgradeSlot.getTier(this.defaultTier);
/*     */     this.energy.setSinkTier(tier);
/*     */     this.dischargeSlot.setTier(tier);
/*     */     this.maxBlockScanCount = 5 * (this.upgradeSlot.augmentation + 1);
/*     */   }
/*     */   
/*     */   public ContainerBase<TileEntityAdvMiner> getGuiContainer(EntityPlayer player) {
/*     */     return (ContainerBase<TileEntityAdvMiner>)new ContainerAdvMiner(player, this);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*     */     return (GuiScreen)new GuiAdvMiner(new ContainerAdvMiner(player, this));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */   
/*     */   public double getEnergy() {
/*     */     return this.energy.getEnergy();
/*     */   }
/*     */   
/*     */   public boolean useEnergy(double amount) {
/*     */     return this.energy.useEnergy(amount);
/*     */   }
/*     */   
/*     */   public BlockPos getMineTarget() {
/*     */     return this.mineTarget;
/*     */   }
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/*     */     super.onPlaced(stack, placer, facing);
/*     */     if (!(func_145831_w()).field_72995_K) {
/*     */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*     */       this.energy.addEnergy(nbt.func_74769_h("energy"));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/*     */     drop = super.adjustDrop(drop, wrench);
/*     */     if (wrench || this.teBlock.getDefaultDrop() == TeBlock.DefaultDrop.Self) {
/*     */       double retainedRatio = ConfigUtil.getDouble(MainConfig.get(), "balance/energyRetainedInStorageBlockDrops");
/*     */       if (retainedRatio > 0.0D) {
/*     */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(drop);
/*     */         nbt.func_74780_a("energy", this.energy.getEnergy() * retainedRatio);
/*     */       } 
/*     */     } 
/*     */     return drop;
/*     */   }
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/*     */     return EnumSet.of(UpgradableProperty.Augmentable, UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityAdvMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */