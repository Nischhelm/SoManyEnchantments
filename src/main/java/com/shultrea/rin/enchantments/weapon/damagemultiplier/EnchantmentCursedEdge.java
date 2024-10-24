package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.CompatUtil;
import com.shultrea.rin.enchantments.weapon.EnchantmentBlessedEdge;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCursedEdge extends EnchantmentCurse {
	
	public EnchantmentCursedEdge(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.cursedEdge;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.cursedEdge;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.cursedEdge, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.cursedEdge, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.cursedEdge;
	}
	
	//TODO maybe not restrict from other multipliers ?
//	@Override
//	public boolean canApplyTogether(Enchantment ench) {
//		return !(ench instanceof IDamageMultiplier) && !(ench instanceof EnchantmentBlessedEdge) && super.canApplyTogether(ench);
//	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getSource().getTrueSource() instanceof EntityPlayer && CompatUtil.isRLCombatLoaded()) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.cursedEdge, stack);
		if(level > 0) {
			float damage = event.getAmount() * (((float)level * 0.2F) + 1.0F);
			if(damage > 0.0F) {
				event.setAmount(damage);
				attacker.attackEntityFrom(DamageSource.MAGIC, damage * 0.25F);
			}
		}
	}
	
	@Optional.Method(modid = "bettercombatmod")
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void modifyDamageEventPost(RLCombatModifyDamageEvent.Post event) {
		if(event.getEntityPlayer() == null || event.getTarget() == null || event.getStack().isEmpty() || !(event.getTarget() instanceof EntityLivingBase)) return;
		
		EntityPlayer player = event.getEntityPlayer();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.cursedEdge, event.getStack());
		if(level > 0) {
			float damage = (event.getBaseDamage() + event.getDamageModifier()) * (float)level * 0.2F;
			if(damage > 0.0F) {
				event.setDamageModifier(event.getDamageModifier() + damage);
				player.attackEntityFrom(DamageSource.MAGIC, (event.getBaseDamage() + event.getDamageModifier()) * 0.25F);
			}
		}
	}
}