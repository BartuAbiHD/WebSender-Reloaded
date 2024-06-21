package drawweb.bungeecord;

import java.net.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;
import drawweb.events.*;

public class BungeeEvents implements EventManager
{
    @Override
    public EventOutput callOnCommandEvent(final Socket socket, final String command) {
        final BungeeSocketOnCommandEvent event = new BungeeSocketOnCommandEvent(socket, command);
        ProxyServer.getInstance().getPluginManager().callEvent((Event)event);
        return new EventOutput(event.getCommand(), event.isCancelled());
    }
    
    @Override
    public EventOutput callOnMessageEvent(final Socket socket, final String message) {
        final BungeeSocketOnMessageEvent event = new BungeeSocketOnMessageEvent(socket, message);
        ProxyServer.getInstance().getPluginManager().callEvent((Event)event);
        return new EventOutput(event.getMessage(), event.isCancelled());
    }
}
