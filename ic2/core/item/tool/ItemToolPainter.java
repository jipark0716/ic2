/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.state.EnumProperty;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.Ic2Color;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*     */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class ItemToolPainter
/*     */   extends ItemIC2
/*     */   implements IMultiItem<Ic2Color>, IBoxable
/*     */ {
/*     */   public ItemToolPainter() {
/*  61 */     super(ItemName.painter);
/*     */     
/*  63 */     func_77656_e(31);
/*  64 */     func_77625_d(1);
/*  65 */     func_77627_a(true);
/*     */     
/*  67 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(final ItemName name) {
/*  73 */     ModelLoader.setCustomMeshDefinition((Item)this, new ItemMeshDefinition()
/*     */         {
/*     */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/*  76 */             Ic2Color color = ItemToolPainter.this.getColor(stack);
/*     */             
/*  78 */             return ItemIC2.getModelLocation(name, (color != null) ? color.getName() : null);
/*     */           }
/*     */         });
/*     */     
/*  82 */     ModelBakery.registerItemVariants((Item)this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, null) });
/*     */     
/*  84 */     for (Ic2Color type : typeProperty.getAllowedValues()) {
/*  85 */       ModelBakery.registerItemVariants((Item)this, new ResourceLocation[] { (ResourceLocation)getModelLocation(name, type.getName()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamage(ItemStack stack) {
/* 102 */     int rawDamage = super.getDamage(stack);
/* 103 */     if (rawDamage == 0) return 0;
/*     */     
/* 105 */     return (rawDamage - 1) / Ic2Color.values.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/* 110 */     return (getDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/* 115 */     int oldRawDamage = super.getDamage(stack);
/* 116 */     if (oldRawDamage == 0) {
/*     */       return;
/*     */     }
/* 119 */     int oldDamage = getDamage(stack);
/* 120 */     int newDamage = Util.limit(damage, 0, 32);
/*     */     
/* 122 */     super.setDamage(stack, oldRawDamage + (newDamage - oldDamage) * Ic2Color.values.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata(ItemStack stack) {
/* 127 */     int rawDamage = super.getDamage(stack);
/* 128 */     if (rawDamage == 0 || rawDamage == 32767) return rawDamage;
/*     */     
/* 130 */     return (rawDamage - 1) % Ic2Color.values.length + 1;
/*     */   }
/*     */   
/*     */   public Ic2Color getColor(ItemStack stack) {
/* 134 */     int meta = getMetadata(stack);
/* 135 */     if (meta == 0) return null;
/*     */     
/* 137 */     return Ic2Color.values[meta - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 145 */     ItemStack stack = StackUtil.get(player, hand);
/* 146 */     Ic2Color color = getColor(stack);
/* 147 */     if (color == null) return EnumActionResult.PASS;
/*     */     
/* 149 */     IBlockState state = world.func_180495_p(pos);
/* 150 */     Block block = state.func_177230_c();
/*     */     
/* 152 */     if (block.recolorBlock(world, pos, side, color.mcColor) || 
/* 153 */       colorBlock(world, pos, block, state, color.mcColor)) {
/* 154 */       damagePainter(player, hand, color);
/*     */       
/* 156 */       if (world.field_72995_K) {
/* 157 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Painter.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */       }
/*     */       
/* 160 */       return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/* 163 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean colorBlock(World world, BlockPos pos, Block block, IBlockState state, EnumDyeColor newColor) {
/* 168 */     for (UnmodifiableIterator<IProperty> unmodifiableIterator = state.func_177228_b().keySet().iterator(); unmodifiableIterator.hasNext(); ) { IProperty<?> property = unmodifiableIterator.next();
/* 169 */       if (property.func_177699_b() == EnumDyeColor.class) {
/*     */         
/* 171 */         IProperty<EnumDyeColor> typedProperty = (IProperty)property;
/*     */         
/* 173 */         EnumDyeColor oldColor = (EnumDyeColor)state.func_177229_b(typedProperty);
/* 174 */         if (oldColor == newColor || !typedProperty.func_177700_c().contains(newColor)) return false;
/*     */         
/* 176 */         world.func_175656_a(pos, state.func_177226_a(typedProperty, (Comparable)newColor));
/*     */         
/* 178 */         return true;
/*     */       }  }
/*     */ 
/*     */     
/* 182 */     if (block == Blocks.field_150405_ch) {
/* 183 */       world.func_175656_a(pos, Blocks.field_150406_ce.func_176223_P().func_177226_a((IProperty)BlockColored.field_176581_a, (Comparable)newColor));
/* 184 */       return true;
/* 185 */     }  if (block == Blocks.field_150359_w) {
/* 186 */       world.func_175656_a(pos, Blocks.field_150399_cn.func_176223_P().func_177226_a((IProperty)BlockStainedGlass.field_176547_a, (Comparable)newColor));
/* 187 */       return true;
/* 188 */     }  if (block == Blocks.field_150410_aZ) {
/* 189 */       world.func_175656_a(pos, Blocks.field_150397_co.func_176223_P().func_177226_a((IProperty)BlockStainedGlassPane.field_176245_a, (Comparable)newColor));
/* 190 */       return true;
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
/* 198 */     EntityPlayer player = event.getEntityPlayer();
/* 199 */     if ((player.func_130014_f_()).field_72995_K)
/*     */       return; 
/* 201 */     Entity entity = event.getEntity();
/* 202 */     ItemStack stack = player.func_184607_cu();
/* 203 */     if (StackUtil.isEmpty(stack) || stack.func_77973_b() != this)
/*     */       return; 
/* 205 */     Ic2Color color = getColor(stack);
/* 206 */     if (color == null)
/*     */       return; 
/* 208 */     if (entity instanceof EntitySheep) {
/* 209 */       EntitySheep sheep = (EntitySheep)entity;
/*     */       
/* 211 */       if (sheep.func_175509_cj() != color.mcColor) {
/* 212 */         ((EntitySheep)entity).func_175512_b(color.mcColor);
/* 213 */         damagePainter(player, event.getHand(), color);
/* 214 */         event.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/* 221 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/* 223 */     if (!world.field_72995_K && IC2.keyboard.isModeSwitchKeyDown(player)) {
/* 224 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */       
/* 226 */       boolean newValue = !nbtData.func_74767_n("autoRefill");
/*     */       
/* 228 */       nbtData.func_74757_a("autoRefill", newValue);
/*     */       
/* 230 */       if (newValue) {
/* 231 */         IC2.platform.messagePlayer(player, "Painter automatic refill mode enabled", new Object[0]);
/*     */       } else {
/* 233 */         IC2.platform.messagePlayer(player, "Painter automatic refill mode disabled", new Object[0]);
/*     */       } 
/* 235 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */     } 
/*     */     
/* 238 */     return new ActionResult(EnumActionResult.PASS, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/* 243 */     Ic2Color color = getColor(stack);
/*     */     
/* 245 */     if (color == null) {
/* 246 */       return func_77658_a();
/*     */     }
/* 248 */     return func_77658_a() + "." + color.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 254 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/* 257 */     subItems.add(getItemStackUnchecked((Ic2Color)null));
/*     */     
/* 259 */     for (Ic2Color type : typeProperty.getAllowedValues()) {
/* 260 */       subItems.add(getItemStackUnchecked(type));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Ic2Color> getAllTypes() {
/* 266 */     return EnumSet.allOf(Ic2Color.class);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 272 */     Ic2Color color = getColor(stack);
/* 273 */     if (color == null)
/*     */       return; 
/* 275 */     ItemStack dyeStack = new ItemStack(Items.field_151100_aR, 1, color.mcColor.func_176767_b());
/*     */     
/* 277 */     tooltip.add(Localization.translate(Items.field_151100_aR.func_77667_c(dyeStack) + ".name"));
/*     */   }
/*     */   
/*     */   public void damagePainter(EntityPlayer player, EnumHand hand, Ic2Color color) {
/* 281 */     assert color != null;
/*     */ 
/*     */     
/* 284 */     ItemStack stack = StackUtil.get(player, hand);
/*     */     
/* 286 */     if (stack.func_77952_i() >= stack.func_77958_k()) {
/* 287 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */       
/* 289 */       if (nbtData.func_74767_n("autoRefill") && 
/* 290 */         StackUtil.consumeFromPlayerInventory(player, StackUtil.oreDict(color.oreDictDyeName), 1, false)) {
/* 291 */         setDamage(stack, 0);
/*     */       } else {
/* 293 */         super.setDamage(stack, 0);
/*     */       } 
/*     */     } else {
/* 296 */       stack.func_77972_a(1, (EntityLivingBase)player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(Ic2Color type) {
/* 304 */     if (type != null && !typeProperty.getAllowedValues().contains(type)) throw new IllegalArgumentException("invalid property value " + type + " for property " + typeProperty);
/*     */     
/* 306 */     return getItemStackUnchecked(type);
/*     */   }
/*     */   
/*     */   private ItemStack getItemStackUnchecked(Ic2Color type) {
/* 310 */     if (type == null) {
/* 311 */       return new ItemStack((Item)this);
/*     */     }
/* 313 */     return new ItemStack((Item)this, 1, 1 + type.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(String variant) {
/*     */     Ic2Color type;
/* 321 */     if (variant != null && !variant.isEmpty()) {
/* 322 */       type = (Ic2Color)typeProperty.getValue(variant);
/* 323 */       if (type == null) throw new IllegalArgumentException("invalid variant " + variant + " for " + this); 
/*     */     } else {
/* 325 */       type = null;
/*     */     } 
/*     */     
/* 328 */     return getItemStackUnchecked(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/* 333 */     if (stack == null) throw new NullPointerException("null stack"); 
/* 334 */     if (stack.func_77973_b() != this) throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this);
/*     */     
/* 336 */     Ic2Color color = getColor(stack);
/* 337 */     if (color == null) return null;
/*     */     
/* 339 */     return color.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 344 */     return true;
/*     */   }
/*     */   
/* 347 */   private static final EnumProperty<Ic2Color> typeProperty = new EnumProperty("type", Ic2Color.class);
/*     */   private static final int maxDamage = 32;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */