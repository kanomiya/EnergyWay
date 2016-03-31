package com.kanomiya.mcmod.energyway.api.props;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.energyway.api.EnergyOwnerInitRegistry;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;
import com.kanomiya.mcmod.energyway.api.energy.IHasEnergy;
import com.kanomiya.mcmod.energyway.api.event.EnergyOwnerInitEvent;

/**
 * @author Kanomiya
 *
 */
public class EntityPropertiesEnergy implements IExtendedEntityProperties, IHasEnergy {

	protected Entity entity;
	protected Map<EnergyType, Energy> energyMap;

	public EntityPropertiesEnergy(Entity entity)
	{
		this.entity = entity;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void init(Entity entity, World world)
	{
		energyMap = EnergyOwnerInitRegistry.INSTANCE.getEntityPropTemplete(entity.getClass());

		MinecraftForge.EVENT_BUS.post(new EnergyOwnerInitEvent(this));
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Map<EnergyType, Energy> energyMap() {
		return energyMap;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setTag("energy", serializeEnergyOwnerNBT());
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		deserializeEnergyOwnerNBT(compound.getCompoundTag("energy"));
	}


}
