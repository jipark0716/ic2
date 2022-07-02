/*     */ package ic2.core.apihelper;
/*     */ 
/*     */ import ic2.api.item.IItemAPI;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.IMultiBlock;
/*     */ import ic2.core.ref.ItemName;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemAPI
/*     */   implements IItemAPI
/*     */ {
/*     */   public ItemStack getItemStack(String name, String variant) {
/*  26 */     if (name == null) return null;
/*     */     
/*  28 */     if (variant == null) {
/*  29 */       int idx = name.indexOf('#');
/*     */       
/*  31 */       if (idx != -1) {
/*  32 */         variant = name.substring(idx + 1);
/*  33 */         name = name.substring(0, idx);
/*     */       } 
/*     */     } 
/*     */     
/*  37 */     BlockName blockName = getBlockName(name);
/*  38 */     if (blockName != null) return blockName.getItemStack(variant);
/*     */     
/*  40 */     ItemName itemName = getItemName(name);
/*  41 */     if (itemName != null) return itemName.getItemStack(variant);
/*     */     
/*  43 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock(String name) {
/*  48 */     if (name == null) return null; 
/*  49 */     BlockName blockName = getBlockName(name);
/*  50 */     if (blockName != null) return blockName.getInstance();
/*     */     
/*  52 */     FluidName fluidName = getFluidName(name);
/*  53 */     if (fluidName != null) return fluidName.getInstance().getBlock();
/*     */     
/*  55 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(String name) {
/*  60 */     if (name == null) return null;
/*     */     
/*  62 */     ItemName itemName = getItemName(name);
/*  63 */     if (itemName != null) return itemName.getInstance();
/*     */     
/*  65 */     Block block = getBlock(name);
/*     */     
/*  67 */     if (block != null) {
/*  68 */       Item ret = Item.func_150898_a(block);
/*  69 */       if (ret != Items.field_190931_a || block == Blocks.field_150350_a) return ret;
/*     */     
/*     */     } 
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState(String name, String variant) {
/*  77 */     if (variant == null) {
/*  78 */       int idx = name.indexOf('#');
/*     */       
/*  80 */       if (idx != -1) {
/*  81 */         variant = name.substring(idx + 1);
/*  82 */         name = name.substring(0, idx);
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     BlockName blockName = getBlockName(name);
/*     */     
/*  88 */     if (blockName != null) {
/*  89 */       Block block = blockName.getInstance();
/*     */       
/*  91 */       if (block instanceof IMultiBlock) {
/*  92 */         return ((IMultiBlock)block).getState(variant);
/*     */       }
/*  94 */       assert variant == null;
/*  95 */       return block.func_176223_P();
/*     */     } 
/*     */ 
/*     */     
/*  99 */     FluidName fluidName = getFluidName(name);
/*     */     
/* 101 */     if (fluidName != null) {
/* 102 */       assert variant == null;
/* 103 */       return fluidName.getInstance().getBlock().func_176223_P();
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private ItemName getItemName(String itemName) {
/* 110 */     for (ItemName name : ItemName.values) {
/* 111 */       if (name.name().equalsIgnoreCase(itemName)) return name;
/*     */     
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   private BlockName getBlockName(String blockName) {
/* 118 */     for (BlockName name : BlockName.values) {
/* 119 */       if (name.name().equalsIgnoreCase(blockName)) return name;
/*     */     
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   private FluidName getFluidName(String fluidName) {
/* 126 */     for (FluidName name : FluidName.values) {
/* 127 */       if (name.name().equalsIgnoreCase(fluidName)) return name;
/*     */     
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\apihelper\ItemAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */