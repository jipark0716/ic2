/*    */ package ic2.core.block.comp;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.network.GrowingBuffer;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import java.io.DataInput;
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.common.capabilities.Capability;
/*    */ 
/*    */ public abstract class TileEntityComponent {
/*    */   protected final TileEntityBlock parent;
/*    */   
/*    */   public TileEntityComponent(TileEntityBlock parent) {
/* 22 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public TileEntityBlock getParent() {
/* 26 */     return this.parent;
/*    */   }
/*    */   public void readFromNbt(NBTTagCompound nbt) {}
/*    */   
/*    */   public NBTTagCompound writeToNbt() {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLoaded() {}
/*    */ 
/*    */   
/*    */   public void onUnloaded() {}
/*    */   
/*    */   public boolean enableWorldTick() {
/* 41 */     return false;
/*    */   } public void onNeighborChange(Block srcBlock, BlockPos srcPos) {} public void onContainerUpdate(EntityPlayerMP player) {}
/*    */   public void onNetworkUpdate(DataInput is) throws IOException {}
/*    */   public void onWorldTick() {}
/*    */   protected void setNetworkUpdate(EntityPlayerMP player, GrowingBuffer data) {
/* 46 */     ((NetworkManager)IC2.network.get(true)).sendComponentUpdate(this.parent, Components.getId((Class)getClass()), player, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<? extends Capability<?>> getProvidedCapabilities(EnumFacing side) {
/* 54 */     return Collections.emptySet();
/*    */   }
/*    */   
/*    */   public <T> T getCapability(Capability<T> cap, EnumFacing side) {
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\TileEntityComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */