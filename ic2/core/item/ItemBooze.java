/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemBooze extends ItemIC2 {
/*     */   public String[] solidRatio;
/*     */   public String[] hopsRatio;
/*     */   public String[] timeRatioNames;
/*     */   public int[] baseDuration;
/*     */   public float[] baseIntensity;
/*     */   
/*  29 */   public ItemBooze() { super(ItemName.booze_mug);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.solidRatio = new String[] { "Watery ", "Clear ", "Lite ", "", "Strong ", "Thick ", "Stodge ", "X" };
/*  86 */     this.hopsRatio = new String[] { "Soup ", "Alcfree ", "White ", "", "Dark ", "Full ", "Black ", "X" };
/*  87 */     this.timeRatioNames = new String[] { "Brew", "Youngster", "Beer", "Ale", "Dragonblood", "Black Stuff" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     this.baseDuration = new int[] { 300, 600, 900, 1200, 1600, 2000, 2400 };
/* 157 */     this.baseIntensity = new float[] { 0.4F, 0.75F, 1.0F, 1.5F, 2.0F }; func_77625_d(1); func_77637_a(null); } @SideOnly(Side.CLIENT) public void registerModels(final ItemName name) { ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
/*     */           public ModelResourceLocation func_178113_a(ItemStack stack) { ItemBooze.BoozeMugType mugType; int meta = stack.func_77960_j(); int type = ItemBooze.getTypeOfValue(meta); if (type == 1) { int timeRatio = Math.min(ItemBooze.getTimeRatioOfBeerValue(meta), ItemBooze.this.timeRatioNames.length - 1); mugType = ItemBooze.BoozeMugType.values[timeRatio]; } else if (type == 2) { mugType = ItemBooze.BoozeMugType.rum; } else { return null; }  return ItemIC2.getModelLocation(name, mugType.getName()); }
/* 159 */         }); for (BoozeMugType type : BoozeMugType.values) { ModelBakery.registerItemVariants(this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, type.getName()) }); }  } public static float rumStackability = 2.0F;
/* 160 */   public String func_77653_i(ItemStack itemstack) { int meta = itemstack.func_77952_i(); int type = getTypeOfValue(meta); if (type == 1) { int timeRatio = Math.min(getTimeRatioOfBeerValue(meta), this.timeRatioNames.length - 1); if (timeRatio == this.timeRatioNames.length - 1) return this.timeRatioNames[timeRatio];  return this.solidRatio[getSolidRatioOfBeerValue(meta)] + this.hopsRatio[getHopsRatioOfBeerValue(meta)] + this.timeRatioNames[timeRatio]; }  if (type == 2) return "Rum";  return "Zero"; } public ItemStack func_77654_b(ItemStack stack, World world, EntityLivingBase living) { int meta = stack.func_77952_i(); int type = getTypeOfValue(meta); if (type == 0) return ItemName.mug.getItemStack(ItemMug.MugType.empty);  if (type == 1) { if (getTimeRatioOfBeerValue(meta) == 5) return drinkBlackStuff(living);  int solidRatio = getSolidRatioOfBeerValue(meta); int alc = getHopsRatioOfBeerValue(meta); int duration = this.baseDuration[solidRatio]; float intensity = this.baseIntensity[getTimeRatioOfBeerValue(meta)]; if (living instanceof EntityPlayer) ((EntityPlayer)living).func_71024_bL().func_75122_a(6 - alc, solidRatio * 0.15F);  int max = (int)(intensity * alc * 0.5F); PotionEffect slow = living.func_70660_b(MobEffects.field_76419_f); int level = -1; if (slow != null) level = slow.func_76458_c();  amplifyEffect(living, MobEffects.field_76419_f, max, intensity, duration); if (level > -1) { amplifyEffect(living, MobEffects.field_76420_g, max, intensity, duration); if (level > 0) { amplifyEffect(living, MobEffects.field_76421_d, max / 2, intensity, duration); if (level > 1) { amplifyEffect(living, MobEffects.field_76429_m, max - 1, intensity, duration); if (level > 2) { amplifyEffect(living, MobEffects.field_76431_k, 0, intensity, duration); if (level > 3) living.func_70690_d(new PotionEffect(MobEffects.field_76433_i, 1, (living.func_130014_f_()).field_73012_v.nextInt(3)));  }  }  }  }  }  if (type == 2) if (getProgressOfRumValue(meta) < 100) { drinkBlackStuff(living); } else { amplifyEffect(living, MobEffects.field_76426_n, 0, rumStackability, rumDuration); PotionEffect def = living.func_70660_b(MobEffects.field_76429_m); int level = -1; if (def != null) level = def.func_76458_c();  amplifyEffect(living, MobEffects.field_76429_m, 2, rumStackability, rumDuration); if (level >= 0) amplifyEffect(living, MobEffects.field_76440_q, 0, rumStackability, rumDuration);  if (level >= 1) amplifyEffect(living, MobEffects.field_76431_k, 0, rumStackability, rumDuration);  }   return ItemName.mug.getItemStack(ItemMug.MugType.empty); } public static int rumDuration = 600;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void amplifyEffect(EntityLivingBase living, Potion potion, int max, float intensity, int duration) {
/* 168 */     PotionEffect eff = living.func_70660_b(potion);
/*     */     
/* 170 */     if (eff == null) {
/* 171 */       living.func_70690_d(new PotionEffect(potion, duration, 0));
/*     */     } else {
/* 173 */       int currentDuration = eff.func_76459_b();
/* 174 */       int maxnewdur = (int)(duration * (1.0F + intensity * 2.0F) - currentDuration) / 2;
/* 175 */       if (maxnewdur < 0) maxnewdur = 0; 
/* 176 */       if (maxnewdur < duration) duration = maxnewdur;
/*     */       
/* 178 */       currentDuration += duration;
/*     */       
/* 180 */       int newamp = eff.func_76458_c();
/* 181 */       if (newamp < max) newamp++; 
/* 182 */       living.func_70690_d(new PotionEffect(potion, currentDuration, newamp));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack drinkBlackStuff(EntityLivingBase living) {
/* 190 */     switch ((living.func_130014_f_()).field_73012_v.nextInt(6)) { case 1:
/* 191 */         living.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 1200, 0)); break;
/* 192 */       case 2: living.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 2400, 0)); break;
/* 193 */       case 3: living.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 2400, 0)); break;
/* 194 */       case 4: living.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 200, 2)); break;
/* 195 */       case 5: living.func_70690_d(new PotionEffect(MobEffects.field_76433_i, 1, (living.func_130014_f_()).field_73012_v.nextInt(4)));
/*     */         break; }
/*     */     
/* 198 */     return ItemName.mug.getItemStack(ItemMug.MugType.empty);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemstack) {
/* 206 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack itemstack) {
/* 214 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 223 */     player.func_184598_c(hand);
/* 224 */     return new ActionResult(EnumActionResult.SUCCESS, StackUtil.get(player, hand));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTypeOfValue(int value) {
/* 229 */     return unpackValue(value, 0, 2);
/*     */   }
/*     */   
/*     */   public static int getAmountOfValue(int value) {
/* 233 */     if (getTypeOfValue(value) == 0) return 0; 
/* 234 */     return unpackValue(value, 2, 5) + 1;
/*     */   }
/*     */   
/*     */   public static int getSolidRatioOfBeerValue(int value) {
/* 238 */     return unpackValue(value, 7, 3);
/*     */   }
/*     */   
/*     */   public static int getHopsRatioOfBeerValue(int value) {
/* 242 */     return unpackValue(value, 10, 3);
/*     */   }
/*     */   
/*     */   public static int getTimeRatioOfBeerValue(int value) {
/* 246 */     return unpackValue(value, 13, 3);
/*     */   }
/*     */   
/*     */   public static int getProgressOfRumValue(int value) {
/* 250 */     return unpackValue(value, 7, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unpackValue(int value, int bitshift, int take) {
/* 261 */     value >>>= bitshift;
/* 262 */     int mask = (1 << take) - 1;
/* 263 */     return value & mask;
/*     */   }
/*     */   
/*     */   private enum BoozeMugType implements IIdProvider {
/* 267 */     beer_brew,
/* 268 */     beer_youngster,
/* 269 */     beer_beer,
/* 270 */     beer_ale,
/* 271 */     beer_dragon_blood,
/* 272 */     beer_black_stuff,
/* 273 */     rum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     public static final BoozeMugType[] values = values();
/*     */     
/*     */     public String getName() {
/*     */       return name();
/*     */     }
/*     */     
/*     */     public int getId() {
/*     */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemBooze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */