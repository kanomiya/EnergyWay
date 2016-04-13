package com.kanomiya.mcmod.energyway;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.event.EnergyProviderCreateEvent;
import com.kanomiya.mcmod.energyway.command.CommandEnergyWay;

@Mod(modid = EnergyWayAPI.MODID)
public class EnergyWay
{

	@Mod.Instance(EnergyWayAPI.MODID)
	public static final EnergyWay instance = null;

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		CapabilityManager.INSTANCE.register(EnergyProvider.class, new EnergyProvider.Storage(), EnergyProvider::new);

		MinecraftForge.EVENT_BUS.register(this);
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
			EnergyProvider provider = new EnergyProvider();
			EnergyProviderCreateEvent e = new EnergyProviderCreateEvent(provider);

			MinecraftForge.EVENT_BUS.post(e);

			event.addCapability(new ResourceLocation(EnergyWayAPI.DOMAIN_NAME), provider);
		}


	}

}
