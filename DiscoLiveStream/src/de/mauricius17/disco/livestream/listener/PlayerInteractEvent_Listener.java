package de.mauricius17.disco.livestream.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mauricius17.disco.livestream.fakeplayer.SpawnCorpse;
import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer;
import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer.Equipment;
import de.mauricius17.disco.livestream.system.Main;
import de.mauricius17.disco.livestream.utils.Utils;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class PlayerInteractEvent_Listener implements Listener {

	boolean cd = false;
	boolean note = false;
	boolean sphere = false;
	int task1 = 0;
	int task2 = 0;
	int task3 = 0;
	int i;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			try {
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(e.getClickedBlock().getType() == Material.STONE_BUTTON) {
						if(p.getItemInHand().getType() == Material.WOOD_SPADE) {
							Utils.getUtilsLocation().setLocation(p, e.getClickedBlock().getLocation(), "cd_button");
							return;
						}
						
						if(p.getItemInHand().getType() == Material.WOOD_PICKAXE) {
							Utils.getUtilsLocation().setLocation(p, e.getClickedBlock().getLocation(), "note_button");
							return;
						}
						
						if(p.getItemInHand().getType() == Material.WOOD_HOE) {
							Utils.getUtilsLocation().setLocation(p, e.getClickedBlock().getLocation(), "sphere_button");
							return;
						}
						
						if(e.getClickedBlock().getLocation().equals(Utils.getUtilsLocation().getLocation("cd_button"))) {
							if(cd) {
								p.sendMessage(Utils.getPrefix() + "§cDieser Effekt wurde breits gestartet!");
								return;
							}
							
							task1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
								
								@Override
								public void run() {
									if(i == 70) {
										i = 0;
										cd = false;
										Bukkit.getScheduler().cancelTask(task1);
										return;
									}

									if(i == 0) {
										cd = true;
									}
									
									Utils.getCD().drop(Utils.getUtilsLocation().getLocation("cd"));
									i++;
								}
							}, 0, 2);
						}
						
						if(e.getClickedBlock().getLocation().equals(Utils.getUtilsLocation().getLocation("note_button"))) {
							if(note) {
								note = false;
								p.sendMessage(Utils.getPrefix() + "§7Noten §cdeaktiviert§7!");
								Bukkit.getScheduler().cancelTask(task2);
							} else {
								note = true;
								p.sendMessage(Utils.getPrefix() + "§7Noten §aaktiviert§7!");

								task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
									
									@SuppressWarnings("deprecation")
									@Override
									public void run() {
										ParticleEffect.NOTE.display(Utils.getUtilsLocation().getLocation("note_1"), 20, 0.5F, 0.5F, 0.5F, 10, 10);
										ParticleEffect.NOTE.display(Utils.getUtilsLocation().getLocation("note_2"), 20, 0.5F, 0.5F, 0.5F, 10, 10);
									}
								}, 0, 5);
							}
						}
						
						if(e.getClickedBlock().getLocation().equals(Utils.getUtilsLocation().getLocation("sphere_button"))) {
							
							if(sphere) {
								sphere = false;
								p.sendMessage(Utils.getPrefix() + "§7Spheren §cdeaktiviert§7!");
								Bukkit.getScheduler().cancelTask(task3);
							} else {
								sphere = true;
								p.sendMessage(Utils.getPrefix() + "§7Spheren §aaktiviert§7!");

								task3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
									
									@Override
									public void run() {
										SphereEffect effect1 = new SphereEffect(Main.getInstance().getEm());
										SphereEffect effect2 = new SphereEffect(Main.getInstance().getEm());
										effect1.setLocation(Utils.getUtilsLocation().getLocation("sphere_1"));
										effect2.setLocation(Utils.getUtilsLocation().getLocation("sphere_2"));
										effect1.start();
										effect2.start();
									}
								}, 0, 20*22);
							}
						}	
					}
				}
				
				if(p.getItemInHand().getType() == Material.STICK) {					
					if(Utils.getSpawnFakePlayer().size() > 0) {
						SpawnFakePlayer fakePlayer = Utils.getSpawnFakePlayer().get(0);
						fakePlayer.despawn();
						Utils.getSpawnFakePlayer().clear();
						p.sendMessage(Utils.getPrefix() + "§7Du hast den §5DJ §cdespawnt!");
					} else {
						SpawnFakePlayer fakePlayer = new SpawnFakePlayer(p.getUniqueId(), p.getLocation());
						fakePlayer.spawn();
						fakePlayer.updateLook(p.getLocation().getYaw(), p.getLocation().getPitch());
						fakePlayer.equip(Equipment.HELMET, p.getInventory().getHelmet());
						fakePlayer.equip(Equipment.CHESTPLATE, p.getInventory().getChestplate());
						fakePlayer.equip(Equipment.LEGGINGS, p.getInventory().getLeggings());
						fakePlayer.equip(Equipment.BOOTS, p.getInventory().getBoots());
						Utils.getSpawnFakePlayer().add(fakePlayer);
						p.sendMessage(Utils.getPrefix() + "§7Du hast den §5DJ §agespawnt!");
					}
				}
				
				if(p.getItemInHand().getType() == Material.APPLE) {
					if(Utils.getCorpse().size() > 0) {
						SpawnCorpse corpse = Utils.getCorpse().get(0);
						corpse.despawn();
						Utils.getCorpse().clear();
						p.sendMessage(Utils.getPrefix() + "§7Du hast die §5Leiche §cdespawnt!");
					} else {
						SpawnCorpse corpse = new SpawnCorpse(p.getUniqueId(), p.getLocation());
						corpse.spawn();
						Utils.getCorpse().add(corpse);
						p.sendMessage(Utils.getPrefix() + "§7Du hast die §5Leiche §agespawnt!");
					}
				}
				
				if(p.getItemInHand().getType() == Material.PAPER) {
					Utils.getUtilsInventory().openAnimationInventory(p);
				}
			} catch(NullPointerException ex) {}
		}
	}
}
