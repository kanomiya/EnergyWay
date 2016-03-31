package com.kanomiya.mcmod.energyway.api.energy;

import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.energyway.api.event.EnergyAcceptedEvent;


/**
 * @author Kanomiya
 *
 */
public interface IHasEnergy {

	default boolean hasEnergy(EnergyType energyType)
	{
		return getEnergy(energyType) != null;
	}

	/**
	 *
	 * エネルギータイプに対応するエネルギーを取得する
	 *
	 * @param energyType エネルギータイプ
	 * @return
	 */
	Energy getEnergy(EnergyType energyType);

	/**
	 *
	 * 外部からエネルギーを受容させる
	 *
	 * @param donor 供与者
	 * @param energyType エネルギータイプ
	 * @param amount 受容エネルギー量
	 */
	default void accept(IHasEnergy donor, EnergyType energyType, int amount)
	{
		if (! hasEnergy(energyType)) return;

		Energy energy = getEnergy(energyType);
		int actualAmount = energy.accept(donor.getEnergy(energyType), amount);

		onEnergyAccepted(energyType, amount, actualAmount, donor);

		MinecraftForge.EVENT_BUS.post(new EnergyAcceptedEvent(energyType, amount, actualAmount, donor, this));
	}

	/**
	 *
	 * acceptメソッドを介してエネルギー受容が達成されたとき呼ばれる
	 *
	 * @param energyType エネルギータイプ
	 * @param expectedAmount 想定されたエネルギー移動量
	 * @param actualAmount 実際のエネルギー移動量
	 * @param donor エネルギー供与者
	 */
	default void onEnergyAccepted(EnergyType energyType, int expectedAmount, int actualAmount, IHasEnergy donor) {  }

}
