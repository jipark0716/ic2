/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ModelPersonalChest
/*    */   extends ModelBase
/*    */ {
/*    */   private final ModelRenderer door;
/*    */   
/*    */   public ModelPersonalChest() {
/* 15 */     this.field_78090_t = 64;
/* 16 */     this.field_78089_u = 64;
/*    */     
/* 18 */     this.door = new ModelRenderer(this, 30, 0);
/* 19 */     this.door.func_178769_a(2.0F, 1.0F, 2.0F, 12, 14, 1, true);
/* 20 */     this.door.func_78787_b(64, 64);
/*    */   }
/*    */   
/*    */   public void render() {
/* 24 */     this.door.func_78785_a(0.0625F);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\ModelPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */