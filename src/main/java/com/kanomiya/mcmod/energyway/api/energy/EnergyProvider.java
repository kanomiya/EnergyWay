package com.kanomiya.mcmod.energyway.api.energy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.event.EnergyAcceptedEvent;


/**
 * @author Kanomiya
 *
 */
public class EnergyProvider implements INBTSerializable<NBTTagCompound> {

	public static EnergyProvider fromNBT(NBTTagCompound nbt)
	{
		EnergyProvider provider = new EnergyProvider();
		provider.deserializeNBT(nbt);
		return provider;
	}

	protected Map<EnergyType, Energy> energyMap;
	protected NBTTagCompound customData;


	public EnergyProvider()
	{
		energyMap = Maps.newHashMap();
	}

	public EnergyProvider(Energy... defaultz)
	{
		this();

		Arrays.asList(defaultz).forEach(this::setEnergy);
	}




	public boolean hasEnergy(EnergyType energyType)
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
	public Energy getEnergy(EnergyType energyType)
	{
		return energyMap().get(energyType);
	}

	public void setEnergy(Energy energy)
	{
		energyMap().put(energy.getEnergyType(), energy);
	}

	public void removeEnergy(EnergyType energyType)
	{
		energyMap().remove(energyType);
	}

	/**
	 * @return エネルギーのマップ
	 */
	public Map<EnergyType, Energy> energyMap()
	{
		return energyMap;
	}

	public boolean hasCustomData()
	{
		NBTTagCompound customData = getCustomData();
		return customData != null && ! customData.hasNoTags();
	}

	/**
	 *
	 * @return カスタムデータ
	 */
	public NBTTagCompound getCustomData()
	{
		return customData;
	}

	/**
	 *
	 * @param customData カスタムデータ
	 */
	public void setCustomData(NBTTagCompound customData)
	{
		this.customData = customData;
	}

	/**
	 *
	 * 外部からエネルギーを受容させる
	 *
	 * @param donor 供与者
	 * @param energyType エネルギータイプ
	 * @param amount 受容エネルギー量
	 */
	public void accept(EnergyProvider donor, EnergyType energyType, int amount)
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
	public void onEnergyAccepted(EnergyType energyType, int expectedAmount, int actualAmount, EnergyProvider donor) {  }


	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		Iterator<Energy> energyItr = energyMap().values().iterator();

		while (energyItr.hasNext())
		{
			Energy energy = energyItr.next();
			compound.setTag(energy.getEnergyType().getId(), energy.serializeNBT());
		}

		if (hasCustomData()) compound.setTag("customData", getCustomData());

		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound)
	{
		Iterator<String> idItr = compound.getKeySet().iterator();
		Map<EnergyType, Energy> energyMap = energyMap();

		while (idItr.hasNext())
		{
			String id = idItr.next();
			EnergyType energyType = EnergyWayAPI.getEnergyTypeById(id);
			if (energyType == null) continue;

			NBTTagCompound eachTag = compound.getCompoundTag(id);

			if (energyMap.containsKey(energyType))
			{
				energyMap.get(energyType).deserializeNBT(eachTag);;
			} else
			{
				energyMap.put(energyType, Energy.createFromNBT(eachTag));
			}
		}

		if (compound.hasKey("customData")) setCustomData(compound.getCompoundTag("customData"));
	}




	/**
	 *
	 * In charge of EnergyProvider's Reader/Writer ??
	 *
	 * @author Kanomiya
	 *
	 */
	public static class Storage implements Capability.IStorage<EnergyProvider>
	{
		/**
		* @inheritDoc
		*/
		@Override
		public NBTBase writeNBT(Capability<EnergyProvider> capability, EnergyProvider instance, EnumFacing side)
		{
			return instance.serializeNBT();
		}

		/**
		* @inheritDoc
		*/
		@Override
		public void readNBT(Capability<EnergyProvider> capability, EnergyProvider instance, EnumFacing side, NBTBase nbt)
		{
			if (nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}

}
