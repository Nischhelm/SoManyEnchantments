package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.weapon.EnchantmentSwifterSlashes;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.ItemMixin
 */
public class EnchantmentHeavyWeight extends EnchantmentCurse {
	
	public EnchantmentHeavyWeight(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.heavyWeight;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.heavyWeight;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.heavyWeight, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.heavyWeight, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.heavyWeight;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentSwifterSlashes) && super.canApplyTogether(ench);
	}
}