package com.kanomiya.mcmod.energyway.api.energy;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Kanomiya
 *
 */
public class SimpleEnergyOwner implements IHasEnergy {

	protected Map<EnergyType, Energy> energyMap;

	public SimpleEnergyOwner(Energy... energy)
	{
		energyMap = Maps.newHashMap();
		Arrays.asList(energy).forEach(e -> energyMap.put(e.energyType, e));
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Energy getEnergy(EnergyType energyType)
	{
		return energyMap.get(energyType);
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Map<EnergyType, Energy> energyMap()
	{
		return energyMap;
	}


}
