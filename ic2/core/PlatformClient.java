/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.BlockTileEntity;
/*     */ import ic2.core.block.EntityDynamite;
/*     */ import ic2.core.block.EntityIC2Explosive;
/*     */ import ic2.core.block.KineticGeneratorRenderer;
/*     */ import ic2.core.block.RenderBlockWall;
/*     */ import ic2.core.block.RenderExplosiveBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.beam.EntityParticle;
/*     */ import ic2.core.block.beam.RenderBeam;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
/*     */ import ic2.core.block.personal.TileEntityPersonalChest;
/*     */ import ic2.core.block.personal.TileEntityPersonalChestRenderer;
/*     */ import ic2.core.block.transport.items.PipeModel;
/*     */ import ic2.core.block.transport.items.PipeType;
/*     */ import ic2.core.block.wiring.CableModel;
/*     */ import ic2.core.command.CommandIc2c;
/*     */ import ic2.core.crop.CropModel;
/*     */ import ic2.core.gui.GlTexture;
/*     */ import ic2.core.item.ElectricItemTooltipHandler;
/*     */ import ic2.core.item.EntityIC2Boat;
/*     */ import ic2.core.item.FluidCellModel;
/*     */ import ic2.core.item.RenderIC2Boat;
/*     */ import ic2.core.item.block.ItemFluidPipe;
/*     */ import ic2.core.item.logistics.ItemPumpCover;
/*     */ import ic2.core.item.logistics.PumpCoverType;
/*     */ import ic2.core.item.tool.EntityMiningLaser;
/*     */ import ic2.core.item.tool.RenderCrossed;
/*     */ import ic2.core.item.tool.RenderObscurator;
/*     */ import ic2.core.model.IReloadableModel;
/*     */ import ic2.core.model.Ic2ModelLoader;
/*     */ import ic2.core.network.RpcHandler;
/*     */ import ic2.core.profile.ProfileManager;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.IBlockModelProvider;
/*     */ import ic2.core.ref.IFluidModelProvider;
/*     */ import ic2.core.ref.IItemModelProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.MetaTeBlock;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.File;
/*     */ import java.util.Objects;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.color.IBlockColor;
/*     */ import net.minecraft.client.renderer.color.IItemColor;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderSnowball;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.ClientCommandHandler;
/*     */ import net.minecraftforge.client.model.ICustomModelLoader;
/*     */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fml.client.SplashProgress;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.LoaderState;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class PlatformClient
/*     */   extends Platform
/*     */ {
/*     */   public boolean isRendering() {
/*  98 */     return Minecraft.func_71410_x().func_152345_ab();
/*     */   }
/*     */ 
/*     */   
/*     */   public void preInit() {
/* 103 */     ClientCommandHandler.instance.func_71560_a((ICommand)new CommandIc2c());
/*     */ 
/*     */     
/* 106 */     for (BlockName name : BlockName.values) {
/* 107 */       if (!name.hasInstance()) {
/* 108 */         IC2.log.warn(LogCategory.Block, "The block " + name + " is not initialized.");
/*     */       }
/*     */       else {
/*     */         
/* 112 */         ((IBlockModelProvider)name.getInstance()).registerModels(name);
/*     */       } 
/*     */     } 
/* 115 */     for (BlockTileEntity block : TeBlockRegistry.getAllBlocks()) {
/* 116 */       if (!block.isIC2()) {
/* 117 */         block.registerModels(null);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 122 */     for (ItemName name : ItemName.values) {
/* 123 */       if (!name.hasInstance()) {
/* 124 */         IC2.log.warn(LogCategory.Item, "The item " + name + " is not initialized.");
/*     */       }
/*     */       else {
/*     */         
/* 128 */         ((IItemModelProvider)name.getInstance()).registerModels(name);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     for (FluidName name : FluidName.values) {
/* 133 */       if (!name.hasInstance()) {
/* 134 */         IC2.log.warn(LogCategory.Block, "The fluid " + name + " is not initialized.");
/*     */       }
/*     */       else {
/*     */         
/* 138 */         Fluid provider = name.getInstance();
/*     */         
/* 140 */         if (provider instanceof IFluidModelProvider) {
/* 141 */           ((IFluidModelProvider)provider).registerModels(name);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     Ic2ModelLoader loader = new Ic2ModelLoader();
/*     */     
/* 148 */     loader.register("models/block/cf/wall", (IReloadableModel)new RenderBlockWall());
/* 149 */     loader.register("models/block/crop/crop", (IReloadableModel)new CropModel());
/* 150 */     loader.register("models/block/wiring/cable", (IReloadableModel)new CableModel());
/* 151 */     loader.register("models/block/transport/item_pipe", (IReloadableModel)new PipeModel());
/*     */     
/* 153 */     loader.register("models/item/cell/fluid_cell", (IReloadableModel)new FluidCellModel());
/* 154 */     loader.register("models/item/tool/electric/obscurator", (IReloadableModel)new RenderObscurator());
/*     */     
/* 156 */     ModelLoaderRegistry.registerLoader((ICustomModelLoader)loader);
/*     */ 
/*     */     
/* 159 */     ProfileManager.doTextureChanges();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPersonalChest.class, (TileEntitySpecialRenderer)new TileEntityPersonalChestRenderer());
/* 169 */     KineticGeneratorRenderer<TileEntityInventory> kineticRenderer = new KineticGeneratorRenderer();
/* 170 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindKineticGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
/* 171 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterKineticGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
/* 172 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
/* 173 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     RenderingRegistry.registerEntityRenderingHandler(EntityIC2Explosive.class, new IRenderFactory<EntityIC2Explosive>()
/*     */         {
/*     */           public Render<EntityIC2Explosive> createRenderFor(RenderManager manager) {
/* 181 */             return (Render<EntityIC2Explosive>)new RenderExplosiveBlock(manager);
/*     */           }
/*     */         });
/* 184 */     RenderingRegistry.registerEntityRenderingHandler(EntityMiningLaser.class, new IRenderFactory<EntityMiningLaser>()
/*     */         {
/*     */           public Render<EntityMiningLaser> createRenderFor(RenderManager manager) {
/* 187 */             return (Render<EntityMiningLaser>)new RenderCrossed(manager, new ResourceLocation("ic2", "textures/models/laser.png"));
/*     */           }
/*     */         });
/* 190 */     RenderingRegistry.registerEntityRenderingHandler(EntityIC2Boat.class, new IRenderFactory<EntityBoat>()
/*     */         {
/*     */           public Render<EntityBoat> createRenderFor(RenderManager manager) {
/* 193 */             return (Render<EntityBoat>)new RenderIC2Boat(manager);
/*     */           }
/*     */         });
/* 196 */     RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new IRenderFactory<EntityDynamite>()
/*     */         {
/*     */           public Render<EntityDynamite> createRenderFor(RenderManager manager) {
/* 199 */             return (Render<EntityDynamite>)new RenderSnowball(manager, ItemName.dynamite.getInstance(), PlatformClient.this.mc.func_175599_af());
/*     */           }
/*     */         });
/*     */     
/* 203 */     if (Util.inDev()) RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, manager -> new RenderBeam(manager));
/*     */ 
/*     */     
/* 206 */     GlTexture.init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayError(String error, Object... args) {
/* 213 */     if (!this.mc.func_152345_ab()) {
/* 214 */       super.displayError(error, args);
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     if (args.length > 0) error = String.format(error, args);
/*     */     
/* 220 */     error = "IndustrialCraft 2 Error\n\n" + error;
/* 221 */     String dialogError = error.replaceAll("([^\n]{80,}?) ", "$1\n");
/* 222 */     error = error.replace("\n", System.getProperty("line.separator"));
/* 223 */     dialogError = dialogError.replace("\n", System.getProperty("line.separator"));
/*     */     
/* 225 */     IC2.log.error(LogCategory.General, "%s", new Object[] { error });
/*     */     
/* 227 */     this.mc.func_71364_i();
/*     */     
/*     */     try {
/* 230 */       if (!Loader.instance().hasReachedState(LoaderState.AVAILABLE)) {
/* 231 */         SplashProgress.finish();
/*     */       }
/* 233 */       Display.destroy();
/*     */       
/* 235 */       JFrame frame = new JFrame("IndustrialCraft 2 Error");
/* 236 */       frame.setUndecorated(true);
/* 237 */       frame.setVisible(true);
/* 238 */       frame.setLocationRelativeTo(null);
/*     */       
/* 240 */       JOptionPane.showMessageDialog(frame, dialogError, "IndustrialCraft 2 Error", 0);
/* 241 */     } catch (Throwable t) {
/* 242 */       IC2.log.error(LogCategory.General, t, "Exception caught while showing an error.");
/*     */     } 
/*     */     
/* 245 */     Util.exit(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayerInstance() {
/* 250 */     return (EntityPlayer)this.mc.field_71439_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld(int dimId) {
/* 255 */     if (isSimulating()) {
/* 256 */       return super.getWorld(dimId);
/*     */     }
/*     */     
/* 259 */     WorldClient worldClient = this.mc.field_71441_e;
/* 260 */     return (((World)worldClient).field_73011_w.getDimension() == dimId) ? (World)worldClient : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getPlayerWorld() {
/* 265 */     return (World)this.mc.field_71441_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public void messagePlayer(EntityPlayer player, String message, Object... args) {
/* 270 */     if (args.length > 0) {
/* 271 */       this.mc.field_71456_v.func_146158_b().func_146227_a((ITextComponent)new TextComponentTranslation(message, (Object[])getMessageComponents(args)));
/*     */     } else {
/* 273 */       this.mc.field_71456_v.func_146158_b().func_146227_a((ITextComponent)new TextComponentString(message));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean launchGuiClient(EntityPlayer player, IHasGui inventory, boolean isAdmin) {
/* 279 */     this.mc.func_147108_a(inventory.getGui(player, isAdmin));
/*     */     
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void profilerStartSection(String section) {
/* 286 */     if (isRendering()) { this.mc.field_71424_I.func_76320_a(section); }
/* 287 */     else { super.profilerStartSection(section); }
/*     */   
/*     */   }
/*     */   
/*     */   public void profilerEndSection() {
/* 292 */     if (isRendering()) { this.mc.field_71424_I.func_76319_b(); }
/* 293 */     else { super.profilerEndSection(); }
/*     */   
/*     */   }
/*     */   
/*     */   public void profilerEndStartSection(String section) {
/* 298 */     if (isRendering()) { this.mc.field_71424_I.func_76318_c(section); }
/* 299 */     else { super.profilerEndStartSection(section); }
/*     */   
/*     */   }
/*     */   
/*     */   public File getMinecraftDir() {
/* 304 */     return this.mc.field_71412_D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSoundSp(String sound, float f, float g) {
/* 309 */     IC2.audioManager.playOnce(getPlayerInstance(), PositionSpec.Hand, sound, true, IC2.audioManager.getDefaultVolume());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostInit() {
/* 314 */     MinecraftForge.EVENT_BUS.register(new GuiOverlayer(this.mc));
/* 315 */     new RpcHandler();
/* 316 */     new ElectricItemTooltipHandler();
/*     */     
/* 318 */     Block leaves = BlockName.leaves.getInstance();
/* 319 */     this.mc.func_184125_al().func_186722_a(new IBlockColor()
/*     */         {
/*     */           public int func_186720_a(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
/* 322 */             return 6723908;
/*     */           }
/*     */         },  new Block[] { leaves });
/* 325 */     this.mc.getItemColors().func_186731_a(new IItemColor()
/*     */         {
/*     */           public int func_186726_a(ItemStack stack, int tintIndex) {
/* 328 */             return 6723908;
/*     */           }
/*     */         },  new Block[] { leaves });
/*     */     
/* 332 */     this.mc.getItemColors().func_186730_a(new IItemColor()
/*     */         {
/*     */           public int func_186726_a(ItemStack stack, int tintIndex) {
/* 335 */             return (tintIndex > 0) ? -1 : ((ItemArmor)stack.func_77973_b()).func_82814_b(stack);
/*     */           }
/* 337 */         },  new Item[] { ItemName.quantum_helmet.getInstance(), ItemName.quantum_chestplate.getInstance(), ItemName.quantum_leggings.getInstance(), ItemName.quantum_boots.getInstance() });
/*     */     
/* 339 */     this.mc.getItemColors().func_186730_a(new IItemColor()
/*     */         {
/*     */           public int func_186726_a(ItemStack stack, int tintIndex) {
/* 342 */             PipeType type = ItemFluidPipe.getPipeType(stack);
/* 343 */             return (type.red & 0xFF) << 16 | (type.green & 0xFF) << 8 | type.blue & 0xFF;
/*     */           }
/* 345 */         },  new Item[] { ItemName.pipe.getInstance() });
/*     */     
/* 347 */     this.mc.func_184125_al().func_186722_a(new IBlockColor()
/*     */         {
/*     */           public int func_186720_a(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
/* 350 */             String variant = ((MetaTeBlock)state.func_177229_b(((BlockTileEntity)state.func_177230_c()).typeProperty)).teBlock.getName();
/* 351 */             if (variant.endsWith("_storage_box")) {
/* 352 */               switch (variant) {
/*     */                 case "wooden_storage_box":
/* 354 */                   return 10454093;
/*     */                 case "iron_storage_box":
/* 356 */                   return 13158600;
/*     */                 case "bronze_storage_box":
/* 358 */                   return 16744448;
/*     */                 case "steel_storage_box":
/* 360 */                   return 8421504;
/*     */               } 
/* 362 */               return 16777215;
/*     */             } 
/*     */             
/* 365 */             return 16777215;
/*     */           }
/* 367 */         },  new Block[] { BlockName.te.getInstance() });
/*     */     
/* 369 */     this.mc.getItemColors().func_186731_a(new IItemColor()
/*     */         {
/*     */           public int func_186726_a(ItemStack stack, int tintIndex) {
/* 372 */             String variant = Objects.<String>requireNonNull(BlockName.te.getVariant(stack));
/* 373 */             if (variant.endsWith("_storage_box")) {
/* 374 */               switch (variant) {
/*     */                 case "wooden_storage_box":
/* 376 */                   return 10454093;
/*     */                 case "iron_storage_box":
/* 378 */                   return 13158600;
/*     */                 case "bronze_storage_box":
/* 380 */                   return 16744448;
/*     */                 case "steel_storage_box":
/* 382 */                   return 8421504;
/*     */               } 
/* 384 */               return 16777215;
/*     */             } 
/*     */             
/* 387 */             return 16777215;
/*     */           }
/* 389 */         },  new Block[] { BlockName.te.getInstance() });
/*     */     
/* 391 */     this.mc.getItemColors().func_186730_a((stack, tintIndex) -> { PumpCoverType type = (PumpCoverType)((ItemPumpCover)stack.func_77973_b()).getType(stack); return (tintIndex == 1) ? type.color : 16777215; }new Item[] { ItemName.cover
/*     */ 
/*     */           
/* 394 */           .getInstance() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestTick(boolean simulating, Runnable runnable) {
/* 401 */     if (simulating) {
/* 402 */       super.requestTick(simulating, runnable);
/*     */     } else {
/* 404 */       this.mc.func_152344_a(runnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tint) {
/* 410 */     return this.mc.func_184125_al().func_186724_a(state, world, pos, tint);
/*     */   }
/*     */   
/* 413 */   private final Minecraft mc = Minecraft.func_71410_x();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\PlatformClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */