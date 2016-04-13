package com.kanomiya.mcmod.energyway.api;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import scala.actors.threadpool.Arrays;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyProviderRegistry
{
	protected static Map<Class<? extends ICapabilityProvider>, Map<EnergyType, Energy>> entityPropTempletes = Maps.newHashMap();

	// TODO EVENT LISTENER

	public static void addTemplete(Class<? extends ICapabilityProvider> clazz, Energy... energy)
	{
		Iterator<Energy> itr = Arrays.asList(energy).iterator();

		if (! entityPropTempletes.containsKey(clazz)) entityPropTempletes.put(clazz, Maps.newHashMap());
		Map<EnergyType, Energy> map = entityPropTempletes.get(clazz);

		while (itr.hasNext())
		{
			Energy e = itr.next();
			map.put(e.getEnergyType(), e);
		}

	}

	public static Map<EnergyType, Energy> getTemplete(Class<? extends ICapabilityProvider> clazz)
	{
		Map<EnergyType, Energy> map = Maps.newHashMap();

		entityPropTempletes.keySet().forEach(new Consumer<Class<? extends ICapabilityProvider>>()
		{
			@Override
			public void accept(Class<? extends ICapabilityProvider> key) {
				if (key.isAssignableFrom(clazz))
				{
					entityPropTempletes.get(key).forEach((EnergyType t, Energy e) -> map.put(t, e));;
				}
			}
		});

		return map;
	}

}
