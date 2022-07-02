/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.block.TileEntityBarrel;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemMug
/*     */   extends ItemMulti<ItemMug.MugType>
/*     */ {
/*     */   public ItemMug() {
/*  27 */     super(ItemName.mug, MugType.class);
/*     */     
/*  29 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  38 */     ItemStack stack = StackUtil.get(player, hand);
/*  39 */     MugType type = getType(stack);
/*     */     
/*  41 */     if (type == MugType.empty) {
/*  42 */       if (world.field_72995_K) return EnumActionResult.FAIL; 
/*  43 */       TileEntity te = world.func_175625_s(pos);
/*  44 */       if (!(te instanceof TileEntityBarrel)) return EnumActionResult.PASS;
/*     */       
/*  46 */       TileEntityBarrel barrel = (TileEntityBarrel)te;
/*  47 */       if (!barrel.getActive() || barrel.getFacing() != side) return EnumActionResult.PASS;
/*     */       
/*  49 */       int value = barrel.calculateMetaValue();
/*     */       
/*  51 */       if (barrel.drainLiquid(1)) {
/*  52 */         ItemStack is = new ItemStack(ItemName.booze_mug.getInstance(), 1, value);
/*     */         
/*  54 */         stack = StackUtil.decSize(stack);
/*     */         
/*  56 */         if (!StackUtil.isEmpty(stack)) {
/*  57 */           if (!player.field_71071_by.func_70441_a(is)) player.func_71019_a(is, false); 
/*     */         } else {
/*  59 */           StackUtil.set(player, hand, is);
/*     */         } 
/*     */         
/*  62 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/*     */     int maxAmplifier, extraDuration;
/*  74 */     if (!(entityLiving instanceof EntityPlayer)) return stack; 
/*  75 */     EntityPlayer player = (EntityPlayer)entityLiving;
/*  76 */     MugType type = getType(stack);
/*  77 */     if (type == null || type == MugType.empty) return stack;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     switch (type) {
/*     */       case cold_coffee:
/*  84 */         maxAmplifier = 1;
/*  85 */         extraDuration = 600;
/*     */         break;
/*     */       case dark_coffee:
/*  88 */         maxAmplifier = 5;
/*  89 */         extraDuration = 1200;
/*     */         break;
/*     */       case coffee:
/*  92 */         maxAmplifier = 6;
/*  93 */         extraDuration = 1200;
/*     */         break;
/*     */       default:
/*  96 */         throw new IllegalStateException("unexpected type: " + type);
/*     */     } 
/*     */     
/*  99 */     int highest = 0;
/* 100 */     int x = amplifyEffect(player, MobEffects.field_76424_c, maxAmplifier, extraDuration);
/* 101 */     if (x > highest) highest = x; 
/* 102 */     x = amplifyEffect(player, MobEffects.field_76422_e, maxAmplifier, extraDuration);
/* 103 */     if (x > highest) highest = x; 
/* 104 */     if (type == MugType.coffee) highest -= 2;
/*     */     
/* 106 */     if (highest >= 3) {
/*     */       
/* 108 */       player.func_70690_d(new PotionEffect(MobEffects.field_76431_k, (highest - 2) * 200, 0));
/* 109 */       if (highest >= 4) {
/* 110 */         player.func_70690_d(new PotionEffect(MobEffects.field_76433_i, 1, highest - 3));
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return getItemStack(MugType.empty);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int amplifyEffect(EntityPlayer player, Potion potion, int maxAmplifier, int extraDuration) {
/* 122 */     PotionEffect eff = player.func_70660_b(potion);
/*     */     
/* 124 */     if (eff != null) {
/*     */       
/* 126 */       int newAmp = eff.func_76458_c();
/* 127 */       int newDur = eff.func_76459_b();
/* 128 */       if (newAmp < maxAmplifier) newAmp++; 
/* 129 */       newDur += extraDuration;
/* 130 */       assert potion == eff.func_188419_a();
/* 131 */       player.func_70690_d(new PotionEffect(potion, newDur, newAmp));
/*     */       
/* 133 */       return newAmp;
/*     */     } 
/*     */     
/* 136 */     player.func_70690_d(new PotionEffect(potion, 300, 0));
/* 137 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack stack) {
/* 146 */     MugType type = getType(stack);
/* 147 */     if (type == null || type == MugType.empty) return 0;
/*     */     
/* 149 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack stack) {
/* 157 */     MugType type = getType(stack);
/* 158 */     if (type == null || type == MugType.empty) return EnumAction.NONE;
/*     */     
/* 160 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 169 */     MugType type = getType(StackUtil.get(player, hand));
/*     */     
/* 171 */     if (type != null && type != MugType.empty) {
/* 172 */       player.func_184598_c(hand);
/*     */     }
/*     */     
/* 175 */     return super.func_77659_a(world, player, hand);
/*     */   }
/*     */   
/*     */   public enum MugType implements IIdProvider {
/* 179 */     empty,
/* 180 */     cold_coffee,
/* 181 */     dark_coffee,
/* 182 */     coffee;
/*     */ 
/*     */     
/*     */     public String getName() {
/* 186 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 191 */       return ordinal();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemMug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */