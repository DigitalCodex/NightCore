package me.digitalcodex.nc.enchants;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class CustomEnchants {
	private static Map<String, Enchantment> customEnchants = new HashMap<String, Enchantment>();
	
	public static Enchantment getEnchant(String key) {
		return customEnchants.get(key);
	}
	
	public static boolean hasEnchant(String key) {
		return customEnchants.containsKey(key);
	}

	public static void add(String name, Enchantment ench) {
		if(!customEnchants.containsKey(name)) {
			customEnchants.put(name, ench);
		}
		return;
	}
}
