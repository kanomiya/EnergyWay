package com.kanomiya.mcmod.energyway.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class CommandEnergyWay extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "energyway";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "command.energyway.usage";
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		List<String> list = new ArrayList<String>();

		if (args.length == 1)
		{
			list.add("give");
		}


		if (args.length == 2)
		{
			if (args[0].equals("give"))
			{
				EnergyWayAPI.energyRegistry.forEach((k, v) -> list.add(k.toString()));
			}
		}

		return list;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		int argLength = args.length;

		Entity entity = sender.getCommandSenderEntity();

		if (EnergyWayAPI.hasEnergyProvider(entity, null))
		{
			EnergyProvider provider = EnergyWayAPI.getEnergyProvider(entity, null);

			if (argLength == 3 && args[0].equals("give") && args[2].matches("-*[0-9]+")) {
				int amount = Integer.valueOf(args[2]);
				EnergyType energyType = EnergyWayAPI.energyRegistry.get(new ResourceLocation(args[1]));

				if (energyType != null)
				{
					provider.accept(Energy.INFINITY, energyType, amount);

					sender.addChatMessage(new TextComponentTranslation("command.energyway.give",
							sender.getDisplayName(),
							new TextComponentString("" +amount),
							new TextComponentTranslation("energyway.energyType." + energyType.getUnlocalizedName())));
				} else
				{
					throw new CommandException("command.energyway.noSuchEnergyType", args[1]);
				}

			} else
			{
				throw new WrongUsageException(getCommandUsage(sender));
			}

			/* else if (argLength == 3 && args[0].equals("charge") && args[2].matches("-*[0-9]+") && entity != null && entity instanceof EntityLivingBase) {
				int amount = Integer.valueOf(args[2]);

				if (args[1].equals("item")) {
					ItemStack heldStack = ((EntityLivingBase) entity).getHeldItem();

					if (heldStack != null) {
						MagicStatus stackStatus = KMagicAPI.getMagicStatus(heldStack);

						if (stackStatus != null) {
							MagicStatus.dealMp(null, stackStatus, amount, true, false);
							sender.addChatMessage(new ChatComponentTranslation("kmagic.command.kmagic.mp", heldStack.getDisplayName(), new ChatComponentText("" +amount)));
							KMagicAPI.setMagicStatus(heldStack, stackStatus);

							success = true;

						}
					}
				}


			}*/

		}


	}
}
