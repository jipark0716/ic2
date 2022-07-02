/*    */ package ic2.core.item.crafting;
/*    */ 
/*    */ import ic2.api.item.IBlockCuttingBlade;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemMulti;
/*    */ import ic2.core.item.type.BlockCuttingBladeType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCuttingBlade
/*    */   extends ItemMulti<BlockCuttingBladeType>
/*    */   implements IBlockCuttingBlade
/*    */ {
/*    */   public BlockCuttingBlade() {
/* 22 */     super(ItemName.block_cutting_blade, BlockCuttingBladeType.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHardness(ItemStack stack) {
/* 27 */     BlockCuttingBladeType blade = (BlockCuttingBladeType)getType(stack);
/* 28 */     if (blade == null) return 0;
/*    */     
/* 30 */     switch (blade) { case iron:
/* 31 */         return 3;
/* 32 */       case steel: return 6;
/* 33 */       case diamond: return 9; }
/* 34 */      return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 41 */     BlockCuttingBladeType blade = (BlockCuttingBladeType)getType(stack);
/* 42 */     if (blade == null)
/*    */       return; 
/* 44 */     switch (blade) {
/*    */       case iron:
/* 46 */         tooltip.add(Localization.translate("ic2.IronBlockCuttingBlade.info"));
/*    */         break;
/*    */       case steel:
/* 49 */         tooltip.add(Localization.translate("ic2.AdvIronBlockCuttingBlade.info"));
/*    */         break;
/*    */       case diamond:
/* 52 */         tooltip.add(Localization.translate("ic2.DiamondBlockCuttingBlade.info"));
/*    */         break;
/*    */     } 
/*    */     
/* 56 */     tooltip.add(Localization.translate("ic2.CuttingBlade.hardness", new Object[] { Integer.valueOf(getHardness(stack)) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\crafting\BlockCuttingBlade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */