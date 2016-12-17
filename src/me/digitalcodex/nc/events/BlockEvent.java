package me.digitalcodex.nc.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import me.digitalcodex.nc.enchants.CustomEnchants;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class BlockEvent implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getItemInHand();
		Block block = event.getBlock();
		Player player = event.getPlayer();
		if(item == null || event.isCancelled() || event.getBlock().getType() == Material.BEDROCK) {
			return;
		}
		if(item.containsEnchantment(CustomEnchants.getEnchant("Jackhammer"))) {
			Block b;
			for(int xOff = -1; xOff <= 1; ++xOff){
				for(int yOff = -1; yOff <= 1; ++yOff){
					for(int zOff = -1; zOff <= 1; ++zOff){
						b = block.getRelative(xOff, yOff, zOff);
						if(b.getType() == null || b.getType() == Material.AIR) {
							continue;
						}
						if(item.containsEnchantment(CustomEnchants.getEnchant("Lodestone"))) {
							for(ItemStack items: b.getDrops()) {
								player.getInventory().addItem(items);
							}
							b.setType(Material.AIR);
						}else{
							b.breakNaturally();
						}
					}
				}
			}
		}
		if(item.containsEnchantment(CustomEnchants.getEnchant("Lodestone"))) {
			for(ItemStack items: block.getDrops()) {
				player.getInventory().addItem(items);
				block.getDrops().remove(items);
			}
			event.setCancelled(true);
			event.getBlock().setType(Material.AIR);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
		String[] strings = new String[] {"/plugins", "/server", "/about", "info", "serverinfo", "si"};
		for(String s: strings) {
			if(event.getMessage().toLowerCase().contains(s)) {
				event.getPlayer().performCommand("/info");
			}
		}
	}
}
