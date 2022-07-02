/*    */ package ic2.core.network;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeUpdateDataServer
/*    */ {
/*    */   void addGlobalField(String name) {
/* 16 */     if (!this.globalFields.add(name))
/*    */       return; 
/* 18 */     if (!this.playerFieldMap.isEmpty()) {
/* 19 */       for (Set<String> playerFields : this.playerFieldMap.values()) {
/* 20 */         playerFields.remove(name);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   void addPlayerField(String name, EntityPlayerMP player) {
/* 26 */     if (this.globalFields.contains(name))
/*    */       return; 
/* 28 */     Set<String> playerFields = this.playerFieldMap.get(player);
/*    */     
/* 30 */     if (playerFields == null) {
/* 31 */       playerFields = new HashSet<>();
/* 32 */       this.playerFieldMap.put(player, playerFields);
/*    */     } 
/*    */     
/* 35 */     playerFields.add(name);
/*    */   }
/*    */   
/*    */   Collection<String> getGlobalFields() {
/* 39 */     return this.globalFields;
/*    */   }
/*    */   
/*    */   Collection<String> getPlayerFields(EntityPlayerMP player) {
/* 43 */     Set<String> ret = this.playerFieldMap.get(player);
/*    */     
/* 45 */     if (ret == null) {
/* 46 */       return Collections.emptyList();
/*    */     }
/* 48 */     return ret;
/*    */   }
/*    */ 
/*    */   
/* 52 */   private final Set<String> globalFields = new HashSet<>();
/* 53 */   private final Map<EntityPlayerMP, Set<String>> playerFieldMap = new IdentityHashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\TeUpdateDataServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */