package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.energy.IHasEnergy;

/**
 * @author Kanomiya
 *
 */
public class EnergyAcceptedEvent extends Event implements IEventEnergy {

	protected EnergyType energyType;
	protected IHasEnergy donor, acceptor;
	protected int expectedAmount, actualAmount;

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param expectedAmount 想定されたエネルギー移動量
	 * @param actualAmount 実際のエネルギー移動量
	 * @param donor エネルギー供与者
	 * @param acceptor エネルギー受容者
	 */
	public EnergyAcceptedEvent(EnergyType energyType, int expectedAmount, int actualAmount, IHasEnergy donor, IHasEnergy acceptor)
	{
		this.energyType = energyType;
		this.expectedAmount = expectedAmount;
		this.actualAmount = actualAmount;

		this.donor = donor;
		this.acceptor = acceptor;
	}


	/**
	 * @return energyType エネルギータイプ
	 */
	public EnergyType getEnergyType() {
		return energyType;
	}

	/**
	 * @return expectedAmount 想定されたエネルギー移動量
	 */
	public int getExpectedAmount() {
		return expectedAmount;
	}

	/**
	 * @return actualAmount 実際のエネルギー移動量
	 */
	public int getActualAmount() {
		return actualAmount;
	}


	/**
	 * @return donor エネルギー供与者
	 */
	public IHasEnergy getDonor() {
		return donor;
	}

	/**
	 * @return acceptor エネルギー受容者
	 */
	public IHasEnergy getAcceptor() {
		return acceptor;
	}

}
