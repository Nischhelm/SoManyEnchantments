package com.shultrea.rin.Prop_Sector;

public class PlayerProperties implements IPlayerProperties {
	
	boolean isResurrecting;
	double moralitasDamage;
	
	@Override
	public boolean isResurrecting() {
		return this.isResurrecting;
	}
	
	@Override
	public void setResurrecting(boolean flag) {
		this.isResurrecting = flag;
	}
	
	@Override
	public double getMoralitasDamage() {
		return moralitasDamage;
	}
	
	@Override
	public void setMoralitasDamage(double moralitasDamage) {
		this.moralitasDamage = moralitasDamage;
	}
}
