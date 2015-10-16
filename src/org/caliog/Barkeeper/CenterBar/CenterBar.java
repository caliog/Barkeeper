package org.caliog.Barkeeper.CenterBar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CenterBar {
	public static void display(Player player, String title, String subtitle, int time, boolean t) {
		NMSUtil util = NMS.getUtil();
		if (title != null) {
			title = ChatColor.translateAlternateColorCodes('&', title);
		}
		if (subtitle != null) {
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
		}
		if (util != null) {
			util.sendBar(player, title, subtitle, t ? 15 : 0, time, t ? 15 : 0);
		}
	}

	public static void display(Player player, String title, int time) {
		display(player, title, null, time, false);
	}

	public static void display(Player player, String title) {
		display(player, title, 60);
	}

	public static void display(Player player, String title, String subtitle, int time) {
		display(player, title, subtitle, time, false);
	}

	public static void display(Player player, String title, String subtitle) {
		display(player, title, subtitle, 60, false);
	}

	public static void broadcast(String title, String subtitle, World world, int time, boolean t) {
		List<Player> players = new ArrayList<Player>();
		if (world == null) {
			for (World w : Bukkit.getWorlds())
				players.addAll(w.getPlayers());
		} else {
			players = world.getPlayers();
		}
		for (Player player : players)
			display(player, title, subtitle, time, t);

	}

	public static void broadcast(String title, String subtitle, World world) {
		broadcast(title, subtitle, world, 60, false);
	}

	public static void broadcast(String title, String subtitle) {
		broadcast(title, subtitle, null);
	}
}
