/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import ic2.core.ref.ItemName;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.RenderItem;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiTransformer extends GuiIC2<ContainerTransformer> {
/* 20 */   public String[] mode = new String[] { "", "", "", "" };
/*    */   
/*    */   public GuiTransformer(ContainerTransformer container) {
/* 23 */     super((ContainerBase)container, 219);
/*    */     
/* 25 */     this.mode[1] = Localization.translate("ic2.Transformer.gui.switch.mode1");
/* 26 */     this.mode[2] = Localization.translate("ic2.Transformer.gui.switch.mode2");
/* 27 */     this.mode[3] = Localization.translate("ic2.Transformer.gui.switch.mode3");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton guibutton) throws IOException {
/* 32 */     super.func_146284_a(guibutton);
/*    */     
/* 34 */     ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerTransformer)this.container).base, guibutton.field_146127_k);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_73864_a(int i, int j, int k) throws IOException {
/* 39 */     super.func_73864_a(i, j, k);
/*    */     
/* 41 */     int x = i - this.field_147003_i;
/* 42 */     int y = j - this.field_147009_r;
/*    */     
/* 44 */     if (x >= 150 && y >= 32 && x <= 167 && y <= 49) {
/* 45 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerTransformer)this.container).base, 3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 51 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 53 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.Transformer.gui.Output"), 6, 30, 4210752);
/* 54 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.Transformer.gui.Input"), 6, 43, 4210752);
/*    */     
/* 56 */     this.field_146289_q.func_78276_b(((TileEntityTransformer)((ContainerTransformer)this.container).base).getoutputflow() + " " + Localization.translate("ic2.generic.text.EUt"), 52, 30, 2157374);
/* 57 */     this.field_146289_q.func_78276_b(((TileEntityTransformer)((ContainerTransformer)this.container).base).getinputflow() + " " + Localization.translate("ic2.generic.text.EUt"), 52, 45, 2157374);
/*    */ 
/*    */ 
/*    */     
/* 61 */     RenderItem renderItem = this.field_146297_k.func_175599_af();
/* 62 */     RenderHelper.func_74520_c();
/*    */     
/* 64 */     switch (((TileEntityTransformer)((ContainerTransformer)this.container).base).getMode()) {
/*    */       case redstone:
/* 66 */         renderItem.func_175042_a(ItemName.wrench.getItemStack(), 152, 67);
/*    */         break;
/*    */       case stepdown:
/* 69 */         renderItem.func_175042_a(ItemName.wrench.getItemStack(), 152, 87);
/*    */         break;
/*    */       case stepup:
/* 72 */         renderItem.func_175042_a(ItemName.wrench.getItemStack(), 152, 107);
/*    */         break;
/*    */     } 
/*    */     
/* 76 */     RenderHelper.func_74518_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 81 */     super.func_73866_w_();
/*    */     
/* 83 */     this.field_146292_n.add(new GuiButton(0, (this.field_146294_l - this.field_146999_f) / 2 + 7, (this.field_146295_m - this.field_147000_g) / 2 + 65, 144, 20, this.mode[1]));
/* 84 */     this.field_146292_n.add(new GuiButton(1, (this.field_146294_l - this.field_146999_f) / 2 + 7, (this.field_146295_m - this.field_147000_g) / 2 + 85, 144, 20, this.mode[2]));
/* 85 */     this.field_146292_n.add(new GuiButton(2, (this.field_146294_l - this.field_146999_f) / 2 + 7, (this.field_146295_m - this.field_147000_g) / 2 + 105, 144, 20, this.mode[3]));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 90 */     return background;
/*    */   }
/*    */   
/* 93 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITransfomer.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\GuiTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */