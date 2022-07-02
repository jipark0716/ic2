/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.api.util.IKeyboard;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Keyboard
/*     */   implements IKeyboard
/*     */ {
/*     */   public boolean isAltKeyDown(EntityPlayer player) {
/*  23 */     return get(player, Key.alt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoostKeyDown(EntityPlayer player) {
/*  31 */     return get(player, Key.boost);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForwardKeyDown(EntityPlayer player) {
/*  39 */     return get(player, Key.forward);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJumpKeyDown(EntityPlayer player) {
/*  47 */     return get(player, Key.jump);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModeSwitchKeyDown(EntityPlayer player) {
/*  55 */     return get(player, Key.modeSwitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideinventoryKeyDown(EntityPlayer player) {
/*  63 */     return get(player, Key.sideInventory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHudModeKeyDown(EntityPlayer player) {
/*  71 */     return get(player, Key.hubMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSneakKeyDown(EntityPlayer player) {
/*  79 */     return player.func_70093_af();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendKeyUpdate() {}
/*     */ 
/*     */   
/*     */   public void processKeyUpdate(EntityPlayer player, int keyState) {
/*  87 */     this.playerKeys.put(player, Key.fromInt(keyState));
/*     */   }
/*     */   
/*     */   public void removePlayerReferences(EntityPlayer player) {
/*  91 */     this.playerKeys.remove(player);
/*     */   }
/*     */   
/*     */   private boolean get(EntityPlayer player, Key key) {
/*  95 */     Set<Key> keys = this.playerKeys.get(player);
/*  96 */     if (keys == null) return false;
/*     */     
/*  98 */     return keys.contains(key);
/*     */   }
/*     */   
/*     */   public void addKeyWatcher(IKeyWatcher watcher) {
/* 102 */     this.watchers.add(watcher);
/*     */   }
/*     */   
/*     */   public boolean isKeyDown(EntityPlayer player, IKeyWatcher watcher) {
/* 106 */     return get(player, watcher.getRepresentation());
/*     */   }
/*     */   
/* 109 */   protected final List<IKeyWatcher> watchers = new ArrayList<>();
/* 110 */   private final Map<EntityPlayer, Set<Key>> playerKeys = new WeakHashMap<>();
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
/*     */   protected enum Key
/*     */   {
/* 128 */     alt,
/* 129 */     boost,
/* 130 */     forward,
/* 131 */     modeSwitch,
/* 132 */     jump,
/* 133 */     sideInventory,
/* 134 */     hubMode;
/*     */     
/* 136 */     public static final Key[] keys = values();
/*     */     
/*     */     public static int toInt(Iterable<Key> keySet) {
/* 139 */       int ret = 0;
/*     */       
/* 141 */       for (Key key : keySet) {
/* 142 */         ret |= 1 << key.ordinal();
/*     */       }
/*     */       
/* 145 */       return ret;
/*     */     } static {
/*     */     
/*     */     } public static Set<Key> fromInt(int keyState) {
/* 149 */       Set<Key> ret = EnumSet.noneOf(Key.class);
/*     */       
/* 151 */       for (int i = 0; keyState != 0 && i < keys.length; i++, keyState >>= 1) {
/* 152 */         if ((keyState & 0x1) != 0) {
/* 153 */           ret.add(keys[i]);
/*     */         }
/*     */       } 
/*     */       
/* 157 */       return ret;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface IKeyWatcher {
/*     */     @SideOnly(Side.CLIENT)
/*     */     void checkForKey(Set<Keyboard.Key> param1Set);
/*     */     
/*     */     Keyboard.Key getRepresentation();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Keyboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */