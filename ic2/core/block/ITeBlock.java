/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.item.block.ItemBlockTileEntity;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import mcp.MethodsReturnNonnullByDefault;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @MethodsReturnNonnullByDefault
/*     */ public interface ITeBlock
/*     */   extends IIdProvider
/*     */ {
/*     */   ResourceLocation getIdentifier();
/*     */   
/*     */   boolean hasItem();
/*     */   
/*     */   @Nullable
/*     */   Class<? extends TileEntityBlock> getTeClass();
/*     */   
/*     */   boolean hasActive();
/*     */   
/*     */   Set<EnumFacing> getSupportedFacings();
/*     */   
/*     */   float getHardness();
/*     */   
/*     */   float getExplosionResistance();
/*     */   
/*     */   TeBlock.HarvestTool getHarvestTool();
/*     */   
/*     */   TeBlock.DefaultDrop getDefaultDrop();
/*     */   
/*     */   EnumRarity getRarity();
/*     */   
/*     */   boolean allowWrenchRotating();
/*     */   
/*     */   default Material getMaterial() {
/*  88 */     return TeBlockRegistry.getInfo(getIdentifier()).getDefaultMaterial();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isTransparent() {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void setPlaceHandler(TeBlock.ITePlaceHandler handler) {
/* 103 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TeBlock.ITePlaceHandler getPlaceHandler() {
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   TileEntityBlock getDummyTe();
/*     */   
/*     */   public static interface ITeBlockCreativeRegisterer {
/*     */     void addSubBlocks(NonNullList<ItemStack> param1NonNullList, BlockTileEntity param1BlockTileEntity, ItemBlockTileEntity param1ItemBlockTileEntity, CreativeTabs param1CreativeTabs);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\ITeBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */