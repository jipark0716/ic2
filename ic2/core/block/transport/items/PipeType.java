/*    */ package ic2.core.block.transport.items;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum PipeType
/*    */   implements IIdProvider {
/*  9 */   bronze(2400, 174, 81, 17),
/*    */   
/* 11 */   steel(4800, 128, 128, 128); public final int transferRate; public final int red;
/*    */   public final int green;
/*    */   
/*    */   PipeType(int transferRate, int red, int green, int blue) {
/* 15 */     this.transferRate = transferRate;
/* 16 */     this.red = red;
/* 17 */     this.green = green;
/* 18 */     this.blue = blue;
/*    */   }
/*    */   public final int blue; public static final PipeType[] values; private static final Map<String, PipeType> nameMap;
/*    */   public String getName(PipeSize size) {
/* 22 */     StringBuilder ret = new StringBuilder(getName());
/*    */     
/* 24 */     ret.append("_pipe");
/*    */     
/* 26 */     if (size != null) {
/* 27 */       ret.append('_');
/* 28 */       ret.append(size.name());
/*    */     } 
/*    */     
/* 31 */     return ret.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 36 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 41 */     return ordinal();
/*    */   }
/*    */   
/*    */   public static PipeType get(String name) {
/* 45 */     return nameMap.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 52 */     values = values();
/* 53 */     nameMap = new HashMap<>();
/*    */ 
/*    */ 
/*    */     
/* 57 */     for (PipeType type : values)
/* 58 */       nameMap.put(type.getName(), type); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\items\PipeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */