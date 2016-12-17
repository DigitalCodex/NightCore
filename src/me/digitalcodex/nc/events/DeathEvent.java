package me.digitalcodex.nc.events;

import java.text.NumberFormat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.digitalcodex.nc.NightCore;
import us.timberdnd.api.HeadManager;

/**
 * Created by DigitalCodex on Dec 15, 2016.
 */
public class DeathEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		if(!(event.getEntity().getKiller() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		Player killer = event.getEntity().getKiller();
		HeadManager hManager = new HeadManager(player.getName());
		hManager.headName("&9" + player.getName());
		double bal = NightCore.econ.getBalance(player.getName());
		String format = NumberFormat.getIntegerInstance().format(getPercentage((int) bal, 10));
		hManager.addLore("&7Killer: &9" + killer.getName())
		.addLore("&7Worth: &9" + format)
		.addLore(" ")
		.addLore("&7Right click to sell this head and")
		.addLore("&7collect additional bounty if any.");
		player.getWorld().dropItemNaturally(player.getLocation(), hManager.build());
	}

	public int getPercentage(int total, int amount) {
		return (int) (total * ((float) amount / 100.0f));
	}
}
