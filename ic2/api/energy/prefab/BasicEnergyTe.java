/*    */ package ic2.api.energy.prefab;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class BasicEnergyTe<T extends BasicEnergyTile>
/*    */   extends TileEntity
/*    */ {
/*    */   protected T energyBuffer;
/*    */   
/*    */   public static class Sink
/*    */     extends BasicEnergyTe<BasicSink>
/*    */   {
/*    */     public Sink(int capacity, int tier) {
/* 54 */       this.energyBuffer = new BasicSink(this, capacity, tier);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Source extends BasicEnergyTe<BasicSource> {
/*    */     public Source(int capacity, int tier) {
/* 60 */       this.energyBuffer = new BasicSource(this, capacity, tier);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public T getEnergyBuffer() {
/* 67 */     return this.energyBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLoad() {
/* 74 */     this.energyBuffer.onLoad();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145843_s() {
/* 79 */     super.func_145843_s();
/*    */     
/* 81 */     this.energyBuffer.invalidate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onChunkUnload() {
/* 86 */     this.energyBuffer.onChunkUnload();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound nbt) {
/* 91 */     super.func_145839_a(nbt);
/*    */     
/* 93 */     this.energyBuffer.readFromNBT(nbt);
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 98 */     return this.energyBuffer.writeToNBT(super.func_189515_b(nbt));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\prefab\BasicEnergyTe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */