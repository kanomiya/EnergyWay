package com.kanomiya.mcmod.energyway.api;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.entity.Entity;
import scala.actors.threadpool.Arrays;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyProviderInitRegistry {

	public static final EnergyProviderInitRegistry INSTANCE = new EnergyProviderInitRegistry();
	protected EnergyProviderInitRegistry() {  }


	protected Map<Class<? extends Entity>, Map<EnergyType, Energy>> entityPropTempletes = Maps.newHashMap();

	public void addEntityPropTemplete(Class<? extends Entity> clazz, Energy... energy)
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

	public Map<EnergyType, Energy> getEntityPropTemplete(Class<? extends Entity> clazz)
	{
		Map<EnergyType, Energy> map = Maps.newHashMap();

		entityPropTempletes.keySet().forEach(new Consumer<Class<? extends Entity>>()
		{
			@Override
			public void accept(Class<? extends Entity> key) {
				if (key.isAssignableFrom(clazz))
				{
					entityPropTempletes.get(key).forEach((EnergyType t, Energy e) -> map.put(t, e));;
				}
			}
		});

		return map;
	}

}
