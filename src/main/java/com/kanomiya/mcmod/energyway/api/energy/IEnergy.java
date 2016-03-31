package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.energyway.api.event.EventEnergyAccepted;

/**
 * @author Kanomiya
 *
 */
public interface IEnergy {

	/**
	 *
	 * @return エネルギータイプ
	 */
	EnergyType getEnergyType();


	/**
	 *
	 * @return エネルギー容量
	 */
	default int getCapacity()
	{
		return Integer.MAX_VALUE;
	}

	/**
	 *
	 * @return エネルギー量
	 */
	int getAmount();


	default boolean isFull()
	{
		return getAmount() == getCapacity();
	}

	default int getRest()
	{
		return getCapacity() -getAmount();
	}


	/**
	 *
	 * 外部からエネルギーを受容させます
	 *
	 * @param donor 供与者
	 * @param amount 受容エネルギー量
	 */
	default void accept(IEnergy donor, int amount)
	{
		int actualAmount = Math.min(amount, donor.getAmount());

		donor.release(actualAmount);
		int rest = accept(actualAmount);
		donor.accept(rest);

		MinecraftForge.EVENT_BUS.post(new EventEnergyAccepted(getEnergyType(), amount, actualAmount, donor, this));
	}

	/**
	 *
	 * 外部からエネルギーを受容させます
	 *
	 * @param amount 受容エネルギー量
	 * @return 余り
	 */
	int accept(int amount);

	/**
	 *
	 * 外部へエネルギーを排出させます
	 *
	 * @param amount 排出エネルギー量
	 * @return 不足
	 */
	int release(int amount);


	/**
	 *
	 * NBTから復元します
	 *
	 * @param nbt 読み込み元のNBT
	 */
	void readFromNBT(NBTTagCompound nbt);

	/**
	 *
	 * NBTへ複製します
	 *
	 * @return nbt 書き込み済みのNBT
	 */
	NBTTagCompound writeToNBT();



}
