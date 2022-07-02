/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.invslot.InvSlotProcessable;
/*    */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*    */ import ic2.core.recipe.BasicMachineRecipeManager;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.Vector;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityExtractor
/*    */   extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>
/*    */ {
/*    */   public TileEntityExtractor() {
/* 27 */     super(2, 300, 1);
/*    */     
/* 29 */     this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.extractor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void init() {
/* 37 */     Recipes.extractor = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStartSoundFile() {
/* 71 */     return "Machines/ExtractorOp.ogg";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getInterruptSoundFile() {
/* 76 */     return "Machines/InterruptOne.ogg";
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 81 */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 88 */   public static List<Map.Entry<ItemStack, ItemStack>> recipes = new Vector<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityExtractor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */