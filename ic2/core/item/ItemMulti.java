/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.block.state.EnumProperty;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.IMultiItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class ItemMulti<T extends Enum<T> & IIdProvider> extends ItemIC2 implements IMultiItem<T> {
/*     */   protected final EnumProperty<T> typeProperty;
/*     */   private final Map<T, IItemRightClickHandler> rightClickHandlers;
/*     */   private final Map<T, IItemUseHandler> useHandlers;
/*     */   private final Map<T, IItemUpdateHandler> updateHandlers;
/*     */   private final Map<T, EnumRarity> rarityFilter;
/*     */   
/*     */   public static <T extends Enum<T> & IIdProvider> ItemMulti<T> create(ItemName name, Class<T> typeClass) {
/*     */     // Byte code:
/*     */     //   0: new ic2/core/block/state/EnumProperty
/*     */     //   3: dup
/*     */     //   4: ldc 'type'
/*     */     //   6: aload_1
/*     */     //   7: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Class;)V
/*     */     //   10: astore_2
/*     */     //   11: aload_2
/*     */     //   12: invokevirtual getAllowedValues : ()Ljava/util/List;
/*     */     //   15: invokeinterface size : ()I
/*     */     //   20: sipush #32767
/*     */     //   23: if_icmple -> 53
/*     */     //   26: new java/lang/IllegalArgumentException
/*     */     //   29: dup
/*     */     //   30: new java/lang/StringBuilder
/*     */     //   33: dup
/*     */     //   34: invokespecial <init> : ()V
/*     */     //   37: ldc 'Too many values to fit in a short for '
/*     */     //   39: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   42: aload_1
/*     */     //   43: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   46: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   49: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   52: athrow
/*     */     //   53: new ic2/core/item/ItemMulti
/*     */     //   56: dup
/*     */     //   57: aload_0
/*     */     //   58: aload_2
/*     */     //   59: invokespecial <init> : (Lic2/core/ref/ItemName;Lic2/core/block/state/EnumProperty;)V
/*     */     //   62: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #32	-> 0
/*     */     //   #33	-> 11
/*     */     //   #35	-> 53
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	63	0	name	Lic2/core/ref/ItemName;
/*     */     //   0	63	1	typeClass	Ljava/lang/Class;
/*     */     //   11	52	2	typeProperty	Lic2/core/block/state/EnumProperty;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	63	1	typeClass	Ljava/lang/Class<TT;>;
/*     */     //   11	52	2	typeProperty	Lic2/core/block/state/EnumProperty<TT;>;
/*     */   }
/*     */   
/*     */   private ItemMulti(ItemName name, EnumProperty<T> typeProperty) {
/*  39 */     super(name);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     this.rightClickHandlers = new IdentityHashMap<>();
/* 223 */     this.useHandlers = new IdentityHashMap<>();
/* 224 */     this.updateHandlers = new IdentityHashMap<>();
/* 225 */     this.rarityFilter = new IdentityHashMap<>();
/*     */     this.typeProperty = typeProperty;
/*     */     func_77627_a(true);
/*     */   }
/*     */   
/*     */   protected ItemMulti(ItemName name, Class<T> typeClass) {
/*     */     this(name, new EnumProperty("type", typeClass));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(ItemName name) {
/*     */     for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */       registerModel(((IIdProvider)enum_).getId(), name, ((IIdProvider)enum_).getModelName()); 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public final int getItemColor(ItemStack stack) {
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return super.getItemColor(stack); 
/*     */     return ((IIdProvider)type).getColor();
/*     */   }
/*     */   
/*     */   public final String func_77667_c(ItemStack stack) {
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return super.func_77667_c(stack); 
/*     */     return super.func_77667_c(stack) + "." + ((IIdProvider)type).getName();
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(T type) {
/*     */     if (!this.typeProperty.getAllowedValues().contains(type))
/*     */       throw new IllegalArgumentException("invalid property value " + type + " for property " + this.typeProperty); 
/*     */     return getItemStackUnchecked(type);
/*     */   }
/*     */   
/*     */   private ItemStack getItemStackUnchecked(T type) {
/*     */     return new ItemStack(this, 1, ((IIdProvider)type).getId());
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(String variant) {
/*     */     Enum enum_ = this.typeProperty.getValue(variant);
/*     */     if (enum_ == null)
/*     */       throw new IllegalArgumentException("invalid variant " + variant + " for " + this); 
/*     */     return getItemStackUnchecked((T)enum_);
/*     */   }
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/*     */     if (stack == null)
/*     */       throw new NullPointerException("null stack"); 
/*     */     if (stack.func_77973_b() != this)
/*     */       throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this); 
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       throw new IllegalArgumentException("The stack " + stack + " doesn't reference any valid subtype"); 
/*     */     return ((IIdProvider)type).getName();
/*     */   }
/*     */   
/*     */   public final void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*     */     if (!func_194125_a(tab))
/*     */       return; 
/*     */     for (Enum enum_ : this.typeProperty.getShownValues())
/*     */       subItems.add(getItemStackUnchecked((T)enum_)); 
/*     */   }
/*     */   
/*     */   public Set<T> getAllTypes() {
/*     */     return EnumSet.allOf(this.typeProperty.func_177699_b());
/*     */   }
/*     */   
/*     */   public final T getType(ItemStack stack) {
/*     */     return (T)this.typeProperty.getValue(stack.func_77960_j());
/*     */   }
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*     */     ItemStack stack = StackUtil.get(player, hand);
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return new ActionResult(EnumActionResult.PASS, stack); 
/*     */     IItemRightClickHandler handler = this.rightClickHandlers.get(type);
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
/*     */     IItemUseHandler handler = this.useHandlers.get(type);
/*     */     if (handler == null)
/*     */       return EnumActionResult.PASS; 
/*     */     return handler.onUse(stack, player, pos, hand, side);
/*     */   }
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
/*     */     T type = getType(stack);
/*     */     if (type == null)
/*     */       return; 
/*     */     IItemUpdateHandler handler = this.updateHandlers.get(type);
/*     */     if (handler == null)
/*     */       return; 
/*     */     handler.onUpdate(stack, world, entity, slotIndex, isCurrentItem);
/*     */   }
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*     */     EnumRarity rarity = this.rarityFilter.get(getType(stack));
/*     */     return (rarity != null) ? rarity : super.func_77613_e(stack);
/*     */   }
/*     */   
/*     */   public void setRightClickHandler(T type, IItemRightClickHandler handler) {
/*     */     if (type == null) {
/*     */       for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */         setRightClickHandler((T)enum_, handler); 
/*     */     } else {
/*     */       this.rightClickHandlers.put(type, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUseHandler(T type, IItemUseHandler handler) {
/*     */     if (type == null) {
/*     */       for (Enum enum_ : this.typeProperty.getAllowedValues())
/*     */         setUseHandler((T)enum_, handler); 
/*     */     } else {
/*     */       this.useHandlers.put(type, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUpdateHandler(T type, IItemUpdateHandler handler) {
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
/*     */   public static interface IItemUpdateHandler {
/*     */     void onUpdate(ItemStack param1ItemStack, World param1World, Entity param1Entity, int param1Int, boolean param1Boolean);
/*     */   }
/*     */   
/*     */   public static interface IItemUseHandler {
/*     */     EnumActionResult onUse(ItemStack param1ItemStack, EntityPlayer param1EntityPlayer, BlockPos param1BlockPos, EnumHand param1EnumHand, EnumFacing param1EnumFacing);
/*     */   }
/*     */   
/*     */   public static interface IItemRightClickHandler {
/*     */     ActionResult<ItemStack> onRightClick(ItemStack param1ItemStack, EntityPlayer param1EntityPlayer, EnumHand param1EnumHand);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */