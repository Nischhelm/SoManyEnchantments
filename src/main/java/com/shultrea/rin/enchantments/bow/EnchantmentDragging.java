package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDragging extends EnchantmentBase {
	
	public EnchantmentDragging(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.dragging;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.dragging;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.dragging, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.dragging, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.dragging;
	}
	
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return fTest != Enchantments.PUNCH && !(fTest instanceof EnchantmentAdvancedPunch) && super.canApplyTogether(fTest);
//	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			/**EntityArrow arrow = (EntityArrow) event.getEntity();
			 EntityLivingBase shooter = (EntityLivingBase) arrow.shootingEntity;
			 if(shooter == null)
			 return;
			 ItemStack bow = shooter.getHeldItemMainhand();
			 int PunchLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.ExtremePunch, bow);
			 if(PunchLevel == 0)
			 return;
			 arrow.setKnockbackStrength((PunchLevel * 2) + 1);
			 */
			EntityArrow arrow = (EntityArrow)event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			if(shooter == null) return;
			ItemStack bow = shooter instanceof EntityPlayer ? shooter.getActiveItemStack() :
							!shooter.getHeldItemMainhand().isEmpty() ? shooter.getHeldItemMainhand() :
							shooter.getHeldItemOffhand();
			if(bow == null || bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == null || bow == ItemStack.EMPTY) {
					return;
				}
			}
			int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.dragging, bow);
			if(enchantmentLevel > 0) {
				if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
				IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
				properties.setPullPower(1.25f + enchantmentLevel * 1.75f);
			}
		}
	}
}