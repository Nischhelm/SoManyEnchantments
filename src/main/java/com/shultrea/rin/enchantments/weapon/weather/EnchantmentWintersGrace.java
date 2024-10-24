package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentWintersGrace extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentWintersGrace(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.wintersGrace;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.wintersGrace;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.wintersGrace, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.wintersGrace, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.wintersGrace;
	}
	
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
//	}
	
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, stack);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		float SkyDamage = 0.0f;
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, dmgSource) <= 0) return;
		BlockPos blockpos = attacker.getPosition();
		float Damage = fEvent.getAmount();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, dmgSource);
		if(attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker) && EnchantmentsUtility.isInColdTemperature(attacker, attacker.getEntityWorld().getBiome(blockpos), true)) {

			float FDamage = EnchantmentsUtility.modifyDamage(Damage, 0.10f, 0.90f, 1.15f, enchantmentLevel);
			fEvent.setAmount(FDamage);

			if(enchantmentLevel >= 2)
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, enchantmentLevel - 2));
			if(enchantmentLevel >= 4)
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 60, enchantmentLevel - 4));
			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 60, enchantmentLevel - 3));
		}
		else if((!attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) || !EnchantmentsUtility.isInColdTemperature(attacker, attacker.getEntityWorld().getBiome(blockpos), true)) {
			float Fin = EnchantmentsUtility.modifyDamage(Damage, 0.00f, -0.6f, 1.0f, enchantmentLevel);
			fEvent.setAmount(Fin);
			if(Math.random() * 5 < 0.02f + enchantmentLevel) EnchantmentsUtility.Raining(attacker.getEntityWorld());
		}
		else if(!EnchantmentsUtility.noBlockLight(attacker)) {
			float Fid = EnchantmentsUtility.modifyDamage(Damage, 0.00f, -0.8f, 1.0f, enchantmentLevel);
			fEvent.setAmount(Fid);
		}
	}
}