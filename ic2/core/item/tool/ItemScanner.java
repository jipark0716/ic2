/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Player;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.init.OreValues;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.item.IHandHeldInventory;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ItemComparableItemStack;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ public class ItemScanner
/*     */   extends BaseElectricItem
/*     */   implements IBoxable, IHandHeldInventory
/*     */ {
/*     */   public ItemScanner() {
/*  51 */     this(ItemName.scanner, 100000.0D, 128.0D, 1);
/*     */   }
/*     */   
/*     */   public ItemScanner(ItemName name, double maxCharge, double transferLimit, int tier) {
/*  55 */     super(name, maxCharge, transferLimit, tier);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/*  61 */     super.func_77624_a(stack, world, tooltip, advanced);
/*  62 */     tooltip.add(Localization.translate("ic2.scanner.range", new Object[] { "" + getScanRange() }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  70 */     ItemStack stack = StackUtil.get(player, hand);
/*  71 */     if ((this.tier == 1 && !ElectricItem.manager.use(stack, 50.0D, (EntityLivingBase)player)) || (this.tier == 2 && 
/*  72 */       !ElectricItem.manager.use(stack, 250.0D, (EntityLivingBase)player))) {
/*  73 */       return new ActionResult(EnumActionResult.FAIL, stack);
/*     */     }
/*     */     
/*  76 */     if (!world.field_72995_K) {
/*  77 */       if (IC2.platform.launchGui(player, getInventory(player, stack)) && player.field_71070_bA instanceof ContainerToolScanner) {
/*     */         
/*  79 */         ContainerToolScanner container = (ContainerToolScanner)player.field_71070_bA;
/*  80 */         Map<ItemComparableItemStack, Integer> scanResult = scan(player.func_130014_f_(), player
/*  81 */             .func_180425_c(), 
/*  82 */             getScanRange());
/*     */         
/*  84 */         container.setResults(scanMapToSortedList(scanResult));
/*     */       } 
/*     */     } else {
/*  87 */       IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/ODScanner.ogg", true, IC2.audioManager.getDefaultVolume());
/*     */     } 
/*     */     
/*  90 */     return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player) {
/*  95 */     if (!(player.func_130014_f_()).field_72995_K && !StackUtil.isEmpty(stack) && player.field_71070_bA instanceof ContainerToolScanner) {
/*  96 */       HandHeldScanner scanner = (HandHeldScanner)((ContainerToolScanner)player.field_71070_bA).base;
/*     */       
/*  98 */       if (scanner.isThisContainer(stack)) {
/*  99 */         scanner.saveAsThrown(stack);
/* 100 */         player.func_71053_j();
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public int startLayerScan(ItemStack stack) {
/* 108 */     return ElectricItem.manager.use(stack, 50.0D, null) ? (getScanRange() / 2) : 0;
/*     */   }
/*     */   
/*     */   public int getScanRange() {
/* 112 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
/* 122 */     return new HandHeldScanner(player, stack);
/*     */   }
/*     */   
/*     */   private Map<ItemComparableItemStack, Integer> scan(World world, BlockPos center, int range) {
/* 126 */     Map<ItemComparableItemStack, Integer> ret = new HashMap<>();
/*     */     
/* 128 */     ChunkCache cache = new ChunkCache(world, center.func_177982_a(-range, -range, -range), center.func_177982_a(range, range, range), 0);
/* 129 */     EntityPlayer player = Ic2Player.get(world);
/* 130 */     BlockPos.MutableBlockPos tmpPos = new BlockPos.MutableBlockPos();
/*     */     
/* 132 */     for (int y = center.func_177956_o() - range; y <= center.func_177956_o() + range; y++) {
/* 133 */       for (int z = center.func_177952_p() - range; z <= center.func_177952_p() + range; z++) {
/* 134 */         for (int x = center.func_177958_n() - range; x <= center.func_177958_n() + range; x++) {
/* 135 */           List<ItemStack> drops; tmpPos.func_181079_c(x, y, z);
/*     */           
/* 137 */           IBlockState state = cache.func_180495_p((BlockPos)tmpPos);
/* 138 */           if (state.func_177230_c().isAir(state, (IBlockAccess)cache, (BlockPos)tmpPos))
/*     */             continue; 
/* 140 */           ItemStack pickStack = StackUtil.getPickStack(world, (BlockPos)tmpPos, state, player);
/*     */ 
/*     */           
/* 143 */           if (pickStack != null && OreValues.get(pickStack) > 0) {
/* 144 */             drops = Arrays.asList(new ItemStack[] { pickStack });
/*     */           } else {
/* 146 */             drops = StackUtil.getDrops((IBlockAccess)cache, (BlockPos)tmpPos, state, 0);
/* 147 */             if (drops.isEmpty() || OreValues.get(drops) <= 0)
/*     */               continue; 
/*     */           } 
/* 150 */           for (ItemStack drop : drops) {
/* 151 */             ItemComparableItemStack key = new ItemComparableItemStack(drop, true);
/*     */             
/* 153 */             Integer count = ret.get(key);
/* 154 */             if (count == null) count = Integer.valueOf(0);
/*     */             
/* 156 */             count = Integer.valueOf(count.intValue() + StackUtil.getSize(drop));
/*     */             
/* 158 */             ret.put(key, count);
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 164 */     return ret;
/*     */   }
/*     */   
/*     */   private List<Tuple.T2<ItemStack, Integer>> scanMapToSortedList(Map<ItemComparableItemStack, Integer> map) {
/* 168 */     List<Tuple.T2<ItemStack, Integer>> ret = new ArrayList<>(map.size());
/*     */     
/* 170 */     for (Map.Entry<ItemComparableItemStack, Integer> entry : map.entrySet()) {
/* 171 */       ret.add(new Tuple.T2(((ItemComparableItemStack)entry.getKey()).toStack(), entry.getValue()));
/*     */     }
/*     */     
/* 174 */     Collections.sort(ret, new Comparator<Tuple.T2<ItemStack, Integer>>()
/*     */         {
/*     */           public int compare(Tuple.T2<ItemStack, Integer> a, Tuple.T2<ItemStack, Integer> b) {
/* 177 */             return ((Integer)b.b).intValue() - ((Integer)a.b).intValue();
/*     */           }
/*     */         });
/*     */     
/* 181 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */