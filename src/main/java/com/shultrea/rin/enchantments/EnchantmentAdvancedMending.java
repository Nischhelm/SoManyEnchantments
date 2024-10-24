package com.shultrea.rin.enchantments;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedMending extends EnchantmentBase {
	
	public EnchantmentAdvancedMending(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedMending;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedMending;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedMending, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedMending, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedMending;
	}
	
	//TODO
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return fTest != Enchantments.MENDING && super.canApplyTogether(fTest);
//	}
	
	//TODO
	@SubscribeEvent
	public void onXP(PlayerPickupXpEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		EntityXPOrb orb = event.getOrb();
		if(orb.xpValue > 0 && ModConfig.miscellaneous.enableDoubleXPBug) {
			player.addExperience(orb.xpValue);
		}
		//Get a random item on the player that has advanced mending
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(this, player);
		//Attempt to repair it using some of the XP from the orb itself
		if(!itemstack.isEmpty() && itemstack.isItemDamaged()) {
			int value = Math.min(orb.xpValue * 3, itemstack.getItemDamage());
			itemstack.setItemDamage(itemstack.getItemDamage() - value);
			orb.xpValue -= value / 2;
			//There is a chance that the orb.xpValue has become negative (for example, an orb.xpValue of 2 can become -1)
			if(orb.xpValue < 0) orb.xpValue = 0;
		}
	}
}