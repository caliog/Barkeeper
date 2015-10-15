package org.caliog.Barkeeper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.caliog.Barkeeper.BottomBar.BottomBar;
import org.caliog.Barkeeper.CenterBar.CenterBar;
import org.caliog.Barkeeper.TopBar.TopBar;
import org.caliog.Barkeeper.Utils.BarConfig;
import org.caliog.Barkeeper.Utils.BarConfig.Message;

public class Manager {

	public static void send(Player player, Message msg, String playerName, String worldName, String level) {
		String message = BarConfig.getMainMessage(msg).replaceAll("%PLAYER%", playerName).replaceAll("%LEVEL%", level)
				.replaceAll("%WORLD%", worldName);
		String subMessage = BarConfig.getSubMessage(msg).replaceAll("%PLAYER%", playerName).replaceAll("%LEVEL%", level)
				.replaceAll("%WORLD%", worldName);
		switch (BarConfig.getPosition(msg)) {
		case BOTTOM:
			BottomBar.display(player, message, BarConfig.isAnimated(msg) ? 8 : 0);
			break;

		case CENTER:
			CenterBar.display(player, message, subMessage, BarConfig.getTime(msg) * 20, BarConfig.isAnimated(msg));
			break;
		case TOP:
			TopBar.updateBar(player, message, BarConfig.getTime(msg) * 20);
			break;
		default:
			break;
		}
	}

	public static void broadcast(Message msg, String playerName, String worldName, String level) {
		String message = BarConfig.getMainMessage(msg).replaceAll("%PLAYER%", playerName).replaceAll("%LEVEL%", level)
				.replaceAll("%WORLD%", worldName);
		String subMessage = BarConfig.getSubMessage(msg).replaceAll("%PLAYER%", playerName).replaceAll("%LEVEL%", level)
				.replaceAll("%WORLD%", worldName);
		switch (BarConfig.getPosition(msg)) {
		case BOTTOM:
			BottomBar.broadcast(message, BarConfig.isAnimated(msg) ? 8 : 0, null);
			break;
		case CENTER:
			CenterBar.broadcast(message, subMessage, null,
					(msg.equals(Message.BROADCAST) ? 2 : BarConfig.getTime(msg)) * 20, BarConfig.isAnimated(msg));
			break;
		case TOP:
			TopBar.broadcast(message, (msg.equals(Message.BROADCAST) ? 2 : BarConfig.getTime(msg)) * 20);
			break;
		default:
			break;
		}
	}

	public static int scheduleRepeatingTask(Runnable r, long d, long p) {
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(Barkeeper.plugin, r, d, p);
	}

	public static int scheduleTask(Runnable r, long d) {
		return Bukkit.getScheduler().scheduleSyncDelayedTask(Barkeeper.plugin, r, d);
	}

	public static int scheduleTask(Runnable r) {
		return Bukkit.getScheduler().scheduleSyncDelayedTask(Barkeeper.plugin, r);
	}

	public static void cancelTask(Integer id) {
		Bukkit.getScheduler().cancelTask(id.intValue());
	}

	public static void cancelAllTasks() {
		Bukkit.getScheduler().cancelTasks(Barkeeper.plugin);
	}

	public static int scheduleRepeatingTask(Runnable r, long i, long j, long l) {
		final int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Barkeeper.plugin, r, i, j);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Barkeeper.plugin, new Runnable() {
			public void run() {
				Bukkit.getScheduler().cancelTask(taskId);
			}
		}, l);
		return taskId;
	}

}