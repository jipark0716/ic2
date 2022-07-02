/*     */ package ic2.core.audio;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
/*     */ public class AudioConfigHandler
/*     */ {
/*     */   @SubscribeEvent
/*     */   public static void guiCreate(GuiScreenEvent.InitGuiEvent.Post event) {
/*  40 */     if (event.getGui() instanceof net.minecraft.client.gui.GuiScreenOptionsSounds) {
/*  41 */       final GuiScreen gui = event.getGui();
/*     */       
/*  43 */       int slot = 11;
/*  44 */       event.getButtonList().add(new GuiButton(50, gui.field_146294_l / 2 - 155 + slot % 2 * 160, gui.field_146295_m / 6 - 12 + 24 * (slot >> 1), 150, 20, "") {
/*  45 */             private final String categoryName = "IC2";
/*  46 */             private float volume = IC2.audioManager.getMasterVolume();
/*     */ 
/*     */             
/*     */             private boolean pressed;
/*     */ 
/*     */ 
/*     */             
/*     */             protected int func_146114_a(boolean mouseOver) {
/*  54 */               return 0;
/*     */             }
/*     */             
/*     */             private String getVolumeDisplay() {
/*  58 */               return (this.volume == 0.0F) ? Localization.translate("options.off") : ((int)(this.volume * 100.0F) + "%");
/*     */             }
/*     */             
/*     */             private void updateVolume(int mouseX, int mouseY) {
/*  62 */               ((AudioManagerClient)IC2.audioManager).masterVolume = this.volume = Util.limit((mouseX - this.field_146128_h + 4) / (this.field_146120_f - 8), 0.0F, 1.0F);
/*  63 */               this.field_146126_j = "IC2: " + getVolumeDisplay();
/*     */ 
/*     */               
/*  66 */               MainConfig.get().set("audio/volume", String.format("%.2f", new Object[] { Float.valueOf(this.volume) }));
/*  67 */               MainConfig.save();
/*     */             }
/*     */ 
/*     */             
/*     */             protected void func_146119_b(Minecraft mc, int mouseX, int mouseY) {
/*  72 */               if (this.field_146125_m) {
/*  73 */                 if (this.pressed) updateVolume(mouseX, mouseY);
/*     */                 
/*  75 */                 GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  76 */                 int sliderPos = (int)(this.volume * (this.field_146120_f - 8));
/*  77 */                 func_73729_b(this.field_146128_h + sliderPos, this.field_146129_i, 0, 66, 4, 20);
/*  78 */                 func_73729_b(this.field_146128_h + sliderPos + 4, this.field_146129_i, 196, 66, 4, 20);
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
/*  84 */               if (super.func_146116_c(mc, mouseX, mouseY)) {
/*  85 */                 updateVolume(mouseX, mouseY);
/*  86 */                 this.pressed = true;
/*     */                 
/*  88 */                 return true;
/*     */               } 
/*  90 */               return false;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void func_146113_a(SoundHandler handler) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void func_146118_a(int mouseX, int mouseY) {
/* 100 */               if (this.pressed) {
/* 101 */                 gui.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
/*     */               }
/*     */               
/* 104 */               this.pressed = false;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\AudioConfigHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */