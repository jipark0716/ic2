/*    */ package ic2.core.block;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public class KineticGeneratorRotor extends ModelBase {
/*    */   ModelRenderer rotor1;
/*    */   ModelRenderer rotor2;
/*    */   
/*    */   public KineticGeneratorRotor(int radius) {
/*  9 */     this.field_78090_t = 32;
/* 10 */     this.field_78089_u = 256;
/*    */     
/* 12 */     this.rotor1 = new ModelRenderer(this, 0, 0);
/* 13 */     this.rotor1.func_78789_a(0.0F, 0.0F, -4.0F, 1, radius * 8, 8);
/* 14 */     this.rotor1.func_78793_a(-8.0F, 0.0F, 0.0F);
/* 15 */     this.rotor1.func_78787_b(32, 256);
/* 16 */     this.rotor1.field_78809_i = true;
/* 17 */     setRotation(this.rotor1, 0.0F, -0.5F, 0.0F);
/*    */     
/* 19 */     this.rotor2 = new ModelRenderer(this, 0, 0);
/* 20 */     this.rotor2.func_78789_a(0.0F, 0.0F, -4.0F, 1, radius * 8, 8);
/* 21 */     this.rotor2.func_78793_a(-8.0F, 0.0F, 0.0F);
/* 22 */     this.rotor2.func_78787_b(32, 256);
/* 23 */     this.rotor2.field_78809_i = true;
/* 24 */     setRotation(this.rotor2, 3.1F, 0.5F, 0.0F);
/*    */     
/* 26 */     this.rotor3 = new ModelRenderer(this, 0, 0);
/* 27 */     this.rotor3.func_78789_a(0.0F, 0.0F, -4.0F, 1, radius * 8, 8);
/* 28 */     this.rotor3.func_78793_a(-8.0F, 0.0F, 0.0F);
/* 29 */     this.rotor3.func_78787_b(32, 256);
/* 30 */     this.rotor3.field_78809_i = true;
/* 31 */     setRotation(this.rotor3, 4.7F, 0.0F, 0.5F);
/*    */     
/* 33 */     this.rotor4 = new ModelRenderer(this, 0, 0);
/* 34 */     this.rotor4.func_78789_a(0.0F, 0.0F, -4.0F, 1, radius * 8, 8);
/* 35 */     this.rotor4.func_78793_a(-8.0F, 0.0F, 0.0F);
/* 36 */     this.rotor4.func_78787_b(32, 256);
/* 37 */     this.rotor4.field_78809_i = true;
/* 38 */     setRotation(this.rotor4, 1.5F, 0.0F, -0.5F);
/*    */   }
/*    */   ModelRenderer rotor3; ModelRenderer rotor4;
/*    */   
/*    */   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
/* 43 */     this.rotor1.func_78785_a(scale);
/* 44 */     this.rotor2.func_78785_a(scale);
/* 45 */     this.rotor3.func_78785_a(scale);
/* 46 */     this.rotor4.func_78785_a(scale);
/*    */   }
/*    */   
/*    */   private static void setRotation(ModelRenderer model, float x, float y, float z) {
/* 50 */     model.field_78795_f = x;
/* 51 */     model.field_78796_g = y;
/* 52 */     model.field_78808_h = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\KineticGeneratorRotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */