package org.caliog.Barkeeper;

import org.bukkit.Bukkit;

public class Manager {
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