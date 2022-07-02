/*    */ package ic2.core.model;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ModelManager;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelUtil
/*    */ {
/*    */   public static ModelResourceLocation getModelLocation(ResourceLocation loc, IBlockState state) {
/* 20 */     return new ModelResourceLocation(loc, getVariant(state));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ModelResourceLocation getTEBlockModelLocation(ResourceLocation loc, IBlockState state) {
/* 31 */     return new ModelResourceLocation(loc, skippingStateMapper.func_178131_a((Map)state.func_177228_b()));
/*    */   }
/*    */   
/*    */   public static String getVariant(IBlockState state) {
/* 35 */     return defaultStateMapper.func_178131_a((Map)state.func_177228_b());
/*    */   }
/*    */   
/* 38 */   private static final DefaultStateMapper defaultStateMapper = new DefaultStateMapper();
/* 39 */   private static final DefaultStateMapper skippingStateMapper = new DefaultStateMapper()
/*    */     {
/*    */       public String func_178131_a(Map<IProperty<?>, Comparable<?>> values) {
/* 42 */         StringBuilder propString = new StringBuilder();
/*    */         
/* 44 */         for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
/* 45 */           IProperty<?> prop = entry.getKey();
/*    */           
/* 47 */           if (!(prop instanceof ic2.core.block.state.ISkippableProperty)) {
/* 48 */             if (propString.length() != 0) {
/* 49 */               propString.append(',');
/*    */             }
/*    */             
/* 52 */             propString.append(prop.func_177701_a());
/* 53 */             propString.append('=');
/* 54 */             propString.append(getPropertyName(prop, entry.getValue()));
/*    */           } 
/*    */         } 
/*    */         
/* 58 */         if (propString.length() == 0) {
/* 59 */           return "normal";
/*    */         }
/*    */         
/* 62 */         return propString.toString();
/*    */       }
/*    */ 
/*    */       
/*    */       private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
/* 67 */         return property.func_177702_a(value);
/*    */       }
/*    */     };
/*    */   
/*    */   public static IBakedModel getMissingModel() {
/* 72 */     return getModelManager().func_174951_a();
/*    */   }
/*    */   
/*    */   public static IBakedModel getModel(ModelResourceLocation loc) {
/* 76 */     return getModelManager().func_174953_a(loc);
/*    */   }
/*    */   
/*    */   public static IBakedModel getBlockModel(IBlockState state) {
/* 80 */     return Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178125_b(state);
/*    */   }
/*    */   
/*    */   private static ModelManager getModelManager() {
/* 84 */     return Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178083_a();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\ModelUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */