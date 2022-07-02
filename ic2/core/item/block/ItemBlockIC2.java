/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.core.block.BlockBase;
/*    */ import ic2.core.block.BlockScaffold;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.ref.BlockName;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemBlockIC2
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemBlockIC2(Block block) {
/* 20 */     super(block);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 25 */     return func_77658_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77653_i(ItemStack stack) {
/* 30 */     return Localization.translate(func_77667_c(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
/* 35 */     return (block.func_177230_c() == BlockName.scaffold.getInstance());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemBurnTime(ItemStack stack) {
/* 40 */     if (this.field_150939_a == BlockName.scaffold.getInstance()) {
/* 41 */       BlockScaffold scaffold = (BlockScaffold)this.field_150939_a;
/*    */       
/* 43 */       IBlockState state = scaffold.getState(scaffold.getVariant(stack));
/* 44 */       return (state.func_185904_a() == Material.field_151575_d) ? 300 : 0;
/*    */     } 
/*    */     
/* 47 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 52 */     if (this.field_150939_a instanceof BlockBase) {
/* 53 */       return ((BlockBase)this.field_150939_a).getRarity(stack);
/*    */     }
/*    */     
/* 56 */     return super.func_77613_e(stack);
/*    */   }
/*    */   
/* 59 */   public static Function<Block, Item> supplier = ItemBlockIC2::new;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemBlockIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */