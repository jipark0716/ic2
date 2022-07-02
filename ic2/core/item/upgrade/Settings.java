/*    */ package ic2.core.item.upgrade;
/*    */ 
/*    */ public class Settings {
/*    */   public final boolean active;
/*    */   public final ComparisonType comparison;
/*    */   public final String mainBox;
/*    */   
/*    */   public Settings(NBTTagCompound nbt) {
/*  9 */     this.active = nbt.func_74767_n("active");
/*    */     
/* 11 */     if (!this.active) {
/* 12 */       this.comparison = ComparisonType.IGNORED;
/* 13 */       this.mainBox = this.extraBox = "";
/* 14 */       this.main = this.extra = ComparisonSettings.DEFAULT;
/*    */     } else {
/* 16 */       if (!nbt.func_150297_b("type", 1)) {
/* 17 */         this.comparison = ComparisonType.DIRECT;
/*    */       } else {
/* 19 */         this.comparison = ComparisonType.getFromNBT(nbt.func_74771_c("type"));
/*    */       } 
/*    */       
/* 22 */       switch (this.comparison) {
/*    */         case DIRECT:
/* 24 */           this.mainBox = this.extraBox = "";
/* 25 */           this.main = this.extra = ComparisonSettings.DEFAULT;
/*    */           return;
/*    */         
/*    */         case COMPARISON:
/* 29 */           this.mainBox = nbt.func_74779_i("normal");
/* 30 */           this.extraBox = "";
/* 31 */           this.main = ComparisonSettings.getFromNBT(nbt.func_74771_c("normalComp"));
/* 32 */           this.extra = ComparisonSettings.DEFAULT;
/*    */           return;
/*    */         
/*    */         case RANGE:
/* 36 */           this.mainBox = nbt.func_74779_i("normal");
/* 37 */           this.extraBox = nbt.func_74779_i("extra");
/* 38 */           this.main = ComparisonSettings.getFromNBT(nbt.func_74771_c("normalComp"));
/* 39 */           this.extra = ComparisonSettings.getFromNBT(nbt.func_74771_c("extraComp"));
/*    */           return;
/*    */       } 
/*    */       
/* 43 */       throw new IllegalStateException("Unexpected comparison type " + this.comparison);
/*    */     } 
/*    */   }
/*    */   public final String extraBox; public final ComparisonSettings main; public final ComparisonSettings extra;
/*    */   
/*    */   public boolean doComparison(int value) {
/* 49 */     switch (this.comparison) {
/*    */       case COMPARISON:
/* 51 */         return this.main.compare(Integer.parseInt(this.mainBox), value);
/*    */       
/*    */       case RANGE:
/* 54 */         return (this.main.compare(Integer.parseInt(this.mainBox), value) && this.extra.compare(value, Integer.parseInt(this.extraBox)));
/*    */     } 
/*    */     
/* 57 */     throw new IllegalStateException("Unexpected comparison type " + this.comparison);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\Settings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */