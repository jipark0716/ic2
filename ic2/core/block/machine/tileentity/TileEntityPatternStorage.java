/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.recipe.IPatternStorage;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableId;
/*     */ import ic2.core.block.machine.container.ContainerPatternStorage;
/*     */ import ic2.core.block.machine.gui.GuiPatternStorage;
/*     */ import ic2.core.item.ItemCrystalMemory;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.uu.UuIndex;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityPatternStorage
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, INetworkClientTileEntityEventListener, IPatternStorage
/*     */ {
/*  40 */   public final InvSlotConsumableId diskSlot = new InvSlotConsumableId((IInventorySlotHolder)this, "SaveSlot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, new Item[] { ItemName.crystal_memory.getInstance() });
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  45 */     super.func_145839_a(nbttagcompound);
/*     */     
/*  47 */     readContents(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  52 */     super.func_189515_b(nbt);
/*     */     
/*  54 */     writeContentsAsNbtList(nbt);
/*     */     
/*  56 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/*  61 */     super.onPlaced(stack, placer, facing);
/*     */     
/*  63 */     if (!(func_145831_w()).field_72995_K) {
/*  64 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*  65 */       readContents(nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
/*  71 */     drop = super.adjustDrop(drop, wrench);
/*     */     
/*  73 */     if (wrench || this.teBlock.getDefaultDrop() == TeBlock.DefaultDrop.Self) {
/*  74 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(drop);
/*  75 */       writeContentsAsNbtList(nbt);
/*     */     } 
/*     */     
/*  78 */     return drop;
/*     */   }
/*     */   
/*     */   public void readContents(NBTTagCompound nbt) {
/*  82 */     NBTTagList patternList = nbt.func_150295_c("patterns", 10);
/*     */     
/*  84 */     for (int i = 0; i < patternList.func_74745_c(); i++) {
/*  85 */       NBTTagCompound contentTag = patternList.func_150305_b(i);
/*  86 */       ItemStack Item = new ItemStack(contentTag);
/*  87 */       addPattern(Item);
/*     */     } 
/*     */     
/*  90 */     refreshInfo();
/*     */   }
/*     */   
/*     */   private void writeContentsAsNbtList(NBTTagCompound nbt) {
/*  94 */     NBTTagList list = new NBTTagList();
/*     */     
/*  96 */     for (ItemStack stack : this.patterns) {
/*  97 */       NBTTagCompound contentTag = new NBTTagCompound();
/*  98 */       stack.func_77955_b(contentTag);
/*  99 */       list.func_74742_a((NBTBase)contentTag);
/*     */     } 
/*     */     
/* 102 */     nbt.func_74782_a("patterns", (NBTBase)list);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityPatternStorage> getGuiContainer(EntityPlayer player) {
/* 107 */     return (ContainerBase<TileEntityPatternStorage>)new ContainerPatternStorage(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 113 */     return (GuiScreen)new GuiPatternStorage(new ContainerPatternStorage(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 118 */     switch (event) {
/*     */       case 0:
/* 120 */         if (!this.patterns.isEmpty()) {
/* 121 */           if (this.index <= 0) {
/* 122 */             this.index = this.patterns.size() - 1;
/*     */           } else {
/* 124 */             this.index--;
/*     */           } 
/*     */           
/* 127 */           refreshInfo();
/*     */         } 
/*     */         break;
/*     */       case 1:
/* 131 */         if (!this.patterns.isEmpty()) {
/* 132 */           if (this.index >= this.patterns.size() - 1) {
/* 133 */             this.index = 0;
/*     */           } else {
/* 135 */             this.index++;
/*     */           } 
/*     */           
/* 138 */           refreshInfo();
/*     */         } 
/*     */         break;
/*     */       case 2:
/* 142 */         if (this.index >= 0 && this.index < this.patterns.size() && 
/* 143 */           !this.diskSlot.isEmpty()) {
/* 144 */           ItemStack crystalMemory = this.diskSlot.get();
/*     */           
/* 146 */           if (crystalMemory.func_77973_b() instanceof ItemCrystalMemory) {
/* 147 */             ((ItemCrystalMemory)crystalMemory.func_77973_b()).writecontentsTag(crystalMemory, this.patterns.get(this.index));
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 154 */         if (!this.diskSlot.isEmpty()) {
/* 155 */           ItemStack crystalMemory = this.diskSlot.get();
/*     */           
/* 157 */           if (crystalMemory.func_77973_b() instanceof ItemCrystalMemory) {
/* 158 */             ItemStack record = ((ItemCrystalMemory)crystalMemory.func_77973_b()).readItemStack(crystalMemory);
/*     */             
/* 160 */             if (record != null) {
/* 161 */               addPattern(record);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshInfo() {
/* 171 */     if (this.index < 0 || this.index >= this.patterns.size()) this.index = 0;
/*     */     
/* 173 */     this.maxIndex = this.patterns.size();
/*     */     
/* 175 */     if (this.patterns.isEmpty()) {
/* 176 */       this.pattern = null;
/*     */     } else {
/* 178 */       this.pattern = this.patterns.get(this.index);
/* 179 */       this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPattern(ItemStack stack) {
/* 190 */     if (StackUtil.isEmpty(stack)) throw new IllegalArgumentException("empty stack: " + StackUtil.toStringSafe(stack));
/*     */     
/* 192 */     for (ItemStack pattern : this.patterns) {
/* 193 */       if (StackUtil.checkItemEquality(pattern, stack)) return false;
/*     */     
/*     */     } 
/* 196 */     this.patterns.add(stack);
/* 197 */     refreshInfo();
/*     */     
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getPatterns() {
/* 204 */     return this.patterns;
/*     */   }
/*     */ 
/*     */   
/* 208 */   private final List<ItemStack> patterns = new ArrayList<>();
/*     */ 
/*     */   
/* 211 */   public int index = 0;
/*     */   public int maxIndex;
/*     */   public ItemStack pattern;
/*     */   public double patternUu;
/*     */   public double patternEu;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityPatternStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */