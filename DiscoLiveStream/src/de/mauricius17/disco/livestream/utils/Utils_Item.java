package de.mauricius17.disco.livestream.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils_Item {

	public ItemStack createItem(Material mat, int amount, byte subID, String displayname, boolean l, String lore) {
		ItemStack item = new ItemStack(mat, amount, (byte) subID);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		
		if(l) {
			meta.setLore(Arrays.asList(lore));
		}
		
		item.setItemMeta(meta);
		
		return item;
	}
}
