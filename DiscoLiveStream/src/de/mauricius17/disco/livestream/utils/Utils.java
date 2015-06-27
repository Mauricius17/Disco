package de.mauricius17.disco.livestream.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.mauricius17.disco.livestream.cd.CD;
import de.mauricius17.disco.livestream.fakeplayer.SpawnCorpse;
import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer;

public class Utils {

	private static Utils_Location utilsLocation = new Utils_Location();
	private static Utils_Item utilsItem = new Utils_Item();
	private static Utils_Inventory utilsInventory = new Utils_Inventory();
	
	private static CD cD = new CD();
	private static List<SpawnFakePlayer> spawnFakePlayer = new ArrayList<>();
	private static List<SpawnCorpse> spawnCorpse = new ArrayList<>();
	private static List<String> animations = new ArrayList<>();
	
	private static String prefix = "§8[§5Disco§8] ", console = "Nur Spieler koennen diese Aktion ausfuehren!";
	private static File file =  new File("plugins/Disco", "location.yml");
	private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static List<String> getAnimations() {
		return animations;
	}
	
	public static CD getcD() {
		return cD;
	}
	
	public static FileConfiguration getCfg() {
		return cfg;
	}
	
	public static String getConsole() {
		return console;
	}
	
	public static File getFile() {
		return file;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static List<SpawnCorpse> getSpawnCorpse() {
		return spawnCorpse;
	}
	
	public static List<SpawnFakePlayer> getSpawnFakePlayer() {
		return spawnFakePlayer;
	}
	
	public static Utils_Inventory getUtilsInventory() {
		return utilsInventory;
	}
	
	public static Utils_Item getUtilsItem() {
		return utilsItem;
	}
	
	public static Utils_Location getUtilsLocation() {
		return utilsLocation;
	}
}
