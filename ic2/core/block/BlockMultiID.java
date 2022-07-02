/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.block.state.EnumProperty;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.IBlockSound;
/*     */ import ic2.core.block.type.IExtBlockType;
/*     */ import ic2.core.item.block.ItemBlockMulti;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.IMultiBlock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockMultiID<T extends Enum<T> & IIdProvider>
/*     */   extends BlockBase
/*     */   implements IMultiBlock<T>
/*     */ {
/*     */   public static <T extends Enum<T> & IIdProvider> BlockMultiID<T> create(BlockName name, Material material, Class<T> typeClass) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokestatic createTypeProperty : (Ljava/lang/Class;)Lic2/core/block/state/EnumProperty;
/*     */     //   4: astore_3
/*     */     //   5: getstatic ic2/core/block/BlockMultiID.currentTypeProperty : Ljava/lang/ThreadLocal;
/*     */     //   8: aload_3
/*     */     //   9: invokevirtual set : (Ljava/lang/Object;)V
/*     */     //   12: new ic2/core/block/BlockMultiID
/*     */     //   15: dup
/*     */     //   16: aload_0
/*     */     //   17: aload_1
/*     */     //   18: invokespecial <init> : (Lic2/core/ref/BlockName;Lnet/minecraft/block/material/Material;)V
/*     */     //   21: astore #4
/*     */     //   23: getstatic ic2/core/block/BlockMultiID.currentTypeProperty : Ljava/lang/ThreadLocal;
/*     */     //   26: invokevirtual remove : ()V
/*     */     //   29: aload #4
/*     */     //   31: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #44	-> 0
/*     */     //   #46	-> 5
/*     */     //   #47	-> 12
/*     */     //   #48	-> 23
/*     */     //   #50	-> 29
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	32	0	name	Lic2/core/ref/BlockName;
/*     */     //   0	32	1	material	Lnet/minecraft/block/material/Material;
/*     */     //   0	32	2	typeClass	Ljava/lang/Class;
/*     */     //   5	27	3	typeProperty	Lic2/core/block/state/EnumProperty;
/*     */     //   23	9	4	ret	Lic2/core/block/BlockMultiID;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	32	2	typeClass	Ljava/lang/Class<TT;>;
/*     */     //   5	27	3	typeProperty	Lic2/core/block/state/EnumProperty<TT;>;
/*     */     //   23	9	4	ret	Lic2/core/block/BlockMultiID<TT;>;
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T> & IIdProvider> EnumProperty<T> createTypeProperty(Class<T> typeClass) {
/*  54 */     EnumProperty<T> ret = new EnumProperty("type", typeClass);
/*  55 */     if (ret.getAllowedValues().size() > 16) throw new IllegalArgumentException("Too many values to fit in 16 meta values for " + typeClass);
/*     */     
/*  57 */     return ret;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <T extends Enum<T> & IIdProvider, U extends BlockMultiID<T>> U create(Class<U> blockClass, Class<T> typeClass, Object... ctorArgs) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokestatic createTypeProperty : (Ljava/lang/Class;)Lic2/core/block/state/EnumProperty;
/*     */     //   4: astore_3
/*     */     //   5: aconst_null
/*     */     //   6: astore #4
/*     */     //   8: aload_0
/*     */     //   9: invokevirtual getDeclaredConstructors : ()[Ljava/lang/reflect/Constructor;
/*     */     //   12: astore #5
/*     */     //   14: aload #5
/*     */     //   16: arraylength
/*     */     //   17: istore #6
/*     */     //   19: iconst_0
/*     */     //   20: istore #7
/*     */     //   22: iload #7
/*     */     //   24: iload #6
/*     */     //   26: if_icmpge -> 143
/*     */     //   29: aload #5
/*     */     //   31: iload #7
/*     */     //   33: aaload
/*     */     //   34: astore #8
/*     */     //   36: aload #8
/*     */     //   38: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
/*     */     //   41: astore #9
/*     */     //   43: aload #9
/*     */     //   45: arraylength
/*     */     //   46: aload_2
/*     */     //   47: arraylength
/*     */     //   48: if_icmpeq -> 54
/*     */     //   51: goto -> 137
/*     */     //   54: iconst_0
/*     */     //   55: istore #10
/*     */     //   57: iload #10
/*     */     //   59: aload #9
/*     */     //   61: arraylength
/*     */     //   62: if_icmpge -> 118
/*     */     //   65: aload #9
/*     */     //   67: iload #10
/*     */     //   69: aaload
/*     */     //   70: astore #11
/*     */     //   72: aload_2
/*     */     //   73: iload #10
/*     */     //   75: aaload
/*     */     //   76: astore #12
/*     */     //   78: aload #12
/*     */     //   80: ifnonnull -> 91
/*     */     //   83: aload #11
/*     */     //   85: invokevirtual isPrimitive : ()Z
/*     */     //   88: ifne -> 137
/*     */     //   91: aload #12
/*     */     //   93: ifnull -> 112
/*     */     //   96: aload #9
/*     */     //   98: iload #10
/*     */     //   100: aaload
/*     */     //   101: aload #12
/*     */     //   103: invokevirtual isInstance : (Ljava/lang/Object;)Z
/*     */     //   106: ifne -> 112
/*     */     //   109: goto -> 137
/*     */     //   112: iinc #10, 1
/*     */     //   115: goto -> 57
/*     */     //   118: aload #4
/*     */     //   120: ifnull -> 133
/*     */     //   123: new java/lang/IllegalArgumentException
/*     */     //   126: dup
/*     */     //   127: ldc 'ambiguous constructor'
/*     */     //   129: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   132: athrow
/*     */     //   133: aload #8
/*     */     //   135: astore #4
/*     */     //   137: iinc #7, 1
/*     */     //   140: goto -> 22
/*     */     //   143: aload #4
/*     */     //   145: ifnonnull -> 158
/*     */     //   148: new java/lang/IllegalArgumentException
/*     */     //   151: dup
/*     */     //   152: ldc 'no matching constructor'
/*     */     //   154: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   157: athrow
/*     */     //   158: getstatic ic2/core/block/BlockMultiID.currentTypeProperty : Ljava/lang/ThreadLocal;
/*     */     //   161: aload_3
/*     */     //   162: invokevirtual set : (Ljava/lang/Object;)V
/*     */     //   165: aload #4
/*     */     //   167: iconst_1
/*     */     //   168: invokevirtual setAccessible : (Z)V
/*     */     //   171: aload #4
/*     */     //   173: aload_2
/*     */     //   174: invokevirtual newInstance : ([Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   177: checkcast ic2/core/block/BlockMultiID
/*     */     //   180: astore #5
/*     */     //   182: getstatic ic2/core/block/BlockMultiID.currentTypeProperty : Ljava/lang/ThreadLocal;
/*     */     //   185: invokevirtual remove : ()V
/*     */     //   188: goto -> 214
/*     */     //   191: astore #6
/*     */     //   193: new java/lang/RuntimeException
/*     */     //   196: dup
/*     */     //   197: aload #6
/*     */     //   199: invokespecial <init> : (Ljava/lang/Throwable;)V
/*     */     //   202: athrow
/*     */     //   203: astore #13
/*     */     //   205: getstatic ic2/core/block/BlockMultiID.currentTypeProperty : Ljava/lang/ThreadLocal;
/*     */     //   208: invokevirtual remove : ()V
/*     */     //   211: aload #13
/*     */     //   213: athrow
/*     */     //   214: aload #5
/*     */     //   216: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #62	-> 0
/*     */     //   #64	-> 5
/*     */     //   #66	-> 8
/*     */     //   #67	-> 36
/*     */     //   #68	-> 43
/*     */     //   #70	-> 54
/*     */     //   #71	-> 65
/*     */     //   #72	-> 72
/*     */     //   #74	-> 78
/*     */     //   #75	-> 103
/*     */     //   #76	-> 109
/*     */     //   #70	-> 112
/*     */     //   #80	-> 118
/*     */     //   #82	-> 133
/*     */     //   #66	-> 137
/*     */     //   #85	-> 143
/*     */     //   #87	-> 158
/*     */     //   #92	-> 165
/*     */     //   #93	-> 171
/*     */     //   #97	-> 182
/*     */     //   #98	-> 188
/*     */     //   #94	-> 191
/*     */     //   #95	-> 193
/*     */     //   #97	-> 203
/*     */     //   #98	-> 211
/*     */     //   #100	-> 214
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   72	40	11	type	Ljava/lang/Class;
/*     */     //   78	34	12	arg	Ljava/lang/Object;
/*     */     //   57	61	10	i	I
/*     */     //   43	94	9	parameterTypes	[Ljava/lang/Class;
/*     */     //   36	101	8	cCtor	Ljava/lang/reflect/Constructor;
/*     */     //   182	9	5	ret	Lic2/core/block/BlockMultiID;
/*     */     //   193	10	6	e	Ljava/lang/Exception;
/*     */     //   0	217	0	blockClass	Ljava/lang/Class;
/*     */     //   0	217	1	typeClass	Ljava/lang/Class;
/*     */     //   0	217	2	ctorArgs	[Ljava/lang/Object;
/*     */     //   5	212	3	typeProperty	Lic2/core/block/state/EnumProperty;
/*     */     //   8	209	4	ctor	Ljava/lang/reflect/Constructor;
/*     */     //   214	3	5	ret	Lic2/core/block/BlockMultiID;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   72	40	11	type	Ljava/lang/Class<*>;
/*     */     //   43	94	9	parameterTypes	[Ljava/lang/Class<*>;
/*     */     //   36	101	8	cCtor	Ljava/lang/reflect/Constructor<*>;
/*     */     //   182	9	5	ret	TU;
/*     */     //   0	217	0	blockClass	Ljava/lang/Class<TU;>;
/*     */     //   0	217	1	typeClass	Ljava/lang/Class<TT;>;
/*     */     //   5	212	3	typeProperty	Lic2/core/block/state/EnumProperty<TT;>;
/*     */     //   8	209	4	ctor	Ljava/lang/reflect/Constructor<TU;>;
/*     */     //   214	3	5	ret	TU;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   165	182	191	java/lang/Exception
/*     */     //   165	182	203	finally
/*     */     //   191	205	203	finally
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockMultiID(BlockName name, Material material) {
/* 104 */     this(name, material, (Class)ItemBlockMulti.class);
/*     */   }
/*     */   
/*     */   protected BlockMultiID(BlockName name, Material material, Class<? extends ItemBlock> itemClass) {
/* 108 */     super(name, material, itemClass);
/*     */     
/* 110 */     this.typeProperty = getTypeProperty();
/*     */     
/* 112 */     func_180632_j(this.field_176227_L.func_177621_b()
/* 113 */         .func_177226_a((IProperty)this.typeProperty, this.typeProperty.getDefault()));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/* 119 */     registerItemModels(this, getTypeStates());
/*     */   }
/*     */   
/*     */   protected final List<IBlockState> getTypeStates() {
/* 123 */     List<IBlockState> ret = new ArrayList<>(this.typeProperty.getAllowedValues().size());
/*     */     
/* 125 */     for (Enum enum_ : this.typeProperty.getAllowedValues()) {
/* 126 */       ret.add(func_176223_P().func_177226_a((IProperty)this.typeProperty, enum_));
/*     */     }
/*     */     
/* 129 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/* 134 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)getTypeProperty() });
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState func_176203_a(int meta) {
/* 139 */     EnumProperty<T> typeProperty = getTypeProperty();
/*     */     
/* 141 */     return func_176223_P().func_177226_a((IProperty)typeProperty, typeProperty.getValueOrDefault(meta));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_176201_c(IBlockState state) {
/* 146 */     return ((IIdProvider)state.func_177229_b((IProperty)getTypeProperty())).getId();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getType(IBlockAccess world, BlockPos pos) {
/* 234 */     return getType(world.func_180495_p(pos));
/*     */   }
/*     */   
/*     */   public final T getType(IBlockState state) {
/* 238 */     if (state.func_177230_c() != this) return null;
/*     */     
/* 240 */     return (T)state.func_177229_b((IProperty)this.typeProperty);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getState(T type) {
/* 245 */     if (type == null) throw new IllegalArgumentException("invalid type: " + type);
/*     */     
/* 247 */     return func_176223_P().func_177226_a((IProperty)this.typeProperty, (Comparable)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getState(String variant) {
/* 252 */     if (variant == null) {
/* 253 */       return func_176223_P();
/*     */     }
/* 255 */     for (Enum enum_ : this.typeProperty.getAllowedValues()) {
/* 256 */       if (enum_.name().equals(variant)) return getState((T)enum_); 
/*     */     } 
/* 258 */     throw new IllegalArgumentException("Invalid type " + variant + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(T type) {
/* 263 */     return getItemStack(getState(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(String variant) {
/* 268 */     if (variant == null) throw new IllegalArgumentException("invalid type: " + variant);
/*     */     
/* 270 */     Enum enum_ = this.typeProperty.getValue(variant);
/* 271 */     if (enum_ == null) throw new IllegalArgumentException("invalid variant " + variant + " for " + this);
/*     */     
/* 273 */     return getItemStack((T)enum_);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/* 278 */     if (stack == null) throw new NullPointerException("null stack");
/*     */     
/* 280 */     Item item = Item.func_150898_a(this);
/* 281 */     if (stack.func_77973_b() != item) throw new IllegalArgumentException("The stack " + stack + " doesn't match " + item + " (" + this + ")");
/*     */     
/* 283 */     IBlockState state = func_176203_a(stack.func_77960_j());
/* 284 */     T type = getType(state);
/*     */     
/* 286 */     return ((IIdProvider)type).getName();
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(IBlockState state) {
/* 290 */     if (state.func_177230_c() != this) return null;
/*     */     
/* 292 */     Item item = Item.func_150898_a(this);
/* 293 */     if (item == null || item == Items.field_190931_a) throw new RuntimeException("no matching item for " + this);
/*     */     
/* 295 */     int meta = func_176201_c(state);
/*     */     
/* 297 */     return new ItemStack(item, 1, meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/* 302 */     ItemStack stack = getItemStack(state);
/*     */     
/* 304 */     if (stack == null) {
/* 305 */       return new ArrayList<>();
/*     */     }
/* 307 */     List<ItemStack> ret = new ArrayList<>();
/* 308 */     ret.add(stack);
/*     */     
/* 310 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
/* 319 */     for (Enum enum_ : this.typeProperty.getShownValues()) {
/* 320 */       itemList.add(getItemStack((T)enum_));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<T> getAllTypes() {
/* 326 */     return EnumSet.allOf(this.typeProperty.func_177699_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_185473_a(World world, BlockPos pos, IBlockState state) {
/* 331 */     return getItemStack(state);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EnumProperty<T> getTypeProperty() {
/*     */     EnumProperty<T> ret;
/* 359 */     if (this.typeProperty != null) {
/* 360 */       ret = this.typeProperty;
/*     */     } else {
/* 362 */       ret = (EnumProperty<T>)currentTypeProperty.get();
/*     */       
/* 364 */       if (ret == null)
/*     */       {
/* 366 */         throw new IllegalStateException("The type property can't be obtained.");
/*     */       }
/*     */     } 
/*     */     
/* 370 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_176195_g(IBlockState state, World world, BlockPos pos) {
/* 376 */     if (IExtBlockType.class.isAssignableFrom(this.typeProperty.func_177699_b())) {
/* 377 */       T type = getType(state);
/*     */       
/* 379 */       if (type != null) return ((IExtBlockType)type).getHardness();
/*     */     
/*     */     } 
/* 382 */     return super.func_176195_g(state, world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
/* 387 */     if (IExtBlockType.class.isAssignableFrom(this.typeProperty.func_177699_b())) {
/* 388 */       T type = getType((IBlockAccess)world, pos);
/*     */       
/* 390 */       if (type != null) return ((IExtBlockType)type).getExplosionResistance();
/*     */     
/*     */     } 
/* 393 */     return super.getExplosionResistance(world, pos, exploder, explosion);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
/* 398 */     if (IBlockSound.class.isAssignableFrom(this.typeProperty.func_177699_b())) {
/* 399 */       T type = getType(state);
/*     */       
/* 401 */       if (type != null) return ((IBlockSound)type).getSound();
/*     */     
/*     */     } 
/* 404 */     return super.getSoundType(state, world, pos, entity);
/*     */   }
/*     */   
/* 407 */   private static final ThreadLocal<EnumProperty<? extends Enum<?>>> currentTypeProperty = new UnstartingThreadLocal<>();
/*     */   protected final EnumProperty<T> typeProperty;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockMultiID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */