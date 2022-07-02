/*    */ package ic2.core.block.comp;
/*    */ 
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class Kinetic
/*    */   extends TileEntityComponent {
/*    */   public Kinetic(TileEntityBlock parent, double capacity, Set<EnumFacing> sinkDirections, Set<EnumFacing> sourceDirections, int sinkTier, int sourceTier, boolean fullEnergy) {
/* 10 */     super(parent);
/*    */   }
/*    */   
/*    */   private Set<EnumFacing> sinkDirections;
/*    */   private Set<EnumFacing> sourceDirections;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Kinetic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */