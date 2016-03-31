package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.nbt.NBTTagCompound;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;

/**
 * @author Kanomiya
 *
 */
public class EnergyBundle implements IEnergy {

	/**
	 *
	 * 空で無限の容量を持つエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @return 空で無限の容量を持つエネルギー塊
	 */
	public static EnergyBundle createUnlimitedEmpty(EnergyType energyType)
	{
		return new EnergyBundle(energyType, Integer.MAX_VALUE, 0);
	}

	/**
	 *
	 * 無限の容量を持つエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @param amount 初期エネルギー量
	 * @return 空で無限の容量を持つエネルギー塊
	 */
	public static EnergyBundle createUnlimited(EnergyType energyType, int amount)
	{
		return new EnergyBundle(energyType, Integer.MAX_VALUE, amount);
	}

	/**
	 *
	 * 空のエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @return 空のエネルギー塊
	 */
	public static EnergyBundle createEmpty(EnergyType energyType, int capacity)
	{
		return new EnergyBundle(energyType, capacity, 0);
	}

	/**
	 *
	 * エネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @param amount 初期エネルギー量
	 * @return エネルギー塊
	 */
	public static EnergyBundle create(EnergyType energyType, int capacity, int amount)
	{
		return new EnergyBundle(energyType, capacity, amount);
	}

	public static EnergyBundle createFromNBT(NBTTagCompound nbt)
	{
		EnergyBundle energy = new EnergyBundle();
		energy.readFromNBT(nbt);
		return energy;
	}

	protected EnergyType energyType;
	protected int capacity;
	protected int amount;

	private EnergyBundle() {  }

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @param amount 初期量
	 */
	protected EnergyBundle(EnergyType energyType, int capacity, int amount)
	{
		this.energyType = energyType;
		this.capacity = Math.max(0, capacity);
		this.amount = Math.min(this.capacity, amount);
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

		int rest = Math.max(0, this.amount +amount -capacity);
		this.amount += amount -rest;
		return rest;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public int release(int amount) {
		amount = Math.max(0, amount);

		int shortage = Math.max(0, amount -this.amount);
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
