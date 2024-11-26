package com.shultrea.rin.util.compat;

import net.minecraftforge.fml.common.Loader;

public class CompatUtil {
	
	private static final String RLCOMBAT_MODID = "bettercombatmod";
	private static final String SWITCHBOW_MODID = "switchbow";
	private static final String INF_MODID = "iceandfire";
	private static final String SCALINGHEALTH_MODID = "scalinghealth";
	private static Boolean isRLCombatLoaded = null;
	private static Boolean isSwitchbowLoaded = null;
	private static Boolean isIceAndFireLoaded = null;
	private static Boolean isScalingHealthLoaded = null;
	
	public static boolean isRLCombatLoaded() {
		if(isRLCombatLoaded == null) isRLCombatLoaded = Loader.isModLoaded(RLCOMBAT_MODID) && isRLCombatCorrectVersion();
		return isRLCombatLoaded;
	}
	
	public static boolean isSwitchbowLoaded() {
		if(isSwitchbowLoaded == null) isSwitchbowLoaded = Loader.isModLoaded(SWITCHBOW_MODID);
		return isSwitchbowLoaded;
	}
	
	public static boolean isIceAndFireLoaded() {
		if(isIceAndFireLoaded == null) isIceAndFireLoaded = Loader.isModLoaded(INF_MODID);
		return isIceAndFireLoaded;
	}
	
	public static boolean isScalingHealthLoaded() {
		if(isScalingHealthLoaded == null) isScalingHealthLoaded = Loader.isModLoaded(SCALINGHEALTH_MODID);
		return isScalingHealthLoaded;
	}
	
	private static boolean isRLCombatCorrectVersion() {
		String[] arrOfStr = Loader.instance().getIndexedModList().get(RLCOMBAT_MODID).getVersion().split("\\.");
		try {
			int i = Integer.parseInt(String.valueOf(arrOfStr[0]));
			if(i == 2) return true;
		}
		catch(Exception ignored) { }
		return false;
	}
}