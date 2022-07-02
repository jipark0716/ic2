/*    */ package ic2.core.init;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.storage.loot.LootPool;
/*    */ import net.minecraft.world.storage.loot.LootTable;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.LootTableLoadEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class Ic2Loot
/*    */ {
/*    */   public static void init() {
/* 16 */     new Ic2Loot();
/*    */   }
/*    */   
/*    */   private Ic2Loot() {
/* 20 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onLootTableLoad(LootTableLoadEvent event) {
/*    */     try {
/* 26 */       if (!event.getName().func_110624_b().equals("minecraft"))
/*    */         return; 
/* 28 */       if (getClass().getResource("/assets/ic2/loot_tables/" + event.getName().func_110623_a() + ".json") == null) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 33 */       LootTable table = event.getLootTableManager().func_186521_a(new ResourceLocation("ic2", event.getName().func_110623_a()));
/* 34 */       if (table == null || table == LootTable.field_186464_a)
/*    */         return; 
/* 36 */       LootPool pool = table.getPool("ic2");
/* 37 */       if (pool == null)
/*    */         return; 
/* 39 */       event.getTable().addPool(pool);
/* 40 */     } catch (Throwable t) {
/* 41 */       IC2.log.warn(LogCategory.General, t, "Error loading loot table %s.", new Object[] { event.getName().func_110623_a() });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\Ic2Loot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */