/*     */ package ic2.core.network;
/*     */ 
/*     */ import ic2.api.network.INetworkUpdateListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TeUpdate
/*     */ {
/*     */   public static void send(WorldData worldData, NetworkManager network) throws IOException {
/*  29 */     if (worldData.tesToUpdate.isEmpty())
/*     */       return; 
/*  31 */     Map<EntityPlayerMP, GrowingBuffer> buffers = new IdentityHashMap<>();
/*  32 */     List<EntityPlayerMP> playersInRange = new ArrayList<>();
/*  33 */     GrowingBuffer commonBuffer = new GrowingBuffer();
/*     */     
/*  35 */     for (Map.Entry<TileEntity, TeUpdateDataServer> entry : (Iterable<Map.Entry<TileEntity, TeUpdateDataServer>>)worldData.tesToUpdate.entrySet()) {
/*  36 */       TileEntity te = entry.getKey();
/*     */       
/*  38 */       NetworkManager.getPlayersInRange(te.func_145831_w(), te.func_174877_v(), playersInRange);
/*  39 */       if (playersInRange.isEmpty())
/*     */         continue; 
/*  41 */       TeUpdateDataServer updateData = entry.getValue();
/*     */ 
/*     */       
/*  44 */       DataEncoder.encode(commonBuffer, te.func_174877_v(), false);
/*  45 */       commonBuffer.mark();
/*  46 */       commonBuffer.writeShort(0);
/*     */       
/*  48 */       for (String field : updateData.getGlobalFields()) {
/*  49 */         NetworkManager.writeFieldData(te, field, commonBuffer);
/*     */       }
/*     */       
/*  52 */       commonBuffer.flip();
/*     */       
/*  54 */       for (EntityPlayerMP player : playersInRange) {
/*  55 */         Collection<String> playerFields = updateData.getPlayerFields(player);
/*  56 */         int fieldCount = updateData.getGlobalFields().size() + playerFields.size();
/*  57 */         if (fieldCount == 0)
/*  58 */           continue;  if (fieldCount > 65535) throw new RuntimeException("too many fields for " + te + ": " + fieldCount);
/*     */ 
/*     */         
/*  61 */         commonBuffer.reset();
/*  62 */         commonBuffer.writeShort(fieldCount);
/*  63 */         commonBuffer.rewind();
/*     */ 
/*     */         
/*  66 */         GrowingBuffer playerBuffer = buffers.get(player);
/*     */         
/*  68 */         if (playerBuffer == null) {
/*  69 */           playerBuffer = new GrowingBuffer(0);
/*  70 */           buffers.put(player, playerBuffer);
/*     */           
/*  72 */           playerBuffer.writeInt((player.func_130014_f_()).field_73011_w.getDimension());
/*     */         } 
/*     */         
/*  75 */         commonBuffer.writeTo(playerBuffer);
/*  76 */         commonBuffer.rewind();
/*     */         
/*  78 */         for (String field : playerFields) {
/*  79 */           NetworkManager.writeFieldData(te, field, playerBuffer);
/*     */         }
/*     */       } 
/*     */       
/*  83 */       commonBuffer.clear();
/*  84 */       playersInRange.clear();
/*     */     } 
/*     */     
/*  87 */     worldData.tesToUpdate.clear();
/*     */     
/*  89 */     for (Map.Entry<EntityPlayerMP, GrowingBuffer> entry : buffers.entrySet()) {
/*  90 */       EntityPlayerMP player = entry.getKey();
/*  91 */       GrowingBuffer playerBuffer = entry.getValue();
/*     */       
/*  93 */       playerBuffer.flip();
/*  94 */       network.sendLargePacket(player, 0, playerBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void receive(GrowingBuffer buffer) throws IOException {
/*  99 */     final int dimensionId = buffer.readInt();
/* 100 */     final TeUpdateDataClient updateData = new TeUpdateDataClient();
/*     */     
/* 102 */     while (buffer.hasAvailable()) {
/* 103 */       BlockPos pos = DataEncoder.<BlockPos>decode(buffer, BlockPos.class);
/* 104 */       int fieldCount = buffer.readUnsignedShort();
/*     */       
/* 106 */       TeUpdateDataClient.TeData teData = updateData.addTe(pos, fieldCount);
/*     */       
/* 108 */       for (int i = 0; i < fieldCount; i++) {
/* 109 */         String fieldName = buffer.readString();
/* 110 */         Object value = DataEncoder.decode(buffer);
/*     */         
/* 112 */         if (fieldName.equals("teBlk")) {
/* 113 */           String name = (String)value;
/*     */           
/* 115 */           if (name.startsWith("Old-")) {
/*     */ 
/*     */ 
/*     */             
/* 119 */             teData.teClass = TeBlockRegistry.getOld(name);
/*     */           } else {
/* 121 */             teData.teClass = TeBlockRegistry.get(name).getTeClass();
/*     */           } 
/*     */         } else {
/* 124 */           teData.addField(fieldName, value);
/*     */         } 
/*     */       } 
/*     */       
/* 128 */       if (teData.teClass != null) {
/* 129 */         for (TeUpdateDataClient.FieldData fieldData : teData.getFields()) {
/* 130 */           fieldData.field = ReflectionUtil.getFieldRecursive(teData.teClass, fieldData.name);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 135 */     if (debug) printDebugOutput(dimensionId, updateData);
/*     */     
/* 137 */     IC2.platform.requestTick(false, new Runnable()
/*     */         {
/*     */           public void run() {
/* 140 */             World world = IC2.platform.getPlayerWorld();
/* 141 */             if (world == null || world.field_73011_w.getDimension() != dimensionId)
/*     */               return; 
/* 143 */             for (TeUpdateDataClient.TeData update : updateData.getTes()) {
/*     */               try {
/* 145 */                 TeUpdate.apply(update, world);
/* 146 */               } catch (Throwable t) {
/* 147 */                 IC2.log.warn(LogCategory.Network, t, "TE update at %s failed.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos) });
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void printDebugOutput(int dimensionId, TeUpdateDataClient data) {
/* 155 */     StringBuilder out = new StringBuilder();
/*     */     
/* 157 */     out.append("dimension: ");
/* 158 */     out.append(dimensionId);
/* 159 */     out.append(", ");
/* 160 */     out.append(data.getTes().size());
/* 161 */     out.append(" tes:\n");
/*     */     
/* 163 */     for (TeUpdateDataClient.TeData te : data.getTes()) {
/* 164 */       out.append("  pos: ");
/* 165 */       out.append(te.pos.func_177958_n());
/* 166 */       out.append('/');
/* 167 */       out.append(te.pos.func_177956_o());
/* 168 */       out.append('/');
/* 169 */       out.append(te.pos.func_177952_p());
/* 170 */       out.append(", ");
/* 171 */       out.append(te.getFields().size());
/* 172 */       out.append(" fields:\n");
/*     */       
/* 174 */       for (TeUpdateDataClient.FieldData field : te.getFields()) {
/* 175 */         out.append("    ");
/* 176 */         out.append(field.name);
/* 177 */         out.append(" = ");
/* 178 */         out.append(field.value);
/*     */         
/* 180 */         if (field.value != null) {
/* 181 */           out.append(" (");
/* 182 */           out.append(field.value.getClass().getSimpleName());
/* 183 */           out.append(')');
/*     */         } 
/*     */         
/* 186 */         out.append('\n');
/*     */       } 
/*     */       
/* 189 */       if (te.teClass != null) {
/* 190 */         out.append("    TE Class: ");
/* 191 */         out.append(te.teClass.getName());
/* 192 */         out.append('\n'); continue;
/*     */       } 
/* 194 */       out.append("    no TE Class\n");
/*     */     } 
/*     */ 
/*     */     
/* 198 */     out.setLength(out.length() - 1);
/*     */     
/* 200 */     IC2.log.info(LogCategory.Network, "Received TE Update:\n" + out.toString());
/*     */   }
/*     */   private static void apply(TeUpdateDataClient.TeData update, World world) {
/*     */     TileEntityBlock tileEntityBlock;
/* 204 */     if (!world.func_175668_a(update.pos, false)) {
/* 205 */       if (debug) IC2.log.info(LogCategory.Network, "Skipping update at %s, chunk not loaded.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos) });
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
/* 216 */     TileEntity te = world.func_175625_s(update.pos);
/*     */     
/* 218 */     if (update.teClass != null && (te == null || te.getClass() != update.teClass || te.func_145837_r() || te.func_145831_w() != world))
/* 219 */     { if (debug) IC2.log.info(LogCategory.Network, "Instantiating %s with %s.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos), update.teClass.getName() });
/*     */       
/* 221 */       tileEntityBlock = TileEntityBlock.instantiate(update.teClass);
/* 222 */       world.func_175690_a(update.pos, (TileEntity)tileEntityBlock);
/*     */       
/* 224 */       assert !tileEntityBlock.func_145837_r();
/* 225 */       assert tileEntityBlock.func_145831_w() == world; }
/* 226 */     else { if (tileEntityBlock == null) {
/* 227 */         if (debug) IC2.log.info(LogCategory.Network, "Can't apply update at %s, no te and no teClass.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos) });  return;
/*     */       } 
/* 229 */       if (tileEntityBlock.func_145837_r() || tileEntityBlock.func_145831_w() != world) {
/* 230 */         if (debug) IC2.log.warn(LogCategory.Network, "Can't apply update at %s, invalid te and no teClass.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos) }); 
/*     */         return;
/*     */       } 
/* 233 */       if (debug) IC2.log.info(LogCategory.Network, "TE class at %s unchanged.", new Object[] { Util.formatPosition((IBlockAccess)world, update.pos) });
/*     */        }
/*     */     
/* 236 */     for (TeUpdateDataClient.FieldData fieldUpdate : update.getFields()) {
/* 237 */       Object value = DataEncoder.getValue(fieldUpdate.value);
/*     */       
/* 239 */       if (fieldUpdate.field != null) {
/* 240 */         ReflectionUtil.setValue(tileEntityBlock, fieldUpdate.field, value);
/*     */       } else {
/* 242 */         ReflectionUtil.setValueRecursive(tileEntityBlock, fieldUpdate.name, value);
/*     */       } 
/*     */       
/* 245 */       if (tileEntityBlock instanceof INetworkUpdateListener) {
/* 246 */         ((INetworkUpdateListener)tileEntityBlock).onNetworkUpdate(fieldUpdate.name);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 251 */   static final boolean debug = (System.getProperty("ic2.network.debug.teupdate") != null);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\TeUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */