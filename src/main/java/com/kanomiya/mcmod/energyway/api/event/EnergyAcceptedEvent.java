package com.kanomiya.mcmod.energyway.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyAcceptedEvent extends Event implements IEnergyEvent
{
	protected EnergyType energyType;
	protected EnergyProvider donor, acceptor;
	protected int expectedAmount, actualAmount;

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param expectedAmount 想定されたエネルギー移動量
	 * @param actualAmount 実際のエネルギー移動量
	 * @param donor エネルギー供与者
	 * @param acceptor エネルギー受容者
	 */
	public EnergyAcceptedEvent(EnergyType energyType, int expectedAmount, int actualAmount, EnergyProvider donor, EnergyProvider acceptor)
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
	public EnergyType getEnergyType()
	{
		return energyType;
	}

	/**
	 * @return expectedAmount 想定されたエネルギー移動量
	 */
	public int getExpectedAmount()
	{
		return expectedAmount;
	}

	/**
	 * @return actualAmount 実際のエネルギー移動量
	 */
	public int getActualAmount()
	{
		return actualAmount;
	}


	/**
	 * @return donor エネルギー供与者
	 */
	public EnergyProvider getDonor()
	{
		return donor;
	}

	/**
	 * @return acceptor エネルギー受容者
	 */
	public EnergyProvider getAcceptor()
	{
		return acceptor;
	}

}
