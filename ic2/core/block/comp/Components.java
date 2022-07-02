/*    */ package ic2.core.block.comp;
/*    */ 
/*    */ import ic2.core.block.steam.ProcessingComponent;
/*    */ import ic2.core.block.transport.cover.Covers;
/*    */ import java.util.HashMap;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Components
/*    */ {
/*    */   public static void init() {
/* 12 */     register((Class)Energy.class, "energy");
/* 13 */     register((Class)Fluids.class, "fluid");
/* 14 */     register((Class)FluidReactorLookup.class, "fluidReactorLookup");
/* 15 */     register((Class)Obscuration.class, "obscuration");
/* 16 */     register((Class)Process.class, "process");
/* 17 */     register((Class)Redstone.class, "redstone");
/* 18 */     register((Class)RedstoneEmitter.class, "redstoneEmitter");
/* 19 */     register((Class)ComparatorEmitter.class, "comparatorEmitter");
/* 20 */     register((Class)ProcessingComponent.class, "processingComponent");
/* 21 */     register((Class)Covers.class, "covers");
/*    */   }
/*    */   
/*    */   public static void register(Class<? extends TileEntityComponent> cls, String id) {
/* 25 */     if (idComponentMap.put(id, cls) != null) throw new IllegalStateException("duplicate id: " + id); 
/* 26 */     if (componentIdMap.put(cls, id) != null) throw new IllegalStateException("duplicate component: " + cls.getName());
/*    */   
/*    */   }
/*    */   
/*    */   public static <T extends TileEntityComponent> Class<T> getClass(String id) {
/* 31 */     return (Class<T>)idComponentMap.get(id);
/*    */   }
/*    */   
/*    */   public static String getId(Class<? extends TileEntityComponent> cls) {
/* 35 */     return componentIdMap.get(cls);
/*    */   }
/*    */   
/* 38 */   private static final Map<String, Class<? extends TileEntityComponent>> idComponentMap = new HashMap<>();
/* 39 */   private static final Map<Class<? extends TileEntityComponent>, String> componentIdMap = new IdentityHashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Components.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */