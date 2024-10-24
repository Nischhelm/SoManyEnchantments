package com.shultrea.rin.enchantments.tool;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EnchantmentQuarrying extends EnchantmentBase {
	
	public EnchantmentQuarrying(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.quarrying;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.quarrying;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.quarrying, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.quarrying, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.quarrying;
	}
	
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && fTest != Enchantments.FORTUNE && fTest != Enchantments.SILK_TOUCH;
//	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onEvent(HarvestDropsEvent e) {
		if(e.getHarvester() != null) {
			ItemStack pick = e.getHarvester().getHeldItemMainhand();
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.quarrying, pick) <= 0) return;
			if(e.getFortuneLevel() > 0) return;
			if(e.isSilkTouching()) return;
			int quarry = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.quarrying, pick);
			List<ItemStack> list = e.getDrops();
			for(int x = 0; x < list.size(); x++) {
				ItemStack stack = list.get(x);
				Block block = Block.getBlockFromItem(stack.getItem());
				Block origBlock = e.getState().getBlock();
				IBlockState origState = e.getState();
				if(!e.getHarvester().getHeldItemMainhand().canHarvestBlock(e.getState())) return;
				if(!ForgeHooks.isToolEffective(e.getWorld(), e.getPos(), pick)) return;
				if(origBlock instanceof BlockStone || origBlock instanceof BlockCrops) return;
				if(stack.isEmpty() || block == origBlock) continue;
				if(ModConfig.miscellaneous.quarryOreOnly) if(!(origBlock instanceof BlockOre)) return;
				if(!ModConfig.miscellaneous.quarryAllowTileEntities) {
					if(origBlock.hasTileEntity(origState)) {
						return;
					}
				}
				if(stack.getCount() == 1) {
					if(e.getHarvester().getRNG().nextInt(100) < 25) {
						int quantity = e.getHarvester().getRNG().nextInt(quarry + 1);
						list.get(x).grow(quantity);
						pick.damageItem(e.getHarvester().getRNG().nextInt(quantity), e.getHarvester());
					}
				}
				else {
					if(e.getHarvester().getRNG().nextInt(100) < 20) {
						float mult = 1.25f + (e.getHarvester().getRNG().nextFloat() * quarry * 0.25f);
						int count = Math.round(stack.getCount() * mult);
						stack.grow(count);
						pick.damageItem(e.getHarvester().getRNG().nextInt(quarry) + 1, e.getHarvester());
					}
				}
			}
		}
	}
}