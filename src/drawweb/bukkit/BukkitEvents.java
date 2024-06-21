package drawweb.bukkit;

import java.net.*;
import org.bukkit.*;
import org.bukkit.event.*;
import drawweb.events.*;

public class BukkitEvents implements EventManager
{
    @Override
    public EventOutput callOnCommandEvent(final Socket socket, final String command) {
        final BukkitSocketOnCommandEvent event = new BukkitSocketOnCommandEvent(socket, command);
        Bukkit.getPluginManager().callEvent((Event)event);
        return new EventOutput(event.getCommand(), event.isCancelled());
    }
    
    @Override
    public EventOutput callOnMessageEvent(final Socket socket, final String message) {
        final BukkitSocketOnMessageEvent event = new BukkitSocketOnMessageEvent(socket, message);
        Bukkit.getPluginManager().callEvent((Event)event);
        return new EventOutput(event.getMessage(), event.isCancelled());
    }
}
