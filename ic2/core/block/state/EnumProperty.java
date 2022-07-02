/*     */ package ic2.core.block.state;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import gnu.trove.map.TIntObjectMap;
/*     */ import gnu.trove.map.hash.TIntObjectHashMap;
/*     */ import ic2.core.profile.Version;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.block.properties.PropertyHelper;
/*     */ 
/*     */ public class EnumProperty<T extends Enum<T> & IIdProvider> extends PropertyHelper<T> {
/*     */   private final List<T> values;
/*     */   
/*     */   public EnumProperty(String name, Class<T> cls) {
/*  17 */     super(name, cls);
/*     */     
/*  19 */     Enum[] arrayOfEnum = (Enum[])cls.getEnumConstants();
/*  20 */     if (arrayOfEnum == null || arrayOfEnum.length == 0) throw new IllegalArgumentException("No enum constants for " + cls);
/*     */     
/*  22 */     this.values = Arrays.asList((T[])arrayOfEnum);
/*  23 */     boolean idsMatchOrdinal = true;
/*     */     
/*  25 */     for (int i = 0; i < arrayOfEnum.length; i++) {
/*  26 */       if (((IIdProvider)arrayOfEnum[i]).getId() != i) {
/*  27 */         idsMatchOrdinal = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  32 */     if (idsMatchOrdinal) {
/*  33 */       this.reverseMap = null;
/*     */     } else {
/*  35 */       this.reverseMap = (TIntObjectMap<T>)new TIntObjectHashMap(arrayOfEnum.length);
/*     */       
/*  37 */       for (Enum enum_ : arrayOfEnum) {
/*  38 */         this.reverseMap.put(((IIdProvider)enum_).getId(), enum_);
/*     */       }
/*     */       
/*  41 */       if (this.reverseMap.size() != arrayOfEnum.length) throw new IllegalArgumentException("The enum " + cls + " provides non-unique ids"); 
/*     */     } 
/*     */   }
/*     */   private final TIntObjectMap<T> reverseMap;
/*     */   
/*     */   public List<T> getAllowedValues() {
/*  47 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> getShownValues() {
/*  56 */     Class<T> valueClass = func_177699_b();
/*  57 */     boolean defaultState = Version.shouldEnable(valueClass);
/*     */     
/*  59 */     return (List<T>)this.values.stream().filter(value -> {
/*     */           try {
/*     */             return Version.shouldEnable(valueClass.getField(value.name()), defaultState);
/*  62 */           } catch (NoSuchFieldException e) {
/*     */             throw new RuntimeException("Impossible missing enum field!", e);
/*     */           } 
/*  65 */         }).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(T value) {
/*  70 */     return ((IIdProvider)value).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<T> func_185929_b(String value) {
/*  75 */     return Optional.fromNullable(getValue(value));
/*     */   }
/*     */   
/*     */   public T getValue(int id) {
/*  79 */     if (this.reverseMap == null) {
/*  80 */       if (id >= 0 && id < this.values.size()) {
/*  81 */         return this.values.get(id);
/*     */       }
/*  83 */       return null;
/*     */     } 
/*     */     
/*  86 */     return (T)this.reverseMap.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public T getValueOrDefault(int id) {
/*  91 */     T ret = getValue(id);
/*     */     
/*  93 */     return (ret != null) ? ret : getDefault();
/*     */   }
/*     */   
/*     */   public T getValue(String name) {
/*  97 */     for (Enum enum_ : this.values) {
/*  98 */       if (((IIdProvider)enum_).getName().equals(name)) return (T)enum_;
/*     */     
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public T getValueOrDefault(String name) {
/* 105 */     T ret = getValue(name);
/*     */     
/* 107 */     return (ret != null) ? ret : getDefault();
/*     */   }
/*     */   
/*     */   public T getDefault() {
/* 111 */     return this.values.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\EnumProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */