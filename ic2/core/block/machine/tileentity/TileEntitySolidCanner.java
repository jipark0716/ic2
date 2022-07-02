/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.block.invslot.InvSlotConsumableSolidCanner;
/*    */ import ic2.core.block.invslot.InvSlotProcessable;
/*    */ import ic2.core.block.invslot.InvSlotProcessableSolidCanner;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntitySolidCanner extends TileEntityStandardMachine<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> {
/*    */   public final InvSlotConsumableSolidCanner canInputSlot;
/*    */   
/*    */   public TileEntitySolidCanner() {
/* 20 */     super(2, 200, 1);
/*    */     
/* 22 */     this.inputSlot = (InvSlotProcessable<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput>)new InvSlotProcessableSolidCanner(this, "input", 1);
/* 23 */     this.canInputSlot = new InvSlotConsumableSolidCanner(this, "canInput", 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStartSoundFile() {
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getInterruptSoundFile() {
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Collection<ItemStack> getOutput(ItemStack output) {
/* 40 */     return Collections.singleton(output);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 45 */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntitySolidCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */