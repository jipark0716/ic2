/*     */ package ic2.core.block.machine.gui;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import ic2.core.ChunkLoaderLogic;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.container.ContainerChunkLoader;
/*     */ import ic2.core.block.machine.tileentity.TileEntityChunkloader;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.Ic2BlockPos;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiChunkLoader extends GuiIC2<ContainerChunkLoader> {
/*  29 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIChunkLoader.png");
/*     */   
/*     */   public GuiChunkLoader(ContainerChunkLoader container) {
/*  32 */     super((ContainerBase)container, 250);
/*  33 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 125, (TileEntityBlock)container.base));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getTexture() {
/*  38 */     return background;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/*  43 */     ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
/*  44 */     ImmutableSet immutableSet = ((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).getLoadedChunks();
/*  45 */     int amountLoadedChunks = 0;
/*  46 */     for (int i = -4; i <= 4; i++) {
/*  47 */       for (int j = -4; j <= 4; j++) {
/*  48 */         ChunkPos currentChunk = new ChunkPos(mainChunk.field_77276_a + i, mainChunk.field_77275_b + j);
/*     */         
/*  50 */         int xpos = -this.field_147003_i + 89 + 16 * i;
/*  51 */         int ypos = -this.field_147009_r + 80 + 16 * j;
/*     */         
/*  53 */         drawChunkAt(xpos, ypos, currentChunk);
/*  54 */         if (immutableSet.contains(currentChunk)) {
/*     */           
/*  56 */           drawColoredRect(xpos, ypos, 16, 16, 805371648);
/*  57 */           amountLoadedChunks++;
/*     */         }
/*     */         else {
/*     */           
/*  61 */           drawColoredRect(xpos, ypos, 16, 16, 822018048);
/*     */         } 
/*     */       } 
/*     */     } 
/*  65 */     GlStateManager.func_179141_d();
/*  66 */     this.field_146289_q.func_78279_b(amountLoadedChunks + " / " + ChunkLoaderLogic.getInstance().getMaxChunksPerTicket(), 8, 16, 15, 4210752);
/*  67 */     super.drawForegroundLayer(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   private void drawChunkAt(int x, int y, ChunkPos chunkPos) {
/*  71 */     World world = ((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_145831_w();
/*  72 */     Chunk chunk = world.func_72964_e(chunkPos.field_77276_a, chunkPos.field_77275_b);
/*  73 */     Ic2BlockPos worldPos = new Ic2BlockPos();
/*     */     
/*  75 */     for (int cx = 0; cx < 16; cx++) {
/*  76 */       worldPos.setX(chunkPos.field_77276_a << 4 | cx);
/*     */       
/*  78 */       for (int cz = 0; cz < 16; cz++) {
/*  79 */         worldPos.setZ(chunkPos.field_77275_b << 4 | cz);
/*  80 */         worldPos.setY(chunk.func_76611_b(cx, cz));
/*     */         
/*  82 */         IBlockState state = chunk.func_177435_g((BlockPos)worldPos);
/*     */ 
/*     */         
/*  85 */         if (state.func_177230_c().isAir(state, (IBlockAccess)world, (BlockPos)worldPos)) {
/*  86 */           worldPos.moveDown();
/*  87 */           state = chunk.func_177435_g((BlockPos)worldPos);
/*     */         } 
/*     */         
/*  90 */         drawColoredRect(x + cx, y + cz, 1, 1, getColor(state, world, (BlockPos)worldPos));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getColor(IBlockState state, World world, BlockPos pos) {
/*  96 */     MapColor color = state.func_185909_g((IBlockAccess)world, pos);
/*  97 */     if (color == null) {
/*  98 */       IC2.log.error(LogCategory.General, "BlockState " + state + " does not have a MapColor set. Please report to the mod author of that mod.");
/*  99 */       return 0;
/*     */     } 
/* 101 */     return color.field_76291_p | 0xFF000000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 107 */     if (mouseButton == 0) {
/*     */ 
/*     */       
/* 110 */       ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
/*     */       
/* 112 */       for (int i = -4; i <= 4; i++) {
/* 113 */         for (int j = -4; j <= 4; j++) {
/* 114 */           if (mouseX - this.field_147003_i > 89 + 16 * i && mouseX - this.field_147003_i <= 89 + 16 * i + 16 && mouseY - this.field_147009_r > 80 + 16 * j && mouseY - this.field_147009_r <= 80 + 16 * j + 16) {
/*     */             
/* 116 */             changeChunk(new ChunkPos(mainChunk.field_77276_a + i, mainChunk.field_77275_b + j));
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */   }
/*     */   
/*     */   private void changeChunk(ChunkPos chunk) {
/* 126 */     ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(((TileEntityChunkloader)((ContainerChunkLoader)this.container).base).func_174877_v());
/* 127 */     ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerChunkLoader)this.container).base, chunk.field_77276_a - mainChunk.field_77276_a + 8 & 0xF | (chunk.field_77275_b - mainChunk.field_77275_b + 8 & 0xF) << 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */