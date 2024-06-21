package drawweb.bungeecord;

import net.md_5.bungee.*;
import net.md_5.bungee.api.plugin.*;
import drawweb.shared.*;
import java.io.*;
import net.md_5.bungee.config.*;
import net.md_5.bungee.api.*;

public class BungeeMain extends Plugin
{
    private static BungeeMain instance;
    
    public void onEnable() {
        BungeeMain.instance = this;
        SocketConfig.status = "BungeeCord";
        this.configLoad();
        BungeeCord.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new BungeeCommand());
        if (SocketConfig.password.equalsIgnoreCase("123dweb")) {
            UtilSocket.createLog("PLUGIN DISABLED! PLEASE CHANGE PASSWORD FROM CONFIG!");
            return;
        }
        UtilSocket.createLog("Starting...");
        final SocketServer socket = new SocketServer();
        socket.start();
        UtilSocket.createLog("Started!");
        
        new UpdateCheckerBungee(this, 110452).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                UtilSocket.createLog("A new update of the plugin is not available.");
            }
            else {
                UtilSocket.createLog("The plugin has a new update. Download link: https://bit.ly/wsenderplugin");
            }
        });
    }
    
    public void onDisable() {
        try {
            if (SocketServer.listenSock != null && !SocketServer.listenSock.isClosed()) {
                SocketServer.listenSock.close();
            }
            if (SocketServer.in != null) {
                SocketServer.in.close();
            }
            if (SocketServer.out != null) {
                SocketServer.out.close();
            }
            if (SocketServer.sock != null && !SocketServer.sock.isClosed()) {
                SocketServer.sock.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        UtilSocket.createLog("Disabled!");
    }
    
    public void configLoad() {
        Configuration config = null;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        final File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
                config.set("socketPort", (Object)SocketConfig.port);
                config.set("socketPassword", (Object)SocketConfig.password);
                config.set("senderPrefix", (Object)SocketConfig.prefix);
                config.set("wrongPassword", (Object)SocketConfig.wrongPassword);
                config.set("wrongData", (Object)SocketConfig.wrongData);
                config.set("succesfullyLogin", (Object)SocketConfig.succesfullyLogin);
                config.set("consoleInfo", (Object)SocketConfig.consoleInfo);
                config.set("commandPermission", (Object)SocketConfig.commandPermission);
                config.set("nothavePerm", (Object)SocketConfig.nothavePerm);
                config.set("pluginReloaded", (Object)SocketConfig.pluginReloaded);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(this.getDataFolder(), "config.yml"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
            SocketConfig.prefix = String.valueOf(String.valueOf(String.valueOf(config.get("senderPrefix")).replaceAll("&", "§"))) + " ";
            SocketConfig.port = config.getInt("socketPort");
            SocketConfig.password = config.getString("socketPassword");
            SocketConfig.wrongPassword = config.getString("wrongPassword").replaceAll("&", "§");
            SocketConfig.wrongData = config.getString("wrongData").replaceAll("&", "§");
            SocketConfig.succesfullyLogin = config.getString("succesfullyLogin").replaceAll("&", "§");
            SocketConfig.consoleInfo = config.getString("consoleInfo");
            SocketConfig.commandPermission = config.getString("commandPermission");
            SocketConfig.nothavePerm = config.getString("nothavePerm").replaceAll("&", "§");
            SocketConfig.pluginReloaded = config.getString("pluginReloaded").replaceAll("&", "§");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static BungeeMain getPlugin() {
        return BungeeMain.instance;
    }
    
    public static boolean sendCommand(final String command) {
        return ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
    }
}
