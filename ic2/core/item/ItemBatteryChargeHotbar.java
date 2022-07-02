/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class ItemBatteryChargeHotbar
/*     */   extends ItemBattery
/*     */   implements IBoxable
/*     */ {
/*     */   public ItemBatteryChargeHotbar(ItemName name, double maxCharge, double transferLimit, int tier) {
/*  35 */     super(name, maxCharge, transferLimit, tier);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> list, ITooltipFlag advanced) {
/*  41 */     super.func_77624_a(stack, world, list, advanced);
/*     */     
/*  43 */     Mode mode = getMode(stack);
/*  44 */     list.add(getNameOfMode(mode));
/*     */     
/*  46 */     if ((Minecraft.func_71410_x()).field_71462_r instanceof ic2.core.item.tool.GuiToolbox) {
/*  47 */       list.add((mode.enabled ? (String)TextFormatting.RED : (String)TextFormatting.GREEN) + Localization.translate("ic2.tooltip.mode.boxable"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
/*  55 */     Mode mode = getMode(stack);
/*     */     
/*  57 */     if (entity instanceof net.minecraft.entity.player.EntityPlayerMP && world.func_82737_E() % 10L < getTier(stack) && mode.enabled) {
/*  58 */       EntityPlayer thePlayer = (EntityPlayer)entity;
/*  59 */       NonNullList<ItemStack> nonNullList = thePlayer.field_71071_by.field_70462_a;
/*  60 */       double limit = getTransferLimit(stack);
/*  61 */       int tier = getTier(stack);
/*     */       
/*  63 */       for (int i = 0; i < 9 && limit > 0.0D; i++) {
/*     */         
/*  65 */         ItemStack toCharge = nonNullList.get(i);
/*  66 */         if (!StackUtil.isEmpty(toCharge) && (mode != Mode.NOT_IN_HAND || i != thePlayer.field_71071_by.field_70461_c))
/*     */         {
/*  68 */           if (!(toCharge.func_77973_b() instanceof ItemBatteryChargeHotbar)) {
/*     */             
/*  70 */             double charge = ElectricItem.manager.charge(toCharge, limit, tier, false, true);
/*     */             
/*  72 */             charge = ElectricItem.manager.discharge(stack, charge, tier, true, false, false);
/*  73 */             ElectricItem.manager.charge(toCharge, charge, tier, true, false);
/*  74 */             limit -= charge;
/*     */           }  } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  81 */     ItemStack stack = StackUtil.get(player, hand);
/*  82 */     if (world.field_72995_K) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/*  84 */     Mode mode = getMode(stack);
/*  85 */     mode = Mode.values[(mode.ordinal() + 1) % Mode.values.length];
/*  86 */     setMode(stack, mode);
/*     */     
/*  88 */     IC2.platform.messagePlayer(player, Localization.translate("ic2.tooltip.mode", new Object[] { getNameOfMode(mode) }), new Object[0]);
/*     */     
/*  90 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */   
/*     */   private String getNameOfMode(Mode mode) {
/*  94 */     return Localization.translate("ic2.tooltip.mode." + mode.toString().toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   public void setMode(ItemStack stack, Mode mode) {
/*  98 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 100 */     nbt.func_74774_a("mode", (byte)mode.ordinal());
/*     */   }
/*     */   
/*     */   public Mode getMode(ItemStack stack) {
/* 104 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 105 */     if (!nbt.func_74764_b("mode")) return Mode.ENABLED;
/*     */     
/* 107 */     return getMode(nbt.func_74771_c("mode"));
/*     */   }
/*     */   
/*     */   private Mode getMode(int mode) {
/* 111 */     if (mode < 0 || mode >= Mode.values.length) mode = 0;
/*     */     
/* 113 */     return Mode.values[mode];
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 117 */     ENABLED(true),
/* 118 */     DISABLED(false),
/* 119 */     NOT_IN_HAND(true);
/*     */     
/*     */     private boolean enabled;
/*     */     
/*     */     Mode(boolean enabled) {
/*     */       this.enabled = enabled;
/*     */     }
/*     */     
/* 127 */     public static final Mode[] values = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 135 */     return (getMode(itemstack) == Mode.DISABLED);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemBatteryChargeHotbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */