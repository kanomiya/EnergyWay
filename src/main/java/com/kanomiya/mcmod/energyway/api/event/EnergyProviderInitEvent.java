package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;

/**
 *
 * Entityにのみ対応
 *
 * @author Kanomiya
 *
 */
public class EnergyProviderInitEvent extends Event implements IEnergyEvent {

	protected EnergyProvider owner;

	/**
	 *
	 * @param owner 初期化するエネルギー所有者
	 */
	public EnergyProviderInitEvent(EnergyProvider owner)
	{
		this.owner = owner;
	}

	public EnergyProvider getOwner()
	{
		return owner;
	}

}
