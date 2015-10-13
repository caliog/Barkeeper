package org.caliog.Barkeeper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.caliog.Barkeeper.Utils.BarConfig;
import org.caliog.Barkeeper.Utils.BarConfig.Message;

public class BarListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
	if (event.getPlayer().hasPlayedBefore()) {
	    if (BarConfig.isEnabled(Message.LOGIN))
		Manager.broadcast(Message.LOGIN);

	} else {
	    if (BarConfig.isEnabled(Message.WM)) {
		Manager.send(event.getPlayer(), Message.WM);
	    }
	    if (BarConfig.isEnabled(Message.FJ)) {
		Manager.broadcast(Message.FJ);
	    }
	}
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLevelUp(PlayerLevelChangeEvent event) {
	if (BarConfig.isEnabled(Message.LVLUP)) {
	    if (event.getOldLevel() + 1 == event.getNewLevel()) {
		Manager.send(event.getPlayer(), Message.LVLUP);
	    }
	}
    }

}
