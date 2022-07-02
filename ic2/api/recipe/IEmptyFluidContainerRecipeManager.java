/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import ic2.api.util.FluidContainerOutputMode;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public interface IEmptyFluidContainerRecipeManager
/*    */   extends IMachineRecipeManager<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> {
/*    */   MachineRecipeResult<Void, Output, ItemStack> apply(ItemStack paramItemStack, Fluid paramFluid, FluidContainerOutputMode paramFluidContainerOutputMode, boolean paramBoolean);
/*    */   
/*    */   public static class Output {
/*    */     public final Collection<ItemStack> container;
/*    */     public final FluidStack fluid;
/*    */     
/*    */     public Output(Collection<ItemStack> container, FluidStack fluid) {
/* 18 */       this.container = container;
/* 19 */       this.fluid = fluid;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IEmptyFluidContainerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */