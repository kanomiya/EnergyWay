package com.kanomiya.mcmod.energyway.api;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.Entity;
import scala.actors.threadpool.Arrays;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyOwnerInitRegistry {

	public static final EnergyOwnerInitRegistry INSTANCE = new EnergyOwnerInitRegistry();
	protected EnergyOwnerInitRegistry() {  }


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
		if (! entityPropTempletes.containsKey(clazz)) return Maps.newHashMap();
		return entityPropTempletes.get(clazz);
	}

}
