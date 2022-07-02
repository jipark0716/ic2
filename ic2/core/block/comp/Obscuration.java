/*     */ package ic2.core.block.comp;
/*     */ 
/*     */ import ic2.api.event.RetextureEvent;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.state.BlockStateUtil;
/*     */ import ic2.core.item.tool.ItemObscurator;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Obscuration
/*     */   extends TileEntityComponent {
/*     */   private final Runnable changeHandler;
/*     */   private ObscurationData[] dataMap;
/*     */   
/*     */   public Obscuration(TileEntityBlock parent, Runnable changeHandler) {
/*  25 */     super(parent);
/*     */     
/*  27 */     this.changeHandler = changeHandler;
/*     */   }
/*     */   
/*     */   public void readFromNbt(NBTTagCompound nbt)
/*     */   {
/*  32 */     if (nbt.func_82582_d())
/*     */       return; 
/*  34 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/*  35 */       if (nbt.func_150297_b(facing.func_176610_l(), 10)) {
/*     */         
/*  37 */         NBTTagCompound cNbt = nbt.func_74775_l(facing.func_176610_l());
/*     */         
/*  39 */         Block block = Util.getBlock(cNbt.func_74779_i("block"));
/*  40 */         if (block != null) {
/*     */           
/*  42 */           String variant = cNbt.func_74779_i("variant");
/*  43 */           IBlockState state = BlockStateUtil.getState(block, variant);
/*  44 */           if (state != null) {
/*     */             
/*  46 */             int rawSide = cNbt.func_74771_c("side");
/*  47 */             if (rawSide >= 0 && rawSide < EnumFacing.field_82609_l.length) {
/*  48 */               EnumFacing side = EnumFacing.field_82609_l[rawSide];
/*     */               
/*  50 */               int[] colorMultipliers = ItemObscurator.internColorMultipliers(cNbt.func_74759_k("colorMuls"));
/*     */               
/*  52 */               ObscurationData data = new ObscurationData(state, variant, side, colorMultipliers);
/*     */               
/*  54 */               if (this.dataMap == null) this.dataMap = new ObscurationData[EnumFacing.field_82609_l.length];
/*     */               
/*  56 */               this.dataMap[facing.ordinal()] = data.intern();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } public NBTTagCompound writeToNbt() {
/*  62 */     if (this.dataMap == null) return null;
/*     */     
/*  64 */     NBTTagCompound ret = new NBTTagCompound();
/*     */     
/*  66 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/*  67 */       ObscurationData data = this.dataMap[facing.ordinal()];
/*  68 */       if (data != null) {
/*     */         
/*  70 */         NBTTagCompound nbt = new NBTTagCompound();
/*     */         
/*  72 */         nbt.func_74778_a("block", Util.getName(data.state.func_177230_c()).toString());
/*  73 */         nbt.func_74778_a("variant", data.variant);
/*  74 */         nbt.func_74774_a("side", (byte)data.side.ordinal());
/*  75 */         nbt.func_74783_a("colorMuls", data.colorMultipliers);
/*     */         
/*  77 */         ret.func_74782_a(facing.func_176610_l(), (NBTBase)nbt);
/*     */       } 
/*     */     } 
/*  80 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean applyObscuration(EnumFacing side, ObscurationData data) {
/*  84 */     if (this.dataMap != null && data.equals(this.dataMap[side.ordinal()])) return false;
/*     */     
/*  86 */     if (this.dataMap == null) this.dataMap = new ObscurationData[EnumFacing.field_82609_l.length];
/*     */     
/*  88 */     this.dataMap[side.ordinal()] = data.intern();
/*     */     
/*  90 */     this.changeHandler.run();
/*     */     
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  96 */     this.dataMap = null;
/*     */     
/*  98 */     this.changeHandler.run();
/*     */   }
/*     */   
/*     */   public boolean hasObscuration() {
/* 102 */     return (this.dataMap != null);
/*     */   }
/*     */   
/*     */   public ObscurationData[] getRenderState() {
/* 106 */     if (this.dataMap == null) return null;
/*     */     
/* 108 */     return Arrays.<ObscurationData>copyOf(this.dataMap, this.dataMap.length);
/*     */   }
/*     */   
/*     */   public static class ObscurationComponentEventHandler {
/*     */     public static void init() {
/* 113 */       new ObscurationComponentEventHandler();
/*     */     }
/*     */     
/*     */     private ObscurationComponentEventHandler() {
/* 117 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */     
/*     */     @SubscribeEvent
/*     */     public void onObscuration(RetextureEvent event) {
/* 122 */       if (event.state.func_177230_c() != BlockName.te.getInstance())
/*     */         return; 
/* 124 */       TileEntity teRaw = event.getWorld().func_175625_s(event.pos);
/* 125 */       if (!(teRaw instanceof TileEntityBlock))
/*     */         return; 
/* 127 */       Obscuration obscuration = (Obscuration)((TileEntityBlock)teRaw).getComponent(Obscuration.class);
/* 128 */       if (obscuration == null)
/*     */         return; 
/* 130 */       Obscuration.ObscurationData data = new Obscuration.ObscurationData(event.refState, event.refVariant, event.refSide, event.refColorMultipliers);
/*     */       
/* 132 */       if (obscuration.applyObscuration(event.side, data)) {
/* 133 */         event.applied = true;
/* 134 */         event.setCanceled(true);
/*     */       } 
/*     */     } }
/*     */   public static class ObscurationData { public final IBlockState state;
/*     */     public final String variant;
/*     */     
/*     */     public ObscurationData(IBlockState state, String variant, EnumFacing side, int[] colorMultipliers) {
/* 141 */       this.state = state;
/* 142 */       this.variant = variant;
/* 143 */       this.side = side;
/* 144 */       this.colorMultipliers = colorMultipliers;
/*     */     }
/*     */     public final EnumFacing side; public final int[] colorMultipliers;
/*     */     
/*     */     public boolean equals(Object obj) {
/* 149 */       if (obj == this) return true; 
/* 150 */       if (!(obj instanceof ObscurationData)) return false;
/*     */       
/* 152 */       ObscurationData o = (ObscurationData)obj;
/*     */       
/* 154 */       return (o.state.equals(this.state) && o.variant
/* 155 */         .equals(this.variant) && o.side == this.side && 
/*     */         
/* 157 */         Arrays.equals(o.colorMultipliers, this.colorMultipliers));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 162 */       return (this.state.hashCode() * 7 + this.side.ordinal()) * 23;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObscurationData intern() {
/* 167 */       return this;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\Obscuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */