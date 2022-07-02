/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.util.Ic2Color;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum CableType
/*    */   implements IIdProvider {
/* 10 */   copper(1, 1, 0.25F, 0.2D, 128),
/* 11 */   glass(0, 0, 0.25F, 0.025D, 8192),
/* 12 */   gold(2, 1, 0.1875F, 0.4D, 512),
/* 13 */   iron(3, 1, 0.375F, 0.8D, 2048),
/* 14 */   tin(1, 1, 0.25F, 0.2D, 32),
/* 15 */   detector(0, 2147483647, 0.5F, 0.5D, 8192),
/* 16 */   splitter(0, 2147483647, 0.5F, 0.5D, 8192); public final int maxInsulation; public final int minColoredInsulation; public final float thickness;
/*    */   
/*    */   CableType(int maxInsulation, int minColoredInsulation, float thickness, double loss, int capacity) {
/* 19 */     this.maxInsulation = maxInsulation;
/* 20 */     this.minColoredInsulation = minColoredInsulation;
/* 21 */     this.thickness = thickness;
/* 22 */     this.loss = loss;
/* 23 */     this.capacity = capacity;
/*    */   }
/*    */   public final double loss; public final int capacity; public static final CableType[] values; private static final Map<String, CableType> nameMap;
/*    */   public String getName(int insulation, Ic2Color color) {
/* 27 */     StringBuilder ret = new StringBuilder(getName());
/*    */     
/* 29 */     ret.append("_cable");
/*    */     
/* 31 */     if (this.maxInsulation != 0) {
/* 32 */       ret.append('_');
/* 33 */       ret.append(insulation);
/*    */     } 
/*    */     
/* 36 */     if (insulation >= this.minColoredInsulation && color != null) {
/* 37 */       ret.append('_');
/* 38 */       ret.append(color.name());
/*    */     } 
/*    */     
/* 41 */     return ret.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 46 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 51 */     return ordinal();
/*    */   }
/*    */   
/*    */   public static CableType get(String name) {
/* 55 */     return nameMap.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 64 */     values = values();
/* 65 */     nameMap = new HashMap<>();
/*    */ 
/*    */     
/* 68 */     for (CableType type : values)
/* 69 */       nameMap.put(type.getName(), type); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\CableType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */