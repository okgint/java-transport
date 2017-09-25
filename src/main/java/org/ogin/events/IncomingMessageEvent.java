package org.ogin.events;

import org.ogin.messages.Message;

/**
 * @author ogin
 */
public interface IncomingMessageEvent<M extends Message> extends Event {
    M getMessage();
}
