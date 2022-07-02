/*    */ package ic2.core.model;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.ICustomModelLoader;
/*    */ import net.minecraftforge.client.model.IModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ic2ModelLoader
/*    */   implements ICustomModelLoader
/*    */ {
/*    */   public void register(String path, IReloadableModel model) {
/* 17 */     register(new ResourceLocation("ic2", path), model);
/*    */   }
/*    */   
/*    */   public void register(ResourceLocation location, IReloadableModel model) {
/* 21 */     models.put(location, model);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_110549_a(IResourceManager resourceManager) {
/* 26 */     for (IReloadableModel model : models.values()) {
/* 27 */       model.onReload();
/*    */     }
/*    */     
/* 30 */     ModelComparator.onReload();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ResourceLocation modelLocation) {
/* 35 */     return models.containsKey(modelLocation);
/*    */   }
/*    */ 
/*    */   
/*    */   public IModel loadModel(ResourceLocation modelLocation) throws IOException {
/* 40 */     return models.get(modelLocation);
/*    */   }
/*    */   
/* 43 */   private static final Map<ResourceLocation, IReloadableModel> models = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\Ic2ModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */