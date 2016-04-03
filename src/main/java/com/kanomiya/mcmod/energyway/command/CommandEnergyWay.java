package com.kanomiya.mcmod.energyway.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.kanomiya.mcmod.energyway.api.EnergyWayAPI;
import com.kanomiya.mcmod.energyway.api.energy.Energy;
import com.kanomiya.mcmod.energyway.api.energy.EnergyProvider;
import com.kanomiya.mcmod.energyway.api.energy.EnergyType;

/**
 * @author Kanomiya
 *
 */
public class CommandEnergyWay extends CommandBase {

	@Override public String getCommandName() {
		return "energyway";
	}

	@Override public String getCommandUsage(ICommandSender sender) {
		return "energyway.command.energyway.usage";
	}

	@Override public List addTabCompletionOptions(ICommandSender sender, String[] option, BlockPos pos) {
		List<String> list = new ArrayList<String>();

		if (option.length == 1) {
			list.add("accept");
		}


		if (option.length == 2) {
			if (option[0].equals("accept")) {
				list.addAll(EnergyWayAPI.energyTypeMap().keySet());
			}
		}

		return list;
	}

	@Override public void processCommand(ICommandSender sender, String[] args) {
		int argLength = args.length;
		if (argLength == 0) { missCommand(sender); return; }

		boolean success = false;

		Entity entity = sender.getCommandSenderEntity();
		EnergyProvider provider = entity.getCapability(EnergyWayAPI.capabilityEnergy, null);

		if (provider != null) {

			if (argLength == 3 && args[0].equals("accept") && args[2].matches("-*[0-9]+")) {
				int amount = Integer.valueOf(args[2]);
				EnergyType energyType = EnergyWayAPI.getEnergyTypeById(args[1]);

				if (energyType != null)
				{
					provider.accept(Energy.INFINITY, energyType, amount);

					sender.addChatMessage(new ChatComponentTranslation("energyway.command.energyway.accept",
							sender.getDisplayName(),
							new ChatComponentText("" +amount),
							new ChatComponentTranslation("energyway.energyType." + energyType.getId())));

					success = true;
				}

			}/* else if (argLength == 3 && args[0].equals("charge") && args[2].matches("-*[0-9]+") && entity != null && entity instanceof EntityLivingBase) {
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

		if (! success) {
			missCommand(sender);
		}

	}

	public void missCommand(ICommandSender sender) {
		sender.addChatMessage(new ChatComponentTranslation("energyway.command.miss"));
	}

}
