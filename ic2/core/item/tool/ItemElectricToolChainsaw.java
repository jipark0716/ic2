/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHitSoundOverride;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.IShearable;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemElectricToolChainsaw
/*     */   extends ItemElectricTool
/*     */   implements IHitSoundOverride
/*     */ {
/*     */   public ItemElectricToolChainsaw() {
/*  56 */     super(ItemName.chainsaw, 100, HarvestLevel.Iron, EnumSet.of(ToolClass.Axe, ToolClass.Sword, ToolClass.Shears));
/*     */     
/*  58 */     this.maxCharge = 30000;
/*  59 */     this.transferLimit = 100;
/*  60 */     this.tier = 1;
/*     */     
/*  62 */     this.field_77864_a = 12.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
/*  76 */     if (world.field_72995_K) return super.func_77659_a(world, player, hand);
/*     */     
/*  78 */     if (IC2.keyboard.isModeSwitchKeyDown(player)) {
/*  79 */       NBTTagCompound compoundTag = StackUtil.getOrCreateNbtData(StackUtil.get(player, hand));
/*     */       
/*  81 */       if (compoundTag.func_74767_n("disableShear")) {
/*  82 */         compoundTag.func_74757_a("disableShear", false);
/*     */         
/*  84 */         IC2.platform.messagePlayer(player, "ic2.tooltip.mode", new Object[] { "ic2.tooltip.mode.normal" });
/*     */       } else {
/*  86 */         compoundTag.func_74757_a("disableShear", true);
/*     */         
/*  88 */         IC2.platform.messagePlayer(player, "ic2.tooltip.mode", new Object[] { "ic2.tooltip.mode.noShear" });
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return super.func_77659_a(world, player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
/*  97 */     if (slot != EntityEquipmentSlot.MAINHAND) {
/*  98 */       return super.getAttributeModifiers(slot, stack);
/*     */     }
/* 100 */     HashMultimap hashMultimap = HashMultimap.create();
/*     */     
/* 102 */     if (ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
/* 103 */       hashMultimap.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Tool modifier", this.field_185065_c, 0));
/*     */       
/* 105 */       hashMultimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(Item.field_111210_e, "Tool modifier", 9.0D, 0));
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return (Multimap<String, AttributeModifier>)hashMultimap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
/* 117 */     ElectricItem.manager.use(itemstack, this.operationEnergyCost, attacker);
/*     */     
/* 119 */     if (attacker instanceof EntityPlayer && entityliving instanceof net.minecraft.entity.monster.EntityCreeper && entityliving.func_110143_aJ() <= 0.0F) {
/* 120 */       IC2.achievements.issueAchievement((EntityPlayer)attacker, "killCreeperChainsaw");
/*     */     }
/*     */     
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
/* 128 */     if (!IC2.platform.isSimulating())
/*     */       return; 
/* 130 */     Entity entity = event.getTarget();
/* 131 */     EntityPlayer player = event.getEntityPlayer();
/* 132 */     ItemStack itemstack = player.field_71071_by.func_70301_a(player.field_71071_by.field_70461_c);
/*     */     
/* 134 */     if (itemstack != null && itemstack.func_77973_b() == this && entity instanceof IShearable && 
/* 135 */       !StackUtil.getOrCreateNbtData(itemstack).func_74767_n("disableShear") && ElectricItem.manager
/* 136 */       .use(itemstack, this.operationEnergyCost, (EntityLivingBase)player)) {
/* 137 */       IShearable target = (IShearable)entity;
/* 138 */       World world = entity.func_130014_f_();
/* 139 */       BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */       
/* 141 */       if (target.isShearable(itemstack, (IBlockAccess)world, pos)) {
/* 142 */         List<ItemStack> drops = target.onSheared(itemstack, (IBlockAccess)world, pos, 
/* 143 */             EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, itemstack));
/*     */         
/* 145 */         for (ItemStack stack : drops) {
/* 146 */           EntityItem ent = entity.func_70099_a(stack, 1.0F);
/*     */           
/* 148 */           ent.field_70181_x += (field_77697_d.nextFloat() * 0.05F);
/* 149 */           ent.field_70159_w += ((field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.1F);
/* 150 */           ent.field_70179_y += ((field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
/* 158 */     if (!IC2.platform.isSimulating()) return false; 
/* 159 */     if (StackUtil.getOrCreateNbtData(itemstack).func_74767_n("disableShear")) return false;
/*     */     
/* 161 */     World world = player.func_130014_f_();
/* 162 */     IBlockState state = world.func_180495_p(pos);
/* 163 */     Block block = state.func_177230_c();
/*     */     
/* 165 */     if (block instanceof IShearable) {
/* 166 */       IShearable target = (IShearable)block;
/*     */       
/* 168 */       if (target.isShearable(itemstack, (IBlockAccess)world, pos) && ElectricItem.manager
/* 169 */         .use(itemstack, this.operationEnergyCost, (EntityLivingBase)player)) {
/* 170 */         List<ItemStack> drops = target.onSheared(itemstack, (IBlockAccess)world, pos, 
/* 171 */             EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, itemstack));
/*     */         
/* 173 */         for (ItemStack stack : drops) {
/* 174 */           StackUtil.dropAsEntity(world, pos, stack);
/*     */         }
/*     */         
/* 177 */         player.func_71064_a(StatList.func_188055_a(block), 1);
/* 178 */         world.func_180501_a(pos, Blocks.field_150350_a.func_176223_P(), 11);
/*     */         
/* 180 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getHitSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String getBreakSoundForBlock(EntityPlayerSP player, World world, BlockPos pos, ItemStack stack) {
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getIdleSound(EntityLivingBase player, ItemStack stack) {
/* 203 */     return "Tools/Chainsaw/ChainsawIdle.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getStopSound(EntityLivingBase player, ItemStack stack) {
/* 208 */     return "Tools/Chainsaw/ChainsawStop.ogg";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemElectricToolChainsaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */