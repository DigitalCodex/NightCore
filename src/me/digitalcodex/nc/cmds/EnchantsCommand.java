package me.digitalcodex.nc.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.digitalcodex.nc.inventories.EnchantsInventory;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class EnchantsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Command is only for players!");
			return false;
		}
		
		Player player = (Player) sender;
		player.openInventory(EnchantsInventory.getInventory());
		return false;
	}
}
