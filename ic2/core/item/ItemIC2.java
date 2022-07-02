/*     */ package ic2.core.item;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.profile.Version;
/*     */ import ic2.core.ref.IItemModelProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.common.capabilities.ICapabilityProvider;
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
/*     */ public class ItemIC2
/*     */   extends Item
/*     */   implements IItemModelProvider
/*     */ {
/*  38 */   private EnumRarity rarity = EnumRarity.COMMON;
/*     */   
/*     */   private Map<Capability<?>, Function<ItemStack, ?>> caps;
/*     */   
/*     */   public ItemIC2(ItemName name) {
/*  43 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  45 */     if (name != null) {
/*  46 */       func_77655_b(name.name());
/*  47 */       BlocksItems.registerItem(this, IC2.getIdentifier(name.name()));
/*  48 */       name.setInstance(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(ItemName name) {
/*  55 */     registerModel(0, name, (String)null);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void registerModel(int meta, ItemName name) {
/*  60 */     registerModel(this, meta, name, (String)null);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void registerModel(int meta, ItemName name, String extraName) {
/*  65 */     registerModel(this, meta, name, extraName);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerModel(Item item, int meta, ItemName name, String extraName) {
/*  70 */     ModelLoader.setCustomModelResourceLocation(item, meta, getModelLocation(name, extraName));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static ModelResourceLocation getModelLocation(ItemName name, String extraName) {
/*  75 */     StringBuilder loc = new StringBuilder();
/*     */     
/*  77 */     loc.append("ic2");
/*  78 */     loc.append(':');
/*  79 */     loc.append(name.getPath(extraName));
/*     */     
/*  81 */     return new ModelResourceLocation(loc.toString(), null);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getItemColor(ItemStack stack) {
/*  86 */     return 16777215;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  91 */     return "ic2." + super.func_77658_a().substring(5);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/*  96 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77657_g(ItemStack stack) {
/* 101 */     return func_77667_c(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack stack) {
/* 106 */     return Localization.translate(func_77667_c(stack));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_194125_a(CreativeTabs tab) {
/* 111 */     return (isEnabled() && super.func_194125_a(tab));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled() {
/* 116 */     return Version.shouldEnable(getClass());
/*     */   }
/*     */   
/*     */   public ItemIC2 setRarity(EnumRarity rarity) {
/* 120 */     if (rarity == null) throw new NullPointerException("null rarity");
/*     */     
/* 122 */     this.rarity = rarity;
/*     */     
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 129 */     if (stack.func_77948_v() && this.rarity != EnumRarity.EPIC) {
/* 130 */       return EnumRarity.RARE;
/*     */     }
/*     */     
/* 133 */     return this.rarity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
/* 138 */     return shouldReequip(oldStack, newStack, slotChanged);
/*     */   }
/*     */   
/*     */   public static boolean shouldReequip(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
/* 142 */     if (!StackUtil.checkItemEquality(newStack, oldStack)) return true; 
/* 143 */     if (oldStack == null) return false; 
/* 144 */     if (StackUtil.getSize(oldStack) != StackUtil.getSize(newStack)) return true;
/*     */     
/* 146 */     return (slotChanged && StackUtil.checkItemEqualityStrict(oldStack, newStack));
/*     */   }
/*     */   
/*     */   protected static int getRemainingUses(ItemStack stack) {
/* 150 */     return stack.func_77958_k() - stack.func_77952_i() + 1;
/*     */   }
/*     */   
/*     */   public <T> void addCapability(Capability<T> cap, Function<ItemStack, T> lookup) {
/* 154 */     if (this.caps == null) {
/* 155 */       this.caps = new IdentityHashMap<>();
/*     */     }
/* 157 */     assert !this.caps.containsKey(cap);
/* 158 */     this.caps.put(cap, lookup);
/*     */   }
/*     */ 
/*     */   
/*     */   public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable NBTTagCompound nbt) {
/* 163 */     return new ICapabilityProvider()
/*     */       {
/*     */         public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
/* 166 */           return (ItemIC2.this.caps != null && ItemIC2.this.caps.containsKey(capability));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
/* 172 */           return (ItemIC2.this.caps == null || !ItemIC2.this.caps.containsKey(capability)) ? null : (T)((Function)ItemIC2.this.caps.get(capability)).apply(stack);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */