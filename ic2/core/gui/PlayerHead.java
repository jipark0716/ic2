/*    */ package ic2.core.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerHead
/*    */   extends ItemImage
/*    */ {
/* 23 */   private static final Map<GameProfile, Supplier<ItemStack>> IMAGE_MAKER = new WeakHashMap<>();
/*    */   private final GameProfile player;
/*    */   
/*    */   public PlayerHead(GuiIC2<?> gui, int x, int y, GameProfile player) {
/* 27 */     this(gui, x, y, TileEntitySkull.func_174884_b(player), null);
/*    */   }
/*    */   
/*    */   private PlayerHead(GuiIC2<?> gui, int x, int y, GameProfile player, Void skip) {
/* 31 */     super(gui, x, y, IMAGE_MAKER.computeIfAbsent(player, profile -> {
/*    */             ItemStack skull = new ItemStack(Items.field_151144_bL, 1, 3);
/*    */             
/*    */             StackUtil.getOrCreateNbtData(skull).func_74782_a("SkullOwner", (NBTBase)NBTUtil.func_180708_a(new NBTTagCompound(), profile));
/*    */             return ();
/*    */           }));
/* 37 */     this.player = player;
/* 38 */     assert player.equals(NBTUtil.func_152459_a(((ItemStack)((Supplier)IMAGE_MAKER.get(player)).get()).func_77978_p().func_74775_l("SkullOwner")));
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getToolTip() {
/* 43 */     List<String> tooltip = super.getToolTip();
/*    */     
/* 45 */     if (StringUtils.isNotBlank(this.player.getName())) {
/* 46 */       tooltip.add(this.player.getName());
/*    */     }
/*    */     
/* 49 */     return tooltip;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\PlayerHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */