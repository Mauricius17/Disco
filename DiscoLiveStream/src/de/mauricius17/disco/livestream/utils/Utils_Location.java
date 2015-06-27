package de.mauricius17.disco.livestream.utils;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Utils_Location {

	public void setLocation(Player p, Location loc, String path) {
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		FileConfiguration cfg = Utils.getCfg();
		
		cfg.set("location." + path + ".world", world);
		cfg.set("location." + path + ".x", x);
		cfg.set("location." + path + ".y", y);
		cfg.set("location." + path + ".z", z);
		
		cfg.options().copyDefaults(true);
		
		try {
			cfg.save(Utils.getFile());
			p.sendMessage(Utils.getPrefix() + "§7Du hast die Location §5" + path + " §7erfolgreich gesetzt!");
		} catch (IOException e) {
			e.printStackTrace();
			p.sendMessage(Utils.getPrefix() + "§cDie Location §5" + path + " §7konnte nicht gesetzt werden!");
		}
	}
	
	public Location getLocation(String path) {
		FileConfiguration cfg = Utils.getCfg();
		
		String world = cfg.getString("location." + path + ".world");
		double x = cfg.getDouble("location." + path + ".x");
		double y = cfg.getDouble("location." + path + ".y");
		double z = cfg.getDouble("location." + path + ".z");
		
		Location location = new Location(Bukkit.getWorld(world), x, y, z);
		
		return location;
	}
}
