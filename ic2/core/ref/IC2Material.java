/*    */ package ic2.core.ref;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ 
/*    */ public class IC2Material extends Material {
/*  7 */   public static final IC2Material MACHINE = new IC2Material("ic2_material_machine", true, true);
/*  8 */   public static final IC2Material PIPE = new IC2Material("ic2_material_pipe", true, true);
/*  9 */   public static final IC2Material CABLE = new IC2Material("ic2_material_cable", false, true);
/*    */   
/*    */   public IC2Material(String name, boolean requiresTool, boolean immovableMobility) {
/* 12 */     super(MapColor.field_151668_h);
/*    */     
/* 14 */     this.name = name;
/*    */     
/* 16 */     if (requiresTool) {
/* 17 */       func_76221_f();
/*    */     }
/*    */     
/* 20 */     if (immovableMobility) {
/* 21 */       func_76225_o();
/*    */     }
/*    */     
/* 24 */     func_85158_p();
/*    */   }
/*    */   
/*    */   public final String name;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\IC2Material.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */