/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemReactorMOX
/*    */   extends ItemReactorUranium
/*    */ {
/*    */   public ItemReactorMOX(ItemName name, int cells) {
/* 20 */     super(name, cells, 10000);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 25 */     if (reactor.isFluidCooled()) {
/* 26 */       float breedereffectiveness = reactor.getHeat() / reactor.getMaxHeat();
/*    */       
/* 28 */       if (breedereffectiveness > 0.5D) {
/* 29 */         heat *= 2;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return heat;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
/*    */     ItemStack ret;
/* 40 */     switch (this.numberOfCells) { case 1:
/* 41 */         ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_mox);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 47 */         return ret.func_77946_l();case 2: ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_dual_mox); return ret.func_77946_l();case 4: ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_quad_mox); return ret.func_77946_l(); }
/*    */     
/*    */     throw new RuntimeException("invalid cell count: " + this.numberOfCells);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 55 */     if (!heatrun) {
/* 56 */       float breedereffectiveness = reactor.getHeat() / reactor.getMaxHeat();
/* 57 */       float ReaktorOutput = 4.0F * breedereffectiveness + 1.0F;
/*    */       
/* 59 */       reactor.addOutput(ReaktorOutput);
/*    */     } 
/*    */     
/* 62 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorMOX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */