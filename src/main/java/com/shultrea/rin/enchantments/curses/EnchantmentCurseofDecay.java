package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofDecay extends EnchantmentCurse {
	
	public EnchantmentCurseofDecay(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfDecay;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfDecay;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.curseOfDecay, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.curseOfDecay, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfDecay;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentCurseofPossession) && super.canApplyTogether(ench);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(!(event.getEntity() instanceof EntityItem)) return;
		EntityItem entity = (EntityItem)event.getEntity();
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfDecay, entity.getItem()) > 0) {
			entity.lifespan = 80;
			entity.setPickupDelay(10);
		}
	}
}