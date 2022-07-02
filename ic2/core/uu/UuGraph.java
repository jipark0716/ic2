/*     */ package ic2.core.uu;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UuGraph
/*     */ {
/*     */   public static void build(boolean reset) {
/*  27 */     if (calculation != null) throw new IllegalStateException("uu graph building is already in progress.");
/*     */     
/*  29 */     if (reset) {
/*  30 */       nodes.clear();
/*  31 */       itemNodes.clear();
/*     */     } 
/*     */     
/*  34 */     long startTime = System.nanoTime();
/*  35 */     final List<RecipeTransformation> transformations = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*  39 */     for (IRecipeResolver resolver : UuIndex.instance.resolvers) {
/*  40 */       transformations.addAll(resolver.getTransformations());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  45 */     for (RecipeTransformation transform : transformations) {
/*  46 */       for (LeanItemStack output : transform.outputs) {
/*  47 */         assert output.getMeta() != 32767 : output;
/*     */         
/*  49 */         getInternal(output);
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     for (InitialValue initialValue : initialValues) {
/*  54 */       getInternal(initialValue.stack);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  59 */     for (ILateRecipeResolver resolver : UuIndex.instance.lateResolvers) {
/*  60 */       transformations.addAll(resolver.getTransformations(nodes.keySet()));
/*     */     }
/*     */     
/*  63 */     IC2.log.debug(LogCategory.Uu, "%d UU recipe transformations fetched after %d ms.", new Object[] { Integer.valueOf(transformations.size()), Long.valueOf((System.nanoTime() - startTime) / 1000000L) });
/*     */ 
/*     */ 
/*     */     
/*  67 */     calculation = (IC2.getInstance()).threadPool.submit(new Runnable()
/*     */         {
/*     */           public void run() {
/*  70 */             UuGraph.processRecipes(transformations);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static void set(ItemStack stack, double value) {
/*  76 */     if (stack.func_77952_i() == 32767) throw new IllegalArgumentException("setting values for wilcard meta stacks isn't supported."); 
/*  77 */     if (calculation != null) throw new IllegalStateException("setting values isn't allowed while the calculation is running, set them earlier.");
/*     */     
/*  79 */     initialValues.add(new InitialValue(new LeanItemStack(stack), value));
/*     */   }
/*     */   
/*     */   public static double get(ItemStack stack) {
/*  83 */     finishCalculation();
/*     */     
/*  85 */     LeanItemStack key = new LeanItemStack(stack, 1);
/*  86 */     Node ret = nodes.get(key);
/*  87 */     if (ret == null) return Double.POSITIVE_INFINITY;
/*     */     
/*  89 */     return ret.value;
/*     */   }
/*     */   
/*     */   public static ItemStack find(ItemStack stack) {
/*  93 */     finishCalculation();
/*     */     
/*  95 */     LeanItemStack key = new LeanItemStack(stack, 1);
/*     */     
/*  97 */     Node exactNode = UuGraph.nodes.get(key);
/*     */     
/*  99 */     if (exactNode != null) {
/* 100 */       return exactNode.stack.toMcStack();
/*     */     }
/*     */     
/* 103 */     LeanItemStack search = new LeanItemStack(stack.func_77973_b(), 32767, stack.func_77978_p(), StackUtil.getSize(stack));
/* 104 */     Collection<Node> nodes = getAll(search);
/*     */     
/* 106 */     if (nodes.isEmpty())
/* 107 */       return StackUtil.emptyStack; 
/* 108 */     if (nodes.size() == 1) {
/* 109 */       return ((Node)nodes.iterator().next()).stack.toMcStack();
/*     */     }
/* 111 */     LeanItemStack ret = null;
/* 112 */     int minDmgDiff = Integer.MAX_VALUE;
/*     */     
/* 114 */     for (Node node : nodes) {
/* 115 */       int dmgDiff = Math.abs(StackUtil.getRawMeta(stack) - node.stack.getMeta());
/*     */       
/* 117 */       if (dmgDiff < minDmgDiff) {
/* 118 */         ret = node.stack;
/* 119 */         minDmgDiff = dmgDiff;
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return ret.toMcStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterator<Map.Entry<ItemStack, Double>> iterator() {
/* 128 */     finishCalculation();
/*     */     
/* 130 */     return new ValueIterator();
/*     */   }
/*     */   
/*     */   private static void processRecipes(List<RecipeTransformation> transformations) {
/* 134 */     long startTime = System.nanoTime();
/*     */     
/* 136 */     for (RecipeTransformation transform : transformations) {
/* 137 */       transform.merge();
/* 138 */       registerTransform(transform);
/*     */     } 
/*     */     
/* 141 */     for (InitialValue initialValue : initialValues) {
/* 142 */       getInternal(initialValue.stack).setValue(initialValue.value);
/*     */     }
/*     */     
/* 145 */     initialValues.clear();
/*     */     
/* 147 */     for (Node node : nodes.values()) {
/* 148 */       node.provides = null;
/*     */     }
/*     */     
/* 151 */     IC2.log.debug(LogCategory.Uu, "UU graph built with %d nodes after %d ms.", new Object[] { Integer.valueOf(nodes.size()), Long.valueOf((System.nanoTime() - startTime) / 1000000L) });
/*     */   }
/*     */   
/*     */   private static Node getInternal(LeanItemStack stack) {
/* 155 */     assert stack.getMeta() != 32767;
/*     */     
/* 157 */     stack = stack.copyWithSize(1);
/*     */     
/* 159 */     Node ret = nodes.get(stack);
/*     */     
/* 161 */     if (ret == null) {
/* 162 */       ret = new Node(stack);
/* 163 */       nodes.put(stack, ret);
/*     */       
/* 165 */       Item item = stack.getItem();
/* 166 */       Set<Node> itemNodeSet = itemNodes.get(item);
/*     */       
/* 168 */       if (itemNodeSet == null) {
/* 169 */         itemNodeSet = new HashSet<>(1);
/* 170 */         itemNodes.put(item, itemNodeSet);
/*     */       } 
/*     */       
/* 173 */       itemNodeSet.add(ret);
/*     */     } 
/*     */     
/* 176 */     return ret;
/*     */   }
/*     */   
/*     */   private static Collection<Node> getAll(LeanItemStack stack) {
/* 180 */     if (stack.getMeta() != 32767) {
/* 181 */       return new ArrayList<>(Arrays.asList(new Node[] { getInternal(stack) }));
/*     */     }
/* 183 */     Collection<Node> ret = itemNodes.get(stack.getItem());
/*     */     
/* 185 */     if (ret != null) {
/* 186 */       return ret;
/*     */     }
/* 188 */     return emptyList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerTransform(RecipeTransformation transform) {
/* 194 */     NodeTransform nt = new NodeTransform(transform);
/*     */     
/* 196 */     for (List<LeanItemStack> inputs : transform.inputs) {
/* 197 */       for (LeanItemStack input : inputs) {
/* 198 */         for (Node node : getAll(input))
/*     */         {
/* 200 */           node.provides.add(nt);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     for (LeanItemStack output : transform.outputs) {
/* 206 */       Node node = getInternal(output);
/*     */       
/* 208 */       nt.out.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void finishCalculation() {
/* 213 */     if (calculation != null) {
/*     */       try {
/* 215 */         calculation.get();
/* 216 */       } catch (Exception e) {
/* 217 */         IC2.log.warn(LogCategory.Uu, e, "Calculation failed.");
/* 218 */         nodes.clear();
/* 219 */         itemNodes.clear();
/*     */       } 
/*     */       
/* 222 */       calculation = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Node {
/*     */     Node(LeanItemStack stack) {
/* 228 */       assert stack.getMeta() != 32767;
/*     */       
/* 230 */       this.stack = stack;
/*     */     }
/*     */     
/*     */     void setValue(double value) {
/* 234 */       if (value >= this.value - 1.0E-9D) {
/*     */         return;
/*     */       }
/*     */       
/* 238 */       this.value = value;
/*     */ 
/*     */       
/* 241 */       for (UuGraph.NodeTransform nt : this.provides) {
/* 242 */         for (Node node : nt.out) {
/* 243 */           int outputSize = nt.getOutputSize(node.stack);
/*     */           
/* 245 */           if (outputSize <= 0) {
/* 246 */             IC2.log.warn(LogCategory.Uu, "UU update: Invalid output size %d in recipetransform %s, expected %s.", new Object[] { Integer.valueOf(outputSize), nt.transform, node.stack }); assert false; continue;
/*     */           } 
/* 248 */           if (node.value > value / outputSize)
/* 249 */             node.updateValue(nt, outputSize); 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     LeanItemStack stack;
/*     */     
/*     */     private void updateValue(UuGraph.NodeTransform nt, int outputSize) {
/* 257 */       double newValue = nt.transform.transformCost;
/*     */       
/* 259 */       for (List<LeanItemStack> inputs : nt.transform.inputs) {
/*     */         
/* 261 */         double minValue = Double.POSITIVE_INFINITY;
/*     */         
/* 263 */         for (LeanItemStack input : inputs) {
/* 264 */           double minValue2 = Double.POSITIVE_INFINITY;
/*     */           
/* 266 */           for (Node node : UuGraph.getAll(input)) {
/* 267 */             if (node.value < minValue2) minValue2 = node.value;
/*     */           
/*     */           } 
/* 270 */           minValue2 *= input.getSize();
/*     */           
/* 272 */           if (minValue2 < minValue) minValue = minValue2;
/*     */         
/*     */         } 
/* 275 */         newValue += minValue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 280 */       setValue(newValue / outputSize);
/*     */     }
/*     */ 
/*     */     
/* 284 */     double value = Double.POSITIVE_INFINITY;
/*     */     
/* 286 */     Set<UuGraph.NodeTransform> provides = new HashSet<>();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NodeTransform
/*     */   {
/*     */     RecipeTransformation transform;
/*     */     Set<UuGraph.Node> out;
/*     */     
/*     */     int getOutputSize(LeanItemStack output) {
/*     */       for (LeanItemStack stack : this.transform.outputs) {
/*     */         if (stack.hasSameItem(output)) {
/*     */           return stack.getSize();
/*     */         }
/*     */       } 
/*     */       return 0;
/*     */     }
/*     */     
/*     */     NodeTransform(RecipeTransformation transform) {
/* 305 */       this.out = new HashSet<>();
/*     */       this.transform = transform;
/*     */     } }
/*     */   
/*     */   private static class InitialValue { InitialValue(LeanItemStack stack, double value) {
/* 310 */       this.stack = stack;
/* 311 */       this.value = value;
/*     */     }
/*     */     
/*     */     LeanItemStack stack;
/*     */     double value; }
/*     */ 
/*     */   
/*     */   private static class ValueIterator
/*     */     implements Iterator<Map.Entry<ItemStack, Double>> {
/*     */     public boolean hasNext() {
/* 321 */       return this.parentIterator.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<ItemStack, Double> next() {
/* 326 */       UuGraph.Node node = this.parentIterator.next();
/*     */       
/* 328 */       return new AbstractMap.SimpleImmutableEntry<>(node.stack.toMcStack(), Double.valueOf(node.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 333 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/* 336 */     private final Iterator<UuGraph.Node> parentIterator = UuGraph.nodes.values().iterator();
/*     */     private ValueIterator() {} }
/*     */   
/* 339 */   private static final List<Node> emptyList = Arrays.asList(new Node[0]);
/*     */   
/*     */   private static final double epsilon = 1.0E-9D;
/* 342 */   private static final Map<LeanItemStack, Node> nodes = new HashMap<>();
/* 343 */   private static final Map<Item, Set<Node>> itemNodes = new IdentityHashMap<>();
/* 344 */   private static final List<InitialValue> initialValues = new ArrayList<>();
/* 345 */   private static volatile Future<?> calculation = null;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\UuGraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */