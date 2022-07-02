/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import ic2.api.util.FluidContainerOutputMode;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public interface IFillFluidContainerRecipeManager
/*    */   extends IMachineRecipeManager<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> {
/*    */   MachineRecipeResult<Void, Collection<ItemStack>, Input> apply(Input paramInput, FluidContainerOutputMode paramFluidContainerOutputMode, boolean paramBoolean);
/*    */   
/*    */   public static class Input {
/*    */     public final ItemStack container;
/*    */     public final FluidStack fluid;
/*    */     
/*    */     public Input(ItemStack container, FluidStack fluid) {
/* 17 */       this.container = container;
/* 18 */       this.fluid = fluid;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IFillFluidContainerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */