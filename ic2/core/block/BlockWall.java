/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.api.event.RetextureEvent;
/*    */ import ic2.core.Ic2Player;
/*    */ import ic2.core.item.block.ItemBlockTileEntity;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.util.Ic2Color;
/*    */ import net.minecraft.block.SoundType;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWall
/*    */   extends BlockMultiID<Ic2Color>
/*    */ {
/*    */   public static BlockWall create() {
/* 29 */     return BlockMultiID.<Ic2Color, BlockWall>create(BlockWall.class, Ic2Color.class, new Object[0]);
/*    */   }
/*    */   
/*    */   private BlockWall() {
/* 33 */     super(BlockName.wall, Material.field_151576_e);
/*    */     
/* 35 */     func_149711_c(3.0F);
/* 36 */     func_149752_b(30.0F);
/* 37 */     func_149672_a(SoundType.field_185851_d);
/*    */     
/* 39 */     func_180632_j(this.field_176227_L.func_177621_b()
/* 40 */         .func_177226_a((IProperty)this.typeProperty, (Comparable)defaultColor));
/*    */     
/* 42 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
/* 47 */     IBlockState state = world.func_180495_p(pos);
/* 48 */     Ic2Color type = getType(state);
/* 49 */     if (type == null) return false;
/*    */     
/* 51 */     Ic2Color newColor = Ic2Color.get(color);
/*    */     
/* 53 */     if (type != newColor) {
/* 54 */       world.func_175656_a(pos, state.func_177226_a((IProperty)this.typeProperty, (Comparable)newColor));
/*    */       
/* 56 */       return true;
/*    */     } 
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRetexture(RetextureEvent event) {
/* 70 */     if (event.state.func_177230_c() != this)
/*    */       return; 
/* 72 */     World world = event.getWorld();
/* 73 */     Ic2Color color = (Ic2Color)event.state.func_177229_b((IProperty)this.typeProperty);
/*    */     
/* 75 */     if (!ItemBlockTileEntity.placeTeBlock(null, (EntityLivingBase)Ic2Player.get(world), world, event.pos, EnumFacing.DOWN, new TileEntityWall(color))) {
/*    */       return;
/*    */     }
/*    */     
/* 79 */     IBlockState newState = BlockName.te.getInstance().func_176223_P();
/*    */     
/* 81 */     RetextureEvent event2 = new RetextureEvent(world, event.pos, newState, event.side, event.player, event.refState, event.refVariant, event.refSide, event.refColorMultipliers);
/*    */     
/* 83 */     MinecraftForge.EVENT_BUS.post((Event)event2);
/*    */     
/* 85 */     if (event2.applied) {
/* 86 */       event.applied = true;
/* 87 */       event.setCanceled(true);
/*    */     } else {
/*    */       
/* 90 */       world.func_175656_a(event.pos, event.state);
/*    */     } 
/*    */   }
/*    */   
/* 94 */   public static final Ic2Color defaultColor = Ic2Color.light_gray;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */