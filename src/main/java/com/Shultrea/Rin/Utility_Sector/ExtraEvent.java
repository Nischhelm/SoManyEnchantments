package com.Shultrea.Rin.Utility_Sector;

import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Main_Sector.somanyenchantments;
import com.Shultrea.Rin.Prop_Sector.ArrowPropertiesProvider;
import com.Shultrea.Rin.Prop_Sector.IArrowProperties;
//import com.Shultrea.Rin.Prop_Sector.PlayerPropertiesProvider;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExtraEvent
{
	@SubscribeEvent
	public void onArrowHit(LivingDamageEvent fEvent){
		if(fEvent.getSource().damageType != "arrow")
			return;
			
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow))
			return;
			
		EntityArrow cause = (EntityArrow) fEvent.getSource().getImmediateSource();
		
		if(!cause.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null))
			return;
		
		IArrowProperties properties = cause.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		
		int flameLevel = properties.getFlameLevel();
		
		switch(flameLevel){
		case 1:
			if(somanyenchantments.config.LesserFlame)
			fEvent.getEntityLiving().setFire(2);
		case 2:
			if(somanyenchantments.config.AdvancedFlame)
			fEvent.getEntityLiving().setFire(15);
		case 3:
			if(somanyenchantments.config.SupremeFlame)
			fEvent.getEntityLiving().setFire(30);
		default:
			break;
		}
		
		float pullPower = properties.getPullPower();
		
		if(pullPower > 0 && somanyenchantments.config.Pulling) {		
			pullPower *= -1;
			float distance = MathHelper.sqrt(cause.motionX * cause.motionX + cause.motionZ * cause.motionZ);
			fEvent.getEntityLiving().addVelocity(cause.motionX * pullPower * 0.6000000238418579D / (double)distance, 0.1D, cause.motionZ * pullPower * 0.6000000238418579D / (double)distance);
			fEvent.getEntityLiving().velocityChanged = true;
		}
	}
}