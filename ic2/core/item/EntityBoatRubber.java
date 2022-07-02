/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBoatRubber
/*    */   extends EntityIC2Boat
/*    */ {
/*    */   public EntityBoatRubber(World world) {
/* 12 */     super(world);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getItem() {
/* 17 */     return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.rubber);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack getBrokenItem() {
/* 22 */     func_184185_a(SoundEvents.field_187638_cR, 16.0F, 8.0F);
/*    */     
/* 24 */     return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.broken_rubber);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTexture() {
/* 29 */     return "textures/models/boat_rubber.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\EntityBoatRubber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */