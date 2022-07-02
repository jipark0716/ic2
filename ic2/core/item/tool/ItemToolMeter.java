/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.energy.EnergyNet;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.item.IHandHeldInventory;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
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
/*    */ public class ItemToolMeter
/*    */   extends ItemIC2
/*    */   implements IBoxable, IHandHeldInventory
/*    */ {
/*    */   public ItemToolMeter() {
/* 37 */     super(ItemName.meter);
/*    */     
/* 39 */     this.field_77777_bU = 1;
/* 40 */     func_77656_e(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/* 45 */     if (world.field_72995_K) return EnumActionResult.PASS;
/*    */     
/* 47 */     IEnergyTile tile = EnergyNet.instance.getTile(world, pos);
/*    */     
/* 49 */     if (tile instanceof ic2.api.energy.tile.IEnergySource || tile instanceof ic2.api.energy.tile.IEnergyConductor || tile instanceof ic2.api.energy.tile.IEnergySink) {
/* 50 */       if (IC2.platform.launchGui(player, getInventory(player, StackUtil.get(player, hand)))) {
/* 51 */         ContainerMeter container = (ContainerMeter)player.field_71070_bA;
/* 52 */         container.setUut(tile);
/*    */         
/* 54 */         return EnumActionResult.SUCCESS;
/*    */       } 
/*    */     } else {
/* 57 */       IC2.platform.messagePlayer(player, "Not an energy net tile", new Object[0]);
/*    */     } 
/*    */     
/* 60 */     return EnumActionResult.SUCCESS;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/* 65 */     if (!(player.func_130014_f_()).field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerMeter) {
/* 66 */       HandHeldMeter euReader = (HandHeldMeter)((ContainerMeter)player.field_71070_bA).base;
/*    */       
/* 68 */       if (euReader.isThisContainer(stack)) {
/* 69 */         euReader.saveAsThrown(stack);
/* 70 */         player.func_71053_j();
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/* 84 */     return new HandHeldMeter(player, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemToolMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */