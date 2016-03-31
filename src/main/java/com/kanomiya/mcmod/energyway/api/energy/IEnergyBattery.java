package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.energyway.api.event.EventEnergyAccepted;

/**
 * @author Kanomiya
 *
 */
public interface IEnergyBattery extends IHasEnergy {

	/**
	 *
	 * @return エネルギー容量
	 */
	int getCapacity();

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
	 * @param amount 受容エネルギー量
	 */
	default void accept(IEnergyBattery battery, int amount)
	{
		int actualAmount = Math.min(amount, battery.getAmount());

		battery.release(actualAmount);
		int rest = accept(actualAmount);
		battery.accept(rest);

		MinecraftForge.EVENT_BUS.post(new EventEnergyAccepted(getEnergyType(), amount, actualAmount, battery, this));
	}

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
