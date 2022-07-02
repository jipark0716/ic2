/*    */ package ic2.core.util;
/*    */ 
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class WorldSearchUtil {
/*    */   public static void findTileEntities(World world, BlockPos center, int range, ITileEntityResultHandler handler) {
/* 10 */     int minX = center.func_177958_n() - range;
/* 11 */     int minY = center.func_177956_o() - range;
/* 12 */     int minZ = center.func_177952_p() - range;
/* 13 */     int maxX = center.func_177958_n() + range;
/* 14 */     int maxY = center.func_177956_o() + range;
/* 15 */     int maxZ = center.func_177952_p() + range;
/*    */     
/* 17 */     int xS = minX >> 4;
/* 18 */     int zS = minZ >> 4;
/* 19 */     int xE = maxX >> 4;
/* 20 */     int zE = maxZ >> 4;
/*    */     
/* 22 */     for (int x = xS; x <= xE; x++) {
/* 23 */       for (int z = zS; z <= zE; z++) {
/* 24 */         Chunk chunk = world.func_72964_e(x, z);
/*    */         
/* 26 */         for (TileEntity te : chunk.func_177434_r().values()) {
/* 27 */           BlockPos pos = te.func_174877_v();
/*    */           
/* 29 */           if (pos.func_177956_o() >= minY && pos.func_177956_o() <= maxY && pos
/* 30 */             .func_177958_n() >= minX && pos.func_177958_n() <= maxX && pos
/* 31 */             .func_177952_p() >= minZ && pos.func_177952_p() <= maxZ && 
/* 32 */             handler.onMatch(te))
/*    */             return; 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface ITileEntityResultHandler {
/*    */     boolean onMatch(TileEntity param1TileEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\WorldSearchUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */