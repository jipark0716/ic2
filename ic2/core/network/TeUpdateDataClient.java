/*    */ package ic2.core.network;
/*    */ 
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ class TeUpdateDataClient
/*    */ {
/*    */   public TeData addTe(BlockPos pos, int fieldCount) {
/* 14 */     TeData ret = new TeData(pos, fieldCount);
/* 15 */     this.updates.add(ret);
/* 16 */     return ret;
/*    */   }
/*    */   
/*    */   public Collection<TeData> getTes() {
/* 20 */     return this.updates;
/*    */   }
/*    */   static class TeData { final BlockPos pos;
/*    */     
/*    */     private TeData(BlockPos pos, int fieldCount) {
/* 25 */       this.pos = pos;
/* 26 */       this.fields = new ArrayList<>(fieldCount);
/*    */     }
/*    */     private final List<TeUpdateDataClient.FieldData> fields; Class<? extends TileEntityBlock> teClass;
/*    */     public void addField(String name, Object value) {
/* 30 */       this.fields.add(new TeUpdateDataClient.FieldData(name, value));
/*    */     }
/*    */     
/*    */     public Collection<TeUpdateDataClient.FieldData> getFields() {
/* 34 */       return this.fields;
/*    */     } }
/*    */ 
/*    */   
/*    */   static class FieldData {
/*    */     final String name;
/*    */     final Object value;
/*    */     Field field;
/*    */     
/*    */     private FieldData(String name, Object value) {
/* 44 */       this.name = name;
/* 45 */       this.value = value;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   private final List<TeData> updates = new ArrayList<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\TeUpdateDataClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */