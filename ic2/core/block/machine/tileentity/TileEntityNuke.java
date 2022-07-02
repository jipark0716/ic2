/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.invslot.InvSlotConsumable;
/*     */ import ic2.core.block.invslot.InvSlotConsumableItemStack;
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.NuclearResourceType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class TileEntityNuke extends TileEntityBridgeNuke implements IHasGui {
/*     */   public static Class<? extends TileEntityBridgeNuke> delegate() {
/*  31 */     return IC2.version.isClassic() ? (Class)TileEntityBridgeNuke.TileEntityClassicNuke.class : (Class)TileEntityNuke.class;
/*     */   }
/*     */   
/*     */   public int RadiationRange;
/*     */   
/*     */   public TileEntityNuke() {
/*  37 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  43 */       .insideSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "insideSlot", 1, new ItemStack[] { BlockName.resource.getItemStack((Enum)ResourceBlock.uranium_block), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.uranium_238), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.uranium_235), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.small_uranium_235), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.plutonium), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.small_plutonium) });
/*  44 */     this.outsideSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "outsideSlot", 1, new ItemStack[] { getBlockType().getItemStack((ITeBlock)TeBlock.itnt) });
/*     */   }
/*     */   public final InvSlotConsumable outsideSlot; public final InvSlotConsumable insideSlot;
/*     */   
/*     */   public int getRadiationRange() {
/*  49 */     return this.RadiationRange;
/*     */   }
/*     */   
/*     */   public void setRadiationRange(int range) {
/*  53 */     if (range != this.RadiationRange) this.RadiationRange = range;
/*     */   
/*     */   }
/*     */   
/*     */   public float getNukeExplosivePower() {
/*  58 */     if (this.outsideSlot.isEmpty()) return -1.0F;
/*     */ 
/*     */     
/*  61 */     int itntCount = StackUtil.getSize(this.outsideSlot.get());
/*     */     
/*  63 */     double ret = 5.0D * Math.pow(itntCount, 0.3333333333333333D);
/*     */     
/*  65 */     if (this.insideSlot.isEmpty()) {
/*  66 */       setRadiationRange(0);
/*     */     } else {
/*  68 */       ItemStack inside = this.insideSlot.get();
/*  69 */       int insideCount = StackUtil.getSize(inside);
/*     */       
/*  71 */       if (StackUtil.checkItemEquality(inside, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.uranium_238))) {
/*  72 */         setRadiationRange(itntCount);
/*  73 */       } else if (StackUtil.checkItemEquality(inside, BlockName.resource.getItemStack((Enum)ResourceBlock.uranium_block))) {
/*  74 */         setRadiationRange(itntCount * 6);
/*  75 */       } else if (StackUtil.checkItemEquality(inside, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.small_uranium_235))) {
/*  76 */         setRadiationRange(itntCount * 2);
/*     */         
/*  78 */         if (itntCount >= 64) {
/*  79 */           ret += 0.05555555555555555D * Math.pow(insideCount, 1.6D);
/*     */         }
/*  81 */       } else if (StackUtil.checkItemEquality(inside, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.uranium_235))) {
/*  82 */         setRadiationRange(itntCount * 2);
/*     */         
/*  84 */         if (itntCount >= 32) {
/*  85 */           ret += 0.5D * Math.pow(insideCount, 1.4D);
/*     */         }
/*  87 */       } else if (StackUtil.checkItemEquality(inside, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.small_plutonium))) {
/*  88 */         setRadiationRange(itntCount * 3);
/*     */         
/*  90 */         if (itntCount >= 32) {
/*  91 */           ret += 0.05555555555555555D * Math.pow(insideCount, 2.0D);
/*     */         }
/*  93 */       } else if (StackUtil.checkItemEquality(inside, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.plutonium))) {
/*  94 */         setRadiationRange(itntCount * 4);
/*     */         
/*  96 */         if (itntCount >= 16) {
/*  97 */           ret += 0.5D * Math.pow(insideCount, 1.8D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     ret = Math.min(ret, ConfigUtil.getFloat(MainConfig.get(), "protection/nukeExplosionPowerLimit"));
/*     */     
/* 104 */     return (float)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityNuke> getGuiContainer(EntityPlayer player) {
/* 109 */     return (ContainerBase<TileEntityNuke>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 115 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onIgnite(EntityLivingBase igniter) {
/* 125 */     super.onIgnite(igniter);
/*     */ 
/*     */     
/* 128 */     this.outsideSlot.clear();
/* 129 */     this.insideSlot.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityNuke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */