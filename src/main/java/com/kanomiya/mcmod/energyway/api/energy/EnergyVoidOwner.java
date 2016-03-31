package com.kanomiya.mcmod.energyway.api.energy;


/**
 * @author Kanomiya
 *
 */
public class EnergyVoidOwner implements IHasEnergy {

	protected Energy energyVoid;

	public EnergyVoidOwner(EnergyVoid energyVoid)
	{
		this.energyVoid = energyVoid;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Energy getEnergy(EnergyType energyType)
	{
		return energyVoid;
	}


}
