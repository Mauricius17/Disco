package de.mauricius17.disco.livestream.cd;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.mauricius17.disco.livestream.system.Main;
import de.mauricius17.disco.livestream.utils.Utils;

public class CD {

	public void drop(Location loc) {
		ItemStack itemStack = Utils.getUtilsItem().createItem(Material.RECORD_12, 1, (byte) 0, getRandomString(10), false, "");
		
		final Item item = loc.getWorld().dropItem(loc, itemStack);
		item.setVelocity(getRandomVector(1F));
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				item.remove();
			}
		}, 20*3);
	}
	
	public Vector getRandomVector(float y) {
		float x = (float) (Math.random() / 20.0F);
		float z = (float) (Math.random() / 20.0F);
		
		if(Math.random() > 0.5D) x -= x * 2.0F; 
		if(Math.random() > 0.5D) z -= z * 2.0F; 
		
		Vector vec = new Vector(x, y, z);
		
		return vec;
	}
		
	public String getRandomString(int times) {
		Random r = new Random();
		String randomString = ""; 
		
		for(int i = 0; i < times; i++) {
			int zf = r.nextInt(26);
			
			if(zf == 0) randomString = randomString + "a";
			if(zf == 1) randomString = randomString + "b";
			if(zf == 2) randomString = randomString + "c";
			if(zf == 3) randomString = randomString + "d";
			if(zf == 4) randomString = randomString + "e";
			if(zf == 5) randomString = randomString + "f";
			if(zf == 6) randomString = randomString + "g";
			if(zf == 7) randomString = randomString + "h";
			if(zf == 8) randomString = randomString + "i";
			if(zf == 9) randomString = randomString + "j";
			if(zf == 10) randomString = randomString + "k";
			if(zf == 11) randomString = randomString + "l";
			if(zf == 12) randomString = randomString + "m";
			if(zf == 13) randomString = randomString + "n";
			if(zf == 14) randomString = randomString + "o";
			if(zf == 15) randomString = randomString + "p";
			if(zf == 16) randomString = randomString + "q";
			if(zf == 17) randomString = randomString + "r";
			if(zf == 18) randomString = randomString + "s";
			if(zf == 19) randomString = randomString + "t";
			if(zf == 20) randomString = randomString + "u";
			if(zf == 21) randomString = randomString + "v";
			if(zf == 22) randomString = randomString + "w";
			if(zf == 23) randomString = randomString + "x";
			if(zf == 24) randomString = randomString + "y";
			if(zf == 25) randomString = randomString + "z";
		}
		
		return randomString;
	}	
}
