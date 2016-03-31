package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.IHasEnergy;

/**
 *
 * Entityにのみ対応
 *
 * @author Kanomiya
 *
 */
public class EnergyOwnerInitEvent extends Event implements IEventEnergy {

	protected IHasEnergy owner;

	/**
	 *
	 * @param owner 初期化するエネルギー所有者
	 */
	public EnergyOwnerInitEvent(IHasEnergy owner)
	{
		this.owner = owner;
	}

	public IHasEnergy getOwner()
	{
		return owner;
	}

}
