package com.kanomiya.mcmod.energyway.api.energy;

/**
 * @author Kanomiya
 *
 */
public class EnergyVoid extends Energy {

	public static EnergyVoid createVoid()
	{
		return new EnergyVoid();
	}

	protected EnergyVoid() {
		super(null, 0, 0);
	}

	@Override
	protected int accept(int amount)
	{
		return 0;
	}

}
