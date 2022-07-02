/*    */ package ic2.core.gui;
/*    */ 
/*    */ import gnu.trove.impl.unmodifiable.TUnmodifiableIntSet;
/*    */ import gnu.trove.set.TIntSet;
/*    */ import gnu.trove.set.hash.TIntHashSet;
/*    */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*    */ import ic2.core.block.machine.gui.GuiElectrolyzer;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*    */ 
/*    */ public class ElectrolyzerTankController extends GuiElement<ElectrolyzerTankController> {
/*    */   private int lastRecipeLength;
/*    */   private final TileEntityElectrolyzer electrolyzer;
/*    */   private final GuiElectrolyzer.ElectrolyzerFluidTank[] tanks;
/*    */   
/* 15 */   public ElectrolyzerTankController(GuiElectrolyzer gui, int x, int y, GuiElectrolyzer.ElectrolyzerFluidTank... tanks) { super((GuiIC2<?>)gui, x, y, 0, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     this.lastRecipeLength = 0;
/*    */     this.electrolyzer = (TileEntityElectrolyzer)((ContainerElectrolyzer)gui.getContainer()).base;
/*    */     this.tanks = tanks; }
/*    */    public boolean contains(int x, int y) {
/*    */     return false;
/* 63 */   } public static final TIntSet ONE_THREE_FIVE = (TIntSet)new TUnmodifiableIntSet((TIntSet)new TIntHashSet(new int[] { 1, 3, 5 }));
/* 64 */   public int getLastRecipeLength() { return this.lastRecipeLength; } public void tick() { for (GuiElectrolyzer.ElectrolyzerFluidTank tank : this.tanks) tank.clear();  if (!this.electrolyzer.hasRecipe()) { this.lastRecipeLength = 0; return; }  IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs = (this.electrolyzer.getCurrentRecipe()).outputs; int length = this.lastRecipeLength = outputs.length; if (length < 1) return;  if (ONE_THREE_FIVE.contains(length)) this.tanks[2].setPair(outputs[length / 2].getFullOutput());  if (TWO_TO_FIVE.contains(length)) { this.tanks[1].setPair(outputs[(length < 4) ? 0 : 1].getFullOutput()); this.tanks[3].setPair(outputs[length - ((length < 4) ? 1 : 2)].getFullOutput()); }  if (FOUR_FIVE.contains(length)) { this.tanks[0].setPair(outputs[0].getFullOutput()); this.tanks[4].setPair(outputs[length - 1].getFullOutput()); }  } public static final TIntSet TWO_TO_FIVE = (TIntSet)new TUnmodifiableIntSet((TIntSet)new TIntHashSet(new int[] { 2, 3, 4, 5 }));
/* 65 */   public static final TIntSet FOUR_FIVE = (TIntSet)new TUnmodifiableIntSet((TIntSet)new TIntHashSet(new int[] { 4, 5 }));
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\ElectrolyzerTankController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */