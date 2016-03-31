package com.kanomiya.mcmod.energyway.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.kanomiya.mcmod.energyway.EnergyWay;
import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.props.EntityPropertiesEnergy;


/**
 * @author Kanomiya
 *
 */
public class EventHandlerEntityInit {

	public static final EventHandlerEntityInit INSTANCE = new EventHandlerEntityInit();

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		Entity entity = event.entity;

		if (entity.getExtendedProperties(EnergyWayAPI.ID_DATA) == null)
		{
			EntityPropertiesEnergy prop = new EntityPropertiesEnergy(entity);

			if (entity.registerExtendedProperties(EnergyWayAPI.ID_DATA, prop).equals(""))
			{
				EnergyWay.logger.error("Failed to register (id: " + entity.getEntityId() + ")");
			}

		}

	}



}
