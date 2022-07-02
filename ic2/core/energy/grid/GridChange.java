/*    */ package ic2.core.energy.grid;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ class GridChange {
/*    */   final Type type;
/*    */   final BlockPos pos;
/*    */   
/*    */   GridChange(Type type, BlockPos pos, IEnergyTile ioTile) {
/* 11 */     this.type = type;
/* 12 */     this.pos = pos;
/* 13 */     this.ioTile = ioTile;
/*    */   }
/*    */   final IEnergyTile ioTile; List<IEnergyTile> subTiles;
/*    */   
/* 17 */   enum Type { ADDITION,
/* 18 */     REMOVAL; }
/*    */ 
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\GridChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */