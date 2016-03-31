package com.kanomiya.mcmod.energyway.api.energy;

import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.energy.IHasEnergy;

/**
 * @author Kanomiya
 *
 */
public class SimpleEnergyOwner implements IHasEnergy {

	protected Energy energy;

	public SimpleEnergyOwner(Energy energy)
	{
		this.energy = energy;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Energy getEnergy(EnergyType energyType)
	{
		if (energyType != energy.getEnergyType()) return null;
		return energy;
	}


}
