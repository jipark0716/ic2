/*     */ package ic2.core.block.reactor.tileentity;
/*     */ 
/*     */ import ic2.core.profile.NotClassic;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.capabilities.Capability;
/*     */ import net.minecraftforge.items.CapabilityItemHandler;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.wrapper.InvWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityReactorAccessHatch
/*     */   extends TileEntityReactorVessel
/*     */   implements IInventory
/*     */ {
/*     */   private IItemHandler itemHandler;
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  27 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  29 */     if (reactor != null) {
/*  30 */       World world = func_145831_w();
/*     */       
/*  32 */       return reactor.getBlockType().func_180639_a(world, reactor.func_174877_v(), world.func_180495_p(reactor.func_174877_v()), player, hand, side, hitX, hitY, hitZ);
/*     */     } 
/*     */     
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/*  43 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  45 */     return (reactor != null) ? reactor.func_70005_c_() : "<null>";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  50 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  52 */     return (reactor != null) ? reactor.func_145818_k_() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/*  57 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  59 */     return (reactor != null) ? reactor.func_145748_c_() : (ITextComponent)new TextComponentString("<null>");
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  64 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  66 */     return (reactor != null) ? reactor.func_70302_i_() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  71 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  73 */     return (reactor != null) ? reactor.func_191420_l() : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int index) {
/*  78 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  80 */     return (reactor != null) ? reactor.func_70301_a(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int index, int count) {
/*  85 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  87 */     return (reactor != null) ? reactor.func_70298_a(index, count) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int index) {
/*  92 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/*  94 */     return (reactor != null) ? reactor.func_70304_b(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int index, ItemStack stack) {
/*  99 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 101 */     if (reactor != null) reactor.func_70299_a(index, stack);
/*     */   
/*     */   }
/*     */   
/*     */   public int func_70297_j_() {
/* 106 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 108 */     return (reactor != null) ? reactor.func_70297_j_() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 113 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 115 */     return (reactor != null) ? reactor.func_70300_a(player) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {
/* 120 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 122 */     if (reactor != null) reactor.func_174889_b(player);
/*     */   
/*     */   }
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {
/* 127 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 129 */     if (reactor != null) reactor.func_174886_c(player);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean func_94041_b(int index, ItemStack stack) {
/* 134 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 136 */     return (reactor != null) ? reactor.func_94041_b(index, stack) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 141 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 143 */     return (reactor != null) ? reactor.func_174887_a_(id) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {
/* 148 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 150 */     if (reactor != null) reactor.func_174885_b(id, value);
/*     */   
/*     */   }
/*     */   
/*     */   public int func_174890_g() {
/* 155 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 157 */     return (reactor != null) ? reactor.func_174890_g() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/* 162 */     TileEntityNuclearReactorElectric reactor = getReactorInstance();
/*     */     
/* 164 */     if (reactor != null) reactor.func_174888_l();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
/* 171 */     return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
/* 176 */     if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
/* 177 */       if (this.itemHandler == null) {
/* 178 */         this.itemHandler = (IItemHandler)new InvWrapper(this);
/*     */       }
/* 180 */       return (T)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler);
/*     */     } 
/* 182 */     return (T)super.getCapability(capability, facing);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\tileentity\TileEntityReactorAccessHatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */