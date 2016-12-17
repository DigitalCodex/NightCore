package me.digitalcodex.nc.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.digitalcodex.nc.NightCore;
import me.digitalcodex.nc.enchants.CustomEnchants;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;
import us.timberdnd.api.InventoryManager;
import us.timberdnd.api.ItemManager;
import us.timberdnd.utils.InventoryMechanics;
import us.timberdnd.utils.SimpleUtils;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class EnchantsInventory implements Listener {

	public static Inventory getInventory() {
		InventoryManager manager = new InventoryManager("&9NightEnchants", 2);

		manager.layout(new char[][] {
			"aaaaEaaaa".toCharArray(),
			"aaaJALaaa".toCharArray(),
		})
		.item('a', new ItemManager(Material.STAINED_GLASS_PANE).data(15).name(" "))
		.item('J', new ItemManager(Material.DIAMOND_PICKAXE).name("&6&lJackhammer Enchantment")
				.lore("&6This enchant allows you to")
				.lore("&6mine in a 3x3 block radius.")
				.lore(" ")
				.lore("&7Price: &6$50,000"))
		.item('A', new ItemManager(Material.FEATHER).name("&e&lAerial Enchantment")
				.lore("&eThis enchant allows you to")
				.lore("&efly in your faction land as well as allies.")
				.lore(" ")
				.lore("&7Price: &e$10,000"))
		.item('L', new ItemManager(Material.HOPPER).name("&5&lLodestone Enchantment")
				.lore("&5This enchant allows you to")
				.lore("&5instantly pickup items you've mined.")
				.lore(" ")
				.lore("&7Price: &5$30,000"))
		.item('E', new ItemManager(Material.IRON_PICKAXE).name("&a&lEnergy Enchantment")
				.lore("&aThis enchant gives you ")
				.lore("&ahaste when holding desired item.")
				.lore(" ")
				.lore("&7Energy 1 Price (&aLeft Click&7): &a$15,000")
				.lore("&7Energy 2 Price (&aRight Click&7): &a$30,000"))
		.mask();
		return manager.getInventory();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getTitle().equalsIgnoreCase(SimpleUtils.translate("&9NightEnchants"))) {
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem() == null 
					|| !event.getCurrentItem().hasItemMeta() 
					|| !event.getCurrentItem().getItemMeta().hasDisplayName()) {
				return;
			}
			ItemManager item = null;
			int price = 0;
			switch(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())) {
			case "Jackhammer Enchantment":
				price = 50000;
				item = new ItemManager(Material.ENCHANTED_BOOK)
						.name("&eEnchanted Book")
						.lore("&7Jackhammer I");
				break;
			case "Aerial Enchantment":
				price = 10000;
				item = new ItemManager(Material.ENCHANTED_BOOK)
						.name("&eEnchanted Book")
						.lore("&7Aerial I");
				break;
			case "Lodestone Enchantment":
				price = 30000;
				item = new ItemManager(Material.ENCHANTED_BOOK)
						.name("&eEnchanted Book")
						.lore("&7Lodestone I");
				break;
			case "Energy Enchantment":
				if(event.getClick() == ClickType.RIGHT) {
					price = 30000;
					item = new ItemManager(Material.ENCHANTED_BOOK)
							.name("&eEnchanted Book")
							.lore("&7Energy II");
				} else {
					price = 15000;
					item = new ItemManager(Material.ENCHANTED_BOOK)
							.name("&eEnchanted Book")
							.lore("&7Energy I");
				}
			}
			if(item != null) {
				EconomyResponse r = NightCore.econ.withdrawPlayer(player, price);
				if(r.transactionSuccess()) {
					player.getInventory().addItem(item.build());
					player.closeInventory();
					player.sendMessage(SimpleUtils.translate("&7You have purchased the " + event.getCurrentItem().getItemMeta().getDisplayName()));
					return;
				} else {
					player.sendMessage(SimpleUtils.translate("&7You do not have enough money for this enchantment"));
					return;
				}
			}
		}
		if(event.getCursor() == null 
				|| !event.getCursor().hasItemMeta() 
				|| !event.getCursor().getItemMeta().hasDisplayName() 
				|| event.getCursor().getType() != Material.ENCHANTED_BOOK
				|| !event.getCursor().getItemMeta().getDisplayName()
				.equalsIgnoreCase(SimpleUtils.translate("&eEnchanted Book"))) {
			return;
		}
		String enchantName = ChatColor.stripColor(event.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
		if(!CustomEnchants.hasEnchant(enchantName)) {
			return;
		}
		Enchantment ench = Enchantment.getByName(enchantName);
		if(ench.getItemTarget().includes(event.getCurrentItem().getType())) {
			ItemStack item = event.getCurrentItem();
			if(ench.getName().equalsIgnoreCase("Energy")) {
				int level = (item.getEnchantmentLevel(CustomEnchants.getEnchant("Energy")) == 1 ? 0 : 1 );
				if(InventoryMechanics.isEqualTo(event.getCurrentItem(), event.getWhoClicked().getItemInHand())) {
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level));
				}
			}
			item.addEnchantment(ench, 1);
			ItemMeta meta = event.getCurrentItem().getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add(event.getCursor().getItemMeta().getLore().get(0));
			if(event.getCurrentItem().getItemMeta().hasLore()) {
				lore.addAll(event.getCurrentItem().getItemMeta().getLore());
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			event.setCancelled(true);
			event.setCurrentItem(item);
			event.setCursor(null);
		}
	}
}
