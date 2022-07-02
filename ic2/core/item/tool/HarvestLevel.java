/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public enum HarvestLevel {
/*  6 */   Wood(0, Item.ToolMaterial.WOOD),
/*  7 */   Stone(1, Item.ToolMaterial.STONE),
/*  8 */   Iron(2, Item.ToolMaterial.IRON),
/*  9 */   Diamond(3, Item.ToolMaterial.DIAMOND),
/* 10 */   Iridium(100, Item.ToolMaterial.DIAMOND);
/*    */   
/*    */   HarvestLevel(int level, Item.ToolMaterial toolMaterial) {
/* 13 */     this.level = level;
/* 14 */     this.toolMaterial = toolMaterial;
/*    */   }
/*    */   
/*    */   public final int level;
/*    */   public final Item.ToolMaterial toolMaterial;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HarvestLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */