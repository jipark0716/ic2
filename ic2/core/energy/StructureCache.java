/*     */ package ic2.core.energy;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructureCache
/*     */ {
/*     */   private static final int maxSize = 32;
/*     */   
/*     */   Data get(Set<Integer> activeSources, Set<Integer> activeSinks) {
/*  21 */     Key key = new Key(activeSources, activeSinks);
/*     */     
/*  23 */     Data ret = this.entries.get(key);
/*     */     
/*  25 */     if (ret == null) {
/*  26 */       ret = new Data();
/*  27 */       add(key, ret);
/*  28 */       this.misses++;
/*     */     } else {
/*  30 */       this.hits++;
/*     */     } 
/*     */     
/*  33 */     ret.queries++;
/*     */     
/*  35 */     return ret;
/*     */   }
/*     */   
/*     */   void clear() {
/*  39 */     this.entries.clear();
/*     */   }
/*     */   
/*     */   int size() {
/*  43 */     return this.entries.size();
/*     */   }
/*     */   
/*     */   private void add(Key key, Data data) {
/*  47 */     int min = Integer.MAX_VALUE;
/*  48 */     Key minKey = null;
/*     */     
/*  50 */     if (this.entries.size() >= 32) {
/*  51 */       for (Map.Entry<Key, Data> entry : this.entries.entrySet()) {
/*  52 */         if (((Data)entry.getValue()).queries < min) {
/*  53 */           min = ((Data)entry.getValue()).queries;
/*  54 */           minKey = entry.getKey();
/*     */         } 
/*     */       } 
/*     */       
/*  58 */       this.entries.remove(minKey);
/*     */     } 
/*     */     
/*  61 */     this.entries.put(new Key(key), data);
/*     */   }
/*     */   
/*  64 */   Map<Key, Data> entries = new HashMap<>();
/*  65 */   int hits = 0;
/*  66 */   int misses = 0;
/*     */   
/*     */   static class Key { final Set<Integer> activeSources;
/*     */     
/*     */     Key(Set<Integer> activeSources1, Set<Integer> activeSinks1) {
/*  71 */       this.activeSources = activeSources1;
/*  72 */       this.activeSinks = activeSinks1;
/*  73 */       this.hashCode = this.activeSources.hashCode() * 31 + this.activeSinks.hashCode();
/*     */     }
/*     */     final Set<Integer> activeSinks; final int hashCode;
/*     */     Key(Key key) {
/*  77 */       this.activeSources = new HashSet<>(key.activeSources);
/*  78 */       this.activeSinks = new HashSet<>(key.activeSinks);
/*  79 */       this.hashCode = key.hashCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  84 */       return this.hashCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  89 */       if (!(o instanceof Key)) return false;
/*     */       
/*  91 */       Key key = (Key)o;
/*     */       
/*  93 */       return (key.activeSources.equals(this.activeSources) && key.activeSinks.equals(this.activeSinks));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Data
/*     */   {
/*     */     boolean isInitialized = false;
/*     */ 
/*     */     
/*     */     Map<Integer, Node> optimizedNodes;
/*     */     
/*     */     List<Node> activeNodes;
/*     */     
/*     */     DenseMatrix64F networkMatrix;
/*     */     
/*     */     DenseMatrix64F sourceMatrix;
/*     */     
/*     */     DenseMatrix64F resultMatrix;
/*     */     
/*     */     LinearSolver<DenseMatrix64F> solver;
/*     */     
/* 116 */     int queries = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\StructureCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */