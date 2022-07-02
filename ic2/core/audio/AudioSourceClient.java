/*     */ package ic2.core.audio;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URL;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import paulscode.sound.SoundSystem;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ final class AudioSourceClient
/*     */   extends AudioSource
/*     */   implements Comparable<AudioSourceClient>
/*     */ {
/*     */   private final SoundSystem soundSystem;
/*     */   private final String sourceName;
/*     */   private boolean valid;
/*     */   private boolean culled;
/*     */   private final String initialSoundFile;
/*     */   private final boolean loop;
/*     */   private final boolean prioritized;
/*     */   private final Reference<Object> obj;
/*     */   private AudioPosition position;
/*     */   private final PositionSpec positionSpec;
/*     */   private float configuredVolume;
/*     */   private float realVolume;
/*     */   private boolean isPlaying;
/*     */   
/*     */   AudioSourceClient(SoundSystem soundSystem, String sourceName, Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean prioritized, float volume) {
/* 280 */     this.valid = false;
/* 281 */     this.culled = false;
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
/* 295 */     this.isPlaying = false;
/*     */     this.soundSystem = soundSystem;
/*     */     this.sourceName = sourceName;
/*     */     this.initialSoundFile = initialSoundFile;
/*     */     this.loop = loop;
/*     */     this.prioritized = prioritized;
/*     */     this.obj = new WeakReference(obj);
/*     */     this.position = AudioPosition.getFrom(obj, positionSpec);
/*     */     this.positionSpec = positionSpec;
/*     */     this.configuredVolume = volume;
/*     */   }
/*     */   
/*     */   void setup() {
/*     */     if (this.valid)
/*     */       throw new IllegalStateException("already initialized"); 
/*     */     URL url = AudioManagerClient.getSourceURL(this.initialSoundFile);
/*     */     if (url == null) {
/*     */       IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", new Object[] { this.initialSoundFile });
/*     */       return;
/*     */     } 
/*     */     this.soundSystem.newSource(this.prioritized, this.sourceName, url, this.initialSoundFile, this.loop, this.position.x, this.position.y, this.position.z, 0, ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0F));
/*     */     this.valid = true;
/*     */     setVolume(this.configuredVolume);
/*     */   }
/*     */   
/*     */   public int compareTo(AudioSourceClient x) {
/*     */     if (this.culled)
/*     */       return (int)((this.realVolume * 0.9F - x.realVolume) * 128.0F); 
/*     */     return (int)((this.realVolume - x.realVolume) * 128.0F);
/*     */   }
/*     */   
/*     */   public void remove() {
/*     */     if (!check())
/*     */       return; 
/*     */     stop();
/*     */     this.soundSystem.removeSource(this.sourceName);
/*     */     setInvalid();
/*     */   }
/*     */   
/*     */   boolean isValid() {
/*     */     return this.valid;
/*     */   }
/*     */   
/*     */   void setInvalid() {
/*     */     this.valid = false;
/*     */   }
/*     */   
/*     */   public void play() {
/*     */     if (!check())
/*     */       return; 
/*     */     if (this.isPlaying)
/*     */       return; 
/*     */     this.isPlaying = true;
/*     */     if (this.culled)
/*     */       return; 
/*     */     this.soundSystem.play(this.sourceName);
/*     */   }
/*     */   
/*     */   public void pause() {
/*     */     if (!check())
/*     */       return; 
/*     */     if (!this.isPlaying || this.culled)
/*     */       return; 
/*     */     this.isPlaying = false;
/*     */     this.soundSystem.pause(this.sourceName);
/*     */   }
/*     */   
/*     */   public void stop() {
/*     */     if (!check() || !this.isPlaying)
/*     */       return; 
/*     */     this.isPlaying = false;
/*     */     if (this.culled)
/*     */       return; 
/*     */     this.soundSystem.stop(this.sourceName);
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     if (!check())
/*     */       return; 
/*     */     if (!this.isPlaying || this.culled)
/*     */       return; 
/*     */     this.soundSystem.flush(this.sourceName);
/*     */   }
/*     */   
/*     */   public void cull() {
/*     */     if (!check() || this.culled)
/*     */       return; 
/*     */     this.soundSystem.cull(this.sourceName);
/*     */     this.culled = true;
/*     */   }
/*     */   
/*     */   public void activate() {
/*     */     if (!check() || !this.culled)
/*     */       return; 
/*     */     this.soundSystem.activate(this.sourceName);
/*     */     this.culled = false;
/*     */     if (this.isPlaying) {
/*     */       this.isPlaying = false;
/*     */       play();
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getVolume() {
/*     */     if (!check())
/*     */       return 0.0F; 
/*     */     return this.soundSystem.getVolume(this.sourceName);
/*     */   }
/*     */   
/*     */   public float getRealVolume() {
/*     */     return this.realVolume;
/*     */   }
/*     */   
/*     */   public void setVolume(float volume) {
/*     */     if (!check())
/*     */       return; 
/*     */     this.configuredVolume = volume;
/*     */     this.soundSystem.setVolume(this.sourceName, 0.001F);
/*     */   }
/*     */   
/*     */   public void setPitch(float pitch) {
/*     */     if (!check())
/*     */       return; 
/*     */     this.soundSystem.setPitch(this.sourceName, pitch);
/*     */   }
/*     */   
/*     */   public void updatePosition() {
/*     */     if (!check())
/*     */       return; 
/*     */     this.position = AudioPosition.getFrom(this.obj.get(), this.positionSpec);
/*     */     if (this.position == null)
/*     */       return; 
/*     */     this.soundSystem.setPosition(this.sourceName, this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */   
/*     */   public void updateVolume(EntityPlayer player) {
/*     */     float distance;
/*     */     if (!check() || !this.isPlaying) {
/*     */       this.realVolume = 0.0F;
/*     */       return;
/*     */     } 
/*     */     float maxDistance = ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0F);
/*     */     float rolloffFactor = 1.0F;
/*     */     float referenceDistance = 1.0F;
/*     */     World world = player.func_130014_f_();
/*     */     float x = (float)player.field_70165_t;
/*     */     float y = (float)player.field_70163_u;
/*     */     float z = (float)player.field_70161_v;
/*     */     if (this.position != null && this.position.getWorld() == world) {
/*     */       float deltaX = this.position.x - x;
/*     */       float deltaY = this.position.y - y;
/*     */       float deltaZ = this.position.z - z;
/*     */       distance = (float)Math.sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));
/*     */     } else {
/*     */       distance = Float.POSITIVE_INFINITY;
/*     */     } 
/*     */     if (distance > maxDistance) {
/*     */       this.realVolume = 0.0F;
/*     */       cull();
/*     */       return;
/*     */     } 
/*     */     if (distance < referenceDistance)
/*     */       distance = referenceDistance; 
/*     */     float gain = 1.0F - rolloffFactor * (distance - referenceDistance) / (maxDistance - referenceDistance);
/*     */     float newRealVolume = gain * this.configuredVolume * IC2.audioManager.getMasterVolume();
/*     */     float dx = (this.position.x - x) / distance;
/*     */     float dy = (this.position.y - y) / distance;
/*     */     float dz = (this.position.z - z) / distance;
/*     */     if (newRealVolume > 0.1D)
/*     */       for (int i = 0; i < distance; i++) {
/*     */         BlockPos pos = new BlockPos(Util.roundToNegInf(x), Util.roundToNegInf(y), Util.roundToNegInf(z));
/*     */         IBlockState state = world.func_180495_p(pos);
/*     */         Block block = state.func_177230_c();
/*     */         if (!block.isAir(state, (IBlockAccess)world, pos))
/*     */           if (block.isNormalCube(state, (IBlockAccess)world, pos)) {
/*     */             newRealVolume *= 0.6F;
/*     */           } else {
/*     */             newRealVolume *= 0.8F;
/*     */           }  
/*     */         x += dx;
/*     */         y += dy;
/*     */         z += dz;
/*     */       }  
/*     */     if (Math.abs(this.realVolume / newRealVolume - 1.0F) > 0.06D)
/*     */       this.soundSystem.setVolume(this.sourceName, IC2.audioManager.getMasterVolume() * Math.min(newRealVolume, 1.0F)); 
/*     */     this.realVolume = newRealVolume;
/*     */   }
/*     */   
/*     */   private boolean check() {
/*     */     if (this.valid && IC2.audioManager.valid())
/*     */       return true; 
/*     */     this.valid = false;
/*     */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\AudioSourceClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */