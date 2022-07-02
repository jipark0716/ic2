/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.DispenseFluidContainer;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidUtil;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemFluidCell
/*     */   extends ItemIC2FluidContainer
/*     */ {
/*     */   public ItemFluidCell() {
/*  30 */     super(ItemName.fluid_cell, 1000);
/*     */     
/*  32 */     BlockDispenser.field_149943_a.func_82595_a(this, DispenseFluidContainer.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
/*  42 */     if (world.field_72995_K) return EnumActionResult.SUCCESS;
/*     */     
/*  44 */     if (interactWithTank(player, hand, world, pos, side)) {
/*  45 */       player.field_71069_bz.func_75142_b();
/*  46 */       return EnumActionResult.SUCCESS;
/*     */     } 
/*  48 */     RayTraceResult position = func_77621_a(world, player, true);
/*  49 */     if (position == null) return EnumActionResult.FAIL;
/*     */     
/*  51 */     if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  52 */       pos = position.func_178782_a();
/*     */       
/*  54 */       if (!world.canMineBlockBody(player, pos)) return EnumActionResult.FAIL; 
/*  55 */       if (!player.func_175151_a(pos, position.field_178784_b, player.func_184586_b(hand))) return EnumActionResult.FAIL;
/*     */       
/*  57 */       if (LiquidUtil.drainBlockToContainer(world, pos, player, hand) || 
/*  58 */         LiquidUtil.fillBlockFromContainer(world, pos, player, hand) || 
/*  59 */         LiquidUtil.fillBlockFromContainer(world, pos.func_177972_a(side), player, hand)) {
/*  60 */         player.field_71069_bz.func_75142_b();
/*  61 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  66 */     return EnumActionResult.FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canfill(Fluid fluid) {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*  77 */     if (!func_194125_a(tab) || IC2.version.isClassic()) {
/*     */       return;
/*     */     }
/*  80 */     ItemStack emptyStack = new ItemStack(this);
/*  81 */     subItems.add(emptyStack);
/*     */     
/*  83 */     for (Fluid fluid : LiquidUtil.getAllFluids()) {
/*  84 */       if (fluid == null)
/*     */         continue; 
/*  86 */       ItemStack stack = getItemStack(fluid);
/*  87 */       if (stack != null) {
/*  88 */         subItems.add(stack);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean interactWithTank(EntityPlayer player, EnumHand hand, World world, BlockPos pos, EnumFacing side) {
/*  94 */     assert !world.field_72995_K;
/*     */     
/*  96 */     IFluidHandler tileHandler = FluidUtil.getFluidHandler(world, pos, side);
/*  97 */     if (tileHandler == null) return false;
/*     */     
/*  99 */     ItemStack stack = StackUtil.get(player, hand);
/* 100 */     boolean single = (StackUtil.getSize(stack) == 1);
/* 101 */     if (!single) stack = StackUtil.copyWithSize(stack, 1);
/*     */     
/* 103 */     boolean changeMade = false;
/*     */     while (true) {
/* 105 */       IFluidHandlerItem itemHandler = FluidUtil.getFluidHandler(StackUtil.copy(stack));
/* 106 */       assert itemHandler != null;
/*     */       
/* 108 */       if (FluidUtil.tryFluidTransfer(tileHandler, (IFluidHandler)itemHandler, 2147483647, true) != null)
/* 109 */       { if (single) {
/* 110 */           StackUtil.set(player, hand, itemHandler.getContainer());
/* 111 */           return true;
/*     */         } 
/*     */         
/* 114 */         StackUtil.consumeOrError(player, hand, 1);
/* 115 */         StackUtil.storeInventoryItem(itemHandler.getContainer(), player, false);
/* 116 */         changeMade = true;
/*     */ 
/*     */ 
/*     */         
/* 120 */         if (StackUtil.isEmpty(player, hand))
/*     */           break;  continue; }  break;
/* 122 */     }  return changeMade;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemFluidCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */