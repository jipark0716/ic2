/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.model.MaskOverlayModel;
/*    */ import ic2.core.model.ModelUtil;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverride;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class RenderObscurator
/*    */   extends MaskOverlayModel
/*    */ {
/*    */   public RenderObscurator() {
/* 24 */     super(baseModelLoc, maskTextureLoc, true, 0.001F);
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
/* 35 */     this.overrideHandler = new ItemOverrideList(Collections.emptyList())
/*    */       {
/*    */         public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
/* 38 */           if (stack == null) return ModelUtil.getMissingModel();
/*    */           
/* 40 */           NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 41 */           IBlockState state = ItemObscurator.getState(nbt);
/* 42 */           EnumFacing side = ItemObscurator.getSide(nbt);
/* 43 */           int[] colorMultipliers = ItemObscurator.getColorMultipliers(nbt);
/*    */           
/*    */           ItemObscurator.ObscuredRenderInfo renderInfo;
/* 46 */           if (state == null || side == null || (
/*    */             
/* 48 */             renderInfo = ItemObscurator.getRenderInfo(state, side)) == null || colorMultipliers == null || colorMultipliers.length * 4 != renderInfo.uvs.length)
/*    */           {
/*    */             
/* 51 */             return RenderObscurator.this.get();
/*    */           }
/* 53 */           return RenderObscurator.this.get(renderInfo.uvs, colorMultipliers);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public ItemOverrideList func_188617_f() {
/*    */     return this.overrideHandler;
/*    */   }
/*    */   
/*    */   private static final ResourceLocation baseModelLoc = new ResourceLocation("ic2", "item/tool/electric/obscurator_raw");
/*    */   private static final ResourceLocation maskTextureLoc = new ResourceLocation("ic2", "textures/items/tool/electric/obscurator_mask.png");
/*    */   private final ItemOverrideList overrideHandler;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\RenderObscurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */