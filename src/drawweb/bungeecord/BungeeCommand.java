package drawweb.bungeecord;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.connection.*;
import drawweb.shared.*;

public class BungeeCommand extends Command
{
    public BungeeCommand() {
        super("websender", (String)null, new String[0]);
    }
    
    @SuppressWarnings("deprecation")
	public void execute(final CommandSender sender, final String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            BungeeMain.getPlugin().configLoad();
            sender.sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + SocketConfig.pluginReloaded);
            return;
        }
        if (!sender.hasPermission(SocketConfig.commandPermission)) {
            UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.nothavePerm);
            return;
        }
        BungeeMain.getPlugin().configLoad();
        UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.pluginReloaded);
    }
}
