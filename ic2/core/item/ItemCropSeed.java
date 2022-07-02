/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.crops.ICropSeed;
/*     */ import ic2.core.crop.TileEntityCrop;
/*     */ import ic2.core.crop.cropcard.GenericCropCard;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
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
/*     */ 
/*     */ 
/*     */ public class ItemCropSeed
/*     */   extends ItemIC2
/*     */   implements ICropSeed
/*     */ {
/*     */   public ItemCropSeed() {
/*  40 */     super(ItemName.crop_seed_bag);
/*     */     
/*  42 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemstack) {
/*  47 */     if (itemstack == null) return "ic2.crop.unknown";
/*     */     
/*  49 */     CropCard cropCard = Crops.instance.getCropCard(itemstack);
/*  50 */     int level = getScannedFromStack(itemstack);
/*     */     
/*  52 */     if (level == 0)
/*  53 */       return "ic2.crop.unknown"; 
/*  54 */     if (level < 0 || cropCard == null) {
/*  55 */       return "ic2.crop.invalid";
/*     */     }
/*  57 */     return cropCard.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack stack) {
/*  63 */     CropCard crop = Crops.instance.getCropCard(stack);
/*  64 */     return Localization.translate((crop == null) ? "ic2.crop.seeds" : crop.getSeedType(), new Object[] { super.func_77653_i(stack) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77645_m() {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> info, ITooltipFlag debugTooltips) {
/*  87 */     if (getScannedFromStack(stack) >= 4) {
/*  88 */       info.add("§2Gr§7 " + getGrowthFromStack(stack));
/*  89 */       info.add("§6Ga§7 " + getGainFromStack(stack));
/*  90 */       info.add("§3Re§7 " + getResistanceFromStack(stack));
/*     */     } 
/*     */     
/*  93 */     if (getScannedFromStack(stack) >= 1) {
/*  94 */       CropCard cropCard = getCropFromStack(stack);
/*  95 */       if (cropCard instanceof GenericCropCard) {
/*  96 */         info.addAll(((GenericCropCard)cropCard).getInformation());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float a, float b, float c) {
/* 107 */     TileEntity te = world.func_175625_s(pos);
/*     */     
/* 109 */     if (te instanceof TileEntityCrop) {
/* 110 */       TileEntityCrop crop = (TileEntityCrop)te;
/* 111 */       ItemStack stack = StackUtil.get(player, hand);
/*     */       
/* 113 */       if (crop.tryPlantIn(Crops.instance.getCropCard(stack), 1, getGrowthFromStack(stack), getGainFromStack(stack), getResistanceFromStack(stack), getScannedFromStack(stack))) {
/* 114 */         if (!player.field_71075_bZ.field_75098_d) {
/* 115 */           player.field_71071_by.field_70462_a.set(player.field_71071_by.field_70461_c, StackUtil.emptyStack);
/*     */         }
/* 117 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tabs, NonNullList<ItemStack> items) {
/* 126 */     if (!func_194125_a(tabs)) {
/*     */       return;
/*     */     }
/* 129 */     for (CropCard crop : Crops.instance.getCrops()) {
/* 130 */       items.add(generateItemStackFromValues(crop, 1, 1, 1, 4));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack generateItemStackFromValues(CropCard crop, int statGrowth, int statGain, int statResistance, int scan) {
/* 139 */     ItemStack stack = ItemName.crop_seed_bag.getItemStack();
/* 140 */     NBTTagCompound tag = new NBTTagCompound();
/*     */     
/* 142 */     tag.func_74778_a("owner", crop.getOwner());
/* 143 */     tag.func_74778_a("id", crop.getId());
/* 144 */     tag.func_74774_a("growth", (byte)statGrowth);
/* 145 */     tag.func_74774_a("gain", (byte)statGain);
/* 146 */     tag.func_74774_a("resistance", (byte)statResistance);
/* 147 */     tag.func_74774_a("scan", (byte)scan);
/*     */     
/* 149 */     stack.func_77982_d(tag);
/*     */     
/* 151 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CropCard getCropFromStack(ItemStack is) {
/* 158 */     NBTTagCompound nbt = is.func_77978_p();
/*     */     
/* 160 */     if (nbt == null || 
/* 161 */       !nbt.func_150297_b("owner", 8) || 
/* 162 */       !nbt.func_150297_b("id", 8)) {
/* 163 */       return null;
/*     */     }
/*     */     
/* 166 */     String owner = nbt.func_74779_i("owner");
/* 167 */     String id = nbt.func_74779_i("id");
/*     */     
/* 169 */     return Crops.instance.getCropCard(owner, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCropFromStack(ItemStack is, CropCard crop) {
/* 174 */     if (is.func_77978_p() == null)
/*     */       return; 
/* 176 */     is.func_77978_p().func_74778_a("owner", crop.getOwner());
/* 177 */     is.func_77978_p().func_74778_a("id", crop.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGrowthFromStack(ItemStack is) {
/* 183 */     if (is.func_77978_p() == null) return -1; 
/* 184 */     return is.func_77978_p().func_74771_c("growth");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGrowthFromStack(ItemStack is, int value) {
/* 189 */     if (is.func_77978_p() == null)
/* 190 */       return;  is.func_77978_p().func_74774_a("growth", (byte)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGainFromStack(ItemStack is) {
/* 196 */     if (is.func_77978_p() == null) return -1; 
/* 197 */     return is.func_77978_p().func_74771_c("gain");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGainFromStack(ItemStack is, int value) {
/* 202 */     if (is.func_77978_p() == null)
/* 203 */       return;  is.func_77978_p().func_74774_a("gain", (byte)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResistanceFromStack(ItemStack is) {
/* 209 */     if (is.func_77978_p() == null) return -1; 
/* 210 */     return is.func_77978_p().func_74771_c("resistance");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResistanceFromStack(ItemStack is, int value) {
/* 215 */     if (is.func_77978_p() == null)
/* 216 */       return;  is.func_77978_p().func_74774_a("resistance", (byte)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScannedFromStack(ItemStack is) {
/* 222 */     if (is.func_77978_p() == null) return -1; 
/* 223 */     return is.func_77978_p().func_74771_c("scan");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScannedFromStack(ItemStack is, int value) {
/* 228 */     if (is.func_77978_p() == null)
/* 229 */       return;  is.func_77978_p().func_74774_a("scan", (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementScannedFromStack(ItemStack is) {
/* 234 */     if (is.func_77978_p() == null)
/*     */       return; 
/* 236 */     is.func_77978_p().func_74774_a("scan", (byte)(getScannedFromStack(is) + 1));
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemCropSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */