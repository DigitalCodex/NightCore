package me.digitalcodex.nc.events;

import java.text.ParseException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SkullMeta;

import me.digitalcodex.nc.NightCore;
import us.timberdnd.api.HeadManager;
import us.timberdnd.utils.SimpleUtils;

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
		int amount = getPercentage((int) bal, 10);
		String format = String.valueOf(amount);
		hManager.addLore("&7Killer: &9" + killer.getName())
		.addLore("&7Worth: &9$" + format)
		.addLore(" ")
		.addLore("&7Right click to sell this head and")
		.addLore("&7collect additional bounty if any.");
		NightCore.econ.withdrawPlayer(player.getName(), amount);
		player.getWorld().dropItemNaturally(player.getLocation(), hManager.build());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) throws ParseException {
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if(event.getItem() == null) {
			return;
		}
		if(event.getItem().getType() != Material.SKULL_ITEM) {
			return;
		}
		for(String s: event.getItem().getItemMeta().getLore()) {
			if(s.startsWith(SimpleUtils.translate("&7Worth: &9$"))) {
				String amount = s.replace(SimpleUtils.translate("&7Worth: &9$"), "");
			int newAmount = (int) Integer.parseInt(amount.replace(",", ""));
			NightCore.econ.depositPlayer(event.getPlayer().getName(), newAmount);
			SkullMeta meta = (SkullMeta) event.getItem().getItemMeta();
			if(event.getPlayer().getItemInHand().getAmount() > 1) {
			event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
			} else{
				event.getPlayer().setItemInHand(null);
			}
			event.getPlayer().sendMessage(SimpleUtils.translate("&7[&9Hunter&7] You have sold &9" + meta.getOwner() + "&7's head for &9$" + amount));
			}
		}
	}

	public int getPercentage(int total, int amount) {
		return (int) (total * ((float) amount / 100.0f));
	}
}
