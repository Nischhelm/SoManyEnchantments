package com.shultrea.rin.enchantments.fishing;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentAdvancedLure extends EnchantmentBase {
	
	public EnchantmentAdvancedLure(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getValue(ItemStack stack) {
		if(!EnchantmentRegistry.advancedLure.isEnabled()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLure, stack);
		if(level <= 0) return 0;
		int toReturn = level + 1;
		if(Math.random() < 0.15f) toReturn = level + 2;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLure;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLure;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedLure, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedLure, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedLure, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedLure, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLure;
	}
}