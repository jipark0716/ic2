/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.invslot.InvSlotProcessable;
/*     */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*     */ import ic2.core.recipe.BasicMachineRecipeManager;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityMacerator
/*     */   extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>
/*     */ {
/*     */   public TileEntityMacerator() {
/*  32 */     super(2, 300, 1);
/*     */     
/*  34 */     this.inputSlot = (InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack>)new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.macerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  42 */     Recipes.macerator = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void updateEntityClient() {
/* 135 */     super.updateEntityClient();
/*     */     
/* 137 */     World world = func_145831_w();
/*     */     
/* 139 */     if (getActive() && world.field_73012_v.nextInt(8) == 0) {
/* 140 */       for (int i = 0; i < 4; i++) {
/* 141 */         double x = this.field_174879_c.func_177958_n() + 0.5D + world.field_73012_v.nextFloat() * 0.6D - 0.3D;
/* 142 */         double y = (this.field_174879_c.func_177956_o() + 1) + world.field_73012_v.nextFloat() * 0.2D - 0.1D;
/* 143 */         double z = this.field_174879_c.func_177952_p() + 0.5D + world.field_73012_v.nextFloat() * 0.6D - 0.3D;
/*     */         
/* 145 */         world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 152 */     return "Machines/MaceratorOp.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 157 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 162 */     return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public static List<Map.Entry<ItemStack, ItemStack>> recipes = new Vector<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityMacerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */