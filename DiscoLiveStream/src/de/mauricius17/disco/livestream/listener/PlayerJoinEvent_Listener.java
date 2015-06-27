package de.mauricius17.disco.livestream.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.mauricius17.disco.livestream.system.Main;

public class PlayerJoinEvent_Listener implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				e.getPlayer().setGameMode(GameMode.CREATIVE);
			}
		}, 20*1);
	}
	
}
