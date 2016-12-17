package me.digitalcodex.nc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.digitalcodex.nc.enchants.CustomEnchants;


/**
 * Created by DigitalCodex on Dec 15, 2016.
 */
public class ItemSwitchEvent implements Listener {

	@EventHandler
	public void onSwitch(PlayerItemHeldEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
		ItemStack item1 = event.getPlayer().getInventory().getItem(event.getPreviousSlot());
		if(item == null && item1 != null) {
			if(item1.containsEnchantment(CustomEnchants.getEnchant("Energy"))) {
				if(event.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
					event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
					return;
				}
			}
		}
		if(item != null && item.containsEnchantment(CustomEnchants.getEnchant("Energy"))) {
			int level = item.getEnchantmentLevel(CustomEnchants.getEnchant("Energy"));
			if(level == 1) {
				level = 0;
			} else { level = 1; }
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level));
		} else if(item != null && !item.containsEnchantment(CustomEnchants.getEnchant("Energy")) && item1.containsEnchantment(CustomEnchants.getEnchant("Energy"))) {
			event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
	}
}
