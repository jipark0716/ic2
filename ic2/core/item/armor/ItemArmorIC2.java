/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import ic2.api.item.IMetalArmor;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.profile.Version;
/*     */ import ic2.core.ref.IItemModelProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmorIC2
/*     */   extends ItemArmor
/*     */   implements IItemModelProvider, IMetalArmor
/*     */ {
/*     */   private final String armorName;
/*     */   private final Object repairMaterial;
/*     */   private Map<Capability<?>, Function<ItemStack, ?>> caps;
/*     */   
/*     */   public ItemArmorIC2(ItemName name, ItemArmor.ArmorMaterial armorMaterial, String armorName, EntityEquipmentSlot armorType, Object repairMaterial) {
/*  52 */     super(armorMaterial, -1, armorType);
/*     */     
/*  54 */     this.repairMaterial = repairMaterial;
/*  55 */     this.armorName = armorName;
/*     */     
/*  57 */     func_77656_e(armorMaterial.func_78046_a(armorType));
/*  58 */     if (name != null) func_77655_b(name.name()); 
/*  59 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  61 */     if (name != null) {
/*  62 */       BlocksItems.registerItem((Item)this, IC2.getIdentifier(name.name()));
/*  63 */       name.setInstance((Item)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(ItemName name) {
/*  70 */     ModelLoader.setCustomModelResourceLocation((Item)this, 0, ItemIC2.getModelLocation(name, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
/*  75 */     char suffix1 = (this.field_77881_a == EntityEquipmentSlot.LEGS) ? '2' : '1';
/*  76 */     String suffix2 = (type != null && hasOverlayTexture()) ? "_overlay" : "";
/*     */     
/*  78 */     return "ic2:textures/armor/" + this.armorName + '_' + suffix1 + suffix2 + ".png";
/*     */   }
/*     */   
/*     */   protected boolean hasOverlayTexture() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77658_a() {
/*  87 */     return "ic2." + super.func_77658_a().substring(5);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/*  92 */     return func_77658_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77657_g(ItemStack stack) {
/*  97 */     return func_77667_c(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack stack) {
/* 102 */     return Localization.translate(func_77667_c(stack));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_194125_a(CreativeTabs tab) {
/* 107 */     return (isEnabled() && super.func_194125_a(tab));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled() {
/* 112 */     return Version.shouldEnable(getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack toRepair, ItemStack repair) {
/* 128 */     return (repair != null && Util.matchesOD(repair, this.repairMaterial));
/*     */   }
/*     */   
/*     */   public <T> void addCapability(Capability<T> cap, Function<ItemStack, T> lookup) {
/* 132 */     if (this.caps == null) {
/* 133 */       this.caps = new IdentityHashMap<>();
/*     */     }
/* 135 */     assert !this.caps.containsKey(cap);
/* 136 */     this.caps.put(cap, lookup);
/*     */   }
/*     */ 
/*     */   
/*     */   public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable NBTTagCompound nbt) {
/* 141 */     return new ICapabilityProvider()
/*     */       {
/*     */         public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
/* 144 */           return (ItemArmorIC2.this.caps != null && ItemArmorIC2.this.caps.containsKey(capability));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
/* 150 */           return (ItemArmorIC2.this.caps == null || !ItemArmorIC2.this.caps.containsKey(capability)) ? null : (T)((Function)ItemArmorIC2.this.caps.get(capability)).apply(stack);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */