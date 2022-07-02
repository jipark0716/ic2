/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.api.item.ITerraformingBP;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.item.ItemMulti;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.DimensionType;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tfbp
/*    */   extends ItemMulti<Tfbp.TfbpType>
/*    */   implements ITerraformingBP
/*    */ {
/*    */   public static void init() {
/* 24 */     for (TfbpType tfbp : TfbpType.values()) {
/* 25 */       if (tfbp.logic != null) {
/* 26 */         tfbp.logic.init();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Tfbp() {
/* 32 */     super(ItemName.tfbp, TfbpType.class);
/*    */     
/* 34 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getConsume(ItemStack stack) {
/* 39 */     TfbpType type = (TfbpType)getType(stack);
/*    */     
/* 41 */     return (type == null) ? 0.0D : type.consume;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack stack) {
/* 46 */     TfbpType type = (TfbpType)getType(stack);
/*    */     
/* 48 */     return (type == null) ? 0 : type.range;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canInsert(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
/* 53 */     TfbpType type = (TfbpType)getType(stack);
/* 54 */     if (type == null) return false;
/*    */     
/* 56 */     if (type == TfbpType.cultivation && world.field_73011_w
/* 57 */       .func_186058_p() == DimensionType.THE_END) {
/* 58 */       IC2.achievements.issueAchievement(player, "terraformEndCultivation");
/*    */     }
/*    */     
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean terraform(ItemStack stack, World world, BlockPos pos) {
/* 66 */     TfbpType type = (TfbpType)getType(stack);
/* 67 */     if (type == null) return false;
/*    */     
/* 69 */     if (type.logic == null) return false; 
/* 70 */     return type.logic.terraform(world, pos);
/*    */   }
/*    */   
/*    */   public enum TfbpType implements IIdProvider {
/* 74 */     blank(0.0D, 0, null),
/* 75 */     chilling(2000.0D, 50, new Chilling()),
/* 76 */     cultivation(4000.0D, 40, new Cultivation()),
/* 77 */     desertification(2500.0D, 40, new Desertification()),
/* 78 */     flatification(4000.0D, 40, new Flatification()),
/* 79 */     irrigation(3000.0D, 60, new Irrigation()),
/* 80 */     mushroom(8000.0D, 25, new Mushroom()); public final double consume;
/*    */     
/*    */     TfbpType(double consume, int range, TerraformerBase logic) {
/* 83 */       this.consume = consume;
/* 84 */       this.range = range;
/* 85 */       this.logic = logic;
/*    */     }
/*    */     public final int range; final TerraformerBase logic;
/*    */     
/*    */     public String getName() {
/* 90 */       return name();
/*    */     }
/*    */ 
/*    */     
/*    */     public int getId() {
/* 95 */       return ordinal();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Tfbp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */