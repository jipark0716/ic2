/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBoatCarbon
/*    */   extends EntityIC2Boat
/*    */ {
/*    */   public EntityBoatCarbon(World world) {
/* 11 */     super(world);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getItem() {
/* 16 */     return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.carbon);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTexture() {
/* 21 */     return "textures/models/boat_carbon.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\EntityBoatCarbon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */