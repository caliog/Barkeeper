package org.caliog.Barkeeper.BottomBar;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.caliog.Barkeeper.Barkeeper;

public class NMS {
    public static NMSUtil getUtil() {
	String version = Barkeeper.plugin.getVersion();
	try {
	    Class<?> raw = Class.forName("org.caliog.Barkeeper.BottomBar." + version + ".NMSUtil");
	    Class<? extends NMSUtil> util = raw.asSubclass(NMSUtil.class);
	    Constructor<? extends NMSUtil> constructor = util.getConstructor(new Class[0]);
	    return (NMSUtil) constructor.newInstance(new Object[0]);
	} catch (ClassNotFoundException ex) {
	    Barkeeper.plugin.getLogger().log(Level.WARNING, "Unsupported bukkit version! (" + version + ")");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
