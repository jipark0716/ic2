/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.api.item.IItemHudInfo;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ public abstract class ItemToolCrafting
/*    */   extends ItemIC2
/*    */   implements IBoxable, IItemHudInfo
/*    */ {
/*    */   public ItemToolCrafting(ItemName name, int maximumUses) {
/* 24 */     super(name);
/*    */     
/* 26 */     func_77656_e(maximumUses - 1);
/* 27 */     func_77625_d(1);
/* 28 */     this.canRepair = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 34 */     tooltip.add(Localization.translate("ic2.item.ItemTool.tooltip.UsesLeft", new Object[] { Integer.valueOf(getRemainingUses(stack)) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 44 */     List<String> info = new LinkedList<>();
/* 45 */     info.add(Localization.translate("ic2.item.ItemTool.tooltip.UsesLeft", new Object[] { Integer.valueOf(getRemainingUses(stack)) }));
/* 46 */     return info;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasContainerItem(ItemStack stack) {
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getContainerItem(ItemStack stack) {
/* 59 */     ItemStack ret = stack.func_77946_l();
/* 60 */     if (ret.func_96631_a(1, IC2.random, null)) {
/* 61 */       return StackUtil.emptyStack;
/*    */     }
/*    */     
/* 64 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */