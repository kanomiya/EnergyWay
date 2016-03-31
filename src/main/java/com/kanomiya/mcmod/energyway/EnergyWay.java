package com.kanomiya.mcmod.energyway;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.energyway.event.EventHandlerEntityInit;

@Mod(modid = EnergyWay.MODID)
public class EnergyWay {

	public static final String MODID = "energyway";

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		MinecraftForge.EVENT_BUS.register(EventHandlerEntityInit.INSTANCE);
	}

}
