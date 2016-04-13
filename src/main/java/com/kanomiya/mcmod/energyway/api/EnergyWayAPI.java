package com.kanomiya.mcmod.energyway.api;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class EnergyWayAPI {

	public static final BiMap<ResourceLocation, EnergyType> energyRegistry = HashBiMap.create();

	public static final String ID_DATA = "energyway";

	@CapabilityInject(value = EnergyProvider.class)
	public static final Capability<EnergyProvider> capEnergy = null;


	public static boolean hasEnergyProvider(ICapabilityProvider capProvider, EnumFacing facing)
	{
		return capProvider.hasCapability(capEnergy, facing);
	}

	public static EnergyProvider getEnergyProvider(ICapabilityProvider capProvider, EnumFacing facing)
	{
		if (! hasEnergyProvider(capProvider, facing)) return null;
		return capProvider.getCapability(capEnergy, facing);
	}




}
