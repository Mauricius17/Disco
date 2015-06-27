package de.mauricius17.disco.livestream.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.mauricius17.disco.livestream.fakeplayer.SpawnFakePlayer.Animation;

public class Utils_Inventory {

	public void openAnimationInventory(Player p) {	
		Inventory inv = Bukkit.createInventory(null, 9, "§5Animationen");
		
		for(int i = 0; i < Animation.values().length; i++) {
			inv.setItem(i, Utils.getUtilsItem().createItem(Material.PAPER, 1, (byte) 0, Animation.values()[i].toString(), false, ""));
		}
		
		p.openInventory(inv);
	}
}