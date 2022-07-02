/*    */ package ic2.core;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraftforge.common.util.FakePlayerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ic2Player
/*    */ {
/*    */   public static EntityPlayer get(World world) {
/* 17 */     if (world instanceof WorldServer) {
/* 18 */       return (EntityPlayer)FakePlayerFactory.get((WorldServer)world, getGameProfile(world.field_73011_w.getDimension()));
/*    */     }
/*    */     
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private static GameProfile getGameProfile(int dim) {
/* 26 */     String name = "[IC2 " + dim + "]";
/* 27 */     UUID uuid = UUID.nameUUIDFromBytes(name.getBytes(Charsets.UTF_8));
/* 28 */     return new GameProfile(uuid, name);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\Ic2Player.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */