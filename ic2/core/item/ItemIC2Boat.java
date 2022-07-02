/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.util.Vector3;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemIC2Boat
/*     */   extends ItemMulti<ItemIC2Boat.BoatType>
/*     */ {
/*     */   public ItemIC2Boat() {
/*  29 */     super(ItemName.boat, BoatType.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  34 */     ItemStack stack = StackUtil.get(player, hand);
/*  35 */     EntityIC2Boat boat = makeBoat(stack, world, player);
/*  36 */     if (boat == null) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/*  38 */     Vector3 lookVec = Util.getLookScaled((Entity)player);
/*  39 */     Vector3 start = Util.getEyePosition((Entity)player);
/*  40 */     Vec3d startMc = start.toVec3();
/*     */     
/*  42 */     RayTraceResult hitPos = world.func_72901_a(startMc, start.add(lookVec).toVec3(), true);
/*  43 */     if (hitPos == null) return new ActionResult(EnumActionResult.PASS, stack);
/*     */     
/*  45 */     boolean inEntity = false;
/*  46 */     float border = 1.0F;
/*  47 */     List<Entity> list = world.func_72839_b((Entity)player, player
/*  48 */         .func_174813_aQ()
/*  49 */         .func_72321_a(lookVec.x, lookVec.y, lookVec.z)
/*  50 */         .func_186662_g(border));
/*     */     
/*  52 */     for (Entity entity : list) {
/*  53 */       if (entity.func_70067_L()) {
/*  54 */         border = entity.func_70111_Y();
/*  55 */         AxisAlignedBB aabb = entity.func_174813_aQ().func_186662_g(border);
/*     */         
/*  57 */         if (aabb.func_72318_a(startMc)) {
/*  58 */           inEntity = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  64 */     if (inEntity) return new ActionResult(EnumActionResult.PASS, stack); 
/*  65 */     if (hitPos.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  66 */       BlockPos pos = hitPos.func_178782_a();
/*     */       
/*  68 */       if (world.func_180495_p(pos).func_177230_c() == Blocks.field_150431_aC) {
/*  69 */         pos = pos.func_177977_b();
/*     */       }
/*     */       
/*  72 */       boat.func_70107_b(pos.func_177958_n() + 0.5D, (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D);
/*  73 */       boat.field_70177_z = (((MathHelper.func_76128_c((player.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/*     */       
/*  75 */       if (!world.func_184144_a((Entity)boat, boat.func_70046_E().func_72321_a(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/*  76 */         return new ActionResult(EnumActionResult.PASS, stack);
/*     */       }
/*     */       
/*  79 */       if (!world.field_72995_K) {
/*  80 */         world.func_72838_d((Entity)boat);
/*     */       }
/*     */       
/*  83 */       if (!player.field_71075_bZ.field_75098_d) {
/*  84 */         stack = StackUtil.decSize(stack);
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */   
/*     */   protected EntityIC2Boat makeBoat(ItemStack stack, World world, EntityPlayer player) {
/*  92 */     BoatType type = getType(stack);
/*  93 */     if (type == null) return null;
/*     */     
/*  95 */     switch (type) { case carbon:
/*  96 */         return new EntityBoatCarbon(world);
/*  97 */       case rubber: return new EntityBoatRubber(world);
/*  98 */       case electric: return new EntityBoatElectric(world); }
/*  99 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 105 */     return (getType(stack) == BoatType.electric);
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack stack) {
/* 110 */     assert hasCustomEntity(stack);
/* 111 */     assert !world.field_72995_K;
/*     */     
/* 113 */     EntityItem item = new FireproofItem(world, location.field_70165_t, location.field_70163_u, location.field_70161_v, stack);
/*     */     
/* 115 */     item.func_174869_p();
/* 116 */     item.field_70159_w = location.field_70159_w;
/* 117 */     item.field_70181_x = location.field_70181_x;
/* 118 */     item.field_70179_y = location.field_70179_y;
/*     */     
/* 120 */     return (Entity)item;
/*     */   }
/*     */   
/*     */   public enum BoatType implements IIdProvider {
/* 124 */     broken_rubber,
/* 125 */     rubber,
/* 126 */     carbon,
/* 127 */     electric;
/*     */ 
/*     */     
/*     */     public String getName() {
/* 131 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 136 */       return ordinal();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FireproofItem extends EntityItem {
/*     */     public FireproofItem(World world, double x, double y, double z, ItemStack stack) {
/* 142 */       super(world, x, y, z, stack);
/*     */       
/* 144 */       this.field_70178_ae = true;
/*     */     }
/*     */     
/*     */     public FireproofItem(World world, double x, double y, double z) {
/* 148 */       super(world, x, y, z);
/*     */       
/* 150 */       this.field_70178_ae = true;
/*     */     }
/*     */     
/*     */     public FireproofItem(World world) {
/* 154 */       super(world);
/*     */       
/* 156 */       this.field_70178_ae = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70071_h_() {
/* 161 */       super.func_70071_h_();
/*     */       
/* 163 */       func_70066_B();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void func_70081_e(int amount) {}
/*     */ 
/*     */     
/*     */     public void func_70015_d(int seconds) {
/* 172 */       func_70066_B();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemIC2Boat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */