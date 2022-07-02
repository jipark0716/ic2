/*      */ package ic2.core.util;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import gnu.trove.TIntCollection;
/*      */ import gnu.trove.iterator.TIntIterator;
/*      */ import gnu.trove.list.array.TIntArrayList;
/*      */ import gnu.trove.procedure.TIntProcedure;
/*      */ import gnu.trove.set.TIntSet;
/*      */ import gnu.trove.set.hash.TIntHashSet;
/*      */ import ic2.api.recipe.IRecipeInput;
/*      */ import ic2.api.recipe.Recipes;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.Ic2Player;
/*      */ import ic2.core.block.personal.IPersonalBlock;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockChest;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.ISidedInventory;
/*      */ import net.minecraft.inventory.InventoryLargeChest;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityChest;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.ILockableContainer;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.items.CapabilityItemHandler;
/*      */ import net.minecraftforge.items.IItemHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class StackUtil
/*      */ {
/*      */   public static boolean isInventoryTile(TileEntity te, EnumFacing side) {
/*   71 */     return (te instanceof IInventory || (te != null && te
/*   72 */       .hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)));
/*      */   }
/*      */   public static class AdjacentInv { public final TileEntity te; public final EnumFacing dir;
/*      */     
/*      */     AdjacentInv(TileEntity te, EnumFacing dir) {
/*   77 */       this.te = te;
/*   78 */       this.dir = dir;
/*      */     }
/*      */     
/*      */     public GameProfile getAccessor() {
/*   82 */       return null;
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class PersonalAdjacentInv
/*      */     extends AdjacentInv {
/*      */     public final GameProfile accessor;
/*      */     
/*      */     PersonalAdjacentInv(TileEntity te, EnumFacing dir, GameProfile accessor) {
/*   91 */       super(te, dir);
/*      */       
/*   93 */       this.accessor = accessor;
/*      */     }
/*      */ 
/*      */     
/*      */     public GameProfile getAccessor() {
/*   98 */       return this.accessor;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static IInventory findDoubleChest(TileEntityChest chest) {
/*  105 */     World world = chest.func_145831_w();
/*  106 */     BlockPos pos = chest.func_174877_v();
/*      */     
/*  108 */     if (world == null || pos == null || !world.func_175667_e(pos)) {
/*  109 */       return null;
/*      */     }
/*      */     
/*  112 */     BlockChest.Type type = chest.func_145980_j();
/*  113 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/*  114 */       TileEntity te = world.func_175625_s(pos.func_177972_a(facing));
/*      */       
/*  116 */       if (te instanceof TileEntityChest && ((TileEntityChest)te).func_145980_j() == type) {
/*      */         TileEntityChest tileEntityChest1; TileEntityChest tileEntityChest2;
/*  118 */         if (facing == EnumFacing.WEST || facing == EnumFacing.NORTH) {
/*  119 */           tileEntityChest1 = (TileEntityChest)te;
/*  120 */           tileEntityChest2 = chest;
/*      */         } else {
/*  122 */           tileEntityChest1 = chest;
/*  123 */           tileEntityChest2 = (TileEntityChest)te;
/*      */         } 
/*      */         
/*  126 */         return (IInventory)new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileEntityChest1, (ILockableContainer)tileEntityChest2);
/*      */       } 
/*      */     } 
/*      */     
/*  130 */     return (IInventory)chest;
/*      */   }
/*      */   
/*      */   public static AdjacentInv getAdjacentInventory(TileEntity source, EnumFacing dir) {
/*  134 */     TileEntity target = source.func_145831_w().func_175625_s(source.func_174877_v().func_177972_a(dir));
/*      */     
/*  136 */     if (!isInventoryTile(target, dir)) return null;
/*      */ 
/*      */     
/*      */     GameProfile srcOwner;
/*  140 */     if (target instanceof IPersonalBlock && source instanceof IPersonalBlock && (
/*      */       
/*  142 */       srcOwner = ((IPersonalBlock)source).getOwner()) != null) {
/*  143 */       return new PersonalAdjacentInv(target, dir, srcOwner);
/*      */     }
/*      */     
/*  146 */     if (target instanceof TileEntityChest && findDoubleChest((TileEntityChest)target) == null) {
/*  147 */       return null;
/*      */     }
/*      */     
/*  150 */     return new AdjacentInv(target, dir);
/*      */   }
/*      */   
/*      */   public static List<AdjacentInv> getAdjacentInventories(TileEntity source) {
/*  154 */     List<AdjacentInv> inventories = new ArrayList<>();
/*      */     
/*  156 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  157 */       AdjacentInv inventory = getAdjacentInventory(source, dir);
/*  158 */       if (inventory != null)
/*      */       {
/*  160 */         inventories.add(inventory);
/*      */       }
/*      */     } 
/*      */     
/*  164 */     Collections.sort(inventories, new Comparator<AdjacentInv>()
/*      */         {
/*      */           public int compare(StackUtil.AdjacentInv a, StackUtil.AdjacentInv b) {
/*  167 */             if (a.te instanceof IPersonalBlock || !(b.te instanceof IPersonalBlock))
/*  168 */               return -1; 
/*  169 */             if (b.te instanceof IPersonalBlock || !(a.te instanceof IPersonalBlock)) {
/*  170 */               return 1;
/*      */             }
/*  172 */             return StackUtil.getInventorySize(b.te, b.dir.func_176734_d(), b.getAccessor()) - StackUtil.getInventorySize(a.te, a.dir.func_176734_d(), a.getAccessor());
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  177 */     return inventories;
/*      */   }
/*      */   
/*      */   public static GameProfile getOwner(TileEntity te) {
/*  181 */     if (te instanceof IPersonalBlock) {
/*  182 */       return ((IPersonalBlock)te).getOwner();
/*      */     }
/*  184 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getInventorySize(TileEntity te, EnumFacing side, GameProfile accessor) {
/*  189 */     if (te instanceof IInventory) {
/*  190 */       IInventory inv = getInventory(te, accessor);
/*  191 */       return (inv == null) ? 0 : inv.func_70302_i_();
/*  192 */     }  if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
/*  193 */       IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
/*  194 */       if (handler == null) return 0;
/*      */       
/*  196 */       return handler.getSlots();
/*      */     } 
/*  198 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static IInventory getInventory(TileEntity te, GameProfile accessor) {
/*  203 */     if (te instanceof TileEntityChest)
/*  204 */       return findDoubleChest((TileEntityChest)te); 
/*  205 */     if (te instanceof IPersonalBlock)
/*  206 */       return ((IPersonalBlock)te).getPrivilegedInventory(accessor); 
/*  207 */     if (te instanceof IInventory) {
/*  208 */       return (IInventory)te;
/*      */     }
/*  210 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int distribute(TileEntity source, ItemStack stack, boolean simulate) {
/*  215 */     ItemStack remaining = copy(stack);
/*      */     
/*  217 */     for (AdjacentInv inventory : getAdjacentInventories(source)) {
/*  218 */       int amount = putInInventory(source, inventory, remaining, simulate);
/*      */       
/*  220 */       remaining = decSize(remaining, amount);
/*  221 */       if (isEmpty(remaining))
/*      */         break; 
/*      */     } 
/*  224 */     return getSize(stack) - getSize(remaining);
/*      */   }
/*      */   
/*      */   public static int fetch(TileEntity source, ItemStack stack, boolean simulate) {
/*  228 */     ItemStack remaining = copy(stack);
/*      */     
/*  230 */     for (AdjacentInv inventory : getAdjacentInventories(source)) {
/*  231 */       ItemStack transferred = getFromInventory(source, inventory, remaining, true, simulate);
/*  232 */       if (isEmpty(transferred))
/*      */         continue; 
/*  234 */       remaining = decSize(remaining, getSize(transferred));
/*  235 */       if (isEmpty(remaining))
/*      */         break; 
/*      */     } 
/*  238 */     return getSize(stack) - getSize(remaining);
/*      */   }
/*      */   
/*      */   public static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount) {
/*  242 */     return transfer(src, dst, dir, amount, Predicates.alwaysTrue(), true);
/*      */   }
/*      */   
/*      */   public static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount, Predicate<ItemStack> checker) {
/*  246 */     return transfer(src, dst, dir, amount, checker, (checker == null || Predicates.alwaysTrue().equals(checker)));
/*      */   }
/*      */   
/*      */   private static int transfer(TileEntity src, TileEntity dst, EnumFacing dir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
/*  250 */     if (amount <= 0) return 0;
/*      */     
/*  252 */     GameProfile srcOwner = getOwner(src);
/*  253 */     GameProfile dstOwner = getOwner(dst);
/*      */     
/*  255 */     EnumFacing reverseDir = dir.func_176734_d();
/*  256 */     int[] srcSlots = getInventorySlots(src, dir, false, true, dstOwner);
/*  257 */     if (srcSlots.length == 0) return 0; 
/*  258 */     int[] dstSlots = getInventorySlots(dst, reverseDir, true, false, srcOwner);
/*  259 */     if (dstSlots.length == 0) return 0;
/*      */     
/*  261 */     if (src instanceof IInventory) {
/*  262 */       IInventory srcInv = getInventory(src, dstOwner);
/*  263 */       if (srcInv == null) return 0;
/*      */       
/*  265 */       if (dst instanceof IInventory) {
/*  266 */         IInventory dstInv = getInventory(dst, srcOwner);
/*  267 */         if (dstInv == null) return 0;
/*      */         
/*  269 */         return transfer(srcInv, srcSlots, dstInv, dstSlots, dir, reverseDir, amount, checker, skipChecker);
/*  270 */       }  if (dst.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d())) {
/*  271 */         IItemHandler dstHandler = (IItemHandler)dst.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d());
/*  272 */         if (dstHandler == null) return 0;
/*      */         
/*  274 */         return transfer(srcInv, srcSlots, dstHandler, dstSlots, reverseDir, amount, checker, skipChecker);
/*      */       } 
/*  276 */       return 0;
/*      */     } 
/*  278 */     if (src.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir)) {
/*  279 */       IItemHandler srcHandler = (IItemHandler)src.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir);
/*  280 */       if (srcHandler == null) return 0;
/*      */       
/*  282 */       if (dst instanceof IInventory) {
/*  283 */         IInventory dstInv = getInventory(dst, srcOwner);
/*  284 */         if (dstInv == null) return 0;
/*      */         
/*  286 */         return transfer(srcHandler, srcSlots, dstInv, dstSlots, reverseDir, amount, checker, skipChecker);
/*  287 */       }  if (dst.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d())) {
/*  288 */         IItemHandler dstHandler = (IItemHandler)dst.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d());
/*  289 */         if (dstHandler == null) return 0;
/*      */         
/*  291 */         return transfer(srcHandler, srcSlots, dstHandler, dstSlots, amount, checker, skipChecker);
/*      */       } 
/*  293 */       return 0;
/*      */     } 
/*      */     
/*  296 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int transfer(IInventory src, int[] srcSlots, IInventory dst, int[] dstSlots, EnumFacing dir, EnumFacing reverseDir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
/*  301 */     ISidedInventory dstSided = (dst instanceof ISidedInventory) ? (ISidedInventory)dst : null;
/*  302 */     int total = amount;
/*      */     
/*  304 */     for (int srcSlot : srcSlots) {
/*  305 */       ItemStack srcStack = src.func_70301_a(srcSlot);
/*  306 */       if (!isEmpty(srcStack) && (skipChecker || checker.apply(srcStack))) {
/*      */         
/*  308 */         int transferred = insert(srcStack, amount, dst, dstSided, reverseDir, dstSlots);
/*      */         
/*  310 */         if (transferred > 0) {
/*  311 */           amount -= transferred;
/*  312 */           src.func_70299_a(srcSlot, decSize(srcStack, transferred));
/*  313 */           if (amount <= 0)
/*      */             break; 
/*      */         } 
/*      */       } 
/*  317 */     }  amount = total - amount;
/*  318 */     assert amount >= 0;
/*      */     
/*  320 */     if (amount > 0) {
/*  321 */       src.func_70296_d();
/*  322 */       dst.func_70296_d();
/*      */     } 
/*      */     
/*  325 */     return amount;
/*      */   }
/*      */   
/*      */   private static int transfer(IItemHandler src, int[] srcSlots, IInventory dst, int[] dstSlots, EnumFacing reverseDir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
/*  329 */     ISidedInventory dstSided = (dst instanceof ISidedInventory) ? (ISidedInventory)dst : null;
/*  330 */     int total = amount;
/*      */     
/*  332 */     for (int srcSlot : srcSlots) {
/*  333 */       ItemStack srcStack = src.extractItem(srcSlot, amount, true);
/*  334 */       if (!isEmpty(srcStack) && (skipChecker || checker.apply(srcStack))) {
/*      */         
/*  336 */         int transferred = insert(srcStack, amount, dst, dstSided, reverseDir, dstSlots);
/*      */         
/*  338 */         if (transferred > 0) {
/*  339 */           amount -= transferred;
/*  340 */           src.extractItem(srcSlot, transferred, false);
/*      */           
/*  342 */           if (amount <= 0)
/*      */             break; 
/*      */         } 
/*      */       } 
/*  346 */     }  amount = total - amount;
/*  347 */     assert amount >= 0;
/*      */     
/*  349 */     if (amount > 0) {
/*  350 */       dst.func_70296_d();
/*      */     }
/*      */     
/*  353 */     return amount;
/*      */   }
/*      */   
/*      */   private static int insert(ItemStack stack, int maxAmount, IInventory dst, ISidedInventory dstSided, EnumFacing side, int[] dstSlots) {
/*  357 */     int sizeLimit = Math.min(stack.func_77976_d(), dst.func_70297_j_());
/*  358 */     int total = Math.min(maxAmount, getSize(stack));
/*  359 */     int remaining = total;
/*      */     
/*  361 */     for (int pass = 0; pass < 2; pass++) {
/*  362 */       int i = 0; while (true) { int amount; if (i < dstSlots.length)
/*  363 */         { int dstSlot = dstSlots[i];
/*  364 */           if (dstSlot < 0)
/*      */             continue; 
/*  366 */           ItemStack dstStack = dst.func_70301_a(dstSlot);
/*      */           
/*  368 */           if ((pass == 0 && (isEmpty(dstStack) || !checkItemEqualityStrict(stack, dstStack))) || (
/*  369 */             pass == 1 && !isEmpty(dstStack)) || 
/*  370 */             !dst.func_94041_b(dstSlot, stack) || (
/*  371 */             dstSided != null && !dstSided.func_180462_a(dstSlot, stack, side)))
/*      */             continue; 
/*  373 */           amount = Math.min(remaining, sizeLimit - getSize(dstStack));
/*      */           
/*  375 */           if (isEmpty(dstStack))
/*  376 */           { dst.func_70299_a(dstSlot, copyWithSize(stack, amount)); }
/*  377 */           else { if (amount <= 0)
/*  378 */             { dstSlots[i] = -1; }
/*      */             else
/*      */             
/*  381 */             { dst.func_70299_a(dstSlot, incSize(dstStack, amount));
/*      */ 
/*      */               
/*  384 */               assert amount > 0; }  continue; }  } else { break; }  assert amount <= 0;
/*      */ 
/*      */         
/*      */         i++; }
/*      */     
/*      */     } 
/*      */     
/*  391 */     return total - remaining;
/*      */   }
/*      */   
/*      */   private static int transfer(IItemHandler src, int[] srcSlots, IItemHandler dst, int[] dstSlots, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
/*  395 */     int total = amount;
/*      */     
/*  397 */     for (int srcSlot : srcSlots) {
/*  398 */       ItemStack srcStack = src.extractItem(srcSlot, amount, true);
/*  399 */       if (!isEmpty(srcStack) && (skipChecker || checker.apply(srcStack))) {
/*      */         
/*  401 */         int transferred = insert(srcStack, 2147483647, dst, dstSlots);
/*      */         
/*  403 */         if (transferred > 0) {
/*  404 */           amount -= transferred;
/*  405 */           src.extractItem(srcSlot, transferred, false);
/*  406 */           if (amount <= 0)
/*      */             break; 
/*      */         } 
/*      */       } 
/*  410 */     }  amount = total - amount;
/*  411 */     assert amount >= 0;
/*      */     
/*  413 */     return amount;
/*      */   }
/*      */   
/*      */   private static int transfer(IInventory src, int[] srcSlots, IItemHandler dst, int[] dstSlots, EnumFacing dir, int amount, Predicate<ItemStack> checker, boolean skipChecker) {
/*  417 */     int total = amount;
/*      */     
/*  419 */     for (int srcSlot : srcSlots) {
/*  420 */       ItemStack srcStack = src.func_70301_a(srcSlot);
/*  421 */       if (!isEmpty(srcStack) && (skipChecker || checker.apply(srcStack))) {
/*      */         
/*  423 */         int transferred = insert(srcStack, amount, dst, dstSlots);
/*      */         
/*  425 */         if (transferred > 0) {
/*  426 */           amount -= transferred;
/*  427 */           src.func_70299_a(srcSlot, decSize(srcStack, transferred));
/*  428 */           if (amount <= 0)
/*      */             break; 
/*      */         } 
/*      */       } 
/*  432 */     }  amount = total - amount;
/*  433 */     assert amount >= 0;
/*      */     
/*  435 */     if (amount > 0) {
/*  436 */       src.func_70296_d();
/*      */     }
/*      */     
/*  439 */     return amount;
/*      */   }
/*      */   
/*      */   private static int insert(ItemStack stack, int maxAmount, IItemHandler dst, int[] dstSlots) {
/*  443 */     int total = Math.min(maxAmount, getSize(stack));
/*  444 */     int remaining = total;
/*  445 */     assert !isEmpty(stack);
/*      */     
/*  447 */     for (int pass = 0; pass < 2; pass++) {
/*  448 */       for (int dstSlot : dstSlots) {
/*  449 */         if (dstSlot >= 0) {
/*      */           
/*  451 */           ItemStack dstStack = dst.getStackInSlot(dstSlot);
/*      */           
/*  453 */           if ((pass != 0 || (!isEmpty(dstStack) && checkItemEqualityStrict(stack, dstStack))) && (
/*  454 */             pass != 1 || isEmpty(dstStack))) {
/*      */             
/*  456 */             ItemStack leftOver = dst.insertItem(dstSlot, copyWithSize(stack, remaining), false);
/*  457 */             int transferred = remaining - getSize(leftOver);
/*      */             
/*  459 */             remaining -= transferred;
/*  460 */             if (remaining <= 0) return total; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  464 */     }  return total - remaining;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void distributeDrops(TileEntity source, List<ItemStack> stacks) {
/*  471 */     for (ListIterator<ItemStack> it = stacks.listIterator(); it.hasNext(); ) {
/*  472 */       ItemStack stack = it.next();
/*      */       
/*  474 */       int amount = distribute(source, stack, false);
/*      */       
/*  476 */       if (amount == getSize(stack)) {
/*  477 */         it.remove(); continue;
/*      */       } 
/*  479 */       it.set(decSize(stack, amount));
/*      */     } 
/*      */ 
/*      */     
/*  483 */     for (ItemStack stack : stacks) {
/*  484 */       dropAsEntity(source.func_145831_w(), source.func_174877_v(), stack);
/*      */     }
/*      */     
/*  487 */     stacks.clear();
/*      */   }
/*      */   
/*      */   private static ItemStack getFromInventory(TileEntity source, AdjacentInv inventory, ItemStack stack, boolean ignoreMaxStackSize, boolean simulate) {
/*  491 */     return getFromInventory(inventory.te, inventory.dir.func_176734_d(), stack, getSize(stack), ignoreMaxStackSize, inventory.getAccessor(), simulate);
/*      */   }
/*      */   
/*      */   public static ItemStack getFromInventory(TileEntity te, EnumFacing side, ItemStack stackDestination, int max, boolean ignoreMaxStackSize, boolean simulate) {
/*  495 */     return getFromInventory(te, side, stackDestination, max, ignoreMaxStackSize, null, simulate);
/*      */   }
/*      */   
/*      */   public static ItemStack getFromInventory(TileEntity te, EnumFacing side, ItemStack stackDestination, int max, boolean ignoreMaxStackSize, GameProfile accessor, boolean simulate) {
/*  499 */     if (!isEmpty(stackDestination) && !ignoreMaxStackSize) {
/*  500 */       max = Math.min(max, stackDestination.func_77976_d() - getSize(stackDestination));
/*      */     }
/*      */     
/*  503 */     int[] slots = getInventorySlots(te, side, false, true, accessor);
/*  504 */     if (slots.length == 0) return emptyStack;
/*      */     
/*  506 */     ItemStack ret = emptyStack;
/*      */     
/*  508 */     if (te instanceof IInventory) {
/*  509 */       IInventory inv = getInventory(te, accessor);
/*  510 */       if (inv == null) return emptyStack;
/*      */       
/*  512 */       for (int slot : slots) {
/*  513 */         if (max <= 0)
/*      */           break; 
/*  515 */         ItemStack stack = inv.func_70301_a(slot);
/*      */         
/*  517 */         if (!isEmpty(stack))
/*      */         {
/*      */ 
/*      */           
/*  521 */           if (isEmpty(stackDestination) || checkItemEqualityStrict(stack, stackDestination)) {
/*  522 */             boolean extra = isEmpty(ret);
/*      */             
/*  524 */             if (extra) {
/*  525 */               ret = copyWithSize(stack, 1);
/*      */               
/*  527 */               if (isEmpty(stackDestination)) {
/*  528 */                 if (!ignoreMaxStackSize) max = Math.min(max, ret.func_77976_d());
/*      */                 
/*  530 */                 stackDestination = ret;
/*      */               } 
/*      */             } 
/*      */             
/*  534 */             int transfer = Math.min(max, getSize(stack));
/*      */             
/*  536 */             if (!simulate) {
/*  537 */               stack = decSize(stack, transfer);
/*  538 */               inv.func_70299_a(slot, stack);
/*      */             } 
/*      */             
/*  541 */             max -= transfer;
/*  542 */             ret = incSize(ret, extra ? (transfer - 1) : transfer);
/*      */           } 
/*      */         }
/*      */       } 
/*  546 */       if (!simulate && !isEmpty(ret)) {
/*  547 */         inv.func_70296_d();
/*      */       }
/*  549 */     } else if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
/*  550 */       IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
/*  551 */       if (handler == null) return emptyStack;
/*      */       
/*  553 */       for (int slot : slots) {
/*  554 */         if (max <= 0)
/*      */           break; 
/*  556 */         if (!isEmpty(stackDestination)) {
/*  557 */           ItemStack itemStack = handler.getStackInSlot(slot);
/*  558 */           if (isEmpty(itemStack) || !checkItemEqualityStrict(itemStack, stackDestination))
/*      */             continue; 
/*      */         } 
/*  561 */         ItemStack stack = handler.extractItem(slot, max, simulate);
/*      */         
/*  563 */         if (!isEmpty(stack)) {
/*  564 */           boolean extra = isEmpty(ret);
/*  565 */           if (extra) {
/*  566 */             ret = copyWithSize(stack, 1);
/*      */             
/*  568 */             if (isEmpty(stackDestination)) {
/*  569 */               if (!ignoreMaxStackSize) max = Math.min(max, ret.func_77976_d());
/*      */               
/*  571 */               stackDestination = ret;
/*      */             } 
/*      */           } else {
/*  574 */             assert checkItemEqualityStrict(stack, ret);
/*      */           } 
/*      */           
/*  577 */           int transfer = getSize(stack);
/*  578 */           max -= transfer;
/*  579 */           ret = incSize(ret, extra ? (transfer - 1) : transfer);
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */     } 
/*  584 */     return ret;
/*      */   }
/*      */   
/*      */   private static int putInInventory(TileEntity source, AdjacentInv inventory, ItemStack stackSource, boolean simulate) {
/*  588 */     return putInInventory(inventory.te, inventory.dir.func_176734_d(), stackSource, inventory.getAccessor(), simulate);
/*      */   }
/*      */   
/*      */   public static int putInInventory(TileEntity te, EnumFacing side, ItemStack stackSource, boolean simulate) {
/*  592 */     return putInInventory(te, side, stackSource, null, simulate);
/*      */   }
/*      */   
/*      */   public static int putInInventory(TileEntity te, EnumFacing side, ItemStack stackSource, GameProfile accessor, boolean simulate) {
/*  596 */     if (isEmpty(stackSource)) return 0;
/*      */     
/*  598 */     int[] slots = getInventorySlots(te, side, true, false, accessor);
/*  599 */     if (slots.length == 0) return 0;
/*      */     
/*  601 */     if (te instanceof IInventory) {
/*  602 */       IInventory inv = getInventory(te, accessor);
/*  603 */       if (inv == null) return 0;
/*      */       
/*  605 */       int toTransfer = getSize(stackSource);
/*      */       
/*  607 */       for (int slot : slots) {
/*  608 */         if (toTransfer <= 0)
/*  609 */           break;  if (inv.func_94041_b(slot, stackSource) && (
/*  610 */           !(inv instanceof ISidedInventory) || ((ISidedInventory)inv).func_180462_a(slot, stackSource, side))) {
/*      */           
/*  612 */           ItemStack stack = inv.func_70301_a(slot);
/*      */           
/*  614 */           if (!isEmpty(stack) && checkItemEqualityStrict(stack, stackSource)) {
/*  615 */             int transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), stack.func_77976_d()) - getSize(stack));
/*      */             
/*  617 */             if (!simulate) inv.func_70299_a(slot, incSize(stack, transfer));
/*      */             
/*  619 */             toTransfer -= transfer;
/*      */           } 
/*      */         } 
/*      */       } 
/*  623 */       for (int slot : slots) {
/*  624 */         if (toTransfer <= 0)
/*  625 */           break;  if (inv.func_94041_b(slot, stackSource) && (
/*  626 */           !(inv instanceof ISidedInventory) || ((ISidedInventory)inv).func_180462_a(slot, stackSource, side))) {
/*      */           
/*  628 */           ItemStack stack = inv.func_70301_a(slot);
/*      */           
/*  630 */           if (isEmpty(stack)) {
/*  631 */             int transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), stackSource.func_77976_d()));
/*      */             
/*  633 */             if (!simulate) {
/*  634 */               ItemStack dest = copyWithSize(stackSource, transfer);
/*  635 */               inv.func_70299_a(slot, dest);
/*      */             } 
/*      */             
/*  638 */             toTransfer -= transfer;
/*      */           } 
/*      */         } 
/*      */       } 
/*  642 */       if (!simulate && toTransfer != getSize(stackSource)) {
/*  643 */         inv.func_70296_d();
/*      */       }
/*      */       
/*  646 */       return getSize(stackSource) - toTransfer;
/*  647 */     }  if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
/*  648 */       IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
/*  649 */       if (handler == null) return 0;
/*      */       
/*  651 */       ItemStack src = stackSource.func_77946_l();
/*      */       
/*  653 */       for (int slot : slots) {
/*  654 */         if (isEmpty(src))
/*      */           break; 
/*  656 */         ItemStack stack = handler.getStackInSlot(slot);
/*      */         
/*  658 */         if (!isEmpty(stack)) {
/*  659 */           ItemStack remaining = handler.insertItem(slot, src.func_77946_l(), simulate);
/*      */           
/*  661 */           if (isEmpty(remaining)) {
/*  662 */             src = emptyStack;
/*  663 */           } else if (getSize(remaining) < getSize(src)) {
/*  664 */             src = setSize(src, getSize(remaining));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  669 */       for (int slot : slots) {
/*  670 */         if (isEmpty(src))
/*      */           break; 
/*  672 */         ItemStack stack = handler.getStackInSlot(slot);
/*      */         
/*  674 */         if (isEmpty(stack)) {
/*  675 */           ItemStack remaining = handler.insertItem(slot, src.func_77946_l(), simulate);
/*      */           
/*  677 */           if (isEmpty(remaining)) {
/*  678 */             src = emptyStack;
/*  679 */           } else if (getSize(remaining) < getSize(src)) {
/*  680 */             src = setSize(src, getSize(remaining));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  685 */       return getSize(stackSource) - getSize(src);
/*      */     } 
/*  687 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getInventorySlots(TileEntity te, EnumFacing side, boolean checkInsert, boolean checkExtract, GameProfile accessor) {
/*  692 */     if (te instanceof IInventory) {
/*  693 */       ISidedInventory sidedInv; int[] ret; IInventory inv = getInventory(te, accessor);
/*      */       
/*  695 */       if (inv == null || inv
/*  696 */         .func_70297_j_() <= 0) {
/*  697 */         return emptySlotArray;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  703 */       if (inv instanceof ISidedInventory) {
/*  704 */         sidedInv = (ISidedInventory)inv;
/*  705 */         ret = sidedInv.func_180463_a(side);
/*  706 */         if (ret.length == 0) return emptySlotArray;
/*      */         
/*  708 */         ret = Arrays.copyOf(ret, ret.length);
/*      */       } else {
/*  710 */         int size = inv.func_70302_i_();
/*  711 */         if (size <= 0) return emptySlotArray;
/*      */         
/*  713 */         sidedInv = null;
/*  714 */         ret = new int[size];
/*      */         
/*  716 */         for (int i = 0; i < ret.length; i++) {
/*  717 */           ret[i] = i;
/*      */         }
/*      */       } 
/*      */       
/*  721 */       if (checkInsert || checkExtract) {
/*  722 */         int writeIdx = 0;
/*      */         
/*  724 */         for (int readIdx = 0; readIdx < ret.length; readIdx++) {
/*  725 */           int slot = ret[readIdx];
/*  726 */           ItemStack stack = inv.func_70301_a(slot);
/*      */           
/*  728 */           if ((!checkExtract || (!isEmpty(stack) && (sidedInv == null || sidedInv
/*  729 */             .func_180461_b(slot, stack, side)))) && (!checkInsert || 
/*  730 */             isEmpty(stack) || (getSize(stack) < stack.func_77976_d() && getSize(stack) < inv.func_70297_j_() && (sidedInv == null || sidedInv
/*  731 */             .func_180462_a(slot, stack, side))))) {
/*  732 */             ret[writeIdx] = slot;
/*  733 */             writeIdx++;
/*      */           } 
/*      */         } 
/*      */         
/*  737 */         if (writeIdx != ret.length) {
/*  738 */           ret = Arrays.copyOf(ret, writeIdx);
/*      */         }
/*      */       } 
/*      */       
/*  742 */       return ret;
/*  743 */     }  if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
/*  744 */       IItemHandler handler = (IItemHandler)te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
/*  745 */       if (handler == null) return emptySlotArray;
/*      */       
/*  747 */       int size = handler.getSlots();
/*  748 */       if (size <= 0) return emptySlotArray;
/*      */       
/*  750 */       int[] ret = new int[size];
/*      */       
/*  752 */       for (int i = 0; i < ret.length; i++) {
/*  753 */         ret[i] = i;
/*      */       }
/*      */       
/*  756 */       if (checkInsert || checkExtract) {
/*  757 */         int writeIdx = 0;
/*      */         
/*  759 */         for (int readIdx = 0; readIdx < ret.length; readIdx++) {
/*  760 */           int slot = ret[readIdx];
/*  761 */           ItemStack stack = handler.getStackInSlot(slot);
/*      */           
/*  763 */           if ((!checkExtract || (!isEmpty(stack) && 
/*  764 */             !isEmpty(handler.extractItem(slot, 2147483647, true)))) && (!checkInsert || 
/*  765 */             checkInsert(handler, slot, stack))) {
/*  766 */             ret[writeIdx] = slot;
/*  767 */             writeIdx++;
/*      */           } 
/*      */         } 
/*      */         
/*  771 */         if (writeIdx != ret.length) {
/*  772 */           ret = Arrays.copyOf(ret, writeIdx);
/*      */         }
/*      */       } 
/*      */       
/*  776 */       return ret;
/*      */     } 
/*  778 */     return emptySlotArray;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean checkInsert(IItemHandler handler, int slot, ItemStack stack) {
/*  783 */     if (isEmpty(stack) || getSize(stack) >= stack.func_77976_d()) return true;
/*      */     
/*  785 */     int testSize = Integer.MAX_VALUE;
/*  786 */     ItemStack result = handler.insertItem(slot, copyWithSize(stack, 2147483647), true);
/*      */     
/*  788 */     return (isEmpty(result) || getSize(result) < Integer.MAX_VALUE);
/*      */   }
/*      */   
/*      */   public static boolean consumeFromPlayerInventory(EntityPlayer player, Predicate<ItemStack> request, int amount, boolean simulate) {
/*  792 */     NonNullList<ItemStack> contents = player.field_71071_by.field_70462_a;
/*      */ 
/*      */     
/*  795 */     for (int pass = 0; pass < 2; pass++) {
/*  796 */       int amountNeeded = amount;
/*      */       
/*  798 */       for (int i = 0; i < contents.size(); i++) {
/*  799 */         ItemStack stack = (ItemStack)contents.get(i);
/*  800 */         if (request.apply(stack)) {
/*  801 */           if (player.field_71075_bZ.field_75098_d) return true;
/*      */           
/*  803 */           int cAmount = Math.min(getSize(stack), amountNeeded);
/*  804 */           amountNeeded -= cAmount;
/*      */           
/*  806 */           if (pass == 1) {
/*  807 */             contents.set(i, decSize(stack, cAmount));
/*      */           }
/*      */           
/*  810 */           if (amountNeeded <= 0)
/*      */             break; 
/*      */         } 
/*  813 */       }  if (amountNeeded > 0) {
/*  814 */         if (pass == 1) IC2.log.warn(LogCategory.General, "Inconsistent inventory transaction for player %s, request %s: %d missing", new Object[] { player, request, Integer.valueOf(amountNeeded) });
/*      */         
/*  816 */         return false;
/*  817 */       }  if (simulate) {
/*  818 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  822 */     return true;
/*      */   }
/*      */   
/*      */   public static Predicate<ItemStack> sameStack(final ItemStack stack) {
/*  826 */     if (isEmpty(stack)) throw new IllegalArgumentException("empty stack");
/*      */     
/*  828 */     return new Predicate<ItemStack>()
/*      */       {
/*      */         public boolean apply(ItemStack input) {
/*  831 */           return StackUtil.checkItemEquality(input, stack);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  836 */           return "stack==" + stack;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static Predicate<ItemStack> sameItem(final Item item) {
/*  842 */     if (item == null) throw new NullPointerException("null item");
/*      */     
/*  844 */     return new Predicate<ItemStack>()
/*      */       {
/*      */         public boolean apply(ItemStack input) {
/*  847 */           return (input.func_77973_b() == item);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  852 */           return "item==" + item;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static Predicate<ItemStack> sameItem(Block block) {
/*  858 */     if (block == null) throw new NullPointerException("null block");
/*      */     
/*  860 */     Item item = Item.func_150898_a(block);
/*  861 */     if (item == null || (item == Items.field_190931_a && block != Blocks.field_150350_a)) throw new IllegalArgumentException("block " + block + " doesn't have an associated item");
/*      */     
/*  863 */     return sameItem(item);
/*      */   }
/*      */   
/*      */   public static Predicate<ItemStack> oreDict(String name) {
/*  867 */     return recipeInput(Recipes.inputFactory.forOreDict(name));
/*      */   }
/*      */   
/*      */   public static Predicate<ItemStack> recipeInput(final IRecipeInput item) {
/*  871 */     return new Predicate<ItemStack>()
/*      */       {
/*      */         public boolean apply(ItemStack input) {
/*  874 */           return item.matches(input);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  879 */           return item.toString();
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*  884 */   public static final Predicate<ItemStack> anyStack = Predicates.alwaysTrue();
/*      */   
/*      */   public static boolean consume(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
/*  887 */     return (consume0(player, hand, request, amount, false) != emptyStack);
/*      */   }
/*      */   
/*      */   public static ItemStack consumeAndGet(EntityPlayer player, Predicate<ItemStack> request, int amount) {
/*  891 */     return consumeAndGet(player, EnumHand.MAIN_HAND, request, amount);
/*      */   }
/*      */   
/*      */   public static ItemStack consumeAndGet(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
/*  895 */     return consume0(player, hand, request, amount, true);
/*      */   }
/*      */   
/*      */   public static void consumeOrError(EntityPlayer player, EnumHand hand, int amount) {
/*  899 */     consumeOrError(player, hand, anyStack, amount);
/*      */   }
/*      */   
/*      */   public static void consumeOrError(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
/*  903 */     if (!consume(player, hand, request, amount)) throw new IllegalStateException("consume failed"); 
/*      */   }
/*      */   private static ItemStack consume0(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount, boolean copyOutput) {
/*      */     ItemStack ret;
/*  907 */     if (amount <= 0) throw new IllegalArgumentException("negative/zero amount");
/*      */     
/*  909 */     ItemStack stack = get(player, hand);
/*  910 */     if (isEmpty(stack)) return emptyStack; 
/*  911 */     if (!request.apply(stack)) return emptyStack; 
/*  912 */     if (player.field_71075_bZ.field_75098_d) return copyOutput ? copyWithSize(stack, amount) : stack; 
/*  913 */     if (getSize(stack) < amount) return emptyStack;
/*      */ 
/*      */ 
/*      */     
/*  917 */     if (getSize(stack) == amount) {
/*  918 */       ret = stack;
/*  919 */       clear(player, hand);
/*      */     } else {
/*  921 */       ret = copyOutput ? copyWithSize(stack, amount) : stack;
/*  922 */       set(player, hand, decSize(stack, amount));
/*      */     } 
/*      */     
/*  925 */     return ret;
/*      */   }
/*      */   
/*      */   public static boolean damage(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
/*  929 */     return (damage0(player, hand, request, amount, false) != emptyStack);
/*      */   }
/*      */   
/*      */   public static void damageOrError(EntityPlayer player, EnumHand hand, int amount) {
/*  933 */     damageOrError(player, hand, anyStack, amount);
/*      */   }
/*      */   
/*      */   public static void damageOrError(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount) {
/*  937 */     if (!damage(player, hand, request, amount)) throw new IllegalStateException("damage failed"); 
/*      */   }
/*      */   private static ItemStack damage0(EntityPlayer player, EnumHand hand, Predicate<ItemStack> request, int amount, boolean copyOutput) {
/*      */     ItemStack ret;
/*  941 */     if (amount <= 0) throw new IllegalArgumentException("negative/zero amount");
/*      */     
/*  943 */     ItemStack stack = get(player, hand);
/*  944 */     if (isEmpty(stack)) return emptyStack;
/*      */     
/*  946 */     int maxDamage = stack.func_77958_k();
/*  947 */     if (maxDamage <= 0) return emptyStack; 
/*  948 */     if (!request.apply(stack)) return emptyStack; 
/*  949 */     if (player.field_71075_bZ.field_75098_d || !stack.func_77984_f()) return copyOutput ? copy(stack) : stack;
/*      */     
/*  951 */     stack.func_77972_a(amount, (EntityLivingBase)player);
/*      */ 
/*      */     
/*  954 */     if (isEmpty(stack)) {
/*  955 */       ret = stack;
/*  956 */       clear(player, hand);
/*      */     } else {
/*  958 */       ret = copyOutput ? copy(stack) : stack;
/*  959 */       set(player, hand, stack);
/*      */     } 
/*      */     
/*  962 */     return ret;
/*      */   }
/*      */   
/*      */   public static ItemStack get(EntityPlayer player, EnumHand hand) {
/*  966 */     return player.func_184586_b(hand);
/*      */   }
/*      */   
/*      */   public static void set(EntityPlayer player, EnumHand hand, ItemStack stack) {
/*  970 */     if (isEmpty(stack)) stack = emptyStack;
/*      */     
/*  972 */     InventoryPlayer inv = player.field_71071_by;
/*      */     
/*  974 */     if (hand == EnumHand.MAIN_HAND) {
/*  975 */       inv.field_70462_a.set(inv.field_70461_c, stack);
/*  976 */     } else if (hand == EnumHand.OFF_HAND) {
/*  977 */       inv.field_184439_c.set(0, stack);
/*      */     } else {
/*  979 */       throw new IllegalArgumentException("invalid hand: " + hand);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clear(EntityPlayer player, EnumHand hand) {
/*  984 */     set(player, hand, emptyStack);
/*      */   }
/*      */   
/*      */   public static void clearEmpty(EntityPlayer player, EnumHand hand) {
/*  988 */     if (isEmpty(player, hand)) {
/*  989 */       clear(player, hand);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void dropAsEntity(World world, BlockPos pos, ItemStack stack) {
/*  994 */     if (isEmpty(stack))
/*      */       return; 
/*  996 */     double f = 0.7D;
/*  997 */     double dx = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/*  998 */     double dy = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/*  999 */     double dz = world.field_73012_v.nextFloat() * f + (1.0D - f) * 0.5D;
/*      */     
/* 1001 */     EntityItem entityItem = new EntityItem(world, pos.func_177958_n() + dx, pos.func_177956_o() + dy, pos.func_177952_p() + dz, stack.func_77946_l());
/* 1002 */     entityItem.func_174869_p();
/* 1003 */     world.func_72838_d((Entity)entityItem);
/*      */   }
/*      */   
/*      */   public static ItemStack copy(ItemStack stack) {
/* 1007 */     return stack.func_77946_l();
/*      */   }
/*      */   
/*      */   public static ItemStack copyWithSize(ItemStack stack, int newSize) {
/* 1011 */     if (isEmpty(stack)) throw new IllegalArgumentException("empty stack: " + toStringSafe(stack));
/*      */     
/* 1013 */     return setSize(copy(stack), newSize);
/*      */   }
/*      */   
/*      */   public static ItemStack copyShrunk(ItemStack stack, int amount) {
/* 1017 */     if (isEmpty(stack)) throw new IllegalArgumentException("empty stack: " + toStringSafe(stack));
/*      */     
/* 1019 */     return setSize(copy(stack), getSize(stack) - amount);
/*      */   }
/*      */   
/*      */   public static ItemStack copyWithWildCard(ItemStack stack) {
/* 1023 */     ItemStack ret = copy(stack);
/* 1024 */     setRawMeta(ret, 32767);
/*      */     
/* 1026 */     return ret;
/*      */   }
/*      */   
/*      */   public static Collection<ItemStack> copy(Collection<ItemStack> c) {
/* 1030 */     List<ItemStack> ret = new ArrayList<>(c.size());
/*      */     
/* 1032 */     for (ItemStack stack : c) {
/* 1033 */       ret.add(copy(stack));
/*      */     }
/*      */     
/* 1036 */     return ret;
/*      */   }
/*      */   
/*      */   public static NBTTagCompound getOrCreateNbtData(ItemStack stack) {
/* 1040 */     NBTTagCompound ret = stack.func_77978_p();
/*      */     
/* 1042 */     if (ret == null) {
/* 1043 */       ret = new NBTTagCompound();
/*      */       
/* 1045 */       stack.func_77982_d(ret);
/*      */     } 
/*      */     
/* 1048 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkItemEquality(ItemStack a, ItemStack b) {
/* 1071 */     return ((isEmpty(a) && isEmpty(b)) || (
/* 1072 */       !isEmpty(a) && !isEmpty(b) && a
/* 1073 */       .func_77973_b() == b.func_77973_b() && (
/* 1074 */       !a.func_77981_g() || a.func_77960_j() == b.func_77960_j()) && 
/* 1075 */       checkNbtEquality(a, b)));
/*      */   }
/*      */   
/*      */   public static boolean checkItemEquality(ItemStack a, Item b) {
/* 1079 */     return ((isEmpty(a) && b == null) || (
/* 1080 */       !isEmpty(a) && b != null && a
/* 1081 */       .func_77973_b() == b));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean checkItemEqualityStrict(ItemStack a, ItemStack b) {
/* 1094 */     return ((isEmpty(a) && isEmpty(b)) || (
/* 1095 */       !isEmpty(a) && !isEmpty(b) && a
/* 1096 */       .func_77969_a(b) && 
/* 1097 */       checkNbtEqualityStrict(a, b)));
/*      */   }
/*      */   
/*      */   private static boolean checkNbtEquality(ItemStack a, ItemStack b) {
/* 1101 */     return checkNbtEquality(a.func_77978_p(), b.func_77978_p());
/*      */   }
/*      */   
/*      */   public static boolean checkNbtEquality(NBTTagCompound a, NBTTagCompound b) {
/* 1105 */     if (a == b) return true;
/*      */     
/* 1107 */     Set<String> keysA = (a != null) ? a.func_150296_c() : Collections.<String>emptySet();
/* 1108 */     Set<String> keysB = (b != null) ? b.func_150296_c() : Collections.<String>emptySet();
/* 1109 */     Set<String> toCheck = new HashSet<>(Math.max(keysA.size(), keysB.size()));
/*      */     
/* 1111 */     for (String key : keysA) {
/* 1112 */       if (ignoredNbtKeys.contains(key))
/*      */         continue; 
/* 1114 */       if (!keysB.contains(key)) return false; 
/* 1115 */       toCheck.add(key);
/*      */     } 
/*      */     
/* 1118 */     for (String key : keysB) {
/* 1119 */       if (ignoredNbtKeys.contains(key))
/*      */         continue; 
/* 1121 */       if (!keysA.contains(key)) return false; 
/* 1122 */       toCheck.add(key);
/*      */     } 
/*      */     
/* 1125 */     for (String key : toCheck) {
/* 1126 */       if (!a.func_74781_a(key).equals(b.func_74781_a(key))) return false;
/*      */     
/*      */     } 
/* 1129 */     return true;
/*      */   }
/*      */   
/* 1132 */   static final Set<String> ignoredNbtKeys = new HashSet<>(Arrays.asList(new String[] { "damage", "charge", "energy", "advDmg" }));
/*      */   
/*      */   public static boolean checkNbtEqualityStrict(ItemStack a, ItemStack b) {
/* 1135 */     NBTTagCompound nbtA = a.func_77978_p();
/* 1136 */     NBTTagCompound nbtB = b.func_77978_p();
/*      */     
/* 1138 */     if (nbtA == nbtB) return true;
/*      */     
/* 1140 */     return (nbtA != null && nbtB != null && nbtA.equals(nbtB));
/*      */   }
/*      */   
/*      */   public static ItemStack getPickStack(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 1144 */     RayTraceResult target = new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d((Vec3i)pos), EnumFacing.DOWN, pos);
/* 1145 */     ItemStack ret = state.func_177230_c().getPickBlock(state, target, world, pos, player);
/*      */     
/* 1147 */     if (isEmpty(ret)) return emptyStack;
/*      */     
/* 1149 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/* 1154 */     return getDrops(world, pos, state, state.func_177230_c(), fortune);
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, Block block, int fortune) {
/* 1159 */     NonNullList<ItemStack> drops = NonNullList.func_191196_a();
/*      */     
/* 1161 */     assert world.func_180495_p(pos).func_177230_c() == block;
/* 1162 */     block.getDrops(drops, world, pos, state, fortune);
/*      */     
/* 1164 */     return (List<ItemStack>)drops;
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, EntityPlayer player, int fortune, boolean silkTouch) {
/* 1169 */     Block block = state.func_177230_c();
/* 1170 */     if (block.isAir(state, world, pos)) return Collections.emptyList();
/*      */     
/* 1172 */     World rawWorld = null;
/*      */     
/* 1174 */     if (silkTouch) {
/* 1175 */       rawWorld = Util.getWorld(world);
/* 1176 */       if (rawWorld == null) throw new IllegalArgumentException("invalid world for silk touch: " + world); 
/* 1177 */       if (player == null) player = Ic2Player.get(rawWorld);
/*      */     
/*      */     } 
/*      */     
/*      */     ItemStack drop;
/* 1182 */     if (silkTouch && block
/* 1183 */       .canSilkHarvest(rawWorld, pos, state, player) && 
/* 1184 */       !isEmpty(drop = getPickStack(rawWorld, pos, state, player))) {
/* 1185 */       return Collections.singletonList(drop);
/*      */     }
/* 1187 */     return getDrops(world, pos, state, block, fortune);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean placeBlock(ItemStack stack, World world, BlockPos pos) {
/* 1192 */     if (isEmpty(stack)) return false;
/*      */     
/* 1194 */     Item item = stack.func_77973_b();
/*      */     
/* 1196 */     if (item instanceof net.minecraft.item.ItemBlock || item instanceof net.minecraft.item.ItemBlockSpecial) {
/*      */       
/* 1198 */       int oldSize = getSize(stack);
/* 1199 */       EntityPlayer player = Ic2Player.get(world);
/* 1200 */       EnumHand hand = EnumHand.MAIN_HAND;
/* 1201 */       ItemStack prev = player.func_184586_b(hand);
/* 1202 */       player.func_184611_a(hand, stack);
/* 1203 */       EnumActionResult result = item.func_180614_a(player, world, pos, hand, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/* 1204 */       player.func_184611_a(hand, prev);
/* 1205 */       stack = setSize(stack, oldSize);
/*      */       
/* 1207 */       return (result == EnumActionResult.SUCCESS);
/*      */     } 
/*      */     
/* 1210 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEmpty(ItemStack stack) {
/* 1214 */     return (stack == emptyStack || stack == null || stack.func_77973_b() == null || stack.func_190916_E() <= 0);
/*      */   }
/*      */   
/*      */   public static boolean isEmpty(EntityPlayer player, EnumHand hand) {
/* 1218 */     return isEmpty(player.func_184586_b(hand));
/*      */   }
/*      */   
/*      */   public static int getSize(ItemStack stack) {
/* 1222 */     if (isEmpty(stack)) {
/* 1223 */       return 0;
/*      */     }
/* 1225 */     return stack.func_190916_E();
/*      */   }
/*      */ 
/*      */   
/*      */   public static ItemStack setSize(ItemStack stack, int size) {
/* 1230 */     if (size <= 0) return emptyStack;
/*      */     
/* 1232 */     stack.func_190920_e(size);
/*      */     
/* 1234 */     return stack;
/*      */   }
/*      */   
/*      */   public static ItemStack incSize(ItemStack stack) {
/* 1238 */     return incSize(stack, 1);
/*      */   }
/*      */   
/*      */   public static ItemStack incSize(ItemStack stack, int amount) {
/* 1242 */     return setSize(stack, getSize(stack) + amount);
/*      */   }
/*      */   
/*      */   public static ItemStack decSize(ItemStack stack) {
/* 1246 */     return decSize(stack, 1);
/*      */   }
/*      */   
/*      */   public static ItemStack decSize(ItemStack stack, int amount) {
/* 1250 */     return incSize(stack, -amount);
/*      */   }
/*      */   
/*      */   public static boolean check2(Iterable<List<ItemStack>> list) {
/* 1254 */     for (List<ItemStack> list2 : list) {
/* 1255 */       if (!check(list2)) return false;
/*      */     
/*      */     } 
/* 1258 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean check(ItemStack[] array) {
/* 1262 */     return check(Arrays.asList(array));
/*      */   }
/*      */   
/*      */   public static boolean check(Iterable<ItemStack> list) {
/* 1266 */     for (ItemStack stack : list) {
/* 1267 */       if (!check(stack)) return false;
/*      */     
/*      */     } 
/* 1270 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean check(ItemStack stack) {
/* 1274 */     return (stack.func_77973_b() != null);
/*      */   }
/*      */   
/*      */   public static String toStringSafe2(Iterable<List<ItemStack>> list) {
/* 1278 */     StringBuilder ret = new StringBuilder("[");
/*      */     
/* 1280 */     for (List<ItemStack> list2 : list) {
/* 1281 */       if (ret.length() > 1) ret.append(", ");
/*      */       
/* 1283 */       ret.append(toStringSafe(list2));
/*      */     } 
/*      */     
/* 1286 */     return ret.append(']').toString();
/*      */   }
/*      */   
/*      */   public static String toStringSafe(ItemStack[] array) {
/* 1290 */     return toStringSafe(Arrays.asList(array));
/*      */   }
/*      */   
/*      */   public static String toStringSafe(Iterable<ItemStack> list) {
/* 1294 */     StringBuilder ret = new StringBuilder("[");
/*      */     
/* 1296 */     for (ItemStack stack : list) {
/* 1297 */       if (ret.length() > 1) ret.append(", ");
/*      */       
/* 1299 */       ret.append(toStringSafe(stack));
/*      */     } 
/*      */     
/* 1302 */     return ret.append(']').toString();
/*      */   }
/*      */   
/*      */   public static String toStringSafe(ItemStack stack) {
/* 1306 */     if (stack == null)
/* 1307 */       return "(null)"; 
/* 1308 */     if (stack.func_77973_b() == null) {
/* 1309 */       return getSize(stack) + "x(null)@(unknown)";
/*      */     }
/* 1311 */     return stack.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean storeInventoryItem(ItemStack stack, EntityPlayer player, boolean simulate) {
/* 1316 */     if (simulate) {
/* 1317 */       int sizeLeft = getSize(stack);
/* 1318 */       int maxStackSize = Math.min(player.field_71071_by.func_70297_j_(), stack.func_77976_d());
/*      */       
/* 1320 */       for (int i = 0; i < player.field_71071_by.field_70462_a.size() && sizeLeft > 0; i++) {
/* 1321 */         ItemStack invStack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
/*      */         
/* 1323 */         if (isEmpty(invStack)) {
/* 1324 */           sizeLeft -= maxStackSize;
/* 1325 */         } else if (checkItemEqualityStrict(stack, invStack) && getSize(invStack) < maxStackSize) {
/* 1326 */           sizeLeft -= maxStackSize - getSize(invStack);
/*      */         } 
/*      */       } 
/*      */       
/* 1330 */       return (sizeLeft <= 0);
/*      */     } 
/* 1332 */     return player.field_71071_by.func_70441_a(stack);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getRawMeta(ItemStack stack) {
/* 1337 */     return Items.field_151100_aR.getDamage(stack);
/*      */   }
/*      */   
/*      */   public static void setRawMeta(ItemStack stack, int meta) {
/* 1341 */     if (meta < 0) throw new IllegalArgumentException("negative meta");
/*      */     
/* 1343 */     Items.field_151100_aR.setDamage(stack, meta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TIntSet getSlotsFromInv(IInventory inv) {
/* 1354 */     TIntHashSet tIntHashSet = new TIntHashSet();
/* 1355 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/* 1356 */       tIntHashSet.add(i);
/*      */     }
/* 1358 */     return (TIntSet)tIntHashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory craftMatrix) {
/* 1370 */     return balanceStacks(craftMatrix, Collections.emptySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory craftMatrix, ItemStack sourceItemStack) {
/* 1383 */     return balanceStacks(craftMatrix, Collections.singleton(sourceItemStack));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(final IInventory inv, Collection<ItemStack> additionalItems) {
/* 1396 */     return balanceStacks(inv, new Predicate<Tuple.T2<ItemStack, Integer>>()
/*      */         {
/*      */           public boolean apply(Tuple.T2<ItemStack, Integer> input)
/*      */           {
/* 1400 */             return !StackUtil.isEmpty(inv.func_70301_a(((Integer)input.b).intValue()));
/*      */           }
/*      */         }, 
/* 1403 */         getSlotsFromInv(inv), additionalItems);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory inv, Predicate<Tuple.T2<ItemStack, Integer>> canInsert) {
/* 1417 */     return balanceStacks(inv, canInsert, getSlotsFromInv(inv), Collections.emptySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Tuple.T2<List<ItemStack>, ? extends TIntCollection> balanceStacks(IInventory inv, Predicate<Tuple.T2<ItemStack, Integer>> canInsert, TIntSet originalAvailableSlots, Collection<ItemStack> additionalStacksOriginal) {
/* 1432 */     List<ItemStack> additionalStacks = new LinkedList<>(additionalStacksOriginal);
/* 1433 */     TIntHashSet tIntHashSet = new TIntHashSet((TIntCollection)originalAvailableSlots);
/* 1434 */     List<ItemStack> leftOvers = new ArrayList<>();
/*      */     
/* 1436 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/* 1437 */       if (tIntHashSet.contains(i)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1442 */         ItemStack stack = inv.func_70301_a(i);
/* 1443 */         if (!isEmpty(stack)) {
/*      */           
/* 1445 */           int amount = 0;
/*      */           
/* 1447 */           for (ListIterator<ItemStack> iter = additionalStacks.listIterator(); iter.hasNext(); ) {
/* 1448 */             ItemStack currentStack = iter.next();
/*      */             
/* 1450 */             if (checkItemEqualityStrict(currentStack, stack)) {
/* 1451 */               iter.remove();
/* 1452 */               amount += getSize(currentStack);
/*      */             } 
/*      */           } 
/*      */           
/* 1456 */           amount = distributeStackToSlots(inv, stack, (TIntSet)tIntHashSet, canInsert, amount);
/*      */ 
/*      */           
/* 1459 */           while (amount > 0) {
/* 1460 */             int size = Math.min(stack.func_77976_d(), amount);
/* 1461 */             amount -= size;
/* 1462 */             leftOvers.add(copyWithSize(stack, size));
/*      */           } 
/*      */         } 
/*      */       } 
/* 1466 */     }  for (ItemStack stack : additionalStacks) {
/* 1467 */       int amount = distributeStackToSlots(inv, stack, (TIntSet)tIntHashSet, canInsert, getSize(stack));
/* 1468 */       if (amount > 0) {
/* 1469 */         leftOvers.add(copyWithSize(stack, amount));
/*      */       }
/*      */     } 
/*      */     
/* 1473 */     originalAvailableSlots.removeAll((TIntCollection)tIntHashSet);
/* 1474 */     return (Tuple.T2)new Tuple.T2<>(leftOvers, originalAvailableSlots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int distributeStackToSlots(final IInventory inv, ItemStack stack, TIntSet availableSlots, Predicate<Tuple.T2<ItemStack, Integer>> canInsert, int amount) {
/* 1489 */     TIntArrayList tIntArrayList = new TIntArrayList();
/*      */     
/* 1491 */     for (TIntIterator iter = availableSlots.iterator(); iter.hasNext(); ) {
/* 1492 */       int currentSlot = iter.next();
/* 1493 */       ItemStack currentStack = inv.func_70301_a(currentSlot);
/*      */       
/* 1495 */       if ((checkItemEqualityStrict(stack, currentStack) || isEmpty(currentStack)) && canInsert.apply(new Tuple.T2<>(stack, Integer.valueOf(currentSlot)))) {
/* 1496 */         amount += getSize(currentStack);
/* 1497 */         tIntArrayList.add(currentSlot);
/* 1498 */         iter.remove();
/*      */       } 
/*      */     } 
/* 1501 */     tIntArrayList.sort();
/*      */     
/* 1503 */     int maxStackSize = Math.min(stack.func_77976_d(), inv.func_70297_j_());
/*      */     
/* 1505 */     int slotsLeft = tIntArrayList.size();
/* 1506 */     for (TIntIterator tIntIterator1 = tIntArrayList.iterator(); tIntIterator1.hasNext() && amount > 0; slotsLeft--, tIntIterator1.remove()) {
/* 1507 */       int currentSlot = tIntIterator1.next();
/*      */       
/* 1509 */       int itemsToPut = amount / slotsLeft;
/*      */       
/* 1511 */       if (amount % slotsLeft > 0) {
/* 1512 */         itemsToPut++;
/*      */       }
/*      */ 
/*      */       
/* 1516 */       itemsToPut = Math.min(itemsToPut, maxStackSize);
/*      */       
/* 1518 */       inv.func_70299_a(currentSlot, copyWithSize(stack, itemsToPut));
/*      */       
/* 1520 */       amount -= itemsToPut;
/*      */     } 
/*      */     
/* 1523 */     if (!tIntArrayList.isEmpty()) {
/* 1524 */       assert amount <= 0;
/*      */       
/* 1526 */       tIntArrayList.forEach(new TIntProcedure()
/*      */           {
/*      */             public boolean execute(int currentSlot) {
/* 1529 */               inv.func_70299_a(currentSlot, StackUtil.emptyStack);
/*      */               
/* 1531 */               return true;
/*      */             }
/*      */           });
/*      */     } 
/*      */     
/* 1536 */     assert amount <= 0 || slotsLeft == 0;
/* 1537 */     return amount;
/*      */   }
/*      */   
/*      */   public static ItemStack setImmutableSize(ItemStack stack, int size) {
/* 1541 */     if (getSize(stack) != size) {
/* 1542 */       stack = copyWithSize(stack, size);
/*      */     }
/*      */     
/* 1545 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean matchesNBT(NBTTagCompound subject, NBTTagCompound target) {
/* 1555 */     if (subject == null) return (target == null || target.func_82582_d()); 
/* 1556 */     if (target == null) return true; 
/* 1557 */     for (String key : target.func_150296_c()) {
/* 1558 */       NBTBase targetNBT = target.func_74781_a(key);
/* 1559 */       if (!subject.func_74764_b(key) || targetNBT.func_74732_a() != subject.func_150299_b(key)) return false; 
/* 1560 */       NBTBase subjectNBT = subject.func_74781_a(key);
/* 1561 */       if (!targetNBT.equals(subjectNBT)) return false; 
/*      */     } 
/* 1563 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ItemStack wrapEmpty(ItemStack stack) {
/* 1569 */     return (stack == null) ? emptyStack : stack;
/*      */   }
/*      */   
/* 1572 */   public static final ItemStack emptyStack = ItemStack.field_190927_a;
/* 1573 */   private static final int[] emptySlotArray = new int[0];
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\StackUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */