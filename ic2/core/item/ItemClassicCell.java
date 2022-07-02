/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.crop.TileEntityCrop;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.type.CellType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ public class ItemClassicCell
/*    */   extends ItemMulti<CellType>
/*    */ {
/*    */   public ItemClassicCell() {
/* 26 */     super(ItemName.cell, CellType.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 31 */     ItemStack stack = StackUtil.get(player, hand);
/* 32 */     CellType type = getType(stack);
/*    */     
/* 34 */     if (type.hasCropAction()) {
/* 35 */       TileEntity te = world.func_175625_s(pos);
/*    */       
/* 37 */       if (te instanceof TileEntityCrop) {
/* 38 */         return type.doCropAction(stack, result -> StackUtil.set(player, hand, result), (TileEntityCrop)te, true);
/*    */       }
/*    */     } 
/*    */     
/* 42 */     return EnumActionResult.PASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemStackLimit(ItemStack stack) {
/* 47 */     CellType type = getType(stack);
/* 48 */     return (type != null) ? type.getStackSize() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showDurabilityBar(ItemStack stack) {
/* 53 */     return (getType(stack).getUsage(stack) > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 58 */     CellType type = getType(stack);
/* 59 */     return type.getUsage(stack) / type.getMaximum(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 65 */     CellType type = getType(stack);
/*    */     
/* 67 */     if (type.getStackSize() == 1 && advanced.func_194127_a()) {
/* 68 */       int max = type.getMaximum(stack);
/* 69 */       tooltip.add(Localization.translate("item.durability", new Object[] { Integer.valueOf(max - type.getUsage(stack)), Integer.valueOf(max) }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemClassicCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */