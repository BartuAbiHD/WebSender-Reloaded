package drawweb.bukkit;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import drawweb.shared.*;

public class BukkitCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command arg1, final String arg2, final String[] args) {
        if (!(sender instanceof Player)) {
            BukkitMain.getPlugin().configLoad();
            sender.sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + SocketConfig.pluginReloaded);
            return true;
        }
        if (sender.hasPermission(SocketConfig.commandPermission) || sender.isOp()) {
            BukkitMain.getPlugin().configLoad();
            UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.pluginReloaded);
            return true;
        }
        UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.nothavePerm);
        return false;
    }
}
