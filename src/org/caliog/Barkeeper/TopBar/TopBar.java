package org.caliog.Barkeeper.TopBar;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.caliog.Barkeeper.Barkeeper;
import org.caliog.Barkeeper.Manager;

public class TopBar {

	static HashMap<UUID, FakeEntity> map = new HashMap<UUID, FakeEntity>();
	private static HashMap<UUID, Integer> tasks = new HashMap<UUID, Integer>();

	public static void init() {
		Barkeeper.plugin.getServer().getPluginManager().registerEvents(new BarListener(), Barkeeper.plugin);
		Manager.scheduleRepeatingTask(new Runnable() {

			@Override
			public void run() {
				NMSUtil util = NMS.getUtil();
				if (util == null)
					return;
				for (UUID id : map.keySet()) {
					util.sendTeleport(Bukkit.getPlayer(id), map.get(id));
				}
			}

		}, 0L, 5L);
	}

	public static void timerBar(final Player player, final String message, final int time) {
		updateBar(player, message, 1F);
		tasks.put(player.getUniqueId(), Manager.scheduleRepeatingTask(new Runnable() {

			private float p = 1F;

			@Override
			public void run() {
				p -= 2F / ((float) time * 20);
				updateBar(player, message, p);
			}
		}, 0L, 2L, time * 20L));

		Manager.scheduleTask(new Runnable() {

			@Override
			public void run() {
				removeBar(player);

			}
		}, time * 20L);
	}

	public static void updateBar(final Player player, final String message, int time) {
		updateBar(player, message);
		Manager.scheduleTask(new Runnable() {

			@Override
			public void run() {
				updateBar(player, message, 0F);

			}
		}, time);
	}

	public static void updateBar(Player player, String message) {
		updateBar(player, message, 1F);
	}

	public static void updateBar(Player player, String message, float p) {
		updateBar(player, message, p, player.getLocation());
	}

	public static void updateBar(Player player, String message, float p, Location loc) {
		FakeEntity entity;
		p = p < 0 ? 0 : p;
		loc = loc == null ? player.getLocation() : loc;
		if (map.get(player.getUniqueId()) != null) {
			entity = map.get(player.getUniqueId());
		} else if (p > 0) {
			entity = createNewFakeEntity(player, loc, ChatColor.translateAlternateColorCodes('&', message), p);
		} else
			return;
		if (p == 0) {
			removeBar(player);
			return;
		}
		entity.setName(message);
		entity.setHealthPercentage(p);
		NMSUtil util = NMS.getUtil();
		if (util != null) {
			util.sendMetaData(player, entity);
			util.sendTeleport(player, entity);
		}

	}

	public static void removeBar(Player player) {
		FakeEntity entity = map.get(player.getUniqueId());
		if (entity != null) {
			NMSUtil util = NMS.getUtil();
			if (util != null)
				util.sendDestroyPacket(player, entity);
			map.remove(player.getUniqueId());

			if (tasks.containsKey(player.getUniqueId())) {
				int task = tasks.get(player.getUniqueId());
				Manager.cancelTask(task);
				tasks.remove(player.getUniqueId());
			}
		}
	}

	private static FakeEntity createNewFakeEntity(Player player, Location loc, String message, float p) {
		FakeEntity entity = new FakeEntity(message, transform(loc), p);
		NMSUtil util = NMS.getUtil();
		if (util != null)
			util.sendSpawnPacket(player, entity);

		map.put(player.getUniqueId(), entity);
		return entity;
	}

	public static Location transform(Location loc) {
		if (Barkeeper.plugin.getVersion().equals("v1_7_R4")) {
			loc.subtract(0.0D, 300.0D, 0.0D);
			return loc;
		}

		float pitch = loc.getPitch();
		if (pitch >= 55.0F) {
			loc.add(0.0D, -300.0D, 0.0D);
		} else if (pitch <= -55.0F) {
			loc.add(0.0D, 300.0D, 0.0D);
		} else {
			loc = loc.getBlock().getRelative(getBlockFace(loc), Barkeeper.plugin.getServer().getViewDistance() * 16)
					.getLocation();
		}
		return loc;
	}

	private static BlockFace getBlockFace(Location loc) {
		float dir = Math.round(loc.getYaw() / 90.0F);
		if ((dir == -4.0F) || (dir == 0.0F) || (dir == 4.0F)) {
			return BlockFace.SOUTH;
		}
		if ((dir == -1.0F) || (dir == 3.0F)) {
			return BlockFace.EAST;
		}
		if ((dir == -2.0F) || (dir == 2.0F)) {
			return BlockFace.NORTH;
		}
		if ((dir == -3.0F) || (dir == 1.0F)) {
			return BlockFace.WEST;
		}
		return null;
	}

	public static FakeEntity getEntity(Player player) {
		return map.get(player);

	}

	public static void broadcast(String mainMessage, int time, World world) {
		Collection<? extends Player> list = Bukkit.getOnlinePlayers();
		if (world != null)
			list = world.getPlayers();
		for (Player player : list) {
			updateBar(player, mainMessage, time);
		}
	}

	public static void broadcast(String mainMessage, int time) {
		broadcast(mainMessage, time, null);
	}

	public static void broadcast(String mainMessage) {
		broadcast(mainMessage, 30);
	}

}
