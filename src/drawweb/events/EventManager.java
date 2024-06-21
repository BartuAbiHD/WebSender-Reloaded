package drawweb.events;

import java.net.*;

public interface EventManager
{
    EventOutput callOnCommandEvent(final Socket p0, final String p1);
    
    EventOutput callOnMessageEvent(final Socket p0, final String p1);
}
