package com.kanomiya.mcmod.energyway.api.energy;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;

/**
 * @author Kanomiya
 *
 */
public class EnergyType {

	protected String id;

	public EnergyType(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}


	public EnergyType register()
	{
		EnergyWayAPI.registerEnergyType(this);
		return this;
	}

}
