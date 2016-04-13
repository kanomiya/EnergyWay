package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;

/**
 *
 * @author Kanomiya
 *
 */
public class EnergyProviderCreateEvent extends Event implements IEnergyEvent
{
	protected EnergyProvider provider;

	/**
	 *
	 * @param provider 初期化するエネルギー所有者
	 */
	public EnergyProviderCreateEvent(EnergyProvider provider)
	{
		this.provider = provider;
	}

	public EnergyProvider getProvider()
	{
		return provider;
	}

}
