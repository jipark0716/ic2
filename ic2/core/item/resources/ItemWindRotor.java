/*    */ package ic2.core.item.resources;
/*    */ 
/*    */ import ic2.api.item.IKineticRotor;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.ItemGradualInt;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.util.ITooltipFlag;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemWindRotor extends ItemGradualInt implements IKineticRotor {
/*    */   private final int maxWindStrength;
/*    */   private final int minWindStrength;
/*    */   private final int radius;
/*    */   
/*    */   public ItemWindRotor(ItemName name, int Radius, int durability, float efficiency, int minWindStrength, int maxWindStrength, ResourceLocation RenderTexture) {
/* 24 */     super(name, durability);
/*    */     
/* 26 */     func_77625_d(1);
/*    */     
/* 28 */     this.radius = Radius;
/* 29 */     this.efficiency = efficiency;
/* 30 */     this.renderTexture = RenderTexture;
/* 31 */     this.minWindStrength = minWindStrength;
/* 32 */     this.maxWindStrength = maxWindStrength;
/* 33 */     this.water = (name != ItemName.rotor_wood);
/*    */   }
/*    */   private final float efficiency; private final ResourceLocation renderTexture; private final boolean water;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 39 */     tooltip.add(Localization.translate("ic2.itemrotor.wind.info", new Object[] { Integer.valueOf(this.minWindStrength), Integer.valueOf(this.maxWindStrength) }));
/* 40 */     IKineticRotor.GearboxType type = null;
/*    */     
/* 42 */     if ((Minecraft.func_71410_x()).field_71462_r instanceof ic2.core.block.kineticgenerator.gui.GuiWaterKineticGenerator) {
/* 43 */       type = IKineticRotor.GearboxType.WATER;
/* 44 */     } else if ((Minecraft.func_71410_x()).field_71462_r instanceof ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator) {
/* 45 */       type = IKineticRotor.GearboxType.WIND;
/*    */     } 
/*    */     
/* 48 */     if (type != null) {
/* 49 */       tooltip.add(Localization.translate("ic2.itemrotor.fitsin." + isAcceptedType(stack, type)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDiameter(ItemStack stack) {
/* 55 */     return this.radius;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getRotorRenderTexture(ItemStack stack) {
/* 60 */     return this.renderTexture;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getEfficiency(ItemStack stack) {
/* 65 */     return this.efficiency;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinWindStrength(ItemStack stack) {
/* 70 */     return this.minWindStrength;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxWindStrength(ItemStack stack) {
/* 75 */     return this.maxWindStrength;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAcceptedType(ItemStack stack, IKineticRotor.GearboxType type) {
/* 80 */     return (type == IKineticRotor.GearboxType.WIND || this.water);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\resources\ItemWindRotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */