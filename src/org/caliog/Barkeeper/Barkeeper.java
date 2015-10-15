package org.caliog.Barkeeper;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.caliog.Barkeeper.BottomBar.BottomBar;
import org.caliog.Barkeeper.CenterBar.CenterBar;
import org.caliog.Barkeeper.TopBar.TopBar;
import org.caliog.Barkeeper.Utils.BarConfig;
import org.caliog.Barkeeper.Utils.BarConfig.Message;
import org.caliog.Barkeeper.Utils.myUtils;

public class Barkeeper extends JavaPlugin {
	public static Barkeeper plugin;
	private String version;

	public void onEnable() {
		File file = new File("plugins/Barkeeper/");
		if (!file.exists())
			file.mkdir();
		String pN = Bukkit.getServer().getClass().getPackage().getName();
		this.version = pN.substring(pN.lastIndexOf(".") + 1);
		if (!version.equals("v1_8_R3")) {
			getLogger().warning("This bukkit version is not supported! The features of this plugin may not work.");
		}
		plugin = this;
		BarConfig.init();
		TopBar.init();
		this.getServer().getPluginManager().registerEvents(new BarListener(), this);

		if (BarConfig.isEnabled(Message.BROADCAST)) {
			long time = BarConfig.getTime(Message.BROADCAST) * 20L * 60;
			Manager.scheduleRepeatingTask(new Runnable() {

				@Override
				public void run() {
					Manager.broadcast(Message.BROADCAST, "", "", "");
				}
			}, time, time);
		}
		this.getLogger().info(this.getDescription().getFullName() + " enabled!");
	}

	public void onDisable() {
		Manager.cancelAllTasks();
		this.getLogger().info(this.getDescription().getFullName() + " enabled!");
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

				if ((args[i].startsWith("\""))) {
					a[counter] = "";
					while (i < args.length && !args[i].endsWith("\"")) {
						a[counter] += " " + args[i].replace("\"", "");
						i++;
					}
					if (i < args.length)
						a[counter] += " " + args[i].replace("\"", "");
					a[counter] = a[counter].trim();
					counter++;
				} else if (counter < a.length) {
					a[counter] = args[i];
					counter++;
				}

			}
		}
		a = myUtils.removeNull(a);
		if (cmd.getName().equals("display")) {
			if (a.length >= 1) {
				if (a[0].equalsIgnoreCase("bottom")) {
					if (a.length >= 4) {
						try {
							int speed = Integer.parseInt(a[1]);
							World world = Bukkit.getWorld(a[2]);
							String message = a[3];
							BottomBar.broadcast(message, speed, world);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "/display bottom [speed] [world] <message>");
							player.sendMessage(ChatColor.RED + "[speed] needs to be a positive integer.");
							return true;
						}
					} else if (a.length == 3) {
						int speed = 0;
						World world = null;
						try {
							speed = Integer.parseInt(a[1]);
						} catch (NumberFormatException e) {
							world = Bukkit.getWorld(a[1]);
						}
						String message = a[2];
						BottomBar.broadcast(message, speed, world);
					} else if (a.length >= 2) {
						String message = a[1];
						BottomBar.broadcast(message);
					} else {
						player.sendMessage(ChatColor.RED + "/display bottom [speed] [world] <message>");
						return true;
					}
				} else if (a[0].equalsIgnoreCase("center")) {
					if (a.length >= 5) {
						try {
							int time = Integer.parseInt(a[1]);
							World w = Bukkit.getWorld(a[2]);
							String message = a[3];
							String subMessage = a[4];
							CenterBar.broadcast(message, subMessage, w, 20 * time, true);
						} catch (NumberFormatException e) {
							player.sendMessage(
									ChatColor.RED + "/display center [time] [world] <message> [sub-message]");
							player.sendMessage(ChatColor.RED + "[time] needs to be a positive integer.");
							return true;
						}
					} else if (a.length >= 2) {
						int time = 2;
						World w = null;
						try {
							time = Integer.parseInt(a[1]);
							w = Bukkit.getWorld(a[2]);
						} catch (NumberFormatException e) {
							w = Bukkit.getWorld(a[1]);
							time = -1;
						}
						String message, subMessage = "";
						if (w == null && time == -1) {
							message = a[1];
							if (a.length >= 3)
								subMessage = a[2];
						} else if (time != -1 && w != null) {
							message = a[3];
							if (a.length >= 5)
								subMessage = a[4];
						} else {
							message = a[2];
							if (a.length >= 4)
								subMessage = a[3];
						}
						if (time == -1)
							time = 2;

						CenterBar.broadcast(message, subMessage, w, time, false);
					} else {
						player.sendMessage(ChatColor.RED + "/display center [time] [world] <message> [sub-message]");
						return true;
					}
				} else if (a[0].equalsIgnoreCase("top")) {
					if (a.length >= 4) {
						try {
							int time = Integer.parseInt(a[1]);
							World w = Bukkit.getWorld(a[2]);
							String message = a[3];
							TopBar.broadcast(message, time, w);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "/display top [time] [world] <message>");
							player.sendMessage(ChatColor.RED + "[time] needs to be a positive integer.");
							return true;
						}
					} else if (a.length >= 3) {
						int time = 45;
						World w = null;
						try {
							time = Integer.parseInt(a[1]);
						} catch (NumberFormatException e) {
							w = Bukkit.getWorld(a[1]);
						}
						TopBar.broadcast(a[2], 20 * time, w);
					} else if (a.length >= 2) {
						TopBar.broadcast(a[1]);
					} else {
						player.sendMessage(ChatColor.RED + "/display top [time] [world] <message>");
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "/display <top|center|bottom>");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "/display <top|center|bottom>");
				return true;
			}
		}
		return true;
	}

	public String getVersion() {
		return this.version;
	}
}
