package com.kanomiya.mcmod.energyway.api.energy;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
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
	default Energy getEnergy(EnergyType energyType)
	{
		return energyMap().get(energyType);
	}

	default void setEnergy(Energy energy)
	{
		energyMap().put(energy.getEnergyType(), energy);
	}

	default void removeEnergy(EnergyType energyType)
	{
		energyMap().remove(energyType);
	}

	/**
	 * @return エネルギーのマップ
	 */
	Map<EnergyType, Energy> energyMap();

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

	default void onUpdate() {  }


	default NBTTagCompound serializeEnergyOwnerNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		Iterator<Energy> energyItr = energyMap().values().iterator();

		while (energyItr.hasNext())
		{
			Energy energy = energyItr.next();
			compound.setTag(energy.getEnergyType().getId(), energy.serializeNBT());
		}

		return compound;
	}

	default void deserializeEnergyOwnerNBT(NBTTagCompound compound)
	{
		Iterator<String> idItr = compound.getKeySet().iterator();
		Map<EnergyType, Energy> energyMap = energyMap();

		while (idItr.hasNext())
		{
			String id = idItr.next();
			EnergyType energyType = EnergyWayAPI.getEnergyTypeById(id);
			NBTTagCompound eachTag = compound.getCompoundTag(id);

			if (energyMap.containsKey(energyType))
			{
				energyMap.get(energyType).deserializeNBT(eachTag);;
			} else
			{
				energyMap.put(energyType, Energy.createFromNBT(eachTag));
			}
		}

	}

}
