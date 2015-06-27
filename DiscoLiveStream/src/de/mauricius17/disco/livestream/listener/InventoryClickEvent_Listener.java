package de.mauricius17.disco.livestream.listener;

import java.util.ConcurrentModificationException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer;
import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer.Animation;
import de.mauricius17.disco.livestream.system.Main;
import de.mauricius17.disco.livestream.utils.Utils;

public class InventoryClickEvent_Listener implements Listener {

	int task = 0;
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if(e.getInventory().getName().equalsIgnoreCase("§5Animationen")) {
				e.setCancelled(true);
				
				try {
					if(e.getCurrentItem().getType().equals(Material.PAPER)) {
						if(Utils.getSpawnFakePlayer().size() < 1) {
							p.sendMessage(Utils.getPrefix() + "§cEs ist kein §5DJ §canwesend!");
							return;
						}
						
						String animation = e.getCurrentItem().getItemMeta().getDisplayName();
						
						if(Utils.getAnimations().contains(animation)) {
							Utils.getAnimations().remove(animation);
							
							if(Utils.getAnimations().size() <= 0) {
								Bukkit.getScheduler().cancelTask(task);
							}
							p.sendMessage(Utils.getPrefix() + "§7Du hast die Animation §5" + animation + " §cbeendet!");
						} else {
							Utils.getAnimations().add(animation);
							
							if(Utils.getAnimations().size() == 1) {
								task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
									
									@Override
									public void run() {
										try {
											for(String a : Utils.getAnimations()) {
												if(Utils.getSpawnFakePlayer().size() > 0) {
													SpawnFakePlayer fakePlayer = Utils.getSpawnFakePlayer().get(0);
													fakePlayer.startAnimation(Animation.valueOf(a));
												} else {
													Utils.getAnimations().clear();
													Bukkit.getScheduler().cancelTask(task);
												}
											}
										} catch(ConcurrentModificationException ex) {}
									}
								}, 0, 20);
							}
							
							p.sendMessage(Utils.getPrefix() + "§7Du hast die Animation §5" + animation + " §agestartet!");
						}
					}
				} catch(NullPointerException ex) {} 
			}
		}
	}
}
