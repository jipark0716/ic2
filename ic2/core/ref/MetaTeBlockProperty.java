/*     */ package ic2.core.ref;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class MetaTeBlockProperty
/*     */   implements IProperty<MetaTeBlock>
/*     */ {
/*     */   private final Collection<MetaTeBlock> allowedValues;
/*     */   private final String resourceLocationName;
/*     */   
/*     */   public MetaTeBlockProperty(final ResourceLocation identifier) {
/*  28 */     this.resourceLocationName = identifier.toString();
/*  29 */     this.allowedValues = new AbstractCollection<MetaTeBlock>()
/*     */       {
/*     */         public Iterator<MetaTeBlock> iterator() {
/*  32 */           return new Iterator<MetaTeBlock>() { private int teBlockIdx;
/*     */               
/*     */               public boolean hasNext() {
/*  35 */                 return (this.teBlockIdx < this.allTeBlockSize);
/*     */               }
/*     */               private boolean active;
/*     */               
/*     */               public MetaTeBlock next() {
/*  40 */                 if (!hasNext()) throw new NoSuchElementException();
/*     */                 
/*  42 */                 MetaTeBlockProperty.MetaTePair teBlockPair = this.teBlockMap.get(this.teBlockIdx);
/*  43 */                 MetaTeBlock ret = teBlockPair.getState(this.active);
/*     */                 
/*  45 */                 if (!this.active && teBlockPair.hasActive()) {
/*  46 */                   this.active = true;
/*     */                 } else {
/*  48 */                   this.active = false;
/*  49 */                   this.teBlockIdx++;
/*     */                 } 
/*     */                 
/*  52 */                 return ret;
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/*  57 */                 throw new UnsupportedOperationException("Cannot remove a MetaTeBlock state.");
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  63 */               private final List<MetaTeBlockProperty.MetaTePair> teBlockMap = (List<MetaTeBlockProperty.MetaTePair>)(MetaTeBlockProperty.resourceToTeBlock.get(identifier)).b;
/*  64 */               private final int allTeBlockSize = this.teBlockMap.size(); }
/*     */             ;
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/*  70 */           return this.trueSize;
/*     */         }
/*     */         
/*  73 */         private final int trueSize = ((Integer)(MetaTeBlockProperty.resourceToTeBlock.get(identifier)).a).intValue();
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_177701_a() {
/*  79 */     return "type";
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<MetaTeBlock> func_177700_c() {
/*  84 */     return this.allowedValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<MetaTeBlock> func_177699_b() {
/*  89 */     return MetaTeBlock.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<MetaTeBlock> func_185929_b(String value) {
/*  94 */     for (MetaTeBlock block : this.allowedValues) {
/*  95 */       if (getName(block).equals(value)) {
/*  96 */         return Optional.of(block);
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return Optional.absent();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(MetaTeBlock value) {
/* 105 */     if (value.active) {
/* 106 */       return value.teBlock.getName() + "_active";
/*     */     }
/* 108 */     return value.teBlock.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return "MetaTeBlockProperty{For " + this.resourceLocationName + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<MetaTePair> getAllStates(ResourceLocation identifier) {
/* 125 */     return (List<MetaTePair>)((Tuple.T2)resourceToTeBlock.get(identifier)).b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MetaTeBlock getState(ITeBlock teBlock) {
/* 133 */     return getState(teBlock, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MetaTeBlock getState(ITeBlock teBlock, boolean active) {
/* 142 */     MetaTePair state = teResourceMapping.get(teBlock);
/* 143 */     if (state == null) return invalid; 
/* 144 */     return state.getState(active);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   private static final Map<ResourceLocation, Tuple.T2<Integer, List<MetaTePair>>> resourceToTeBlock = new HashMap<>();
/* 155 */   private static final Map<ITeBlock, MetaTePair> teResourceMapping = new IdentityHashMap<>(); public static final MetaTeBlock invalid;
/*     */   static {
/* 157 */     for (Map.Entry<ResourceLocation, Set<? extends ITeBlock>> blocks : (Iterable<Map.Entry<ResourceLocation, Set<? extends ITeBlock>>>)TeBlockRegistry.getAll()) {
/* 158 */       List<MetaTePair> locationBlocks = new ArrayList<>(((Set)blocks.getValue()).size());
/* 159 */       int states = 0;
/*     */       
/* 161 */       for (ITeBlock block : blocks.getValue()) {
/*     */         MetaTePair lastIn;
/* 163 */         if (block.hasActive()) {
/* 164 */           states += 2;
/*     */           
/* 166 */           locationBlocks.add(lastIn = new MetaTePair(block, true));
/*     */         } else {
/* 168 */           states++;
/*     */           
/* 170 */           locationBlocks.add(lastIn = new MetaTePair(block, false));
/*     */         } 
/* 172 */         teResourceMapping.put(block, lastIn);
/*     */       } 
/*     */       
/* 175 */       resourceToTeBlock.put(blocks.getKey(), new Tuple.T2(Integer.valueOf(states), locationBlocks));
/*     */     } 
/*     */     
/* 178 */     MetaTePair invalidStates = teResourceMapping.get(TeBlock.invalid);
/* 179 */     invalid = invalidStates.inactive;
/* 180 */     assert invalid != null : "Failed to properly map ITeBlocks to MetaTeBlocks!";
/*     */ 
/*     */     
/* 183 */     for (Map.Entry<ResourceLocation, Tuple.T2<Integer, List<MetaTePair>>> type : resourceToTeBlock.entrySet()) {
/* 184 */       if (type.getKey() != invalid.teBlock.getIdentifier()) {
/* 185 */         Tuple.T2 t2 = type.getValue(); Integer integer = (Integer)t2.a; Object object = t2.a = Integer.valueOf(((Integer)t2.a).intValue() + 1);
/* 186 */         ((List<MetaTePair>)((Tuple.T2)type.getValue()).b).add(invalidStates);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class MetaTePair {
/*     */     public final MetaTeBlock inactive;
/*     */     public final MetaTeBlock active;
/*     */     private final boolean hasActive;
/*     */     
/*     */     public MetaTePair(ITeBlock block, boolean active) {
/* 197 */       this.inactive = new MetaTeBlock(block, false);
/* 198 */       this.active = active ? new MetaTeBlock(block, true) : null;
/*     */       
/* 200 */       this.hasActive = active;
/*     */     }
/*     */     
/*     */     public ITeBlock getBlock() {
/* 204 */       return this.inactive.teBlock;
/*     */     }
/*     */     
/*     */     public MetaTeBlock getState(boolean active) {
/* 208 */       return (active && this.hasActive) ? this.active : this.inactive;
/*     */     }
/*     */     
/*     */     boolean hasActive() {
/* 212 */       return this.hasActive;
/*     */     }
/*     */     
/*     */     public boolean hasItem() {
/* 216 */       return getBlock().hasItem();
/*     */     }
/*     */     
/*     */     public ResourceLocation getIdentifier() {
/* 220 */       return getBlock().getIdentifier();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 224 */       return getBlock().getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\MetaTeBlockProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */