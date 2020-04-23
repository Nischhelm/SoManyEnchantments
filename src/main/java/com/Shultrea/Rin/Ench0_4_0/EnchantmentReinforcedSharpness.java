package com.Shultrea.Rin.Ench0_4_0;

import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import com.Shultrea.Rin.Enum.EnumList;
import com.Shultrea.Rin.Interfaces.IEnchantmentDamage;
import com.Shultrea.Rin.Main_Sector.somanyenchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;


public class EnchantmentReinforcedSharpness extends Enchantment implements IEnchantmentDamage {
	public EnchantmentReinforcedSharpness()
	{
		super(Rarity.RARE, EnumList.COMBAT_TOOL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("sharperedge");
		this.setRegistryName("sharperedge");
	}
	
	@Override
	public int getMaxLevel()
    {
        return 5;
    }
	
	@Override
    public int getMinEnchantability(int par1)
    {
        return 18 + 16 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 40;
    }
    
    
    @Override
    public boolean canApplyTogether(Enchantment fTest)
    {
    	return super.canApplyTogether(fTest) && fTest != Smc_010.Bluntness && !(fTest instanceof IEnchantmentDamage) && !(fTest instanceof EnchantmentDamage);
    }
     
    @Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
    {
    	if(somanyenchantments.config.SharperEdge)
    		return (0.75f * level + 0.5f);
    	
    	return 0;
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return somanyenchantments.config.SharperEdge && stack.getItem().canApplyAtEnchantingTable(stack, this);
    }
    
    @Override
    public boolean isAllowedOnBooks()
    {
        return somanyenchantments.config.SharperEdge;
    }
    
    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
    	if(somanyenchantments.config.SharperEdge)
    	if(target instanceof EntityLivingBase && user instanceof EntityPlayer){
    		EntityLivingBase victim = (EntityLivingBase) target;
    		
    		int x = victim.getTotalArmorValue();
    		
    		if(x > 20)
    		x = 20;
    		
    		if(level >= 9)
    			level = 9;
    		
    		if(!victim.isSilent()){
    		victim.setSilent(true);
    			
    		victim.hurtResistantTime = 0;
    		victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
    		victim.setSilent(false);
    		}
    		
    		else victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
    	}
    	
    }
    }
