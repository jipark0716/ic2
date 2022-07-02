/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorComponent;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemGradualInt;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractDamageableReactorComponent
/*    */   extends ItemGradualInt
/*    */   implements IReactorComponent
/*    */ {
/*    */   protected AbstractDamageableReactorComponent(ItemName name, int maxDamage) {
/* 21 */     super(name, maxDamage);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {}
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 50 */     return heat;
/*    */   }
/*    */ 
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 55 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 61 */     super.func_77624_a(stack, world, tooltip, advanced);
/*    */     
/* 63 */     tooltip.add(Localization.translate("ic2.reactoritem.durability") + " " + (
/* 64 */         getMaxCustomDamage(stack) - getCustomDamage(stack)) + "/" + getMaxCustomDamage(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\AbstractDamageableReactorComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */