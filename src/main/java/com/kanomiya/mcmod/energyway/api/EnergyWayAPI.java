package com.kanomiya.mcmod.energyway.api;

import java.util.Map;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyWayAPI {

	protected static final Map<String, EnergyType> idToEnergyType = Maps.newHashMap();

	public static final String ID_DATA = "energyway";

	@CapabilityInject(value = EnergyProvider.class)
	public static final Capability<EnergyProvider> capabilityEnergy = null;

	public static void registerEnergyType(EnergyType energyType)
	{
		idToEnergyType.put(energyType.getId(), energyType);
	}

	public static EnergyType getEnergyTypeById(String id)
	{
		if (! idToEnergyType.containsKey(id)) return null;
		return idToEnergyType.get(id);
	}

	public static Map<String, EnergyType> energyTypeMap()
	{
		return idToEnergyType;
	}



}
