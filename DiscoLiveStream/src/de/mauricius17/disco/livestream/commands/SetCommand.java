package de.mauricius17.disco.livestream.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mauricius17.disco.livestream.utils.Utils;

public class SetCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Utils.getConsole());
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length != 1 && args.length != 2) {
			sendHelp(p);
			return true;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("cd")) {
				Utils.getUtilsLocation().setLocation(p, p.getLocation(), "cd");
			} else {
				sendHelp(p);
			}
		} 
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("button")) {
				if(args[1].equalsIgnoreCase("cd")) {
					p.getInventory().addItem(Utils.getUtilsItem().createItem(Material.WOOD_SPADE, 1, (byte) 0, "§5Setze den CD Button", false, ""));
				} else 
					if(args[1].equalsIgnoreCase("note")) {
						p.getInventory().addItem(Utils.getUtilsItem().createItem(Material.WOOD_PICKAXE, 1, (byte) 0, "§5Setze den Noten Button", false, ""));
					} else 
						if(args[1].equalsIgnoreCase("sphere")) {
							p.getInventory().addItem(Utils.getUtilsItem().createItem(Material.WOOD_HOE, 1, (byte) 0, "§5Setze den Spheren Button", false, ""));
						} else {
							sendHelp(p);
						}
			} else
				if(args[0].equalsIgnoreCase("sphere")) {
					if(args[1].equalsIgnoreCase("1")) {
						Utils.getUtilsLocation().setLocation(p, p.getLocation(), "sphere_1");
					} else 
						if(args[1].equalsIgnoreCase("2")) {
							Utils.getUtilsLocation().setLocation(p, p.getLocation(), "sphere_2");
						} else {
							sendHelp(p);
						}
				} else
					if(args[0].equalsIgnoreCase("note")) {
						if(args[1].equalsIgnoreCase("1")) {
							Utils.getUtilsLocation().setLocation(p, p.getLocation(), "note_1");
						} else 
							if(args[1].equalsIgnoreCase("2")) {
								Utils.getUtilsLocation().setLocation(p, p.getLocation(), "note_2");
							} else {
								sendHelp(p);
							}
					} else {
						sendHelp(p);
					}
		}
		
		return true;
	}
	
	public void sendHelp(Player p) {
		p.sendMessage("§8==========[§5Disco§8]==========");
		p.sendMessage("§7/set button <cd|note|sphere>");
		p.sendMessage("§7/set sphere <1|2>");
		p.sendMessage("§7/set note <1|2>");
		p.sendMessage("§7/set cd");
		p.sendMessage("§8===========================");
	}
}
