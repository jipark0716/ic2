/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.reactor.IBaseReactorComponent;
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemNuclearResource
/*    */   extends ItemMulti<NuclearResourceType>
/*    */   implements IBaseReactorComponent
/*    */ {
/*    */   public ItemNuclearResource() {
/* 20 */     super(ItemName.nuclear, NuclearResourceType.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
/* 25 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemNuclearResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */