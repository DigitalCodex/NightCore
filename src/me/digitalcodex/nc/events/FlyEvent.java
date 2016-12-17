package me.digitalcodex.nc.events;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;

import me.digitalcodex.nc.enchants.CustomEnchants;
import us.timberdnd.api.ArmorEquipEvent;
import us.timberdnd.utils.SimpleUtils;

/**
 * Created by DigitalCodex on Dec 15, 2016.
 */
public class FlyEvent implements Listener {
	
	/*@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onToggle(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.getInventory().getBoots() != null) {
			if(player.getInventory().getBoots().containsEnchantment(CustomEnchants.getEnchant("Aerial"))) {
		            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
		            Faction dest = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
		            Relation rel = fPlayer.getRelationTo(dest);
		            if (rel != Relation.ALLY && rel != Relation.MEMBER) {
		                player.setAllowFlight(false);
		                player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] Flight only available in faction or allied claimed land."));
		                return;
		            }
				if(event.isFlying()) {
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] You have toggled flight &9on&7."));
				} else {
					player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] You have toggled flight &9off&7."));
				}
			}
		}
	}*/
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onToggle(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		if(event.getNewArmorPiece() == null) {
			return;
		}
		if(event.getNewArmorPiece().getType().toString().endsWith("_BOOTS")) {
			if(event.getNewArmorPiece().containsEnchantment(CustomEnchants.getEnchant("Aerial"))) {
		            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
		            Faction dest = Board.getInstance().getFactionAt(new FLocation(player.getLocation()));
		            Relation rel = fPlayer.getRelationTo(dest);
		            if (rel != Relation.ALLY && rel != Relation.MEMBER) {
		                player.setAllowFlight(false);
		                player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] Flight only available in faction or allied claimed land."));
		                return;
		            }
				if(!player.isFlying()) {
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] You have toggled flight &9on&7."));
				} else {
					player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] You have toggled flight &9off&7."));
				}
			}
		}
	}
	
	  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	    public void onPlayerMove(PlayerMoveEvent event) {
	        if (event.getPlayer().isOp()) {
	            return;
	        } else if (!event.getPlayer().getAllowFlight()) {
	            return;
	        } else if (event.getFrom().getBlockX() >> 4 == event.getTo().getBlockX() >> 4 
	        		&& event.getFrom().getBlockZ() >> 4 == event.getTo().getBlockZ() >> 4 
	        		&& event.getFrom().getWorld() == event.getTo().getWorld()) {
	            return;
	        } else {
	            Player player = event.getPlayer();
	            FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
	            Faction dest = Board.getInstance().getFactionAt(new FLocation(event.getTo()));
	            Relation rel = fplayer.getRelationTo(dest);

	            if (rel == Relation.MEMBER) {
	                return;
	            } else if (rel == Relation.ALLY) {
	                return;
	            } else {
	            	Location loc = player.getLocation();
	            	player.teleport(loc.getWorld().getHighestBlockAt(loc).getLocation());
	            	player.setFlying(false);
					player.setAllowFlight(false);
					player.sendMessage(SimpleUtils.translate("&7[&9Aerial&7] You have toggled flight &9off&7."));
	            }
	        }
	    }
}
