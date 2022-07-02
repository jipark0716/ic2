/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import ic2.core.util.Util;
/*    */ import ic2.core.uu.UuIndex;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemCrystalMemory
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemCrystalMemory() {
/* 23 */     super(ItemName.crystal_memory);
/*    */     
/* 25 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRepairable() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 36 */     ItemStack recorded = readItemStack(stack);
/*    */     
/* 38 */     if (!StackUtil.isEmpty(recorded)) {
/* 39 */       tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.Item") + " " + recorded.func_82833_r());
/* 40 */       tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.UU-Matter") + " " + Util.toSiString(UuIndex.instance.getInBuckets(recorded), 4) + "B");
/*    */     } else {
/*    */       
/* 43 */       tooltip.add(Localization.translate("ic2.item.CrystalMemory.tooltip.Empty"));
/*    */     } 
/*    */   }
/*    */   
/*    */   public ItemStack readItemStack(ItemStack stack) {
/* 48 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 49 */     NBTTagCompound contentTag = nbt.func_74775_l("Pattern");
/*    */     
/* 51 */     ItemStack Item = new ItemStack(contentTag);
/* 52 */     return Item;
/*    */   }
/*    */   
/*    */   public void writecontentsTag(ItemStack stack, ItemStack recorded) {
/* 56 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 57 */     NBTTagCompound contentTag = new NBTTagCompound();
/* 58 */     recorded.func_77955_b(contentTag);
/* 59 */     nbt.func_74782_a("Pattern", (NBTBase)contentTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemCrystalMemory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */