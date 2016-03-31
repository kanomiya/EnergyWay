package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.energy.IEnergy;

/**
 * @author Kanomiya
 *
 */
public class EventEnergyAccepted extends Event implements IEventEnergy {

	protected EnergyType energyType;
	protected IEnergy donor, acceptor;
	protected int expectedAmount, actualAmount;

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param expectedAmount 想定されたエネルギー移動量
	 * @param actualAmount 実際のエネルギー移動量
	 * @param donor エネルギー供与者
	 * @param acceptor エネルギー受容者
	 */
	public EventEnergyAccepted(EnergyType energyType, int expectedAmount, int actualAmount, IEnergy donor, IEnergy acceptor)
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
	public IEnergy getDonor() {
		return donor;
	}

	/**
	 * @return acceptor エネルギー受容者
	 */
	public IEnergy getAcceptor() {
		return acceptor;
	}

}
