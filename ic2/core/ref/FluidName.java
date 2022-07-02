/*    */ package ic2.core.ref;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.Both;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public enum FluidName
/*    */   implements IIdProvider
/*    */ {
/* 14 */   air(false),
/* 15 */   biogas(false),
/* 16 */   biomass,
/* 17 */   construction_foam,
/* 18 */   coolant,
/*    */   
/* 20 */   distilled_water,
/* 21 */   hot_coolant,
/* 22 */   hot_water,
/* 23 */   pahoehoe_lava(false),
/* 24 */   steam(false),
/* 25 */   superheated_steam(false),
/* 26 */   uu_matter,
/* 27 */   weed_ex(false),
/* 28 */   oxygen(false),
/* 29 */   hydrogen(false),
/* 30 */   heavy_water,
/* 31 */   deuterium(false),
/* 32 */   creosote,
/*    */ 
/*    */   
/* 35 */   molten_brass(false),
/* 36 */   molten_bronze(false),
/* 37 */   molten_copper(false),
/* 38 */   molten_gold(false),
/* 39 */   molten_iron(false),
/* 40 */   molten_lead(false),
/* 41 */   molten_silver(false),
/* 42 */   molten_steel(false),
/* 43 */   molten_tin(false),
/* 44 */   molten_zinc(false),
/*    */   
/* 46 */   milk;
/*    */   
/*    */   public static final FluidName[] values;
/*    */   
/*    */   private final boolean hasFlowTexture;
/*    */   private Fluid instance;
/*    */   
/*    */   FluidName(boolean hasFlowTexture) {
/* 54 */     this.hasFlowTexture = hasFlowTexture;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 59 */     return "ic2" + name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 64 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public ResourceLocation getTextureLocation(boolean flowing) {
/* 68 */     if (name().startsWith("molten_")) {
/* 69 */       return new ResourceLocation("ic2", "blocks/fluid/molten_metal");
/*    */     }
/*    */     
/* 72 */     String type = (flowing && this.hasFlowTexture) ? "flow" : "still";
/*    */     
/* 74 */     return new ResourceLocation("ic2", "blocks/fluid/" + name() + "_" + type);
/*    */   }
/*    */   
/*    */   public boolean hasInstance() {
/* 78 */     return (this.instance != null);
/*    */   }
/*    */   
/*    */   public Fluid getInstance() {
/* 82 */     if (this.instance == null) throw new IllegalStateException("the requested fluid instance for " + name() + " isn't set (yet)");
/*    */     
/* 84 */     return this.instance;
/*    */   }
/*    */   
/*    */   public void setInstance(Fluid fluid) {
/* 88 */     if (fluid == null) throw new NullPointerException("null fluid"); 
/* 89 */     if (this.instance != null) throw new IllegalStateException("conflicting instance");
/*    */     
/* 91 */     this.instance = fluid;
/*    */   }
/*    */   static {
/* 94 */     values = values();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\FluidName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */