/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.recipe.AdvRecipe;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityAssemblyBench
/*     */   extends TileEntityBatchCrafter
/*     */   implements IHasGui, IUpgradableBlock
/*     */ {
/*     */   public static class UuRecipe
/*     */     implements IRecipe {
/*     */     protected final ItemStack output;
/*     */     protected final boolean[][] shape;
/*     */     
/*     */     public static UuRecipe create(ItemStack output, Object... args) {
/*  40 */       Queue<String> inputArrangement = new ArrayDeque<>();
/*     */       
/*  42 */       for (Object arg : args) {
/*  43 */         if (arg instanceof String) {
/*  44 */           String str = (String)arg;
/*     */           
/*  46 */           if (str.isEmpty() || str.length() > 3) {
/*  47 */             AdvRecipe.displayError("none or too many crafting columns", "Input: " + str + "\nSize: " + str.length(), output, false);
/*     */           }
/*     */           
/*  50 */           inputArrangement.add(str);
/*     */         } 
/*     */       } 
/*     */       
/*  54 */       boolean[][] shape = new boolean[3][3];
/*     */       
/*  56 */       for (int y = 0; y < 3; y++) {
/*  57 */         String layer = inputArrangement.poll();
/*  58 */         for (int x = 0; x < 3; x++) {
/*  59 */           shape[y][x] = (layer.charAt(x) != ' ');
/*     */         }
/*     */       } 
/*     */       
/*  63 */       return new UuRecipe(output, shape);
/*     */     }
/*     */     
/*     */     public UuRecipe(ItemStack output, boolean[][] shape) {
/*  67 */       if (StackUtil.isEmpty(output)) AdvRecipe.displayError("Empty result", "UU recipe with shape " + Arrays.deepToString((Object[])shape), output, false);
/*     */       
/*  69 */       int inputWidth = (shape[0]).length;
/*  70 */       for (boolean[] col : shape) {
/*  71 */         if (col.length != inputWidth) {
/*  72 */           AdvRecipe.displayError("Inconsistent recipe shape", "UU recipe with shape " + Arrays.deepToString((Object[])shape), output, false);
/*     */         }
/*     */       } 
/*     */       
/*  76 */       this.output = output;
/*  77 */       this.shape = shape;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_77569_a(InventoryCrafting inv, World world) {
/*  82 */       ItemStack uu = ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter);
/*     */       
/*  84 */       for (int y = 0, height = inv.func_174923_h(), width = inv.func_174922_i(); y < height; y++) {
/*  85 */         boolean[] layer = this.shape[y];
/*     */         
/*  87 */         for (int x = 0; x < width; x++) {
/*  88 */           ItemStack stack = inv.func_70463_b(x, y);
/*     */           
/*  90 */           if (layer[x])
/*  91 */           { if (!StackUtil.checkItemEquality(stack, uu)) return false;
/*     */              }
/*  93 */           else if (!StackUtil.isEmpty(stack)) { return false; }
/*     */         
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_77571_b() {
/* 103 */       return this.output;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack func_77572_b(InventoryCrafting inv) {
/* 109 */       return func_77571_b();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194133_a(int width, int height) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IRecipe setRegistryName(ResourceLocation name) {
/* 119 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceLocation getRegistryName() {
/* 124 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<IRecipe> getRegistryType() {
/* 129 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/* 132 */   public static final List<IRecipe> RECIPES = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IRecipe findRecipe() {
/* 141 */     for (IRecipe recipe : RECIPES) {
/* 142 */       if (recipe.func_77569_a(this.crafting, func_145831_w())) {
/* 143 */         return recipe;
/*     */       }
/*     */     } 
/*     */     
/* 147 */     return null;
/*     */   }
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
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
/* 165 */     tooltip.add("You probably want the " + Localization.translate(getBlockType().func_149739_a() + '.' + TeBlock.replicator.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 171 */     return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityAssemblyBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */