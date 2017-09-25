package org.ogin.messages.requests;

import java.util.Map;

/**
 * @author ogin
 */
public class DisconnectMessage extends AbstractRequestMessage {
    public DisconnectMessage() {
    }

    public DisconnectMessage(String clientId) {
        super(clientId);
    }

    public DisconnectMessage(
            String id,
            String clientId,
            long timestamp,
            long timeToLive,
            Map<String, Object> headers) {

        super(id, clientId, timestamp, timeToLive, headers);
    }

    public DisconnectMessage copy() {
        DisconnectMessage message = new DisconnectMessage();

        copy(message);

        return message;
    }

    public Type getType() {
        return Type.DISCONNECT;
    }
}
