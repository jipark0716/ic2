/*     */ package ic2.core.block;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Collections2;
/*     */ import ic2.api.event.TeBlockFinalCallEvent;
/*     */ import ic2.api.item.ITeBlockSpecialItem;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ public final class TeBlockRegistry {
/*     */   public static class TeBlockInfo<E extends Enum<E> & ITeBlock> { private BlockTileEntity block;
/*     */     private final boolean specialModels;
/*     */     private Material defaultMaterial;
/*     */     private ITeBlock.ITeBlockCreativeRegisterer creativeRegisterer;
/*     */     private final Set<E> teBlocks;
/*     */     private final List<ITeBlock> idMap;
/*     */     
/*     */     TeBlockInfo(E universe) {
/*  35 */       this((Class)universe.getClass());
/*     */     }
/*     */ 
/*     */     
/*     */     void register(E block) {
/*     */       if (!this.teBlocks.add(block)) {
/*     */         throw new IllegalStateException("ITeBlock already registered!");
/*     */       }
/*     */       TeBlockRegistry.addName((ITeBlock)block);
/*     */       TeBlockRegistry.addClass((ITeBlock)block);
/*     */       if (((IIdProvider)block).getId() > -1) {
/*     */         int ID = ((IIdProvider)block).getId();
/*     */         for (; this.idMap.size() < ID; this.idMap.add(null));
/*     */         if (this.idMap.size() == ID) {
/*     */           this.idMap.add((ITeBlock)block);
/*     */         } else {
/*     */           if (this.idMap.get(ID) != null) {
/*     */             throw new IllegalStateException("The id " + ID + " for " + block + " is already in use by " + this.idMap.get(ID) + '.');
/*     */           }
/*     */           this.idMap.set(ID, (ITeBlock)block);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void registerAll(Class<E> universe) {
/*     */       for (Enum enum_ : EnumSet.<Enum>allOf(universe)) {
/*     */         register((E)enum_);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void setBlock(BlockTileEntity block) {
/*     */       if (hasBlock()) {
/*     */         throw new IllegalStateException("Already has block set (" + this.block + ") when adding " + block);
/*     */       }
/*     */       this.block = block;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasBlock() {
/*     */       return (this.block != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockTileEntity getBlock() {
/*     */       return this.block;
/*     */     }
/*     */ 
/*     */     
/*     */     void setCreativeRegisterer(ITeBlock.ITeBlockCreativeRegisterer creativeRegisterer) {
/*     */       this.creativeRegisterer = creativeRegisterer;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCreativeRegisterer() {
/*     */       return (this.creativeRegisterer != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public ITeBlock.ITeBlockCreativeRegisterer getCreativeRegisterer() {
/*     */       return this.creativeRegisterer;
/*     */     }
/*     */ 
/*     */     
/*     */     void setDefaultMaterial(Material material) {
/*     */       this.defaultMaterial = material;
/*     */     }
/*     */ 
/*     */     
/*     */     public Material getDefaultMaterial() {
/*     */       return this.defaultMaterial;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSpecialModels() {
/*     */       return this.specialModels;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<? extends ITeBlock> getTeBlocks() {
/*     */       return Collections.unmodifiableSet(this.teBlocks);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<ITeBlock> getIdMap() {
/*     */       return Collections.unmodifiableList(this.idMap);
/*     */     }
/*     */     
/*     */     TeBlockInfo(Class<E> universe) {
/* 125 */       this.defaultMaterial = Material.field_151573_f;
/*     */ 
/*     */ 
/*     */       
/* 129 */       this.idMap = new ArrayList<>();
/*     */       this.teBlocks = EnumSet.noneOf(universe);
/*     */       this.specialModels = ITeBlockSpecialItem.class.isAssignableFrom(universe);
/* 132 */     } } private static final Map<String, ITeBlock> NAME_REGISTRY = new HashMap<>();
/* 133 */   private static final Map<Class<? extends TileEntityBlock>, ITeBlock> CLASS_REGISTRY = new IdentityHashMap<>();
/* 134 */   private static final Map<String, Class<? extends TileEntityBlock>> OLD_REGISTRY = new HashMap<>();
/* 135 */   private static final Map<ResourceLocation, TeBlockInfo<?>> RESOURCE_REGISTRY = new HashMap<>(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean blocksBuilt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends Enum<E> & ITeBlock> void add(E block) {
/*     */     // Byte code:
/*     */     //   0: invokestatic canBuildBlocks : ()Z
/*     */     //   3: ifne -> 16
/*     */     //   6: new java/lang/IllegalStateException
/*     */     //   9: dup
/*     */     //   10: ldc 'Cannot register additional ITeBlocks once block map built!'
/*     */     //   12: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   15: athrow
/*     */     //   16: aload_0
/*     */     //   17: ifnonnull -> 30
/*     */     //   20: new java/lang/NullPointerException
/*     */     //   23: dup
/*     */     //   24: ldc 'Cannot register null ITeBlock!'
/*     */     //   26: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   29: athrow
/*     */     //   30: aload_0
/*     */     //   31: checkcast ic2/core/block/ITeBlock
/*     */     //   34: invokeinterface getIdentifier : ()Lnet/minecraft/util/ResourceLocation;
/*     */     //   39: astore_2
/*     */     //   40: getstatic ic2/core/block/TeBlockRegistry.RESOURCE_REGISTRY : Ljava/util/Map;
/*     */     //   43: aload_2
/*     */     //   44: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*     */     //   49: ifne -> 75
/*     */     //   52: getstatic ic2/core/block/TeBlockRegistry.RESOURCE_REGISTRY : Ljava/util/Map;
/*     */     //   55: aload_2
/*     */     //   56: new ic2/core/block/TeBlockRegistry$TeBlockInfo
/*     */     //   59: dup
/*     */     //   60: aload_0
/*     */     //   61: invokespecial <init> : (Ljava/lang/Enum;)V
/*     */     //   64: dup
/*     */     //   65: astore_1
/*     */     //   66: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   71: pop
/*     */     //   72: goto -> 88
/*     */     //   75: getstatic ic2/core/block/TeBlockRegistry.RESOURCE_REGISTRY : Ljava/util/Map;
/*     */     //   78: aload_2
/*     */     //   79: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   84: checkcast ic2/core/block/TeBlockRegistry$TeBlockInfo
/*     */     //   87: astore_1
/*     */     //   88: aload_1
/*     */     //   89: aload_0
/*     */     //   90: invokevirtual register : (Ljava/lang/Enum;)V
/*     */     //   93: aload_0
/*     */     //   94: instanceof ic2/core/block/ITeBlock$ITeBlockCreativeRegisterer
/*     */     //   97: ifeq -> 108
/*     */     //   100: aload_1
/*     */     //   101: aload_0
/*     */     //   102: checkcast ic2/core/block/ITeBlock$ITeBlockCreativeRegisterer
/*     */     //   105: invokevirtual setCreativeRegisterer : (Lic2/core/block/ITeBlock$ITeBlockCreativeRegisterer;)V
/*     */     //   108: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #142	-> 0
/*     */     //   #143	-> 6
/*     */     //   #145	-> 16
/*     */     //   #146	-> 20
/*     */     //   #149	-> 30
/*     */     //   #151	-> 40
/*     */     //   #152	-> 52
/*     */     //   #154	-> 75
/*     */     //   #158	-> 88
/*     */     //   #160	-> 93
/*     */     //   #161	-> 100
/*     */     //   #163	-> 108
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   66	9	1	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo;
/*     */     //   0	109	0	block	Ljava/lang/Enum;
/*     */     //   88	21	1	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo;
/*     */     //   40	69	2	loc	Lnet/minecraft/util/ResourceLocation;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   66	9	1	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo<TE;>;
/*     */     //   0	109	0	block	TE;
/*     */     //   88	21	1	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo<TE;>;
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
/*     */   public static <E extends Enum<E> & ITeBlock> void addAll(Class<E> enumClass, ResourceLocation identifier) {
/*     */     // Byte code:
/*     */     //   0: invokestatic canBuildBlocks : ()Z
/*     */     //   3: ifne -> 16
/*     */     //   6: new java/lang/IllegalStateException
/*     */     //   9: dup
/*     */     //   10: ldc 'Cannot register additional ITeBlocks once block map built!'
/*     */     //   12: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   15: athrow
/*     */     //   16: aload_0
/*     */     //   17: invokestatic allOf : (Ljava/lang/Class;)Ljava/util/EnumSet;
/*     */     //   20: invokevirtual isEmpty : ()Z
/*     */     //   23: ifeq -> 36
/*     */     //   26: new java/lang/IllegalArgumentException
/*     */     //   29: dup
/*     */     //   30: ldc 'Cannot register empty enum!'
/*     */     //   32: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   35: athrow
/*     */     //   36: aload_1
/*     */     //   37: ifnonnull -> 50
/*     */     //   40: new java/lang/NullPointerException
/*     */     //   43: dup
/*     */     //   44: ldc 'Cannot register a null identifier!'
/*     */     //   46: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   49: athrow
/*     */     //   50: getstatic ic2/core/block/TeBlockRegistry.RESOURCE_REGISTRY : Ljava/util/Map;
/*     */     //   53: aload_1
/*     */     //   54: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*     */     //   59: ifeq -> 89
/*     */     //   62: new java/lang/IllegalArgumentException
/*     */     //   65: dup
/*     */     //   66: new java/lang/StringBuilder
/*     */     //   69: dup
/*     */     //   70: invokespecial <init> : ()V
/*     */     //   73: ldc 'Already registered an enum for '
/*     */     //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   78: aload_1
/*     */     //   79: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   82: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   85: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   88: athrow
/*     */     //   89: new ic2/core/block/TeBlockRegistry$TeBlockInfo
/*     */     //   92: dup
/*     */     //   93: aload_0
/*     */     //   94: invokespecial <init> : (Ljava/lang/Class;)V
/*     */     //   97: astore_2
/*     */     //   98: getstatic ic2/core/block/TeBlockRegistry.RESOURCE_REGISTRY : Ljava/util/Map;
/*     */     //   101: aload_1
/*     */     //   102: aload_2
/*     */     //   103: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   108: pop
/*     */     //   109: aload_2
/*     */     //   110: aload_0
/*     */     //   111: invokevirtual registerAll : (Ljava/lang/Class;)V
/*     */     //   114: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #166	-> 0
/*     */     //   #167	-> 6
/*     */     //   #169	-> 16
/*     */     //   #170	-> 26
/*     */     //   #172	-> 36
/*     */     //   #173	-> 40
/*     */     //   #175	-> 50
/*     */     //   #176	-> 62
/*     */     //   #178	-> 89
/*     */     //   #179	-> 98
/*     */     //   #180	-> 109
/*     */     //   #181	-> 114
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	115	0	enumClass	Ljava/lang/Class;
/*     */     //   0	115	1	identifier	Lnet/minecraft/util/ResourceLocation;
/*     */     //   98	17	2	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	115	0	enumClass	Ljava/lang/Class<TE;>;
/*     */     //   98	17	2	instance	Lic2/core/block/TeBlockRegistry$TeBlockInfo<TE;>;
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
/*     */   public static <T extends ITeBlock & ITeBlock.ITeBlockCreativeRegisterer> void addCreativeRegisterer(T registerer) {
/* 184 */     addCreativeRegisterer((ITeBlock.ITeBlockCreativeRegisterer)registerer, registerer.getIdentifier());
/*     */   }
/*     */   
/*     */   public static void addCreativeRegisterer(ITeBlock.ITeBlockCreativeRegisterer registerer, ResourceLocation identifier) {
/* 188 */     if (!RESOURCE_REGISTRY.containsKey(identifier)) {
/* 189 */       throw new IllegalStateException("Must register an ITeBlock instance before adding a creative registerer!");
/*     */     }
/*     */     
/* 192 */     ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).setCreativeRegisterer(registerer);
/*     */   }
/*     */   
/*     */   public static void setDefaultMaterial(ResourceLocation identifier, Material material) {
/* 196 */     if (!RESOURCE_REGISTRY.containsKey(identifier)) {
/* 197 */       throw new IllegalStateException("Must register an ITeBlock instance before setting the default material!");
/*     */     }
/*     */     
/* 200 */     ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).setDefaultMaterial(material);
/*     */   }
/*     */   
/*     */   static void addName(ITeBlock teBlock) {
/* 204 */     if (NAME_REGISTRY.put(teBlock.getName(), teBlock) != null) throw new IllegalStateException("Duplicate name for different ITeBlocks!"); 
/*     */   }
/*     */   
/*     */   static void addClass(ITeBlock teBlock) {
/* 208 */     if (CLASS_REGISTRY.put(teBlock.getTeClass(), teBlock) != null) throw new IllegalStateException("Duplicate class name for different ITeBlocks!");
/*     */   
/*     */   }
/*     */   
/*     */   public static void ensureMapping(TeBlock block, Class<? extends TileEntityBlock> te) {
/* 213 */     CLASS_REGISTRY.putIfAbsent(te, block);
/*     */     
/* 215 */     if (block.getTeClass() != te) OLD_REGISTRY.put("Old-" + block.getName(), te);
/*     */   
/*     */   }
/*     */   
/*     */   public static void buildBlocks() {
/* 220 */     if (!canBuildBlocks()) {
/* 221 */       throw new IllegalStateException("Cannot build blocks twice!");
/*     */     }
/* 223 */     MinecraftForge.EVENT_BUS.post((Event)new TeBlockFinalCallEvent());
/*     */     
/* 225 */     blocksBuilt = true;
/* 226 */     ResourceLocation ic2Loc = TeBlock.invalid.getIdentifier();
/*     */     
/* 228 */     for (Map.Entry<ResourceLocation, TeBlockInfo<?>> entry : RESOURCE_REGISTRY.entrySet()) {
/* 229 */       BlockTileEntity block; ResourceLocation location = entry.getKey();
/* 230 */       TeBlockInfo<?> info = entry.getValue();
/*     */       
/* 232 */       Set<Material> mats = new LinkedHashSet<>();
/* 233 */       mats.add(info.getDefaultMaterial());
/*     */       
/* 235 */       for (ITeBlock teBlock : info.getTeBlocks()) {
/* 236 */         mats.add(teBlock.getMaterial());
/*     */       }
/*     */       
/* 239 */       if (mats.size() > 8) throw new RuntimeException("Cannot form a TeBlock with more than 8 different materials (attempted " + mats.size() + ')');
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (location == ic2Loc) {
/* 244 */         block = BlockTileEntity.create(BlockName.te, mats);
/*     */       } else {
/* 246 */         block = BlockTileEntity.create("te_" + location.func_110623_a(), location, mats);
/*     */       } 
/*     */       
/* 249 */       info.setBlock(block);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBuildBlocks() {
/* 255 */     return !blocksBuilt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITeBlock get(String name) {
/* 260 */     ITeBlock ret = NAME_REGISTRY.get(name);
/*     */     
/* 262 */     return (ret != null) ? ret : (ITeBlock)TeBlock.invalid;
/*     */   }
/*     */   
/*     */   public static Class<? extends TileEntityBlock> getOld(String name) {
/* 266 */     return OLD_REGISTRY.get(name);
/*     */   }
/*     */   
/*     */   public static ITeBlock get(ResourceLocation identifier, int ID) {
/* 270 */     if (ID >= 0 && RESOURCE_REGISTRY.containsKey(identifier)) {
/* 271 */       List<ITeBlock> items = ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).getIdMap();
/*     */       
/* 273 */       if (ID < items.size()) {
/* 274 */         return items.get(ID);
/*     */       }
/*     */     } 
/* 277 */     return null;
/*     */   }
/*     */   
/*     */   public static ITeBlock get(Class<? extends TileEntityBlock> cls) {
/* 281 */     return CLASS_REGISTRY.get(cls);
/*     */   }
/*     */   
/*     */   public static BlockTileEntity get(ResourceLocation identifier) {
/* 285 */     return RESOURCE_REGISTRY.containsKey(identifier) ? ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).getBlock() : null;
/*     */   }
/*     */   
/*     */   public static Iterable<Map.Entry<ResourceLocation, Set<? extends ITeBlock>>> getAll() {
/* 289 */     return Collections2.transform(RESOURCE_REGISTRY.entrySet(), new Function<Map.Entry<ResourceLocation, TeBlockInfo<?>>, Map.Entry<ResourceLocation, Set<? extends ITeBlock>>>()
/*     */         {
/*     */           public AbstractMap.SimpleImmutableEntry<ResourceLocation, Set<? extends ITeBlock>> apply(Map.Entry<ResourceLocation, TeBlockRegistry.TeBlockInfo<?>> input) {
/* 292 */             return new AbstractMap.SimpleImmutableEntry<>(input.getKey(), ((TeBlockRegistry.TeBlockInfo)input.getValue()).getTeBlocks());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static Collection<BlockTileEntity> getAllBlocks() {
/* 298 */     return Collections2.transform(RESOURCE_REGISTRY.values(), new Function<TeBlockInfo<?>, BlockTileEntity>()
/*     */         {
/*     */           public BlockTileEntity apply(TeBlockRegistry.TeBlockInfo<?> input) {
/* 301 */             return input.getBlock();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static Set<? extends ITeBlock> getAll(ResourceLocation identifier) {
/* 307 */     return RESOURCE_REGISTRY.containsKey(identifier) ? ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).getTeBlocks() : Collections.<ITeBlock>emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   static TeBlockInfo<?> getInfo(ResourceLocation identifier) {
/* 312 */     return RESOURCE_REGISTRY.get(identifier);
/*     */   }
/*     */   
/*     */   static List<ITeBlock> getItems(ResourceLocation identifier) {
/* 316 */     return RESOURCE_REGISTRY.containsKey(identifier) ? ((TeBlockInfo)RESOURCE_REGISTRY.get(identifier)).getIdMap() : Collections.<ITeBlock>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\TeBlockRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */