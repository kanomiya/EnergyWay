package com.kanomiya.mcmod.energyway;

import java.util.Map;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.energyway.api.EnergyProviderRegistry;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.command.CommandEnergyWay;

@Mod(modid = EnergyWay.MODID)
public class EnergyWay
{
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


	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent event)
	{
		if (event.getObject() instanceof ICapabilityProvider)
		{
			Map<EnergyType, Energy> templete = EnergyProviderRegistry.getTemplete(((ICapabilityProvider) event.getObject()).getClass());


			if (! templete.isEmpty())
			{
				// TODO event.addCapability();
			}

		}



	}

}
