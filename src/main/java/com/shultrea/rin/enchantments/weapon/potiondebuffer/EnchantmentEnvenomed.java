package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentEnvenomed extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentEnvenomed(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.envenomed;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.envenomed;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.envenomed, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.envenomed, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.envenomed;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(!(entiti instanceof EntityLivingBase)) return;
		EntityLivingBase entity = (EntityLivingBase)entiti;
		entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 30 + (level * 10), level - 1));
		entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 30 + (level * 10), level));
	}
	
	//TODO
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
//	}
}