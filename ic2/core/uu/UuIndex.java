/*     */ package ic2.core.uu;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.Config;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class UuIndex
/*     */ {
/*  20 */   public static final UuIndex instance = new UuIndex();
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResolver(IRecipeResolver resolver) {
/*  25 */     this.resolvers.add(resolver);
/*     */   }
/*     */   
/*     */   public void addResolver(ILateRecipeResolver resolver) {
/*  29 */     this.lateResolvers.add(resolver);
/*     */   }
/*     */   
/*     */   public void add(ItemStack stack, double value) {
/*  33 */     if (stack == null || stack.func_77973_b() == null) throw new NullPointerException("invalid itemstack to add"); 
/*  34 */     UuGraph.set(stack, value);
/*     */   }
/*     */   
/*     */   public double get(ItemStack request) {
/*  38 */     return UuGraph.get(request);
/*     */   }
/*     */   
/*     */   public double getInBuckets(ItemStack request) {
/*  42 */     double ret = UuGraph.get(request);
/*     */     
/*  44 */     ret *= 1.0E-5D;
/*     */     
/*  46 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  52 */     addResolver(new VanillaSmeltingResolver());
/*  53 */     addResolver(new RecipeResolver());
/*  54 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.macerator));
/*  55 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.extractor));
/*  56 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.compressor));
/*  57 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.centrifuge));
/*  58 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.blockcutter));
/*  59 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.blastfurnace));
/*  60 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerExtruding));
/*  61 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerCutting));
/*  62 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerRolling));
/*  63 */     addResolver(new MachineRecipeResolver((IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.oreWashing));
/*  64 */     addResolver(new CannerBottleSolidResolver());
/*  65 */     addResolver(new ScrapBoxResolver());
/*  66 */     addResolver(new ManualRecipeResolver());
/*  67 */     addResolver(new RecyclerResolver());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh(boolean reset) {
/*  73 */     Config config = MainConfig.get().getSub("balance/uu-values/world scan");
/*     */     
/*  75 */     if (config == null) {
/*  76 */       IC2.log.info(LogCategory.Uu, "Loading predefined UU world scan values, run /ic2 uu-world-scan <small|medium|large> to calibrate them for your world.");
/*     */       
/*  78 */       config = new Config("uu scan values");
/*     */       
/*     */       try {
/*  81 */         config.load(IC2.class.getResourceAsStream("/assets/ic2/config/uu_scan_values.ini"));
/*  82 */       } catch (Exception e) {
/*  83 */         throw new RuntimeException("Error loading base config", e);
/*     */       } 
/*     */     } else {
/*  86 */       IC2.log.debug(LogCategory.Uu, "Loading UU world scan values from the user config.");
/*     */     } 
/*     */     Iterator<Config.Value> it;
/*  89 */     for (it = config.valueIterator(); it.hasNext(); ) {
/*  90 */       ItemStack stack; Config.Value value = it.next();
/*     */ 
/*     */       
/*     */       try {
/*  94 */         stack = ConfigUtil.asStack(value.name);
/*  95 */       } catch (ParseException e) {
/*  96 */         throw new Config.ParseException("invalid key", value, e);
/*     */       } 
/*     */       
/*  99 */       if (stack == null) {
/* 100 */         IC2.log.warn(LogCategory.Uu, "UU world-scan config: Can't find ItemStack for %s, ignoring the entry in line %d.", new Object[] { value.name, Integer.valueOf(value.getLine()) });
/*     */         
/*     */         continue;
/*     */       } 
/* 104 */       add(stack, value.getDouble());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 109 */     for (it = MainConfig.get().getSub("balance/uu-values/predefined").valueIterator(); it.hasNext(); ) {
/* 110 */       ItemStack stack; Config.Value value = it.next();
/*     */ 
/*     */       
/*     */       try {
/* 114 */         stack = ConfigUtil.asStack(value.name);
/* 115 */       } catch (ParseException e) {
/* 116 */         throw new Config.ParseException("invalid key", value, e);
/*     */       } 
/*     */       
/* 119 */       if (stack == null) {
/* 120 */         IC2.log.warn(LogCategory.Uu, "UU predefined config: Can't find ItemStack for %s, ignoring the entry in line %d.", new Object[] { value.name, Integer.valueOf(value.getLine()) });
/*     */         
/*     */         continue;
/*     */       } 
/* 124 */       add(stack, value.getDouble());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 129 */     UuGraph.build(reset);
/*     */   }
/*     */   
/* 132 */   protected final List<IRecipeResolver> resolvers = new ArrayList<>();
/* 133 */   protected final List<ILateRecipeResolver> lateResolvers = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\UuIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */