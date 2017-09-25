package org.ogin.messages.requests;

import org.ogin.messages.AbstractMessage;
import org.ogin.messages.RequestMessage;

import java.util.Map;

/**
 * @author ogin
 */
public abstract class AbstractRequestMessage extends AbstractMessage implements RequestMessage{

    public AbstractRequestMessage() {
    }

    public AbstractRequestMessage(String clientId) {
        super(clientId);
    }

    public AbstractRequestMessage(String id, String clientId, long timestamp, long timeToLive, Map<String, Object> headers) {
        super(id, clientId, timestamp, timeToLive, headers);
    }
}
