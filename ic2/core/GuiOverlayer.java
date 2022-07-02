/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.HudMode;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.api.item.IItemHudProvider;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiOverlayer
/*     */   extends Gui
/*     */ {
/*     */   private final Minecraft mc;
/*     */   
/*     */   public GuiOverlayer(Minecraft mc) {
/*  40 */     this.mc = mc;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHotBar(RenderGameOverlayEvent.Post event) {
/*  45 */     if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     ItemStack helm = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.HEAD);
/*     */     
/*  51 */     if (StackUtil.isEmpty(helm) || !(helm.func_77973_b() instanceof IItemHudProvider) || !((IItemHudProvider)helm.func_77973_b()).doesProvideHUD(helm))
/*  52 */       return;  HudMode hudMode = ((IItemHudProvider)helm.func_77973_b()).getHudMode(helm);
/*  53 */     if (!hudMode.shouldDisplay())
/*     */       return; 
/*  55 */     ItemStack boots = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.FEET);
/*  56 */     ItemStack legs = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.LEGS);
/*  57 */     ItemStack chestplate = this.mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST);
/*     */     
/*  59 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  60 */     GL11.glDisable(2896);
/*  61 */     RenderItem renderItem = this.mc.func_175599_af();
/*  62 */     RenderHelper.func_74520_c();
/*  63 */     this.mc.func_110434_K().func_110577_a(background);
/*  64 */     func_73729_b(0, 0, 0, 0, 71, 69);
/*     */     
/*  66 */     renderItem.func_175042_a(helm, 5, 4);
/*  67 */     this.mc.field_71466_p.func_78276_b(mapCharge(helm) + "%", 25, 9, 16777215);
/*     */     
/*  69 */     if (StackUtil.getOrCreateNbtData(helm).func_74767_n("Nightvision")) {
/*  70 */       renderItem.func_175042_a(ItemName.nightvision_goggles.getItemStack(), 50, 4);
/*     */     }
/*     */     
/*  73 */     if (!StackUtil.isEmpty(chestplate)) {
/*  74 */       int charge = getCharge(chestplate);
/*     */       
/*  76 */       if (charge >= 0) {
/*  77 */         this.mc.field_71466_p.func_78276_b(charge + "%", 25, 25, 16777215);
/*  78 */         renderItem.func_175042_a(chestplate, 5, 20);
/*     */         
/*  80 */         NBTTagCompound nbtDatachestplate = StackUtil.getOrCreateNbtData(chestplate);
/*  81 */         if (nbtDatachestplate.func_74767_n("jetpack")) {
/*     */           ItemStack jetpack;
/*  83 */           if (nbtDatachestplate.func_74767_n("hoverMode")) {
/*  84 */             jetpack = ItemName.jetpack_electric.getItemStack();
/*     */           } else {
/*  86 */             jetpack = ItemName.jetpack.getItemStack();
/*     */           } 
/*  88 */           renderItem.func_175042_a(jetpack, 50, 20);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     if (!StackUtil.isEmpty(legs)) {
/*  94 */       int charge = getCharge(legs);
/*     */       
/*  96 */       if (charge >= 0) {
/*  97 */         this.mc.field_71466_p.func_78276_b(charge + "%", 25, 41, 16777215);
/*  98 */         renderItem.func_175042_a(legs, 5, 36);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if (!StackUtil.isEmpty(boots)) {
/* 103 */       int charge = getCharge(boots);
/*     */       
/* 105 */       if (charge >= 0) {
/* 106 */         this.mc.field_71466_p.func_78276_b(charge + "%", 25, 56, 16777215);
/* 107 */         renderItem.func_175042_a(boots, 5, 52);
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     if (hudMode.hasTooltip()) {
/* 112 */       ItemStack rightItem = this.mc.field_71439_g.func_184614_ca();
/* 113 */       ItemStack leftItem = this.mc.field_71439_g.func_184592_cb();
/*     */       
/* 115 */       int nextLine = 83;
/* 116 */       if (!StackUtil.isEmpty(rightItem)) {
/* 117 */         renderItem.func_175042_a(rightItem, 5, 74);
/* 118 */         this.mc.field_71466_p.func_78276_b(rightItem.func_82833_r(), 30, 78, 16777215);
/* 119 */         List<String> info = new LinkedList<>();
/*     */         
/* 121 */         if (rightItem.func_77973_b() instanceof IItemHudInfo) {
/* 122 */           info.addAll(((IItemHudInfo)rightItem.func_77973_b()).getHudInfo(rightItem, (hudMode == HudMode.ADVANCED)));
/*     */           
/* 124 */           if (info.size() > 0) {
/* 125 */             for (int l = 0; l < info.size(); l++) {
/* 126 */               this.mc.field_71466_p.func_78276_b(info.get(l), 8, 83 + (l + 1) * 14, 16777215);
/*     */             }
/*     */           }
/*     */           
/* 130 */           nextLine += (info.size() + 1) * 14;
/*     */         } else {
/* 132 */           info.addAll(rightItem.func_82840_a((EntityPlayer)this.mc.field_71439_g, () -> (hudMode == HudMode.ADVANCED)));
/*     */           
/* 134 */           if (info.size() > 1) {
/* 135 */             for (int l = 1; l < info.size(); l++) {
/* 136 */               this.mc.field_71466_p.func_78276_b(info.get(l), 8, 83 + l * 14, 16777215);
/*     */             }
/*     */           }
/*     */           
/* 140 */           nextLine += info.size() * 14;
/*     */         } 
/* 142 */         nextLine += 8;
/*     */       } 
/*     */       
/* 145 */       if (!StackUtil.isEmpty(leftItem)) {
/* 146 */         renderItem.func_175042_a(leftItem, 5, nextLine - 9);
/* 147 */         this.mc.field_71466_p.func_78276_b(leftItem.func_82833_r(), 30, nextLine - 5, 16777215);
/* 148 */         List<String> info = new LinkedList<>();
/*     */         
/* 150 */         if (leftItem.func_77973_b() instanceof IItemHudInfo) {
/* 151 */           info.addAll(((IItemHudInfo)leftItem.func_77973_b()).getHudInfo(leftItem, (hudMode == HudMode.ADVANCED)));
/*     */           
/* 153 */           if (info.size() > 0) {
/* 154 */             for (int l = 0; l < info.size(); l++) {
/* 155 */               this.mc.field_71466_p.func_78276_b(info.get(l), 8, nextLine + (l + 1) * 14, 16777215);
/*     */             }
/*     */           }
/*     */         } else {
/* 159 */           info.addAll(leftItem.func_82840_a((EntityPlayer)this.mc.field_71439_g, () -> (hudMode == HudMode.ADVANCED)));
/*     */           
/* 161 */           if (info.size() > 1) {
/* 162 */             for (int l = 1; l < info.size(); l++) {
/* 163 */               this.mc.field_71466_p.func_78276_b(info.get(l), 8, nextLine + l * 14, 16777215);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     RenderHelper.func_74518_a();
/*     */   }
/*     */   
/*     */   private static final int getCharge(ItemStack stack) {
/* 174 */     Item item = stack.func_77973_b();
/* 175 */     assert item != null;
/*     */     
/* 177 */     if (item instanceof IItemHudProvider.IItemHudBarProvider)
/* 178 */       return ((IItemHudProvider.IItemHudBarProvider)item).getBarPercent(stack); 
/* 179 */     if (item instanceof ic2.api.item.IElectricItem)
/* 180 */       return mapCharge(stack); 
/* 181 */     if (item.func_77645_m()) {
/* 182 */       return (int)Util.map(1.0D - item.getDurabilityForDisplay(stack), 1.0D, 100.0D);
/*     */     }
/* 184 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int mapCharge(ItemStack stack) {
/* 189 */     assert stack.func_77973_b() instanceof ic2.api.item.IElectricItem;
/*     */     
/* 191 */     double charge = ElectricItem.manager.getCharge(stack);
/* 192 */     double maxCharge = charge + ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, true);
/*     */     
/* 194 */     return (int)Util.map(charge, maxCharge, 100.0D);
/*     */   }
/*     */   
/* 197 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIOverlay.png");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\GuiOverlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */