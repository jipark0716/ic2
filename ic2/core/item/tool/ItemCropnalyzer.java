/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.crop.TileEntityCrop;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.item.IHandHeldInventory;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
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
/*     */ public class ItemCropnalyzer
/*     */   extends BaseElectricItem
/*     */   implements IHandHeldInventory
/*     */ {
/*     */   public ItemCropnalyzer() {
/*  39 */     super(ItemName.cropnalyzer, 100000.0D, 128.0D, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  44 */     ItemStack stack = StackUtil.get(player, hand);
/*  45 */     if (IC2.platform.isSimulating()) {
/*  46 */       IC2.platform.launchGui(player, getInventory(player, stack));
/*     */     }
/*     */     
/*  49 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*  55 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/*  60 */     return new HandHeldCropnalyzer(player, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/*  69 */     if (player instanceof EntityPlayerMP && 
/*  70 */       !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerCropnalyzer) {
/*     */       
/*  72 */       HandHeldCropnalyzer cropnalyzer = (HandHeldCropnalyzer)((ContainerCropnalyzer)player.field_71070_bA).base;
/*     */       
/*  74 */       if (cropnalyzer.isThisContainer(stack)) {
/*  75 */         cropnalyzer.saveAsThrown(stack);
/*  76 */         ((EntityPlayerMP)player).func_71053_j();
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  85 */     if (world.field_72995_K || player.func_70093_af()) return EnumActionResult.PASS;
/*     */     
/*  87 */     TileEntity te = world.func_175625_s(pos);
/*     */     
/*  89 */     if (te instanceof TileEntityCrop) {
/*  90 */       TileEntityCrop crop = (TileEntityCrop)te;
/*  91 */       if (crop.getCrop() == null) return EnumActionResult.PASS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       if (ElectricItem.manager.discharge(StackUtil.get(player, hand), HandHeldCropnalyzer.energyForLevel(2), 3, true, false, false) > 0.0D) {
/* 102 */         CropCard plant = crop.getCrop();
/*     */         
/* 104 */         IC2.platform.messagePlayer(player, "Crop name: " + Localization.translate(plant.getUnlocalizedName()) + " (by " + plant.getDiscoveredBy() + ')', new Object[0]);
/* 105 */         IC2.platform.messagePlayer(player, "Crop size: " + crop.getCurrentSize() + '/' + plant.getMaxSize(), new Object[0]);
/* 106 */         IC2.platform.messagePlayer(player, "Nutrient storage: " + crop.getStorageNutrients() + "/100", new Object[0]);
/* 107 */         IC2.platform.messagePlayer(player, "Water storage: " + crop.getStorageWater() + "/200", new Object[0]);
/* 108 */         IC2.platform.messagePlayer(player, "Weed-Ex storage: " + crop.getStorageWeedEX() + "/100", new Object[0]);
/* 109 */         IC2.platform.messagePlayer(player, "Growth points: " + crop.getGrowthPoints() + '/' + plant.getGrowthDuration((ICropTile)crop), new Object[0]);
/*     */         
/* 111 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return EnumActionResult.PASS;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */