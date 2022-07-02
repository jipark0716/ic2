/*    */ package ic2.core.block.machine.tileentity;
/*    */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Fluids;
/*    */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*    */ import ic2.core.block.invslot.InvSlotOutput;
/*    */ import ic2.core.block.invslot.InvSlotProcessable;
/*    */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*    */ import ic2.core.gui.dynamic.DynamicContainer;
/*    */ import ic2.core.gui.dynamic.DynamicGui;
/*    */ import ic2.core.gui.dynamic.GuiParser;
/*    */ import ic2.core.network.GuiSynced;
/*    */ import ic2.core.recipe.BasicMachineRecipeManager;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityOreWashing extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
/*    */   public final InvSlotConsumableLiquid fluidSlot;
/*    */   public final InvSlotOutput cellSlot;
/*    */   
/*    */   public TileEntityOreWashing() {
/* 38 */     super(16, 500, 3);
/*    */     
/* 40 */     this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.oreWashing);
/* 41 */     this.fluidSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByList((IInventorySlotHolder)this, "fluid", 1, new Fluid[] { FluidRegistry.WATER });
/* 42 */     this.cellSlot = new InvSlotOutput((IInventorySlotHolder)this, "cell", 1);
/*    */     
/* 44 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/* 45 */     this.fluidTank = (FluidTank)this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(new Fluid[] { FluidRegistry.WATER }));
/*    */   } @GuiSynced
/*    */   protected final FluidTank fluidTank; protected final Fluids fluids;
/*    */   public static void init() {
/* 49 */     Recipes.oreWashing = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 55 */     super.updateEntityServer();
/*    */     
/* 57 */     if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
/* 58 */       gainFluid();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output, Collection<ItemStack> processResult) {
/* 64 */     super.operateOnce(output, processResult);
/*    */     
/* 66 */     this.fluidTank.drainInternal(output.getRecipe().getMetaData().func_74762_e("amount"), true);
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
/* 71 */     MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> ret = super.getOutput();
/*    */     
/* 73 */     if (ret != null) {
/* 74 */       if (ret.getRecipe().getMetaData() == null) return null; 
/* 75 */       if (ret.getRecipe().getMetaData().func_74762_e("amount") > this.fluidTank.getFluidAmount()) return null;
/*    */     
/*    */     } 
/* 78 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean gainFluid() {
/* 82 */     return this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.cellSlot);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 88 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<TileEntityOreWashing> getGuiContainer(EntityPlayer player) {
/* 93 */     return (ContainerBase<TileEntityOreWashing>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 98 */     return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[] { UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming });
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityOreWashing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */