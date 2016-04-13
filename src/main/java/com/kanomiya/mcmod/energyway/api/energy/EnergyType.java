package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.util.text.translation.I18n;



/**
 * @author Kanomiya
 *
 */
public class EnergyType {

	protected String unlocalizedName;

	public EnergyType(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	public String getDisplayName()
	{
		return I18n.translateToLocal(getUnlocalizedName());
	}

}
