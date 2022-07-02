/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.init.Localization;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorHeatStorage
/*    */   extends AbstractDamageableReactorComponent
/*    */ {
/*    */   public ItemReactorHeatStorage(ItemName name, int heatStorage) {
/* 25 */     super(name, heatStorage);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 35 */     return getMaxCustomDamage(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 40 */     return getCustomDamage(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 49 */     int myHeat = getCurrentHeat(stack, reactor, x, y);
/*    */     
/* 51 */     myHeat += heat;
/*    */     
/* 53 */     int max = getMaxHeat(stack, reactor, x, y);
/*    */     
/* 55 */     if (myHeat > max) {
/*    */       
/* 57 */       reactor.setItemAt(x, y, null);
/* 58 */       heat = max - myHeat + 1;
/*    */     } else {
/* 60 */       if (myHeat < 0) {
/* 61 */         heat = myHeat;
/* 62 */         myHeat = 0;
/*    */       } else {
/* 64 */         heat = 0;
/*    */       } 
/*    */       
/* 67 */       setCustomDamage(stack, myHeat);
/*    */     } 
/*    */     
/* 70 */     return heat;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 76 */     super.func_77624_a(stack, world, tooltip, advanced);
/*    */     
/* 78 */     if (getCustomDamage(stack) > 0) {
/* 79 */       tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line1"));
/* 80 */       tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line2"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorHeatStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */