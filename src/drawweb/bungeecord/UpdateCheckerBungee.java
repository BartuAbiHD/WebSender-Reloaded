package drawweb.bungeecord;

import java.util.function.*;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;

import java.net.*;
import java.util.*;
import java.io.*;

public class UpdateCheckerBungee {

    private BungeeMain bungeePlugin;
    private int resourceId;

    public UpdateCheckerBungee(BungeeMain bungeePlugin, int resourceId) {
        this.bungeePlugin = bungeePlugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(final Consumer<String> consumer) {
    	
    	Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.bungeePlugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                    Scanner scanner = new Scanner(inputStream)) {
               if (scanner.hasNext()) {
                   consumer.accept(scanner.next());
               }
           } catch (IOException exception) {
        	   bungeePlugin.getLogger().info("[WebSender] Cannot call updates: " + exception.getMessage());
           }
    	});
    	
    }
}