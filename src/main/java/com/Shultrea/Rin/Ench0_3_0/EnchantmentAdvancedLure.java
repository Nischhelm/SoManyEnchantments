package com.Shultrea.Rin.Ench0_3_0;

import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import com.Shultrea.Rin.Enchantments_Sector.Smc_020;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedLure extends Enchantment
{
	public EnchantmentAdvancedLure()
	{
		super(Rarity.VERY_RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("AdvancedLure");
		this.setRegistryName("AdvancedLure");
	}
		
	@Override
	public int getMaxLevel()
	{
		return 3;
	}
		
	@Override
	public int getMinEnchantability(int par1)
	{
		return 15 + 15 * (par1);
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 30;
	}
	    
	@Override
	public boolean canApplyTogether(Enchantment fTest)
	{
		
		return fTest == Enchantments.LURE  ? false : super.canApplyTogether(fTest);
	}
	@Override
	public boolean canApply(ItemStack fTest)
	    {
		return super.canApply(fTest);
	    }
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEvent(EntityJoinWorldEvent fEvent)
	{
		if (fEvent.getEntity() instanceof EntityFishHook)
		{
			EntityFishHook hook = (EntityFishHook) fEvent.getEntity();
			EntityLivingBase fisher = hook.getAngler();
			ItemStack fishingRod = fisher.getHeldItemMainhand();
			
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
			fishingRod = fisher.getHeldItemOffhand();
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
				return;
			}
		}
		
			
			//ItemStack item = EnchantmentHelper.getEnchantedItem(Smc_030.AdvancedLure, fisher);
			
			int level = EnchantmentHelper.getEnchantmentLevel(Smc_030.AdvancedLure, fishingRod);
			
			if(level <= 0)
			return;
			
			hook.setLureSpeed(level + 1);
			if(Math.random() < 0.15f){
			hook.setLureSpeed(level + 2);
			}
		}
}
	}
	