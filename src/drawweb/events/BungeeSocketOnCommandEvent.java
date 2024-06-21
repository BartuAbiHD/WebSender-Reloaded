package drawweb.events;

import net.md_5.bungee.api.plugin.*;
import java.net.*;

public class BungeeSocketOnCommandEvent extends Event implements Cancellable
{
    private Socket socket;
    private String command;
    private boolean cancelled;
    
    public BungeeSocketOnCommandEvent(final Socket socket, final String command) {
        this.socket = socket;
        this.command = command;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(final String command) {
        this.command = command;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
