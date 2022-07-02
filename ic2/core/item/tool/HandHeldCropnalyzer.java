/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.crops.ICropSeed;
/*     */ import ic2.api.info.Info;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IWorldTickCallback;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class HandHeldCropnalyzer
/*     */   extends HandHeldInventory
/*     */   implements IWorldTickCallback
/*     */ {
/*     */   public HandHeldCropnalyzer(EntityPlayer player, ItemStack stack) {
/*  27 */     super(player, stack, 3);
/*     */     
/*  29 */     if (IC2.platform.isSimulating()) {
/*  30 */       IC2.tickHandler.requestContinuousWorldTick(player.func_130014_f_(), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/*  36 */     if (func_145818_k_()) {
/*  37 */       return this.containerStack.func_77978_p().func_74779_i("display");
/*     */     }
/*     */     
/*  40 */     return "Cropnalyzer";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  45 */     return StackUtil.getOrCreateNbtData(this.containerStack).func_74764_b("display");
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<HandHeldCropnalyzer> getGuiContainer(EntityPlayer player) {
/*  50 */     return (ContainerBase<HandHeldCropnalyzer>)new ContainerCropnalyzer(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*  56 */     return (GuiScreen)new GuiCropnalyzer(new ContainerCropnalyzer(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {
/*  61 */     super.onGuiClosed(player);
/*     */     
/*  63 */     if (IC2.platform.isSimulating()) {
/*  64 */       IC2.tickHandler.removeContinuousWorldTick(player.func_130014_f_(), this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(World world) {
/*  73 */     ItemStack battery = this.inventory[2];
/*  74 */     ItemStack input = this.inventory[0];
/*  75 */     ItemStack output = this.inventory[1];
/*     */     
/*  77 */     if (!StackUtil.isEmpty(battery)) {
/*  78 */       double need = ElectricItem.manager.charge(this.containerStack, Double.POSITIVE_INFINITY, 2147483647, true, true);
/*     */       
/*  80 */       if (need > 0.0D) {
/*  81 */         double get = Info.itemInfo.getEnergyValue(battery);
/*  82 */         if (get > 0.0D) {
/*  83 */           this.inventory[2] = battery = StackUtil.decSize(battery);
/*     */         } else {
/*  85 */           get = ElectricItem.manager.discharge(battery, need, 2147483647, false, true, false);
/*     */         } 
/*  87 */         if (get > 0.0D) ElectricItem.manager.charge(this.containerStack, get, 3, true, false);
/*     */       
/*     */       } 
/*     */     } 
/*  91 */     if (StackUtil.isEmpty(output) && !StackUtil.isEmpty(input) && input.func_77973_b() instanceof ICropSeed) {
/*  92 */       int level = ((ICropSeed)input.func_77973_b()).getScannedFromStack(this.inventory[0]);
/*     */       
/*  94 */       if (level < 4) {
/*  95 */         double ned = energyForLevel(level);
/*  96 */         double got = ElectricItem.manager.discharge(this.containerStack, ned, 2, true, false, false);
/*     */         
/*  98 */         if (!Util.isSimilar(got, ned))
/*     */           return; 
/* 100 */         ((ICropSeed)input.func_77973_b()).incrementScannedFromStack(this.inventory[0]);
/*     */       } 
/*     */       
/* 103 */       this.inventory[1] = input;
/* 104 */       this.inventory[0] = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int energyForLevel(int i) {
/* 113 */     switch (i) { default:
/* 114 */         return 10;
/* 115 */       case 1: return 90;
/* 116 */       case 2: return 900;
/* 117 */       case 3: break; }  return 9000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard crop() {
/* 126 */     return Crops.instance.getCropCard(this.inventory[1]);
/*     */   }
/*     */   
/*     */   public int getScannedLevel() {
/* 130 */     ItemStack output = this.inventory[1];
/*     */     
/* 132 */     if (output == null || !(output.func_77973_b() instanceof ICropSeed)) return -1; 
/* 133 */     return ((ICropSeed)output.func_77973_b()).getScannedFromStack(output);
/*     */   }
/*     */   
/*     */   public String getSeedName() {
/* 137 */     return Localization.translate(crop().getUnlocalizedName());
/*     */   }
/*     */   
/*     */   public String getSeedTier() {
/* 141 */     switch (crop().getProperties().getTier()) { default:
/* 142 */         return "0";
/* 143 */       case 1: return "I";
/* 144 */       case 2: return "II";
/* 145 */       case 3: return "III";
/* 146 */       case 4: return "IV";
/* 147 */       case 5: return "V";
/* 148 */       case 6: return "VI";
/* 149 */       case 7: return "VII";
/* 150 */       case 8: return "VIII";
/* 151 */       case 9: return "IX";
/* 152 */       case 10: return "X";
/* 153 */       case 11: return "XI";
/* 154 */       case 12: return "XII";
/* 155 */       case 13: return "XIII";
/* 156 */       case 14: return "XIV";
/* 157 */       case 15: return "XV";
/* 158 */       case 16: break; }  return "XVI";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeedDiscovered() {
/* 163 */     return crop().getDiscoveredBy();
/*     */   }
/*     */   
/*     */   public String getSeedDesc(int i) {
/* 167 */     return crop().desc(i);
/*     */   }
/*     */   
/*     */   public int getSeedGrowth() {
/* 171 */     return ((ICropSeed)this.inventory[1].func_77973_b()).getGrowthFromStack(this.inventory[1]);
/*     */   }
/*     */   
/*     */   public int getSeedGain() {
/* 175 */     return ((ICropSeed)this.inventory[1].func_77973_b()).getGainFromStack(this.inventory[1]);
/*     */   }
/*     */   
/*     */   public int getSeedResistence() {
/* 179 */     return ((ICropSeed)this.inventory[1].func_77973_b()).getResistanceFromStack(this.inventory[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */