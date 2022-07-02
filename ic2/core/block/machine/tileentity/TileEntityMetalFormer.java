/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*     */ import ic2.core.block.machine.container.ContainerMetalFormer;
/*     */ import ic2.core.block.machine.gui.GuiMetalFormer;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.recipe.BasicMachineRecipeManager;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityMetalFormer extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> implements INetworkClientTileEntityEventListener {
/*     */   private int mode;
/*     */   
/*     */   public TileEntityMetalFormer() {
/*  32 */     super(10, 200, 1);
/*     */     
/*  34 */     this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.metalformerExtruding);
/*     */   }
/*     */   public static final int EventSwitch = 0;
/*     */   public static void init() {
/*  38 */     Recipes.metalformerExtruding = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
/*  39 */     Recipes.metalformerCutting = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
/*  40 */     Recipes.metalformerRolling = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
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
/*     */     
/*  74 */     if (ConfigUtil.getBool(MainConfig.get(), "recipes/allowCoinCrafting"));
/*     */   }
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
/*     */   public static void addRecipeCutting(ItemStack input, int amount, ItemStack output) {
/*  93 */     addRecipeCutting(Recipes.inputFactory.forStack(input, amount), output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRecipeCutting(IRecipeInput input, ItemStack output) {
/* 101 */     Recipes.metalformerCutting.addRecipe(input, null, false, new ItemStack[] { output });
/*     */   }
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
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 118 */     super.func_145839_a(nbt);
/*     */     
/* 120 */     setMode(nbt.func_74762_e("mode"));
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 125 */     super.func_189515_b(nbt);
/*     */     
/* 127 */     nbt.func_74768_a("mode", this.mode);
/*     */     
/* 129 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityMetalFormer> getGuiContainer(EntityPlayer player) {
/* 134 */     return (ContainerBase<TileEntityMetalFormer>)new ContainerMetalFormer(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 140 */     return (GuiScreen)new GuiMetalFormer(new ContainerMetalFormer(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 145 */     switch (event) { case 0:
/* 146 */         cycleMode();
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 152 */     super.onNetworkUpdate(field);
/*     */     
/* 154 */     if (field.equals("mode")) {
/* 155 */       setMode(this.mode);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMode() {
/* 160 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(int mode1) {
/* 164 */     InvSlotProcessableGeneric slot = (InvSlotProcessableGeneric)this.inputSlot;
/*     */     
/* 166 */     switch (mode1) {
/*     */       case 0:
/* 168 */         slot.setRecipeManager((IMachineRecipeManager)Recipes.metalformerExtruding);
/*     */         break;
/*     */       case 1:
/* 171 */         slot.setRecipeManager((IMachineRecipeManager)Recipes.metalformerRolling);
/*     */         break;
/*     */       case 2:
/* 174 */         slot.setRecipeManager((IMachineRecipeManager)Recipes.metalformerCutting);
/*     */         break;
/*     */       default:
/* 177 */         throw new RuntimeException("invalid mode: " + mode1);
/*     */     } 
/*     */     
/* 180 */     this.mode = mode1;
/*     */   }
/*     */   
/*     */   private void cycleMode() {
/* 184 */     setMode((getMode() + 1) % 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 189 */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityMetalFormer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */