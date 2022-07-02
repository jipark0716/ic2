/*    */ package ic2.api.tile;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ExplosionWhitelist
/*    */ {
/*    */   public static void addWhitelistedBlock(Block block) {
/* 24 */     whitelist.add(block);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void removeWhitelistedBlock(Block block) {
/* 33 */     whitelist.remove(block);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isBlockWhitelisted(Block block) {
/* 43 */     return whitelist.contains(block);
/*    */   }
/*    */   
/* 46 */   private static Set<Block> whitelist = Collections.newSetFromMap(new IdentityHashMap<>());
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\tile\ExplosionWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */