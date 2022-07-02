/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ManualRecipeResolver
/*    */   implements IRecipeResolver {
/*    */   private static final double transformCost = 0.0D;
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 15 */     List<RecipeTransformation> ret = new ArrayList<>();
/*    */     
/* 17 */     ret.add(toTransform(ItemName.uranium_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_uranium)));
/* 18 */     ret.add(toTransform(ItemName.dual_uranium_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_dual_uranium)));
/* 19 */     ret.add(toTransform(ItemName.quad_uranium_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_quad_uranium)));
/*    */     
/* 21 */     ret.add(toTransform(ItemName.mox_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_mox)));
/* 22 */     ret.add(toTransform(ItemName.dual_mox_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_dual_mox)));
/* 23 */     ret.add(toTransform(ItemName.quad_mox_fuel_rod.getItemStack(), ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_quad_mox)));
/*    */     
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   private static RecipeTransformation toTransform(ItemStack input, ItemStack output) {
/* 29 */     List<List<LeanItemStack>> inputs = Collections.singletonList(Collections.singletonList(new LeanItemStack(input)));
/* 30 */     List<LeanItemStack> outputs = Collections.singletonList(new LeanItemStack(output));
/*    */     
/* 32 */     return new RecipeTransformation(0.0D, inputs, outputs);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\ManualRecipeResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */