package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentTierFA extends EnchantmentBase {
	
	private static final String[] DAMAGE_NAMES = new String[]{"lfa", "afa", "sfa"};
	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfasp, 1 = advfasp, 2 = supfasp
	 */
	public final int damageType;
	
	public EnchantmentTierFA(String name, Rarity rarity, EnumEnchantmentType type, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
		this.damageType = damageTypeIn;
		this.type = type;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0:
				return ModConfig.enabled.lesserFireAspect;
			case 1:
				return ModConfig.enabled.advancedFireAspect;
			case 2:
				return ModConfig.enabled.supremeFireAspect;
			default:
				return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0:
				return ModConfig.level.lesserFireAspect;
			case 1:
				return ModConfig.level.advancedFireAspect;
			case 2:
				return ModConfig.level.supremeFireAspect;
			default:
				return 2;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case 1:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case 2:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case 1:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case 2:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default:
				return 0;
		}
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0:
				return ModConfig.treasure.lesserFireAspect;
			case 1:
				return ModConfig.treasure.advancedFireAspect;
			case 2:
				return ModConfig.treasure.supremeFireAspect;
			default:
				return false;
		}
	}
	//TODO
	
	/**
	 * Called whenever a mob is damaged with an item that has this enchantment on it.
	 */
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!isEnabled()) return;
		//Check if target is EntityLivingBase
		if(target instanceof EntityLivingBase) {
			if(this.damageType == 2) {
				if(level > 0) target.setFire(16 * level);
			}
			else if(this.damageType == 1) {
				if(level > 0) target.setFire(8 * level);
			}
			else if(this.damageType == 0) {
				if(level > 0) target.setFire(2 * level);
			}
		}
	}
	
	//TODO
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return 0.0f;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentTierFA || ench == EnchantmentRegistry.fieryEdge || ench == EnchantmentRegistry.waterAspect);
	}
	
	//TODO
	@Override
	public String getName() {
		return "enchantment." + DAMAGE_NAMES[this.damageType];
	}
}