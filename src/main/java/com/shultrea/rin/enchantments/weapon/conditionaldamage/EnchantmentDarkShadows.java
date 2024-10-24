package com.shultrea.rin.enchantments.weapon.conditionaldamage;

import com.shultrea.rin.Interfaces.IConditionalDamage;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDarkShadows extends EnchantmentBase implements IConditionalDamage {
	
	public EnchantmentDarkShadows(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.darkShadows;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.darkShadows;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.darkShadows, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.darkShadows, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.darkShadows;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(level >= 3 && entiti instanceof EntityLivingBase) {
			EntityLivingBase e = (EntityLivingBase)entiti;
			if(user.getBrightness() <= 0.1f && e.getBrightness() <= 0.1f)
				e.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 160));
			if(user.getRNG().nextInt(10) < level) e.setRevengeTarget(null);
		}
	}
	
	//TODO
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IConditionalDamage);
//	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEvent(LivingHurtEvent e) {
		if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.darkShadows, attacker.getHeldItemMainhand());
		if(enchantmentLevel <= 0) return;
		if(attacker.getBrightness() <= 0.1f && e.getEntityLiving().getBrightness() <= 0.1f)
			e.setAmount(e.getAmount() + enchantmentLevel * 0.75f);
	}
}