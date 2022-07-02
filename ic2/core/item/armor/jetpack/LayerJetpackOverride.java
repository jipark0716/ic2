/*    */ package ic2.core.item.armor.jetpack;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class LayerJetpackOverride extends LayerBipedArmor {
/*    */   private final RenderLivingBase<?> renderer;
/*    */   
/*    */   public LayerJetpackOverride(RenderLivingBase<?> renderer) {
/* 18 */     super(null);
/*    */     
/* 20 */     this.renderer = renderer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_177141_a(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 26 */     ModelBiped model = getArmorModelHook(entity, JetpackHandler.jetpack, EntityEquipmentSlot.CHEST, (ModelBiped)func_188360_a(EntityEquipmentSlot.CHEST));
/* 27 */     model.func_178686_a(this.renderer.func_177087_b());
/* 28 */     model.func_78086_a(entity, limbSwing, limbSwingAmount, partialTicks);
/* 29 */     func_188359_a(model, EntityEquipmentSlot.CHEST);
/* 30 */     this.renderer.func_110776_a(getArmorResource((Entity)entity, JetpackHandler.jetpack, EntityEquipmentSlot.CHEST, null));
/*    */     
/* 32 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 33 */     model.func_78088_a((Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\LayerJetpackOverride.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */