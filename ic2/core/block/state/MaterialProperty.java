/*     */ package ic2.core.block.state;
/*     */ import com.google.common.base.Optional;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.ref.IC2Material;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.PropertyHelper;
/*     */ 
/*     */ public class MaterialProperty extends PropertyHelper<MaterialProperty.WrappedMaterial> implements ISkippableProperty {
/*     */   private final int length;
/*     */   private final List<WrappedMaterial> values;
/*     */   
/*     */   public static final class WrappedMaterial implements Comparable<WrappedMaterial> {
/*     */     private final int id;
/*     */     private final String name;
/*     */     
/*     */     private WrappedMaterial(Material material, String name) {
/*  23 */       if (material instanceof IC2Material) {
/*  24 */         name = ((IC2Material)material).name;
/*     */       }
/*  26 */       this.material = material;
/*  27 */       this.name = name.toLowerCase(Locale.ENGLISH);
/*  28 */       this.id = nextId++;
/*     */     }
/*     */     private final Material material; private static int nextId;
/*     */     public Material getMaterial() {
/*  32 */       return this.material;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  36 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(WrappedMaterial other) {
/*  41 */       return this.id - other.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     private static final Map<Material, WrappedMaterial> MATERIAL_TO_WRAP = new HashMap<>();
/*     */     
/*     */     public static WrappedMaterial get(Material material) {
/*  52 */       WrappedMaterial ret = MATERIAL_TO_WRAP.get(material);
/*     */       
/*  54 */       if (ret == null) {
/*  55 */         ret = new WrappedMaterial(material, material.getClass().getName());
/*  56 */         MATERIAL_TO_WRAP.put(material, ret);
/*     */       } 
/*     */       
/*  59 */       return ret;
/*     */     }
/*     */     
/*     */     public static boolean check(WrappedMaterial state, ITeBlock teBlock) {
/*  63 */       return (teBlock.getMaterial() == state.getMaterial());
/*     */     }
/*     */     
/*     */     static {
/*     */       try {
/*  68 */         for (Field field : Material.class.getFields()) {
/*  69 */           if (field.getType() == Material.class) {
/*  70 */             Material material = (Material)field.get(null);
/*  71 */             MATERIAL_TO_WRAP.put(material, new WrappedMaterial(material, field.getName()));
/*     */           } 
/*     */         } 
/*     */         
/*  75 */         assert !MATERIAL_TO_WRAP.isEmpty();
/*  76 */       } catch (Exception e) {
/*  77 */         throw new RuntimeException("Error building materials name map", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public MaterialProperty(Collection<Material> materials) {
/*  83 */     super("material", WrappedMaterial.class);
/*     */     
/*  85 */     this.values = new ArrayList<>(materials.size());
/*     */     
/*  87 */     for (Material material : materials) {
/*  88 */       this.values.add(WrappedMaterial.get(material));
/*     */     }
/*     */     
/*  91 */     this.length = this.values.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<WrappedMaterial> func_177700_c() {
/*  96 */     return this.values;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<WrappedMaterial> func_185929_b(String value) {
/* 101 */     for (WrappedMaterial material : WrappedMaterial.MATERIAL_TO_WRAP.values()) {
/* 102 */       if (material.getName().equals(value)) {
/* 103 */         return Optional.of(material);
/*     */       }
/*     */     } 
/* 106 */     return Optional.absent();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(WrappedMaterial value) {
/* 111 */     return value.getName();
/*     */   }
/*     */   
/*     */   public int getId(WrappedMaterial material) {
/* 115 */     assert this.values.contains(material);
/* 116 */     return this.values.indexOf(material);
/*     */   }
/*     */   
/*     */   public WrappedMaterial getMaterial(int ID) {
/* 120 */     assert ID >= 0 && ID < this.length;
/* 121 */     return this.values.get(ID % this.length);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\MaterialProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */