package me.digitalcodex.nc.enchants;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class JackhammerEnchant extends Enchantment {

	public JackhammerEnchant(int id) {
		super(id);
		if(id > 256) {
			throw new IllegalArgumentException("Id must be lower then 256!");
		}
				
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			@SuppressWarnings("unchecked")
			HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			@SuppressWarnings("unchecked")
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

			if(byId.containsKey(id))
				byId.remove(id);

			if(byName.containsKey(getName()))
				byName.remove(getName());
			
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
	      	f.setAccessible(true);
	      	f.set(null, true);
	      	
	      	Enchantment.registerEnchantment(this);
	      	CustomEnchants.add(getName(), this);
	      	
		} catch(Exception ignored) { ignored.printStackTrace(); }
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return true;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		return "Jackhammer";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

}
