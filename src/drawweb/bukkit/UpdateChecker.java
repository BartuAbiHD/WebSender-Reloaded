package drawweb.bukkit;

import java.util.function.*;
import org.bukkit.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class UpdateChecker {

    private BukkitMain plugin;
    private int resourceId;

    public UpdateChecker(BukkitMain plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
                plugin.getLogger().info("[WebSender] Cannot call updates: " + exception.getMessage());
            }
        });
    }
}