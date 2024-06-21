package drawweb.events;

import java.net.*;
import org.bukkit.event.*;

public class BukkitSocketOnCommandEvent extends Event implements Cancellable
{
    private Socket socket;
    private String command;
    private boolean cancelled;
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public BukkitSocketOnCommandEvent(final Socket socket, final String command) {
        super(true);
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
    
    public HandlerList getHandlers() {
        return BukkitSocketOnCommandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BukkitSocketOnCommandEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
