package com.shultrea.rin.enchantments.base;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public abstract class EnchantmentCurse extends EnchantmentBase {
	
	public EnchantmentCurse(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModConfig.miscellaneous.canCursesBeAppliedAtEnchantingTable && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ModConfig.miscellaneous.canCursesBeAppliedAtEnchantingTable && super.canApply(stack);
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return ModConfig.miscellaneous.canCursesBeAppliedToBooks && super.isAllowedOnBooks();
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.RED + "";
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
}