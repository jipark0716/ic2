/*     */ package ic2.core.uu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class RecipeTransformation {
/*     */   public final double transformCost;
/*     */   
/*     */   public RecipeTransformation(double transformCost, List<List<LeanItemStack>> inputs, LeanItemStack... outputs) {
/*  12 */     this(transformCost, inputs, Arrays.asList(outputs));
/*     */   }
/*     */   public List<List<LeanItemStack>> inputs; public List<LeanItemStack> outputs;
/*     */   public RecipeTransformation(double transformCost, List<List<LeanItemStack>> inputs, List<LeanItemStack> outputs) {
/*  16 */     this.transformCost = transformCost;
/*  17 */     this.inputs = inputs;
/*  18 */     this.outputs = outputs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void merge() {
/*  25 */     List<List<LeanItemStack>> cleanInputs = new ArrayList<>();
/*     */     
/*  27 */     for (List<LeanItemStack> inputList : this.inputs) {
/*  28 */       boolean found = false;
/*     */       
/*  30 */       for (ListIterator<List<LeanItemStack>> it = cleanInputs.listIterator(); it.hasNext(); ) {
/*  31 */         List<LeanItemStack> cleanInputList = it.next();
/*  32 */         cleanInputList = mergeEqualLists(inputList, cleanInputList);
/*     */         
/*  34 */         if (cleanInputList != null) {
/*  35 */           found = true;
/*  36 */           it.set(cleanInputList);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  41 */       if (!found) cleanInputs.add(inputList);
/*     */     
/*     */     } 
/*  44 */     for (List<LeanItemStack> inputList : this.inputs) {
/*  45 */       for (List<LeanItemStack> cleanInputList : cleanInputs) {
/*  46 */         List<LeanItemStack> unmatched = new LinkedList<>(inputList);
/*  47 */         boolean found = false;
/*     */         
/*  49 */         for (LeanItemStack stackOffer : cleanInputList) {
/*  50 */           found = false;
/*     */           
/*  52 */           for (Iterator<LeanItemStack> it = unmatched.iterator(); it.hasNext(); ) {
/*  53 */             LeanItemStack stackReq = it.next();
/*     */             
/*  55 */             if (stackOffer.hasSameItem(stackReq)) {
/*  56 */               found = true;
/*  57 */               it.remove();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*  62 */           if (!found)
/*     */             break; 
/*     */         } 
/*     */       } 
/*     */     } 
/*  67 */     this.inputs = cleanInputs;
/*  68 */     List<LeanItemStack> cleanOutputs = new ArrayList<>();
/*     */     
/*  70 */     for (LeanItemStack output : this.outputs) {
/*  71 */       boolean found = false;
/*     */       
/*  73 */       for (ListIterator<LeanItemStack> it = cleanOutputs.listIterator(); it.hasNext(); ) {
/*  74 */         LeanItemStack stack = it.next();
/*     */         
/*  76 */         if (output.hasSameItem(stack)) {
/*  77 */           found = true;
/*  78 */           it.set(stack.copyWithSize(stack.getSize() + output.getSize()));
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  83 */       if (!found) cleanOutputs.add(output);
/*     */     
/*     */     } 
/*  86 */     this.outputs = cleanOutputs;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     return "{ " + this.transformCost + " + " + this.inputs + " -> " + this.outputs + " }";
/*     */   }
/*     */   
/*     */   private List<LeanItemStack> mergeEqualLists(List<LeanItemStack> listA, List<LeanItemStack> listB) {
/*  95 */     if (listA.size() != listB.size()) return null;
/*     */     
/*  97 */     List<LeanItemStack> ret = new ArrayList<>(listA.size());
/*  98 */     List<LeanItemStack> listBCopy = new LinkedList<>(listB);
/*     */     
/* 100 */     for (LeanItemStack a : listA) {
/* 101 */       boolean found = false;
/*     */       
/* 103 */       for (Iterator<LeanItemStack> it = listBCopy.iterator(); it.hasNext(); ) {
/* 104 */         LeanItemStack b = it.next();
/*     */         
/* 106 */         if (a.hasSameItem(b)) {
/* 107 */           found = true;
/* 108 */           ret.add(a.copyWithSize(a.getSize() + b.getSize()));
/* 109 */           it.remove();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 114 */       if (!found) return null;
/*     */     
/*     */     } 
/* 117 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\RecipeTransformation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */