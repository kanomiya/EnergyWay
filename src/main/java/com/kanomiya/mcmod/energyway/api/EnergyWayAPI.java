package com.kanomiya.mcmod.energyway.api;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.props.EntityPropertiesEnergy;

/**
 * @author Kanomiya
 *
 */
public class EnergyWayAPI {

	protected static final Map<String, EnergyType> idToEnergyType = Maps.newHashMap();

	public static final String ID_DATA = "energyway";

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

	public static EntityPropertiesEnergy getProperties(Entity entity)
	{
		IExtendedEntityProperties props = entity.getExtendedProperties(EnergyWayAPI.ID_DATA);
		if (props instanceof EntityPropertiesEnergy) return (EntityPropertiesEnergy) props;
		return null;
	}

}
