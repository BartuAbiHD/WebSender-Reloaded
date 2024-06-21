package drawweb.events;

import java.net.*;
import org.bukkit.event.*;

public class BukkitSocketOnMessageEvent extends Event implements Cancellable
{
    private Socket socket;
    private String message;
    private boolean cancelled;
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public BukkitSocketOnMessageEvent(final Socket socket, final String message) {
        super(true);
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
    
    public HandlerList getHandlers() {
        return BukkitSocketOnMessageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BukkitSocketOnMessageEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
