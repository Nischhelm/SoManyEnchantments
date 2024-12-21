package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentButchering extends EnchantmentBase {
	
	public EnchantmentButchering(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.butchering;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.butchering;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.butchering, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.butchering, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.butchering, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.butchering, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.butchering;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;

		//if(CompatUtil.isIceAndFireLoaded() && (victim instanceof EntityDragonBase || victim instanceof EntitySeaSerpent)) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(victim instanceof EntityAnimal) {
					event.setAmount(event.getAmount() + 2.5F + 1.5F * (float)level);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLootingLevelEvent(LootingLevelEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getDamageSource())) return;

		EntityLivingBase attacker = (EntityLivingBase)event.getDamageSource().getTrueSource();
		if(attacker == null) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
		if(level <= 0) return;

		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(!(victim instanceof EntityAnimal)) return;

		if(!EnchantmentBase.isDamageSourceAllowed(event.getDamageSource())) return;

		event.setLootingLevel(event.getLootingLevel() + level);
	}
}