package me.digitalcodex.nc.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.timberdnd.utils.SimpleUtils;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sendAll(sender,
				SimpleUtils.centerText("&9&lNightFactions: &7&lServer-Info"),
				"&7Server Address: &9play.nightfactions.com",
				"&7Forums: &9Coming soon",
				"&7Discord Invitation: &9Coming soon",
				"&7Store Address: &9store.nightfactions.com"
				);
		return false;
	}
	
	public void sendAll(CommandSender sender, String...strings) {
		for(String string: strings) {
			sender.sendMessage(SimpleUtils.translate(string));
		}
	}
}
