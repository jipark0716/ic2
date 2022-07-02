/*    */ package ic2.core.item.block;
/*    */ import ic2.core.block.EntityIC2Explosive;
/*    */ import ic2.core.block.EntityItnt;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BehaviorTeBlockDispense extends BehaviorDefaultDispenseItem {
/*    */   protected ItemStack func_82487_b(IBlockSource source, ItemStack stack) {
/* 21 */     if (StackUtil.checkItemEquality(stack, BlockName.te.getItemStack((Enum)TeBlock.itnt))) {
/* 22 */       World world = source.func_82618_k();
/* 23 */       BlockPos pos = source.func_180699_d().func_177972_a((EnumFacing)source.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a));
/* 24 */       assert !world.field_72995_K;
/*    */       
/* 26 */       EntityItnt entityItnt = new EntityItnt(world, pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D);
/*    */       
/* 28 */       world.func_175698_g(pos);
/* 29 */       world.func_72838_d((Entity)entityItnt);
/* 30 */       world.func_184148_a(null, ((EntityIC2Explosive)entityItnt).field_70165_t, ((EntityIC2Explosive)entityItnt).field_70163_u, ((EntityIC2Explosive)entityItnt).field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*    */       
/* 32 */       return StackUtil.decSize(stack);
/*    */     } 
/*    */     
/* 35 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\BehaviorTeBlockDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */