/*    */ package ic2.core.apihelper;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import ic2.api.item.IC2Items;
/*    */ import ic2.api.network.INetworkManager;
/*    */ import ic2.api.network.NetworkHelper;
/*    */ import ic2.api.tile.RotorRegistry;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2DamageSource;
/*    */ import ic2.core.block.KineticGeneratorRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApiHelper
/*    */ {
/*    */   public static void preload() {
/* 25 */     Info.DMG_ELECTRIC = (DamageSource)IC2DamageSource.electricity;
/* 26 */     Info.DMG_NUKE_EXPLOSION = (DamageSource)IC2DamageSource.nuke;
/* 27 */     Info.DMG_RADIATION = (DamageSource)IC2DamageSource.radiation;
/*    */     
/* 29 */     IC2Items.setInstance(new ItemAPI());
/*    */     
/* 31 */     NetworkHelper.setInstance((INetworkManager)IC2.network.get(true), (INetworkManager)IC2.network.get(false));
/*    */     
/* 33 */     if (IC2.platform.isRendering())
/* 34 */       RotorRegistry.setInstance(new RotorRegistry.IRotorRegistry()
/*    */           {
/*    */             public <T extends net.minecraft.tileentity.TileEntity & ic2.api.tile.IRotorProvider> void registerRotorProvider(Class<T> clazz) {
/* 37 */               ClientRegistry.bindTileEntitySpecialRenderer(clazz, (TileEntitySpecialRenderer)new KineticGeneratorRenderer());
/*    */             }
/*    */           }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\apihelper\ApiHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */