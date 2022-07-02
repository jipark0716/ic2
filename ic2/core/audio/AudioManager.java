/*    */ package ic2.core.audio;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AudioManager
/*    */ {
/*    */   public void initialize() {}
/*    */   
/*    */   public void playOnce(Object obj, String soundFile) {}
/*    */   
/*    */   public String playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {
/* 14 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void chainSource(String source, FutureSound onFinish) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeSource(String source) {}
/*    */ 
/*    */   
/*    */   public void removeSources(Object obj) {}
/*    */ 
/*    */   
/*    */   public AudioSource createSource(Object obj, String initialSoundFile) {
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {}
/*    */ 
/*    */   
/*    */   public float getMasterVolume() {
/* 42 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public float getDefaultVolume() {
/* 46 */     return 0.0F;
/*    */   }
/*    */   
/*    */   protected boolean valid() {
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\AudioManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */