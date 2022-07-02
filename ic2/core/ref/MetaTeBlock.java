/*    */ package ic2.core.ref;
/*    */ 
/*    */ public class MetaTeBlock implements Comparable<MetaTeBlock> {
/*    */   public final ITeBlock teBlock;
/*    */   
/*    */   MetaTeBlock(ITeBlock teBlock, boolean active) {
/*  7 */     this.teBlock = teBlock;
/*  8 */     this.active = active;
/*    */   }
/*    */   public final boolean active;
/*    */   
/*    */   public int compareTo(MetaTeBlock o) {
/* 13 */     int ret = this.teBlock.getId() - o.teBlock.getId();
/* 14 */     if (ret != 0) return ret;
/*    */     
/* 16 */     return Boolean.compare(this.active, o.active);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     StringBuilder ret = (new StringBuilder("MetaTeBlock{")).append(this.teBlock.getName());
/* 22 */     if (this.active) {
/* 23 */       ret.append("_active");
/*    */     }
/* 25 */     return ret.append('}').toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\MetaTeBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */