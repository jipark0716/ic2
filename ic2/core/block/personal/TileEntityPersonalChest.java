/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.DelegatingInventory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
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
/*     */ public class TileEntityPersonalChest
/*     */   extends TileEntityInventory
/*     */   implements IPersonalBlock, IHasGui
/*     */ {
/*  52 */   public final InvSlot contentSlot = new InvSlot((IInventorySlotHolder)this, "content", InvSlot.Access.NONE, 54);
/*     */ 
/*     */   
/*  55 */   private GameProfile owner = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  63 */     super.func_145839_a(nbt);
/*     */     
/*  65 */     if (nbt.func_74764_b("ownerGameProfile")) {
/*  66 */       this.owner = NBTUtil.func_152459_a(nbt.func_74775_l("ownerGameProfile"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  76 */     super.func_189515_b(nbt);
/*     */     
/*  78 */     if (this.owner != null) {
/*  79 */       NBTTagCompound ownerNbt = new NBTTagCompound();
/*  80 */       NBTUtil.func_180708_a(ownerNbt, this.owner);
/*  81 */       nbt.func_74782_a("ownerGameProfile", (NBTBase)ownerNbt);
/*     */     } 
/*     */     
/*  84 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/*  90 */     super.updateEntityClient();
/*     */     
/*  92 */     this.prevLidAngle = this.lidAngle;
/*     */     
/*  94 */     if (this.usingPlayerCount > 0 && this.lidAngle <= 0) {
/*  95 */       World world = func_145831_w();
/*  96 */       world.func_184133_a(null, this.field_174879_c, SoundEvents.field_187657_V, SoundCategory.BLOCKS, 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/*  99 */     if ((this.usingPlayerCount == 0 && this.lidAngle > 0) || (this.usingPlayerCount > 0 && this.lidAngle < 10)) {
/* 100 */       if (this.usingPlayerCount > 0) {
/* 101 */         this.lidAngle = (byte)(this.lidAngle + 1);
/*     */       } else {
/* 103 */         this.lidAngle = (byte)(this.lidAngle - 1);
/*     */       } 
/*     */       
/* 106 */       int closeThreshold = 5;
/*     */       
/* 108 */       if (this.lidAngle < closeThreshold && this.prevLidAngle >= closeThreshold) {
/* 109 */         World world = func_145831_w();
/* 110 */         world.func_184133_a(null, this.field_174879_c, SoundEvents.field_187651_T, SoundCategory.BLOCKS, 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/* 117 */     return aabbs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {
/* 122 */     if (!(func_145831_w()).field_72995_K) {
/* 123 */       this.usingPlayers.add(player);
/* 124 */       updateUsingPlayerCount();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {
/* 130 */     if (!(func_145831_w()).field_72995_K) {
/* 131 */       this.usingPlayers.remove(player);
/* 132 */       updateUsingPlayerCount();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateUsingPlayerCount() {
/* 137 */     this.usingPlayerCount = this.usingPlayers.size();
/*     */     
/* 139 */     ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "usingPlayerCount");
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 144 */     List<String> ret = super.getNetworkedFields();
/*     */     
/* 146 */     ret.add("owner");
/* 147 */     ret.add("usingPlayerCount");
/*     */     
/* 149 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer player) {
/* 154 */     if (!permitsAccess(player.func_146103_bH())) {
/* 155 */       IC2.platform.messagePlayer(player, "This safe is owned by " + this.owner.getName(), new Object[0]);
/*     */       
/* 157 */       return false;
/*     */     } 
/*     */     
/* 160 */     if (!this.contentSlot.isEmpty()) {
/* 161 */       IC2.platform.messagePlayer(player, "Can't wrench non-empty safe", new Object[0]);
/*     */       
/* 163 */       return false;
/*     */     } 
/*     */     
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean permitsAccess(GameProfile profile) {
/* 174 */     return checkAccess(this, profile);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getPrivilegedInventory(GameProfile accessor) {
/* 179 */     if (!permitsAccess(accessor)) return (IInventory)this;
/*     */ 
/*     */     
/* 182 */     return (IInventory)new DelegatingInventory((IInventory)this)
/*     */       {
/*     */         public int func_70302_i_() {
/* 185 */           return TileEntityPersonalChest.this.contentSlot.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public ItemStack func_70301_a(int index) {
/* 190 */           return TileEntityPersonalChest.this.contentSlot.get(index);
/*     */         }
/*     */ 
/*     */         
/*     */         public ItemStack func_70298_a(int index, int amount) {
/* 195 */           ItemStack stack = func_70301_a(index);
/* 196 */           if (StackUtil.isEmpty(stack)) return StackUtil.emptyStack;
/*     */           
/* 198 */           if (amount >= StackUtil.getSize(stack)) {
/* 199 */             func_70299_a(index, StackUtil.emptyStack);
/*     */             
/* 201 */             return stack;
/* 202 */           }  if (amount != 0) {
/* 203 */             if (amount < 0) {
/* 204 */               int space = Math.min(TileEntityPersonalChest.this.contentSlot.getStackSizeLimit(), stack.func_77976_d()) - StackUtil.getSize(stack);
/*     */               
/* 206 */               amount = Math.max(amount, -space);
/*     */             } 
/*     */             
/* 209 */             stack = StackUtil.decSize(stack, amount);
/* 210 */             func_70299_a(index, stack);
/*     */           } 
/*     */           
/* 213 */           ItemStack ret = StackUtil.copyWithSize(stack, amount);
/*     */           
/* 215 */           return ret;
/*     */         }
/*     */ 
/*     */         
/*     */         public ItemStack func_70304_b(int index) {
/* 220 */           ItemStack ret = func_70301_a(index);
/*     */           
/* 222 */           if (!StackUtil.isEmpty(ret)) {
/* 223 */             func_70299_a(index, StackUtil.emptyStack);
/*     */           }
/*     */           
/* 226 */           return ret;
/*     */         }
/*     */ 
/*     */         
/*     */         public void func_70299_a(int index, ItemStack stack) {
/* 231 */           TileEntityPersonalChest.this.contentSlot.put(index, stack);
/* 232 */           func_70296_d();
/*     */         }
/*     */ 
/*     */         
/*     */         public int func_70297_j_() {
/* 237 */           return TileEntityPersonalChest.this.contentSlot.getStackSizeLimit();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean func_94041_b(int index, ItemStack stack) {
/* 242 */           return TileEntityPersonalChest.this.contentSlot.accepts(stack);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static <T extends TileEntity & IPersonalBlock> boolean checkAccess(T te, GameProfile profile) {
/* 248 */     if (profile == null) return (((IPersonalBlock)te).getOwner() == null);
/*     */     
/* 250 */     GameProfile teOwner = ((IPersonalBlock)te).getOwner();
/* 251 */     if (!(te.func_145831_w()).field_72995_K) {
/* 252 */       if (teOwner == null) {
/* 253 */         ((IPersonalBlock)te).setOwner(profile);
/* 254 */         ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)te, "owner");
/*     */         
/* 256 */         return true;
/* 257 */       }  if (te.func_145831_w().func_73046_m().func_184103_al().func_152596_g(profile)) {
/* 258 */         return true;
/*     */       }
/* 260 */     } else if (teOwner == null) {
/* 261 */       return true;
/*     */     } 
/*     */     
/* 264 */     return (teOwner.getId() != null) ? teOwner.getId().equals(profile.getId()) : teOwner.getName().equals(profile.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile getOwner() {
/* 269 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(GameProfile owner) {
/* 274 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canEntityDestroy(Entity entity) {
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 284 */     if (!(func_145831_w()).field_72995_K && !permitsAccess(player.func_146103_bH())) {
/* 285 */       IC2.platform.messagePlayer(player, "This safe is owned by " + getOwner().getName(), new Object[0]);
/*     */       
/* 287 */       return false;
/*     */     } 
/*     */     
/* 290 */     return super.onActivated(player, hand, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityPersonalChest> getGuiContainer(EntityPlayer player) {
/* 295 */     func_174889_b(player);
/*     */     
/* 297 */     return (ContainerBase<TileEntityPersonalChest>)new DynamicContainer<TileEntityPersonalChest>(this, player, GuiParser.parse(this.teBlock))
/*     */       {
/*     */         public void func_75134_a(EntityPlayer player) {
/* 300 */           ((TileEntityPersonalChest)this.base).onGuiClosed(player);
/*     */           
/* 302 */           super.func_75134_a(player);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 310 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {
/* 315 */     func_174886_c(player);
/*     */   }
/*     */   
/*     */   public float getLidAngle(float partialTicks) {
/* 319 */     return Util.lerp(this.prevLidAngle, this.lidAngle, partialTicks) / 10.0F;
/*     */   }
/*     */ 
/*     */   
/* 323 */   private static final List<AxisAlignedBB> aabbs = Arrays.asList(new AxisAlignedBB[] { new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D) });
/*     */ 
/*     */   
/* 326 */   private final Set<EntityPlayer> usingPlayers = Collections.newSetFromMap(new WeakHashMap<>());
/*     */   private static final int openingSteps = 10;
/*     */   private int usingPlayerCount;
/*     */   private byte lidAngle;
/*     */   private byte prevLidAngle;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\TileEntityPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */