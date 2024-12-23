package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDisorientatingBlade extends EnchantmentBase {
	
	public EnchantmentDisorientatingBlade(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disorientatingBlade;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disorientatingBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.disorientatingBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.disorientatingBlade, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.disorientatingBlade, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.disorientatingBlade, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disorientatingBlade;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(!(entiti instanceof EntityLivingBase)) return;
		EntityLivingBase entity = (EntityLivingBase)entiti;
		//TODO this RNG check may also be meant for levels 3 and above, even though it originally only surrounded 1 and 2
		if(entity.getRNG().nextInt(100) < 10) {
			if(level == 1 || level == 2) {
				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 80 + (level * 10), 0));
			}
		}
		if(level >= 3) {
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 + (level * 10), level - 1));
			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 25 + (level * 7), 0));
		}
	}
	
	//TODO
	@SubscribeEvent
	public void criticalWhenDisoriented(CriticalHitEvent e) {
		if(!(e.getTarget() instanceof EntityLivingBase)) return;
		EntityLivingBase eb = (EntityLivingBase)e.getTarget();
		if(eb.isPotionActive(MobEffects.SLOWNESS) && eb.isPotionActive(MobEffects.NAUSEA)) {
			//if(EnchantmentsUtility.isLevelMax(e.getEntityPlayer().getHeldItemMainhand(), EnchantmentRegistry.Disorientation))
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.disorientatingBlade, e.getEntityPlayer().getHeldItemMainhand()) >= 4) {
				e.setDamageModifier(e.getDamageModifier() + 0.25f);
			}
		}
	}
}