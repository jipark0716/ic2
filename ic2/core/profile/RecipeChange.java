/*    */ package ic2.core.profile;
/*    */ import java.io.InputStream;
/*    */ import java.io.SequenceInputStream;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public abstract class RecipeChange {
/*    */   public final String name;
/*    */   public final ChangeType type;
/*    */   
/*    */   public enum ChangeType {
/* 13 */     EXTENSION, ADDITION, REMOVAL, REPLACEMENT;
/*    */   }
/*    */   
/*    */   public RecipeChange(String name, ChangeType type) {
/* 17 */     this.name = name;
/* 18 */     this.type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract InputStream getStream();
/*    */ 
/*    */ 
/*    */   
/*    */   static InputStream asStream(ProfileTarget target) {
/*    */     try {
/* 30 */       return target.asStream();
/* 31 */     } catch (IOException e) {
/* 32 */       throw new RuntimeException("Error getting replacement stream for " + target, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class RecipeExtension extends RecipeChange { public final String profile;
/*    */     
/*    */     public RecipeExtension(String name, String profile) {
/* 39 */       super(name, RecipeChange.ChangeType.EXTENSION);
/*    */       
/* 41 */       this.profile = profile;
/*    */     }
/*    */ 
/*    */     
/*    */     public InputStream getStream() {
/* 46 */       throw new UnsupportedOperationException();
/*    */     } }
/*    */ 
/*    */   
/*    */   public static class RecipeReplacement extends RecipeChange {
/*    */     protected final ProfileTarget[] targets;
/*    */     
/*    */     public RecipeReplacement(String name, ProfileTarget... targets) {
/* 54 */       super(name, RecipeChange.ChangeType.REPLACEMENT);
/*    */       
/* 56 */       this.targets = targets;
/*    */     }
/*    */ 
/*    */     
/*    */     public InputStream getStream() {
/* 61 */       switch (this.targets.length) {
/*    */         case 0:
/* 63 */           return new ByteArrayInputStream(new byte[0]);
/*    */         
/*    */         case 1:
/* 66 */           return asStream(this.targets[0]);
/*    */       } 
/*    */       
/* 69 */       return new SequenceInputStream(Collections.enumeration((Collection<? extends InputStream>)Arrays.<ProfileTarget>stream(this.targets).map(RecipeChange::asStream).collect(Collectors.toList())));
/*    */     }
/*    */   }
/*    */   
/*    */   public static class RecipeAddition
/*    */     extends RecipeChange {
/*    */     protected final ProfileTarget[] targets;
/*    */     
/*    */     public RecipeAddition(String name, ProfileTarget... targets) {
/* 78 */       super(name, RecipeChange.ChangeType.ADDITION);
/*    */       
/* 80 */       this.targets = targets;
/*    */     }
/*    */ 
/*    */     
/*    */     public InputStream getStream() {
/* 85 */       switch (this.targets.length) {
/*    */         case 0:
/* 87 */           return null;
/*    */         
/*    */         case 1:
/* 90 */           return asStream(this.targets[0]);
/*    */       } 
/*    */       
/* 93 */       return new SequenceInputStream(Collections.enumeration((Collection<? extends InputStream>)Arrays.<ProfileTarget>stream(this.targets).map(RecipeChange::asStream).collect(Collectors.toList())));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\RecipeChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */