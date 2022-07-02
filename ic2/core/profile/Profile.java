/*    */ package ic2.core.profile;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Profile {
/*    */   public final String name;
/*    */   public final Set<TextureStyle> textures;
/*    */   
/*    */   public Profile(String name, Set<TextureStyle> textures, Version style, RecipeChange... changes) {
/* 15 */     this.name = name;
/* 16 */     this.textures = textures;
/* 17 */     this.style = style;
/*    */     
/* 19 */     if (changes.length == 0) {
/* 20 */       this.recipeConfigs = Collections.emptyMap();
/* 21 */       this.recipeRemovals = Collections.emptyMap();
/*    */     } else {
/* 23 */       Map<String, List<RecipeChange>> recipeConfigs = new HashMap<>();
/* 24 */       Map<String, List<Object>> recipeRemovals = new HashMap<>();
/*    */       
/* 26 */       for (RecipeChange change : changes) {
/* 27 */         if (change.type != RecipeChange.ChangeType.REMOVAL) {
/* 28 */           ((List<RecipeChange>)recipeConfigs.computeIfAbsent(change.name, k -> new ArrayList())).add(change);
/*    */         } else {
/* 30 */           ((List<RecipeChange>)recipeRemovals.computeIfAbsent(change.name, k -> new ArrayList())).add(change);
/*    */         } 
/*    */       } 
/*    */       
/* 34 */       this.recipeConfigs = !recipeConfigs.isEmpty() ? recipeConfigs : Collections.<String, List<RecipeChange>>emptyMap();
/* 35 */       this.recipeRemovals = !recipeRemovals.isEmpty() ? recipeRemovals : Collections.<String, List<Object>>emptyMap();
/*    */     } 
/*    */   }
/*    */   public final Version style; public final Map<String, List<RecipeChange>> recipeConfigs; public final Map<String, List<Object>> recipeRemovals;
/*    */   public List<RecipeChange> processRecipeConfigs(String name) {
/* 40 */     List<RecipeChange> configs = this.recipeConfigs.get(name);
/* 41 */     if (configs == null) return Collections.emptyList();
/*    */     
/* 43 */     List<RecipeChange> ret = new ArrayList<>();
/* 44 */     for (RecipeChange change : configs) {
/* 45 */       switch (change.type) {
/*    */         case EXTENSION:
/* 47 */           ret.addAll(ProfileManager.getOrError(((RecipeChange.RecipeExtension)change).profile).processRecipeConfigs(name));
/*    */           continue;
/*    */         
/*    */         case ADDITION:
/*    */         case REPLACEMENT:
/* 52 */           ret.add(change);
/*    */           continue;
/*    */       } 
/*    */ 
/*    */       
/* 57 */       throw new IllegalStateException("Unexpected recipe change " + change + " for " + name);
/*    */     } 
/*    */ 
/*    */     
/* 61 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return "Profile: " + this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\Profile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */