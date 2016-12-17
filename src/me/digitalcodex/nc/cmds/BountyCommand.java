package me.digitalcodex.nc.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by DigitalCodex on Dec 15, 2016.
 */
public class BountyCommand implements CommandExecutor {
	
	/*
	 * 
	 *  /bounty
	 *  	set <player> <amount> - Set a bounty of <amount> on <player>
	 *  	withdraw <player> - Withdraw the bounty you've set on <player>
	 *   	increase <player> <amount> - Increase the bounty reward you've set on <player> by <amount>
	 *      active - View active bounties.
	 * 
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}
}
