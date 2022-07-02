/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class KeyboardClient extends Keyboard {
/*    */   private static final String keyCategory = "IC2";
/*    */   
/*    */   public KeyboardClient() {
/* 20 */     if (!registeredKeys) {
/* 21 */       registeredKeys = true;
/*    */       
/* 23 */       ClientRegistry.registerKeyBinding(this.altKey);
/* 24 */       ClientRegistry.registerKeyBinding(this.boostKey);
/* 25 */       ClientRegistry.registerKeyBinding(this.modeSwitchKey);
/* 26 */       ClientRegistry.registerKeyBinding(this.sideinventoryKey);
/* 27 */       ClientRegistry.registerKeyBinding(this.expandinfo);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendKeyUpdate() {
/* 33 */     Set<Keyboard.Key> keys = EnumSet.noneOf(Keyboard.Key.class);
/*    */     
/* 35 */     GuiScreen currentScreen = (Minecraft.func_71410_x()).field_71462_r;
/*    */     
/* 37 */     if (currentScreen == null || currentScreen.field_146291_p) {
/* 38 */       if (GameSettings.func_100015_a(this.altKey)) keys.add(Keyboard.Key.alt); 
/* 39 */       if (GameSettings.func_100015_a(this.boostKey)) keys.add(Keyboard.Key.boost); 
/* 40 */       if (GameSettings.func_100015_a(this.mc.field_71474_y.field_74351_w)) keys.add(Keyboard.Key.forward); 
/* 41 */       if (GameSettings.func_100015_a(this.modeSwitchKey)) keys.add(Keyboard.Key.modeSwitch); 
/* 42 */       if (GameSettings.func_100015_a(this.mc.field_71474_y.field_74314_A)) keys.add(Keyboard.Key.jump); 
/* 43 */       if (GameSettings.func_100015_a(this.sideinventoryKey)) keys.add(Keyboard.Key.sideInventory); 
/* 44 */       if (GameSettings.func_100015_a(this.expandinfo)) keys.add(Keyboard.Key.hubMode); 
/* 45 */       for (Keyboard.IKeyWatcher watcher : this.watchers) watcher.checkForKey(keys);
/*    */     
/*    */     } 
/* 48 */     int currentKeyState = Keyboard.Key.toInt(keys);
/*    */     
/* 50 */     if (currentKeyState != this.lastKeyState) {
/* 51 */       ((NetworkManager)IC2.network.get(false)).initiateKeyUpdate(currentKeyState);
/* 52 */       processKeyUpdate(IC2.platform.getPlayerInstance(), currentKeyState);
/* 53 */       this.lastKeyState = currentKeyState;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 58 */   private final Minecraft mc = Minecraft.func_71410_x();
/* 59 */   private final KeyBinding altKey = new KeyBinding("ALT Key", 56, "IC2");
/* 60 */   private final KeyBinding boostKey = new KeyBinding("Boost Key", 29, "IC2");
/* 61 */   private final KeyBinding modeSwitchKey = new KeyBinding("Mode Switch Key", 50, "IC2");
/* 62 */   private final KeyBinding sideinventoryKey = new KeyBinding("Side Inventory Key", 46, "IC2");
/* 63 */   private final KeyBinding expandinfo = new KeyBinding("Hub Expand Key", 45, "IC2");
/*    */   private static boolean registeredKeys = false;
/* 65 */   private int lastKeyState = 0;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\KeyboardClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */