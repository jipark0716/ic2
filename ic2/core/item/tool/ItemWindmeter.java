/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ @NotClassic
/*     */ public class ItemWindmeter
/*     */   extends ItemElectricTool
/*     */ {
/*     */   public ItemWindmeter() {
/*  33 */     super(ItemName.wind_meter, 50);
/*     */     
/*  35 */     func_77625_d(1);
/*     */     
/*  37 */     this.maxCharge = 10000;
/*  38 */     this.transferLimit = 100;
/*  39 */     this.tier = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/*  46 */     tooltip.add(Localization.translate("ic2.wind_meter.tooltipA"));
/*  47 */     tooltip.add(Localization.translate("ic2.wind_meter.tooltipB"));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  52 */     if (world.field_72995_K || player.func_70093_af()) return EnumActionResult.PASS;
/*     */     
/*  54 */     ItemStack stack = StackUtil.get(player, hand);
/*  55 */     if (!ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
/*  56 */       return EnumActionResult.PASS;
/*     */     }
/*     */     
/*  59 */     TileEntity te = world.func_175625_s(pos);
/*     */     
/*  61 */     if (te instanceof TileEntityWindKineticGenerator) {
/*  62 */       TileEntityWindKineticGenerator windyTE = (TileEntityWindKineticGenerator)te;
/*     */       
/*  64 */       if (!windyTE.getActive()) {
/*  65 */         if (windyTE.hasRotor()) {
/*  66 */           IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.rotor.blocked"), new Object[0]);
/*     */         } else {
/*  68 */           IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.rotor.none"), new Object[0]);
/*     */         } 
/*     */         
/*  71 */         return EnumActionResult.FAIL;
/*     */       } 
/*     */       
/*  74 */       ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/*     */       
/*  76 */       if (windyTE.getObstructions() >= 0) {
/*  77 */         float displayWind = roundWind(windyTE.calcWindStrength());
/*     */         
/*  79 */         if (displayWind <= 0.0F) {
/*  80 */           IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.obstructed", new Object[] { Integer.valueOf(windyTE.getObstructions()) }), new Object[0]);
/*     */         } else {
/*  82 */           IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.effective", new Object[] { Float.valueOf(displayWind) }), new Object[0]);
/*     */         } 
/*     */       } else {
/*  85 */         IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.blocked", new Object[] { Integer.valueOf(windyTE.getRotorDiameter() * 3) }), new Object[0]);
/*     */       } 
/*     */       
/*  88 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/*  91 */     if (te instanceof TileEntityWindGenerator) {
/*  92 */       ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/*  93 */       TileEntityWindGenerator windyTE = (TileEntityWindGenerator)te;
/*     */       
/*  95 */       double obstructiveFactor = windyTE.getObstructions() / 567.0D;
/*  96 */       double wind = (obstructiveFactor >= 1.0D) ? 0.0D : ((WorldData.get(world)).windSim.getWindAt(pos.func_177956_o()) * (1.0D - obstructiveFactor));
/*  97 */       float displayWind = roundWind(wind);
/*     */       
/*  99 */       if (displayWind <= 0.0F) {
/* 100 */         IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.obstructed", new Object[] { Integer.valueOf(windyTE.getObstructions()) }), new Object[0]);
/*     */       } else {
/*     */         
/* 103 */         IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info.effective", new Object[] { Float.valueOf(displayWind) }), new Object[0]);
/*     */       } 
/*     */       
/* 106 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/* 109 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 114 */     ItemStack stack = StackUtil.get(player, hand);
/* 115 */     if (!IC2.platform.isSimulating()) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/* 117 */     if (!ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
/* 118 */       return new ActionResult(EnumActionResult.PASS, stack);
/*     */     }
/*     */     
/* 121 */     ElectricItem.manager.use(stack, this.operationEnergyCost, (EntityLivingBase)player);
/* 122 */     double windStrength = (WorldData.get(world)).windSim.getWindAt(player.field_70163_u);
/* 123 */     if (windStrength < 0.0D) windStrength = 0.0D; 
/* 124 */     IC2.platform.messagePlayer(player, Localization.translate("ic2.wind_meter.info", new Object[] { Float.valueOf(roundWind(windStrength)) }), new Object[0]);
/*     */     
/* 126 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */   
/*     */   private static float roundWind(double windStrength) {
/* 130 */     return (float)Math.round(windStrength * 100.0D) / 100.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemWindmeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */