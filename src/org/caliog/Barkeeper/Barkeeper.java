package org.caliog.Barkeeper;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.caliog.Barkeeper.BottomBar.BottomBar;
import org.caliog.Barkeeper.CenterBar.CenterBar;
import org.caliog.Barkeeper.TopBar.TopBar;
import org.caliog.Barkeeper.Utils.myUtils;

public class Barkeeper extends JavaPlugin {
    public static Barkeeper plugin;
    private String version;

    public void onEnable() {
	String pN = Bukkit.getServer().getClass().getPackage().getName();
	this.version = pN.substring(pN.lastIndexOf(".") + 1);
	plugin = this;
	TopBar.init();
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (!(sender instanceof Player)) {
	    return false;
	}
	Player player = (Player) sender;
	String command = "";
	for (String a : args) {
	    command = command + a + " ";
	}
	command = command.trim();
	int count = command.length() - command.replace("\"", "").length();
	String[] a = new String[args.length];
	if (count < 2) {
	    a = args;
	} else {
	    int counter = 0;
	    for (int i = 0; i < args.length; i++) {
		if (args[i].startsWith("\"")) {
		    a[counter] = "";
		    while ((i < args.length) && (!args[i].endsWith("\""))) {
			int tmp161_159 = counter;
			String[] tmp161_157 = a;
			tmp161_157[tmp161_159] = (tmp161_157[tmp161_159] + " " + args[i].replace("\"", ""));
			i++;
		    }
		    if (i < args.length) {
			int tmp234_232 = counter;
			String[] tmp234_230 = a;
			tmp234_230[tmp234_232] = (tmp234_230[tmp234_232] + " " + args[i].replace("\"", ""));
		    }
		    a[counter] = a[counter].trim();
		    counter++;
		} else if (counter < a.length) {
		    a[counter] = args[i];
		    counter++;
		}
	    }
	}
	a = myUtils.removeNull(a);
	if (cmd.getName().equals("hotbar")) {
	    if (a.length == 3) {
		int speed = Math.abs(Integer.valueOf(a[0]).intValue());
		String w = a[1];
		World world = Bukkit.getWorld(w);
		String message = a[2];
		BottomBar.broadcast(message, speed, world);
	    } else if (a.length == 1) {
		BottomBar.broadcast(a[0]);
	    } else {

		player.sendMessage("/hotbar [speed] [world] <message>");
	    }
	} else if (cmd.getName().equals("centerbar")) {
	    CenterBar.display(player, a[0], a[1]);
	}
	return true;
    }

    public String getVersion() {
	return this.version;
    }
}
