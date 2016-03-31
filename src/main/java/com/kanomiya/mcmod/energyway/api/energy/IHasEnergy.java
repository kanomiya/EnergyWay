package com.kanomiya.mcmod.energyway.api.energy;

/**
 * @author Kanomiya
 *
 */
public interface IHasEnergy {

	/**
	 *
	 * @return エネルギータイプ
	 */
	EnergyType getEnergyType();

	/**
	 *
	 * @return エネルギー量
	 */
	int getAmount();

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



}
