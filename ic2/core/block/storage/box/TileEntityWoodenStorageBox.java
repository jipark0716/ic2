/*    */ package ic2.core.block.storage.box;
/*    */ 
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class TileEntityWoodenStorageBox extends TileEntityStorageBox {
/*    */   public TileEntityWoodenStorageBox() {
/*  8 */     super(27);
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundType getBlockSound(Entity entity) {
/* 13 */     return SoundType.field_185848_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\storage\box\TileEntityWoodenStorageBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */