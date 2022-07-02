/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.core.block.state.EnumProperty;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.item.ItemMulti;
/*     */ import ic2.core.item.ItemToolIC2;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
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
/*     */ public class ItemToolMulti<T extends Enum<T> & IIdProvider>
/*     */   extends ItemToolIC2
/*     */   implements IMultiItem<T>, ICustomDamageItem
/*     */ {
/*     */   protected final EnumProperty<T> typeProperty;
/*     */   private final Map<T, ItemMulti.IItemRightClickHandler> rightClickHandlers;
/*     */   private final Map<T, ItemMulti.IItemUseHandler> useHandlers;
/*     */   private final Map<T, ItemMulti.IItemUpdateHandler> updateHandlers;
/*     */   private final Map<T, EnumRarity> rarityFilter;
/*     */   
/*     */   public static <T extends Enum<T> & IIdProvider> ItemToolMulti<T> create(ItemName name, Class<T> typeClass, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
/*     */     // Byte code:
/*     */     //   0: new ic2/core/block/state/EnumProperty
/*     */     //   3: dup
/*     */     //   4: ldc 'type'
/*     */     //   6: aload_1
/*     */     //   7: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Class;)V
/*     */     //   10: astore #7
/*     */     //   12: aload #7
/*     */     //   14: invokevirtual getAllowedValues : ()Ljava/util/List;
/*     */     //   17: invokeinterface size : ()I
/*     */     //   22: sipush #32767
/*     */     //   25: if_icmple -> 55
/*     */     //   28: new java/lang/IllegalArgumentException
/*     */     //   31: dup
/*     */     //   32: new java/lang/StringBuilder
/*     */     //   35: dup
/*     */     //   36: invokespecial <init> : ()V
/*     */     //   39: ldc 'Too many values to fit in a short for '
/*     */     //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   44: aload_1
/*     */     //   45: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   48: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   51: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   54: athrow
/*     */     //   55: new ic2/core/item/tool/ItemToolMulti
/*     */     //   58: dup
/*     */     //   59: aload_0
/*     */     //   60: aload #7
/*     */     //   62: fload_2
/*     */     //   63: fload_3
/*     */     //   64: aload #4
/*     */     //   66: aload #5
/*     */     //   68: aload #6
/*     */     //   70: invokespecial <init> : (Lic2/core/ref/ItemName;Lic2/core/block/state/EnumProperty;FFLic2/core/item/tool/HarvestLevel;Ljava/util/Set;Ljava/util/Set;)V
/*     */     //   73: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #45	-> 0
/*     */     //   #46	-> 12
/*     */     //   #47	-> 28
/*     */     //   #49	-> 55
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	74	0	name	Lic2/core/ref/ItemName;
/*     */     //   0	74	1	typeClass	Ljava/lang/Class;
/*     */     //   0	74	2	damage	F
/*     */     //   0	74	3	speed	F
/*     */     //   0	74	4	harvestLevel	Lic2/core/item/tool/HarvestLevel;
/*     */     //   0	74	5	toolClasses	Ljava/util/Set;
/*     */     //   0	74	6	mineableBlocks	Ljava/util/Set;
/*     */     //   12	62	7	typeProperty	Lic2/core/block/state/EnumProperty;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	74	1	typeClass	Ljava/lang/Class<TT;>;
/*     */     //   0	74	5	toolClasses	Ljava/util/Set<+Lic2/core/item/tool/IToolClass;>;
/*     */     //   0	74	6	mineableBlocks	Ljava/util/Set<Lnet/minecraft/block/Block;>;
/*     */     //   12	62	7	typeProperty	Lic2/core/block/state/EnumProperty<TT;>;
/*     */   }
/*     */   
/*     */   private ItemToolMulti(ItemName name, EnumProperty<T> typeProperty, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
/*  55 */     super(name, damage, speed, harvestLevel, toolClasses, mineableBlocks);
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
/* 298 */     this.rightClickHandlers = new IdentityHashMap<>();
/* 299 */     this.useHandlers = new IdentityHashMap<>();
/* 300 */     this.updateHandlers = new IdentityHashMap<>();
/* 301 */     this.rarityFilter = new IdentityHashMap<>();
/*     */     this.typeProperty = typeProperty;
/*     */     func_77627_a(true);
/*     */   }
/*     */   
/*     */   protected ItemToolMulti(ItemName name, Class<T> typeClass, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses) {
/*     */     this(name, typeClass, harvestLevel, toolClasses, new HashSet<>());
/*     */   }
/*     */   
/*     */   protected ItemToolMulti(ItemName name, Class<T> typeClass, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
/*     */     this(name, typeClass, 0.0F, 0.0F, harvestLevel, toolClasses, mineableBlocks);
/*     */   }
/*     */   
/*     */   protected ItemToolMulti(ItemName name, Class<T> typeClass, float damage, float speed, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
/*     */     this(name, new EnumProperty("type", typeClass), damage, speed, harvestLevel, toolClasses, mineableBlocks);
/*     */   }
/*     */   
/*     */   public final String func_77667_c(ItemStack stack) {
/*     */     T type = getType(stack);
/*     */     return (type == null) ? super.func_77667_c(stack) : (super.func_77667_c(stack) + "." + ((IIdProvider)type).getName());
/*     */   }
/*     */   
/*     */   public final void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*     */     if (!func_194125_a(tab))
/*     */       return; 
/*     */     for (Enum enum_ : this.typeProperty.getShownValues())
/*     */       subItems.add(getItemStackUnchecked((T)enum_)); 
/*     */   }
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*     */     EnumRarity rarity = this.rarityFilter.get(getType(stack));
/*     */     return (rarity != null) ? rarity : super.func_77613_e(stack);
/*     */   }
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*     */     ItemStack stack = StackUtil.get(player, hand);
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return new ActionResult(EnumActionResult.PASS, stack); 
/*     */     ItemMulti.IItemRightClickHandler handler = this.rightClickHandlers.get(type);
/*     */     if (handler == null)
/*     */       return new ActionResult(EnumActionResult.PASS, stack); 
/*     */     return handler.onRightClick(stack, player, hand);
/*     */   }
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*     */     ItemStack stack = StackUtil.get(player, hand);
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return EnumActionResult.PASS; 
/*     */     ItemMulti.IItemUseHandler handler = this.useHandlers.get(type);
/*     */     if (handler == null)
/*     */       return EnumActionResult.PASS; 
/*     */     return handler.onUse(stack, player, pos, hand, side);
/*     */   }
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return; 
/*     */     ItemMulti.IItemUpdateHandler handler = this.updateHandlers.get(type);
/*     */     if (handler == null)
/*     */       return; 
/*     */     handler.onUpdate(stack, world, entity, slotIndex, isCurrentItem);
/*     */   }
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/*     */     return true;
/*     */   }
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/*     */     return getCustomDamage(stack) / getMaxCustomDamage(stack);
/*     */   }
/*     */   
/*     */   public boolean func_77645_m() {
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/*     */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */   
/*     */   public int getDamage(ItemStack stack) {
/*     */     return getCustomDamage(stack);
/*     */   }
/*     */   
/*     */   public int getMaxDamage(ItemStack stack) {
/*     */     return getMaxCustomDamage(stack);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(ItemName name) {
/*     */     for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */       ItemIC2.registerModel((Item)this, ((IIdProvider)enum_).getId(), name, ((IIdProvider)enum_).getModelName()); 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public final int getItemColor(ItemStack stack, int tintIndex) {
/*     */     T type = getType(stack);
/*     */     return (type == null) ? super.getItemColor(stack, tintIndex) : ((IIdProvider)type).getColor();
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(T type) {
/*     */     if (!this.typeProperty.getAllowedValues().contains(type))
/*     */       throw new IllegalArgumentException("Invalid property value " + type + " for property " + this.typeProperty); 
/*     */     return getItemStackUnchecked(type);
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(String variant) {
/*     */     Enum enum_ = this.typeProperty.getValue(variant);
/*     */     if (enum_ == null)
/*     */       throw new IllegalArgumentException("Invalid variant " + variant + " for " + this); 
/*     */     return getItemStackUnchecked((T)enum_);
/*     */   }
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/*     */     if (stack == null)
/*     */       throw new NullPointerException("The stack cannot be null"); 
/*     */     if (stack.func_77973_b() != this)
/*     */       throw new IllegalArgumentException("The stack " + stack + " does not match " + this); 
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       throw new IllegalArgumentException("The stack " + stack + " does not reference any valid subtype"); 
/*     */     return ((IIdProvider)type).getName();
/*     */   }
/*     */   
/*     */   public Set<T> getAllTypes() {
/*     */     return EnumSet.allOf(this.typeProperty.func_177699_b());
/*     */   }
/*     */   
/*     */   public int getCustomDamage(ItemStack stack) {
/*     */     if (!stack.func_77942_o())
/*     */       return 0; 
/*     */     NBTTagCompound data = stack.func_77978_p();
/*     */     assert data != null;
/*     */     return data.func_74764_b("durability") ? data.func_74762_e("durability") : 0;
/*     */   }
/*     */   
/*     */   public int getMaxCustomDamage(ItemStack stack) {
/*     */     if (!stack.func_77942_o())
/*     */       return 0; 
/*     */     NBTTagCompound data = stack.func_77978_p();
/*     */     assert data != null;
/*     */     return data.func_74764_b("maxDurability") ? data.func_74762_e("maxDurability") : 0;
/*     */   }
/*     */   
/*     */   public void setCustomDamage(ItemStack stack, int damage) {
/*     */     NBTTagCompound data = StackUtil.getOrCreateNbtData(stack);
/*     */     data.func_74768_a("durability", damage);
/*     */   }
/*     */   
/*     */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase source) {
/*     */     setCustomDamage(stack, getCustomDamage(stack) + damage);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public final T getType(ItemStack stack) {
/*     */     return (T)this.typeProperty.getValue(stack.func_77960_j());
/*     */   }
/*     */   
/*     */   public void setRightClickHandler(T type, ItemMulti.IItemRightClickHandler handler) {
/*     */     if (type == null) {
/*     */       for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */         setRightClickHandler((T)enum_, handler); 
/*     */     } else {
/*     */       this.rightClickHandlers.put(type, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUseHandler(T type, ItemMulti.IItemUseHandler handler) {
/*     */     if (type == null) {
/*     */       for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */         setUseHandler((T)enum_, handler); 
/*     */     } else {
/*     */       this.useHandlers.put(type, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUpdateHandler(T type, ItemMulti.IItemUpdateHandler handler) {
/*     */     if (type == null) {
/*     */       for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */         setUpdateHandler((T)enum_, handler); 
/*     */     } else {
/*     */       this.updateHandlers.put(type, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRarity(T type, EnumRarity rarity) {
/*     */     if (type == null) {
/*     */       setRarity(rarity);
/*     */     } else {
/*     */       this.rarityFilter.put(type, rarity);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ItemStack getItemStackUnchecked(T type) {
/*     */     return new ItemStack((Item)this, 1, ((IIdProvider)type).getId());
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */