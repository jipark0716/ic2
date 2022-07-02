/*    */ package ic2.api.recipe;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ @Deprecated
/*    */ public final class RecipeOutput {
/*    */   public final List<ItemStack> items;
/*    */   
/*    */   public RecipeOutput(NBTTagCompound metadata1, List<ItemStack> items1) {
/* 13 */     assert !items1.contains(null);
/*    */     
/* 15 */     this.metadata = metadata1;
/* 16 */     this.items = items1;
/*    */   }
/*    */   public final NBTTagCompound metadata;
/*    */   public RecipeOutput(NBTTagCompound metadata1, ItemStack... items1) {
/* 20 */     this(metadata1, Arrays.asList(items1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 25 */     if (obj instanceof RecipeOutput) {
/* 26 */       RecipeOutput ro = (RecipeOutput)obj;
/*    */       
/* 28 */       if (this.items.size() == ro.items.size() && ((this.metadata == null && ro.metadata == null) || (this.metadata != null && ro.metadata != null && this.metadata
/* 29 */         .equals(ro.metadata)))) {
/* 30 */         Iterator<ItemStack> itA = this.items.iterator();
/* 31 */         Iterator<ItemStack> itB = ro.items.iterator();
/*    */         
/* 33 */         while (itA.hasNext() && itB.hasNext()) {
/* 34 */           ItemStack stackA = itA.next();
/* 35 */           ItemStack stackB = itB.next();
/*    */           
/* 37 */           if (ItemStack.func_77989_b(stackA, stackB)) return false;
/*    */         
/*    */         } 
/* 40 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "ROutput<" + this.items + "," + this.metadata + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\RecipeOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */