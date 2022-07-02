/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.model.MaskOverlayModel;
/*    */ import ic2.core.model.ModelUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverride;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ 
/*    */ public class FluidCellModel
/*    */   extends MaskOverlayModel
/*    */ {
/*    */   public FluidCellModel() {
/* 23 */     super(baseModelLoc, maskTextureLoc, false, -0.1F);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     this.overrideHandler = new ItemOverrideList(Collections.emptyList())
/*    */       {
/*    */         public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
/* 37 */           if (stack == null) return ModelUtil.getMissingModel();
/*    */           
/* 39 */           FluidStack fs = FluidUtil.getFluidContained(stack);
/*    */           
/*    */           ResourceLocation spriteLoc;
/* 42 */           if (fs == null || (spriteLoc = fs.getFluid().getStill(fs)) == null) {
/* 43 */             return FluidCellModel.this.get();
/*    */           }
/* 45 */           return FluidCellModel.this.get(Minecraft.func_71410_x().func_147117_R().func_110572_b(spriteLoc.toString()), fs.getFluid().getColor(fs));
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public ItemOverrideList func_188617_f() {
/*    */     return this.overrideHandler;
/*    */   }
/*    */   
/*    */   private static final ResourceLocation baseModelLoc = new ResourceLocation("ic2", "item/cell/fluid_cell_case");
/*    */   private static final ResourceLocation maskTextureLoc = new ResourceLocation("ic2", "textures/items/cell/fluid_cell_window.png");
/*    */   private final ItemOverrideList overrideHandler;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\FluidCellModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */