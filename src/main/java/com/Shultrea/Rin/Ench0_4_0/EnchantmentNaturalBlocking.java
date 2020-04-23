package com.Shultrea.Rin.Ench0_4_0;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.Shultrea.Rin.Enum.EnumList;
import com.Shultrea.Rin.Main_Sector.somanyenchantments;
import com.Shultrea.Rin.Utility_Sector.EnchantmentsUtility;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class EnchantmentNaturalBlocking extends Enchantment{
	public EnchantmentNaturalBlocking()
	{
		super(Rarity.RARE, EnumList.SHIELD, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		this.setName("NaturalBlocking");
		this.setRegistryName("NaturalBlocking");
		
	}
	
	@Override
	public int getMaxLevel()
	{
		return 3;
	}
		
	@Override
	public int getMinEnchantability(int par1)
	{
		return 18 + 12 * (par1 - 1);
	   }

	@Override
    public int getMaxEnchantability(int par1)
	{
        return this.getMinEnchantability(par1) + 40;
	}
	    
	@Override
    public boolean canApplyTogether(Enchantment fTest)
	{
		return super.canApplyTogether(fTest);	
    }
	
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return somanyenchantments.config.NaturalBlocking && stack.getItem().canApplyAtEnchantingTable(stack, this);
    }
    
    @Override
    public boolean isAllowedOnBooks()
    {
        return somanyenchantments.config.NaturalBlocking;
    }
	   
    @SubscribeEvent(priority = EventPriority.LOWEST) 
    public void naturalBlock(LivingDamageEvent fEvent){
    	
    	if(!(fEvent.getEntity() instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();
    	
    	if(victim == null)
    		return;
    	
		ItemStack shield = victim.getHeldItemMainhand();
				
		if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)){
			shield = victim.getHeldItemOffhand();
			if(shield.isEmpty())
			return;	
		}
				
		if(EnchantmentHelper.getEnchantmentLevel(Smc_040.NaturalBlocking, shield) <= 0)
			return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_040.NaturalBlocking, shield);
			
		float damage = fEvent.getAmount() - fEvent.getAmount() * (level * 0.1f + 0.1f);
		
		if(shield.getItem().isShield(shield, victim) && victim.getActiveItemStack() != shield){
			float percentage = damage / fEvent.getAmount();
			percentage = 1 - percentage;
    		fEvent.setAmount(damage);	
    		if(EnchantmentsUtility.isLevelMax(shield, this)){
    			 shield.damageItem((int) (1.25f * fEvent.getAmount() * percentage) + 1, victim);
    		}
    		else shield.damageItem((int) (1.75f * fEvent.getAmount() * percentage) + 1, victim);
    	}
		
    }
    
		
    }
  
