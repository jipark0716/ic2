/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import ic2.core.item.ItemBooze;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public class TileEntityBarrel
/*     */   extends TileEntityBlock
/*     */ {
/*     */   public TileEntityBarrel() {}
/*     */   
/*     */   public TileEntityBarrel(int value) {
/*  44 */     this.type = ItemBooze.getTypeOfValue(value);
/*     */     
/*  46 */     if (this.type > 0) {
/*  47 */       this.boozeAmount = ItemBooze.getAmountOfValue(value);
/*     */     }
/*     */     
/*  50 */     if (this.type == 1) {
/*  51 */       this.opened = true;
/*  52 */       this.hopsRatio = (byte)ItemBooze.getHopsRatioOfBeerValue(value);
/*  53 */       this.solidRatio = (byte)ItemBooze.getSolidRatioOfBeerValue(value);
/*  54 */       this.timeRatio = (byte)ItemBooze.getTimeRatioOfBeerValue(value);
/*     */     } 
/*     */     
/*  57 */     if (this.type == 2) {
/*  58 */       this.opened = false;
/*  59 */       this.age = timeNedForRum(this.boozeAmount) * ItemBooze.getProgressOfRumValue(value) / 100;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  68 */     super.func_145839_a(nbt);
/*     */     
/*  70 */     this.type = nbt.func_74771_c("type");
/*  71 */     this.boozeAmount = nbt.func_74771_c("waterCount");
/*  72 */     this.age = nbt.func_74762_e("age");
/*  73 */     this.opened = nbt.func_74767_n("opened");
/*     */     
/*  75 */     if (this.type == 1) {
/*  76 */       if (!this.opened) {
/*  77 */         this.hopsCount = nbt.func_74771_c("hopsCount");
/*  78 */         this.wheatCount = nbt.func_74771_c("wheatCount");
/*     */       } 
/*     */       
/*  81 */       this.solidRatio = nbt.func_74771_c("solidRatio");
/*  82 */       this.hopsRatio = nbt.func_74771_c("hopsRatio");
/*  83 */       this.timeRatio = nbt.func_74771_c("timeRatio");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  93 */     super.func_189515_b(nbt);
/*     */     
/*  95 */     nbt.func_74774_a("type", (byte)this.type);
/*  96 */     nbt.func_74774_a("waterCount", (byte)this.boozeAmount);
/*  97 */     nbt.func_74768_a("age", this.age);
/*  98 */     nbt.func_74757_a("opened", this.opened);
/*     */     
/* 100 */     if (this.type == 1) {
/* 101 */       if (!this.opened) {
/* 102 */         nbt.func_74774_a("hopsCount", this.hopsCount);
/* 103 */         nbt.func_74774_a("wheatCount", this.wheatCount);
/*     */       } 
/*     */       
/* 106 */       nbt.func_74774_a("solidRatio", this.solidRatio);
/* 107 */       nbt.func_74774_a("hopsRatio", this.hopsRatio);
/* 108 */       nbt.func_74774_a("timeRatio", this.timeRatio);
/*     */     } 
/*     */     
/* 111 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 119 */     super.updateEntityServer();
/*     */     
/* 121 */     if (!isEmpty() && !getActive()) {
/* 122 */       this.age++;
/*     */       
/* 124 */       if (this.type == 1 && this.timeRatio < 5) {
/* 125 */         int x = this.timeRatio;
/* 126 */         if (x == 4) x += 2;
/*     */         
/* 128 */         if (this.age >= 24000.0D * Math.pow(3.0D, x)) {
/* 129 */           this.age = 0;
/* 130 */           this.timeRatio = (byte)(this.timeRatio + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 137 */     return (this.type == 0 || this.boozeAmount <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 145 */     ItemStack stack = StackUtil.get(player, hand);
/* 146 */     if (stack == null) return false;
/*     */     
/* 148 */     if (side.func_176740_k() != EnumFacing.Axis.Y && 
/* 149 */       !getActive() && 
/* 150 */       StackUtil.consume(player, hand, StackUtil.sameStack(ItemName.treetap.getItemStack()), 1)) {
/* 151 */       if (!(func_145831_w()).field_72995_K) setActive(true); 
/* 152 */       if (getFacing() != side) setFacing(side);
/*     */       
/* 154 */       return true;
/*     */     } 
/*     */     
/* 157 */     if (!this.opened) {
/* 158 */       if (this.type == 0 || this.type == 1) {
/* 159 */         int minAmount = 1000;
/* 160 */         int space = (32 - this.boozeAmount) * 1000;
/*     */         
/* 162 */         if (player.func_70093_af()) space = Math.min(space, 1000);
/*     */ 
/*     */         
/*     */         FluidStack fs;
/* 166 */         if (space >= 1000 && (
/* 167 */           fs = LiquidUtil.drainContainer(player, hand, FluidRegistry.WATER, space, FluidContainerOutputMode.InPlacePreferred, true)) != null && fs.amount >= 1000) {
/*     */           
/* 169 */           int amount = fs.amount / 1000 * 1000;
/*     */           
/* 171 */           fs = LiquidUtil.drainContainer(player, hand, FluidRegistry.WATER, amount, FluidContainerOutputMode.InPlacePreferred, true);
/* 172 */           if (fs.amount != amount) return false;
/*     */           
/* 174 */           LiquidUtil.drainContainer(player, hand, FluidRegistry.WATER, amount, FluidContainerOutputMode.InPlacePreferred, false);
/*     */           
/* 176 */           this.type = 1;
/* 177 */           this.boozeAmount += amount / 1000;
/*     */           
/* 179 */           return true;
/* 180 */         }  if (stack.func_77973_b() == Items.field_151015_O) {
/* 181 */           this.type = 1;
/* 182 */           int amount = StackUtil.getSize(stack);
/* 183 */           if (player.func_70093_af()) amount = 1; 
/* 184 */           if (amount > 64 - this.wheatCount) amount = 64 - this.wheatCount; 
/* 185 */           if (amount <= 0) return false; 
/* 186 */           this.wheatCount = (byte)(this.wheatCount + amount);
/*     */           
/* 188 */           StackUtil.consumeOrError(player, hand, amount);
/* 189 */           alterComposition();
/*     */           
/* 191 */           return true;
/* 192 */         }  if (StackUtil.checkItemEquality(stack, ItemName.crop_res.getItemStack((Enum)CropResItemType.hops))) {
/* 193 */           this.type = 1;
/* 194 */           int amount = StackUtil.getSize(stack);
/* 195 */           if (player.func_70093_af()) amount = 1; 
/* 196 */           if (amount > 64 - this.hopsCount) amount = 64 - this.hopsCount; 
/* 197 */           if (amount <= 0) return false; 
/* 198 */           this.hopsCount = (byte)(this.hopsCount + amount);
/*     */           
/* 200 */           StackUtil.consumeOrError(player, hand, amount);
/* 201 */           alterComposition();
/*     */           
/* 203 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 207 */       if ((this.type == 0 || this.type == 2) && 
/* 208 */         stack.func_77973_b() == Items.field_151120_aE) {
/* 209 */         if (this.age > 600) return false;
/*     */         
/* 211 */         this.type = 2;
/* 212 */         int amount = StackUtil.getSize(stack);
/* 213 */         if (player.func_70093_af()) amount = 1; 
/* 214 */         if (this.boozeAmount + amount > 32) amount = 32 - this.boozeAmount; 
/* 215 */         if (amount <= 0) return false; 
/* 216 */         this.boozeAmount += amount;
/*     */         
/* 218 */         StackUtil.consumeOrError(player, hand, amount);
/*     */         
/* 220 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onClicked(EntityPlayer player) {
/* 230 */     super.onClicked(player);
/*     */     
/* 232 */     World world = func_145831_w();
/*     */     
/* 234 */     if (getActive()) {
/* 235 */       if (!world.field_72995_K) {
/* 236 */         StackUtil.dropAsEntity(world, this.field_174879_c, ItemName.treetap.getItemStack());
/* 237 */         setActive(false);
/*     */       } 
/* 239 */       drainLiquid(1);
/*     */       
/*     */       return;
/*     */     } 
/* 243 */     if (!world.field_72995_K) StackUtil.dropAsEntity(world, this.field_174879_c, new ItemStack(ItemName.barrel.getInstance(), 1, calculateMetaValue()));
/*     */     
/* 245 */     world.func_175656_a(this.field_174879_c, BlockName.scaffold.getBlockState(BlockScaffold.ScaffoldType.wood));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void alterComposition() {
/* 252 */     if (this.timeRatio <= 0) {
/* 253 */       this.age = 0;
/* 254 */     } else if (this.timeRatio == 1) {
/* 255 */       World world = func_145831_w();
/*     */       
/* 257 */       if (world.field_73012_v.nextBoolean())
/* 258 */       { this.timeRatio = 0; }
/*     */       
/* 260 */       else if (world.field_73012_v.nextBoolean()) { this.timeRatio = 5; }
/*     */     
/* 262 */     } else if (this.timeRatio == 2) {
/* 263 */       if ((func_145831_w()).field_73012_v.nextBoolean()) this.timeRatio = 5; 
/*     */     } else {
/* 265 */       this.timeRatio = 5;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drainLiquid(int amount) {
/* 275 */     if (isEmpty()) return false; 
/* 276 */     if (amount > this.boozeAmount) return false;
/*     */ 
/*     */     
/* 279 */     open();
/*     */     
/* 281 */     if (this.type == 2) {
/* 282 */       int progress = this.age * 100 / timeNedForRum(this.boozeAmount);
/* 283 */       this.boozeAmount -= amount;
/* 284 */       this.age = progress / 100 * timeNedForRum(this.boozeAmount);
/*     */     } else {
/* 286 */       this.boozeAmount -= amount;
/*     */     } 
/*     */     
/* 289 */     if (this.boozeAmount <= 0) {
/*     */       
/* 291 */       if (this.type == 1) {
/* 292 */         this.hopsCount = 0;
/* 293 */         this.wheatCount = 0;
/* 294 */         this.hopsRatio = 0;
/* 295 */         this.solidRatio = 0;
/* 296 */         this.timeRatio = 0;
/*     */       } 
/* 298 */       this.type = 0;
/* 299 */       this.opened = false;
/* 300 */       this.boozeAmount = 0;
/*     */     } 
/*     */     
/* 303 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void open() {
/* 310 */     if (this.opened)
/*     */       return; 
/* 312 */     this.opened = true;
/*     */     
/* 314 */     if (this.type == 1) {
/*     */       float ratio;
/*     */       
/* 317 */       if (this.hopsCount <= 0) {
/* 318 */         ratio = 0.0F;
/*     */       } else {
/* 320 */         ratio = this.hopsCount / this.wheatCount;
/*     */       } 
/*     */       
/* 323 */       if (ratio <= 0.25F) {
/* 324 */         this.hopsRatio = 0;
/* 325 */       } else if (ratio <= 0.33333334F) {
/* 326 */         this.hopsRatio = 1;
/* 327 */       } else if (ratio <= 0.5F) {
/* 328 */         this.hopsRatio = 2;
/* 329 */       } else if (ratio < 2.0F) {
/* 330 */         this.hopsRatio = 3;
/*     */       } else {
/* 332 */         this.hopsRatio = (byte)(int)Math.min(6.0D, Math.floor(ratio) + 2.0D);
/* 333 */         if (ratio >= 5.0F) this.timeRatio = 5;
/*     */       
/*     */       } 
/* 336 */       if (this.boozeAmount <= 0) {
/* 337 */         ratio = Float.POSITIVE_INFINITY;
/*     */       } else {
/* 339 */         ratio = (this.hopsCount + this.wheatCount) / this.boozeAmount;
/*     */       } 
/*     */       
/* 342 */       if (ratio <= 0.41666666F) {
/* 343 */         this.solidRatio = 0;
/* 344 */       } else if (ratio <= 0.5F) {
/* 345 */         this.solidRatio = 1;
/* 346 */       } else if (ratio < 1.0F) {
/* 347 */         this.solidRatio = 2;
/* 348 */       } else if (ratio == 1.0F) {
/* 349 */         this.solidRatio = 3;
/* 350 */       } else if (ratio < 2.0F) {
/* 351 */         this.solidRatio = 4;
/* 352 */       } else if (ratio < 2.4F) {
/* 353 */         this.solidRatio = 5;
/*     */       } else {
/* 355 */         this.solidRatio = 6;
/* 356 */         if (ratio >= 4.0F) this.timeRatio = 5;
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateMetaValue() {
/* 364 */     if (isEmpty()) return 0; 
/* 365 */     if (this.type == 1) {
/* 366 */       open();
/* 367 */       int value = 0;
/* 368 */       value |= this.timeRatio;
/* 369 */       value <<= 3;
/* 370 */       value |= this.hopsRatio;
/* 371 */       value <<= 3;
/* 372 */       value |= this.solidRatio;
/* 373 */       value <<= 5;
/* 374 */       value |= this.boozeAmount - 1;
/* 375 */       value <<= 2;
/* 376 */       value |= this.type;
/*     */ 
/*     */       
/* 379 */       return value;
/*     */     } 
/* 381 */     if (this.type == 2) {
/* 382 */       open();
/* 383 */       int value = 0;
/* 384 */       int progress = this.age * 100 / timeNedForRum(this.boozeAmount);
/* 385 */       if (progress > 100) progress = 100; 
/* 386 */       value |= progress;
/* 387 */       value <<= 5;
/* 388 */       value |= this.boozeAmount - 1;
/* 389 */       value <<= 2;
/* 390 */       value |= this.type;
/* 391 */       return value;
/*     */     } 
/* 393 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int timeNedForRum(int amount) {
/* 400 */     return (int)((1200 * amount) * Math.pow(0.95D, (amount - 1)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
/* 405 */     return BlockName.scaffold.getItemStack(BlockScaffold.ScaffoldType.wood);
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ItemStack> getAuxDrops(int fortune) {
/* 410 */     List<ItemStack> ret = new ArrayList<>(super.getAuxDrops(fortune));
/*     */     
/* 412 */     ret.add(ItemName.barrel.getItemStack());
/*     */     
/* 414 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   private int type = 0;
/*     */   
/* 422 */   private int boozeAmount = 0;
/*     */   
/* 424 */   private int age = 0;
/*     */ 
/*     */   
/*     */   private boolean opened;
/*     */ 
/*     */   
/* 430 */   private byte hopsCount = 0;
/*     */   
/* 432 */   private byte wheatCount = 0;
/*     */ 
/*     */ 
/*     */   
/* 436 */   private byte solidRatio = 0;
/*     */   
/* 438 */   private byte hopsRatio = 0;
/*     */   
/* 440 */   private byte timeRatio = 0;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\TileEntityBarrel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */