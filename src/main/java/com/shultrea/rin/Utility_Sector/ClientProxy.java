package com.shultrea.rin.Utility_Sector;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;

/**
 * The client proxy (rendering and such)
 */
public class ClientProxy extends CommProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void onInit(FMLInitializationEvent event) {
		super.onInit(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Nullable
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}
}