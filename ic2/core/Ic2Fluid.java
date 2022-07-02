/*    */ package ic2.core;
/*    */ 
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import ic2.core.ref.FluidName;
/*    */ import ic2.core.ref.IFluidModelProvider;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*    */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.client.model.ModelLoader;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class Ic2Fluid
/*    */   extends Fluid
/*    */   implements IFluidModelProvider {
/*    */   public Ic2Fluid(FluidName name) {
/* 24 */     super(name.getName(), name
/* 25 */         .getTextureLocation(false), name
/* 26 */         .getTextureLocation(true));
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerModels(FluidName name) {
/* 32 */     if (!name.getInstance().canBePlacedInWorld())
/*    */       return; 
/* 34 */     final String variant = "type=" + name.name();
/*    */     
/* 36 */     ModelLoader.setCustomStateMapper(getBlock(), new IStateMapper()
/*    */         {
/*    */           public Map<IBlockState, ModelResourceLocation> func_178130_a(Block blockIn) {
/* 39 */             Map<IBlockState, ModelResourceLocation> ret = new IdentityHashMap<>();
/* 40 */             ModelResourceLocation loc = new ModelResourceLocation(Ic2Fluid.fluidLocation, variant);
/*    */             
/* 42 */             for (UnmodifiableIterator<IBlockState> unmodifiableIterator = Ic2Fluid.this.getBlock().func_176194_O().func_177619_a().iterator(); unmodifiableIterator.hasNext(); ) { IBlockState state = unmodifiableIterator.next();
/* 43 */               ret.put(state, loc); }
/*    */ 
/*    */             
/* 46 */             return ret;
/*    */           }
/*    */         });
/*    */     
/* 50 */     Item item = Item.func_150898_a(getBlock());
/*    */     
/* 52 */     if (item != null && item != Items.field_190931_a) {
/* 53 */       ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(fluidLocation, variant));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName() {
/* 59 */     return "ic2." + super.getUnlocalizedName().substring(6);
/*    */   }
/*    */   
/* 62 */   private static final ResourceLocation fluidLocation = new ResourceLocation("ic2", "fluid");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\Ic2Fluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */