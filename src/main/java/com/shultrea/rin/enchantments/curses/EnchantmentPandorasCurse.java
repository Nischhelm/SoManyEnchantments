package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.CurseLister;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.registry.ModRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantmentPandorasCurse extends EnchantmentCurse {
	
	public EnchantmentPandorasCurse(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.pandorasCurse;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.pandorasCurse;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.pandorasCurse, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.pandorasCurse, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.pandorasCurse, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.pandorasCurse, stack) || super.canApply(stack);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public String getTranslatedName(int level) {
		if(!this.isEnabled()) return super.getTranslatedName(level);
		String s = I18n.translateToLocal(this.getName());
		s = TextFormatting.DARK_RED + s;
		EntityPlayer player = SoManyEnchantments.proxy.getClientPlayer();
		return level < 3 && player != null && !player.isCreative() ? "" : s;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.pandorasCurse;
	}
	
	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if(event.phase != Phase.END || event.player == null || event.player.world.isRemote) return;
		
		if(event.player.ticksExisted%ModConfig.miscellaneous.pandorasCurseInterval == 0) {
			InventoryPlayer inv = event.player.inventory;
			ItemStack cursedStack = null;
			int curseLevel = 0;
			List<ItemStack> candidates = new ArrayList<ItemStack>();
			
			for(ItemStack stack : inv.offHandInventory) {
				if(!stack.isEmpty() && !(stack.getItem() instanceof ItemEnchantedBook)) {
					int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.pandorasCurse, stack);
					if(lvl > 0) {
						cursedStack = stack;
						curseLevel = lvl;
					}
					else if(stack.getMaxStackSize() == 1) candidates.add(stack);
				}
			}
			for(ItemStack stack : inv.armorInventory) {
				if(!stack.isEmpty() && !(stack.getItem() instanceof ItemEnchantedBook)) {
					int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.pandorasCurse, stack);
					if(lvl > 0) {
						cursedStack = stack;
						curseLevel = lvl;
					}
					else if(stack.getMaxStackSize() == 1) candidates.add(stack);
				}
			}
			for(ItemStack stack : inv.mainInventory) {
				if(!stack.isEmpty() && !(stack.getItem() instanceof ItemEnchantedBook)) {
					int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.pandorasCurse, stack);
					if(lvl > 0) {
						cursedStack = stack;
						curseLevel = lvl;
					}
					else if(stack.getMaxStackSize() == 1) candidates.add(stack);
				}
			}
			
			if(cursedStack == null || candidates.isEmpty()) return;
			
			int origCurseLevel = curseLevel;
			
			List<Enchantment> curses = CurseLister.CURSE;
			for(ItemStack stack : candidates) {
				if(curseLevel <= 5 && event.player.world.rand.nextInt(8) < 1) {
					Enchantment curse = curses.get(event.player.world.rand.nextInt(curses.size()));
					if(curse != EnchantmentRegistry.pandorasCurse && curse.canApply(stack)) {
						boolean compat = true;
						for(Enchantment ench : EnchantmentHelper.getEnchantments(stack).keySet()) {
							if(!ench.isCompatibleWith(curse)) {
								compat = false;
								break;
							}
						}
						if(compat) {
							curseLevel++;
							stack.addEnchantment(curse, event.player.world.rand.nextInt(curse.getMaxLevel()) + 1);
						}
					}
				}
			}
			
			if(curseLevel != origCurseLevel || curseLevel > 5) {
				Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(cursedStack);
				if(curseLevel > 5) {
					enchants.remove(EnchantmentRegistry.pandorasCurse);
					event.player.world.playSound(null, event.player.posX, event.player.posY, event.player.posZ, ModRegistry.PANDORA_REMOVAL, SoundCategory.PLAYERS, 0.8F, (event.player.world.rand.nextFloat()-event.player.world.rand.nextFloat())*0.1F+1.4F);
				}
				else {
					enchants.put(EnchantmentRegistry.pandorasCurse, curseLevel);
				}
				EnchantmentHelper.setEnchantments(enchants, cursedStack);
			}
		}
	}
}