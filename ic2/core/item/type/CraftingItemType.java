/*     */ package ic2.core.item.type;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.profile.NotExperimental;
/*     */ 
/*     */ public enum CraftingItemType implements IIdProvider {
/*   8 */   rubber(0),
/*   9 */   circuit(1),
/*  10 */   advanced_circuit(2),
/*  11 */   alloy(3),
/*  12 */   iridium(4),
/*  13 */   coil(5),
/*     */   
/*  15 */   electric_motor(6),
/*     */   
/*  17 */   heat_conductor(7),
/*     */   
/*  19 */   copper_boiler(8),
/*     */   
/*  21 */   fuel_rod(9),
/*  22 */   tin_can(10),
/*     */   
/*  24 */   small_power_unit(11),
/*     */   
/*  26 */   power_unit(12),
/*     */ 
/*     */   
/*  29 */   carbon_fibre(13),
/*  30 */   carbon_mesh(14),
/*  31 */   carbon_plate(15),
/*     */   
/*  33 */   coal_ball(16),
/*  34 */   coal_block(17),
/*  35 */   coal_chunk(18),
/*  36 */   industrial_diamond(19),
/*     */   
/*  38 */   plant_ball(20),
/*  39 */   compressed_plants(39),
/*     */   
/*  41 */   bio_chaff(21),
/*  42 */   compressed_hydrated_coal(22),
/*     */ 
/*     */   
/*  45 */   scrap(23),
/*  46 */   scrap_box(24),
/*     */   
/*  48 */   cf_powder(25),
/*     */   
/*  50 */   pellet(26),
/*     */ 
/*     */   
/*  53 */   raw_crystal_memory(27),
/*     */ 
/*     */   
/*  56 */   iron_shaft(29),
/*     */   
/*  58 */   steel_shaft(30),
/*     */ 
/*     */   
/*  61 */   wood_rotor_blade(31),
/*     */   
/*  63 */   iron_rotor_blade(32),
/*     */   
/*  65 */   steel_rotor_blade(33),
/*     */   
/*  67 */   carbon_rotor_blade(34),
/*     */ 
/*     */   
/*  70 */   steam_turbine_blade(35),
/*     */   
/*  72 */   steam_turbine(36),
/*     */ 
/*     */   
/*  75 */   jetpack_attachment_plate(37),
/*     */ 
/*     */   
/*  78 */   coin(38),
/*     */   
/*  80 */   empty_fuel_can(40),
/*     */ 
/*     */   
/*  83 */   bronze_rotor_blade(41),
/*     */   
/*  85 */   bronze_shaft(42);
/*     */   
/*     */   private final int id;
/*     */   
/*     */   CraftingItemType(int id) {
/*  90 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  95 */     return name();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 100 */     return this.id;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\CraftingItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */