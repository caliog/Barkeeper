package org.caliog.Barkeeper.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class BarConfig {

    public enum Position {
	BOTTOM, CENTER, TOP;
    }

    public enum Message {

	LVLUP("level-up"), WM("welcome-message"), LOGIN("login"), FJ("first-join"), BROADCAST("broadcast");
	public String name;

	Message(String name) {
	    this.name = name;
	}
    }

    private static YamlConfiguration config;

    @SuppressWarnings("deprecation")
    public static void init() {
	try {
	    File file = new File("plugins/Barkeeper/");
	    if (!file.exists())
		file.createNewFile();
	    config = YamlConfiguration.loadConfiguration(file);
	    config.setDefaults(YamlConfiguration.loadConfiguration(new BarConfig().getClass().getResourceAsStream(
		    "config.yml")));
	    config.options().copyDefaults(true);
	    config.save(file);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static boolean isAnimated(Message m) {
	return config.getBoolean(m.name + ".animated", false);
    }

    public static boolean isEnabled(Message m) {
	return config.getBoolean(m.name + ".enabled", false);
    }

    public static String getMainMessage(Message m) {
	return config.getString(m.name + ".message", "");
    }

    public static String getSubMessage(Message m) {
	return config.getString(m.name + ".sub-message", "");
    }

    public static Position getPosition(Message m) {
	return Position.valueOf(config.getString("position"));
    }

    public static int getTime(Message m) {
	return config.getInt(m.name + ".time", 2);
    }
}
