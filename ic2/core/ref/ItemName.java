/*     */ package ic2.core.ref;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ItemName
/*     */ {
/*  10 */   boat(ItemFolder.boat, PathStyle.FolderAndNameWithPrefix),
/*     */   
/*  12 */   crushed(ItemFolder.resource),
/*  13 */   purified(ItemFolder.resource),
/*  14 */   dust(ItemFolder.resource),
/*  15 */   ingot(ItemFolder.resource),
/*  16 */   plate(ItemFolder.resource),
/*  17 */   casing(ItemFolder.resource),
/*  18 */   nuclear(ItemFolder.resource),
/*  19 */   misc_resource(ItemFolder.resource, PathStyle.FolderAndSubName),
/*     */   
/*  21 */   block_cutting_blade(ItemFolder.crafting, PathStyle.FolderAndNameWithPrefix),
/*  22 */   crafting(ItemFolder.root),
/*  23 */   crystal_memory(ItemFolder.crafting),
/*  24 */   upgrade_kit(ItemFolder.crafting, PathStyle.FolderAndNameWithPrefix),
/*     */   
/*  26 */   crop_res(ItemFolder.crop, PathStyle.FolderAndSubName),
/*  27 */   terra_wart(ItemFolder.crop),
/*     */   
/*  29 */   re_battery(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  30 */   advanced_re_battery(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  31 */   energy_crystal(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  32 */   lapotron_crystal(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  33 */   single_use_battery(ItemFolder.battery),
/*  34 */   charging_re_battery(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  35 */   advanced_charging_re_battery(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  36 */   charging_energy_crystal(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*  37 */   charging_lapotron_crystal(ItemFolder.battery, PathStyle.FolderAndNameWithSuffix),
/*     */   
/*  39 */   heat_storage(ItemFolder.reactor),
/*  40 */   tri_heat_storage(ItemFolder.reactor),
/*  41 */   hex_heat_storage(ItemFolder.reactor),
/*  42 */   plating(ItemFolder.reactor),
/*  43 */   heat_plating(ItemFolder.reactor),
/*  44 */   containment_plating(ItemFolder.reactor),
/*  45 */   heat_exchanger(ItemFolder.reactor),
/*  46 */   reactor_heat_exchanger(ItemFolder.reactor),
/*  47 */   component_heat_exchanger(ItemFolder.reactor),
/*  48 */   advanced_heat_exchanger(ItemFolder.reactor),
/*  49 */   heat_vent(ItemFolder.reactor),
/*  50 */   reactor_heat_vent(ItemFolder.reactor),
/*  51 */   overclocked_heat_vent(ItemFolder.reactor),
/*  52 */   component_heat_vent(ItemFolder.reactor),
/*  53 */   advanced_heat_vent(ItemFolder.reactor),
/*  54 */   neutron_reflector(ItemFolder.reactor),
/*  55 */   thick_neutron_reflector(ItemFolder.reactor),
/*  56 */   iridium_reflector(ItemFolder.reactor),
/*  57 */   rsh_condensator(ItemFolder.reactor),
/*  58 */   lzh_condensator(ItemFolder.reactor),
/*  59 */   heatpack(ItemFolder.reactor),
/*     */   
/*  61 */   uranium_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  62 */   dual_uranium_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  63 */   quad_uranium_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  64 */   mox_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  65 */   dual_mox_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  66 */   quad_mox_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  67 */   lithium_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  68 */   tritium_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*  69 */   depleted_isotope_fuel_rod(ItemFolder.reactorFuelRod, PathStyle.FolderAndNameM2WithSuffix),
/*     */   
/*  71 */   tfbp(ItemFolder.tfbp, PathStyle.FolderAndSubName),
/*     */   
/*  73 */   bronze_axe(ItemFolder.tool),
/*  74 */   bronze_hoe(ItemFolder.tool),
/*  75 */   bronze_pickaxe(ItemFolder.tool),
/*  76 */   bronze_shovel(ItemFolder.tool),
/*  77 */   bronze_sword(ItemFolder.tool),
/*  78 */   containment_box(ItemFolder.tool),
/*  79 */   cutter(ItemFolder.tool),
/*  80 */   debug_item(ItemFolder.tool),
/*  81 */   foam_sprayer(ItemFolder.tool),
/*  82 */   forge_hammer(ItemFolder.tool),
/*  83 */   frequency_transmitter(ItemFolder.tool),
/*  84 */   meter(ItemFolder.tool),
/*  85 */   remote(ItemFolder.tool),
/*  86 */   tool_box(ItemFolder.tool, PathStyle.FolderAndNameWithSuffix),
/*  87 */   treetap(ItemFolder.tool),
/*  88 */   wrench(ItemFolder.tool),
/*  89 */   wrench_new(ItemFolder.tool),
/*  90 */   crowbar(ItemFolder.tool),
/*     */   
/*  92 */   barrel(ItemFolder.brewing),
/*  93 */   booze_mug(ItemFolder.brewing, PathStyle.FolderAndNameWithSuffix),
/*  94 */   mug(ItemFolder.brewing, PathStyle.FolderAndNameWithSuffix),
/*     */   
/*  96 */   crop_stick(ItemFolder.crop),
/*  97 */   cropnalyzer(ItemFolder.crop),
/*  98 */   crop_seed_bag(ItemFolder.crop),
/*  99 */   weeding_trowel(ItemFolder.crop),
/*     */   
/* 101 */   advanced_scanner(ItemFolder.toolElectric),
/* 102 */   chainsaw(ItemFolder.toolElectric),
/* 103 */   diamond_drill(ItemFolder.toolElectric),
/* 104 */   drill(ItemFolder.toolElectric),
/* 105 */   electric_hoe(ItemFolder.toolElectric),
/* 106 */   electric_treetap(ItemFolder.toolElectric),
/* 107 */   electric_wrench(ItemFolder.toolElectric),
/* 108 */   iridium_drill(ItemFolder.toolElectric),
/* 109 */   mining_laser(ItemFolder.toolElectric),
/* 110 */   nano_saber(ItemFolder.toolElectric, PathStyle.FolderAndNameWithSuffix),
/* 111 */   obscurator(ItemFolder.toolElectric),
/* 112 */   plasma_launcher(ItemFolder.toolElectric),
/* 113 */   scanner(ItemFolder.toolElectric),
/* 114 */   wind_meter(ItemFolder.toolElectric),
/*     */   
/* 116 */   painter(ItemFolder.toolPainter, PathStyle.FolderAndNameWithSuffix),
/*     */   
/* 118 */   fluid_cell(ItemFolder.cell),
/* 119 */   cell(ItemFolder.cell, PathStyle.FolderAndNameWithPrefix),
/*     */   
/* 121 */   cable(ItemFolder.cable),
/*     */   
/* 123 */   upgrade(ItemFolder.upgrade, PathStyle.FolderAndSubName),
/*     */   
/* 125 */   advanced_batpack(ItemFolder.armor),
/* 126 */   alloy_chestplate(ItemFolder.armor),
/* 127 */   batpack(ItemFolder.armor),
/* 128 */   bronze_boots(ItemFolder.armor),
/* 129 */   bronze_chestplate(ItemFolder.armor),
/* 130 */   bronze_helmet(ItemFolder.armor),
/* 131 */   bronze_leggings(ItemFolder.armor),
/* 132 */   cf_pack(ItemFolder.armor),
/* 133 */   energy_pack(ItemFolder.armor),
/* 134 */   hazmat_chestplate(ItemFolder.armor),
/* 135 */   hazmat_helmet(ItemFolder.armor),
/* 136 */   hazmat_leggings(ItemFolder.armor),
/* 137 */   jetpack(ItemFolder.armor),
/* 138 */   jetpack_electric(ItemFolder.armor),
/* 139 */   lappack(ItemFolder.armor),
/* 140 */   nano_boots(ItemFolder.armor),
/* 141 */   nano_chestplate(ItemFolder.armor),
/* 142 */   nano_helmet(ItemFolder.armor),
/* 143 */   nano_leggings(ItemFolder.armor),
/* 144 */   nightvision_goggles(ItemFolder.armor),
/* 145 */   quantum_boots(ItemFolder.armor),
/* 146 */   quantum_chestplate(ItemFolder.armor),
/* 147 */   quantum_helmet(ItemFolder.armor),
/* 148 */   quantum_leggings(ItemFolder.armor),
/* 149 */   rubber_boots(ItemFolder.armor),
/* 150 */   solar_helmet(ItemFolder.armor),
/* 151 */   static_boots(ItemFolder.armor),
/* 152 */   filled_tin_can(ItemFolder.root),
/* 153 */   filled_fuel_can(ItemFolder.root),
/* 154 */   iodine_tablet(ItemFolder.root),
/* 155 */   rotor_wood(ItemFolder.rotor),
/* 156 */   rotor_bronze(ItemFolder.rotor),
/* 157 */   rotor_iron(ItemFolder.rotor),
/* 158 */   rotor_carbon(ItemFolder.rotor),
/* 159 */   rotor_steel(ItemFolder.rotor),
/*     */   
/* 161 */   dynamite(ItemFolder.root),
/* 162 */   dynamite_sticky(ItemFolder.root),
/*     */ 
/*     */   
/* 165 */   pipe(ItemFolder.pipe),
/* 166 */   item_pipe(ItemFolder.pipe),
/*     */ 
/*     */   
/* 169 */   cover(ItemFolder.pipe),
/* 170 */   extractor_cover(ItemFolder.pipe),
/*     */   
/* 172 */   coke(ItemFolder.resource),
/* 173 */   ingot2(ItemFolder.resource),
/*     */   
/* 175 */   test_pick(ItemFolder.tool);
/*     */   
/*     */   private final ItemFolder folder;
/*     */   private final PathStyle pathStyle;
/*     */   private Item instance;
/*     */   public static final ItemName[] values;
/*     */   
/*     */   ItemName(ItemFolder folder, PathStyle pathStyle) {
/* 183 */     if (folder == null) throw new NullPointerException("null folder");
/*     */     
/* 185 */     this.folder = folder;
/* 186 */     this.pathStyle = pathStyle;
/*     */   }
/*     */   
/*     */   public String getPath(String extraName) {
/* 190 */     StringBuilder ret = new StringBuilder();
/*     */     
/* 192 */     if (this.folder.path != null) {
/* 193 */       ret.append(this.folder.path);
/* 194 */       ret.append('/');
/*     */     } 
/*     */     
/* 197 */     if (this.pathStyle == PathStyle.FolderAndNameWithPrefix && extraName != null) {
/* 198 */       ret.append(extraName);
/* 199 */       ret.append('_');
/*     */     } 
/*     */     
/* 202 */     if (this.pathStyle != PathStyle.FolderAndSubName) {
/* 203 */       String name = getName();
/*     */       
/* 205 */       if (this.pathStyle == PathStyle.FolderAndNameM2WithSuffix) {
/* 206 */         int pos = name.lastIndexOf('_', name.lastIndexOf('_') - 1);
/* 207 */         ret.append(name.substring(0, pos));
/*     */       } else {
/* 209 */         ret.append(name);
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     if (this.pathStyle != PathStyle.FolderAndNameWithPrefix && extraName != null) {
/* 214 */       if (this.pathStyle != PathStyle.FolderAndSubName) {
/* 215 */         if (this.pathStyle == PathStyle.FolderAndNameWithSuffix || this.pathStyle == PathStyle.FolderAndNameM2WithSuffix) {
/*     */           
/* 217 */           ret.append('_');
/*     */         } else {
/* 219 */           ret.append('/');
/*     */         } 
/*     */       }
/*     */       
/* 223 */       ret.append(extraName);
/*     */     } 
/*     */     
/* 226 */     if (ret.length() == 0) throw new IllegalArgumentException("empty name for " + this + " (" + this.pathStyle + ") with extraName=" + extraName);
/*     */     
/* 228 */     return ret.toString();
/*     */   }
/*     */   
/*     */   private String getName() {
/* 232 */     return name();
/*     */   }
/*     */   
/*     */   public boolean hasInstance() {
/* 236 */     return (this.instance != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Item & IItemModelProvider> T getInstance() {
/* 241 */     if (this.instance == null) throw new IllegalStateException("the requested item instance for " + name() + " isn't set (yet)");
/*     */     
/* 243 */     return (T)this.instance;
/*     */   }
/*     */   
/*     */   public <T extends Item & IItemModelProvider> void setInstance(T instance) {
/* 247 */     if (this.instance != null) throw new IllegalStateException("conflicting instance");
/*     */     
/* 249 */     this.instance = (Item)instance;
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/* 253 */     return getItemStack((String)null);
/*     */   }
/*     */   
/*     */   public <T extends Enum<T> & ic2.core.block.state.IIdProvider> ItemStack getItemStack(T variant) {
/* 257 */     if (this.instance == null) return null;
/*     */     
/* 259 */     if (this.instance instanceof IMultiItem) {
/*     */ 
/*     */       
/* 262 */       IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
/*     */       
/* 264 */       return multiItem.getItemStack(variant);
/* 265 */     }  if (variant == null) {
/* 266 */       return new ItemStack(this.instance);
/*     */     }
/* 268 */     throw new IllegalArgumentException("not applicable");
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Enum<T> & ic2.core.block.state.IIdProvider> ItemStack getItemStack(String variant) {
/* 273 */     if (this.instance == null) return null;
/*     */     
/* 275 */     if (this.instance instanceof IMultiItem) {
/*     */ 
/*     */       
/* 278 */       IMultiItem<T> multiItem = (IMultiItem<T>)this.instance;
/*     */       
/* 280 */       return multiItem.getItemStack(variant);
/* 281 */     }  if (variant == null) {
/* 282 */       return new ItemStack(this.instance);
/*     */     }
/* 284 */     throw new IllegalArgumentException("not applicable");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVariant(ItemStack stack) {
/* 289 */     if (this.instance == null) return null;
/*     */     
/* 291 */     if (this.instance instanceof IMultiItem) {
/* 292 */       return ((IMultiItem)this.instance).getVariant(stack);
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   private enum PathStyle
/*     */   {
/* 299 */     FolderAndNameAndSubName,
/* 300 */     FolderAndSubName,
/* 301 */     FolderAndNameWithPrefix,
/* 302 */     FolderAndNameWithSuffix,
/* 303 */     FolderAndNameM2WithSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 310 */     values = values();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\ItemName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */