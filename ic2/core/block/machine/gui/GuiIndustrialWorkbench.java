/*     */ package ic2.core.block.machine.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
/*     */ import ic2.core.block.personal.IPersonalBlock;
/*     */ import ic2.core.gui.Area;
/*     */ import ic2.core.gui.CustomButton;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.Image;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.gui.VanillaButton;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiIndustrialWorkbench extends GuiIC2<ContainerIndustrialWorkbench> {
/*     */   public GuiIndustrialWorkbench(ContainerIndustrialWorkbench container) {
/*  40 */     super((ContainerBase)container, 194, 228);
/*     */     
/*  42 */     addElement((new Area(this, 173, 3, 18, 108)
/*     */         {
/*     */           protected boolean suppressTooltip(int mouseX, int mouseY) {
/*  45 */             for (GuiElement<?> element : (Iterable<GuiElement<?>>)GuiIndustrialWorkbench.this.elements) {
/*  46 */               if (element.isEnabled() && element != this && element.contains(mouseX, mouseY)) {
/*  47 */                 return true;
/*     */               }
/*     */             } 
/*  50 */             return false;
/*     */           }
/*  52 */         }).withTooltip("ic2.IndustrialWorkbench.gui.adjacent"));
/*  53 */     for (EnumFacing side : EnumFacing.field_82609_l) {
/*  54 */       addElement(((CustomButton)((CustomButton)(new CustomButton(this, 173, 3 + (side.func_176745_a() + 5) % 6 * 18, 18, 18, new IClickHandler()
/*     */             {
/*     */               public void onClick(MouseButton button) {
/*  57 */                 TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).base;
/*  58 */                 assert base.func_145830_o();
/*     */                 
/*  60 */                 TileEntity neighbour = base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side));
/*  61 */                 assert neighbour instanceof IHasGui;
/*     */                 
/*  63 */                 if (!(neighbour instanceof IPersonalBlock) || ((IPersonalBlock)neighbour).permitsAccess(((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).player.func_146103_bH())) {
/*  64 */                   ((NetworkManager)IC2.network.get(false)).requestGUI((IHasGui)neighbour);
/*  65 */                   MinecraftForge.EVENT_BUS.register(this);
/*     */                 } else {
/*  67 */                   IC2.platform.messagePlayer(((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).player, "Owned by " + ((IPersonalBlock)neighbour).getOwner().getName(), new Object[0]);
/*     */                 } 
/*     */               }
/*     */               
/*     */               @SubscribeEvent
/*     */               public void waitForClose(GuiOpenEvent event) {
/*  73 */                 if (keepOpen(event.getGui()))
/*     */                   return; 
/*  75 */                 if (!this.firstOpen) {
/*  76 */                   ((NetworkManager)IC2.network.get(false)).requestGUI((IHasGui)((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).base);
/*  77 */                   event.setGui((GuiScreen)GuiIndustrialWorkbench.this);
/*     */                   
/*  79 */                   MinecraftForge.EVENT_BUS.unregister(this);
/*     */                 } else {
/*  81 */                   this.firstOpen = false;
/*     */                 } 
/*     */               }
/*     */               
/*     */               private boolean keepOpen(GuiScreen screen) {
/*  86 */                 if (GuiIndustrialWorkbench.jeiScreenRecipesGuiCheck == null)
/*  87 */                   return false; 
/*  88 */                 if (GuiIndustrialWorkbench.jeiScreenRecipesGuiCheck.apply(screen)) {
/*  89 */                   this.jei = true;
/*     */                   
/*  91 */                   return true;
/*  92 */                 }  if (this.jei) {
/*  93 */                   this.jei = false;
/*     */                   
/*  95 */                   return true;
/*     */                 } 
/*  97 */                 return false;
/*     */               }
/*     */               private boolean firstOpen = true;
/*     */               
/*     */               private boolean jei = false;
/* 102 */             })).withEnableHandler(new IEnableHandler()
/*     */             {
/*     */               public boolean isEnabled() {
/* 105 */                 TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).base;
/* 106 */                 return (base.func_145830_o() && base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side)) instanceof IHasGui);
/*     */               }
/* 108 */             })).withIcon(new Supplier<ItemStack>()
/*     */             {
/*     */               public ItemStack get() {
/* 111 */                 TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).base;
/* 112 */                 assert base.func_145830_o();
/*     */                 
/* 114 */                 BlockPos pos = base.func_174877_v().func_177972_a(side);
/* 115 */                 IBlockState state = base.func_145831_w().func_180495_p(pos);
/* 116 */                 return state.func_177230_c().getPickBlock(state, null, base.func_145831_w(), pos, ((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).player);
/*     */               }
/* 118 */             })).withTooltip(new Supplier<String>() {
/*     */               private String getSideName() {
/* 120 */                 switch (side) { case WEST:
/* 121 */                     return "ic2.dir.West";
/* 122 */                   case EAST: return "ic2.dir.East";
/* 123 */                   case DOWN: return "ic2.dir.Bottom";
/* 124 */                   case UP: return "ic2.dir.Top";
/* 125 */                   case NORTH: return "ic2.dir.North";
/* 126 */                   case SOUTH: return "ic2.dir.South"; }
/* 127 */                  throw new IllegalStateException("Unexpected direction: " + side);
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public String get() {
/* 133 */                 TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)GuiIndustrialWorkbench.this.container).base;
/* 134 */                 assert base.func_145830_o();
/*     */                 
/* 136 */                 TileEntity neighbour = base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side));
/* 137 */                 assert neighbour instanceof IHasGui;
/*     */                 
/* 139 */                 return Localization.translate(((IHasGui)neighbour).func_70005_c_()) + '\n' + TextFormatting.DARK_GRAY + Localization.translate(getSideName());
/*     */               }
/*     */             }));
/*     */     } 
/* 143 */     int cancelX = 93, cancelY = 42;
/* 144 */     addElement((new VanillaButton(this, 93, 42, 16, 16, new IClickHandler()
/*     */           {
/*     */             public void onClick(MouseButton button) {
/* 147 */               ((NetworkManager)IC2.network.get(false)).sendContainerEvent(GuiIndustrialWorkbench.this.container, "clear");
/*     */             }
/* 149 */           })).withTooltip("Clear"));
/* 150 */     addElement((GuiElement)Image.create(this, 94, 43, 14, 14, GuiElement.commonTexture, 256, 256, 210, 47, 224, 61));
/*     */   }
/*     */   public static Predicate<GuiScreen> jeiScreenRecipesGuiCheck;
/*     */   
/*     */   protected ResourceLocation getTexture() {
/* 155 */     return TEXTURE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 160 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIIndustrialWorkbench.png");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiIndustrialWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */