package com.shultrea.rin.enchantments;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentUpgradedPotentials extends EnchantmentBase {
	
	public EnchantmentUpgradedPotentials(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.upgradedPotentials;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.upgradedPotentials;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.upgradedPotentials, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.upgradedPotentials, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.upgradedPotentials, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.upgradedPotentials, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.upgradedPotentials;
	}
	
	@SubscribeEvent
	public void onAnvilUpdateEvent(AnvilUpdateEvent event) {
		if(!this.isEnabled()) return;
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if(left.isEmpty() || right.isEmpty()) return;
		
		if(right.getItem() == Items.ENCHANTED_BOOK) {
			int level = EnchantmentHelper.getEnchantmentLevel(this, right);
			if(level > 0) {
				if(left.isStackable() || EnchantmentHelper.getEnchantments(left).isEmpty() || EnchantmentHelper.getEnchantmentLevel(this, left) > 0) {
					event.setCanceled(true);
					return;
				}
				int cost = left.getRepairCost();
				cost = Math.max(0, (cost / 4) - 20);
				ItemStack output = left.copy();
				output.setRepairCost(cost);
				output.addEnchantment(this, 1);
				event.setOutput(output);
				event.setCost(10);
			}
		}
	}
}