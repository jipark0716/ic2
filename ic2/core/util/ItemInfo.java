/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.api.info.IInfoProvider;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.item.type.DustResourceType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemInfo
/*    */   implements IInfoProvider
/*    */ {
/*    */   public double getEnergyValue(ItemStack stack) {
/* 22 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*    */     
/* 24 */     if (StackUtil.checkItemEquality(stack, ItemName.single_use_battery.getItemStack()))
/* 25 */       return 1200.0D; 
/* 26 */     if (StackUtil.checkItemEquality(stack, Items.field_151137_ax))
/* 27 */       return 800.0D; 
/* 28 */     if (StackUtil.checkItemEquality(stack, ItemName.dust.getItemStack((Enum)DustResourceType.energium))) {
/* 29 */       return 16000.0D;
/*    */     }
/* 31 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFuelValue(ItemStack stack, boolean allowLava) {
/* 37 */     if (StackUtil.isEmpty(stack)) return 0;
/*    */ 
/*    */     
/* 40 */     if ((StackUtil.checkItemEquality(stack, ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap)) || 
/* 41 */       StackUtil.checkItemEquality(stack, ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box))) && 
/* 42 */       !ConfigUtil.getBool(MainConfig.get(), "misc/allowBurningScrap")) {
/* 43 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 47 */     FluidStack liquid = FluidUtil.getFluidContained(stack);
/*    */ 
/*    */     
/* 50 */     boolean isLava = (liquid != null && liquid.amount > 0 && liquid.getFluid() == FluidRegistry.LAVA);
/* 51 */     if (isLava && !allowLava) return 0;
/*    */     
/* 53 */     int ret = TileEntityFurnace.func_145952_a(stack);
/*    */     
/* 55 */     return isLava ? (ret / 10) : ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\ItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */