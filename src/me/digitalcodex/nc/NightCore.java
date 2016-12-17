package me.digitalcodex.nc;

import java.util.logging.Logger;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.digitalcodex.nc.cmds.ClearChatCommand;
import me.digitalcodex.nc.cmds.EnchantsCommand;
import me.digitalcodex.nc.cmds.IClearChatCommand;
import me.digitalcodex.nc.cmds.InfoCommand;
import me.digitalcodex.nc.enchants.AerialEnchant;
import me.digitalcodex.nc.enchants.EnergyEnchant;
import me.digitalcodex.nc.enchants.JackhammerEnchant;
import me.digitalcodex.nc.enchants.LodestoneEnchant;
import me.digitalcodex.nc.events.BlockEvent;
import me.digitalcodex.nc.events.DeathEvent;
import me.digitalcodex.nc.events.FlyEvent;
import me.digitalcodex.nc.events.ItemSwitchEvent;
import me.digitalcodex.nc.inventories.EnchantsInventory;
import net.milkbowl.vault.economy.Economy;

/**
 * Created by DigitalCodex on Dec 12, 2016.
 */
public class NightCore extends JavaPlugin {

	private static NightCore instance;
	private static Logger log;
    public static Economy econ = null;

	public void onEnable() {
		instance = this;
		log = getInstance().getLogger();
		if (!setupEconomy() ) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getCommand("info").setExecutor(new InfoCommand());
		getCommand("enchants").setExecutor(new EnchantsCommand());
		getCommand("clearchat").setExecutor(new ClearChatCommand());
		getCommand("iclearchat").setExecutor(new IClearChatCommand());
		getServer().getPluginManager().registerEvents(new EnchantsInventory(), this);
		getServer().getPluginManager().registerEvents(new BlockEvent(), this);
		getServer().getPluginManager().registerEvents(new FlyEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		getServer().getPluginManager().registerEvents(new ItemSwitchEvent(), this);
		registerEnchantments();
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

	public void registerEnchantments() {
		new JackhammerEnchant(96);
		new LodestoneEnchant(97);
		new AerialEnchant(98);
		new EnergyEnchant(99);
	}

	public static NightCore getInstance() {
		return instance;
	}
}
