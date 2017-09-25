package org.ogin.messages.requests;

import java.util.Map;

/**
 * @author ogin
 */
public class PingMessage extends AbstractRequestMessage {
    public PingMessage() {
    }

    public PingMessage(String clientId) {
        super(clientId);
    }

    public PingMessage(String id, String clientId, long timestamp, long timeToLive, Map<String, Object> headers) {
        super(id, clientId, timestamp, timeToLive, headers);
    }

    public Type getType() {
        return Type.PING;
    }

    public PingMessage copy() {
        PingMessage message = new PingMessage();

        copy(message);

        return message;
    }
}
