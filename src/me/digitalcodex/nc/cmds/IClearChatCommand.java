package me.digitalcodex.nc.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import us.timberdnd.utils.SimpleUtils;

/**
 * Created by DigitalCodex on Dec 13, 2016.
 */
public class IClearChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("cc.donor")) {
			sender.sendMessage(SimpleUtils.translate("&9You do not have permission for this."));
			return false;
		}
		for(int i = 0; i < 200; i++) {
			sender.sendMessage(" ");
		}
		sender.sendMessage(SimpleUtils.translate("&7Chat was cleared by &9" + sender.getName()));
		return false;
	}
}
