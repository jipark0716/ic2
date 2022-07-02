/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
/*    */ import ic2.core.item.armor.jetpack.JetpackHandler;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.ItemComparableItemStack;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JetpackRecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final ItemStack in;
/*    */   private final ItemStack out;
/*    */   private static List<JetpackRecipeWrapper> jetpackRecipes;
/*    */   
/*    */   private JetpackRecipeWrapper(ItemStack in) {
/* 37 */     this.in = in;
/* 38 */     ItemStack out = in.func_77946_l();
/* 39 */     JetpackHandler.setJetpackAttached(out, true);
/* 40 */     this.out = out;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<JetpackRecipeWrapper> generateJetpackRecipes() {
/* 46 */     if (jetpackRecipes != null) return jetpackRecipes;
/*    */     
/* 48 */     NonNullList<ItemStack> stacks = NonNullList.func_191196_a();
/* 49 */     Set<ItemComparableItemStack> added = new HashSet<>();
/* 50 */     jetpackRecipes = new ArrayList<>(100);
/*    */     
/* 52 */     for (Item item : ForgeRegistries.ITEMS) {
/* 53 */       if (JetpackAttachmentRecipe.blacklistedItems.contains(item))
/*    */         continue; 
/* 55 */       stacks.clear();
/* 56 */       added.clear();
/* 57 */       item.func_150895_a(CreativeTabs.field_78027_g, stacks);
/*    */       
/* 59 */       for (ItemStack stack : stacks) {
/* 60 */         if (EntityLiving.func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
/* 61 */           ItemComparableItemStack comparable = new ItemComparableItemStack(stack, false);
/* 62 */           if (!added.contains(comparable)) {
/* 63 */             jetpackRecipes.add(new JetpackRecipeWrapper(stack));
/* 64 */             added.add(comparable);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     return jetpackRecipes;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 75 */     ingredients.setInputs(ItemStack.class, Arrays.asList(new ItemStack[] { ItemName.jetpack_electric.getItemStack(), ItemName.crafting.getItemStack((Enum)CraftingItemType.jetpack_attachment_plate), this.in }));
/* 76 */     ingredients.setOutput(ItemStack.class, this.out);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\JetpackRecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */