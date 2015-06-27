package de.mauricius17.disco.livestream.fakeplayer;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnFakePlayer {
	
	private int entityId;
	private UUID uuid;
	private Location location;
	
	public SpawnFakePlayer(UUID uuid, Location location) {
		this.entityId = new Random().nextInt(1000000000);
		this.uuid = uuid;
		this.location = location;
	}
	
	public void spawn() {
		try {
			PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();			
			set(packet, "a", entityId);
			set(packet, "b", uuid);
			set(packet, "c", toFixedPointNumber(location.getX()));
			set(packet, "d", toFixedPointNumber(location.getY()));
			set(packet, "e", toFixedPointNumber(location.getZ()));
			set(packet, "f", toPacketByte(location.getYaw()));
			set(packet, "f", toPacketByte(location.getPitch()));
			set(packet, "h", 267);
			
			DataWatcher watcher = new DataWatcher(null);
			watcher.a(6, (float) 20F);
			watcher.a(10, (byte) 127);
			
			set(packet, "i", watcher);
			
			for(Player players: Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
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
 
	public void updateLook(float yaw, float pitch) {
		try {
			PacketPlayOutEntityLook packet1 = new PacketPlayOutEntityLook(entityId, toPacketByte(yaw), toPacketByte(pitch), true);
			PacketPlayOutEntityHeadRotation packet2 = new PacketPlayOutEntityHeadRotation();
			set(packet2, "a", entityId);
			set(packet2, "b", toPacketByte(yaw));
			
			
			for(Player players: Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet1);
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet2);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void equip(Equipment equip, ItemStack itemStack) {
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, equip.getInt(), CraftItemStack.asNMSCopy(itemStack));
		
		for(Player players: Bukkit.getOnlinePlayers()) {
			((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void startAnimation(Animation animation) {
		try {
			PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
			set(packet, "a", entityId);
			set(packet, "b", animation.getInt());
			
			for(Player players: Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public enum Equipment {
		HELD(0), BOOTS(1), LEGGINGS(2), CHESTPLATE(3), HELMET(4);
		
		
		private int i;
		
		private Equipment(int i) {
			this.i = i;
		}
		
		public int getInt() {
			return i;
		}
	}
	
	public enum Animation {
		SWING_ARM(0), TAKE_DAMGE(1), LEAVE_BED(2), EAT_FOOD(3), CRITICAL_EFFECT(4), UNKNOWN(102), CROUCH(104), UNCHROUCH(105);
		
		private int i;
		
		private Animation(int i) {
			this.i = i;
		}
		
		public int getInt() {
			return i;
		}
	}
	
	private void set(Object instance, String name, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(instance, value);
	}
	
	private byte toPacketByte(float f) {
		return (byte) ((int) (f * 256.0F / 360.0F));
	}
	
	private int toFixedPointNumber(double d) {
		return (int) (d*32D);
	}
}
