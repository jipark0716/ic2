/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import ic2.core.ref.BlockName;
/*    */ import java.util.Collections;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Flatification
/*    */   extends TerraformerBase
/*    */ {
/*    */   void init() {
/* 24 */     removable.add(Blocks.field_150433_aE);
/* 25 */     removable.add(Blocks.field_150432_aD);
/* 26 */     removable.add(Blocks.field_150349_c);
/* 27 */     removable.add(Blocks.field_150348_b);
/* 28 */     removable.add(Blocks.field_150351_n);
/* 29 */     removable.add(Blocks.field_150354_m);
/* 30 */     removable.add(Blocks.field_150346_d);
/* 31 */     removable.add(Blocks.field_150362_t);
/* 32 */     removable.add(Blocks.field_150361_u);
/* 33 */     removable.add(Blocks.field_150364_r);
/* 34 */     removable.add(Blocks.field_150329_H);
/* 35 */     removable.add(Blocks.field_150328_O);
/* 36 */     removable.add(Blocks.field_150327_N);
/* 37 */     removable.add(Blocks.field_150345_g);
/* 38 */     removable.add(Blocks.field_150464_aj);
/* 39 */     removable.add(Blocks.field_150337_Q);
/* 40 */     removable.add(Blocks.field_150338_P);
/* 41 */     removable.add(Blocks.field_150423_aK);
/* 42 */     removable.add(Blocks.field_150440_ba);
/*    */     
/* 44 */     removable.add(BlockName.leaves.getInstance());
/* 45 */     removable.add(BlockName.sapling.getInstance());
/* 46 */     removable.add(BlockName.rubber_wood.getInstance());
/*    */   }
/*    */ 
/*    */   
/*    */   boolean terraform(World world, BlockPos pos) {
/* 51 */     BlockPos workPos = TileEntityTerra.getFirstBlockFrom(world, pos, 20);
/* 52 */     if (workPos == null) return false;
/*    */     
/* 54 */     if (world.func_180495_p(workPos).func_177230_c() == Blocks.field_150431_aC) {
/* 55 */       workPos = workPos.func_177977_b();
/*    */     }
/*    */     
/* 58 */     if (pos.func_177956_o() == workPos.func_177956_o())
/* 59 */       return false; 
/* 60 */     if (workPos.func_177956_o() < pos.func_177956_o()) {
/* 61 */       world.func_175656_a(workPos.func_177984_a(), Blocks.field_150346_d.func_176223_P());
/*    */       
/* 63 */       return true;
/* 64 */     }  if (canRemove(world.func_180495_p(workPos).func_177230_c())) {
/* 65 */       world.func_175698_g(workPos);
/*    */       
/* 67 */       return true;
/*    */     } 
/*    */     
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean canRemove(Block block) {
/* 78 */     return removable.contains(block);
/*    */   }
/*    */   
/* 81 */   static Set<Block> removable = Collections.newSetFromMap(new IdentityHashMap<>());
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Flatification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */