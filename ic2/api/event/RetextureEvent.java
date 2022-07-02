/*    */ package ic2.api.event;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class RetextureEvent extends WorldEvent {
/*    */   public final BlockPos pos;
/*    */   
/*    */   public RetextureEvent(World world, BlockPos pos, IBlockState state, EnumFacing side, EntityPlayer player, IBlockState refState, String refVariant, EnumFacing refSide, int[] refColorMultipliers) {
/* 16 */     super(world);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     this.applied = false;
/*    */     if (world == null)
/*    */       throw new NullPointerException("null world"); 
/*    */     if (world.field_72995_K)
/*    */       throw new IllegalStateException("remote world"); 
/*    */     if (pos == null)
/*    */       throw new NullPointerException("null pos"); 
/*    */     if (state == null)
/*    */       throw new NullPointerException("null state"); 
/*    */     if (side == null)
/*    */       throw new NullPointerException("null side"); 
/*    */     if (refState == null)
/*    */       throw new NullPointerException("null refState"); 
/*    */     if (refVariant == null)
/*    */       throw new NullPointerException("null refVariant"); 
/*    */     if (refSide == null)
/*    */       throw new NullPointerException("null refSide"); 
/*    */     if (refColorMultipliers == null)
/*    */       throw new NullPointerException("null refColorMultipliers"); 
/*    */     this.pos = pos;
/*    */     this.state = state;
/*    */     this.side = side;
/*    */     this.player = player;
/*    */     this.refState = refState;
/*    */     this.refVariant = refVariant;
/*    */     this.refSide = refSide;
/*    */     this.refColorMultipliers = refColorMultipliers;
/*    */   }
/*    */   
/*    */   public final IBlockState state;
/*    */   public final EnumFacing side;
/*    */   public final EntityPlayer player;
/*    */   public final IBlockState refState;
/*    */   public final String refVariant;
/*    */   public final EnumFacing refSide;
/*    */   public final int[] refColorMultipliers;
/*    */   public boolean applied;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\event\RetextureEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */