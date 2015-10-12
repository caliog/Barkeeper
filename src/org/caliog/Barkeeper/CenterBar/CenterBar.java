package org.caliog.Barkeeper.CenterBar;

import org.bukkit.ChatColor;
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
}
