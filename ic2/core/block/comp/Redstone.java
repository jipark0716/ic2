/*     */ package ic2.core.block.comp;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Redstone extends TileEntityComponent {
/*     */   private int redstoneInput;
/*     */   private Set<IRedstoneChangeHandler> changeSubscribers;
/*     */   
/*     */   public Redstone(TileEntityBlock parent) {
/*  18 */     super(parent);
/*     */   }
/*     */   private Set<IRedstoneModifier> modifiers; private LinkHandler outboundLink;
/*     */   
/*     */   public void onLoaded() {
/*  23 */     super.onLoaded();
/*     */     
/*  25 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  30 */     unlinkOutbound();
/*  31 */     unlinkInbound();
/*     */     
/*  33 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNeighborChange(Block srcBlock, BlockPos neighborPos) {
/*  38 */     super.onNeighborChange(srcBlock, neighborPos);
/*     */     
/*  40 */     update();
/*     */   }
/*     */   
/*     */   public void update() {
/*  44 */     World world = this.parent.func_145831_w();
/*  45 */     if (world == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  50 */     int input = world.func_175687_A(this.parent.func_174877_v());
/*     */     
/*  52 */     if (this.modifiers != null) {
/*  53 */       for (IRedstoneModifier modifier : this.modifiers) {
/*  54 */         input = modifier.getRedstoneInput(input);
/*     */       }
/*     */     }
/*     */     
/*  58 */     if (input != this.redstoneInput) {
/*  59 */       this.redstoneInput = input;
/*     */       
/*  61 */       if (this.changeSubscribers != null) {
/*  62 */         for (IRedstoneChangeHandler subscriber : this.changeSubscribers) {
/*  63 */           subscriber.onRedstoneChange(input);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getRedstoneInput() {
/*  70 */     return this.redstoneInput;
/*     */   }
/*     */   
/*     */   public boolean hasRedstoneInput() {
/*  74 */     return (this.redstoneInput > 0);
/*     */   }
/*     */   
/*     */   public void subscribe(IRedstoneChangeHandler handler) {
/*  78 */     if (handler == null) throw new NullPointerException("null handler"); 
/*  79 */     if (this.changeSubscribers == null) this.changeSubscribers = new HashSet<>();
/*     */     
/*  81 */     this.changeSubscribers.add(handler);
/*     */   }
/*     */   
/*     */   public void unsubscribe(IRedstoneChangeHandler handler) {
/*  85 */     if (handler == null) throw new NullPointerException("null handler"); 
/*  86 */     if (this.changeSubscribers == null)
/*     */       return; 
/*  88 */     this.changeSubscribers.remove(handler);
/*     */     
/*  90 */     if (this.changeSubscribers.isEmpty()) this.changeSubscribers = null; 
/*     */   }
/*     */   
/*     */   public void addRedstoneModifier(IRedstoneModifier modifier) {
/*  94 */     if (this.modifiers == null) {
/*  95 */       this.modifiers = new HashSet<>();
/*     */     }
/*     */     
/*  98 */     this.modifiers.add(modifier);
/*     */   }
/*     */   
/*     */   public void addRedstoneModifiers(Collection<IRedstoneModifier> modifiers) {
/* 102 */     if (this.modifiers == null) {
/* 103 */       this.modifiers = new HashSet<>(modifiers);
/*     */     } else {
/* 105 */       this.modifiers.addAll(modifiers);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeRedstoneModifier(IRedstoneModifier modifier) {
/* 110 */     if (this.modifiers == null)
/*     */       return; 
/* 112 */     this.modifiers.remove(modifier);
/*     */   }
/*     */   
/*     */   public void removeRedstoneModifiers(Collection<IRedstoneModifier> modifiers) {
/* 116 */     if (this.modifiers == null)
/*     */       return; 
/* 118 */     this.modifiers.removeAll(modifiers);
/* 119 */     if (this.modifiers.isEmpty()) this.modifiers = null; 
/*     */   }
/*     */   
/*     */   public boolean isLinked() {
/* 123 */     return (this.outboundLink != null);
/*     */   }
/*     */   
/*     */   public Redstone getLinkReceiver() {
/* 127 */     return (this.outboundLink != null) ? this.outboundLink.receiver : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Redstone> getLinkedOrigins() {
/* 132 */     if (this.modifiers == null) return Collections.emptyList();
/*     */     
/* 134 */     List<Redstone> ret = new ArrayList<>(this.modifiers.size());
/*     */     
/* 136 */     for (IRedstoneModifier modifier : this.modifiers) {
/* 137 */       if (modifier instanceof LinkHandler) {
/* 138 */         ret.add(((LinkHandler)modifier).origin);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return Collections.unmodifiableList(ret);
/*     */   }
/*     */   
/*     */   public void linkTo(Redstone receiver) {
/* 146 */     if (receiver == null) throw new NullPointerException("null receiver");
/*     */     
/* 148 */     if (this.outboundLink != null) {
/* 149 */       if (this.outboundLink.receiver != receiver) {
/* 150 */         throw new IllegalStateException("already linked");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 156 */     this.outboundLink = new LinkHandler(this, receiver);
/*     */     
/* 158 */     this.outboundLink.receiver.addRedstoneModifier(this.outboundLink);
/* 159 */     subscribe(this.outboundLink);
/* 160 */     receiver.update();
/*     */   }
/*     */   
/*     */   public void unlinkOutbound() {
/* 164 */     if (this.outboundLink == null)
/*     */       return; 
/* 166 */     this.outboundLink.receiver.removeRedstoneModifier(this.outboundLink);
/* 167 */     unsubscribe(this.outboundLink);
/* 168 */     this.outboundLink = null;
/*     */   }
/*     */   
/*     */   public void unlinkInbound() {
/* 172 */     for (Redstone origin : getLinkedOrigins()) {
/* 173 */       origin.unlinkOutbound();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LinkHandler
/*     */     implements IRedstoneChangeHandler, IRedstoneModifier
/*     */   {
/*     */     private final Redstone origin;
/*     */     
/*     */     private final Redstone receiver;
/*     */ 
/*     */     
/*     */     public LinkHandler(Redstone origin, Redstone receiver) {
/* 187 */       this.origin = origin;
/* 188 */       this.receiver = receiver;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onRedstoneChange(int newLevel) {
/* 193 */       this.receiver.update();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRedstoneInput(int redstoneInput) {
/* 198 */       return Math.max(redstoneInput, this.origin.redstoneInput);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface IRedstoneChangeHandler {
/*     */     void onRedstoneChange(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface IRedstoneModifier {
/*     */     int getRedstoneInput(int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Redstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */