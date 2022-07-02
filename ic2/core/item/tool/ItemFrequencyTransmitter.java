/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class ItemFrequencyTransmitter
/*     */   extends ItemIC2
/*     */ {
/*     */   private static final String targetSetNbt = "targetSet";
/*     */   private static final String targetJustSetNbt = "targetJustSet";
/*     */   private static final String targetXNbt = "targetX";
/*     */   private static final String targetYNbt = "targetY";
/*     */   private static final String targetZNbt = "targetZ";
/*     */   
/*     */   public ItemFrequencyTransmitter() {
/*  36 */     super(ItemName.frequency_transmitter);
/*     */     
/*  38 */     this.field_77777_bU = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  43 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/*  45 */     if (IC2.platform.isSimulating()) {
/*  46 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*  47 */       boolean hadJustSet = nbtData.func_74767_n("targetJustSet");
/*     */       
/*  49 */       if (nbtData.func_74767_n("targetSet") && !hadJustSet) {
/*  50 */         nbtData.func_74757_a("targetSet", false);
/*     */         
/*  52 */         IC2.platform.messagePlayer(player, "Frequency Transmitter unlinked", new Object[0]);
/*     */       } 
/*  54 */       if (hadJustSet) {
/*  55 */         nbtData.func_74757_a("targetJustSet", false);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  64 */     if (world.field_72995_K) return EnumActionResult.PASS;
/*     */     
/*  66 */     TileEntity te = world.func_175625_s(pos);
/*  67 */     if (!(te instanceof TileEntityTeleporter)) return EnumActionResult.PASS;
/*     */     
/*  69 */     TileEntityTeleporter tp = (TileEntityTeleporter)te;
/*  70 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(StackUtil.get(player, hand));
/*  71 */     boolean targetSet = nbtData.func_74767_n("targetSet");
/*  72 */     boolean justSetTarget = true;
/*  73 */     BlockPos target = new BlockPos(nbtData.func_74762_e("targetX"), nbtData.func_74762_e("targetY"), nbtData.func_74762_e("targetZ"));
/*     */     
/*  75 */     if (!targetSet) {
/*  76 */       targetSet = true;
/*  77 */       target = tp.func_174877_v();
/*     */       
/*  79 */       IC2.platform.messagePlayer(player, "Frequency Transmitter linked to Teleporter.", new Object[0]);
/*  80 */     } else if (tp.func_174877_v().equals(target)) {
/*  81 */       IC2.platform.messagePlayer(player, "Can't link Teleporter to itself.", new Object[0]);
/*  82 */     } else if (tp.hasTarget() && tp.getTarget().equals(target)) {
/*  83 */       IC2.platform.messagePlayer(player, "Teleportation link unchanged.", new Object[0]);
/*     */     } else {
/*  85 */       TileEntity targetTe = world.func_175625_s(target);
/*     */       
/*  87 */       if (targetTe instanceof TileEntityTeleporter) {
/*  88 */         tp.setTarget(target);
/*  89 */         ((TileEntityTeleporter)targetTe).setTarget(pos);
/*     */         
/*  91 */         IC2.platform.messagePlayer(player, "Teleportation link established.", new Object[0]);
/*     */       } else {
/*  93 */         targetSet = justSetTarget = false;
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     nbtData.func_74757_a("targetSet", targetSet);
/*  98 */     nbtData.func_74757_a("targetJustSet", justSetTarget);
/*  99 */     nbtData.func_74768_a("targetX", target.func_177958_n());
/* 100 */     nbtData.func_74768_a("targetY", target.func_177956_o());
/* 101 */     nbtData.func_74768_a("targetZ", target.func_177952_p());
/*     */     
/* 103 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 109 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 111 */     if (nbtData.func_74767_n("targetSet")) {
/* 112 */       tooltip.add(Localization.translate("ic2.frequency_transmitter.tooltip.target", new Object[] { Integer.valueOf(nbtData.func_74762_e("targetX")), Integer.valueOf(nbtData.func_74762_e("targetY")), Integer.valueOf(nbtData.func_74762_e("targetZ")) }));
/*     */     } else {
/* 114 */       tooltip.add(Localization.translate("ic2.frequency_transmitter.tooltip.blank"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemFrequencyTransmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */