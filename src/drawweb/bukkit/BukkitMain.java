package drawweb.bukkit;

import org.bukkit.plugin.java.*;

import org.bukkit.event.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.*;
import drawweb.shared.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class BukkitMain extends JavaPlugin implements Listener
{
    private static BukkitMain instance;
    FileConfiguration config;
    
    public BukkitMain() {
        this.config = this.getConfig();
    }
    
    public void onEnable() {
        BukkitMain.instance = this;
        SocketConfig.status = "Bukkit";
        this.config.addDefault("socketPort", (Object)SocketConfig.port);
        this.config.addDefault("socketPassword", (Object)SocketConfig.password);
        this.config.addDefault("senderPrefix", (Object)SocketConfig.prefix);
        this.config.addDefault("wrongPassword", (Object)SocketConfig.wrongPassword);
        this.config.addDefault("wrongData", (Object)SocketConfig.wrongData);
        this.config.addDefault("succesfullyLogin", (Object)SocketConfig.succesfullyLogin);
        this.config.addDefault("consoleInfo", (Object)SocketConfig.consoleInfo);
        this.config.addDefault("commandPermission", (Object)SocketConfig.commandPermission);
        this.config.addDefault("nothavePerm", (Object)SocketConfig.nothavePerm);
        this.config.addDefault("pluginReloaded", (Object)SocketConfig.pluginReloaded);
        this.config.options().copyDefaults(true);
        this.saveConfig();
        this.configLoad();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getCommand("websender").setExecutor((CommandExecutor)new BukkitCommand());
        if (SocketConfig.password.equalsIgnoreCase("123dweb")) {
            UtilSocket.createLog("PLUGIN DISABLED! PLEASE CHANGE PASSWORD FROM CONFIG!");
            return;
        }
        UtilSocket.createLog("Starting...");
        final SocketServer socket = new SocketServer();
        socket.start();
        UtilSocket.createLog("Started!");
        
        new UpdateChecker(this, 110452).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "WebSender" + ChatColor.GRAY + "] " + ChatColor.RED + "A new update of the plugin is not available.");
                UtilSocket.createLog("A new update of the plugin is not available.");
            }
            else {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "WebSender" + ChatColor.GRAY + "] " + ChatColor.GREEN + "The plugin has a new update. Download link: https://bit.ly/wsenderplugin");
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
    }
    
    public void configLoad() {
        this.reloadConfig();
        final FileConfiguration config = this.getConfig();
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
    
    public static BukkitMain getPlugin() {
        return BukkitMain.instance;
    }
    
    public static boolean sendCommand(final String command) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)getPlugin(), () -> Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command));
        return true;
    }
    
    public void getLoggerColored(final String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }
    
}
