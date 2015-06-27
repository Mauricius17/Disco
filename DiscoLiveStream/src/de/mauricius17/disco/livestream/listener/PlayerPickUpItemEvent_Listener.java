package de.mauricius17.disco.livestream.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickUpItemEvent_Listener implements Listener {

	@EventHandler
	public void onPickUpItem(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}	
}
