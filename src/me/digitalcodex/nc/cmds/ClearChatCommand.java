package me.digitalcodex.nc.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.timberdnd.utils.SimpleUtils;

/**
 * Created by DigitalCodex on Dec 13, 2016.
 */
public class ClearChatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("cc.admin")) {
			sender.sendMessage(SimpleUtils.translate("&9You do not have permission for this."));
			return false;
		}
		for(Player p: Bukkit.getOnlinePlayers()) {
			for(int i = 0; i < 200; i++) {
				p.sendMessage(" ");
			}
			p.sendMessage(SimpleUtils.translate("&7Chat was cleared by &9" + sender.getName()));
		}
		return false;
	}
}
