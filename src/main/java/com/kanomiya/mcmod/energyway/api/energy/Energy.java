package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;

/**
 * @author Kanomiya
 *
 */
public class Energy implements INBTSerializable<NBTTagCompound> {
	protected static final EnergyType energyTypeVoid = new EnergyType("void");
	protected static final Energy energyVoid = Energy.createEmpty(Energy.energyTypeVoid, 0);
	public static final EnergyProvider VOID = new EnergyProvider(energyVoid)
	{
		@Override
		public Energy getEnergy(EnergyType energyType)
		{
			return energyVoid;
		}
	};

	protected static final EnergyType energyTypeInfinity = new EnergyType("infinity");
	protected static final Energy energyInfinity = Energy.createUnlimited(Energy.energyTypeInfinity, Integer.MAX_VALUE);
	public static final EnergyProvider INFINITY = new EnergyProvider(energyInfinity)
	{
		@Override
		public Energy getEnergy(EnergyType energyType)
		{
			return energyInfinity;
		}
	};

	static
	{
		EnergyWayAPI.energyRegistry.put(new ResourceLocation("void"), energyTypeVoid);
		EnergyWayAPI.energyRegistry.put(new ResourceLocation("infinity"), energyTypeInfinity);
	}


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
		energy.deserializeNBT(nbt);
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
		if (energyType == Energy.energyTypeVoid) return 0;

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
		if (energyType == energyTypeInfinity) return 0;

		amount = Math.max(0, amount);

		int shortage = Math.max(0, amount -this.amount);
		this.amount -= amount -shortage;
		return shortage;
	}

	/**
	 *
	 * NBTから復元する
	 *
	 * @param compound 読み込み元のNBT
	 */
	@Override
	public void deserializeNBT(NBTTagCompound compound)
	{
		energyType = EnergyWayAPI.energyRegistry.get(new ResourceLocation(compound.getString("id"))); // TODO UNKNOWN
		capacity = compound.getInteger("capacity");
		amount = compound.getInteger("amount");
	}

	/**
	 *
	 * NBTへ複製する
	 *
	 * @return nbt 書き込み済みのNBT
	 */
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();

		ResourceLocation id = EnergyWayAPI.energyRegistry.inverse().get(energyType);
		compound.setString("id", id == null ? "null" : id.toString());
		compound.setInteger("capacity", capacity);
		compound.setInteger("amount", amount);

		return compound;
	}

	/**
	 *
	 * @return 複製
	 */
	public Energy copy()
	{
		return new Energy(energyType, capacity, amount);
	}


}
