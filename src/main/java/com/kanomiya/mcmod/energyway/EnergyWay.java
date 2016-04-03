package com.kanomiya.mcmod.energyway;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.command.CommandEnergyWay;

@Mod(modid = EnergyWay.MODID)
public class EnergyWay {

	public static final String MODID = "energyway";

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		CapabilityManager.INSTANCE.register(EnergyProvider.class, new EnergyProvider.Storage(), EnergyProvider::new);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandEnergyWay());

	}

}
