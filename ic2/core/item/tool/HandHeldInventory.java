/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.slot.SlotHologramSlot;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ 
/*     */ 
/*     */ public abstract class HandHeldInventory
/*     */   implements IHasGui
/*     */ {
/*     */   protected ItemStack containerStack;
/*     */   protected final ItemStack[] inventory;
/*     */   protected final EntityPlayer player;
/*     */   private boolean cleared;
/*     */   
/*     */   public HandHeldInventory(EntityPlayer player, ItemStack containerStack, int inventorySize) {
/*  34 */     this.containerStack = containerStack;
/*  35 */     this.inventory = new ItemStack[inventorySize];
/*  36 */     this.player = player;
/*     */     
/*  38 */     if (IC2.platform.isSimulating()) {
/*  39 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(containerStack);
/*     */       
/*  41 */       if (!nbt.func_150297_b("uid", 3))
/*     */       {
/*  43 */         nbt.func_74768_a("uid", IC2.random.nextInt());
/*     */       }
/*     */       
/*  46 */       NBTTagList contentList = nbt.func_150295_c("Items", 10);
/*     */       
/*  48 */       for (int i = 0; i < contentList.func_74745_c(); i++) {
/*  49 */         NBTTagCompound slotNbt = contentList.func_150305_b(i);
/*     */         
/*  51 */         int slot = slotNbt.func_74771_c("Slot");
/*     */         
/*  53 */         if (slot >= 0 && slot < this.inventory.length) {
/*  54 */           this.inventory[slot] = new ItemStack(slotNbt);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  62 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  67 */     for (ItemStack stack : this.inventory) {
/*  68 */       if (!StackUtil.isEmpty(stack)) return false;
/*     */     
/*     */     } 
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/*  76 */     return StackUtil.wrapEmpty(this.inventory[slot]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int amount) {
/*     */     ItemStack stack;
/*  83 */     if (index >= 0 && index < this.inventory.length && !StackUtil.isEmpty(stack = this.inventory[index])) {
/*     */       ItemStack ret;
/*     */       
/*  86 */       if (amount >= StackUtil.getSize(stack)) {
/*  87 */         ret = stack;
/*  88 */         this.inventory[index] = StackUtil.emptyStack;
/*     */       } else {
/*  90 */         ret = StackUtil.copyWithSize(stack, amount);
/*  91 */         this.inventory[index] = StackUtil.decSize(stack, amount);
/*     */       } 
/*     */       
/*  94 */       save();
/*     */       
/*  96 */       return ret;
/*     */     } 
/*  98 */     return StackUtil.emptyStack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/* 104 */     if (!StackUtil.isEmpty(stack) && StackUtil.getSize(stack) > func_70297_j_()) {
/* 105 */       stack = StackUtil.copyWithSize(stack, func_70297_j_());
/*     */     }
/*     */     
/* 108 */     if (StackUtil.isEmpty(stack)) {
/* 109 */       this.inventory[slot] = StackUtil.emptyStack;
/*     */     } else {
/* 111 */       this.inventory[slot] = stack;
/*     */     } 
/*     */     
/* 114 */     save();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 119 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack1) {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 129 */     save();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 134 */     return (player == this.player && getPlayerInventoryIndex() >= -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/* 149 */     ItemStack ret = func_70301_a(index);
/*     */     
/* 151 */     if (!StackUtil.isEmpty(ret)) {
/* 152 */       func_70299_a(index, null);
/*     */     }
/*     */     
/* 155 */     return ret;
/*     */   }
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 159 */     return 0;
/*     */   }
/*     */   
/*     */   public int func_174890_g() {
/* 163 */     return 0;
/*     */   }
/*     */   public void func_174885_b(int id, int value) {}
/*     */   public ITextComponent func_145748_c_() {
/* 167 */     return (ITextComponent)new TextComponentString(func_70005_c_());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {
/* 172 */     save();
/*     */     
/* 174 */     if (!(player.func_130014_f_()).field_72995_K) {
/* 175 */       if (PLAYERS_IN_GUI.contains(player)) {
/*     */         
/* 177 */         PLAYERS_IN_GUI.remove(player);
/*     */       } else {
/*     */         
/* 180 */         StackUtil.getOrCreateNbtData(this.containerStack).func_82580_o("uid");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isThisContainer(ItemStack stack) {
/* 186 */     if (StackUtil.isEmpty(stack) || stack.func_77973_b() != this.containerStack.func_77973_b()) return false;
/*     */     
/* 188 */     NBTTagCompound nbt = stack.func_77978_p();
/*     */     
/* 190 */     return (nbt != null && nbt.func_74762_e("uid") == getUid());
/*     */   }
/*     */   
/*     */   protected int getUid() {
/* 194 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(this.containerStack);
/*     */     
/* 196 */     return nbt.func_74762_e("uid");
/*     */   }
/*     */   
/*     */   protected int getPlayerInventoryIndex() {
/* 200 */     for (int i = -1; i < this.player.field_71071_by.func_70302_i_(); i++) {
/* 201 */       ItemStack stack = (i == -1) ? this.player.field_71071_by.func_70445_o() : this.player.field_71071_by.func_70301_a(i);
/*     */       
/* 203 */       if (isThisContainer(stack)) return i;
/*     */     
/*     */     } 
/* 206 */     return Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   protected void save() {
/* 210 */     if (!IC2.platform.isSimulating())
/* 211 */       return;  if (this.cleared)
/*     */       return; 
/* 213 */     boolean dropItself = false;
/*     */ 
/*     */     
/* 216 */     for (int i = 0; i < this.inventory.length; i++) {
/* 217 */       if (isThisContainer(this.inventory[i])) {
/* 218 */         this.inventory[i] = null;
/* 219 */         dropItself = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 224 */     NBTTagList contentList = new NBTTagList();
/*     */     
/* 226 */     for (int j = 0; j < this.inventory.length; j++) {
/* 227 */       if (!StackUtil.isEmpty(this.inventory[j])) {
/* 228 */         NBTTagCompound nbt = new NBTTagCompound();
/*     */         
/* 230 */         nbt.func_74774_a("Slot", (byte)j);
/* 231 */         this.inventory[j].func_77955_b(nbt);
/*     */         
/* 233 */         contentList.func_74742_a((NBTBase)nbt);
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     StackUtil.getOrCreateNbtData(this.containerStack).func_74782_a("Items", (NBTBase)contentList);
/*     */     try {
/* 239 */       this.containerStack = StackUtil.copyWithSize(this.containerStack, 1);
/* 240 */     } catch (IllegalArgumentException e) {
/* 241 */       CrashReport crash = new CrashReport("Hand held container stack vanished", e);
/*     */       
/* 243 */       CrashReportCategory category = crash.func_85058_a("Container stack");
/* 244 */       category.func_71507_a("Stack", StackUtil.toStringSafe(this.containerStack));
/* 245 */       category.func_71507_a("NBT", this.containerStack.func_77978_p());
/* 246 */       category.func_71507_a("Position", Integer.valueOf(getPlayerInventoryIndex()));
/* 247 */       category.func_71507_a("Had thrown", Boolean.valueOf(dropItself));
/*     */       
/* 249 */       category = crash.func_85058_a("Container info");
/* 250 */       category.func_71507_a("Type", getClass().getName());
/* 251 */       category.func_71507_a("Container", (this.player.field_71070_bA == null) ? null : this.player.field_71070_bA.getClass().getName());
/* 252 */       if (this.player.field_70170_p.field_72995_K)
/* 253 */         category.func_189529_a("GUI", new ICrashReportDetail<String>()
/*     */             {
/*     */               public String call() throws Exception {
/* 256 */                 GuiScreen gui = (Minecraft.func_71410_x()).field_71462_r;
/* 257 */                 return (gui == null) ? null : gui.getClass().getName();
/*     */               }
/*     */             }); 
/* 260 */       category.func_71507_a("Opened by", this.player);
/*     */       
/* 262 */       throw new ReportedException(crash);
/*     */     } 
/*     */ 
/*     */     
/* 266 */     if (dropItself) {
/* 267 */       StackUtil.dropAsEntity(this.player.func_130014_f_(), this.player.func_180425_c(), this.containerStack);
/* 268 */       func_174888_l();
/*     */     } else {
/* 270 */       int idx = getPlayerInventoryIndex();
/*     */       
/* 272 */       if (idx < -1) {
/* 273 */         IC2.log.warn(LogCategory.Item, "Handheld inventory saving failed for player " + this.player.func_145748_c_().func_150260_c() + '.');
/* 274 */         func_174888_l();
/* 275 */       } else if (idx == -1) {
/* 276 */         this.player.field_71071_by.func_70437_b(this.containerStack);
/*     */       } else {
/* 278 */         this.player.field_71071_by.func_70299_a(idx, this.containerStack);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveAsThrown(ItemStack stack) {
/* 284 */     assert IC2.platform.isSimulating();
/* 285 */     NBTTagList contentList = new NBTTagList();
/*     */     
/* 287 */     for (int i = 0; i < this.inventory.length; i++) {
/* 288 */       if (!StackUtil.isEmpty(this.inventory[i]) && !isThisContainer(this.inventory[i])) {
/* 289 */         NBTTagCompound nbt = new NBTTagCompound();
/*     */         
/* 291 */         nbt.func_74774_a("Slot", (byte)i);
/* 292 */         this.inventory[i].func_77955_b(nbt);
/*     */         
/* 294 */         contentList.func_74742_a((NBTBase)nbt);
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     StackUtil.getOrCreateNbtData(stack).func_74782_a("Items", (NBTBase)contentList);
/* 299 */     assert StackUtil.getOrCreateNbtData(stack).func_74762_e("uid") == 0;
/* 300 */     func_174888_l();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/* 305 */     for (int i = 0; i < this.inventory.length; i++) {
/* 306 */       this.inventory[i] = null;
/*     */     }
/*     */     
/* 309 */     this.cleared = true;
/*     */   }
/*     */   
/*     */   public SlotHologramSlot.ChangeCallback makeSaveCallback() {
/* 313 */     return new SlotHologramSlot.ChangeCallback()
/*     */       {
/*     */         public void onChanged(int index) {
/* 316 */           HandHeldInventory.this.save();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEvent(String event) {}
/*     */ 
/*     */   
/*     */   public static void addMaintainedPlayer(EntityPlayer player) {
/* 327 */     PLAYERS_IN_GUI.add(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   private static final Set<EntityPlayer> PLAYERS_IN_GUI = new HashSet<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */