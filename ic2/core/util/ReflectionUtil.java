/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.DataEncoder;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public class ReflectionUtil
/*     */ {
/*     */   public static Field getField(Class<?> clazz, String... names) {
/*  11 */     for (String name : names) {
/*     */       try {
/*  13 */         Field ret = clazz.getDeclaredField(name);
/*  14 */         ret.setAccessible(true);
/*  15 */         return ret;
/*  16 */       } catch (NoSuchFieldException e) {
/*     */       
/*  18 */       } catch (SecurityException e) {
/*  19 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/*  23 */     return null;
/*     */   }
/*     */   
/*     */   public static Field getField(Class<?> clazz, Class<?> type) {
/*  27 */     Field ret = null;
/*     */     
/*  29 */     for (Field field : clazz.getDeclaredFields()) {
/*  30 */       if (type.isAssignableFrom(field.getType())) {
/*  31 */         if (ret != null) return null;
/*     */         
/*  33 */         field.setAccessible(true);
/*  34 */         ret = field;
/*     */       } 
/*     */     } 
/*     */     
/*  38 */     return ret;
/*     */   }
/*     */   
/*     */   public static Field getFieldRecursive(Class<?> clazz, String fieldName) {
/*  42 */     Field ret = null;
/*     */     
/*     */     do {
/*     */       try {
/*  46 */         ret = clazz.getDeclaredField(fieldName);
/*  47 */         ret.setAccessible(true);
/*  48 */       } catch (NoSuchFieldException e) {
/*  49 */         clazz = clazz.getSuperclass();
/*     */       } 
/*  51 */     } while (ret == null && clazz != null);
/*     */     
/*  53 */     return ret;
/*     */   }
/*     */   
/*     */   public static Field getFieldRecursive(Class<?> clazz, Class<?> type, boolean requireUnique) {
/*  57 */     Field ret = null;
/*     */     
/*     */     do {
/*  60 */       for (Field field : clazz.getDeclaredFields()) {
/*  61 */         if (type.isAssignableFrom(field.getType())) {
/*  62 */           if (!requireUnique) {
/*  63 */             field.setAccessible(true);
/*  64 */             return field;
/*  65 */           }  if (ret != null) {
/*  66 */             return null;
/*     */           }
/*  68 */           field.setAccessible(true);
/*  69 */           ret = field;
/*     */         } 
/*     */       } 
/*     */       
/*  73 */       clazz = clazz.getSuperclass();
/*  74 */     } while (ret == null && clazz != null);
/*     */     
/*  76 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T getFieldValue(Field field, Object obj) {
/*     */     try {
/*  82 */       return (T)field.get(obj);
/*  83 */     } catch (Exception e) {
/*  84 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <T> T getValue(Object object, Class<?> type) {
/*  89 */     Field field = getField(object.getClass(), type);
/*  90 */     if (field == null) return null;
/*     */     
/*  92 */     return getFieldValue(field, object);
/*     */   }
/*     */   
/*     */   public static <T> T getValueRecursive(Object object, String fieldName) throws NoSuchFieldException {
/*  96 */     Field field = getFieldRecursive(object.getClass(), fieldName);
/*  97 */     if (field == null) throw new NoSuchFieldException(fieldName);
/*     */     
/*  99 */     return getFieldValue(field, object);
/*     */   }
/*     */   
/*     */   public static <T> T getValueRecursive(Object object, Class<?> type, boolean requireUnique) throws NoSuchFieldException {
/* 103 */     Field field = getFieldRecursive(object.getClass(), type, requireUnique);
/* 104 */     if (field == null) throw new NoSuchFieldException(type.getName());
/*     */     
/* 106 */     return getFieldValue(field, object);
/*     */   }
/*     */   
/*     */   public static void setValue(Object object, Field field, Object value) {
/* 110 */     if (field.getType().isEnum() && value instanceof Integer) {
/* 111 */       value = field.getType().getEnumConstants()[((Integer)value).intValue()];
/*     */     }
/*     */     
/*     */     try {
/* 115 */       Object oldValue = field.get(object);
/*     */       
/* 117 */       if (!DataEncoder.copyValue(value, oldValue)) {
/* 118 */         field.set(object, value);
/*     */       }
/* 120 */     } catch (Exception e) {
/* 121 */       throw new RuntimeException("can't set field " + field.getName() + " in " + object + " to " + value, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean setValueRecursive(Object object, String fieldName, Object value) {
/* 126 */     Field field = getFieldRecursive(object.getClass(), fieldName);
/*     */     
/* 128 */     if (field == null) {
/* 129 */       IC2.log.warn(LogCategory.General, "Can't find field %s in %s to set it to %s.", new Object[] { fieldName, object, value });
/* 130 */       return false;
/*     */     } 
/* 132 */     setValue(object, field, value);
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class<?> clazz, String[] names, Class<?>... parameterTypes) {
/* 138 */     for (String name : names) {
/*     */       try {
/* 140 */         Method ret = clazz.getDeclaredMethod(name, parameterTypes);
/* 141 */         ret.setAccessible(true);
/* 142 */         return ret;
/* 143 */       } catch (NoSuchMethodException e) {
/*     */       
/* 145 */       } catch (SecurityException e) {
/* 146 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\ReflectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */