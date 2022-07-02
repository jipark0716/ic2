/*    */ package ic2.core.block.transport.cover;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFluidConsumingCover
/*    */   extends ICoverItem
/*    */ {
/*    */   default Collection<? extends Capability<?>> getProvidedCapabilities() {
/* 17 */     return Collections.singleton(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\cover\IFluidConsumingCover.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */