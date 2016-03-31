package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.nbt.NBTTagCompound;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;

/**
 * @author Kanomiya
 *
 */
public class Energy {

	/**
	 *
	 * 空で無限の容量を持つエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @return 空で無限の容量を持つエネルギー塊
	 */
	public static Energy createUnlimitedEmpty(EnergyType energyType)
	{
		return new Energy(energyType, Integer.MAX_VALUE, 0);
	}

	/**
	 *
	 * 無限の容量を持つエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @param amount 初期エネルギー量
	 * @return 空で無限の容量を持つエネルギー塊
	 */
	public static Energy createUnlimited(EnergyType energyType, int amount)
	{
		return new Energy(energyType, Integer.MAX_VALUE, amount);
	}

	/**
	 *
	 * 空のエネルギー塊を返します
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @return 空のエネルギー塊
	 */
	public static Energy createEmpty(EnergyType energyType, int capacity)
	{
		return new Energy(energyType, capacity, 0);
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
	public static Energy create(EnergyType energyType, int capacity, int amount)
	{
		return new Energy(energyType, capacity, amount);
	}

	public static Energy createFromNBT(NBTTagCompound nbt)
	{
		Energy energy = new Energy();
		energy.readFromNBT(nbt);
		return energy;
	}

	protected EnergyType energyType;
	protected int capacity;
	protected int amount;

	private Energy() {  }

	/**
	 *
	 * @param energyType エネルギータイプ
	 * @param capacity 容量
	 * @param amount 初期量
	 */
	protected Energy(EnergyType energyType, int capacity, int amount)
	{
		this.energyType = energyType;
		this.capacity = Math.max(0, capacity);
		this.amount = Math.min(this.capacity, amount);
	}

	/**
	 *
	 * @return エネルギータイプ
	 */
	public EnergyType getEnergyType()
	{
		return energyType;
	}

	/**
	 *
	 * @return エネルギー容量
	 */
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 *
	 * @return エネルギー量
	 */
	public int getAmount()
	{
		return amount;
	}


	/**
	 *
	 * @return エネルギーが満タンがどうか
	 */
	public boolean isFull()
	{
		return getAmount() == getCapacity();
	}

	/**
	 *
	 * @return エネルギー容量と現在エネルギー量の差分
	 */
	public int getRest()
	{
		return getCapacity() -getAmount();
	}


	/**
	 *
	 * 外部からエネルギーを受容させる
	 *
	 * @param donor 供与者
	 * @param amount 受容エネルギー量
	 * @return 実際に受容したエネルギー量
	 */
	public int accept(Energy donor, int amount)
	{
		int actualAmount = Math.min(amount, donor.getAmount());

		donor.release(actualAmount);
		int rest = accept(actualAmount);
		donor.accept(rest);

		return actualAmount;
	}

	/**
	 *
	 * 外部からエネルギーを受容させる
	 *
	 * @param amount 受容エネルギー量
	 * @return 余り
	 */
	protected int accept(int amount) {
		amount = Math.max(0, amount);

		int rest = Math.max(0, this.amount +amount -capacity);
		this.amount += amount -rest;
		return rest;
	}

	/**
	 *
	 * 外部へエネルギーを排出させる
	 *
	 * @param amount 排出エネルギー量
	 * @return 不足
	 */
	protected int release(int amount) {
		amount = Math.max(0, amount);

		int shortage = Math.max(0, amount -this.amount);
		this.amount -= amount -shortage;
		return shortage;
	}

	/**
	 *
	 * NBTから復元する
	 *
	 * @param nbt 読み込み元のNBT
	 */
	public void readFromNBT(NBTTagCompound nbt)
	{
		energyType = EnergyWayAPI.getEnergyTypeById(nbt.getString("id")); // TODO UNKNOWN
		capacity = nbt.getInteger("capacity");
		amount = nbt.getInteger("amount");
	}

	/**
	 *
	 * NBTへ複製する
	 *
	 * @return nbt 書き込み済みのNBT
	 */
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("id", energyType.getId());
		nbt.setInteger("capacity", capacity);
		nbt.setInteger("amount", amount);

		return nbt;
	}



}
