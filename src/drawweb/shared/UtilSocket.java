package drawweb.shared;

import drawweb.events.*;
import java.security.*;
import java.math.*;
//import javax.xml.bind.*;
//import jakarta.xml.bind.*;
import java.io.*;
import drawweb.bukkit.*;
import drawweb.bungeecord.*;
import org.bukkit.*;
import net.md_5.bungee.api.*;

public class UtilSocket
{
    private static EventManager manager;
    
    public static EventManager getManager() {
        if (UtilSocket.manager == null) {
            if (SocketConfig.isBukkit()) {
                UtilSocket.manager = new BukkitEvents();
            }
            if (SocketConfig.isBungee()) {
                UtilSocket.manager = new BungeeEvents();
            }
        }
        return UtilSocket.manager;
    }
    
    public static String hash(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(input.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() % 2 != 0) {
                result = "0" + result;
            }
            return result;
        }
        catch (Exception ex) {
            return "";
        }
    }
    
    public static String readString(final DataInputStream in, final boolean base64) throws IOException {
        final int stringSize = in.readInt();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < stringSize; ++i) {
            buffer.append(in.readChar());
        }
        return base64 ? DecodeBASE64(buffer.toString()) : buffer.toString();
    }
    
    public static String EncodeBASE64(final String text) {
        return new String(text.getBytes());
    }
    
    public static String DecodeBASE64(final String text) {
        return new String(text);
    }
    
    public static void writeString(final DataOutputStream out, final String string) throws IOException {
        out.writeInt(string.length());
        out.writeChars(string);
    }
    
    public static void sendCommand(final String command, final DataOutputStream out) throws IOException {
        boolean success;
        try {
            if (SocketConfig.isBukkit()) {
                success = BukkitMain.sendCommand(command);
            }
            else {
                success = BungeeMain.sendCommand(command);
            }
        }
        catch (Exception ex) {
            createLog(String.valueOf(String.valueOf(SocketConfig.prefix)) + "ERROR: " + ex.getMessage());
            success = false;
        }
        out.writeInt(success ? 1 : 0);
        out.flush();
    }
    
    @SuppressWarnings("deprecation")
	public static void createLog(final String data) {
        if (SocketConfig.consoleInfo.equals("true")) {
            if (SocketConfig.isBukkit()) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + data);
            }
            else {
                ProxyServer.getInstance().getConsole().sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + data);
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	public static void sendPlayerMsg(final String playerName, final String data) {
        if (SocketConfig.isBukkit()) {
            Bukkit.getPlayer(playerName).sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + data);
        }
        else {
            ProxyServer.getInstance().getPlayer(playerName).sendMessage(String.valueOf(String.valueOf(SocketConfig.prefix)) + data);
        }
    }
}
