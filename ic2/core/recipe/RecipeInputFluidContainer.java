/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.core.IC2;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.LoaderState;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ 
/*     */ public class RecipeInputFluidContainer
/*     */   extends RecipeInputBase
/*     */   implements IRecipeInput {
/*     */   private final Fluid fluid;
/*     */   private final int amount;
/*     */   
/*     */   RecipeInputFluidContainer(Fluid fluid) {
/*  25 */     this(fluid, 1000);
/*     */   }
/*     */   
/*     */   RecipeInputFluidContainer(Fluid fluid, int amount) {
/*  29 */     this.fluid = fluid;
/*  30 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(ItemStack subject) {
/*  35 */     FluidStack fs = FluidUtil.getFluidContained(subject);
/*     */ 
/*     */     
/*  38 */     return ((fs == null && this.fluid == null) || (fs != null && fs
/*  39 */       .getFluid() == this.fluid && fs.amount >= this.amount));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/*  45 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getInputs() {
/*  50 */     return getFluidContainer(this.fluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  55 */     return "RInputFluidContainer<" + this.amount + "x" + this.fluid.getName() + ">";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RecipeInputFluidContainer other;
/*  61 */     return (obj != null && getClass() == obj.getClass() && (other = (RecipeInputFluidContainer)obj).fluid == this.fluid && other.amount == this.amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ItemStack> getFluidContainer(Fluid fluid) {
/*  66 */     FluidHandlerInfo info = fluidHandlerInfo;
/*     */     
/*  68 */     if (info.loaderState != LoaderState.AVAILABLE && info.loaderState != Loader.instance().getLoaderState()) {
/*  69 */       List<ItemStack> list = new ArrayList<>();
/*     */       
/*  71 */       for (Item item : ForgeRegistries.ITEMS) {
/*  72 */         ItemStack stack = new ItemStack(item);
/*  73 */         IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
/*     */         
/*  75 */         if (handler != null) {
/*  76 */           handler.drain(2147483647, true);
/*  77 */           ItemStack container = handler.getContainer();
/*     */           
/*  79 */           if (container == null) {
/*  80 */             IC2.platform.displayError("Null container stack!\nItem: %s\nRegistry: %s\nUnlocalised: %s\nHandler: %s (%s)", new Object[] { item, item
/*  81 */                   .getRegistryName(), item.func_77658_a(), handler, handler.getClass() });
/*     */           }
/*     */           
/*  84 */           if (FluidUtil.getFluidContained(container) == null) {
/*  85 */             list.add(stack);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  90 */       LoaderState state = Loader.instance().hasReachedState(LoaderState.AVAILABLE) ? LoaderState.AVAILABLE : Loader.instance().getLoaderState();
/*  91 */       fluidHandlerInfo = info = new FluidHandlerInfo(Collections.unmodifiableList(list), state);
/*     */     } 
/*     */     
/*  94 */     if (fluid == null)
/*     */     {
/*  96 */       return info.items;
/*     */     }
/*     */     
/*  99 */     List<ItemStack> ret = new ArrayList<>();
/*     */     
/* 101 */     for (ItemStack stack : info.items) {
/* 102 */       IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack.func_77946_l());
/*     */       
/* 104 */       if (handler != null && handler
/* 105 */         .fill(new FluidStack(fluid, 2147483647), true) > 0) {
/* 106 */         ItemStack container = handler.getContainer();
/*     */         
/* 108 */         if (FluidUtil.getFluidContained(container) != null) {
/* 109 */           ret.add(container);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return ret;
/*     */   }
/*     */   
/*     */   private static class FluidHandlerInfo {
/*     */     FluidHandlerInfo(List<ItemStack> items, LoaderState loaderState) {
/* 119 */       this.items = items;
/* 120 */       this.loaderState = loaderState;
/*     */     }
/*     */ 
/*     */     
/*     */     final List<ItemStack> items;
/*     */     
/*     */     final LoaderState loaderState;
/*     */   }
/*     */ 
/*     */   
/* 130 */   private static volatile FluidHandlerInfo fluidHandlerInfo = new FluidHandlerInfo(Collections.emptyList(), LoaderState.PREINITIALIZATION);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputFluidContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */