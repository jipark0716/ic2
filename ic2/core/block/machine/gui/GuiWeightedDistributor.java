/*     */ package ic2.core.block.machine.gui;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.block.machine.tileentity.IWeightedDistributor;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.gui.StickyVanillaButton;
/*     */ import ic2.core.gui.Text;
/*     */ import ic2.core.gui.dynamic.TextProvider;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public abstract class GuiWeightedDistributor<T extends ContainerBase<? extends IWeightedDistributor>>
/*     */   extends GuiIC2<T> {
/*     */   protected final StickyVanillaButton[][] buttons;
/*     */   
/*     */   public GuiWeightedDistributor(T container, int height) {
/*  25 */     super((ContainerBase)container, height);
/*     */     
/*  27 */     this.buttons = new StickyVanillaButton[5][6];
/*  28 */     for (int y = 0; y < 5; y++) {
/*  29 */       TextProvider.ITextProvider text; final int row = y;
/*  30 */       for (int col = 0; col < 6; col++) {
/*  31 */         final EnumFacing facing = EnumFacing.func_82600_a(facingOffset(col));
/*  32 */         addElement(
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
/* 114 */             (GuiElement)(this.buttons[y][col] = (new StickyVanillaButton(this, 63 + col * 18, 17 + y * 18, 16, 16, new IClickHandler() { private void rebalance(int change) { for (int i = change + 1; i < GuiWeightedDistributor.this.buttons.length; i++) { for (int side = 0; side < 6; side++) { StickyVanillaButton button = GuiWeightedDistributor.this.buttons[i][side]; if (button.isOn()) { GuiWeightedDistributor.this.buttons[i - 1][side].setOn(true); button.setOn(false); break; }  }  }  } private int findNextEmptyRow(int start) { while (start-- > 0) { for (StickyVanillaButton button : GuiWeightedDistributor.this.buttons[start]) { if (button.isOn()) return start + 1;  }  }  return 0; } public void onClick(MouseButton mouse) { boolean switchingOff = false; for (int i = 0, aim = GuiWeightedDistributor.buttonOffset(facing.func_176745_a()); i < GuiWeightedDistributor.this.buttons.length; i++) { if (GuiWeightedDistributor.this.buttons[i][aim].isOn()) { GuiWeightedDistributor.this.buttons[i][aim].setOn(false); switchingOff = (i == row); rebalance(i); break; }  }  if (!switchingOff) { StickyVanillaButton[] switches = GuiWeightedDistributor.this.buttons[findNextEmptyRow(row)]; for (int j = 0, k = GuiWeightedDistributor.buttonOffset(facing.func_176745_a()); j < switches.length; j++) switches[j].setOn((j == k));  }  List<EnumFacing> priorities = ((IWeightedDistributor)(GuiWeightedDistributor.this.getContainer()).base).getPriority(); priorities.clear(); for (StickyVanillaButton[] switches : GuiWeightedDistributor.this.buttons) { for (int j = 0; j < switches.length; j++) { if (switches[j].isOn()) { priorities.add(EnumFacing.func_82600_a(GuiWeightedDistributor.facingOffset(j))); break; }  }  }  ((IWeightedDistributor)(GuiWeightedDistributor.this.getContainer()).base).updatePriority(false); } })).withDisableHandler(new IEnableHandler() { public boolean isEnabled() { return (((IWeightedDistributor)(GuiWeightedDistributor.this.getContainer()).base).getFacing() != facing); } }).withText(facing.func_176610_l().substring(0, 1).toUpperCase(Locale.ENGLISH)).withTooltip(getNameForFacing(facing))));
/*     */       } 
/*     */ 
/*     */       
/* 118 */       switch (y) {
/*     */         case 0:
/* 120 */           text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.highest");
/*     */           break;
/*     */         
/*     */         case 1:
/* 124 */           text = TextProvider.of("↑");
/*     */           break;
/*     */         
/*     */         case 2:
/* 128 */           text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.priority");
/*     */           break;
/*     */         
/*     */         case 3:
/* 132 */           text = TextProvider.of("↓");
/*     */           break;
/*     */         
/*     */         case 4:
/* 136 */           text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.lowest");
/*     */           break;
/*     */         
/*     */         default:
/* 140 */           throw new IllegalStateException("Ended up being on y=" + y);
/*     */       } 
/* 142 */       addElement((GuiElement)Text.create(this, 8, 21 + y * 18, text, 4210752, false));
/*     */     } 
/*     */     
/* 145 */     int end = 0;
/* 146 */     for (EnumFacing side : ((IWeightedDistributor)((ContainerBase)container).base).getPriority()) {
/* 147 */       this.buttons[end++][buttonOffset(side.func_176745_a())].setOn(true);
/*     */     }
/*     */   }
/*     */   
/*     */   static int facingOffset(int facing) {
/* 152 */     return (facing + 1) % 6;
/*     */   }
/*     */   
/*     */   static int buttonOffset(int facing) {
/* 156 */     return (facing + 5) % 6;
/*     */   }
/*     */   
/*     */   private static String getNameForFacing(EnumFacing facing) {
/* 160 */     switch (facing) { case WEST:
/* 161 */         return "ic2.dir.West";
/* 162 */       case EAST: return "ic2.dir.East";
/* 163 */       case DOWN: return "ic2.dir.Bottom";
/* 164 */       case UP: return "ic2.dir.Top";
/* 165 */       case NORTH: return "ic2.dir.North";
/* 166 */       case SOUTH: return "ic2.dir.South"; }
/* 167 */      throw new IllegalStateException("Unexpected direction: " + facing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiWeightedDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */