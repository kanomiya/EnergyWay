package com.kanomiya.mcmod.energyway.energy;

import net.minecraft.nbt.NBTTagCompound;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.energy.IEnergyBattery;

/**
 * @author Kanomiya
 *
 */
public class SimpleEnergyBattery implements IEnergyBattery {

	protected EnergyType energyType;
	protected int capacity;
	protected int amount;

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @param amount 初期量
	 */
	public SimpleEnergyBattery(EnergyType energyType, int capacity, int amount)
	{
		this.energyType = energyType;
		this.capacity = Math.max(0, capacity);
		this.amount = Math.min(this.capacity, amount);
	}

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 */
	public SimpleEnergyBattery(EnergyType energyType, int capacity)
	{
		this(energyType, capacity, 0);
	}


	/**
	 *
	 * @inheritDoc
	 */
	@Override
	public EnergyType getEnergyType()
	{
		return energyType;
	}

	/**
	 *
	 * @inheritDoc
	 */
	@Override
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 *
	 * @inheritDoc
	 */
	@Override
	public int getAmount()
	{
		return amount;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public int accept(int amount) {
		amount = Math.max(0, amount);

		int rest = this.amount +amount -capacity;
		this.amount += amount -rest;
		return rest;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public int release(int amount) {
		amount = Math.max(0, amount);

		int shortage = amount -this.amount;
		this.amount -= amount -shortage;
		return shortage;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		energyType = EnergyWayAPI.getEnergyTypeById(nbt.getString("id")); // TODO UNKNOWN
		capacity = nbt.getInteger("capacity");
		amount = nbt.getInteger("amount");
	}

	/**
	* @inheritDoc
	*/
	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("id", energyType.getId());
		nbt.setInteger("capacity", capacity);
		nbt.setInteger("amount", amount);

		return nbt;
	}



}
