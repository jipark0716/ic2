/*    */ package ic2.core.block.beam;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*    */ import java.util.Collections;
/*    */ import java.util.EnumMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ 
/*    */ public class TileAccelerator
/*    */   extends TileEntityElectricMachine
/*    */ {
/*    */   public TileAccelerator() {
/* 15 */     super(5000, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/* 20 */     return FACING_AABBs.get(getFacing().func_176740_k());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getLightOpacity() {
/* 25 */     return 0;
/*    */   }
/*    */   
/*    */   private static Map<EnumFacing.Axis, List<AxisAlignedBB>> makeAABBMap() {
/* 29 */     Map<EnumFacing.Axis, List<AxisAlignedBB>> ret = new EnumMap<>(EnumFacing.Axis.class);
/*    */     
/* 31 */     ret.put(EnumFacing.Axis.X, Collections.singletonList(new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D)));
/* 32 */     ret.put(EnumFacing.Axis.Y, Collections.singletonList(new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 0.75D, 1.0D)));
/* 33 */     ret.put(EnumFacing.Axis.Z, Collections.singletonList(new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D)));
/*    */ 
/*    */     
/* 36 */     return ret;
/*    */   }
/*    */   
/* 39 */   private static final Map<EnumFacing.Axis, List<AxisAlignedBB>> FACING_AABBs = makeAABBMap();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\beam\TileAccelerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */