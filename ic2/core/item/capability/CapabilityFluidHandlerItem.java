/*    */ package ic2.core.item.capability;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
/*    */ 
/*    */ public class CapabilityFluidHandlerItem
/*    */   extends FluidHandlerItemStack
/*    */ {
/*    */   public CapabilityFluidHandlerItem(ItemStack container, int capacity) {
/* 10 */     super(container, capacity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setContainerToEmpty() {
/* 16 */     super.setContainerToEmpty();
/* 17 */     if (this.container.func_77978_p().func_82582_d())
/* 18 */       this.container.func_77982_d(null); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\capability\CapabilityFluidHandlerItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */