/*     */ package ic2.core.item.upgrade;
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.gui.EnumCycleHandler;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.gui.VanillaButton;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.DynamicHandHeldContainer;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiConditionProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.ContainerHandHeldInventory;
/*     */ import ic2.core.item.tool.HandHeldInventory;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class HandHeldAdvancedUpgrade extends HandHeldInventory implements IHolographicSlotProvider, IGuiConditionProvider {
/*     */   @GuiSynced
/*     */   protected boolean meta;
/*     */   @GuiSynced
/*     */   protected boolean energy;
/*     */   @ClientModifiable
/*     */   protected NbtSettings nbt;
/*     */   
/*     */   private static ItemStack checkContainerStack(EntityPlayer player, ItemStack containerStack) {
/*  43 */     if (!(player.func_130014_f_()).field_72995_K && player.field_71070_bA instanceof ContainerHandHeldInventory && ((ContainerHandHeldInventory)player.field_71070_bA).base instanceof HandHeldUpgradeOption) {
/*     */ 
/*     */       
/*  46 */       addMaintainedPlayer(player);
/*     */ 
/*     */       
/*  49 */       return (ItemStack)ReflectionUtil.getFieldValue(ReflectionUtil.getField(HandHeldInventory.class, ItemStack.class), ((ContainerHandHeldInventory)player.field_71070_bA).base);
/*     */     } 
/*     */     
/*  52 */     return containerStack;
/*     */   }
/*     */   private static final byte META_GUI = 0; private static final byte DAMAGE_GUI = 1; private static final byte ENERGY_GUI = 2; private static final byte ORE_GUI = 3;
/*     */   
/*     */   public HandHeldAdvancedUpgrade(EntityPlayer player, ItemStack containerStack) {
/*  57 */     super(player, checkContainerStack(player, containerStack), 9);
/*     */     
/*  59 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(containerStack);
/*  60 */     this.meta = readTag(nbt, "meta");
/*  61 */     this.nbt = NbtSettings.getFromNBT(getTag(nbt, "nbt").func_74771_c("type"));
/*  62 */     this.energy = readTag(nbt, "energy");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void save() {
/*  67 */     super.save();
/*     */     
/*  69 */     if (IC2.platform.isSimulating()) {
/*  70 */       NBTTagCompound nbt = this.containerStack.func_77978_p();
/*  71 */       assert nbt != null;
/*     */       
/*  73 */       writeTag(nbt, "meta", this.meta);
/*  74 */       NBTTagCompound tag = getTag(nbt, "nbt");
/*  75 */       tag.func_74757_a("active", this.nbt.enabled());
/*  76 */       tag.func_74774_a("type", this.nbt.getForNBT());
/*  77 */       nbt.func_74782_a("nbtSettings", (NBTBase)tag);
/*  78 */       writeTag(nbt, "energy", this.energy);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NBTTagCompound getTag(NBTTagCompound nbt, String name) {
/*  83 */     return nbt.func_74775_l(name + "Settings");
/*     */   }
/*     */   
/*     */   protected static boolean readTag(NBTTagCompound nbt, String name) {
/*  87 */     return getTag(nbt, name).func_74767_n("active");
/*     */   }
/*     */   
/*     */   protected static void writeTag(NBTTagCompound nbt, String name, boolean active) {
/*  91 */     NBTTagCompound tag = getTag(nbt, name);
/*  92 */     tag.func_74757_a("active", active);
/*  93 */     nbt.func_74782_a(name + "Settings", (NBTBase)tag);
/*     */   }
/*     */   
/*     */   static IHasGui delegate(EntityPlayer player, ItemStack stack, int ID) {
/*  97 */     switch (ID) { case 0:
/*  98 */         return (IHasGui)new HandHeldValueConfig(new HandHeldAdvancedUpgrade(player, stack), "meta");
/*  99 */       case 1: return null;
/* 100 */       case 2: return (IHasGui)new HandHeldValueConfig(new HandHeldAdvancedUpgrade(player, stack), "energy");
/* 101 */       case 3: return (IHasGui)new HandHeldOre(new HandHeldAdvancedUpgrade(player, stack)); }
/* 102 */      IC2.log.warn(LogCategory.Network, "Unexpected delegate ID: " + ID); return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 108 */     return (ContainerBase<?>)DynamicHandHeldContainer.create(this, player, getNode());
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 114 */     final DynamicGui<?> gui = DynamicGui.create(this, player, getNode());
/* 115 */     if (Util.inDev()) {
/* 116 */       gui.addElement(((VanillaButton)(new VanillaButton((GuiIC2)gui, 10, 62, 50, 20, (IClickHandler)new EnumCycleHandler<NbtSettings>(NbtSettings.VALUES, this.nbt)
/*     */             {
/*     */               public void onClick(MouseButton button) {
/* 119 */                 super.onClick(button);
/*     */                 
/* 121 */                 HandHeldAdvancedUpgrade.this.nbt = (NbtSettings)getCurrentValue();
/* 122 */                 ((NetworkManager)IC2.network.get(false)).sendHandHeldInvField(gui.getContainer(), "nbt");
/*     */               }
/* 124 */             })).withText("ic2.upgrade.advancedGUI.nbt")).withTooltip(new Supplier<String>() {
/* 125 */               private final String NBT = Localization.translate("ic2.upgrade.advancedGUI.nbt");
/*     */ 
/*     */               
/*     */               public String get() {
/* 129 */                 return Localization.translate("ic2.upgrade.advancedGUI.nbt.desc", new Object[] { Localization.translate(this.this$0.nbt.name), TextFormatting.GRAY, 
/* 130 */                       Localization.translate(this.this$0.nbt.name + ".desc", new Object[] { this.NBT }) });
/*     */               }
/*     */             }));
/*     */     }
/* 134 */     return (GuiScreen)gui;
/*     */   }
/*     */   
/*     */   protected GuiParser.GuiNode getNode() {
/*     */     try {
/* 139 */       return GuiParser.parse(GUI_XML, HandHeldAdvancedUpgrade.class);
/* 140 */     } catch (SAXException e) {
/* 141 */       throw new RuntimeException("XML Exception opening Advanced Upgrade GUI", e);
/* 142 */     } catch (IOException e) {
/* 143 */       throw new RuntimeException("IO Exception opening Advanced Upgrade GUI", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/* 154 */     return this.containerStack.func_77977_a();
/*     */   }
/*     */   
/*     */   EntityPlayer getPlayer() {
/* 158 */     return this.player;
/*     */   }
/*     */   
/*     */   ItemStack getContainerStack() {
/* 162 */     return this.containerStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getStacksForName(String name) {
/* 167 */     if ("filter".equals(name)) {
/* 168 */       return this.inventory;
/*     */     }
/* 170 */     throw new IllegalArgumentException("Unexpected stack array name requested: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getGuiState(String name) {
/* 175 */     if ("meta".equals(name)) {
/* 176 */       return this.meta;
/*     */     }
/* 178 */     if ("nbt".equals(name)) {
/* 179 */       return this.nbt.enabled();
/*     */     }
/* 181 */     if ("energy".equals(name)) {
/* 182 */       return (this.energy || this.nbt == NbtSettings.EXACT);
/*     */     }
/* 184 */     if ("dev".equals(name)) {
/* 185 */       return Util.inDev();
/*     */     }
/* 187 */     throw new IllegalArgumentException("Unexpected conditional name requested: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEvent(String event) {
/* 192 */     boolean dev = false;
/* 193 */     if (Util.inDev() && event.endsWith("Dev")) {
/* 194 */       dev = true;
/* 195 */       event = event.substring(0, event.lastIndexOf("Dev"));
/*     */     } 
/*     */     
/* 198 */     if ("meta".equals(event)) {
/* 199 */       if (!dev) {
/* 200 */         this.meta = !this.meta;
/*     */       } else {
/* 202 */         launchGUI((IHasGui)new HandHeldValueConfig(this, "meta"), 0);
/*     */       } 
/* 204 */     } else if ("energy".equals(event)) {
/* 205 */       if (!dev) {
/* 206 */         this.energy = !this.energy;
/*     */       } else {
/* 208 */         launchGUI((IHasGui)new HandHeldValueConfig(this, "energy"), 2);
/*     */       } 
/* 210 */     } else if ("ore".equals(event)) {
/* 211 */       assert dev;
/* 212 */       launchGUI((IHasGui)new HandHeldOre(this), 3);
/*     */     } else {
/* 214 */       super.onEvent(event);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void launchGUI(IHasGui gui, int ID) {
/* 219 */     if (!(this.player.func_130014_f_()).field_72995_K) {
/* 220 */       HandHeldInventory.addMaintainedPlayer(this.player);
/* 221 */       IC2.platform.launchSubGui(this.player, gui, ID);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   private static final ResourceLocation GUI_XML = new ResourceLocation("ic2", "guidef/advanced_upgrade.xml");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\HandHeldAdvancedUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */