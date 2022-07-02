/*    */ package ic2.api.event;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public abstract class ProfileEvent extends Event {
/*    */   public static class Load extends ProfileEvent {
/*    */     public final Set<String> loaded;
/*    */     public final String active;
/*    */     
/*    */     public Load(Set<String> loaded, String active) {
/* 12 */       this.loaded = loaded;
/* 13 */       this.active = active;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Switch
/*    */     extends ProfileEvent
/*    */   {
/*    */     public final String from;
/*    */     public final String to;
/*    */     
/*    */     public Switch(String from, String to) {
/* 25 */       this.from = from;
/* 26 */       this.to = to;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\event\ProfileEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */