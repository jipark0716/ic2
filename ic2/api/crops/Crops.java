/*     */ package ic2.api.crops;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Crops
/*     */ {
/*     */   public static Crops instance;
/*     */   public static CropCard weed;
/*     */   
/*     */   public abstract void addBiomenutrientsBonus(BiomeDictionary.Type paramType, int paramInt);
/*     */   
/*     */   public abstract void addBiomehumidityBonus(BiomeDictionary.Type paramType, int paramInt);
/*     */   
/*     */   public abstract int getHumidityBiomeBonus(Biome paramBiome);
/*     */   
/*     */   public abstract int getNutrientBiomeBonus(Biome paramBiome);
/*     */   
/*     */   public abstract CropCard getCropCard(String paramString1, String paramString2);
/*     */   
/*     */   public abstract CropCard getCropCard(ItemStack paramItemStack);
/*     */   
/*     */   public abstract Collection<CropCard> getCrops();
/*     */   
/*     */   public abstract void registerCrop(CropCard paramCropCard);
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public abstract void registerCropTextures(Map<ResourceLocation, TextureAtlasSprite> paramMap);
/*     */   
/*     */   public abstract boolean registerBaseSeed(ItemStack paramItemStack, CropCard paramCropCard, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public abstract BaseSeed getBaseSeed(ItemStack paramItemStack);
/*     */   
/*     */   public static class CropRegisterEvent
/*     */     extends Event
/*     */   {
/*     */     public void register(CropCard crop) {
/* 138 */       Crops.instance.registerCrop(crop);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void register(CropCard... crops) {
/* 147 */       for (CropCard crop : crops)
/* 148 */         register(crop); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\Crops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */