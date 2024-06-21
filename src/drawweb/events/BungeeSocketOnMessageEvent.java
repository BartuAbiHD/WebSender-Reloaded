package drawweb.events;

import net.md_5.bungee.api.plugin.*;
import java.net.*;

public class BungeeSocketOnMessageEvent extends Event implements Cancellable
{
    private Socket socket;
    private String message;
    private boolean cancelled;
    
    public BungeeSocketOnMessageEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
