package com.kanomiya.mcmod.energyway.api;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyWayAPI {

	protected static Map<String, EnergyType> idToEnergyType = Maps.newHashMap();

	public static void registerEnergyType(EnergyType energyType)
	{
		idToEnergyType.put(energyType.getId(), energyType);
	}

	public static EnergyType getEnergyTypeById(String id)
	{
		if (! idToEnergyType.containsKey(id)) return null;
		return idToEnergyType.get(id);
	}

}
