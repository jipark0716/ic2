/*    */ package ic2.core.block.state;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStateUtil
/*    */ {
/*    */   public static String getVariantString(IBlockState state) {
/* 16 */     ImmutableMap<IProperty<?>, Comparable<?>> properties = state.func_177228_b();
/* 17 */     if (properties.isEmpty()) return "normal";
/*    */     
/* 19 */     StringBuilder ret = new StringBuilder();
/*    */     
/* 21 */     for (UnmodifiableIterator<Map.Entry<IProperty<?>, Comparable<?>>> unmodifiableIterator = properties.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<IProperty<?>, Comparable<?>> entry = unmodifiableIterator.next();
/*    */       
/* 23 */       IProperty property = entry.getKey();
/*    */       
/* 25 */       if (ret.length() > 0) ret.append(',');
/*    */       
/* 27 */       ret.append(property.func_177701_a());
/* 28 */       ret.append('=');
/* 29 */       ret.append(property.func_177702_a(entry.getValue())); }
/*    */ 
/*    */     
/* 32 */     return ret.toString();
/*    */   }
/*    */   
/*    */   public static IBlockState getState(Block block, String variant) {
/* 36 */     IBlockState ret = block.func_176223_P();
/*    */     
/* 38 */     if (variant.isEmpty() || variant.equals("normal")) return ret;
/*    */     
/* 40 */     int pos = 0;
/*    */     
/* 42 */     while (pos < variant.length()) {
/* 43 */       int nextPos = variant.indexOf(',', pos);
/* 44 */       if (nextPos == -1) nextPos = variant.length();
/*    */       
/* 46 */       int sepPos = variant.indexOf('=', pos);
/* 47 */       if (sepPos == -1 || sepPos >= nextPos) return null;
/*    */       
/* 49 */       String name = variant.substring(pos, sepPos);
/* 50 */       String value = variant.substring(sepPos + 1, nextPos);
/*    */       
/* 52 */       ret = applyProperty(ret, name, value);
/*    */       
/* 54 */       pos = nextPos + 1;
/*    */     } 
/*    */     
/* 57 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T extends Comparable<T>> IBlockState applyProperty(IBlockState state, String name, String value) {
/* 62 */     IProperty<T> property = null;
/*    */     
/* 64 */     for (IProperty<?> cProperty : (Iterable<IProperty<?>>)state.func_177227_a()) {
/* 65 */       if (cProperty.func_177701_a().equals(name)) {
/* 66 */         property = (IProperty)cProperty;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 71 */     if (property == null) {
/* 72 */       return state;
/*    */     }
/*    */     
/* 75 */     for (Comparable comparable : property.func_177700_c()) {
/* 76 */       if (value.equals(property.func_177702_a(comparable))) {
/* 77 */         return state.func_177226_a(property, comparable);
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return state;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\BlockStateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */