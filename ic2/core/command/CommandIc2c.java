/*    */ package ic2.core.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public class CommandIc2c
/*    */   extends CommandBase {
/*    */   public String func_71517_b() {
/* 22 */     return "ic2c";
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_71518_a(ICommandSender icommandsender) {
/* 27 */     return "/ic2c (rightClick <x> <y> <z> [XN|XP|YN|YP|ZN|ZP]) | currentItem";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
/* 34 */     if (args.length == 1) {
/* 35 */       return func_71530_a(args, new String[] { "rightClick", "currentItem" });
/*    */     }
/*    */     
/* 38 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 43 */     if (args.length == 0) {
/* 44 */       throw new WrongUsageException(func_71518_a(sender), new Object[0]);
/*    */     }
/*    */     
/* 47 */     if (args.length >= 4 && args.length <= 5 && args[0].equals("rightClick")) {
/* 48 */       cmdRightClick(sender, args);
/* 49 */     } else if (args.length == 1 && args[0].equals("currentItem")) {
/* 50 */       CommandIc2.cmdCurrentItem(sender);
/*    */     } else {
/* 52 */       CommandIc2.msg(sender, "Unknown Command.");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void cmdRightClick(ICommandSender sender, String[] args) throws CommandException {
/*    */     ClickSide side;
/* 59 */     BlockPos pos = new BlockPos(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
/*    */ 
/*    */     
/* 62 */     if (args.length == 5) {
/*    */       try {
/* 64 */         side = ClickSide.valueOf(args[4]);
/* 65 */       } catch (IllegalArgumentException e) {
/* 66 */         throw new CommandException("Invalid side: " + args[4], new Object[0]);
/*    */       } 
/*    */     } else {
/* 69 */       side = ClickSide.YP;
/*    */     } 
/*    */     
/* 72 */     Minecraft mc = Minecraft.func_71410_x();
/* 73 */     EntityPlayerSP player = mc.field_71439_g;
/* 74 */     mc.field_71442_b.func_187099_a(player, (WorldClient)player.func_130014_f_(), pos, side.facing, new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
/* 75 */     CommandIc2.msg(sender, "Right click executed.");
/*    */   }
/*    */   
/*    */   private enum ClickSide {
/* 79 */     XN((String)EnumFacing.WEST),
/* 80 */     XP((String)EnumFacing.EAST),
/* 81 */     YN((String)EnumFacing.DOWN),
/* 82 */     YP((String)EnumFacing.UP),
/* 83 */     ZN((String)EnumFacing.NORTH),
/* 84 */     ZP((String)EnumFacing.SOUTH);
/*    */     
/*    */     ClickSide(EnumFacing facing) {
/* 87 */       this.facing = facing;
/*    */     }
/*    */     
/*    */     final EnumFacing facing;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\command\CommandIc2c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */