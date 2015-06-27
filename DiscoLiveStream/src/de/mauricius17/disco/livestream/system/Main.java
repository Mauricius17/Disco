package de.mauricius17.disco.livestream.system;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.mauricius17.disco.livestream.commands.SetCommand;
import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer;
import de.mauricius17.disco.livestream.listener.InventoryClickEvent_Listener;
import de.mauricius17.disco.livestream.listener.PlayerInteractEvent_Listener;
import de.mauricius17.disco.livestream.listener.PlayerJoinEvent_Listener;
import de.mauricius17.disco.livestream.listener.PlayerPickUpItemEvent_Listener;
import de.mauricius17.disco.livestream.utils.Utils;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private EffectManager em;
	
	@Override
	public void onEnable() {
		instance = this;
		
		registerCommands();
		registerEvents();
		
		em = new EffectManager(EffectLib.instance());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(Player players: Bukkit.getOnlinePlayers()) {
					players.setGameMode(GameMode.CREATIVE);
				}
			}
		}, 20*1);
	}
	
	@Override
	public void onDisable() {
		instance = null;
		
		if(Utils.getSpawnFakePlayer().size() > 0) {
			SpawnFakePlayer fakePlayer = Utils.getSpawnFakePlayer().get(0);
			fakePlayer.despawn();
		}
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerPickUpItemEvent_Listener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEvent_Listener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent_Listener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickEvent_Listener(), this);
	}
	
	private void registerCommands() {
		getCommand("set").setExecutor(new SetCommand());
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public EffectManager getEm() {
		return em;
	}
}
