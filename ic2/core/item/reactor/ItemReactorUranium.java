/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.core.IC2Potion;
/*     */ import ic2.core.item.armor.ItemArmorHazmat;
/*     */ import ic2.core.item.type.NuclearResourceType;
/*     */ import ic2.core.ref.ItemName;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReactorUranium
/*     */   extends AbstractDamageableReactorComponent
/*     */ {
/*     */   public final int numberOfCells;
/*     */   
/*     */   public ItemReactorUranium(ItemName name, int cells) {
/*  31 */     this(name, cells, 20000);
/*     */   }
/*     */   
/*     */   protected ItemReactorUranium(ItemName name, int cells, int duration) {
/*  35 */     super(name, duration);
/*     */     
/*  37 */     func_77625_d(64);
/*     */     
/*  39 */     this.numberOfCells = cells;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(ItemName name) {
/*  45 */     registerModel(0, name, null);
/*  46 */     registerModel(1, name, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata(ItemStack stack) {
/*  51 */     return (getCustomDamage(stack) > 0) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
/*  56 */     if (!reactor.produceEnergy())
/*     */       return; 
/*  58 */     int basePulses = 1 + this.numberOfCells / 2;
/*     */     
/*  60 */     for (int iteration = 0; iteration < this.numberOfCells; iteration++) {
/*  61 */       int pulses = basePulses;
/*     */       
/*  63 */       if (!heatRun) {
/*  64 */         for (int i = 0; i < pulses; i++) {
/*  65 */           acceptUraniumPulse(stack, reactor, stack, x, y, x, y, heatRun);
/*     */         }
/*     */         
/*  68 */         pulses += checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + 
/*  69 */           checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + 
/*  70 */           checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + 
/*  71 */           checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
/*     */       } else {
/*  73 */         pulses += checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + 
/*  74 */           checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + 
/*  75 */           checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + 
/*  76 */           checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
/*     */         
/*  78 */         int heat = triangularNumber(pulses) * 4;
/*     */         
/*  80 */         heat = getFinalHeat(stack, reactor, x, y, heat);
/*     */         
/*  82 */         Queue<ItemStackCoord> heatAcceptors = new ArrayDeque<>();
/*     */         
/*  84 */         checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
/*  85 */         checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
/*  86 */         checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
/*  87 */         checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
/*     */         
/*  89 */         while (!heatAcceptors.isEmpty() && heat > 0) {
/*  90 */           int dheat = heat / heatAcceptors.size();
/*  91 */           heat -= dheat;
/*  92 */           ItemStackCoord acceptor = heatAcceptors.remove();
/*  93 */           IReactorComponent acceptorComp = (IReactorComponent)acceptor.stack.func_77973_b();
/*  94 */           dheat = acceptorComp.alterHeat(acceptor.stack, reactor, acceptor.x, acceptor.y, dheat);
/*  95 */           heat += dheat;
/*     */         } 
/*     */         
/*  98 */         if (heat > 0) reactor.addHeat(heat);
/*     */       
/*     */       } 
/*     */     } 
/* 102 */     if (!heatRun && getCustomDamage(stack) >= getMaxCustomDamage(stack) - 1) {
/* 103 */       reactor.setItemAt(x, y, getDepletedStack(stack, reactor));
/* 104 */     } else if (!heatRun) {
/* 105 */       applyCustomDamage(stack, 1, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 110 */     return heat;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
/*     */     ItemStack ret;
/* 116 */     switch (this.numberOfCells) { case 1:
/* 117 */         ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_uranium);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         return ret.func_77946_l();case 2: ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_dual_uranium); return ret.func_77946_l();case 4: ret = ItemName.nuclear.getItemStack((Enum)NuclearResourceType.depleted_quad_uranium); return ret.func_77946_l(); }
/*     */     
/*     */     throw new RuntimeException("invalid cell count: " + this.numberOfCells);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
/* 131 */     ItemStack other = reactor.getItemAt(x, y);
/*     */     
/* 133 */     if (other != null && other.func_77973_b() instanceof IReactorComponent && (
/* 134 */       (IReactorComponent)other.func_77973_b()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun)) {
/* 135 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 139 */     return 0;
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
/*     */   protected static int triangularNumber(int x) {
/* 152 */     return (x * x + x) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkHeatAcceptor(IReactor reactor, int x, int y, Collection<ItemStackCoord> heatAcceptors) {
/* 160 */     ItemStack stack = reactor.getItemAt(x, y);
/*     */     
/* 162 */     if (stack != null && stack.func_77973_b() instanceof IReactorComponent && (
/* 163 */       (IReactorComponent)stack.func_77973_b()).canStoreHeat(stack, reactor, x, y))
/* 164 */       heatAcceptors.add(new ItemStackCoord(stack, x, y)); 
/*     */   }
/*     */   private static class ItemStackCoord { public final ItemStack stack;
/*     */     public final int x;
/*     */     public final int y;
/*     */     
/*     */     public ItemStackCoord(ItemStack stack, int x, int y) {
/* 171 */       this.stack = stack;
/* 172 */       this.x = x;
/* 173 */       this.y = y;
/*     */     } }
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
/*     */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 186 */     if (!heatrun) {
/* 187 */       reactor.addOutput(1.0F);
/*     */     }
/*     */     
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 195 */     return (2 * this.numberOfCells);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
/* 200 */     if (entity instanceof EntityLivingBase) {
/* 201 */       EntityLivingBase entityLiving = (EntityLivingBase)entity;
/*     */       
/* 203 */       if (!ItemArmorHazmat.hasCompleteHazmat(entityLiving))
/* 204 */         IC2Potion.radiation.applyTo(entityLiving, 200, 100); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */