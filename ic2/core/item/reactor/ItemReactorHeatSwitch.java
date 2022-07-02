/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.core.ref.ItemName;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReactorHeatSwitch
/*     */   extends ItemReactorHeatStorage
/*     */ {
/*     */   public final int switchSide;
/*     */   public final int switchReactor;
/*     */   
/*     */   public ItemReactorHeatSwitch(ItemName name, int heatStorage, int switchside, int switchreactor) {
/*  20 */     super(name, heatStorage);
/*     */     
/*  22 */     this.switchSide = switchside;
/*  23 */     this.switchReactor = switchreactor;
/*     */   }
/*     */ 
/*     */   
/*     */   private class ItemStackCoord
/*     */   {
/*     */     public ItemStack stack;
/*     */     
/*     */     public int x;
/*     */     public int y;
/*     */     
/*     */     public ItemStackCoord(ItemStack stack1, int x1, int y1) {
/*  35 */       this.stack = stack1;
/*  36 */       this.x = x1;
/*  37 */       this.y = y1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
/*  43 */     if (!heatrun)
/*     */       return; 
/*  45 */     int myHeat = 0;
/*     */     
/*  47 */     ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<>();
/*     */ 
/*     */     
/*  50 */     if (this.switchSide > 0) {
/*  51 */       checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
/*  52 */       checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
/*  53 */       checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
/*  54 */       checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
/*     */     } 
/*     */     
/*  57 */     if (this.switchSide > 0) {
/*  58 */       for (ItemStackCoord stackcoord : heatAcceptors) {
/*  59 */         IReactorComponent heatable = (IReactorComponent)stackcoord.stack.func_77973_b();
/*     */         
/*  61 */         double mymed = getCurrentHeat(stack, reactor, x, y) * 100.0D / getMaxHeat(stack, reactor, x, y);
/*  62 */         double heatablemed = heatable.getCurrentHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y) * 100.0D / heatable.getMaxHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y);
/*     */         
/*  64 */         int add = (int)(heatable.getMaxHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y) / 100.0D * (heatablemed + mymed / 2.0D));
/*     */         
/*  66 */         if (add > this.switchSide) add = this.switchSide;
/*     */         
/*  68 */         if (heatablemed + mymed / 2.0D < 1.0D) add = this.switchSide / 2; 
/*  69 */         if (heatablemed + mymed / 2.0D < 0.75D) add = this.switchSide / 4; 
/*  70 */         if (heatablemed + mymed / 2.0D < 0.5D) add = this.switchSide / 8; 
/*  71 */         if (heatablemed + mymed / 2.0D < 0.25D) add = 1;
/*     */         
/*  73 */         if (Math.round(heatablemed * 10.0D) / 10.0D > Math.round(mymed * 10.0D) / 10.0D) {
/*  74 */           add -= 2 * add;
/*  75 */         } else if (Math.round(heatablemed * 10.0D) / 10.0D == Math.round(mymed * 10.0D) / 10.0D) {
/*     */           
/*  77 */           add = 0;
/*     */         } 
/*     */         
/*  80 */         myHeat -= add;
/*  81 */         add = heatable.alterHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y, add);
/*  82 */         myHeat += add;
/*     */       } 
/*     */     }
/*     */     
/*  86 */     if (this.switchReactor > 0) {
/*  87 */       double mymed = getCurrentHeat(stack, reactor, x, y) * 100.0D / getMaxHeat(stack, reactor, x, y);
/*  88 */       double Reactormed = reactor.getHeat() * 100.0D / reactor.getMaxHeat();
/*     */       
/*  90 */       int add = (int)Math.round(reactor.getMaxHeat() / 100.0D * (Reactormed + mymed / 2.0D));
/*  91 */       if (add > this.switchReactor) add = this.switchReactor;
/*     */       
/*  93 */       if (Reactormed + mymed / 2.0D < 1.0D) add = this.switchSide / 2; 
/*  94 */       if (Reactormed + mymed / 2.0D < 0.75D) add = this.switchSide / 4; 
/*  95 */       if (Reactormed + mymed / 2.0D < 0.5D) add = this.switchSide / 8; 
/*  96 */       if (Reactormed + mymed / 2.0D < 0.25D) add = 1;
/*     */       
/*  98 */       if (Math.round(Reactormed * 10.0D) / 10.0D > Math.round(mymed * 10.0D) / 10.0D) {
/*  99 */         add -= 2 * add;
/* 100 */       } else if (Math.round(Reactormed * 10.0D) / 10.0D == Math.round(mymed * 10.0D) / 10.0D) {
/*     */         
/* 102 */         add = 0;
/*     */       } 
/*     */       
/* 105 */       myHeat -= add;
/* 106 */       reactor.setHeat(reactor.getHeat() + add);
/*     */     } 
/*     */     
/* 109 */     alterHeat(stack, reactor, x, y, myHeat);
/*     */   }
/*     */   
/*     */   private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemStackCoord> heatAcceptors) {
/* 113 */     ItemStack stack = reactor.getItemAt(x, y);
/*     */     
/* 115 */     if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/* 116 */       IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*     */       
/* 118 */       if (comp.canStoreHeat(stack, reactor, x, y))
/* 119 */         heatAcceptors.add(new ItemStackCoord(stack, x, y)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorHeatSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */