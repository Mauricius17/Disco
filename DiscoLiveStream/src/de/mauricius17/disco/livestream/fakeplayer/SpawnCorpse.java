package de.mauricius17.disco.livestream.fakeplayer;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.PacketPlayOutBed;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SpawnCorpse {

	private int entityId;
	private UUID uuid;
	private Location location;
	
	public SpawnCorpse(UUID uuid, Location location) {
		this.entityId = new Random().nextInt(1000000000);
		this.uuid = uuid;
		this.location = location;
	}

	public void spawn() {		
		try {
			PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
			PacketPlayOutBed bed = new PacketPlayOutBed();
			
			DataWatcher watcher = new DataWatcher(null);
			watcher.a(6, (float) 20F);
			watcher.a(10, (byte) 127);
			
			BlockPosition positon = new BlockPosition(location.getX(), location.getY(), location.getZ());

			set(packet, "a", entityId);
			set(packet, "b", uuid);
			set(packet, "i", watcher);
			
			set(bed, "a", entityId);			
			set(bed, "b", positon);
			
			for(Player players: Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(bed);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void despawn() {
		try {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{entityId});
			
			for(Player players: Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void set(Object instance, String name, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(instance, value);
	}
}
