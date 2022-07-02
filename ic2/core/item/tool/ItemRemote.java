/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.BlockDynamite;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ public class ItemRemote
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemRemote() {
/*  42 */     super(ItemName.remote);
/*     */     
/*  44 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  52 */     if (world.field_72995_K) return EnumActionResult.SUCCESS;
/*     */     
/*  54 */     IBlockState state = world.func_180495_p(pos);
/*  55 */     Block block = state.func_177230_c();
/*  56 */     if (block != BlockName.dynamite.getInstance()) return EnumActionResult.SUCCESS;
/*     */     
/*  58 */     ItemStack stack = StackUtil.get(player, hand);
/*  59 */     if (!((Boolean)state.func_177229_b(BlockDynamite.linked)).booleanValue()) {
/*  60 */       addRemote(pos, stack);
/*  61 */       world.func_175656_a(pos, state.func_177226_a(BlockDynamite.linked, Boolean.valueOf(true)));
/*     */     } else {
/*  63 */       int index = hasRemote(pos, stack);
/*     */       
/*  65 */       if (index > -1) {
/*  66 */         world.func_175656_a(pos, state.func_177226_a(BlockDynamite.linked, Boolean.valueOf(false)));
/*  67 */         removeRemote(index, stack);
/*     */       } else {
/*  69 */         IC2.platform.messagePlayer(player, "This dynamite stick is not linked to this remote, cannot unlink.", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  81 */     ItemStack stack = StackUtil.get(player, hand);
/*  82 */     if (world.field_72995_K) return new ActionResult(EnumActionResult.SUCCESS, stack); 
/*  83 */     IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/dynamiteomote.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */     
/*  85 */     launchRemotes(world, stack, player);
/*  86 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRemote(BlockPos pos, ItemStack freq) {
/*  94 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/*  95 */     if (!compound.func_74764_b("coords")) compound.func_74782_a("coords", (NBTBase)new NBTTagList()); 
/*  96 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/*  97 */     NBTTagCompound coord = new NBTTagCompound();
/*  98 */     coord.func_74768_a("x", pos.func_177958_n());
/*  99 */     coord.func_74768_a("y", pos.func_177956_o());
/* 100 */     coord.func_74768_a("z", pos.func_177952_p());
/* 101 */     coords.func_74742_a((NBTBase)coord);
/* 102 */     compound.func_74782_a("coords", (NBTBase)coords);
/* 103 */     freq.func_77964_b(coords.func_74745_c());
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/* 109 */     if (stack.func_77952_i() > 0) tooltip.add("Linked to " + stack.func_77952_i() + " dynamite");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void launchRemotes(World world, ItemStack freq, EntityPlayer player) {
/* 117 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 118 */     if (!compound.func_74764_b("coords"))
/* 119 */       return;  NBTTagList coords = compound.func_150295_c("coords", 10);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     for (int i = 0; i < coords.func_74745_c(); ) {
/* 125 */       NBTTagCompound coord = coords.func_150305_b(i);
/*     */       
/* 127 */       BlockPos pos = new BlockPos(coord.func_74762_e("x"), coord.func_74762_e("y"), coord.func_74762_e("z"));
/*     */       
/* 129 */       if (world.func_175667_e(pos)) {
/* 130 */         IBlockState state = world.func_180495_p(pos);
/* 131 */         if (state.func_177230_c() == BlockName.dynamite.getInstance() && ((Boolean)state
/* 132 */           .func_177229_b(BlockDynamite.linked)).booleanValue()) {
/* 133 */           state.func_177230_c().removedByPlayer(state, world, pos, player, false);
/* 134 */           world.func_175698_g(pos);
/*     */         } 
/* 136 */         coords.func_74744_a(i);
/*     */         continue;
/*     */       } 
/* 139 */       i++;
/*     */     } 
/*     */     
/* 142 */     freq.func_77964_b(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hasRemote(BlockPos pos, ItemStack freq) {
/* 149 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 150 */     if (!compound.func_74764_b("coords")) return -1;
/*     */     
/* 152 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/*     */     
/* 154 */     for (int i = 0; i < coords.func_74745_c(); i++) {
/* 155 */       NBTTagCompound coord = coords.func_150305_b(i);
/*     */       
/* 157 */       if (coord.func_74762_e("x") == pos.func_177958_n() && coord
/* 158 */         .func_74762_e("y") == pos.func_177956_o() && coord
/* 159 */         .func_74762_e("z") == pos.func_177952_p()) {
/* 160 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeRemote(int index, ItemStack freq) {
/* 171 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 172 */     if (!compound.func_74764_b("coords"))
/*     */       return; 
/* 174 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/* 175 */     NBTTagList newCoords = new NBTTagList();
/*     */     
/* 177 */     for (int i = 0; i < coords.func_74745_c(); i++) {
/* 178 */       if (i != index) newCoords.func_74742_a((NBTBase)coords.func_150305_b(i));
/*     */     
/*     */     } 
/* 181 */     compound.func_74782_a("coords", (NBTBase)newCoords);
/* 182 */     freq.func_77964_b(newCoords.func_74745_c());
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */