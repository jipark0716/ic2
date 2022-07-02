/*     */ package ic2.core.audio;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHitSoundOverride;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.audio.SoundManager;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.sound.PlaySoundEvent;
/*     */ import net.minecraftforge.client.event.sound.SoundLoadEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import paulscode.sound.SoundSystem;
/*     */ import paulscode.sound.SoundSystemConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public final class AudioManagerClient
/*     */   extends AudioManager
/*     */ {
/*     */   public static class WeakObject
/*     */     extends WeakReference<Object>
/*     */   {
/*     */     public WeakObject(Object referent) {
/*  53 */       super(referent);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object object) {
/*  58 */       if (object instanceof WeakObject) {
/*  59 */         return (((WeakObject)object).get() == get());
/*     */       }
/*  61 */       return (get() == object);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  66 */       Object object = get();
/*     */       
/*  68 */       if (object == null) {
/*  69 */         return 0;
/*     */       }
/*  71 */       return object.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  77 */     this.enabled = ConfigUtil.getBool(MainConfig.get(), "audio/enabled");
/*  78 */     this.masterVolume = ConfigUtil.getFloat(MainConfig.get(), "audio/volume");
/*  79 */     this.fadingDistance = ConfigUtil.getFloat(MainConfig.get(), "audio/fadeDistance");
/*  80 */     this.maxSourceCount = ConfigUtil.getInt(MainConfig.get(), "audio/maxSourceCount");
/*     */     
/*  82 */     if (this.maxSourceCount <= 6) {
/*  83 */       IC2.log.info(LogCategory.Audio, "The audio source limit is too low to enable IC2 sounds.");
/*  84 */       this.enabled = false;
/*     */     } 
/*     */     
/*  87 */     if (!this.enabled) {
/*  88 */       IC2.log.debug(LogCategory.Audio, "Sounds disabled.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (this.maxSourceCount < 6) {
/* 116 */       this.enabled = false;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 121 */     IC2.log.debug(LogCategory.Audio, "Using %d audio sources.", new Object[] { Integer.valueOf(this.maxSourceCount) });
/*     */     
/* 123 */     SoundSystemConfig.setNumberStreamingChannels(4);
/* 124 */     SoundSystemConfig.setNumberNormalChannels(this.maxSourceCount - 4);
/*     */ 
/*     */     
/* 127 */     this.soundManagerLoaded = ReflectionUtil.getField(SoundManager.class, boolean.class);
/*     */     
/* 129 */     if (this.soundManagerLoaded == null) {
/* 130 */       IC2.log.warn(LogCategory.Audio, "Can't find SoundManager.loaded, IC2 audio disabled.");
/* 131 */       this.enabled = false;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 136 */     MinecraftForge.EVENT_BUS.register(this);
/* 137 */     MinecraftForge.EVENT_BUS.register(AudioConfigHandler.class);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSoundSetup(SoundLoadEvent event) {
/* 142 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 147 */     for (List<AudioSourceClient> sources : this.objectToAudioSourceMap.values()) {
/* 148 */       for (AudioSourceClient source : sources) {
/* 149 */         if (source.isValid()) {
/* 150 */           source.setInvalid();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 156 */     this.objectToAudioSourceMap.clear();
/*     */     
/* 158 */     Thread thread = this.initThread;
/*     */     
/* 160 */     if (thread != null) {
/* 161 */       thread.interrupt();
/*     */       
/*     */       try {
/* 164 */         thread.join();
/* 165 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */     
/* 168 */     IC2.log.debug(LogCategory.Audio, "IC2 audio starting.");
/*     */     
/* 170 */     this.soundSystem = null;
/* 171 */     this.soundManager = getSoundManager();
/*     */ 
/*     */     
/* 174 */     this.initThread = new Thread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 178 */               while (!Thread.currentThread().isInterrupted()) {
/*     */                 boolean loaded;
/*     */                 
/*     */                 try {
/* 182 */                   loaded = AudioManagerClient.this.soundManagerLoaded.getBoolean(AudioManagerClient.this.soundManager);
/* 183 */                 } catch (Exception e) {
/* 184 */                   throw new RuntimeException(e);
/*     */                 } 
/*     */                 
/* 187 */                 if (loaded) {
/* 188 */                   AudioManagerClient.this.soundSystem = AudioManagerClient.getSoundSystem(AudioManagerClient.this.soundManager);
/*     */                   
/* 190 */                   if (AudioManagerClient.this.soundSystem == null) {
/* 191 */                     IC2.log.warn(LogCategory.Audio, "IC2 audio unavailable.");
/* 192 */                     AudioManagerClient.this.enabled = false; break;
/*     */                   } 
/* 194 */                   IC2.log.debug(LogCategory.Audio, "IC2 audio ready.");
/*     */ 
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */                 
/* 200 */                 Thread.sleep(100L);
/*     */               } 
/* 202 */             } catch (InterruptedException loaded) {
/*     */               InterruptedException interruptedException;
/* 204 */             }  AudioManagerClient.this.initThread = null;
/*     */           }
/*     */         }"IC2 audio init thread");
/*     */     
/* 208 */     this.initThread.setDaemon(true);
/* 209 */     this.initThread.start();
/*     */   }
/*     */   
/*     */   private static SoundManager getSoundManager() {
/* 213 */     SoundHandler handler = Minecraft.func_71410_x().func_147118_V();
/*     */     
/* 215 */     return (SoundManager)ReflectionUtil.getValue(handler, SoundManager.class);
/*     */   }
/*     */   
/*     */   private static SoundSystem getSoundSystem(SoundManager soundManager) {
/*     */     try {
/* 220 */       return (SoundSystem)ReflectionUtil.getValueRecursive(soundManager, SoundSystem.class, false);
/* 221 */     } catch (NoSuchFieldException e) {
/* 222 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 228 */     if (!this.enabled || !valid())
/* 229 */       return;  assert IC2.platform.isRendering();
/*     */     
/* 231 */     IC2.platform.profilerStartSection("UpdateSourceVolume");
/*     */     
/* 233 */     EntityPlayer player = IC2.platform.getPlayerInstance();
/*     */     
/* 235 */     if (player == null) {
/*     */       
/* 237 */       for (List<AudioSourceClient> sources : this.objectToAudioSourceMap.values()) {
/* 238 */         removeSources(sources);
/*     */       }
/*     */       
/* 241 */       this.objectToAudioSourceMap.clear();
/* 242 */       this.singleSoundQueue.clear();
/*     */     } else {
/* 244 */       boolean isPaused = Minecraft.func_71410_x().func_147113_T();
/* 245 */       if (!isPaused && !this.singleSoundQueue.isEmpty()) {
/* 246 */         IC2.platform.profilerStartSection("SoundQueuing");
/*     */         
/* 248 */         for (Iterator<Map.Entry<String, FutureSound>> iterator = this.singleSoundQueue.entrySet().iterator(); iterator.hasNext(); ) {
/* 249 */           Map.Entry<String, FutureSound> entry = iterator.next();
/*     */           
/* 251 */           if (!this.soundSystem.playing(entry.getKey())) {
/* 252 */             ((FutureSound)entry.getValue()).onFinish();
/* 253 */             iterator.remove(); continue;
/* 254 */           }  if (((FutureSound)entry.getValue()).isCancelled()) {
/* 255 */             removeSource(entry.getKey());
/* 256 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */         
/* 260 */         synchronized (SoundSystemConfig.THREAD_SYNC) {
/*     */ 
/*     */           
/* 263 */           IC2.platform.profilerEndSection();
/*     */         } 
/*     */       } 
/*     */       Iterator<Map.Entry<WeakObject, List<AudioSourceClient>>> it;
/* 267 */       for (it = this.objectToAudioSourceMap.entrySet().iterator(); it.hasNext(); ) {
/* 268 */         Map.Entry<WeakObject, List<AudioSourceClient>> entry = it.next();
/*     */         
/* 270 */         if (((WeakObject)entry.getKey()).get() == null) {
/* 271 */           it.remove();
/* 272 */           removeSources(entry.getValue()); continue;
/*     */         } 
/* 274 */         for (AudioSource audioSource : entry.getValue()) {
/* 275 */           if (!this.wasPaused) audioSource.updateVolume(player);
/*     */           
/* 277 */           if (audioSource.getRealVolume() > 0.0F) this.validAudioSources.add(audioSource);
/*     */         
/*     */         } 
/*     */       } 
/*     */       
/* 282 */       IC2.platform.profilerEndStartSection("Culling");
/*     */       
/* 284 */       if (!isPaused) {
/* 285 */         if (this.wasPaused) {
/* 286 */           for (it = (Iterator)this.validAudioSources.iterator(); it.hasNext(); ) { AudioSource source = (AudioSource)it.next();
/* 287 */             source.play(); }
/*     */ 
/*     */           
/* 290 */           this.wasPaused = false;
/*     */         } 
/*     */         
/* 293 */         for (int i = 0; !this.validAudioSources.isEmpty(); i++) {
/* 294 */           AudioSource source = this.validAudioSources.poll();
/*     */           
/* 296 */           if (i < this.maxSourceCount) {
/* 297 */             source.activate();
/*     */           } else {
/* 299 */             source.cull();
/*     */           } 
/*     */         } 
/* 302 */       } else if (isPaused != this.wasPaused) {
/* 303 */         this.wasPaused = true;
/*     */         
/* 305 */         while (!this.validAudioSources.isEmpty()) {
/* 306 */           ((AudioSource)this.validAudioSources.poll()).pause();
/*     */         }
/*     */       } else {
/* 309 */         assert isPaused;
/* 310 */         assert this.wasPaused;
/* 311 */         this.validAudioSources.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     IC2.platform.profilerEndSection();
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioSource createSource(Object obj, String initialSoundFile) {
/* 320 */     return createSource(obj, PositionSpec.Center, initialSoundFile, false, false, getDefaultVolume());
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
/* 325 */     if (!this.enabled) return null; 
/* 326 */     if (!valid()) return null; 
/* 327 */     assert IC2.platform.isRendering();
/*     */     
/* 329 */     String sourceName = getSourceName(this.nextId);
/* 330 */     this.nextId++;
/*     */     
/* 332 */     AudioSourceClient audioSource = new AudioSourceClient(this.soundSystem, sourceName, obj, positionSpec, initialSoundFile, loop, priorized, volume);
/* 333 */     audioSource.setup();
/*     */     
/* 335 */     WeakObject key = new WeakObject(obj);
/* 336 */     List<AudioSourceClient> sources = this.objectToAudioSourceMap.get(key);
/*     */     
/* 338 */     if (sources == null) {
/* 339 */       sources = new ArrayList<>();
/* 340 */       this.objectToAudioSourceMap.put(key, sources);
/*     */     } 
/*     */     
/* 343 */     sources.add(audioSource);
/*     */     
/* 345 */     return audioSource;
/*     */   }
/*     */   
/*     */   static URL getSourceURL(String soundFile) {
/* 349 */     int colonIndex = soundFile.indexOf(':');
/*     */     
/* 351 */     if (colonIndex > -1) {
/* 352 */       return AudioSource.class.getClassLoader().getResource("assets/" + soundFile.substring(0, colonIndex) + "/sounds/" + soundFile.substring(++colonIndex));
/*     */     }
/* 354 */     return AudioSource.class.getClassLoader().getResource("ic2/sounds/" + soundFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSources(Object obj) {
/*     */     WeakObject key;
/* 360 */     if (!valid())
/* 361 */       return;  assert IC2.platform.isRendering();
/*     */ 
/*     */ 
/*     */     
/* 365 */     if (obj instanceof WeakObject) {
/* 366 */       key = (WeakObject)obj;
/*     */     } else {
/* 368 */       key = new WeakObject(obj);
/*     */     } 
/*     */     
/* 371 */     List<AudioSourceClient> sources = this.objectToAudioSourceMap.remove(key);
/* 372 */     if (sources == null)
/*     */       return; 
/* 374 */     removeSources(sources);
/*     */   }
/*     */   
/*     */   private static void removeSources(List<AudioSourceClient> sources) {
/* 378 */     for (AudioSourceClient audioSource : sources) {
/* 379 */       audioSource.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playOnce(Object obj, String soundFile) {
/* 385 */     playOnce(obj, PositionSpec.Center, soundFile, true, getDefaultVolume());
/*     */   }
/*     */ 
/*     */   
/*     */   public String playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {
/* 390 */     if (!this.enabled) return null; 
/* 391 */     if (!valid()) return null; 
/* 392 */     assert IC2.platform.isRendering();
/*     */     
/* 394 */     AudioPosition position = AudioPosition.getFrom(obj, positionSpec);
/* 395 */     if (position == null) return null;
/*     */     
/* 397 */     URL url = getSourceURL(soundFile);
/*     */     
/* 399 */     if (url == null) {
/* 400 */       IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", new Object[] { soundFile });
/* 401 */       return null;
/*     */     } 
/*     */     
/* 404 */     String sourceName = this.soundSystem.quickPlay(priorized, url, soundFile, false, position.x, position.y, position.z, 2, this.fadingDistance * Math.max(volume, 1.0F));
/* 405 */     this.soundSystem.setVolume(sourceName, this.masterVolume * Math.min(volume, 1.0F));
/*     */     
/* 407 */     return sourceName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void chainSource(String source, FutureSound onFinish) {
/* 412 */     this.singleSoundQueue.put(source, onFinish);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSource(String source) {
/* 417 */     if (source != null) {
/* 418 */       this.soundSystem.stop(source);
/* 419 */       this.soundSystem.removeSource(source);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDefaultVolume() {
/* 425 */     return 1.2F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMasterVolume() {
/* 430 */     return this.masterVolume;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean valid() {
/*     */     try {
/* 436 */       return (this.soundSystem != null && this.soundManager != null && this.soundManagerLoaded.getBoolean(this.soundManager));
/* 437 */     } catch (Exception e) {
/* 438 */       throw new RuntimeException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSoundPlayed(PlaySoundEvent event) {
/* 466 */     SoundCategory category = event.getSound().func_184365_d();
/*     */     
/* 468 */     if ((category == SoundCategory.NEUTRAL && event.getName().endsWith(".hit")) || (category == SoundCategory.BLOCKS && event
/* 469 */       .getName().endsWith(".break"))) {
/* 470 */       EntityPlayerSP player = (Minecraft.func_71410_x()).field_71439_g;
/* 471 */       ItemStack stack = player.field_71071_by.func_70448_g();
/*     */       
/* 473 */       if (stack != null && stack.func_77973_b() instanceof IHitSoundOverride) {
/* 474 */         World world = player.func_130014_f_();
/* 475 */         RayTraceResult mop = getMovingObjectPositionFromPlayer(world, (EntityPlayer)player, false);
/* 476 */         BlockPos pos = new BlockPos(event.getSound().func_147649_g(), event.getSound().func_147654_h(), event.getSound().func_147651_i());
/*     */         
/* 478 */         if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK && pos.equals(mop.func_178782_a())) {
/*     */           String replace;
/*     */           
/* 481 */           if (event.getSound().func_184365_d() == SoundCategory.NEUTRAL) {
/* 482 */             replace = ((IHitSoundOverride)stack.func_77973_b()).getHitSoundForBlock(player, world, pos, stack);
/*     */           } else {
/* 484 */             replace = ((IHitSoundOverride)stack.func_77973_b()).getBreakSoundForBlock(player, world, pos, stack);
/*     */           } 
/*     */           
/* 487 */           if (replace != null) {
/* 488 */             event.setResultSound(null);
/*     */             
/* 490 */             if (!replace.isEmpty()) {
/* 491 */               IC2.platform.playSoundSp(replace, 1.0F, 1.0F);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   private static RayTraceResult getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
/* 508 */     float f = playerIn.field_70125_A;
/* 509 */     float f1 = playerIn.field_70177_z;
/* 510 */     double d0 = playerIn.field_70165_t;
/* 511 */     double d1 = playerIn.field_70163_u + playerIn.func_70047_e();
/* 512 */     double d2 = playerIn.field_70161_v;
/* 513 */     Vec3d vec3 = new Vec3d(d0, d1, d2);
/* 514 */     float f2 = MathHelper.func_76134_b(-f1 * 0.017453292F - 3.1415927F);
/* 515 */     float f3 = MathHelper.func_76126_a(-f1 * 0.017453292F - 3.1415927F);
/* 516 */     float f4 = -MathHelper.func_76134_b(-f * 0.017453292F);
/* 517 */     float f5 = MathHelper.func_76126_a(-f * 0.017453292F);
/* 518 */     float f6 = f3 * f4;
/* 519 */     float f7 = f2 * f4;
/* 520 */     double d3 = 5.0D;
/* 521 */     Vec3d vec31 = vec3.func_72441_c(f6 * d3, f5 * d3, f7 * d3);
/* 522 */     return worldIn.func_147447_a(vec3, vec31, useLiquids, !useLiquids, false);
/*     */   }
/*     */   
/*     */   private static String getSourceName(int id) {
/* 526 */     return "asm_snd" + id;
/*     */   }
/*     */   
/* 529 */   public float fadingDistance = 16.0F;
/*     */   
/*     */   private boolean enabled = true;
/*     */   
/*     */   private boolean wasPaused = false;
/* 534 */   private int maxSourceCount = 32;
/* 535 */   private final int streamingSourceCount = 4;
/*     */   
/*     */   private SoundManager soundManager;
/*     */   
/*     */   private Field soundManagerLoaded;
/*     */   private volatile Thread initThread;
/* 541 */   private SoundSystem soundSystem = null;
/* 542 */   float masterVolume = 0.5F;
/*     */   
/* 544 */   private int nextId = 0;
/* 545 */   private final Map<WeakObject, List<AudioSourceClient>> objectToAudioSourceMap = new HashMap<>();
/* 546 */   private final Queue<AudioSource> validAudioSources = new PriorityQueue<>();
/* 547 */   private final Map<String, FutureSound> singleSoundQueue = new HashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\AudioManagerClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */