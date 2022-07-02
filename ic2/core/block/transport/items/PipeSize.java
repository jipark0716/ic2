/*    */ package ic2.core.block.transport.items;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum PipeSize
/*    */   implements IIdProvider {
/*  9 */   tiny(1, 0.25F, 0.16666667F),
/* 10 */   small(4, 0.375F, 0.33333334F),
/* 11 */   medium(16, 0.5F, 1.0F),
/* 12 */   large(64, 0.625F, 2.0F); public final int maxStackSize; public final float thickness;
/*    */   
/*    */   PipeSize(int maxStackSize, float thickness, float multiplier) {
/* 15 */     this.maxStackSize = maxStackSize;
/* 16 */     this.thickness = thickness;
/* 17 */     this.multiplier = multiplier;
/*    */   }
/*    */   public final float multiplier; public static final PipeSize[] values; private static final Map<String, PipeSize> nameMap;
/*    */   
/*    */   public String getName() {
/* 22 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 27 */     return ordinal();
/*    */   }
/*    */   
/*    */   public static PipeSize get(String name) {
/* 31 */     return nameMap.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 39 */     values = values();
/* 40 */     nameMap = new HashMap<>();
/*    */ 
/*    */ 
/*    */     
/* 44 */     for (PipeSize type : values)
/* 45 */       nameMap.put(type.getName(), type); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\items\PipeSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */