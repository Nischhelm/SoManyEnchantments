package com.Shultrea.Rin.Ench0_3_0;

import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Interfaces.IPotionDebuffer;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentLevitator extends Enchantment implements IPotionDebuffer{
	public EnchantmentLevitator(){
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("Levitator");
		this.setRegistryName("Levitator");
	}
	
	@Override
	public int getMaxLevel()
    {
        return 2;
    }
	
	@Override
    public int getMinEnchantability(int par1)
    {
		return 15 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 30;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment fTest)
    {
    			
    	return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
    }
    
    @Override
    public boolean canApply(ItemStack fTest)
    {
    	return super.canApply(fTest);
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity entiti, int level)
    {
    	if(!(entiti instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase entity = (EntityLivingBase) entiti;
    			
    	entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (level * 12), 1 + level));		
    }
    
    
}